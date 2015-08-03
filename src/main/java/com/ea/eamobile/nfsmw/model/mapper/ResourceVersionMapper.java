package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.ResourceVersion;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Nov 07 16:33:25 CST 2012
 * @since 1.0
 */
public interface ResourceVersionMapper {

    public ResourceVersion getResourceVersion(int id);
    
    public List<ResourceVersion> getResourceVersionList();

    public void deleteById(int id);

    public int insert(ResourceVersion resourceVersion);

    public int update(ResourceVersion resourceVersion);

}