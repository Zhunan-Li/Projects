package isekai.kitsune.bungeedatabridgeSpigot;

import isekai.kitsune.bungeedatabridgeSpigot.Objects.PartyInformation;
import isekai.kitsune.bungeedatabridgeSpigot.Objects.PlayerInformation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartyDataManager {
    public static Map<Player, String> playerPartyMap = new HashMap<>();
    public static Map<String, PartyInformation> stringPartyInformationMap = new HashMap<>();
    public static BungeeDataBridgeSpigot plugin;


    public static void leaveParty(String leavePlayer) {
        Player player = Bukkit.getPlayer(leavePlayer);
        if (player != null && player.isOnline()) {
            if (playerPartyMap.containsKey(player)) playerPartyMap.put(player, null);
        }
    }

    /* 人員變動 */
    public static void updateInformation(String leaderName, List<String> memberNames, String leftPlayer) { //TBU
        /* 如果隊長離開隊伍 */
        if (leaderName.equals("")) {
            /* 而且只剩下一個人 */
            if (memberNames.size() == 1) {
                Player p = Bukkit.getPlayer(memberNames.get(0));
                if (p == null || !playerPartyMap.containsKey(p)) {
                    if (stringPartyInformationMap.containsKey(leftPlayer)) {
                        stringPartyInformationMap.get(leftPlayer).clearParty();
                        stringPartyInformationMap.remove(leftPlayer);
                    }
                    return;
                }
                if (stringPartyInformationMap.containsKey(playerPartyMap.get(p))) {
                    stringPartyInformationMap.get(playerPartyMap.get(p)).clearParty();
                    stringPartyInformationMap.remove(playerPartyMap.get(p));
                }
                leaveParty(memberNames.get(0));
            }
            /* 否則等隊長變動Event觸發 */
            if (stringPartyInformationMap.containsKey(leftPlayer)) {
                stringPartyInformationMap.get(leftPlayer).clearParty();
                stringPartyInformationMap.remove(leftPlayer);
            }
            return;
        }
        /* 其他成員變動 */
        if (!checkIfMemberExist(leaderName, memberNames)) {
            if (stringPartyInformationMap.containsKey(leaderName)) {
                stringPartyInformationMap.get(leaderName).clearParty();
                stringPartyInformationMap.remove(leaderName);
            }
            return;
        }
        if (memberNames.size() == 0) {
            leaveParty(leaderName);
            if (stringPartyInformationMap.containsKey(leaderName)) {
                stringPartyInformationMap.get(leaderName).clearParty();
                stringPartyInformationMap.remove(leaderName);
            }
            return;
        }
        Bukkit.broadcastMessage(memberNames + "");
        updateStringPartyInformationMap(leaderName, memberNames);
        updatePlayerPartyMap(leaderName, memberNames);
    }

    /* 隊長變動Event觸發 */
    public static void updateInformationOnLeaderChange(String leaderName, List<String> memberNames) { //TBU
        String original = getOriginalName(leaderName, memberNames);
        /* 沒人在這個伺服器就直接把資料砍掉 */
        if (!checkIfMemberExist(leaderName, memberNames)) {
            if (stringPartyInformationMap.containsKey(original)) {
                stringPartyInformationMap.get(original).clearParty();
                stringPartyInformationMap.remove(original);
            }
            return;
        }
        /* 再砍掉原本的隊伍 */
        if (stringPartyInformationMap.containsKey(original)) {
            stringPartyInformationMap.get(original).clearParty();
            stringPartyInformationMap.remove(original);
        }
        /* 如果有就新增新的隊長和隊員重新放置到Map裡面 */
        updateStringPartyInformationMap(leaderName, memberNames);
        /* 把成員綁到新的隊長名字 */
        updatePlayerPartyMap(leaderName, memberNames);
    }

    private static void updatePlayerPartyMap(String leaderName, List<String> memberNames) {
        Player leaderPlayer = Bukkit.getPlayer(leaderName);
        if (leaderPlayer != null && leaderPlayer.isOnline()) playerPartyMap.put(leaderPlayer, leaderName);


        for (String memberName : memberNames) {
            Player member = Bukkit.getPlayer(memberName);
            if (member != null && member.isOnline()) playerPartyMap.put(member, leaderName);
        }

    }


    /* Helper Function 檢查有沒有人在這個伺服器 */
    private static boolean checkIfMemberExist(String leaderName, List<String> memberNames) {
        Player leader = Bukkit.getPlayer(leaderName);
        if (leader != null && leader.isOnline()) return true;
        for (String member : memberNames) {
            Player m = Bukkit.getPlayer(member);
            if (m != null && m.isOnline()) return true;
        }
        return false;
    }

    private static boolean checkIfMemberExist(String leaderName) {
        Player leader = Bukkit.getPlayer(leaderName);
        if (leader != null && leader.isOnline()) return true;
        PartyInformation pi = stringPartyInformationMap.get(leaderName);
        for (PlayerInformation member : pi.getPartyMembers()) {
            if (member != null && member.getOnline()) return true;
        }
        return false;
    }

    /* Helper Function 檢查本來leader是誰 */
    private static String getOriginalName(String leaderName, List<String> memberNames) {
        Player leader = Bukkit.getPlayer(leaderName);
        if (playerPartyMap.containsKey(leader)) return playerPartyMap.get(leader);

        for (String member : memberNames) {
            Player m = Bukkit.getPlayer(member);
            if (playerPartyMap.containsKey(m)) return playerPartyMap.get(m);
        }
        return null;
    }


    private static void updateStringPartyInformationMap(String leaderName, List<String> memberNames) {
        PartyInformation partyInformation = new PartyInformation(plugin, leaderName, memberNames);
        stringPartyInformationMap.put(leaderName, partyInformation);
        Bukkit.broadcastMessage(leaderName + " "+memberNames);
    }

    public static void updatePartyInformationMapOnSwitchQuit(String leaderName) {
        if (leaderName == null) {
            System.out.println("leaderName null");
            return;
        }
        if (!stringPartyInformationMap.containsKey(leaderName)) {
            System.out.println("!containsKey(leaderName)");
            return;
        }
        PartyInformation partyInformation = stringPartyInformationMap.get(leaderName);
        partyInformation.UpdateParty(plugin);
        if (!checkIfMemberExist(leaderName)) {
            stringPartyInformationMap.remove(leaderName);
            System.out.print("Remove " + leaderName);
        }

        Player leader = Bukkit.getPlayer(leaderName);
        if (leader != null && leader.isOnline() && PartyDataManager.playerPartyMap.get(leader) == null) {
            PartyDataManager.playerPartyMap.put(leader, leaderName);
        }
        for (PlayerInformation pi : partyInformation.getPartyMembers()) {
            if (pi.getPlayer() != null && pi.getOnline() && PartyDataManager.playerPartyMap.get(pi.getPlayer()) == null) {
                PartyDataManager.playerPartyMap.put(pi.getPlayer(), leaderName);
            }
        }
    }

    public static void updatePartyInformationMapOnSwitchJoin(String leaderName, List<String> memberNames) {
        if (leaderName == null) {
            return;
        }
        updateStringPartyInformationMap(leaderName, memberNames);
        updatePlayerPartyMap(leaderName, memberNames);
        if (!checkIfMemberExist(leaderName)) {
            stringPartyInformationMap.remove(leaderName);
            System.out.print("Remove " + leaderName);
        }
    }
}
