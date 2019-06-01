package com.px.share.dao;

import com.px.share.entity.Month;
import java.util.List;

/**
 *
 * @author Opas
 */
public interface MonthDao extends GenericDao< Month, Integer>{
    List<Month> list(int offset,int limit,String sort,String dir);
    List<Month> listAll(String sort,String dir);    
    Integer countAll(); 
}
