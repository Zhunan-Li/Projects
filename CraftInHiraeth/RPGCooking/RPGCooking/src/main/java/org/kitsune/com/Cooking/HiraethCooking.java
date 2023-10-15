package org.kitsune.com.Cooking;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.kitsune.com.Cooking.Configs.Configs;
import org.kitsune.com.Cooking.Database.DatabaseCore;
import org.kitsune.com.Cooking.Listener.EventsListener;
import org.kitsune.com.Cooking.Listener.ExpListener;
import org.kitsune.com.Cooking.Zukan.ZukanInitializer;

import java.io.IOException;

public class HiraethCooking {
    private Plugin plugin;

    public HiraethCooking(Plugin _plugin) {
        this.plugin = _plugin;
        this.plugin.getLogger().info("a");
        try {
            new Configs(this.plugin);
        } catch (IOException e) {
            e.printStackTrace();
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }

        new DatabaseCore(this.plugin);
        new EventsListener(this.plugin);
        new ExpListener(this.plugin);
        new ZukanInitializer(this.plugin);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(this.plugin).listenerPriority(ListenerPriority.HIGHEST).types(PacketType.Play.Client.USE_ITEM)) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                if (!EventsListener.uuidCookingIndicatorMap.containsKey(event.getPlayer().getUniqueId())) return;
                MovingObjectPositionBlock m = (MovingObjectPositionBlock) packet.getModifier().read(0);
                int x = m.a().u();
                int y = m.a().v();
                int z = m.a().w();
                Location loc = EventsListener.uuidCookingIndicatorMap.get(event.getPlayer().getUniqueId()).getUsingCampfireLocation();
                if (loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
