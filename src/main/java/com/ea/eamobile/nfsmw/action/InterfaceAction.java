package com.ea.eamobile.nfsmw.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ea.eamobile.nfsmw.service.GarageLeaderboardService;

/**
 * Message Of Day Page
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/iface")
public class InterfaceAction {

    private static final String SECRET = "EAGAMsddfIENd~!s)m354ENFSMW";
    @Autowired
    GarageLeaderboardService glbService;

    @RequestMapping(value="glb",method=RequestMethod.POST)
    public @ResponseBody String index(HttpServletRequest request, HttpServletResponse response,Model model) {
        String t = request.getParameter("t");
        String md5 = request.getParameter("md5");
        String buildMd5 = DigestUtils.md5Hex(t+SECRET);
        if(StringUtils.isBlank(t) || StringUtils.isBlank(md5) || !md5.equals(buildMd5)){
            return "{\"error\",\"no authoriation\"}";
        }
        String str = glbService.getJson();
        try {
            return new String(str.getBytes("utf-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
        } 
        return "{\"error\",\"encoding err\"}";
    }

}
