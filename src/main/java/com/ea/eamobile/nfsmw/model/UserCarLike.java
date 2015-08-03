package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:31 CST 2013
 * @since 1.0
 */
public class UserCarLike implements Serializable{

    private static final long serialVersionUID = 1L;

    private long userCarId;
    
    private int count;
    
	
    public long getUserCarId() {
        return userCarId;
    }
    public void setUserCarId(long userCarId) {
        this.userCarId = userCarId;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
