package me.springbreezex.craftinhiraethcore.fishing.check;

import me.springbreezex.craftinhiraethcore.fishing.listeners.FishingListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class RangeCheck {
    public static boolean checkRange(Player p) {
        List<Entity> entityList = p.getNearbyEntities(30, 30, 30);
        return entityList.contains(FishingListener.rodMap.get(p.getUniqueId()));
    }
}
