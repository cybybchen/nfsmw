package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public class SpendActivity implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int startTime;
    
    private int endTime;
    
    private String displayName;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
