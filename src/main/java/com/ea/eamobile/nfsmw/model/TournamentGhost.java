package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.BaseGhost;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
public class TournamentGhost extends BaseGhost implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private int tournamentOnlineId;
    
    private String eventName;
    
    private float raceTime;
    
    private long userId;
    
    private String carId;
    
    private int carColorIndex;
    
    private int eol;
    
    private float averageSpeed;
    
    private int carScore;
    
    private int classId;
	
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }
    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
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
    public int getEol() {
        return eol;
    }
    public void setEol(int eol) {
        this.eol = eol;
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
    @Override
    public String toString() {
        return "ghost [onlineid=" + tournamentOnlineId + ", uid=" + userId + ", cid="
                + classId + ",time="+raceTime+"]";
    }
    
}
