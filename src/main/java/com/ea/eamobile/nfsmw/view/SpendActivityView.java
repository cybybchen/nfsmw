package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class SpendActivityView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String startTime;

    private String endTime;

    private String status;
    
    private String displayName;
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
