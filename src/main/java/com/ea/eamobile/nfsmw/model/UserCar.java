package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public class UserCar implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long userId;

    private String carId;

    private int score;

    private int status;

    private int chartletId;

    private int createTime;
    
    private int limit;
    
    private int states;
    
    

    public int getStates() {
		return states;
	}

	public void setStates(int states) {
		this.states = states;
	}

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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChartletId() {
        return chartletId;
    }

    public void setChartletId(int chartletId) {
        this.chartletId = chartletId;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;

    }

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
    
}
