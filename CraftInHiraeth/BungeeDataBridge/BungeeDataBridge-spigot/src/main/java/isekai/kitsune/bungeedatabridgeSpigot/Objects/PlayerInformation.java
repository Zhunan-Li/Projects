package isekai.kitsune.bungeedatabridgeSpigot.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class PlayerInformation {
    private String name;
    private Player player;
    private boolean online;

    public PlayerInformation(String name) {
        this.name = name;
        this.player = Bukkit.getPlayer(name);
        if (this.player != null && this.player.isOnline()) {
            this.online = true;
        } else {
            this.online = false;
        }

    }

    public boolean UpdatePlayer() {
        this.player = Bukkit.getPlayer(name);
        if (this.player != null && this.player.isOnline()) {
            System.out.println(name + " is online");
            this.online = true;
            return true;
        } else {
            System.out.println(name + " is offline");
            this.online = false;
            return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Boolean getOnline() {
        return this.online;
    }
}
