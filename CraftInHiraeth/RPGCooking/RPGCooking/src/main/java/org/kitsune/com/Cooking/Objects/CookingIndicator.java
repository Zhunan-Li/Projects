package org.kitsune.com.Cooking.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CookingIndicator {
    private Player player;
    private int fuelTime;
    private Location usingCampfireLocation;
    private Block usingCampfireBlock;
    private Location usingCauldronLocation;
    private List<String> userMaterials; //玩家放進去的
    private int needCookingTime; //煮熟所需時間
    private int cookingTime; //已經煮了多久
    private Boolean soundPlayed; //煮熟音效播過了沒

    public CookingIndicator(Player player, int fuelTime, Location usingCampfireLocation) {
        this.fuelTime = fuelTime;
        this.usingCampfireLocation = usingCampfireLocation;
        this.usingCampfireBlock = usingCampfireLocation.getBlock();
        this.player = player;
        this.userMaterials = new ArrayList<>();
        this.needCookingTime = 0;
        this.usingCauldronLocation = new Location(usingCampfireLocation.getWorld(), usingCampfireLocation.getBlockX(), usingCampfireLocation.getBlockY() + 1, usingCampfireLocation.getBlockZ());
        this.soundPlayed = false;
    }

    public int getFuelTime() {
        return this.fuelTime;
    }

    public void updateFuelTime(int newFuelTime) {
        this.fuelTime = newFuelTime;
    }

    public Location getUsingCampfireLocation() {
        return this.usingCampfireLocation;
    }

    public Block getUsingCampfireBlock() {
        return this.usingCampfireBlock;
    }

    public Location getUsingCauldronLocation() {
        return this.usingCauldronLocation;
    }

    public List<String> getUserMaterials() {
        return this.userMaterials;
    }

    public void replaceUserMaterialsList(List<String> stringList) {
        this.userMaterials = stringList;
    }

    public void addUserMaterial(String string) {
        this.userMaterials.add(string);
    }

    public void litCampfire(Player p, boolean lit, Location baseBlockLocation) {
        Campfire campfireData = (Campfire) Objects.requireNonNull(baseBlockLocation.getWorld()).getBlockAt(baseBlockLocation).getBlockData();
        campfireData.setLit(lit);
        p.sendBlockChange(this.usingCampfireLocation, campfireData);
    }

    public void addNeedCookingTime(int time) {
        this.needCookingTime += time;
    }

    public int getNeedCookingTime() {
        return this.needCookingTime;
    }

    public void resetNeedCookingTime() {
        this.needCookingTime = 0;
        this.soundPlayed = false;
    }

    public void addCookingTime() {
        this.cookingTime++;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public void resetCookingTime() {
        this.cookingTime = 0;
    }

    public Boolean getSoundPlayed(){
        return this.soundPlayed;
    }

    public void setSoundPlayed(Boolean played){
        this.soundPlayed = played;
    }
}
