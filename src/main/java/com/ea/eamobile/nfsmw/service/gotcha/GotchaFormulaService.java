package com.ea.eamobile.nfsmw.service.gotcha;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.GotchaFormula;
import com.ea.eamobile.nfsmw.model.mapper.GotchaFormulaMapper;


/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:03:31 CST 2013
 * @since 1.0
 */
@Service
public class GotchaFormulaService {

    private static final int CAR_PARAM_A = 1;
    private static final int CAR_PARAM_B = 2;
    private static final int PART_PARAM_A = 3;
    private static final int PART_PARAM_B = 4;
    private static final int PART_PARAM_C = 5;
    @Autowired
    private GotchaFormulaMapper gotchaFormulaMapper;

    public GotchaFormula getGotchaFormula(int id) {
        GotchaFormula gf = (GotchaFormula) InProcessCache.getInstance().get("getGotchaFormula." + id);
        if (gf == null) {
            gf = gotchaFormulaMapper.getGotchaFormula(id);
            InProcessCache.getInstance().set("getGotchaFormula." + id, gf);
        }
        return gf;
    }

    public List<GotchaFormula> getGotchaFormulaList() {
        return gotchaFormulaMapper.getGotchaFormulaList();
    }

    public int insert(GotchaFormula gotchaFormula) {
        return gotchaFormulaMapper.insert(gotchaFormula);
    }

    public void update(GotchaFormula gotchaFormula) {
        gotchaFormulaMapper.update(gotchaFormula);
    }

    public void deleteById(int id) {
        gotchaFormulaMapper.deleteById(id);
    }

    public double getCarParamA() {
        GotchaFormula gf = getGotchaFormula(CAR_PARAM_A);
        return gf != null ? gf.getValue() : 0;
    }

    public double getCarParamB() {
        GotchaFormula gf = getGotchaFormula(CAR_PARAM_B);
        return gf != null ? gf.getValue() : 0;
    }

    public double getPartParamA() {
        GotchaFormula gf = getGotchaFormula(PART_PARAM_A);
        return gf != null ? gf.getValue() : 0;
    }

    public double getPartParamB() {
        GotchaFormula gf = getGotchaFormula(PART_PARAM_B);
        return gf != null ? gf.getValue() : 0;
    }

    public double getPartParamC() {
        GotchaFormula gf = getGotchaFormula(PART_PARAM_C);
        return gf != null ? gf.getValue() : 0;
    }

}