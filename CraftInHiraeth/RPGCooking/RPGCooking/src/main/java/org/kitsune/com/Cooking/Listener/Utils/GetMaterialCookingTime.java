package org.kitsune.com.Cooking.Listener.Utils;

public class GetMaterialCookingTime {
    public static int getMaterialCookingTime(String tier) {
        switch (tier) {
            case "E":
            case "D":
                return 5;
            case "C":
            case "B":
            case "A":
                return 10;
            case "S":
            case "SS":
                return 15;
        }
        return 0;
    }
}
