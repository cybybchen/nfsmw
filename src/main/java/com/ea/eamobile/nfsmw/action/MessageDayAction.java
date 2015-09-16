package com.ea.eamobile.nfsmw.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.GarageLeaderboard;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.GarageLeaderboardService;
import com.ea.eamobile.nfsmw.service.JsonService;
import com.ea.eamobile.nfsmw.service.NewsService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserSessionService;

/**
 * Message Of Day Page
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/news")
public class MessageDayAction {

    private static final Logger log = LoggerFactory.getLogger(MessageDayAction.class);
    
    @Autowired
    UserService userService;
    @Autowired
    UserSessionService sessionService;
    @Autowired
    NewsService newsService;
    @Autowired
    JsonService jsonService;
    @Autowired
    GarageLeaderboardService garagelbService;

    @RequestMapping("")
    public String index(@RequestParam(value = "session", required = false) String session,
            @RequestParam(value = "display", required = false) Integer deviceType, Model model) {
        long userId = 0;
        try {
            UserSession userSession = sessionService.getSession(session);
            if (userSession != null) {
                userId = userSession.getUserId();
                User user = userService.getUser(userId);
                if (user != null) {
                    model.addAttribute("user", user);
                    // update weibo
                    String url = jsonService.getBindingStart(user.getWillowtreeToken(), "false");
                    model.addAttribute("url", url);
                }
            }
            //add garage leaderboard
            boolean inlist = false;
            GarageLeaderboard self = garagelbService.getSelf(userId);
            List<GarageLeaderboard> garageList = garagelbService.getToptenList();
            if(self!=null){
                if(garageList!=null&& garageList.size()>0){
                    for(GarageLeaderboard lb : garageList){
                        if(lb.getUserId()==self.getUserId()){
                            inlist = true;
                            break;
                        }
                    }
                }
            }
            model.addAttribute("garageList", garageList);
            model.addAttribute("self", self);
            
            // add news
            String boards = newsService.getBoard();
            String actions = newsService.getAction();
            String css = newsService.getCss();
            String contact = newsService.getContact();
            model.addAttribute("boards", boards);
            model.addAttribute("actions", actions);
            model.addAttribute("css", css);
            model.addAttribute("contact", contact);
            model.addAttribute("inlist", inlist);
            model.addAttribute("userId", userId);
            
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("message_of_day : userid={},fail",userId);
        }
        return getPage(deviceType);
    }
    private String getPage(Integer display) {
        if(display==null){
            display = 2;
        }
        switch (display) {
        case Const.DEVICE_IS_IPAD:
            return "msg";
        case Const.DEVICE_IS_IPHONE:
            return "msg_iphone4";
        case Const.DEVICE_IS_IPHONE5:
            return "msg_iphone5";
        case Const.DEVICE_IS_IPHONE6:
        	return "msg_iphone6";
        case Const.DEVICE_IS_IPHONE6_PLUS:
        	return "msg_iphone6_plus";
        }
        return "msg";
    }

}
