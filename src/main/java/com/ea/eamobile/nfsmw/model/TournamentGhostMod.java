package com.ea.eamobile.nfsmw.model;

import java.io.Serializable;

import com.ea.eamobile.nfsmw.view.BaseGhostMod;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
public class TournamentGhostMod extends BaseGhostMod implements Serializable{

    private static final long serialVersionUID = 1L;

    private long id;
    
    private long tournamentGhostId;
    
    private int modeType;
    
    private float modeValue;
    
    private int modeLevel;
    
    private int modeId;
    
	
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getTournamentGhostId() {
        return tournamentGhostId;
    }
    public void setTournamentGhostId(long tournamentGhostId) {
        this.tournamentGhostId = tournamentGhostId;
    }
    public int getModeType() {
        return modeType;
    }
    public void setModeType(int modeType) {
        this.modeType = modeType;
    }
    public float getModeValue() {
        return modeValue;
    }
    public void setModeValue(float modeValue) {
        this.modeValue = modeValue;
    }
    public int getModeLevel() {
        return modeLevel;
    }
    public void setModeLevel(int modeLevel) {
        this.modeLevel = modeLevel;
    }
    public int getModeId() {
        return modeId;
    }
    public void setModeId(int modeId) {
        this.modeId = modeId;
    }
}
