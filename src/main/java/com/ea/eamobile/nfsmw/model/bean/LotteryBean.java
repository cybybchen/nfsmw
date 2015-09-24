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
	private List<LotteryRewardBean> lotteryRewardList = new ArrayList<LotteryRewardBean>();
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
	public List<LotteryRewardBean> getLotteryRewardList() {
		return lotteryRewardList;
	}
	public void setLotteryRewardList(List<LotteryRewardBean> lotteryRewardList) {
		this.lotteryRewardList = lotteryRewardList;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("weight", weight);
			json.put("lotteryRewardList", lotteryRewardList);
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
		JSONObject json = (JSONObject) JSONObject.stringToValue(lotteryStr);
		
		lottery.setId(CommonUtil.jsonGetInt(json, "id"));
		lottery.setName(CommonUtil.jsonGetString(json, "name"));
		lottery.setWeight(CommonUtil.jsonGetInt(json, "weight"));
		
		List<LotteryRewardBean> rewardList = new ArrayList<LotteryRewardBean>();
		JSONArray lotteryRewardArray = CommonUtil.jsonGetArray(json, "lotteryRewardList");
		for (int i = 0;i < lotteryRewardArray.length(); ++i) {
			String rewardStr = "";
			try {
				rewardStr = lotteryRewardArray.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LotteryRewardBean reward = LotteryRewardBean.fromJson(rewardStr);
			rewardList.add(reward);
		}
		lottery.setLotteryRewardList(rewardList);

		return lottery;
	}
}
