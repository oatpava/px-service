package com.px.wf.service;

import com.px.wf.daoimpl.WfReserveContentNoDaoImpl;
import com.px.wf.entity.WfReserveContentNo;
import com.px.share.service.GenericService;
import com.px.wf.model.WfReserveContentNoModel;
import com.px.wf.model.WfReserveContentNoModel_groupReserveContentNoAndUser;
import com.px.admin.service.UserProfileService;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * @author Mali
 */
public class WfReserveContentNoService implements GenericService<WfReserveContentNo, WfReserveContentNoModel> {

    private static final Logger log = Logger.getLogger(WfReserveContentNoService.class.getName());
    private final WfReserveContentNoDaoImpl reserveContentNoDaoImpl;

    public WfReserveContentNoService() {
        this.reserveContentNoDaoImpl = new WfReserveContentNoDaoImpl();
    }

    @Override
    public WfReserveContentNo create(WfReserveContentNo reserveContentNo) {
        checkNotNull(reserveContentNo, "reserveContentNo entity must not be null");
        checkNotNull(reserveContentNo.getCreatedBy(), "create by must not be null");
        return reserveContentNoDaoImpl.create(reserveContentNo);
    }

    @Override
    public WfReserveContentNo getById(int id) {
        return reserveContentNoDaoImpl.getById(id);
    }

    @Override
    public WfReserveContentNo getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfReserveContentNo update(WfReserveContentNo reserveContentNo) {
        checkNotNull(reserveContentNo, "reserveContentNo entity must not be null");
        checkNotNull(reserveContentNo.getUpdatedBy(), "update by must not be null");
        reserveContentNo.setUpdatedDate(LocalDateTime.now());
        return reserveContentNoDaoImpl.update(reserveContentNo);
    }

    @Override
    public WfReserveContentNo remove(int id, int userId) {
        checkNotNull(id, "reserveContentNo id must not be null");
        WfReserveContentNo reserveContentNo = getById(id);
        checkNotNull(reserveContentNo, "reserveContentNo entity not found in database.");
        reserveContentNo.setRemovedBy(userId);
        reserveContentNo.setRemovedDate(LocalDateTime.now());
        return reserveContentNoDaoImpl.update(reserveContentNo);
    }

    @Override
    public List<WfReserveContentNo> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfReserveContentNo> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfReserveContentNo> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfReserveContentNoModel tranformToModel(WfReserveContentNo reserveContentNo) {//oat-edit
        UserProfileService userProfileService = new UserProfileService();
        WfReserveContentNoModel reserveContentNoModel = new WfReserveContentNoModel();
        if (reserveContentNo != null) {
            reserveContentNoModel.setId(reserveContentNo.getId());
            reserveContentNoModel.setCreatedBy(reserveContentNo.getCreatedBy());
            reserveContentNoModel.setCreatedDate(Common.localDateTimeToString(reserveContentNo.getCreatedDate()));
            reserveContentNoModel.setOrderNo((float) reserveContentNo.getOrderNo());
            reserveContentNoModel.setRemovedBy(reserveContentNo.getRemovedBy());
            reserveContentNoModel.setRemovedDate(Common.localDateTimeToString(reserveContentNo.getRemovedDate()));
            reserveContentNoModel.setUpdatedBy(reserveContentNo.getUpdatedBy());
            reserveContentNoModel.setUpdatedDate(Common.localDateTimeToString(reserveContentNo.getUpdatedDate()));
            reserveContentNoModel.setReserveContentNoFolderId(reserveContentNo.getReserveContentNoFolderId());
            reserveContentNoModel.setReserveContentNoContentNo(reserveContentNo.getReserveContentNoContentNo());
            //reserveContentNoModel.setReserveContentNoContentDate(Common.localDateTimeToString2(reserveContentNo.getReserveContentNoContentDate()));////
            reserveContentNoModel.setReserveContentNoContentDate(Common.localDateTimeToString3(reserveContentNo.getReserveContentNoContentDate(), reserveContentNo.getReserveContentNoContentTime()));////
            reserveContentNoModel.setReserveContentNoContentTime(reserveContentNo.getReserveContentNoContentTime());
            reserveContentNoModel.setReserveContentNoUserId(reserveContentNo.getReserveContentNoUserId());
            reserveContentNoModel.setReserveContentNoContentYear(reserveContentNo.getReserveContentNoContentYear());
            reserveContentNoModel.setReserveContentNoContentNumber(reserveContentNo.getReserveContentNoContentNumber());
            reserveContentNoModel.setReserveContentNoNote(reserveContentNo.getReserveContentNoNote());
            reserveContentNoModel.setReserveContentNoStatus(reserveContentNo.getReserveContentNoStatus());
            reserveContentNoModel.setReserveContentNoUserName(userProfileService.getById(reserveContentNo.getReserveContentNoUserId()).getUserProfileFullName());////
        }
        return reserveContentNoModel;
    }

    public WfReserveContentNoModel_groupReserveContentNoAndUser tranformToModelGroupReserveContentNoAndUser(WfReserveContentNo reserveContentNo) {
        UserProfileService userProfileService = new UserProfileService();
        WfReserveContentNoModel_groupReserveContentNoAndUser reserveContentNoModel_groupReserveContentNoAndUser = new WfReserveContentNoModel_groupReserveContentNoAndUser();
        String userName = userProfileService.getById(reserveContentNo.getReserveContentNoUserId()).getUserProfileFullName();

        if (reserveContentNo != null) {
            reserveContentNoModel_groupReserveContentNoAndUser.setId(reserveContentNo.getId());
            reserveContentNoModel_groupReserveContentNoAndUser.setCreatedBy(reserveContentNo.getCreatedBy());
            reserveContentNoModel_groupReserveContentNoAndUser.setCreatedDate(Common.localDateTimeToString(reserveContentNo.getCreatedDate()));
            reserveContentNoModel_groupReserveContentNoAndUser.setOrderNo((float) reserveContentNo.getOrderNo());
            reserveContentNoModel_groupReserveContentNoAndUser.setRemovedBy(reserveContentNo.getRemovedBy());
            reserveContentNoModel_groupReserveContentNoAndUser.setRemovedDate(Common.localDateTimeToString(reserveContentNo.getRemovedDate()));
            reserveContentNoModel_groupReserveContentNoAndUser.setUpdatedBy(reserveContentNo.getUpdatedBy());
            reserveContentNoModel_groupReserveContentNoAndUser.setUpdatedDate(Common.localDateTimeToString(reserveContentNo.getUpdatedDate()));
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoFolderId(reserveContentNo.getReserveContentNoFolderId());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoContentNo(reserveContentNo.getReserveContentNoContentNo());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoContentDate(Common.localDateTimeToString(reserveContentNo.getReserveContentNoContentDate()));
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoContentTime(reserveContentNo.getReserveContentNoContentTime());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoUserId(reserveContentNo.getReserveContentNoUserId());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoContentYear(reserveContentNo.getReserveContentNoContentYear());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoContentNumber(reserveContentNo.getReserveContentNoContentNumber());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoNote(reserveContentNo.getReserveContentNoNote());
            reserveContentNoModel_groupReserveContentNoAndUser.setReserveContentNoStatus(reserveContentNo.getReserveContentNoStatus());
            reserveContentNoModel_groupReserveContentNoAndUser.setUserName(userName);
        }
        return reserveContentNoModel_groupReserveContentNoAndUser;
    }

    public int getMaxContentNumber(int folderId, int contentYear) {
        checkNotNull(folderId, "reserveContentNo folderId must not be null");
        checkNotNull(contentYear, "reserveContentNo contentYear must not be null");
        return reserveContentNoDaoImpl.getMaxContentNumber(folderId, contentYear);
    }

    public WfReserveContentNo getContentNoByFolderId(int folderId, String contentNo) {
        checkNotNull(folderId, "reserveContentNo folderId must not be null");
        checkNotNull(contentNo, "reserveContentNo contentNo must not be null");
        return reserveContentNoDaoImpl.getContentNoByFolderId(folderId, contentNo);
    }

    //oat-edit
    public List<WfReserveContentNo> listByDate(int folderId, String dateBegin, String dateEnd, String sort, String dir) {
        checkNotNull(folderId, "folderId folderId must not be null");
        checkNotNull(sort, "sort contentNo must not be null");
        checkNotNull(dir, "dir contentNo must not be null");
        return reserveContentNoDaoImpl.listByDate(folderId, dateBegin, dateEnd, sort, dir);
    }

    //oat-add
    public List<WfReserveContentNo> list(int folderId, String sort, String dir) {
        checkNotNull(folderId, "folderId folderId must not be null");
        checkNotNull(sort, "sort contentNo must not be null");
        checkNotNull(dir, "dir contentNo must not be null");
        return reserveContentNoDaoImpl.list(folderId, sort, dir);
    }
    
    //oat-add
    public List<WfReserveContentNo> listByUser(int folderId, int userId, String sort, String dir) {
        checkNotNull(folderId, "folderId folderId must not be null");
        checkNotNull(sort, "sort contentNo must not be null");
        checkNotNull(dir, "dir contentNo must not be null");
        return reserveContentNoDaoImpl.listByUser(folderId, userId, sort, dir);
    }
    
        //oat-add
    public List<WfReserveContentNo> listCanceled(int folderId, String sort, String dir) {
        checkNotNull(folderId, "folderId folderId must not be null");
        checkNotNull(sort, "sort contentNo must not be null");
        checkNotNull(dir, "dir contentNo must not be null");
        return reserveContentNoDaoImpl.listCanceled(folderId, sort, dir);
    }

}
