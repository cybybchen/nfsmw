package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:45 CST 2013
 * @since 1.0
 */
public class GotchaCar implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String carId;
    
    private int partId;
    
    private int partNum;
    
    private int goldLowLimit;
    
    private int goldUpperLimit;
    
    private int goldPayLimit;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getPartId() {
        return partId;
    }
    public void setPartId(int partId) {
        this.partId = partId;
    }
    public int getPartNum() {
        return partNum;
    }
    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }
    public int getGoldLowLimit() {
        return goldLowLimit;
    }
    public void setGoldLowLimit(int goldLowLimit) {
        this.goldLowLimit = goldLowLimit;
    }
    public int getGoldUpperLimit() {
        return goldUpperLimit;
    }
    public void setGoldUpperLimit(int goldUpperLimit) {
        this.goldUpperLimit = goldUpperLimit;
    }
    public int getGoldPayLimit() {
        return goldPayLimit;
    }
    public void setGoldPayLimit(int goldPayLimit) {
        this.goldPayLimit = goldPayLimit;
    }
}
