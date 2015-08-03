package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 06 18:04:11 CST 2013
 * @since 1.0
 */
public class UserRaceAction implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int valueId;
    
    private int value;
    
	
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
    public int getValueId() {
        return valueId;
    }
    public void setValueId(int valueId) {
        this.valueId = valueId;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
