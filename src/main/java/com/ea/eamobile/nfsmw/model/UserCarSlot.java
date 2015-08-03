package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public class UserCarSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private long userCarId;

    private int slotId;

    private int createTime;

    private int consumableId;
    
    //no db table column
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserCarId() {
        return userCarId;
    }

    public void setUserCarId(long userCarId) {
        this.userCarId = userCarId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getCreateTime() {

        return createTime;
    }

    public void setCreateTime(int createTime) {

        this.createTime = createTime;

    }

    public int getConsumableId() {
        return consumableId;
    }

    public void setConsumableId(int consumableId) {
        this.consumableId = consumableId;
    }

    @Override
    public String toString() {
        return "slot [id=" + id + ", userCarId=" + userCarId + ", slotId=" + slotId + ", score=" + score + "]";
    }
    
}
