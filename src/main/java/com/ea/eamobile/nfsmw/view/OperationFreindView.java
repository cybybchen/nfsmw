package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;

public class OperationFreindView implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
      
    int ret;
    
    String code;
    
    String message;
    
    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
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
