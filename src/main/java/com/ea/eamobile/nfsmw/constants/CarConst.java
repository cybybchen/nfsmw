package com.ea.eamobile.nfsmw.constants;

public class CarConst {

    public static final int LOCK = -1;

    public static final int UNLOCK = 0;
    /**
     * 已购买
     */
    public static final int OWN = 1;

    public static final int TIME_LIMITED = 2;

    public static final int TIME_LIMITED_AVAILABLE = 3;
    /**
     * 部件升级中
     */
    public static final int UPGRADING = 4;
    /**
     * 运输中
     */
    public static final int TRANSPORTING = 5;

    // 以下是插槽状态
    /**
     * 未开启
     */
    public static final int SLOT_CLOSE = 0;
    /**
     * 可升级
     */
    public static final int SLOT_UPGRADE = 1;

    public static final int SLOT_LOCKED = 2;
    /**
     * 最高级了
     */
    public static final int SLOT_MAX = 3;
    /**
     * 隐藏
     */
    public static final int SLOT_HIDE = 4;

    public static final int SLOT_MAX_LEVEL = 3;

    public static final String TIER_ZERO_CAR = "dodge_challenger_srt8_392_2011_desc";

    public static final String FREE_CAR = "jaguar_cx16_concept_2012_desc";

    public static final String SLOT_CAN_UNLOCK = "点击开启插槽";

    public static final String SLOT_CAN_NOT_UNLOCK = "请先开启上一个槽";

    public static final int NO_TIME_LIMIT = 0;

    public static final int IS_SPECIAL_CAR = 1;

    public static final int POWER_PACK_CONSUMBLE = 6;

    public static final int NITROUS_BURN_CONSUMBLE = 7;
}
