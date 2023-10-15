package isekai.kitsune.bungeedatabridgeBungee;

import java.util.Arrays;

import net.md_5.bungee.api.plugin.Event;

public class SocketMessageReceiveEvent extends Event {

    private final String server;
    private final byte[] data;

    public SocketMessageReceiveEvent(String server, byte[] data) {
        this.server = server;
        this.data = data;
    }

    public String getServer() {
        return server;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

}
