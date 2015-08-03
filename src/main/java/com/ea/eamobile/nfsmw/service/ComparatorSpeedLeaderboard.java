package com.ea.eamobile.nfsmw.service;

import java.io.Serializable;
import java.util.Comparator;

import com.ea.eamobile.nfsmw.view.AbstractLeaderboard;

public class ComparatorSpeedLeaderboard implements Comparator<AbstractLeaderboard> ,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(AbstractLeaderboard lb1, AbstractLeaderboard lb2) {
        float diff = lb1.getResult() - lb2.getResult();
        if (diff > 0) {
            return -1;
        } else if (diff < 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
