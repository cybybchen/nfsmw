package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Jan 16 19:35:37 CST 2013
 * @since 1.0
 */
public class Hints implements Serializable{

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
