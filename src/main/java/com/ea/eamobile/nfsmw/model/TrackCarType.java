package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

public class TrackCarType implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String trackId;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    private String carId;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
