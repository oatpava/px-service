package com.px.admin.dao;

import com.px.admin.entity.ModuleConfig;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface ModuleConfigDao extends GenericDao<ModuleConfig, Integer>{
    List<ModuleConfig> list(int offset,int limit,String sort,String dir);
    List<ModuleConfig> listAll(String sort,String dir);    
    Integer countAll();
}
