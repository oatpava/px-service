package com.px.authority.dao;

import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface SubmoduleUserAuthDao extends GenericDao< SubmoduleUserAuth, Integer>{
    List<SubmoduleUserAuth> list(int offset,int limit,String sort,String dir);
    List<SubmoduleUserAuth> listAll(String sort,String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthStructure(SubmoduleAuth submoduleAuth,Structure structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthStructure(SubmoduleAuth submoduleAuth,List<Structure> structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthStructure(List<SubmoduleAuth> submoduleAuth,Structure structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthStructure(List<SubmoduleAuth> submoduleAuth,List<Structure> structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(SubmoduleAuth submoduleAuth,UserProfile userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(SubmoduleAuth submoduleAuth,List<UserProfile> userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(List<SubmoduleAuth> submoduleAuth,UserProfile userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(List<SubmoduleAuth> submoduleAuth,List<UserProfile> userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(SubmoduleAuth submoduleAuth,Integer linkId,Structure structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(SubmoduleAuth submoduleAuth,Integer linkId,List<Structure> structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth,Integer linkId,Structure structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth,Integer linkId,List<Structure> structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth,List<Integer> linkId,List<Structure> structure,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(SubmoduleAuth submoduleAuth,Integer linkId,UserProfile userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(SubmoduleAuth submoduleAuth,Integer linkId,List<UserProfile> userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth,Integer linkId,UserProfile userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth,List<Integer> linkId,UserProfile userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth,Integer linkId,List<UserProfile> userProfile,String sort, String dir);
    List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth,List<Integer> linkId,List<UserProfile> userProfile,String sort, String dir);
    Integer countAll(); 
}
