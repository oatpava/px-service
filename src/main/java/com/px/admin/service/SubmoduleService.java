package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.SubmoduleDaoImpl;
import com.px.admin.entity.Submodule;
import com.px.admin.model.SubmoduleModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class SubmoduleService implements GenericService<Submodule, SubmoduleModel>{
    private static final Logger LOG = Logger.getLogger(SubmoduleService.class.getName());
    private final SubmoduleDaoImpl submoduleDaoImpl;
    
    public SubmoduleService() {
        this.submoduleDaoImpl = new SubmoduleDaoImpl();
    }

    @Override
    public Submodule create(Submodule submodule) {
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(submodule.getSubmoduleCode(), "submodule code must not be null");
        checkNotNull(submodule.getSubmoduleName(), "submodule name must not be null");
        checkNotNull(submodule.getModule(), "submodule module must not be null");
        checkNotNull(submodule.getCreatedBy(),"create by must not be null");
        submodule = submoduleDaoImpl.create(submodule);
        if(submodule.getOrderNo()==0){
            submodule.setOrderNo(submodule.getId());
            submodule = submoduleDaoImpl.update(submodule);
        }
        
        return submodule;
    }

    @Override
    public Submodule getById(int id) {
        checkNotNull(id, "submodule id entity must not be null");
        return submoduleDaoImpl.getById(id);
    }

    @Override
    public Submodule update(Submodule submodule) {
        checkNotNull(submodule, "submodule entity must not be null");
        checkNotNull(submodule.getSubmoduleCode(), "submodule code must not be null");
        checkNotNull(submodule.getSubmoduleName(), "submodule name must not be null");
        checkNotNull(submodule.getModule(), "submodule module must not be null");
        checkNotNull(submodule.getUpdatedBy(),"update by must not be null");
        submodule.setUpdatedDate(LocalDateTime.now());
        return submoduleDaoImpl.update(submodule);
    }

    @Override
    public Submodule remove(int id, int userId) {
        checkNotNull(id, "submodule id must not be null");
        Submodule submodule = getById(id);
        checkNotNull(submodule, "submodule entity not found in database.");
        submodule.setRemovedBy(userId);
        submodule.setRemovedDate(LocalDateTime.now());
        return submoduleDaoImpl.update(submodule);
    }

    @Override
    public List<Submodule> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return submoduleDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<Submodule> listAll(String sort, String dir) {
        return submoduleDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return submoduleDaoImpl.countAll();
    }

    @Override
    public List<Submodule> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubmoduleModel tranformToModel(Submodule submodule) {
        SubmoduleModel submoduleModel = null;
        if(submodule!=null){
            ModuleService moduleService = new ModuleService();
            
            submoduleModel = new SubmoduleModel();
            submoduleModel.setId(submodule.getId());
            submoduleModel.setSubmoduleCode(submodule.getSubmoduleCode());
            submoduleModel.setSubmoduleName(submodule.getSubmoduleName());
            submoduleModel.setModule(moduleService.tranformToModel(submodule.getModule()));
        }
        return submoduleModel;
    }
    
    @Override
    public Submodule getByIdNotRemoved(int id) {
        checkNotNull(id, "Submodule id entity must not be null");
        return submoduleDaoImpl.getByIdNotRemoved(id);
    }
    
    public Submodule getBySubmoduleCode(String submoduleCode) {
        checkNotNull(submoduleCode, "Submodule code must not be null");
        return submoduleDaoImpl.getBySubmoduleCode(submoduleCode);
    }
}
