package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 30 14:53:58 CST 2012
 * @since 1.0
 */
public class CarChartlet extends Merchandise implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    
    private String carId;
    
    private String name;
    
    private String diffuseTexturePath;
    
    private String diffuseMaskPath;
    
    private String brdfPath;
    
    private String brdfSpecularPath;
    
    private String numberPlatePath;
    
    private long swatchColor;
    
    private long swatchColor2;
    
    private int paintType;
    
    private int useVinylMap;
    
    private String resource;
    
    private int price;
    
    private int priceType;
    
    private int tenancy;
    
    private int isHot;
    
    private int isNew;
    
    private int isBestSell;
    
    private int invisible;
	
    private int orderId;   

    private int score;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDiffuseTexturePath() {
        return diffuseTexturePath;
    }
    public void setDiffuseTexturePath(String diffuseTexturePath) {
        this.diffuseTexturePath = diffuseTexturePath;
    }
    public String getDiffuseMaskPath() {
        return diffuseMaskPath;
    }
    public void setDiffuseMaskPath(String diffuseMaskPath) {
        this.diffuseMaskPath = diffuseMaskPath;
    }
    public String getBrdfPath() {
        return brdfPath;
    }
    public void setBrdfPath(String brdfPath) {
        this.brdfPath = brdfPath;
    }
    public String getBrdfSpecularPath() {
        return brdfSpecularPath;
    }
    public void setBrdfSpecularPath(String brdfSpecularPath) {
        this.brdfSpecularPath = brdfSpecularPath;
    }
    public String getNumberPlatePath() {
        return numberPlatePath;
    }
    public void setNumberPlatePath(String numberPlatePath) {
        this.numberPlatePath = numberPlatePath;
    }
    public long getSwatchColor() {
        return swatchColor;
    }
    public void setSwatchColor(long swatchColor) {
        this.swatchColor = swatchColor;
    }
    public long getSwatchColor2() {
        return swatchColor2;
    }
    public void setSwatchColor2(long swatchColor2) {
        this.swatchColor2 = swatchColor2;
    }
    public int getPaintType() {
        return paintType;
    }
    public void setPaintType(int paintType) {
        this.paintType = paintType;
    }
    public int getUseVinylMap() {
        return useVinylMap;
    }
    public void setUseVinylMap(int useVinylMap) {
        this.useVinylMap = useVinylMap;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
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
    public int getTenancy() {
        return tenancy;
    }
    public void setTenancy(int tenancy) {
        this.tenancy = tenancy;
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
    
    public int getInvisible() {
        return invisible;
    }

    public void setInvisible(int invisible) {
        this.invisible = invisible;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
