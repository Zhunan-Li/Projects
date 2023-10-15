package isekai.kitsune.bungeedatabridgeSpigot;

import isekai.kitsune.bungeedatabridgeSpigot.Register.EventListener;
import isekai.kitsune.bungeedatabridgeSpigot.Register.PlayerListener;
import isekai.kitsune.bungeedatabridgeSpigot.Register.SocketListener;
import isekai.kitsune.bungeedatabridgeSpigot.ScoreBoard.ScoreBoardManager;
import isekai.kitsune.bungeedatabridgeSpigot.sockets.Client;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BungeeDataBridgeSpigot extends JavaPlugin{

    public static Plugin plugin;

    public static ScoreBoardManager SBManager;

    public static String SERVER = "lobby";
    public static String HOST = "localhost";
    public static int PORT = 3000;

    private static Client clientConnection;


    @Override
    public void onEnable() {

        plugin = this;

        File config = new File(plugin.getDataFolder()+"/config.yml");
        if (!config.exists()) {
            Bukkit.getConsoleSender().sendMessage(net.md_5.bungee.api.ChatColor.RED+ "No config Found! Creating default config.");
            plugin.saveResource("config.yml", false);
        }
        FileConfiguration fileread= YamlConfiguration.loadConfiguration(config);
        SERVER = fileread.getString("servername");
        HOST = fileread.getString("serverip");
        PORT = fileread.getInt("port");




        clientConnection = new Client(this, SERVER, HOST, PORT);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            if (clientConnection.socket.isClosed()) {
                clientConnection = new Client(this, SERVER, HOST, PORT);
            }
        }, 0, 20);

        getLogger().info(ChatColor.GREEN + "[SocketProxyLOOHP] SocketProxy(By LOOHP) has been enabled!");


        // Plugin startup logic
        new EventListener(this);
        new PlayerListener(this);
        SBManager = new ScoreBoardManager(this);
        PartyDataManager.plugin = this;
        getServer().getPluginCommand("pi").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new SocketListener(), this);

    }

    @Override
    public void onDisable() {


        getLogger().info(ChatColor.GREEN + "[SocketProxyLOOHP] SocketProxy(By LOOHP) has been disabled!");


        // Plugin shutdown logic
    }



}
