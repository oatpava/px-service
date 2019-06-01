package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserTypeOrderDaoImpl;
import com.px.admin.entity.UserTypeOrder;
import com.px.admin.model.UserTypeOrderModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class UserTypeOrderService implements GenericService<UserTypeOrder, UserTypeOrderModel>{
    private static final Logger LOG = Logger.getLogger(UserTypeOrderService.class.getName());
    private final UserTypeOrderDaoImpl userTypeDaoImpl;
    
    public UserTypeOrderService() {
        this.userTypeDaoImpl = new UserTypeOrderDaoImpl();
    }

    @Override
    public UserTypeOrder create(UserTypeOrder userTypeOrder) {
        checkNotNull(userTypeOrder, "userTypeOrder entity must not be null");
        checkNotNull(userTypeOrder.getUserTypeOrderName(), "userTypeOrder name must not be null");
        checkNotNull(userTypeOrder.getCreatedBy(),"create by must not be null");
        userTypeOrder = userTypeDaoImpl.create(userTypeOrder);
        if(userTypeOrder.getOrderNo()==0){
            userTypeOrder.setOrderNo(userTypeOrder.getId());
            userTypeOrder = update(userTypeOrder);
        }
        return userTypeOrder;
    }

    @Override
    public UserTypeOrder getById(int id) {
        checkNotNull(id, "userTypeOrder id entity must not be null");
        return userTypeDaoImpl.getById(id);
    }

    @Override
    public UserTypeOrder update(UserTypeOrder userTypeOrder) {
        checkNotNull(userTypeOrder, "userTypeOrder entity must not be null");
        checkNotNull(userTypeOrder.getUserTypeOrderName(), "userTypeOrder name must not be null");
        checkNotNull(userTypeOrder.getUpdatedBy(),"update by must not be null");
        userTypeOrder.setUpdatedDate(LocalDateTime.now());
        return userTypeDaoImpl.update(userTypeOrder);
    }

    @Override
    public UserTypeOrder remove(int id, int userId) {
        checkNotNull(id, "userTypeOrder id must not be null");
        UserTypeOrder userTypeOrder = getById(id);
        checkNotNull(userTypeOrder, "userTypeOrder entity not found in database.");
        userTypeOrder.setRemovedBy(userId);
        userTypeOrder.setRemovedDate(LocalDateTime.now());
        return userTypeDaoImpl.update(userTypeOrder);
    }

    @Override
    public List<UserTypeOrder> listAll(String sort, String dir) {
        return userTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<UserTypeOrder> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userTypeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public UserTypeOrderModel tranformToModel(UserTypeOrder userTypeOrder) {
        UserTypeOrderModel userTypeOrderModel = null;
        if(userTypeOrder!=null){
            userTypeOrderModel = new UserTypeOrderModel();
            userTypeOrderModel.setId(userTypeOrder.getId());
            userTypeOrderModel.setName(userTypeOrder.getUserTypeOrderName());
        }
        return userTypeOrderModel;
    }

    @Override
    public int countAll() {
        return userTypeDaoImpl.countAll();
    }

    @Override
    public List<UserTypeOrder> search(MultivaluedMap<String, String> queryUserTypes, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryUserTypes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public UserTypeOrder getByIdNotRemoved(int id) {
        checkNotNull(id, "UserTypeOrder id entity must not be null");
        return userTypeDaoImpl.getByIdNotRemoved(id);
    }
    
}
