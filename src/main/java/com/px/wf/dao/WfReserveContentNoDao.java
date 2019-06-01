
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.WfReserveContentNo;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface WfReserveContentNoDao extends GenericDao<WfReserveContentNo, Integer>{
     int getMaxContentNumber(int folderId,int contentYear);
     WfReserveContentNo getContentNoByFolderId(int folderId,String contentNo);
     List<WfReserveContentNo> listWfReserveContentNoByContentDate(int folderId,String reserveContentNoContentDateBegin,String reserveContentNoContentDateEnd,String sort,String dir);
}
