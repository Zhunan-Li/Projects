package org.kitsune.com.Cooking.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private int id;
    private Material material;
    private int countUse;
    private int customModelData;
    private List<String> lore;
    private int exp;
    private int money;
    private List<String> requireMaterial;
    private int multiplier;
    private String tier;

    public Recipe(String name, int id, String material, int countUse, int customModelData, List<String> lore, int exp, int money, List<String> requireMaterial, int multiplier, String tier) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.id = id;
        this.material = Material.getMaterial(material.toUpperCase());
        this.countUse = countUse;
        this.customModelData = customModelData;
        List<String> loreCovered = new ArrayList<>();
        for (String string : lore) {
            loreCovered.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        this.lore = loreCovered;
        this.exp = exp;
        this.money = money;
        this.requireMaterial = requireMaterial;
        this.multiplier = multiplier;
        this.tier = tier;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getCountUse() {
        return this.countUse;
    }

    public int getCustomModelData() {
        return this.customModelData;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public int getExp() {
        return this.exp;
    }

    public int getMoney() {
        return this.money;
    }

    public List<String> getRequireMaterial() {
        return this.requireMaterial;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public String getTier() {
        return this.tier;
    }
}
