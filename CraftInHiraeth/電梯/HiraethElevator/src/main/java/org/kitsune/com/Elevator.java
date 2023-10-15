package org.kitsune.com;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private HiraethElevator plugin;
    private final BukkitRunnable timer;
    private final List<Location> elevatorLocation;
    private final List<Entity> elevator;
    private final List<Entity> fallingBlock;
    private final List<Entity> shulker;
    public Elevator(HiraethElevator plugin){
        this.plugin = plugin;
        this.elevatorLocation = new ArrayList<>();
        this.elevator = new ArrayList<>();
        this.fallingBlock = new ArrayList<>();
        this.shulker = new ArrayList<>();

        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 14.5, 62, 189.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 13.5, 62, 189.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 12.5, 62, 189.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 14.5, 62, 188.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 13.5, 62, 188.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 12.5, 62, 188.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 14.5, 62, 187.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 13.5, 62, 187.5));
        elevatorLocation.add(new Location(this.plugin.getServer().getWorlds().get(0), 12.5, 62, 187.5));

        BlockData oakPlanksData = Bukkit.createBlockData(Material.OAK_PLANKS);
        for(Location loc: elevatorLocation){
            Entity armorStand = this.plugin.getServer().getWorlds().get(0).spawn(loc, ArmorStand.class, a -> {
                a.setGravity(false);
                a.setInvisible(true);
                a.setInvulnerable(true);
                a.setSilent(true);
            });


            Entity fallingBlock = this.plugin.getServer().getWorlds().get(0).spawnFallingBlock(loc, oakPlanksData);
            fallingBlock.setGravity(false);
            fallingBlock.setTicksLived(1);
            this.fallingBlock.add(fallingBlock);

            Entity shulker = this.plugin.getServer().getWorlds().get(0).spawn(loc, Shulker.class, s -> {
                s.setInvisible(true);
                s.setInvulnerable(true);
                s.setGravity(false);
                s.setAI(false);
                s.setSilent(true);
            });
            this.shulker.add(shulker);

            armorStand.addPassenger(shulker);
            armorStand.addPassenger(fallingBlock);
            this.elevator.add(armorStand);
        }

        this.timer = new BukkitRunnable() {
            @Override
            public void run() {
                for(Entity entity : fallingBlock){
                    entity.setTicksLived(1);
                }
            }
        };
        this.timer.runTaskTimer(this.plugin, 0 , 300);
    }

    List<Entity> passengers = new ArrayList<>();
    public void up(){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for(Entity armorStand : elevator){
                    double y = armorStand.getLocation().getY();
                    if(y < 66.5){
                        y += 0.2;
                        for(Entity passenger : armorStand.getPassengers()){
                            passenger.leaveVehicle();
                            passengers.add(passenger);
                        }
                        armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX(), y, armorStand.getLocation().getZ()));
                        for(Entity passenger : passengers){
                            armorStand.addPassenger(passenger);
                        }
                        passengers.clear();
                    }else{
                        cancel();
                    }
                }
            }
        };
        bukkitRunnable.runTaskTimer(this.plugin , 0 , 2);
    }

    public void down(){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for(Entity armorStand : elevator){
                    double y = armorStand.getLocation().getY();
                    if(y > 61.5){
                        y -= 0.2;
                        for(Entity passenger : armorStand.getPassengers()){
                            passenger.leaveVehicle();
                            passengers.add(passenger);
                        }
                        armorStand.teleport(new Location(armorStand.getWorld(), armorStand.getLocation().getX(), y, armorStand.getLocation().getZ()));
                        for(Entity passenger : passengers){
                            armorStand.addPassenger(passenger);
                        }
                        passengers.clear();
                    }else{
                        cancel();
                    }
                }
            }
        };
        bukkitRunnable.runTaskTimer(this.plugin , 0 , 2);
    }

    public List<Entity> getElevator(){
        return this.elevator;
    }
    public List<Entity> getFallingBlock(){
        return this.fallingBlock;
    }
    public List<Entity> getShulker(){
        return this.shulker;
    }

    public void clearAllEntity(){
        this.timer.cancel();
        for(Entity entity : shulker){
            entity.remove();
        }
        for(Entity entity : fallingBlock){
            entity.remove();
        }
        for(Entity entity : elevator){
            entity.remove();
        }
    }
}
