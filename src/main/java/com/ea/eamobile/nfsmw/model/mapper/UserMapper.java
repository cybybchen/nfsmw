package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import java.util.Map;

import com.ea.eamobile.nfsmw.model.User;

public interface UserMapper {

    public User queryById(long userId);

    public User getUserByIdentityId(String identityId);
    
    public User getUserByWillowtreeToken(String willowtreeToken);
    
    public User getUserByName(String name);

    public Integer insert(User user);

    public void update(User user);

    public void deleteById(long id);

    public int queryMaxEol();

    public List<User> queryByEol(int eol);
    
    public void updateConsumeEnergy(long userId,int reduceEnergyNum);
    
    public void updateRpNum(long userId,int reduceEnergyNum);
    
    public void updateStarNum(long userId,int addStarNum);
    
    public void updateTier(long userId,int tier);
    
    public void updateReward(long userid,int gainRpNum,int gainStarNum,int money,int gold,int energy);

    public Integer getResouceVersionUserCount(int version);
    
    public List<User> getUsersByIds(Long[] ids);
    
    public List<User> getUsersByTokens(String[] tokens);
    
    public int getNickNameCount(String nickname);
    
    public List<User> getTopHundredUser();
    
    public Integer getRpRank(int rpNum);
    
    public void updateStatusByIds(Map<String,Object> params);
    
}
