package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.mwp.daoimpl.WorkflowTypeDaoImpl;
import com.px.mwp.entity.WorkflowType;
import com.px.mwp.model.WorkflowTypeModel;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WorkflowTypeService implements GenericService<WorkflowType, WorkflowTypeModel> {

    private static final Logger log = Logger.getLogger(WorkflowTypeService.class.getName());
    private final WorkflowTypeDaoImpl workflowTypeDaoImpl;

    public WorkflowTypeService() {
        this.workflowTypeDaoImpl = new WorkflowTypeDaoImpl();
    }

    @Override
    public WorkflowType create(WorkflowType workflowType) {
        checkNotNull(workflowType, "workflowType entity must not be null");
        checkNotNull(workflowType.getCreatedBy(), "create by must not be null");
        return workflowTypeDaoImpl.create(workflowType);
    }

    @Override
    public WorkflowType getById(int id) {
        return workflowTypeDaoImpl.getById(id);
    }

    @Override
    public WorkflowType getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorkflowType update(WorkflowType workflowType) {
        checkNotNull(workflowType, "workflowType entity must not be null");
        checkNotNull(workflowType.getUpdatedBy(), "update by must not be null");
        workflowType.setUpdatedDate(LocalDateTime.now());
        return workflowTypeDaoImpl.update(workflowType);
    }

    @Override
    public WorkflowType remove(int id, int userId) {
        checkNotNull(id, "workflowType id must not be null");
        WorkflowType workflowType = getById(id);
        checkNotNull(workflowType, "workflowType entity not found in database.");
        workflowType.setRemovedBy(userId);
        workflowType.setRemovedDate(LocalDateTime.now());
        return workflowTypeDaoImpl.update(workflowType);
    }

    @Override
    public List<WorkflowType> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WorkflowType> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return workflowTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WorkflowType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WorkflowTypeModel tranformToModel(WorkflowType workflowType) {
        WorkflowTypeModel workflowTypeModel = null;
        if (workflowType != null) {
            workflowTypeModel = new WorkflowTypeModel();
            workflowTypeModel.setId(workflowType.getId());
            workflowTypeModel.setCreatedBy(workflowType.getCreatedBy());
            workflowTypeModel.setCreatedDate(Common.localDateTimeToString(workflowType.getCreatedDate()));
            workflowTypeModel.setOrderNo((float) workflowType.getOrderNo());
            workflowTypeModel.setUpdatedBy(workflowType.getUpdatedBy());
            workflowTypeModel.setUpdatedDate(Common.localDateTimeToString(workflowType.getUpdatedDate()));
            workflowTypeModel.setRemovedBy(workflowType.getRemovedBy());
            workflowTypeModel.setRemovedDate(Common.localDateTimeToString(workflowType.getRemovedDate()));
            workflowTypeModel.setWorkflowTypeTitle(workflowType.getWorkflowTypeTitle());
            workflowTypeModel.setWorkflowTypeActionType(workflowType.getWorkflowTypeActionType());
            workflowTypeModel.setWorkflowTypeAction(workflowType.getWorkflowTypeAction());
        }
        return workflowTypeModel;
    }
    
    public List<WorkflowType> listByActionType(String actionType, String sort, String dir) {
        checkNotNull(actionType, "actionType must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return workflowTypeDaoImpl.listByActionType(actionType,sort, dir);
    }

}
