package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Apr 23 17:38:22 CST 2013
 * @since 1.0
 */
public class UserFreeFragRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private String carId;
    
    private int level;
    
    private int lastUseTime;
    
    private int leftTimes;
    
	
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
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLastUseTime() {
        return lastUseTime;
    }
    public void setLastUseTime(int lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
    public int getLeftTimes() {
        return leftTimes;
    }
    public void setLeftTimes(int leftTimes) {
        this.leftTimes = leftTimes;
    }
}
