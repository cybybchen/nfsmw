package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.bean.PropBean;
import com.ea.eamobile.nfsmw.model.mapper.PropMapper;
import com.ea.eamobile.nfsmw.service.redis.PropRedisService;

@Service
public class PropService {

	@Autowired
	private PropRedisService propRedisService;
    @Autowired
    private PropMapper propMapper;

    public PropBean queryProp(int id) {
    	PropBean ret = propRedisService.getPropById(id);
        if (ret != null) {
            return ret;
        }
        
        parseAndSavePropList();
        ret = propRedisService.getPropById(id);
        return ret;
    }

    public List<PropBean> getProps() {
        List<PropBean> ret = propRedisService.getPropList();
        if (ret != null) {
            return ret;
        }
        ret = parseAndSavePropList();
        return ret;
    }

    private List<PropBean> parseAndSavePropList() {
    	List<PropBean> propList = propMapper.getProps();
    	propRedisService.setPropList(propList);
        
        return propList;
    }
    
}
