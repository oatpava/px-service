
package com.px.mwp.dao;

import com.px.mwp.entity.InOutAssign;
import com.px.share.dao.GenericDao;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface InOutAssignDao extends GenericDao<InOutAssign, Integer> {
     List<InOutAssign> listByAssignId(int assignId,String sort,String dir);
     List<InOutAssign> listByOwnerId(int ownerId,int ownerType, String sort, String dir);
     List<InOutAssign> listByOwnerIdAndAssignId(int ownerId, int ownerType,int assignId, String sort, String dir);
     List<InOutAssign> listAfterEndDate(LocalDateTime nowDate); 
}
