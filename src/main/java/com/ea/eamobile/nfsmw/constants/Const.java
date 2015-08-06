package com.ea.eamobile.nfsmw.constants;

/**
 * 通用常量定义
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public class Const {
    /**
     * 已经看过开场动画
     */
    public static final int HAS_PROLOGUED = 1;

    /**
     * 赛道完成度值
     */
    public static final int TRACK_FINISH_RATIO = 100;

    /**
     * 微博头像的索引值
     */
    public static final int HEAD_INDEX_WEIBO = -1;

    public static final int LEADERBOARD_NUM = 10;

    public static final int RPLEADERBOARD_ALL = 0;

    public static final int RPLEADERBOARD_FRIEAND = 1;

    public static final int TUTORIAL_REWARDID = 2;

    public static final int BIND_REWARDID = 3;
    
    public static final int GET_SEND_ENERGY_REWARDID = 5;

    public static final int NOT_INVISIBLE = 0;

    // Tournament const
    public static final int SHOW_IN_PROGRESS_TOURNAMENT = 0;
    public static final int SHOW_USER_FINISH_TOURNAMENT = 1;
    // 可以报名
    public static final int CAN_SIGNUP_TOURNAMENT = 0;
    // 已经报名
    public static final int HAS_SIGNUPED_TOURNAMENT = 1;
    // 可以重复报名
    public static final int CAN_REPEAT_SIGNUP = 1;
    public static final int SHOW_RANK_COUNT_IN_TOURMESSAGE = 3;
    // 比赛是否提供车辆
    public static final int PROVIDE_CAR = 1;

    public static final int ONE_DAY_SECONDS = 24 * 60 * 60;

    /**
     * 改名的花费
     */
    public static final int CHANGE_NAME_COST = 100;
    public static final int HEAD_INDEX_MAX = 20;

    public static final int JAGUAR_MODE_ID = 300000;

    public static final int JAGUAR_HINT_SIZE = 4;

    public static final String GET_REWARD = "获得奖励";
    
    public static final String GET_SEND_ENERGY = "汽油领取成功";
    
    public static final String GET_SEND_ENERGY_FAILED = "汽油领取失败";

    public static final int HAVE_GET_REWARD = 1;

    public static final int NOT_HAVE_GET_REWARD = 0;

    public static final int NO_DAILY_RACE_TIMES = 0;

    public static final int DEVICE_IS_IPHONE = 1;
    public static final int DEVICE_IS_IPAD = 2;
    public static final int DEVICE_IS_IPHONE5 = 3;

    public static final int RENT_JAGUAR_IPAD = 1;
    public static final int RENT_JAGUAR_IPHONE = 2;
    public static final int RENT_JAGUAR_IPHONE5 = 8;
    public static final int GET_JAGUAR_IPAD = 3;
    public static final int GET_JAGUAR_IPHONE = 4;
    public static final int GET_JAGUAR_IPHONE5 = 7;
    public static final int JAGUAR_MORE_IPAD = 5;
    public static final int JAGUAR_MORE_IPHONE = 6;

    //system config
    public static final int CTA_PARAM_SYSTEMCONFIG_ID = 2;
    public static final int SPEED_FACTOR_SYSTEMCONFIG_ID = 3;
    public static final int RACE_REWARD_SHARE_OPEN_SYSTEMCONFIG_ID = 4;
    public static final int TRACK_UNLOCK_INFO_SHARE_OPEN_SYSTEMCONFIG_ID = 5;
    public static final int CAR_UNLOCK_INFO_SHARE_OPEN_SYSTEMCONFIG_ID = 6;
    public static final int SPEED_WALL_SHARE_OPEN_SYSTEMCONFIG_ID = 7;
    public static final int BINDING_WEIBO_SHARE_OPEN_SYSTEMCONFIG_ID = 8;
    public static final int TOURNAMENT_REWARD_SHARE_OPEN_SYSTEMCONFIG_ID = 9;
    public static final int BUY_CAR_SHARE_OPEN_SYSTEMCONFIG_ID = 10;
    public static final int UPDATE_CONSUMBLE_SHARE_OPEN_SYSTEMCONFIG_ID = 11;
    public static final int JAGUAR_SHARE_OPEN_SYSTEMCONFIG_ID = 12;
    public static final int MOD_CONFIG = 13; //MOD开关 0close,1open,2autoshow
    
	public static final int BUY_CAR_SEND_CHARTLET_START = 43;	// 买金币车送贴图开始时间
	public static final int BUY_CAR_SEND_CHARTLET_END = 44;		// 买金币车送贴图结束时间
	public static final int MODE_FINISH_RATIO_START_TIME = 45;// 职业生涯赛关卡完成度翻倍开始时间
	public static final int MODE_FINISH_RATIO_END_TIME = 46;// 职业生涯赛关卡完成度翻倍结束时间
	public static final int MODE_FINISH_RATIO_ADD_TIMES = 47;// 职业生涯赛关卡完成度翻倍次数
	
    public static final String MOD_CLOSE = "0";
    public static final String MOD_OPEN = "1";
    public static final String MOD_POPUP = "2";
    
    public static final int HOUR_SECONDS = 60 * 60;

    public static final float DEFAULT_MODE_DISTANCE = 4.5f;

    // user certification type
    public static final int CERT_TYPE_DEVICE = 1;
    public static final int CERT_TYPE_WEIBO = 2;

    public static final int DEFAULT_CAR_MAX_SPEED = 400;

    public static final String IAP_URL = "https://buy.itunes.apple.com/verifyReceipt";

    public static final String SANDBOX_URL = "https://sandbox.itunes.apple.com/verifyReceipt";

    public static final int IS_BAN = 1 << 0;

    public static final int IS_NORECORD = 1 << 1;

    public static final int IS_NOGHOST = 1 << 2;

    public static final int IS_GHOSTRECORD = 1 << 3;

    public static final int IS_SHOWMOD = 1 << 4;

    public static final int DEFAULT_USER_STATUS = 0;

    public static final int INIT_USER_NUMBER = 5000;

    public static final int GOLD_TYPE = 1;

    public static final int LEADERBOARD_SIZE = 10;

    public static final int MAX_GHOST_NUM = 20000;

    public static final int FIRST_MODE_ID = 100111;

    public static final float FIRST_GHOST_TIME = 116.2f;

    public static final float SECOND_GHOST_TIME = 116.9f;

    public static final float THIRD_GHOST_TIME = 117.2f;

    public static final float FORTH_GHOST_TIME = 117.5f;

    public static final float FIFTH_GHOST_TIME = 118.1f;

    public static final int GOLD = 1;

    public static final int CURRENCY = 2;

    public static final int MAX_RP_NUM = 8945759;

    public static final int CAN_CAL_TOURNAMENT_ONLINE_SIZE = 2;

    public static final int VERSION_ONE_NUM = 0;

    public static final int VERSION_TWO_NUM = 1;

    public static final int VERSION_THREE_NUM = 2;

    public static final int USER_UPDATE_VERSION_REWARD_ID = 4;

    public static final int CHEAT_TYPE_CAR_CONFIG_FILE = 1;

    public static final int CHEAT_TYPE_EXE_MD5 = 2;

    public static final long EA_USER_ID = 10000;

    public static final int SAND_BOX_STATUS = 21007;

    public static final int DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS = 12 * 60 * 60;

    public static final int GOTCHA_CONTENT_SIZE = 3;

    public static final String CHEAT_RIGHT_MD5 = "123";

    public static final int CHECK_IS_MOST_RACETIME = 1;

    public static final int CHECK_IS_LEAST_RACETIME = 2;

    public static final float MAX_RACETIME = 250;

}
