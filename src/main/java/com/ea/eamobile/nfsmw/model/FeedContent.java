package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 22:10:52 CST 2013
 * @since 1.0
 */
public class FeedContent implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String content;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
