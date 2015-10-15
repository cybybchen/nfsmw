package com.ea.eamobile.nfsmw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.service.command.EnergyCommandService;

/**
 * 日期操作类
 * 
 * @author ma.ruofei@ea.com
 * @version 1.0 2009-06-23 15:12:23
 * @since 1.0
 */
public class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    /** 单例 */
    private static final DateUtil instance = new DateUtil();

    public static final String STARTTIME_STRING = "start_time";
    public static final String ENDTIME_STRING = "end_time";
//    public static final int SEND_ENERGY_DELHOURS_MIN = 2;
    public static final int SEND_ENERGY_DELHOURS = 4;
    
    private static final String ENERGY_SEND_START_TIME_AM = "12:00:00";
    private static final String ENERGY_SEND_END_TIME_AM = "14:00:00";
    private static final String ENERGY_SEND_START_TIME_PM = "18:00:00";
    private static final String ENERGY_SEND_END_TIME_PM = "20:00:00";
    
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_INT = "yyyyMMdd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final long S = 1000;
    public static final long M = 1000 * 60;
    public static final long H = 1000 * 60 * 60;
    public static final long D = 1000 * 60 * 60 * 24;

    // 时间相关参数
    public  static final long MINUTE_PER_RECOVER = 5;
    private static final long HOURS_PER_DAY = 24;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long SECONDS_PER_MINUTE = 60;
    public static final long MILLIONSECONDS_PER_SECOND = 1000;
    private static final long MILLIONSECONDS_PER_MINUTE = MILLIONSECONDS_PER_SECOND * SECONDS_PER_MINUTE;
    public static final long MILLIONSECONDS_PER_HOUR = MILLIONSECONDS_PER_SECOND * SECONDS_PER_MINUTE
            * MINUTES_PER_HOUR;
    private static final long MILLION_SECOND_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE
            * MILLIONSECONDS_PER_SECOND;
    private static final long MILLION_SECOND_PER_MINUTE = SECONDS_PER_MINUTE
            * MILLIONSECONDS_PER_SECOND;
    public static final long MILLIONSECONDS_PER_RECOVER = MINUTE_PER_RECOVER * MILLION_SECOND_PER_MINUTE;

    private DateUtil() {
    }

    /**
     * 取时间的增量
     */
    public static String getAddDate(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addDay);
        return formatDate(cal.getTime());
    }

    /**
     * 取得该类唯一实例
     * 
     * @return 该类唯实例
     */
    public static DateUtil instance() {
        return instance;
    }

    /**
     * 获得当天的格式化日期。
     * 
     * @return
     */
    public static String getCurrentDateString() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date());
    }

    /**
     * 获得当天的格式化时间。
     * 
     * @return
     */
    public static String getCurrentTimeString() {
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(new Date());
    }
    
    public static int getDateId() {
        return Integer.parseInt(new SimpleDateFormat(DATE_FORMAT_INT).format(new Date()));
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换为日期对象
     * 
     * @param date
     *            待转换字符串
     * @return 转换后日期对象
     * @see #getDate(String, String, Date)
     */
    public static Date getDate(String date) {
        return getDate(date, DEFAULT_DATE_FORMAT, null);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为日期对象
     * 
     * @param date
     *            待转换字符串
     * @return 转换后日期对象
     * @see #getDate(String, String, Date)
     */
    public static Date getDateTime(String date) {
    	if (date == null || date.equals(""))
    		date = CommonUtil.getCurrentTimeStr(DateUtil.DEFAULT_DATETIME_FORMAT);
        return getDate(date, DEFAULT_DATETIME_FORMAT, null);
    }

    /**
     * 将指定格式的字符串转换为日期对象
     * 
     * @param date
     *            待转换字符串
     * @param format
     *            日期格式
     * @return 转换后日期对象
     * @see #getDate(String, String, Date)
     */
    public static Date getDate(String date, String format) {
        return getDate(date, format, null);
    }

    /**
     * 将指定格式的字符串转换为日期对象
     * 
     * @param date
     *            日期对象
     * @param format
     *            日期格式
     * @param defVal
     *            转换失败时的默认返回值
     * @return 转换后的日期对象
     */
    public static Date getDate(String date, String format, Date defVal) {
        if (StringUtil.isEmpty(date) || StringUtil.isEmpty(format))
            return null;
        Date d;
        try {
            d = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
        	log.debug("parse error={}", e);
            d = defVal;
        }
        return d;
    }

    /**
     * 将日期对象格式化成yyyy-MM-dd格式的字符串
     * 
     * @param date
     *            待格式化日期对象
     * @return 格式化后的字符串
     * @see #formatDate(Date, String, String)
     */
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT, null);
    }

    /**
     * 将日期对象格式化成yyyy-MM-dd HH:mm:ss格式的字符串
     * 
     * @param date
     *            待格式化日期对象
     * @return 格式化后的字符串
     * @see #formatDate(Date, String, String)
     */
    public static String forDatetime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT, null);
    }

    /**
     * 将日期对象格式化成HH:mm:ss格式的字符串
     * 
     * @param date
     *            待格式化日期对象
     * @return 格式化后的字符串
     * @see #formatDate(Date, String, String)
     */
    public static String formatTime(Date date) {
        return formatDate(date, DEFAULT_TIME_FORMAT, null);
    }

    /**
     * 将日期对象格式化成指定类型的字符串
     * 
     * @param date
     *            待格式化日期对象
     * @param format
     *            格式化格式
     * @return 格式化后的字符串
     * @see #formatDate(Date, String, String)
     */
    public static String formatDate(Date date, String format) {
        return formatDate(date, format, null);
    }

    /**
     * 将日期对象格式化成指定类型的字符串
     * 
     * @param date
     *            待格式化日期对象
     * @param format
     *            格式化格式
     * @param defVal
     *            格式化失败时的默认返回值
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String format, String defVal) {
        if (date == null || StringUtil.isEmpty(format))
            return defVal;
        String ret;
        try {
            ret = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            ret = defVal;
        }
        return ret;
    }

    /**
     * 获得两个时间之间相差的分钟数(返回值去掉了小数部分，即只返回)。（time1 - time2）
     * 
     * @param time1
     * @param time2
     * @return 返回两个时间之间的分钟数，如果time1晚于time1，则返回正数，否则返回负数或者0
     */
    public static int intervalMinute(long time1, long time2) {
        long intervalMillSecond = time1 - time2;

        // 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用去尾制)
        return (int) (intervalMillSecond / MILLION_SECOND_PER_MINUTE);
    }
    
    /**
     * 获得两个日期之间相差的天数(返回值去掉了小数部分，即只返回)。（date1 - date2）
     * 
     * @param date1
     * @param date2
     * @return 返回两个日期之间的天数差，如果date1晚于date2，则返回正数，否则返回负数或者0
     */
    public static int intervalDays(Date date1, Date date2) {
        long intervalMillSecond = setToDayStartTime(date1).getTime() - setToDayStartTime(date2).getTime();

        // 相差的天数 = 相差的毫秒数 / 每天的毫秒数 (小数位采用去尾制)
        return (int) (intervalMillSecond / MILLION_SECOND_PER_DAY);
    }

    /**
     * 将时间调整到当天的0：0：0
     * 
     * @param date
     * @return
     */
    public static Date setToDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 将时间调整到当天的0：0：0
     * 
     * @param date
     * @return
     */
    public static void updateToDayStartTime(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            date.setTime(calendar.getTimeInMillis());
        }

    }

    public static int intervalHours(long time1, long time2) {
        return (int) ((time2 - time1) / MILLIONSECONDS_PER_HOUR);
    }

    /**
     * 获得两个日期之间相差的小时数。（date1 - date2）
     * 
     * @param date1
     * @param date2
     * @return 返回两个日期之间相差的小时数。
     */
    public static int intervalHours(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();

        // 相差的小时数 = 相差的毫秒数 / 每小时的毫秒数 (抛弃剩余的分钟数)
        return (int) (intervalMillSecond / MILLIONSECONDS_PER_HOUR);
    }

    /**
     * 获得两个日期之间相差的分钟数。（date1 - date2）
     * 
     * @param date1
     * @param date2
     * @return 返回两个日期之间相差的分钟数。
     */
    public static int intervalMinutes(Date date1, Date date2) {
        return intervalMinutes(date1.getTime(), date2.getTime());
    }

    /**
     * 获得两个日期之间相差的分钟数。（timeMillis1 - timeMillis2）
     * 
     * @param timeMillis1
     * @param timeMillis2
     * @return
     */
    public static int intervalMinutes(long timeMillis1, long timeMillis2) {
        long intervalMillSecond = timeMillis1 - timeMillis2;

        // 相差的分钟数 = 相差的毫秒数 / 每分钟的毫秒数 (小数位采用进位制处理，即大于0则加1)
        return (int) (intervalMillSecond / MILLIONSECONDS_PER_MINUTE + (intervalMillSecond % MILLIONSECONDS_PER_MINUTE > 0 ? 1
                : 0));
    }

    /**
     * 获得两个日期之间相差的秒数。（date1 - date2）
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int intervalSeconds(Date date1, Date date2) {
        long intervalMillSecond = date1.getTime() - date2.getTime();

        return (int) (intervalMillSecond / MILLIONSECONDS_PER_SECOND + (intervalMillSecond % MILLIONSECONDS_PER_SECOND > 0 ? 1
                : 0));
    }

    public static int intervalSeconds(long date1, long date2) {
        long intervalMillSecond = date1 - date2;
        return (int) (intervalMillSecond / MILLIONSECONDS_PER_SECOND + (intervalMillSecond % MILLIONSECONDS_PER_SECOND > 0 ? 1
                : 0));
    }

    /**
     * 取得过去的某个云上的日子
     * 
     * @param mark
     *            基准时间点
     * @param interval
     *            离传入时间之前的天数
     * @return
     */
    public static Date getPastDay(Date mark, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(mark);
        c.add(Calendar.DAY_OF_YEAR, -interval);

        return c.getTime();
    }

    /**
     * 取得未来某个绚丽的日子
     * 
     * @param mark
     * @param interval
     * @return
     */
    public static Date getFutureDay(Date mark, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(mark);
        c.add(Calendar.DAY_OF_YEAR, interval);

        return c.getTime();
    }

    /**
     * 取得未来几个小时后的时间
     * 
     * @param mark
     * @param interval
     * @return
     */
    public static Date getFutureHour(Date mark, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(mark);
        c.add(Calendar.HOUR, interval);

        return c.getTime();
    }

    /**
     * 时间检测器
     * 
     * @param date
     *            被检测时间
     * @return
     */
    public static String timeCheck(Date date) {
        String time_desc;
        int num;
        long check = Math.abs(System.currentTimeMillis() - date.getTime());
        if (check < M) {
            num = (int) (check / S);
            time_desc = String.valueOf(num) + "秒";
        } else if (check >= M && check < H) {
            num = (int) (check / M);
            time_desc = String.valueOf(num) + "分钟";
        } else if (check >= H && check < D) {
            num = (int) (check / H);
            time_desc = String.valueOf(num) + "小时";
            num = (int) (check % H / M);
            time_desc += String.valueOf(num) + "分钟";
        } else if (check >= D && check < 8 * D) {
            num = (int) (check / D);
            time_desc = String.valueOf(num) + "天";
        } else {
            time_desc = new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        }

        return time_desc;
    }

    /**
     * 时间检测器哟
     * 
     * @param date
     *            被检测时间
     * @return
     */
    public static String timeDiffCheck(Date date) {
        String time_desc = timeCheck(date);
        long diff = System.currentTimeMillis() - date.getTime();
        if (diff != 0) {
            time_desc += diff > 0 ? "前" : "后";
        }
        return time_desc;
    }

    /**
     * 体力回复时间检测
     * 
     * @param date
     *            上次恢复时间点
     * @return
     */
    public static String energyTiemCheck(int regainTime, Date date) {

        long interval = System.currentTimeMillis() - date.getTime();
        long remain = regainTime * S - interval; // 剩余时间
        return getTimeRemainString(remain);
    }

    public static String getTimeRemainString(long remain) {
        String desc;
        int num;
        if (remain < M) {
            num = (int) (remain / S);
            desc = String.valueOf(num) + "秒后";
        } else if (remain >= M && remain < H) {
            int min = (int) (remain / M);
            desc = String.valueOf(min) + "分";
            long sec = remain % M;
            desc += String.valueOf(sec / S) + "秒后";
        } else {
            int hour = (int) (remain / H);
            desc = String.valueOf(hour) + "小时";
            long min = remain % H;
            desc += String.valueOf(min / M) + "分后";
        }

        return desc;
    }

    /**
     * 根据偏移量计算一个变更后的date时间
     * 
     * @param date
     * @param amount
     * @return
     */
    public static Date changeDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    /**
     * 计算两个日期段相交的秒数
     * 
     * @param s1
     *            第一段日期的开始时间
     * @param e1
     *            第一段日期的结束时间
     * @param s2
     *            第二段日期的开始时间
     * @param e2
     *            第二段日期的结束时间
     * @return
     */
    public static int intersectionSeconds(Date s1, Date e1, Date s2, Date e2) {
        if (s1.after(e1) || s2.after(e2)) {
            throw new IllegalArgumentException("Date s1(s2) must before e1(e2).");
        }

        Date s = s1.before(s2) ? s2 : s1;
        Date e = e1.before(e2) ? e1 : e2;

        return e.before(s) ? 0 : intervalSeconds(e, s);
    }
    
    /**
     * 判断当前是否在活动期间
     * @param start 活动开始时间10位时间戳
     * @param end 活动结束时间10位时间戳
     * @return
     */
    public static boolean isActivity(String start,String end){
    	if(StringUtil.isEmpty(start) || StringUtil.isEmpty(end)){
    		return false;
    	}
    	
    	try{
    		long startT = Long.parseLong(start);
    		long endT = Long.parseLong(end);
    		long now = System.currentTimeMillis() / 1000l;
    		if(now > startT && now < endT){
    			return true;
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static Map<String, Integer> getEnergyTimeMap(long lastSendEnergyDate) {
    	long currentTime = System.currentTimeMillis();
    	int hours = intervalHours(lastSendEnergyDate, currentTime);
    	boolean isGet = false;
    	if (hours < SEND_ENERGY_DELHOURS)
    		isGet = true;
    	Map<String, Integer> timeMap = new HashMap<String, Integer>();
    	int startSeconds = 0;
    	int endSeconds = 0;
    	Date currentDate = getDate(getCurrentTimeString(), DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyStartDateAM = getDate(ENERGY_SEND_START_TIME_AM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyEndDateAM = getDate(ENERGY_SEND_END_TIME_AM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyStartDatePM = getDate(ENERGY_SEND_START_TIME_PM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyEndDatePM = getDate(ENERGY_SEND_END_TIME_PM, DEFAULT_TIME_FORMAT, null);
//    	log.debug("startAM={}, endAm={}", sendEnergyStartDateAM, sendEnergyEndDateAM);
//    	log.debug("startPM={}, endPM={}", sendEnergyStartDatePM, sendEnergyEndDatePM);
//    	log.debug("currentDate={}", currentDate);
    	if (currentDate.after(sendEnergyEndDateAM) && currentDate.before(sendEnergyEndDatePM)) {
    		int addTime = 0;
    		if (currentDate.after(sendEnergyStartDatePM) && hours < SEND_ENERGY_DELHOURS)
    			addTime = 18 * 60 * 60;
    		startSeconds = intervalSeconds(sendEnergyStartDatePM, currentDate) + addTime;
    		endSeconds = intervalSeconds(sendEnergyEndDatePM, currentDate) + addTime;
    	} else {
    		int addTime = 0;
    		if (currentDate.after(sendEnergyStartDateAM) && hours < SEND_ENERGY_DELHOURS)
    			addTime = 6 * 60 * 60;
    		startSeconds = intervalSeconds(sendEnergyStartDateAM, currentDate) + addTime;
    		endSeconds = intervalSeconds(sendEnergyEndDateAM, currentDate) + addTime;
    	}
    	timeMap.put(STARTTIME_STRING, startSeconds);
    	timeMap.put(ENDTIME_STRING, endSeconds);
    	
    	return timeMap;
    }
    
    public static boolean canSendEnergy(long lastSendEnergyDate) {
    	long currentTime = System.currentTimeMillis();
    	int hours = intervalHours(lastSendEnergyDate, currentTime);
    	if (hours < SEND_ENERGY_DELHOURS)
    		return false;
    	
    	Date currentDate = getDate(getCurrentTimeString(), DEFAULT_TIME_FORMAT, null);

    	Date sendEnergyStartDateAM = getDate(ENERGY_SEND_START_TIME_AM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyEndDateAM = getDate(ENERGY_SEND_END_TIME_AM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyStartDatePM = getDate(ENERGY_SEND_START_TIME_PM, DEFAULT_TIME_FORMAT, null);
    	Date sendEnergyEndDatePM = getDate(ENERGY_SEND_END_TIME_PM, DEFAULT_TIME_FORMAT, null);
    	if ((currentDate.after(sendEnergyStartDateAM) && currentDate.before(sendEnergyEndDateAM)) 
    			|| (currentDate.after(sendEnergyStartDatePM) && currentDate.before(sendEnergyEndDatePM))) {
    		return true;
    	} 
    	
    	return false;
    }
}
