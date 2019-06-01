package com.px.admin.dao;

import com.px.admin.entity.Holiday;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface HolidayDao extends GenericDao<Holiday, Integer>{
    List<Holiday> list(int offset,int limit,String sort,String dir);
    List<Holiday> listAll(String sort,String dir);    
    Integer countAll();
}
