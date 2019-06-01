package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserProfileTypeDaoImpl;
import com.px.admin.entity.UserProfileType;
import com.px.admin.model.UserProfileTypeModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class UserProfileTypeService implements GenericService<UserProfileType, UserProfileTypeModel>{
    private static final Logger LOG = Logger.getLogger(UserProfileTypeService.class.getName());
    private final UserProfileTypeDaoImpl userProfileTypeDaoImpl;
    
    public UserProfileTypeService() {
        this.userProfileTypeDaoImpl = new UserProfileTypeDaoImpl();
    }

    @Override
    public UserProfileType create(UserProfileType userProfileType) {
        checkNotNull(userProfileType, "userProfileType entity must not be null");
        checkNotNull(userProfileType.getUserProfileTypeName(), "userProfileType name must not be null");
        checkNotNull(userProfileType.getCreatedBy(),"create by must not be null");
        userProfileType = userProfileTypeDaoImpl.create(userProfileType);
        if(userProfileType.getOrderNo()==0){
            userProfileType.setOrderNo(userProfileType.getId());
            userProfileType = update(userProfileType);
        }
        return userProfileType;
    }

    @Override
    public UserProfileType getById(int id) {
        checkNotNull(id, "userProfileType id entity must not be null");
        return userProfileTypeDaoImpl.getById(id);
    }

    @Override
    public UserProfileType update(UserProfileType userProfileType) {
        checkNotNull(userProfileType, "userProfileType entity must not be null");
        checkNotNull(userProfileType.getUserProfileTypeName(), "userProfileType name must not be null");
        checkNotNull(userProfileType.getUpdatedBy(),"update by must not be null");
        userProfileType.setUpdatedDate(LocalDateTime.now());
        return userProfileTypeDaoImpl.update(userProfileType);
    }

    @Override
    public UserProfileType remove(int id, int userId) {
        checkNotNull(id, "userProfileType id must not be null");
        UserProfileType userProfileType = getById(id);
        checkNotNull(userProfileType, "userProfileType entity not found in database.");
        userProfileType.setRemovedBy(userId);
        userProfileType.setRemovedDate(LocalDateTime.now());
        return userProfileTypeDaoImpl.update(userProfileType);
    }

    @Override
    public List<UserProfileType> listAll(String sort, String dir) {
        return userProfileTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<UserProfileType> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userProfileTypeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public UserProfileTypeModel tranformToModel(UserProfileType userProfileType) {
        UserProfileTypeModel userProfileTypeModel = null;
        if(userProfileType!=null){
            userProfileTypeModel = new UserProfileTypeModel();
            userProfileTypeModel.setId(userProfileType.getId());
            userProfileTypeModel.setName(userProfileType.getUserProfileTypeName());
        }
        return userProfileTypeModel;
    }

    @Override
    public int countAll() {
        return userProfileTypeDaoImpl.countAll();
    }

    @Override
    public List<UserProfileType> search(MultivaluedMap<String, String> queryUserProfileTypes, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryUserProfileTypes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public UserProfileType getByIdNotRemoved(int id) {
        checkNotNull(id, "UserProfileType id entity must not be null");
        return userProfileTypeDaoImpl.getByIdNotRemoved(id);
    }
    
}
