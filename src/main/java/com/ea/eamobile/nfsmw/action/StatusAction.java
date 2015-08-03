package com.ea.eamobile.nfsmw.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.HTTPStringResolver;
import com.ea.eamobile.nfsmw.utils.HttpUtil;

@Controller
public class StatusAction {

    @Autowired
    private CarService carService;

    protected static final HttpUtil<String> http = new com.ea.eamobile.nfsmw.utils.HttpUtil<String>(
            new HTTPStringResolver());

    @RequestMapping(value = "nfsmw/status")
    public String show() {

        // check db
        if (carService == null) {
            System.out.println("nfsmw-status:service null");
            return "";
        }
        if (carService.getCarList().size() == 0) {
            System.out.println("nfsmw-status:service db null");
            return "";
        }
        // check cmd
        String data = http.get(ConfigUtil.COMMAND_URL);
        if (data == null) {
            System.out.println("nfsmw-status:service gamedata null");
            return "";
        }

        return "status";
    }
}
