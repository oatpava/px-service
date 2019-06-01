package com.px.admin.dao;

import com.px.admin.entity.Lookup;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface LookupDao extends GenericDao<Lookup, Integer>{
    List<Lookup> list(int offset,int limit,String sort,String dir);
    List<Lookup> listAll(String sort,String dir);    
    Integer countAll();
}
