package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Nov 29 14:38:39 CST 2012
 * @since 1.0
 */
public class TierCarLimit implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int tierId;
    
    private String carId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTierId() {
        return tierId;
    }
    public void setTierId(int tierId) {
        this.tierId = tierId;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
}
