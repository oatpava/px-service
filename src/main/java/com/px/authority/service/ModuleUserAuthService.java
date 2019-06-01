package com.px.authority.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.Module;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.ModuleService;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.authority.daoimpl.ModuleUserAuthDaoImpl;
import com.px.authority.entity.ModuleUserAuth;
import com.px.authority.model.ModuleUserAuthModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;

/**
 *
 * @author Peach
 */
public class ModuleUserAuthService implements GenericService<ModuleUserAuth, ModuleUserAuthModel>{
    private static final Logger LOG = Logger.getLogger(ModuleUserAuthService.class.getName());
    private final ModuleUserAuthDaoImpl moduleUserAuthDaoImpl;

    public ModuleUserAuthService() {
        this.moduleUserAuthDaoImpl = new ModuleUserAuthDaoImpl();
    }

    @Override
    public ModuleUserAuth create(ModuleUserAuth moduleUserAuth) {
        checkNotNull(moduleUserAuth, "moduleUserAuth entity must not be null");
        checkNotNull(moduleUserAuth.getModule(), "moduleUserAuth module must not be null");
        checkNotNull(moduleUserAuth.getAuthority(), "moduleUserAuth authority must not be null");
        checkNotNull(moduleUserAuth.getCreatedBy(),"create by must not be null");
        moduleUserAuth = moduleUserAuthDaoImpl.create(moduleUserAuth);
        if(moduleUserAuth.getOrderNo()==0){
            moduleUserAuth.setOrderNo(moduleUserAuth.getId());
            moduleUserAuth = moduleUserAuthDaoImpl.update(moduleUserAuth);
        }
        
        return moduleUserAuth;
    }

    @Override
    public ModuleUserAuth getById(int id) {
        checkNotNull(id, "moduleUserAuth id entity must not be null");
        return moduleUserAuthDaoImpl.getById(id);
    }

    @Override
    public ModuleUserAuth update(ModuleUserAuth moduleUserAuth) {
        checkNotNull(moduleUserAuth, "moduleUserAuth entity must not be null");
        checkNotNull(moduleUserAuth.getModule(), "moduleUserAuth module must not be null");
        checkNotNull(moduleUserAuth.getAuthority(), "moduleUserAuth authority must not be null");
        checkNotNull(moduleUserAuth.getUpdatedBy(),"update by must not be null");
        moduleUserAuth.setUpdatedDate(LocalDateTime.now());
        return moduleUserAuthDaoImpl.update(moduleUserAuth);
    }

    @Override
    public ModuleUserAuth remove(int id, int userId) {
        checkNotNull(id, "moduleUserAuth id must not be null");
        ModuleUserAuth moduleUserAuth = getById(id);
        checkNotNull(moduleUserAuth, "moduleUserAuth entity not found in database.");
        moduleUserAuth.setRemovedBy(userId);
        moduleUserAuth.setRemovedDate(LocalDateTime.now());
        return moduleUserAuthDaoImpl.update(moduleUserAuth);
    }

    @Override
    public List<ModuleUserAuth> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return moduleUserAuthDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<ModuleUserAuth> listAll(String sort, String dir) {
        return moduleUserAuthDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return moduleUserAuthDaoImpl.countAll();
    }

    @Override
    public List<ModuleUserAuth> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ModuleUserAuthModel tranformToModel(ModuleUserAuth moduleUserAuth) {
        ModuleUserAuthModel moduleUserAuthModel = null;
        if(moduleUserAuth!=null){
            StructureService structureService = new StructureService();
            UserProfileService userProfileService = new UserProfileService();
            ModuleService moduleService = new ModuleService();
            
            int id = -1;
            if(moduleUserAuth.getId()!=null){ id = moduleUserAuth.getId(); }
            
            moduleUserAuthModel = new ModuleUserAuthModel();
            moduleUserAuthModel.setId(id);
            moduleUserAuthModel.setStructure(structureService.tranformToModel(moduleUserAuth.getStructure()));
            moduleUserAuthModel.setUserProfile(userProfileService.tranformToModel(moduleUserAuth.getUserProfile()));
            moduleUserAuthModel.setModule(moduleService.tranformToModel(moduleUserAuth.getModule()));
            moduleUserAuthModel.setAuthority(moduleUserAuth.getAuthority());
        }
        return moduleUserAuthModel;
    }
    
    public List<ModuleUserAuth> getAuthorityByStructure(Structure structure){
        checkNotNull(structure, "structure entity must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getAuthorityByStructure(structureList);
    }
    
    public List<ModuleUserAuth> getAuthorityByStructure(List<Structure> structureList){
        checkNotNull(structureList, "structure list must not be null");
        ArrayList<ModuleUserAuth> result = new ArrayList();

        StructureService structureService = new StructureService();
        List<Structure> parentKeyList;
        List<ModuleUserAuth> moduleUserAuthList;
        boolean hasAuth;

        for(Structure structure: structureList){
            parentKeyList = structureService.listFromParentKey(structure);
            moduleUserAuthList = moduleUserAuthDaoImpl.listByStructure(parentKeyList, "structure", "asc");

            for(int i=parentKeyList.size()-1;i>=0;i--){//Get Structure Auth order from Child to Parent
                for(ModuleUserAuth moduleUserAuth:moduleUserAuthList){
                    if(moduleUserAuth.getStructure().getId().equals(parentKeyList.get(i).getId())){
                        //Check Duplicate Result
                        hasAuth = false; 
                        for(ModuleUserAuth resultModuleUserAuth : result) {
                            if(resultModuleUserAuth.getStructure().getId().equals(structure.getId()) 
                                && resultModuleUserAuth.getModule().getId().equals(moduleUserAuth.getModule().getId())){
                                hasAuth = true;
                                break;
                            }
                        }

                        if(!hasAuth){
                            ModuleUserAuth tmpModuleUserAuth = moduleUserAuth;
                            //Change to new Object if get Auth from parent
                            if(!tmpModuleUserAuth.getStructure().getId().equals(structure.getId())){
                                tmpModuleUserAuth.setId(null);
                                tmpModuleUserAuth.setUserProfile(null);
                                tmpModuleUserAuth.setStructure(structure);
                            }
                            result.add(tmpModuleUserAuth);
                        }
                    }
                }
            }
        }
            
        return result;
    }
    
    public List<ModuleUserAuth> getAuthorityByUserProfile(UserProfile userProfile){
        checkNotNull(userProfile, "userProfile entity must not be null");
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getAuthorityByUserProfile(userProfileList);
    }
    
    public List<ModuleUserAuth> getAuthorityByUserProfile(List<UserProfile> userProfileList){
        checkNotNull(userProfileList, "userProfile list must not be null");
        List<ModuleUserAuth> result = new ArrayList();
        
        List<ModuleUserAuth> moduleUserAuthList;
        List<ModuleUserAuth> structureModuleUserAuthList;
        
        boolean hasAuth;

        for(UserProfile userProfile: userProfileList){
            moduleUserAuthList = moduleUserAuthDaoImpl.listByUserProfile(userProfile, "orderNo", "asc");
            if(userProfile.getStructure()!=null){//Get Auth From Structure
                structureModuleUserAuthList = this.getAuthorityByStructure(userProfile.getStructure());
                if(!structureModuleUserAuthList.isEmpty()){
                    for(ModuleUserAuth structureModuleUserAuth : structureModuleUserAuthList){
                        //Check Duplicate
                        hasAuth = false;
                        for(ModuleUserAuth moduleUserAuth : moduleUserAuthList){
                            if(moduleUserAuth.getModule().getId().equals(structureModuleUserAuth.getModule().getId())){
                                hasAuth = true;
                                break;
                            }
                        }
                        
                        if(!hasAuth) {
                            structureModuleUserAuth.setId(null);
                            structureModuleUserAuth.setStructure(null);
                            structureModuleUserAuth.setUserProfile(userProfile);
                            
                            moduleUserAuthList.add(structureModuleUserAuth);
                        }
                    }
                }
            }
            /*if(moduleUserAuthList.isEmpty() && userProfile.getStructure()!=null){
                moduleUserAuthList = this.getAuthorityByStructure(userProfile.getStructure());
                if(!moduleUserAuthList.isEmpty()){
                    for(int i =0;i<moduleUserAuthList.size();i++){
                        moduleUserAuthList.get(i).setId(null);
                        moduleUserAuthList.get(i).setStructure(null);
                        moduleUserAuthList.get(i).setUserProfile(userProfile);
                    }
                }
            }*/

            if(!moduleUserAuthList.isEmpty()){
                for(ModuleUserAuth moduleUserAuth:moduleUserAuthList){
                    result.add(moduleUserAuth);
                }
            }
        }
            
        return result;
    }
    
    public List<Module> getModuleByStructureAuthority(Structure structure,String authority){
        checkNotNull(authority, "authority value must not be null");
        checkNotNull(structure, "structure entity must not be null");
        ArrayList<Structure> structureList = new ArrayList();
        structureList.add(structure);
        return this.getModuleByStructureAuthority(structureList,authority);
    }
    
    public List<Module> getModuleByStructureAuthority(List<Structure> structureList,String authority){
        checkNotNull(authority, "authority value must not be null");
        checkNotNull(structureList, "structure list must not be null");
        ArrayList<Module> result = new ArrayList();

        boolean noModule;
        List<ModuleUserAuth> moduleUserAuthList = this.getAuthorityByStructure(structureList);
        for(ModuleUserAuth moduleUserAuth : moduleUserAuthList){
            if(moduleUserAuth.getAuthority().equalsIgnoreCase(authority)){
                noModule = true;
                for(Module module : result){//check duplicate
                    if(module.getId().equals(moduleUserAuth.getModule().getId())){
                        noModule = false;
                        break;
                    }
                }
                
                if(noModule) {result.add(moduleUserAuth.getModule());}
            }
        }
        
        return result;
    }
    
    public List<Module> getModuleByUserProfileAuthority(UserProfile userProfile,String authority){
        checkNotNull(authority, "authority value must not be null");
        checkNotNull(userProfile, "userProfile entity must not be null");
        ArrayList<UserProfile> userProfileList = new ArrayList();
        userProfileList.add(userProfile);
        return this.getModuleByUserProfileAuthority(userProfileList,authority);
    }
    
    public List<Module> getModuleByUserProfileAuthority(List<UserProfile> userProfileList,String authority){
        checkNotNull(authority, "authority value must not be null");
        checkNotNull(userProfileList, "userProfile list must not be null");
        ArrayList<Module> result = new ArrayList();

        boolean noModule;
        List<ModuleUserAuth> moduleUserAuthList = this.getAuthorityByUserProfile(userProfileList);
        for(ModuleUserAuth moduleUserAuth : moduleUserAuthList){
            if(moduleUserAuth.getAuthority().equalsIgnoreCase(authority)){
                noModule = true;
                for(Module module : result){//check duplicate
                    if(module.getId().equals(moduleUserAuth.getModule().getId())){
                        noModule = false;
                        break;
                    }
                }
                
                if(noModule) { result.add(moduleUserAuth.getModule()); }
            }
        }
        
        return result;
    }
    
    @Override
    public ModuleUserAuth getByIdNotRemoved(int id) {
        checkNotNull(id, "ModuleUserAuth id entity must not be null");
        return moduleUserAuthDaoImpl.getByIdNotRemoved(id);
    }

}
