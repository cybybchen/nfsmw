package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 16:36:50 CST 2013
 * @since 1.0
 */
public class UserExpenseRec implements Serializable{

    private static final long serialVersionUID = 1L;

    private long userId;
    
    private int gold;
    
    private int r1;
    
    private int r2;
    
    private int r3;
    
    private int r4;
    
    private int r5;
    
    private int r6;
    
	
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getR1() {
        return r1;
    }
    public void setR1(int r1) {
        this.r1 = r1;
    }
    public int getR2() {
        return r2;
    }
    public void setR2(int r2) {
        this.r2 = r2;
    }
    public int getR3() {
        return r3;
    }
    public void setR3(int r3) {
        this.r3 = r3;
    }
    public int getR4() {
        return r4;
    }
    public void setR4(int r4) {
        this.r4 = r4;
    }
    public int getR5() {
        return r5;
    }
    public void setR5(int r5) {
        this.r5 = r5;
    }
    public int getR6() {
        return r6;
    }
    public void setR6(int r6) {
        this.r6 = r6;
    }
}
