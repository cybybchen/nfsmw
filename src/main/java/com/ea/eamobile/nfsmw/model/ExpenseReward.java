package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 13:28:36 CST 2013
 * @since 1.0
 */
public class ExpenseReward implements Serializable{

    private static final long serialVersionUID = 1L;

    private int gold;
    
    private int rewardId;
    
	
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
}
