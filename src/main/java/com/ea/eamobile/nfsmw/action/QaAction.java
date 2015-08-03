package com.ea.eamobile.nfsmw.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserTrack;
import com.ea.eamobile.nfsmw.service.RpLevelService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserTrackService;

@Controller
@RequestMapping("/nfsmw/admin")
public class QaAction {

    @Autowired
    UserService userService;
    @Autowired
    RpLevelService rplevelService;
    @Autowired
    UserTrackService userTrackService;

    @RequestMapping("qahome")
    public String home(Model model, HttpServletResponse response) {
        return "qa/home";
    }

    @RequestMapping("qa/searchuser")
    public String searchByUserName(
            @RequestParam(value = "username", required = false, defaultValue = "") String userName, Model model,
            HttpServletResponse response) {
        User user = userService.getUserByName(userName);
        model.addAttribute("user", user);
        model.addAttribute("message", "");
        return "qa/userinfo";
    }

    @RequestMapping("qa/searchusertrack")
    public String searchUserTrack(@RequestParam(value = "userid", required = false, defaultValue = "") Long userId,
            Model model, HttpServletResponse response) {
        try {
            List<UserTrack> userTrackList = userTrackService.getUserTracks(userId);
            model.addAttribute("userTrackList", userTrackList);
            model.addAttribute("message", "");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "qa/usertrackinfo";
    }

    @RequestMapping("qa/updateuser")
    public String updateUser(@RequestParam(value = "userId", required = false, defaultValue = "") Long userId,
            @RequestParam(value = "userName", required = false, defaultValue = "") String userName,
            @RequestParam(value = "userGold", required = false, defaultValue = "") Integer userGold,
            @RequestParam(value = "userMoney", required = false, defaultValue = "") Integer userMoney,
            @RequestParam(value = "userRpNum", required = false, defaultValue = "") Integer userRpNum,
            @RequestParam(value = "userMwNum", required = false, defaultValue = "") Integer userMwNum,
            @RequestParam(value = "userEnergy", required = false, defaultValue = "") Integer userEnergy,
            @RequestParam(value = "tier", required = false, defaultValue = "") Integer tier,
            @RequestParam(value = "userHeadIndex", required = false, defaultValue = "") Integer userHeadIndex,
            @RequestParam(value = "userHeadUrl", required = false, defaultValue = "") String userHeadUrl, Model model,
            HttpServletResponse response) {
        User user = userService.getUser(userId);
        User temp = userService.getUserByName(userName);
        if (temp != null && temp.getId() != userId) {
            model.addAttribute("user", user);
            model.addAttribute("message", "double name");
            return "qa/userinfo";
        }
        if(userHeadIndex<-1||userHeadIndex>7){
            model.addAttribute("user", user);
            model.addAttribute("message", "wrong headIndex");
            return "qa/userinfo";
        }
        user.setGold(userGold);
        user.setMoney(userMoney);
        user.setRpNum(userRpNum);
        user.setStarNum(userMwNum);
        user.setTier(tier);
        user.setTierStatus(3);
        user.setName(userName);
        user.setHeadIndex(userHeadIndex);
        user.setHeadUrl(userHeadUrl);
        user.setEnergy(userEnergy);
        int updatedLevel = rplevelService.getLevelByRpNum(user.getRpNum());
        user.setLevel(updatedLevel);
        userService.updateUser(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "Update successful!");
        return "qa/userinfo";
    }

    @RequestMapping("qa/changeusertrack")
    public String changeUserTrack(@RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "userId", required = false, defaultValue = "") Integer userId, Model model,
            HttpServletResponse response) {
        UserTrack userTrack = userTrackService.queryUserTrack(id);
        model.addAttribute("userTrack", userTrack);
        model.addAttribute("message", "");
        return "qa/trackinfo";
    }

    @RequestMapping("qa/updatetrackinfo")
    public String updateTrackInfo(@RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "value", required = false, defaultValue = "") Integer value, Model model,
            HttpServletResponse response) {
        UserTrack userTrack = userTrackService.queryUserTrack(id);
        userTrack.setValue(value);
        userTrackService.updateUserTrack(userTrack);
        model.addAttribute("userTrack", userTrack);
        model.addAttribute("message", "Update successful!");
        return "qa/trackinfo";
    }

}
