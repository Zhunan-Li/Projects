package org.kitsune.com.TabListManager;

import codecrafter47.bungeetablistplus.api.bungee.CustomTablist;
import codecrafter47.bungeetablistplus.api.bungee.Icon;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendsInfo {
    private Icon defaultIcon;
    private PAFPlayerManager pafPlayerManager;

    public FriendsInfo(Icon icon) {
        this.defaultIcon = icon;
        this.pafPlayerManager = PAFPlayerManager.getInstance();
    }

    public void getFriends(CustomTablist customTablist, UUID uuid) {
        List<PAFPlayer> onlineFriends = new ArrayList<>();
        List<PAFPlayer> offlineFriends = new ArrayList<>();
        for (PAFPlayer pafPlayer : this.pafPlayerManager.getPlayer(uuid).getFriends()) {
            if (pafPlayer.isOnline()) {
                onlineFriends.add(pafPlayer);
            } else {
                offlineFriends.add(pafPlayer);
            }
        }

        customTablist.setSlot(0, 20, this.defaultIcon, "Online Friends : ".concat(String.valueOf(onlineFriends.size())), 0);
        customTablist.setSlot(0, 21, this.defaultIcon, "Total Friends : ".concat(String.valueOf(onlineFriends.size() + offlineFriends.size())), 0);

        for (int i = 22; i <= 39; i++) {
            customTablist.setSlot(0, i, this.defaultIcon, "", 0);
        }
        for (int i = 3; i <= 39; i++) {
            int slotIndex = i + 19;
            int listIndex = i - 3;
            try {
                customTablist.setSlot(0, slotIndex, this.defaultIcon,
                        ChatColor.translateAlternateColorCodes('&', "&a".concat(onlineFriends.get(listIndex).getDisplayName())), 0);
            } catch (IndexOutOfBoundsException exception) {
                try {
                    int _listIndex = listIndex - onlineFriends.size();
                    customTablist.setSlot(0, slotIndex, this.defaultIcon,
                            ChatColor.translateAlternateColorCodes('&', "&7".concat(offlineFriends.get(_listIndex).getDisplayName())), 0);
                } catch (IndexOutOfBoundsException exception1) {
                    customTablist.setSlot(0, slotIndex, this.defaultIcon, "", 0);
                }
            }
        }
    }
}
