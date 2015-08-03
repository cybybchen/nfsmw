package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

public class RpLeaderBoard implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String headUrl;

    private int headIndex;
    
    private String name;
    
    private int mostWantedNum;
    
    private int rpNum;
    
    private int rpLevel;
    
    private long userId;
    
    
    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    
    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getRpNum() {
        return rpNum;
    }

    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
    }
    
    public int getRpLevel() {
        return rpLevel;
    }

    public void setRpLevel(int rpLevel) {
        this.rpLevel = rpLevel;
    }
    
    public int getMostWantedNum() {
        return mostWantedNum;
    }

    public void setMostWantedNum(int mostWantedNum) {
        this.mostWantedNum = mostWantedNum;
    }
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    

}
