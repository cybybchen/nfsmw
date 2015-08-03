package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.protoc.Commands.AccountInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.UserWeiboInfo;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.HTTPStringResolver;
import com.ea.eamobile.nfsmw.utils.HttpUtil;
import com.ea.eamobile.nfsmw.view.BaseView;
import com.ea.eamobile.nfsmw.view.BindingResultView;
import com.ea.eamobile.nfsmw.view.BindingUrl;
import com.ea.eamobile.nfsmw.view.ErrorInfo;
import com.ea.eamobile.nfsmw.view.UserView;

/**
 * 
 * TODO willowtree接口需要提取到公共props文件里 参数怎么办？
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class JsonService {

    private static final Logger log = LoggerFactory.getLogger(JsonService.class);

    @Autowired
    private MemcachedClient cache;

    private HttpUtil<String> http = new HttpUtil<String>(new HTTPStringResolver());
    private static final String TOKEN = "access_token";
    private static volatile String ACCESS_TOKEN = null;

    public static String getAccessToken() {
        if (ACCESS_TOKEN != null) {
            return ACCESS_TOKEN;
        }
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/oauth/authorize";
        log.info(">>>>>>>>>>>The getAccessToken url is: " + url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", ConfigUtil.CLIENT_ID);
        params.put("client_secret", ConfigUtil.CLIENT_SECRET);
        params.put("grant_type", "client_credentials");
        params.put("scope", "ALL");
        try {
            String ret = HttpUtil.post(url, params);
            log.info(">>>>>>>>>>>>>The getAccessToken response json is: " + ret);
            JSONObject json = new JSONObject(ret);
            ACCESS_TOKEN = json.getString("access_token");
            log.info(">>>>>>>>>>>>>>>The getAccessToken token is: " + ACCESS_TOKEN);
            return ACCESS_TOKEN;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装login UserInfo
     * 
     * @param deviceId
     * @param nickname
     * @param token
     * @return
     */
    public BaseView login(String deviceId, String nickname, String token) {
        BaseView loginResult = null;
        String url = ConfigUtil.WILLOWTREE_PREFIX
                + "/login"
                + buildRequestParams("token", token, "nickname", nickname, "deviceId", deviceId, TOKEN,
                        getAccessToken(),"platform","1"); //channel=1=appstore define from wt
        log.info(">>>>>>>>>>>The login url is: " + url);
        loginResult = buildUserInfo(http.get(url), token);
        return loginResult;
    }

    /**
     * 封装getUser
     * 
     * @param USER_ID
     * @return
     */
    public BaseView getUser(String token) {
        UserView view = (UserView) cache.get(CacheKey.WT_USER + token);
        if (view == null) {
            String url = ConfigUtil.WILLOWTREE_PREFIX + "/user/get"
                    + buildRequestParams("token", token, TOKEN, getAccessToken());
            log.info(">>>>>>>>>>>>>>The getUser url is: " + url);
            BaseView result = buildUserInfo(http.get(url), token);
            // 正确返回才cache
            if (result instanceof UserView) {
                cache.set(CacheKey.WT_USER + token, result, MemcachedClient.HOUR);
                return result;
            }
        }
        return view;
    }

    public UserWeiboInfo getWeiboTokenInfo(String token) {
        UserWeiboInfo info = (UserWeiboInfo) cache.get(CacheKey.USER_WEIBO_INFO + token);
        if (info == null) {
            String url = ConfigUtil.WILLOWTREE_PREFIX + "/weibo/getAccessToken"
                    + buildRequestParams("token", token, TOKEN, getAccessToken());
            log.info(">>>>>>>>>>>>>The getWeiboTokenInfo url is: " + url);
            UserWeiboInfo.Builder builder = UserWeiboInfo.newBuilder();
            try {
                String ret = http.get(url);
                JSONObject json = new JSONObject(ret);
                if (isCorrectResult(json)) {
                    builder.setAccessToken(json.getString("accessToken"));
                    builder.setUid(json.getString("weiboId"));
                    log.info(">>>>>>>>>>>>correct json result");
                    info = builder.build();
                    cache.set(CacheKey.USER_WEIBO_INFO + token, info, MemcachedClient.HOUR);
                    return info;
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
            builder.setAccessToken("");
            builder.setUid("");
            info = builder.build();
            cache.set(CacheKey.USER_WEIBO_INFO + token, info, MemcachedClient.HOUR);
        }
        log.info(">>>>>>>>>>>>>get weibo info ={}", info);
        return info;
    }

    private BaseView buildUserInfo(String ret, String token) {
    	log.info(">>>>>>>>>>>>>The ret is: " + ret);
        UserView view = null;
        try {
            JSONObject json = new JSONObject(ret);
            if (isCorrectResult(json)) {
                view = new UserView();
                view.setToken(json.getString("token"));
                wrapUser(view, json.getJSONObject("user"));
                // wrap account list
                JSONArray accountJson = json.getJSONArray("accounts");
                view.setAccounts(wrapAccounts(accountJson));
                // add weiboinfo TODO merge to account
                view.setWeiboInfo(getWeiboTokenInfo(token));
            } else {
                return buildErrorInfo(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 判断是否是正确的json结果
     * 
     * @param json
     * @return
     */
    private boolean isCorrectResult(JSONObject json) {
        try {
            if (json != null && json.has("ret")) {
                return json.getInt("ret") == 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 把入参拼接成请求串 格式：参数名，参数值...
     * 
     * @param params
     * @return
     */
    private String buildRequestParams(String... params) {
        StringBuilder result = new StringBuilder();
        String real = "";
        if (params != null && params.length > 0) {
            result.append("?");
            for (int i = 0; i < params.length; i++) {
                result.append(params[i]);
                result.append((i % 2 == 0) ? "=" : "&");
            }
            real = result.substring(0, result.length() - 1);
        }
        return real;
    }

    /**
     * 得到微博登陆页面 根据forceLogin判断是否是正常登录或强制登录
     * 
     * @param USER_ID
     * @return
     */
    public String getBindingStart(String token, String forcelogin) {
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/weibo/binding/start";
        url += buildRequestParams("originalToken", token, TOKEN, getAccessToken(), "forceLogin", forcelogin,
                "forceConfirm", "true");
        log.info(">>>>>>>>>>>>the getBindingStart url is: " + url);
        String ret = http.get(url);
        try {
            JSONObject json = new JSONObject(ret);
            return json.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getBindingStart(String token) {
        return getBindingStart(token, "true");
    }

    public BindingUrl getBindingUrl(String token) {
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/weibo/binding/start";
        url += buildRequestParams("originalToken", token, TOKEN, getAccessToken(), "forceLogin", "true",
                "forceConfirm", "true");
        log.info(">>>>>>>>>>>>>>>the getBindingUrl is: " + url);
        String ret = http.get(url);
        BindingUrl bindingUrl = new BindingUrl();
        try {
            JSONObject json = new JSONObject(ret);
            bindingUrl.setAuthUrl(json.getString("url"));
            if (!json.isNull("closeUrl")) {
                bindingUrl.setCloseUrl(json.getString("closeUrl"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bindingUrl;
    }

    /**
     * 获取绑定结果信息
     * 
     * @param token
     * @return
     */
    public BaseView getBindingResult(String token) {
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/weibo/binding/result";
        url += buildRequestParams("originalToken", token, TOKEN, getAccessToken());
        log.info(">>>>>>>>>>>>>getBindingResult url: " + url);
        String ret = http.get(url);
        log.info(">>>>>>>>>>>binding result ={}", ret);
        BindingResultView result = new BindingResultView();
        try {
            JSONObject json = new JSONObject(ret);
            if (isCorrectResult(json)) {
                // wrap binding
                JSONObject bindingJson = json.getJSONObject("binding");
                wrapBinding(result, bindingJson);
                // wrap user view
                JSONObject userJson = json.getJSONObject("user");
                wrapBindingUser(result, userJson);
                // wrap accounts
                JSONArray accountJson = json.getJSONArray("accounts");
                result.setAccounts(wrapAccounts(accountJson));

            } else {
                return buildErrorInfo(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private BaseView buildErrorInfo(JSONObject json) throws JSONException {
        if (json.has("code")) {
            return new ErrorInfo(json.getString("code"), json.getString("message"));
        } else if (json.has("error")) {
            String err = json.getString("error");
            return new ErrorInfo(err, err);
        }
        return new ErrorInfo("unknown error", "unknown error");
    }

    private List<AccountInfo> wrapAccounts(JSONArray accountJson) {
        List<AccountInfo> accounts = Collections.emptyList();
        if (accountJson != null && accountJson.length() > 0) {
            accounts = new ArrayList<AccountInfo>();
            try {
                for (int i = 0; i < accountJson.length(); i++) {
                    JSONObject acc = accountJson.getJSONObject(i);
                    AccountInfo.Builder builder = AccountInfo.newBuilder();
                    builder.setType(acc.getInt("type"));
                    builder.setName(acc.getString("name"));
                    builder.setHeadUrl(acc.getString("headUrl"));
                    builder.setIsExpired(acc.getBoolean("expired"));
                    accounts.add(builder.build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }

    private void wrapUser(UserView view, JSONObject userJson) throws JSONException {
        view.setNickname(userJson.getString("name"));
        String headUrl = userJson.getString("headUrl");
        view.setHeadUrl(headUrl != null ? headUrl : "");
    }

    private void wrapBindingUser(BindingResultView result, JSONObject userJson) throws JSONException {
        result.setNickname(userJson.getString("name"));
        result.setHeadUrl(userJson.getString("headUrl"));
    }

    private void wrapBinding(BindingResultView result, JSONObject bindingJson) throws JSONException {
        boolean isBinding = bindingJson.getInt("isBinding") == 1;
        boolean needConfirm = bindingJson.getInt("needConfirm") == 1;
        result.setBinding(isBinding);
        result.setNeedConfirm(needConfirm);
        result.setReturnToken(bindingJson.getString("returnToken"));
        result.setUserId(bindingJson.getLong("userId"));
        if (bindingJson.has("returnUserId")) {
            result.setReturnUserId(bindingJson.getLong("userId"));
        }
    }

    /**
     * 取好友token list 不会空 添加了自己
     * 
     * @param token
     * @return
     */
    public List<String> getSinaFreindUserList(String token) {
        List<String> result = new ArrayList<String>();
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/friend/weibo/weibofriendlist";
        url += buildRequestParams("token", token + "", TOKEN, getAccessToken());
        String ret = http.get(url);
        try {
            JSONObject json = new JSONObject(ret);
            if (isCorrectResult(json)) {
                JSONArray sinaFriendList = json.getJSONArray("sinaFriendList");
                for (int i = 0; i < 200; i++) {
                    if (sinaFriendList.isNull(i)) {
                        break;
                    }
                    String uid = sinaFriendList.getString(i);
                    result.add(uid);
                }
            }
        } catch (JSONException e) {
        }
        // add self
        if (!result.contains(token)) {
            result.add(token);
        }
        return result;
    }

    /**
     * 得到绑定确认后返回的token
     * 
     * @param token
     * @return
     */
    public String getBindingConfirm(String token, boolean isOverride) {
        String url = ConfigUtil.WILLOWTREE_PREFIX + "/weibo/binding/confirm";
        url += buildRequestParams("originalToken", token, "isOverride", String.valueOf(isOverride), TOKEN,
                getAccessToken());
        log.info(">>>>>>>>>>>>>>getBindingConfirm url: " + url);
        String ret = http.get(url);
        log.info("binding confirm = {}", ret);
        try {
            JSONObject json = new JSONObject(ret);
            return json.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
