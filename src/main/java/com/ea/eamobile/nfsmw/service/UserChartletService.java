package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserChartlet;
import com.ea.eamobile.nfsmw.model.handler.UserChartLetHandler;
import com.ea.eamobile.nfsmw.model.mapper.UserChartletMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.RWSplit;
import com.ea.eamobile.nfsmw.view.CarChartletView;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 17:14:26 CST 2012
 * @since 1.0
 */
@Service
public class UserChartletService {

    private static final Logger log = LoggerFactory.getLogger(UserChartletService.class);

    @Autowired
    private UserChartletMapper userChartletMapper;
    @Autowired
    private CarChartletService chartletService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private PayService payService;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private MemcachedClient cache;

    public UserChartlet getUserChartlet(int id) {
        return userChartletMapper.getUserChartlet(id);
    }

    public int insert(UserChartlet userChartlet) {
        return userChartletMapper.insert(userChartlet);
    }

    public void update(UserChartlet userChartlet) {
        userChartletMapper.update(userChartlet);
    }

    public void deleteById(int id) {
        userChartletMapper.deleteById(id);
    }

    public UserChartlet getUserChartlet(long userId, int chartletId) throws SQLException {
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        return run.query("SELECT * from user_chartlet where user_id = ? and chartlet_id=? ", new UserChartLetHandler(),
                userId, chartletId);
        // return userChartletMapper.getUserChartletByUserId(userId, chartletId);
    }

    /**
     * 使用贴图
     * 
     * @param userId
     * @param chartletId
     * @return
     */
    public ResultInfo useChartlet(long userId, int chartletId, String carId) throws SQLException {
        ResultInfo result = new ResultInfo(false, "");
        UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
        if (userCar == null) {
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.USER_CAR_NULL).getContent());
            return result;
        }
        CarChartlet chartlet = chartletService.getCarChartlet(chartletId);
        if (chartlet == null) {
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.CHARTLET_NULL).getContent());
            return result;
        }
        UserChartlet userChartlet = getUserChartlet(userId, chartletId);
        // 判断是否匹配
        if (!chartlet.getCarId().equals(userCar.getCarId())) {
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.CHARTLET_NOT_FIT_CAR).getContent());
            return result;
        }
        long time = 0;
        if (userChartlet != null) {
            time = (chartlet.getTenancy() * DateUtil.S - (System.currentTimeMillis() - userChartlet.getRentTime()
                    .getTime())) / DateUtil.S;
        }
        // 购买还是更换
        if (userChartlet == null || time < 0) {
            result = payService.buy(chartlet, userId);
            if (result.isSuccess()) {
                if (userChartlet == null) {
                    userChartlet = new UserChartlet();
                    userChartlet.setUserId(userId);
                    userChartlet.setChartletId(chartletId);
                    userChartlet.setIsOwned(1);
                    userChartlet.setRentTime(new Timestamp(System.currentTimeMillis()));
                    insert(userChartlet);
                    log.debug("buy chartlet success. chartletId={},userid={}", chartletId, userId);
                } else {
                    userChartlet.setRentTime(new Timestamp(System.currentTimeMillis()));
                    update(userChartlet);
                }
            }
        } else {
            userCar.setChartletId(chartletId);
            userCarService.update(userCar);
            result.setSuccess(true);
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.CHARTLET_CHANGE_SUCCESS).getContent());
            log.debug("user has changed car chartlet.chartletId={},userid={}", chartletId, userId);
        }
        // 清除memcache
        cache.delete(CacheKey.USER_CAR_CHARTLET_VIEW_LIST + userId + "_" + carId);
        return result;
    }

    /**
     * 获取车辆的贴图试图列表 全部不管用户是否有
     * 
     * @param carId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<CarChartletView> getChartletViewList(String carId, long userId) throws SQLException {
    	// 从memcache里读取
    	List<CarChartletView> resultCache = (List<CarChartletView>) cache.get(CacheKey.USER_CAR_CHARTLET_VIEW_LIST + userId + "_" + carId);
    	if (resultCache != null) {
    		return resultCache;
    	}
    	
        List<CarChartletView> result = Collections.emptyList();
        List<CarChartlet> list = chartletService.getChartletsByCarId(carId);
        if (list != null && list.size() > 0) {
            result = new ArrayList<CarChartletView>();
            for (CarChartlet let : list) {
                if (let.getInvisible() == Const.NOT_INVISIBLE) {
                    CarChartletView view = new CarChartletView();
                    view.setChartlet(let);
                    boolean isOwned = false;
                    int remainTime = 0; // 返回秒
                    UserChartlet ulet = getUserChartlet(userId, let.getId());
                    if (ulet != null) {
                        isOwned = ulet.getIsOwned() != 0;
                        // 租赁时长 - 开始租赁时间
                        long time = (let.getTenancy() * DateUtil.S - (System.currentTimeMillis() - ulet.getRentTime()
                                .getTime())) / DateUtil.S;
                        if (time > 0) {
                            remainTime = (int) time;
                        }
                    }
                    view.setOrderId(let.getOrderId());
                    view.setSellFlag(let.getIsBestSell() * 4 + let.getIsHot() * 2 + let.getIsNew() * 1);
                    view.setOwned(isOwned);
                    view.setRemainTime(remainTime);
                    view.setScore(let.getScore());
                    result.add(view);
                }
            }
        }
        // 存入memcache
        cache.set(CacheKey.USER_CAR_CHARTLET_VIEW_LIST + userId + "_" + carId, result);
        
        return result;
    }

}