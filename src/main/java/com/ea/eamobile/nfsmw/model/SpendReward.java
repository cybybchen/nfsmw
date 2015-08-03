package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public class SpendReward implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int goldAmount;

    private int addGold;

    private int addMoney;

    private int addEnergy;

    private String displayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getAddGold() {
        return addGold;
    }

    public void setAddGold(int addGold) {
        this.addGold = addGold;
    }

    public int getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(int addMoney) {
        this.addMoney = addMoney;
    }

    public int getAddEnergy() {
        return addEnergy;
    }

    public void setAddEnergy(int addEnergy) {
        this.addEnergy = addEnergy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
