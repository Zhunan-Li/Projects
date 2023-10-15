package isekai.kitsune.hiraeth;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;

public class EventHandler implements Listener {
    private HiraethLastServerRecord plugin;

    public EventHandler(HiraethLastServerRecord plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerListener(this.plugin, this);
    }

    @net.md_5.bungee.event.EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxyServer.getInstance().getScheduler().runAsync(this.plugin, () -> {
            if (!Database.playerExists(event.getPlayer().getUniqueId())) {
                Database.createPlayer(event.getPlayer());
            }
            Database.loadPlayer(event.getPlayer());
        });
    }

    @net.md_5.bungee.event.EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        String serverName = event.getPlayer().getServer().getInfo().getName();
        if (!Config.blackListServers.contains(serverName)) {
            Database.setServerName(event.getPlayer(), serverName);
        }
    }
}
