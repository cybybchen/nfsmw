package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.model.Merchandise;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * 物品购买业务逻辑
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class PayService {

    @Autowired
    UserService userService;
    @Autowired
    CtaContentService ctaContentService;

    private static final Logger log = LoggerFactory.getLogger(PayService.class);

    public static final int GOLD = 1;

    public static final int CURRENCY = 2;

    /**
     * 购买商品
     * 
     * @param item
     * @param userId
     * @return
     */
    public ResultInfo buy(Merchandise item, long userId) {
        boolean success = false;
        String message = "";
        User user = userService.getUser(userId);
        int userMoney = 0;
        if (item.getPriceType() == GOLD) {
        	
            userMoney = user.getGold();
            log.info(">>>>>>>>>>>>>>>>use gold, the amount is: " + userMoney + ", the item price is: " + item.getPrice());
            user.setGold(userMoney - item.getPrice());
        } else if (item.getPriceType() == CURRENCY) {
            userMoney = user.getMoney();
            log.info(">>>>>>>>>>>>>>>>use gold, the amount is: " + userMoney + ", the item price is: " + item.getPrice());
            user.setMoney(userMoney - item.getPrice());
        }
        
        
        if (userMoney >= item.getPrice()) {
            success = true;
            message = ctaContentService.getCtaContent(CtaContentConst.BUY_SUCCESS).getContent();
            // inc money
            userService.updateUser(user);
            // log
            log.debug("buy merchandise: item={},userid={}", item, userId);
        } else {
            message = ctaContentService.getCtaContent(CtaContentConst.NOT_ENOUGH_MONEY).getContent();
        }
        ResultInfo result = new ResultInfo(success, message, user);
        return result;
    }
    
    public ResultInfo buy(Merchandise item, User user) {
    	long userId = user.getId();
        boolean success = false;
        String message = "";
        int userMoney = 0;
        if (item.getPriceType() == GOLD) {
        	
            userMoney = user.getGold();
            log.info(">>>>>>>>>>>>>>>>use gold, the amount is: " + userMoney + ", the item price is: " + item.getPrice());
            user.setGold(userMoney - item.getPrice());
        } else if (item.getPriceType() == CURRENCY) {
            userMoney = user.getMoney();
            log.info(">>>>>>>>>>>>>>>>use gold, the amount is: " + userMoney + ", the item price is: " + item.getPrice());
            user.setMoney(userMoney - item.getPrice());
        }
        
        
        if (userMoney >= item.getPrice()) {
            success = true;
            message = ctaContentService.getCtaContent(CtaContentConst.BUY_SUCCESS).getContent();
            // inc money
            userService.updateUser(user);
            // log
            log.debug("buy merchandise: item={},userid={}", item, userId);
        } else {
            message = ctaContentService.getCtaContent(CtaContentConst.NOT_ENOUGH_MONEY).getContent();
        }
        ResultInfo result = new ResultInfo(success, message, user);
        return result;
    }

    public ResultInfo buy(List<Merchandise> items, User user) {
        boolean success = true;
        String message = "";
        if (items != null && items.size() > 0) {
            int userGold = user.getGold();
            int userCurrency = user.getMoney();
            for (Merchandise item : items) {
                if (item.getPriceType() == GOLD) {
                    userGold -= item.getPrice();
                } else if (item.getPriceType() == CURRENCY) {
                    userCurrency -= item.getPrice();
                }
            }
            if (userGold >= 0 && userCurrency >= 0) {
                message = ctaContentService.getCtaContent(CtaContentConst.BUY_SUCCESS).getContent();
                // inc money
                user.setGold(userGold);
                user.setMoney(userCurrency);
                userService.updateUser(user);
                // log
                log.debug("buy merchandise: items={},userid={}", items, user.getId());
            } else {
                success = false;
                message = ctaContentService.getCtaContent(CtaContentConst.NOT_ENOUGH_MONEY).getContent();
            }
        }
        ResultInfo result = new ResultInfo(success, message, user);
        return result;
    }
}
