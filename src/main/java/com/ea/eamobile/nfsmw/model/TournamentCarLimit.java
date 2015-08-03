package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:32 CST 2012
 * @since 1.0
 */
public class TournamentCarLimit implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int groupId;
    
    private String carId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
}
