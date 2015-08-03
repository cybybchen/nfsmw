package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:00:24 CST 2013
 * @since 1.0
 */
public class GotchaRatio implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int gotchaId;
    
    private int rewardId;
    
    private int weight;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGotchaId() {
        return gotchaId;
    }
    public void setGotchaId(int gotchaId) {
        this.gotchaId = gotchaId;
    }
    public int getRewardId() {
        return rewardId;
    }
    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
