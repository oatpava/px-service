/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.dms.daoimpl.DmsFieldDaoImpl;
import com.px.dms.entity.DmsField;
import com.px.dms.model.DmsFieldModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DmsFieldService implements GenericService<DmsField, DmsFieldModel> {

    private static final Logger log = Logger.getLogger(DmsFieldService.class.getName());
    private final DmsFieldDaoImpl dmsFieldDaoImpl;

    public DmsFieldService() {
        this.dmsFieldDaoImpl = new DmsFieldDaoImpl();
    }

    @Override
    public DmsField create(DmsField t) {
        checkNotNull(t, "report entity must not be null");
        checkNotNull(t.getDmsFieldMap(), "FieldMap must not be null");
        checkNotNull(t.getDmsFieldName(), "FieldName must not be null");
        checkNotNull(t.getDmsFieldType(), "FieldType must not be null");
        checkNotNull(t.getCreatedBy(), "create by must not be null");
        return dmsFieldDaoImpl.create(t);
    }

    @Override
    public DmsField getById(int id) {
        return dmsFieldDaoImpl.getById(id);
    }

    @Override
    public DmsField update(DmsField t) {
        checkNotNull(t, "report entity must not be null");
        checkNotNull(t.getDmsFieldMap(), "FieldMap must not be null");
        checkNotNull(t.getDmsFieldName(), "FieldName must not be null");
        checkNotNull(t.getDmsFieldType(), "FieldType must not be null");
        checkNotNull(t.getUpdatedBy(), "update by must not be null");
        t.setUpdatedDate(LocalDateTime.now());
        return dmsFieldDaoImpl.update(t);
    }

    @Override
    public DmsField remove(int id, int userId) {
        checkNotNull(id, "DmsField id must not be null");
        DmsField dmsField = getById(id);
        checkNotNull(dmsField, "dmsField entity not found in database.");
        dmsField.setRemovedBy(userId);
        dmsField.setRemovedDate(LocalDateTime.now());
        return dmsFieldDaoImpl.update(dmsField);
    }

    @Override
    public List<DmsField> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsFieldDaoImpl.list(offset,limit,sort, dir);
    }

    @Override
    public List<DmsField> listAll(String sort, String dir) {
         checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsFieldDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return dmsFieldDaoImpl.countAll();
    }

    @Override
    public List<DmsField> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DmsFieldModel tranformToModel(DmsField t) {
        DmsFieldModel dmsFieldModel  = new DmsFieldModel();
        if (t != null) {
            dmsFieldModel.setId(t.getId());
            dmsFieldModel.setFieldName(t.getDmsFieldName());
            dmsFieldModel.setFieldType(t.getDmsFieldType());
            dmsFieldModel.setFieldMap(t.getDmsFieldMap() );
            
        }
        return dmsFieldModel;
    }

    public List<DmsField> listDmsField() {

        return dmsFieldDaoImpl.listDmsField();
    }
    
    @Override
    public DmsField getByIdNotRemoved(int id) {
        checkNotNull(id, "DmsField id entity must not be null");
        return dmsFieldDaoImpl.getByIdNotRemoved(id);
    }
    
    public DmsField getByFieldMap(String map) {
        checkNotNull(map, "DmsField map  must not be null");
        return dmsFieldDaoImpl.getByFieldMap(map);
    }
}
