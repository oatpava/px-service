package com.px.menu.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.service.ModuleService;
import com.px.menu.daoimpl.MenuTypeDaoImpl;
import com.px.menu.entity.MenuType;
import com.px.menu.model.MenuTypeModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class MenuTypeService implements GenericService<MenuType, MenuTypeModel>{
    private static final Logger log = Logger.getLogger(MenuTypeService.class.getName());
    private final MenuTypeDaoImpl menuTypeDaoImpl;
    
    public MenuTypeService() {
        this.menuTypeDaoImpl = new MenuTypeDaoImpl();
    }
    
    @Override
    public MenuType create(MenuType menuType) {
        checkNotNull(menuType, "menuType entity must not be null");
        checkNotNull(menuType.getMenuTypeCode(), "menuType code must not be null");
        checkNotNull(menuType.getMenuTypeName(), "menuType name must not be null");
        checkNotNull(menuType.getModule(), "menuType module must not be null");
        checkNotNull(menuType.getCreatedBy(),"create by must not be null");
        menuType = menuTypeDaoImpl.create(menuType);
        if(menuType.getOrderNo()==0){
            menuType.setOrderNo(menuType.getId());
            menuType = menuTypeDaoImpl.update(menuType);
        }
        
        return menuType;
    }

    @Override
    public MenuType getById(int id) {
        checkNotNull(id, "menuType id entity must not be null");
        return menuTypeDaoImpl.getById(id);
    }

    @Override
    public MenuType update(MenuType menuType) {
        checkNotNull(menuType, "menuType entity must not be null");
        checkNotNull(menuType.getMenuTypeCode(), "menuType code must not be null");
        checkNotNull(menuType.getMenuTypeName(), "menuType name must not be null");
        checkNotNull(menuType.getModule(), "menuType module must not be null");
        checkNotNull(menuType.getUpdatedBy(),"update by must not be null");
        menuType.setUpdatedDate(LocalDateTime.now());
        return menuTypeDaoImpl.update(menuType);
    }

    @Override
    public MenuType remove(int id, int userId) {
        checkNotNull(id, "menuType id must not be null");
        MenuType menuType = getById(id);
        checkNotNull(menuType, "menuType entity not found in database.");
        menuType.setRemovedBy(userId);
        menuType.setRemovedDate(LocalDateTime.now());
        return menuTypeDaoImpl.update(menuType);
    }

    @Override
    public List<MenuType> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return menuTypeDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<MenuType> listAll(String sort, String dir) {
        return menuTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return menuTypeDaoImpl.countAll();
    }

    @Override
    public List<MenuType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MenuTypeModel tranformToModel(MenuType menuType) {
        MenuTypeModel menuTypeModel = null;
        if(menuType!=null){
            ModuleService moduleService = new ModuleService();
            
            menuTypeModel = new MenuTypeModel();
            menuTypeModel.setId(menuType.getId());
            menuTypeModel.setMenuTypeCode(menuType.getMenuTypeCode());
            menuTypeModel.setMenuTypeName(menuType.getMenuTypeName());
            menuTypeModel.setModule(moduleService.tranformToModel(menuType.getModule()));
        }
        return menuTypeModel;
    }
    
    @Override
    public MenuType getByIdNotRemoved(int id) {
        checkNotNull(id, "MenuType id entity must not be null");
        return menuTypeDaoImpl.getByIdNotRemoved(id);
    }
    
    public MenuType getByMenuTypeCode(String menuTypeCode) {
        checkNotNull(menuTypeCode, "MenuType code must not be null");
        return menuTypeDaoImpl.getByMenuTypeCode(menuTypeCode);
    }
}
