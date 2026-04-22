package com.px.admin.dao;

import com.px.admin.entity.Alert;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface AlertDao extends GenericDao<Alert, Integer> {

    List<Alert> list(Boolean active, int offset, int limit, String sort, String dir);

    List<Alert> listAll(Boolean active, String sort, String dir);
    
    List<Alert> listAllCurrent(List<Integer> excludeIds);

    Integer countAll(Boolean active);
}
