package org.kitsune.com;

import org.bukkit.plugin.java.JavaPlugin;

public final class RPGFishing extends JavaPlugin {

    @Override
    public void onEnable() {
        new me.springbreezex.craftinhiraethcore.fishing.HiraethFishing(this);
    }

    @Override
    public void onDisable() {

    }
}
