package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ma.ruofei
 * @version 1.0 Tue May 21 10:58:46 CST 2013
 * @since 1.0
 */
public class ProfileTrackLog implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long userId;
    
    private long viewedUserId;
    
    /**
     * pageId:
     * public static final int PAGE_ID_OVERVIEW = 0;
     * public static final int PAGE_ID_NEXT_CAR = 1;
     * public static final int PAGE_ID_COMPARISON = 2;
     */
    private int pageId;
    
    /**
     * sourceId:
     * public static final int SOURCE_ID_RPLEADERBOARD = 0;
     * public static final int SOURCE_ID_TOURNAMENT = 1;
     * public static final int SOURCE_ID_CAREER = 2;
     */
    private int sourceId;
    
    private int tournamentOnlineId;
    
    private int tournamentGroupId;
    
    private String carId;
    
    private java.util.Date time;
    
	
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getViewedUserId() {
        return viewedUserId;
    }
    public void setViewedUserId(long viewedUserId) {
        this.viewedUserId = viewedUserId;
    }
    public int getPageId() {
        return pageId;
    }
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
    public int getSourceId() {
        return sourceId;
    }
    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }
    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
    }
    public int getTournamentGroupId() {
        return tournamentGroupId;
    }
    public void setTournamentGroupId(int tournamentGroupId) {
        this.tournamentGroupId = tournamentGroupId;
    }
    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public java.util.Date getTime() {
        return time != null ? new Date(time.getTime()) : null;
    }
    public void setTime(java.util.Date time) {
        if (time != null) {
            this.time = (Date) time.clone();
        }
    }
}
