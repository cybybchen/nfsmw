package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri May 24 17:51:48 CST 2013
 * @since 1.0
 */
public class ModeStandardResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int modeId;

    private String carId;

    private float raceTime;

    private float averageSpeed;

    private float nitrousBurnAddTime;

    private float powerPackAddTime;
    
    private float nitrousBurnAddSpeed;

    private float powerPackAddSpeed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public float getNitrousBurnAddTime() {
        return nitrousBurnAddTime;
    }

    public void setNitrousBurnAddTime(float nitrousBurnAddTime) {
        this.nitrousBurnAddTime = nitrousBurnAddTime;
    }

    public float getPowerPackAddTime() {
        return powerPackAddTime;
    }

    public void setPowerPackAddTime(float powerPackAddTime) {
        this.powerPackAddTime = powerPackAddTime;
    }

    public float getNitrousBurnAddSpeed() {
        return nitrousBurnAddSpeed;
    }

    public void setNitrousBurnAddSpeed(float nitrousBurnAddSpeed) {
        this.nitrousBurnAddSpeed = nitrousBurnAddSpeed;
    }

    public float getPowerPackAddSpeed() {
        return powerPackAddSpeed;
    }

    public void setPowerPackAddSpeed(float powerPackAddSpeed) {
        this.powerPackAddSpeed = powerPackAddSpeed;
    }
}
