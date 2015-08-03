package com.ea.eamobile.nfsmw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.RequestLog;
import com.ea.eamobile.nfsmw.model.mapper.RequestLogMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 17 15:42:35 CST 2012
 * @since 1.0
 */
@Service
public class RequestLogService {

    private static final Logger logger = LoggerFactory.getLogger("RequestLog");
    @Autowired
    private RequestLogMapper requestLogMapper;

    public int insert(RequestLog requestLog) {
        logger.info(requestLog.getUserId() + "\t" + requestLog.getRequestName() + "\t" + requestLog.getCreateTime()
                + "\t" + requestLog.getParam().replaceAll("(\\r|\\n)", ""));
        return 1;// requestLogMapper.insert(requestLog);
    }

}