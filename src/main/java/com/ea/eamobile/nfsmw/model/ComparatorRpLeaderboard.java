package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorRpLeaderboard implements Comparator<RpLeaderBoard> ,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(RpLeaderBoard o1, RpLeaderBoard o2) {
        RpLeaderBoard tu0 = (RpLeaderBoard) o1;
        RpLeaderBoard tu1 = (RpLeaderBoard) o2;
        int flag = 0;
        if (tu0.getRpNum() > tu1.getRpNum()) {
            flag = -1;
        }
        if (tu0.getRpNum() < tu1.getRpNum()) {
            flag = 1;
        }
        if (flag == 0) {
            return (tu0.getName() + "").compareTo(tu1.getName() + "");
        } else {
            return flag;
        }
    }

}
