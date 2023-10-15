package org.kitsune.com.TabListManager;

import codecrafter47.bungeetablistplus.api.bungee.CustomTablist;
import codecrafter47.bungeetablistplus.api.bungee.Icon;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

public class PartyInfo {
    private Icon defaultIcon;
    private PartyManager partyManager;
    public PartyInfo(Icon icon){
        this.defaultIcon = icon;
        this.partyManager = PartyManager.getInstance();
    }

    public void getPartyInfo(CustomTablist customTablist, UUID uuid){
        for(int i = 40; i <= 59; i++){
            customTablist.setSlot(0, i, this.defaultIcon, "", 0);
        }

        PlayerParty playerParty = this.partyManager.getParty(uuid);
        if(playerParty != null){
            /* if the player joined a party */
            customTablist.setSlot(0, 40, this.defaultIcon,
                    ChatColor.translateAlternateColorCodes('&',"&cParty Leader : "), 0);
            customTablist.setSlot(0, 41, this.defaultIcon, playerParty.getLeader().getDisplayName(), 0);
            customTablist.setSlot(0, 42, this.defaultIcon,
                    ChatColor.translateAlternateColorCodes('&', "&aParty Members : "),0);
            int partyPlayersIndex = 0;
            for(OnlinePAFPlayer onlinePAFPlayer : playerParty.getPlayers()){
                customTablist.setSlot(0, 43 + partyPlayersIndex, this.defaultIcon, onlinePAFPlayer.getDisplayName(), 0);
                partyPlayersIndex++;
            }
        }else{
            customTablist.setSlot(0, 40, this.defaultIcon, "Use /party invite", 0);
            customTablist.setSlot(0, 41, this.defaultIcon, "to create a party.", 0);
        }
    }
}
