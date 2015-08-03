package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:55 CST 2012
 * @since 1.0
 */
public class TournamentReward implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int groupId;
    
    private int rankMax;
    
    private int rankMin;
    
    private String specialNumber;
    
    private int rewardId;
    

    
    private int orderId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getRankMax() {
        return rankMax;
    }
    public void setRankMax(int rankMax) {
        this.rankMax = rankMax;
    }
    public int getRankMin() {
        return rankMin;
    }
    public void setRankMin(int rankMin) {
        this.rankMin = rankMin;
    }
    public String getSpecialNumber() {
        return specialNumber;
    }
    public void setSpecialNumber(String specialNumber) {
        this.specialNumber = specialNumber;
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
}
