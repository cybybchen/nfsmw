package com.ea.eamobile.nfsmw.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;

/**
 * 设备判断相关
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class DeviceService {

    public int getDeviceType(String deviceName) {
        if (StringUtils.isNotBlank(deviceName)) {
            deviceName = deviceName.toLowerCase();
            if (deviceName.contains("ipad")) {
                return Const.DEVICE_IS_IPAD;
            } else if (deviceName.contains("iphone5") || deviceName.contains("ipod5") || deviceName.contains("iphone6")) {
                return Const.DEVICE_IS_IPHONE5;
            } /*else if (deviceName.contains("iphone7,1")) {
            	return Const.DEVICE_IS_IPHONE6_PLUS;
            } else if (deviceName.contains("iphone7,2")) {
            	return Const.DEVICE_IS_IPHONE6;
            } else {
                return Const.DEVICE_IS_IPHONE;
            }*/
            else {
                return Const.DEVICE_IS_IPHONE;
            }
        }
        return Const.DEVICE_IS_IPAD;
    }
}
