package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserStatusDaoImpl;
import com.px.admin.entity.UserStatus;
import com.px.admin.model.UserStatusModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class UserStatusService implements GenericService<UserStatus, UserStatusModel>{
    private static final Logger LOG = Logger.getLogger(UserStatusService.class.getName());
    private final UserStatusDaoImpl userStatusDaoImpl;
    
    public UserStatusService() {
        this.userStatusDaoImpl = new UserStatusDaoImpl();
    }

    @Override
    public UserStatus create(UserStatus userStatus) {
        checkNotNull(userStatus, "userStatus entity must not be null");
        checkNotNull(userStatus.getUserStatusName(), "userStatus name must not be null");
        checkNotNull(userStatus.getCreatedBy(),"create by must not be null");
        userStatus = userStatusDaoImpl.create(userStatus);
        if(userStatus.getOrderNo()==0){
            userStatus.setOrderNo(userStatus.getId());
            userStatus = update(userStatus);
        }
        return userStatus;
    }

    @Override
    public UserStatus getById(int id) {
        checkNotNull(id, "userStatus id entity must not be null");
        return userStatusDaoImpl.getById(id);
    }

    @Override
    public UserStatus update(UserStatus userStatus) {
        checkNotNull(userStatus, "userStatus entity must not be null");
        checkNotNull(userStatus.getUserStatusName(), "userStatus name must not be null");
        checkNotNull(userStatus.getUpdatedBy(),"update by must not be null");
        userStatus.setUpdatedDate(LocalDateTime.now());
        return userStatusDaoImpl.update(userStatus);
    }

    @Override
    public UserStatus remove(int id, int userId) {
        checkNotNull(id, "userStatus id must not be null");
        UserStatus userStatus = getById(id);
        checkNotNull(userStatus, "userStatus entity not found in database.");
        userStatus.setRemovedBy(userId);
        userStatus.setRemovedDate(LocalDateTime.now());
        return userStatusDaoImpl.update(userStatus);
    }

    @Override
    public List<UserStatus> listAll(String sort, String dir) {
        return userStatusDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<UserStatus> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");        
        return userStatusDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public UserStatusModel tranformToModel(UserStatus userStatus) {
        UserStatusModel userStatusModel = null;
        if(userStatus!=null){
            userStatusModel = new UserStatusModel();
            userStatusModel.setId(userStatus.getId());
            userStatusModel.setName(userStatus.getUserStatusName());
        }
        return userStatusModel;
    }

    @Override
    public int countAll() {
        return userStatusDaoImpl.countAll();
    }

    @Override
    public List<UserStatus> search(MultivaluedMap<String, String> queryUserStatuss, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryUserStatuss) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public UserStatus getByIdNotRemoved(int id) {
        checkNotNull(id, "UserStatus id entity must not be null");
        return userStatusDaoImpl.getByIdNotRemoved(id);
    }
    
}
