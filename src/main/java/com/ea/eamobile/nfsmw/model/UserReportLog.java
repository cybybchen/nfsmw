package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public class UserReportLog implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private long reportedId;
    
    private int reportDate;
    
	
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
    public long getReportedId() {
        return reportedId;
    }
    public void setReportedId(long reportedId) {
        this.reportedId = reportedId;
    }
    public int getReportDate() {
        return reportDate;
    }
    public void setReportDate(int reportDate) {
        this.reportDate = reportDate;
    }
}
