package org.kitsune.com.Cooking.Listener.Utils;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.enums.ExpSource;
import com.sucy.skill.api.player.PlayerClass;
import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import org.bukkit.entity.Player;
import org.kitsune.com.Cooking.Objects.CookingIndicator;

public class AddExp {
    public static int E;
    public static int D;
    public static int C;
    public static int B;
    public static int A;
    public static int S;
    public static int SS;

    public static void addExp(Player p, CookingIndicator cookingIndicator) {
        int xp = 0;
        for (String materialName : cookingIndicator.getUserMaterials()) {
            FoodItem foodItem = HiraethConsume.getFoodItems().get(materialName);
            if (foodItem.getTier().equals("E")) {
                xp += AddExp.E;
            }
            if (foodItem.getTier().equals("D")) {
                xp += AddExp.D;
            }
            if (foodItem.getTier().equals("C")) {
                xp += AddExp.C;
            }
            if (foodItem.getTier().equals("B")) {
                xp += AddExp.B;
            }
            if (foodItem.getTier().equals("A")) {
                xp += AddExp.A;
            }
            if (foodItem.getTier().equals("S")) {
                xp += AddExp.S;
            }
            if (foodItem.getTier().equals("SS")) {
                xp += AddExp.SS;
            }
        }

        PlayerClass pc = SkillAPI.getPlayerData(p).getClass("chef");
        int exp = (int) (xp * (Math.pow(1.1, (pc.getLevel() - 1))));
        pc.giveExp(exp, ExpSource.COMMAND);
    }
}
