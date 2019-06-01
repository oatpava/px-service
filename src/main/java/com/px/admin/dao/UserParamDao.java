package com.px.admin.dao;

import com.px.admin.entity.UserParam;
import java.util.List;

/**
 *
 * @author Pritsana
 */
public interface UserParamDao {

    Integer countAll();

    List<UserParam> list(int offset, int limit, String sort, String dir);

    List<UserParam> listAll(String sort, String dir);
    
    UserParam getByParamName(String paramName,int id);
    
}
