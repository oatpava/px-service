
package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.wf.entity.WfCommandName;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import com.px.wf.daoimpl.WfCommandNameDaoImpl;
import com.px.wf.model.WfCommandNameModel;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WfCommandNameService implements GenericService<WfCommandName, WfCommandNameModel> {

    private static final Logger log = Logger.getLogger(WfCommandNameService.class.getName());
    private final WfCommandNameDaoImpl wfCommandNameDaoImpl;

    public WfCommandNameService() {
        this.wfCommandNameDaoImpl = new WfCommandNameDaoImpl();
    }

    @Override
    public WfCommandName create(WfCommandName wfCommandName) {
        checkNotNull(wfCommandName, "wfCommandName entity must not be null");
        checkNotNull(wfCommandName.getCreatedBy(), "create by must not be null");
        return wfCommandNameDaoImpl.create(wfCommandName);
    }

    @Override
    public WfCommandName getById(int id) {
        return wfCommandNameDaoImpl.getById(id);
    }

    @Override
    public WfCommandName getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfCommandName update(WfCommandName wfCommandName) {
        checkNotNull(wfCommandName, "wfCommandName entity must not be null");
        checkNotNull(wfCommandName.getUpdatedBy(), "update by must not be null");
        wfCommandName.setUpdatedDate(LocalDateTime.now());
        return wfCommandNameDaoImpl.update(wfCommandName); 
    }

    @Override
    public WfCommandName remove(int id, int userId) {
        checkNotNull(id, "wfCommandName id must not be null");
        WfCommandName wfCommandName = getById(id);
        checkNotNull(wfCommandName, "wfCommandName entity not found in database.");
        wfCommandName.setRemovedBy(userId);
        wfCommandName.setRemovedDate(LocalDateTime.now());
        return wfCommandNameDaoImpl.update(wfCommandName);
    }

    @Override
    public List<WfCommandName> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfCommandName> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return wfCommandNameDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfCommandName> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfCommandNameModel tranformToModel(WfCommandName wfCommandName) {
         WfCommandNameModel wfCommandNameModel = null;
        if (wfCommandName != null) {
            wfCommandNameModel = new WfCommandNameModel(wfCommandName.getId());
            wfCommandNameModel.setCreatedBy(wfCommandName.getCreatedBy());
            wfCommandNameModel.setCreatedDate(Common.localDateTimeToString(wfCommandName.getCreatedDate()));
            wfCommandNameModel.setOrderNo((float) wfCommandName.getOrderNo());
            wfCommandNameModel.setRemovedBy(wfCommandName.getRemovedBy());
            wfCommandNameModel.setRemovedDate(Common.localDateTimeToString(wfCommandName.getRemovedDate()));
            wfCommandNameModel.setUpdatedBy(wfCommandName.getUpdatedBy());
            wfCommandNameModel.setUpdatedDate(Common.localDateTimeToString(wfCommandName.getUpdatedDate()));
            wfCommandNameModel.setWfCommandNameName(wfCommandName.getWfCommandNameName());

        }
        return wfCommandNameModel;
    }

    public void delete(WfCommandName wfCommandName) {
        checkNotNull(wfCommandName, "WfCommandName must not be null");
        wfCommandNameDaoImpl.delete(wfCommandName);
    }
    
}
