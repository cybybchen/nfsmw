package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.Resource;
import com.ea.eamobile.nfsmw.model.mapper.ResourceMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private MemcachedClient cache;
    
    /**
     * 取全部的下载文件列表 TODO cache
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Resource> getResourceList() {
        List<Resource> list = (List<Resource>) cache.get(CacheKey.RESOURCE);
        if(list==null){
            list = resourceMapper.getResourceList();
            cache.set(CacheKey.RESOURCE, list);
        }
        return list;
    }

    public Resource getResource(int id) {
        return resourceMapper.getResource(id);
    }

    public void update(Resource resource){
        clear();
        resourceMapper.update(resource);
    }
    
    public void clear(){
        cache.delete(CacheKey.RESOURCE);
    }
    
    /**
     * admin use
     * 
     * @param version
     * @return
     */
    public List<Resource> getResourceListByVersion(int version) {
        List<Resource> list = getResourceList();
        List<Resource> result = new ArrayList<Resource>();
        for (Resource res : list) {
            if (res.getVersion() == version) {
                result.add(res);
            }
        }
        return result;
    }

    public void delete(Integer id) {
        clear();
        resourceMapper.delete(id);
    }

    public void copy(int targetVersion, int currVersion) {
        List<Resource> targetList =  getResourceListByVersion(targetVersion);
        if(targetList==null || targetList.size()==0){
            return;
        }
//        List<Resource> list = new ArrayList<Resource>();
        for(Resource res : targetList){
            res.setVersion(currVersion);
        }
        clear();
        resourceMapper.insertBatch(targetList);
    }

}
