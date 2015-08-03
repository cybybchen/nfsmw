package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class CacheUser implements Serializable {

    private static final long serialVersionUID = -2863659794723381639L;

    private String name = "";

    private int headIndex;

    private String headUrl = "";

    @Override
    public String toString() {
        return "CacheUser [name=" + name + ", headIndex=" + headIndex + ", headUrl=" + headUrl + ", level=" + level
                + ", accountStatus=" + accountStatus + "]";
    }

    private int level;
    
    private int accountStatus;


    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }


    public CacheUser(String name, int headIndex, String headUrl, int level,int accountStatus) {
        this.name = name;
        this.headIndex = headIndex;
        this.headUrl = headUrl;
        this.level = level;
        this.accountStatus = accountStatus;

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public CacheUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

}
