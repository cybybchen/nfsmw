package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorSpeedCareerBestRacetimeRecord implements Comparator<CareerBestRacetimeRecord> ,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public int compare(CareerBestRacetimeRecord o1, CareerBestRacetimeRecord o2) {
        CareerBestRacetimeRecord tu0 = (CareerBestRacetimeRecord) o1;
        CareerBestRacetimeRecord tu1 = (CareerBestRacetimeRecord) o2;
        int flag = 0;
        if (tu0.getAverageSpeed() < tu1.getAverageSpeed()) {
            flag = 1;
        }
        if (tu0.getAverageSpeed() > tu1.getAverageSpeed()) {
            flag = -1;
        }
        if (flag == 0) {
            return (tu0.getUserId() + "").compareTo(tu1.getUserId() + "");
        } else {
            return flag;
        }
    }

}
