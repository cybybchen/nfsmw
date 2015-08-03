package com.ea.eamobile.nfsmw.service.gotcha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.GotchaExpense;
import com.ea.eamobile.nfsmw.model.GotchaRecord;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.model.UserFreeFragRecord;
import com.ea.eamobile.nfsmw.model.UserGotcha;
import com.ea.eamobile.nfsmw.model.UserPay;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.service.GotchaRecordService;
import com.ea.eamobile.nfsmw.service.PayService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserCarFragmentService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserFreeFragRecordService;
import com.ea.eamobile.nfsmw.service.UserGotchaService;
import com.ea.eamobile.nfsmw.service.UserPayService;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.NumberUtil;
import com.ea.eamobile.nfsmw.view.GotchaView;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * 业务逻辑类
 * 
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:45 CST 2013
 * @since 1.0
 */
@Service
public class GotchaService {
    @Autowired
    private GotchaCarService gcarService;
    @Autowired
    private GotchaExpenseService expService;
    @Autowired
    private GotchaFormulaService formulaService;
    @Autowired
    private GotchaRatioService ratioService;
    @Autowired
    private UserPayService userPayService;
    @Autowired
    private UserGotchaService userGotchaService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserCarFragmentService fragmentService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private PayService payService;
    @Autowired
    private UserFreeFragRecordService userFreeFragRecordService;
    @Autowired
    private GotchaRecordService gotchaRecordService;
    private static final Logger log = LoggerFactory.getLogger(GotchaService.class);

    /**
     * 抽奖总逻辑
     * 
     * @param user
     * @param expenseId
     * @return
     */
    public GotchaView gotcha(User user, int level, String carId, GotchaRecord gotchaRecord) {
    	log.info(">>>>>>>>>>>gotha");
        GotchaCar gcar = gcarService.getGotchaCar(carId);
        if (gcar == null) {
        	log.info(">>>>>>>>>>>>the gcar is null");
            return null;
        }
        int gid = gcar.getId();
        log.info("The gid is: " + gid + ", the level is： " + level);
        GotchaExpense expense = expService.getGotchaExpense(gid, level);
        if (expense == null) {
        	log.info(">>>>>>>>>>>>the expense is null");
            return null;
        }
        long userId = user.getId();
        // 判断是否有钱gotcha 并扣除

        boolean isGotchaFree = isGotchaFree(userId, expense);
        if (!isGotchaFree) {
        	log.info(">>>>>>>>>>>>>>gotcha is not free!");
            ResultInfo buyInfo = payService.buy(expense, userId);
            if (!buyInfo.isSuccess()) {
            	log.info(">>>>>>>>>>>>!buyInfo.isSuccess()");
                return null;
            }
            user = buyInfo.getUser();
        } else {
        	log.info(">>>>>>>>>>>>>>gotcha is free!");
            updateLeftFreeTimes(userId, expense);
        }
        GotchaView result = new GotchaView();
        result.setGotchaId(gid);
        result.setFragmentId(gcar.getPartId());
        result.setCarId(gcar.getCarId());
        List<Integer> rewards = new ArrayList<Integer>();
        UserGotcha ugot = userGotchaService.getUserGotchaByUidGid(userId, gcar.getId());
        if (ugot == null) {
            ugot = new UserGotcha();
            ugot.setGotchaId(gid);
            ugot.setUserId(userId);
            ugot.setGold(0);// 如果不是gold要处理
            ugot.setMissFragCount(0);
            userGotchaService.insert(ugot);
        }
        UserCarFragment frag = fragmentService.getUserCarFragment(userId, gcar.getPartId());
        if (frag == null) {
            frag = fragmentService.build(userId, gcar.getPartId(), 0);
            fragmentService.insert(frag);
        }
        boolean mustBingo = mustBingo(userId);
        boolean canGetPart = frag.getCount() < gcar.getPartNum();
        int gotchaCount = expense.getCount(); // 循环抽奖的次数
        boolean canGot = canGetCar(userId, gcar, ugot);
        int partCount = 0;
        int noPartCount = ugot.getMissFragCount(); // 记录连续不出碎片的次数
        int userGotchaGold = ugot.getGold();
        for (int i = 0; i < gotchaCount; i++) {
            // 抽车
            userGotchaGold = userGotchaGold + expense.getPrice() / expense.getCount();
            double carRatio = calcCarRatio(userGotchaGold, gcar);
            if (canGot || bingo(carRatio)) {
                log.warn("get_car:carRatio=" + carRatio + "spend gold:" + userGotchaGold);
                result.setLuckdog(true);
                canGot = false;
                gotchaRecord.setIsBingoCar(1);
                continue;
            }
            // 抽碎片
            if (mustBingo) {
                partCount = partCount + NumberUtil.randomRange(1, 3);
                noPartCount = 0;
                mustBingo = false;
                continue;
            }
            if (canGetPart) {
                double partRatio = calcPartRatio(noPartCount);
                if (bingo(partRatio)) {
                    partCount = partCount + NumberUtil.randomRange(1, 3);
                    noPartCount = 0;
                    continue;
                } else {
                    noPartCount++;
                }
            }
            // 出通用奖励
            rewards.add(ratioService.getRandomReward(gid));

        }
        result.setFragmentNum(partCount);
        result.setRewards(rewards);
        // 保存本次消费
        userPayService.saveExpense(userId, expense.getPrice(), expense.getPriceType());

        ugot.setGold(ugot.getGold() + expense.getPrice());

        ugot.setMissFragCount(noPartCount);
        userGotchaService.update(ugot);

        // 进行奖励 如果碎片够换车 此处会改变返回试图的状态 以便前端显示用
        user = awardGotcha(result, gcar, user);
        result.setUser(user);
        int spendGoldNum = expense.getPrice();
        if (isGotchaFree) {
            spendGoldNum = 0;
        }
        gotchaRecord.setCarId(carId);
        gotchaRecord.setFragId(gcar.getPartId());
        gotchaRecord.setFragNum(partCount);
        gotchaRecord.setGotchaId(expense.getGotchaId());
        gotchaRecord.setSpendGoldNum(spendGoldNum);
        gotchaRecord.setUserId(userId);
        return result;
    }

    /**
     * 发放奖励
     * 
     * @param gcar
     * @param result
     * @param user
     */
    private User awardGotcha(GotchaView view, GotchaCar gcar, User user) {

        long userId = user.getId();
        // give car
        if (view.isLuckdog()) {
            userCarService.save(userId, view.getCarId());
        }
        // give fragment
        if (view.getFragmentNum() > 0) {
            UserCarFragment frag = fragmentService.getUserCarFragment(userId, view.getFragmentId());
            if (frag == null) {
                frag = fragmentService.build(userId, view.getFragmentId(), view.getFragmentNum());
                fragmentService.insert(frag);
            } else {
                frag.setCount(frag.getCount() + view.getFragmentNum());
                fragmentService.update(frag);
            }
            // 如果可以换车就直接给车 注意没得到车的时候才去换碎片 以免用户碎片被扣除
            // if (!view.isLuckdog() && frag.getCount() >= gcar.getPartNum()) {
            // userCarService.save(userId, view.getCarId());
            // view.setLuckdog(true);
            // frag.setCount(frag.getCount() - gcar.getPartNum());
            // fragmentService.update(frag);
            // }
        }
        // give rewardUser
        user = rewardService.doRewards(user, view.getRewards());
        return user;

    }

    /**
     * 用户是否可以抽车 必须>=本车消费下限 必须>=充值金币下限
     * 
     * @param userId
     * @param gcar
     * @return
     */
    private boolean canGetCar(long userId, GotchaCar gcar, UserGotcha ugot) {
        if (ugot == null) {
            return false;
        }
        UserPay userPay = userPayService.getUserPay(userId);
        if (userPay == null) {
            return false;
        }
        if (userPay.getPayment() >= gcar.getGoldPayLimit() && ugot.getGold() >= gcar.getGoldUpperLimit()) {
            return true;
        }
        return false;
    }

    private double calcCarRatio(int userGotchaGold, GotchaCar gcar) {
        double A = formulaService.getCarParamA();
        double B = formulaService.getCarParamB();
        double ratio = (userGotchaGold - gcar.getGoldLowLimit())
                / (double) (gcar.getGoldUpperLimit() - gcar.getGoldLowLimit());
        return Math.pow(ratio, A) * B;
    }

    private double calcPartRatio(int noPartCount) {
        double A = formulaService.getPartParamA();
        double B = formulaService.getPartParamB();
        double C = formulaService.getPartParamC();
        return Math.pow(noPartCount / A, B) + C;
    }

    /**
     * 嗨孙子，爷中了！
     * 
     * @param ratio
     * @return
     */
    private boolean bingo(double ratio) {
        if (ratio <= 0) {
            return false;
        }
        double result = NumberUtil.randomDouble();
        return (result - ratio) < 0;
    }

    private boolean mustBingo(long userId) {
        boolean result = false;
        int maxTime = gotchaRecordService.getMaxTimeByUserId(userId);
        if (maxTime > 0 && DateUtil.intervalDays(new Date(System.currentTimeMillis()), new Date(maxTime * 1000l)) > 2) {
            result = true;
        }
        return result;
    }

    /**
     * 给gotcha后刷新车辆数据调用的
     * 
     * 
     * @param userId
     * @param carId
     * @return
     */
    public UserCarFragment getUserCarFragment(long userId, String carId) {
        GotchaCar gcar = gcarService.getGotchaCar(carId);
        if (gcar == null) {
            return null;
        }
        return fragmentService.getUserCarFragment(userId, gcar.getPartId());
    }

    /**
     * 给gotcha后刷新车辆数据调用的 抽奖3按钮
     * 
     * @param carId
     * @return
     */

    public List<Commands.GotchaExpense> buildGotchaExpenseProtocModels(String carId, long userId) {

        GotchaCar gcar = gcarService.getGotchaCar(carId);
        if (gcar == null) {
            return Collections.emptyList();
        }
        List<GotchaExpense> list = expService.getGotchaExpenseList(gcar.getId());

        if (list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        List<Commands.GotchaExpense> result = new ArrayList<Commands.GotchaExpense>();
        for (GotchaExpense exp : list) {
            Commands.GotchaExpense.Builder builder = Commands.GotchaExpense.newBuilder();
            setGotchaExpenseBuilder(builder, exp, userId);
            result.add(builder.build());
        }
        return result;
    }

    private void setGotchaExpenseBuilder(Commands.GotchaExpense.Builder builder, GotchaExpense gotchaExpense,
            long userId) {
        boolean isFree = isGotchaFree(userId, gotchaExpense);
        int price = gotchaExpense.getPrice();
        int leftFreeTimes = 0;
        if (isFree) {
            price = 0;
            leftFreeTimes = getLeftFreeTimes(userId, gotchaExpense);
        }
        builder.setLeftFreeTimes(leftFreeTimes);
        builder.setLevel(gotchaExpense.getLevel());
        builder.setPrice(price);
        builder.setPriceType(gotchaExpense.getPriceType());
    }

    private boolean isGotchaFree(long userId, GotchaExpense gotchaExpense) {
        boolean result = false;
        GotchaCar gotchaCar = gcarService.getGotchaCarById(gotchaExpense.getGotchaId());
        if (gotchaCar == null) {
            return false;
        }
        UserFreeFragRecord userFreeFragRecord = userFreeFragRecordService.getUserFreeFragRecord(userId,
                gotchaCar.getCarId(), gotchaExpense.getLevel());
        if (userFreeFragRecord == null) {
            userFreeFragRecord = new UserFreeFragRecord();
            userFreeFragRecord.setCarId(gotchaCar.getCarId());
            userFreeFragRecord.setLastUseTime((int) (System.currentTimeMillis() / 1000));
            userFreeFragRecord.setLevel(gotchaExpense.getLevel());
            userFreeFragRecord.setLeftTimes(gotchaExpense.getDailyFreeTimes());
            userFreeFragRecord.setUserId(userId);
            userFreeFragRecordService.insert(userFreeFragRecord);
        }
        Date lastUseTime = new Date(userFreeFragRecord.getLastUseTime() * 1000L
                - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS * 1000l);

        Date currentTime = new Date(System.currentTimeMillis() - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS * 1000l);
        int days = DateUtil.intervalDays(currentTime, lastUseTime);
        if (userFreeFragRecord.getLeftTimes() > 0 || (days > 0 && gotchaExpense.getDailyFreeTimes() > 0)) {
            result = true;
        }

        return result;
    }

    private void updateLeftFreeTimes(long userId, GotchaExpense gotchaExpense) {
        GotchaCar gotchaCar = gcarService.getGotchaCarById(gotchaExpense.getGotchaId());
        if (gotchaCar == null) {
            return;
        }
        UserFreeFragRecord userFreeFragRecord = userFreeFragRecordService.getUserFreeFragRecord(userId,
                gotchaCar.getCarId(), gotchaExpense.getLevel());
        if (userFreeFragRecord != null && userFreeFragRecord.getLeftTimes() > 0) {
            userFreeFragRecord.setLastUseTime((int) (System.currentTimeMillis() / 1000));
            userFreeFragRecord.setLeftTimes(userFreeFragRecord.getLeftTimes() - 1);
            userFreeFragRecordService.update(userFreeFragRecord);
        }
    }

    private int getLeftFreeTimes(long userId, GotchaExpense gotchaExpense) {
        int leftFreeTimes = 0;
        GotchaCar gotchaCar = gcarService.getGotchaCarById(gotchaExpense.getGotchaId());
        if (gotchaCar == null) {
            return leftFreeTimes;
        }
        UserFreeFragRecord userFreeFragRecord = userFreeFragRecordService.getUserFreeFragRecord(userId,
                gotchaCar.getCarId(), gotchaExpense.getLevel());
        if (userFreeFragRecord == null) {
            userFreeFragRecord = new UserFreeFragRecord();
            userFreeFragRecord.setLeftTimes(gotchaExpense.getDailyFreeTimes());
            userFreeFragRecord.setLastUseTime((int) (System.currentTimeMillis() / 1000));
            userFreeFragRecordService.insert(userFreeFragRecord);
            return userFreeFragRecord.getLeftTimes();
        }
        Date lastUseTime = new Date(userFreeFragRecord.getLastUseTime() * 1000L
                - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS * 1000l);

        Date currentTime = new Date(System.currentTimeMillis() - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS * 1000l);
        int days = DateUtil.intervalDays(currentTime, lastUseTime);
        if (days > 0) {
            userFreeFragRecord.setLeftTimes(gotchaExpense.getDailyFreeTimes());
            userFreeFragRecord.setLastUseTime((int) (System.currentTimeMillis() / 1000));

            userFreeFragRecordService.update(userFreeFragRecord);

            leftFreeTimes = gotchaExpense.getDailyFreeTimes();
        } else {
            leftFreeTimes = userFreeFragRecord.getLeftTimes();
        }
        return leftFreeTimes;

    }
}