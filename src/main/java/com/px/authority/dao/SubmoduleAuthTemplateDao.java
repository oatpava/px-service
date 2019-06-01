package com.px.authority.dao;

import com.px.admin.entity.Submodule;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface SubmoduleAuthTemplateDao extends GenericDao< SubmoduleAuthTemplate, Integer>{
    List<SubmoduleAuthTemplate> list(int offset,int limit,String sort,String dir);
    List<SubmoduleAuthTemplate> listAll(String sort,String dir);
    List<SubmoduleAuthTemplate> listBySubmodule(Submodule submodule,String sort, String dir);
    Integer countAll(); 
}
