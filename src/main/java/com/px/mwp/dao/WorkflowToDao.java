
package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.WorkflowTo;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface WorkflowToDao extends GenericDao<WorkflowTo, Integer>{
    List<WorkflowTo> list(int offset,int limit,String sort,String dir);
    List<WorkflowTo> listByWorkflowId(int workflowId,int offset,int limit,String sort,String dir);
    List<WorkflowTo> listAll(String sort,String dir);    
    Integer countAll();  
    List<WorkflowTo> listByWorkflowId(int workflowId);
    List<WorkflowTo> listByUserProfileId(int userProfileId);
    List<WorkflowTo> listByStructureId(int structureId);
    WorkflowTo getByWorkflowIdAndUserProfileId(int workflowId,int userProfileId);
    WorkflowTo getByWorkflowIdAndStructureId(int workflowId,int structureId);
}
