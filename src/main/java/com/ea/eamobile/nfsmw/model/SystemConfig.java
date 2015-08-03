package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Dec 25 16:56:54 CST 2012
 * @since 1.0
 */
public class SystemConfig implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String name;
    
    private String value;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
