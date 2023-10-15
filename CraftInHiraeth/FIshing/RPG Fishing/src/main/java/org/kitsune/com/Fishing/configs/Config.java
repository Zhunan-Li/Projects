package me.springbreezex.craftinhiraethcore.fishing.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Config {
    private Plugin plugin;

    public Config(Plugin plugin) throws IOException {
        this.plugin = plugin;

        new FishConfigs(this.plugin);
        new ItemConfigs(this.plugin);
        new EventConfigs(this.plugin);

        getConfig();
    }

    public static FileConfiguration configuration;

    private void getConfig() {
        File fishConfig = new File(this.plugin.getDataFolder().toString().concat("/fishing/config.yml"));
        if (!fishConfig.exists()) {
            plugin.saveResource("fishing/config.yml", false);
        }
        configuration = YamlConfiguration.loadConfiguration(fishConfig);
        //this.plugin.saveDefaultConfig();
    }
}
