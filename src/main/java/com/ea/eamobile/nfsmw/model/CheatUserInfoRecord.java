package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue May 28 17:32:10 CST 2013
 * @since 1.0
 */
public class CheatUserInfoRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int cheatType;
    
    private int version;
    
    private String carName;
    
    private int middleGearSpeed;
    
    private int topGearSpeed;
    
    private String md5;
    
    private String userSelectCarId;
    
	
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
    public int getCheatType() {
        return cheatType;
    }
    public void setCheatType(int cheatType) {
        this.cheatType = cheatType;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public String getCarName() {
        return carName;
    }
    public void setCarName(String carName) {
        this.carName = carName;
    }
    public int getMiddleGearSpeed() {
        return middleGearSpeed;
    }
    public void setMiddleGearSpeed(int middleGearSpeed) {
        this.middleGearSpeed = middleGearSpeed;
    }
    public int getTopGearSpeed() {
        return topGearSpeed;
    }
    public void setTopGearSpeed(int topGearSpeed) {
        this.topGearSpeed = topGearSpeed;
    }
    public String getMd5() {
        return md5;
    }
    public void setMd5(String md5) {
        this.md5 = md5;
    }
    public String getUserSelectCarId() {
        return userSelectCarId;
    }
    public void setUserSelectCarId(String userSelectCarId) {
        this.userSelectCarId = userSelectCarId;
    }
}
