package me.springbreezex.craftinhiraethcore.fishing.utils;

import me.springbreezex.craftinhiraethcore.fishing.check.LevelCheck;
import me.springbreezex.craftinhiraethcore.fishing.check.RegionCheck;
import me.springbreezex.craftinhiraethcore.fishing.configs.EventConfigs;
import me.springbreezex.craftinhiraethcore.fishing.configs.FishConfigs;
import me.springbreezex.craftinhiraethcore.fishing.configs.ItemConfigs;
import me.springbreezex.craftinhiraethcore.fishing.listeners.FishingListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateFishingIndicator {
    public static FishingIndicator generateFishingIndicator(Player p, ItemStack fishingRodItemStack) {
        List<String> rewardList = new ArrayList<>();
        Map<String, ItemStack> itemRewardList = new HashMap<>();
        Map<String, Event> eventRewardList = new HashMap<>();
        Entity rod = FishingListener.rodMap.get(p.getUniqueId());
        float fishingRodFishBuff = 1;
        float fishingRodEventBuff = 1;
        if (fishingRodItemStack.getItemMeta().getDisplayName().contains("活餌")) {
            fishingRodFishBuff = 1.5f;
        }
        if (fishingRodItemStack.getItemMeta().getDisplayName().contains("魔石")) {
            fishingRodEventBuff = 1.5f;
        }
        /* add fish rewards to the rewardList */
        for (Fish fish : FishConfigs.fishMap.values()) {
            if (LevelCheck.LevelCheck(p, fish.getLevel_min(), fish.getLevel_max()) && RegionCheck.checkRegion(rod, fish.getRegion())) {
                /* 機率 */
                for (int count = (int) (((Math.random() * fish.getChance_max()) + fish.getChance_min()) * fishingRodFishBuff); count > 0; count--) {
                    rewardList.add(fish.toString());
                }
                /* 兌換 */
                itemRewardList.put(fish.toString(), fish.getItemStack().getItem());
            }
        }
        /* add item reward to the rewardList */
        for (Item item : ItemConfigs.itemMap.values()) {
            if (LevelCheck.LevelCheck(p, item.getLevel_min(), item.getLevel_max()) && RegionCheck.checkRegion(rod, item.getRegion())) {
                for (int count = (int) (Math.random() * item.getChance_max()) + item.getChance_min(); count > 0; count--) {
                    rewardList.add(item.toString());
                }
                itemRewardList.put(item.toString(), item.getItemStack().getItem());
            }
        }
        /* add event reward to the rewardList */
        for (Event event : EventConfigs.eventMap.values()) {
            if (LevelCheck.LevelCheck(p, event.getLevel_min(), event.getLevel_max()) && RegionCheck.checkRegion(rod, event.getRegion())) {
                for (int count = (int) (((Math.random() * event.getChance_max()) + event.getChance_min()) * fishingRodEventBuff); count > 0; count--) {
                    rewardList.add(event.toString());
                }
                eventRewardList.put(event.toString(), event);
            }
        }
        if (rewardList.isEmpty()) return null;
        else {
            String rewardID = rewardList.get((int) (Math.random() * rewardList.size()));
            FishingIndicator fishingIndicator = null;
            if (itemRewardList.containsKey(rewardID)) {
                /* ItemStack reward */
                ItemStack rewardItemStack = itemRewardList.get(rewardID);

                /* ItemStack(Fish) reward */
                for (Fish fish : FishConfigs.fishMap.values()) {
                    if (fish.getItemStack().getItem().equals(rewardItemStack)) {
                        fishingIndicator = new FishingIndicator("FISH");
                        fishingIndicator.setFish(fish);
                    }
                }
                /* ItemStack(Item) reward */
                for (Item item : ItemConfigs.itemMap.values()) {
                    if (item.getItemStack().getItem().equals(rewardItemStack)) {
                        fishingIndicator = new FishingIndicator("ITEM");
                        fishingIndicator.setItem(item);
                    }
                }
            } else if (eventRewardList.containsKey(rewardID)) {
                /* Event reward */
                Event event = eventRewardList.get(rewardID);
                fishingIndicator = new FishingIndicator("EVENT");
                fishingIndicator.setEvent(event);
            }
            assert fishingIndicator != null;
            fishingIndicator.generateStats();
            return fishingIndicator;
        }
    }
}
