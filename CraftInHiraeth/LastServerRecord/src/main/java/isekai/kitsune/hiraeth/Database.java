package isekai.kitsune.hiraeth;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.*;
import java.util.UUID;

public class Database {
    private static Connection connection;
    private static String host = "HOST IP";
    private static String database = "DATABASE NAME";
    private static String username = "USERNAME";
    private static String password = "PASSWORD";
    private static String table = "BungeeCord_PlayerLastDisconnectedServer";
    private static int port = 3306;
    private static String infoString = "[LastServer]";

    private static Object syncdb = new Object();

    public static void setup() {
        synchronized (syncdb) {
            mysqlSetup(true);
            createTable();
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void open() {
        mysqlSetup(false);
    }

    public static void mysqlSetup(boolean echo) {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));
            if (echo) {
                ProxyServer.getInstance().getLogger().info(ChatColor.GREEN + infoString.concat(" MYSQL CONNECTED"));
            }
        } catch (SQLException e) {
            ProxyServer.getInstance().getLogger().info(ChatColor.RED + infoString.concat(" MYSQL Failed to connect! (SQLException)"));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            ProxyServer.getInstance().getLogger().info(ChatColor.RED + infoString.concat(" MYSQL Failed to connect! (ClassNotFoundException)"));
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        Database.connection = connection;
    }

    public static void createTable() {
        synchronized (syncdb) {
            open();
            try {
                String sql = "CREATE TABLE IF NOT EXISTS " + table + " " +
                        "(UUID Text, " +
                        "NAME Text," +
                        "SERVERNAME Text)";
                PreparedStatement statement = getConnection().prepareStatement(sql);

                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean playerExists(ProxiedPlayer player) {
        return playerExists(player.getUniqueId());
    }

    public static boolean playerExists(UUID uuid) {
        synchronized (syncdb) {
            boolean exist = false;
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, uuid.toString());

                ResultSet results = statement.executeQuery();
                if (results.next()) {
                    exist = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return exist;
        }
    }

    public static void createPlayer(ProxiedPlayer player) {
        synchronized (syncdb) {
            open();
            try {
                String statement = "INSERT INTO " + table + " (UUID,NAME,SERVERNAME) VALUES (?,?,?)";
                PreparedStatement insert = getConnection().prepareStatement(statement);
                insert.setString(1, player.getUniqueId().toString());
                insert.setString(2, player.getName());
                insert.setString(3, Config.defaultServer);
                insert.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean reset(ProxiedPlayer player) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();
                try {
                    PreparedStatement statement2 = getConnection().prepareStatement("UPDATE " + table + " SET SERVERNAME=? WHERE UUID=?");
                    statement2.setString(1, Config.defaultServer);
                    statement2.setString(2, player.getUniqueId().toString());
                    statement2.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                HiraethLastServerRecord.playerData.put(player, Config.defaultServer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean setServerName(ProxiedPlayer player, String serverName) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();

                try {
                    PreparedStatement statement2 = getConnection().prepareStatement("UPDATE " + table + " SET SERVERNAME=? WHERE UUID=?");
                    statement2.setString(1, serverName);
                    statement2.setString(2, player.getUniqueId().toString());
                    statement2.executeUpdate();
                    HiraethLastServerRecord.playerData.put(player, serverName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void loadPlayer(ProxiedPlayer player) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();
                String serverName = Config.defaultServer;
                try {
                    serverName = results.getString("SERVERNAME");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                HiraethLastServerRecord.playerData.put(player, serverName);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
