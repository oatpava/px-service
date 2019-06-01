package com.px.admin.dao;

import com.px.admin.entity.Module;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface ModuleDao extends GenericDao< Module, Integer>{
    List<Module> list(int offset,int limit,String sort,String dir);
    List<Module> listAll(String sort,String dir);    
    Integer countAll();
    Module getByModuleCode(String moduleCode);
}
