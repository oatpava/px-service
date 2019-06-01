
package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.WorkflowCc;
import java.util.List;

/**
 *
 * @author Mali
 */
public interface WorkflowCcDao extends GenericDao<WorkflowCc, Integer>{
    List<WorkflowCc> list(int offset,int limit,String sort,String dir);
    List<WorkflowCc> listByWorkflowId(int workflowId,int offset,int limit,String sort,String dir);
    List<WorkflowCc> listByWorkflowId(int workflowId);
    WorkflowCc getByWorkflowIdAndUserProfileId(int workflowId,int userProfileId);
    WorkflowCc getByWorkflowIdAndStructureId(int workflowId,int structureId);
    List<WorkflowCc> listAll(String sort,String dir);
    List<WorkflowCc> listByUserProfileId(int userProfileId);  
    List<WorkflowCc> listByStructureId(int structureId);
    Integer countAll(); 
}
