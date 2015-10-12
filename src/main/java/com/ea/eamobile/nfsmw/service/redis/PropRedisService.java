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
import com.ea.eamobile.nfsmw.model.bean.PropBean;

@Service
public class PropRedisService {
	private Logger logger = Logger.getLogger(PropRedisService.class);
	
	private static final String PROP_KEY = "prop";
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	
	public List<PropBean> getPropList() {
		return redisTemplate.execute(new RedisCallback<List<PropBean>>() {
			@Override
			public List<PropBean> doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + PROP_KEY);
				
				List<PropBean> propList = new ArrayList<PropBean>();
				Set<Entry<String, String>> set = bhOps.entries().entrySet();
				Iterator<Entry<String, String>> itr = set.iterator();
				while(itr.hasNext()) {
					Entry<String, String> entry = itr.next();
					PropBean prop = PropBean.fromJson(entry.getValue());
					if (prop != null) {
						propList.add(prop);
					}
				}
				
				return propList;
			}
		
		});
	}
	
	public void setPropList(final List<PropBean> propList) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + PROP_KEY);
				
				for (PropBean prop : propList) {
					bhOps.put("" + prop.getId(), prop.toJson());
				}
				
				return null;
			}
		
		});
	}
	
	public PropBean getPropById(final int id) {
		return redisTemplate.execute(new RedisCallback<PropBean>() {
			@Override
			public PropBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + PROP_KEY);
				
				PropBean prop = PropBean.fromJson(bhOps.get("" + id));
				return prop;
			}
		
		});
	}
	
}
