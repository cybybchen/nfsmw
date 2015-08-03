package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:56 CST 2013
 * @since 1.0
 */
public class GotchaExpense extends Merchandise implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int gotchaId;

    private int level;

    private int maxFreeTimes;

    private int dailyFreeTimes;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int price;

    private int priceType;

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getMaxFreeTimes() {
        return maxFreeTimes;
    }

    public void setMaxFreeTimes(int maxFreeTimes) {
        this.maxFreeTimes = maxFreeTimes;
    }

    public int getDailyFreeTimes() {
        return dailyFreeTimes;
    }

    public void setDailyFreeTimes(int dailyFreeTimes) {
        this.dailyFreeTimes = dailyFreeTimes;
    }

}
