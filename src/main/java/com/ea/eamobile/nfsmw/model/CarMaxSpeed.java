package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:54 CST 2013
 * @since 1.0
 */
public class CarMaxSpeed implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String carId;
    
    private int maxSpeed;
    
	
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
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
