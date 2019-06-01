
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfContentType;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Mali
 */
public interface WfContentTypeDao extends GenericDao<WfContentType, Integer>{
    List<WfContentType> listAll(String sort,String dir);   
}
