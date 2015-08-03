package com.ea.eamobile.nfsmw.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserExpenseRec;
import com.ea.eamobile.nfsmw.model.mapper.UserExpenseRecMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 11:39:58 CST 2013
 * @since 1.0
 */
 @Service
public class UserExpenseRecService {

    @Autowired
    private UserExpenseRecMapper userExpenseRecMapper;
    @Autowired
    private ExpenseRewardService exRewardService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserService userService;
    
    /**
     * 保存消费记录 返回是否发了奖用于提示判断
     * @param userId
     * @param gold
     */
    public boolean saveRec(long userId,int expenseGold){
        boolean haveSendReward = false;
        //取消费记录
        UserExpenseRec rec = getUserExpenseRec(userId);
        int totalGold = expenseGold;
        if(rec==null){
            rec = buildRec(userId, expenseGold);
            insert(rec);
        }else{
            totalGold += rec.getGold();
            rec.setGold(totalGold);
            update(rec);
            if(rec.getR6()==1){
                return haveSendReward;
            }
        }
        //根据合计的金币获取奖励列表
        Map<Integer, Integer> rewardMap = exRewardService.getRewardMapByGold(totalGold);
        //如果奖励列表有数据，发放奖励
        if(rewardMap!=null && rewardMap.size()>0){
            User user = userService.getUser(userId);
            //有不一定发 要判断是否发过 表设计导致丑陋的判断
            if(rec.getR1()==0){
                Integer r1 = rewardMap.get(1);
                if(r1!=null){
                    rewardService.doRewards(user, r1);
                    rec.setR1(1);
                    haveSendReward = true;
                }
            }
            if(rec.getR2()==0){
                Integer r2 = rewardMap.get(2);
                if(r2!=null){
                    rewardService.doRewards(user, r2);
                    rec.setR2(1);
                    haveSendReward = true;
                }
            }
            if(rec.getR3()==0){
                Integer r3 = rewardMap.get(3);
                if(r3!=null){
                    rewardService.doRewards(user, r3);
                    rec.setR3(1);
                    haveSendReward = true;
                }
            }
            if(rec.getR4()==0){
                Integer r4 = rewardMap.get(4);
                if(r4!=null){
                    rewardService.doRewards(user, r4);
                    rec.setR4(1);
                    haveSendReward = true;
                }
            }
            if(rec.getR5()==0){
                Integer r5 = rewardMap.get(5);
                if(r5!=null){
                    rewardService.doRewards(user, r5);
                    rec.setR5(1);
                    haveSendReward = true;
                }
            }
            if(rec.getR6()==0){
                Integer r6 = rewardMap.get(6);
                if(r6!=null){
                    rewardService.doRewards(user, r6);
                    rec.setR6(1);
                    haveSendReward = true;
                }
            }
            //更新记录状态
            update(rec);
        }
        return haveSendReward;
    }
    
    public void update(UserExpenseRec userExpenseRec) {
        userExpenseRecMapper.update(userExpenseRec);
    }
    
    private UserExpenseRec buildRec(long userId,int gold){
        UserExpenseRec rec = new UserExpenseRec();
        rec.setUserId(userId);
        rec.setGold(gold);
        return rec;
    }
    public UserExpenseRec getUserExpenseRec(long userId){
        return userExpenseRecMapper.getUserExpenseRec(userId);
    }
    public int insert(UserExpenseRec userExpenseRec){
        return userExpenseRecMapper.insert(userExpenseRec);
    }

}