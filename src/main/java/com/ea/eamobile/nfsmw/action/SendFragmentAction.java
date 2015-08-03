package com.ea.eamobile.nfsmw.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.model.GotchaFragment;
import com.ea.eamobile.nfsmw.model.OperateBatch;
import com.ea.eamobile.nfsmw.model.OperateChangeRecord;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.service.OperateBatchService;
import com.ea.eamobile.nfsmw.service.OperateChangeRecordService;
import com.ea.eamobile.nfsmw.service.UserCarFragmentService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaFragmentService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

@Controller
@RequestMapping("/nfsmw/admin")
public class SendFragmentAction {

    protected static final String COOKIE_NAME = "ticket";

    @Autowired
    OperateBatchService operateBatchService;
    @Autowired
    OperateChangeRecordService operateChangeRecordService;
    @Autowired
    UserService userService;
    @Autowired
    GotchaFragmentService gotchaFragmentService;
    @Autowired
    UserCarFragmentService userCarFragmentService;

    @RequestMapping("sendfragment")
    public String sendfragmentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        model.addAttribute("message", "");
        return "admin/sendfragment";
    }

    protected boolean isLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(COOKIE_NAME) && cookie.getValue().equals(buildCookie())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected String buildCookie() {
        String username = ConfigUtil.ADMIN_USER;
        String password = ConfigUtil.ADMIN_PASSWORD;
        return DigestUtils.md5Hex(username + password);
    }

    @RequestMapping(value = "addfragment")
    public String addnewcar(@RequestParam(value = "userIdList", required = false) String userIdList,
            @RequestParam(value = "fragmentId", required = false) int fragmentId,
            @RequestParam(value = "amount", required = false) int amount, Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        String[] paramList = userIdList.split("\r\n");
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < paramList.length; i++) {
            long temp = Long.parseLong(paramList[i]);
            idList.add(temp);

        }
        StringBuffer buf = new StringBuffer();
        String message = "";
        OperateBatch operateBatch = new OperateBatch();

        operateBatchService.insert(operateBatch);

        for (Long userId : idList) {
            User user = userService.getUser(userId);
            if (user == null) {
                buf.append("failed ,user is null userId is " + userId + " ");
                continue;
            }
            GotchaFragment gotchaFragment = gotchaFragmentService.getGotchaPart(fragmentId);
            if (gotchaFragment == null) {
                buf.append("failed ,fragment is null fragment id is" + fragmentId + " ");
                continue;
            }
            UserCarFragment userCarFragment = userCarFragmentService.getUserCarFragment(userId, fragmentId);

            if (userCarFragment == null) {
                userCarFragment = new UserCarFragment();
                userCarFragment.setCount(0);
                userCarFragment.setFragmentId(fragmentId);
                userCarFragment.setUserId(userId);
                userCarFragmentService.insert(userCarFragment);
            }
            userCarFragment.setCount(userCarFragment.getCount() + amount);
            userCarFragmentService.update(userCarFragment);
            OperateChangeRecord operateChangeRecord = new OperateChangeRecord();
            operateChangeRecord.setAddCar("");
            operateChangeRecord.setAddEnergy(0);
            operateChangeRecord.setAddGold(0);
            operateChangeRecord.setAddMoney(0);
            operateChangeRecord.setAddFragCount(amount);
            operateChangeRecord.setFragmentId(fragmentId);
            operateChangeRecord.setCurrentFragCount(userCarFragment.getCount());
            operateChangeRecord.setOriginalFragCount(userCarFragment.getCount() - amount);
            operateChangeRecord.setBatchNum(operateBatch.getId());
            operateChangeRecord.setOriginalEnergy(user.getEnergy());
            operateChangeRecord.setOriginalGold(user.getGold());
            operateChangeRecord.setOriginalMoney(user.getMoney());
            operateChangeRecord.setCurrentEnergy(user.getEnergy());
            operateChangeRecord.setCurrentGold(user.getGold());
            operateChangeRecord.setCurrentMoney(user.getMoney());
            operateChangeRecord.setUserId(userId);
            operateChangeRecordService.insert(operateChangeRecord);

        }
        message = buf.toString();
        if (message.length() < 1) {
            message = "success";
        }
        model.addAttribute("message", message);
        return "admin/sendfragment";
    }

}
