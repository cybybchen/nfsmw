package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.model.UserCarFragment;

public class BuyCayUserFragView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int price;
    
    private UserCarFragment userCarFragment;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public UserCarFragment getUserCarFragment() {
        return userCarFragment;
    }

    public void setUserCarFragment(UserCarFragment userCarFragment) {
        this.userCarFragment = userCarFragment;
    }
    
   
    
    
}
