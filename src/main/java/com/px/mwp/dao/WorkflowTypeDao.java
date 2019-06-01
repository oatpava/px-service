package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.WorkflowType;
import java.util.List;
/**
 *
 * @author Mali
 */
public interface WorkflowTypeDao extends GenericDao<WorkflowType, Integer>{
    List<WorkflowType> listAll(String sort,String dir); 
    List<WorkflowType> listByActionType(String actionType,String sort,String dir); 
    
}
