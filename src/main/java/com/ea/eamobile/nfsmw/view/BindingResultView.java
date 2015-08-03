package com.ea.eamobile.nfsmw.view;

import java.io.Serializable;
import java.util.List;

import com.ea.eamobile.nfsmw.protoc.Commands.AccountInfo;

public class BindingResultView implements Serializable, BaseView {

    private static final long serialVersionUID = 1L;

    private boolean isBinding;

    private boolean needConfirm;

    private String originalToken;

    private String returnToken;

    private long userId;

    private String headUrl;

    private String nickname;
    
    private long returnUserId;

    public long getReturnUserId() {
        return returnUserId;
    }

    public void setReturnUserId(long returnUserId) {
        this.returnUserId = returnUserId;
    }

    /**
     * 三方账号列表
     */
    private List<AccountInfo> accounts;

    public String getOriginalToken() {
        return originalToken;
    }

    public void setOriginalToken(String originalToken) {
        this.originalToken = originalToken;
    }

    public List<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountInfo> accounts) {
        this.accounts = accounts;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isBinding() {
        return isBinding;
    }

    public void setBinding(boolean isBinding) {
        this.isBinding = isBinding;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
    }

    public String getReturnToken() {
        return returnToken;
    }

    public void setReturnToken(String returnToken) {
        this.returnToken = returnToken;
    }

}
