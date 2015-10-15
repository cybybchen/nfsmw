package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class LotteryBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	private int weight = 0;
	private int leastTimes = 0;
	private List<RewardBean> lotteryRewardList = new ArrayList<RewardBean>();
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getLeastTimes() {
		return leastTimes;
	}
	public void setLeastTimes(int leastTimes) {
		this.leastTimes = leastTimes;
	}
	public List<RewardBean> getLotteryRewardList() {
		return lotteryRewardList;
	}
	public void setLotteryRewardList(List<RewardBean> lotteryRewardList) {
		this.lotteryRewardList = lotteryRewardList;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("weight", weight);
			json.put("leastTimes", leastTimes);
			for (RewardBean reward : lotteryRewardList) {
				jsonArray.put(reward.toJsonObject());
			}
			json.put("lotteryRewardList", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static LotteryBean fromJson(String lotteryStr) {
		if (lotteryStr == null)
			return null;
		LotteryBean lottery = new LotteryBean();
		JSONObject json = null;
		try {
			json = new JSONObject(lotteryStr);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lottery.setId(CommonUtil.jsonGetInt(json, "id"));
		lottery.setName(CommonUtil.jsonGetString(json, "name"));
		lottery.setWeight(CommonUtil.jsonGetInt(json, "weight"));
		lottery.setLeastTimes(CommonUtil.jsonGetInt(json, "leastTimes"));
		
		List<RewardBean> rewardList = new ArrayList<RewardBean>();
		JSONArray lotteryRewardArray = CommonUtil.jsonGetArray(json, "lotteryRewardList");
		for (int i = 0;i < lotteryRewardArray.length(); ++i) {
			String rewardStr = "";
			try {
				rewardStr = lotteryRewardArray.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RewardBean reward = RewardBean.fromJson(rewardStr);
			rewardList.add(reward);
		}
		lottery.setLotteryRewardList(rewardList);

		return lottery;
	}
}
