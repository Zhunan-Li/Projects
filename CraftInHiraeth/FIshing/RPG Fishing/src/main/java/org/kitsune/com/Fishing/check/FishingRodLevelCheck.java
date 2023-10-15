package me.springbreezex.craftinhiraethcore.fishing.check;

import com.sucy.skill.SkillAPI;
import me.springbreezex.craftinhiraethcore.fishing.object.FishingRodItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FishingRodLevelCheck {
    public static boolean checkLevel(FishingRodItem fishingRod, Player p) {
        int level = 1;
        if (fishingRod.getName().contains("中階")) level = 46;
        if (fishingRod.getName().contains("高階")) level = 76;
        int currentLevel = SkillAPI.getPlayerData(p).getClass("fisherman").getLevel();
        if (currentLevel>=level) {
            return true;
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c&l!&8]&7 無法使用這支釣竿，原因：等級不足。"));
            return false;
        }
    }
}
