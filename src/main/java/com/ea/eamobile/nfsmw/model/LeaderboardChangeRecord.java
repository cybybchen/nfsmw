package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
public class LeaderboardChangeRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int modeId;
    
    private float raceTime;
    
    private float averageSpeed;
    
    private String carId;
    
    private String firstConsumbleId;
    
    private String secondConsumbleId;
    
    private String thirdConsumbleId;
    
    private int time;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public float getAverageSpeed() {
        return averageSpeed;
    }
    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getFirstConsumbleId() {
        return firstConsumbleId;
    }
    public void setFirstConsumbleId(String firstConsumbleId) {
        this.firstConsumbleId = firstConsumbleId;
    }
    public String getSecondConsumbleId() {
        return secondConsumbleId;
    }
    public void setSecondConsumbleId(String secondConsumbleId) {
        this.secondConsumbleId = secondConsumbleId;
    }
    public String getThirdConsumbleId() {
        return thirdConsumbleId;
    }
    public void setThirdConsumbleId(String thirdConsumbleId) {
        this.thirdConsumbleId = thirdConsumbleId;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
}
