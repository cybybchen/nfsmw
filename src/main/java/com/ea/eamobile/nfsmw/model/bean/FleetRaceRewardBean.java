package com.ea.eamobile.nfsmw.model.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;


public class FleetRaceRewardBean {
	private static final long serialVersionUID = 1L;
	public int indexMin = 0;			
    public int indexMax = 0;			
    private List<RewardBean> rewardList = new ArrayList<RewardBean>();
	public int getIndexMin() {
		return indexMin;
	}
	public void setIndexMin(int indexMin) {
		this.indexMin = indexMin;
	}
	public int getIndexMax() {
		return indexMax;
	}
	public void setIndexMax(int indexMax) {
		this.indexMax = indexMax;
	}
	public List<RewardBean> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<RewardBean> rewardList) {
		this.rewardList = rewardList;
	}
    
	public String toJson(){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("indexMin", indexMin);
			json.put("indexMax", indexMax);
			for (RewardBean reward : rewardList) {
				jsonArray.put(reward.toJsonObject());
			}
			json.put("rewardList", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public static FleetRaceRewardBean fromJson(String lotteryStr) {
		if (lotteryStr == null)
			return null;
		FleetRaceRewardBean lottery = new FleetRaceRewardBean();
		JSONObject json = null;
		try {
			json = new JSONObject(lotteryStr);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lottery.setIndexMin(CommonUtil.jsonGetInt(json, "indexMin"));
		lottery.setIndexMin(CommonUtil.jsonGetInt(json, "indexMax"));
		
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
