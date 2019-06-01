package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.mwp.daoimpl.InOutFieldDaoImpl;
import com.px.mwp.entity.InOutField;
import com.px.mwp.model.InOutFieldModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
public class InOutFieldService implements GenericService<InOutField, InOutFieldModel> {

    private static final Logger log = Logger.getLogger(InOutFieldService.class.getName());
    private final InOutFieldDaoImpl InOutFieldDaoImpl;

    public InOutFieldService() {
        this.InOutFieldDaoImpl = new InOutFieldDaoImpl();
    }

    @Override
    public InOutField create(InOutField inOutField) {
        checkNotNull(inOutField, "inOutField entity must not be null");
        checkNotNull(inOutField.getCreatedBy(), "create by must not be null");
        return InOutFieldDaoImpl.create(inOutField);
    }

    @Override
    public InOutField getById(int id) {
        return InOutFieldDaoImpl.getById(id);
    }

    @Override
    public InOutField update(InOutField inOutField) {
        checkNotNull(inOutField, "inOutField entity must not be null");
        checkNotNull(inOutField.getUpdatedBy(), "update by must not be null");
        inOutField.setUpdatedDate(LocalDateTime.now());
        return InOutFieldDaoImpl.update(inOutField);
    }

    @Override
    public InOutField remove(int id, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InOutField> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InOutField> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InOutFieldDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return InOutFieldDaoImpl.countAll();
    }

    @Override
    public List<InOutField> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InOutFieldModel tranformToModel(InOutField inOutField) {
        InOutFieldModel inoutFieldModel = null;
        if (inOutField != null) {
            inoutFieldModel = new InOutFieldModel();
            inoutFieldModel.setId(inOutField.getId());
            inoutFieldModel.setCreatedBy(inOutField.getCreatedBy());
            inoutFieldModel.setCreatedDate(Common.localDateTimeToString(inOutField.getCreatedDate()));
            inoutFieldModel.setOrderNo((float) inOutField.getOrderNo());
            inoutFieldModel.setRemovedBy(inOutField.getRemovedBy());
            inoutFieldModel.setRemovedDate(Common.localDateTimeToString(inOutField.getRemovedDate()));
            inoutFieldModel.setUpdatedBy(inOutField.getUpdatedBy());
            inoutFieldModel.setUpdatedDate(Common.localDateTimeToString(inOutField.getUpdatedDate()));
            inoutFieldModel.setInoutFieldName(inOutField.getInoutFieldName());
            inoutFieldModel.setInoutFieldDescription(inOutField.getInoutFieldDescription());
            inoutFieldModel.setInoutFieldType(inOutField.getInoutFieldType());
            inoutFieldModel.setInoutFieldLength(inOutField.getInoutFieldLength());
        }
        return inoutFieldModel;
    }

    @Override
    public InOutField getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
