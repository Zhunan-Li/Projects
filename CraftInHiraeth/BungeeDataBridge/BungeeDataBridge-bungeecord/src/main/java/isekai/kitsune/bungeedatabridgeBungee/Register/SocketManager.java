package isekai.kitsune.bungeedatabridgeBungee.Register;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import isekai.kitsune.bungeedatabridgeBungee.BungeeDataBridgeBungeeCord;
import isekai.kitsune.bungeedatabridgeBungee.sockets.ClientConnection;
import isekai.kitsune.bungeedatabridgeBungee.utils.DataTypeIO;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class SocketManager {
    private BungeeDataBridgeBungeeCord plugin;

    public SocketManager(BungeeDataBridgeBungeeCord plugin) {
        this.plugin = plugin;
    }

    public static void sendJoinData(String server, String eventType, String leaderName, int memberCount, List<String> members) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.flush();

            DataTypeIO.writeString(out, eventType, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, leaderName, StandardCharsets.UTF_8);
            DataTypeIO.writeVarInt(out, memberCount);
            for (int i = memberCount; i > 0; i--) {
                DataTypeIO.writeString(out, members.get(i - 1), StandardCharsets.UTF_8);
            }

            ClientConnection connection = BungeeDataBridgeBungeeCord.serverConnection.getConnectedClient(server);

            if (connection != null) {
                connection.send(stream.toByteArray());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendLeaveData(String server, String eventType, String leftPlayerName, String leaderName, int memberCount, List<String> members) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.flush();

            DataTypeIO.writeString(out, eventType, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, leftPlayerName, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, leaderName, StandardCharsets.UTF_8);
            DataTypeIO.writeVarInt(out, memberCount);
            for (int i = memberCount; i > 0; i--) {
                DataTypeIO.writeString(out, members.get(i - 1), StandardCharsets.UTF_8);
            }

            ClientConnection connection = BungeeDataBridgeBungeeCord.serverConnection.getConnectedClient(server);

            if (connection != null) {
                connection.send(stream.toByteArray());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendLeaderChangeData(String server, String eventType, String newLeaderName, int memberCount, List<String> members) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.flush();

            DataTypeIO.writeString(out, eventType, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, newLeaderName, StandardCharsets.UTF_8);
            DataTypeIO.writeVarInt(out, memberCount);
            for (int i = memberCount; i > 0; i--) {
                DataTypeIO.writeString(out, members.get(i - 1), StandardCharsets.UTF_8);
            }

            ClientConnection connection = BungeeDataBridgeBungeeCord.serverConnection.getConnectedClient(server);

            if (connection != null) {
                connection.send(stream.toByteArray());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendServerConnectedData(String server, String eventType, String leaderName, int memberCount, List<String> members) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.flush();

            DataTypeIO.writeString(out, eventType, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, leaderName, StandardCharsets.UTF_8);
            DataTypeIO.writeVarInt(out, memberCount);
            for (int i = memberCount; i > 0; i--) {
                DataTypeIO.writeString(out, members.get(i - 1), StandardCharsets.UTF_8);
            }

            ClientConnection connection = BungeeDataBridgeBungeeCord.serverConnection.getConnectedClient(server);

            if (connection != null) {
                connection.send(stream.toByteArray());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
