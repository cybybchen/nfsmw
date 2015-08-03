package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public class DailyRaceModeId implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int modeId;
    
    private String modeName;
    
    private int passTime;
    
    private String displayString;
    
	
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
    public String getModeName() {
        return modeName;
    }
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
    public int getPassTime() {
        return passTime;
    }
    public void setPassTime(int passTime) {
        this.passTime = passTime;
    }
    public String getDisplayString() {
        return displayString;
    }
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
}
