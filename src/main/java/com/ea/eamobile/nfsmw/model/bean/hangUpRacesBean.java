package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;

public class hangUpRacesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String name = "";
	private String carDesc = "";
	private int needTime = 0;
	private int score = 0;
	private int needLimit = 0;
	private int needEnergy = 0;
	private int status = 0;
	private List<hangUpRankBean> hangUpRankList = new ArrayList<hangUpRankBean>();
	private String startTime = ""; 
	private int rank = 0;
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

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

	public String getCarDesc() {
		return carDesc;
	}

	public void setCarDesc(String carDesc) {
		this.carDesc = carDesc;
	}

	public int getNeedTime() {
		return needTime;
	}

	public void setNeedTime(int needTime) {
		this.needTime = needTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNeedLimit() {
		return needLimit;
	}

	public void setNeedLimit(int needLimit) {
		this.needLimit = needLimit;
	}

	public int getNeedEnergy() {
		return needEnergy;
	}

	public void setNeedEnergy(int needEnergy) {
		this.needEnergy = needEnergy;
	}

	public List<hangUpRankBean> getHangUpRankList() {
		return hangUpRankList;
	}

	public void setHangUpRankList(List<hangUpRankBean> hangUpRankList) {
		this.hangUpRankList = hangUpRankList;
	}

	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("carDesc", carDesc);
			json.put("needTime", needTime);
			json.put("needLimit", needLimit);
			json.put("needEnergy", needEnergy);
			json.put("score", score);
			json.put("status", status);
			json.put("startTime", startTime);
			json.put("rank",rank);
			for (hangUpRankBean hangUpRank : hangUpRankList) {
				jsonArray.put(hangUpRank.toJsonObject());
			}
			json.put("hangUpRankList", jsonArray);
		} catch (JSONException e) {
		}
		return json;
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("id", id);
			json.put("name", name);
			json.put("carDesc", carDesc);
			json.put("needTime", needTime);
			json.put("needLimit", needLimit);
			json.put("needEnergy", needEnergy);
			json.put("score", score);
			json.put("status" ,status);
			json.put("startTime", startTime);
			json.put("rank", rank);
			for (hangUpRankBean hangUpRank : hangUpRankList) {
				jsonArray.put(hangUpRank.toJsonObject());
			}
			json.put("hangUpRankList", jsonArray);
		} catch (JSONException e) {
		}
		return json.toString();
	}

	public static hangUpRacesBean fromJson(String hangUpRacesString) {
		if (hangUpRacesString == null)
			return null;
		hangUpRacesBean hangUpRaces = new hangUpRacesBean();
		JSONObject json = null;
		try {
			json = new JSONObject(hangUpRacesString);
		} catch (JSONException e) {
		}
		hangUpRaces.setId(CommonUtil.jsonGetInt(json, "id"));
		hangUpRaces.setCarDesc(CommonUtil.jsonGetString(json, "carDesc"));
		hangUpRaces.setName(CommonUtil.jsonGetString(json, "name"));
		hangUpRaces.setNeedTime(CommonUtil.jsonGetInt(json, "needTime"));
		hangUpRaces.setScore(CommonUtil.jsonGetInt(json, "score"));
		hangUpRaces.setNeedLimit(CommonUtil.jsonGetInt(json, "needLimit"));
		hangUpRaces.setNeedEnergy(CommonUtil.jsonGetInt(json, "needEnergy"));
		hangUpRaces.setNeedEnergy(CommonUtil.jsonGetInt(json, "status"));
		hangUpRaces.setName(CommonUtil.jsonGetString(json, "startTime"));
		hangUpRaces.setRank(CommonUtil.jsonGetInt(json, "rank"));
		
		List<hangUpRankBean> hangUpRankList = new ArrayList<hangUpRankBean>();
		JSONArray hangUpRankArray = CommonUtil.jsonGetArray(json, "hangUpRankList");
		for (int i = 0; i < hangUpRankArray.length(); i++) {
			String rankstring = "";
			try {
				rankstring = hangUpRankArray.toString(i);
			} catch (JSONException e) {
			}
			hangUpRankBean hangUpRank = hangUpRankBean.fromJson(rankstring);
			hangUpRankList.add(hangUpRank);
		}
		hangUpRaces.setHangUpRankList(hangUpRankList);

		return hangUpRaces;
	}
}
