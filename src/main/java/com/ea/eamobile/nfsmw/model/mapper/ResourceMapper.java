package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Resource;

public interface ResourceMapper {

    public List<Resource> queryResourceListByVersion(int version);

    public List<Resource> getResourceList();

    public Resource getResource(int id);

    public void update(Resource resource);

    public void delete(int id);

    public void insertBatch(List<Resource> list);

}
