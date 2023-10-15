package org.kitsune.com.Cooking.Listener;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.kitsune.com.Cooking.Configs.Configs;
import org.kitsune.com.Cooking.Listener.Utils.*;
import org.kitsune.com.Cooking.Objects.CookingIndicator;

import java.util.*;

public class EventsListener implements Listener {
    public static Map<UUID, CookingIndicator> uuidCookingIndicatorMap = new HashMap<>();
    Map<UUID, BukkitRunnable> uuidBukkitRunnableMap = new HashMap<>();
    private Plugin plugin;
    private ItemAnimation itemAnimation;
    private SendIndicator sendIndicator;
    private Material furnaceTopMaterial;
    private Material furnaceBaseMaterial;
    private int fuelTime;

    public EventsListener(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        this.itemAnimation = new ItemAnimation(this.plugin);

        this.sendIndicator = new SendIndicator();
        this.furnaceTopMaterial = Material.getMaterial(Configs.configuration.getString("StructMaterial.Top").toUpperCase());
        this.furnaceBaseMaterial = Material.getMaterial(Configs.configuration.getString("StructMaterial.Base").toUpperCase());
        this.fuelTime = Configs.configuration.getInt("Fuel_Settings.Time");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void playerAnimationEvent(PlayerAnimationEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if (!event.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) return;
        Block clickedBlock = p.getTargetBlockExact(4);
        if (clickedBlock == null) return;
        if (clickedBlock.getType().equals(this.furnaceTopMaterial) && uuidCookingIndicatorMap.containsKey(uuid)) {
            CookingIndicator cookingIndicator = uuidCookingIndicatorMap.get(uuid);
            if (cookingIndicator.getUserMaterials().isEmpty()) return;
            if (p.isSneaking()) {
                /* take out all material */
                cookingIndicator.resetCookingTime(); //reset cookingTime
                cookingIndicator.setSoundPlayed(false);

                List<ItemStack> materialItemStacks = new ArrayList<>();
                for (String name : cookingIndicator.getUserMaterials()) {
                    materialItemStacks.add(HiraethConsume.getFoodItemStack(name));
                }
                HashMap<Integer, ItemStack> leakItemsMap = p.getInventory().addItem(materialItemStacks.toArray(new ItemStack[0]));
                cookingIndicator.replaceUserMaterialsList(new ArrayList<>());
                List<String> leakItemsName = new ArrayList<>();
                for (ItemStack itemStack : leakItemsMap.values()) {
                    leakItemsName.add(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).split(" ")[0]);
                }
                cookingIndicator.replaceUserMaterialsList(leakItemsName);
            } else {
                /* finish */
                if (cookingIndicator.getCookingTime() < cookingIndicator.getNeedCookingTime()) return; //沒熟不給拿

                ItemStack itemStack = RecipeCheck.generateItem(p, cookingIndicator.getUserMaterials());
                this.itemAnimation.sendItemStackPacket(p, new Location(cookingIndicator.getUsingCauldronLocation().getWorld(),
                        cookingIndicator.getUsingCauldronLocation().getBlockX() + 0.5,
                        cookingIndicator.getUsingCauldronLocation().getBlockY() + 3,
                        cookingIndicator.getUsingCauldronLocation().getBlockZ() + 0.5), itemStack);

                ItemStack fishOil = OilCheck.generateOil(cookingIndicator.getUserMaterials());
                if (fishOil != null) {
                    this.itemAnimation.sendItemStackPacket(p, new Location(cookingIndicator.getUsingCauldronLocation().getWorld(),
                            cookingIndicator.getUsingCauldronLocation().getBlockX() + 0.5,
                            cookingIndicator.getUsingCauldronLocation().getBlockY() + 3.5,
                            cookingIndicator.getUsingCauldronLocation().getBlockZ() + 0.5), fishOil);
                }
                AddExp.addExp(p, cookingIndicator);
                cookingIndicator.replaceUserMaterialsList(new ArrayList<>());
            }
            sendIndicator.sendIndicator(cookingIndicator, p);
            cookingIndicator.resetNeedCookingTime();
            cookingIndicator.resetCookingTime();
            p.playSound(cookingIndicator.getUsingCauldronLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 2);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerClickBlock(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && !(event.getAction().equals(Action.LEFT_CLICK_BLOCK)))
            return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;

        Block clickedBlock = event.getClickedBlock();
        assert clickedBlock != null;
        Location clickedBlockLocation = clickedBlock.getLocation();
        Player p = event.getPlayer();
        UUID playerUUID = p.getUniqueId();
        /* check if the furnace is valid */
        if (StructCheck.struckCheck(clickedBlock, this.furnaceTopMaterial, this.furnaceBaseMaterial)) {
            event.setCancelled(true);
            /* check if the player is using a furnace */
            if (!uuidCookingIndicatorMap.containsKey(playerUUID)) {
                /* the player doesn't using any furnace */
                if (!clickedBlock.getType().equals(furnaceTopMaterial)) {
                    /* a new furnace user */
                    if (FuelCheck.checkIfItemIsFuel(p.getInventory().getItemInMainHand()) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        /* the player is right click with a valid fuel */
                        int calculateFuelTime = this.fuelTime;
                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                        p.playSound(clickedBlockLocation, Sound.ITEM_FLINTANDSTEEL_USE, 1, 0);
                        CookingIndicator cookingIndicator = new CookingIndicator(p, calculateFuelTime, clickedBlockLocation);
                        uuidCookingIndicatorMap.put(playerUUID, cookingIndicator);
                        /* set timer */
                        uuidBukkitRunnableMap.put(playerUUID, new BukkitRunnable() {
                            @Override
                            public void run() {
                                int fuelTimeRemaining = cookingIndicator.getFuelTime();
                                Player rP = p;
                                if (Bukkit.getServer().getPlayer(playerUUID) == null) return;
                                if (Bukkit.getServer().getPlayer(playerUUID).isOnline()) {
                                    rP = Bukkit.getServer().getPlayer(playerUUID);
                                }
                                if (fuelTimeRemaining > 0) {
                                    cookingIndicator.updateFuelTime(fuelTimeRemaining - 1);
                                    if (!cookingIndicator.getUserMaterials().isEmpty() && (cookingIndicator.getCookingTime() < cookingIndicator.getNeedCookingTime()))
                                        cookingIndicator.addCookingTime();
                                    if (cookingIndicator.getCookingTime() == cookingIndicator.getNeedCookingTime() && !cookingIndicator.getSoundPlayed()){
                                        rP.playSound(cookingIndicator.getUsingCauldronLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 2);
                                        cookingIndicator.setSoundPlayed(true);
                                    }
                                    cookingIndicator.litCampfire(rP, true, cookingIndicator.getUsingCampfireLocation());
                                    sendIndicator.sendIndicator(cookingIndicator, rP);
                                    if (!cookingIndicator.getUserMaterials().isEmpty()){
                                        p.playSound(cookingIndicator.getUsingCauldronLocation(), Sound.BLOCK_LAVA_AMBIENT, 1, 1);
                                    }
                                } else {
                                    /* reset the player */
                                    sendIndicator.destroyArmorStandPacket(rP);
                                    uuidBukkitRunnableMap.get(rP.getUniqueId()).cancel();
                                    uuidBukkitRunnableMap.remove(rP.getUniqueId());
                                    uuidCookingIndicatorMap.get(rP.getUniqueId()).litCampfire(rP, false, cookingIndicator.getUsingCampfireLocation());
                                    uuidCookingIndicatorMap.remove(rP.getUniqueId());
                                }
                            }
                        });
                        uuidBukkitRunnableMap.get(playerUUID).runTaskTimer(this.plugin, 0, 20);
                    }
                }
            } else {
                /* the player is using a furnace */

                /* check if the player is using another furnace */
                if (!FurnaceCheck.furnaceCheck(clickedBlock, uuidCookingIndicatorMap.get(playerUUID), furnaceTopMaterial, furnaceBaseMaterial)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Configs.configuration.getString("Messages.AlreadyFire")));
                    event.setCancelled(true);
                } else {
                    /* check player is click on a base block(campfire) or a top block(cauldron) */
                    CookingIndicator cookingIndicator = uuidCookingIndicatorMap.get(playerUUID);
                    if (clickedBlock.getType().equals(furnaceTopMaterial)) {
                        /* player clicked on the top block(cauldron) */
                        if (MaterialCheck.materialCheck(p, p.getInventory().getItemInMainHand())) {
                            /* player clicked on the top block(cauldron) with a valid material */
                            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                                /* 限制material數量為5 */
                                if (cookingIndicator.getUserMaterials().size() < 5) {
                                    /* add material */
                                    String materialName = ChatColor.stripColor(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName()).split(" ")[0];
                                    cookingIndicator.addUserMaterial(materialName);
                                    cookingIndicator.addNeedCookingTime(GetMaterialCookingTime.getMaterialCookingTime(HiraethConsume.getFoodItems().get(materialName).getTier()));
                                    cookingIndicator.setSoundPlayed(false);
                                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                                    sendIndicator.sendIndicator(cookingIndicator, p);
                                    p.playSound(cookingIndicator.getUsingCauldronLocation(), Sound.ITEM_BUCKET_FILL_LAVA, 1, 1);
                                }
                            }
                        }
                        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && p.isSneaking()) {
                            /* take out all material */
                            cookingIndicator.resetCookingTime(); //reset cookingTime
                            cookingIndicator.resetNeedCookingTime();

                            List<ItemStack> materialItemStacks = new ArrayList<>();
                            for (String name : cookingIndicator.getUserMaterials()) {
                                materialItemStacks.add(HiraethConsume.getFoodItemStack(name));
                            }
                            HashMap<Integer, ItemStack> leakItemsMap = p.getInventory().addItem(materialItemStacks.toArray(new ItemStack[0]));
                            cookingIndicator.replaceUserMaterialsList(new ArrayList<>());
                            List<String> leakItemsName = new ArrayList<>();
                            for (ItemStack itemStack : leakItemsMap.values()) {
                                leakItemsName.add(ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).split(" ")[0]);
                            }
                            cookingIndicator.replaceUserMaterialsList(leakItemsName);
                            cookingIndicator.setSoundPlayed(false);
                            sendIndicator.sendIndicator(cookingIndicator, p);
                        }
                        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && !p.isSneaking() && !cookingIndicator.getUserMaterials().isEmpty()) {
                            /* 沒熟不給拿 */
                            if (cookingIndicator.getCookingTime() < cookingIndicator.getNeedCookingTime()) return;
                            /* finish */
                            ItemStack itemStack = RecipeCheck.generateItem(p, cookingIndicator.getUserMaterials());
                            this.itemAnimation.sendItemStackPacket(p, new Location(cookingIndicator.getUsingCauldronLocation().getWorld(),
                                    cookingIndicator.getUsingCauldronLocation().getBlockX() + 0.5,
                                    cookingIndicator.getUsingCauldronLocation().getBlockY() + 3,
                                    cookingIndicator.getUsingCauldronLocation().getBlockZ() + 0.5), itemStack);
                            ItemStack fishOil = OilCheck.generateOil(cookingIndicator.getUserMaterials());
                            if (fishOil != null) {
                                this.itemAnimation.sendItemStackPacket(p, new Location(cookingIndicator.getUsingCauldronLocation().getWorld(),
                                        cookingIndicator.getUsingCauldronLocation().getBlockX() + 0.5,
                                        cookingIndicator.getUsingCauldronLocation().getBlockY() + 3.5,
                                        cookingIndicator.getUsingCauldronLocation().getBlockZ() + 0.5), fishOil);
                            }
                            AddExp.addExp(p, cookingIndicator);
                            cookingIndicator.replaceUserMaterialsList(new ArrayList<>());
                            sendIndicator.sendIndicator(cookingIndicator, p);
                            cookingIndicator.resetCookingTime();
                            cookingIndicator.resetNeedCookingTime();
                            p.playSound(cookingIndicator.getUsingCauldronLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 2);
                        }
                    } else {
                        /* player clicked on the base block(campfire) */
                        if (FuelCheck.checkIfItemIsFuel(p.getInventory().getItemInMainHand())) {
                            /* if player clicked on the base block with a valid fuel -> add fuel */
                            int calculateFuelTime = this.fuelTime;
                            if (calculateFuelTime + cookingIndicator.getFuelTime() > 60) {
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                return;
                            }
                            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                            p.playSound(cookingIndicator.getUsingCampfireLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 0);
                            p.playSound(cookingIndicator.getUsingCampfireLocation(), Sound.ENTITY_GENERIC_BURN, 1, 2);
                            cookingIndicator.updateFuelTime(calculateFuelTime + cookingIndicator.getFuelTime());
                            sendIndicator.sendIndicator(cookingIndicator, p);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SendIndicator.uuidArmorStandsMap.containsKey(event.getPlayer().getUniqueId())) {
            SendIndicator.quitPlayerList.add(event.getPlayer().getUniqueId());
        }
    }
}
