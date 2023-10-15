package isekai.kitsune.bungeedatabridgeSpigot;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SocketMessageReceiveEvent extends Event {

    private final byte[] data;

    public SocketMessageReceiveEvent(byte[] data) {
        super(!Bukkit.isPrimaryThread());
        this.data = data;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
