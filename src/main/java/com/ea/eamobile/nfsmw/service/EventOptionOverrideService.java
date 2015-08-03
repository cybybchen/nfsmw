package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.EventOptionOverride;
import com.ea.eamobile.nfsmw.model.mapper.EventOptionOverrideMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 03 10:56:22 CST 2012
 * @since 1.0
 */
@Service
public class EventOptionOverrideService {

    @Autowired
    private EventOptionOverrideMapper eventOptionOverrideMapper;

    public List<EventOptionOverride> getEventOptionOverrideList() {
        @SuppressWarnings("unchecked")
        List<EventOptionOverride> ret = (List<EventOptionOverride>) InProcessCache.getInstance().get("getEventOptionOverrideList");
        if (ret != null) {
            return ret;
        }
        ret = eventOptionOverrideMapper.getEventOptionOverrideList();
        InProcessCache.getInstance().set("getEventOptionOverrideList", ret);
        return ret;
    }

    public Map<Integer, List<EventOptionOverride>> getEventOptionOverridesMap() {
        Map<Integer, List<EventOptionOverride>> map = Collections.emptyMap();
        List<EventOptionOverride> list = getEventOptionOverrideList();
        if (list == null) {
            return map;
        }
        map = new HashMap<Integer, List<EventOptionOverride>>();
        for (EventOptionOverride option : list) {
            int modeId = option.getModeId();
            List<EventOptionOverride> tempList = map.get(modeId);
            if (tempList == null) {
                tempList = new ArrayList<EventOptionOverride>();
                map.put(modeId, tempList);
            }
            tempList.add(option);
        }
        return map;
    }

    /**
     * get children list<fatherid,option>
     * 
     * @return
     */

    public Map<Integer, List<EventOptionOverride>> getEventOptionOverrideMapByFather() {
        Map<Integer, List<EventOptionOverride>> map = Collections.emptyMap();
        List<EventOptionOverride> list = getEventOptionOverrideList();
        if (list != null) {
            map = new HashMap<Integer, List<EventOptionOverride>>();
            for (EventOptionOverride option : list) {
                List<EventOptionOverride> temp = map.get(option.getFatherId());
                if (temp == null) {
                    temp = new ArrayList<EventOptionOverride>();
                    map.put(option.getFatherId(), temp);
                }
                temp.add(option);
            }
        }
        return map;
    }

    public void clearCache() {
    }

    public int insert(EventOptionOverride eventOptionOverride) {
        return eventOptionOverrideMapper.insert(eventOptionOverride);
    }

    public void update(EventOptionOverride eventOptionOverride) {
        eventOptionOverrideMapper.update(eventOptionOverride);
    }

    public void deleteById(int id) {
        eventOptionOverrideMapper.deleteById(id);
    }

}