package com.ea.eamobile.nfsmw.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.model.ComparatorTournamentOnlineByStartTime;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.view.TournamentOnlineView;

@Controller
@RequestMapping("/nfsmw/admin")
public class TournamentAction {

    protected static final String COOKIE_NAME = "ticket";

    @Autowired
    TournamentOnlineService tournamentOnlineService;

    @RequestMapping(value = "tournament")
    public String tournamentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        List<TournamentOnline> toList = tournamentOnlineService.getTournamentOnlineList();
        ComparatorTournamentOnlineByStartTime cl = new ComparatorTournamentOnlineByStartTime();
        Collections.sort(toList, cl);
        List<TournamentOnlineView> result = new ArrayList<TournamentOnlineView>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        for (TournamentOnline tournamentOnline : toList) {
            TournamentOnlineView tv = new TournamentOnlineView();
            tv.setId(tournamentOnline.getId());
            tv.setTournamentId(tournamentOnline.getTournamentId());
            long realEndTime = tournamentOnline.getEndTime();
            tv.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
            long realStartTime = tournamentOnline.getStartTime();
            tv.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
            result.add(tv);
        }
        model.addAttribute("tournamentOnlineList", result);
        return "admin/tournament";

    }

    @RequestMapping(value = "updateTournament")
    public String updateTournament(Model model, HttpServletRequest request,
            @RequestParam(value = "tournamentOnlineId", required = false) Integer tournamentOnlineId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        TournamentOnline tournamentOnline = tournamentOnlineService.getTournamentOnline(tournamentOnlineId);
        if (tournamentOnline == null) {
            model.addAttribute("erro", "tournamentOnline is not exist");
            return "admin/tournamentUpdate";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        TournamentOnlineView tv = new TournamentOnlineView();
        tv.setId(tournamentOnline.getId());
        tv.setTournamentId(tournamentOnline.getTournamentId());
        long realEndTime = tournamentOnline.getEndTime();
        tv.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = tournamentOnline.getStartTime();
        tv.setStartTime(sdf.format(new Date(realStartTime * 1000l)));

        model.addAttribute("tournamentOnline", tournamentOnline);
        model.addAttribute("tournamentOnlineView", tv);

        return "admin/tournamentUpdate";

    }

    @RequestMapping(value = "doUpdateTournamentOnline")
    public String doUpdateTournamentOnline(Model model, HttpServletRequest request,
            @RequestParam(value = "tournamentId", required = false) Integer tournamentId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "info", required = false) String info,
            @RequestParam(value = "endContent", required = false) String endContent,
            @RequestParam(value = "startContent", required = false) String startContent,
            @RequestParam(value = "weiboShareContent", required = false) String weiboShareContent,
            @RequestParam(value = "orderId", required = false) Integer orderId,
            @RequestParam(value = "tournamentOnlineId", required = false) Integer tournamentOnlineId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        TournamentOnline tournamentOnline = tournamentOnlineService.getTournamentOnline(tournamentOnlineId);
        if (tournamentOnline == null) {
            model.addAttribute("info", "tournamentOnline is not exist");
            return "admin/tournamentResult";
        }
        if (System.currentTimeMillis() / 1000 > tournamentOnline.getStartTime()) {
            model.addAttribute("info", "tournamentOnline have been started");
            return "admin/tournamentResult";
        }
        tournamentOnline.setTournamentId(tournamentId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);
            tournamentOnline.setEndTime((int) (endDate.getTime() / 1000));
            tournamentOnline.setStartTime((int) (startDate.getTime() / 1000));
            tournamentOnline.setIsFinish(0);
            tournamentOnline.setInfo(info);
            tournamentOnline.setEndContent(endContent);
            tournamentOnline.setStartContent(startContent);
            tournamentOnline.setWeiboShareContent(weiboShareContent);
            tournamentOnline.setOrderId(orderId);
            tournamentOnlineService.update(tournamentOnline);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        TournamentOnlineView tv = new TournamentOnlineView();
        tv.setId(tournamentOnline.getId());
        tv.setTournamentId(tournamentOnline.getTournamentId());
        long realEndTime = tournamentOnline.getEndTime();
        tv.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = tournamentOnline.getStartTime();
        tv.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        model.addAttribute("message", "success");
        model.addAttribute("tournamentOnline", tournamentOnline);
        model.addAttribute("tournamentOnlineView", tv);

        return "admin/tournamentUpdate";

    }

    @RequestMapping(value = "deleteTournamentOnlineId")
    public String deleteTournamentOnlineId(Model model, HttpServletRequest request,
            @RequestParam(value = "tournamentOnlineId", required = false) Integer tournamentOnlineId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        TournamentOnline tournamentOnline = tournamentOnlineService.getTournamentOnline(tournamentOnlineId);
        if (tournamentOnline == null) {
            model.addAttribute("info", "tournamentOnline is not exist");
            return "admin/tournamentResult";
        }
        if (System.currentTimeMillis() / 1000 > tournamentOnline.getStartTime()) {
            model.addAttribute("info", "tournamentOnline have been started");
            return "admin/tournamentResult";
        }
        tournamentOnlineService.deleteById(tournamentOnlineId);
        model.addAttribute("info", "success");

        return "admin/tournamentResult";

    }

    protected String buildCookie() {
        String username = ConfigUtil.ADMIN_USER;
        String password = ConfigUtil.ADMIN_PASSWORD;
        return DigestUtils.md5Hex(username + password);
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

    @RequestMapping(value = "addNewTournamentOnlineId")
    public String addNewTournamentOnlineId(
            @RequestParam(value = "tournamentIdList", required = false) String tournamentIdList,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "info", required = false) String info,
            @RequestParam(value = "endContent", required = false) String endContent,
            @RequestParam(value = "startContent", required = false) String startContent,
            @RequestParam(value = "weiboShareContent", required = false) String weiboShareContent,
            @RequestParam(value = "orderId", required = false) Integer orderId,
            @RequestParam(value = "addSum", required = false, defaultValue = "0") int addSum, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String result = "";
        String[] paramList = tournamentIdList.split(",");
        List<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < paramList.length; i++) {
            int temp = Integer.parseInt(paramList[i]);
            idList.add(temp);

        }
        for (int j = 0; j < idList.size(); j++) {
            int tournamentId = idList.get(j);
            for (int i = 0; i < addSum; i++) {
                TournamentOnline to = new TournamentOnline();
                to.setTournamentId(tournamentId);

                Date endDate;
                Date startDate;
                try {
                    startDate = sdf.parse(startTime);

                    endDate = sdf.parse(endTime);
                    to.setEndTime((int) ((DateUtil.changeDate(endDate, i * 60 * 24).getTime()) / 1000));
                    to.setStartTime((int) ((DateUtil.changeDate(startDate, i * 60 * 24).getTime()) / 1000));
                    to.setIsFinish(0);
                    to.setInfo(info);
                    to.setEndContent(endContent);
                    to.setStartContent(startContent);
                    to.setWeiboShareContent(weiboShareContent);
                    to.setOrderId(orderId);
                    tournamentOnlineService.insert(to);
                    result = "add successful";
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        List<TournamentOnline> toList = tournamentOnlineService.getTournamentOnlineList();
        ComparatorTournamentOnlineByStartTime cl = new ComparatorTournamentOnlineByStartTime();
        Collections.sort(toList, cl);
        List<TournamentOnlineView> resultList = new ArrayList<TournamentOnlineView>();
        for (TournamentOnline tournamentOnline : toList) {
            TournamentOnlineView tv = new TournamentOnlineView();
            tv.setId(tournamentOnline.getId());
            tv.setTournamentId(tournamentOnline.getTournamentId());
            long realEndTime = tournamentOnline.getEndTime();
            tv.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
            long realStartTime = tournamentOnline.getStartTime();
            tv.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
            resultList.add(tv);
        }
        model.addAttribute("tournamentOnlineList", resultList);
        model.addAttribute("message", result);
        return "admin/tournament";
    }

}
