
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfCommandType;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface WfCommandTypeDao extends GenericDao<WfCommandType, Integer>{
    List<WfCommandType> listAll(String sort,String dir); 
}
