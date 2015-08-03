package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Oct 17 15:58:52 CST 2012
 * @since 1.0
 */
public class RpLevel implements Serializable{

    private static final long serialVersionUID = 1L;

    private int level;
    
    private int rpNum;
    
    private int rewardId;
    
    private String name;
    
    private String iconId;
	
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getRpNum() {
        return rpNum;
    }
    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIconId() {
        return iconId;
    }
    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
