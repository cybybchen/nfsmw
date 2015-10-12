package com.ea.eamobile.nfsmw.service.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RedisKey;
import com.ea.eamobile.nfsmw.model.bean.FansRewardBean;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;

@Service
public class FansRewardRedisService {
	private Logger logger = Logger.getLogger(FansRewardRedisService.class);
	
	private static final String FANS_REWARD_KEY = "fans_reward";
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	
	public List<FansRewardBean> getFansRewardList() {
		return redisTemplate.execute(new RedisCallback<List<FansRewardBean>>() {
			@Override
			public List<FansRewardBean> doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FANS_REWARD_KEY);
				
				List<FansRewardBean> fansRewardList = new ArrayList<FansRewardBean>();
				Set<Entry<String, String>> set = bhOps.entries().entrySet();
				Iterator<Entry<String, String>> itr = set.iterator();
				while(itr.hasNext()) {
					Entry<String, String> entry = itr.next();
					FansRewardBean fansReward = FansRewardBean.fromJson(entry.getValue());
					if (fansReward != null) {
						fansRewardList.add(fansReward);
					}
				}
				
				return fansRewardList;
			}
		
		});
	}
	
	public void setFansRewardList(final List<FansRewardBean> fansRewardList) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FANS_REWARD_KEY);
				
				for (FansRewardBean fansReward : fansRewardList) {
					bhOps.put("" + fansReward.getId(), fansReward.toJson());
				}
				
				return null;
			}
		
		});
	}
	
	public FansRewardBean getFansReward(final int id) {
		return redisTemplate.execute(new RedisCallback<FansRewardBean>() {
			@Override
			public FansRewardBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + FANS_REWARD_KEY);
				
				FansRewardBean fansReward = FansRewardBean.fromJson(bhOps.get("" + id));
				return fansReward;
			}
		
		});
	}
	
}
