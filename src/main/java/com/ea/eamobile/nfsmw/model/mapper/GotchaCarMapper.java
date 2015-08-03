package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GotchaCar;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:45 CST 2013
 * @since 1.0
 */
public interface GotchaCarMapper {

    public GotchaCar getGotchaCar(String carId);
    
    public GotchaCar getGotchaCarById(int id);

    public List<GotchaCar> getGotchaCarList();

}