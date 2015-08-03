package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.UserPay;
import com.ea.eamobile.nfsmw.model.mapper.UserPayMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:11 CST 2013
 * @since 1.0
 */
@Service
public class UserPayService {

    @Autowired
    private UserPayMapper userPayMapper;
    @Autowired
    private MemcachedClient cache;

    public UserPay getUserPay(long userId) {
        UserPay pay = (UserPay) cache.get(CacheKey.USER_PAY + userId);
        if (pay == null) {
            pay = userPayMapper.getUserPay(userId);
            cache.set(CacheKey.USER_PAY + userId, pay, MemcachedClient.HOUR);
        }
        return pay;
    }

    public int insertOrUpdate(UserPay userPay) {
        cache.delete(CacheKey.USER_PAY + userPay.getUserId());
        return userPayMapper.insert(userPay);
    }

    public void deleteById(long userId) {
        userPayMapper.deleteById(userId);
        cache.delete(CacheKey.USER_PAY + userId);
    }

    public UserPay buildUserPay(long userId, int payment, int gold, int money) {
        UserPay pay = new UserPay();
        pay.setUserId(userId);
        pay.setExpenseGold(gold);
        pay.setExpenseMoney(money);
        pay.setPayment(payment);
        return pay;
    }

    public void saveExpense(long userId, int price, int priceType) {
        int gold = 0;
        int money = 0;
        if (priceType == Const.GOLD) {
            gold = price;
        } else {
            money = price;
        }
        UserPay userPay = buildUserPay(userId, 0, gold, money);
        insertOrUpdate(userPay);
    }

}