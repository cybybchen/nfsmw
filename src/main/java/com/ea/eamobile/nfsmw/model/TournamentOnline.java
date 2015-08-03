package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:56 CST 2012
 * @since 1.0
 */
public class TournamentOnline implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int tournamentId;

    private int startTime;

    private int endTime;
    
    private int isFinish;
    
    private String info;
    
    private int orderId;
    
    private String startContent;
    
    private String endContent;
    
    private String weiboShareContent;
    
    //no ormapping,for easy to use
    private Tournament tournament;

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getStartContent() {
        return startContent;
    }

    public void setStartContent(String startContent) {
        this.startContent = startContent;
    }
    
    public String getEndContent() {
        return endContent;
    }

    public void setEndContent(String endContent) {
        this.endContent = endContent;
    }
    
    public String getWeiboShareContent() {
        return weiboShareContent;
    }

    public void setWeiboShareContent(String weiboShareContent) {
        this.weiboShareContent = weiboShareContent;
    }
}
