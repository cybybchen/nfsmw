package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 15:42:32 CST 2013
 * @since 1.0
 */
public class UserInitGold implements Serializable{

    private static final long serialVersionUID = 1L;

    private int level;
    
    private int userCount;
    
    private int gold;
    
	
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getUserCount() {
        return userCount;
    }
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
}
