package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.constants.Const;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
public class JaguarOwnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private int gender;

    private String mobile;

    private String email;

    private String province;

    private String city;

    private int buyYear;

    private int buySeason;

    private String testArea;

    private int budget;

    private String ip;

    private long createTime;

    private int device;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String genderName;
    private String deviceName;
    private String seasonName;
    private String money;
    private String ageName;

    public String getAgeName() {
        switch (age) {
        case 1:
            ageName = "小于18岁";
            break;
        case 2:
            ageName = "19-25岁";
            break;
        case 3:
            ageName = "16-35岁";
            break;
        case 4:
            ageName = "36-45岁";
            break;
        case 5:
            ageName = "45岁以上";
            break;
        default:
            ageName = "";
            break;
        }
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

    public String getMoney() {
        switch (budget) {
        case 1:
            money = "50万以下";
            break;
        case 2:
            money = "50-75万";
            break;
        case 3:
            money = "75-100万";
            break;
        case 4:
            money = "100-150万";
            break;
        case 5:
            money = "150-200万";
            break;
        case 6:
            money = "200万以上";
            break;
        default:
            money = "不确定";
            break;
        }
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSeasonName() {
        switch (buySeason) {
        case 1:
            seasonName = "1-3月";
            break;
        case 2:
            seasonName = "4-6月";
            break;
        case 3:
            seasonName = "7-9月";
            break;
        case 4:
            seasonName = "10-12月";
            break;
        default:
            seasonName = "不确定";
            break;
        }
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getGenderName() {
        switch (gender) {
        case 1:
            genderName = "先生";
            break;
        case 2:
            genderName = "女士";
            break;
        default:
            genderName = "未知";
            break;
        }
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getDeviceName() {
        switch (device) {
        case Const.DEVICE_IS_IPAD:
            deviceName = "ipad";
            break;
        case Const.DEVICE_IS_IPHONE:
            deviceName = "iphone4";
            break;
        case Const.DEVICE_IS_IPHONE5:
            deviceName = "iphone5";
            break;
        default:
            break;

        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getBuyYear() {
        return buyYear;
    }

    public void setBuyYear(int buyYear) {
        this.buyYear = buyYear;
    }

    public int getBuySeason() {
        return buySeason;
    }

    public void setBuySeason(int buySeason) {
        this.buySeason = buySeason;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTestArea() {
        return testArea;
    }

    public void setTestArea(String testArea) {
        this.testArea = testArea;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
