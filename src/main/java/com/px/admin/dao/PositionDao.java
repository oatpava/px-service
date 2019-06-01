package com.px.admin.dao;

import com.px.admin.entity.Position;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface PositionDao extends GenericDao<Position, Integer>{
    List<Position> list(int offset,int limit,String sort,String dir);
    List<Position> listAll(String sort,String dir);    
    Integer countAll();
}
