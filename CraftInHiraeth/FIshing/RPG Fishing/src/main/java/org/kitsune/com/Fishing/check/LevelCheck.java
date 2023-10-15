package me.springbreezex.craftinhiraethcore.fishing.check;

import com.sucy.skill.SkillAPI;
import org.bukkit.entity.Player;

public class LevelCheck {
    public static boolean LevelCheck(Player p, int min, int max){
        int currentLevel = SkillAPI.getPlayerData(p).getClass("fisherman").getLevel();
        if (currentLevel>=min&&currentLevel<=max) return true;
        return false;
    }
}
