package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.wf.daoimpl.WfFieldDaoImpl;
import com.px.wf.entity.WfField;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.wf.model.WfFieldModel;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
public class WfFieldService implements GenericService<WfField, WfFieldModel> {

    private static final Logger log = Logger.getLogger(WfFolderService.class.getName());
    private final WfFieldDaoImpl WfFieldDaoImpl;

    public WfFieldService() {
        this.WfFieldDaoImpl = new WfFieldDaoImpl();
    }

    @Override
    public WfField create(WfField wfField) {
        checkNotNull(wfField, "wfField entity must not be null");
        checkNotNull(wfField.getCreatedBy(), "create by must not be null");
        return WfFieldDaoImpl.create(wfField);
    }

    @Override
    public WfField getById(int id) {
        return WfFieldDaoImpl.getById(id);
    }

    @Override
    public WfField update(WfField t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfField remove(int id, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfField> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfField> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WfFieldDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return WfFieldDaoImpl.countAll();
    }

    @Override
    public List<WfField> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfFieldModel tranformToModel(WfField wfField) {
        WfFieldModel wfFieldModel = null;
        if (wfField != null) {
            wfFieldModel = new WfFieldModel();
            wfFieldModel.setId(wfField.getId());
            wfFieldModel.setCreatedBy(wfField.getCreatedBy());
            wfFieldModel.setCreatedDate(Common.localDateTimeToString(wfField.getCreatedDate()));
            wfFieldModel.setOrderNo((float) wfField.getOrderNo());
            wfFieldModel.setUpdatedBy(wfField.getUpdatedBy());
            wfFieldModel.setUpdatedDate(Common.localDateTimeToString(wfField.getUpdatedDate()));
            wfFieldModel.setRemovedBy(wfField.getRemovedBy());
            wfFieldModel.setRemovedDate(Common.localDateTimeToString(wfField.getRemovedDate()));
            wfFieldModel.setWfFieldName(wfField.getWfFieldName());
            wfFieldModel.setWfFieldDescription(wfField.getWfFieldDescription());
            wfFieldModel.setWfFieldType(wfField.getWfFieldType());
            wfFieldModel.setWfFieldLength(wfField.getWfFieldLength());
        }
        return wfFieldModel;
    }
    
    @Override
    public WfField getByIdNotRemoved(int id) {
        checkNotNull(id, "WfField id entity must not be null");
        return WfFieldDaoImpl.getByIdNotRemoved(id);
    }

}
