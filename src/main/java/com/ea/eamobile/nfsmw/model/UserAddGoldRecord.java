package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 16:01:12 CST 2013
 * @since 1.0
 */
public class UserAddGoldRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int addGold;
    
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
    public int getAddGold() {
        return addGold;
    }
    public void setAddGold(int addGold) {
        this.addGold = addGold;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
}
