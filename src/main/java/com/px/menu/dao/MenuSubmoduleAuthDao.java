package com.px.menu.dao;

import com.px.authority.entity.SubmoduleAuth;
import com.px.menu.entity.MenuSubmoduleAuth;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface MenuSubmoduleAuthDao extends GenericDao<MenuSubmoduleAuth, Integer>{
    List<MenuSubmoduleAuth> list(int offset,int limit,String sort,String dir);
    List<MenuSubmoduleAuth> listAll(String sort,String dir); 
    List<MenuSubmoduleAuth> listBySubmoduleAuth(SubmoduleAuth submoduleAuth,String sort, String dir);
    List<MenuSubmoduleAuth> listBySubmoduleAuth(List<SubmoduleAuth> submoduleAuth,String sort, String dir);
    Integer countAll(); 
}
