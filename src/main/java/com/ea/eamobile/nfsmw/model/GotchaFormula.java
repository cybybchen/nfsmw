package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:03:31 CST 2013
 * @since 1.0
 */
public class GotchaFormula implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private float value;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
}
