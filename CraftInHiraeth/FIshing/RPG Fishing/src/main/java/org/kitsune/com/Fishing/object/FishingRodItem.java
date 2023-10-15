package me.springbreezex.craftinhiraethcore.fishing.object;

import me.springbreezex.craftinhiraethcore.CraftInHiraethCore;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public enum FishingRodItem {

    TIERA_STONEFISHINGROD("&5高階魔石釣竿", 2, 9, 35, 4, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7採用了較高級的魔石，並且裝上", "&7了金屬籠保護魔石，即使被大型", "&7魔物撕咬，魔石也不會輕易損壞", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &6S", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f高", "", "&8⟲ 耐久度: 4000/4000", "&f뿫"),
    TIERB_STONEFISHINGROD("&9中階魔石釣竿", 1, 8, 20, 2, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7使用了比較高級的魔石，魔石的", "&7光芒明顯亮了不少，在混濁的水", "&7中也清晰可見", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &aC", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f中", "", "&8⟲ 耐久度: 2000/2000", "&f뿫"),
    TIERC_STONEFISHINGROD("&a初階魔石釣竿", 0, 7, 5, 1, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7不使用傳統的魚鉤，只把發著微", "&7光的魔石綁在線的末端，能稍微", "&7吸引水生魔物和怪東西", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &7E", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f低", "", "&8⟲ 耐久度: 1000/1000", "&f뿫"),

    TIERA_BAITFISHINGROD("&5高階活餌釣竿", 2, 9, 35, 4, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7強韌的魚線，穩固的魚鉤，細膩而", "&7舒適的觸感，這根優秀的釣竿使", "&7你充滿了決心", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &6S", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f高", "", "&8⟲ 耐久度: 4000/4000", "&f뿫"),
    TIERB_BAITFISHINGROD("&9中階活餌釣竿", 1, 8, 20, 2, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7用料稍微有點良心的釣竿，但是", "&7整體而言還是很一般", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &aC", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f中", "", "&8⟲ 耐久度: 2000/2000", "&f뿫"),
    TIERC_BAITFISHINGROD("&a初階活餌釣竿", 0, 7, 5, 1, "", "&f뾿뿀" + CraftInHiraethCore.HiraethDarkColor + "&l採集工具: 魚類", "&f뿁", "&7能在魚鉤上綁活餌的釣竿，不過", "&7鉤子做工非常粗糙，這讓你稍微", "&7失去成為水產大王的決心", "", CraftInHiraethCore.HiraethLightColor + "☀ 需求等級: &7E", CraftInHiraethCore.HiraethLightColor + "\uD83C\uDFA3 採集速度: &f低", "", "&8⟲ 耐久度: 1000/1000", "&f뿫");

    String name;
    int lureLevel;
    int modelID;
    int coinSell;
    int speed;
    List<String> lore;

    FishingRodItem(final String name, final int lureLevel, final int modelID, final int coinSell, final int speed, final String... lore) {
        this.name = name;
        this.lureLevel = lureLevel;
        this.modelID = modelID;
        this.coinSell = coinSell;
        this.speed = speed;
        int i = 0;
        for (String line : lore) {
            lore[i] = ChatColor.translateAlternateColorCodes('&', line);
            i++;
        }
        this.lore = Arrays.asList(lore);
    }

    public void setMaterial(final int modelID) {
        this.modelID = modelID;
    }

    public int getmodelID() {
        return this.modelID;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FISHING_ROD, 1);
        net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);
        stack.setTag(new NBTTagCompound());
        stack.getTag().setInt("coinsell", coinSell);
        item = CraftItemStack.asCraftMirror(stack);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        im.setLore(lore);
        im.setCustomModelData(modelID);
        im.setUnbreakable(true);
        if(lureLevel != 0){
            im.addEnchant(Enchantment.LURE, lureLevel, true);
        }
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);

        return item;
    }
}