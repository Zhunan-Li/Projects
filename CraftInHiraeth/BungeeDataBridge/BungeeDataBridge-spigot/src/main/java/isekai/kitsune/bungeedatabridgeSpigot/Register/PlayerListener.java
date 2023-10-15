package isekai.kitsune.bungeedatabridgeSpigot.Register;

import isekai.kitsune.bungeedatabridgeSpigot.BungeeDataBridgeSpigot;
import isekai.kitsune.bungeedatabridgeSpigot.ScoreBoard.ScoreBoardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Score;

public class PlayerListener implements Listener {
    private BungeeDataBridgeSpigot plugin;

    public PlayerListener(BungeeDataBridgeSpigot plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        //BungeeDataBridgeSpigot.SBManager.CreatePlayerScoreBoard(e.getPlayer());
        ScoreBoardManager.CreatePlayerScoreBoard(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ScoreBoardManager.ClearAll(e.getPlayer());
    }
}
