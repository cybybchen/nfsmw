package com.ea.eamobile.nfsmw.view;

import com.ea.eamobile.nfsmw.constants.Const;

public class DeviceCount {

    private int device;

    private int count;

    private String deviceName;

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDeviceName() {
        switch (device) {
        case Const.DEVICE_IS_IPAD:
            deviceName = "ipad";
            break;
        case Const.DEVICE_IS_IPHONE:
            deviceName = "iphone4";
            break;
        case Const.DEVICE_IS_IPHONE5:
            deviceName = "iphone5";
            break;
        default:
            break;
        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}
