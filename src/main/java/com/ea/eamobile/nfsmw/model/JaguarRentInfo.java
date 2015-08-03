package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.constants.Const;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
public class JaguarRentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private int gender;

    private String mobile;

    private String email;

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

    // no db column
    private String genderName;
    private String deviceName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
