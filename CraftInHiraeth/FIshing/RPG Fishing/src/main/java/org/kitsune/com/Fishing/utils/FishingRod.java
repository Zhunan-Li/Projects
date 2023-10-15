package me.springbreezex.craftinhiraethcore.fishing.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FishingRod {
    private String name;
    private List<String> lore;
    private int CustomModelData;
    private Material material;
    private List<Enchantment> enchantments;
    private ItemStack itemStack;

    public FishingRod(String name, List<String> lore, int CustomModelData, String material, List<String> enchantments){
        this.name = ChatColor.translateAlternateColorCodes('&',name);
        List<String> loreColored = new ArrayList<>();
        for(String string: lore){
            loreColored.add(ChatColor.translateAlternateColorCodes('&',string));
        }
        this.lore = loreColored;
        this.CustomModelData = CustomModelData;
        this.material = Material.getMaterial(material.toUpperCase());
        List<Enchantment> enchantmentsCovered = new ArrayList<>();
        for(String string: enchantments){
            String id = string.split(":")[0];
            int level = Integer.parseInt(string.split(":")[1]);
            Enchantment enchantment = new Enchantment(id, level);
            enchantmentsCovered.add(enchantment);
        }
        this.enchantments = enchantmentsCovered;
    }

    public String getName(){return this.name;}
    public List<String> getLore(){return this.lore;}
    public int getCustomModelData(){return this.CustomModelData;}
    public Material getMaterial(){return this.material;}
    public void setItemStack(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    public ItemStack getItemStack(){
        return this.itemStack;
    }
    public List<Enchantment> getEnchantments(){
        return this.enchantments;
    }
}
