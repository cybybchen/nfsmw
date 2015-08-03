package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class CarSlotView implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int slotId;
    
    private int level;
    
    private int status;
    
    private int remainTime;
    
    private int score;
    
    private String description;

    private int nextAddScore;
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
    
    public int getNextAddScore() {
        return nextAddScore;
    }

    public void setNextAddScore(int nextAddScore) {
        this.nextAddScore = nextAddScore;
    }

}
