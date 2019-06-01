package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.ModuleDaoImpl;
import com.px.admin.entity.Module;
import com.px.admin.model.ModuleModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class ModuleService implements GenericService<Module, ModuleModel>{
    private static final Logger LOG = Logger.getLogger(ModuleService.class.getName());
    private final ModuleDaoImpl moduleDaoImpl;
    
    public ModuleService() {
        this.moduleDaoImpl = new ModuleDaoImpl();
    }

    @Override
    public Module create(Module module) {
        checkNotNull(module, "module entity must not be null");
        checkNotNull(module.getModuleName(), "module name must not be null");
        checkNotNull(module.getModuleNameEng(), "module name eng must not be null");
        checkNotNull(module.getModuleIcon(), "module icon must not be null");
        checkNotNull(module.getCreatedBy(),"create by must not be null");
        module = moduleDaoImpl.create(module);
        if(module.getOrderNo()==0){
            module.setOrderNo(module.getId());
            module = moduleDaoImpl.update(module);
        }
        
        return module;
    }

    @Override
    public Module getById(int id) {
        checkNotNull(id, "module id entity must not be null");
        return moduleDaoImpl.getById(id);
    }

    @Override
    public Module update(Module module) {
        checkNotNull(module, "module entity must not be null");
        checkNotNull(module.getModuleName(), "module name must not be null");
        checkNotNull(module.getModuleNameEng(), "module name eng must not be null");
        checkNotNull(module.getModuleIcon(), "module icon must not be null");
        checkNotNull(module.getUpdatedBy(),"update by must not be null");
        module.setUpdatedDate(LocalDateTime.now());
        return moduleDaoImpl.update(module);
    }

    @Override
    public Module remove(int id, int userId) {
        checkNotNull(id, "module id must not be null");
        Module module = getById(id);
        checkNotNull(module, "module entity not found in database.");
        module.setRemovedBy(userId);
        module.setRemovedDate(LocalDateTime.now());
        return moduleDaoImpl.update(module);
    }

    @Override
    public List<Module> listAll(String sort, String dir) {
        return moduleDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Module> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return moduleDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public ModuleModel tranformToModel(Module module) {
        ModuleModel moduleModel = null;
        if(module!=null){
            moduleModel = new ModuleModel();
            moduleModel.setId(module.getId());
            moduleModel.setModuleCode(module.getModuleCode());
            moduleModel.setModuleConfigInAdmin(module.getModuleConfigInAdmin());
            moduleModel.setModuleIcon(module.getModuleIcon());
            moduleModel.setModuleName(module.getModuleName());
            moduleModel.setModuleNameEng(module.getModuleNameEng());
        }
        return moduleModel;
    }

    @Override
    public int countAll() {
        return moduleDaoImpl.countAll();
    }

    @Override
    public List<Module> search(MultivaluedMap<String, String> queryModules, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryModules) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Module> listAllForConfig(String sort, String dir) {
        return moduleDaoImpl.listAllForConfig(sort, dir);
    }
    
    @Override
    public Module getByIdNotRemoved(int id) {
        checkNotNull(id, "Module id entity must not be null");
        return moduleDaoImpl.getByIdNotRemoved(id);
    }
    
    public Module getByModuleCode(String moduleCode) {
        checkNotNull(moduleCode, "Module code must not be null");
        return moduleDaoImpl.getByModuleCode(moduleCode);
    }
    
    public Module getByModuleNameEng(String moduleNameEng) {
        checkNotNull(moduleNameEng, "moduleNameEng must not be null");
        return moduleDaoImpl.getByModuleNameEng(moduleNameEng);
    }
    
    public List<Module> listByUserType(int userType,String sort, String dir){
        checkNotNull(userType, "userType must not be null");
        List<Module> listModule = moduleDaoImpl.listAll(sort, dir);
        ArrayList<Module> resultListModule = new ArrayList<>();
        for (Module module : listModule) {
            if(userType>1){
                if(!module.getModuleNameEng().equalsIgnoreCase("ADMIN")){
                    resultListModule.add(module);
                }
            }else{
                resultListModule.add(module);
            }
        }
        return resultListModule;
    }
}