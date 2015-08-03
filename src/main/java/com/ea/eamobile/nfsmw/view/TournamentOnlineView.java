package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class TournamentOnlineView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int tournamentId;

    private String startTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String endTime;

    // private long startTime;
    //
    // private long endTime;

    private int isFinish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
    

    // public long getStartTime() {
    // return startTime;
    // }
    //
    // public void setStartTime(long startTime) {
    // this.startTime = startTime;
    // }
    // public long getEndTime() {
    // return endTime;
    // }
    //
    // public void setEndTime(long endTime) {
    // this.endTime = endTime;
    // }
    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
}
