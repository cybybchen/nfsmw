package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 27 10:34:36 CST 2012
 * @since 1.0
 */
public class PurchaseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private long userId;

    private int type;

    private int buyTime;

    private int purchaseId;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(int buyTime) {
        this.buyTime = buyTime;

    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
}
