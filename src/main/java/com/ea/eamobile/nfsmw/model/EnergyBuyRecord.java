package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Oct 15 14:42:10 CST 2012
 * @since 1.0
 */
public class EnergyBuyRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private java.util.Date buyDate;
    
	
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
    public Date getBuyDate() {
        Date temp = buyDate;
        return temp;
    }
    public void setBuyDate(java.util.Date buyDate) {
        if (buyDate != null) {
            this.buyDate = (Date) buyDate.clone();
        }
    }
}
