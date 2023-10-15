package isekai.kitsune.bungeedatabridgeBungee;

import isekai.kitsune.bungeedatabridgeBungee.Register.EventListener;
import isekai.kitsune.bungeedatabridgeBungee.sockets.ClientConnection;
import isekai.kitsune.bungeedatabridgeBungee.sockets.ServerConnection;
import isekai.kitsune.bungeedatabridgeBungee.utils.DataTypeIO;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class BungeeDataBridgeBungeeCord extends Plugin implements Listener {

    public static ServerConnection serverConnection;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new EventListener(this);

        ProxyServer.getInstance().getPluginManager().registerListener(this, this);

        serverConnection = new ServerConnection(3000);
        serverConnection.start();
        getLogger().info(ChatColor.GREEN + "[SocketProxyLOOHP] SocketProxy(By LOOHP) has been enabled!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.RED + "[SocketProxyLOOHP] SocketProxy(By LOOHP) has been disabled!");

    }


    @EventHandler
    public void onSocket(SocketMessageReceiveEvent event) {
        if (event.getServer().equals("lobby")) {
            event.getData();
            getLogger().info(event.getData().length+"");
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            try {
                String message = DataTypeIO.readString(in, StandardCharsets.UTF_8) + " " + DataTypeIO.readString(in, StandardCharsets.UTF_8) + " PROXYY!!!!!";
                ClientConnection connection = serverConnection.getConnectedClient(event.getServer());
                if (connection != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(stream);
                    out.flush();
                    DataTypeIO.writeString(out, message, StandardCharsets.UTF_8);
                    connection.send(stream.toByteArray());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
