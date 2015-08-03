package com.ea.eamobile.nfsmw.model;

/**
 * 商品基类 
 * 用来统一处理购买逻辑
 * 所有需要购买的物品需要实现此基类
 * @author ma.ruofei@ea.com
 *
 */
public abstract class Merchandise {

    private int price;
    
    private int priceType;

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

}
