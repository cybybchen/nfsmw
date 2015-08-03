package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorSpendReward implements Comparator<SpendReward>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(SpendReward o1, SpendReward o2) {
        SpendReward tu0 = (SpendReward) o1;
        SpendReward tu1 = (SpendReward) o2;
        int flag = 0;
        if (tu0.getGoldAmount() < tu1.getGoldAmount()) {
            flag = -1;
        }
        if (tu0.getGoldAmount() > tu1.getGoldAmount()) {
            flag = 1;
        }
        if (flag == 0) {
            return (tu0.getId() + "").compareTo(tu1.getId() + "");
        } else {
            return flag;
        }
    }

}
