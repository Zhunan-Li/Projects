package org.kitsune.com.Cooking.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.kitsune.com.Cooking.Listener.Utils.AddExp;

import java.io.IOException;

public class Configs {
    public static FileConfiguration configuration;
    private Plugin plugin;

    public Configs(Plugin plugin) throws IOException {
        this.plugin = plugin;
        getConfig();

        new RecipeConfigs(this.plugin);
        getTierExp();
    }

    private void getConfig() {
        configuration = this.plugin.getConfig();
        this.plugin.saveDefaultConfig();
    }

    private void getTierExp() {
        AddExp.E = configuration.getInt("TierExp.E");
        AddExp.D = configuration.getInt("TierExp.D");
        AddExp.C = configuration.getInt("TierExp.C");
        AddExp.B = configuration.getInt("TierExp.B");
        AddExp.A = configuration.getInt("TierExp.A");
        AddExp.S = configuration.getInt("TierExp.S");
        AddExp.SS = configuration.getInt("TierExp.SS");
    }
}
