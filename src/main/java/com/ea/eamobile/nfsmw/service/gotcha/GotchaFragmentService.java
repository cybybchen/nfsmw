package com.ea.eamobile.nfsmw.service.gotcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.GotchaFragment;
import com.ea.eamobile.nfsmw.model.mapper.GotchaFragmentMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:00:24 CST 2013
 * @since 1.0
 */
@Service
public class GotchaFragmentService {

    @Autowired
    private GotchaFragmentMapper gotchaPartMapper;

    public GotchaFragment getGotchaPart(int id) {
        GotchaFragment part = (GotchaFragment) InProcessCache.getInstance().get("getGotchaPart." + id);
        if (part == null) {
            part = gotchaPartMapper.getGotchaPart(id);
            InProcessCache.getInstance().set("getGotchaPart." + id, part);
        }
        return part;
    }

}