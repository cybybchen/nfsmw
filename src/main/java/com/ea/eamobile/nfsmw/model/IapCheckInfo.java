package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Dec 19 21:13:48 CST 2012
 * @since 1.0
 */
public class IapCheckInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long userId;

    private long transactionId;

    private int quantity;

    private String productId;

    private int createTime;

    private int version;

    private int rmbNum;

    private String paymentMode;

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

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCreateTime() {

        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRmbNum() {
        return rmbNum;
    }

    public void setRmbNum(int rmbNum) {
        this.rmbNum = rmbNum;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}
