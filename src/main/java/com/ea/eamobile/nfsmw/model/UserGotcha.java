package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:11 CST 2013
 * @since 1.0
 */
public class UserGotcha implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int gotchaId;
    
    private int gold;
    
    private int missFragCount;
	
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getGotchaId() {
        return gotchaId;
    }
    public void setGotchaId(int gotchaId) {
        this.gotchaId = gotchaId;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getMissFragCount() {
        return missFragCount;
    }
    public void setMissFragCount(int missFragCount) {
        this.missFragCount = missFragCount;
    }
}
