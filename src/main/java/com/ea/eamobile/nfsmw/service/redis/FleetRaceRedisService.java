package com.ea.eamobile.nfsmw.service.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RedisKey;
import com.ea.eamobile.nfsmw.model.RpLeaderBoard;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.RpLeaderboardBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpBean;
import com.ea.eamobile.nfsmw.model.bean.hangUpRacesBean;
import com.ea.eamobile.nfsmw.utils.CommonUtil;

@Service
public class FleetRaceRedisService {
	private Logger logger = Logger.getLogger(FansRewardRedisService.class);

	private static final String FLEET_RACE_KEY = "fleet_race";
	private static final int FLEET_RACE_EXPIRED_TIME = 1;
	private static final String FLEET_RACE_ = "fleet_race_";
	private static final String USERIDZSET_PREFIX = "userIdZSet";
	private static final String FLEET_RANKMAP = "fleet_rankmap";
	private static final int RANK_TOTAL_SIZE = 10000;
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;

	public void setFleetRacet(final hangUpBean hangupbean) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundValueOperations<String, String> bvOps = redisTemplate
						.boundValueOps(RedisKey.PREFIX + FLEET_RACE_KEY);
				bvOps.set(hangupbean.toJson());
				// for(hangUpBean hangUp : hangUpList){
				// bhOps.put(""+hangUp.getType(),hangUp.toJson());
				// }
				return null;
			}
		});
	}

	public hangUpBean getFleetRace() {
		return redisTemplate.execute(new RedisCallback<hangUpBean>() {
			@Override
			public hangUpBean doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundValueOperations<String, String> bhOps = redisTemplate
						.boundValueOps(RedisKey.PREFIX + FLEET_RACE_KEY);
				hangUpBean hangUp = hangUpBean.fromJson(bhOps.get());
				return hangUp;
			}
		});
	}

	public void addRacebyUserid(final long userId, final int id, final hangUpRacesBean race) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RACE_ + userId);
				bhOps.put("" + id, race.toJson());
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	}

	public hangUpRacesBean getRacebyUserId(final long userId, final int id) {
		return redisTemplate.execute(new RedisCallback<hangUpRacesBean>() {
			@Override
			public hangUpRacesBean doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RACE_ + userId);
				hangUpRacesBean race = hangUpRacesBean.fromJson(bhOps.get("" + id));
				return race;
			}
		});
	}

	public List<hangUpRacesBean> getAllRaceByUserId(final long userId) {
		return redisTemplate.execute(new RedisCallback<List<hangUpRacesBean>>() {
			@Override
			public List<hangUpRacesBean> doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RACE_ + userId);
				List<hangUpRacesBean> hanguplist = new ArrayList<hangUpRacesBean>();
				Iterator<Entry<String, String>> it = bhOps.entries().entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					hangUpRacesBean hangup = hangUpRacesBean.fromJson(entry.getValue());
					if (hangup != null)
						hanguplist.add(hangup);
				}
				return hanguplist;
			}
		});
	}
	
	public void delRaceByUserId(final long userId, final int id) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RACE_ + userId);
				bhOps.delete(""+id);
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	}

	public void addFleetRaceRank(final RpLeaderboardBean user) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundZSetOperations<String, String> bzOps = redisTemplate
						.boundZSetOps(RedisKey.PREFIX + USERIDZSET_PREFIX);
				
				boolean addRet = bzOps.add("" + user.getUserId(), user.getRpNumWeek());
				
				if (addRet && bzOps.size() > RANK_TOTAL_SIZE) {
					bzOps.removeRange(0, 0);
					bzOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				}
				return null;
			}
		});
	}
	
	public int getFleetRaceRank(final RpLeaderboardBean user) {
		return redisTemplate.execute(new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(RedisConnection arg0) 
					throws DataAccessException {
				BoundZSetOperations<String, String> bzOps = redisTemplate
						.boundZSetOps(RedisKey.PREFIX + USERIDZSET_PREFIX);
				if (bzOps.reverseRank("" + user.getUserId()) != null)
					return bzOps.reverseRank("" + user.getUserId()).intValue() + 1;
				return 0;
			}
		});
	}
	
	public List<RpLeaderboardBean> getRank(final int start, final int end) {
		return redisTemplate.execute(new RedisCallback<List<RpLeaderboardBean>>() {
			@Override
			public List<RpLeaderboardBean> doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundZSetOperations<String, String> bzOps = redisTemplate
						.boundZSetOps(RedisKey.PREFIX + USERIDZSET_PREFIX);
				List<RpLeaderboardBean> rankList = new ArrayList<RpLeaderboardBean>();
				Set<String> userIdSet = bzOps.reverseRange(start, end);
				Iterator<String> itr = userIdSet.iterator();
				while (itr.hasNext()) {
					String idStr = itr.next();
					long id = CommonUtil.stringToLong(idStr);
					RpLeaderboardBean user = getCompeteModeUser(id);
					if (user == null) {
						user = new RpLeaderboardBean();
						user.setUserId(id);
					}
					long rank = bzOps.reverseRank(idStr) + 1;
					user.setRank((int)rank);
					rankList.add(user);
				}
				return rankList;
			}
		});
	}
	
	public void addRankUser(final RpLeaderboardBean user) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RANKMAP);
				bhOps.put("" + user.getUserId(), user.toJson());
				bhOps.expire(FLEET_RACE_EXPIRED_TIME, TimeUnit.DAYS);
				return null;
			}
		});
	}
	
	public RpLeaderboardBean getCompeteModeUser(final long userId) {
		return redisTemplate.execute(new RedisCallback<RpLeaderboardBean>() {
			@Override
			public RpLeaderboardBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FLEET_RANKMAP);
				
				return RpLeaderboardBean.fromJson(bhOps.get("" + userId));
			}
		});
	}

}