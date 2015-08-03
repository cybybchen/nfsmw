package com.ea.eamobile.nfsmw.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class DateToRight {

    public static long getTargetTimes1(){
        Calendar now=Calendar.getInstance();
        long targetTime=now.getTimeInMillis()+TimeZone.getDefault().getRawOffset();
        return targetTime;
    }
}
