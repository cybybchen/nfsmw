package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.ea.eamobile.nfsmw.utils.Markable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 25 17:34:05 CST 2012
 * @since 1.0
 */
public class UserSession extends Markable implements Serializable {

    private static final long serialVersionUID = 1L;

    protected long userId;

    protected String session;
    
    protected Timestamp upTime;
    
    protected static final String USER_ID = "user_id";
    
    protected static final String SESSION = "session";
    
    protected static final String UP_TIME = "up_time";
    
    public Timestamp getUpTime() {
        Timestamp temp=upTime;
        return temp;
    }

    public void setUpTime(Timestamp upTime) {
        if (upTime != null) {
            this.upTime = (Timestamp) upTime.clone();
            mark(UP_TIME, upTime);
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        mark(USER_ID, userId);
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
        mark(SESSION, session);
    }
}
