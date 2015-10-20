package com.ea.eamobile.nfsmw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CommonUtil {
	private static Logger logger = Logger.getLogger(CommonUtil.class);
	/**
	 * 判断一个字符串中是否含有特殊字符
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean checkSpecialChar(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}';',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		
		return m.find();
	}
	
	/**
	 * 随机生成数字
	 * 
	 * @param count
	 * @return
	 */
	public static int randomGet(int count) {
		Random random = new Random();
		
		return random.nextInt(count);
	}
	
	public static int stringToInt(String str) {
		int id = 0;
		try {
			id = Integer.parseInt(str);
		} catch (Exception e) {
//			logger.error("parse str to int error:" + str);
		}
		return id;
	}
	
	public static float stringToFloat(String str) {
		float id = 0.f;
		try {
			id = Float.parseFloat(str);
		} catch (Exception e) {
//			logger.error("parse str to int error:" + str);
		}
		return id;
	}
	
	public static long stringToLong(String str) {
		long id = 0;
		try {
			id = Long.parseLong(str);
		} catch (Exception e) {
//			logger.error("parse str to long error:" + str);
		}
		return id;
	}
	
	public static boolean stringToBoolean(String str) {
		boolean result = false;
		try {
			result = Boolean.parseBoolean(str);
		} catch (Exception e) {
//			logger.error("parse str to boolean error:" + str);
		}
		return result;
	}
	
	/**
	 * 获取指定文件的MD5值, 若存在md5文件，则返回md5文件中的值
	 * 
	 * @param fileName
	 * @return
	 */
	public static String myGetMd5String(String fileName) {
		if (fileExist(fileName + ".md5")) {
			String md5 = getFileContent(fileName + ".md5");
			if (!md5.trim().equals("") && md5 != null) {
				return md5;
			}
		}
		if (!fileExist(fileName)) {
			logger.info("get file md5 " + fileName
					+ ", file dost not exist!");
			return "";
		}
		File file = new File(fileName);
		if (!file.isFile()) {
			logger.info("get file md5 " + fileName
					+ ", not a file!");
			return "";
		}
		String md5 = "";
		// 缓冲区大小（这个可以抽出一个参数）
		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用DigestInputStream
			fileInputStream = new FileInputStream(file);
			digestInputStream = new DigestInputStream(fileInputStream,
					messageDigest);
			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0)
				;
			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 同样，把字节数组转换成字符串
			md5 = byte2hex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (digestInputStream != null) {
					digestInputStream.close();
					digestInputStream = null;
				}
				
				if (fileInputStream != null) {
					fileInputStream.close();
					fileInputStream = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return md5;
	}
	
	/**
	 * 判断文件是否存在（不包含assets目录）
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean fileExist(String fileName) {
		boolean bExist = false;

		File file = new File(fileName);
		if (!file.isDirectory()) {
			bExist = file.exists();
		}
		
		return bExist;
	}
	
	/**
	 * 读取文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileContent(String fileName) {
		File file = new File(fileName);
		String content = "";
		if (fileExist(fileName)) {
			content = file.toString();
		} else {
			logger.info("file is not found!");
		}
		
		return content;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		final String hex = "0123456789abcdef";
		String str = "";

		for (int i = 0; i < bytes.length; i++) {
			str += hex.charAt((bytes[i] >> 4) & 0xf);
			str += hex.charAt(bytes[i] & 0xf);
		}
		return str;
	}
	
	public static String getImagePath(String imageName) {
		String path = "/var/www/html/HappyRunCoolFile/res/" + imageName;
		return path;
	}
	
	public static String getDownloadFilePath(String fileName, String version) {
		String path = "/var/www/html/HappyRunCoolFile/download/" + version + "/" + fileName;
		return path;
	}
	
	public static String getConfigFilePath(String file) {
		String path = "/var/www/html/NfsmwFile/conf_file/" + file;
		return path;
	}
	
	public static JSONArray jsonGetArray(JSONObject json, String property) {
		JSONArray value = null;
		try {
			value = json.getJSONArray(property);
		} catch (JSONException e) {
			logger.info("parse json to int error:" + property + e);
		}
		return value;
	}
	
	public static int jsonGetInt(JSONObject json, String property) {
		int value = 0;
		try {
			value = json.getInt(property);
		} catch (JSONException e) {
//			logger.info("parse json to int error:" + property);
		}
		return value;
	}
	
	public static long jsonGetLong(JSONObject json, String property) {
		long value = 0;
		try {
			value = json.getLong(property);
		} catch (JSONException e) {
//			logger.info("parse json to int error:" + property);
		}
		return value;
	}
	
	public static float jsonGetFloat(JSONObject json, String property) {
		float value = .0f;
		try {
			value = (float) json.getDouble(property);
		} catch (JSONException e) {
//			logger.info("parse json to float error:" + property);
		}
		return value;
	}
	
	public static String jsonGetString(JSONObject json, String property) {
		String value = "";
		try {
			value = json.getString(property);
		} catch (JSONException e) {
//			logger.info("parse json to string error:" + property);
		}
		return value;
	}  
	
	public static boolean jsonGetBoolean(JSONObject json, String property) {
		boolean value = false;
		try {
			value = json.getBoolean(property);
		} catch (JSONException e) {
//			logger.info("parse json to boolean error:" + property);
		}
		return value;
	} 
	
	public static Date getEndDateOfY(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = df.format(new Date());
		String day = now.substring(0, 10);
//		int hour = Integer.parseInt(now.substring(11, 13));
		String resultTime = "";
		if (time.equals("")) {
			resultTime = day + " 24:00:00";
		} else {
			resultTime = time;
		}
//		if (hour < 6) {
//			// 时间设置到当天6点
//			resultTime = day + " " + time;
//		} else {
//			// 时间设置到第二天6点
//			int daynow = Integer.parseInt(day.substring(8, 10));
//			resultTime = day.substring(0, 8) + (daynow + 1) + " " + time;
//		}
//		logger.info("time is:"+time+"target time out " + resultTime);
		Date date = null;
		try {
			date = df.parse(resultTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		logger.info("date after is:"+date);
		
		return date;
	}
	
	public static Date getEndDate(String time) {
		return getEndDateOfY(time);
	}
	
	public static Date getEndDate() {
		return getEndDateOfY("");
	}
	
	public static Date getEndDateOfD() {
		return getEndDateOfD("");
	}
	
	public static Date getEndDateOfD(String time) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String resultTime = "";
		if (time.equals("")) {
			resultTime = "24:00:00";
		} else {
			resultTime = time;
		}
//		logger.info("time is:"+time+"target time out " + resultTime);
		Date date = null;
		try {
			date = df.parse(resultTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		logger.info("date after is:"+date);
		
		return date;
	}
	
	public static String getXmlTextString(String rootStr, Map<String, String> elementMap) {
		if (elementMap == null) {
			return "";
		}
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<" + rootStr + ">";
		Iterator<Entry<String, String>> itr = elementMap.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			xmlString += "<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">";
		}
		
		xmlString += "</" + rootStr + ">";
		return xmlString;
	}
	
	public static String getXmlString(String rootStr, Map<String, String> elementMap) {
		if (elementMap == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<" + rootStr + ">");
		Iterator<Entry<String, String>> itr = elementMap.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			sb.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
		}
		sb.append("</" + rootStr + ">");
        
        return sb.toString();
	}
	
	public static String getBASE64(String s) {   
		if (s == null) return null;   
		return (new BASE64Encoder()).encode( s.getBytes());   
	} 
	
	public static String getBASE64(byte[] buffer) {   
		if (buffer == null) return null;
		BASE64Encoder encode = new BASE64Encoder();
		return encode.encode(buffer);   
	} 
	
	public static String decodeBase64String(String s) {
		if (s == null)
			return null;
		byte[] bt = null;  
	    try {  
	    	s = s.replace(' ', '+');
	        BASE64Decoder decoder = new BASE64Decoder();  
	        bt = decoder.decodeBuffer(s);  
	    } catch (IOException e) {  
	        logger.error("decodebase64 failed " + e);  
	    }  
	    
	    return new String(bt);
	}
	
	public static String decodeBase64String(InputStream is) {
		if (is == null)
			return null;
		byte[] bt = null;  
	    try {  
//	    	String s = buffer.replace(' ', '+');
	        BASE64Decoder decoder = new BASE64Decoder();  
	        bt = decoder.decodeBuffer(is); 
	    } catch (IOException e) {  
	        logger.error("decodebase64 failed " + e);  
	    }  
	    
	    return new String(bt);
	}
	
	public static String toHexString(String str) {
        String hexStr = "";  
        for (int i = 0; i < str.length(); i++) {  
            int ch = (int) str.charAt(i);  
            String sHex = Integer.toHexString(ch);  
            hexStr = hexStr + sHex;  
        }  
        return "0x" + hexStr;//0x表示十六进制  
	}
	
	public static String base64URLDecode(String str) {
		str = str.replace('-', '+');
		str = str.replace('_', '/');
		if (str.length() % 4 != 0) {
			StringBuilder builder = new StringBuilder(str);
			int padding = str.length() % 4;
			while (padding++ < 4)
				builder.append('=');
			str = builder.toString();
		}
		
		String result = "";
		try {
			result = new String(Base64.decode(str), "UTF-8");
		} catch (Exception e) {
			logger.error("base64 decode failed:" + str);
		}
		
		return result;
	} 
	
	public static String UTF8Decode(String str) {
		String decodeStr = "";
		try {
			decodeStr = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("urldecode failed:" + e);
		}
		
		return decodeStr;
	}
	
	public static boolean isTimeExpried(String time) {
		logger.debug("111time is:" + time);
		if (time == null || time.equals(""))
			return false;
		logger.debug("222time is:" + time);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		Date currentDate = null;
		String currentTimeStr = df.format(new Date());
		try {
			date= df.parse(time);
			currentDate = df.parse(currentTimeStr);
		} catch (ParseException e) {
			logger.error("parse time failed"+e);
		}  
		
		if (date.after(currentDate))
			return true;
		
		return false;
	}
	
	public static int floatToInt(float f){  
	    int i = 0;  
	    if (f > 0) //正数  
	    	i = (int) ((f * 10 + 5) / 10);  
	    else if (f < 0) //负数  
	        i = (int) ((f * 10 - 5) / 10);  
	    else i = 0;  
	  
	    return i;  
	}  
	
	/**
	 * 判断是否是当天的第一次登录
	 * 
	 * @param lastLoginTime
	 * @return
	 */
	public  static boolean isNextDay(String lastLoginTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		boolean nextDay = false;
		if (lastLoginTime == null || lastLoginTime.trim().equals("")) {
			return true;
		}
		try {
			String now = df.format(new Date());
			String last = df.format(df.parse(lastLoginTime));
			if (now.equals(last)) {
				nextDay = false;
			} else {
				nextDay = true;
			}
		} catch (ParseException e) {
			nextDay = false;
			e.printStackTrace();
		}
		
		return nextDay;
	}
	
	public static String getCurrentTimeStr(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		String now = df.format(new Date());
		return now;
	}
	
	public static String getExpiredTime(String time, int hour) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTimeStr = df.format(new Date());
		Calendar calendar = Calendar.getInstance();   
		Date expiredDate = null;
		Date currentDate = null;
	    try {
	    	if (time.equals(""))
	    		calendar.setTime(df.parse(currentTimeStr));
	    	else  {
	    		expiredDate = df.parse(time);
		    	currentDate = df.parse(currentTimeStr);
		    	if (expiredDate.after(currentDate))
		    		calendar.setTime(expiredDate);
		    	else
		    		calendar.setTime(currentDate);
	    	}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("parse expired time failed");
		}  
	    calendar.set(Calendar.HOUR_OF_DAY , calendar.get(Calendar.HOUR_OF_DAY) + hour);
	    expiredDate = calendar.getTime();
	    
	    logger.info("time is:" + time + "|hour is:" + hour + "|date is:" + expiredDate);
	    
	    return df.format(expiredDate);
	}
}
