package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class UserPropBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private long userId = 0;
	private int propId = 0;
	private int propCount = 0;
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
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getPropCount() {
		return propCount;
	}
	public void setPropCount(int propCount) {
		this.propCount = propCount;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("userId", userId);
			json.put("propId", propId);
			json.put("propCount", propCount);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static UserPropBean fromJson(String userPropStr) {
		if (userPropStr == null)
			return null;
		UserPropBean userProp = new UserPropBean();
		JSONObject json = null;
		try {
			json = new JSONObject(userPropStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userProp.setId(CommonUtil.jsonGetInt(json, "id"));
		userProp.setUserId(CommonUtil.jsonGetLong(json, "userId"));
		userProp.setPropId(CommonUtil.jsonGetInt(json, "propId"));
		userProp.setPropCount(CommonUtil.jsonGetInt(json, "propCount"));

		return userProp;
	}
}
