package org.kitsune.com.Cooking.Zukan.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kitsune.com.Cooking.Configs.RecipeConfigs;
import org.kitsune.com.Cooking.Database.DatabaseCore;
import org.kitsune.com.Cooking.Objects.Recipe;
import org.kitsune.com.Cooking.Zukan.ZukanInitializer;

public class GUI {

    public static Inventory generate(Player p, int page) {
        Inventory inventory = Bukkit.createInventory(null, 54, "食譜 (".concat(String.valueOf(page)).concat("/").concat(String.valueOf(ZukanInitializer.totalPageCount)).concat(")"));

        inventory.setItem(4, ItemManager.createGUIItem(Material.NAME_TAG, 16, ""));
        inventory.setItem(13, ItemManager.createGUIItem(Material.NAME_TAG, 15, ""));

        if (page < ZukanInitializer.totalPageCount)
            inventory.setItem(46, ItemManager.createGUIItem(Material.RED_DYE, 12, ChatColor.translateAlternateColorCodes('&', "上一頁")));
        if (page < ZukanInitializer.totalPageCount)
            inventory.setItem(52, ItemManager.createGUIItem(Material.RED_DYE, 11, ChatColor.translateAlternateColorCodes('&', "下一頁")));
        for (Recipe recipe : RecipeConfigs.recipeMap.values()) {
            if (recipe.getId() == 0) continue;
            if (recipe.getId() <= (page - 1) * 40) continue;
            if (recipe.getId() > page * 40) continue;
            int id = recipe.getId() % 40;
            int slot = id;
            if (id > 20) {
                id = id - 20;
                while (id > 4) {
                    slot += 5;
                    id -= 4;
                }
                slot += 4;
            } else {
                while (id > 4) {
                    slot += 5;
                    id -= 4;
                }
                slot--;
            }

            if (DatabaseCore.playerData.get(p).get(recipe.getId())) {
                //true
                ItemStack i = ItemManager.createGUIItem(recipe.getMaterial(), recipe.getCustomModelData(), ChatColor.translateAlternateColorCodes('&', "&eID.".concat(String.valueOf(recipe.getId())).concat(" ").concat(recipe.getName())));
                inventory.setItem(slot, i);
            } else {
                ItemStack i = ItemManager.createGUIItem(Material.RED_DYE, 22, "§c✘ ID:".concat(String.valueOf(recipe.getId())).concat(" ???"));
                inventory.setItem(slot, i);
            }
        }

        return inventory;
    }
}
