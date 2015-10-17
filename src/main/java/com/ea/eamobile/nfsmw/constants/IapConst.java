package com.ea.eamobile.nfsmw.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IapConst {
    
    public static final int IPSP_OLD_TYPE=0;
    
    public static final int IPSP_NEW_TYPE=1;
    
    public static final Map<Character, Integer> HEX_VALUE_MAP = new HashMap<Character, Integer>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put((char) '0', 0);
            put((char) '1', 1);
            put((char) '2', 2);
            put((char) '3', 3);
            put((char) '4', 4);
            put((char) '5', 5);
            put((char) '6', 6);
            put((char) '7', 7);
            put((char) '8', 8);
            put((char) '9', 9);
            put((char) 'a', 10);
            put((char) 'b', 11);
            put((char) 'c', 12);
            put((char) 'd', 13);
            put((char) 'e', 14);
            put((char) 'f', 15);
        }
    };

    public static final Map<String, Integer> GOLD_VALUE_MAP = new HashMap<String, Integer>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("com.ea.mtx.860047", 60);
            put("com.ea.mtx.860048", 300);
            put("com.ea.mtx.860051", 798);
            put("com.ea.mtx.860052", 1988);
            put("com.ea.mtx.860053", 3888);
            put("com.ea.mtx.860055", 6988);
        }
    };
    
    public static final Map<String, Integer> GOLD_ADD_MAP = new HashMap<String, Integer>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("com.ea.mtx.860047", 0);
            put("com.ea.mtx.860048", 60);
            put("com.ea.mtx.860051", 140);
            put("com.ea.mtx.860052", 400);
            put("com.ea.mtx.860053", 800);
            put("com.ea.mtx.860055", 1900);
        }
    };

    
    
    public static final Map<String, Integer> RMB_MAP = new HashMap<String, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put("com.ea.mtx.860047", 6);
            put("com.ea.mtx.860048", 30);
            put("com.ea.mtx.860051", 78);
            put("com.ea.mtx.860052", 188);
            put("com.ea.mtx.860053", 348);
            put("com.ea.mtx.860055", 618);
        }
    };
    
    public static final Map<String, String> SENDCAR_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("com.ea.mtx.860047", "ford_mustang_boss_302_2012_desc");
            put("com.ea.mtx.860048", "subaru_cosworth_impreza_sti_cs400_2010_desc");
            put("com.ea.mtx.860051", "lamborghini_countach_5000qv_1985_desc");
            put("com.ea.mtx.860052", "mercedesbenz_sl65_amg_blackseries_2009_desc");
            put("com.ea.mtx.860053", "porsche_911_carrera_s_2012_desc");
//            put("com.ea.mtx.859318", "porsche_911_carrera_s_2012_desc");
        }
    };
    
    public static final Map<String, String> SENDCAR_CNNAME_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("ford_mustang_boss_302_2012_desc", "野马");
            put("subaru_cosworth_impreza_sti_cs400_2010_desc", "斯巴鲁");
            put("lamborghini_countach_5000qv_1985_desc", "兰博基尼COUNTACH");
            put("mercedesbenz_sl65_amg_blackseries_2009_desc", "梅赛德斯奔驰 SL65");
            put("porsche_911_carrera_s_2012_desc", "保时捷 911 CARRERA");
        }
    };
    
    public static final List<String> RESTORE_IAP_PRODUCTID_LIST = new ArrayList<String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add("com.ea.mtx.871593");
			add("com.ea.mtx.871595");
			add("com.ea.mtx.871592");
			add("com.ea.mtx.871606");
		}
    };
}
