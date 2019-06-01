package com.px.admin.dao;

import com.px.admin.entity.PositionType;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface PositionTypeDao extends GenericDao<PositionType, Integer>{
    List<PositionType> list(int offset,int limit,String sort,String dir);
    List<PositionType> listAll(String sort,String dir);    
    Integer countAll();
}
