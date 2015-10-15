package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class TaskBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	private String des = "";
	private int type = 0;
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<RewardBean> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<RewardBean> rewardList) {
		this.rewardList = rewardList;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("des", des);
			json.put("type", type);
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
	public static TaskBean fromJson(String taskStr) {
		if (taskStr == null)
			return null;
		TaskBean task = new TaskBean();
		JSONObject json = null;
		try {
			json = new JSONObject(taskStr);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		task.setId(CommonUtil.jsonGetInt(json, "id"));
		task.setName(CommonUtil.jsonGetString(json, "name"));
		task.setDes(CommonUtil.jsonGetString(json, "des"));
		task.setType(CommonUtil.jsonGetInt(json, "type"));
		
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
		task.setRewardList(rewardList);

		return task;
	}
}
