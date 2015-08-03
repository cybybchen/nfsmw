package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorSpendActivity implements Comparator<SpendActivity>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(SpendActivity o1, SpendActivity o2) {
        SpendActivity tu0 = (SpendActivity) o1;
        SpendActivity tu1 = (SpendActivity) o2;
        int flag = 0;
        if (tu0.getStartTime() < tu1.getStartTime()) {
            flag = -1;
        }
        if (tu0.getStartTime() > tu1.getStartTime()) {
            flag = 1;
        }
        if (flag == 0) {
            return (tu0.getId() + "").compareTo(tu1.getId() + "");
        } else {
            return flag;
        }
    }

}
