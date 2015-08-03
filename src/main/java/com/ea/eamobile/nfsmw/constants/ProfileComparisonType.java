package com.ea.eamobile.nfsmw.constants;

public enum ProfileComparisonType {

    GARAGE_SCORE(1),//("超跑名流", "车库总评分", "分", 0, false),
    TOURNAMENT_CHAMPION(2),//("一骑绝尘", "获得锦标赛冠军次数", "次", 0, false),
    TAKE_DOWN_COPS(3),//("横冲直撞", "撞毁警车数量", "辆", 0, false),
    CONSUMABLE_COLLECTOR(4),//("有备无患", "使用模组数", "个", 0, false),
    DRIFT_KING(5),//("漂移之王", "漂移总距离", "", 1, false),
    JUMPER(6),//("飞车手", "飞跃总距离", "", 1, false),
    BILLBOARD_DESTROYER(7),//("特技演员", "撞碎广告牌数", "个", 0, false);
    RP_NUM(8);//("车手的荣耀", "获取的声望数量", "点", 0, false);

    private final int index;
	
    private ProfileComparisonType(int index) {
    	this.index = index;
    }
    
    public int getIndex() {
    	return this.index;
    }
}
