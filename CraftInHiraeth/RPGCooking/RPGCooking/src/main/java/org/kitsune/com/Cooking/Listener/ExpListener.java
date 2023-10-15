package org.kitsune.com.Cooking.Listener;

import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExpListener implements Listener {
    /* 處理經驗條需要三個Map */
    public static Map<UUID, BukkitRunnable> cooldownTask;
    public static Map<UUID, Long> cooldown;
    public static Map<UUID, BossBar> lvbossbar;
    private static Plugin plugin;

    public ExpListener(Plugin plugin) {
        ExpListener.plugin = plugin;
        ExpListener.plugin.getServer().getPluginManager().registerEvents(this, ExpListener.plugin);

        ExpListener.cooldownTask = new HashMap<>();
        ExpListener.cooldown = new HashMap<>();
        ExpListener.lvbossbar = new HashMap<>();
    }

    /* 需要一個Listener */
    @EventHandler
    public void ExpBarShow(PlayerExperienceGainEvent event) {
        if (event.isCancelled()) return;
        PlayerData pd = event.getPlayerData();
        if (event.getPlayerClass() != pd.getClass("chef")) return;
        new BukkitRunnable() {
            public void run() {

                PlayerClass playerClass = pd.getClass("chef");

                int level = playerClass.getLevel();
                double exp = playerClass.getExp();
                double next = playerClass.getRequiredExp();
                float percentage = (float) (exp / next);
                Player p = event.getPlayerData().getPlayer();

                if (ExpListener.lvbossbar.containsKey(event.getPlayerData().getPlayer().getUniqueId())) { //HiraethFishing這邊自己改一下
                    ExpListener.cooldown.put(p.getUniqueId(), 5L);
                    ExpListener.lvbossbar.get(p.getUniqueId()).setProgress(percentage);
                    ExpListener.lvbossbar.get(p.getUniqueId()).setTitle("料理等級 Lv." + level);
                } else {
                    BossBar bossBar = Bukkit.createBossBar("料理等級 Lv." + level, BarColor.GREEN, BarStyle.SOLID);
                    bossBar.setProgress(percentage);
                    bossBar.setVisible(true);
                    bossBar.addPlayer(p);
                    ExpListener.lvbossbar.put(p.getUniqueId(), bossBar);
                    ExpListener.cooldown.put(p.getUniqueId(), 5L);
                    ExpListener.cooldownTask.put(p.getUniqueId(), new BukkitRunnable() {
                        @Override
                        public void run() {
                            Long second = ExpListener.cooldown.get(p.getUniqueId());
                            if (second == 0) {
                                this.cancel();
                                ExpListener.lvbossbar.remove(p.getUniqueId()).removeAll();
                                ExpListener.cooldownTask.remove(p.getUniqueId());
                                ExpListener.cooldown.remove(p.getUniqueId());
                            } else {
                                ExpListener.cooldown.put(p.getUniqueId(), ExpListener.cooldown.get(p.getUniqueId()) - 1);
                                ExpListener.lvbossbar.get(p.getUniqueId()).setVisible(true);
                                bossBar.addPlayer(p);
                            }
                        }
                    });
                    ExpListener.cooldownTask.get(p.getUniqueId()).runTaskTimer(ExpListener.plugin, 0L, 20L);
                }
            }
        }.runTaskLater(plugin, 1);
    }
}
