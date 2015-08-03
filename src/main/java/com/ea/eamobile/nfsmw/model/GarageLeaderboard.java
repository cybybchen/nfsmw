package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 16:27:05 CST 2013
 * @since 1.0
 */
public class GarageLeaderboard implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private long userId;

    private String name;

    @JsonIgnore
    private String weiboName;

    private String headUrl;

    private int carNum;

    private int carTotalScore;

    private int rank;
    
    private int rpLevel;
    
    private int rpNum;
    
    private int mwNum;
    
    private int tier;
    
    private String zone;
    
    private double latitude;
    
    private double longitude;

    public int getRpLevel() {
        return rpLevel;
    }

    public void setRpLevel(int rpLevel) {
        this.rpLevel = rpLevel;
    }

    public int getRpNum() {
        return rpNum;
    }

    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
    }

    public int getMwNum() {
        return mwNum;
    }

    public void setMwNum(int mwNum) {
        this.mwNum = mwNum;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWeiboName() {
        return weiboName;
    }

    public void setWeiboName(String weiboName) {
        this.weiboName = weiboName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "glb [rank=" + rank + ",name=" + name + ",userId=" + userId + ", carNum=" + carNum + ", score="
                + carTotalScore + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getCarTotalScore() {
        return carTotalScore;
    }

    public void setCarTotalScore(int carTotalScore) {
        this.carTotalScore = carTotalScore;
    }
}
