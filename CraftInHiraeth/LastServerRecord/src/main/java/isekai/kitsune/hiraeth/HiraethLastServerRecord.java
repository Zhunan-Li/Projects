package isekai.kitsune.hiraeth;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class HiraethLastServerRecord extends Plugin {
    static Map<ProxiedPlayer, String> playerData = new HashMap<>();

    @Override
    public void onEnable() {
        try {
            Config.loadConfig(this);
        } catch (IOException exception) {
            exception.printStackTrace();
            getLogger().log(Level.WARNING, "Plugin disabling...");
            return;
        }
        new EventHandler(this);
        this.getProxy().getPluginManager().registerCommand(this, new DebugCommand("lastserver", this));

        Database.setup();
    }

}
