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
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.model.bean.LotteryRewardBean;

public class XmlUtil extends CommonUtil {
	private static Logger logger = Logger.getLogger(XmlUtil.class);

	
	public static List<LotteryBean> getLotteryList(int type) {
		List<LotteryBean> lotteryList = new ArrayList<LotteryBean>();
		try {
			String filePath = CommonUtil.getConfigFilePath(XmlParseConst.LOTTERY_FILE_PREFIX + type + ".xml");
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
				List<?> rewardNodeList = lotteryElement.elements();
				List<LotteryRewardBean> rewardList = new ArrayList<LotteryRewardBean>();
				for (int k = 0; k < rewardNodeList.size(); ++k) {
					Element rewardElement = (Element) rewardNodeList.get(k);
					LotteryRewardBean reward = new LotteryRewardBean();
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
			logger.error("parse lottery.xml failed");
		}
		
		return lotteryList;
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
