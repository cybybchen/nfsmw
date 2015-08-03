package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.BaseGhostMod;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 11:27:22 CST 2012
 * @since 1.0
 */
public class CareerGhostMod extends BaseGhostMod implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long ghostId;
    
    private int modeType;
    
    private float modeValue;
    
    private int modeLevel;
    
    private int modeId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getGhostId() {
        return ghostId;
    }
    public void setGhostId(long ghostId) {
        this.ghostId = ghostId;
    }
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
}
