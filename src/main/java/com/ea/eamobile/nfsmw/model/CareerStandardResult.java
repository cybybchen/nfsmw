package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:31:36 CST 2013
 * @since 1.0
 */
public class CareerStandardResult implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int modeType;
    
    private long raceTime;
    
    private long averageSpeed;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getModeType() {
        return modeType;
    }
    public void setModeType(int modeType) {
        this.modeType = modeType;
    }
    public long getRaceTime() {
        return raceTime;
    }
    public void setRaceTime(long raceTime) {
        this.raceTime = raceTime;
    }
    public long getAverageSpeed() {
        return averageSpeed;
    }
    public void setAverageSpeed(long averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
