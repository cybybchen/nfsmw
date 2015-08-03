package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:56 CST 2012
 * @since 1.0
 */
public class Tournament implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String name;
    
    private String logo;
    
    private int type;
    
    private int levelLimit;
    
    private int tierLimit;
    
    private int modeType;
    
    private int scheduleType;
    
    private int lastTime;
    
    private int specialFlag;
    
    private int orderId;
    
    private int adId;
    
    private int feedId;
    
    private int raceNum;
    
    private String backgroundPictureId;
    
    private int noConsumble;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getLevelLimit() {
        return levelLimit;
    }
    public void setLevelLimit(int levelLimit) {
        this.levelLimit = levelLimit;
    }
    public int getTierLimit() {
        return tierLimit;
    }
    public void setTierLimit(int tierLimit) {
        this.tierLimit = tierLimit;
    }
    public int getModeType() {
        return modeType;
    }
    public void setModeType(int modeType) {
        this.modeType = modeType;
    }
    public int getScheduleType() {
        return scheduleType;
    }
    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }
    public int getLastTime() {
        return lastTime;
    }
    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }
    public int getSpecialFlag() {
        return specialFlag;
    }
    public void setSpecialFlag(int specialFlag) {
        this.specialFlag = specialFlag;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getAdId() {
        return adId;
    }
    public void setAdId(int adId) {
        this.adId = adId;
    }
    public int getFeedId() {
        return feedId;
    }
    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }
    public int getRaceNum() {
        return raceNum;
    }
    public void setRaceNum(int raceNum) {
        this.raceNum = raceNum;
    }
    public String getBackgroundPictureId() {
        return backgroundPictureId;
    }
    public void setBackgroundPictureId(String backgroundPictureId) {
        this.backgroundPictureId = backgroundPictureId;
    }
    public int getNoConsumble() {
        return noConsumble;
    }
    public void setNoConsumble(int noConsumble) {
        this.noConsumble = noConsumble;
    }
}
