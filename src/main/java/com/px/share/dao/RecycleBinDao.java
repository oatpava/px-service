package com.px.share.dao;

import com.px.share.entity.RecycleBin;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface RecycleBinDao extends GenericDao<RecycleBin, Integer>{
    List<RecycleBin> list(int offset,int limit,String sort,String dir);
    List<RecycleBin> listAll(String sort,String dir);    
    Integer countAll();
    
}
