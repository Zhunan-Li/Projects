package isekai.kitsune.bungeedatabridgeBungee.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public class ServerConnection extends Thread {

    private List<ClientConnection> pendingClients = Collections.synchronizedList(new ArrayList<>());
    private Map<String, ClientConnection> clients = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;
    private int serverPort;

    public ServerConnection(int port) {
        this.serverPort = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
            while (true) {
                Socket connection = serverSocket.accept();
                ClientConnection sc = new ClientConnection(connection, this);
                pendingClients.add(sc);
                sc.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean confirmClient(String server, ClientConnection client) {
        ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
        if (info == null) {
            return false;
        }
        if (!pendingClients.contains(client)) {
            return false;
        }
        pendingClients.remove(client);
        clients.put(server, client);
        return true;
    }

    public void removeClient(ClientConnection client) {
        pendingClients.remove(client);
        clients.values().remove(client);
    }

    public Map<String, ClientConnection> getConnectedClients() {
        return clients;
    }

    public ClientConnection getConnectedClient(String server) {
        return clients.get(server);
    }

    public List<ClientConnection> getPendingClients() {
        return pendingClients;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

}
