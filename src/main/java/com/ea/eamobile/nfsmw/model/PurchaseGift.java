package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Jan 25 18:37:59 CST 2013
 * @since 1.0
 */
public class PurchaseGift implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int energy;
    
    private int money;
    
    private int gold;
    
    private int rpNum;
    
    private int starNum;
    
    private int purchaseId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getRpNum() {
        return rpNum;
    }
    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
    }
    public int getStarNum() {
        return starNum;
    }
    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }
    public int getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
}
