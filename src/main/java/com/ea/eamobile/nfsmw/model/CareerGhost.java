package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.BaseGhost;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 20 14:47:25 CST 2013
 * @since 1.0
 */
public class CareerGhost extends BaseGhost implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private int modeType;

    private String eventName;

    private float raceTime;

    private long userId;

    private String carId;

    private int carColorIndex;

    private float averageSpeed;

    private int carScore;

    private int isGoldCar;

    private int isHasConsumble;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public float getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
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

    public int getCarColorIndex() {
        return carColorIndex;
    }

    public void setCarColorIndex(int carColorIndex) {
        this.carColorIndex = carColorIndex;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getCarScore() {
        return carScore;
    }

    public void setCarScore(int carScore) {
        this.carScore = carScore;
    }

    public int getIsGoldCar() {
        return isGoldCar;
    }

    public void setIsGoldCar(int isGoldCar) {
        this.isGoldCar = isGoldCar;
    }

    public int getIsHasConsumble() {
        return isHasConsumble;
    }

    public void setIsHasConsumble(int isHasConsumble) {
        this.isHasConsumble = isHasConsumble;
    }
}
