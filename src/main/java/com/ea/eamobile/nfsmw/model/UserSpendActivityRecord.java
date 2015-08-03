package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
public class UserSpendActivityRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int spendActivityId;
    
    private int spendGoldNum;
    
	
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
    public int getSpendGoldNum() {
        return spendGoldNum;
    }
    public void setSpendGoldNum(int spendGoldNum) {
        this.spendGoldNum = spendGoldNum;
    }
}
