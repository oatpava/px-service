package com.px.menu.dao;

import com.px.menu.entity.Menu;
import com.px.menu.entity.MenuType;
import com.px.share.dao.GenericTreeDao;
import java.util.List;

/**
 *
 * @author Peach
 */
public interface MenuDao extends GenericTreeDao<Menu, Integer>{
    List<Menu> list(int offset,int limit,String sort,String dir);
    List<Menu> listAll(String sort,String dir);
    List<Menu> listByMenuType(MenuType menuType,String sort, String dir);
    List<Menu> listByMenuType(List<MenuType> menuType,String sort, String dir);
    Integer countAll();
    Menu getByMenuCode(String menuCode);
}
