package com.px.admin.dao;

import com.px.admin.entity.Submodule;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface SubmoduleDao extends GenericDao< Submodule, Integer>{
    List<Submodule> list(int offset,int limit,String sort,String dir);
    List<Submodule> listAll(String sort,String dir);    
    Integer countAll(); 
    Submodule getBySubmoduleCode(String submoduleCode);
}
