package com.ea.eamobile.nfsmw.service.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDataHelper;

public class UserDataHelper extends User implements GeneralDataHelper {

	private static final long serialVersionUID = 1L;

	public UserDataHelper() {

	}

	public UserDataHelper(User data) {
		this.id = data.getId();
		this.name = data.getName();
		this.eol = data.getEol();
		this.level = data.getLevel();
		this.money = data.getMoney();
		this.energy = data.getEnergy();
		this.headUrl = data.getHeadUrl();

		this.headIndex = data.getHeadIndex();
		this.isNicknameChanged = data.getIsNicknameChanged();
		this.tier = data.getTier();
		this.dailyRewardDate = data.getDailyRewardDate();
		this.starNum = data.getStarNum();
		this.lastWinOrNot = data.getLastWinOrNot();
		this.duraMatchNum = data.getDuraMatchNum();
		this.maxMatchWinNum = data.getMaxMatchWinNum();
		this.maxMatchLoseNum = data.getMaxMatchLoseNum();
		this.lastRegainEnergyDate = data.getLastRegainEnergyDate();
		this.rpNum = data.getRpNum();
		this.gold = data.getGold();
		this.createTime = data.getCreateTime();
		this.isWriteJaguar = data.getIsWriteJaguar();
		this.willowtreeToken = data.getWillowtreeToken();
		this.version = data.getVersion();
		this.isRaceStart = data.getIsRaceStart();
		this.isOldUser = data.getIsOldUser();
		this.eventOptionVersion = data.getEventOptionVersion();
		this.isGetTutorialReward = data.getIsGetTutorialReward();
		this.isRewardedBind = data.getIsRewardedBind();
		this.tierStatus = data.getTierStatus();
		this.certType = data.getCertType();
		this.accountStatus = data.getAccountStatus();
		this.accessToken = data.getAccessToken();
		this.uid = data.getUid();
		this.lastSendEnergyDate = data.getLastSendEnergyDate();
		this.packageBuyRecord = data.getPackageBuyRecord();
		this.vipEndTime = data.getVipEndTime();
		this.vipLastRewardTime = data.getVipLastRewardTime();
		this.fansRewardStatus = data.getFansRewardStatus();
		this.fansRewardLastTime = data.getFansRewardLastTime();
		this.missionRewardStatus = data.getMissionRewardStatus();
		this.missionFinishStatus = data.getMissionFinishStatus();
		this.goldModeUnlockStatus = data.getGoldModeUnlockStatus();
		this.monthGoldCardEndTime = data.getMonthGoldCardEndTime();
		this.monthGoldCardLastRewardTime = data.getMonthGoldCardLastRewardTime();
		this.monthGoldCard2EndTime = data.getMonthGoldCard2EndTime();
		this.monthGoldCard2LastRewardTime = data.getMonthGoldCard2LastRewardTime();
		this.isNewUser = data.getIsNewUser();
		this.playGameTimes = data.getPlayGameTimes();
		this.lotteryTimes = data.getLotteryTimes();
		this.rpExpWeek = data.getRpExpWeek();
		this.refreshTime = data.getRefreshTime();
		setMarks(data.getMarks());
	}

	@Override
	public void parseDbData(ResultSet rs) throws SQLException {
		this.id = rs.getLong("id");
		this.name = rs.getString("name");
		this.eol = rs.getInt("eol");
		this.level = rs.getInt("level");
		this.money = rs.getInt("money");
		this.energy = rs.getInt("energy");
		this.headUrl = rs.getString("head_url");
		this.headIndex = rs.getInt("head_index");
		this.isNicknameChanged = rs.getBoolean("is_nickname_changed");
		this.tier = rs.getInt("tier");
		this.dailyRewardDate = rs.getDate("daily_reward_date");
		this.starNum = rs.getInt("star_num");
		this.lastWinOrNot = rs.getInt("last_win_or_not");
		this.duraMatchNum = rs.getInt("dura_match_num");
		this.maxMatchWinNum = rs.getInt("max_match_win_num");
		this.maxMatchLoseNum = rs.getInt("max_match_lose_num");
		this.lastRegainEnergyDate = rs.getInt("last_regain_energy_date");
		this.rpNum = rs.getInt("rp_num");
		this.gold = rs.getInt("gold");
		this.createTime = rs.getInt("create_time");
		this.isWriteJaguar = rs.getInt("is_write_jaguar");
		this.willowtreeToken = rs.getString("willowtree_token");
		this.version = rs.getInt("version");
		this.isRaceStart = rs.getInt("is_race_start");
		this.isOldUser = rs.getInt("is_old_user");
		this.eventOptionVersion = rs.getInt("event_option_version");
		this.isGetTutorialReward = rs.getInt("is_get_tutorial_reward");
		this.isRewardedBind = rs.getInt("is_rewarded_bind");
		this.tierStatus = rs.getInt("tier_status");
		this.certType = rs.getInt("cert_type");
		this.accountStatus = rs.getInt("account_status");
		this.accessToken = rs.getString("access_token");
		this.uid = rs.getString("uid");
		this.lastSendEnergyDate = rs.getInt("last_send_energy_date");
		this.packageBuyRecord = rs.getInt("package_buy_record");
		this.vipEndTime = rs.getString("vip_end_time");
		this.vipLastRewardTime = rs.getString("vip_last_reward_time");
		this.fansRewardStatus = rs.getInt("fans_reward_status");
		this.fansRewardLastTime = rs.getInt("fans_reward_last_time");
		this.missionRewardStatus = rs.getInt("mission_reward_status");
		this.missionFinishStatus = rs.getInt("mission_finish_status");
		this.goldModeUnlockStatus = rs.getInt(GOLD_MODE_UNLOCK_STATUS);
		this.monthGoldCardEndTime = rs.getString(MONTH_GOLD_CARD_END_TIME);
		this.monthGoldCardLastRewardTime = rs.getString(MONTH_GOLD_CARD_LAST_REWARD_TIME);
		this.monthGoldCard2EndTime = rs.getString(MONTH_GOLD_CARD2_END_TIME);
		this.monthGoldCard2LastRewardTime = rs.getString(MONTH_GOLD_CARD2_LAST_REWARD_TIME);
		this.isNewUser = rs.getInt(IS_NEW_USER);
		this.playGameTimes = rs.getInt(PLAY_GAME_TIMES);
		this.lotteryTimes = rs.getInt(LOTTERY_TIMES);
		this.rpExpWeek = rs.getInt(RPEXPWEEK);
		this.refreshTime = rs.getString(REFRESHTIME);
	}

	@Override
	public Map<String, String> getDataMap() {
		return demark();
	}

	@Override
	public void addDefaultDataMap(Map<String, String> dataMap) {

		if (!dataMap.containsKey(STAR_NUM)) {
			dataMap.put(STAR_NUM, "0");
		}
		if (!dataMap.containsKey(LAST_WIN_OR_NOT)) {
			dataMap.put(LAST_WIN_OR_NOT, "0");
		}
		if (!dataMap.containsKey(DURA_MATCH_NUM)) {
			dataMap.put(DURA_MATCH_NUM, "0");
		}
		if (!dataMap.containsKey(MAX_MATCH_WIN_NUM)) {
			dataMap.put(MAX_MATCH_WIN_NUM, "0");
		}
		if (!dataMap.containsKey(MAX_MATCH_LOSE_NUM)) {
			dataMap.put(MAX_MATCH_LOSE_NUM, "0");
		}
		if (!dataMap.containsKey(RP_NUM)) {
			dataMap.put(RP_NUM, "0");
		}
		if (!dataMap.containsKey(GOLD)) {
			dataMap.put(GOLD, "0");
		}
		if (!dataMap.containsKey(CREATE_TIME)) {
			dataMap.put(CREATE_TIME, "0");
		}
		if (!dataMap.containsKey(VERSION)) {
			dataMap.put(VERSION, "0");
		}

	}

	@Override
	public void deleteIgnoreDataMap(Map<String, String> dataMap) {
		if (dataMap.containsKey(ID)) {
			dataMap.remove(ID);
		}
	}

}
