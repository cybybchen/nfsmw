package com.ea.eamobile.nfsmw.constants;

public enum GotchaType {

    FRAGMENT(0, "fragment", "个", true, true), MONEY(1, "money", "$", false, false), GOLD(2, "gold", "金币", true, false), ENERGY(
            3, "energy", "点汽油", true, false);

    private final int type;
    private final String name;
    private final String unit;
    /**
     * 单位是否后置
     */
    private final boolean suffixUnit;
    private final boolean needExtraUnit;

    public String getUnit() {
        return unit;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isSuffixUnit() {
        return suffixUnit;
    }

    private GotchaType(int type, String name, String unit, boolean suffixUnit, boolean needExtraUnit) {
        this.type = type;
        this.name = name;
        this.unit = unit;
        this.suffixUnit = suffixUnit;
        this.needExtraUnit = needExtraUnit;
    }

    public static GotchaType getType(int type) {
        for (GotchaType gt : values()) {
            if (gt.type == type) {
                return gt;
            }
        }
        return null;
    }

    public boolean needExtraUnit() {
        return needExtraUnit;
    }
}
