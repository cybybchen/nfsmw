package com.ea.eamobile.nfsmw.model;

/**
 * 完成度系数
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class FinishRatio {
    
    private int id;

    private int modeType;

    private int rank;

    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
