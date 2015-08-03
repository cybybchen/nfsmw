package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class ErrorInfo implements BaseView,Serializable{

    private static final long serialVersionUID = 1L;
    
    private String code;
    
    private String message;
    
    public ErrorInfo(){}
    public ErrorInfo(String code,String msg){
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
