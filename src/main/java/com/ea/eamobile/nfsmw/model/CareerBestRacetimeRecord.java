package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 05 15:58:43 CST 2012
 * @since 1.0
 */
public class CareerBestRacetimeRecord implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int modeType;

    private long userId;

    private float raceTime;

    private float averageSpeed;

    private User user;// 非ormapping字段 ，避免重复取db数据用

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public CareerBestRacetimeRecord clone() {

        try {
            return (CareerBestRacetimeRecord) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
