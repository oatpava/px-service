package com.px.wf.service;

import com.px.wf.daoimpl.WfCommandTypeDaoImpl;
import com.px.wf.entity.WfCommandType;
import com.px.share.service.GenericService;
import com.px.wf.model.WfCommandTypeModel;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.util.Common;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WfCommandTypeService implements GenericService<WfCommandType, WfCommandTypeModel> {

    private static final Logger log = Logger.getLogger(WfCommandTypeService.class.getName());
    private final WfCommandTypeDaoImpl commandTypeDaoImpl;

    public WfCommandTypeService() {
        this.commandTypeDaoImpl = new WfCommandTypeDaoImpl();
    }

    @Override
    public WfCommandType create(WfCommandType wfCommandType) {
        checkNotNull(wfCommandType, "wfCommandType entity must not be null");
        checkNotNull(wfCommandType.getCreatedBy(), "create by must not be null");
        return commandTypeDaoImpl.create(wfCommandType);
    }

    @Override
    public WfCommandType getById(int id) {
        return commandTypeDaoImpl.getById(id);
    }

    @Override
    public WfCommandType getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfCommandType update(WfCommandType wfCommandType) {
        checkNotNull(wfCommandType, "wfCommandType entity must not be null");
        checkNotNull(wfCommandType.getUpdatedBy(), "update by must not be null");
        wfCommandType.setUpdatedDate(LocalDateTime.now());
        return commandTypeDaoImpl.update(wfCommandType); 
    }

    @Override
    public WfCommandType remove(int id, int userId) {
        checkNotNull(id, "wfCommandType id must not be null");
        WfCommandType wfCommandType = getById(id);
        checkNotNull(wfCommandType, "wfCommandType entity not found in database.");
        wfCommandType.setRemovedBy(userId);
        wfCommandType.setRemovedDate(LocalDateTime.now());
        return commandTypeDaoImpl.update(wfCommandType);
    }

    @Override
    public List<WfCommandType> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfCommandType> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return commandTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfCommandType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfCommandTypeModel tranformToModel(WfCommandType commandType) {
        WfCommandTypeModel commandTypeModel = null;
        if (commandType != null) {
            commandTypeModel = new WfCommandTypeModel(commandType.getId());
            commandTypeModel.setCreatedBy(commandType.getCreatedBy());
            commandTypeModel.setCreatedDate(Common.localDateTimeToString(commandType.getCreatedDate()));
            commandTypeModel.setOrderNo((float) commandType.getOrderNo());
            commandTypeModel.setRemovedBy(commandType.getRemovedBy());
            commandTypeModel.setRemovedDate(Common.localDateTimeToString(commandType.getRemovedDate()));
            commandTypeModel.setUpdatedBy(commandType.getUpdatedBy());
            commandTypeModel.setUpdatedDate(Common.localDateTimeToString(commandType.getUpdatedDate()));
            commandTypeModel.setCommandTypeName(commandType.getCommandTypeName());

        }
        return commandTypeModel;
    }

}
