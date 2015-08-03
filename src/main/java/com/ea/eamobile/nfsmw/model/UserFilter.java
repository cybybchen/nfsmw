package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 17 11:50:46 CST 2013
 * @since 1.0
 */
public class UserFilter implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private long userId;
    
    private int filterOption;
	
    public int getFilterOption() {
        return filterOption;
    }
    public void setFilterOption(int filterOption) {
        this.filterOption = filterOption;
    }
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
}
