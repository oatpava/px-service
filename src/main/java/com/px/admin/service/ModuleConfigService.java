package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.ModuleConfigDaoImpl;
import com.px.admin.entity.ModuleConfig;
import com.px.admin.model.ModuleConfigModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class ModuleConfigService implements GenericService<ModuleConfig, ModuleConfigModel>{
    private static final Logger LOG = Logger.getLogger(ModuleConfigService.class.getName());
    private final ModuleConfigDaoImpl moduleConfigDaoImpl;
    
    public ModuleConfigService() {
        this.moduleConfigDaoImpl = new ModuleConfigDaoImpl();
    }

    @Override
    public ModuleConfig create(ModuleConfig moduleConfig) {
        checkNotNull(moduleConfig, "moduleConfig entity must not be null");
        checkNotNull(moduleConfig.getModuleConfigName(), "moduleConfig name must not be null");
        checkNotNull(moduleConfig.getCreatedBy(),"create by must not be null");
        moduleConfig = moduleConfigDaoImpl.create(moduleConfig);
        if(moduleConfig.getOrderNo()==0){
            moduleConfig.setOrderNo(moduleConfig.getId());
            moduleConfig = update(moduleConfig);
        }
        return moduleConfig;
    }

    @Override
    public ModuleConfig getById(int id) {
        checkNotNull(id, "moduleConfig id entity must not be null");
        return moduleConfigDaoImpl.getById(id);
    }

    @Override
    public ModuleConfig update(ModuleConfig moduleConfig) {
        checkNotNull(moduleConfig, "moduleConfig entity must not be null");
        checkNotNull(moduleConfig.getModuleConfigName(), "moduleConfig name must not be null");
        checkNotNull(moduleConfig.getUpdatedBy(),"update by must not be null");
        moduleConfig.setUpdatedDate(LocalDateTime.now());
        return moduleConfigDaoImpl.update(moduleConfig);
    }

    @Override
    public ModuleConfig remove(int id, int userId) {
        checkNotNull(id, "moduleConfig id must not be null");
        ModuleConfig moduleConfig = getById(id);
        checkNotNull(moduleConfig, "moduleConfig entity not found in database.");
        moduleConfig.setRemovedBy(userId);
        moduleConfig.setRemovedDate(LocalDateTime.now());
        return moduleConfigDaoImpl.update(moduleConfig);
    }

    @Override
    public List<ModuleConfig> listAll(String sort, String dir) {
        return moduleConfigDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<ModuleConfig> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return moduleConfigDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public ModuleConfigModel tranformToModel(ModuleConfig moduleConfig) {
        ModuleConfigModel moduleConfigModel = null;
        if(moduleConfig!=null){
            moduleConfigModel = new ModuleConfigModel();
            moduleConfigModel.setId(moduleConfig.getId());
            moduleConfigModel.setModuleConfigFunction(moduleConfig.getModuleConfigFunction());
            moduleConfigModel.setModuleConfigIcon(moduleConfig.getModuleConfigIcon());
            moduleConfigModel.setModuleConfigName(moduleConfig.getModuleConfigName());
            moduleConfigModel.setModuleConfigNameEng(moduleConfig.getModuleConfigNameEng());
            moduleConfigModel.setModuleConfigShow(moduleConfig.getModuleConfigShow());
        }
        return moduleConfigModel;
    }

    @Override
    public int countAll() {
        return moduleConfigDaoImpl.countAll();
    }

    @Override
    public List<ModuleConfig> search(MultivaluedMap<String, String> queryModuleConfigs, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryModuleConfigs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<ModuleConfig> listAllByModuleId(int moduleId, String sort, String dir) {
        return moduleConfigDaoImpl.listAllByModuleId(moduleId,sort, dir);
    }
    
    @Override
    public ModuleConfig getByIdNotRemoved(int id) {
        checkNotNull(id, "ModuleConfig id entity must not be null");
        return moduleConfigDaoImpl.getByIdNotRemoved(id);
    }
}
