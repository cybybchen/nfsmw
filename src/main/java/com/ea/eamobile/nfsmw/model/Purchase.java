package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 26 20:29:21 CST 2012
 * @since 1.0
 */
public class Purchase extends Merchandise implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int priceType;

    private int price;

    private int isAvailable;

    private int discount;

    private String discountNum;

    private int displayPrice;

    private int buyCount;

    private int isDiscount;

    private int itemType;

    private String name;

    private int limitTime;

    private int energySerialNumber;

    private int isHot;

    private int isNew;

    private int isBestSell;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(String discountNum) {
        this.discountNum = discountNum;
    }

    public int getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(int displayPrice) {
        this.displayPrice = displayPrice;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public int getLimitTime() {

        return limitTime;
    }

    public void setLimitTime(int limitTime) {

        this.limitTime = limitTime;

    }

    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemTypet(int itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergySerialNumber() {
        return energySerialNumber;
    }

    public void setEnergySerialNumber(int energySerialNumber) {
        this.energySerialNumber = energySerialNumber;
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

    public int getIsBestSell() {
        return isBestSell;
    }

    public void setIsBestSell(int isBestSell) {
        this.isBestSell = isBestSell;
    }
}
