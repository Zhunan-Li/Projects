package isekai.kitsune.bungeedatabridgeSpigot.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import isekai.kitsune.bungeedatabridgeSpigot.SocketMessageReceiveEvent;
import isekai.kitsune.bungeedatabridgeSpigot.utils.DataTypeIO;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class Client {

    private static final String SECRET = "Why 1/10 of me is given out";

    public Socket socket;
    public String host;
    public int port;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(Plugin plugin, String server, String host, int port) {
        try {
            if (socket != null) {
                if (socket.isConnected()) {
                    socket.close();
                }
            }
            this.host = host;
            this.port = port;
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            DataTypeIO.writeString(out, SECRET, StandardCharsets.UTF_8);
            DataTypeIO.writeString(out, server, StandardCharsets.UTF_8);

            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> recieve());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {}
        }
    }

    public void send(byte[] data) {
        try {
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {}
        }
    }

    public void recieve() {
        try {
            int size = in.readInt();
            byte[] data = new byte[size];
            in.readFully(data);

            Bukkit.getPluginManager().callEvent(new SocketMessageReceiveEvent(data));
            recieve();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {}
        }
    }

}
