package com.ea.eamobile.nfsmw.service.command.racetype;

import java.util.List;

import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.view.BaseGhost;

public interface RaceTypeHandler {
    
    /**
     * career mode调用
     * @param user
     * @param mode
     * @return
     */
    List<Reward> getRewards(User user, RaceMode mode);
    
    /**
     * tournament mode 调用
     * @param user
     * @return
     */
    List<Reward> getRewards(User user);
    
    List<? extends BaseGhost> getCareerGhosts(User user,RaceMode raceMode);
    
    List<? extends BaseGhost> getTournamentGhosts(User user,int onlineId);
}
