package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public class Car extends Merchandise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private int type;

    private int tier;

    private int price;

    private int priceType;

    private int unlockMwNum;

    private int isHot;

    private int isNew;

    private int isBestSell;

    private int needTransport;

    private int transportTime;

    private int score;

    private int visible;

    private int startTime;

    private int endTime;

    private int isSpecialCar;

    public boolean needTransport() {
        return needTransport > 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getUnlockMwNum() {
        return unlockMwNum;
    }

    public void setUnlockMwNum(int unlockMwNum) {
        this.unlockMwNum = unlockMwNum;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getNeedTransport() {
        return needTransport;
    }

    public void setNeedTransport(int needTransport) {
        this.needTransport = needTransport;
    }

    public int getTransportTime() {
        return transportTime;
    }

    public void setTransportTime(int transportTime) {
        this.transportTime = transportTime;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getIsBestSell() {
        return isBestSell;
    }

    public void setIsBestSell(int isBestSell) {
        this.isBestSell = isBestSell;
    }

    public int getStartTime() {

        return startTime;
    }

    public void setStartTime(int startTime) {

        this.startTime = startTime;

    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {

        this.endTime = endTime;

    }

    public int getIsSpecialCar() {
        return isSpecialCar;
    }

    public void setIsSpecialCar(int isSpecialCar) {
        this.isSpecialCar = isSpecialCar;
    }
}
