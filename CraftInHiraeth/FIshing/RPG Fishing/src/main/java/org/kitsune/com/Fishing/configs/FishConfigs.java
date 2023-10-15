package me.springbreezex.craftinhiraethcore.fishing.configs;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import me.springbreezex.craftinhiraethcore.fishing.utils.Fish;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class FishConfigs {
    public static Map<String, Fish> fishMap = new HashMap<>();
    private Plugin plugin;

    public FishConfigs(Plugin plugin) {
        this.plugin = plugin;
        load(this.plugin.getDataFolder(), this.plugin.getLogger());
    }

    public void load(File dataFolder, Logger logger) {
        File fishFolder = new File(dataFolder.toString().concat("/fishing/Fish/"));
        fishFolder.mkdirs();
        if (fishFolder.listFiles().length != 0) {
            genMap(dataFolder, logger);
        } else {
            generate(dataFolder, logger);
        }
    }

    private void generate(File dataFolder, Logger logger) {
        logger.info("generating default fish files...");
        List<String> fishFiles = new ArrayList<>(Arrays.asList("fishing/Fish/PUIPUI", "fishing/Fish/Rainbow"));
        for (String path : fishFiles) {
            this.plugin.saveResource(path.concat(".yml"), false);
        }
        genMap(dataFolder, logger);
    }

    private void genMap(File dataFolder, Logger logger) {
        for (File file : new File(dataFolder.toString().concat("/fishing/Fish/")).listFiles()) {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("Name");
            int difficult_min = configuration.getInt("Difficult_min");
            int difficult_max = configuration.getInt("Difficult_max");
            int PullTimes_min = configuration.getInt("PullTimes_min");
            int PullTimes_max = configuration.getInt("PullTimes_max");
            int Xp_min = configuration.getInt("Xp_min");
            int Xp_max = configuration.getInt("Xp_max");
            int Chance_min = configuration.getInt("Chance_min");
            int Chance_max = configuration.getInt("Chance_max");
            int Level_min = configuration.getInt("Level_min");
            int Level_max = configuration.getInt("Level_max");
            List<String> region = configuration.getStringList("Region");
            Fish fish = new Fish(name, difficult_min, difficult_max, PullTimes_min, PullTimes_max, Xp_min, Xp_max, Chance_min, Chance_max, Level_min, Level_max, region);
            FoodItem fishItemStack = HiraethConsume.getFoodItems().get(ChatColor.stripColor(name));
            fish.setItemStack(fishItemStack);
            fishMap.put(name, fish);
        }
        logger.info("Loaded ".concat(String.valueOf(fishMap.size())).concat(" types of fish!"));
    }
}
