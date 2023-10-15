package org.kitsune.com.Cooking.Zukan;

import org.bukkit.plugin.Plugin;
import org.kitsune.com.Cooking.Configs.RecipeConfigs;

import java.util.Objects;

public class ZukanInitializer {
    public static int totalPageCount;
    private final Plugin plugin;

    public ZukanInitializer(Plugin _plugin) {
        this.plugin = _plugin;
        Objects.requireNonNull(this.plugin.getServer().getPluginCommand("recipeZukan")).setExecutor(new Commands());

        totalPageCount = getTotalPageCount();
    }

    private Integer getTotalPageCount() {
        int page = RecipeConfigs.recipeMap.size() - 1;
        /* 每頁40個 */
        while (page > 40) {
            page++;
            page -= 40;
        }
        return Math.max(page, 1);
    }
}
