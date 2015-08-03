package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public class SpendActivityReward implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int spendActivityId;
    
    private int spendRewardId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
}
