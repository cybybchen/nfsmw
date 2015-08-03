package com.ea.eamobile.nfsmw.view;

public abstract class AbstractLeaderboard {
    
    private float result;
    
    private long userId;

    private String userName;

    private int headIndex;

    private String headUrl;

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
