package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 11:46:57 CST 2013
 * @since 1.0
 */
public class News implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int type;
    
    private String content;
    
    private int createTime;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getCreateTime() {
        return createTime;
    }
    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
