package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.mwp.daoimpl.WorkflowCcDaoImpl;
import com.px.mwp.entity.WorkflowCc;
import com.px.mwp.model.WorkflowCcModel;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WorkflowCcService implements GenericService<WorkflowCc, WorkflowCcModel> {

    private static final Logger log = Logger.getLogger(WorkflowCcService.class.getName());
    private final WorkflowCcDaoImpl WorkflowCcDaoImpl;

    public WorkflowCcService() {
        this.WorkflowCcDaoImpl = new WorkflowCcDaoImpl();
    }

    @Override
    public WorkflowCc create(WorkflowCc workflowCc) {
        checkNotNull(workflowCc, "workflowCc entity must not be null");
        checkNotNull(workflowCc.getCreatedBy(), "create by must not be null");
        return WorkflowCcDaoImpl.create(workflowCc);
    }

    @Override
    public WorkflowCc getById(int id) {
        return WorkflowCcDaoImpl.getById(id);
    }

    @Override
    public WorkflowCc update(WorkflowCc workflowCc) {
        checkNotNull(workflowCc, "workflowCc entity must not be null");
        checkNotNull(workflowCc.getUpdatedBy(), "update by must not be null");
        workflowCc.setUpdatedDate(LocalDateTime.now());
        return WorkflowCcDaoImpl.update(workflowCc);
    }

    @Override
    public WorkflowCc remove(int id, int userId) {
        checkNotNull(id, "workflowCc id must not be null");
        WorkflowCc workflowCc = getById(id);
        checkNotNull(workflowCc, "workflowCc entity not found in database.");
        workflowCc.setRemovedBy(userId);
        workflowCc.setRemovedDate(LocalDateTime.now());
        return WorkflowCcDaoImpl.update(workflowCc);
    }

    public List<WorkflowCc> listByWorkflowId(int workflowId, int limit, int offset, String sort, String dir) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowCcDaoImpl.listByWorkflowId(workflowId, offset, limit, sort, dir);
    }

    public List<WorkflowCc> listByWorkflowId(int workflowId) {
        checkNotNull(workflowId, "workflowId must not be null");
        return WorkflowCcDaoImpl.listByWorkflowId(workflowId);
    }

    public List<WorkflowCc> listByUserProfileId(int userProfileId) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        return WorkflowCcDaoImpl.listByUserProfileId(userProfileId);
    }

    public WorkflowCc getByWorkflowIdAndUserProfileId(int workflowId, int userProfileId) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(userProfileId, "userProfileId must not be null");
        return WorkflowCcDaoImpl.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
    }

    public WorkflowCc getByWorkflowIdAndStructureId(int workflowId, int structureId) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(structureId, "structureId must not be null");
        return WorkflowCcDaoImpl.getByWorkflowIdAndStructureId(workflowId, structureId);
    }

    @Override
    public List<WorkflowCc> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowCcDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<WorkflowCc> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowCcDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return WorkflowCcDaoImpl.countAll();
    }

    @Override
    public List<WorkflowCc> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorkflowCcModel tranformToModel(WorkflowCc workflowCc) {
        WorkflowService workflowService = new WorkflowService();
        WorkflowCcModel workflowCcModel = null;
        if (workflowCc != null) {
            workflowCcModel = new WorkflowCcModel();
            workflowCcModel.setId(workflowCc.getId());
            workflowCcModel.setCreatedBy(workflowCc.getCreatedBy());
            workflowCcModel.setCreatedDate(Common.localDateTimeToString(workflowCc.getCreatedDate()));
            workflowCcModel.setOrderNo((float) workflowCc.getOrderNo());
            workflowCcModel.setUpdatedBy(workflowCc.getUpdatedBy());
            workflowCcModel.setUpdatedDate(Common.localDateTimeToString(workflowCc.getUpdatedDate()));
            workflowCcModel.setRemovedBy(workflowCc.getRemovedBy());
            workflowCcModel.setRemovedDate(Common.localDateTimeToString(workflowCc.getRemovedDate()));
            workflowCcModel.setWorkflow(workflowService.tranformToModel(workflowCc.getWorkflow()));
            workflowCcModel.setUserProfileId(workflowCc.getUserProfileId());
            workflowCcModel.setStructureId(workflowCc.getStructureId());
            workflowCcModel.setUserProfileFullName(workflowCc.getUserProfileFullName());
            workflowCcModel.setUserProfilePosition(workflowCc.getUserProfilePosition());
            workflowCcModel.setWorkflowCcOpenDate(Common.localDateTimeToString(workflowCc.getWorkflowCcOpenDate()));
            workflowCcModel.setWorkflowCcOpenFlag(workflowCc.getWorkflowCcOpenFlag());
            workflowCcModel.setWorkflowCcReceiveDate(Common.localDateTimeToString(workflowCc.getWorkflowCcReceiveDate()));
            workflowCcModel.setWorkflowCcReceiveFlag(workflowCc.getWorkflowCcReceiveFlag());
            workflowCcModel.setWorkflowCcActionDate(Common.localDateTimeToString(workflowCc.getWorkflowCcActionDate()));
            workflowCcModel.setWorkflowCcActionFlag(workflowCc.getWorkflowCcActionFlag());
            workflowCcModel.setWorkflowCcFinishDate(Common.localDateTimeToString(workflowCc.getWorkflowCcFinishDate()));
            workflowCcModel.setWorkflowCcFinishFlag(workflowCc.getWorkflowCcFinishFlag());

        }
        return workflowCcModel;
    }

    @Override
    public WorkflowCc getByIdNotRemoved(int id) {
        checkNotNull(id, "WorkflowCc id entity must not be null");
        return WorkflowCcDaoImpl.getByIdNotRemoved(id);
    }

    public List<WorkflowCc> listByStructureId(int structureId) {
        checkNotNull(structureId, "structureId must not be null");
        return WorkflowCcDaoImpl.listByStructureId(structureId);
    }
}
