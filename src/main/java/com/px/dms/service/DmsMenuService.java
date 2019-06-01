/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.dms.daoimpl.DmsMenuDaoImpl;
import com.px.dms.entity.DmsMenu;
import com.px.dms.model.DmsMenuModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DmsMenuService implements GenericService<DmsMenu, DmsMenuModel> {
    private static final Logger log = Logger.getLogger(DmsMenuService.class.getName());

    

    private final DmsMenuDaoImpl dmsMenuDaoImpl;

    public DmsMenuService() {
        this.dmsMenuDaoImpl = new DmsMenuDaoImpl();
    }

    @Override
    public DmsMenu create(DmsMenu t) {
        checkNotNull(t, "DmsMenu entity must not be null");
        checkNotNull(t.getOrderNo(), "report name must not be null");
        return dmsMenuDaoImpl.create(t);
    }

    @Override
    public DmsMenu getById(int id) {
        return dmsMenuDaoImpl.getById(id);
    }

    @Override
    public DmsMenu update(DmsMenu t) {
        checkNotNull(t, "DmsMenu entity must not be null");
        checkNotNull(t.getId(), "DmsMenu id must not be null");
        checkNotNull(t.getDmsMenuAuthorityOrderType(), "DmsMenu getDmsMenuAuthorityOrderType must not be null");
        checkNotNull(t.getUpdatedBy(), "update by must not be null");
        t.setUpdatedDate(LocalDateTime.now());
        return dmsMenuDaoImpl.update(t);
    }

    @Override
    public DmsMenu remove(int id, int userId) {
        checkNotNull(id, "DmsMenu id must not be null");
        DmsMenu dmsMenu = getById(id);
        checkNotNull(dmsMenu, "dmsMenu entity not found in database.");
        dmsMenu.setRemovedBy(userId);
        dmsMenu.setRemovedDate(LocalDateTime.now());
        return dmsMenuDaoImpl.update(dmsMenu);
    }

    @Override
    public List<DmsMenu> list(int limit, int offset, String sort, String dir) {
         checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsMenuDaoImpl.list(offset,limit,sort, dir);
    }

    @Override
    public List<DmsMenu> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsMenuDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
         return dmsMenuDaoImpl.countAll();
    }

    @Override
    public List<DmsMenu> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DmsMenuModel tranformToModel(DmsMenu t) {
         DmsMenuModel dmsMenuModel = null;
         
         if(t!=null){
            dmsMenuModel.setId(t.getId());
            dmsMenuModel.setDmsMenuName(t.getDmsMenuName());
            dmsMenuModel.setDmsMenuAuthorityDetail(t.getDmsMenuAuthorityDetail());
            dmsMenuModel.setDmsMenuType(t.getDmsMenuType());
            dmsMenuModel.setDmsMenuAuthoritySystem(t.getDmsMenuAuthoritySystem());
            dmsMenuModel.setDmsMenuAuthorityOrderType(t.getDmsMenuAuthorityOrderType());
         }
         return dmsMenuModel;
    }

    public List<DmsMenu> listAll() {
        return dmsMenuDaoImpl.findListMenu();
    }

    @Override
    public DmsMenu getByIdNotRemoved(int id) {
        checkNotNull(id, "DmsMenu id entity must not be null");
        return dmsMenuDaoImpl.getByIdNotRemoved(id);
    }
}
