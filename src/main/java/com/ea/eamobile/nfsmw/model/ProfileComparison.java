package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 06 16:32:05 CST 2013
 * @since 1.0
 */
public class ProfileComparison implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int orderIndex;
    
    private String title;
    
    private String subTitle;
    
    private int valueType;
    
    private boolean hasProgressBar;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getOrderIndex() {
        return orderIndex;
    }
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubTitle() {
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    public int getValueType() {
        return valueType;
    }
    public void setValueType(int valueType) {
        this.valueType = valueType;
    }
    public boolean getHasProgressBar() {
        return hasProgressBar;
    }
    public void setHasProgressBar(boolean hasProgressBar) {
        this.hasProgressBar = hasProgressBar;
    }
}
