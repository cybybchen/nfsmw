package com.ea.eamobile.nfsmw.service.redis;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RedisKey;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.utils.CommonUtil;

@Service
public class TimeRedisService {
	private static Logger log = Logger.getLogger(TimeRedisService.class);
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	@Autowired
	private UserService userService;

	private static final String USERIDZSET_PREFIX = "userIdZSet";
	private static final String USERIDZSET_PREFIX_OLD = "userIdZSet_old";
	private static final String SEND_REWARD_STATE = "send_reward_state";
	
//	@Scheduled(cron = "0 0 0 ? * MON")
//	@Scheduled(cron="0/5 * *  * * ? ")
//	@Transactional(rollbackFor = Exception.class)
	public void modifyFleetRaceRank() {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundZSetOperations<String, String> bzOps = redisTemplate
						.boundZSetOps(RedisKey.PREFIX + USERIDZSET_PREFIX);
				bzOps.rename(RedisKey.PREFIX + USERIDZSET_PREFIX_OLD);
				Set<String> userIdSet = bzOps.reverseRange(0, -1);
				Iterator<String> itr = userIdSet.iterator();
				while (itr.hasNext()) {
					String idStr = itr.next();
					long id = CommonUtil.stringToLong(idStr);
					User user = userService.getUser(id);
					user.setRpExpWeek(0);
					userService.updateUser(user);
				}
				return null;
			}
		});
	}
	
	public int getFleetRaceRankFromRedis(final User user) {
		return redisTemplate.execute(new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(RedisConnection arg0) throws DataAccessException {
				BoundZSetOperations<String, String> bzOps = redisTemplate
						.boundZSetOps(RedisKey.PREFIX + USERIDZSET_PREFIX_OLD);
				if (bzOps.reverseRank("" + user.getId()) != null)
					return bzOps.reverseRank("" + user.getId()).intValue() + 1;
				return 0;
			}
		});
	}

	public boolean hasKeyOfTopList(final User user) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection arg0) throws DataAccessException {
				if (redisTemplate.hasKey(RedisKey.PREFIX + USERIDZSET_PREFIX_OLD)) {
					return true;
				} else
					return false;
			}
		});
	}
	
	public void deletePAId(){
		redisTemplate.execute(new RedisCallback<Object>() {
		@Override
		public Object doInRedis(RedisConnection arg0)
			throws DataAccessException{
			redisTemplate.delete(RedisKey.PREFIX+USERIDZSET_PREFIX_OLD);
			return null;
		}
		});
	}

}
