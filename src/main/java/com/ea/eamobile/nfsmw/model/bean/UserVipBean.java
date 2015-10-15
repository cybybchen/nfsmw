package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class UserVipBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private long userId = 0;
	private int vipId = 0;
	private String lastRewardTime = "";
	private String endTime = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getVipId() {
		return vipId;
	}
	public void setVipId(int vipId) {
		this.vipId = vipId;
	}
	public String getLastRewardTime() {
		return lastRewardTime;
	}
	public void setLastRewardTime(String lastRewardTime) {
		this.lastRewardTime = lastRewardTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("userId", userId);
			json.put("vipId", vipId);
			json.put("lastRewardTime", lastRewardTime);
			json.put("endTime", endTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static UserVipBean fromJson(String userVipStr) {
		if (userVipStr == null)
			return null;
		UserVipBean userVip = new UserVipBean();
		JSONObject json = null;
		try {
			json = new JSONObject(userVipStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userVip.setId(CommonUtil.jsonGetInt(json, "id"));
		userVip.setUserId(CommonUtil.jsonGetLong(json, "userId"));
		userVip.setVipId(CommonUtil.jsonGetInt(json, "vipId"));
		userVip.setLastRewardTime(CommonUtil.jsonGetString(json, "lastRewardTime"));
		userVip.setEndTime(CommonUtil.jsonGetString(json, "endTime"));

		return userVip;
	}
}
