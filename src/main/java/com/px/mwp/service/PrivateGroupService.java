/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.mwp.daoimpl.PrivateGroupDaoImpl;
import com.px.mwp.entity.PrivateGroup;
import com.px.mwp.model.PrivateGroupModel;
import com.px.mwp.model.PrivateGroupUserModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
public class PrivateGroupService implements GenericService<PrivateGroup, PrivateGroupModel> {

    private static final Logger LOG = Logger.getLogger(PrivateGroupService.class.getName());
    private final PrivateGroupDaoImpl privateGroupDaoImpl;

    public PrivateGroupService() {
        this.privateGroupDaoImpl = new PrivateGroupDaoImpl();
    }

    @Override
    public PrivateGroup create(PrivateGroup privateGroup) {
        checkNotNull(privateGroup, "privateGroup entity must not be null");
        checkNotNull(privateGroup.getCreatedBy(), "create by must not be null");
        checkNotNull(privateGroup.getGroupName(), "gropName must not be null");
        return privateGroupDaoImpl.create(privateGroup);
    }

    @Override
    public PrivateGroup getById(int id) {
        return privateGroupDaoImpl.getById(id);
    }

    @Override
    public PrivateGroup getByIdNotRemoved(int id) {
        return privateGroupDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public PrivateGroup update(PrivateGroup privateGroup) {
        checkNotNull(privateGroup, "privateGroup entity must not be null");
        checkNotNull(privateGroup.getUpdatedBy(), "update by must not be null");
        checkNotNull(privateGroup.getGroupName(), "gropName by must not be null");
        privateGroup.setUpdatedDate(LocalDateTime.now());
        return privateGroupDaoImpl.update(privateGroup);
    }

    @Override
    public PrivateGroup remove(int id, int userId) {
        checkNotNull(id, "privateGroup id must not be null");
        PrivateGroup privateGroup = getById(id);
        checkNotNull(privateGroup, "privateGroup entity not found in database.");
        privateGroup.setRemovedBy(userId);
        privateGroup.setRemovedDate(LocalDateTime.now());
        return privateGroupDaoImpl.update(privateGroup);
    }

    @Override
    public List<PrivateGroup> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrivateGroup> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrivateGroup> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrivateGroupModel tranformToModel(PrivateGroup privateGroup) {
        PrivateGroupModel privateGroupModel = new PrivateGroupModel();
        privateGroupModel.setId(privateGroup.getId());
        //privateGroupModel.setCreatedBy(privateGroup.getCreatedBy());
        //privateGroupModel.setCreatedDate(privateGroup.getCreatedDate());
        privateGroupModel.setOwnerId(privateGroup.getOwnerId());
        privateGroupModel.setGroupName(privateGroup.getGroupName());
        privateGroupModel.setType(privateGroup.getType());
        return privateGroupModel;
    }

    public PrivateGroupModel tranformToModel(PrivateGroup privateGroup, List<PrivateGroupUserModel> listUser) {
        PrivateGroupModel privateGroupModel = new PrivateGroupModel();
        privateGroupModel.setId(privateGroup.getId());
        privateGroupModel.setOwnerId(privateGroup.getOwnerId());
        privateGroupModel.setGroupName(privateGroup.getGroupName());
        privateGroupModel.setType(privateGroup.getType());
        privateGroupModel.setListUser(listUser);
        return privateGroupModel;
    }

    public List<PrivateGroup> listByOwnerIdAndType(int ownerId, int type) {
        checkNotNull(ownerId, "ownerId must not be null");
        checkNotNull(type, "type must not be null");
        return privateGroupDaoImpl.listByOwnerIdAndType(ownerId, type);
    }

    public void delete(PrivateGroup privateGroup) {
        checkNotNull(privateGroup, "privateGroup must not be null");
        privateGroupDaoImpl.delete(privateGroup);
    }

}
