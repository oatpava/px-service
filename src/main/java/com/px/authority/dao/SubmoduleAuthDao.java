package com.px.authority.dao;

import com.px.admin.entity.Submodule;
import com.px.authority.entity.SubmoduleAuth;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface SubmoduleAuthDao extends GenericDao< SubmoduleAuth, Integer>{
    List<SubmoduleAuth> list(int offset,int limit,String sort,String dir);
    List<SubmoduleAuth> listAll(String sort,String dir);    
    List<SubmoduleAuth> listBySubmodule(Submodule submodule,String sort, String dir);
    Integer countAll(); 
    SubmoduleAuth getBySubmoduleAuthCode(String submoduleAuthCode);
}
