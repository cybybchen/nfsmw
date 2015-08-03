package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
public class CarExt implements Serializable{

    private static final long serialVersionUID = 1L;

    private String carId;
    
    private int startTime;
    
    private int endTime;
    
    private int visible;
    
    private int price;
    
    private int priceType;
    
	
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public int getVisible() {
        return visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getPriceType() {
        return priceType;
    }
    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }
}
