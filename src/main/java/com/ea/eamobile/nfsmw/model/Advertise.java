package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Dec 13 14:34:54 CST 2012
 * @since 1.0
 */
public class Advertise implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String hintString;
    
    private String adviseString;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getHintString() {
        return hintString;
    }
    public void setHintString(String hintString) {
        this.hintString = hintString;
    }
    public String getAdviseString() {
        return adviseString;
    }
    public void setAdviseString(String adviseString) {
        this.adviseString = adviseString;
    }
}
