package com.px.admin.dao;

import com.px.admin.entity.Province;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface ProvinceDao extends GenericDao<Province, Integer>{
    List<Province> list(int offset,int limit,String sort,String dir);
    List<Province> listAll(String sort,String dir);    
    Integer countAll();
}
