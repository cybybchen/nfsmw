package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.bean.PropBean;

public interface PropMapper {

    public PropBean queryById(int id);

    public List<PropBean> getProps();

}
