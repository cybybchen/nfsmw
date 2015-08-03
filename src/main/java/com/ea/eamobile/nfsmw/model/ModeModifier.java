package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 03 11:37:44 CST 2013
 * @since 1.0
 */
public class ModeModifier implements Serializable{

    private static final long serialVersionUID = 1L;

    private int modeId;
    
    private float modifier1;
    
    private float modifier2;
    
    private float modifier3;
    
    private float modifier4;
    
    private float modifier5;
    
    private float modifier1v1;
    
    private float standardTime;
    
	
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
    public float getModifier1() {
        return modifier1;
    }
    public void setModifier1(float modifier1) {
        this.modifier1 = modifier1;
    }
    public float getModifier2() {
        return modifier2;
    }
    public void setModifier2(float modifier2) {
        this.modifier2 = modifier2;
    }
    public float getModifier3() {
        return modifier3;
    }
    public void setModifier3(float modifier3) {
        this.modifier3 = modifier3;
    }
    public float getModifier4() {
        return modifier4;
    }
    public void setModifier4(float modifier4) {
        this.modifier4 = modifier4;
    }
    public float getModifier5() {
        return modifier5;
    }
    public void setModifier5(float modifier5) {
        this.modifier5 = modifier5;
    }
    public float getModifier1v1() {
        return modifier1v1;
    }
    public void setModifier1v1(float modifier1v1) {
        this.modifier1v1 = modifier1v1;
    }
    public float getStandardTime() {
        return standardTime;
    }
    public void setStandardTime(float standardTime) {
        this.standardTime = standardTime;
    }
}
