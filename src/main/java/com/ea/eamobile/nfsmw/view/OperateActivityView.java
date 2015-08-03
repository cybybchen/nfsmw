package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class OperateActivityView implements Serializable {

    private static final long serialVersionUID = 1L;

  private int id;
    
    private float careerRpTimes;
    
    private float tournamentRpTimes;
    
    private float careerGoldTimes;
    
    private float tournamentGoldTimes;
    
    private float careerMoneyTimes;
    
    private float tournamentMoneyTimes;
    
    private String startTime;
    
    private String endTime;
    
    private String status;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
