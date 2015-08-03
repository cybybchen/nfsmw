package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;
import java.util.List;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.AccountInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.UserWeiboInfo;

public class UserView implements BaseView, Serializable {

    private static final long serialVersionUID = -3182087332076652008L;
    
    private User user;

    private long userId;

    private int energy;

    private int starNum;

    private int headIndex;

    private String headUrl = "";

    private int tier;

    private String nickname = "";

    private String token = "";
    
    private List<AccountInfo> accounts;
    
    private UserWeiboInfo weiboInfo;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserWeiboInfo getWeiboInfo() {
        return weiboInfo;
    }

    public void setWeiboInfo(UserWeiboInfo weiboInfo) {
        this.weiboInfo = weiboInfo;
    }

    public List<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountInfo> accounts) {
        this.accounts = accounts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

}
