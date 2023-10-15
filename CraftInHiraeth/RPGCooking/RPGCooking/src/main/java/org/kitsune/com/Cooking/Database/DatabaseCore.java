package org.kitsune.com.Cooking.Database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class DatabaseCore {
    public static Map<Player, Map<Integer, Boolean>> playerData = new HashMap<>();
    private Plugin plugin;

    public DatabaseCore(Plugin plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTask(this.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                    if (!Database.playerExists(p)) {
                        Database.createPlayer(p);
                    }
                });
            }
        });
        Database.setup();

        new PlayerHandler(this.plugin);
    }
}
