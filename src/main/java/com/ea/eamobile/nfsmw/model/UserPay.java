package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:10 CST 2013
 * @since 1.0
 */
public class UserPay implements Serializable {

    private static final long serialVersionUID = 1L;

    private long userId;

    /**
     * 充值金额
     */
    private int payment;

    /**
     * 消费金币数
     */
    private int expenseGold;
    
    /**
     * 消费游戏币
     */
    private int expenseMoney;

    public int getExpenseGold() {
        return expenseGold;
    }

    public void setExpenseGold(int expenseGold) {
        this.expenseGold = expenseGold;
    }

    public int getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(int expenseMoney) {
        this.expenseMoney = expenseMoney;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
