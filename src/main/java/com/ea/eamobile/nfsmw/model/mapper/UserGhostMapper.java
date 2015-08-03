package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserGhost;

public interface UserGhostMapper {

    public int insert(UserGhost ghost);

    public void update(UserGhost ghost);

    public UserGhost getByUserIdAndModeId(@Param("userId") long userId, @Param("modeId") int modeId);
    
    public List<UserGhost> getGhostListByModeIdAndNum( long userId,  int modeId,int num);
    
    public List<UserGhost> getGhostListByModeIdAndEol( @Param("modeId") int modeId ,@Param("eol") int eol);
    
    public List<UserGhost> getGhostList(@Param("from") int from,@Param("limit") int limit);
    
    public List<UserGhost> getGhostListByModeIdEolNum( @Param("userId") long userId, @Param("modeId") int modeId,@Param("eol")int eol,@Param("num")int num);
    
    public List<UserGhost> queryOpponentUserListByUseridAndEolAndFloatValue( long userId, int eol, int floatValue,int modeId,int num);

    public List<Integer> getModeIdList();
    
    public Integer getMaxEolByModeId(int modeId);
    
    public long getMaxGhostId();
    
    public List<UserGhost> getGhostListById(Long id);
}
