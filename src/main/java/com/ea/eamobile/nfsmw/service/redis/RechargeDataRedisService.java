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
import com.ea.eamobile.nfsmw.model.bean.RechargeDataBean;

@Service
public class RechargeDataRedisService {
	private Logger logger = Logger.getLogger(RechargeDataRedisService.class);
	
	private static final String RECHARGE_DATA_KEY = "recharge_data";
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	
	public List<RechargeDataBean> getRechargeDataList() {
		return redisTemplate.execute(new RedisCallback<List<RechargeDataBean>>() {
			@Override
			public List<RechargeDataBean> doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + RECHARGE_DATA_KEY);
				
				List<RechargeDataBean> rechargeDataList = new ArrayList<RechargeDataBean>();
				Set<Entry<String, String>> rechargeDataSet = bhOps.entries().entrySet();
				Iterator<Entry<String, String>> itr = rechargeDataSet.iterator();
				while(itr.hasNext()) {
					Entry<String, String> entry = itr.next();
					RechargeDataBean rechargeData = RechargeDataBean.fromJson(entry.getValue());
					if (rechargeData != null) {
						rechargeDataList.add(rechargeData);
					}
				}
				
				return rechargeDataList;
			}
		
		});
	}
	
	public void setRechargeDataList(final List<RechargeDataBean> rechargeDataList) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + RECHARGE_DATA_KEY);
				
				for (RechargeDataBean rechargeData : rechargeDataList) {
					bhOps.put(rechargeData.getTransactionId(), rechargeData.toJson());
				}
				
				return null;
			}
		
		});
	}
	
	public RechargeDataBean getRechargeData(final String transactionId) {
		return redisTemplate.execute(new RedisCallback<RechargeDataBean>() {
			@Override
			public RechargeDataBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + RECHARGE_DATA_KEY);
				
				RechargeDataBean rechargeData = RechargeDataBean.fromJson(bhOps.get(transactionId));
				return rechargeData;
			}
		
		});
	}
	
}
