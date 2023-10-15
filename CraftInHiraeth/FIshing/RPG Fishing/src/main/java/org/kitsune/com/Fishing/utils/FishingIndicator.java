package me.springbreezex.craftinhiraethcore.fishing.utils;

import org.bukkit.ChatColor;

public class FishingIndicator {
    private String rewardType;
    private Fish fish;
    private Item item;
    private Event event;
    private int pullTimes;
    private int pullTimesRemain;
    private int xp;
    private String title;
    private int greenStart;
    private int greenEnd;
    private int countDown;

    public FishingIndicator(String rewardType) {
        this.rewardType = rewardType;
    }

    public void generateStats() {
        switch (rewardType) {
            case "FISH":
                Fish fish = this.fish;
                this.pullTimesRemain = (int) ((Math.random() * fish.getPullTimes_max()) + fish.getPullTimes_min());
                this.xp = (int) ((Math.random() * (fish.getXp_max() - fish.getXp_min() + 1)) + fish.getXp_min());
                break;
            case "ITEM":
                Item item = this.item;
                this.pullTimesRemain = (int) ((Math.random() * item.getPullTimes_max()) + item.getPullTimes_min());
                this.xp = (int) ((Math.random() * (item.getXp_max() - item.getXp_min() + 1)) + item.getXp_min());
                break;
            case "EVENT":
                Event event = this.event;
                this.pullTimesRemain = (int) ((Math.random() * event.getPullTimes_max()) + event.getPullTimes_min());
                this.xp = (int) ((Math.random() * (event.getXp_max() - event.getXp_min())) + event.getXp_min());
                break;
        }
        this.pullTimes = pullTimesRemain;
    }

    public void pullSuccess() {
        this.pullTimesRemain -= 1;
        this.title = null;
    }

    public String getRewardType() {
        return this.rewardType;
    }

    public int getPullTimesRemain() {
        return this.pullTimesRemain;
    }

    public int getXp() {
        return this.xp;
    }

    public String generateTitle() {
        if (this.title != null) {
            this.title = this.title.substring(3);
        } else {
            String greenTitle = "";
            int difficult = 0;
            if (this.rewardType.equals("FISH")) {
                Fish fish = this.fish;
                difficult = (int) ((Math.random() * (fish.getDifficult_max() - fish.getDifficult_min() + 1)) + fish.getDifficult_min());
            }
            if (this.rewardType.equals("ITEM")) {
                Item item = this.item;
                difficult = (int) ((Math.random() * (item.getDifficult_max() - item.getDifficult_min() + 1)) + item.getDifficult_min());
            }
            if (this.rewardType.equals("EVENT")) {
                Event event = this.event;
                difficult = (int) ((Math.random() * (event.getDifficult_max() - event.getDifficult_min() + 1)) + event.getDifficult_min());
            }
            for (int count = difficult; count > 0; count--) {
                greenTitle = greenTitle.concat(ChatColor.GREEN + "|");
            }
            String redTitle = "";
            for (int count = 20 - difficult; count > 0; count--) {
                redTitle = redTitle.concat(ChatColor.RED + "|");
            }
            int greenStart = (int) (Math.random() * (20 - difficult) * 3);
            while (greenStart % 3 != 0) {
                greenStart++;
            }
            this.greenStart = greenStart;
            this.greenEnd = greenStart + (difficult * 3);
            this.title = redTitle.substring(0, greenStart).concat(greenTitle).concat(redTitle.substring(greenStart));
        }
        return this.title;
    }

    public boolean checkTitle() {
        return this.title != null;
    }

    public String getTitle() {
        return this.title;
    }

    public int getGreenStart() {
        return this.greenStart;
    }

    public int getGreenEnd() {
        return this.greenEnd;
    }

    public String generatePullTimesBar() {
        String bar = "";
        for (int count = this.pullTimes - this.pullTimesRemain; count > 0; count--) {
            bar = bar.concat(ChatColor.GREEN + "⦿");
        }
        for (int count = this.pullTimesRemain; count > 0; count--) {
            bar = bar.concat(ChatColor.GRAY + "⦿");
        }
        return bar;
    }

    public int getCountDown() {
        return this.countDown;
    }

    public void setCountDown(int time) {
        this.countDown = time;
    }

    public String generateYellowBar() {
        String title = "";
        for (int count = this.countDown; count > 0; count--) {
            title = title.concat(ChatColor.YELLOW + "|");
        }
        return title;
    }

    public Fish getFish() {
        return this.fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
