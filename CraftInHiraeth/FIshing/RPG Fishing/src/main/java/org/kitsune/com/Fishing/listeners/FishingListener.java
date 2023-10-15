package me.springbreezex.craftinhiraethcore.fishing.listeners;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.enums.ExpSource;
import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import me.springbreezex.craftinhiraethcore.fishing.HiraethFishing;
import me.springbreezex.craftinhiraethcore.fishing.check.FishingRodLevelCheck;
import me.springbreezex.craftinhiraethcore.fishing.check.RangeCheck;
import me.springbreezex.craftinhiraethcore.fishing.configs.Config;
import me.springbreezex.craftinhiraethcore.fishing.object.FishingRodItem;
import me.springbreezex.craftinhiraethcore.fishing.utils.Event;
import me.springbreezex.craftinhiraethcore.fishing.utils.FishingIndicator;
import me.springbreezex.craftinhiraethcore.fishing.utils.GenerateFishingIndicator;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftChatMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FishingListener implements Listener {
    public static Map<UUID, Entity> rodMap = new HashMap<>();
    private static Plugin plugin;
    private Map<UUID, FishingIndicator> caught = new HashMap<>();
    private Map<UUID, BukkitRunnable> runnableMap = new HashMap<>();

    public FishingListener(Plugin plugin) {
        FishingListener.plugin = plugin;
        FishingListener.plugin.getServer().getPluginManager().registerEvents(this, FishingListener.plugin);
    }

    public static String getDurabilityBar(double percent) {
        if (percent >= 95) return "뿫";
        if (percent >= 90) return "뿪";
        if (percent >= 85) return "뿩";
        if (percent >= 80) return "뿨";
        if (percent >= 75) return "뿧";
        if (percent >= 70) return "뿦";
        if (percent >= 65) return "뿥";
        if (percent >= 60) return "뿤";
        if (percent >= 55) return "뿣";
        if (percent >= 50) return "뿢";
        if (percent >= 45) return "뿡";
        if (percent >= 40) return "뿠";
        if (percent >= 35) return "뿟";
        if (percent >= 30) return "뿞";
        if (percent >= 25) return "뿝";
        if (percent >= 20) return "뿜";
        if (percent >= 15) return "뿛";
        if (percent >= 10) return "뿚";
        if (percent >= 5) return "뿙";
        if (percent >= 0) return "뿘";
        return "뿬";
    }

    @EventHandler
    public void fishing(PlayerFishEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        ItemStack fishingRodItem = p.getInventory().getItemInMainHand();
        if (!fishingRodItem.getType().equals(Material.FISHING_ROD)) {
            return;
        }
        FishingRodItem fishingRod = null;
        for (FishingRodItem fr : FishingRodItem.values()) {
            if (fishingRodItem.getItemMeta() == null) return;
            if (ChatColor.stripColor(fishingRodItem.getItemMeta().getDisplayName()).equals(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', fr.getName())))) {
                fishingRod = fr;
            }
        }
        if (fishingRod == null) {
            return;
        }

        /* check fishing rod required level */
        if (FishingRodLevelCheck.checkLevel(fishingRod, p)) {

            /* player already caught fish and right click again */
            if (caught.containsKey(uuid) && (event.getState() != PlayerFishEvent.State.FAILED_ATTEMPT) && (event.getState() != PlayerFishEvent.State.BITE)) {
                event.setCancelled(true);
                FishingIndicator fishingIndicator = caught.get(uuid);
                /* pull check */
                if ((fishingIndicator.getGreenStart() + 3 < fishingIndicator.getCountDown() * 3) && (fishingIndicator.getCountDown() * 3 <= fishingIndicator.getGreenEnd() + 3)) {
                    /* pull successfully */
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    fishingIndicator.pullSuccess();
                } else {
                    /* pull failed */
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.FishRushAway")), "", 10, 30, 20);
                    reset(p);
                }
                updateFishingRodDuration(fishingRodItem, p);
                /* caught fish for first time */
            } else if ((event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) && (event.getCaught() != null) && (!caught.containsKey(uuid))) {
                event.setCancelled(true);
                /* clear the origin loot */
                ((Item) event.getCaught()).getItemStack().setAmount(0);
                if (!updateFishingRodDuration(fishingRodItem, p)) {
                    return;
                }

                FishingIndicator fishingIndicator = GenerateFishingIndicator.generateFishingIndicator(p, fishingRodItem);
                /* no reward */
                if (fishingIndicator == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.NoRewardMatch")));
                    rodMap.get(uuid).remove();
                    rodMap.remove(uuid);
                } else {
                    /* reward matched */
                    caught.put(uuid, fishingIndicator);
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.CaughtFishTitle")), "", 10, 30, 10);

                    runnableMap.put(uuid, new BukkitRunnable() {
                        @Override
                        public void run() {
                            /* check player is holding fishing rod */
                            if (!(p.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)) && !(p.getInventory().getItemInOffHand().getType().equals(Material.FISHING_ROD))) {
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.NoFishingRod")), "", 10, 30, 10);
                                reset(p);
                                /* 玩家有沒有遠離浮標 */
                            } else if (!RangeCheck.checkRange(p)) {
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.OutOfRange")), "", 10, 30, 10);
                                reset(p);
                                /* check pull times */
                            } else if (fishingIndicator.getPullTimesRemain() > 0) {
                                if (!fishingIndicator.checkTitle()) {
                                    String title = fishingIndicator.generateTitle();
                                    String pullTimesBar = fishingIndicator.generatePullTimesBar();
                                    p.sendTitle(title, pullTimesBar, 0, 40, 0);
                                    fishingIndicator.setCountDown(1);
                                } else {
                                    if (fishingIndicator.getCountDown() <= 20) {
                                        p.sendTitle(fishingIndicator.generateYellowBar().concat(fishingIndicator.getTitle().substring(3)), fishingIndicator.generatePullTimesBar(), 0, 20, 0);
                                        fishingIndicator.setCountDown(fishingIndicator.getCountDown() + 1);
                                        fishingIndicator.generateTitle();
                                    } else {
                                        reset(p);
                                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.FishRushAway")), "", 0, 40, 20);
                                    }
                                }
                            } else {
                                /* give player reward */
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.getReward")), fishingIndicator.generatePullTimesBar(), 0, 30, 20);
                                /* summon item reward */
                                if (!fishingIndicator.getRewardType().equals("EVENT")) {
                                    ItemStack rewardItemStack = null;
                                    if (fishingIndicator.getRewardType().equals("FISH")) {
                                        rewardItemStack = fishingIndicator.getFish().getItemStack().getItem();
                                    }
                                    if (fishingIndicator.getRewardType().equals("ITEM")) {
                                        rewardItemStack = fishingIndicator.getItem().getItemStack().getItem();
                                    }
                                    assert rewardItemStack != null;
                                    sendItemStackPacket(p, rodMap.get(uuid).getLocation(), rewardItemStack);
                                } else {
                                    /* trigger event reward */
                                    Event event = fishingIndicator.getEvent();
                                    p.sendMessage("EventType: ".concat(event.getType()).concat(" EventMobName: ").concat(event.getMob_name()));
                                }
                                int xp = fishingIndicator.getXp();
                                EXPReward(p,xp);
                                reset(p);
                            }
                        }
                    });
                    runnableMap.get(uuid).runTaskTimer(this.plugin, 10, 3);
                }
            }

        } else {
            /* doesn't reach the required level */
            event.setCancelled(true);
        }
    }

    public static void EXPReward(Player p, int xp) {
        PlayerClass pc = SkillAPI.getPlayerData(p).getClass("fisherman");
        int exp = (int) (xp*(Math.pow(1.1,(pc.getLevel()-1))));
        pc.giveExp(exp, ExpSource.COMMAND);
    }

    /* reset the player's fishing status */
    private void reset(Player p) {
        UUID uuid = p.getUniqueId();
        if (rodMap.containsKey(uuid)) {
            rodMap.get(uuid).remove();
            rodMap.remove(uuid);
        }
        if (caught.containsKey(uuid)) caught.remove(uuid);
        if (runnableMap.containsKey(uuid)) {
            runnableMap.get(uuid).cancel();
            runnableMap.remove(uuid);
        }
    }

    /* update fishing rod duration */
    private boolean updateFishingRodDuration(ItemStack fishingRodItem, Player p) {
        ItemMeta fishingRodItemMeta = fishingRodItem.getItemMeta();
        List<String> itemLore = fishingRodItemMeta.getLore();


        String itemDamageLine = ChatColor.stripColor(itemLore.get(itemLore.size() - 2));
        int itemDamage = Integer.parseInt(itemDamageLine.replace("⟲ 耐久度: ", "").split("/")[0]);
        //int itemDamage = 1;
        String maxDamage = itemDamageLine.replace("⟲ 耐久度: ", "").split("/")[1];
        itemDamage = --itemDamage;
        if (itemDamage < 0) {
            /* item break */
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);

            p.sendTitle(ChatColor.translateAlternateColorCodes('&', Config.configuration.getString("Messages.FishRushAway")), "", 10, 30, 20);
            reset(p);
            return false;
        } else {
            /* update the duration lore line */
            itemLore.set(itemLore.size() - 2, ChatColor.translateAlternateColorCodes('&', "&8⟲ 耐久度: ".concat(String.valueOf(itemDamage)).concat("/").concat(maxDamage)));
            double percent = 100.0 * itemDamage / Integer.parseInt(maxDamage);
            itemLore.set(itemLore.size() - 1, ChatColor.translateAlternateColorCodes('&', "&f" + getDurabilityBar(percent)));
            fishingRodItemMeta.setLore(itemLore);
            p.getInventory().getItemInMainHand().setItemMeta(fishingRodItemMeta);
        }
        return true;
    }

    private void sendItemStackPacket(Player p, Location loc, ItemStack item) {
        EntityItem item2 = new EntityItem(((CraftWorld) p.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        item2.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
//        item2.setItemStack(CraftItemStack.asNMSCopy(item));
        item2.setCustomName(CraftChatMessage.fromStringOrNull(item.getItemMeta().getDisplayName()));
        item2.setNoGravity(true);
        item2.setMot(0, 0, 0);
        item2.setOnGround(false);
        item2.setCustomNameVisible(true);
        PacketPlayOutSpawnEntity spawnItem = new PacketPlayOutSpawnEntity(item2, 1);
        PacketPlayOutEntityVelocity velItem = new PacketPlayOutEntityVelocity(item2);
        PacketPlayOutEntityMetadata data = new PacketPlayOutEntityMetadata(item2.getId(), item2.getDataWatcher(), true);

        ((CraftPlayer) p).getHandle().b.sendPacket(spawnItem);
        ((CraftPlayer) p).getHandle().b.sendPacket(data);
        ((CraftPlayer) p).getHandle().b.sendPacket(velItem);
        p.spawnParticle(Particle.FIREWORKS_SPARK, loc, 2);

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                item2.setMot(p.getLocation().getX() - item2.locX(), p.getLocation().getY() - item2.locY(), p.getLocation().getZ() - item2.locZ());
                PacketPlayOutEntityVelocity velItem2 = new PacketPlayOutEntityVelocity(item2);
                ((CraftPlayer) p).getHandle().b.sendPacket(velItem2);
                p.spawnParticle(Particle.FIREWORKS_SPARK, loc, 2);

                Bukkit.getScheduler().runTaskLater(FishingListener.plugin, new Runnable() {
                    @Override
                    public void run() {
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                        PacketPlayOutEntityDestroy destroyItem = new PacketPlayOutEntityDestroy(item2.getId());

                        ((CraftPlayer) p).getHandle().b.sendPacket(destroyItem);
                        HashMap<Integer, ItemStack> items_given = p.getInventory().addItem(item);
                        if (!items_given.isEmpty()) {
                            for (ItemStack is : items_given.values()) {
                                p.getWorld().dropItemNaturally(p.getLocation(), is);
                            }
                        }
                    }
                }, 5L);

            }
        }, 10L);
    }

    /* record the player's fishing hook */
    @EventHandler
    public void throwFishingRod(ProjectileLaunchEvent event) {
        if (event.getEntityType().equals(EntityType.FISHING_HOOK)) {
            if (event.getEntity().getShooter() == null) return;
            rodMap.put(((Player) event.getEntity().getShooter()).getUniqueId(), event.getEntity());
        }
    }

    @EventHandler
    public void ExpbarShow (PlayerExperienceGainEvent event) {
        if (event.isCancelled()) return;
        PlayerData pd = event.getPlayerData();
        if(event.getPlayerClass()!=pd.getClass("fisherman")) return;
        new BukkitRunnable() {
            public void run() {

                PlayerClass playerClass = pd.getClass("fisherman");

                int level = playerClass.getLevel();
                double exp = playerClass.getExp();
                double next = playerClass.getRequiredExp();
                float percentage = (float) (exp/next);
                Player p = event.getPlayerData().getPlayer();

                if (HiraethFishing.lvbossbar.containsKey(event.getPlayerData().getPlayer().getUniqueId())) {
                    HiraethFishing.cooldown.put(p.getUniqueId(), 5L);
                    HiraethFishing.lvbossbar.get(p.getUniqueId()).setProgress(percentage);
                    HiraethFishing.lvbossbar.get(p.getUniqueId()).setTitle("釣魚等級 Lv."+level);
                } else {
                    BossBar bossBar = Bukkit.createBossBar("釣魚等級 Lv."+level, BarColor.GREEN, BarStyle.SOLID);
                    bossBar.setProgress(percentage);
                    bossBar.setVisible(true);
                    bossBar.addPlayer(p);
                    HiraethFishing.lvbossbar.put(p.getUniqueId(), bossBar);
                    HiraethFishing.cooldown.put(p.getUniqueId(), 5L);
                    HiraethFishing.cooldownTask.put(p.getUniqueId(), new BukkitRunnable() {
                        @Override
                        public void run() {
                            Long second = HiraethFishing.cooldown.get(p.getUniqueId());
                            if ( second == 0) {
                                this.cancel();
                                HiraethFishing.lvbossbar.remove(p.getUniqueId()).removeAll();
                                HiraethFishing.cooldownTask.remove(p.getUniqueId());
                                HiraethFishing.cooldown.remove(p.getUniqueId());
                            } else {
                                HiraethFishing.cooldown.put(p.getUniqueId(), HiraethFishing.cooldown.get(p.getUniqueId())-1);
                                HiraethFishing.lvbossbar.get(p.getUniqueId()).setVisible(true);
                                bossBar.addPlayer(p);
                            }
                        }
                    });
                    HiraethFishing.cooldownTask.get(p.getUniqueId()).runTaskTimer(HiraethFishing.plugin, 0L, 20L);
                }
            }
        }.runTaskLater(HiraethFishing.plugin, 1);


    }

}
