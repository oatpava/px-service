package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.wf.daoimpl.WfContentTypeDaoImpl;
import com.px.wf.entity.WfContentType;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.wf.model.WfContentTypeModel;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WfContentTypeService implements GenericService<WfContentType, WfContentTypeModel> {

    private static final Logger log = Logger.getLogger(FolderPropertyService.class.getName());
    private final WfContentTypeDaoImpl wfContentTypeDaoImpl;

    public WfContentTypeService() {
        this.wfContentTypeDaoImpl = new WfContentTypeDaoImpl();
    }

    @Override
    public WfContentType create(WfContentType wfContentType) {
        checkNotNull(wfContentType, "wfContentType entity must not be null");
        checkNotNull(wfContentType.getCreatedBy(), "create by must not be null");
        return wfContentTypeDaoImpl.create(wfContentType);
    }

    @Override
    public WfContentType getById(int id) {
       return wfContentTypeDaoImpl.getById(id);
    }

    @Override
    public WfContentType update(WfContentType wfContentType) {
        checkNotNull(wfContentType, "wfContentType entity must not be null");
        checkNotNull(wfContentType.getUpdatedBy(), "update by must not be null");
        wfContentType.setUpdatedDate(LocalDateTime.now());
        return wfContentTypeDaoImpl.update(wfContentType);
    }

    @Override
    public WfContentType remove(int id, int userId) {
        checkNotNull(id, "wfContentType id must not be null");
        WfContentType wfContentType = getById(id);
        checkNotNull(wfContentType, "wfContentType entity not found in database.");
        wfContentType.setRemovedBy(userId);
        wfContentType.setRemovedDate(LocalDateTime.now());
        return wfContentTypeDaoImpl.update(wfContentType);
    }

    @Override
    public List<WfContentType> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfContentType> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return wfContentTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfContentType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfContentTypeModel tranformToModel(WfContentType wfContentType) {
        WfContentTypeModel wfContentTypeModel = null;
        if (wfContentType != null) {
            wfContentTypeModel = new WfContentTypeModel(wfContentType.getId());
            wfContentTypeModel.setCreatedBy(wfContentType.getCreatedBy());
            wfContentTypeModel.setCreatedDate(Common.localDateTimeToString(wfContentType.getCreatedDate()));
            wfContentTypeModel.setOrderNo((float) wfContentType.getOrderNo());
            wfContentTypeModel.setRemovedBy(wfContentType.getRemovedBy());
            wfContentTypeModel.setRemovedDate(Common.localDateTimeToString(wfContentType.getRemovedDate()));
            wfContentTypeModel.setUpdatedBy(wfContentType.getUpdatedBy());
            wfContentTypeModel.setUpdatedDate(Common.localDateTimeToString(wfContentType.getUpdatedDate()));
            wfContentTypeModel.setContentTypeName(wfContentType.getContentTypeName());
            wfContentTypeModel.setContentTypeChild(wfContentType.getContentTypeChild());
        }
        return wfContentTypeModel;
    }
    
    @Override
    public WfContentType getByIdNotRemoved(int id) {
        checkNotNull(id, "WfContentType id entity must not be null");
        return wfContentTypeDaoImpl.getByIdNotRemoved(id);
    }
}
