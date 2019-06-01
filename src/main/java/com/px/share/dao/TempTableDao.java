
package com.px.share.dao;

import com.px.share.entity.TempTable;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface TempTableDao extends GenericDao<TempTable, Integer>{
    List<TempTable> listByComputerNameAndJobType (int userID,String computerName,String jobType,String sort,String dir);
}
