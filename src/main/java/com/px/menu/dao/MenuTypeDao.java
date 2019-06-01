package com.px.menu.dao;

import com.px.menu.entity.MenuType;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface MenuTypeDao extends GenericDao<MenuType, Integer>{
    List<MenuType> list(int offset,int limit,String sort,String dir);
    List<MenuType> listAll(String sort,String dir);    
    Integer countAll();
    MenuType getByMenuTypeCode(String menuTypeCode);
}
