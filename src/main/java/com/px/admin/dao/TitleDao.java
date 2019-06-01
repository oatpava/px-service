package com.px.admin.dao;

import com.px.admin.entity.Title;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface TitleDao extends GenericDao<Title, Integer>{
    List<Title> list(int offset,int limit,String sort,String dir);
    List<Title> listAll(String sort,String dir);    
    Integer countAll();
    
}
