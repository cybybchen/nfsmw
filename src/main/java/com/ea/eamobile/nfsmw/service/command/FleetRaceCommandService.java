package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRacesBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRankBean;
import com.ea.eamobile.nfsmw.protoc.Commands.FleetRace;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFixCarLimitCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetDoubleCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetEndCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetRaceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardList;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardN;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.redis.FleetRaceRedisService;
import com.ea.eamobile.nfsmw.utils.XmlUtil;
import com.ea.eamobile.nfsmw.view.CarChartletView;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class FleetRaceCommandService {

	@Autowired
	private UserInfoMessageService userInfoMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private RewardService rewardService;
	@Autowired
	private PushCommandService pushService;
	@Autowired
	private FleetRaceRedisService fleetRaceRedis;
	@Autowired
	private UserCarService userCarService;
	@Autowired
	private CarService carService;
	@Autowired
	protected UserChartletService userChartletService;

	private static final Logger log = LoggerFactory.getLogger(FleetRaceCommandService.class);
	private static final int LARGE_SCALE_NUM = 3;
	private static final int MINI_SCALE_NUM = 4;

	public ResponseFleetRaceCommand getFleetRaceCommand(User user) {
		ResponseFleetRaceCommand.Builder builder = ResponseFleetRaceCommand.newBuilder();
		builder.addAllRaces(getAllFleetRaceList(user));
		// builder.addRpLeaderboard();
		builder.setSelfRank(1);// TODO
		builder.setAnnounce("nihao");// TODO
		// builder.addAllRewards(getRankReward(user,id));// error??

		return builder.build();
	}

	public ResponseFleetStartCommand FleetRaceStartCommand(User user, int id, List<String> cars) throws SQLException {// list
																												// car
		ResponseFleetStartCommand.Builder builder = ResponseFleetStartCommand.newBuilder();
		builder.setId(id);
		builder.setResult(0);// TODO
		builder.setDisplayName("chenggong");
		long userId = user.getId();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());
		FleetRace.Builder fleetRace = FleetRace.newBuilder();
		hangUpBean hangup = fleetRaceRedis.getRacebyUserId(userId, id);
		List<hangUpRacesBean> hangupraceList = hangup.getHangUpRacesList();
		for (hangUpRacesBean hangUpRaces : hangupraceList) {
			int raceid = id % 10;
			if (hangUpRaces.getId() == raceid) {
				hangUpRaces.setStartTime(now);
				hangUpRaces.setRank(getMyrank(cars, 1, user));
				hangUpRaces.setStatus(1);
				fleetRace.setCarid(hangUpRaces.getCarDesc());
				fleetRace.setName(hangUpRaces.getName());
				fleetRace.setId(id);
				fleetRace.setLimitCost(hangUpRaces.getNeedLimit());
				fleetRace.setEnergyCost(hangUpRaces.getNeedEnergy());
				fleetRace.setTime(hangUpRaces.getNeedTime());
				fleetRace.setPoints(hangUpRaces.getScore());
				fleetRace.setType(hangup.getType());
				fleetRace.setCount(4); // 支持同时使用的车辆数
				fleetRace.setState(hangUpRaces.getStatus());// 赛事状态,ok
				fleetRace.setTier(getFleetRaceTier(hangup.getType()));// 赛道等级T1～4,ok
				fleetRace.setCartype(1);// 可以使用的跑车类型
				fleetRace.setRamainTime(getFleetRaceRamainTime(userId, raceid));// 剩余时间
				fleetRace.setMyrank(hangUpRaces.getRank());
				fleetRace.addAllMyrewards(getClosingReward());// TODO
				List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
				RewardList.Builder rewardListBuilder = RewardList.newBuilder();
				List<RewardN> rewardList = new ArrayList<RewardN>();
				for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
					rewardListBuilder.setName("" + hangUpRank.getIndex());
					for (RewardBean hangupreward : hangUpRank.getRewardList()) {
						RewardN.Builder reward = RewardN.newBuilder();
						reward.setId(hangupreward.getRewardId());
						reward.setCount(hangupreward.getRewardCount());
						rewardList.add(reward.build());
					}
				}
				rewardListBuilder.addAllRewards(rewardList);
				rewardBuilderList.add(rewardListBuilder.build());
				fleetRace.addAllRewards(rewardBuilderList);
			}
			fleetRaceRedis.addRacebyUserid(userId, raceid, hangup, hangup.getType());
		}
		builder.setRace(fleetRace);
		return builder.build();
	}

	public ResponseFleetEndCommand FleetRaceEndCommand(int id, boolean advanced, User user) {
		ResponseFleetEndCommand.Builder builder = ResponseFleetEndCommand.newBuilder();
		long userId = user.getId();
		builder.setId(id);
		builder.setResult(0);
		hangUpBean hangup = fleetRaceRedis.getRacebyUserId(userId, id);
		List<hangUpRacesBean> hangupraceList = hangup.getHangUpRacesList();
		for (hangUpRacesBean hangUpRaces : hangupraceList) {
			int raceid = id % 10;
			if (hangUpRaces.getId() == raceid) {
				builder.setRank(hangUpRaces.getRank());
			}
		}
		builder.addAllRewards(getClosingReward());
		builder.setDisplayName("yes");
		return builder.build();
	}

	public ResponseFleetDoubleCommand FleetRaceDoubleCommand(int id) {
		ResponseFleetDoubleCommand.Builder builder = ResponseFleetDoubleCommand.newBuilder();
		builder.setId(id);
		builder.setResult(0);
		builder.setDisplayName("ok");
		return builder.build();
	}

	public ResponseFixCarLimitCommand FixCarLimitCommand(String carId) {
		ResponseFixCarLimitCommand.Builder builder = ResponseFixCarLimitCommand.newBuilder();
		builder.setCarId(carId);
		builder.setResult(0);
		builder.setDisplayName("nice");
		return builder.build();
	}
	
	public ResponseProfileCarCommand ProfileCarCommand(User user){
		ResponseProfileCarCommand.Builder builder = ResponseProfileCarCommand.newBuilder();
		return builder.build();
	}

	public List<hangUpBean> getFleetRaceList() {
		List<hangUpBean> hangUpList = XmlUtil.getHangUpList();
		fleetRaceRedis.setFleetRaceList(hangUpList);
		return hangUpList;
	}

	private int getFleetRaceTier(int num) {
		int tier = 0;
		tier = (num + 1) >> 1;
		return tier;
	}

	private int getFleetRaceRamainTime(long userId, int id) {
		hangUpBean hangup = fleetRaceRedis.getRacebyUserId(userId, id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<hangUpRacesBean> hangupraceList = hangup.getHangUpRacesList();
		for (hangUpRacesBean hangUpRaces : hangupraceList) {
			if (hangUpRaces.getId() == id) {
				String strDate = hangUpRaces.getStartTime();
				Date firsttime = null;
				try {
					firsttime = sdf.parse(strDate);
				} catch (ParseException e1) {
				}
				SimpleDateFormat mdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String now = mdf.format(new Date());
				Date thistime = null;
				try {
					thistime = sdf.parse(now);
				} catch (ParseException e) {
				}
				int time = (int) (thistime.getTime() - firsttime.getTime());
				return time;
			}
		}
		return 0;
	}

	private int getMyrank(List<String> cars, int type, User user)  {
		long userId = user.getId();
		Iterator<String> iter = cars.iterator();  
		int totalFc = 0;
		while(iter.hasNext())  
		{  
		String carId =iter.next();
		UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
		CarView carView = null;
		User u = userService.getUser(userId);
		Car car = carService.getCar(carId);
		Map<String, UserCar> userCars = userCarService.getUserCarMap(userId);
		if (car != null) {
			carView = userCarService.buildCarView(car, userCars.get(carId), u, true);
		}
		int score = 0;
		score = carView.getScore();
		// 计算插槽
		score += userCarService.getCarSlotScore(userCar);
		// 计算喷图
		try {
			score += getPaintJobScore(userCar.getCarId(), userCar.getUserId());
		} catch (SQLException e) {
		}
		totalFc +=score;
		}
		int rank = getFleetRaceRank(totalFc, 1000);
		return rank;
	}

	private int getFleetRaceRank(int totalFC, int basicFC) {
		double roll;
		roll = Math.random() * 2;
		if (totalFC > 0) {
			int the1stPL = totalFC / basicFC;
			int the2ndPL = (int) (the1stPL * 1.1);
			int the3rdPL = (int) (the1stPL * 1.2);
			int the4thPL = (int) (the1stPL * 1.3);
			int the5thPL = (int) (the1stPL * 1.4);
			if (the1stPL >= 1) {
				return 1;
			}
			roll = Math.random() * 2;
			if (roll <= the1stPL) {
				return 1;
			}
			roll = Math.random() * 2;
			if (roll <= the2ndPL) {
				return 2;
			}
			roll = Math.random() * 2;
			if (roll <= the3rdPL) {
				return 3;
			}
			roll = Math.random() * 2;
			if (roll <= the4thPL) {
				return 4;
			}
			roll = Math.random() * 2;
			if (roll <= the5thPL) {
				return 5;
			}
			return 6;
		}
		return -1;
	}

	private List<RewardN> getClosingReward() {

		return null;
	}

	private List<RewardList> getRankReward(User user, int id) {
		long userId = user.getId();
		hangUpBean hangup = fleetRaceRedis.getRacebyUserId(userId, id);
		List<hangUpRacesBean> hangupraceList = hangup.getHangUpRacesList();
		for (hangUpRacesBean hangUpRaces : hangupraceList) {
			int raceid = id % 10;
			if (hangUpRaces.getId() == raceid) {
				int rank = hangUpRaces.getRank();
				List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
				RewardList.Builder rewardListBuilder = RewardList.newBuilder();
				List<RewardN> rewardList = new ArrayList<RewardN>();
				for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
					if (hangUpRank.getIndex() == rank) {
						rewardListBuilder.setName("" + hangUpRank.getIndex());
						for (RewardBean hangupreward : hangUpRank.getRewardList()) {
							RewardN.Builder reward = RewardN.newBuilder();
							reward.setId(hangupreward.getRewardId());
							reward.setCount(hangupreward.getRewardCount());
							rewardList.add(reward.build());
						}
					}
					rewardBuilderList.add(rewardListBuilder.build());
				}
				return rewardBuilderList;
			}
		}
		return null;
	}

	private FleetRace getRaceByID(int type, int id, User user) {
		long userId = user.getId();
		List<hangUpBean> hanupList = this.getFleetRaceList();
		for (hangUpBean hangup : hanupList) {
			if (hangup.getType() == type) {
				List<hangUpRacesBean> hangUpRacesList = hangup.getHangUpRacesList();
				for (hangUpRacesBean hangUpRaces : hangUpRacesList) {
					if (hangUpRaces.getId() == id) {
						FleetRace.Builder fleetRace = FleetRace.newBuilder();
						fleetRace.setCarid(hangUpRaces.getCarDesc());
						fleetRace.setName(hangUpRaces.getName());
						int raceid = hangup.getType() * 10 + id;
						fleetRace.setId(raceid);
						fleetRace.setLimitCost(hangUpRaces.getNeedLimit());
						fleetRace.setEnergyCost(hangUpRaces.getNeedEnergy());
						fleetRace.setTime(hangUpRaces.getNeedTime());
						fleetRace.setPoints(hangUpRaces.getScore());
						fleetRace.setType(hangup.getType());
						fleetRace.setCount(4);
						fleetRace.setState(0);
						fleetRace.setTier(getFleetRaceTier(hangup.getType()));
						fleetRace.setCartype(1);// don't know
						fleetRace.setRamainTime(0);
						fleetRace.setMyrank(0);
						// fleetRace.addAllMyrewards(rewardList);// TODO
						List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
						RewardList.Builder rewardListBuilder = RewardList.newBuilder();
						List<RewardN> rewardList = new ArrayList<RewardN>();
						for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
							rewardListBuilder.setName("" + hangUpRank.getIndex());
							for (RewardBean hangupreward : hangUpRank.getRewardList()) {
								RewardN.Builder reward = RewardN.newBuilder();
								reward.setId(hangupreward.getRewardId());
								reward.setCount(hangupreward.getRewardCount());
								rewardList.add(reward.build());
							}
						}
						rewardListBuilder.addAllRewards(rewardList);
						rewardBuilderList.add(rewardListBuilder.build());
						fleetRace.addAllRewards(rewardBuilderList);
						fleetRaceRedis.addRacebyUserid(userId, raceid, hangup, type);
						return fleetRace.build();
					}
				}
			}
		}
		return null;
	}

	private List<FleetRace> createFleetRace(int num, int type, int count, User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		Set<Integer> set = new HashSet<Integer>();
		log.debug("num:"+num);
		while (true) {
			set.add((int) (Math.random() * count + 1));
			if (set.size() == num)
				break;
		}
		log.debug("11111111111:"+set.size());
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
		log.debug("22222222222");
			int ite = it.next();
			FleetRace fleetrace = getRaceByID(type, ite, user);
			if (fleetrace != null) {
				log.debug("type:"+type+" iter:"+ite+" "+fleetrace.getName());
				fleetRaceList.add(fleetrace);
			}else{
				log.debug("fleetrace is null");
			}
		}
		return fleetRaceList;
	}

	private List<FleetRace> getAllFleetRaceList(User user) {
		int rpnum = user.getRpNum();
		log.debug(""+rpnum);
		if ( rpnum < 100)
			return getFleetRace_0(user);
		if (rpnum > 100 && rpnum < 500)
			return getFleetRace_100(user);
		if (rpnum > 500 && rpnum < 1000)
			return getFleetRace_500(user);
		if (rpnum > 1000 && rpnum < 3000)
			return getFleetRace_1000(user);
		if (rpnum > 3000 && rpnum < 6000)
			return getFleetRace_3000(user);
		if (rpnum > 6000 & rpnum < 10000)
			return getFleetRace_6000(user);
		if (rpnum > 10000)
			return getFleetRace_10000(user);
		else
			return getFleetRace_0(user);
	}

	private List<FleetRace> getFleetRace_0(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		int type = 1;
		int num = 1;
		int count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		type = 2;
		num = 1;
		count = LARGE_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_100(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_0(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 3;
		int num = 1;
		int count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_500(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_100(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 4;
		int num = 1;
		int count = LARGE_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		type = 5;
		num = 1;
		count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_1000(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_500(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 7;
		int num = 1;
		int count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_3000(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_1000(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 6;
		int num = 1;
		int count = LARGE_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		type = 8;
		num = 1;
		count = LARGE_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_6000(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_3000(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 1;
		int num = 1;
		int count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		type = 3;
		num = 1;
		count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	private List<FleetRace> getFleetRace_10000(User user) {
		List<FleetRace> fleetRaceList = new ArrayList<FleetRace>();
		List<FleetRace> minFleetRace = new ArrayList<FleetRace>();
		minFleetRace = getFleetRace_3000(user);
		fleetRaceList.addAll(minFleetRace);
		int type = 5;
		int num = 1;
		int count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		type = 7;
		num = 1;
		count = MINI_SCALE_NUM;
		minFleetRace = createFleetRace(num, type, count, user);
		fleetRaceList.addAll(minFleetRace);
		return fleetRaceList;
	}

	protected int getPaintJobScore(String carId, long userId) throws SQLException {
		int score = 0;
		List<CarChartletView> list = userChartletService.getChartletViewList(carId, userId);
		if (list != null && list.size() > 0) {
			for (CarChartletView view : list) {
				CarChartlet clet = view.getChartlet();
				if (view.isOwned()) {
					score += clet.getScore();
				}
			}
		}
		return score;
	}

}
