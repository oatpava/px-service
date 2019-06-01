package com.px.admin.dao;

import com.px.admin.entity.UserTypeOrder;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface UserTypeOrderDao extends GenericDao<UserTypeOrder, Integer>{
    List<UserTypeOrder> list(int offset,int limit,String sort,String dir);
    List<UserTypeOrder> listAll(String sort,String dir);    
    Integer countAll();
}
