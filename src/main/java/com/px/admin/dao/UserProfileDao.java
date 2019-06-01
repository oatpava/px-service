package com.px.admin.dao;

import com.px.admin.entity.UserProfile;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface UserProfileDao extends GenericDao<UserProfile, Integer>{
    List<UserProfile> list(int offset,int limit,String sort,String dir);
    List<UserProfile> listAll(String sort,String dir);    
    Integer countAll();
}
