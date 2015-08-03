package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 14 21:41:55 CST 2013
 * @since 1.0
 */
public class OperateBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
