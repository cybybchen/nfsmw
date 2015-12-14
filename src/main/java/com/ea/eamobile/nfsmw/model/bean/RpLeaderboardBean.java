package com.ea.eamobile.nfsmw.model.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.model.RpLeaderBoard;
import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class RpLeaderboardBean {
	private int rank = 0;
	private int headIndex = 0;
	private String headUrl = "";
	private String name = "";
	private int rpLevelWeek = 0;
	private int rpNumWeek = 0;
	private long userId = 0;

	public RpLeaderBoard toRpLeaderBoard() {
		RpLeaderBoard rpboard = new RpLeaderBoard();
		rpboard.setHeadIndex(headIndex);
		rpboard.setHeadUrl(headUrl);
		rpboard.setName(name);
		rpboard.setRpLevel(rpLevelWeek);
		rpboard.setRpNumWeek(rpNumWeek);
		rpboard.setUserId(userId);
		return rpboard;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRpLevelWeek() {
		return rpLevelWeek;
	}

	public void setRpLevelWeek(int rpLevelWeek) {
		this.rpLevelWeek = rpLevelWeek;
	}

	public int getRpNumWeek() {
		return rpNumWeek;
	}

	public void setRpNumWeek(int rpNumWeek) {
		this.rpNumWeek = rpNumWeek;
	}

	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("rank", rank);
			json.put("headIndex", headIndex);
			json.put("headUrl", headUrl);
			json.put("name", name);
			json.put("rpLevelWeek", rpLevelWeek);
			json.put("rpNumWeek", rpNumWeek);
			json.put("	userId", userId);
		} catch (JSONException e) {
		}
		return json;
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("rank", rank);
			json.put("headIndex", headIndex);
			json.put("headUrl", headUrl);
			json.put("name", name);
			json.put("rpLevelWeek", rpLevelWeek);
			json.put("rpNumWeek", rpNumWeek);
			json.put("userId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public static RpLeaderboardBean fromJson(String rpBoardStr) {
		if (rpBoardStr == null)
			return null;
		RpLeaderboardBean rpboard = new RpLeaderboardBean();
		JSONObject json = null;
		try {
			json = new JSONObject(rpBoardStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		rpboard.setRank(CommonUtil.jsonGetInt(json, "rank"));
		rpboard.setHeadIndex(CommonUtil.jsonGetInt(json, "headIndex"));
		rpboard.setHeadUrl(CommonUtil.jsonGetString(json, "hearUrl"));
		rpboard.setName(CommonUtil.jsonGetString(json, "name"));
		rpboard.setRpLevelWeek(CommonUtil.jsonGetInt(json, "rpLevelWeek"));
		rpboard.setRpNumWeek(CommonUtil.jsonGetInt(json, "rpNumWeek"));
		rpboard.setUserId(CommonUtil.jsonGetInt(json, "userId"));

		return rpboard;
	}

}
