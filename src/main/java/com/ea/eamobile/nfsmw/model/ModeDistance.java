package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:53 CST 2013
 * @since 1.0
 */
public class ModeDistance implements Serializable {

    private static final long serialVersionUID = 1L;

    private int modeId;

    private float distance;

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
