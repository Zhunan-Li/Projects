package org.kitsune.com.Cooking.Listener.Utils;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialCheck {
    public static boolean materialCheck(Player p, ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            assert itemStack.getItemMeta() != null;
            String itemDisplayName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).split(" ")[0];
            if (HiraethConsume.checkFoodItemsExist(itemDisplayName)) {
                FoodItem foodItem = HiraethConsume.getFoodItems().get(itemDisplayName);
                return playerLevelCheck(p, foodItem);
            }
        }
        return false;
    }

    private static boolean playerLevelCheck(Player p, FoodItem foodItem) {
        return true;
    }
}
