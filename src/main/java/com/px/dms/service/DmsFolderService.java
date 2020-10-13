/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.entity.LogData;
import com.px.admin.entity.UserProfile;
import com.px.share.service.LogDataService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.model.AuthEnableDisableIdListModel;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.dms.daoimpl.DmsFolderImpl;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsFolder;
import com.px.dms.model.DmsFolderModel;
import com.px.dms.model.DmsSearchModel;
import com.px.share.service.GenericTreeService;
import com.px.share.util.Common;
import com.px.share.util.TreeUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
//222
public class DmsFolderService implements GenericTreeService<DmsFolder, DmsFolderModel> {

    private static final Logger LOG = Logger.getLogger(DmsFolderService.class.getName());
    private final DmsFolderImpl dmsFolderDaoImpl;

    public DmsFolderService() {
//        LOG.info("");
        this.dmsFolderDaoImpl = new DmsFolderImpl();
    }

    @Override
    public DmsFolder create(DmsFolder folder) {
        checkNotNull(folder, "folder entity must not be null");
        checkNotNull(folder.getDmsFolderParentId(), "ParentId id must not be null");
//        checkNotNull(folder.getDmsFolderParentType(), "folderParentType must not be null");
        checkNotNull(folder.getDocumentTypeId(), "DocumentTypeId must not be null");
        checkNotNull(folder.getCreatedBy(), "create by must not be null");
        return dmsFolderDaoImpl.createFolder(folder);
    }

    @Override
    public DmsFolder getById(int id) {
        return dmsFolderDaoImpl.getById(id);
    }

    @Override
    public DmsFolder update(DmsFolder t) {
        checkNotNull(t, "folder entity must not be null");
        checkNotNull(t.getDocumentTypeId(), "folder DocumentTypeId must not be null");

        t.setUpdatedDate(LocalDateTime.now());
        return dmsFolderDaoImpl.update(t);
    }

    @Override
    public DmsFolder remove(int id, int userId) {
        checkNotNull(id, "folder id must not be null");
        DmsFolder folder = getById(id);
        checkNotNull(folder, "folder entity not found in database.");
        folder.setRemovedBy(userId);
        folder.setRemovedDate(LocalDateTime.now());
        return dmsFolderDaoImpl.update(folder);
    }

    @Override
    public List<DmsFolder> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsFolderDaoImpl.list(offset, limit, sort, dir);

    }

    @Override
    public List<DmsFolder> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsFolderDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return dmsFolderDaoImpl.countAll();
    }

    public int countAll(int ParentId) {
        return dmsFolderDaoImpl.countAll(ParentId);
    }

    public int countAll(int folderId, int offset, int limit, AuthEnableDisableIdListModel temp) {
        return dmsFolderDaoImpl.countAll(folderId, offset, limit, temp);

    }

    @Override
    public List<DmsFolder> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DmsFolderModel tranformToModel(DmsFolder t) {
        DmsFolderModel DmsFolderModel = null;

//        System.out.println("DmsFolder = "+ t.getId());
        if (t != null) {
//            DmsFolderModel = new DmsFolderModel(t.getId(),t.getDmsFolderType(),t.getDmsFolderParentId(),t.getDmsFolderParentType(),t.getDmsFolderParentKey(),t.get);
            DmsFolderModel = new DmsFolderModel();
            DmsFolderModel.setCreateDate(Common.localDateTimeToString(t.getCreatedDate()));
            DmsFolderModel.setDocumentTypeId(t.getDocumentTypeId());
            DmsFolderModel.setFolderDescription(t.getDmsFolderDescription());
            DmsFolderModel.setFolderName(t.getDmsFolderName());
            DmsFolderModel.setFolderNodeLevel(t.getDmsFolderNodeLevel());
            DmsFolderModel.setFolderOrderId(t.getDmsFolderOrderId());
            DmsFolderModel.setFolderParentId(t.getDmsFolderParentId());
            DmsFolderModel.setFolderParentKey(t.getDmsFolderParentKey());
            DmsFolderModel.setFolderParentType(t.getDmsFolderParentType());
            DmsFolderModel.setFolderType(t.getDmsFolderType());
            DmsFolderModel.setFolderTypeExpire(t.getDmsFolderTypeExpire());
            DmsFolderModel.setFolderTypeExpireNumber(t.getDmsFolderTypeExpireNumber());
            DmsFolderModel.setId(t.getId());
            DmsFolderModel.setCreateBy(t.getCreatedBy());
            DmsFolderModel.setRemoveDate(Common.localDateTimeToString(t.getRemovedDate()));
            DmsFolderModel.setRemoveBy(t.getRemovedBy());
            DmsFolderModel.setUpDateBy(t.getCreatedBy());
            DmsFolderModel.setUpDateDate(Common.localDateTimeToString(t.getUpdatedDate()));
            DmsFolderModel.setIcon(t.getIcon());
            DmsFolderModel.setIconColor(t.getIconColor());
            DmsFolderModel.setDmsEmailUserPreExpire(t.getDmsEmailUserPreExpire());
            DmsFolderModel.setDmsSearchId(t.getDmsSearchId());
            DmsFolderModel.setFullPathName(t.getFullPathName());

        };
//         System.out.println(DmsFolderModel);
        return DmsFolderModel;

    }

    public DmsFolder createUpdateFolder(DmsFolder folder) {
        checkNotNull(folder, "folder entity must not be null");
        checkNotNull(folder.getDmsFolderOrderId(), "FolderOrderId entity must not be null");
        checkNotNull(folder.getDmsFolderParentKey(), "FolderParentKey entity must not be null");

        return dmsFolderDaoImpl.createUpdateFolder(folder);
    }

    public boolean orderFolder(List<DmsFolder> listFolderOrder) {
        checkNotNull(listFolderOrder, "listFolderOrder entity must not be null");

        return dmsFolderDaoImpl.orderFolder(listFolderOrder);
    }

    public boolean updateOrderFolder(int folderId, float orderId) {
        checkNotNull(folderId, "folderId entity must not be null");
        checkNotNull(orderId, "orderId entity must not be null");

        return dmsFolderDaoImpl.updateOrderFolder(folderId, orderId);
    }

    public List<DmsFolder> listFolderByparenID(int folderId, int offset, int limit) {
        checkNotNull(folderId, "folderId  must not be null");
        return dmsFolderDaoImpl.findListByFolderParentId(folderId, offset, limit);
    }

    public List<DmsFolder> listFolderByparenKey(String dmsFolderParentKey, String dmsFolderName, String dmsFolderDescription) {
        checkNotNull(dmsFolderParentKey, "dmsFolderParentKey  must not be null");
        return dmsFolderDaoImpl.findListByParentKey(dmsFolderParentKey, dmsFolderName, dmsFolderDescription);
    }

    public boolean moveFolder(DmsFolder folderMove) {
        checkNotNull(folderMove, "folder entity must not be null");
        return dmsFolderDaoImpl.moveFolder(folderMove);
    }

    public int copyFolder(DmsFolder folderCopy) {
        checkNotNull(folderCopy, "folderCopy entity must not be null");
        checkNotNull(folderCopy.getUpdatedBy(), "user update  must not be null");
        return dmsFolderDaoImpl.copyFolder(folderCopy);
    }

    public List<DmsFolder> findListParentByFolderId(int folderId) {
        checkNotNull(folderId, "folder id must not be null");

        return dmsFolderDaoImpl.findListParentByFolderId(folderId);

    }

    @Override
    public DmsFolder getByIdNotRemoved(int id) {
        checkNotNull(id, "DmsFolder id entity must not be null");
        return dmsFolderDaoImpl.getByIdNotRemoved(id);
    }

    public String[] getListChildFolder(int folderId) {
        checkNotNull(folderId, "folder id must not be null");

        return dmsFolderDaoImpl.getListChildFolder(folderId);

    }

    public List<DmsFolder> getListChildFolderObj(int folderId) {
        checkNotNull(folderId, "folder id must not be null");

        return dmsFolderDaoImpl.getListChildFolderObj(folderId);

    }

    public List<DmsFolder> getListChildFolderList(int folderId) {
        checkNotNull(folderId, "folder id must not be null");

        return dmsFolderDaoImpl.getListChildFolderList(folderId);

    }

    @Override
    public String generateParentKey(DmsFolder t) {
        String result = "";
        try {
            result = TreeUtil.generateParentKey(t);
        } catch (Exception e) {
            LOG.info("Exception : " + e);
        }

        return result;
    }

    @Override
    public DmsFolder getParent(DmsFolder t) {
        checkNotNull(t, "menu entity must not be null");
        checkNotNull(t.getParentId(), "menu parent id must not be null");
        return dmsFolderDaoImpl.findParent(t.getParentId());

    }

    @Override
    public List<DmsFolder> listChild(DmsFolder t) {
        checkNotNull(t, "menu entity must not be null");
        checkNotNull(t.getId(), "menu id must not be null");
        return dmsFolderDaoImpl.findChild(t.getId());
    }

    @Override
    public List<DmsFolder> listFromParentKey(DmsFolder t) {
        checkNotNull(t, "menu entity must not be null");
        checkNotNull(t.getParentId(), "menu parent id must not be null");
        ArrayList<DmsFolder> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(t);
            for (Object tmp : parentList) {
                result.add((DmsFolder) tmp);
            }
        } catch (Exception e) {
            LOG.info("Exception : " + e);
        }

        return result;
    }

    public DmsFolder createFolderS(DmsFolder folder) {
        checkNotNull(folder, "folder entity must not be null");
        return dmsFolderDaoImpl.create(folder);
    }

    public String getFullPathName(int folderId) {
        DmsFolder temp = dmsFolderDaoImpl.getById(folderId);

        String parent = temp.getDmsFolderParentKey();

        String listId = "";
        String[] valueFolderParent = parent.split("฿");

        for (int i = 1; i < valueFolderParent.length; i++) {

            DmsFolder temp2 = dmsFolderDaoImpl.getById(Integer.parseInt(valueFolderParent[i]));

            if (listId.length() == 0) {
                listId = temp2.getDmsFolderName();
            } else {
                listId = listId + '/' + temp2.getDmsFolderName();
            }
        }

        return listId;
    }

    public String getFullPathName(String FolderParent) {

        String parent = FolderParent;
        String listId = "";
        String[] valueFolderParent = parent.split("฿");

//        for (String FolderId : valueFolderParent) {
//            System.out.println("getFullPathName = "+FolderId);
//        }
        for (int i = 1; i < valueFolderParent.length; i++) {
//            System.out.println(" ,, "+valueFolderParent[i]);
            DmsFolder temp2 = dmsFolderDaoImpl.getById(Integer.parseInt(valueFolderParent[i]));
//            System.out.println("temp2 = "+temp2.getDmsFolderName());
            if (listId.length() == 0) {
                listId = temp2.getDmsFolderName();
            } else {
                listId = listId + '/' + temp2.getDmsFolderName();
            }
        }
//        System.out.println("listId = "+listId);
        return listId;
    }

    public DmsFolder saveLogForCreate(DmsFolder dmsFolder, String clientIp) {
        //For Create Log when create MeetingRoom
//        System.out.println("saveLogForCreate 33333333");
        String logDescription = this.generateLogForCreateEntity(dmsFolder);
//        System.out.println("saveLogForCreate generateLogForCreateEntity");
        LogData logData = new LogData();
        System.out.println(dmsFolder.getCreatedBy());
        logData.setCreatedBy(dmsFolder.getCreatedBy());
//        System.out.println("saveLogForCreate --------2");
        logData.setDescription(logDescription);
        logData.setEntityName(dmsFolder.getClass().getName());
        logData.setLinkId(dmsFolder.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_DMS);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
//        System.out.println("saveLogForCreate 444444444");
        return dmsFolder;
    }

    public DmsFolder saveLogForUpdate(DmsFolder dmsFolderOld, DmsFolder dmsFolderNew, String clientIp) {
        //For Create Log when update MeetingRoom
        String logDescription = this.generateLogForUpdateEntity(dmsFolderOld, dmsFolderNew);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsFolderNew.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsFolderNew.getClass().getName());
        logData.setLinkId(dmsFolderNew.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsFolderNew;
    }

    public DmsFolder saveLogForRemove(DmsFolder dmsFolder, String clientIp) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForRemoveEntity(dmsFolder);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsFolder.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsFolder.getClass().getName());
        logData.setLinkId(dmsFolder.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return dmsFolder;
    }

    public DmsFolder saveLogForCopy(DmsFolder dmsFolder, int oldParentFolderId, String clientIp, int userId) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForCopyEntity(dmsFolder, oldParentFolderId);
        LogData logData = new LogData();
        logData.setCreatedBy(userId);
        logData.setDescription(logDescription);
        logData.setEntityName(dmsFolder.getClass().getName());
        logData.setLinkId(dmsFolder.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return dmsFolder;
    }

    public DmsFolder saveLogForMove(DmsFolder dmsFolder, int oldParentFolderId, String clientIp, int userId) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForMoveEntity(dmsFolder, oldParentFolderId);
        LogData logData = new LogData();
        logData.setCreatedBy(userId);
        logData.setDescription(logDescription);
        logData.setEntityName(dmsFolder.getClass().getName());
        logData.setLinkId(dmsFolder.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsFolder;
    }

    private String generateLogForCreateEntity(DmsFolder dmsFolder) {

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        DmsFolderService dmsFolderService = new DmsFolderService();
        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
        StringBuilder dmsFoldertLog = new StringBuilder();
        dmsFoldertLog.append("ชื่อที่เก็บเอกสาร : ");
        dmsFoldertLog.append(Common.noNull(dmsFolder.getDmsFolderName(), ""));
        dmsFoldertLog.append(" ชื่อประเภทที่เก็บเอกสาร : ");
        dmsFoldertLog.append(Common.noNull(documentTypeService.getById(dmsFolder.getDocumentTypeId()).getDocumentTypeName(), ""));
        dmsFoldertLog.append(" ชื่อคนที่สร้าง : ");
        dmsFoldertLog.append(Common.noNull(userProfileService.getById(dmsFolder.getCreatedBy()).getUserProfileFullName(), ""));
        dmsFoldertLog.append(" เวลาที่สร้าง : ");
        dmsFoldertLog.append(Common.localDateTimeToString(dmsFolder.getCreatedDate()));
        return dmsFoldertLog.toString();
    }

    private String generateLogForUpdateEntity(DmsFolder dmsFolderNew, DmsFolder dmsFolderOld) {
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        DmsFolderService dmsFolderService = new DmsFolderService();
//        DocumentTypeService documentTypeService = new DocumentTypeService();
//        UserProfileService userProfileService = new UserProfileService();
        StringBuilder dmsFoldertLog = new StringBuilder();

        if (dmsFolderNew.getDmsFolderName() != null) {
            if (dmsFolderOld.getDmsFolderName() == null) {
                dmsFolderOld.setDmsFolderName(" ");
            }

            dmsFoldertLog.append("ชื่อที่เก็บเอกสาร : ");
            dmsFoldertLog.append(dmsFolderOld.getDmsFolderName());
            dmsFoldertLog.append("เป็น :");
            dmsFoldertLog.append(dmsFolderNew.getDmsFolderName());

        }

        if (dmsFolderNew.getDmsFolderDescription() != null) {
            if (dmsFolderOld.getDmsFolderDescription() == null) {
                dmsFolderOld.setDmsFolderDescription(" ");
            }
            dmsFoldertLog.append("คำอธิบาย : ");
            dmsFoldertLog.append(dmsFolderOld.getDmsFolderDescription());
            dmsFoldertLog.append("เป็น :");
            dmsFoldertLog.append(dmsFolderNew.getDmsFolderDescription());

        }

        if (dmsFolderNew.getDmsFolderTypeExpire() != null) {
            if (dmsFolderOld.getDmsFolderTypeExpire() == null) {
                dmsFolderOld.setDmsFolderTypeExpire(" ");
            }

            dmsFoldertLog.append("ชนิดวันหมดอายุ : ");
            dmsFoldertLog.append(dmsFolderOld.getDmsFolderTypeExpire());
            dmsFoldertLog.append("เป็น :");
            dmsFoldertLog.append(dmsFolderNew.getDmsFolderTypeExpire());

        }

        if (dmsFolderOld.getDmsFolderTypeExpireNumber() != (dmsFolderNew.getDmsFolderTypeExpireNumber())) {

            dmsFoldertLog.append("เวลาวันหมดอายุ : ");
            dmsFoldertLog.append(dmsFolderOld.getDmsFolderTypeExpireNumber());
            dmsFoldertLog.append("เป็น :");
            dmsFoldertLog.append(dmsFolderNew.getDmsFolderTypeExpireNumber());

        }
        return dmsFoldertLog.toString();
    }

    private String generateLogForRemoveEntity(DmsFolder dmsFolder) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        DmsFolderService dmsFolderService = new DmsFolderService();
//        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
        StringBuilder dmsFoldertLog = new StringBuilder();
        dmsFoldertLog.append("ชื่อที่เก็บเอกสาร : ");
        dmsFoldertLog.append(Common.noNull(dmsFolder.getDmsFolderName(), ""));
        dmsFoldertLog.append(" ชื่อคนที่ลบ : ");
        dmsFoldertLog.append(Common.noNull(userProfileService.getById(dmsFolder.getRemovedBy()).getUserProfileFullName(), ""));
        dmsFoldertLog.append(" เวลาที่ลบ : ");
        dmsFoldertLog.append(Common.localDateTimeToString(dmsFolder.getRemovedDate()));
        return dmsFoldertLog.toString();

    }

    private String generateLogForCopyEntity(DmsFolder dmsFolder, int oldParentFolderId) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
//        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อที่เก็บเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsFolder.getDmsFolderName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่คัดลอก : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsFolder.getCreatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่คัดลอก : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsFolder.getCreatedDate()));
        dmsDocumentLog.append(" คัดลอกจาก : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(oldParentFolderId).getDmsFolderName(), ""));
        dmsDocumentLog.append(" ไป : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsFolder.getDmsFolderParentId()).getDmsFolderName(), ""));
        return dmsDocumentLog.toString();
    }

    private String generateLogForMoveEntity(DmsFolder dmsFolder, int oldParentFolderId) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
//        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อที่เก็บเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsFolder.getDmsFolderName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่ย้าย : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsFolder.getCreatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่ย้าย : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsFolder.getCreatedDate()));
        dmsDocumentLog.append(" ย้ายจาก: ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(oldParentFolderId).getDmsFolderName(), ""));
        dmsDocumentLog.append(" ไป : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsFolder.getDmsFolderParentId()).getDmsFolderName(), ""));
        return dmsDocumentLog.toString();
    }

    public DmsFolder getByname(String folderName) {
        checkNotNull(folderName, "folderName must not be null");

        return dmsFolderDaoImpl.getByname(folderName);

    }

    public DmsFolderModel tranformToModel2(DmsFolder t) {
        DmsFolderModel DmsFolderModel = null;

//        System.out.println("DmsFolder = "+ t.getId());
        if (t != null) {
            UserProfile userProfile = new UserProfile();
            UserProfileService userProfileService = new UserProfileService();
//            DmsFolderModel = new DmsFolderModel(t.getId(),t.getDmsFolderType(),t.getDmsFolderParentId(),t.getDmsFolderParentType(),t.getDmsFolderParentKey(),t.get);
            DmsFolderModel = new DmsFolderModel();
            DmsFolderModel.setCreateDate(Common.localDateTimeToString(t.getCreatedDate()));
            DmsFolderModel.setDocumentTypeId(t.getDocumentTypeId());
            DmsFolderModel.setFolderDescription(t.getDmsFolderDescription());
            DmsFolderModel.setFolderName(t.getDmsFolderName());
            DmsFolderModel.setFolderNodeLevel(t.getDmsFolderNodeLevel());
            DmsFolderModel.setFolderOrderId(t.getDmsFolderOrderId());
            DmsFolderModel.setFolderParentId(t.getDmsFolderParentId());
            DmsFolderModel.setFolderParentKey(t.getDmsFolderParentKey());
            DmsFolderModel.setFolderParentType(t.getDmsFolderParentType());
            DmsFolderModel.setFolderType(t.getDmsFolderType());
            DmsFolderModel.setFolderTypeExpire(t.getDmsFolderTypeExpire());
            DmsFolderModel.setFolderTypeExpireNumber(t.getDmsFolderTypeExpireNumber());
            DmsFolderModel.setId(t.getId());
            DmsFolderModel.setCreateBy(t.getCreatedBy());
            DmsFolderModel.setRemoveDate(Common.localDateTimeToString(t.getRemovedDate()));
            DmsFolderModel.setRemoveBy(t.getRemovedBy());
            DmsFolderModel.setUpDateBy(t.getCreatedBy());
            DmsFolderModel.setUpDateDate(Common.localDateTimeToString(t.getUpdatedDate()));
            DmsFolderModel.setIcon(t.getIcon());
            DmsFolderModel.setIconColor(t.getIconColor());
            DmsFolderModel.setDmsEmailUserPreExpire(t.getDmsEmailUserPreExpire());

            if (t.getCreatedBy() != 0 && t.getCreatedBy() == -1) {
                userProfile = userProfileService.getById(t.getCreatedBy());
                DmsFolderModel.setUserProfileCreate(userProfileService.tranformToModel(userProfile));
            }
            if (t.getUpdatedBy() != 0 && t.getUpdatedBy() == -1) {
                userProfile = userProfileService.getById(t.getUpdatedBy());
                DmsFolderModel.setUserProfileUpdate(userProfileService.tranformToModel(userProfile));

            }
            if (t.getUpdatedBy() == 0 && t.getUpdatedBy() == -1) {
                userProfile.setUserProfileFullName("");
                DmsFolderModel.setUserProfileUpdate(userProfileService.tranformToModel(userProfile));

            }

            if (t.getDmsUserPreExpire() != 0) {
                userProfile = userProfileService.getById(t.getDmsUserPreExpire());
                DmsFolderModel.setDmsUserProfilePreExpire(userProfileService.tranformToModel(userProfile));

            }

        };
//         System.out.println(DmsFolderModel);
        return DmsFolderModel;

    }

    public int duplicateAdocFolder(int parentId, String nameFolder) {
        checkNotNull(parentId, "parentId must not be null");
        checkNotNull(nameFolder, "nameFolder must not be null");
        return dmsFolderDaoImpl.duplicateAdocFolder(parentId, nameFolder);
    }

    public DmsFolderModel tranformToModel2(DmsFolder t, String isWfFolderFromType) {
        DmsFolderModel DmsFolderModel = null;

//        System.out.println("DmsFolder = "+ t.getId());
        if (t != null) {
//            DmsFolderModel = new DmsFolderModel(t.getId(),t.getDmsFolderType(),t.getDmsFolderParentId(),t.getDmsFolderParentType(),t.getDmsFolderParentKey(),t.get);
            DmsFolderModel = new DmsFolderModel();
            DmsFolderModel.setCreateDate(Common.localDateTimeToString(t.getCreatedDate()));
            DmsFolderModel.setDocumentTypeId(t.getDocumentTypeId());
            DmsFolderModel.setFolderDescription(t.getDmsFolderDescription());
            DmsFolderModel.setFolderName(t.getDmsFolderName());
            DmsFolderModel.setFolderNodeLevel(t.getDmsFolderNodeLevel());
            DmsFolderModel.setFolderOrderId(t.getDmsFolderOrderId());
            DmsFolderModel.setFolderParentId(t.getDmsFolderParentId());
            DmsFolderModel.setFolderParentKey(t.getDmsFolderParentKey());
            DmsFolderModel.setFolderParentType(t.getDmsFolderParentType());
            DmsFolderModel.setFolderType(t.getDmsFolderType());
            DmsFolderModel.setFolderTypeExpire(t.getDmsFolderTypeExpire());
            DmsFolderModel.setFolderTypeExpireNumber(t.getDmsFolderTypeExpireNumber());
            DmsFolderModel.setId(t.getId());
            DmsFolderModel.setCreateBy(t.getCreatedBy());
            DmsFolderModel.setRemoveDate(Common.localDateTimeToString(t.getRemovedDate()));
            DmsFolderModel.setRemoveBy(t.getRemovedBy());
            DmsFolderModel.setUpDateBy(t.getCreatedBy());
            DmsFolderModel.setUpDateDate(Common.localDateTimeToString(t.getUpdatedDate()));
            DmsFolderModel.setIcon(t.getIcon());
            DmsFolderModel.setIconColor(t.getIconColor());
            DmsFolderModel.setIsWfFolderFromType(isWfFolderFromType);
            DmsFolderModel.setDmsEmailUserPreExpire(t.getDmsEmailUserPreExpire());

        };
//         System.out.println(DmsFolderModel);
        return DmsFolderModel;

    }

    public List<DmsFolder> getListFolderByWfDocTypeCode(String wfDocTypeCode) {

        checkNotNull(wfDocTypeCode, "wfDocTypeCode must not be null");
        return dmsFolderDaoImpl.getListFolderByWfDocTypeCode(wfDocTypeCode);

    }

    public DmsFolder createFolderCopy(int folderIdCopy, int folderIdAddress, int userId) {
        DmsFolder folderCopy = dmsFolderDaoImpl.getById(folderIdCopy);
        DmsFolder folderParent = dmsFolderDaoImpl.getById(folderIdAddress);

        folderCopy.setDmsFolderParentId(folderIdAddress);
        folderCopy.setParentId(folderIdAddress);
        folderCopy.setCreatedBy(userId);
        folderCopy.setDmsFolderNodeLevel(folderParent.getDmsFolderNodeLevel() + 1);
        folderCopy.setDmsFolderParentKey(folderParent.getDmsFolderParentKey());
        folderCopy.setParentKey(folderParent.getDmsFolderParentKey());
        folderCopy = dmsFolderDaoImpl.createFolder(folderCopy);
        String folderParentKey = folderCopy.getDmsFolderParentKey() + folderCopy.getId() + "฿";

        int folderOrderId = folderCopy.getId();
        folderCopy.setDmsFolderOrderId(folderOrderId);
        folderCopy.setDmsFolderParentKey(folderParentKey);
        folderCopy.setNodeLevel(folderParent.getDmsFolderNodeLevel() + 1);
        folderCopy.setParentId(folderIdAddress);
        folderCopy.setParentKey(folderParentKey);
//        folderCopy = dmsFolderDaoImpl.update(folderCopy);

        /// add search
//        System.out.println("1111");
        String folderParentKeyTemp = folderCopy.getParentKey();
//        System.out.println("2222");
        DmsSearchService dmsSearchService = new DmsSearchService();
        DmsFolderService dmsFolderService = new DmsFolderService();
//        System.out.println("33333");
        DmsSearchModel tempDoc = dmsSearchService.changFolderToSearch(folderCopy);
//        System.out.println("4444");
        DmsSearchModel resultSearch = dmsSearchService.addDataFolder(tempDoc, folderParentKeyTemp);
//        System.out.println("55555");
        folderCopy.setDmsSearchId(resultSearch.getDmsSearchId());
//        System.out.println("666666666");
        String fullPath = dmsFolderService.getFullPathName(folderParentKeyTemp);
//        System.out.println("77777");
        folderCopy.setFullPathName(fullPath);
        /// add search

        folderCopy = dmsFolderDaoImpl.update(folderCopy);

        List<DmsFolder> listFolderChild = dmsFolderDaoImpl.findListByFolderParentId2(folderIdCopy, 0, 100);
        for (int i = 0; i < listFolderChild.size(); i++) {
            createFolderCopy(listFolderChild.get(i).getId(), folderCopy.getId(), userId);
        }
        return folderCopy;
    }

    public DmsFolder folderMove(int folderIdMove, int folderIdAddress, int userId) {
        DmsFolder folderMove = dmsFolderDaoImpl.getById(folderIdMove);
        DmsFolder folderParent = dmsFolderDaoImpl.getById(folderIdAddress);

        folderMove.setDmsFolderParentId(folderIdAddress);
        folderMove.setParentId(folderIdAddress);
        folderMove.setUpdatedBy(userId);
        folderMove.setDmsFolderNodeLevel(folderParent.getDmsFolderNodeLevel() + 1);

        folderMove.setDmsFolderParentKey(folderParent.getDmsFolderParentKey() + folderMove.getId() + "฿");
        folderMove.setParentKey(folderParent.getDmsFolderParentKey() + folderMove.getId() + "฿");

//        folderMove = dmsFolderDaoImpl.update(folderMove);
        // update search
        DmsFolderService dmsFolderService = new DmsFolderService();
        String searchId = folderMove.getDmsSearchId();
        String folderParentKeyTemp = folderMove.getParentKey();
        String fullPath = dmsFolderService.getFullPathName(folderMove.getParentKey());
        folderMove.setFullPathName(fullPath);
//        if (searchId != null) {
//            DmsSearchService dmsSearchService = new DmsSearchService();
//           
//            DmsSearchModel temp = dmsSearchService.changFolderToSearch(folderMove);
//            DmsSearchModel result = dmsSearchService.updateDataFolder(searchId, temp, folderParentKeyTemp);
//            folderMove.setDmsSearchId(result.getDmsSearchId());
//            String fullPath = dmsFolderService.getFullPathName(folderMove.getParentKey());
//            folderMove.setFullPathName(fullPath);
//        } else {
//            //add seach
//            DmsSearchService dmsSearchService = new DmsSearchService();
//            DmsFolderService dmsFolderService = new DmsFolderService();
//            DmsSearchModel tempDoc = dmsSearchService.changFolderToSearch(folderMove);
//            DmsSearchModel resultSearch = dmsSearchService.addDataFolder(tempDoc, folderParentKeyTemp);
//            folderMove.setDmsSearchId(resultSearch.getDmsSearchId());
//            String fullPath = dmsFolderService.getFullPathName(folderMove.getParentKey());
//            folderMove.setFullPathName(fullPath);
//            //end search
//
//        }

        //
        folderMove = dmsFolderDaoImpl.update(folderMove);

        List<DmsFolder> listFolderChild = dmsFolderDaoImpl.findListByFolderParentId2(folderIdMove, 0, 1000);
        for (int i = 0; i < listFolderChild.size(); i++) {
            folderMove(listFolderChild.get(i).getId(), folderMove.getId(), userId);
        }
        System.out.println("listFolderChild.size() = " + listFolderChild.size());
        if (listFolderChild.size() == 0) {
//            System.out.println("folder id = " + folderIdMove);
            String parentKey = folderMove.getDmsFolderParentKey();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();

            List<DmsDocument> listDmsDocument = DmsDocumentService.findListDocumentS(folderIdMove, 0, 1000, "createdDate", "asc");
//            System.out.println("listDmsDocument size = " + listDmsDocument.size());
            if (listDmsDocument.size() > 0) {

                String listId = "";
                String[] valueFolderParent = folderMove.getDmsFolderParentKey().split("฿");

                for (int i = 1; i < valueFolderParent.length; i++) {
                    DmsFolder temp2 = dmsFolderDaoImpl.getById(Integer.parseInt(valueFolderParent[i]));
                    if (listId.length() == 0) {
                        listId = temp2.getDmsFolderName();
                    } else {
                        listId = listId + '/' + temp2.getDmsFolderName();
                    }
                }

                DmsSearchService dmsSearchService = new DmsSearchService();
                for (int i = 0; i < listDmsDocument.size(); i++) {
//                    folderMove(listFolderChild.get(i).getId(), folderMove.getId(), userId);
                    DmsDocument temp = listDmsDocument.get(i);
                    temp.setFullPathName(listId);
                    DmsDocumentService.update(temp);
                    DmsSearchModel result = dmsSearchService.getData(temp.getDmsSearchId());

                    if (result.getUpdatedDate() != null) {
//                        System.out.println("aaaa");
                        result.setUpdatedDate(localDateTimeToString2(temp.getUpdatedDate()));
                    }
                    if (result.getDocumentPublicStatus() != null) {
//                        System.out.println("bbbb");
                        result.setDocumentPublicDate(localDateTimeToString2(temp.getDmsDocumentPublicDate()));
                    }
                    if (result.getDocumentDate01() != null) {
//                        System.out.println("cccc");
                        result.setDocumentDate01(localDateTimeToString2(temp.getDmsDocumentDatetime01()));
                    }
                    if (result.getDocumentDate01() != null) {
//                        System.out.println("dddd");
                        result.setDocumentDate02(localDateTimeToString2(temp.getDmsDocumentDatetime02()));
                    }
                    if (result.getDocumentDate01() != null) {
//                        System.out.println("eeee");
                        result.setDocumentDate03(localDateTimeToString2(temp.getDmsDocumentDatetime03()));
                    }
                    if (result.getDocumentDate01() != null) {
//                        System.out.println("ffff");
                        result.setDocumentDate04(localDateTimeToString2(temp.getDmsDocumentDatetime04()));
                    }
//                    System.out.println("aaaaa1234");
                    System.out.println("exp " + result.getDocumentExpireDate());
                    if (result.getDocumentExpireDate() != null) {
//                        System.out.println("gggg");
                        result.setDocumentExpireDate(localDateTimeToString2(temp.getDmsDocumentExpireDate()));
                    }
//                    result.setParentKey(parentKey);
                    String[] parts2 = parentKey.split("฿");
                    List<String> temp2 = new ArrayList<String>();
                    for (int j = 1; j < parts2.length; j++) {

                        temp2.add(parts2[j]);

                    }
                    result.setParentKey(temp2);

                    dmsSearchService.updateData(temp.getDmsSearchId(), result);
                }
            }
        }
        return folderMove;
    }

    public static String localDateTimeToString2(LocalDateTime localDateTime) {
        String resultDate = "";
        if (localDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formatDateTime = localDateTime.format(formatter);
            String[] arrayInputDateTime = formatDateTime.split(" ");
            int year = 0;
            String dm = arrayInputDateTime[0].substring(0, arrayInputDateTime[0].length() - 4);
            String y = arrayInputDateTime[0].substring(arrayInputDateTime[0].length() - 4);
            year = Integer.parseInt(y) + 543;
            resultDate = dm + year + " " + arrayInputDateTime[1];
        }
        return resultDate;
    }

    public List<DmsFolder> getListForSearchNotInParentKey(DmsFolder dmsFolder, SubmoduleAuth submoduleAuth, UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(submoduleAuth, "submoduleAuth entity must not be null");
        checkNotNull(dmsFolder, "dmsFolder entity must not be null");

        List<DmsFolder> result = new ArrayList();
        try {
            List<DmsFolder> entityList = new ArrayList();
            entityList = getListForSearchNotInParentKeyDetail(entityList, dmsFolder, submoduleAuth, userProfile);

            result = entityList;

        } catch (Exception e) {
            LOG.info("Exception:getListForSearchNotInParentKey = " + e);
        }

        return result;
    }

    public List<DmsFolder> findListByFolderParentIdLazy(int folderId, int offset, int limit, AuthEnableDisableIdListModel data) {
        checkNotNull(folderId, "folderId  must not be null");
        return dmsFolderDaoImpl.findListByFolderParentIdLazy(folderId, offset, limit, data);
    }

    private List getListForSearchNotInParentKeyDetail(List<DmsFolder> resultList, DmsFolder dmsFolder, SubmoduleAuth submoduleAuth, UserProfile userProfile) {
        List result = resultList;
        System.out.println("dmsFolder = " + dmsFolder.getId());
        try {
            boolean addEntity = false;
            //Get Entity Detail
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            List<SubmoduleUserAuth> tmpList = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submoduleAuth, userProfile, dmsFolder);
            for (SubmoduleUserAuth submoduleUserAuth : tmpList) {
                if (submoduleUserAuth.getSubmoduleAuth().getId().equals(submoduleAuth.getId()) && submoduleUserAuth.getAuthority().equals("2")) {
                    result.add(dmsFolder);
                    addEntity = true;
                }
            }

            if (!addEntity) {
                //Get Child Detail
                List<DmsFolder> childList = listChild(dmsFolder);
                if (childList != null) {
                    for (DmsFolder child : childList) {
                        result = getListForSearchNotInParentKeyDetail(result, child, submoduleAuth, userProfile);
                    }
                }
            }

        } catch (Exception e) {
            LOG.info("Exception:getListForSearchNotInParentKeyDetail = " + e);
        }

        return result;
    }

}
