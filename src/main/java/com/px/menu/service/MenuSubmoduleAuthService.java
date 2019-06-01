package com.px.menu.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.service.SubmoduleAuthService;
import com.px.menu.daoimpl.MenuSubmoduleAuthDaoImpl;
import com.px.menu.entity.MenuSubmoduleAuth;
import com.px.menu.model.MenuSubmoduleAuthModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class MenuSubmoduleAuthService implements GenericService<MenuSubmoduleAuth, MenuSubmoduleAuthModel>{
    private static final Logger log = Logger.getLogger(MenuSubmoduleAuthService.class.getName());
    private final MenuSubmoduleAuthDaoImpl menuSubmoduleAuthDaoImpl;

    public MenuSubmoduleAuthService() {
        this.menuSubmoduleAuthDaoImpl = new MenuSubmoduleAuthDaoImpl();
    }

    @Override
    public MenuSubmoduleAuth create(MenuSubmoduleAuth menuSubmoduleAuth) {
        checkNotNull(menuSubmoduleAuth, "menuSubmoduleAuth entity must not be null");
        checkNotNull(menuSubmoduleAuth.getMenu(), "menuSubmoduleAuth menu must not be null");
        checkNotNull(menuSubmoduleAuth.getSubmoduleAuth(), "menuSubmoduleAuth submoduleAuth must not be null");
        checkNotNull(menuSubmoduleAuth.getCreatedBy(),"create by must not be null");
        menuSubmoduleAuth = menuSubmoduleAuthDaoImpl.create(menuSubmoduleAuth);
        if(menuSubmoduleAuth.getOrderNo()==0){
            menuSubmoduleAuth.setOrderNo(menuSubmoduleAuth.getId());
            menuSubmoduleAuth = menuSubmoduleAuthDaoImpl.update(menuSubmoduleAuth);
        }
        
        return menuSubmoduleAuth;
    }

    @Override
    public MenuSubmoduleAuth getById(int id) {
        checkNotNull(id, "menuSubmoduleAuth id entity must not be null");
        return menuSubmoduleAuthDaoImpl.getById(id);
    }

    @Override
    public MenuSubmoduleAuth update(MenuSubmoduleAuth menuSubmoduleAuth) {
        checkNotNull(menuSubmoduleAuth, "menuSubmoduleAuth entity must not be null");
        checkNotNull(menuSubmoduleAuth.getMenu(), "menuSubmoduleAuth menu must not be null");
        checkNotNull(menuSubmoduleAuth.getSubmoduleAuth(), "menuSubmoduleAuth submoduleAuth must not be null");
        checkNotNull(menuSubmoduleAuth.getUpdatedBy(),"update by must not be null");
        menuSubmoduleAuth.setUpdatedDate(LocalDateTime.now());
        return menuSubmoduleAuthDaoImpl.update(menuSubmoduleAuth);
    }

    @Override
    public MenuSubmoduleAuth remove(int id, int userId) {
        checkNotNull(id, "menuSubmoduleAuth id must not be null");
        MenuSubmoduleAuth menuSubmoduleAuth = getById(id);
        checkNotNull(menuSubmoduleAuth, "menuSubmoduleAuth entity not found in database.");
        menuSubmoduleAuth.setRemovedBy(userId);
        menuSubmoduleAuth.setRemovedDate(LocalDateTime.now());
        return menuSubmoduleAuthDaoImpl.update(menuSubmoduleAuth);
    }

    @Override
    public List<MenuSubmoduleAuth> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return menuSubmoduleAuthDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<MenuSubmoduleAuth> listAll(String sort, String dir) {
        return menuSubmoduleAuthDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return menuSubmoduleAuthDaoImpl.countAll();
    }

    @Override
    public List<MenuSubmoduleAuth> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MenuSubmoduleAuthModel tranformToModel(MenuSubmoduleAuth menuSubmoduleAuth) {
        MenuSubmoduleAuthModel menuSubmoduleAuthModel = null;
        if(menuSubmoduleAuth!=null){
            MenuService menuService = new MenuService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            
            menuSubmoduleAuthModel = new MenuSubmoduleAuthModel();
            menuSubmoduleAuthModel.setId(menuSubmoduleAuth.getId());
            menuSubmoduleAuthModel.setMenu(menuService.tranformToModel(menuSubmoduleAuth.getMenu()));
            menuSubmoduleAuthModel.setSubmoduleAuth(submoduleAuthService.tranformToModel(menuSubmoduleAuth.getSubmoduleAuth()));
        }
        return menuSubmoduleAuthModel;
    }
    
    public List<MenuSubmoduleAuth> listBySubmoduleAuth(SubmoduleAuth submoduleAuth,String sort, String dir){
        checkNotNull(submoduleAuth, "SubmoduleAuth entity must not be null");
        return menuSubmoduleAuthDaoImpl.listBySubmoduleAuth(submoduleAuth, sort, dir);
    }
    
    public List<MenuSubmoduleAuth> listBySubmoduleAuth(List<SubmoduleAuth> submoduleAuth,String sort, String dir){
        checkNotNull(submoduleAuth, "SubmoduleAuth entity must not be null");
        return menuSubmoduleAuthDaoImpl.listBySubmoduleAuth(submoduleAuth, sort, dir);
    }
    
    @Override
    public MenuSubmoduleAuth getByIdNotRemoved(int id) {
        checkNotNull(id, "MenuSubmoduleAuth id entity must not be null");
        return menuSubmoduleAuthDaoImpl.getByIdNotRemoved(id);
    }
}
