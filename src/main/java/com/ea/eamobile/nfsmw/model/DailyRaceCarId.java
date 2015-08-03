package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public class DailyRaceCarId implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String carId;
    
    private int tier;
    
    private String carDisplayName;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getTier() {
        return tier;
    }
    public void setTier(int tier) {
        this.tier = tier;
    }
    public String getCarDisplayName() {
        return carDisplayName;
    }
    public void setCarDisplayName(String carDisplayName) {
        this.carDisplayName = carDisplayName;
    }
}
