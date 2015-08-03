package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:32 CST 2012
 * @since 1.0
 */
public class DailyRaceReward implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int rpLevel;
    
    private int duraNum;
    
    private int isFinish;
    
    private int rewardId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRpLevel() {
        return rpLevel;
    }
    public void setRpLevel(int rpLevel) {
        this.rpLevel = rpLevel;
    }
    public int getDuraNum() {
        return duraNum;
    }
    public void setDuraNum(int duraNum) {
        this.duraNum = duraNum;
    }
    public int getIsFinish() {
        return isFinish;
    }
    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
}
