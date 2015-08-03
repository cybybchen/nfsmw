package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:46:24 CST 2013
 * @since 1.0
 */
public class RaceStartRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private String firstConsumebleId;
    
    private String secondConsumebleId;
    
    private String thirdConsumebleId;
    
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
    public String getFirstConsumebleId() {
        return firstConsumebleId;
    }
    public void setFirstConsumebleId(String firstConsumebleId) {
        this.firstConsumebleId = firstConsumebleId;
    }
    public String getSecondConsumebleId() {
        return secondConsumebleId;
    }
    public void setSecondConsumebleId(String secondConsumebleId) {
        this.secondConsumebleId = secondConsumebleId;
    }
    public String getThirdConsumebleId() {
        return thirdConsumebleId;
    }
    public void setThirdConsumebleId(String thirdConsumebleId) {
        this.thirdConsumebleId = thirdConsumebleId;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
}
