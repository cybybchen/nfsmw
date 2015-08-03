package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class RaceResultView implements Serializable {

    private static final long serialVersionUID = -5866368886833601189L;

    private int trackId;

    private int modeId;

    private long userId;

    private int rank;

    private long result; // time

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

}
