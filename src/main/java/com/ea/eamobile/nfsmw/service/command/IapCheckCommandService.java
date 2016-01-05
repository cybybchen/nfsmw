package com.ea.eamobile.nfsmw.service.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.constants.IapConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.IapCheckInfo;
import com.ea.eamobile.nfsmw.model.IapFailtureRecord;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserPay;
import com.ea.eamobile.nfsmw.model.bean.RechargeDataBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestIapCheckCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseIapCheckCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseNotificationCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.IapCheckInfoService;
import com.ea.eamobile.nfsmw.service.IapFailtureRecordService;
import com.ea.eamobile.nfsmw.service.RechargeDataService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserPayService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserVipService;
import com.ea.eamobile.nfsmw.utils.CommonUtil;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class IapCheckCommandService extends BaseCommandService {
	
	private static final Logger log = LoggerFactory.getLogger(IapCheckCommandService.class);
	
    @Autowired
    private UserService userService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private IapCheckInfoService iapCheckInfoService;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private IapFailtureRecordService iapFailtureRecordService;
    @Autowired
    private UserPayService userPayService;
    @Autowired
    private RechargeDataService rechargeDataService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserVipService userVipService;

    public ResponseIapCheckCommand getResponseIapCheckCommand(RequestIapCheckCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) {
        ResponseIapCheckCommand.Builder builder = ResponseIapCheckCommand.newBuilder();
        // User user = userService.getUser(userId);
        long userId = user.getId();
        IapFailtureRecord iapFailtureRecord = new IapFailtureRecord();
        iapFailtureRecord.setReceiptData(reqcmd.getReceiptData());
        iapFailtureRecord.setUserId(userId);
        List<IapCheckInfo> iapCheckInfoList = getGoldFromIap(reqcmd.getReceiptData(), iapFailtureRecord, userId, responseBuilder
                .build().getHead().getVersion());

        boolean allWrong = true;
        for (IapCheckInfo iapCheckInfo : iapCheckInfoList) {
        	if (iapCheckInfo.getTransactionId() == 0) {
                iapFailtureRecord.setReason("wrong receipt data");
                iapFailtureRecordService.insert(iapFailtureRecord);
            } else
            	allWrong = false;
        }
        
        if (allWrong) {
            builder.setSuccess(false);
            builder.setMessage(ctaContentService.getCtaContent(CtaContentConst.WRONG_ITEM_INFO).getContent());
            //

            return builder.build();
        }
        boolean allUsefulCheck = false;
        for (IapCheckInfo iapCheckInfo : iapCheckInfoList) {
        	IapCheckInfo oldIapCheckInfo = new IapCheckInfo();
        	iapCheckInfo.setUserId(userId);
        	if (IapConst.RESTORE_IAP_PRODUCTID_LIST.contains(iapCheckInfo.getProductId())) {
        		oldIapCheckInfo = iapCheckInfoService.getIapCheckInfoByUserIdAndTransactionId(iapCheckInfo);
        	} else {
        		oldIapCheckInfo = iapCheckInfoService.getIapCheckInfoByTransactionId(iapCheckInfo
	                .getTransactionId());
        	}
	        
	        if (oldIapCheckInfo != null) {
	            continue;
	        } else
	        	allUsefulCheck = true;
	        
	        int addGold = 0;
	        Integer itemValue = IapConst.GOLD_VALUE_MAP.get(iapCheckInfo.getProductId());
	        if (itemValue != null) {
	            addGold = itemValue * iapCheckInfo.getQuantity();
	        }
	        Integer rmbNum = 0;
	        int additionGold = getAdditionGold(iapCheckInfo.getProductId(), responseBuilder, userId);
	        addGold = addGold + additionGold;
	        user.setGold(user.getGold() + addGold);
	        try {
				sendCarOfFirstBuyGold(iapCheckInfo.getProductId(), responseBuilder, userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        //购买礼包
	        checkBuyPackageResult(iapCheckInfo.getProductId(), responseBuilder, user);
	        
	        iapCheckInfo.setCreateTime((int) (new Date().getTime() / 1000));
	        iapCheckInfo.setUserId(userId);
	        int payMent = 0;
	        if (IapConst.RMB_MAP.get(iapCheckInfo.getProductId()) != null) {
	            payMent = IapConst.RMB_MAP.get(iapCheckInfo.getProductId());
	            rmbNum = IapConst.RMB_MAP.get(iapCheckInfo.getProductId());
	        }
	        iapCheckInfo.setRmbNum(rmbNum);
	        UserPay userPay = userPayService.buildUserPay(userId, payMent, 0, 0);
	        userPayService.insertOrUpdate(userPay);
	        iapCheckInfoService.insert(iapCheckInfo); 
        }
        
        if (!allUsefulCheck) {
        	iapFailtureRecord.setReason("double add");
            iapFailtureRecordService.insert(iapFailtureRecord);
	        builder.setSuccess(false);
            builder.setMessage(ctaContentService.getCtaContent(CtaContentConst.MONEY_ALREADY_ADDED).getContent());
            return builder.build();
        }
        
        userService.updateUser(user);
        builder.setSuccess(true);
        builder.setMessage(ctaContentService.getCtaContent(CtaContentConst.HAVE_BEEN_ADDED_MONEY).getContent());
        pushService.pushUserInfoCommand(responseBuilder, user);
        return builder.build();
    }

    //首次充值，送对应的car
    private void sendCarOfFirstBuyGold(String productId, Commands.ResponseCommand.Builder responseBuilder, 
    		long userId) throws SQLException {
    	IapCheckInfo iapCheckInfo = new IapCheckInfo();
    	iapCheckInfo.setUserId(userId);
    	iapCheckInfo.setProductId(productId);
    	IapCheckInfo oldIapCheckInfo = iapCheckInfoService.getIapCheckInfoByUserIdAndProductId(iapCheckInfo);
    	if (oldIapCheckInfo == null) {
	    	String carId = IapConst.SENDCAR_MAP.get(productId);
	    	if (carId != null) {
	    		ResultInfo result = userCarService.sendCar(userId, carId);
	    		if (result.isSuccess()) {
	    			log.debug("send car success,carId is {}", carId);
	    			pushService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarList(userId),
	                        userId);
	    			pushService.pushPopupCommand(responseBuilder, null, Match.SEND_CAR_POPUP, IapConst.SENDCAR_CNNAME_MAP.get(carId), 0,
	                        0);
	    		}
	    	}
    	}
    }
    
    boolean IsMonthPackage(int id)
    {
    	if(id == RewardConst.PACKAGE_GOLDCARD_MONTH_ID)
    		return true;
    	else if(id == RewardConst.PACKAGE_GOLDCARD2_MONTH_ID)
    		return true;
    	else if(id == RewardConst.PACKAGE_VIP_ID)
    		return true;
    	return false;
    }
    //check购买礼包结果
    private void checkBuyPackageResult(String productId, Commands.ResponseCommand.Builder responseBuilder, 
    		User user) {
    	RechargeDataBean rechargeData = rechargeDataService.getRechargeData(productId);
    	if (rechargeData != null) {
    		if (!IsMonthPackage(rechargeData.getId())) {
    			List<RewardBean> rewardList = rechargeData.getRewardList();
    			rewardService.doRewards(user, rewardList, responseBuilder);
    		}
        	int packageBuyStatus = user.getPackageBuyRecord();
        	if ((packageBuyStatus >> rechargeData.getId() & 1) == 0)
        		user.setPackageBuyRecord(user.getPackageBuyRecord() + (1 << rechargeData.getId()));
        	if (rechargeData.getId() == RewardConst.PACKAGE_VIP_ID)
        		user.setVipEndTime(CommonUtil.getExpiredTime(user.getVipEndTime(), rechargeData.getLastTime() * 24));
        	else if (rechargeData.getId() == RewardConst.PACKAGE_GOLD_ID)
        		user.setGoldModeUnlockStatus(1);
        	else if (rechargeData.getId() == RewardConst.PACKAGE_GOLDCARD_MONTH_ID)
        		user.setMonthGoldCardEndTime(CommonUtil.getExpiredTime(user.getMonthGoldCardEndTime(), rechargeData.getLastTime() * 24));
        	else if (rechargeData.getId() == RewardConst.PACKAGE_GOLDCARD2_MONTH_ID)
        		user.setMonthGoldCard2EndTime(CommonUtil.getExpiredTime(user.getMonthGoldCard2EndTime(), rechargeData.getLastTime() * 24));
        	log.debug("user package buy record is:" + user.getPackageBuyRecord());
        	//领取贵族和金币月卡奖励
            doVipReward(responseBuilder, user);
            doMonthGoldCardReward(responseBuilder, user);
            doMonthGoldCard2Reward(responseBuilder, user);
    	}
    }
    
    private int getAdditionGold(String productId, Commands.ResponseCommand.Builder responseBuilder, long userId) {
        int result = 0;
        long currentTime = System.currentTimeMillis();
        if (currentTime > DateUtil.getDateTime(ConfigUtil.ACT_START).getTime()
                && currentTime < DateUtil.getDateTime(ConfigUtil.ACT_END).getTime()
                && IapConst.GOLD_ADD_MAP.get(productId) != null) {
            result = IapConst.GOLD_ADD_MAP.get(productId);
            if (result > 0) {
                ResponseNotificationCommand command = buildResponseNotification("恭喜你充值成功！活动额外获得" + result + "金币！", 5.0f);
                responseBuilder.setNotificationCommand(command);
            }

        }
        if (result == 0 && userId < 10000 && IapConst.GOLD_ADD_MAP.get(productId) != null) {

            result = IapConst.GOLD_ADD_MAP.get(productId);
            if (result > 0) {
                ResponseNotificationCommand command = buildResponseNotification("恭喜你充值成功！活动额外获得" + result + "金币！", 5.0f);
                responseBuilder.setNotificationCommand(command);
            }
        }
        return result;
    }

    private List<IapCheckInfo> getGoldFromIap(String receiptData, IapFailtureRecord iapFailtureRecord, long userId,
            int version) {
    	List<IapCheckInfo> checkList = new ArrayList<IapCheckInfo>();
//        IapCheckInfo iapCheckInfo = new IapCheckInfo();
//        iapCheckInfo.setTransactionId(0);
//        iapCheckInfo.setVersion(version);
        JSONObject json = getResultJsonByUrl(Const.IAP_URL, receiptData);
        log.debug("iap check return json is:" + json);
        if (json != null) {
            iapFailtureRecord.setResult(json.toString());
            if (!json.isNull("status")) {
                try {
                    int status = json.getInt("status");
                    if (status == 0) {
                    	JSONObject receiptResult = json.getJSONObject("receipt");
                        JSONArray in_app = receiptResult.getJSONArray("in_app");
                        for (int i = 0;i < in_app.length(); ++i) {
                        	IapCheckInfo iapCheckInfo = new IapCheckInfo();
                            iapCheckInfo.setTransactionId(0);
                            iapCheckInfo.setVersion(version);
                            JSONObject checkInfo = in_app.getJSONObject(i);
                            long transactionId = checkInfo.getLong("transaction_id");
                            iapCheckInfo.setTransactionId(transactionId);
                            String productId = checkInfo.getString("product_id");
                            int num = checkInfo.getInt("quantity");
                            iapCheckInfo.setProductId(productId);
                            iapCheckInfo.setQuantity(num);
                            iapCheckInfo.setPaymentMode("appleStore");
                            checkList.add(iapCheckInfo);
                        } 
                    } else if (ConfigUtil.SANBOX_OPEN && status == Const.SAND_BOX_STATUS
                            && version == Const.VERSION_THREE_NUM) {
//                            && ((environment != null && environment.equals("Sandbox"))|| status == Const.SAND_BOX_STATUS)) {
                        json = getResultJsonByUrl(Const.SANDBOX_URL, receiptData);
                        log.debug("iap check of sanbox return json is:" + json);
                        if (json != null) {
                            iapFailtureRecord.setResult(json.toString());
                            if (!json.isNull("status")) {
                                status = json.getInt("status");
                                if (status == 0) {
                                    JSONObject receiptResult = json.getJSONObject("receipt");
                                    JSONArray in_app = receiptResult.getJSONArray("in_app");
                                    for (int i = 0;i < in_app.length(); ++i) {
                                    	IapCheckInfo iapCheckInfo = new IapCheckInfo();
                                        iapCheckInfo.setTransactionId(0);
                                        iapCheckInfo.setVersion(version);
                                        JSONObject checkInfo = in_app.getJSONObject(i);
                                        long transactionId = checkInfo.getLong("transaction_id");
                                        iapCheckInfo.setTransactionId(transactionId);
                                        String productId = checkInfo.getString("product_id");
                                        int num = checkInfo.getInt("quantity");
                                        iapCheckInfo.setProductId(productId);
                                        iapCheckInfo.setQuantity(num);
                                        iapCheckInfo.setPaymentMode("sandbox");
                                        checkList.add(iapCheckInfo);
                                    } 
                                }
                            }
                        }
                    } 
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }

            }
        }
        return checkList;
    }

    private JSONObject getResultJsonByUrl(String url, String receiptData) {
        String encoding = "UTF-8";
//        byte[] bse64;
        try {
//            bse64 = hexToString(receiptData, IapConst.HEX_VALUE_MAP).getBytes(encoding);
//        	bse64 = receiptData.getBytes();
            URL dataUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "text/json");
            con.setRequestProperty("Proxy-Connection", "Keep-Alive");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), encoding);
//            String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + new String(bse64, encoding) + "\"}");
            String str = "{\"receipt-data\":\"" + receiptData + "\"}";
            log.debug("ios request str={}", str);
            out.write(str);
            out.flush();
            out.close();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
            String line = null;
            String content = "";
            while ((line = reader.readLine()) != null) {
//                if (!line.startsWith("{")) {
//                    line = "{" + line;
//                }
//                if (!line.endsWith("}")) {
//                    line = line + "}";
//                }
                
                content += line;
            }
        	try {
        		log.debug("content={}", content);
	            JSONObject json = new JSONObject(content);
	            reader.close();
	            return json;
        	} catch (JSONException e) {
                // TODO Auto-generated catch block
                log.debug("json parse failed: " + content);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hexToString(String src, Map<Character, Integer> valueMap) {
        StringBuffer ret = new StringBuffer();
        boolean isPart = false;
        char currentChar = 0;
        for (int i = 0; i < src.length(); ++i) {
            char c = src.charAt(i);
            if (c == '<' || c == '>' || c == ' ') {
                continue;
            }
            if (isPart) {
                currentChar = (char) ((valueMap.get(currentChar) << 4) | valueMap.get(c));
                ret.append(currentChar);
                isPart = false;
            } else {
                currentChar = c;
                isPart = true;
            }
        }
        return ret.toString();
    }
    
    private void doVipReward(Builder responseBuilder, User user) {
        boolean ret = userVipService.addUserVipReward(user, responseBuilder);
        if (ret) {
        	pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_VIPREWARD_POPUP, "每日登录获得[color=fbce54]10金币，$100，免费抽奖1次[/color] ", 0, 0);
        }
    }
	
	private void doMonthGoldCardReward(Builder responseBuilder, User user) {
        boolean ret = userVipService.doUserMonthGoldCardReward(user);
        if (ret) {
        	pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_MONTH_GOLD_POPUP, "每日登录获得[color=fbce54]80金币[/color] ", 0, 0);
        }
    }

	private void doMonthGoldCard2Reward(Builder responseBuilder, User user) {
        boolean ret = userVipService.doUserMonthGoldCard2Reward(user);
        if (ret) {
        	pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_MONTH_GOLD_POPUP2, "每日登录获得[color=fbce54]160金币[/color] ", 0, 0);
        }
    }
}
