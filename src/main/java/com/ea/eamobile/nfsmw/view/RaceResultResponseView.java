package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;
import java.util.List;

/**
 * 比赛结果处理后返回给前端的信息 主要是各种解锁
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class RaceResultResponseView implements Serializable {

    private static final long serialVersionUID = -8222531965073879820L;

    private long userId;

    private int currTrackId; // 当前比赛赛道

    private int currTrackFinishRatio; // 当前赛道完成度 100则完成

    private int currModeId;

    private int currModeFinishRatio;

    private int gainStar; // 获得星星数

    private List<Integer> unlockTracks; // 解锁赛道 可能size=0

    private List<Integer> unlockModes; // 解锁关卡 可能 size==0

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCurrTrackId() {
        return currTrackId;
    }

    public void setCurrTrackId(int currTrackId) {
        this.currTrackId = currTrackId;
    }

    public int getCurrTrackFinishRatio() {
        return currTrackFinishRatio;
    }

    public void setCurrTrackFinishRatio(int currTrackFinishRatio) {
        this.currTrackFinishRatio = currTrackFinishRatio;
    }

    public int getCurrModeId() {
        return currModeId;
    }

    public void setCurrModeId(int currModeId) {
        this.currModeId = currModeId;
    }

    public int getCurrModeFinishRatio() {
        return currModeFinishRatio;
    }

    public void setCurrModeFinishRatio(int currModeFinishRatio) {
        this.currModeFinishRatio = currModeFinishRatio;
    }

    public int getGainStar() {
        return gainStar;
    }

    public void setGainStar(int gainStar) {
        this.gainStar = gainStar;
    }

    public List<Integer> getUnlockTracks() {
        return unlockTracks;
    }

    public void setUnlockTracks(List<Integer> unlockTracks) {
        this.unlockTracks = unlockTracks;
    }

    public List<Integer> getUnlockModes() {
        return unlockModes;
    }

    public void setUnlockModes(List<Integer> unlockModes) {
        this.unlockModes = unlockModes;
    }

}
