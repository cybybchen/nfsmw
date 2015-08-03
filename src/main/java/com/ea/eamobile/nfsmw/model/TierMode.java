package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Sep 27 15:36:13 CST 2012
 * @since 1.0
 */
public class TierMode implements Serializable{

    private static final long serialVersionUID = 1L;

    private int tier;
    
    private int modeId;
    
    private int unlockStarNum;
    
    private int energy;
    
    private String eventName;
    
    private int rewardId;
	
    private String displayString;
    
    public int getTier() {
        return tier;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
    public int getUnlockStarNum() {
        return unlockStarNum;
    }
    public void setUnlockStarNum(int unlockStarNum) {
        this.unlockStarNum = unlockStarNum;
    }
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
    
    public String getDisplayString() {
        return displayString;
    }
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
}
