package com.ea.eamobile.nfsmw.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ea.eamobile.nfsmw.model.CarChangeTime;
import com.ea.eamobile.nfsmw.model.CarExt;
import com.ea.eamobile.nfsmw.service.CarChangeTimeService;
import com.ea.eamobile.nfsmw.service.CarExtService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.view.CarExtView;

@Controller
@RequestMapping("/nfsmw/admin")
public class CarExtAction {

    protected static final String COOKIE_NAME = "ticket";

    @Autowired
    private CarExtService carExtService;
    @Autowired
    private CarChangeTimeService carChangeTimeService;

    @RequestMapping("carhome")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        List<CarExt> carExtList = carExtService.getCarExtList();
        List<CarExtView> carExtViews = new ArrayList<CarExtView>();
        for (CarExt carExt : carExtList) {
            CarExtView carExtView = new CarExtView();
            carExtView.setCarId(carExt.getCarId());
            carExtView.setEndTime(sdf.format(new Date(carExt.getEndTime() * 1000l)));
            carExtView.setStartTime(sdf.format(new Date(carExt.getStartTime() * 1000l)));
            carExtView.setPrice(carExt.getPrice());
            carExtView.setPriceType(carExt.getPriceType());
            carExtView.setVisible(carExt.getVisible());
            carExtViews.add(carExtView);
        }
        model.addAttribute("carExtList", carExtViews);
        return "admin/car";
    }

    @RequestMapping("updateCarExt")
    public String updateCarExt(Model model, HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "carId", required = false) String carId) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        CarExt carExt = carExtService.getCarExt(carId);
        CarExtView carExtView = new CarExtView();
        carExtView.setCarId(carId);
        carExtView.setEndTime(sdf.format(new Date(carExt.getEndTime() * 1000l)));
        carExtView.setStartTime(sdf.format(new Date(carExt.getStartTime() * 1000l)));
        carExtView.setPrice(carExt.getPrice());
        carExtView.setPriceType(carExt.getPriceType());
        carExtView.setVisible(carExt.getVisible());
        model.addAttribute("carExt", carExtView);
        // model.addAttribute("carExtList", carExtList);
        return "admin/carinfo";
    }

    @RequestMapping(value = "doUpdateCarExt")
    public String updateTournament(Model model, HttpServletRequest request,
            @RequestParam(value = "carId", required = false) String carId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "priceType", required = false) Integer priceType,
            @RequestParam(value = "visible", required = false) Integer visible) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        CarExt carExt = carExtService.getCarExt(carId);
        if (carExt == null) {
            model.addAttribute("erro", "carExt is not exist");
            return "admin/carinfo";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date endDate;
        Date startDate;
        try {
            startDate = sdf.parse(startTime);

            endDate = sdf.parse(endTime);
            carExt.setEndTime((int) (endDate.getTime() / 1000));
            carExt.setStartTime((int) (startDate.getTime() / 1000));
            carExt.setPrice(price);
            carExt.setPriceType(priceType);
            carExt.setVisible(visible);
            carExtService.update(carExt);
            CarChangeTime carChangeTime = new CarChangeTime();
            carChangeTime.setCarId(carId);
            carChangeTime.setTime((int) (System.currentTimeMillis() / 1000));
            carChangeTimeService.insert(carChangeTime);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CarExtView carExtView = new CarExtView();
        carExtView.setCarId(carId);
        carExtView.setEndTime(sdf.format(new Date(carExt.getEndTime() * 1000l)));
        carExtView.setStartTime(sdf.format(new Date(carExt.getStartTime() * 1000l)));
        carExtView.setPrice(carExt.getPrice());
        carExtView.setPriceType(carExt.getPriceType());
        carExtView.setVisible(carExt.getVisible());
        model.addAttribute("message", "success");
        model.addAttribute("carExt", carExtView);

        return "admin/carinfo";

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

}
