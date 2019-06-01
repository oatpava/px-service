package com.px.authority.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.authority.daoimpl.AuthDaoImpl;
import com.px.authority.entity.Auth;
import com.px.authority.model.AuthModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class AuthService implements GenericService<Auth, AuthModel>{
    private static final Logger LOG = Logger.getLogger(AuthService.class.getName());
    private final AuthDaoImpl authDaoImpl;
    
    public AuthService() {
        this.authDaoImpl = new AuthDaoImpl();
    }

    @Override
    public Auth create(Auth auth) {
        checkNotNull(auth, "auth entity must not be null");
        checkNotNull(auth.getAuthName(), "auth name must not be null");
        checkNotNull(auth.getCreatedBy(),"create by must not be null");
        auth = authDaoImpl.create(auth);
        if(auth.getOrderNo()==0){
            auth.setOrderNo(auth.getId());
            auth = authDaoImpl.update(auth);
        }
        
        return auth;
    }

    @Override
    public Auth getById(int id) {
        checkNotNull(id, "auth id entity must not be null");
        return authDaoImpl.getById(id);
    }

    @Override
    public Auth update(Auth auth) {
        checkNotNull(auth, "auth entity must not be null");
        checkNotNull(auth.getAuthName(), "auth name must not be null");
        checkNotNull(auth.getUpdatedBy(),"update by must not be null");
        auth.setUpdatedDate(LocalDateTime.now());
        return authDaoImpl.update(auth);
    }

    @Override
    public Auth remove(int id, int userId) {
        checkNotNull(id, "auth id must not be null");
        Auth auth = getById(id);
        checkNotNull(auth, "auth entity not found in database.");
        auth.setRemovedBy(userId);
        auth.setRemovedDate(LocalDateTime.now());
        return authDaoImpl.update(auth);
    }

    @Override
    public List<Auth> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return authDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<Auth> listAll(String sort, String dir) {
        return authDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return authDaoImpl.countAll();
    }

    @Override
    public List<Auth> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AuthModel tranformToModel(Auth auth) {
        AuthModel authModel = null;
        if(auth!=null){
            authModel = new AuthModel();
            authModel.setId(auth.getId());
            authModel.setAuthName(auth.getAuthName());
        }
        return authModel;
    }
    
    @Override
    public Auth getByIdNotRemoved(int id) {
        checkNotNull(id, "Auth id entity must not be null");
        return authDaoImpl.getByIdNotRemoved(id);
    }
}
