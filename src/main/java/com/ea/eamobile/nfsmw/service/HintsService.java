package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Hints;
import com.ea.eamobile.nfsmw.model.mapper.HintsMapper;
import com.ea.eamobile.nfsmw.utils.NumberUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Jan 16 19:35:38 CST 2013
 * @since 1.0
 */
@Service
public class HintsService {

    @Autowired
    private HintsMapper hintsMapper;

  

    public List<Hints> getHintsList() {
        @SuppressWarnings("unchecked")
        List<Hints> ret = (List<Hints>) InProcessCache.getInstance().get("hints_list");
        if (ret != null) {
            return ret;
        }
        ret = hintsMapper.getHintsList();
        InProcessCache.getInstance().set("hints_list", ret);
        return ret;
    }

    public int insert(Hints hints) {
        return hintsMapper.insert(hints);
    }

    public void update(Hints hints) {
        hintsMapper.update(hints);
    }

    public void deleteById(int id) {
        hintsMapper.deleteById(id);
    }

    public String getRandomHint() {
        String result = "";
        List<Hints> hints = getHintsList();
        Hints hint = NumberUtil.randomList(hints);
        if (hint != null) {
            result = hint.getContent();
        }
        return result;
    }

    public String getJaguarRandomHint() {
        String result = "";
        List<Hints> hints = getHintsList();
        if (hints != null && hints.size() >= Const.JAGUAR_HINT_SIZE) {
            Hints hint = hints.get(NumberUtil.randomNumber(Const.JAGUAR_HINT_SIZE));
            result = hint.getContent();
        }
        return result;
    }

}