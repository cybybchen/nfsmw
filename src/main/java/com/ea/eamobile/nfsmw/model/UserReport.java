package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public class UserReport implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int reportCount;
    
    private int reportedCount;
    
	
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
    public int getReportCount() {
        return reportCount;
    }
    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
    public int getReportedCount() {
        return reportedCount;
    }
    public void setReportedCount(int reportedCount) {
        this.reportedCount = reportedCount;
    }
}
