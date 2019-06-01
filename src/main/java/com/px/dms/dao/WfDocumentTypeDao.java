package com.px.dms.dao;

import com.px.dms.entity.WfDocumentType;
import com.px.share.dao.GenericTreeDao;
import java.util.List;

/**
 *
 * @author CHALERMPOL
 */
public interface WfDocumentTypeDao extends GenericTreeDao<WfDocumentType, Integer>{
    List<WfDocumentType> list(int offset,int limit,String sort,String dir);
    List<WfDocumentType> listAll(String sort,String dir);    
    Integer countAll();
    List<WfDocumentType> listAllParent(String sort, String dir);
    List<WfDocumentType> listChildByParentId(String sort, String dir, int parentId);
    Integer getMaxCode(int parentId);
}
