package com.ea.eamobile.nfsmw.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.mapper.RpLevelMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Oct 17 15:58:53 CST 2012
 * @since 1.0
 */
@Service
public class RpLevelService {

    @Autowired
    private RpLevelMapper rpLevelMapper;

    public int getLevelByRpNum(int rpNum) {
        int level = 1;
        Map<Integer, RpLevel> map = getLevelMap();
        for (Map.Entry<Integer, RpLevel> entry : map.entrySet()) {
            RpLevel rp = entry.getValue();
            // 注意必须是treemap
            if (rp.getRpNum() > rpNum) {
                break;
            }
            level = entry.getKey();
        }
        return level;
    }

    public List<RpLevel> getLevelList() {
        @SuppressWarnings("unchecked")
        List<RpLevel> ret = (List<RpLevel>) InProcessCache.getInstance().get("rp_level_level_list");
        if (ret != null) {
            return ret;
        }
        ret = rpLevelMapper.getLevelList();
        InProcessCache.getInstance().set("rp_level_level_list", ret);
        return ret;
    }

    public Map<Integer, RpLevel> getLevelMap() {
        Map<Integer, RpLevel> map = Collections.emptyMap();
        List<RpLevel> list = getLevelList();
        if (list != null && list.size() > 0) {
            map = new TreeMap<Integer, RpLevel>();
            for (RpLevel level : list) {
                map.put(level.getLevel(), level);
            }
        }
        return map;
    }

    public RpLevel getLevel(int level) {
        return getLevelMap().get(level);
    }

}