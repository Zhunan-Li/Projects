package org.kitsune.com.Cooking.Listener.Utils;

import me.springbreezex.craftinhiraethcore.blacksmith.ForgingStone;
import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OilCheck {
    public static ItemStack generateOil(List<String> userMaterialList) {
        int getOilPercent = 0;
        List<String> tierList = new ArrayList<>();
        for (String string : userMaterialList) {
            FoodItem foodItem = HiraethConsume.getFoodItems().get(string);
            if (foodItem.getType().toUpperCase().equals("FISH")) {
                getOilPercent += 5;
                tierList.add(foodItem.getTier().toUpperCase());
            }
        }
        ItemStack fishOil = null;
        int percent = (int) (Math.random() * 100) + 1;
        /* 是否得到魚油 */
        if (percent <= getOilPercent) {
            /* 魚油品質 */
            Collections.shuffle(tierList);
            String tier = tierList.get(0);
            if (tier.equals("E") || tier.equals("D")) {
                fishOil = ForgingStone.TIER3_FISH_OIL.getItem();
            } else if (tier.equals("C") || tier.equals("B") || tier.equals("A")) {
                fishOil = ForgingStone.TIER2_FISH_OIL.getItem();
            } else if (tier.equals("S") || tier.equals("SS")) {
                fishOil = ForgingStone.TIER1_FISH_OIL.getItem();
            }
        }

        return fishOil;
    }
}
