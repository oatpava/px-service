package com.px.wf.service;

import com.px.wf.daoimpl.ThegifDepartmentDaoImpl;
import com.px.wf.entity.ThegifDepartment;
import com.px.wf.model.get.ThegifDepartmentModel;
import com.px.share.service.GenericTreeService;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.util.Common;
import com.px.wf.model.get.ThegifDepartmentModel_hasChild;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Mali
 */

public class ThegifDepartmentService implements GenericTreeService<ThegifDepartment, ThegifDepartmentModel> {

    private final ThegifDepartmentDaoImpl thegifDepartmentDaoImpl;

    public ThegifDepartmentService() {
        this.thegifDepartmentDaoImpl = new ThegifDepartmentDaoImpl();
    }

    @Override
    public ThegifDepartment create(ThegifDepartment thegifDepartment) {
        checkNotNull(thegifDepartment, "thegifDepartment entity must not be null");
        checkNotNull(thegifDepartment.getCreatedBy(), "thegifDepartment by must not be null");
        checkNotNull(thegifDepartment.getNodeLevel(), "nodeLevel by must not be null");
        checkNotNull(thegifDepartment.getParentId(), "parentId by must not be null");
        return thegifDepartmentDaoImpl.create(thegifDepartment);
    }

    @Override
    public ThegifDepartment getById(int id) {
        return thegifDepartmentDaoImpl.getById(id);
    }

    @Override
    public ThegifDepartment getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ThegifDepartment update(ThegifDepartment thegifDepartment) {
        checkNotNull(thegifDepartment, "thegifDepartment entity must not be null");
        checkNotNull(thegifDepartment.getUpdatedBy(), "thegifDepartment by must not be null");
        checkNotNull(thegifDepartment.getNodeLevel(), "nodeLevel by must not be null");
        checkNotNull(thegifDepartment.getParentId(), "parentId by must not be null");
        thegifDepartment.setUpdatedDate(LocalDateTime.now());
        return thegifDepartmentDaoImpl.update(thegifDepartment);
    }

    @Override
    public ThegifDepartment remove(int id, int userId) {
        checkNotNull(id, "thegifDepartment id must not be null");
        ThegifDepartment thegifDepartment = getById(id);
        checkNotNull(thegifDepartment, "thegifDepartment entity not found in database.");
        thegifDepartment.setRemovedBy(userId);
        thegifDepartment.setRemovedDate(LocalDateTime.now());
        return thegifDepartmentDaoImpl.update(thegifDepartment);
    }

    @Override
    public List<ThegifDepartment> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDepartment> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return thegifDepartmentDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDepartment> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ThegifDepartmentModel tranformToModel(ThegifDepartment thegifDepartment) {
        ThegifDepartmentModel thegifDepartmentModel = null;
        if (thegifDepartment != null) {
            thegifDepartmentModel = new ThegifDepartmentModel();
            thegifDepartmentModel.setId(thegifDepartment.getId());
            thegifDepartmentModel.setCreatedBy(thegifDepartment.getCreatedBy());
            thegifDepartmentModel.setCreatedDate(Common.localDateTimeToString(thegifDepartment.getCreatedDate()));
            thegifDepartmentModel.setOrderNo((float) thegifDepartment.getOrderNo());
            thegifDepartmentModel.setUpdatedBy(thegifDepartment.getUpdatedBy());
            thegifDepartmentModel.setUpdatedDate(Common.localDateTimeToString(thegifDepartment.getUpdatedDate()));
            thegifDepartmentModel.setRemovedBy(thegifDepartment.getRemovedBy());
            thegifDepartmentModel.setRemovedDate(Common.localDateTimeToString(thegifDepartment.getRemovedDate()));
            thegifDepartmentModel.setThegifDepartmentName(thegifDepartment.getThegifDepartmentName());
            thegifDepartmentModel.setThegifDepartmentCode(thegifDepartment.getThegifDepartmentCode());
            thegifDepartmentModel.setNodeLevel(thegifDepartment.getNodeLevel());
            thegifDepartmentModel.setParentId(thegifDepartment.getParentId());
            thegifDepartmentModel.setParentKey(thegifDepartment.getParentKey());
            thegifDepartmentModel.setThegifDepartmentServiceName(thegifDepartment.getThegifDepartmentServiceName());
        }
        return thegifDepartmentModel;
    }

    public ThegifDepartmentModel_hasChild tranformToModelCheckHasChild(ThegifDepartment thegifDepartment) {
        ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
        ThegifDepartmentModel_hasChild thegifDepartmentModel_hasChild = null;
        if (thegifDepartment != null) {
            thegifDepartmentModel_hasChild = new ThegifDepartmentModel_hasChild();
            thegifDepartmentModel_hasChild.setId(thegifDepartment.getId());
            thegifDepartmentModel_hasChild.setCreatedBy(thegifDepartment.getCreatedBy());
            thegifDepartmentModel_hasChild.setCreatedDate(Common.localDateTimeToString(thegifDepartment.getCreatedDate()));
            thegifDepartmentModel_hasChild.setOrderNo((float) thegifDepartment.getOrderNo());
            thegifDepartmentModel_hasChild.setUpdatedBy(thegifDepartment.getUpdatedBy());
            thegifDepartmentModel_hasChild.setUpdatedDate(Common.localDateTimeToString(thegifDepartment.getUpdatedDate()));
            thegifDepartmentModel_hasChild.setRemovedBy(thegifDepartment.getRemovedBy());
            thegifDepartmentModel_hasChild.setRemovedDate(Common.localDateTimeToString(thegifDepartment.getRemovedDate()));
            thegifDepartmentModel_hasChild.setThegifDepartmentName(thegifDepartment.getThegifDepartmentName());
            thegifDepartmentModel_hasChild.setThegifDepartmentCode(thegifDepartment.getThegifDepartmentCode());
            thegifDepartmentModel_hasChild.setNodeLevel(thegifDepartment.getNodeLevel());
            thegifDepartmentModel_hasChild.setParentId(thegifDepartment.getParentId());
            thegifDepartmentModel_hasChild.setParentKey(thegifDepartment.getParentKey());
            thegifDepartmentModel_hasChild.setThegifDepartmentServiceName(thegifDepartment.getThegifDepartmentServiceName());

            List<ThegifDepartment> listThegifDepartment = new ArrayList();
            listThegifDepartment = thegifDepartmentService.listChild(thegifDepartment);
            if (!listThegifDepartment.isEmpty()) {
                thegifDepartmentModel_hasChild.setHasChild(true);
            } else {
                thegifDepartmentModel_hasChild.setHasChild(false);
            }
        }
        return thegifDepartmentModel_hasChild;
    }

    @Override
    public String generateParentKey(ThegifDepartment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ThegifDepartment getParent(ThegifDepartment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDepartment> listChild(ThegifDepartment thegifDepartment) {
        checkNotNull(thegifDepartment, "thegifDepartment entity must not be null");
        checkNotNull(thegifDepartment.getId(), "thegifDepartment id must not be null");
        return thegifDepartmentDaoImpl.findChild(thegifDepartment.getId());
    }

    public List<ThegifDepartment> listChild(ThegifDepartment thegifDepartment, String sort, String dir) {
        checkNotNull(thegifDepartment, "thegifDepartment entity must not be null");
        checkNotNull(thegifDepartment.getId(), "thegifDepartment id must not be null");
        return thegifDepartmentDaoImpl.findChild(thegifDepartment.getId(), sort, dir);
    }

    @Override
    public List<ThegifDepartment> listFromParentKey(ThegifDepartment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ThegifDepartment getByThegifDepartmentCode(String thegifDepartmentCode) {
        checkNotNull(thegifDepartmentCode, "thegifDepartmentCode entity must not be null");
        return thegifDepartmentDaoImpl.getByThegifDepartmentCode(thegifDepartmentCode);
    }

    public List<ThegifDepartment> searchThegifDepartment(MultivaluedMap<String, String> queryParams, String sort, String dir) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return thegifDepartmentDaoImpl.searchThegifDepartment(queryParams, sort, dir);
    }

    public int countSearchThegifDepartment(MultivaluedMap<String, String> queryParams) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        return thegifDepartmentDaoImpl.countSearchThegifDepartment(queryParams);
    }

    public List<ThegifDepartment> listByThegifDepartmentName(String thegifDepartmentName) {
        checkNotNull(thegifDepartmentName, "thegifDepartmentName entity must not be null");
        return thegifDepartmentDaoImpl.listByThegifDepartmentName(thegifDepartmentName);
    }

}
