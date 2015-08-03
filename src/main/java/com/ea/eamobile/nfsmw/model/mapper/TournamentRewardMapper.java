package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentReward;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public interface TournamentRewardMapper {

  
  
    public TournamentReward getTournamentRewardByRank(@Param("rank")int rank,@Param("groupId")int groupId);

    public List<TournamentReward> getTournamentRewardList();
   
    
    public List<TournamentReward> getTournamentRewardListByGroupId(int groupId);
    
    public int insert(TournamentReward tournamentReward);

    public void update(TournamentReward tournamentReward);

    public void deleteById(int id);

}