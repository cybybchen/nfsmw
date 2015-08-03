package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.model.CarChartlet;

public class CarChartletView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private CarChartlet chartlet;
    
    private boolean isOwned;
    
    private int remainTime;

    private int sellFlag;
    
    private int orderId;
    
    private int score;
    
    public CarChartlet getChartlet() {
        return chartlet;
    }

    public void setChartlet(CarChartlet chartlet) {
        this.chartlet = chartlet;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
    
    public int getSellFlag() {
        return sellFlag;
    }

    public void setSellFlag(int sellFlag) {
        this.sellFlag = sellFlag;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
