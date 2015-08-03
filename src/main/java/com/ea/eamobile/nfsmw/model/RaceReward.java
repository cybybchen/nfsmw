package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 18 14:56:40 CST 2012
 * @since 1.0
 */
public class RaceReward implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int id;

    private int rpLevel;
    
    private int gameMode;
    
    private int raceType;
    
    private int position;
    
    private int rewardId;
    
    private int isFinish;
    
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
    public int getGameMode() {
        return gameMode;
    }
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
    public int getRaceType() {
        return raceType;
    }
    public void setRaceType(int raceType) {
        this.raceType = raceType;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
    public int getIsFinish() {
        return isFinish;
    }
    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
}
