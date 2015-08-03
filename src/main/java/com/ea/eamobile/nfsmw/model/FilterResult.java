package com.ea.eamobile.nfsmw.model;

import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand;

public class FilterResult {
    private Boolean canContinue;
    
    private ResponseCommand.Builder responseBuilder;
    
    private long userId;
    
    public Boolean getCanContinue() {
        return canContinue;
    }

    public void setCanContinue(Boolean canContinue) {
        this.canContinue = canContinue;
    }
    
    public ResponseCommand.Builder getResponseBuilder() {
        return responseBuilder;
    }

    public void setResponseBuilder(ResponseCommand.Builder responseBuilder) {
        this.responseBuilder = responseBuilder;
    }
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
