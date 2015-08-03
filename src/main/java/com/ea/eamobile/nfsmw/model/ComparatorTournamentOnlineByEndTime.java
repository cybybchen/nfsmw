package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorTournamentOnlineByEndTime implements Comparator<TournamentOnline>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(TournamentOnline o1, TournamentOnline o2) {
        TournamentOnline tu0 = (TournamentOnline) o1;
        TournamentOnline tu1 = (TournamentOnline) o2;
        int flag = 0;
        if (tu0.getEndTime() < tu1.getEndTime()) {
            flag = 1;
        }
        if (tu0.getEndTime() > tu1.getEndTime()) {
            flag = -1;
        }
        if (flag == 0) {
            return (tu0.getId() + "").compareTo(tu1.getId() + "");
        } else {
            return flag;
        }
    }

}
