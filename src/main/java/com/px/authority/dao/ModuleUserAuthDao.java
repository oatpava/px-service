package com.px.authority.dao;

import com.px.admin.entity.Module;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.authority.entity.ModuleUserAuth;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface ModuleUserAuthDao extends GenericDao< ModuleUserAuth, Integer>{
    List<ModuleUserAuth> list(int offset,int limit,String sort,String dir);
    List<ModuleUserAuth> listAll(String sort,String dir);
    List<ModuleUserAuth> listByAuthorityStructure(String authority,Structure structure,String sort, String dir);
    List<ModuleUserAuth> listByAuthorityStructure(String authority,List<Structure> structure,String sort, String dir);
    List<ModuleUserAuth> listByAuthorityUserProfile(String authority,UserProfile userProfile,String sort, String dir);
    List<ModuleUserAuth> listByAuthorityUserProfile(String authority,List<UserProfile> userProfile,String sort, String dir);
    List<ModuleUserAuth> listByModule(Module module,String sort,String dir);
    List<ModuleUserAuth> listByModule(List<Module> module,String sort,String dir);
    List<ModuleUserAuth> listByModuleStructure(Module module,Structure structure,String sort, String dir);
    List<ModuleUserAuth> listByModuleStructure(Module module,List<Structure> structure,String sort, String dir);
    List<ModuleUserAuth> listByModuleUserProfile(Module module,UserProfile userProfile,String sort, String dir);
    List<ModuleUserAuth> listByModuleUserProfile(Module module,List<UserProfile> userProfile,String sort, String dir);
    List<ModuleUserAuth> listByStructure(Structure structure,String sort,String dir);
    List<ModuleUserAuth> listByStructure(List<Structure> structure,String sort,String dir);
    List<ModuleUserAuth> listByUserProfile(UserProfile userProfile,String sort,String dir);
    List<ModuleUserAuth> listByUserProfile(List<UserProfile> userProfile,String sort,String dir);
    Integer countAll(); 
}
