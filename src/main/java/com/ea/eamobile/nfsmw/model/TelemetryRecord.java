package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 17 15:42:34 CST 2012
 * @since 1.0
 */
public class TelemetryRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //add delta param
    private int deltaMoney;
    private int deltaGold;
    private int deltaMwNum;
    private int deltaRpNum;
    private int deltaEnergy;
    
    private int raceResultStatus=-1;
    
    private int version;
    
    public int getDeltaMoney() {
        return deltaMoney;
    }

    public void setDeltaMoney(int deltaMoney) {
        this.deltaMoney = deltaMoney;
    }

    public int getDeltaGold() {
        return deltaGold;
    }

    public void setDeltaGold(int deltaGold) {
        this.deltaGold = deltaGold;
    }

    public int getDeltaMwNum() {
        return deltaMwNum;
    }

    public void setDeltaMwNum(int deltaMwNum) {
        this.deltaMwNum = deltaMwNum;
    }

    public int getDeltaRpNum() {
        return deltaRpNum;
    }

    public void setDeltaRpNum(int deltaRpNum) {
        this.deltaRpNum = deltaRpNum;
    }

    public int getDeltaEnergy() {
        return deltaEnergy;
    }

    public void setDeltaEnergy(int deltaEnergy) {
        this.deltaEnergy = deltaEnergy;
    }

    public TelemetryRecord(User user) {
        if (user == null) {
            return;
        }
        this.rpNum = user.getRpNum();
        this.mwNum = user.getStarNum();
        this.tier = user.getTier();
        this.money = user.getMoney();
        this.gold = user.getGold();
        this.headIndex = user.getHeadIndex();
        this.headUrl = user.getHeadUrl();
        this.nickName = user.getName();
        this.energy=user.getEnergy();
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRpNum() {
        return rpNum;
    }

    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
    }

    public int getMwNum() {
        return mwNum;
    }

    public void setMwNum(int mwNum) {
        this.mwNum = mwNum;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(float raceTime) {
        this.raceTime = raceTime;
    }

    public float getAverAgeSpeed() {
        return averAgeSpeed;
    }

    public void setAverAgeSpeed(float averAgeSpeed) {
        this.averAgeSpeed = averAgeSpeed;
    }

    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }

    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTournamentRank() {
        return tournamentRank;
    }

    public void setTournamentRank(int tournamentRank) {
        this.tournamentRank = tournamentRank;
    }

    public int getDailyRaceDuraNum() {
        return dailyRaceDuraNum;
    }

    public void setDailyRaceDuraNum(int dailyRaceDuraNum) {
        this.dailyRaceDuraNum = dailyRaceDuraNum;
    }

    public int getDailyRaceResult() {
        return dailyRaceResult;
    }

    public void setDailyRaceResult(int dailyRaceResult) {
        this.dailyRaceResult = dailyRaceResult;
    }

    public int getBindingWeiBo() {
        return bindingWeiBo;
    }

    public void setBindingWeiBo(int bindingWeiBo) {
        this.bindingWeiBo = bindingWeiBo;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getConsumble1Id() {
        return consumble1Id;
    }

    public void setConsumble1Id(String consumble1Id) {
        this.consumble1Id = consumble1Id;
    }

    public String getConsumble2Id() {
        return consumble2Id;
    }

    public void setConsumble2Id(String consumble2Id) {
        this.consumble2Id = consumble2Id;
    }

    public String getConsumble3Id() {
        return consumble3Id;
    }

    public void setConsumble3Id(String consumble3Id) {
        this.consumble3Id = consumble3Id;
    }

    
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public int getRaceResultStatus() {
        return raceResultStatus;
    }

    public void setRaceResultStatus(int raceResultStatus) {
        this.raceResultStatus = raceResultStatus;
    }
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    private String requestName = "";
    private long userId = 0;
    private int rpNum = -1;
    private int mwNum = -1;
    private int tier = -1;
    private int money = -1;
    private int gold = -1;
    private int modeId = -1;
    private int gameMode = -1;
    private String carId = "";
    private int rank = 0;
    private float raceTime = 0f;
    private float averAgeSpeed = 0f;
    private int tournamentOnlineId = -1;
    private int groupId = 0;
    private int classId = 0;
    private int tournamentRank = 0;
    private int dailyRaceDuraNum = -1;
    private int dailyRaceResult = -1;
    private int bindingWeiBo = -1;
    private int headIndex = -2;
    private String headUrl = "";
    private String nickName = "";
    private String consumble1Id = "";
    private String consumble2Id = "";
    private String consumble3Id = "";
    private int energy=-1;

}
