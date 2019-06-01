package com.px.admin.dao;

import com.px.admin.entity.User;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface UserDao extends GenericDao<User, Integer>{
    List<User> list(int offset,int limit,String sort,String dir);
    List<User> listAll(String sort,String dir);    
    Integer countAll();
}
