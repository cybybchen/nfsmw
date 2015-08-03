package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Sep 25 16:33:42 CST 2012
 * @since 1.0
 */
public class RaceModeUnlock implements Serializable{

    private static final long serialVersionUID = 1L;

    private String trackId;
    
    private int unlockValue;
    
    private int modeId;
    
    public String getTrackId() {
        return trackId;
    }
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public int getUnlockValue() {
        return unlockValue;
    }
    public void setUnlockValue(int unlockValue) {
        this.unlockValue = unlockValue;
    }
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
}
