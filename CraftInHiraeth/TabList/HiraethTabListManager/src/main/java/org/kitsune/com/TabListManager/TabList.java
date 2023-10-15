package org.kitsune.com.TabListManager;

import codecrafter47.bungeetablistplus.api.bungee.BungeeTabListPlusAPI;
import codecrafter47.bungeetablistplus.api.bungee.CustomTablist;
import codecrafter47.bungeetablistplus.api.bungee.Icon;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.kitsune.com.HiraethTabListManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

public class TabList {
    private HiraethTabListManager plugin;
    private Icon defaultIcon;
    private Map<ProxiedPlayer, CustomTablist> playerCustomTablistMap;
    private GlobalInfo globalInfo;
    private FriendsInfo friendsInfo;
    private PartyInfo partyInfo;
    private CustomTablist defaultTabList;
    public TabList(HiraethTabListManager plugin) throws InterruptedException {
        this.playerCustomTablistMap = new HashMap<>();
        this.plugin = plugin;
        try {
            this.plugin.getLogger().info(this.plugin.getDataFolder().getParentFile().getAbsolutePath());
            BufferedImage image = ImageIO.read(new File(this.plugin.getDataFolder().getParentFile().getAbsolutePath().concat("/BungeeTabListPlus/heads/colors/gray.png")));
            BungeeTabListPlusAPI.createIcon(image, icon -> this.defaultIcon = icon);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Failed to load icon.", ex);
        }
        Thread.sleep(1000); /* wait defaultIcon loaded */

        this.globalInfo = new GlobalInfo(this.plugin, this.defaultIcon);
        this.friendsInfo = new FriendsInfo(this.defaultIcon);
        this.partyInfo = new PartyInfo(this.defaultIcon);

        /* create default tablist */
        this.defaultTabList = BungeeTabListPlusAPI.createCustomTablist();
        this.defaultTabList.setSize(1);
        this.defaultTabList.setSlot(0,0, this.defaultIcon, "", 0);
    }

    public void setDefaultTabList(ProxiedPlayer player){
        BungeeTabListPlusAPI.setCustomTabList(player, this.defaultTabList);
    }

    public void getTabList(ProxiedPlayer player){
        /* create tablist for the join player */
        CustomTablist customTablist = createCustomTabList(player);
        BungeeTabListPlusAPI.setCustomTabList(player, customTablist);
        this.playerCustomTablistMap.put(player, customTablist);

        /* update */
        updateTabListInfo(null);
    }

    private CustomTablist createCustomTabList(ProxiedPlayer player){
        /* init */
        CustomTablist customTablist = BungeeTabListPlusAPI.createCustomTablist();
        customTablist.setSize(80);
        for(int i = 0; i <= 79; i++){
            customTablist.setSlot(0, i, this.defaultIcon, "", 0);
        }
        /* add info */
        this.addTabInfo(customTablist, player.getUniqueId());
        return customTablist;
    }

    public void updateTabListInfo(ProxiedPlayer non_updatePlayer){
        List<ProxiedPlayer> playerList = new ArrayList<>(this.playerCustomTablistMap.keySet());
        playerList.remove(non_updatePlayer);
        for(ProxiedPlayer player : playerList){
            CustomTablist customTablist = this.playerCustomTablistMap.get(player);
            if(non_updatePlayer == null){
                this.addTabInfo(customTablist, player.getUniqueId());
            } else {
                this.addTabInfo(customTablist, player.getUniqueId(), non_updatePlayer);
            }
            BungeeTabListPlusAPI.setCustomTabList(player, customTablist);
        }
    }

    public void playerDisconnect(ProxiedPlayer player){
        this.playerCustomTablistMap.remove(player);
        /* update */
        updateTabListInfo(player);
    }

    private void addTabInfo(CustomTablist customTablist, UUID uuid){
        this.globalInfo.getGlobal(customTablist);
        this.friendsInfo.getFriends(customTablist, uuid);
        this.partyInfo.getPartyInfo(customTablist, uuid);
    }
    private void addTabInfo(CustomTablist customTablist, UUID uuid, ProxiedPlayer player){
        this.globalInfo.getGlobal(customTablist, player);
        this.friendsInfo.getFriends(customTablist, uuid);
        this.partyInfo.getPartyInfo(customTablist, uuid);
    }
}
