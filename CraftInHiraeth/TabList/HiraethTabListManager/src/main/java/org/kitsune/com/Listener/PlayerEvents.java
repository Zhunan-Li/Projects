package org.kitsune.com.Listener;

import de.simonsator.partyandfriends.api.events.friends.FriendRemovedEvent;
import de.simonsator.partyandfriends.api.events.friends.FriendRequestAcceptedEvent;
import de.simonsator.partyandfriends.api.events.party.LeftPartyEvent;
import de.simonsator.partyandfriends.api.events.party.PartyCreatedEvent;
import de.simonsator.partyandfriends.api.events.party.PartyJoinEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.kitsune.com.HiraethTabListManager;
import org.kitsune.com.TabListManager.TabList;

public class PlayerEvents implements Listener {
    private HiraethTabListManager plugin;
    private TabList tabList;
    public PlayerEvents(HiraethTabListManager plugin) throws InterruptedException {
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerListener(this.plugin, this);
        this.tabList = new TabList(this.plugin);
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        this.tabList.setDefaultTabList(event.getPlayer());
        new Thread(() -> {
            try {
                Thread.sleep(500);
                tabList.getTabList(event.getPlayer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @EventHandler
    public void onPlayerQuit(PlayerDisconnectEvent event){
        tabList.playerDisconnect(event.getPlayer());
    }

    @EventHandler
    public void onFriendAdd(FriendRequestAcceptedEvent event){
        new Thread(() -> {
            try{
                Thread.sleep(500);
                tabList.updateTabListInfo(null);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

    @EventHandler
    public void onFriendRemoved(FriendRemovedEvent event){
        new Thread(() -> {
            try{
                Thread.sleep(500);
                tabList.updateTabListInfo(null);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

    @EventHandler
    public void onPartyCreate(PartyCreatedEvent event){
        new Thread(() -> {
            try{
                Thread.sleep(500);
                tabList.updateTabListInfo(null);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void onPartyJoin(PartyJoinEvent event){
        new Thread(() -> {
            try {
                Thread.sleep(500);
                tabList.updateTabListInfo(null);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

    @EventHandler
    public void onPartyLeave(LeftPartyEvent event){
        new Thread(() -> {
            try {
                Thread.sleep(500);
                tabList.updateTabListInfo(null);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }
}
