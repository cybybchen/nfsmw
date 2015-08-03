package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.ProfileComparison;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 06 16:32:07 CST 2013
 * @since 1.0
 */
public interface ProfileComparisonMapper {

    public ProfileComparison getProfileComparison(int id);
    
    public ProfileComparison getProfileComparisonByOrderIndex(int orderIndex);

    public List<ProfileComparison> getProfileComparisonList();

    public int insert(ProfileComparison profileComparison);

    public void update(ProfileComparison profileComparison);

    public void deleteById(int id);

}