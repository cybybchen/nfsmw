package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 13 14:32:03 CST 2013
 * @since 1.0
 */
public class FinishRatioAddition implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private float addTimes;

    private int modeId;

    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAddTimes() {
        return addTimes;
    }

    public void setAddTimes(float addTimes) {
        this.addTimes = addTimes;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
