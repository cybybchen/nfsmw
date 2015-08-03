package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Nov 07 16:33:25 CST 2012
 * @since 1.0
 */
public class ResourceVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int version;

    private int status;

    private int createTime;
    
    private int gameEdition;

    public int getGameEdition() {
        return gameEdition;
    }

    public void setGameEdition(int gameEdition) {
        this.gameEdition = gameEdition;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;

    }
}
