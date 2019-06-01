package com.px.admin.dao;

import com.px.admin.entity.UserStatus;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface UserStatusDao extends GenericDao<UserStatus, Integer>{
    List<UserStatus> list(int offset,int limit,String sort,String dir);
    List<UserStatus> listAll(String sort,String dir);    
    Integer countAll();
}
