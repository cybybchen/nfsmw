package com.ea.eamobile.nfsmw.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Message Of Day Page
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Controller
@RequestMapping("/nfsmw/help")
public class HelpAction {

    private static final Logger log = LoggerFactory.getLogger(HelpAction.class);

    @RequestMapping("")
    public String index(@RequestParam(value = "userId", required = false) Integer userId, Model model) {
        log.warn("help_page : userId = {}", userId);
        return "help";
    }

}
