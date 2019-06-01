package com.px.admin.dao;

import com.px.admin.entity.Structure;
import com.px.share.dao.GenericTreeDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface StructureDao extends GenericTreeDao<Structure, Integer>{
    List<Structure> list(int offset,int limit,String sort,String dir);
    List<Structure> listAll(String sort,String dir);    
    Integer countAll();
}
