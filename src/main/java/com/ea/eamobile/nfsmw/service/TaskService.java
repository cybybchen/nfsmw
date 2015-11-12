package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.TaskBean;
import com.ea.eamobile.nfsmw.service.command.MissionCommandService;
import com.ea.eamobile.nfsmw.service.redis.TaskRedisService;
import com.ea.eamobile.nfsmw.utils.XmlUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
@Service
public class TaskService {
	
	private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRedisService taskRedis;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserService userService;

    public TaskBean getTask(int id) {
    	TaskBean task = taskRedis.getTask(id);
        if (task == null) {
        	parseAndSaveTaskList();
        	task = taskRedis.getTask(id);
        }
        return task;
    }
    
    public List<TaskBean> getTaskList() {
    	List<TaskBean> taskList = taskRedis.getTaskList();
        if (taskList == null || taskList.size() == 0) {
        	taskList = parseAndSaveTaskList();
        }
        return taskList;
    }
    
    public void addTaskFinishStatus(User user, int taskId) {
    	TaskBean task = getTask(taskId);
    	if (task == null)
    		return;
    	int missionFinishStatus = user.getMissionFinishStatus();
    	log.debug("missionFinishStatus is:" + missionFinishStatus);
    	if ((missionFinishStatus >> taskId & 1) == 0) {
    		user.setMissionFinishStatus(missionFinishStatus + (1 << taskId));
    		
    		userService.updateUser(user);
    		log.debug("222missionFinishStatus is:" + user.getMissionFinishStatus());
    	}
    }
    
    public void addTaskReward(User user) {
    	List<TaskBean> taskList = getTaskList();
    	int missionFinishStatus = user.getMissionFinishStatus();
    	int missionRewardStatus = user.getMissionRewardStatus();
    	
    	for (TaskBean task : taskList) {
    		if ((missionFinishStatus >> task.getId() & 1) == 0 ||
    				(missionRewardStatus >> task.getId() & 1) == 1)
    			continue;
    		
    		missionRewardStatus += (1 << task.getId());
    		List<RewardBean> rewardList = task.getRewardList();
        	rewardService.doRewards(user, rewardList);
    	}
    	
    	user.setMissionRewardStatus(missionRewardStatus);
    	userService.updateUser(user);
    }
    
    private List<TaskBean> parseAndSaveTaskList() {
    	List<TaskBean> taskList = XmlUtil.getTaskList();
    	taskRedis.setTaskList(taskList);
        
        return taskList;
    }
}