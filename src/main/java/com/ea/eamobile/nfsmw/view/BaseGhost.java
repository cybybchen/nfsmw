package com.ea.eamobile.nfsmw.view;

public abstract class BaseGhost {

    private long id;

    private int modeId;
    
    private int tournamentOnlineId;


    private String eventName;

    private long userId;

    private String carId;

    private int carColorIndex;
    
    private int eol;
    
    private float raceTime;
    
    private float averageSpeed;
    
    private int carScore;

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }

    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public int getEol() {
        return eol;
    }

    public void setEol(int eol) {
        this.eol = eol;
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

    public int getCarScore() {
        return carScore;
    }

    public void setCarScore(int carScore) {
        this.carScore = carScore;
    }
    
}
