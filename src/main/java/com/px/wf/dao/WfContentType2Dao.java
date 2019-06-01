
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfContentType2;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Mali
 */
public interface WfContentType2Dao extends GenericDao<WfContentType2, Integer>{
     List<WfContentType2> listByWfContentType(List<Integer> wfContentType,String sort,String dir); 
     List<WfContentType2> listAll(String sort,String dir);   
}
