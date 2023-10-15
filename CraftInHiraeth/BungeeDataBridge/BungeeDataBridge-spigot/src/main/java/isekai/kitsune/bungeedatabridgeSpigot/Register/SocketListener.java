package isekai.kitsune.bungeedatabridgeSpigot.Register;

import isekai.kitsune.bungeedatabridgeSpigot.BungeeDataBridgeSpigot;
import isekai.kitsune.bungeedatabridgeSpigot.PartyDataManager;
import isekai.kitsune.bungeedatabridgeSpigot.SocketMessageReceiveEvent;
import isekai.kitsune.bungeedatabridgeSpigot.utils.DataTypeIO;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SocketListener implements Listener {

    @EventHandler
    public void onSocket(SocketMessageReceiveEvent event) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));

        try {
            String eventType = DataTypeIO.readString(in, StandardCharsets.UTF_8);
            if (eventType.equals("PARTY:JOIN")) {
                String leaderName = DataTypeIO.readString(in, StandardCharsets.UTF_8);
                int memberCounts = DataTypeIO.readVarInt(in);
                List<String> memberNames = new ArrayList<>();
                for (int i = memberCounts; i > 0; i--) {
                    memberNames.add(DataTypeIO.readString(in, StandardCharsets.UTF_8));
                }
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        PartyDataManager.updateInformation(leaderName, memberNames, null);
                    }
                }.runTask(BungeeDataBridgeSpigot.plugin);
                return;
            }
            if (eventType.equals("PARTY:LEAVE")) {
                String leftPlayer = DataTypeIO.readString(in, StandardCharsets.UTF_8);
                String leaderName = DataTypeIO.readString(in, StandardCharsets.UTF_8);
                int memberCounts = DataTypeIO.readVarInt(in);
                List<String> memberNames = new ArrayList<>();
                for (int i = memberCounts; i > 0; i--) {
                    memberNames.add(DataTypeIO.readString(in, StandardCharsets.UTF_8));
                }
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        PartyDataManager.leaveParty(leftPlayer);
                        PartyDataManager.updateInformation(leaderName, memberNames, leftPlayer);
                    }
                }.runTaskLater(BungeeDataBridgeSpigot.plugin,10);
                Bukkit.getConsoleSender().sendMessage("[PARTY:LEAVE] Update team "+leaderName);
                return;
            }

            if (eventType.equals("PARTY:LEADERCHANGE")) {
                String leaderName = DataTypeIO.readString(in, StandardCharsets.UTF_8);
                int memberCounts = DataTypeIO.readVarInt(in);
                List<String> memberNames = new ArrayList<>();
                for (int i = memberCounts; i > 0; i--) {
                    memberNames.add(DataTypeIO.readString(in, StandardCharsets.UTF_8));
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("LEADERLEAVE MESSAGE");
                        PartyDataManager.updateInformationOnLeaderChange(leaderName, memberNames);
                    }
                }.runTaskLater(BungeeDataBridgeSpigot.plugin, 20);  //25

            }

            if (eventType.equals("SERVER:SWITCH")) {
                String leaderName = DataTypeIO.readString(in, StandardCharsets.UTF_8);
                int memberCounts = DataTypeIO.readVarInt(in);
                List<String> memberNames = new ArrayList<>();
                for (int i = memberCounts; i > 0; i--) {
                    memberNames.add(DataTypeIO.readString(in, StandardCharsets.UTF_8));
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(memberNames.toString() + " test");

                        Bukkit.broadcastMessage("update (SERVER:SWITCH) " + leaderName);
                        PartyDataManager.updatePartyInformationMapOnSwitchJoin(leaderName, memberNames);
                    }
                }.runTaskLater(BungeeDataBridgeSpigot.plugin,15); //20

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
