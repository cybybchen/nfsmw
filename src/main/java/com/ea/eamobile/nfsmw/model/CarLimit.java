package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 15:12:07 CST 2012
 * @since 1.0
 */
public class CarLimit implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String displayString;
    
    private int tonrnamentGroupId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDisplayString() {
        return displayString;
    }
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    
    public int getTonrnamentGroupId() {
        return tonrnamentGroupId;
    }
    public void setTonrnamentGroupId(int tonrnamentGroupId) {
        this.tonrnamentGroupId = tonrnamentGroupId;
    }
    
}
