package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.AbstractLeaderboard;

public class Leaderboard extends AbstractLeaderboard implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int modeType;

    private long userId;

    private String userName;

    private int headIndex;

    private String headUrl;

    private float result;

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

}
