package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 08 14:28:18 CST 2013
 * @since 1.0
 */
public class UserCarFragment implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int fragmentId;
    
    private int count;
    
	
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
    public int getFragmentId() {
        return fragmentId;
    }
    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
