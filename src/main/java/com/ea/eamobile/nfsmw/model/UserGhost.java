package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.BaseGhost;

public class UserGhost extends BaseGhost implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private int modeId;

    private String eventName;

    private long userId;

    private String carId;

    private int carColorIndex;
    
    private int eol;
    
    private float raceTime;
    
    private float averageSpeed;
    
    private int carScore;
    
    private long lastTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getCarColorIndex() {
        return carColorIndex;
    }

    public void setCarColorIndex(int carColorIndex) {
        this.carColorIndex = carColorIndex;
    }
    
    public int getEol() {
        return eol;
    }

    public void setEol(int eol) {
        this.eol = eol;
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

    public int getCarScore() {
        return carScore;
    }

    public void setCarScore(int carScore) {
        this.carScore = carScore;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }
}
