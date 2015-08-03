package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 17 15:42:34 CST 2012
 * @since 1.0
 */
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 1L;

    public RequestLog() {
    }

    public RequestLog(long userId, String requestName, String param) {
        this.userId = userId;
        this.requestName = requestName;
        this.param = param;
    }

    private int id;

    private long userId;

    private String requestName;

    private int createTime = (int) (System.currentTimeMillis() / 1000L);

    private String param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;

    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
