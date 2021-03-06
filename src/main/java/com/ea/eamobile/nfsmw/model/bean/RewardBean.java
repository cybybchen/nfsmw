package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class RewardBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int rewardId = 0;
	private int rewardCount = 0;
	private boolean isContinued = false;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public int getRewardCount() {
		return rewardCount;
	}
	public void setRewardCount(int rewardCount) {
		this.rewardCount = rewardCount;
	}
	public boolean isContinued() {
		return isContinued;
	}
	public void setContinued(boolean isContinued) {
		this.isContinued = isContinued;
	}
	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("rewardId", rewardId);
			json.put("rewardCount", rewardCount);
			json.put("isContinued", isContinued);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("rewardId", rewardId);
			json.put("rewardCount", rewardCount);
			json.put("isContinued", isContinued);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static RewardBean fromJson(String rewardStr) {
		if (rewardStr == null)
			return null;
		RewardBean reward = new RewardBean();
		JSONObject json = null;
		try {
			json = new JSONObject(rewardStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reward.setId(CommonUtil.jsonGetInt(json, "id"));
		reward.setRewardId(CommonUtil.jsonGetInt(json, "rewardId"));
		reward.setRewardCount(CommonUtil.jsonGetInt(json, "rewardCount"));
		reward.setContinued(CommonUtil.jsonGetBoolean(json, "isContinued"));
		

		return reward;
	}
}
