package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
public class UserGetSpendRewardRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int spendActivityId;
    
    private int spendRewardId;
    
    private int time;
    
    
	
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
    public int getSpendActivityId() {
        return spendActivityId;
    }
    public void setSpendActivityId(int spendActivityId) {
        this.spendActivityId = spendActivityId;
    }
    public int getSpendRewardId() {
        return spendRewardId;
    }
    public void setSpendRewardId(int spendRewardId) {
        this.spendRewardId = spendRewardId;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

}
