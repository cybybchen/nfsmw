package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserGhost;
import com.ea.eamobile.nfsmw.model.mapper.UserGhostMapper;

@Service
public class UserGhostService {
    @Autowired
    private UserGhostMapper ghostMapper;

    public int insertGhost(UserGhost ghost) {
        return ghostMapper.insert(ghost);
    }

    public void updateGhost(UserGhost ghost) {
        ghostMapper.update(ghost);
    }

    public UserGhost getByUserIdAndModeId(long userId, int modeId) {
        return ghostMapper.getByUserIdAndModeId(userId, modeId);
    }

    public List<UserGhost> getGhostList(int from,int limit) {
        return ghostMapper.getGhostList(from,limit);
    }

    @Deprecated
    public long getMaxGhostId() {
        return ghostMapper.getMaxGhostId();
    }

    @Deprecated
    public List<UserGhost> getGhostListById(long id) {
        return ghostMapper.getGhostListById(id);
    }

}
