package me.springbreezex.craftinhiraethcore.fishing.check;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Entity;

import java.util.List;

public class RegionCheck {
    public static boolean checkRegion(Entity rod, List<String> regionList) {
        RegionContainer worldContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager worldRegions = worldContainer.get(BukkitAdapter.adapt(rod.getWorld()));
        if ((worldRegions == null) || (regionList.isEmpty())) {
            return true;
        } else {
            Location loc = BukkitAdapter.adapt(rod.getLocation());
            RegionContainer playerContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = playerContainer.createQuery();
            ApplicableRegionSet playerRegionSet = query.getApplicableRegions(loc);
            for (ProtectedRegion region : playerRegionSet) {
                if (regionList.contains(region.getId())) return true;
            }
        }
        return false;
    }
}
