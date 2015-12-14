package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.utils.CommonUtil;

public class hangUpBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int type = 0;
	private int  tie = 0;
	private List<hangUpRacesBean> hangUpRacesList = new ArrayList<hangUpRacesBean>();


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	public int getTie() {
		return tie;
	}

	public void setTie(int tie) {
		this.tie = tie;
	}

	public List<hangUpRacesBean> getHangUpRacesList() {
		return hangUpRacesList;
	}

	public void setHangUpRacesList(List<hangUpRacesBean> hangUpRacesList) {
		this.hangUpRacesList = hangUpRacesList;
	}
	public String toString() {
		return toJson();
	}
	public String toJson() {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			json.put("type", type);
			json.put("title", tie);
			for (hangUpRacesBean hangUpRaces : hangUpRacesList) {
				jsonArray.put(hangUpRaces.toJsonObject());
			}
			json.put("hangUpRacesList", jsonArray);
		} catch (JSONException e) {
		}
		return json.toString();
	}

	public static hangUpBean fromJson(String hangUpString) {
		if (hangUpString == null)
			return null;
		hangUpBean hangUp = new hangUpBean();
		JSONObject json = null;
		try {
			json = new JSONObject(hangUpString);
		} catch (JSONException e) {
		}
		hangUp.setType(CommonUtil.jsonGetInt(json, "type"));
		hangUp.setTie(CommonUtil.jsonGetInt(json, "tie"));

		List<hangUpRacesBean> hangUpRacesList = new ArrayList<hangUpRacesBean>();
		JSONArray hangupracesArray = CommonUtil.jsonGetArray(json, "hangUpRacesList");
		for (int i = 0; i < hangupracesArray.length(); i++) {
			String hangUpRacesStr = "";
			try {
				hangUpRacesStr = hangupracesArray.getString(i);
			} catch (JSONException e) {
			}
			hangUpRacesBean hangUpRaces = hangUpRacesBean.fromJson(hangUpRacesStr);
			hangUpRacesList.add(hangUpRaces);
		}
		hangUp.setHangUpRacesList(hangUpRacesList);

		return hangUp;
	}
}
