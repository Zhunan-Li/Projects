package org.kitsune.com.Cooking.Listener.Utils;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.kitsune.com.Cooking.Configs.Configs;
import org.kitsune.com.Cooking.Objects.CookingIndicator;

import java.util.*;

public class SendIndicator {
    public static Map<UUID, List<EntityArmorStand>> uuidArmorStandsMap = new HashMap<>();
    public static List<UUID> quitPlayerList = new ArrayList<>();
    private double topLineHigh;
    private double secondLineHigh;
    private double thirdLineHigh;
    private IChatBaseComponent tutorialMessage1;
    private IChatBaseComponent tutorialMessage2;
    private String timeMessage;

    public SendIndicator() {
        this.topLineHigh = Configs.configuration.getDouble("HologramSettings.TopLineHigh");
        this.secondLineHigh = Configs.configuration.getDouble("HologramSettings.SecondLineHigh");
        this.thirdLineHigh = Configs.configuration.getDouble("HologramSettings.ThirdLineHigh");
        this.tutorialMessage1 = CraftChatMessage.fromStringOrNull(ChatColor.translateAlternateColorCodes('&', Configs.configuration.getString("HologramSettings.TutorialMessage1")));
        this.tutorialMessage2 = CraftChatMessage.fromStringOrNull(ChatColor.translateAlternateColorCodes('&', Configs.configuration.getString("HologramSettings.TutorialMessage2")));
        this.timeMessage = ChatColor.translateAlternateColorCodes('&', Configs.configuration.getString("HologramSettings.TimeMessage"));
    }

    /* line 1 : tutorialMessage1 /  x(disappear)
     *  line 2 : tutorialMessage2 /  player's material
     *  line 3 :                 time
     * */
    public void sendIndicator(CookingIndicator cookingIndicator, Player p) {
        UUID playerUUID = p.getUniqueId();
        if (uuidArmorStandsMap.containsKey(p.getUniqueId())) {
            if (quitPlayerList.contains(p.getUniqueId())) {
                /* 玩家重登 */
                sendSummonArmorStandPacket(p);
                quitPlayerList.remove(p.getUniqueId());
            }
            /* update */

            /* check if the user material list is empty
             * 如果是空的 -> 一、二行顯示回教學並更新時間
             * 不是空的   -> 第一行煮熟進度條 第二行改為顯示 user material list 並更新時間
             * */
            List<EntityArmorStand> entityArmorStandList = uuidArmorStandsMap.get(playerUUID);
            if (cookingIndicator.getUserMaterials().isEmpty()) {
                entityArmorStandList.get(0).a(this.tutorialMessage1);
                entityArmorStandList.get(1).a(this.tutorialMessage2);
            } else {
                /* set the top line armor stand text to invisible */
                double percent = 1.0 * cookingIndicator.getCookingTime() / cookingIndicator.getNeedCookingTime();
                entityArmorStandList.get(0).a(CraftChatMessage.fromStringOrNull(ChatColor.translateAlternateColorCodes('&', getBar(percent))));
                /* set the second line armor stand text to material list */
                IChatBaseComponent materialListText = CraftChatMessage.fromStringOrNull(generateMaterialListString(cookingIndicator));
                entityArmorStandList.get(1).a(materialListText);
            }
            /* update time(third line) */
            IChatBaseComponent time = CraftChatMessage.fromStringOrNull(this.timeMessage.replace("{time}", String.valueOf(cookingIndicator.getFuelTime())));
            entityArmorStandList.get(2).a(time);

        } else {
            /* initialize(summon armor stand) */
            List<EntityArmorStand> armorStandList = initializeArmorStand(cookingIndicator);
            uuidArmorStandsMap.put(playerUUID, armorStandList);
            sendSummonArmorStandPacket(p);
        }
        sendArmorStandDataPacket(p);
    }

    private List<EntityArmorStand> initializeArmorStand(CookingIndicator cookingIndicator) {
        List<EntityArmorStand> armorStandsList = new ArrayList<>();
        Location usingCauldronLocation = cookingIndicator.getUsingCauldronLocation();

        EntityArmorStand topArmorStand = new EntityArmorStand(((CraftWorld) usingCauldronLocation.getWorld()).getHandle(), usingCauldronLocation.getBlockX(), usingCauldronLocation.getBlockY(), usingCauldronLocation.getBlockZ());
        topArmorStand.a(usingCauldronLocation.getBlockX() + 0.5, usingCauldronLocation.getBlockY() + this.topLineHigh, usingCauldronLocation.getBlockZ() + 0.5, 0, 0);
        topArmorStand.a(this.tutorialMessage1);
        armorStandsList.add(topArmorStand);

        EntityArmorStand secondArmorStand = new EntityArmorStand(((CraftWorld) usingCauldronLocation.getWorld()).getHandle(), usingCauldronLocation.getBlockX(), usingCauldronLocation.getBlockY(), usingCauldronLocation.getBlockZ());
        secondArmorStand.a(usingCauldronLocation.getBlockX() + 0.5, usingCauldronLocation.getBlockY() + this.secondLineHigh, usingCauldronLocation.getBlockZ() + 0.5, 0, 0);
        secondArmorStand.a(this.tutorialMessage2);
        armorStandsList.add(secondArmorStand);

        EntityArmorStand thirdArmorStand = new EntityArmorStand(((CraftWorld) usingCauldronLocation.getWorld()).getHandle(), usingCauldronLocation.getBlockX(), usingCauldronLocation.getBlockY(), usingCauldronLocation.getBlockZ());
        thirdArmorStand.a(usingCauldronLocation.getBlockX() + 0.5, usingCauldronLocation.getBlockY() + this.thirdLineHigh, usingCauldronLocation.getBlockZ() + 0.5, 0, 0);
        IChatBaseComponent thirdLineText = CraftChatMessage.fromStringOrNull(this.timeMessage.replace("{time}", String.valueOf(cookingIndicator.getFuelTime())));
        thirdArmorStand.a(thirdLineText);
        armorStandsList.add(thirdArmorStand);

        for (EntityArmorStand entityArmorStand : armorStandsList) {
            entityArmorStand.n(true);
            entityArmorStand.a(true);
            entityArmorStand.e(true);
            entityArmorStand.j(true);
            entityArmorStand.t(true);
        }

        return armorStandsList;
    }

    private void sendSummonArmorStandPacket(Player p) {
        List<EntityArmorStand> armorStandList = this.uuidArmorStandsMap.get(p.getUniqueId());
        for (EntityArmorStand entityArmorStand : armorStandList) {
            PacketPlayOutSpawnEntity packetPlayOutEntity = new PacketPlayOutSpawnEntity(entityArmorStand, 1);
            ((CraftPlayer) p).getHandle().b.a(packetPlayOutEntity);
        }
    }

    private void sendArmorStandDataPacket(Player p) {
        List<EntityArmorStand> armorStandList = this.uuidArmorStandsMap.get(p.getUniqueId());
        for (EntityArmorStand entityArmorStand : armorStandList) {
            PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(entityArmorStand.ae(), entityArmorStand.ai(), true);
            ((CraftPlayer) p).getHandle().b.a(packetPlayOutEntityMetadata);
        }
    }

    public void destroyArmorStandPacket(Player p) {
        if (uuidArmorStandsMap.containsKey(p.getUniqueId())) {
            for (EntityArmorStand entityArmorStand : uuidArmorStandsMap.get(p.getUniqueId())) {
                PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.ae());
                ((CraftPlayer) p).getHandle().b.a(packetPlayOutEntityDestroy);
            }
            uuidArmorStandsMap.remove(p.getUniqueId());
        }
    }

    private String generateMaterialListString(CookingIndicator cookingIndicator) {
        String string = "";
        int materialListSize = cookingIndicator.getUserMaterials().size();
        int stringMaterialShowedCount = materialListSize;
        for (String itemName : cookingIndicator.getUserMaterials()) {
            FoodItem foodItem = HiraethConsume.getFoodItems().get(itemName);
            if (stringMaterialShowedCount != 1) {
                string = string.concat(foodItem.getDisplayName()).concat("&r-");
                stringMaterialShowedCount--;
            } else {
                string = string.concat(foodItem.getDisplayName());
            }
        }
        for (int count = 5 - materialListSize; count > 0; count--) {
            string = string.concat("&r-_");
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private String getBar(double percent) {
        String returnString = "&f烹飪中: &7[&a";
        int stage = (int) (percent * 5);
        for (int i = 0; i < stage; i++) {
            returnString = returnString.concat("■");
        }
        returnString = returnString.concat("&8");
        for (int i = 0; i < 5 - stage; i++) {
            returnString = returnString.concat("■");
        }
        returnString = returnString.concat("&7]");
        return returnString;
    }
}
