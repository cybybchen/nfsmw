package com.ea.eamobile.nfsmw.service.rank;

import java.io.Serializable;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;

@Service
public class BaseRankUtil {
    
    public Comparator<Integer> getComparator(int rankType) {
        Comparator<Integer> com = null;
        if (rankType == Match.MODE_RANK_TYPE_BY_TIME) {
            com = new TimeComparator();
        } else {
            com = new SpeedComparator();
        }
        return com;
    }
    
    public Comparator<Float> getFComparator(int rankType) {
        Comparator<Float> com = null;
        if (rankType == Match.MODE_RANK_TYPE_BY_TIME) {
            com = new FTimeComparator();
        } else {
            com = new FSpeedComparator();
        }
        return com;
    }
    
    public static class FTimeComparator implements Comparator<Float>,Serializable {
        private static final long serialVersionUID = 1L;

        // racetime min to max
        @Override
        public int compare(Float x, Float y) {
            if (x > y) {
                return 1;
            } else if (x < y) {
                return -1;
            }
            return 0;
        }
    }

    public static class FSpeedComparator implements Comparator<Float> ,Serializable {
        private static final long serialVersionUID = 1L;
        // speed max to min
        @Override
        public int compare(Float x, Float y) {
            if (x > y) {
                return -1;
            } else if (x < y) {
                return 1;
            }
            return 0;
        }
    }
    
    public static class TimeComparator implements Comparator<Integer>,Serializable  {
        private static final long serialVersionUID = 1L;
        // racetime min to max
        @Override
        public int compare(Integer x, Integer y) {
            if (x > y) {
                return 1;
            } else if (x < y) {
                return -1;
            }
            return 0;
        }
    }

    public static class SpeedComparator implements Comparator<Integer>,Serializable  {
        private static final long serialVersionUID = 1L;
        // speed max to min
        @Override
        public int compare(Integer x, Integer y) {
            if (x > y) {
                return -1;
            } else if (x < y) {
                return 1;
            }
            return 0;
        }
    }
    
    public int format(float raceTime) {
        return Math.round(raceTime);
    }
    
}
