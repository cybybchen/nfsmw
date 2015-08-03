package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
public class GotchaRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private long userId;

    private int gotchaId;

    private int spendGoldNum;

    private int getGoldNum;

    private int getMoneyNum;

    private int getGasNum;

    private int fragId;

    private int fragNum;

    private String carId;

    private int time;

    private int isBingoCar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getGotchaId() {
        return gotchaId;
    }

    public void setGotchaId(int gotchaId) {
        this.gotchaId = gotchaId;
    }

    public int getSpendGoldNum() {
        return spendGoldNum;
    }

    public void setSpendGoldNum(int spendGoldNum) {
        this.spendGoldNum = spendGoldNum;
    }

    public int getGetGoldNum() {
        return getGoldNum;
    }

    public void setGetGoldNum(int getGoldNum) {
        this.getGoldNum = getGoldNum;
    }

    public int getGetMoneyNum() {
        return getMoneyNum;
    }

    public void setGetMoneyNum(int getMoneyNum) {
        this.getMoneyNum = getMoneyNum;
    }

    public int getGetGasNum() {
        return getGasNum;
    }

    public void setGetGasNum(int getGasNum) {
        this.getGasNum = getGasNum;
    }

    public int getFragId() {
        return fragId;
    }

    public void setFragId(int fragId) {
        this.fragId = fragId;
    }

    public int getFragNum() {
        return fragNum;
    }

    public void setFragNum(int fragNum) {
        this.fragNum = fragNum;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIsBingoCar() {
        return isBingoCar;
    }

    public void setIsBingoCar(int isBingoCar) {
        this.isBingoCar = isBingoCar;
    }
}
