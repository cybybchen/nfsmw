package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.AbstractLeaderboard;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 06 15:20:44 CST 2012
 * @since 1.0
 */
public class TournamentLeaderboard extends AbstractLeaderboard implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int tournamentOnlineId;

    private float raceTime; // TODO del

    private int classId;

    private long userId;

    private float averageSpeed; // TODO del

    private float result;

    private String userName;

    private int headIndex;

    private String headUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

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

    public float getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    /**
     * 此重写只为通过lb匹配ghost时减少list遍历用 (non-Javadoc)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TournamentLeaderboard)) {
            return false;
        }
        TournamentLeaderboard lb = (TournamentLeaderboard) obj;
        if (lb.getId() == this.getId()) {
            return true;
        }
        if (lb.getUserId() == this.getUserId() && lb.getTournamentOnlineId() == this.getTournamentOnlineId()
                && lb.getClassId() == this.getClassId()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result *= 37 + userId;
        result *= 37 + classId;
        result *= 37 + tournamentOnlineId;
        return result;
    }
}
