package com.px.admin.dao;

import com.px.admin.entity.Ad;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface AdDao extends GenericDao<Ad, Integer>{
    List<Ad> list(int offset,int limit,String sort,String dir);
    List<Ad> listAll(String sort,String dir);    
    Integer countAll();
}
