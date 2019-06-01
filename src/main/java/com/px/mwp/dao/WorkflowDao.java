package com.px.mwp.dao;

import com.px.share.dao.GenericDao;
import com.px.mwp.entity.Workflow;
import java.util.List;

/**
 *
 * @author Oat
 */
public interface WorkflowDao extends GenericDao<Workflow, Integer> {
    List<Workflow> listByLinkIdGetRemoved(int linkId, String sort, String dir);
    Workflow getByLinkIdAndLinkId2(int linkId, int linkId2, String type);//เฉพาะจุด
    Workflow getByLinkIdAndLinkId3(int linkId, int linkId3, String type);//ทั้ง flow (โดยต้นเรื่อง)
}
