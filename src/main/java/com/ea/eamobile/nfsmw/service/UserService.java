package com.ea.eamobile.nfsmw.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.constants.NicknameConst;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.mapper.UserMapper;
import com.ea.eamobile.nfsmw.service.dao.UserDao;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.view.CacheUser;
import com.ea.eamobile.nfsmw.view.UserView;

@Service("userService")
public class UserService {
    Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private MemcachedClient cache;
    @Autowired
    UserMapper userMapper;

    public long insertUser(User user) {
        return userDao.insert(user);
    }

    public void updateUser(User user) {
        if (user.getMoney() > 999999999) {
            user.setMoney(999999999);
        }
        if (user.getGold() > 9999999) {
            user.setGold(9999999);
        }
        if (user.getRpNum() > 9999999) {
            user.setRpNum(9999999);
        }
        if (user.getStarNum() > 999) {
            user.setStarNum(999);
        }
        if (user.getMoney() < 0) {
            user.setMoney(0);
        }
        if (user.getGold() < 0) {
            user.setGold(0);
        }
        userDao.update(user);
    }

    public void delete(long id) {
        userDao.deleteById(id);
    }

    public User getUser(long userId) {
    	log.info("The user id is: " + userId);
        User user = userDao.queryById(userId);
        if (user != null && user.getName().startsWith("DELETE_")) {
            return null;
        }
        if (user != null && ((user.getAccountStatus() & Const.IS_BAN) == 1)) {
            return null;
        }
        return user;
    }

    public User getUserForAdmin(long userId) {
        return userDao.queryById(userId);
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public User getUserByWillowtreeToken(String willowtreeToken) {
        return userDao.getUserByWillowtreeToken(willowtreeToken);
    }
    
    public User getUserByUid(String uid) {
        return userDao.getUserByUid(uid);
    }

    /**
     * 鏍规嵁willow鐢ㄦ埛杩斿洖娓告垙鍐呯敤鎴� 濡傛灉娓告垙鍐呯敤鎴风┖ 闇�瑕佸垵濮嬪寲
     * 
     * @param villow
     * @param identityId
     * @return
     */
    public UserView getUserView(UserView view) {
        String token = view.getToken();
        User user = getUserByWillowtreeToken(token);
        // 娓告垙鍐呯敤鎴蜂负绌� 鍒濆鍖�
        if (user == null) {
            user = initUser(view.getNickname(), view.getHeadUrl(), token);
            // swrve
        } else {
        	user.setIsOldUser(1);
            user = regainEnergy(user);
        }
        view.setEnergy(user.getEnergy());
        view.setStarNum(user.getStarNum());
        view.setTier(user.getTier());
        // 濡傛灉鐢ㄦ埛浣跨敤鏈湴澶村儚灏嗕笉涓嬪彂寰崥澶村儚
        if (user.getHeadIndex() != Const.HEAD_INDEX_WEIBO) {
            view.setHeadIndex(user.getHeadIndex());
            view.setHeadUrl(user.getHeadUrl());
        } else {
            // 鏇存柊寰崥澶村儚
            if (StringUtils.isBlank(user.getHeadUrl()) || !user.getHeadUrl().equals(view.getHeadUrl())) {
                user.setHeadIndex(Const.HEAD_INDEX_WEIBO);
                user.setHeadUrl(view.getHeadUrl());
                updateUser(user);
                clearCacheUser(user.getId());
            }
        }
        // warning!!鏈湴鏄电О瑕嗙洊wt鏄电О 姝ゅ鏈夐棶棰� 娉ㄦ剰
        view.setNickname(user.getName());
        view.setUser(user);
        return view;
    }

    public User initUser(String nickname, String headUrl, String token) {
        User user = new User();

        if (nickname == null) {
            nickname = NicknameConst.nicknameByTimestamp();
        }
        user.setName(nickname);
        user.setLevel(1);
        user.setMoney(10300);
        user.setEnergy(200);
        user.setEol(1500);
        user.setHeadUrl(headUrl);
        user.setTier(1);
        user.setVersion(0);
        user.setIsRaceStart(0);

        user.setGold(190);
        user.setIsNicknameChanged(false);
        user.setIsOldUser(0);
        user.setIsWriteJaguar(0);
        user.setWillowtreeToken(token);
        user.setLastRegainEnergyDate((int) (System.currentTimeMillis() / 1000));
        user.setCreateTime((int) (System.currentTimeMillis() / 1000));
        user.setIsGetTutorialReward(0);
        user.setIsRewardedBind(0);
        user.setTierStatus(3);
        user.setCertType(Const.CERT_TYPE_DEVICE);// 鍒濆鍖栫敤鎴蜂竴瀹氭槸device
        user.setAccountStatus(Const.DEFAULT_USER_STATUS);
        user.setAccessToken("");
        user.setUid("");

        Long generatedId = -1L;

        generatedId = insertUser(user);

        if (generatedId == -1L) {
            log.debug("DON'T PANIC, Possiable duplicate name but we try again");
            user.setName(NicknameConst.nicknameByTimestamp());
            generatedId = insertUser(user);
        }
        // int gold = getCorrectGold(user.getId());

        user.setId(generatedId);

        user.setId(generatedId);

        return user;
    }

    public UserView loginUserError() {
        UserView view = new UserView();
        view.setUserId(0);
        view.setEnergy(0);
        view.setStarNum(0);
        view.setTier(0);
        view.setHeadIndex(0);
        view.setHeadUrl("");
        return view;
    }

    public void updateConsumeEnergy(long userId, int reduceEnergyNum) {
        userDao.updateConsumeEnergy(userId, reduceEnergyNum);
    }

    public void updateRpNum(long userId, int rpNum) {
        userDao.updateRpNum(userId, rpNum);
    }

    public void updateTier(long userId, int tier) {
        userDao.updateTier(userId, tier);
    }

    public void updateStarNum(long userId, int starNum) {
        userDao.updateStarNum(userId, starNum);
    }

    public User regainEnergy(User user) {
        long regainTime = 0;
        long currentTime = 0;
        long startTime = DateUtil.getDateTime("2013-04-13 00:00:00").getTime();
        long lastRecoveryTime = DateUtil.getDateTime("2013-04-13 03:00:00").getTime();
        if (System.currentTimeMillis() < startTime) {
            regainTime = user.getLastRegainEnergyDate() * 1000L - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L;
            currentTime = System.currentTimeMillis() - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L;
        } else {
            regainTime = user.getLastRegainEnergyDate() * 1000L - Match.ENERGY_REGAIN_HOUR_SECONDS_AFTER_APRIL_THIRTEEN
                    * 1000L;
            currentTime = System.currentTimeMillis() - Match.ENERGY_REGAIN_HOUR_SECONDS_AFTER_APRIL_THIRTEEN * 1000L;
        }
        Date regainDate = new Date(regainTime);
        Date currentDate = new Date(currentTime);
        int days = Math.abs(DateUtil.intervalDays(currentDate, regainDate));
        if (user.getLastRegainEnergyDate() * 1000L <= lastRecoveryTime
                && System.currentTimeMillis() >= lastRecoveryTime) {
            days = days + 1;
        }
        int energy = Math.min(Match.ENERGY_MAX, user.getEnergy() + days * Match.EVERDAY_GET_ENERGY_NUM);
        if (user.getEnergy() < Match.ENERGY_MAX) {
            user.setEnergy(energy);
        }

        user.setLastRegainEnergyDate((int) (System.currentTimeMillis() / 1000));
        updateUser(user);

        return user;
    }

    public User regainEnergyNew(User user) {
        Date regainTime = new Date(user.getLastRegainEnergyDate() * 1000L - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L);

        Date currentTime = new Date(System.currentTimeMillis() - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L);
        int minutes = Math.abs(DateUtil.intervalMinutes(currentTime, regainTime));
        // int days = Math.abs(DateUtil.intervalDays(currentTime, regainTime));

        int energy = Math.min(Match.ENERGY_MAX, user.getEnergy() + minutes * Match.EVERTIME_GET_ENERGY_NUM);
        if (user.getEnergy() < Match.ENERGY_MAX) {
            user.setEnergy(energy);
        }
        user.setLastRegainEnergyDate((int) (System.currentTimeMillis() / 1000));
        updateUser(user);

        return user;
    }

    /**
     * 鑾峰彇浣跨敤姝よ祫婧愮増鏈殑鐢ㄦ埛浜烘暟
     * 
     * @param version
     * @return
     */
    public int getResouceVersionUserCount(int version) {
        return userDao.getResouceVersionUserCount(version);
    }

    public List<User> getUsersByIds(Long[] ids) {
        return userDao.getUsersByIds(ids);
    }

    public List<User> getUsersByTokens(String[] tokens) {
        return userDao.getUsersByTokens(tokens);
    }

    public int getNickNameCount(String nickname) {
        return userDao.getNickNameCount(nickname);
    }

    public int getRpRank(int rpNum) {
        return userDao.getRpRank(rpNum);
    }

    public List<User> getTopHundredUser() {
        return userDao.getTopHundredUser();
    }

    public void updateStatusByIds(Map<String, Object> params) {
        userDao.updateStatusByIds(params);
    }

    /**
     * 浠巆ache鍙栦笉甯稿彉鏇寸殑鐢ㄦ埛淇℃伅 缂撳瓨涓嶅鏄撳彉鐨勫瓧娈电粰鎺掕姒滅瓑鐢�
     * 
     * @param userId
     * @return
     */
    public CacheUser getCacheUser(long userId) {
        CacheUser cacheUser = (CacheUser) cache.get(CacheKey.CACHE_USER + userId);
        if (cacheUser == null) {
            User user = getUser(userId);
            if (user != null) {
                cacheUser = new CacheUser(user.getName(), user.getHeadIndex(), user.getHeadUrl(), user.getLevel(),
                        user.getAccountStatus());
                cache.set(CacheKey.CACHE_USER + userId, cacheUser, MemcachedClient.HOUR);
            }
        }
        return cacheUser;
    }
    public User getUserFromDb(long userId){
        return userMapper.queryById(userId);
    }
    public void clearCacheUser(long userId) {
        cache.delete(CacheKey.CACHE_USER + userId);
    }
}
