package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserParamDaoImpl;
import com.px.admin.entity.UserParam;
import com.px.admin.model.UserParamModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Pritsana
 */
public class UserParamService implements GenericService<UserParam, UserParamModel>{

    private static final Logger LOG = Logger.getLogger(UserParamService.class.getName());
    private final UserParamDaoImpl userParamDaoImpl;

    public UserParamService() {
        this.userParamDaoImpl = new UserParamDaoImpl();
    }
    
    @Override
    public UserParam create(UserParam userParam) {
        checkNotNull(userParam, "param entity must not be null");
        checkNotNull(userParam.getUser().getId(), "param user id entity must not be null");
        checkNotNull(userParam.getParamName(), "param name must not be null");
        checkNotNull(userParam.getParamValue(), "param value must not be null");
        checkNotNull(userParam.getCreatedBy(),"create by must not be null");
        userParam = userParamDaoImpl.create(userParam);
        if(userParam.getOrderNo()==0){
            userParam.setOrderNo(userParam.getId());
            userParam = update(userParam);
        }
        return userParam;
    }

    @Override
    public UserParam getById(int id) {
        checkNotNull(id, "param id entity must not be null");
        return userParamDaoImpl.getById(id);
    }

    @Override
    public UserParam getByIdNotRemoved(int id) {
        checkNotNull(id, "Param id entity must not be null");
        return userParamDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public UserParam update(UserParam userParam) {
        checkNotNull(userParam, "param entity must not be null");
        checkNotNull(userParam.getUser().getId(), "param user id entity must not be null");
        checkNotNull(userParam.getParamName(), "param name must not be null");
        checkNotNull(userParam.getParamValue(), "param value must not be null");
        checkNotNull(userParam.getUpdatedBy(),"update by must not be null");
        userParam.setUpdatedDate(LocalDateTime.now());
        return userParamDaoImpl.update(userParam);
    }

    @Override
    public UserParam remove(int id, int userId) {
        checkNotNull(id, "param id must not be null");
        UserParam userParam = getById(id);
        checkNotNull(userParam, "param entity not found in database.");
        userParam.setRemovedBy(userId);
        userParam.setRemovedDate(LocalDateTime.now());
        return userParamDaoImpl.update(userParam);
    }

    @Override
    public List<UserParam> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userParamDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<UserParam> listAll(String sort, String dir) {
        return userParamDaoImpl.listAll(sort, dir);
    }
    
    public List<UserParam> listByUserId(int userId) {
        checkNotNull(userId, "userId must not be null");
        return userParamDaoImpl.listByUserId(userId);
    }
    
    public UserParam getByParamName(String paramName,int userId) {
        checkNotNull(paramName, "paramName entity must not be null");
        return userParamDaoImpl.getByParamName(paramName,userId);
    }

    @Override
    public int countAll() {
        return userParamDaoImpl.countAll();
    }

    @Override
    public List<UserParam> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserParamModel tranformToModel(UserParam userParam) {
        UserParamModel userParamModel = null;
        if(userParam!=null){
            userParamModel = new UserParamModel();
            userParamModel.setId(userParam.getId());
            userParamModel.setUser(new UserService().tranformToModel(userParam.getUser()));
            userParamModel.setParamName(userParam.getParamName());
            userParamModel.setParamValue(userParam.getParamValue());
        }
        return userParamModel;
    }
    
}
