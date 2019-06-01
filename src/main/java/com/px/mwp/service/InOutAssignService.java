package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.daoimpl.InOutAssignDaoImpl;
import com.px.mwp.entity.InOutAssign;
import com.px.mwp.entity.StructureFolder;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.model.InOutAssignModel;
import com.px.mwp.model.InOutAssignModel_groupDate;
import com.px.mwp.model.InOutAssignModel_groupInOutAssignAndUserName;
import com.px.mwp.model.InOutAssignModel_groupUserStructure;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author Mali
 */
public class InOutAssignService implements GenericService<InOutAssign, InOutAssignModel> {

    private static final Logger log = Logger.getLogger(InOutAssignService.class.getName());
    private final InOutAssignDaoImpl InOutAssignDaoImpl;

    public InOutAssignService() {
        this.InOutAssignDaoImpl = new InOutAssignDaoImpl();
    }

    @Override
    public InOutAssign create(InOutAssign inOutAssign) {
        checkNotNull(inOutAssign, "inOutAssign entity must not be null");
        checkNotNull(inOutAssign.getCreatedBy(), "create by must not be null");
        return InOutAssignDaoImpl.create(inOutAssign);
    }

    @Override
    public InOutAssign getById(int id) {
        return InOutAssignDaoImpl.getById(id);
    }

    @Override
    public InOutAssign getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InOutAssign update(InOutAssign inOutAssign) {
        checkNotNull(inOutAssign, "inOutAssign entity must not be null");
        checkNotNull(inOutAssign.getUpdatedBy(), "update by must not be null");
        inOutAssign.setUpdatedDate(LocalDateTime.now());
        return InOutAssignDaoImpl.update(inOutAssign);
    }

    @Override
    public InOutAssign remove(int id, int userId) {
        checkNotNull(id, "inOutAssign id must not be null");
        InOutAssign inOutAssign = getById(id);
        checkNotNull(inOutAssign, "inOutAssign entity not found in database.");
        inOutAssign.setRemovedBy(userId);
        inOutAssign.setRemovedDate(LocalDateTime.now());
        return InOutAssignDaoImpl.update(inOutAssign);
    }

    public void delete(InOutAssign inOutAssign) {
        checkNotNull(inOutAssign, "inOutAssign must not be null");
        InOutAssignDaoImpl.delete(inOutAssign);
    }

    @Override
    public List<InOutAssign> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InOutAssign> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InOutAssign> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override //oat-edit
    public InOutAssignModel tranformToModel(InOutAssign inOutAssign) {
        InOutAssignModel inOutAssignModel = new InOutAssignModel();
        inOutAssignModel.setId(inOutAssign.getId());
        inOutAssignModel.setCreatedBy(inOutAssign.getCreatedBy());
        inOutAssignModel.setCreatedDate(Common.localDateTimeToString(inOutAssign.getCreatedDate()));
        inOutAssignModel.setOrderNo((float) inOutAssign.getOrderNo());
        inOutAssignModel.setUpdatedBy(inOutAssign.getUpdatedBy());
        inOutAssignModel.setUpdatedDate(Common.localDateTimeToString(inOutAssign.getUpdatedDate()));
        inOutAssignModel.setRemovedBy(inOutAssign.getRemovedBy());
        inOutAssignModel.setRemovedDate(Common.localDateTimeToString(inOutAssign.getRemovedDate()));
        inOutAssignModel.setInOutAssignOwnerId(inOutAssign.getInOutAssignOwnerId());
        inOutAssignModel.setInOutAssignAssignId(inOutAssign.getInOutAssignAssignId());
        inOutAssignModel.setInOutAssignOwnerType(inOutAssign.getInOutAssignOwnerType());
        inOutAssignModel.setInOutAssignIsperiod(inOutAssign.getInOutAssignIsperiod());
        inOutAssignModel.setInOutAssignStartDate(Common.localDateTimeToString(inOutAssign.getInOutAssignStartDate()));
        inOutAssignModel.setInOutAssignEndDate(Common.localDateTimeToString(inOutAssign.getInOutAssignEndDate()));
        if (inOutAssign.getInOutAssignOwnerType() == 0) {
            inOutAssignModel.setOwnerName(new UserProfileService().getById(inOutAssign.getInOutAssignOwnerId()).getUserProfileFullName());
        } else {
            inOutAssignModel.setOwnerName(new StructureService().getById(inOutAssign.getInOutAssignOwnerId()).getStructureName());
        }
        return inOutAssignModel;
    }

    public InOutAssignModel_groupUserStructure tranformToModelGroupUserStructure(Structure structure) {
        InOutAssignModel_groupUserStructure inOutAssignModel_groupUserStructure = null;
        if (structure != null) {
            inOutAssignModel_groupUserStructure = new InOutAssignModel_groupUserStructure();
            inOutAssignModel_groupUserStructure.setId(structure.getId());
            inOutAssignModel_groupUserStructure.setFullName(structure.getStructureName());
            inOutAssignModel_groupUserStructure.setInOutAssignOwnerType(1);
        }
        return inOutAssignModel_groupUserStructure;
    }

    public InOutAssignModel_groupUserStructure tranformToModelGroupUserStructure(UserProfile userProfile) {
        InOutAssignModel_groupUserStructure inOutAssignModel_groupUserStructure = null;
        if (userProfile != null) {
            inOutAssignModel_groupUserStructure = new InOutAssignModel_groupUserStructure();
            inOutAssignModel_groupUserStructure.setId(userProfile.getId());
            inOutAssignModel_groupUserStructure.setFullName(userProfile.getUserProfileFullName());
            inOutAssignModel_groupUserStructure.setInOutAssignOwnerType(0);
        }
        return inOutAssignModel_groupUserStructure;
    }

    public InOutAssignModel_groupInOutAssignAndUserName tranformToModelGroupInOutAssignAndUserName(InOutAssign inOutAssign, ArrayList<InOutAssignModel_groupDate> inOutAssignModel_groupDate) {
        InOutAssignModel_groupInOutAssignAndUserName inOutAssignModel_groupInOutAssignAndUserName = new InOutAssignModel_groupInOutAssignAndUserName();
        UserProfileService userProfileService = new UserProfileService();
        //InOutAssignService inOutAssignService = new InOutAssignService();

        String userName = userProfileService.getById(inOutAssign.getInOutAssignAssignId()).getUserProfileFullName();

        inOutAssignModel_groupInOutAssignAndUserName.setId(inOutAssign.getId());
        inOutAssignModel_groupInOutAssignAndUserName.setCreatedBy(inOutAssign.getCreatedBy());
        inOutAssignModel_groupInOutAssignAndUserName.setCreatedDate(Common.localDateTimeToString(inOutAssign.getCreatedDate()));
        inOutAssignModel_groupInOutAssignAndUserName.setOrderNo((float) inOutAssign.getOrderNo());
        inOutAssignModel_groupInOutAssignAndUserName.setUpdatedBy(inOutAssign.getUpdatedBy());
        inOutAssignModel_groupInOutAssignAndUserName.setUpdatedDate(Common.localDateTimeToString(inOutAssign.getUpdatedDate()));
        inOutAssignModel_groupInOutAssignAndUserName.setRemovedBy(inOutAssign.getRemovedBy());
        inOutAssignModel_groupInOutAssignAndUserName.setRemovedDate(Common.localDateTimeToString(inOutAssign.getRemovedDate()));
        inOutAssignModel_groupInOutAssignAndUserName.setInOutAssignOwnerId(inOutAssign.getInOutAssignOwnerId());
        inOutAssignModel_groupInOutAssignAndUserName.setInOutAssignAssignId(inOutAssign.getInOutAssignAssignId());
        inOutAssignModel_groupInOutAssignAndUserName.setInOutAssignOwnerType(inOutAssign.getInOutAssignOwnerType());
        inOutAssignModel_groupInOutAssignAndUserName.setInOutAssignIsperiod(inOutAssign.getInOutAssignIsperiod());
        inOutAssignModel_groupInOutAssignAndUserName.setListInOutAssignModel_groupDate(inOutAssignModel_groupDate);
        inOutAssignModel_groupInOutAssignAndUserName.setAssignName(userName);
        return inOutAssignModel_groupInOutAssignAndUserName;
    }

    public List<InOutAssign> listByAssignId(int assignId, String sort, String dir) {
        checkNotNull(assignId, "assignId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InOutAssignDaoImpl.listByAssignId(assignId, sort, dir);
    }

    public List<InOutAssign> listByOwnerId(int ownerId, int ownerType, String sort, String dir) {
        checkNotNull(ownerId, "ownerId must not be null");
        checkNotNull(ownerType, "ownerType must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InOutAssignDaoImpl.listByOwnerId(ownerId, ownerType, sort, dir);
    }

    public List<InOutAssign> listByOwnerIdAndAssignId(int ownerId, int ownerType, int assignId, String sort, String dir) {
        checkNotNull(ownerId, "ownerId must not be null");
        checkNotNull(ownerType, "ownerType must not be null");
        checkNotNull(assignId, "assignId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InOutAssignDaoImpl.listByOwnerIdAndAssignId(ownerId, ownerType, assignId, sort, dir);
    }

    public List<InOutAssign> listAfterEndDate(LocalDateTime nowDate) {
        return InOutAssignDaoImpl.listAfterEndDate(nowDate);
    }

    //oat-add
//    public HashMap tranformToNotiModel(InOutAssign inOutAssign) {
//        HashMap notiModel = new HashMap();
//        //            notiModel.put("name", new String(""));
////            notiModel.put("num", new Integer(0));
//        InboxService inboxService = new InboxService();
//        UserProfileService userProfileService = new UserProfileService();
//        UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
//
//        if (inOutAssign.getInOutAssignOwnerType() == 0) {
//            List<Integer> listUserProfileFolderId = new ArrayList();
//            List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(inOutAssign.getInOutAssignOwnerId(), "I");
//            listUserProfileFolder.forEach((userProfileFolder) -> {
//                listUserProfileFolderId.add(userProfileFolder.getId());
//            });
//            notiModel.put("name", userProfileService.getById(inOutAssign.getInOutAssignOwnerId()).getUserProfileFullName());
//            notiModel.put("num", inboxService.countListByUserProfileFolderIdAll(listUserProfileFolderId));
//        } else {
//            List<Integer> listStructureFolderId = new ArrayList<>();
//            List<StructureFolder> listStructureFolder = structureFolderService.listByStructureId(inOutAssign.getInOutAssignOwnerId(), "I");
//            if (!listStructureFolderId.isEmpty()) {
//                for (StructureFolder structureFolder : listStructureFolderId) {
//                    listStructureFolderId.add(structureFolder.getId());
//                }
//            }
//
//        }
//
//        InOutAssignModel inOutAssignModel = new InOutAssignModel();
//        inOutAssignModel.setId(inOutAssign.getId());
//        inOutAssignModel.setCreatedBy(inOutAssign.getCreatedBy());
//        inOutAssignModel.setCreatedDate(Common.localDateTimeToString(inOutAssign.getCreatedDate()));
//        inOutAssignModel.setOrderNo((float) inOutAssign.getOrderNo());
//        inOutAssignModel.setUpdatedBy(inOutAssign.getUpdatedBy());
//        inOutAssignModel.setUpdatedDate(Common.localDateTimeToString(inOutAssign.getUpdatedDate()));
//        inOutAssignModel.setRemovedBy(inOutAssign.getRemovedBy());
//        inOutAssignModel.setRemovedDate(Common.localDateTimeToString(inOutAssign.getRemovedDate()));
//        inOutAssignModel.setInOutAssignOwnerId(inOutAssign.getInOutAssignOwnerId());
//        inOutAssignModel.setInOutAssignAssignId(inOutAssign.getInOutAssignAssignId());
//        inOutAssignModel.setInOutAssignOwnerType(inOutAssign.getInOutAssignOwnerType());
//        inOutAssignModel.setInOutAssignIsperiod(inOutAssign.getInOutAssignIsperiod());
//        inOutAssignModel.setInOutAssignStartDate(Common.localDateTimeToString(inOutAssign.getInOutAssignStartDate()));
//        inOutAssignModel.setInOutAssignEndDate(Common.localDateTimeToString(inOutAssign.getInOutAssignEndDate()));
//        inOutAssignModel.setOwnerName(new UserProfileService().getById(inOutAssign.getInOutAssignOwnerId()).getUserProfileFullName());
//
//        return notiModel;
//    }

}
