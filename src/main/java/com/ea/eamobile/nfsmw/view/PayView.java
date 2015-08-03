package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class PayView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private String orderId;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PayView:[orderid=" + orderId + ",code=" + code + ",status=" + status + ",msg=" + msg + "]";
    }
}
