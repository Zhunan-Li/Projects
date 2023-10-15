package me.springbreezex.craftinhiraethcore.fishing;

import me.springbreezex.craftinhiraethcore.fishing.configs.Config;
import me.springbreezex.craftinhiraethcore.fishing.listeners.FishingListener;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HiraethFishing {
    public static Plugin plugin;

    /* Level Boss Bar */
    public static Map<UUID, BukkitRunnable> cooldownTask;
    public static Map<UUID, Long> cooldown;
    public static Map<UUID, BossBar> lvbossbar;

    public HiraethFishing(Plugin _plugin) {
        this.plugin = _plugin;

        try {
            new Config(this.plugin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new FishingListener(this.plugin);

        /* Level Boss Bar */

        lvbossbar = new HashMap<>();
        cooldown = new HashMap<>();
        cooldownTask = new HashMap<>();
    }

    public void onUnload() {

    }
}
