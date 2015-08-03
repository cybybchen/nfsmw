package com.ea.eamobile.nfsmw.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.dao.UserSessionDao;
import com.ea.eamobile.nfsmw.utils.DateUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 25 17:34:05 CST 2012
 * @since 1.0
 */
@Service
public class UserSessionService {

    @Autowired
    private UserSessionDao userSessionDao;

    public UserSession getUserSession(int id) {
        return userSessionDao.getUserSession(id);
    }

    public UserSession getSession(String session) {
        return userSessionDao.getSession(session);
    }

    public int insertOrUpdate(UserSession userSession) {
        return userSessionDao.insertOrUpdate(userSession);
    }

    public void delete(long userId) {
        userSessionDao.delete(userId);
    }

    /**
     * 保存session<br/>
     * 可能新增可能更新 <br/>
     * login后调用 每个login周期使用一个session
     * 
     * @param userId
     * @return
     */
    public UserSession save(long userId, String sessionString) {
        UserSession session = new UserSession();
        session.setUserId(userId);
        session.setSession(sessionString);
        insertOrUpdate(session);
        return session;
    }

    public String generateSession(long userId, String token) {
        return DigestUtils.md5Hex(userId + token + System.currentTimeMillis());
    }

    public boolean isExpired(UserSession userSession) {
        if (userSession != null && userSession.getUpTime()!=null) {
            int hour = DateUtil.intervalHours(new Date(), userSession.getUpTime());
            return hour >= 1;
        }
        return true;
    }
    
    public String generateToken(String deviceId, String deviceName) {
        return DigestUtils.md5Hex(deviceId + deviceName + System.currentTimeMillis());
    }

}