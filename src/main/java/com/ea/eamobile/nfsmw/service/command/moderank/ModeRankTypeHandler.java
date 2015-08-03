package com.ea.eamobile.nfsmw.service.command.moderank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.rank.TourRecordRankService;

public abstract class ModeRankTypeHandler {

    @Autowired
    protected TournamentUserService tourUserService;
    @Autowired
    protected TourRecordRankService tourRecordRankService;
    /**
     * 取ghost的成绩
     * 
     * @param ghost
     * @return
     */
    public abstract float getGhostResult(GhostInfo ghost);

    public abstract float getRecord(CareerBestRacetimeRecord record);
    
    public abstract float getResult(TournamentUser tournamentUser);
    

    /**
     * 判断用户是否可以上榜是否是新记录是否更快
     * 
     * @param self
     * @param other
     * @return
     */
    public abstract boolean isFaster(float time1, float time2);

    /**
     * 判断是否刷新了记录 record数据分开存储导致需要2个入参判断
     * 
     * @param raceTime
     * @param avgSpeed
     * @param record
     * @return
     */
    public abstract boolean isFaster(float raceTime, float avgSpeed, CareerBestRacetimeRecord record);
    
    
    /**
     * tournament use
     * @param raceTime1
     * @param avgSpeed1
     * @param raceTime2
     * @param avgSpeed2
     * @return
     */
    public abstract boolean isFaster(float raceTime1, float avgSpeed1, float raceTime2, float avgSpeed2);

    /**
     * 比较成绩的结果差
     * 
     * @param lb1
     * @param lb2
     * @return
     */
    public abstract float diffResult(float time1, float time2);

    public abstract int getRank(int onlineId, TournamentUser tourUser);
    
    public abstract void sortRecord(List<CareerBestRacetimeRecord> records);
    


}
