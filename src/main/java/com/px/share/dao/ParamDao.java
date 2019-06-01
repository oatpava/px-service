package com.px.share.dao;

import com.px.share.entity.Param;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface ParamDao extends GenericDao<Param, Integer>{
    Param getByParamName(String paramName);
    List<Param> list(int offset,int limit,String sort,String dir);
    List<Param> listAll(String sort,String dir);    
    Integer countAll();
}
