package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 20 07:22:03 ADT 2013
 * @since 1.0
 */
public class IapFailtureRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String receiptData;
    
    private long userId;
    
    private String result;
    
    private String reason;
    
    
    
	
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getReceiptData() {
        return receiptData;
    }
    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
