package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 19 16:49:27 CST 2012
 * @since 1.0
 */
public class UserDailyRace implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long userId;

    private int lastMatchDate;

    private int leftTimes;

    private int duraDayNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLastMatchDate() {
        return lastMatchDate;
    }

    public void setLastMatchDate(int lastMatchDate) {

        this.lastMatchDate = lastMatchDate;

    }

    public int getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(int leftTimes) {
        this.leftTimes = leftTimes;
    }

    public int getDuraDayNum() {
        return duraDayNum;
    }

    public void setDuraDayNum(int duraDayNum) {
        this.duraDayNum = duraDayNum;
    }
}
