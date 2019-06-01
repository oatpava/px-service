package com.px.admin.dao;

import com.px.admin.entity.LookupDetail;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface LookupDetailDao extends GenericDao<LookupDetail, Integer>{
    List<LookupDetail> list(int offset,int limit,String sort,String dir);
    List<LookupDetail> listAll(String sort,String dir);    
    Integer countAll();
}
