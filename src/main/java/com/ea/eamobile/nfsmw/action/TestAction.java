package com.ea.eamobile.nfsmw.action;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.GarageLeaderboardService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserCarSlotService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserDailyRaceService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserSessionService;
import com.ea.eamobile.nfsmw.service.UserTrackService;
import com.ea.eamobile.nfsmw.service.WordService;
import com.ea.eamobile.nfsmw.view.CacheUser;

/**
 * TestCase 构建清理数据用
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/test")
public class TestAction {

    @Autowired
    UserService userService;
    @Autowired
    UserSessionService sessionService;
    @Autowired
    UserTrackService trackService;
    @Autowired
    UserDailyRaceService raceService;
    @Autowired
    UserCarService userCarService;
    @Autowired
    UserCarSlotService slotService;
    @Autowired
    UserChartletService chartletService;
    @Autowired
    WordService wordService;
    @Autowired
    GarageLeaderboardService glbService;

    @RequestMapping("test")
    public @ResponseBody
    String test(@RequestParam("id") long id) {
        CacheUser user = userService.getCacheUser(id);
        return user.toString();
    }

    @RequestMapping("word")
    public @ResponseBody
    String www(@RequestParam("s") String s) {
        return s + ": " + wordService.isCensorable(s);
    }

    @RequestMapping("clear/user")
    public @ResponseBody
    String clearUser(@RequestParam("session") String session) {
        // UserSession us = sessionService.getSession(session);
        // if(us!=null){
        // long userId = us.getUserId();
        // sessionService.delete(userId);
        // userService.delete(userId);
        // raceService.delete(userId);
        // }
        return "del succ";
    }

    @RequestMapping("clear/car")
    public @ResponseBody
    String clearCar(@RequestParam("userId") long userId, @RequestParam("carId") String carId,
            @RequestParam("chartletId") int chartletId) throws SQLException {
        // UserCar car = userCarService.getUserCarByUserIdAndCarId(userId, carId);
        // if (car != null) {
        // userCarService.deleteById(car.getId());
        // // delete 1st slot
        // List<UserCarSlot> slots = slotService.getSlotListByUserCarId(car.getId());
        // for (UserCarSlot slot : slots) {
        // slotService.deleteById(slot.getId());
        // }
        // //
        // UserChartlet let = chartletService.getUserChartlet(userId, chartletId);
        // if (let != null) {
        // chartletService.deleteById(let.getId());
        // }
        // }
        return "del succ";
    }

    @RequestMapping("get/userid")
    public @ResponseBody
    String getUserId(@RequestParam("session") String session) {
        long userId = 0;
        UserSession sess = sessionService.getSession(session);
        if (sess != null) {
            userId = sess.getUserId();
        }
        return String.valueOf(userId);
    }

    @RequestMapping("savedata")
    public @ResponseBody
    String saveTestData(@RequestParam("userId") long userId, @RequestParam("token") String token,
            @RequestParam("deviceId") String deviceId, @RequestParam("nickname") String nickname,
            @RequestParam("session") String session) {

        // save user
        User tokenUser = userService.getUserByWillowtreeToken(token);
        if (tokenUser != null) {
            userService.delete(tokenUser.getId());
        }
        User user = userService.getUser(userId);
        if (user == null) {
            user = new User();
            user.setId(userId);
            user.setName(nickname);
            user.setLevel(1);
            user.setTier(1);
            user.setMoney(10000);
            user.setEnergy(200);
            user.setEol(1500);
            user.setHeadUrl("http://testurl");
            user.setWillowtreeToken(token);
            user.setLastRegainEnergyDate((int) (new Date().getTime() / 1000));
            userService.insertUser(user);
        } else {
            user.setWillowtreeToken(token);
            userService.updateUser(user);
        }
        sessionService.save(userId, session);

        return "ok";
    }

}
