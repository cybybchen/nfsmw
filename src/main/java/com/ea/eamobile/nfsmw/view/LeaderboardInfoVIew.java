package com.ea.eamobile.nfsmw.view;

public class LeaderboardInfoVIew {

    private int modeId;

    private long userId;

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

    public float getStandardRaceTime() {
        return standardRaceTime;
    }

    public void setStandardRaceTime(float standardRaceTime) {
        this.standardRaceTime = standardRaceTime;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(float topSpeed) {
        this.topSpeed = topSpeed;
    }

    public float getRegularTopSpeed() {
        return regularTopSpeed;
    }

    public void setRegularTopSpeed(float regularTopSpeed) {
        this.regularTopSpeed = regularTopSpeed;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public float getRegularAverageSpeed() {
        return regularAverageSpeed;
    }

    public void setRegularAverageSpeed(float regularAverageSpeed) {
        this.regularAverageSpeed = regularAverageSpeed;
    }

    public String getFirstConsumble() {
        return firstConsumble;
    }

    public void setFirstConsumble(String firstConsumble) {
        this.firstConsumble = firstConsumble;
    }

    public String getSecondConsumble() {
        return secondConsumble;
    }

    public void setSecondConsumble(String secondConsumble) {
        this.secondConsumble = secondConsumble;
    }

    public String getThirdConsumble() {
        return thirdConsumble;
    }

    public void setThirdConsumble(String thirdConsumble) {
        this.thirdConsumble = thirdConsumble;
    }

    private String userName;

    private String carId;

    private float raceTime;

    private float standardRaceTime;

    private float topSpeed;

    private float regularTopSpeed;

    private float averageSpeed;

    private float regularAverageSpeed;

    private String firstConsumble;

    private String secondConsumble;

    private String thirdConsumble;

    private int isBan;

    private int isRecord;

    private int isNoGhost;

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsBan() {
        return isBan;
    }

    public void setIsBan(int isBan) {
        this.isBan = isBan;
    }

    public int getIsRecord() {
        return isRecord;
    }

    public void setIsRecord(int isRecord) {
        this.isRecord = isRecord;
    }

    public int getIsNoGhost() {
        return isNoGhost;
    }

    public void setIsNoGhost(int isNoGhost) {
        this.isNoGhost = isNoGhost;
    }

}
