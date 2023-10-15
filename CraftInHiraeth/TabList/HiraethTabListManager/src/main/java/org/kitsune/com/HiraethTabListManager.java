package org.kitsune.com;

import net.md_5.bungee.api.plugin.Plugin;
import org.kitsune.com.Listener.PlayerEvents;

public final class HiraethTabListManager extends Plugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            new PlayerEvents(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
