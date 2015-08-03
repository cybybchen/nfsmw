package com.ea.eamobile.nfsmw.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ea.eamobile.nfsmw.model.OperateActivity;
import com.ea.eamobile.nfsmw.service.OperateActivityService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.view.OperateActivityView;

@Controller
@RequestMapping("/nfsmw/admin")
public class OperateActivityAction {

    protected static final String COOKIE_NAME = "ticket";

    @Autowired
    OperateActivityService operateActivityService;

    @RequestMapping(value = "operateactivity")
    public String tournamentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        setOperateActivityList(model);
        return "admin/operateActivity";

    }

    @RequestMapping(value = "updateOperateActivity")
    public String updateOperateActivity(Model model, HttpServletRequest request,
            @RequestParam(value = "operateActivityId", required = false) Integer operateActivityId) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        OperateActivity operateActivityTemp = operateActivityService.getOperateActivity(operateActivityId);

        if (operateActivityTemp == null) {
            model.addAttribute("message", "operateActivity is not exist");
            return "admin/operateActivity";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        OperateActivityView operateActivityView = new OperateActivityView();
        operateActivityView.setId(operateActivityTemp.getId());
        long realEndTime = operateActivityTemp.getEndTime();
        operateActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = operateActivityTemp.getStartTime();
        operateActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        operateActivityView.setCareerGoldTimes(operateActivityTemp.getCareerGoldTimes());
        operateActivityView.setCareerMoneyTimes(operateActivityTemp.getCareerMoneyTimes());
        operateActivityView.setCareerRpTimes(operateActivityTemp.getCareerRpTimes());
        operateActivityView.setTournamentGoldTimes(operateActivityTemp.getTournamentGoldTimes());
        operateActivityView.setTournamentMoneyTimes(operateActivityTemp.getTournamentMoneyTimes());
        operateActivityView.setTournamentRpTimes(operateActivityTemp.getTournamentRpTimes());
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        operateActivityView.setStatus(status);

        model.addAttribute("operateActivity", operateActivityView);

        return "admin/operateActivityUpdate";

    }

    @RequestMapping(value = "doUpdateOperateActivity")
    public String doUpdateOperateActivity(Model model, HttpServletRequest request,
            @RequestParam(value = "operateActivityId", required = false) Integer id,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "careerRpTimes", required = false) float careerRpTimes,
            @RequestParam(value = "tournamentRpTimes", required = false) float tournamentRpTimes,
            @RequestParam(value = "careerGoldTimes", required = false) float careerGoldTimes,
            @RequestParam(value = "tournamentGoldTimes", required = false) float tournamentGoldTimes,
            @RequestParam(value = "careerMoneyTimes", required = false) float careerMoneyTimes,
            @RequestParam(value = "tournamentMoneyTimes", required = false) float tournamentMoneyTimes) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        OperateActivity operateActivity = operateActivityService.getOperateActivity(id);

        if (operateActivity == null) {
            model.addAttribute("message", "operateActivity is not exist");
            return "admin/operateActivityUpdate";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
            operateActivity.setEndTime((int) ((endDate.getTime()) / 1000));
            operateActivity.setStartTime((int) ((startDate.getTime()) / 1000));
            operateActivity.setCareerGoldTimes(careerGoldTimes);
            operateActivity.setCareerMoneyTimes(careerMoneyTimes);
            operateActivity.setCareerRpTimes(careerRpTimes);
            operateActivity.setTournamentGoldTimes(tournamentGoldTimes);
            operateActivity.setTournamentMoneyTimes(tournamentMoneyTimes);
            operateActivity.setTournamentRpTimes(tournamentRpTimes);
            operateActivityService.update(operateActivity);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OperateActivityView operateActivityView = new OperateActivityView();
        operateActivityView.setId(operateActivity.getId());
        long realEndTime = operateActivity.getEndTime();
        operateActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = operateActivity.getStartTime();
        operateActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        operateActivityView.setCareerGoldTimes(operateActivity.getCareerGoldTimes());
        operateActivityView.setCareerMoneyTimes(operateActivity.getCareerMoneyTimes());
        operateActivityView.setCareerRpTimes(operateActivity.getCareerRpTimes());
        operateActivityView.setTournamentGoldTimes(operateActivity.getTournamentGoldTimes());
        operateActivityView.setTournamentMoneyTimes(operateActivity.getTournamentMoneyTimes());
        operateActivityView.setTournamentRpTimes(operateActivity.getTournamentRpTimes());

        model.addAttribute("operateActivity", operateActivityView);
        model.addAttribute("message", "success");
        return "admin/operateActivityUpdate";

    }

    @RequestMapping(value = "deleteOperateActivity")
    public String deleteTournamentOnlineId(Model model, HttpServletRequest request,
            @RequestParam(value = "operateActivityId", required = false) Integer operateActivityId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        OperateActivity operateActivity = operateActivityService.getOperateActivity(operateActivityId);
        if (operateActivity == null) {
            model.addAttribute("message", "operateActivity is not exist");
            return "admin/operateActivity";
        }
        operateActivityService.deleteById(operateActivityId);
        model.addAttribute("message", "success");
        setOperateActivityList(model);
        return "admin/operateActivity";

    }

    @RequestMapping(value = "stopOperateActivity")
    public String stopTournamentOnlineId(Model model, HttpServletRequest request,
            @RequestParam(value = "operateActivityId", required = false) Integer operateActivityId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        OperateActivity operateActivity = operateActivityService.getOperateActivity(operateActivityId);
        if (operateActivity == null) {
            model.addAttribute("message", "operateActivity is not exist");
            return "admin/operateActivity";
        }
        operateActivity.setEndTime((int) (System.currentTimeMillis() / 1000));
        operateActivityService.update(operateActivity);
        model.addAttribute("message", "success");
        setOperateActivityList(model);
        return "admin/operateActivity";

    }

    private void setOperateActivityList(Model model) {
        List<OperateActivity> operateActivities = operateActivityService.getOperateActivityList();
        List<OperateActivityView> resultList = new ArrayList<OperateActivityView>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        for (OperateActivity operateActivityTemp : operateActivities) {
            OperateActivityView operateActivityView = new OperateActivityView();
            operateActivityView.setId(operateActivityTemp.getId());
            long realEndTime = operateActivityTemp.getEndTime();
            operateActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
            long realStartTime = operateActivityTemp.getStartTime();
            operateActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
            operateActivityView.setCareerGoldTimes(operateActivityTemp.getCareerGoldTimes());
            operateActivityView.setCareerMoneyTimes(operateActivityTemp.getCareerMoneyTimes());
            operateActivityView.setCareerRpTimes(operateActivityTemp.getCareerRpTimes());
            operateActivityView.setTournamentGoldTimes(operateActivityTemp.getTournamentGoldTimes());
            operateActivityView.setTournamentMoneyTimes(operateActivityTemp.getTournamentMoneyTimes());
            operateActivityView.setTournamentRpTimes(operateActivityTemp.getTournamentRpTimes());
            String status = "UnActivity";
            long currentTime = System.currentTimeMillis();
            if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
                status = "Activity";
            }
            operateActivityView.setStatus(status);
            resultList.add(operateActivityView);
        }
        model.addAttribute("operateActivityList", resultList);
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

    @RequestMapping(value = "addNewOperateActivity")
    public String addNewOperateActivity(@RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "careerRpTimes", required = false) float careerRpTimes,
            @RequestParam(value = "tournamentRpTimes", required = false) float tournamentRpTimes,
            @RequestParam(value = "careerGoldTimes", required = false) float careerGoldTimes,
            @RequestParam(value = "tournamentGoldTimes", required = false) float tournamentGoldTimes,
            @RequestParam(value = "careerMoneyTimes", required = false) float careerMoneyTimes,
            @RequestParam(value = "tournamentMoneyTimes", required = false) float tournamentMoneyTimes, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String result = "";

        OperateActivity operateActivity = new OperateActivity();

        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);
            if (startDate.getTime() > endDate.getTime()) {
                model.addAttribute("message", "time conflict");
                return "admin/operateActivity";
            }
            int maxEndTime = operateActivityService.getMaxEndTime();
            if (((int) ((startDate.getTime()) / 1000)) < maxEndTime) {
                model.addAttribute("message", "time conflict");
                return "admin/operateActivity";
            }
            operateActivity.setEndTime((int) ((endDate.getTime()) / 1000));
            operateActivity.setStartTime((int) ((startDate.getTime()) / 1000));
            operateActivity.setCareerGoldTimes(careerGoldTimes);
            operateActivity.setCareerMoneyTimes(careerMoneyTimes);
            operateActivity.setCareerRpTimes(careerRpTimes);
            operateActivity.setTournamentGoldTimes(tournamentGoldTimes);
            operateActivity.setTournamentMoneyTimes(tournamentMoneyTimes);
            operateActivity.setTournamentRpTimes(tournamentRpTimes);
            operateActivityService.insert(operateActivity);
            result = "add successful";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setOperateActivityList(model);
        model.addAttribute("message", result);
        return "admin/operateActivity";
    }

}
