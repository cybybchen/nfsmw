package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 22 12:02:03 CST 2013
 * @since 1.0
 */
public class OperateActivity implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private float careerRpTimes;
    
    private float tournamentRpTimes;
    
    private float careerGoldTimes;
    
    private float tournamentGoldTimes;
    
    private float careerMoneyTimes;
    
    private float tournamentMoneyTimes;
    
    private int startTime;
    
    private int endTime;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getCareerRpTimes() {
        return careerRpTimes;
    }
    public void setCareerRpTimes(float careerRpTimes) {
        this.careerRpTimes = careerRpTimes;
    }
    public float getTournamentRpTimes() {
        return tournamentRpTimes;
    }
    public void setTournamentRpTimes(float tournamentRpTimes) {
        this.tournamentRpTimes = tournamentRpTimes;
    }
    public float getCareerGoldTimes() {
        return careerGoldTimes;
    }
    public void setCareerGoldTimes(float careerGoldTimes) {
        this.careerGoldTimes = careerGoldTimes;
    }
    public float getTournamentGoldTimes() {
        return tournamentGoldTimes;
    }
    public void setTournamentGoldTimes(float tournamentGoldTimes) {
        this.tournamentGoldTimes = tournamentGoldTimes;
    }
    public float getCareerMoneyTimes() {
        return careerMoneyTimes;
    }
    public void setCareerMoneyTimes(float careerMoneyTimes) {
        this.careerMoneyTimes = careerMoneyTimes;
    }
    public float getTournamentMoneyTimes() {
        return tournamentMoneyTimes;
    }
    public void setTournamentMoneyTimes(float tournamentMoneyTimes) {
        this.tournamentMoneyTimes = tournamentMoneyTimes;
    }
    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public int getEndTime() {
        return endTime;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
