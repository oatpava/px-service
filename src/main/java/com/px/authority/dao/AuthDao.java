package com.px.authority.dao;

import com.px.authority.entity.Auth;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface AuthDao extends GenericDao< Auth, Integer>{
    List<Auth> list(int offset,int limit,String sort,String dir);
    List<Auth> listAll(String sort,String dir);    
    Integer countAll(); 
}
