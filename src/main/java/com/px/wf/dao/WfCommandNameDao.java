
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfCommandName;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface WfCommandNameDao extends GenericDao<WfCommandName, Integer>{
    List<WfCommandName> listAll(String sort,String dir); 
    
}
