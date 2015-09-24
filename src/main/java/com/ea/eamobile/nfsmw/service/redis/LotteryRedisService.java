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
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;

@Service
public class LotteryRedisService {
	private Logger logger = Logger.getLogger(LotteryRedisService.class);
	
	private static final String LOTTERY_KEY_PREFIX = "lorrery_";
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	
	public List<LotteryBean> getLotteryList(final int type) {
		return redisTemplate.execute(new RedisCallback<List<LotteryBean>>() {
			@Override
			public List<LotteryBean> doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + LOTTERY_KEY_PREFIX + type);
				
				List<LotteryBean> lotteryList = new ArrayList<LotteryBean>();
				Set<Entry<String, String>> lotterySet = bhOps.entries().entrySet();
				Iterator<Entry<String, String>> itr = lotterySet.iterator();
				while(itr.hasNext()) {
					Entry<String, String> entry = itr.next();
					LotteryBean lottery = LotteryBean.fromJson(entry.getValue());
					if (lottery != null) {
						lotteryList.add(lottery);
					}
				}
				
				return lotteryList;
			}
		
		});
	}
	
	public void setLotteryList(final List<LotteryBean> lotteryList, final int type) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + LOTTERY_KEY_PREFIX + type);
				
				for (LotteryBean lottery : lotteryList) {
					bhOps.put("" + lottery.getId(), lottery.toJson());
				}
				
				return null;
			}
		
		});
	}
	
	public LotteryBean getLottery(final int id, final int type) {
		return redisTemplate.execute(new RedisCallback<LotteryBean>() {
			@Override
			public LotteryBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + LOTTERY_KEY_PREFIX + type);
				
				LotteryBean lottery = LotteryBean.fromJson(bhOps.get("" + id));
				return lottery;
			}
		
		});
	}
	
}
