package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;
import java.util.List;


import com.ea.eamobile.nfsmw.model.User;

/**
 * 每次抽奖返回的奖励信息
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class GotchaView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int gotchaId;
    
    private String carId;
    
    private User user;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    /**
     * 是否获得了车
     */
    private boolean luckdog =false;

    private int fragmentId;

    private int fragmentNum =0 ;

    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }

    public int getFragmentNum() {
        return fragmentNum;
    }

    public void setFragmentNum(int fragmentNum) {
        this.fragmentNum = fragmentNum;
    }

    private List<Integer> rewards;

    public int getGotchaId() {
        return gotchaId;
    }

    public void setGotchaId(int gotchaId) {
        this.gotchaId = gotchaId;
    }

    public boolean isLuckdog() {
        return luckdog;
    }

    public void setLuckdog(boolean luckdog) {
        this.luckdog = luckdog;
    }

    public List<Integer> getRewards() {
        return rewards;
    }

    public void setRewards(List<Integer> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "GotchaView [gotchaId=" + gotchaId + ", carId=" + carId + ", luckdog=" + luckdog + ", fragmentId="
                + fragmentId + ", fragmentNum=" + fragmentNum + ", rewards=" + rewards + "]";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
