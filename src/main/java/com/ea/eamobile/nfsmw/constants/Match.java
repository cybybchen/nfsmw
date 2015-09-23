package com.ea.eamobile.nfsmw.constants;

import java.util.HashMap;
import java.util.Map;

public class Match {

    public static final int CAREER_MODE = 0;

    public static final int TOURNAMENT_MODE = 1;

    public static final int TIER_MODE = 2;

    public static final int EVERYDAY_RACE_MODE = 3;
    
    public static final int GOLD_MODE = 4;

    // race type
    public static final int RACE_TYPE_1V5 = 0;

    public static final int RACE_TYPE_1V1 = 4;

    public static final int RACE_TYPE_SPEED_RUN = 1;

    public static final int RACE_TYPE_HOT_RIDE = 3;

    // /
    public static final int RACE_HOT_RIDE_REWARD_NUM = 2;

    public static final int RACE_TPYE_ZERO_GHOST_NUM = 5;

    public static final int RACE_TYPE_FOUR_GHOST_NUM = 1;

    public static final int RACE_TPYE_ZERO_RACER_NUM = 6;

    public static final int RACE_TYPE_FOUR_RACER_NUM = 2;

    // mode rank type
    public static final int MODE_RANK_TYPE_BY_TIME = 0;
    public static final int MODE_RANK_TYPE_BY_AVGSPEED = 1;

    public static final int EVERYDAY_ENERGY_REGAIN_TIME = 180;

    public static final int ENERGY_REGAIN_HOUR_SECONDS = 3 * 60 * 60;

    public static final int ENERGY_REGAIN_HOUR_SECONDS_AFTER_APRIL_THIRTEEN = 15 * 60 * 60;
    
    public static final int DAY_MINUTES = 1440;

    public static final int EVERDAY_GET_ENERGY_NUM = 120;
    
    public static final int EVERY5MINUTE_GET_ENERGY_NUM = 1;

    public static final int EVERTIME_GET_ENERGY_NUM = 1;
    
    public static final int ENERGY_MAX = 200;
    
    public static final int ENERGY_BUY_MAX = 1000;

    public static final int MAX_LEVEL = 100;

    public static final int MONEY_ICON_ID = 123;

    public static final int RMB_ICON_ID = 888;

    public static final float EOL_PER = 0.85f;

    public static final double LOSE_M = 20;

    public static final double WIN_M = 65;

    public static final int BIND_POPUP = 0;

    public static final int EVERYDAYRACE_POPUP = 1;

    public static final int TRACK_POPUP = 2;

    public static final int TIER_POPUP = 3;

    public static final int USER_VERSION_UPDATE_POPUP = 4;
    
    public static final int SEND_ENERGY_POPUP = 5;
    
    public static final int SEND_ENERGY_FAILED_POPUP = 6;
    
    public static final int SEND_CAR_POPUP = 7;

    public static final int NO_RANK = 0;

    public static final int REACH_FINISH = 3;

    public static final int RACE_FAILTURE = 1;

    public static final int TIER_FIRST_SEE = 3;

    public static final int TIER_IS_NEW = 4;

    public static final int TIER_HAVE_BEEN_RUN = 1;
       
    public static final int YELLOW_LINE_TOURNAMENT_MODE_ID=300030;
    
    public static final int SEND_ENERGY_AMOUNT = 90;

    public static final Map<Integer, Float> ONE_VS_FIVE_MAP = new HashMap<Integer, Float>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put(1, 1.0f);
            put(2, 0.6f);
            put(3, 0.4f);
            put(4, 0.1f);
            put(5, 0.0f);
            put(6, 0.0f);
        }
    };

    public static final Map<Integer, Float> ONE_VS_ONE_MAP = new HashMap<Integer, Float>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put(1, 1.0f);
            put(2, 0.0f);
        }
    };

    public static final int NOT_RACE_START = 0;
    
    public static enum GET_SEND_ENERGY_STATUS {
    	
    }

}
