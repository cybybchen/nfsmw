package com.ea.eamobile.nfsmw.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.RpLevelService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserTrackService;

@Controller
@RequestMapping("/nfsmw/admin")
public class UserBanAction {

    @Autowired
    UserService userService;
    @Autowired
    RpLevelService rplevelService;
    @Autowired
    UserTrackService userTrackService;

    @RequestMapping("userChangeStatus")
    public String home(Model model, HttpServletResponse response) {
        model.addAttribute("message", "");
        return "admin/userChangeStatus";
    }

    @RequestMapping("changeuserstatus")
    public String changeUserStatus(
            @RequestParam(value = "idList", required = false, defaultValue = "") String idlist,
            @RequestParam(value = "ban", required = false, defaultValue = "0") int ban,
            @RequestParam(value = "norecord", required = false, defaultValue = "0") int norecord,
            @RequestParam(value = "noghost", required = false, defaultValue = "0") int noghost,
            @RequestParam(value = "ghostRecord", required = false, defaultValue = "0") int ghostRecord,
            @RequestParam(value = "showMod", required = false, defaultValue = "0") int showMod,
            @RequestParam(value = "isBanChanged", required = false, defaultValue = "0") int isBanChanged,
            @RequestParam(value = "isRecordChanged", required = false, defaultValue = "0") int isRecordChanged,
            @RequestParam(value = "isNoGhostChanged", required = false, defaultValue = "0") int isNoGhostChanged,
            @RequestParam(value = "isRecordGhostChanged", required = false, defaultValue = "0") int isRecordGhostChanged,
            @RequestParam(value = "isShowModChanged", required = false, defaultValue = "0") int isShowModChanged,
            Model model, HttpServletResponse response) {
        String message = "";
        String[] paramList = idlist.split("\r\n");
        List<Long> idList = new ArrayList<Long>();
        for (int i = 0; i < paramList.length; i++) {
            long temp = Long.parseLong(paramList[i]);
            idList.add(temp);

        }
        StringBuffer buf = new StringBuffer();
        for (Long userId : idList) {
            User user = userService.getUserForAdmin(userId);
            if (user == null) {
                buf.append("user " + userId + " is null");
                continue;
            }
            int status = user.getAccountStatus();
            if (isBanChanged == 1) {
                if (ban == 0 && ((status & Const.IS_BAN) != 0)) {
                    status = status - Const.IS_BAN;
                } else if (ban == 1) {
                    status = status | Const.IS_BAN;
                }
            }
            if (isRecordChanged == 1) {
                if (norecord == 0 && ((status & Const.IS_NORECORD) != 0)) {
                    status = status - Const.IS_NORECORD;
                } else if (norecord == 1) {
                    status = status | Const.IS_NORECORD;
                }
            }
            if (isNoGhostChanged == 1) {
                if (noghost == 0 && ((status & Const.IS_NOGHOST) != 0)) {
                    status = status - Const.IS_NOGHOST;
                } else if (noghost == 1) {
                    status = status | Const.IS_NOGHOST;
                }
            }
            if (isRecordGhostChanged == 1) {
                if (ghostRecord == 0 && ((status & Const.IS_GHOSTRECORD) != 0)) {
                    status = status - Const.IS_GHOSTRECORD;
                } else if (ghostRecord == 1) {
                    status = status | Const.IS_GHOSTRECORD;
                }
            }
            if (isShowModChanged == 1) {
                if (showMod == 0 && ((status & Const.IS_SHOWMOD) != 0)) {
                    status = status - Const.IS_SHOWMOD;
                } else if (showMod == 1) {
                    status = status | Const.IS_SHOWMOD;
                }
            }
            user.setAccountStatus(status);
            userService.updateUser(user);

        }
        message = buf.toString();
        if (message.length() < 1) {
            message = "success";
        }
        model.addAttribute("message", message);
        return "admin/userChangeStatus";
    }
    // private void deleteCareerRecord(List<Long> idList){
    //
    // }

}
