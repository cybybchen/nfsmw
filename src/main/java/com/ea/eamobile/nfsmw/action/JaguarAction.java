package com.ea.eamobile.nfsmw.action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.JaguarOwnInfo;
import com.ea.eamobile.nfsmw.model.JaguarRentInfo;
import com.ea.eamobile.nfsmw.service.JaguarOwnInfoService;
import com.ea.eamobile.nfsmw.service.JaguarRentInfoService;
import com.ea.eamobile.nfsmw.view.DeviceCount;

/**
 * jaguar表单
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/jaguar")
public class JaguarAction {

    private static final String username = "eamobilechina";
    private static final String pwd = "891MR&Y)~fkoi6Mx";

    @Autowired
    private JaguarOwnInfoService ownService;
    @Autowired
    private JaguarRentInfoService rentService;

    @RequestMapping("lottery")
    public String lottery(HttpServletResponse res, Model model, @RequestParam("display") int display) {
        model.addAttribute("display", display);
        return getPage("lottery", display);
    }
    
    @RequestMapping("own")
    public String own(HttpServletResponse res, Model model, @RequestParam("display") int display) {
        res.addHeader("Cache-Control", "no-cache");
        res.addHeader("Expires", "-1");
        model.addAttribute("display", display);
        return getPage("own", display);
    }

    @RequestMapping("own/submit")
    public String ownSumbit(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        int year = Integer.parseInt(request.getParameter("year"));
        String monthStr = request.getParameter("month");
        int season = Integer.parseInt(monthStr); // in fact is season
        String testArea = request.getParameter("try");
        int budget = Integer.parseInt(request.getParameter("budget"));
        String ip = request.getRemoteAddr();
        // 判断手机邮箱是否已经提交过 不提示错误信息
        int display = Integer.parseInt(request.getParameter("display"));
        int age = Integer.parseInt(request.getParameter("age"));
        if (!ownService.hasSubmit(mobile, email)) {
            ownService.save(name, mobile, email, gender, province, city, year, season, testArea, budget, ip, display,age);
            return "redirect:/nfsmw/jaguar/own-thanks?display=" + display;
        }
        return getPage("own_result", display);
    }

    @RequestMapping("own-thanks")
    public String ownThank(@RequestParam("display") int display) {
        return getPage("own_result", display);
    }

    @RequestMapping("rent")
    public String rent(HttpServletResponse res,Model model, @RequestParam("display") int display) {
        res.addHeader("Cache-Control", "no-cache");
        res.addHeader("Expires", "-1");
        model.addAttribute("display", display);
        return getPage("rent", display);
    }

    @RequestMapping("rent/submit")
    public String rentSubmit(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        String ip = request.getRemoteAddr();
        int display = Integer.parseInt(request.getParameter("display"));
        int age = Integer.parseInt(request.getParameter("age"));
        if (!rentService.hasSubmit(mobile, email)) {
            rentService.save(name, mobile, email, gender, ip, display,age);
            return "redirect:/nfsmw/jaguar/rent-thanks?display=" + display;
        }
        return getPage("rent_result", display);
    }

    @RequestMapping("rent-thanks")
    public String rentThank(@RequestParam("display") int display) {
        return getPage("rent_result", display);
    }

    private String getPage(String page, int display) {
        switch (display) {
        case Const.DEVICE_IS_IPAD:
            return "jaguar/" + page + "_ipad";
        case Const.DEVICE_IS_IPHONE:
            return "jaguar/" + page + "_iphone4";
        case Const.DEVICE_IS_IPHONE5:
            return "jaguar/" + page + "_iphone5";
        }
        return "jaguar/"+page+"_ipad";
    }

    @RequestMapping("admin")
    public String admin(@RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "from", required = false) Integer from, Model model, HttpServletRequest request) {
        boolean logined = false;
        Cookie[] cookies = request.getCookies();
        for (Cookie cook : cookies) {
            if (cook.getName().equals("jaguarTicket")) {
                String ticket = DigestUtils.md5Hex(username + pwd);
                if (cook.getValue().equals(ticket)) {
                    logined = true;
                }
            }
        }
        if (!logined) {
            return "jaguar/login";
        }
        // type=1永久 =2租用
        if (type == null) {
            type = 1;
        }
        // 分页
        if (from == null || from < 0) {
            from = 0;
        }
        int total = 0;
        // 取各型号数量
        List<DeviceCount> counts = null;
        if (type == 1) {
            counts = ownService.getDeviceCount();
            List<JaguarOwnInfo> ownDatas = ownService.getJaguarOwnInfoList(email, mobile, from);
            total = ownService.getTotal();
            model.addAttribute("ownDatas", ownDatas);
        } else {
            counts = rentService.getDeviceCount();
            List<JaguarRentInfo> rentDatas = rentService.getJaguarRentInfoList(email, mobile, from);
            total = rentService.getTotal();
            model.addAttribute("rentDatas", rentDatas);
        }
        model.addAttribute("type", type);
        model.addAttribute("counts", counts);
        model.addAttribute("from", from);
        model.addAttribute("total", total);
        return "jaguar/admin";
    }

    @RequestMapping("admin/login")
    public String login(@RequestParam(value = "username", required = false, defaultValue = "") String userName,
            @RequestParam(value = "password", required = false, defaultValue = "") String password, Model model,
            HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return "jaguar/login";
        }
        if (userName.equals(username) && password.equals(pwd)) {
            String ticket = DigestUtils.md5Hex(username + pwd);
            Cookie cookie = new Cookie("jaguarTicket", ticket);
            response.addCookie(cookie);
            return "jaguar/admin";
        }
        model.addAttribute("message", "username or password error.");
        return "jaguar/login";
    }

    @RequestMapping("admin/logout")
    public String login(HttpServletResponse response) {
        Cookie cookie = new Cookie("jaguarTicket", null);
        response.addCookie(cookie);
        return "jaguar/login";
    }

}
