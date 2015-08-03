package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 25 14:27:47 CST 2013
 * @since 1.0
 */
public class CheatRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int modeId;
    
    private float raceTime;
    
    private String carId;
    
    private int isRaceStart;
    
    private String reason;
    
    private int gameMode;
    
	
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
    public float getRaceTime() {
        return raceTime;
    }
    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getIsRaceStart() {
        return isRaceStart;
    }
    public void setIsRaceStart(int isRaceStart) {
        this.isRaceStart = isRaceStart;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public int getGameMode() {
        return gameMode;
    }
    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}
