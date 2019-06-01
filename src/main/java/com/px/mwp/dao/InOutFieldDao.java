package com.px.mwp.dao;

import com.px.mwp.entity.InOutField;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface InOutFieldDao extends GenericDao<InOutField, Integer> {
    List<InOutField> listAll(String sort, String dir);
    Integer countAll();
}
