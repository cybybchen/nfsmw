package com.ea.eamobile.nfsmw.constants;

import java.util.HashMap;
import java.util.Map;

public enum ErrorConst {

    USER_NULL(1000, "该账号在其他地方登陆"), SESSION_EXPIRED(1001, "账号过期"), CHEAT_RACETIME(1002, "成绩异常"), NOT_RACE_START(1003,
            "比赛尚未开启"), WRONG_CAR_ID(1004, "使用车辆与赛道限制不符"), BIND_USER(1005, "账号已冻结"),WRONG_CONSUMBLE(1006,"模组异常"),
            USER_NOT_EXIST(1007, "该用户不存在"), USER_CAR_NOT_EXIST(1008, "该用户的车辆不存在");

    private final int code;

    private final String message;

    public int getCode() {
        return code;
    }

    public String getMesssage() {
        return message;
    }

    private ErrorConst(final int code, final String msg) {
        this.code = code;
        this.message = msg;
    }

    private static Map<Integer, ErrorConst> map = new HashMap<Integer, ErrorConst>();
    static {
        for (ErrorConst err : ErrorConst.values()) {
            map.put(err.getCode(), err);
        }
    }

    public static ErrorConst getErrorCode(int code) {
        return map.get(code);
    }

}
