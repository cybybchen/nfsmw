package com.ea.eamobile.nfsmw.view;

import com.ea.eamobile.nfsmw.model.Merchandise;

/**
 * 实例化一个虚拟的可购买商品 如报名费等
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class MerchandiseImpl extends Merchandise {

    private int price;

    private int priceType;

    public MerchandiseImpl(int price, int priceType) {
        this.price = price;
        this.priceType = priceType;
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
}
