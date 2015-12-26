package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.RpLeaderBoard;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.bean.FansRewardBean;
import com.ea.eamobile.nfsmw.model.bean.FleetRaceRewardBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.RpLeaderboardBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRacesBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRankBean;
import com.ea.eamobile.nfsmw.model.mapper.UserCarMapper;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.FleetRace;
import com.ea.eamobile.nfsmw.protoc.Commands.ProfileCarInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFixCarLimitCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetDoubleCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetEndCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetRaceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetRankRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFleetStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardList;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardN;
import com.ea.eamobile.nfsmw.protoc.Commands.RpLeaderboardMessage;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.RpLevelService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.service.redis.FleetRaceRedisService;
import com.ea.eamobile.nfsmw.utils.XmlUtil;
import com.ea.eamobile.nfsmw.view.CarChartletView;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class FleetRaceCommandService {

    @Autowired
    private UserCarMapper userCarMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private FleetRaceRedisService fleetRaceRedis;
	@Autowired
	private UserCarService userCarService;
	@Autowired
	private CarService carService;
	@Autowired
	protected UserChartletService userChartletService;
	@Autowired
    private RewardService rewardService;
    @Autowired
	private PushCommandService pushCommandService;
    @Autowired
    private MemcachedClient cache;
    @Autowired
    private BaseCommandService baseCommandService;
    @Autowired
    private RpLevelService levelService;

	private static final Logger log = LoggerFactory.getLogger(FleetRaceCommandService.class);


	public ResponseFleetRaceCommand getFleetRaceCommand(User user) {
		ResponseFleetRaceCommand.Builder builder = ResponseFleetRaceCommand.newBuilder();
		builder.addAllRaces(getAllFleetRaceList(user));
//		builder.addAllRpLeaderboard(values);
		builder.setSelfRank(1000);
		buildRpLeaderboardCommand(builder,getRpLeaderboard(),user);
		builder.setAnnounce("nihao");
		builder.addAllRewards(getTopListReward(user));
		builder.setCanReward(sendTopListReward(user));
		return builder.build();
	}

	public ResponseFleetStartCommand FleetRaceStartCommand(User user, int id, List<String> cars,
			Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
		ResponseFleetStartCommand.Builder builder = ResponseFleetStartCommand.newBuilder();
		builder.setId(id);
		builder.setResult(0);
		builder.setDisplayName("chenggong");
		long userId = user.getId();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());
		FleetRace.Builder fleetRace = FleetRace.newBuilder();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		fleetRace.setCarid(hangUpRaces.getCarDesc());
		fleetRace.setName(hangUpRaces.getName());
		fleetRace.setId(id);
		fleetRace.setLimitCost(hangUpRaces.getNeedLimit());
		fleetRace.setEnergyCost(hangUpRaces.getNeedEnergy());
		fleetRace.setTime(hangUpRaces.getNeedTime());
		fleetRace.setPoints(hangUpRaces.getScore());
		fleetRace.setType((id / 10)%2);
		fleetRace.setCount(getCountByType(id/10)); // 支持同时使用的车辆数
		fleetRace.setState(hangUpRaces.getStatus());// 赛事状态,ok
		fleetRace.setTier(getFleetRaceTier(id / 10));// 赛道等级T1～4,ok
		fleetRace.setCartype(1);// 可以使用的跑车类型
		fleetRace.setRemainTime(hangUpRaces.getFleetRaceRamainTime());// 剩余时间
		fleetRace.addAllMyrewards(getClosingReward(user,id));
		List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
		RewardList.Builder rewardListBuilder = RewardList.newBuilder();
		ArrayList<RewardN> rewardList = new ArrayList<RewardN>();
		modifyCarLimit(cars, user, hangUpRaces.getNeedLimit());
		modifyUserEnergy(user, hangUpRaces.getNeedEnergy());
		modifyCarStatus(cars,user,1,id);
		pushCommandService.pushUserCarInfoCommand(responseBuilder, getCarViewByCars(cars,user), userId);
		rewardListBuilder.addAllRewards(rewardList);
		rewardBuilderList.add(rewardListBuilder.build());
		fleetRace.addAllRewards(rewardBuilderList);

		hangUpRaces.setStartTime(now);
		hangUpRaces.setRank(getMyrank(cars, 1, user,hangUpRaces.getCarDesc(),hangUpRaces));
		fleetRace.setMyrank(hangUpRaces.getRank());
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			rewardListBuilder.setName("" + hangUpRank.getIndex());
			if( hangUpRank.getIndex() == fleetRace.getMyrank())
			{
				List<RewardBean> rewards =  hangUpRank.getRewardList();
				if(!cars.contains(fleetRace.getCarid() ))
				{
					Iterator<RewardBean> it = rewards.iterator();
					while(it.hasNext())
					{
						RewardBean reward = it.next();
						if(reward .getId()/1000 == 2  )
						{
							it.remove();
							break;
						}
					}
				}
				hangUpRaces.setMyrewards(rewards);
			}
			for (RewardBean hangupreward : hangUpRank.getRewardList()) {
				RewardN.Builder reward = RewardN.newBuilder();
				reward.setId(hangupreward.getRewardId());
				reward.setCount(hangupreward.getRewardCount());
				rewardList.add(reward.build());
			}
		}
		
		hangUpRaces.setStatus(1);
		fleetRaceRedis.addRacebyUserid(userId, id, hangUpRaces);
		builder.setRace(hangUpRaces.toFleetRace());
		return builder.build();
	}

	public ResponseFleetEndCommand FleetRaceEndCommand(int id, boolean advanced, User user) {
		ResponseFleetEndCommand.Builder builder = ResponseFleetEndCommand.newBuilder();
		long userId = user.getId();
		builder.setId(id);
		builder.setResult(0);
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		hangUpRaces.setStatus(2);
		fleetRaceRedis.addRacebyUserid(userId, id, hangUpRaces);
		builder.setRank(hangUpRaces.getRank());
		builder.addAllRewards(getClosingReward(user,id));
		List<RewardBean> rewardList = hangUpRaces.getMyrewards();
		rewardService.doRewardList(user, rewardList);
		fleetRaceRedis.addRankUser(createRpBoardUser(user));
		fleetRaceRedis.addFleetRaceRank(createRpBoardUser(user));
		List<String> cars = fleetRaceRedis.getUserCarStatus(user, id);
		for(int i=0;i<cars.size();i++){
			log.debug("cars is"+cars.get(i));
		}
		modifyCarStatus(cars, user, 0, id);
		builder.setDisplayName("yes");
		return builder.build();
	}

	public ResponseFleetDoubleCommand FleetRaceDoubleCommand(int id,User user) {
		ResponseFleetDoubleCommand.Builder builder = ResponseFleetDoubleCommand.newBuilder();
		long userId = user.getId();
		builder.setId(id);
		builder.setResult(0);
		builder.setDisplayName("ok");
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		hangUpRaces.setStatus(2);
		fleetRaceRedis.addRacebyUserid(userId, id, hangUpRaces);
		List<String> cars = fleetRaceRedis.getUserCarStatus(user, id);
		modifyCarStatus(cars, user, 0, id);
		builder.addAllRewards(getDoubleClosingReward(user,id));
		List<RewardBean> rewardList = getDoubleRankReward(user,id);
		rewardService.doRewardList(user, rewardList);
		int rank = 0;
		if(fleetRaceRedis.getFleetRaceRankFromRedis(user)==0){
			rank = 10000;
		}else{
			rank = fleetRaceRedis.getFleetRaceRankFromRedis(user);
		}
		builder.setRank(rank);
		return builder.build();
	}

	public ResponseFixCarLimitCommand FixCarLimitCommand(List<String> cars, User user,Commands.ResponseCommand.Builder responseBuilder) {
		ResponseFixCarLimitCommand.Builder builder = ResponseFixCarLimitCommand.newBuilder();
		Iterator<String> iter = cars.iterator();
		while (iter.hasNext()) {
			String carId = iter.next();
			builder.setCarId(carId);
		}
		FixCarLimit(cars, user);
		try {
			pushCommandService.pushUserCarInfoCommand(responseBuilder, getCarViewByCars(cars,user), user.getId());
		} catch (SQLException e) {

			e.printStackTrace();
		}
		builder.setResult(0);
		builder.setDisplayName("nice");
		return builder.build();
	}
	
	public ResponseProfileCarCommand ProfileCarCommand(long userId, Commands.ResponseCommand.Builder responseBuilder) {
		ResponseProfileCarCommand.Builder builder = ResponseProfileCarCommand.newBuilder();
		List<ProfileCarInfo> ProfileCarInfoList = new ArrayList<ProfileCarInfo>();
		List<CarView> carViewList = getUserBestCarView(userId);
//		Iterator<CarView> iter = carViewList.iterator();
		log.debug("1111111111111111111111111111");
		for (int i = 0; i < carViewList.size(); i++) {
//		while (iter.hasNext()) {
			log.debug("222222222222222222222222");
//			String carId = iter.next().getCarId();
			String carId = carViewList.get(i).getCarId();
			log.debug("carId is :"+carId);
			CarView carView = userCarService.getUserCarView(userId, carId);
			UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
			List<ProfileCarInfo> carInfoList = new ArrayList<ProfileCarInfo>();
			ProfileCarInfo.Builder carInfo = ProfileCarInfo.newBuilder();
			if (userCar == null) {// if userCar == null: player don't unlock any
									// car
				carInfo = baseCommandService.buildProfileCarInfo();
			} else {
				try {
					carInfo = baseCommandService.buildProfileCarInfo(userId, userCar, carView);
					log.debug("carinfo carid is :" + carInfo.getCarId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			carInfoList.add(carInfo.build());
			ProfileCarInfoList.addAll(carInfoList);
		}
		builder.addAllCarInfos(ProfileCarInfoList);
		return builder.build();
	}
	
	public ResponseFleetRankRewardCommand  FleetRankRewardCommand(User user,Commands.ResponseCommand.Builder responseBuilder){
		ResponseFleetRankRewardCommand.Builder builder = ResponseFleetRankRewardCommand.newBuilder();
		builder.setRank( fleetRaceRedis.getFleetRaceRankFromRedis(user));
		//succeed --1 fail--0
		builder.setDisplayName("keyi");
		builder.addAllRewards(sendFleetRaceRewardsList(user,builder));
		return builder.build();
	}
	

	public List<hangUpBean> getFleetRaceList() {
		List<hangUpBean> hangUpList = XmlUtil.getHangUpList();//TODO redis
		for (hangUpBean hangup : hangUpList) {
			fleetRaceRedis.setFleetRace(hangup);
		}
		return hangUpList;
	}
	
	public List<FleetRaceRewardBean> getFleetRaceReawardList(){//TODO redis
		List<FleetRaceRewardBean> fleetracerewardlist = XmlUtil.getFleetRaceRewardList();
		for(FleetRaceRewardBean fleetracereward : fleetracerewardlist ){
			fleetRaceRedis.setFleetRankReward(fleetracereward);
		}
		return fleetracerewardlist;
	}
	
	private List<RewardN> sendFleetRaceRewardsList(User user, ResponseFleetRankRewardCommand.Builder builder) {
		int rank = fleetRaceRedis.getFleetRaceRankFromRedis(user);
		if (rank == 0) {
			List<RewardN> rewardList = new ArrayList<RewardN>();
			builder.setRank(10000);
			builder.setResult(0);
			return rewardList;
		} else {
			List<RewardN> rewardList = new ArrayList<RewardN>();
			List<FleetRaceRewardBean> fleetracerewardlist = XmlUtil.getFleetRaceRewardList();
			List<RewardBean> topListRewardList = new ArrayList<RewardBean>();
			for (FleetRaceRewardBean FleetRaceReward : fleetracerewardlist) {
				int rankIndexMin = FleetRaceReward.getIndexMin();
				int rankIndexMax = FleetRaceReward.getIndexMax();
				while (rank >= rankIndexMin && rank <= rankIndexMax) {
					topListRewardList = FleetRaceReward.getRewardList();
					for (RewardBean topListReward : topListRewardList) {
						RewardN.Builder reward = RewardN.newBuilder();
						reward.setId(topListReward.getRewardId());
						reward.setCount(topListReward.getRewardCount());
						rewardList.add(reward.build());
					 }
				break;
				}
			}
			fleetRaceRedis.addPAId(user.getId(), 1);
			rewardService.doRewardList(user, topListRewardList);
			user.setAccFleetRaceReward(1);
			builder.setResult(1);
			return rewardList;
		}
	}
	
	private List<RpLeaderBoard> getRpLeaderboard(){
		List<RpLeaderboardBean> topUserList = fleetRaceRedis.getRank(0, 99);
		List<RpLeaderBoard> list = new ArrayList<RpLeaderBoard>();
		for (int i = 0; i < topUserList.size(); i++) {
			RpLeaderboardBean rpboard = topUserList.get(i);
			list.add(rpboard.toRpLeaderBoard());
		}
		return list;
	}
	
	private void buildRpLeaderboardCommand(ResponseFleetRaceCommand.Builder builder,
			List<RpLeaderBoard> rpLeaderBoardList, User user) {
		List<RpLeaderboardMessage> list = new ArrayList<Commands.RpLeaderboardMessage>();
		long userId = user.getId();
		if (rpLeaderBoardList != null && rpLeaderBoardList.size() > 0) {
			for (int i = 0; i < rpLeaderBoardList.size(); i++) {
				RpLeaderboardMessage.Builder msgBuilder = RpLeaderboardMessage.newBuilder();
				RpLeaderBoard rpLeaderBoard = rpLeaderBoardList.get(i);
				msgBuilder.setHeadIndex(rpLeaderBoard.getHeadIndex());
				msgBuilder.setHeadUrl(rpLeaderBoard.getHeadUrl());
				msgBuilder.setName(rpLeaderBoard.getName());
				msgBuilder.setUserId(rpLeaderBoard.getUserId());
				msgBuilder.setRank(i + 1);
				msgBuilder.setRpLevel(rpLeaderBoard.getRpLevel());
				msgBuilder.setRpNum(rpLeaderBoard.getRpNum());
				msgBuilder.setWeekrpNum(rpLeaderBoard.getRpExpWeek());
				user.setRpExpWeek(rpLeaderBoard.getRpExpWeek());
				userService.updateUser(user);
				list.add(msgBuilder.build());
				if (userId == rpLeaderBoard.getUserId()) {
					builder.setSelfRank(i + 1);
				}
			}
		}
		builder.addAllRpLeaderboard(list);
	}
	
	private RpLeaderboardBean createRpBoardUser(User user){
		RpLeaderboardBean rpboard = new RpLeaderboardBean();
		rpboard.setHeadIndex(user.getHeadIndex());
		rpboard.setHeadUrl(user.getHeadUrl());
		rpboard.setName(user.getName());
		rpboard.setRpExpWeek(user.getRpExpWeek());
		rpboard.setRpLevelWeek(0);
		rpboard.setUserId(user.getId());
		RpLevel currentLevel = levelService.getLevel(user.getLevel());
        int exp = currentLevel != null ? user.getRpNum() - currentLevel.getRpNum() : user.getRpNum();
		rpboard.setRpNum(exp);
		return rpboard;
	}
	
	private int getFleetRaceTier(int num) {
		int tier = 0;
		tier = (num + 1) >> 1;
		return tier;
	}
	
	private void modifyUserEnergy(User user, int needenergy) {
		int energy = user.getEnergy() - needenergy;
		user.setEnergy(energy);
		userService.updateUser(user);
	}
	
	private boolean sendTopListReward(User user){
		long userId = user.getId();
		int state = 0;
		state = fleetRaceRedis.getPaRewardStatus(userId);
		if(state == 0){
			return true;
		}else
			return false;
	}
	
	private void FixCarLimit(List<String>cars,User user){
		long userId = user.getId();
		Iterator<String> iter = cars.iterator();
		while(iter.hasNext())  
		{  
		int money = 0;
		String carId =iter.next();
		UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
		Car car = carService.getCar(userCar.getCarId());
		int tie = car.getTier();
			switch (tie) {
			case 1:
				money = (user.getMoney() - 40 * (100 - userCar.getLimit()));
				break;
			case 2:
				money = (int) (user.getMoney() - 40 * 1.4 * (100 - userCar.getLimit()));
				break;
			case 3:
				money = (int) (user.getMoney() - 40 * 1.8 * (100 - userCar.getLimit()));
				break;
			case 4:
				money = (int) (user.getMoney() - 40 * 2.5 * (100 - userCar.getLimit()));
				break;
			default:
				money = (user.getMoney() - 40 * (100 - userCar.getLimit()));
				break;
			}
		user.setMoney(money);
		userCar.setLimit(100);
		userCarService.update(userCar);
		userService.updateUser(user);
		}
	}
	
	private void modifyCarStatus(List<String>cars,User user ,int Status,int id){//TODO
		long userId = user.getId();
		Iterator<String> iter = cars.iterator();
		while(iter.hasNext())  
		{  
		String carId =iter.next();
		UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
		userCar.setStates(Status);
		userCarService.update(userCar);
		}
		fleetRaceRedis.userCarStatus(user, cars, id);
	}
	
   private void modifyCarLimit(List<String>cars,User user,int needlimit){
	   long userId = user.getId();
		Iterator<String> iter = cars.iterator();
		while(iter.hasNext())  
		{  
		int limit = 0;
		String carId =iter.next();
		UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
		limit = userCar.getLimit() - needlimit;
			if (limit < 0) {
				limit = 0;
			}
		userCar.setLimit(limit);
			userCarService.update(userCar);
		}
   }
		
   
   public List<CarView> getUserBestCarView(long userId) {
//		String carId = (String) cache.get(CacheKey.USER_BEST_CAR + userId);
	   String carId = null;
		CarView carview = new CarView();
		List<CarView> carViewList = new ArrayList<CarView>();
		UserCar theBestUserCar = new UserCar();
		if (carId == null) {
			List<UserCar> ucList = getUserCarList(userId);
			User u = userService.getUser(userId);
			for (int i = 0; i < ucList.size(); i++) {
				int score = 0;
				for (UserCar ucTmp : ucList) {
					Car car = carService.getCar(ucTmp.getCarId());
					if (userCarService.isUserCarOwned(car, ucTmp, u) && ucTmp.getScore() > score) {
						score = ucTmp.getScore();
						carId = ucTmp.getCarId();
						theBestUserCar = ucTmp;
					}
				}
				carview = userCarService.getUserCarView(userId, carId);
				carViewList.add(carview);
				ucList.remove(theBestUserCar);
				if (i == 6)
					break;
			}
//			cache.set(CacheKey.USER_BEST_CAR + userId, carId, MemcachedClient.MIN);
		}
		if (carId == null) {
			carId = "dodge_challenger_srt8_392_2011_desc";
		}
		return carViewList;
	}
   
   private List<CarView> getCarViewByCars(List<String>cars,User user){
	   long userId = user.getId();
		Iterator<String> iter = cars.iterator();  
		List<CarView> carviewList = new ArrayList<CarView>();
		while(iter.hasNext())  
		{  
		String carId =iter.next();
		CarView carView = null;
		User u = userService.getUser(userId);
		Car car = carService.getCar(carId);
		Map<String, UserCar> userCars = userCarService.getUserCarMap(userId);
		if (car != null) {
			carView = userCarService.buildCarView(car, userCars.get(carId), u, true);
		}
		carviewList.add(carView);
		}
		return carviewList;
   }
   
	private int getMyrank(List<String> cars, int type, User user, String refCar, hangUpRacesBean hanguprace) {
		long userId = user.getId();
		Iterator<String> iter = cars.iterator();
		int totalFc = 0;
		while (iter.hasNext()) {
			String carId = iter.next();
			UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
			CarView carView = null;
			User u = userService.getUser(userId);
			Car car = carService.getCar(carId);
			Map<String, UserCar> userCars = userCarService.getUserCarMap(userId);
			if (car != null) {
				carView = userCarService.buildCarView(car, userCars.get(carId), u, true);

				int score = 0;
				score = carView.getScore();
				// 计算插槽
				score += userCarService.getCarSlotScore(userCar);
				// 计算喷图
				try {
					score += getPaintJobScore(userCar.getCarId(), userCar.getUserId());
				} catch (SQLException e) {
				}
				totalFc += score;
				if (carId.equals(refCar))
				{
					hanguprace.setUseRefCar(true);
					totalFc += 0.5*score;
				}
			}
		}
		
		int rank = getFleetRaceRank(totalFc, hanguprace.getScore());
		log.debug("getFleetRaceRank: "+totalFc+" rank is "+rank);
		return rank;
	}
 
	private int getFleetRaceRank(int totalFC, int basicFC) {
		if (totalFC <= 0) 
			return 6;
		double the1stPL = Math.min(1, totalFC / (double)basicFC);
		if (Math.random() <= the1stPL) 
			return 1;
		double the2ndPL =Math.min(1,  the1stPL * 1.1);
		if (Math.random() <= the2ndPL) 
			return 2;
		double the3rdPL =Math.min(1,  the1stPL * 1.2);
		if (Math.random() <= the3rdPL) 
			return 3;
		double the4thPL = Math.min(1, the1stPL * 1.3);
		if (Math.random() <= the4thPL) 
			return 4;
		double the5thPL = Math.min(1, the1stPL * 1.4);
		if (Math.random() <= the5thPL) 
			return 5;
		return 6;
	}

	private List<RewardN> getClosingReward(User user, int id) {
		long userId = user.getId();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		int rank = hangUpRaces.getRank();
		List<RewardN> rewardList = new ArrayList<RewardN>();
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			if (hangUpRank.getIndex() == rank) {
				for (RewardBean hangupreward : hangUpRank.getRewardList()) {
					RewardN.Builder reward = RewardN.newBuilder();
					reward.setId(hangupreward.getRewardId());
					reward.setCount(hangupreward.getRewardCount());
					if(hangUpRaces.isUseRefCar() || reward.getId()/1000 != 2){
						rewardList.add(reward.build());
						hangUpRaces.setUseRefCar(false);
						}
				}
			}
		}
		return rewardList;
	}
	
	public List<RewardBean> setClosingReward(User user, int id) {
		long userId = user.getId();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		int rank = hangUpRaces.getRank();
		List<RewardBean> reward = new ArrayList<RewardBean>();
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			if (hangUpRank.getIndex() == rank) {
				reward = hangUpRank.getRewardList();
			}
		}
		return reward;
	}
	
	
	private List<RewardN> getDoubleClosingReward(User user, int id) {
		long userId = user.getId();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		int rank = hangUpRaces.getRank();
		List<RewardN> rewardList = new ArrayList<RewardN>();
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			if (hangUpRank.getIndex() == rank) {
				for (RewardBean hangupreward : hangUpRank.getRewardList()) {
					RewardN.Builder reward = RewardN.newBuilder();
					reward.setId(hangupreward.getRewardId());
					reward.setCount(hangupreward.getRewardCount()*2);
					if(hangUpRaces.isUseRefCar() || reward.getId()/1000 != 2){
						rewardList.add(reward.build());
						hangUpRaces.setUseRefCar(false);
						}
				}
			}
		}
		return rewardList;
	}
	
	private List<RewardList> getTopListReward(User user) {
		List<FleetRaceRewardBean> fleetracerewardlist = XmlUtil.getFleetRaceRewardList();
		List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
		
		for (FleetRaceRewardBean fleetracereward : fleetracerewardlist) {
			RewardList.Builder rewardListBuilder = RewardList.newBuilder();
			int rankIndexMin = fleetracereward.getIndexMin();
			int rankIndexMax = fleetracereward.getIndexMax();
			rewardListBuilder.setName(rankIndexMin + " - " + rankIndexMax);
			List<RewardN> rewardList = new ArrayList<RewardN>();
			for (RewardBean hangupreward : fleetracereward.getRewardList()) {
				RewardN.Builder reward = RewardN.newBuilder();
				reward.setId(hangupreward.getRewardId());
				reward.setCount(hangupreward.getRewardCount());
				rewardList.add(reward.build());
			}
			rewardListBuilder.addAllRewards(rewardList);
			rewardBuilderList.add(rewardListBuilder.build());
		}
		return rewardBuilderList;
	}

	
	
	private List<RewardBean> getRankReward(User user, int id) {
		long userId = user.getId();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		List<RewardBean> reward = new ArrayList<RewardBean>();
		int rank = hangUpRaces.getRank();
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			if (hangUpRank.getIndex() == rank) {
				reward=hangUpRank.getRewardList();
				}
			}
		return reward;
	}
	
	private List<RewardBean> getDoubleRankReward(User user, int id) {
		long userId = user.getId();
		hangUpRacesBean hangUpRaces = fleetRaceRedis.getRacebyUserId(userId, id);
		List<RewardBean> reward = new ArrayList<RewardBean>();
	
		int rank = hangUpRaces.getRank();
		for (hangUpRankBean hangUpRank : hangUpRaces.getHangUpRankList()) {
			if (hangUpRank.getIndex() == rank) {
				for(RewardBean rwd :hangUpRank.getRewardList()){
					rwd.setRewardCount(rwd.getRewardCount()*2);
					reward.add(rwd);
				}
				}
			}
		return reward;
	}
	
	private boolean  contain(int[] races, int id){
		for(int i = 0; i < races.length; i++){
			if(races[i] == id)
				return true;
		}
		return false;
	}


	private FleetRace getRaceByID(int type, int[] races, User user) {
		List<hangUpBean> hanupList = this.getFleetRaceList();
		long userId = user.getId();
		for (hangUpBean hangup : hanupList) {
			if (hangup.getType() == type) {
				List<hangUpRacesBean> hangUpRacesList = hangup.getHangUpRacesList();
				int id = (int) (type*10+Math.random()*hangUpRacesList.size()+1);
				while(contain(races,id)){
					 id = (int) (type*10+Math.random()*hangUpRacesList.size()+1);
				}
				for (hangUpRacesBean hangUpRaces : hangUpRacesList) {
					if (hangUpRaces.getId() == id) {
						fleetRaceRedis.addRacebyUserid(userId,hangUpRaces.getId(),hangUpRaces);
						return hangUpRaces.toFleetRace();
					}
				}
			}
		}
		return hanupList.get(0).getHangUpRacesList().get(0).toFleetRace();
	}
	
	private List<FleetRace> getAllFleetRaceList(User user) {
		int types[] = {1,2,3,4,5,7,6,8,1,3,5,7};
		List<FleetRace> fleetRace = new ArrayList<FleetRace>();
		RpLevel currentLevel = levelService.getLevel(user.getLevel());
		int exp = currentLevel != null ? user.getRpNum() - currentLevel.getRpNum() : user.getRpNum();
		int rpnum = exp;
		long userId = user.getId();
		
		List<hangUpRacesBean> hangupRaceList = new  ArrayList<hangUpRacesBean>();
		hangupRaceList = fleetRaceRedis.getAllRaceByUserId(userId);

		int races[] = new int[hangupRaceList.size()];
		for(int i = 0; i < hangupRaceList.size(); i++){
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date starttime = new Date();
			try {
				String time  = hangupRaceList.get(i).getStartTime();
				starttime = df.parse(time);
			} catch (Exception e1) {
			}
			Date thistime = new Date();
			int startday = (int) (starttime.getTime() / 300 / 1000);
			int nowday = (int) (thistime.getTime() / 300 / 1000);
			
			
			if (hangupRaceList.get(i).getStatus() == 2 && startday != nowday) {
				for(int j = 0;j < hangupRaceList.size(); j++){
					races[i] = hangupRaceList.get(j).getId();
				}
				int type = hangupRaceList.get(i).getId()/10;
				fleetRaceRedis.delRaceByUserId(userId, hangupRaceList.get(i).getId());
				fleetRace.add(getFleetRace_type(user, type,races));
			}
			else{
				fleetRace.add(hangupRaceList.get(i).toFleetRace());
				fleetRaceRedis.addRacebyUserid(userId,hangupRaceList.get(i).getId(),hangupRaceList.get(i));
			}
		}
		int racesize = 2;
		if (rpnum > 100 && rpnum < 500)
			racesize = 3;
		if (rpnum > 500 && rpnum < 1000)
			racesize =5;
		if (rpnum > 1000 && rpnum < 3000)
			racesize = 6;
		if (rpnum > 3000 && rpnum < 6000)
			racesize = 8;
		if (rpnum > 6000 & rpnum < 10000)
			racesize = 10;
		if (rpnum > 10000)
			racesize = 12;
		for(int i = 0; i < hangupRaceList.size(); i++){
			races[i] = hangupRaceList.get(i).getId();
		}
		for(int i = hangupRaceList.size() ; i <  racesize; i++){
			fleetRace.add(getFleetRace_type(user, types[i],races));
		}
		return fleetRace;
	}
	
	private FleetRace getFleetRace_type(User user,int type,  int[] races) {
		return getRaceByID(type, races, user);
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
	
	private hangUpRacesBean listToBean(List<hangUpRacesBean> list) {
		hangUpRacesBean hanguprace = new hangUpRacesBean();
		for (int i = 0; i < list.size(); i++) {
			hanguprace = list.get(i);
		}
		return hanguprace;
	}
	
	private int getCountByType(int type) {
	/*	if ((type % 2) == 0) {
			return 5;
		}
		return 3;*/
		return 5;
	}
	
    public List<UserCar> getUserCarList(long userId) {
        return userCarMapper.getUserCarList(userId);
    }

}
