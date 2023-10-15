package org.kitsune.com.Cooking.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.kitsune.com.Cooking.Objects.Recipe;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class RecipeConfigs {
    public static Map<String, Recipe> recipeMap = new HashMap<>();
    private Plugin plugin;

    public RecipeConfigs(Plugin plugin) {
        this.plugin = plugin;

        this.load(this.plugin.getDataFolder(), this.plugin.getLogger());
    }

    private void load(File dataFolder, Logger logger) {
        File fishFolder = new File(dataFolder.toString().concat("/Recipes/"));
        fishFolder.mkdirs();
        if (fishFolder.listFiles().length != 0) {
            genMap(dataFolder, logger);
        } else {
            generate(dataFolder, logger);
        }
    }

    private void generate(File dataFolder, Logger logger) {
        logger.info("generating default recipe files...");
        List<String> recipeFiles = new ArrayList<>(Arrays.asList("Recipes/cake"));
        for (String path : recipeFiles) {
            this.plugin.saveResource(path.concat(".yml"), false);
        }
        genMap(dataFolder, logger);
    }

    private void genMap(File dataFolder, Logger logger) {
        for (File file : new File(dataFolder.toString().concat("/Recipes/")).listFiles()) {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("Name");
            int id = configuration.getInt("ID");
            String material = configuration.getString("Material");
            int countUse = configuration.getInt("CountUse");
            int customModelData = configuration.getInt("CustomModelData");
            List<String> lore = configuration.getStringList("Lore");
            int exp = configuration.getInt("Exp");
            int money = configuration.getInt("Money");
            List<String> requireMaterials = configuration.getStringList("RequireMaterials");
            int multiplier = configuration.getInt("Multiplier");
            String tier = configuration.getString("Tier");

            recipeMap.put(name, new Recipe(name, id, material, countUse, customModelData, lore, exp, money, requireMaterials, multiplier, tier));
        }
        logger.info("Loaded ".concat(String.valueOf(recipeMap.size())).concat(" recipes!"));

        /* 雜食 name = none */
        recipeMap.put("none", new Recipe(Configs.configuration.getString("DefaultItem.UnknownRecipe.Name"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.ID"),
                Configs.configuration.getString("DefaultItem.UnknownRecipe.Material"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.CountUse"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.CustomModelData"),
                Configs.configuration.getStringList("DefaultItem.UnknownRecipe.Lore"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.Exp"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.Money"),
                Configs.configuration.getStringList("DefaultItem.UnknownRecipe.RequireMaterials"),
                Configs.configuration.getInt("DefaultItem.UnknownRecipe.Multiplier"),
                Configs.configuration.getString("DefaultItem.UnknownRecipe.Tier")));
    }
}
