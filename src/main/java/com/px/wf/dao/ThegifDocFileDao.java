
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.ThegifDocFile;
import java.util.List;

/**
 *
 * @author TUMKUNG
 */
public interface ThegifDocFileDao extends GenericDao<ThegifDocFile, Integer>{
    List<ThegifDocFile> listByThegifId(int thegifId,String sort,String dir);
}
