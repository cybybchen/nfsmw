package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 14 21:41:55 CST 2013
 * @since 1.0
 */
public class OperateChangeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int time;

    private long userId;

    private int addMoney;

    private int addGold;

    private int addEnergy;

    private String addCar;

    private int batchNum;

    private int currentMoney;

    private int originalMoney;

    private int currentGold;

    private int originalGold;

    private int currentEnergy;

    private int originalEnergy;

    private int addFragCount;

    private int fragmentId;

    private int originalFragCount;

    private int currentFragCount;

    public int getAddFragCount() {
        return addFragCount;
    }

    public void setAddFragCount(int addFragCount) {
        this.addFragCount = addFragCount;
    }

    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }

    public int getOriginalFragCount() {
        return originalFragCount;
    }

    public void setOriginalFragCount(int originalFragCount) {
        this.originalFragCount = originalFragCount;
    }

    public int getCurrentFragCount() {
        return currentFragCount;
    }

    public void setCurrentFragCount(int currentFragCount) {
        this.currentFragCount = currentFragCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(int addMoney) {
        this.addMoney = addMoney;
    }

    public int getAddGold() {
        return addGold;
    }

    public void setAddGold(int addGold) {
        this.addGold = addGold;
    }

    public int getAddEnergy() {
        return addEnergy;
    }

    public void setAddEnergy(int addEnergy) {
        this.addEnergy = addEnergy;
    }

    public String getAddCar() {
        return addCar;
    }

    public void setAddCar(String addCar) {
        this.addCar = addCar;
    }

    public int getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    public int getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(int originalMoney) {
        this.originalMoney = originalMoney;
    }

    public int getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(int currentGold) {
        this.currentGold = currentGold;
    }

    public int getOriginalGold() {
        return originalGold;
    }

    public void setOriginalGold(int originalGold) {
        this.originalGold = originalGold;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public int getOriginalEnergy() {
        return originalEnergy;
    }

    public void setOriginalEnergy(int originalEnergy) {
        this.originalEnergy = originalEnergy;
    }

}
