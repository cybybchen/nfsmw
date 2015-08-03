package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public class TournamentUser implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private int tournamentOnlineId;

    private int groupId;

    private long userId;

    private String name;

    private float result;

    private int lefttimes;

    private int classId;

    private int isGetReward;

    private float averageSpeed;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public int getLefttimes() {
        return lefttimes;
    }

    public void setLefttimes(int lefttimes) {
        this.lefttimes = lefttimes;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getIsGetReward() {
        return isGetReward;
    }

    public void setIsGetReward(int isGetReward) {
        this.isGetReward = isGetReward;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public TournamentUser clone() {

        try {
            return (TournamentUser) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return this;
    }
}
