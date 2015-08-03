package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
public class TournamentLeaderboardChangeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int tournamentOnlineId;

    private int groupId;

    private int classId;

    private String carId;

    private float raceTime;

    private float averageSpeed;

    private String secondConsumbleId;

    private String thirdConsumbleId;

    private String firstConsumbleId;

    private long userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }

    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

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

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getSecondConsumbleId() {
        return secondConsumbleId;
    }

    public void setSecondConsumbleId(String secondConsumbleId) {
        this.secondConsumbleId = secondConsumbleId;
    }

    public String getThirdConsumbleId() {
        return thirdConsumbleId;
    }

    public void setThirdConsumbleId(String thirdConsumbleId) {
        this.thirdConsumbleId = thirdConsumbleId;
    }

    public String getFirstConsumbleId() {
        return firstConsumbleId;
    }

    public void setFirstConsumbleId(String firstConsumbleId) {
        this.firstConsumbleId = firstConsumbleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
