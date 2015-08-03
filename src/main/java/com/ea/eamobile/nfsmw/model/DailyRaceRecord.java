package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public class DailyRaceRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int modeIndex;
    
    private int carIndex;
    
    private long createTime;
    
    private int tier;
    
    private String carId;
    
    private String displayName;
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getModeIndex() {
        return modeIndex;
    }
    public void setModeIndex(int modeIndex) {
        this.modeIndex = modeIndex;
    }
    public int getCarIndex() {
        return carIndex;
    }
    public void setCarIndex(int carIndex) {
        this.carIndex = carIndex;
    }
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public int getTier() {
        return tier;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
    
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
}
