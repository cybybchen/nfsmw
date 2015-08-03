package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 17:21:02 CST 2013
 * @since 1.0
 */
public class TournamentGroupClass implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    
    private int groupId;
    
    private int userCount;
    
    private int tournamentOnlineId;
    
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public int getUserCount() {
        return userCount;
    }
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
    public int getTournamentOnlineId() {
        return tournamentOnlineId;
    }
    public void setTournamentOnlineId(int tournamentOnlineId) {
        this.tournamentOnlineId = tournamentOnlineId;
    }
}
