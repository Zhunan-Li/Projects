package org.kitsune.com;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EventListener implements Listener {
    private final HiraethElevator plugin;
    private final Elevator elevator;
    public EventListener(HiraethElevator plugin, Elevator elevator){
        this.plugin = plugin;
        this.elevator = elevator;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onButtonPressed(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.OAK_BUTTON)){
            Location clickedBlockLocation = event.getClickedBlock().getLocation();
            if(clickedBlockLocation.equals(new Location(clickedBlockLocation.getWorld(), 13, 64, 186))){
                this.elevator.up();
            }
            if(clickedBlockLocation.equals(new Location(clickedBlockLocation.getWorld(), 13, 69, 186))){
                this.elevator.down();
            }
        }
    }
}
