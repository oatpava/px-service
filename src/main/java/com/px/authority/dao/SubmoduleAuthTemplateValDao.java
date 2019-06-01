package com.px.authority.dao;

import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleAuthTemplateVal;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface SubmoduleAuthTemplateValDao extends GenericDao< SubmoduleAuthTemplateVal, Integer>{
    List<SubmoduleAuthTemplateVal> list(int offset,int limit,String sort,String dir);
    List<SubmoduleAuthTemplateVal> listAll(String sort,String dir);
    List<SubmoduleAuthTemplateVal> listBySubmoduleAuthTemplate(SubmoduleAuthTemplate submoduleAuthTemplate,String sort, String dir);
    Integer countAll(); 
}
