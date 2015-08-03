package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:56 CST 2012
 * @since 1.0
 */
public class TournamentGroup implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String name;
    
    private int tournamentId;
    
    private int rpDownLevel;
    
    private int rpUpLevel;
    
    private String carTypeLimit;
    
    private int isProvide;
    
    private String carProvide;
    
    private int personNum;
    
    private int fee;
    
    private int feeType;
    
    private String trackId;
    
    private int isConsumeEnergy;
    
    private int useEnergy;
    
    private int trafficNum;
    
    private int cops;
    
    private int modeId;
    
    private String matchDescribe;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTournamentId() {
        return tournamentId;
    }
    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
    public int getRpDownLevel() {
        return rpDownLevel;
    }
    public void setRpDownLevel(int rpDownLevel) {
        this.rpDownLevel = rpDownLevel;
    }
    public int getRpUpLevel() {
        return rpUpLevel;
    }
    public void setRpUpLevel(int rpUpLevel) {
        this.rpUpLevel = rpUpLevel;
    }
    public String getCarTypeLimit() {
        return carTypeLimit;
    }
    public void setCarTypeLimit(String carTypeLimit) {
        this.carTypeLimit = carTypeLimit;
    }
    public int getIsProvide() {
        return isProvide;
    }
    public void setIsProvide(int isProvide) {
        this.isProvide = isProvide;
    }
    public String getCarProvide() {
        return carProvide;
    }
    public void setCarProvide(String carProvide) {
        this.carProvide = carProvide;
    }
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }
    public int getFee() {
        return fee;
    }
    public void setFee(int fee) {
        this.fee = fee;
    }
    public int getFeeType() {
        return feeType;
    }
    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }
    public String getTrackId() {
        return trackId;
    }
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
    public int getIsConsumeEnergy() {
        return isConsumeEnergy;
    }
    public void setIsConsumeEnergy(int isConsumeEnergy) {
        this.isConsumeEnergy = isConsumeEnergy;
    }
    public int getUseEnergy() {
        return useEnergy;
    }
    public void setUseEnergy(int useEnergy) {
        this.useEnergy = useEnergy;
    }
    public int getTrafficNum() {
        return trafficNum;
    }
    public void setTrafficNum(int trafficNum) {
        this.trafficNum = trafficNum;
    }
    public int getCops() {
        return cops;
    }
    public void setCops(int cops) {
        this.cops = cops;
    }
    
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
    
    public String getMatchDescribe() {
        return matchDescribe;
    }
    public void setMatchDescribe(String matchDescribe) {
        this.matchDescribe = matchDescribe;
    }
}
