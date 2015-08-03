package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed May 22 20:57:09 CST 2013
 * @since 1.0
 */
public class CareerGhostMatchRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private int modeId;
    
    private long ownerUserId;
    
    private long opponentUserId;
    
    private String carId;
    
    private int carColorIndex;
    
    private String firstConsumbleId;
    
    private String secondConsumbleId;
    
    private String thirdConsumbleId;
    
    private int time;
    
    private float raceTime;
    
	
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
    public long getOwnerUserId() {
        return ownerUserId;
    }
    public void setOwnerUserId(long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }
    public long getOpponentUserId() {
        return opponentUserId;
    }
    public void setOpponentUserId(long opponentUserId) {
        this.opponentUserId = opponentUserId;
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
    public float getRaceTime() {
        return raceTime;
    }
    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }
}
