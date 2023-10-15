package isekai.kitsune.bungeedatabridgeSpigot;

import isekai.kitsune.bungeedatabridgeSpigot.Objects.PartyInformation;
import isekai.kitsune.bungeedatabridgeSpigot.Objects.PlayerInformation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    private static String online = ChatColor.GREEN + "";
    private static String offline = ChatColor.RED + "";

    public static void sendInformation(CommandSender player, PartyInformation pi) {
        player.sendMessage("Leader:");
        if (pi.getLeaderPlayer().getOnline()) {
            player.sendMessage(online + pi.getLeaderPlayer().getName());
        } else {
            player.sendMessage(offline + pi.getLeaderPlayer().getName());
        }

        player.sendMessage("Members:");
        for (PlayerInformation playerInformation : pi.getPartyMembers()) {
            if (playerInformation.getOnline()) {
                player.sendMessage(online + playerInformation.getName());
            } else {
                player.sendMessage(offline + playerInformation.getName());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        CommandSender player = sender;
        String arg = args[0];
        if (arg.equalsIgnoreCase("self")) {
            if (PartyDataManager.playerPartyMap.get(player) == null) {
                player.sendMessage("No Party");
            } else {

                PartyInformation pi = PartyDataManager.stringPartyInformationMap.get(PartyDataManager.playerPartyMap.get(player));

                player.sendMessage("Leader:");
                if (pi.getLeaderPlayer().getOnline()) {
                    player.sendMessage(online + pi.getLeaderPlayer().getName());
                } else {
                    player.sendMessage(offline + pi.getLeaderPlayer().getName());
                }

                player.sendMessage("Members:");
                for (PlayerInformation playerInformation : pi.getPartyMembers()) {
                    if (playerInformation.getOnline()) {
                        player.sendMessage(online + playerInformation.getName());
                    } else {
                        player.sendMessage(offline + playerInformation.getName());
                    }
                }
            }
        } else if (arg.equalsIgnoreCase("all")) {
            player.sendMessage("PlayerPartyMap");
            for (Player _player : PartyDataManager.playerPartyMap.keySet()) {
                player.sendMessage(_player.getName() + " " + PartyDataManager.playerPartyMap.get(_player));
            }
            player.sendMessage("StringPartyInformation");
            for (String string : PartyDataManager.stringPartyInformationMap.keySet()) {
                sendInformation(player, PartyDataManager.stringPartyInformationMap.get(string));
                player.sendMessage("");
            }
        } else {
            for (PlayerInformation playerInformation : PartyDataManager.stringPartyInformationMap.get(arg).getPartyMembers()) {
                player.sendMessage(playerInformation.getName() + " " + playerInformation.getOnline());
            }
        }

        return false;
    }
}
