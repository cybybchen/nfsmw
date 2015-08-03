package com.ea.eamobile.nfsmw.view;

public class BaseGhostMod {
    
    private int modeType;
    
    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public float getModeValue() {
        return modeValue;
    }

    public void setModeValue(float modeValue) {
        this.modeValue = modeValue;
    }

    public int getModeLevel() {
        return modeLevel;
    }

    public void setModeLevel(int modeLevel) {
        this.modeLevel = modeLevel;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    private float modeValue;
    
    private int modeLevel;
    
    private int modeId;
}
