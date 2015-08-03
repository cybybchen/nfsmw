package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Jan 22 20:22:17 CST 2013
 * @since 1.0
 */
public class CtaContent implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String content;
    
    private String comments;
    
	
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
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
