package com.ea.eamobile.nfsmw.service.redis;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RedisKey;
import com.ea.eamobile.nfsmw.model.bean.hangUpBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRacesBean;

@Service
public class FleetRaceRedisService {
	private Logger logger = Logger.getLogger(FansRewardRedisService.class);
	
	private static final String FLEET_RACE_KEY = "fleet_race";
	private static final String FLEET_RACE_STATUS_IN = "fleet_race_status_in";
	private static final int FLEET_RACE_EXPIRED_TIME = 1;
	private static final String FlEET_RACE_ID = "fllet_race _id";
	private static final String FLEET_RACE_ = "fleet_race_";
	
	@Resource
	public  RedisTemplate<String, String> redisTemplate;
	
	public void setFleetRaceList (final List<hangUpBean> hangUpList){
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundValueOperations<String, String> bvOps = redisTemplate
						.boundValueOps(RedisKey.PREFIX+FLEET_RACE_KEY);
				
				for(hangUpBean hangUp : hangUpList){
					bvOps.set(hangUp.toJson());
				}
				return null;
			}
		});
	}
	
	public hangUpBean getFleetRace(){
		return redisTemplate.execute(new RedisCallback<hangUpBean>() {
			@Override
			public hangUpBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundValueOperations<String, String> bvOps = redisTemplate
						.boundValueOps(RedisKey.PREFIX + FLEET_RACE_KEY);
				hangUpBean hangUp = hangUpBean.fromJson(bvOps.get());
				return hangUp;
			}
		});
	}
	
/*	public static void setStatusById(final int index, final int id, final int status) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(FLEET_RACE_STATUS_IN + index);
				bhOps.put("" + id, "" + status);
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	}

	public static int getRaceStatus(final int index, final int id) {
		return redisTemplate.execute(new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(FLEET_RACE_STATUS_IN + index);
				return CommonUtil.stringToInt(bhOps.get("" + id));
			}
		});
		
	}*/
	
/*	public static void setRaceToRedis(final int index, final int id) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(FlEET_RACE_ID);
				bhOps.put("" + index, "" + id);
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	}*/
	
	public void addRacebyUserid(final long userId, final int id, final hangUpBean race,final int type ){
		redisTemplate.execute(new RedisCallback<Object>(){
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(FLEET_RACE_ + userId);
				long raceId = type*10 + id;
				bhOps.put(""+raceId, ""+race.toJson());
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	} 
	
	public hangUpBean getRacebyUserId(final long userId, final int id){
		return redisTemplate.execute(new RedisCallback<hangUpBean>(){
			@Override
		public hangUpBean doInRedis(RedisConnection arg0) 
				throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate.
						boundHashOps(FLEET_RACE_ + userId);
				hangUpBean race = hangUpBean.fromJson(bhOps.get("" + id));
				return race;
			}
		});
	}
	
	
	public boolean hasAddRace(final long userId, final int id) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(FLEET_RACE_ + userId);
//				long raceId = userId + id;
				if (bhOps.hasKey(id))
					return true;
				else
					return false;
			}
		});
	}
	
}