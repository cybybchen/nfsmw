package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 18:44:41 CST 2012
 * @since 1.0
 */
public class Reward implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String name;
    
    private int rpNum;
    
    private int money;
    
    private int gold;
    
    private int energy;
    
    private String carId;
    
    private String displayName;
    
    private int mostwantedNum;
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    
    public int getMostwantedNum() {
        return mostwantedNum;
    }
    public void setMostwantedNum(int mostwantedNum) {
        this.mostwantedNum = mostwantedNum;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
