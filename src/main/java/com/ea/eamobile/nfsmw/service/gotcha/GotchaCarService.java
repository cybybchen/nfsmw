package com.ea.eamobile.nfsmw.service.gotcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.mapper.GotchaCarMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:45 CST 2013
 * @since 1.0
 */
@Service
public class GotchaCarService {

    @Autowired
    private GotchaCarMapper gotchaCarMapper;

    public GotchaCar getGotchaCar(String carId) {
        GotchaCar gcar = (GotchaCar) InProcessCache.getInstance().get("getGotchaCar" + carId);
        if (gcar == null) {
            gcar = gotchaCarMapper.getGotchaCar(carId);
            InProcessCache.getInstance().set("getGotchaCar" + carId, gcar);
        }
        return gcar;
    }

    public GotchaCar getGotchaCarById(int id) {
        GotchaCar gcar = (GotchaCar) InProcessCache.getInstance().get("getGotchaCarById" + id);
        if (gcar == null) {
            gcar = gotchaCarMapper.getGotchaCarById(id);
            InProcessCache.getInstance().set("getGotchaCarById" + id, gcar);
        }
        return gcar;
    }

}