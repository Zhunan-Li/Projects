package me.springbreezex.craftinhiraethcore.fishing.utils;

import java.util.List;

public class Event {
    private String type;
    private String mob_name;
    private int difficult_min;
    private int difficult_max;
    private int pullTimes_min;
    private int pullTimes_max;
    private int xp_min;
    private int xp_max;
    private int chance_min;
    private int chance_max;
    private int level_min;
    private int level_max;
    private List<String> region;
    public Event(String type, String mob_name, int difficult_min, int difficult_max, int pullTimes_min, int pullTimes_max, int xp_min, int xp_max, int chance_min, int chance_max, int level_min, int level_max,List<String> region){
        this.type = type;
        this.mob_name = mob_name;
        this.difficult_min = difficult_min;
        this.difficult_max = difficult_max;
        this.pullTimes_min = pullTimes_min;
        this.pullTimes_max = pullTimes_max;
        this.xp_min = xp_min;
        this.xp_max = xp_max;
        this.chance_min = chance_min;
        this.chance_max = chance_max;
        this.level_min = level_min;
        this.level_max = level_max;
        this.region = region;
    }
    public String getType(){
        return this.type;
    }
    public String getMob_name(){
        return this.mob_name;
    }
    public int getDifficult_min(){
        return this.difficult_min;
    }
    public int getDifficult_max(){
        return this.difficult_max;
    }
    public int getPullTimes_min(){
        return this.pullTimes_min;
    }
    public int getPullTimes_max(){
        return this.pullTimes_max;
    }
    public int getXp_min(){
        return this.xp_min;
    }
    public int getXp_max(){
        return this.xp_max;
    }
    public int getChance_min(){
        return this.chance_min;
    }
    public int getChance_max(){
        return this.chance_max;
    }
    public int getLevel_min(){
        return this.level_min;
    }
    public int getLevel_max(){
        return this.level_max;
    }
    public List<String> getRegion(){
        return this.region;
    }
}
