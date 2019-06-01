package com.px.admin.dao;

import com.px.admin.entity.Organize;
import java.util.List;

/**
 *
 * @author PRAXiS
 */
public interface OrganizeDao {

    Integer countAll();

    List<Organize> list(int offset, int limit, String sort, String dir);

    List<Organize> listAll(String sort, String dir);
    
}
