package isekai.kitsune.bungeedatabridgeSpigot.Objects;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.util.FlagData;
import com.sucy.skill.api.util.FlagManager;
import isekai.kitsune.bungeedatabridgeSpigot.BungeeDataBridgeSpigot;
import isekai.kitsune.bungeedatabridgeSpigot.ScoreBoard.CScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartyInformation {

    private static int PERIOD = 20;
    private static boolean DEBUG = false;

    private PlayerInformation leaderPlayer;
    private List<PlayerInformation> partyMembers;
    private CScoreboard PartyBoard;
    private HashMap<PlayerInformation, CScoreboard.Row> cScoreBoardRow_Name;
    private HashMap<PlayerInformation, CScoreboard.Row> cScoreBoardRow_HP;
    private HashMap<PlayerInformation, String> cScoreBoardRow_Temp;
    private BukkitRunnable BoardRunnable;

    public PartyInformation(BungeeDataBridgeSpigot plugin, String leaderName, List<String> memberNames) {
        this.leaderPlayer = new PlayerInformation(leaderName);
        this.partyMembers = new ArrayList<>();
        for (String name : memberNames) {
            this.partyMembers.add(new PlayerInformation(name));
        }
        /* Create Party Board */
        PartyBoard = new CScoreboard("p", "party", "띑");
        cScoreBoardRow_Name = new HashMap<>();
        cScoreBoardRow_HP = new HashMap<>();
        cScoreBoardRow_Temp = new HashMap<>();
        if (leaderPlayer.getOnline()) {
            Player leader = leaderPlayer.getPlayer();
            PlayerData leader_pd = SkillAPI.getPlayerData(leader);
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + leader_pd.getMainClass().getLevel() + " " + GetPlayerName(leaderPlayer.getName()) + " 뾩"));
            String status = GetHealthAndStatus(leader);
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        } else {
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + leaderPlayer.getName() + " 뾩"));
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域"));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        }
        PartyBoard.addRow("");
        int num = 0;
        for (PlayerInformation pi : partyMembers) {
            String temp = "";
            if (num == 1) temp = "띌";
            else if (num == 2) temp = "띍";
            else if (num == 3) temp = "띎";
            else if (num == 4) temp = "띏";
            if (pi.getOnline()) {
                Player p = pi.getPlayer();
                PlayerData p_pd = SkillAPI.getPlayerData(p);
                CScoreboard.Row p_name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName()) + " 뾩" + temp));
                String status = GetHealthAndStatus(p);
                CScoreboard.Row p_hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status + temp));
                cScoreBoardRow_Name.put(pi, p_name);
                cScoreBoardRow_HP.put(pi, p_hp);
            } else {
                CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + pi.getName() + " 뾩" + temp));
                CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域" + temp));
                cScoreBoardRow_Name.put(pi, Leader_Name);
                cScoreBoardRow_HP.put(pi, Leader_Hp);
            }
            cScoreBoardRow_Temp.put(pi,temp);
            PartyBoard.addRow(temp);
            num++;
        }


        PartyBoard.finish();
        if (leaderPlayer.getOnline()) {
            PartyBoard.display(leaderPlayer.getPlayer());
        }
        for (PlayerInformation pi : partyMembers) {
            if (pi.getOnline()) {
                PartyBoard.display(pi.getPlayer());
            }
        }
        BoardRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (DEBUG) Bukkit.broadcastMessage("A PARTY " + leaderName + " " + partyMembers+ " is running");
                if (leaderPlayer.getOnline()) {
                    Player leader = leaderPlayer.getPlayer();
                    PlayerData leader_pd = SkillAPI.getPlayerData(leader);
                    cScoreBoardRow_Name.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶"+leader_pd.getMainClass().getLevel() +" "+GetPlayerName(leaderPlayer.getName()) +" 뾩"));
                    String status = GetHealthAndStatus(leader);
                    cScoreBoardRow_HP.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                }
                for (PlayerInformation pi : partyMembers) {
                    if (pi.getOnline()) {
                        Player p = pi.getPlayer();
                        PlayerData p_pd = SkillAPI.getPlayerData(p);
                        cScoreBoardRow_Name.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName()) + cScoreBoardRow_Temp.get(pi)));
                        String status = GetHealthAndStatus(p);
                        cScoreBoardRow_HP.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                    }
                }
            }
        };
        BoardRunnable.runTaskTimer(plugin, PERIOD, PERIOD);

        if (DEBUG) Bukkit.broadcastMessage("CREATED A PARTY");
    }

    public static String GetHealthAndStatus(Player p) {
        double Maxhealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double health = p.getHealth();
        double percent = health / Maxhealth;

        double Maxmana = SkillAPI.getPlayerData(p).getMaxMana();
        double mana = SkillAPI.getPlayerData(p).getMana();
        double manapercent = mana / Maxmana;
        String status = "";
        if (percent > 0.95) {
            if (manapercent > 0.85) {
                status = "뜊";
            } else if (manapercent > 0.65) {
                status = "뜕";
            } else if (manapercent > 0.45) {
                status = "뜠";
            } else if (manapercent > 0.25) {
                status = "뜫";
            } else if (manapercent > 0.05) {
                status = "뜶";
            } else {
                status = "띁";
            }
        } else if (percent > 0.85) {
            if (manapercent > 0.85) {
                status = "뜋";
            } else if (manapercent > 0.65) {
                status = "뜖";
            } else if (manapercent > 0.45) {
                status = "뜡";
            } else if (manapercent > 0.25) {
                status = "뜬";
            } else if (manapercent > 0.05) {
                status = "뜷";
            } else {
                status = "띂";
            }
        } else if (percent > 0.75) {
            if (manapercent > 0.85) {
                status = "뜌";
            } else if (manapercent > 0.65) {
                status = "뜗";
            } else if (manapercent > 0.45) {
                status = "뜢";
            } else if (manapercent > 0.25) {
                status = "뜭";
            } else if (manapercent > 0.05) {
                status = "뜸";
            } else {
                status = "띃";
            }
        } else if (percent > 0.65) {
            if (manapercent > 0.85) {
                status = "뜍";
            } else if (manapercent > 0.65) {
                status = "뜘";
            } else if (manapercent > 0.45) {
                status = "뜣";
            } else if (manapercent > 0.25) {
                status = "뜮";
            } else if (manapercent > 0.05) {
                status = "뜹";
            } else {
                status = "띄";
            }
        } else if (percent > 0.55) {
            if (manapercent > 0.85) {
                status = "뜎";
            } else if (manapercent > 0.65) {
                status = "뜙";
            } else if (manapercent > 0.45) {
                status = "뜤";
            } else if (manapercent > 0.25) {
                status = "뜯";
            } else if (manapercent > 0.05) {
                status = "뜺";
            } else {
                status = "띅";
            }
        } else if (percent > 0.45) {
            if (manapercent > 0.85) {
                status = "뜏";
            } else if (manapercent > 0.65) {
                status = "뜚";
            } else if (manapercent > 0.45) {
                status = "뜥";
            } else if (manapercent > 0.25) {
                status = "뜰";
            } else if (manapercent > 0.05) {
                status = "뜻";
            } else {
                status = "띆";
            }
        } else if (percent > 0.35) {
            if (manapercent > 0.85) {
                status = "뜐";
            } else if (manapercent > 0.65) {
                status = "뜛";
            } else if (manapercent > 0.45) {
                status = "뜦";
            } else if (manapercent > 0.25) {
                status = "뜱";
            } else if (manapercent > 0.05) {
                status = "뜼";
            } else {
                status = "띇";
            }
        } else if (percent > 0.25) {
            if (manapercent > 0.85) {
                status = "뜑";
            } else if (manapercent > 0.65) {
                status = "뜜";
            } else if (manapercent > 0.45) {
                status = "뜧";
            } else if (manapercent > 0.25) {
                status = "뜲";
            } else if (manapercent > 0.05) {
                status = "뜽";
            } else {
                status = "띈";
            }
        } else if (percent > 0.15) {
            if (manapercent > 0.85) {
                status = "뜒";
            } else if (manapercent > 0.65) {
                status = "뜝";
            } else if (manapercent > 0.45) {
                status = "뜨";
            } else if (manapercent > 0.25) {
                status = "뜳";
            } else if (manapercent > 0.05) {
                status = "뜾";
            } else {
                status = "띉";
            }
        } else if (percent > 0.05) {
            if (manapercent > 0.85) {
                status = "뜓";
            } else if (manapercent > 0.65) {
                status = "뜞";
            } else if (manapercent > 0.45) {
                status = "뜩";
            } else if (manapercent > 0.25) {
                status = "뜴";
            } else if (manapercent > 0.05) {
                status = "뜿";
            } else {
                status = "띊";
            }
        } else {
            if (manapercent > 0.85) {
                status = "뜔";
            } else if (manapercent > 0.65) {
                status = "뜟";
            } else if (manapercent > 0.45) {
                status = "뜪";
            } else if (manapercent > 0.25) {
                status = "뜵";
            } else if (manapercent > 0.05) {
                status = "띀";
            } else {
                status = "띋";
            }
        }
        FlagData fd = FlagManager.getFlagData(p);
        //buff

        if (fd.hasFlag("移動加速I") || fd.hasFlag("移動加速II") || fd.hasFlag("移動加速III") || fd.hasFlag("移動加速IV"))
            status += "뛺"; //移動加速
        if (fd.hasFlag("免疫I") || fd.hasFlag("免疫II") || fd.hasFlag("免疫III") || fd.hasFlag("免疫IV")) status += "뛽"; //免疫
        if (fd.hasFlag("韌性增加I") || fd.hasFlag("韌性增加II") || fd.hasFlag("韌性增加III") || fd.hasFlag("韌性增加IV"))
            status += "뛱"; //韌性增加
        //if (fd.hasFlag("eledefUp_III")||fd.hasFlag("eledefUp_II")||fd.hasFlag("eledefUp_I")) status+= "뛿"; //元素防禦提升

        //debuff


        if (fd.hasFlag("緩速I") || fd.hasFlag("緩速II") || fd.hasFlag("緩速III") || fd.hasFlag("緩速IV")) status += "뛻"; //緩速
        if (fd.hasFlag("虛弱I") || fd.hasFlag("虛弱II") || fd.hasFlag("虛弱III") || fd.hasFlag("虛弱IV")) status += "뛷"; //虛弱
        if (fd.hasFlag("沉默I") || fd.hasFlag("沉默II") || fd.hasFlag("沉默III") || fd.hasFlag("沉默IV")) status += "띹"; //沉默
        if (fd.hasFlag("易傷I") || fd.hasFlag("易傷II") || fd.hasFlag("易傷III") || fd.hasFlag("易傷IV")) status += "뛲"; //易傷
        if (fd.hasFlag("防禦下降I") || fd.hasFlag("防禦下降II") || fd.hasFlag("防禦下降III") || fd.hasFlag("防禦下降IV"))
            status += "뛲"; //防禦下降
        if (fd.hasFlag("壓制I") || fd.hasFlag("壓制II") || fd.hasFlag("壓制III") || fd.hasFlag("壓制IV")) status += "띸"; //壓制

        if (fd.hasFlag("精準X")) status += "띷";
        else if (fd.hasFlag("精準IX")) status += "띶";
        else if (fd.hasFlag("精準VIII")) status += "띵";
        else if (fd.hasFlag("精準VII")) status += "띴";
        else if (fd.hasFlag("精準VI")) status += "띳";
        else if (fd.hasFlag("精準V")) status += "띲";
        else if (fd.hasFlag("精準IV")) status += "띱";
        else if (fd.hasFlag("精準III")) status += "띰";
        else if (fd.hasFlag("精準II")) status += "띯";
        else if (fd.hasFlag("精準I")) status += "띮";

        if (fd.hasFlag("特殊彈藥")) status += "띒";

        if (fd.hasFlag("蓄力III")) status += "띭";
        else if (fd.hasFlag("蓄力II")) status += "띬";
        else if (fd.hasFlag("蓄力I")) status += "띫";

        if (fd.hasFlag("疲勞II") || fd.hasFlag("疲勞I")) status += "띻";

        if (fd.hasFlag("破綻IV") || fd.hasFlag("破綻III") || fd.hasFlag("破綻II") || fd.hasFlag("破綻I")) status += "라";

        if (fd.hasFlag("弱點察覺X")) status += "띪";
        else if (fd.hasFlag("弱點察覺IX")) status += "띩";
        else if (fd.hasFlag("弱點察覺VIII")) status += "띨";
        else if (fd.hasFlag("弱點察覺VII")) status += "띧";
        else if (fd.hasFlag("弱點察覺VI")) status += "띦";
        else if (fd.hasFlag("弱點察覺V")) status += "띥";
        else if (fd.hasFlag("弱點察覺IV")) status += "띤";
        else if (fd.hasFlag("弱點察覺III")) status += "띣";
        else if (fd.hasFlag("弱點察覺II")) status += "띢";
        else if (fd.hasFlag("弱點察覺I")) status += "띡";

        if (fd.hasFlag("流風V")) status += "띠";
        else if (fd.hasFlag("流風IV")) status += "띟";
        else if (fd.hasFlag("流風III")) status += "띞";
        else if (fd.hasFlag("流風II")) status += "띝";
        else if (fd.hasFlag("流風I")) status += "띜";

        if (fd.hasFlag("防禦架勢")) status += "띖";
        if (fd.hasFlag("進攻架勢")) status += "띗";

        if (fd.hasFlag("劍之誓願III") || fd.hasFlag("劍之誓願II") || fd.hasFlag("劍之誓願I")) status += "띘";

        if (fd.hasFlag("挑釁")) status += "뛳";
        if (fd.hasFlag("崩擊")) status += "띚";
        if (fd.hasFlag("暴風")) status += "띙";
        if (fd.hasFlag("牽制")) status += "띛";


        return status;
    }

    public static String GetPlayerName(String name) {
        if (name.length() > 8) {
            String name_ = "" + name.charAt(0) + name.charAt(1) + name.charAt(2) + name.charAt(3) + name.charAt(4) + name.charAt(5) + name.charAt(6) + name.charAt(7);
            return name_;
        }
        return name;
    }

    /* Called when leader still the same */
    public void UpdateParty(BungeeDataBridgeSpigot plugin, String leaderName, List<String> memberNames) {
        BoardRunnable.cancel();
        this.leaderPlayer.UpdatePlayer();
        this.partyMembers.clear();
        for (String name : memberNames) {
            this.partyMembers.add(new PlayerInformation(name));
        }

        cScoreBoardRow_Name.clear();
        cScoreBoardRow_HP.clear();
        cScoreBoardRow_Temp.clear();
        /* Create Party Board */
        PartyBoard = new CScoreboard("p", "party", "띑");
        cScoreBoardRow_Name = new HashMap<>();
        cScoreBoardRow_HP = new HashMap<>();
        cScoreBoardRow_Temp = new HashMap<>();
        if (leaderPlayer.getOnline()) {
            Player leader = leaderPlayer.getPlayer();
            PlayerData leader_pd = SkillAPI.getPlayerData(leader);
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + leader_pd.getMainClass().getLevel() + " " + GetPlayerName(leaderPlayer.getName()) + " 뾩"));
            String status = GetHealthAndStatus(leader);
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        } else {
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + leaderPlayer.getName() + " 뾩"));
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域"));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        }
        PartyBoard.addRow("");
        int num = 0;
        for (PlayerInformation pi : partyMembers) {
            String temp = "";
            if (num == 1) temp = "띌";
            else if (num == 2) temp = "띍";
            else if (num == 3) temp = "띎";
            else if (num == 4) temp = "띏";
            if (pi.getOnline()) {
                Player p = pi.getPlayer();
                PlayerData p_pd = SkillAPI.getPlayerData(p);
                CScoreboard.Row p_name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName()) + temp));
                String status = GetHealthAndStatus(p);
                CScoreboard.Row p_hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status + temp));
                cScoreBoardRow_Name.put(pi, p_name);
                cScoreBoardRow_HP.put(pi, p_hp);
            } else {
                CScoreboard.Row p_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + pi.getName() + temp));
                CScoreboard.Row p_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域" + temp));
                cScoreBoardRow_Name.put(pi, p_Name);
                cScoreBoardRow_HP.put(pi, p_Hp);
            }
            cScoreBoardRow_Temp.put(pi, temp);
            PartyBoard.addRow(temp);
            num++;
        }


        PartyBoard.finish();
        if (leaderPlayer.getOnline()) {
            PartyBoard.display(leaderPlayer.getPlayer());
        }
        for (PlayerInformation pi : partyMembers) {
            if (pi.getOnline()) {
                PartyBoard.display(pi.getPlayer());
            }
        }
        BoardRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("A PARTY " + leaderName + " " + partyMembers+ " is running");
                if (leaderPlayer.getOnline()) {
                    Player leader = leaderPlayer.getPlayer();
                    PlayerData leader_pd = SkillAPI.getPlayerData(leader);
                    cScoreBoardRow_Name.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶"+leader_pd.getMainClass().getLevel() +" "+GetPlayerName(leaderPlayer.getName()) +" 뾩"));
                    String status = GetHealthAndStatus(leader);
                    cScoreBoardRow_HP.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                }
                for (PlayerInformation pi : partyMembers) {
                    if (pi.getOnline()) {
                        Player p = pi.getPlayer();
                        PlayerData p_pd = SkillAPI.getPlayerData(p);
                        cScoreBoardRow_Name.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName()) + cScoreBoardRow_Temp.get(pi)));
                        String status = GetHealthAndStatus(p);
                        cScoreBoardRow_HP.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                    }
                }
            }
        };
        BoardRunnable.runTaskTimer(plugin, PERIOD, PERIOD);
    }

    /* Called when no member change */
    public void UpdateParty(BungeeDataBridgeSpigot plugin) {
        BoardRunnable.cancel();

        this.leaderPlayer.UpdatePlayer();
        for (PlayerInformation member : partyMembers) {
            member.UpdatePlayer();
        }

        cScoreBoardRow_Name.clear();
        cScoreBoardRow_HP.clear();
        cScoreBoardRow_Temp.clear();
        /* Create Party Board */
        PartyBoard = new CScoreboard("p", "party", "띑");
        cScoreBoardRow_Name = new HashMap<>();
        cScoreBoardRow_HP = new HashMap<>();
        cScoreBoardRow_Temp = new HashMap<>();
        if (leaderPlayer.getOnline()) {
            Player leader = leaderPlayer.getPlayer();
            PlayerData leader_pd = SkillAPI.getPlayerData(leader);
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + leader_pd.getMainClass().getLevel() + " " + GetPlayerName(leaderPlayer.getName()) + " 뾩"));
            String status = GetHealthAndStatus(leader);
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        } else {
            CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + leaderPlayer.getName() + " 뾩"));
            CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域"));
            cScoreBoardRow_Name.put(leaderPlayer, Leader_Name);
            cScoreBoardRow_HP.put(leaderPlayer, Leader_Hp);
        }
        PartyBoard.addRow("");
        int num = 0;
        for (PlayerInformation pi : partyMembers) {
            String temp = "";
            if (num == 1) temp = "띌";
            else if (num == 2) temp = "띍";
            else if (num == 3) temp = "띎";
            else if (num == 4) temp = "띏";
            if (pi.getOnline()) {
                Player p = pi.getPlayer();
                PlayerData p_pd = SkillAPI.getPlayerData(p);
                CScoreboard.Row p_name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName())+ temp));
                String status = GetHealthAndStatus(p);
                CScoreboard.Row p_hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', status + temp));
                cScoreBoardRow_Name.put(pi, p_name);
                cScoreBoardRow_HP.put(pi, p_hp);
            } else {
                CScoreboard.Row Leader_Name = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "✘ " + pi.getName() + temp));
                CScoreboard.Row Leader_Hp = PartyBoard.addRow(ChatColor.translateAlternateColorCodes('&', "  &7該成員與你身處不同區域" + temp));
                cScoreBoardRow_Name.put(pi, Leader_Name);
                cScoreBoardRow_HP.put(pi, Leader_Hp);
            }
            cScoreBoardRow_Temp.put(pi,temp);
            PartyBoard.addRow(temp);
            num++;
        }


        PartyBoard.finish();
        if (leaderPlayer.getOnline()) {
            PartyBoard.display(leaderPlayer.getPlayer());
        }
        for (PlayerInformation pi : partyMembers) {
            if (pi.getOnline()) {
                PartyBoard.display(pi.getPlayer());
            }
        }
        BoardRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (DEBUG) Bukkit.broadcastMessage("A PARTY " + leaderPlayer.getName() + " " + partyMembers+ " is running");
                if (leaderPlayer.getOnline()) {
                    Player leader = leaderPlayer.getPlayer();
                    PlayerData leader_pd = SkillAPI.getPlayerData(leader);
                    cScoreBoardRow_Name.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶"+leader_pd.getMainClass().getLevel() +" "+GetPlayerName(leaderPlayer.getName()) +" 뾩"));
                    String status = GetHealthAndStatus(leader);
                    cScoreBoardRow_HP.get(leaderPlayer).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                }
                for (PlayerInformation pi : partyMembers) {
                    if (pi.getOnline()) {
                        Player p = pi.getPlayer();
                        PlayerData p_pd = SkillAPI.getPlayerData(p);
                        cScoreBoardRow_Name.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', "뿶" + p_pd.getMainClass().getLevel() + " " + GetPlayerName(pi.getName()) + cScoreBoardRow_Temp.get(pi)));
                        String status = GetHealthAndStatus(p);
                        cScoreBoardRow_HP.get(pi).setMessage(ChatColor.translateAlternateColorCodes('&', status));
                    }
                }
            }
        };
        BoardRunnable.runTaskTimer(plugin, PERIOD, PERIOD);
    }

    public void clearParty() {
        if (DEBUG) Bukkit.broadcastMessage("Clearing Party "+ leaderPlayer.getName() +partyMembers);
        BoardRunnable.cancel();
        cScoreBoardRow_HP.clear();
        cScoreBoardRow_Name.clear();
        cScoreBoardRow_Temp.clear();
        partyMembers.clear();
    }

    public PlayerInformation getLeaderPlayer() {
        return leaderPlayer;
    }

    public List<PlayerInformation> getPartyMembers() {
        return partyMembers;
    }
}
