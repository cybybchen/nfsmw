package com.ea.eamobile.nfsmw.service;

import java.io.Serializable;
import java.util.Comparator;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.view.AbstractLeaderboard;

public class ComparatorLeaderboard implements Comparator<AbstractLeaderboard> ,Serializable{
    private int rankType;
    
    
    public int getRankType() {
        return rankType;
    }

    public void setRankType(int rankType) {
        this.rankType = rankType;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(AbstractLeaderboard lb1, AbstractLeaderboard lb2) {
        float diff=0;
        if(rankType==Match.MODE_RANK_TYPE_BY_TIME){
         diff = lb1.getResult() - lb2.getResult();
        }
        else if(rankType==Match.MODE_RANK_TYPE_BY_AVGSPEED){
          diff=  lb2.getResult() - lb1.getResult();
        }
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
