package com.px.wf.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.service.UserProfileService;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.wf.daoimpl.WfRecordDaoImpl;
import com.px.wf.entity.WfRecord;
import com.px.wf.model.WfRecordModel;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author oat
 */
public class WfRecordService implements GenericService<WfRecord, WfRecordModel> {

    private final WfRecordDaoImpl WfRecordDaoImpl;

    public WfRecordService() {
        this.WfRecordDaoImpl = new WfRecordDaoImpl();
    }

    @Override
    public WfRecord create(WfRecord wfRecord) {
        checkNotNull(wfRecord, "wfRecord entity must not be null");
        checkNotNull(wfRecord.getCreatedBy(), "create by must not be null");
        return WfRecordDaoImpl.create(wfRecord);
    }

    @Override
    public WfRecord getById(int id) {
        return WfRecordDaoImpl.getById(id);
    }

    @Override
    public WfRecord update(WfRecord wfRecord) {
        checkNotNull(wfRecord, "wfRecord entity must not be null");
        checkNotNull(wfRecord.getUpdatedBy(), "update by must not be null");
        wfRecord.setUpdatedDate(LocalDateTime.now());
        return WfRecordDaoImpl.update(wfRecord);
    }

    @Override
    public WfRecord remove(int id, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfRecord getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfRecord> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfRecord> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfRecord> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfRecordModel tranformToModel(WfRecord wfRecord) {
        WfRecordModel wfRecordModel = null;
        if (wfRecord != null) {
            wfRecordModel = new WfRecordModel();
            wfRecordModel.setId(wfRecord.getId());
            wfRecordModel.setCreatedDate(Common.localDateTimeToString2(wfRecord.getCreatedDate()));
            wfRecordModel.setContentId(wfRecord.getContentId());
            wfRecordModel.setDescription(wfRecord.getDescription());
            wfRecordModel.setCreator(new UserProfileService().getById(wfRecord.getCreatedBy()).getUserProfileFullName());
        }
        return wfRecordModel;
    }

    public List<WfRecord> listByContentId(int contentId) {
        checkNotNull(contentId, "contentId must not be null");
        return WfRecordDaoImpl.listByContentId(contentId);
    }

}
