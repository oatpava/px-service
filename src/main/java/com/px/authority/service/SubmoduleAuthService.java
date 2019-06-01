package com.px.authority.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.Submodule;
import com.px.admin.service.SubmoduleService;
import com.px.authority.daoimpl.SubmoduleAuthDaoImpl;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.model.SubmoduleAuthModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class SubmoduleAuthService implements GenericService<SubmoduleAuth, SubmoduleAuthModel>{
    private static final Logger LOG = Logger.getLogger(SubmoduleAuthService.class.getName());
    private final SubmoduleAuthDaoImpl submoduleAuthDaoImpl;

    public SubmoduleAuthService() {
        this.submoduleAuthDaoImpl = new SubmoduleAuthDaoImpl();
    }

    @Override
    public SubmoduleAuth create(SubmoduleAuth submoduleAuth) {
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(submoduleAuth.getSubmoduleAuthCode(), "submoduleAuth code must not be null");
        checkNotNull(submoduleAuth.getSubmodule(), "submoduleAuth submodule must not be null");
        checkNotNull(submoduleAuth.getAuth(), "submoduleAuth auth must not be null");
        checkNotNull(submoduleAuth.getCreatedBy(),"create by must not be null");
        submoduleAuth = submoduleAuthDaoImpl.create(submoduleAuth);
        if(submoduleAuth.getOrderNo()==0){
            submoduleAuth.setOrderNo(submoduleAuth.getId());
            submoduleAuth = submoduleAuthDaoImpl.update(submoduleAuth);
        }
        
        return submoduleAuth;
    }

    @Override
    public SubmoduleAuth getById(int id) {
        checkNotNull(id, "submoduleAuth id entity must not be null");
        return submoduleAuthDaoImpl.getById(id);
    }

    @Override
    public SubmoduleAuth update(SubmoduleAuth submoduleAuth) {
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(submoduleAuth.getSubmoduleAuthCode(), "submoduleAuth code must not be null");
        checkNotNull(submoduleAuth.getSubmodule(), "submoduleAuth submodule must not be null");
        checkNotNull(submoduleAuth.getAuth(), "submoduleAuth auth must not be null");
        checkNotNull(submoduleAuth.getUpdatedBy(),"update by must not be null");
        submoduleAuth.setUpdatedDate(LocalDateTime.now());
        return submoduleAuthDaoImpl.update(submoduleAuth);
    }

    @Override
    public SubmoduleAuth remove(int id, int userId) {
        checkNotNull(id, "submoduleAuth id must not be null");
        SubmoduleAuth submoduleAuth = getById(id);
        checkNotNull(submoduleAuth, "submoduleAuth entity not found in database.");
        submoduleAuth.setRemovedBy(userId);
        submoduleAuth.setRemovedDate(LocalDateTime.now());
        return submoduleAuthDaoImpl.update(submoduleAuth);
    }

    @Override
    public List<SubmoduleAuth> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return submoduleAuthDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<SubmoduleAuth> listAll(String sort, String dir) {
        return submoduleAuthDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return submoduleAuthDaoImpl.countAll();
    }

    @Override
    public List<SubmoduleAuth> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubmoduleAuthModel tranformToModel(SubmoduleAuth submoduleAuth) {
        SubmoduleAuthModel submoduleAuthModel = null;
        if(submoduleAuth!=null){
            SubmoduleService submoduleService = new SubmoduleService();
            AuthService authService = new AuthService();
            
            submoduleAuthModel = new SubmoduleAuthModel();
            submoduleAuthModel.setId(submoduleAuth.getId());
            submoduleAuthModel.setSubmoduleAuthCode(submoduleAuth.getSubmoduleAuthCode());
            submoduleAuthModel.setSubmodule(submoduleService.tranformToModel(submoduleAuth.getSubmodule()));
            submoduleAuthModel.setAuth(authService.tranformToModel(submoduleAuth.getAuth()));
        }
        return submoduleAuthModel;
    }
    
    public List<SubmoduleAuth> listBySubmodule(Submodule submodule,String sort, String dir) {
        checkNotNull(submodule, "submodule entity must not be null");
        return submoduleAuthDaoImpl.listBySubmodule(submodule, sort, dir);
    }
    
    @Override
    public SubmoduleAuth getByIdNotRemoved(int id) {
        checkNotNull(id, "SubmoduleAuth id entity must not be null");
        return submoduleAuthDaoImpl.getByIdNotRemoved(id);
    }
    
    public SubmoduleAuth getBySubmoduleAuthCode(String submoduleAuthCode) {
        checkNotNull(submoduleAuthCode, "SubmoduleAuth code must not be null");
        return submoduleAuthDaoImpl.getBySubmoduleAuthCode(submoduleAuthCode);
    }
}
