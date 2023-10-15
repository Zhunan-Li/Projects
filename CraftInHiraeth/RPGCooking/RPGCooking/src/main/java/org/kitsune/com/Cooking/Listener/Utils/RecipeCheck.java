package org.kitsune.com.Cooking.Listener.Utils;

import me.springbreezex.craftinhiraethcore.consume.HiraethConsume;
import me.springbreezex.craftinhiraethcore.consume.food.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kitsune.com.Cooking.Configs.Configs;
import org.kitsune.com.Cooking.Configs.RecipeConfigs;
import org.kitsune.com.Cooking.Database.Database;
import org.kitsune.com.Cooking.Database.DatabaseCore;
import org.kitsune.com.Cooking.Objects.Recipe;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecipeCheck {
    public static ItemStack generateItem(Player p, List<String> userMaterialList) {
        Recipe resultRecipe = null;
        Collections.sort(userMaterialList);
        for (Recipe recipe : RecipeConfigs.recipeMap.values()) {
            Collections.sort(recipe.getRequireMaterial());
            if (recipe.getRequireMaterial().equals(userMaterialList)) {
                resultRecipe = recipe;
            }
        }
        if (resultRecipe != null) {
            /* log to database */
            Map<Integer, Boolean> playerData = DatabaseCore.playerData.get(p);
            if (!playerData.get(resultRecipe.getId())) {
                /* found new recipe */
                Database.achievedRecipe(p, resultRecipe.getId());
                String message = Configs.configuration.getString("Messages.UnlockNewRecipe").replace("{recipeName}", resultRecipe.getName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
        if (resultRecipe == null) {
            /* none recipe */
            resultRecipe = RecipeConfigs.recipeMap.get("none");
        }
        assert resultRecipe != null;

        int countUse = resultRecipe.getCountUse();
        int time = 0;
        int EnduranceValue = 0;
        int ConstitutionValue = 0;
        int StrengthValue = 0;
        int Intelligence = 0;
        int Dexterity = 0;
        int Stamina = 0;
        int Dodge_rate = 0;
        int Critical_rate = 0;
        int Exp_multiplier = 0;
        int Proficiency_multiplier = 0;
        int Fire_element_multiplier = 0;
        int Water_element_multiplier = 0;
        int Wood_element_multiplier = 0;
        int Light_element_multiplier = 0;
        int Dark_element_multiplier = 0;
        int CountBuff = 0;
        int ReverseEffect = 0;
        int TimeBuff = 0;
        int ValueBuff = 0;
        int money = 0;
        for (String string : userMaterialList) {
            FoodItem foodItem = HiraethConsume.getFoodItems().get(string);
            time += foodItem.getTime(); //時效
            EnduranceValue += foodItem.getEnduranceValue();
            ConstitutionValue += foodItem.getConstitutionValue();
            StrengthValue += foodItem.getStrengthValue();
            Intelligence += foodItem.getIntelligenceValue();
            Dexterity += foodItem.getDexterityValue();
            Stamina += foodItem.getStaminaValue();
            Dodge_rate += foodItem.getDodgeRateValue();
            Critical_rate += foodItem.getCriticalRateValue();
            Exp_multiplier += foodItem.getExpMultiplierValue();
            Proficiency_multiplier += foodItem.getProficiencyMultiplierValue();
            Fire_element_multiplier += foodItem.getFireElementMultiplierValue();
            Water_element_multiplier += foodItem.getWaterElementMultiplierValue();
            Wood_element_multiplier += foodItem.getWoodElementMultiplierValue();
            Light_element_multiplier += foodItem.getLightElementMultiplierValue();
            Dark_element_multiplier += foodItem.getDarkElementMultiplierValue();
            CountBuff += foodItem.getCountBuff();
            ReverseEffect += foodItem.getReverseEffect();
            TimeBuff += foodItem.getTimeBuff();
            ValueBuff += foodItem.getValueBuff();
            money += foodItem.getMoney();
        }
        if (ReverseEffect % 2 == 1) {
            EnduranceValue *= -1;
            ConstitutionValue *= -1;
            StrengthValue *= -1;
            Intelligence *= -1;
            Dexterity *= -1;
            Stamina *= -1;
            Dodge_rate *= -1;
            Critical_rate *= -1;
            Exp_multiplier *= -1;
            Proficiency_multiplier *= -1;
            Fire_element_multiplier *= -1;
            Water_element_multiplier *= -1;
            Wood_element_multiplier *= -1;
            Light_element_multiplier *= -1;
            Dark_element_multiplier *= -1;
            CountBuff *= -1;
        }
        countUse += CountBuff;
        if (countUse <= 0) {
            countUse = 1;
        }
        for (int i = 0; i < TimeBuff; i++) {
            time *= 1.25;
        }
        for (int i = 0; i > TimeBuff; i--) {
            time *= 0.75;
        }
        for (int i = 0; i < ValueBuff; i++) {
            EnduranceValue *= 1.25;
            ConstitutionValue *= 1.25;
            StrengthValue *= 1.25;
            Intelligence *= 1.25;
            Dexterity *= 1.25;
            Stamina *= 1.25;
            Dodge_rate *= 1.25;
            Critical_rate *= 1.25;
            Exp_multiplier *= 1.25;
            Proficiency_multiplier *= 1.25;
        }
        for (int i = 0; i > TimeBuff; i--) {
            EnduranceValue *= 0.75;
            ConstitutionValue *= 0.75;
            StrengthValue *= 0.75;
            Intelligence *= 0.75;
            Dexterity *= 0.75;
            Stamina *= 0.75;
            Dodge_rate *= 0.75;
            Critical_rate *= 0.75;
            Exp_multiplier *= 0.75;
            Proficiency_multiplier *= 0.75;
        }
        EnduranceValue *= resultRecipe.getMultiplier();
        ConstitutionValue *= resultRecipe.getMultiplier();
        StrengthValue *= resultRecipe.getMultiplier();
        Intelligence *= resultRecipe.getMultiplier();
        Dexterity *= resultRecipe.getMultiplier();
        Stamina *= resultRecipe.getMultiplier();
        Dodge_rate *= resultRecipe.getMultiplier();
        Critical_rate *= resultRecipe.getMultiplier();
        Exp_multiplier *= resultRecipe.getMultiplier();
        Proficiency_multiplier *= resultRecipe.getMultiplier();

        FoodItem foodItem = new FoodItem(resultRecipe.getName(), ChatColor.translateAlternateColorCodes('&', resultRecipe.getName()),
                resultRecipe.getTier(), "料理", resultRecipe.getMaterial(), resultRecipe.getCustomModelData(), resultRecipe.getLore(), countUse, time
                , EnduranceValue, ConstitutionValue, StrengthValue, Intelligence, Dexterity, Stamina, Dodge_rate, Critical_rate, Exp_multiplier, Proficiency_multiplier,
                Fire_element_multiplier, Water_element_multiplier, Wood_element_multiplier, Light_element_multiplier, Dark_element_multiplier, 0, 0,
                0, 0, money, "result");

        return foodItem.getItem();
    }
}
