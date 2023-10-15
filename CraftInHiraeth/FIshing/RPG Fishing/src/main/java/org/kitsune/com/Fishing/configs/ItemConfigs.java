package me.springbreezex.craftinhiraethcore.fishing.configs;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.herb.HerbItem;
import me.springbreezex.craftinhiraethcore.fishing.utils.Item;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class ItemConfigs {
    public static Map<String, Item> itemMap = new HashMap<>();
    private Plugin plugin;

    public ItemConfigs(Plugin plugin) {
        this.plugin = plugin;
        load(this.plugin.getDataFolder(), this.plugin.getLogger());
    }

    public void load(File dataFolder, Logger logger) {
        File itemFolder = new File(dataFolder.toString().concat("/fishing/Items/"));
        itemFolder.mkdirs();
        if (itemFolder.listFiles().length != 0) {
            genMap(dataFolder, logger);
        } else {
            generate(dataFolder, logger);
        }
    }

    private void generate(File dataFolder, Logger logger) {
        logger.info("generating default item files...");
        List<String> itemFiles = new ArrayList<>(Arrays.asList("fishing/Items/OldShoes"));
        for (String path : itemFiles) {
            this.plugin.saveResource(path.concat(".yml"), false);
        }
        genMap(dataFolder, logger);
    }

    private void genMap(File dataFolder, Logger logger) {
        for (File file : new File(dataFolder.toString().concat("/fishing/Items/")).listFiles()) {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("Name");
            int difficult_min = configuration.getInt("Difficult_min");
            int difficult_max = configuration.getInt("Difficult_max");
            int pullTimes_min = configuration.getInt("PullTimes_min");
            int pullTimes_max = configuration.getInt("PullTimes_max");
            int xp_min = configuration.getInt("Xp_min");
            int xp_max = configuration.getInt("Xp_max");
            int chance_min = configuration.getInt("Chance_min");
            int chance_max = configuration.getInt("Chance_max");
            int level_min = configuration.getInt("Level_min");
            int level_max = configuration.getInt("Level_max");
            List<String> region = configuration.getStringList("Region");
            Item item = new Item(name, difficult_min, difficult_max, pullTimes_min, pullTimes_max, xp_min, xp_max, chance_min, chance_max, level_min, level_max, region);
            HerbItem herbItem = HiraethConsume.getHerbItems().get(ChatColor.stripColor(name));
            item.setItemStack(herbItem);
            itemMap.put(name, item);

        }
        logger.info("Loaded ".concat(String.valueOf(itemMap.size())).concat(" types of item!"));
    }
}
