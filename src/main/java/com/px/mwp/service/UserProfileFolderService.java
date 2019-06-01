package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.service.UserProfileService;
import com.px.mwp.daoimpl.UserProfileFolderDaoImpl;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.model.UserProfileFolderModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class UserProfileFolderService implements GenericService<UserProfileFolder, UserProfileFolderModel> {

    private static final Logger LOG = Logger.getLogger(UserProfileService.class.getName());
    private final UserProfileFolderDaoImpl userProfileFolderDaoImpl;

    public UserProfileFolderService() {
        this.userProfileFolderDaoImpl = new UserProfileFolderDaoImpl();
    }

    @Override
    public UserProfileFolder create(UserProfileFolder userProfileFolder) {
        checkNotNull(userProfileFolder, "userProfileFolder entity must not be null");
        checkNotNull(userProfileFolder.getCreatedBy(), "create by must not be null");
        userProfileFolder = userProfileFolderDaoImpl.create(userProfileFolder);
        if(userProfileFolder.getOrderNo()==0){
            userProfileFolder.setOrderNo(userProfileFolder.getId());
            userProfileFolder = update(userProfileFolder);
        }
        return userProfileFolder;
    }

    @Override
    public UserProfileFolder getById(int id) {
        return userProfileFolderDaoImpl.getById(id);
    }

    @Override
    public UserProfileFolder update(UserProfileFolder userProfileFolder) {
        checkNotNull(userProfileFolder, "userProfileFolder entity must not be null");
        checkNotNull(userProfileFolder.getUpdatedBy(), "update by must not be null");
        userProfileFolder.setUpdatedDate(LocalDateTime.now());
        return userProfileFolderDaoImpl.update(userProfileFolder);
    }

    @Override
    public UserProfileFolder remove(int id, int userId) {
        checkNotNull(id, "inbox id must not be null");
        UserProfileFolder userProfileFolder = getById(id);
        checkNotNull(userProfileFolder, "userProfileFolder entity not found in database.");
        userProfileFolder.setRemovedBy(userId);
        userProfileFolder.setRemovedDate(LocalDateTime.now());
        return userProfileFolderDaoImpl.update(userProfileFolder);
    }

    @Override
    public List<UserProfileFolder> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserProfileFolder> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserProfileFolder> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserProfileFolderModel tranformToModel(UserProfileFolder userProfileFolder) {
        UserProfileFolderModel userProfileFolderModel = null;
        if (userProfileFolder != null) {
            UserProfileService userProfileService = new UserProfileService();
            userProfileFolderModel = new UserProfileFolderModel();
            userProfileFolderModel.setId(userProfileFolder.getId());
            userProfileFolderModel.setCreatedBy(userProfileFolder.getCreatedBy());
            userProfileFolderModel.setCreatedDate(Common.localDateTimeToString(userProfileFolder.getCreatedDate()));
            userProfileFolderModel.setOrderNo((float) userProfileFolder.getOrderNo());
            userProfileFolderModel.setUpdatedBy(userProfileFolder.getUpdatedBy());
            userProfileFolderModel.setUpdatedDate(Common.localDateTimeToString(userProfileFolder.getUpdatedDate()));
            userProfileFolderModel.setRemovedBy(userProfileFolder.getRemovedBy());
            userProfileFolderModel.setRemovedDate(Common.localDateTimeToString(userProfileFolder.getRemovedDate()));
            userProfileFolderModel.setUserProfileFolderDetail(userProfileFolder.getUserProfileFolderDetail());
            userProfileFolderModel.setUserProfileFolderLinkId(userProfileFolder.getUserProfileFolderLinkId());
            userProfileFolderModel.setUserProfileFolderName(userProfileFolder.getUserProfileFolderName());
            userProfileFolderModel.setUserProfileFolderType(userProfileFolder.getUserProfileFolderType());
            userProfileFolderModel.setUserProfileId(userProfileFolder.getUserProfileId());
//            userProfileFolderModel.setUserProfile(userProfileService.tranformToModel(userProfileFolder.getUserProfile()));
        }
        return userProfileFolderModel;
    }
    
    public UserProfileFolderModel tranformToModel2(UserProfileFolder userProfileFolder) {
        UserProfileFolderModel userProfileFolderModel = null;
        if (userProfileFolder != null) {
            UserProfileService userProfileService = new UserProfileService();
            userProfileFolderModel = new UserProfileFolderModel();
            userProfileFolderModel.setId(userProfileFolder.getId());
            userProfileFolderModel.setCreatedBy(userProfileFolder.getCreatedBy());
            userProfileFolderModel.setCreatedDate(Common.localDateTimeToString(userProfileFolder.getCreatedDate()));
            userProfileFolderModel.setOrderNo((float) userProfileFolder.getOrderNo());
            userProfileFolderModel.setUpdatedBy(userProfileFolder.getUpdatedBy());
            userProfileFolderModel.setUpdatedDate(Common.localDateTimeToString(userProfileFolder.getUpdatedDate()));
            userProfileFolderModel.setRemovedBy(userProfileFolder.getRemovedBy());
            userProfileFolderModel.setRemovedDate(Common.localDateTimeToString(userProfileFolder.getRemovedDate()));
            userProfileFolderModel.setUserProfileFolderDetail(userProfileFolder.getUserProfileFolderDetail());
            userProfileFolderModel.setUserProfileFolderLinkId(userProfileFolder.getUserProfileFolderLinkId());
            userProfileFolderModel.setUserProfileFolderName(userProfileFolder.getUserProfileFolderName());
            userProfileFolderModel.setUserProfileFolderType(userProfileFolder.getUserProfileFolderType());
        }
        return userProfileFolderModel;
    }

    public List<UserProfileFolder> listByUserProfileId(int userProfileId) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        return userProfileFolderDaoImpl.listByUserProfileId(userProfileId);
    }
    
     public List<UserProfileFolder> listByUserProfileId(int userProfileId,String type) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        checkNotNull(type, "type must not be null");
        return userProfileFolderDaoImpl.listByUserProfileId(userProfileId,type);
    }
     
    @Override
    public UserProfileFolder getByIdNotRemoved(int id) {
        checkNotNull(id, "UserProfileFolder id entity must not be null");
        return userProfileFolderDaoImpl.getByIdNotRemoved(id);
    }
    
    public UserProfileFolder getByUserProfileId(int userProfileId, String type) {
        return userProfileFolderDaoImpl.getByUserProfileId(userProfileId, type);
    }
}
