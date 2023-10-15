package isekai.kitsune.hiraeth;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class Config {
    public static Configuration configuration;
    public static String defaultServer;
    public static List<String> blackListServers;

    public static void loadConfig(Plugin plugin) throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream inputStream = plugin.getResourceAsStream("config.yml")) {
                Files.copy(inputStream, file.toPath());
            }
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        defaultServer = configuration.getString("DefaultServer");
        blackListServers = configuration.getStringList("BlackList");
    }
}
