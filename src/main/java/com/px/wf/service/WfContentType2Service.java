package com.px.wf.service;

import com.px.wf.daoimpl.WfContentType2DaoImpl;
import com.px.wf.entity.WfContentType2;
import com.px.share.service.GenericService;
import com.px.wf.model.WfContentType2Model;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.util.Common;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WfContentType2Service implements GenericService<WfContentType2, WfContentType2Model> {

    private static final Logger log = Logger.getLogger(FolderPropertyService.class.getName());
    private final WfContentType2DaoImpl wfContentType2DaoImpl;

    public WfContentType2Service() {
        this.wfContentType2DaoImpl = new WfContentType2DaoImpl();
    }

    @Override
    public WfContentType2 create(WfContentType2 wfContentType2) {
        checkNotNull(wfContentType2, "wfContentType2 entity must not be null");
        checkNotNull(wfContentType2.getCreatedBy(), "create by must not be null");
        return wfContentType2DaoImpl.create(wfContentType2);
    }

    @Override
    public WfContentType2 getById(int id) {
        return wfContentType2DaoImpl.getById(id);
    }

    @Override
    public WfContentType2 update(WfContentType2 wfContentType2) {
        checkNotNull(wfContentType2, "wfContentType2 entity must not be null");
        checkNotNull(wfContentType2.getUpdatedBy(), "update by must not be null");
        wfContentType2.setUpdatedDate(LocalDateTime.now());
        return wfContentType2DaoImpl.update(wfContentType2);
    }

    @Override
    public WfContentType2 remove(int id, int userId) {
        checkNotNull(id, "wfContentType2 id must not be null");
        WfContentType2 wfContentType2 = getById(id);
        checkNotNull(wfContentType2, "wfContentType2 entity not found in database.");
        wfContentType2.setRemovedBy(userId);
        wfContentType2.setRemovedDate(LocalDateTime.now());
        return wfContentType2DaoImpl.update(wfContentType2);
    }

    @Override
    public List<WfContentType2> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfContentType2> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return wfContentType2DaoImpl.listAll(sort, dir);
    }

    public List<WfContentType2> listByWfContentType(List<Integer> wfContentTypeId, String sort, String dir) {
        checkNotNull(wfContentTypeId, "wfContentTypeId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return wfContentType2DaoImpl.listByWfContentType(wfContentTypeId, sort, dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfContentType2> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfContentType2Model tranformToModel(WfContentType2 wfContentType2) {
        WfContentType2Model wfContentType2Model = null;
        if (wfContentType2 != null) {
            wfContentType2Model = new WfContentType2Model(wfContentType2.getId());
            wfContentType2Model.setCreatedBy(wfContentType2.getCreatedBy());
            wfContentType2Model.setCreatedDate(Common.localDateTimeToString(wfContentType2.getCreatedDate()));
            wfContentType2Model.setOrderNo((float) wfContentType2.getOrderNo());
            wfContentType2Model.setRemovedBy(wfContentType2.getRemovedBy());
            wfContentType2Model.setRemovedDate(Common.localDateTimeToString(wfContentType2.getRemovedDate()));
            wfContentType2Model.setUpdatedBy(wfContentType2.getUpdatedBy());
            wfContentType2Model.setUpdatedDate(Common.localDateTimeToString(wfContentType2.getUpdatedDate()));
            wfContentType2Model.setWfContentType2Name(wfContentType2.getContentType2Name());

        }
        return wfContentType2Model;
    }

    @Override
    public WfContentType2 getByIdNotRemoved(int id) {
        checkNotNull(id, "WfContentType2 id entity must not be null");
        return wfContentType2DaoImpl.getByIdNotRemoved(id);
    }
}
