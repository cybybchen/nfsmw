package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 03 10:56:22 CST 2012
 * @since 1.0
 */
public class EventOptionOverride implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int modeId;
    
    private String name;
    
    private int level;
    
    private String value ="";
    
    private int isFather;
    
    private int fatherId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public int getIsFather() {
        return isFather;
    }
    public void setIsFather(int isFather) {
        this.isFather = isFather;
    }
    public int getFatherId() {
        return fatherId;
    }
    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }
    @Override
    public String toString() {
        return "EventOptionOverride [id=" + id + ", modeId=" + modeId + ", name=" + name + ", level=" + level + ", value="
                + value + ", isFather=" + isFather + ", fatherId=" + fatherId + "]";
    }
}
