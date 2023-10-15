package org.kitsune.com.TabListManager;

import codecrafter47.bungeetablistplus.api.bungee.CustomTablist;
import codecrafter47.bungeetablistplus.api.bungee.Icon;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.kitsune.com.HiraethTabListManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalInfo {
    private final HiraethTabListManager plugin;
    private final Icon defaultIcon;
    public GlobalInfo(HiraethTabListManager plugin, Icon defaultIcon){
        this.plugin = plugin;
        this.defaultIcon = defaultIcon;
    }

    public void getGlobal(CustomTablist customTablist){
        int onlinePlayerCount = this.plugin.getProxy().getPlayers().size();
        customTablist.setSlot(0,0, this.defaultIcon, "Online Players : ".concat(String.valueOf(onlinePlayerCount)), 0);

        for(int i = 2; i <= 19; i++){
            customTablist.setSlot(0, i, this.defaultIcon, "", 0);
        }

        int showPlayerCount = Math.min(this.plugin.getProxy().getPlayers().size(), 18);
        System.out.println(this.plugin.getProxy().getPlayers());
        for(int i = showPlayerCount; i > 0; i--){
            customTablist.setSlot(0, i + 1, this.defaultIcon, new ArrayList<>(this.plugin.getProxy().getPlayers()).get(i - 1).getDisplayName(), 0);
        }
    }
    public void getGlobal(CustomTablist customTablist, ProxiedPlayer leavePlayer){
        int onlinePlayerCount = this.plugin.getProxy().getPlayers().size() - 1;
        customTablist.setSlot(0,0, this.defaultIcon, "Online Players : ".concat(String.valueOf(onlinePlayerCount)), 0);

        for(int i = 2; i <= 19; i++){
            customTablist.setSlot(0, i, this.defaultIcon, "", 0);
        }

        int showPlayerCount = Math.min(this.plugin.getProxy().getPlayers().size(), 18);
        System.out.println(this.plugin.getProxy().getPlayers());
        List<ProxiedPlayer> onlinePlayers = new ArrayList<>(this.plugin.getProxy().getPlayers());
        onlinePlayers.remove(leavePlayer);
        for(int i = showPlayerCount; i > 0; i--){
            customTablist.setSlot(0, i + 1, this.defaultIcon, onlinePlayers.get(i - 1).getDisplayName(), 0);
        }
    }
}
