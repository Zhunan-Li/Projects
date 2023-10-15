package org.kitsune.com.Cooking.Zukan.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {
    protected static ItemStack createGUIItem(final Material material, final int model, final String name, final String... lore) {
        final ItemStack itemStack = new ItemStack(material, 1);
        final ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        if (model != 0) meta.setCustomModelData(model);
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
