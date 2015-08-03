package com.ea.eamobile.nfsmw.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

/**
 * 所有跳转url的控制器
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/url")
public class JumpUrlAction {

    @RequestMapping("")
    public String index(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id == null) {
            id = 0;
        }
        String url = "";
        switch (id) {
        case Const.RENT_JAGUAR_IPAD:
            // url = "http://210.51.43.82/jaguar2/unlock-car-ipad.php";
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/rent?display=" + Const.DEVICE_IS_IPAD;
            break;
        case Const.RENT_JAGUAR_IPHONE:
            // url = "http://210.51.43.82/jaguar2/unlock-car.php";
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/rent?display=" + Const.DEVICE_IS_IPHONE;
            break;
        case Const.RENT_JAGUAR_IPHONE5:
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/rent?display=" + Const.DEVICE_IS_IPHONE5;
            break;
        case Const.GET_JAGUAR_IPAD:
            // url = "http://210.51.43.82/jaguar2/complete-info-ipad.php";
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/own?display=" + Const.DEVICE_IS_IPAD;
            break;
        case Const.GET_JAGUAR_IPHONE:
            // url = "http://210.51.43.82/jaguar2/complete-info.php";
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/own?display=" + Const.DEVICE_IS_IPHONE;
            break;
        case Const.GET_JAGUAR_IPHONE5:
            url = ConfigUtil.NFSMW_URL + "/nfsmw/jaguar/own?display=" + Const.DEVICE_IS_IPHONE5;
            break;
        case Const.JAGUAR_MORE_IPAD:
            url = "http://tracking.ea.com.cn/click.php?id=652";
            break;
        case Const.JAGUAR_MORE_IPHONE:
            url = "http://tracking.ea.com.cn/click.php?id=651";
            break;
        default:
            url = ConfigUtil.NFSMW_URL + "/nfsmw/news";
            break;
        }
        return "redirect:" + url;
    }

}
