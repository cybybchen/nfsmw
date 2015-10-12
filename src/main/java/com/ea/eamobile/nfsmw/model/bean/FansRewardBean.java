package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class FansRewardBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	private float countdown = 0.f;
	private List<RewardBean> rewardList = new ArrayList<RewardBean>();
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
	public float getCountdown() {
		return countdown;
	}
	public void setCountdown(float countdown) {
		this.countdown = countdown;
	}
	public List<RewardBean> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<RewardBean> rewardList) {
		this.rewardList = rewardList;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("countdown", countdown);
			json.put("rewardList", rewardList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static FansRewardBean fromJson(String lotteryStr) {
		if (lotteryStr == null)
			return null;
		FansRewardBean lottery = new FansRewardBean();
		JSONObject json = (JSONObject) JSONObject.stringToValue(lotteryStr);
		
		lottery.setId(CommonUtil.jsonGetInt(json, "id"));
		lottery.setName(CommonUtil.jsonGetString(json, "name"));
		lottery.setCountdown(CommonUtil.jsonGetFloat(json, "countdown"));
		
		List<RewardBean> rewardList = new ArrayList<RewardBean>();
		JSONArray rewardArray = CommonUtil.jsonGetArray(json, "rewardList");
		for (int i = 0;i < rewardArray.length(); ++i) {
			String rewardStr = "";
			try {
				rewardStr = rewardArray.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RewardBean reward = RewardBean.fromJson(rewardStr);
			rewardList.add(reward);
		}
		lottery.setRewardList(rewardList);

		return lottery;
	}
}
