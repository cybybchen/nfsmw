package com.ea.eamobile.nfsmw.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 17 15:42:35 CST 2012
 * @since 1.0
 */
@Service
public class ResponseLogService {

    private static final Logger logger = LoggerFactory.getLogger("ResponseLog");

    public int insert(long userId, String requestName, String param) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String time = sdf.format(new Date(System.currentTimeMillis()));
        logger.info(userId + "\t" + requestName + "\t" + time + "\t" + param.replaceAll("(\\r|\\n)", ""));
        return 1;// requestLogMapper.insert(requestLog);
    }

}