package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class hangUpRankBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int index = 0;
	private List<RewardBean> rewardList = new ArrayList<RewardBean>();

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<RewardBean> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<RewardBean> rewardList) {
		this.rewardList = rewardList;
	}

	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("index", index);
			for (RewardBean reward : rewardList) {
				jsonArray.put(reward.toJsonObject());
			}
			json.put("rewardList", jsonArray);
		} catch (JSONException e) {
		}
		return json;
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("index", index);

			for (RewardBean reward : rewardList) {
				jsonArray.put(reward.toJsonObject());
			}
			json.put("rewardList", jsonArray);
		} catch (JSONException e) {
		}
		return json.toString();
	}

	public static hangUpRankBean fromJson(String hangUpRankString) {
		if (hangUpRankString == null)
			return null;
		hangUpRankBean hangUpRank = new hangUpRankBean();
		JSONObject json = null;
		try {
			json = new JSONObject(hangUpRankString);
		} catch (JSONException e1) {
		}
		hangUpRank.setIndex(CommonUtil.jsonGetInt(json, "index"));

		List<RewardBean> rewardList = new ArrayList<RewardBean>();
		JSONArray rewardArray = CommonUtil.jsonGetArray(json, "rewardList");
		for (int i = 0; i < rewardArray.length(); ++i) {
			String rewardStr = "";
			try {
				rewardStr = rewardArray.getString(i);
			} catch (JSONException e) {
			}
			RewardBean reward = RewardBean.fromJson(rewardStr);
			rewardList.add(reward);
		}
		hangUpRank.setRewardList(rewardList);

		return hangUpRank;
	}
}
