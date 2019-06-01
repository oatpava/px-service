package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfField;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Mali
 */
public interface WfFieldDao extends GenericDao<WfField, Integer> {
    List<WfField> listAll(String sort,String dir);    
    Integer countAll(); 
}
