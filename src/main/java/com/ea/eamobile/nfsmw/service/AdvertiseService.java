package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.Advertise;
import com.ea.eamobile.nfsmw.model.mapper.AdvertiseMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Dec 13 14:34:54 CST 2012
 * @since 1.0
 */
@Service
public class AdvertiseService {

    @Autowired
    private AdvertiseMapper advertiseMapper;

    public Advertise getAdvertise(int id) {
        Advertise ret = (Advertise) InProcessCache.getInstance().get("getAdvertise."+id);
        if (ret != null) {
            return ret;
        }
        ret = advertiseMapper.getAdvertise(id);
        InProcessCache.getInstance().set("getAdvertise."+id, ret);
        return ret;
    }

    public List<Advertise> getAdvertiseList() {
        @SuppressWarnings("unchecked")
        List<Advertise> ret = (List<Advertise>) InProcessCache.getInstance().get("getAdvertiseList");
        if (ret != null) {
            return ret;
        }
        ret = advertiseMapper.getAdvertiseList();
        InProcessCache.getInstance().set("getAdvertiseList", ret);
        return ret;
    }

    public int insert(Advertise advertise) {
        return advertiseMapper.insert(advertise);
    }

    public void update(Advertise advertise) {
        advertiseMapper.update(advertise);
    }

    public void deleteById(int id) {
        advertiseMapper.deleteById(id);
    }

}