package org.kitsune.com.Cooking.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kitsune.com.Cooking.Configs.Configs;
import org.kitsune.com.Cooking.Configs.RecipeConfigs;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {
    private static Connection connection;
    private static FileConfiguration config = Configs.configuration;
    private static String host, database, username, password;
    private static String table = "CookingRecipes_UserData";
    private static int port;
    private static String infoString = "[CookingRecipes]";

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
        host = config.getString("MySQL.Host");
        port = config.getInt("MySQL.Port");
        database = config.getString("MySQL.DataBase");
        username = config.getString("MySQL.UserName");
        password = config.getString("MySQL.Password");

        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));
            if (echo) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + infoString.concat(" MYSQL CONNECTED"));
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + infoString.concat(" MYSQL Failed to connect! (SQLException)"));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + infoString.concat(" MYSQL Failed to connect! (ClassNotFoundException)"));
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
                        "NAME Text";

                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    sql = sql + ", RECIPE" + i + " BOOLEAN";
                }
                sql = sql + ")";
                PreparedStatement statement = getConnection().prepareStatement(sql);

                statement.execute();

                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    try {
                        String updateSql = "ALTER TABLE " + table + " ADD RECIPE" + i + " BOOLEAN";
                        PreparedStatement updateStatement = getConnection().prepareStatement(updateSql);

                        updateStatement.execute();
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + infoString.concat(" Added Colume RECIPE") + i);
                    } catch (SQLException e) {
                        continue;
                    }

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
    }

    public static boolean playerExists(Player player) {
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

    public static void createPlayer(Player player) {
        synchronized (syncdb) {
            open();
            try {
                String statement = "INSERT INTO " + table + " (UUID,NAME";
                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    statement = statement + ",RECIPE" + i;
                }
                statement = statement + ") VALUES (?,?";
                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    statement = statement + ",?";
                }
                statement = statement + ")";
                PreparedStatement insert = getConnection().prepareStatement(statement);
                insert.setString(1, player.getUniqueId().toString());
                insert.setString(2, player.getName());
                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    insert.setBoolean(3 + i, false);
                }
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

    public static boolean reset(Player player) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();
                Map<Integer, Boolean> bools = new HashMap<>();
                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    try {
                        PreparedStatement statement2 = getConnection().prepareStatement("UPDATE " + table + " SET RECIPE" + i + "=? WHERE UUID=?");
                        statement2.setBoolean(1, false);
                        statement2.setString(2, player.getUniqueId().toString());
                        statement2.executeUpdate();
                        bools.put(i, false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                DatabaseCore.playerData.put(player, bools);
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

    public static boolean achievedRecipe(Player player, int id) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();

                try {
                    PreparedStatement statement2 = getConnection().prepareStatement("UPDATE " + table + " SET RECIPE" + id + "=? WHERE UUID=?");
                    statement2.setBoolean(1, true);
                    statement2.setString(2, player.getUniqueId().toString());
                    statement2.executeUpdate();
                    DatabaseCore.playerData.get(player).put(id, true);
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

    public static void loadPlayer(Player player) {
        synchronized (syncdb) {
            open();
            try {
                PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM " + table + " WHERE UUID=?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();
                results.next();
                Map<Integer, Boolean> bools = new HashMap<>();
                for (int i = 0; i < RecipeConfigs.recipeMap.size(); i++) {
                    try {
                        boolean bool = results.getBoolean("RECIPE" + i);
                        if (bool == false) {
                            PreparedStatement statement2 = getConnection().prepareStatement("UPDATE " + table + " SET RECIPE" + i + "=? WHERE UUID=?");
                            statement2.setBoolean(1, false);
                            statement2.setString(2, player.getUniqueId().toString());
                            statement2.executeUpdate();
                        }
                        bools.put(i, bool);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

                DatabaseCore.playerData.put(player, bools);

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
