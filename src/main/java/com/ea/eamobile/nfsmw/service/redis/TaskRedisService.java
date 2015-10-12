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
import com.ea.eamobile.nfsmw.model.bean.TaskBean;

@Service
public class TaskRedisService {
	private Logger logger = Logger.getLogger(TaskRedisService.class);
	
	private static final String TASK_KEY = "task";
	
	@Resource
	public RedisTemplate<String, String> redisTemplate;
	
	public List<TaskBean> getTaskList() {
		return redisTemplate.execute(new RedisCallback<List<TaskBean>>() {
			@Override
			public List<TaskBean> doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + TASK_KEY);
				
				List<TaskBean> taskList = new ArrayList<TaskBean>();
				Set<Entry<String, String>> set = bhOps.entries().entrySet();
				Iterator<Entry<String, String>> itr = set.iterator();
				while(itr.hasNext()) {
					Entry<String, String> entry = itr.next();
					TaskBean task = TaskBean.fromJson(entry.getValue());
					if (task != null) {
						taskList.add(task);
					}
				}
				
				return taskList;
			}
		
		});
	}
	
	public void setTaskList(final List<TaskBean> taskList) {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + TASK_KEY);
				
				for (TaskBean task : taskList) {
					bhOps.put("" + task.getId(), task.toJson());
				}
				
				return null;
			}
		
		});
	}
	
	public TaskBean getTask(final int id) {
		return redisTemplate.execute(new RedisCallback<TaskBean>() {
			@Override
			public TaskBean doInRedis(RedisConnection arg0)
					throws DataAccessException {
				BoundHashOperations<String, String, String> bhOps = redisTemplate
						.boundHashOps(RedisKey.PREFIX + TASK_KEY);
				
				TaskBean task = TaskBean.fromJson(bhOps.get("" + id));
				return task;
			}
		
		});
	}
	
}
