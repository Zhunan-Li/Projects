package isekai.kitsune.bungeedatabridgeSpigot.Register;

import isekai.kitsune.bungeedatabridgeSpigot.BungeeDataBridgeSpigot;
import isekai.kitsune.bungeedatabridgeSpigot.PartyDataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    private BungeeDataBridgeSpigot plugin;

    public EventListener(BungeeDataBridgeSpigot plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!PartyDataManager.playerPartyMap.containsKey(event.getPlayer())) {
            PartyDataManager.playerPartyMap.put(event.getPlayer(), null);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
//            if (Bukkit.getOnlinePlayers().isEmpty()) {
//                PartyDataManager.stringPartyInformationMap.clear();
//            }
            PartyDataManager.playerPartyMap.remove(event.getPlayer());
        }, 1L);
    }
}
