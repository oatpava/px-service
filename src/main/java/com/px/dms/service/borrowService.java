/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.dms.daoimpl.borrowDaoImpl;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.borrow;
import com.px.dms.model.borrowModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author top
 */
public class borrowService implements GenericService<borrow, borrowModel> {

    private static final Logger LOG = Logger.getLogger(borrowService.class.getName());
    private final borrowDaoImpl borrowDaoImpl;

    public borrowService() {
//        LOG.info("");
        this.borrowDaoImpl = new borrowDaoImpl();
    }

    @Override
    public borrow create(borrow t) {
        return borrowDaoImpl.create(t);
    }

    @Override
    public borrow getById(int id) {
        return borrowDaoImpl.getById(id);
    }

    @Override
    public borrow getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public List<borrow> getListIdNotRemoved(DmsDocument documen) {
        return borrowDaoImpl.getByIdNotRemoved(documen);

    }

    @Override
    public borrow update(borrow t) {
        t.setUpdatedDate(LocalDateTime.now());
        return borrowDaoImpl.update(t);
    }

    @Override
    public borrow remove(int id, int userId) {

        borrow borrow = getById(id);
        checkNotNull(borrow, "borrow entity not found in database.");
        borrow.setRemovedBy(userId);
        borrow.setRemovedDate(LocalDateTime.now());
        return borrowDaoImpl.update(borrow);
    }

    @Override
    public List<borrow> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<borrow> listAll(String sort, String dir) {
        return borrowDaoImpl.listAll(sort,dir);
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<borrow> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public borrowModel tranformToModel(borrow t, int StatusId) {
        borrowModel borrowModel = null;
        if (t != null) {
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = new UserProfile();
            borrowModel = new borrowModel();

            borrowModel.setId(t.getId());
            borrowModel.setLendDate(Common.localDateTimeToString(t.getLendDate()));
            borrowModel.setReturnDate(Common.localDateTimeToString(t.getRetuenDate()));
            borrowModel.setStatusId(StatusId);
            borrowModel.setUserHandlerId(t.getUserHandlerId());
            borrowModel.setUserLentId(t.getUserLentId());
            borrowModel.setUserReturnId(t.getUserReturnId());
            borrowModel.setRetuenDateNum(t.getNumLentDay());
            borrowModel.setLendName(t.getLendName());
            borrowModel.setReturnName(t.getReturnName());
//            

            if (t.getUserHandlerId() != 0 && t.getUserHandlerId() != -1) {
                userProfile = userProfileService.getById(t.getCreatedBy());
                borrowModel.setUserProfileHandler(userProfileService.tranformToModel(userProfile));
            }

            if (t.getUserLentId() != 0 && t.getUserLentId() != -1) {
                userProfile = userProfileService.getById(t.getCreatedBy());
                borrowModel.setUserProfileLent(userProfileService.tranformToModel(userProfile));
            }

            if (t.getUserReturnId() != 0 && t.getUserReturnId() != -1) {
                userProfile = userProfileService.getById(t.getCreatedBy());
                borrowModel.setUserProfileReturn(userProfileService.tranformToModel(userProfile));
            }

            borrowModel.setDmsDocument(DmsDocumentService.tranformToModel(t.getDmsDocument()));

        }
        return borrowModel;
    }

    public List<borrow> ListBorrow(DmsDocument document) {

        return borrowDaoImpl.history(document);
    }

    @Override
    public borrowModel tranformToModel(borrow t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkStatusDocBorrow(DmsDocument document) {
        checkNotNull(document, "document must not be null");
        return borrowDaoImpl.checkStatusDocBorrow(document);
    }
    
    

}
