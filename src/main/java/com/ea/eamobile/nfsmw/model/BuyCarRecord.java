package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
public class BuyCarRecord implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int spendGoldNum;
    
    private String carId;
    
    private int fragNum;
    

    
	
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
    public int getSpendGoldNum() {
        return spendGoldNum;
    }
    public void setSpendGoldNum(int spendGoldNum) {
        this.spendGoldNum = spendGoldNum;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getFragNum() {
        return fragNum;
    }
    public void setFragNum(int fragNum) {
        this.fragNum = fragNum;
    }

}
