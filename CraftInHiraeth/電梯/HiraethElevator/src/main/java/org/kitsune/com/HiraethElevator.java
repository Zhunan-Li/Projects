package org.kitsune.com;

import org.bukkit.plugin.java.JavaPlugin;

public final class HiraethElevator extends JavaPlugin {
    private Elevator elevator;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.elevator = new Elevator(this);
        new EventListener(this, this.elevator);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.elevator.clearAllEntity();
    }
}
