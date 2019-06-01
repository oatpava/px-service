package com.px.menu.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.menu.daoimpl.MenuDaoImpl;
import com.px.menu.entity.Menu;
import com.px.menu.entity.MenuSubmoduleAuth;
import com.px.menu.entity.MenuType;
import com.px.menu.model.MenuModel;
import com.px.menu.model.MenuModel_Tree;
import com.px.share.service.GenericTreeService;
import com.px.share.util.TreeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class MenuService implements GenericTreeService<Menu, MenuModel>{
    private static final Logger LOG = Logger.getLogger(MenuService.class.getName());
    private final MenuDaoImpl menuDaoImpl;
    private final String disableShow = "Y"; //Disable but show menu
    private final String disableNotShow = "N"; //Disable then not show menu

    public MenuService() {
        this.menuDaoImpl = new MenuDaoImpl();
    }
    
    public String getDisableShowValue(){
        return disableShow;
    }
    
    public String getDisableNotShowValue(){
        return disableNotShow;
    }

    @Override
    public String generateParentKey(Menu menu) {
        String result = "";
        try{
            result = TreeUtil.generateParentKey(menu);
        } catch(Exception e){LOG.info("Exception : "+e);}
        
        return result;
    }

    @Override
    public Menu getParent(Menu menu) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getParentId(), "menu parent id must not be null");
        return menuDaoImpl.findParent(menu.getParentId());
    }

    @Override
    public List<Menu> listChild(Menu menu) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getId(), "menu id must not be null");
        return menuDaoImpl.findChild(menu.getId());
    }
    
    public List<Menu> listChild(Menu menu,String sort, String dir) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getId(), "menu id must not be null");
        return menuDaoImpl.findChild(menu.getId(),sort,dir);
    }
    
    public List<Menu> listChild(int id) {
        checkNotNull(id, "menu id must not be null");
        return menuDaoImpl.findChild(id);
    }
    
    public List<Menu> listChild(int id,String sort, String dir) {
        checkNotNull(id, "menu id must not be null");
        return menuDaoImpl.findChild(id,sort,dir);
    }

    @Override
    public List<Menu> listFromParentKey(Menu menu) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getParentId(), "menu parent id must not be null");
        ArrayList<Menu> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(menu);
            for (Object tmp: parentList){
                result.add((Menu)tmp);
            }
        }catch(Exception e){LOG.info("Exception : "+e);}
        
        return result;
    }

    @Override
    public Menu create(Menu menu) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getMenuCode(), "menu code must not be null");
        checkNotNull(menu.getMenuName(), "menu name must not be null");
        checkNotNull(menu.getMenuEngName(), "menu english name must not be null");
        checkNotNull(menu.getMenuPicture(), "menu picture must not be null");
        checkNotNull(menu.getMenuDetail(), "menu detail must not be null");
        checkNotNull(menu.getMenuFunction(), "menu function must not be null");
        checkNotNull(menu.getMenuDisableShow(), "menu disable show must not be null");
        checkNotNull(menu.getMenuDisableCSS(), "menu disable CSS must not be null");
        checkNotNull(menu.getMenuType(), "menu Type must not be null");
        checkNotNull(menu.getCreatedBy(),"create by must not be null");
        menu = menuDaoImpl.create(menu);
        
        menu.setParentKey(generateParentKey(menu));
        menu.setNodeLevel(TreeUtil.generateNodeLevel(menu));
        if(menu.getOrderNo()==0){
            menu.setOrderNo(menu.getId());
        }
        menu = update(menu);
        
        return menu;
    }

    @Override
    public Menu getById(int id) {
        checkNotNull(id, "menu id entity must not be null");
        return menuDaoImpl.getById(id);
    }

    @Override
    public Menu update(Menu menu) {
        checkNotNull(menu, "menu entity must not be null");
        checkNotNull(menu.getMenuCode(), "menu code must not be null");
        checkNotNull(menu.getMenuName(), "menu name must not be null");
        checkNotNull(menu.getMenuEngName(), "menu english name must not be null");
        checkNotNull(menu.getMenuPicture(), "menu picture must not be null");
        checkNotNull(menu.getMenuDetail(), "menu detail must not be null");
        checkNotNull(menu.getMenuFunction(), "menu function must not be null");
        checkNotNull(menu.getMenuDisableShow(), "menu disable show must not be null");
        checkNotNull(menu.getMenuDisableCSS(), "menu disable CSS must not be null");
        checkNotNull(menu.getMenuType(), "menu Type must not be null");
        checkNotNull(menu.getUpdatedBy(),"update by must not be null");
        menu.setUpdatedDate(LocalDateTime.now());
        return menuDaoImpl.update(menu);
    }

    @Override
    public Menu remove(int id, int userId) {
        checkNotNull(id, "menu id must not be null");
        Menu menu = getById(id);
        checkNotNull(menu, "menu entity not found in database.");
        menu.setRemovedBy(userId);
        menu.setRemovedDate(LocalDateTime.now());
        return menuDaoImpl.update(menu);
    }

    @Override
    public List<Menu> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");        
        return menuDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<Menu> listAll(String sort, String dir) {
        return menuDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return menuDaoImpl.countAll();
    }

    @Override
    public List<Menu> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MenuModel tranformToModel(Menu menu) {
        MenuModel menuModel = null;
        if(menu!=null){
            MenuTypeService menuTypeService = new MenuTypeService();
            
            menuModel = new MenuModel();
            menuModel.setId(menu.getId());
            menuModel.setMenuCode(menu.getMenuCode());
            menuModel.setMenuName(menu.getMenuName());
            menuModel.setMenuEngName(menu.getMenuEngName());
            menuModel.setMenuPicture(menu.getMenuPicture());
            menuModel.setMenuDetail(menu.getMenuDetail());
            menuModel.setMenuFunction(menu.getMenuFunction());
            menuModel.setMenuDisableShow(menu.getMenuDisableShow());
            menuModel.setMenuDisableCSS(menu.getMenuDisableCSS());
            menuModel.setMenuType(menuTypeService.tranformToModel(menu.getMenuType()));
            
            menuModel.setParentId(menu.getParentId());
            menuModel.setParentKey(menu.getParentKey());
            menuModel.setNodeLevel(menu.getNodeLevel());
        }
        return menuModel;
    }
    
    public List<MenuModel_Tree> tranformToModelTree(List<Menu> menuList,Integer startParent){
        List<MenuModel_Tree> result = new ArrayList();
        MenuTypeService menuTypeService = new MenuTypeService();
        for(Menu menu : menuList){
            if(menu.getParentId().equals(startParent)){
                MenuModel_Tree menuModel_Tree = new MenuModel_Tree();
                
                menuModel_Tree.setId(menu.getId());
                menuModel_Tree.setMenuCode(menu.getMenuCode());
                menuModel_Tree.setMenuName(menu.getMenuName());
                menuModel_Tree.setMenuEngName(menu.getMenuEngName());
                menuModel_Tree.setMenuPicture(menu.getMenuPicture());
                menuModel_Tree.setMenuDetail(menu.getMenuDetail());
                menuModel_Tree.setMenuFunction(menu.getMenuFunction());
                menuModel_Tree.setMenuDisableShow(menu.getMenuDisableShow());
                menuModel_Tree.setMenuDisableCSS(menu.getMenuDisableCSS());
                menuModel_Tree.setMenuType(menuTypeService.tranformToModel(menu.getMenuType()));

                menuModel_Tree.setParentId(menu.getParentId());
                menuModel_Tree.setParentKey(menu.getParentKey());
                menuModel_Tree.setNodeLevel(menu.getNodeLevel());
                
                List<MenuModel_Tree> child = tranformToModelTree(menuList,menu.getId());
                menuModel_Tree.setChild(child);
                
                result.add(menuModel_Tree);
            }
        }
        return result;
    }
    
    public List<Menu> listByMenuType(MenuType menuType,String sort, String dir){
        checkNotNull(menuType, "menuType entity must not be null");
        return menuDaoImpl.listByMenuType(menuType, sort, dir);
    }
    
    public List<Menu> listByMenuType(List<MenuType> menuType,String sort, String dir){
        checkNotNull(menuType, "menuType entity must not be null");
        return menuDaoImpl.listByMenuType(menuType, sort, dir);
    }
    
    public List<Menu> listByMenuTypeSubmoduleUserAuth(MenuType menuType,List<SubmoduleUserAuth> submoduleUserAuthList,String sort, String dir){
        checkNotNull(menuType, "menuType entity must not be null");
        checkNotNull(submoduleUserAuthList, "submoduleUserAuth list must not be null");
        List<MenuType> menuTypeList = new ArrayList();
        menuTypeList.add(menuType);
        return listByMenuTypeSubmoduleUserAuth(menuTypeList,submoduleUserAuthList,sort,dir);
    }
    
    public List<Menu> listByMenuTypeSubmoduleUserAuth(List<MenuType> menuTypeList,List<SubmoduleUserAuth> submoduleUserAuthList,String sort, String dir){
        checkNotNull(menuTypeList, "menuType list must not be null");
        checkNotNull(submoduleUserAuthList, "submoduleUserAuth list must not be null");
        
        List<Menu> result = new ArrayList();
        List<Menu> menuList = listByMenuType(menuTypeList,sort,dir);
        LOG.info("*********menu = "+menuList.size());
        //Find MenuList From SubmoduleAuth
        List<SubmoduleAuth> submoduleAuthList = new ArrayList();
        for(SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList) {
            if(submoduleUserAuth.getSubmoduleAuth()!=null){
                submoduleAuthList.add(submoduleUserAuth.getSubmoduleAuth());
            }
        }
        
        MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
        List<MenuSubmoduleAuth> menuSubmoduleAuthList = menuSubmoduleAuthService.listBySubmoduleAuth(submoduleAuthList, sort, dir);
        
        //Get Disable Parent Menu List
        List<Menu> disableMenuList = new ArrayList();
        boolean isDisable;
        for(Menu menu : menuList){
            isDisable = false;
            
            for(MenuSubmoduleAuth menuSubmoduleAuth : menuSubmoduleAuthList){
                if(menuSubmoduleAuth.getMenu().getId().equals(menu.getId())){
                    for(SubmoduleUserAuth submoduleUserAuth : submoduleUserAuthList){
                        if(submoduleUserAuth.getSubmoduleAuth().getId().equals(menuSubmoduleAuth.getSubmoduleAuth().getId())){
                            if(submoduleUserAuth.getAuthority().equals(submoduleUserAuthService.getDisableAuthValue())){
                                isDisable = true;
                                disableMenuList.add(menu);
                            }
                        }
                        
                        if(isDisable){break;}
                    }
                }
                
                if(isDisable){break;}
            }
        }
        
        //Get Menu List
        for(Menu menu : menuList){
            isDisable = false;
            for(Menu disableMenu : disableMenuList){
                if(menu.getParentKey().contains(TreeUtil.getSeparator()+disableMenu.getId()+TreeUtil.getSeparator())){
                    isDisable = true;
                    break;
                }
            }
            
            if(!isDisable || (menu.getMenuDisableShow().equalsIgnoreCase(disableShow))){
                if(!isDisable){
                    menu.setMenuDisableShow("");
                    menu.setMenuDisableCSS("");
                }
                
                result.add(menu);
            }
        }
        
        return result;
    }
    
    @Override
    public Menu getByIdNotRemoved(int id) {
        checkNotNull(id, "Menu id entity must not be null");
        return menuDaoImpl.getByIdNotRemoved(id);
    }
    
    public Menu getByMenuCode(String menuCode) {
        checkNotNull(menuCode, "Menu code must not be null");
        return menuDaoImpl.getByMenuCode(menuCode);
    }
}
