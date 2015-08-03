package com.ea.eamobile.nfsmw.view;

public class LeaderboardUserView {

    private int modeId;

    private long userId;

    private double result;

    private String headUrl;

    private String name;
    
    private String rankType;

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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

}
