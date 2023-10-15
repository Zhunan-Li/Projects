package org.kitsune.com.Cooking.Listener.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.kitsune.com.Cooking.Configs.Configs;

public class FuelCheck {
    public static boolean checkIfItemIsFuel(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        if (itemStack.getType().equals(Material.getMaterial(Configs.configuration.getString("Fuel_Settings.Material").toUpperCase()))) {
            return itemStack.getItemMeta().getCustomModelData() == Configs.configuration.getInt("Fuel_Settings.CustomModelData");
        }
        return false;
    }
}
