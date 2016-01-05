package com.ea.eamobile.nfsmw.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ea.eamobile.nfsmw.service.redis.FleetRaceRedisService;
import com.ea.eamobile.nfsmw.service.redis.TimeRedisService;

@Service
public class CrontabService {
	private static Logger log = Logger.getLogger(CrontabService.class);
	
	@Autowired
	private TimeRedisService timeRedisService;

	
//	@Scheduled(cron="0/5 * *  * * ? ")
	@Scheduled(cron = "0 0 0 ? * MON")
//	@Scheduled(cron = "0 * 16 * * ?")//16:00-16:59 每分钟运行一次
	@Transactional(rollbackFor = Exception.class)
	public void crontab() {
		log.debug("rename userZSet of rank");
		timeRedisService.modifyFleetRaceRank();
	}
	
////	//test
//	@Scheduled(cron = "0 0 0 ? * MON")
//	@Transactional(rollbackFor = Exception.class)
//	public void crontab2() {
//		log.debug("rename userZSet of rank");
//		timeRedisService.deletePAId();
//	}
}
