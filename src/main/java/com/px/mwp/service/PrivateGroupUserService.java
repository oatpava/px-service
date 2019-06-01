/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.daoimpl.PrivateGroupUserDaoImpl;
import com.px.mwp.entity.PrivateGroupUser;
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
public class PrivateGroupUserService implements GenericService<PrivateGroupUser, PrivateGroupUserModel> {

    private static final Logger LOG = Logger.getLogger(PrivateGroupUserService.class.getName());
    private final PrivateGroupUserDaoImpl privateGroupUserDaoImpl;

    public PrivateGroupUserService() {
        this.privateGroupUserDaoImpl = new PrivateGroupUserDaoImpl();
    }

    @Override
    public PrivateGroupUser create(PrivateGroupUser pgu) {
        checkNotNull(pgu, "privateGroupUser entity must not be null");
        checkNotNull(pgu.getCreatedBy(), "create by must not be null");
        checkNotNull(pgu.getPrivateGroupId(), "privateGroupId must not be null");
        checkNotNull(pgu.getUserId(), "userId must not be null");
        checkNotNull(pgu.getUserName(), "userName must not be null");
        checkNotNull(pgu.getUserType(), "userType must not be null");
        return privateGroupUserDaoImpl.create(pgu);
    }

    @Override
    public PrivateGroupUser getById(int id) {
        return privateGroupUserDaoImpl.getById(id);
    }

    @Override
    public PrivateGroupUser getByIdNotRemoved(int id) {
        return privateGroupUserDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public PrivateGroupUser update(PrivateGroupUser pgu) {
        checkNotNull(pgu, "privateGroupUser entity must not be null");
        checkNotNull(pgu.getUpdatedBy(), "update by must not be null");
        checkNotNull(pgu.getPrivateGroupId(), "gropName by must not be null");
        checkNotNull(pgu.getUserId(), "userId must not be null");
        checkNotNull(pgu.getUserName(), "userName must not be null");
        checkNotNull(pgu.getUserType(), "userType must not be null");
        pgu.setUpdatedDate(LocalDateTime.now());
        return privateGroupUserDaoImpl.update(pgu);
    }

    @Override
    public PrivateGroupUser remove(int id, int userId) {
        checkNotNull(id, "privateGroupUser id must not be null");
        PrivateGroupUser pgu = getById(id);
        checkNotNull(pgu, "privateGroupUser entity not found in database.");
        pgu.setRemovedBy(userId);
        pgu.setRemovedDate(LocalDateTime.now());
        return privateGroupUserDaoImpl.update(pgu);
    }

    @Override
    public List<PrivateGroupUser> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrivateGroupUser> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PrivateGroupUser> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrivateGroupUserModel tranformToModel(PrivateGroupUser pgu) {
        PrivateGroupUserModel pguModel = new PrivateGroupUserModel();
        pguModel.setId(pgu.getId());
        pguModel.setPrivateGroupId(pgu.getPrivateGroupId());
        pguModel.setUserId(pgu.getUserId());
        pguModel.setUserName(pgu.getUserName());
        pguModel.setType(pgu.getUserType());
        StructureService structureService = new StructureService();
        switch (pgu.getUserType()) {
            case 0://user
//                pguModel.setEmail(new UserProfileService().getByUserId(pgu.getUserId()).getUserProfileEmail());
                pguModel.setEmail(new UserProfileService().getById(pgu.getUserId()).getUserProfileEmail());//userprofileId not userId
                pguModel.setStructure(structureService.tranformToModel(new UserProfileService().getById(pgu.getUserId()).getStructure()));
                break;
            case 1://structure
                pguModel.setEmail("");               
                pguModel.setStructure(structureService.tranformToModel(structureService.getById(pgu.getUserId())));
                break;
            case 2://outside
                pguModel.setEmail(pgu.getEmail());
                pguModel.setStructure(null);
                break;
        }
        return pguModel;
    }

    public List<PrivateGroupUser> listByPrivateGroupId(int privateGroupId, String sort, String dir) {
        checkNotNull(privateGroupId, "privateGroupId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return privateGroupUserDaoImpl.listByPrivateGroupId(privateGroupId, sort, dir);
    }

    public void delete(PrivateGroupUser pgu) {
        checkNotNull(pgu, "privateGroupUser must not be null");
        privateGroupUserDaoImpl.delete(pgu);
    }

}
