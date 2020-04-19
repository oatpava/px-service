package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.Structure;
import com.px.admin.service.StructureService;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.mwp.daoimpl.WorkflowToDaoImpl;
import com.px.mwp.entity.WorkflowTo;
import com.px.mwp.model.WorkflowToModel;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WorkflowToService implements GenericService<WorkflowTo, WorkflowToModel> {

    private static final Logger log = Logger.getLogger(WorkflowToService.class.getName());
    private final WorkflowToDaoImpl WorkflowToDaoImpl;

    public WorkflowToService() {
        this.WorkflowToDaoImpl = new WorkflowToDaoImpl();
    }

    @Override
    public WorkflowTo create(WorkflowTo workflowTo) {
        checkNotNull(workflowTo, "workflowTo entity must not be null");
        checkNotNull(workflowTo.getCreatedBy(), "create by must not be null");
        return WorkflowToDaoImpl.create(workflowTo);
    }

    @Override
    public WorkflowTo getById(int id) {
        return WorkflowToDaoImpl.getById(id);
    }

    @Override
    public WorkflowTo update(WorkflowTo workflowTo) {
        checkNotNull(workflowTo, "workflowTo entity must not be null");
        checkNotNull(workflowTo.getUpdatedBy(), "update by must not be null");
        workflowTo.setUpdatedDate(LocalDateTime.now());
        return WorkflowToDaoImpl.update(workflowTo);
    }

    @Override
    public WorkflowTo remove(int id, int userId) {
        checkNotNull(id, "workflowTo id must not be null");
        WorkflowTo workflowTo = getById(id);
        checkNotNull(workflowTo, "workflowTo entity not found in database.");
        workflowTo.setRemovedBy(userId);
        workflowTo.setRemovedDate(LocalDateTime.now());
        return WorkflowToDaoImpl.update(workflowTo);
    }

    @Override
    public List<WorkflowTo> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowToDaoImpl.list(offset, limit, sort, dir);
    }

    public List<WorkflowTo> listByWorkflowId(int workflowId, int limit, int offset, String sort, String dir) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowToDaoImpl.listByWorkflowId(workflowId, offset, limit, sort, dir);
    }

    //oat-add
    public List<WorkflowTo> listByContentId(int contentId, int limit, int offset, String sort, String dir) {
        checkNotNull(contentId, "contentId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowToDaoImpl.listByContentId(contentId, offset, limit, sort, dir);
    }

    public List<WorkflowTo> listByWorkflowId(int workflowId) {
        checkNotNull(workflowId, "workflowId must not be null");
        return WorkflowToDaoImpl.listByWorkflowId(workflowId);
    }

    public List<WorkflowTo> listByUserProfileId(int userProfileId) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        return WorkflowToDaoImpl.listByUserProfileId(userProfileId);
    }

    public List<WorkflowTo> listByStructureId(int structureId) {
        checkNotNull(structureId, "structureId must not be null");
        return WorkflowToDaoImpl.listByStructureId(structureId);
    }

    @Override
    public List<WorkflowTo> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WorkflowToDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return WorkflowToDaoImpl.countAll();
    }

    @Override
    public List<WorkflowTo> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorkflowToModel tranformToModel(WorkflowTo workflowTo) {
        WorkflowService workflowService = new WorkflowService();
        WorkflowToModel workflowToModel = null;
        if (workflowTo != null) {
            workflowToModel = new WorkflowToModel();
            workflowToModel.setId(workflowTo.getId());
            workflowToModel.setCreatedBy(workflowTo.getCreatedBy());
            workflowToModel.setCreatedDate(Common.localDateTimeToString(workflowTo.getCreatedDate()));
            workflowToModel.setOrderNo((float) workflowTo.getOrderNo());
            workflowToModel.setUpdatedBy(workflowTo.getUpdatedBy());
            workflowToModel.setUpdatedDate(Common.localDateTimeToString(workflowTo.getUpdatedDate()));
            workflowToModel.setRemovedBy(workflowTo.getRemovedBy());
            workflowToModel.setRemovedDate(Common.localDateTimeToString(workflowTo.getRemovedDate()));
            workflowToModel.setWorkflow(workflowService.tranformToModel(workflowTo.getWorkflow()));
            workflowToModel.setUserProfileId(workflowTo.getUserProfileId());
            workflowToModel.setStructureId(workflowTo.getStructureId());
            workflowToModel.setUserProfileFullName(workflowTo.getUserProfileFullName());
            workflowToModel.setUserProfilePosition(workflowTo.getUserProfilePosition());
            workflowToModel.setWorkflowToOpenDate(Common.localDateTimeToString(workflowTo.getWorkflowToOpenDate()));
            workflowToModel.setWorkflowToOpenFlag(workflowTo.getWorkflowToOpenFlag());
            workflowToModel.setWorkflowToReceiveDate(Common.localDateTimeToString(workflowTo.getWorkflowToReceiveDate()));
            workflowToModel.setWorkflowToReceiveFlag(workflowTo.getWorkflowToReceiveFlag());
            workflowToModel.setWorkflowToActionDate(Common.localDateTimeToString(workflowTo.getWorkflowToActionDate()));
            workflowToModel.setWorkflowToActionFlag(workflowTo.getWorkflowToActionFlag());
            workflowToModel.setWorkflowToFinishDate(Common.localDateTimeToString(workflowTo.getWorkflowToFinishDate()));
            workflowToModel.setWorkflowToFinishFlag(workflowTo.getWorkflowToFinishFlag());
            StructureService structureService = new StructureService();
            Structure structure = structureService.getById(workflowTo.getStructureId());
            workflowToModel.setStructure((structure != null) ? structureService.tranformToModel(structure) : null);
        }
        return workflowToModel;
    }

    @Override
    public WorkflowTo getByIdNotRemoved(int id) {
        checkNotNull(id, "WorkflowTo id entity must not be null");
        return WorkflowToDaoImpl.getByIdNotRemoved(id);
    }

    public WorkflowTo getByWorkflowIdAndUserProfileId(int workflowId, int userProfileId) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(userProfileId, "userProfileId must not be null");
        return WorkflowToDaoImpl.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
    }

    public WorkflowTo getByWorkflowIdAndStructureId(int workflowId, int structureId) {
        checkNotNull(workflowId, "workflowId must not be null");
        checkNotNull(structureId, "structureId must not be null");
        return WorkflowToDaoImpl.getByWorkflowIdAndStructureId(workflowId, structureId);
    }

    public List<WorkflowTo> listByName(int userId, String name) {
        checkNotNull(userId, "userId must not be null");
        checkNotNull(name, "name must not be null");
        return WorkflowToDaoImpl.listByName(userId, name);
    }

}
