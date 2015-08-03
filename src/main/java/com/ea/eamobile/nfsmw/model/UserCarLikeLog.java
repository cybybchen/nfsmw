package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:31 CST 2013
 * @since 1.0
 */
public class UserCarLikeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private long userId;

    private long userCarId;

    private long likedUserId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserCarId() {
        return userCarId;
    }

    public void setUserCarId(long userCarId) {
        this.userCarId = userCarId;
    }

    public long getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(long likedUserId) {
        this.likedUserId = likedUserId;
    }
}
