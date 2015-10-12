package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.bean.RechargeDataBean;
import com.ea.eamobile.nfsmw.service.redis.RechargeDataRedisService;
import com.ea.eamobile.nfsmw.utils.XmlUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
@Service
public class RechargeDataService {

	public static final int TPYE_RANDOM_ONETIME = 0;
	public static final int TYPE_RANDOM_TENTIME = 1;
	public static final int RANDOM_COUNT_ONETIME = 1;
	public static final int RANDOM_COUNT_TENTIME = 10;
	
    @Autowired
    private RechargeDataRedisService rechargeDataRedis;

    public RechargeDataBean getRechargeData(String transactionId) {
    	RechargeDataBean rechargeData = rechargeDataRedis.getRechargeData(transactionId);
        if (rechargeData == null) {
        	parseAndSaveRechargeDataList();
        	rechargeData = rechargeDataRedis.getRechargeData(transactionId);
        }
        return rechargeData;
    }
    
    public List<RechargeDataBean> getRechargeDataList() {
    	List<RechargeDataBean> rechargeDataList = rechargeDataRedis.getRechargeDataList();
        if (rechargeDataList == null) {
        	rechargeDataList = parseAndSaveRechargeDataList();
        }
        return rechargeDataList;
    }
    
    private List<RechargeDataBean> parseAndSaveRechargeDataList() {
    	List<RechargeDataBean> rechargeDataList = XmlUtil.getRechargeDataList();
    	rechargeDataRedis.setRechargeDataList(rechargeDataList);
        
        return rechargeDataList;
    }
    
    public RechargeDataBean getRechargeDataByVipId(int vipId) {
    	List<RechargeDataBean> rechargeDataList = getRechargeDataList();
        for (RechargeDataBean rechargeData : rechargeDataList) {
        	if (rechargeData.getVipId() == vipId && rechargeData.getVipId() != 0)
        		return rechargeData;
        }
        return null;
    }
}