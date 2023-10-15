package org.kitsune.com.Cooking.Listener.Utils;

import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.kitsune.com.Cooking.Configs.Configs;

import java.util.Map;

public class ItemAnimation {
    private Plugin plugin;

    public ItemAnimation(Plugin plugin) {
        this.plugin = plugin;
    }

    public void sendItemStackPacket(Player p, Location loc, ItemStack item) {

        EntityItem item2 = new EntityItem(((CraftWorld) p.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        item2.a(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        item2.a(CraftChatMessage.fromStringOrNull(item.getItemMeta().getDisplayName()));
        item2.e(true);
        item2.n(0, 0, 0);
        item2.c(false);
        item2.n(true);
        PacketPlayOutSpawnEntity spawnItem = new PacketPlayOutSpawnEntity(item2, 1);
        PacketPlayOutEntityVelocity velItem = new PacketPlayOutEntityVelocity(item2);
        PacketPlayOutEntityMetadata data = new PacketPlayOutEntityMetadata(item2.ae(), item2.ai(), true);

        ((CraftPlayer) p).getHandle().b.a(spawnItem);
        ((CraftPlayer) p).getHandle().b.a(data);
        ((CraftPlayer) p).getHandle().b.a(velItem);
        p.spawnParticle(Particle.TOTEM, loc, 2);

        Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
            @Override
            public void run() {
                item2.n(p.getLocation().getX() - item2.dc(), (p.getLocation().getY() - item2.de()) * 0.5, p.getLocation().getZ() - item2.di());
                PacketPlayOutEntityVelocity velItem2 = new PacketPlayOutEntityVelocity(item2);
                ((CraftPlayer) p).getHandle().b.a(velItem2);
                p.spawnParticle(Particle.FIREWORKS_SPARK, loc, 2);

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                        PacketPlayOutEntityDestroy destroyItem = new PacketPlayOutEntityDestroy(item2.ae());

                        ((CraftPlayer) p).getHandle().b.a(destroyItem);
                        Map<Integer, ItemStack> leakItemsMap = p.getInventory().addItem(item);
                        if (!leakItemsMap.isEmpty()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Configs.configuration.getString("Messages.InventoryIsFull")));
                            for (ItemStack itemStack : leakItemsMap.values()) {
                                p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
                            }
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                    }
                }, 5L);

            }
        }, 40L);
    }
}
