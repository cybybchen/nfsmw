package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Date;

import com.ea.eamobile.nfsmw.utils.Markable;

public class User extends Markable implements Serializable {

    private static final long serialVersionUID = 1L;

    protected long id;

    protected String name;

    protected int eol;

    protected int level;

    protected int money;

    protected int energy;

    protected String headUrl;

    protected int headIndex;

    protected boolean isNicknameChanged;

    /**
     * 考级级别
     */
    protected int tier;

    protected Date dailyRewardDate;

    protected int starNum;

    protected int lastWinOrNot;

    protected int duraMatchNum;

    protected int maxMatchWinNum;

    protected int maxMatchLoseNum;

    protected int lastRegainEnergyDate;

    protected int rpNum;

    protected int gold;

    protected int createTime;

    protected int isWriteJaguar;

    protected String willowtreeToken;

    /**
     * 用户当前可见资源版本
     */
    protected int version;

    protected int isRaceStart;

    protected int isOldUser;

    protected int eventOptionVersion;

    protected int isGetTutorialReward;

    protected int isRewardedBind;

    protected int tierStatus;

    protected int certType;

    protected int accountStatus;
    
    protected String accessToken;
    
    protected String uid;
    
    protected int lastSendEnergyDate;
    
    protected int packageBuyRecord;
    
    protected String vipEndTime;
    
    protected String vipLastRewardTime;
    
    protected int fansRewardStatus;
    
    protected int fansRewardLastTime;
    
    protected int missionRewardStatus;
    
    protected int missionFinishStatus;
    
    protected int goldModeUnlockStatus;
    
    protected String monthGoldCardEndTime;
    
    protected String monthGoldCardLastRewardTime;
    
    protected int isNewUser;
    
    protected int playGameTimes;

    // db field
    protected static final String ID = "id";
    protected static final String NAME = "name";
    protected static final String EOL = "eol";
    protected static final String LEVEL = "level";
    protected static final String MONEY = "money";
    protected static final String ENERGY = "energy";
    protected static final String HEAD_URL = "head_url";
    protected static final String HEAD_INDEX = "head_index";
    protected static final String IS_NICKNAME_CHANGED = "is_nickname_changed";
    protected static final String TIER = "tier";
    protected static final String DAILY_REWARD_DATA = "daily_reward_date";
    protected static final String STAR_NUM = "star_num";
    protected static final String LAST_WIN_OR_NOT = "last_win_or_not";
    protected static final String DURA_MATCH_NUM = "dura_match_num";
    protected static final String MAX_MATCH_WIN_NUM = "max_match_win_num";
    protected static final String MAX_MATCH_LOSE_NUM = "max_match_lose_num";
    protected static final String LAST_REGAIN_ENERGY_DATE = "last_regain_energy_date";
    protected static final String RP_NUM = "rp_num";
    protected static final String GOLD = "gold";
    protected static final String CREATE_TIME = "create_time";
    protected static final String IS_WRITE_JAGUAR = "is_write_jaguar";
    protected static final String WILLOWTREE_TOKEN = "willowtree_token";
    protected static final String VERSION = "version";
    protected static final String IS_RACE_START = "is_race_start";
    protected static final String IS_OLD_USER = "is_old_user";
    protected static final String EVENT_OPTION_VERSION = "event_option_version";
    protected static final String IS_GET_TUTORIAL_REWARD = "is_get_tutorial_reward";
    protected static final String IS_REWARDED_BIND = "is_rewarded_bind";
    protected static final String TIER_STATUS = "tier_status";
    protected static final String CERT_TYPE = "cert_type";
    protected static final String ACCOUNT_STATUS = "account_status";
    protected static final String ACCESS_TOKEN = "access_token";
    protected static final String UID = "uid";
    protected static final String LAST_SEND_ENERGY_DATE = "last_send_energy_date";
    protected static final String PACKAGE_BUY_RECORD = "package_buy_record";
    protected static final String VIP_END_TIME = "vip_end_time";
    protected static final String VIP_LAST_REWARD_TIME = "vip_last_reward_time";
    protected static final String FANS_REWARD_STATUS = "fans_reward_status";
    protected static final String FANS_REWARD_LAST_TIME = "fans_reward_last_time";
    protected static final String MISSION_REWARD_STATUS = "mission_reward_status";
    protected static final String MISSION_FINISH_STATUS = "mission_finish_status";
    protected static final String GOLD_MODE_UNLOCK_STATUS = "gold_mode_unlock_status";
    protected static final String MONTH_GOLD_CARD_END_TIME = "month_gold_card_end_time";
    protected static final String MONTH_GOLD_CARD_LAST_REWARD_TIME = "month_gold_card_last_reward_time";
    protected static final String IS_NEW_USER = "new_user";
    protected static final String PLAY_GAME_TIMES = "play_game_times";

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
        mark(ACCOUNT_STATUS, accountStatus);
    }

    public int getCertType() {
        return certType;
    }

    public void setCertType(int certType) {
        this.certType = certType;
        mark(CERT_TYPE, certType);
    }

    // not a db column
    private int deviceType;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getEventOptionVersion() {
        return eventOptionVersion;
    }

    public void setEventOptionVersion(int eventOptionVersion) {
        this.eventOptionVersion = eventOptionVersion;
        mark(EVENT_OPTION_VERSION, eventOptionVersion);
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
        mark(STAR_NUM, starNum);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        mark(ID, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        mark(NAME, name);
    }

    public int getEol() {
        return eol;
    }

    public void setEol(int eol) {
        this.eol = eol;
        mark(EOL, eol);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        mark(LEVEL, level);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        mark(MONEY, money);
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
        mark(HEAD_URL, headUrl);
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
        mark(TIER, tier);
    }

    public Date getDailyRewardDate() {
        Date temp = dailyRewardDate;
        return temp;
    }

    public void setDailyRewardDate(Date dailyRewardDate) {
        if (dailyRewardDate != null) {
            this.dailyRewardDate = (Date) dailyRewardDate.clone();
        }
        mark(DAILY_REWARD_DATA, dailyRewardDate);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        mark(ENERGY, energy);
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
        mark(HEAD_INDEX, headIndex);
    }

    public int getLastWinOrNot() {
        return lastWinOrNot;
    }

    public void setLastWinOrNot(int lastWinOrNot) {
        this.lastWinOrNot = lastWinOrNot;
        mark(LAST_WIN_OR_NOT, lastWinOrNot);
    }

    public int getDuraMatchNum() {
        return duraMatchNum;
    }

    public void setDuraMatchNum(int duraMatchNum) {
        this.duraMatchNum = duraMatchNum;
        mark(DURA_MATCH_NUM, duraMatchNum);
    }

    public int getMaxMatchWinNum() {
        return maxMatchWinNum;
    }

    public void setMaxMatchWinNum(int maxMatchWinNum) {
        this.maxMatchWinNum = maxMatchWinNum;
        mark(MAX_MATCH_WIN_NUM, maxMatchWinNum);
    }

    public int getMaxMatchLoseNum() {
        return maxMatchLoseNum;
    }

    public void setMaxMatchLoseNum(int maxMatchLoseNum) {
        this.maxMatchLoseNum = maxMatchLoseNum;
        mark(MAX_MATCH_LOSE_NUM, maxMatchLoseNum);
    }

    public int getLastRegainEnergyDate() {
        return lastRegainEnergyDate;
    }

    public void setLastRegainEnergyDate(int lastRegainEnergyDate) {

        this.lastRegainEnergyDate = lastRegainEnergyDate;
        mark(LAST_REGAIN_ENERGY_DATE, lastRegainEnergyDate);

    }

    public int getRpNum() {
        return rpNum;
    }

    public void setRpNum(int rpNum) {
        this.rpNum = rpNum;
        mark(RP_NUM, rpNum);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        mark(GOLD, gold);
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;
        mark(CREATE_TIME, createTime);

    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
        mark(VERSION, version);
    }

    public int getIsRaceStart() {
        return isRaceStart;
    }

    public void setIsRaceStart(int isRaceStart) {
        this.isRaceStart = isRaceStart;
        mark(IS_RACE_START, isRaceStart);
    }

    public void setIsNicknameChanged(boolean isNicknameChanged) {
        this.isNicknameChanged = isNicknameChanged;
        mark(IS_NICKNAME_CHANGED, isNicknameChanged ? 1 : 0);
    }

    public boolean getIsNicknameChanged() {
        return isNicknameChanged;
    }

    public void setIsOldUser(int isOldUser) {
        this.isOldUser = isOldUser;
        mark(IS_OLD_USER, isOldUser);
    }

    public int getIsOldUser() {
        return isOldUser;
    }

    public void setIsWriteJaguar(int isWriteJaguar) {
        this.isWriteJaguar = isWriteJaguar;
        mark(IS_WRITE_JAGUAR, isWriteJaguar);
    }

    public int getIsWriteJaguar() {
        return isWriteJaguar;
    }

    public String getWillowtreeToken() {
        return willowtreeToken;
    }

    public void setWillowtreeToken(String willowtreeToken) {
        this.willowtreeToken = willowtreeToken;
        mark(WILLOWTREE_TOKEN, willowtreeToken);
    }

    public void setIsGetTutorialReward(int isGetTutorialReward) {
        this.isGetTutorialReward = isGetTutorialReward;
        mark(IS_GET_TUTORIAL_REWARD, isGetTutorialReward);
    }

    public int getIsGetTutorialReward() {
        return isGetTutorialReward;
    }

    public void setIsRewardedBind(int isRewardedBind) {
        this.isRewardedBind = isRewardedBind;
        mark(IS_REWARDED_BIND, isRewardedBind);
    }

    public int getIsRewardedBind() {
        return isRewardedBind;
    }

    public void setTierStatus(int tierStatus) {
        this.tierStatus = tierStatus;
        mark(TIER_STATUS, tierStatus);
    }

    public int getTierStatus() {
        return tierStatus;
    }

    public void setNicknameChanged(boolean isNicknameChanged) {
        this.isNicknameChanged = isNicknameChanged;
        mark(IS_NICKNAME_CHANGED, isNicknameChanged);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        mark(ACCESS_TOKEN, accessToken);
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        mark(UID, uid);
    }
    
    public int getLastSendEnergyDate() {
        return lastSendEnergyDate;
    }

    public void setLastSendEnergyDate(int lastSendEnergyDate) {
        this.lastSendEnergyDate = lastSendEnergyDate;
        mark(LAST_SEND_ENERGY_DATE, lastSendEnergyDate);
    }
    
    public int getPackageBuyRecord() {
        return packageBuyRecord;
    }

    public void setPackageBuyRecord(int packageBuyRecord) {
        this.packageBuyRecord = packageBuyRecord;
        mark(PACKAGE_BUY_RECORD, packageBuyRecord);
    }
    
    public String getVipEndTime() {
		return vipEndTime;
	}

	public void setVipEndTime(String vipEndTime) {
		this.vipEndTime = vipEndTime;
		mark(VIP_END_TIME, vipEndTime);
	}

	public String getVipLastRewardTime() {
		return vipLastRewardTime;
	}

	public void setVipLastRewardTime(String vipLastRewardTime) {
		this.vipLastRewardTime = vipLastRewardTime;
		mark(VIP_LAST_REWARD_TIME, vipLastRewardTime);
	}

	public int getFansRewardStatus() {
		return fansRewardStatus;
	}

	public void setFansRewardStatus(int fansRewardStatus) {
		this.fansRewardStatus = fansRewardStatus;
		mark(FANS_REWARD_STATUS, fansRewardStatus);
	}

	public int getFansRewardLastTime() {
		return fansRewardLastTime;
	}

	public void setFansRewardLastTime(int fansRewardLastTime) {
		this.fansRewardLastTime = fansRewardLastTime;
		mark(FANS_REWARD_LAST_TIME, fansRewardLastTime);
	}

	public int getMissionRewardStatus() {
		return missionRewardStatus;
	}

	public void setMissionRewardStatus(int missionRewardStatus) {
		this.missionRewardStatus = missionRewardStatus;
		mark(MISSION_REWARD_STATUS, missionRewardStatus);
	}

	public int getMissionFinishStatus() {
		return missionFinishStatus;
	}

	public void setMissionFinishStatus(int missionFinishStatus) {
		this.missionFinishStatus = missionFinishStatus;
		mark(MISSION_FINISH_STATUS, missionFinishStatus);
	}

	public int getGoldModeUnlockStatus() {
		return goldModeUnlockStatus;
	}

	public void setGoldModeUnlockStatus(int goldModeUnlockStatus) {
		this.goldModeUnlockStatus = goldModeUnlockStatus;
		mark(GOLD_MODE_UNLOCK_STATUS, goldModeUnlockStatus);
	}

	public String getMonthGoldCardEndTime() {
		return monthGoldCardEndTime;
	}

	public void setMonthGoldCardEndTime(String monthGoldCardEndTime) {
		this.monthGoldCardEndTime = monthGoldCardEndTime;
		mark(MONTH_GOLD_CARD_END_TIME, monthGoldCardEndTime);
	}

	public String getMonthGoldCardLastRewardTime() {
		return monthGoldCardLastRewardTime;
	}

	public void setMonthGoldCardLastRewardTime(String monthGoldCardLastRewardTime) {
		this.monthGoldCardLastRewardTime = monthGoldCardLastRewardTime;
		mark(MONTH_GOLD_CARD_LAST_REWARD_TIME, monthGoldCardLastRewardTime);
	}

	public int getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(int isNewUser) {
		this.isNewUser = isNewUser;
		mark(IS_NEW_USER, isNewUser);
	}

	public int getPlayGameTimes() {
		return playGameTimes;
	}

	public void setPlayGameTimes(int playGameTimes) {
		this.playGameTimes = playGameTimes;
		mark(PLAY_GAME_TIMES, playGameTimes);
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ",rp_num=" + rpNum + "]";
    }

}
