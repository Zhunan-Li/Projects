package org.kitsune.com.Cooking.Listener.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.kitsune.com.Cooking.Objects.CookingIndicator;

public class FurnaceCheck {
    public static boolean furnaceCheck(Block clickedBlock, CookingIndicator cookingIndicator, Material topMaterial, Material baseMaterial) {
        if (clickedBlock.getType().equals(baseMaterial)) {
            return clickedBlock.getLocation().equals(cookingIndicator.getUsingCampfireLocation());
        } else if (clickedBlock.getType().equals(topMaterial)) {
            return clickedBlock.getLocation().equals(cookingIndicator.getUsingCauldronLocation());
        }
        return false;
    }
}
