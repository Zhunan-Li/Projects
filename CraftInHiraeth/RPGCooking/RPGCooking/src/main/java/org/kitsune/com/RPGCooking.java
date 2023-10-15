package org.kitsune.com;

import org.bukkit.plugin.java.JavaPlugin;
import org.kitsune.com.Cooking.HiraethCooking;

public final class RPGCooking extends JavaPlugin {

    @Override
    public void onEnable() {
        new HiraethCooking(this);
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }
}
