package com.ea.eamobile.nfsmw.model.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ea.eamobile.nfsmw.protoc.Commands.FleetRace;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardList;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardN;
import com.ea.eamobile.nfsmw.utils.CommonUtil;


public class hangUpRacesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int raceid = 0;
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
	private boolean useRefCar = false ;

	public FleetRace toFleetRace() {
		FleetRace.Builder fleetRace = FleetRace.newBuilder();
		fleetRace.setCarid(getCarDesc());
		fleetRace.setName(getName());
		fleetRace.setId(getId());
		fleetRace.setLimitCost(getNeedLimit());
		fleetRace.setEnergyCost(getNeedEnergy());
		fleetRace.setTime(getNeedTime());
		fleetRace.setPoints(getScore());
		fleetRace.setType(getId()/10);
		fleetRace.setCount(getCountByType(getId()/10)); 
		fleetRace.setState(getStatus());
		fleetRace.setCartype(1);// don't know
		fleetRace.setRemainTime(getFleetRaceRamainTime());
		fleetRace.setMyrank(getRank());
		fleetRace.setType((getId() / 10) % 2);
		fleetRace.setTier(((getId() / 10) + 1) >> 1);
		// fleetRace.addAllMyrewards(rewardList);// TODO
		List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
		for (hangUpRankBean hangUpRank : getHangUpRankList()) {
			RewardList.Builder rewardListBuilder = RewardList.newBuilder();
			List<RewardN> rewardList = new ArrayList<RewardN>();
			rewardListBuilder.setName("" + hangUpRank.getIndex());
			for (RewardBean hangupreward : hangUpRank.getRewardList()) {
				RewardN.Builder reward = RewardN.newBuilder();
				reward.setId(hangupreward.getRewardId());
				reward.setCount(hangupreward.getRewardCount());
				rewardList.add(reward.build());
			}
			rewardListBuilder.addAllRewards(rewardList);
			rewardBuilderList.add(rewardListBuilder.build());
		}
		fleetRace.addAllRewards(rewardBuilderList);
		return fleetRace.build();
	}
	
	public boolean isUseRefCar() {
		return useRefCar;
	}

	public void setUseRefCar(boolean useRefCar) {
		this.useRefCar = useRefCar;
	}

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
		return raceid;
	}

	public void setId(int raceid) {
		this.raceid = raceid;
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
			json.put("raceid", raceid);
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
			json.put("raceid", raceid);
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
			e.printStackTrace();
		}
		hangUpRaces.setId(CommonUtil.jsonGetInt(json, "raceid"));
		hangUpRaces.setCarDesc(CommonUtil.jsonGetString(json, "carDesc"));
		hangUpRaces.setName(CommonUtil.jsonGetString(json, "name"));
		hangUpRaces.setNeedTime(CommonUtil.jsonGetInt(json, "needTime"));
		hangUpRaces.setScore(CommonUtil.jsonGetInt(json, "score"));
		hangUpRaces.setNeedLimit(CommonUtil.jsonGetInt(json, "needLimit"));
		hangUpRaces.setNeedEnergy(CommonUtil.jsonGetInt(json, "needEnergy"));
		hangUpRaces.setStatus(CommonUtil.jsonGetInt(json, "status"));
		hangUpRaces.setStartTime(CommonUtil.jsonGetString(json, "startTime"));
		hangUpRaces.setRank(CommonUtil.jsonGetInt(json, "rank"));
		
		List<hangUpRankBean> hangUpRankList = new ArrayList<hangUpRankBean>();
		JSONArray hangUpRankArray = CommonUtil.jsonGetArray(json, "hangUpRankList");
		for (int i = 0; i < hangUpRankArray.length(); i++) {
			String rankstring = "";
			try {
				rankstring = hangUpRankArray.getString(i);
			} catch (JSONException e) {
			}
			hangUpRankBean hangUpRank = hangUpRankBean.fromJson(rankstring);
			hangUpRankList.add(hangUpRank);
		}
		hangUpRaces.setHangUpRankList(hangUpRankList);

		return hangUpRaces;
	}
	
	public int getFleetRaceRamainTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date firsttime = new Date();
		try {
			String strDate = getStartTime();
			firsttime = sdf.parse(strDate);
		} catch (Exception e1) {
		}
		SimpleDateFormat mdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = mdf.format(new Date());
		Date thistime =  new Date();
		try {
			thistime = sdf.parse(now);
		} catch (Exception e) {
		}
		int time = (int) (firsttime.getTime() - thistime.getTime())/1000+getNeedTime();
		return time;
	}
	
	private int getCountByType(int type) {
		if ((type % 2) == 0) {
			return 5;
		}
		return 3;
	}
}
