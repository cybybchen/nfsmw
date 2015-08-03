package com.ea.eamobile.nfsmw.view;

/**
 * mode 参赛对手
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class RacerView {

    private long userId;

    private String name;

    private int carId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
