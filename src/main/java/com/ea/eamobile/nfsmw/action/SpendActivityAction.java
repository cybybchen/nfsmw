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
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.model.ComparatorSpendActivity;
import com.ea.eamobile.nfsmw.model.ComparatorSpendReward;
import com.ea.eamobile.nfsmw.model.SpendActivity;
import com.ea.eamobile.nfsmw.model.SpendActivityReward;
import com.ea.eamobile.nfsmw.model.SpendReward;
import com.ea.eamobile.nfsmw.service.SpendActivityRewardService;
import com.ea.eamobile.nfsmw.service.SpendActivityService;
import com.ea.eamobile.nfsmw.service.SpendRewardService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.view.SpendActivityView;

@Controller
@RequestMapping("/nfsmw/admin")
public class SpendActivityAction {

    @Autowired
    private SpendRewardService spendRewardService;

    @Autowired
    private SpendActivityService spendActivityService;
    @Autowired
    private SpendActivityRewardService spendActivityRewardService;

    protected static final String COOKIE_NAME = "ticket";

    @RequestMapping("operateSpendActivity")
    public String operateSpendActivity(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        return "admin/spendActivity";
    }

    @RequestMapping("spendactivity/spendRewardHome")
    public String spendRewardHome(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        List<SpendReward> spendRewards = spendRewardService.getSpendRewardList();
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("message", "");
        return "spendactivity/spendRewardHome";
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

    @RequestMapping(value = "spendactivity/spendActivityHome")
    public String tournamentHome(Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        setSpendActivityList(model);
        return "spendactivity/spendActivityHome";

    }

    private void setSpendActivityList(Model model) {
        List<SpendActivity> spendActivities = spendActivityService.getSpendActivityList();
        ComparatorSpendActivity ct = new ComparatorSpendActivity();
        Collections.sort(spendActivities, ct);
        List<SpendActivityView> resultList = new ArrayList<SpendActivityView>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        for (SpendActivity spendActivity : spendActivities) {
            SpendActivityView spendActivityView = new SpendActivityView();
            spendActivityView.setId(spendActivity.getId());
            spendActivityView.setDisplayName(spendActivity.getDisplayName());
            long realEndTime = spendActivity.getEndTime();
            spendActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
            long realStartTime = spendActivity.getStartTime();
            spendActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
            String status = "UnActivity";
            long currentTime = System.currentTimeMillis();
            if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
                status = "Activity";
            }
            if ((currentTime / 1000) > realEndTime) {
                status = "Ended";
            }
            spendActivityView.setStatus(status);
            resultList.add(spendActivityView);
        }

        model.addAttribute("spendActivityList", resultList);
    }

    @RequestMapping(value = "addNewSpendActivity")
    public String addNewOperateActivity(@RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "displayName", required = false) String displayName, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String result = "";

        SpendActivity spendActivity = new SpendActivity();

        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);

            spendActivity.setEndTime((int) ((endDate.getTime()) / 1000));
            spendActivity.setStartTime((int) ((startDate.getTime()) / 1000));
            spendActivity.setDisplayName(displayName);
            boolean canAddSpendActivity = canAddSpendActicity(spendActivity);
            if (!canAddSpendActivity || startDate.getTime() > endDate.getTime()) {
                setSpendActivityList(model);
                model.addAttribute("message", "time conflict");
                return "spendactivity/spendActivityHome";
            }
            spendActivityService.insert(spendActivity);
            result = "add successful";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setSpendActivityList(model);
        model.addAttribute("message", result);
        return "spendactivity/spendActivityHome";
    }

    @RequestMapping(value = "showSpendActivity")
    public String showSpendActivity(Model model, HttpServletRequest request,
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivityView spendActivityView = new SpendActivityView();
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            model.addAttribute("message", "spendActivity is not exist");
            setSpendActivityList(model);
            return "spendactivity/spendActivityHome";
        }
        spendActivityView.setId(spendActivityId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        spendActivityView.setDisplayName(spendActivity.getDisplayName());
        long realEndTime = spendActivity.getEndTime();
        spendActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = spendActivity.getStartTime();
        spendActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        if ((currentTime / 1000) > realEndTime) {
            status = "Ended";
        }
        spendActivityView.setStatus(status);

        List<SpendActivityReward> spendActivityRewards = spendActivityRewardService
                .getSpendActivityRewardList(spendActivityId);
        List<SpendReward> spendRewards = new ArrayList<SpendReward>();
        if (spendActivityRewards != null) {
            for (SpendActivityReward spendActivityReward : spendActivityRewards) {
                SpendReward reward = spendRewardService.getSpendReward(spendActivityReward.getSpendRewardId());
                if (reward != null) {
                    spendRewards.add(reward);
                }
            }
        }
        ComparatorSpendReward ct = new ComparatorSpendReward();
        Collections.sort(spendRewards, ct);
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("spendActivity", spendActivityView);

        return "spendactivity/showSpendActivity";

    }

    @RequestMapping(value = "updateSpendActivity")
    public String updateSpendActivity(Model model, HttpServletRequest request,
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivityView spendActivityView = new SpendActivityView();
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            model.addAttribute("message", "spendActivity is not exist");
            setSpendActivityList(model);
            return "spendactivity/spendActivityHome";
        }
        spendActivityView.setId(spendActivityId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        spendActivityView.setDisplayName(spendActivity.getDisplayName());
        long realEndTime = spendActivity.getEndTime();
        spendActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = spendActivity.getStartTime();
        spendActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        if ((currentTime / 1000) > realEndTime) {
            status = "Ended";
        }
        spendActivityView.setStatus(status);

        List<SpendActivityReward> spendActivityRewards = spendActivityRewardService
                .getSpendActivityRewardList(spendActivityId);
        List<SpendReward> spendRewards = new ArrayList<SpendReward>();
        if (spendActivityRewards != null) {
            for (SpendActivityReward spendActivityReward : spendActivityRewards) {
                SpendReward reward = spendRewardService.getSpendReward(spendActivityReward.getSpendRewardId());
                if (reward != null) {
                    spendRewards.add(reward);
                }
            }
        }
        ComparatorSpendReward ct = new ComparatorSpendReward();
        Collections.sort(spendRewards, ct);
        List<SpendReward> spendRewardList = spendRewardService.getSpendRewardList();
        model.addAttribute("allSpendRewardList", spendRewardList);
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("spendActivity", spendActivityView);

        return "spendactivity/updateSpendActivity";

    }

    private boolean canAddSpendActicity(SpendActivity spendActivity) {
        boolean result = true;
        List<SpendActivity> spendActivityList = new ArrayList<SpendActivity>();
        List<SpendActivity> spendActivities = spendActivityService.getSpendActivityList();
        spendActivityList.addAll(spendActivities);
        spendActivityList.add(spendActivity);
        ComparatorSpendActivity ct = new ComparatorSpendActivity();
        Collections.sort(spendActivityList, ct);
        int lastEndTime = 0;
        for (SpendActivity spendActivitytemp : spendActivityList) {
            if (spendActivitytemp.getStartTime() < lastEndTime) {
                result = false;
                break;
            }
            lastEndTime = spendActivitytemp.getEndTime();
        }
        return result;
    }

    @RequestMapping(value = "spendReward/addNewSpendReward")
    public String addNewSpendReward(@RequestParam(value = "goldAmount", required = false) Integer goldAmount,
            @RequestParam(value = "addGold", required = false) Integer addGold,
            @RequestParam(value = "addMoney", required = false) Integer addMoney,
            @RequestParam(value = "addEnergy", required = false) Integer addEnergy,
            @RequestParam(value = "displayName", required = false) String displayName, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendReward spendReward = new SpendReward();
        spendReward.setAddEnergy(addEnergy);
        spendReward.setAddGold(addGold);
        spendReward.setAddMoney(addMoney);
        spendReward.setDisplayName(displayName);
        spendReward.setGoldAmount(goldAmount);
        spendRewardService.insert(spendReward);

        List<SpendReward> spendRewards = spendRewardService.getSpendRewardList();
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("message", "success");
        return "spendactivity/spendRewardHome";
    }

    @RequestMapping(value = "spendReward/updateSpendReward")
    public String updateSpendReward(Model model, HttpServletRequest request,
            @RequestParam(value = "spendRewardId", required = false) Integer spendRewardId) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendReward spendReward = spendRewardService.getSpendReward(spendRewardId);

        if (spendReward == null) {
            model.addAttribute("erro", "spendReward is not exist");
            return "spendactivity/spendRewardUpdate";
        }
        model.addAttribute("spendReward", spendReward);
        return "spendactivity/spendRewardUpdate";

    }

    @RequestMapping(value = "doUpdateSpendReward")
    public String doUpdateSpendReward(Model model, HttpServletRequest request,
            @RequestParam(value = "goldAmount", required = false) Integer goldAmount,
            @RequestParam(value = "addGold", required = false) Integer addGold,
            @RequestParam(value = "addMoney", required = false) Integer addMoney,
            @RequestParam(value = "addEnergy", required = false) Integer addEnergy,
            @RequestParam(value = "displayName", required = false) String displayName,
            @RequestParam(value = "spendRewardId", required = false) Integer spendRewardId) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        SpendReward spendReward = spendRewardService.getSpendReward(spendRewardId);

        if (spendReward == null) {
            model.addAttribute("erro", "spendReward is not exist");
            return "spendactivity/spendRewardUpdate";
        }
        spendReward.setAddEnergy(addEnergy);
        spendReward.setAddGold(addGold);
        spendReward.setAddMoney(addMoney);
        spendReward.setDisplayName(displayName);
        spendReward.setGoldAmount(goldAmount);
        spendRewardService.update(spendReward);
        model.addAttribute("message", "success");
        model.addAttribute("spendReward", spendReward);
        return "spendactivity/spendRewardUpdate";

    }

    @RequestMapping(value = "doUpdateSpendActivity")
    public String doUpdateSpendActivity(
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "displayName", required = false) String displayName, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            model.addAttribute("message", "spendActivity is not exist");
            setSpendActivityList(model);
            return "spendactivity/spendActivityHome";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String result = "";

        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);

            spendActivity.setEndTime((int) ((endDate.getTime()) / 1000));
            spendActivity.setStartTime((int) ((startDate.getTime()) / 1000));
            spendActivity.setDisplayName(displayName);
            boolean canAddSpendActivity = canAddSpendActicity(spendActivity);
            if (!canAddSpendActivity) {
                model.addAttribute("message", "time conflict");
                return "spendactivity/spendActivityHome";
            }
            spendActivityService.update(spendActivity);
            result = "update successful";
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SpendActivityView spendActivityView = new SpendActivityView();
        spendActivityView.setId(spendActivityId);
        spendActivityView.setDisplayName(displayName);
        spendActivityView.setEndTime(endTime);
        spendActivityView.setStartTime(startTime);
        long realEndTime = spendActivity.getEndTime();
        long realStartTime = spendActivity.getStartTime();
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        if ((currentTime / 1000) > realEndTime) {
            status = "Ended";
        }
        spendActivityView.setStatus(status);
        List<SpendActivityReward> spendActivityRewards = spendActivityRewardService
                .getSpendActivityRewardList(spendActivityId);
        List<SpendReward> spendRewards = new ArrayList<SpendReward>();
        if (spendActivityRewards != null) {
            for (SpendActivityReward spendActivityReward : spendActivityRewards) {
                SpendReward reward = spendRewardService.getSpendReward(spendActivityReward.getSpendRewardId());
                if (reward != null) {
                    spendRewards.add(reward);
                }
            }
        }
        ComparatorSpendReward ct = new ComparatorSpendReward();
        Collections.sort(spendRewards, ct);
        List<SpendReward> spendRewardList = spendRewardService.getSpendRewardList();
        model.addAttribute("allSpendRewardList", spendRewardList);
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("spendActivity", spendActivityView);
        model.addAttribute("message", result);
        return "spendactivity/updateSpendActivity";
    }

    @RequestMapping(value = "addNewSpendRewardByRewardAndActivity")
    public String addNewSpendRewardByRewardAndActivity(
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId,
            @RequestParam(value = "spendRewardId", required = false) Integer spendRewardId, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            model.addAttribute("message", "spendActivity is not exist");
            setSpendActivityList(model);
            return "spendactivity/spendActivityHome";
        }
        SpendActivityReward spendActivityReward = new SpendActivityReward();
        spendActivityReward.setSpendActivityId(spendActivityId);
        spendActivityReward.setSpendRewardId(spendRewardId);
        spendActivityRewardService.insert(spendActivityReward);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String result = "";

        SpendActivityView spendActivityView = new SpendActivityView();
        spendActivityView.setId(spendActivityId);
        spendActivityView.setDisplayName(spendActivity.getDisplayName());
        long realEndTime = spendActivity.getEndTime();
        spendActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = spendActivity.getStartTime();
        spendActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        if ((currentTime / 1000) > realEndTime) {
            status = "Ended";
        }
        spendActivityView.setStatus(status);
        List<SpendActivityReward> spendActivityRewards = spendActivityRewardService
                .getSpendActivityRewardList(spendActivityId);
        List<SpendReward> spendRewards = new ArrayList<SpendReward>();
        if (spendActivityRewards != null) {
            for (SpendActivityReward spendActivityRewardtemp : spendActivityRewards) {
                SpendReward reward = spendRewardService.getSpendReward(spendActivityRewardtemp.getSpendRewardId());
                if (reward != null) {
                    spendRewards.add(reward);
                }
            }
        }
        ComparatorSpendReward ct = new ComparatorSpendReward();
        Collections.sort(spendRewards, ct);
        List<SpendReward> spendRewardList = spendRewardService.getSpendRewardList();
        model.addAttribute("allSpendRewardList", spendRewardList);
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("spendActivity", spendActivityView);
        model.addAttribute("message", result);
        return "spendactivity/updateSpendActivity";
    }

    @RequestMapping(value = "deleteSpendRewardByRewardAndActivity")
    public String deleteSpendRewardByRewardAndActivity(
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId,
            @RequestParam(value = "spendRewardId", required = false) Integer spendRewardId, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            model.addAttribute("message", "spendActivity is not exist");
            setSpendActivityList(model);
            return "spendactivity/spendActivityHome";
        }

        spendActivityRewardService.deleteById(spendActivityId, spendRewardId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String result = "";

        SpendActivityView spendActivityView = new SpendActivityView();
        spendActivityView.setId(spendActivityId);
        spendActivityView.setDisplayName(spendActivity.getDisplayName());
        long realEndTime = spendActivity.getEndTime();
        spendActivityView.setEndTime(sdf.format(new Date(realEndTime * 1000l)));
        long realStartTime = spendActivity.getStartTime();
        spendActivityView.setStartTime(sdf.format(new Date(realStartTime * 1000l)));
        String status = "UnActivity";
        long currentTime = System.currentTimeMillis();
        if ((currentTime / 1000) > realStartTime && (currentTime / 1000) < realEndTime) {
            status = "Activity";
        }
        if ((currentTime / 1000) > realEndTime) {
            status = "Ended";
        }
        spendActivityView.setStatus(status);
        List<SpendActivityReward> spendActivityRewards = spendActivityRewardService
                .getSpendActivityRewardList(spendActivityId);
        List<SpendReward> spendRewards = new ArrayList<SpendReward>();
        if (spendActivityRewards != null) {
            for (SpendActivityReward spendActivityRewardtemp : spendActivityRewards) {
                SpendReward reward = spendRewardService.getSpendReward(spendActivityRewardtemp.getSpendRewardId());
                if (reward != null) {
                    spendRewards.add(reward);
                }
            }
        }
        ComparatorSpendReward ct = new ComparatorSpendReward();
        Collections.sort(spendRewards, ct);
        List<SpendReward> spendRewardList = spendRewardService.getSpendRewardList();
        model.addAttribute("allSpendRewardList", spendRewardList);
        model.addAttribute("spendRewardList", spendRewards);
        model.addAttribute("spendActivity", spendActivityView);
        model.addAttribute("message", result);
        return "spendactivity/updateSpendActivity";
    }

    @RequestMapping(value = "stopSpendActivity")
    public String stopSpendActivity(@RequestParam(value = "spendActivityId", required = false) Integer spendActivityId,
            Model model, HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            setSpendActivityList(model);
            model.addAttribute("message", "spendActivty is not exist");
            return "spendactivity/spendActivityHome";
        }
        spendActivity.setEndTime((int) (System.currentTimeMillis() / 1000));
        spendActivityService.update(spendActivity);
        setSpendActivityList(model);
        model.addAttribute("message", "stop success");
        return "spendactivity/spendActivityHome";
    }

    @RequestMapping(value = "deleteSpendActivity")
    public String deleteSpendActivity(
            @RequestParam(value = "spendActivityId", required = false) Integer spendActivityId, Model model,
            HttpServletRequest request) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SpendActivity spendActivity = spendActivityService.getSpendActivity(spendActivityId);
        if (spendActivity == null) {
            setSpendActivityList(model);
            model.addAttribute("message", "spendActivty is not exist");
            return "spendactivity/spendActivityHome";
        }
        spendActivityService.deleteById(spendActivityId);
        spendActivityRewardService.deleteBySpendActivityId(spendActivityId);
        setSpendActivityList(model);
        model.addAttribute("message", "delete success");
        return "spendactivity/spendActivityHome";
    }

}
