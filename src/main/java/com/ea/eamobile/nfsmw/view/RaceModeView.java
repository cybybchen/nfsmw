package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class RaceModeView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -266917047494432310L;

    private int id;

    private String name;

    private int finishRatio;

    private int star; // 星级

    private int userStar; // 用户星星数

    private int type;

    private String kingName;

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

    public int getFinishRatio() {
        return finishRatio;
    }

    public void setFinishRatio(int finishRatio) {
        this.finishRatio = finishRatio;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getUserStar() {
        return userStar;
    }

    public void setUserStar(int userStar) {
        this.userStar = userStar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKingName() {
        return kingName;
    }

    public void setKingName(String kingName) {
        this.kingName = kingName;
    }
}
