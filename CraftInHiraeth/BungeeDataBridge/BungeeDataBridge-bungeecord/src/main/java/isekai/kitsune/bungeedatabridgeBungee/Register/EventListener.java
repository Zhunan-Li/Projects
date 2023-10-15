package isekai.kitsune.bungeedatabridgeBungee.Register;

import de.simonsator.partyandfriends.api.events.party.LeftPartyEvent;
import de.simonsator.partyandfriends.api.events.party.PartyJoinEvent;
import de.simonsator.partyandfriends.api.events.party.PartyLeaderChangedEvent;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import isekai.kitsune.bungeedatabridgeBungee.BungeeDataBridgeBungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class EventListener implements Listener {
    private BungeeDataBridgeBungeeCord plugin;
    private PAFPlayerManager pafPlayerManager;
    private PartyManager partyManager;

    public EventListener(BungeeDataBridgeBungeeCord plugin) {
        this.plugin = plugin;
        this.pafPlayerManager = PAFPlayerManager.getInstance();
        this.partyManager = PartyManager.getInstance();
        this.plugin.getProxy().getPluginManager().registerListener(this.plugin, this);
    }


    @EventHandler
    public void onPartyJoin(PartyJoinEvent event) {


        List<String> servers = new ArrayList<>();
        servers.add(event.getPlayer().getServer().getName());
        PlayerParty playerParty = event.getParty();
        String leaderName = playerParty.getLeader().getName();
        if (!servers.contains(playerParty.getLeader().getServer().getName())) {
            servers.add(playerParty.getLeader().getServer().getName());
        }
        List<String> memberNames = new ArrayList<>();
        memberNames.add(event.getPlayer().getName());
        for (OnlinePAFPlayer onlinePAFPlayer : playerParty.getAllPlayers()) {
            if (!onlinePAFPlayer.getName().equals(leaderName)) {
                memberNames.add(onlinePAFPlayer.getName());
                String server = onlinePAFPlayer.getServer().getName();
                if (!servers.contains(server)) {
                    servers.add(server);
                }
            }
        }
        int memberCount = memberNames.size();

        for (String server : servers) {
            SocketManager.sendJoinData(server, "PARTY:JOIN", leaderName, memberCount, memberNames);
            System.out.println("[PartyJoinEvent] Updated Server " + server);
        }

    }

    @EventHandler
    public void onPartyLeave(LeftPartyEvent event) {
        String leaderName = (event.getParty().getLeader() != null) ? event.getParty().getLeader().getName() : "";
        /* if event.getParty().getLeader()) == null -> leader left */


        String leftPlayerName = event.getPlayer().getName();

        List<String> servers = new ArrayList<>();

        if (event.getParty().getLeader() != null) {
            if (!servers.contains(event.getParty().getLeader().getServer().getName())) {
                servers.add(event.getParty().getLeader().getServer().getName());
            }
        }

        List<String> memberNames = new ArrayList<>();
        for (OnlinePAFPlayer member : event.getParty().getPlayers()) {
            memberNames.add(member.getName());
            String server = member.getServer().getName();
            if (!servers.contains(server)) servers.add(server);
        }

        for (String server : servers) {
            SocketManager.sendLeaveData(server, "PARTY:LEAVE", leftPlayerName, leaderName, memberNames.size(), memberNames);
            System.out.println("[LeftPartyEvent] Updated Server " + server);
        }

    }

    @EventHandler
    public void onPartyLeaderChange(PartyLeaderChangedEvent event) {
        System.out.println(event.getParty().getLeader().getName() + " getParty");
        System.out.println(event.getNewLeader().getName() + " new Leader");
        List<String> servers = new ArrayList<>();
        servers.add(event.getNewLeader().getPlayer().getServer().getInfo().getName());
        String newLeaderName = event.getNewLeader().getName();
        List<String> memberNames = new ArrayList<>();
        for (OnlinePAFPlayer onlinePAFPlayer : event.getParty().getPlayers()) {
            memberNames.add(onlinePAFPlayer.getName());
            String server = onlinePAFPlayer.getServer().getName();
            if (!servers.contains(server)) servers.add(server);
        }
        for (String server : servers) {
            SocketManager.sendLeaderChangeData(server, "PARTY:LEADERCHANGE", newLeaderName, memberNames.size(), memberNames);
            System.out.println("[PartyLeaderChangeEvent] Updated Server " + server);
        }
    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        List<String> servers = new ArrayList<>();
        List<String> memberNames = new ArrayList<>();
        PlayerParty playerParty = this.partyManager.getParty(event.getPlayer().getUniqueId());
        if (playerParty != null) {
            String leaderName = playerParty.getLeader().getName();
            servers.add(playerParty.getLeader().getPlayer().getServer().getInfo().getName());
            for (OnlinePAFPlayer onlinePAFPlayer : playerParty.getPlayers()) {
                String server = onlinePAFPlayer.getServer().getName();
                if (!servers.contains(server)) servers.add(server);
                memberNames.add(onlinePAFPlayer.getName());
            }
            for (String server : servers) {
                SocketManager.sendServerConnectedData(server, "SERVER:SWITCH", leaderName, memberNames.size(), memberNames);
                System.out.println("[ServerConnectedEvent] Updated Server " + server);
            }
        }
    }

    @EventHandler
    public void onServerConnected(ServerDisconnectEvent event) {
        List<String> servers = new ArrayList<>();
        List<String> memberNames = new ArrayList<>();
        PlayerParty playerParty = this.partyManager.getParty(event.getPlayer().getUniqueId());
        if (playerParty != null) {
            servers.add(event.getPlayer().getServer().getInfo().getName());
            String leaderName = playerParty.getLeader().getName();
            if (!servers.contains(playerParty.getLeader().getPlayer().getServer().getInfo().getName())) servers.add(playerParty.getLeader().getPlayer().getServer().getInfo().getName());
            for (OnlinePAFPlayer onlinePAFPlayer : playerParty.getPlayers()) {
                String server = onlinePAFPlayer.getServer().getName();
                if (!servers.contains(server)) servers.add(server);
                memberNames.add(onlinePAFPlayer.getName());
            }
            for (String server : servers) {
                SocketManager.sendServerConnectedData(server, "SERVER:SWITCH", leaderName, memberNames.size(), memberNames);
                System.out.println("[ServerDisconnectedEvent] Updated Server " + server);
            }
        }
    }
}
