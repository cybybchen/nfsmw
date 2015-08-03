package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 14:15:32 CST 2013
 * @since 1.0
 */
public class CensorWord implements Serializable{

    private static final long serialVersionUID = 1L;

    private String content;
    
	
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
