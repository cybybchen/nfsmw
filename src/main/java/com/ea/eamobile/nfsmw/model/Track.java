package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

public class Track implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private int tier;

    private int star;

    private int unlockStarNum; // 解锁需要星星

    private int unlockTier; // 解锁需要的考级级别

    private int firstMode; // 默认解锁赛道后添加的关卡

    private int rewardId;
    
    private String carLimitDisplay; 
    
    private int completeStarNum;
    
    private int mostStarNum;
    
    private String displayString;
    
    public int getFirstMode() {
        return firstMode;
    }

    public void setFirstMode(int firstMode) {
        this.firstMode = firstMode;
    }

    public int getUnlockStarNum() {
        return unlockStarNum;
    }

    public void setUnlockStarNum(int unlockStarNum) {
        this.unlockStarNum = unlockStarNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnlockTier() {
        return unlockTier;
    }

    public void setUnlockTier(int unlockTier) {
        this.unlockTier = unlockTier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
    
    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }
    
    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    
    public String getCarLimitDisplay() {
        return carLimitDisplay;
    }

    public void setCarLimitDisplay(String carLimitDisplay) {
        this.carLimitDisplay = carLimitDisplay;
    }
    
    public int getCompleteStarNum() {
        return completeStarNum;
    }

    public void setCompleteStarNum(int completeStarNum) {
        this.completeStarNum = completeStarNum;
    }
    
    public int getMostStarNum() {
        return mostStarNum;
    }

    public void setMostStarNum(int mostStarNum) {
        this.mostStarNum = mostStarNum;
    }
    
    public String getDisplayString() {
        return displayString;
    }

    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
   
   @Override
   public String toString(){
       return "track:[id="+id+",name="+name+"]";
   }
}
