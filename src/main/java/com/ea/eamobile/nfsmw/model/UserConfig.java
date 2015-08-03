package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 10 13:50:59 CST 2013
 * @since 1.0
 */
public class UserConfig implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private int type;
    
    private String content;
    
	
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
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
