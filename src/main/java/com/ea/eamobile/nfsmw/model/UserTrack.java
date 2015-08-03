package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

public class UserTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private long userId;

    private String trackId;

    private int modeId;

    private int value; // 当前赛道当前关卡的完成度

    private int isNew; // 是否是新解锁赛道 跑过比赛要设置false

    private int isFinish; // 是否通关卡，即是否给奖励

    @Override
    public String toString() {
        return "UserTrack:[id=" + id + ",userid=" + userId + ",trackid=" + trackId + ",modeId=" + modeId + ",value="
                + value + ",isnew=" + isNew + ",isfinish=" + isFinish + "]";
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
