package com.px.admin.dao;

import com.px.admin.entity.UserProfileType;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface UserProfileTypeDao extends GenericDao<UserProfileType, Integer>{
    List<UserProfileType> list(int offset,int limit,String sort,String dir);
    List<UserProfileType> listAll(String sort,String dir);    
    Integer countAll();
}
