package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

public class RaceMode implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private int star;

    private int value;

    private String goal;

    private int playerCount;

    private int gainStarNum;

    private int energy;

    private int finishRatioType; // 完成度配置类型

    private String trackId; // 关卡所属赛道

    private int type;
    
    private int rewardId;
    
    private int orderId;
    
    private int modeType;
    
    private int realTrackIndex;
    
    private int rankType;
    
    private String displayName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGainStarNum() {
        return gainStarNum;
    }

    public void setGainStarNum(int gainStarNum) {
        this.gainStarNum = gainStarNum;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getFinishRatioType() {
        return finishRatioType;
    }

    public void setFinishRatioType(int finishRatioType) {
        this.finishRatioType = finishRatioType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    
    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }
    
    public int getRealTrackIndex() {
        return realTrackIndex;
    }

    public void setRealTrackIndex(int realTrackIndex) {
        this.realTrackIndex = realTrackIndex;
    }
    
    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    @Override
    public String toString() {
        return "RaceMode:[id=" + id + ",name=" + name + ",star=" + star + ",value=" + value + ",gainStar="
                + gainStarNum + ",finishRatioType=" + finishRatioType + ",trackId=" + trackId + "]";
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
