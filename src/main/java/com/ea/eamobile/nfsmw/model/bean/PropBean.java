package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.model.Merchandise;
import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class PropBean extends Merchandise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String des = "";
	private String name = "";
	private int priceType = 0;
	private int price = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriceType() {
		return priceType;
	}
	public void setPriceType(int priceType) {
		this.priceType = priceType;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("des", des);
			json.put("name", name);
			json.put("priceType", priceType);
			json.put("price", price);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	public static PropBean fromJson(String rewardStr) {
		if (rewardStr == null)
			return null;
		PropBean prop = new PropBean();
		JSONObject json = (JSONObject) JSONObject.stringToValue(rewardStr);
		
		prop.setId(CommonUtil.jsonGetInt(json, "id"));
		prop.setDes(CommonUtil.jsonGetString(json, "des"));
		prop.setName(CommonUtil.jsonGetString(json, "name"));
		prop.setPriceType(CommonUtil.jsonGetInt(json, "priceType"));
		prop.setPrice(CommonUtil.jsonGetInt(json, "price"));
		

		return prop;
	}
}
