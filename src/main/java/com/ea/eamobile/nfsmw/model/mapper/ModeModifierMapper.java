package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.ModeModifier;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 03 11:37:44 CST 2013
 * @since 1.0
 */
public interface ModeModifierMapper {

    public ModeModifier getModeModifier(int modeId);

    public List<ModeModifier> getModeModifierList();

    public int insert(ModeModifier modeModifier);

    public void update(ModeModifier modeModifier);

    public void deleteById(int id);

}