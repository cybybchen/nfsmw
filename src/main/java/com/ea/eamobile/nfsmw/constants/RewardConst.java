package com.ea.eamobile.nfsmw.constants;

import java.util.HashMap;
import java.util.Map;

public class RewardConst {
	public static final int PACKAGE_BUY_GOLD = 1 << 4;
	public static final int PACKAGE_BUY_VIP = 1 << 3;
	public static final int PACKAGE_BUY_PROP = 1 << 2;
	public static final int PACKAGE_BUY_NEW = 1 << 1;
	
	public static final int TYPE_REWARD_ERENGY = 1001;
	public static final int TYPE_REWARD_MONEY = 1002;
	public static final int TYPE_REWARD_GOLD = 1003;
	public static final int TYPE_REWARD_PROP = 2000;
	public static final int TYPE_REWARD_CAR = 3000;
	
	public static final int TYPE_REWARD_PROP_FREELOTTERY = 1;
	
	public static final Map<Integer, String> REWARD_CAR_MAP = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(1, "ford_mustang_boss_302_2012_desc");
            put(2, "subaru_cosworth_impreza_sti_cs400_2010_desc");
            put(2, "lamborghini_countach_5000qv_1985_desc");
        }
    };
}
