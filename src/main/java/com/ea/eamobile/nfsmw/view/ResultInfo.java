package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.model.User;

/**
 * 返回信息用
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class ResultInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    
    private User user;

    public ResultInfo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public ResultInfo(boolean success, String message,User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ResultInfo(){}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
