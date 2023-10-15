package isekai.kitsune.bungeedatabridgeBungee.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


import isekai.kitsune.bungeedatabridgeBungee.SocketMessageReceiveEvent;
import isekai.kitsune.bungeedatabridgeBungee.utils.DataTypeIO;
import net.md_5.bungee.api.ProxyServer;

public class ClientConnection extends Thread {

    private static final String SECRET = "Why 1/10 of me is given out";

    private ServerConnection serverConnection;

    private String server;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public ClientConnection(Socket clientSocket, ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.clientSocket = clientSocket;
        try {
            this.out = new DataOutputStream(clientSocket.getOutputStream());
            this.in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        try {
            clientSocket.setKeepAlive(true);

            if (!DataTypeIO.readString(in, StandardCharsets.UTF_8).equals(SECRET)) {
                close();
            }

            server = DataTypeIO.readString(in, StandardCharsets.UTF_8);
            if (!serverConnection.confirmClient(server, this)) {
                close();
            }

            while (true) {
                int size = in.readInt();
                byte[] data = new byte[size];
                in.readFully(data);

                ProxyServer.getInstance().getPluginManager().callEvent(new SocketMessageReceiveEvent(server, data));
            }
        } catch (IOException e) {
            try {
                close();
            } catch (IOException e1) {}
        }
    }

    public void send(byte[] data) {
        try {
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        } catch (IOException e) {
            try {
                close();
            } catch (IOException e1) {}
        }
    }

    public void close() throws IOException {
        serverConnection.removeClient(this);
        clientSocket.close();
    }

}