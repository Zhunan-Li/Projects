package org.kitsune.com.Cooking.Listener.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class StructCheck {
    public static boolean struckCheck(Block clickedBlock, Material topMaterial, Material baseMaterial) {
        Location clickedBlockLocation = clickedBlock.getLocation();
        int x = clickedBlockLocation.getBlockX();
        int y = clickedBlockLocation.getBlockY();
        int z = clickedBlockLocation.getBlockZ();
        if (clickedBlock.getType().equals(baseMaterial)) {
            Location topBlockLocation = new Location(clickedBlockLocation.getWorld(), x, y + 1, z);
            return clickedBlock.getWorld().getBlockAt(topBlockLocation).getType().equals(topMaterial) && checkBarrierItemFrame(topBlockLocation);
        } else if (clickedBlock.getType().equals(topMaterial)) {
            return clickedBlock.getWorld().getBlockAt(x, y - 1, z).getType().equals(baseMaterial) && checkBarrierItemFrame(clickedBlockLocation);
        }
        return false;
    }

    private static Boolean checkBarrierItemFrame(Location topBlockLocation) {
        for (Entity entity : topBlockLocation.getWorld().getNearbyEntities(topBlockLocation, 0.5, 1.5, 0.5)) {
            if (entity.getType() == EntityType.ITEM_FRAME) {
                ItemFrame itemFrame = (ItemFrame) entity;
                ItemStack itemStack = itemFrame.getItem();
                if (itemStack.getType() != Material.DIAMOND) return false;
                if (!itemStack.hasItemMeta()) return false;
                if (!Objects.requireNonNull(itemStack.getItemMeta()).hasCustomModelData()) return false;
                int model = itemStack.getItemMeta().getCustomModelData();

                if (model == 70) return true;
            }
        }
        return false;
    }
}
