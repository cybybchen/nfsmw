package com.ea.eamobile.nfsmw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ea.eamobile.nfsmw.constants.XmlParseConst;
import com.ea.eamobile.nfsmw.model.bean.FansRewardBean;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.model.bean.RechargeDataBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.TaskBean;

public class XmlUtil extends CommonUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class);

	
	public static List<LotteryBean> getLotteryList(int type) {
		logger.debug("parse random_" + type + ".xml");
		List<LotteryBean> lotteryList = new ArrayList<LotteryBean>();
		try {
			String filePath = CommonUtil.getConfigFilePath(XmlParseConst.LOTTERY_FILE_PREFIX + type + ".xml");
			logger.debug("filePath is:" + filePath);
			SAXReader reader = new SAXReader();
			InputStream inStream = new FileInputStream(new File(filePath));
			Document doc = reader.read(inStream);
			// 获取根节点
			Element root = doc.getRootElement();
			List<?> rootList = root.elements();
			for (int i = 0; i < rootList.size(); i++) {
				LotteryBean lottery = new LotteryBean();
				Element lotteryElement = (Element) rootList.get(i);
				lottery.setId(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.ID)));
				lottery.setWeight(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.WEIGHT)));
				lottery.setName(getElementAttr(lotteryElement, XmlParseConst.NAME));
				lottery.setLeastTimes(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.LEASTTIMES)));
				List<?> rewardNodeList = lotteryElement.elements();
				List<RewardBean> rewardList = new ArrayList<RewardBean>();
				for (int k = 0; k < rewardNodeList.size(); ++k) {
					Element rewardElement = (Element) rewardNodeList.get(k);
					RewardBean reward = new RewardBean();
					reward.setId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.ID)));
					reward.setRewardId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDID)));
					reward.setRewardCount(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDCOUNT)));
					rewardList.add(reward);
				}
				lottery.setLotteryRewardList(rewardList);
				lotteryList.add(lottery);
			}
		} catch (Exception e) {
			logger.error("parse random_" + type + ".xml failed");
		}
		
		return lotteryList;
	}
	
	public static List<FansRewardBean> getFansRewardList() {
		List<FansRewardBean> fansRewardList = new ArrayList<FansRewardBean>();
		try {
			String filePath = CommonUtil.getConfigFilePath(XmlParseConst.FANS_REWARD_FILE);
			SAXReader reader = new SAXReader();
			InputStream inStream = new FileInputStream(new File(filePath));
			Document doc = reader.read(inStream);
			// 获取根节点
			Element root = doc.getRootElement();
			List<?> rootList = root.elements();
			for (int i = 0; i < rootList.size(); i++) {
				FansRewardBean fansReward = new FansRewardBean();
				Element lotteryElement = (Element) rootList.get(i);
				fansReward.setId(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.ID)));
				fansReward.setCountdown(CommonUtil.stringToFloat(
						getElementAttr(lotteryElement, XmlParseConst.COUNTDOWN)));
				fansReward.setName(getElementAttr(lotteryElement, XmlParseConst.NAME));
				List<?> rewardNodeList = lotteryElement.elements();
				List<RewardBean> rewardList = new ArrayList<RewardBean>();
				for (int k = 0; k < rewardNodeList.size(); ++k) {
					Element rewardElement = (Element) rewardNodeList.get(k);
					RewardBean reward = new RewardBean();
					reward.setId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.ID)));
					reward.setRewardId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDID)));
					reward.setRewardCount(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDCOUNT)));
					rewardList.add(reward);
				}
				fansReward.setRewardList(rewardList);
				fansRewardList.add(fansReward);
			}
		} catch (Exception e) {
			logger.error("parse fans_reward.xml failed");
		}
		
		return fansRewardList;
	}
	
	public static List<RechargeDataBean> getRechargeDataList() {
		List<RechargeDataBean> rechargeDataList = new ArrayList<RechargeDataBean>();
		try {
			String filePath = CommonUtil.getConfigFilePath(XmlParseConst.RECHARGE_DATA_FILE);
			SAXReader reader = new SAXReader();
			InputStream inStream = new FileInputStream(new File(filePath));
			Document doc = reader.read(inStream);
			// 获取根节点
			Element root = doc.getRootElement();
			List<?> rootList = root.elements();
			for (int i = 0; i < rootList.size(); i++) {
				RechargeDataBean recharge = new RechargeDataBean();
				Element rechargeElement = (Element) rootList.get(i);
				recharge.setId(CommonUtil.stringToInt(
						getElementAttr(rechargeElement, XmlParseConst.ID)));
				recharge.setExpense(CommonUtil.stringToInt(
						getElementAttr(rechargeElement, XmlParseConst.EXPENSE)));
				recharge.setName(getElementAttr(rechargeElement, XmlParseConst.NAME));
				recharge.setTransactionId(getElementAttr(rechargeElement, XmlParseConst.TRANSACTIONID));
				recharge.setVipId(CommonUtil.stringToInt(
						getElementAttr(rechargeElement, XmlParseConst.VIPID)));
				recharge.setLastTime(CommonUtil.stringToInt(
						getElementAttr(rechargeElement, XmlParseConst.LASTTIME)));
				List<?> rewardNodeList = rechargeElement.elements();
				List<RewardBean> rewardList = new ArrayList<RewardBean>();
				for (int k = 0; k < rewardNodeList.size(); ++k) {
					Element rewardElement = (Element) rewardNodeList.get(k);
					RewardBean reward = new RewardBean();
					reward.setId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.ID)));
					reward.setRewardId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDID)));
					reward.setRewardCount(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDCOUNT)));
					reward.setContinued(CommonUtil.stringToBoolean(
							getElementAttr(rewardElement, XmlParseConst.ISCONTINUED)));
					rewardList.add(reward);
				}
				recharge.setRewardList(rewardList);
				rechargeDataList.add(recharge);
			}
		} catch (Exception e) {
			logger.error("parse recharge_data.xml failed");
		}
		
		return rechargeDataList;
	}
	
	public static List<TaskBean> getTaskList() {
		List<TaskBean> taskList = new ArrayList<TaskBean>();
		try {
			String filePath = CommonUtil.getConfigFilePath(XmlParseConst.TASK_FILE);
			SAXReader reader = new SAXReader();
			InputStream inStream = new FileInputStream(new File(filePath));
			Document doc = reader.read(inStream);
			// 获取根节点
			Element root = doc.getRootElement();
			List<?> rootList = root.elements();
			for (int i = 0; i < rootList.size(); i++) {
				TaskBean task = new TaskBean();
				Element lotteryElement = (Element) rootList.get(i);
				task.setId(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.ID)));
				task.setDes(getElementAttr(lotteryElement, XmlParseConst.DES));
				task.setName(getElementAttr(lotteryElement, XmlParseConst.NAME));
				task.setType(CommonUtil.stringToInt(
						getElementAttr(lotteryElement, XmlParseConst.TYPE)));
				List<?> rewardNodeList = lotteryElement.elements();
				List<RewardBean> rewardList = new ArrayList<RewardBean>();
				for (int k = 0; k < rewardNodeList.size(); ++k) {
					Element rewardElement = (Element) rewardNodeList.get(k);
					RewardBean reward = new RewardBean();
					reward.setId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.ID)));
					reward.setRewardId(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDID)));
					reward.setRewardCount(CommonUtil.stringToInt(
							getElementAttr(rewardElement, XmlParseConst.REWARDCOUNT)));
					rewardList.add(reward);
				}
				task.setRewardList(rewardList);
				taskList.add(task);
			}
		} catch (Exception e) {
			logger.error("parse task.xml failed");
		}
		
		return taskList;
	}
	
	public static String getElementText(Element element, String name) {
		String text = "";
		try {
			text = element.elementText(name);
		} catch (Exception e) {
			logger.error("parse element failed:" + name);
		}
		if (text == null)
			text = "";
		logger.debug("text is:"+text);
		return text;
	}
	
	public static String getElementAttr(Element element, String name) {
		String attr = "";
		try {
			attr = element.attributeValue(name);
		} catch (Exception e) {
			logger.error("parse element failed" + name);
		}
		if (attr == null)
			attr = "";
		logger.debug("attr is:"+attr);
		return attr;
	}
}
