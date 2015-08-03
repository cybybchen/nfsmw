package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 17:14:26 CST 2012
 * @since 1.0
 */
public class UserChartlet implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private long userId;

    private int chartletId;
    
    private int isOwned;
    
    
    private Timestamp rentTime;

    public Timestamp getRentTime() {
        Timestamp temp=rentTime;
        return temp;
    }

    public void setRentTime(Timestamp rentTime) {
        if (rentTime != null) {
            this.rentTime = (Timestamp) rentTime.clone();
        }
    }

    public int getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(int isOwned) {
        this.isOwned = isOwned;
    }

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

    public int getChartletId() {
        return chartletId;
    }

    public void setChartletId(int chartletId) {
        this.chartletId = chartletId;
    }

   
}
