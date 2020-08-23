package com.px.wf.service;

import com.px.wf.daoimpl.WfFolderDaoImpl;
import com.px.wf.entity.WfFolder;
import com.px.share.service.GenericTreeService;
import com.px.wf.model.WfFolderModel;
import com.px.wf.model.WfFolderModel_groupWfFolderAndShortcut;
import com.px.admin.service.UserProfileService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.share.entity.LogData;
import com.px.share.service.LogDataService;
import com.px.share.util.Common;
import com.px.share.util.TreeUtil;
import com.px.wf.model.WfFolderContentAuthModel;
import java.time.LocalDateTime;

/**
 *
 * @author Mali
 */
public class WfFolderService implements GenericTreeService<WfFolder, WfFolderModel> {

    private static final Logger LOG = Logger.getLogger(WfFolderService.class.getName());
    private final WfFolderDaoImpl WfFolderDaoImpl;

    public WfFolderService() {
        this.WfFolderDaoImpl = new WfFolderDaoImpl();
    }

    @Override
    public WfFolder create(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder entity must not be null");
        checkNotNull(wfFolder.getCreatedBy(), "create by must not be null");
        checkNotNull(wfFolder.getWfFolderParentType(), "WfFolderParentType by must not be null");
        checkNotNull(wfFolder.getWfFolderAutorun(), "WfFolderAutorun by must not be null");
        checkNotNull(wfFolder.getWfFolderBookNoType(), "WfFolderBookNoType by must not be null");
        checkNotNull(wfFolder.getWfFolderOwnerId(), "WfFolderOwnerId by must not be null");
        checkNotNull(wfFolder.getWfFolderLinkFolderId(), "WfFolderLinkFolderId by must not be null");
        checkNotNull(wfFolder.getWfFolderLinkId(), "WfFolderLinkId by must not be null");
        checkNotNull(wfFolder.getWfFolderByBudgetYear(), "WfFolderByBudgetYear by must not be null");
        checkNotNull(wfFolder.getWfFolderNumYearExpire(), "WfFolderNumYearExpire by must not be null");
        return WfFolderDaoImpl.create(wfFolder);
    }

    @Override
    public WfFolder getById(int id) {
        return WfFolderDaoImpl.getById(id);
    }

    @Override
    public WfFolder getByIdNotRemoved(int id) {
        checkNotNull(id, "WfFolder id entity must not be null");
        return WfFolderDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public WfFolder update(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder entity must not be null");
        checkNotNull(wfFolder.getUpdatedBy(), "update by must not be null");
        checkNotNull(wfFolder.getWfFolderParentType(), "WfFolderParentType by must not be null");
        checkNotNull(wfFolder.getWfFolderAutorun(), "WfFolderAutorun by must not be null");
        checkNotNull(wfFolder.getWfFolderBookNoType(), "WfFolderBookNoType by must not be null");
        checkNotNull(wfFolder.getWfFolderOwnerId(), "WfFolderOwnerId by must not be null");
        checkNotNull(wfFolder.getWfFolderLinkFolderId(), "WfFolderLinkFolderId by must not be null");
        checkNotNull(wfFolder.getWfFolderLinkId(), "WfFolderLinkId by must not be null");
        checkNotNull(wfFolder.getWfFolderByBudgetYear(), "WfFolderByBudgetYear by must not be null");
        checkNotNull(wfFolder.getWfFolderNumYearExpire(), "WfFolderNumYearExpire by must not be null");
        wfFolder.setUpdatedDate(LocalDateTime.now());
        return WfFolderDaoImpl.update(wfFolder);
    }

    @Override
    public WfFolder remove(int id, int userId) {
        checkNotNull(id, "wfFolder id must not be null");
        WfFolder wfFolder = getById(id);
        checkNotNull(wfFolder, "wfFolder entity not found in database.");
        wfFolder.setRemovedBy(userId);
        wfFolder.setRemovedDate(LocalDateTime.now());
        return WfFolderDaoImpl.update(wfFolder);
    }

    @Override
    public List<WfFolder> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfFolder> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfFolder> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WfFolderModel tranformToModel(WfFolder wfFolder) {
        WfContentTypeService wfContentTypeService = new WfContentTypeService();
        WfContentType2Service wfContentType2Service = new WfContentType2Service();
        UserProfileService userProfileService = new UserProfileService();
        WfFolderModel wfFolderModel = null;
        if (wfFolder != null) {
            wfFolderModel = new WfFolderModel();
            wfFolderModel.setId(wfFolder.getId());
            wfFolderModel.setCreatedBy(wfFolder.getCreatedBy());
            wfFolderModel.setCreatedDate(Common.localDateTimeToString(wfFolder.getCreatedDate()));
            wfFolderModel.setOrderNo((float) wfFolder.getOrderNo());
            wfFolderModel.setRemovedBy(wfFolder.getRemovedBy());
            wfFolderModel.setRemovedDate(Common.localDateTimeToString(wfFolder.getRemovedDate()));
            wfFolderModel.setUpdatedBy(wfFolder.getUpdatedBy());
            wfFolderModel.setUpdatedDate(Common.localDateTimeToString(wfFolder.getUpdatedDate()));
            wfFolderModel.setWfFolderName(wfFolder.getWfFolderName());
            wfFolderModel.setWfFolderType(wfFolder.getWfFolderType());
            wfFolderModel.setParentId(wfFolder.getParentId());
            wfFolderModel.setParentKey(wfFolder.getParentKey());
            wfFolderModel.setNodeLevel(wfFolder.getNodeLevel());
            wfFolderModel.setWfFolderParentName(wfFolder.getWfFolderParentName());
            wfFolderModel.setWfFolderParentType(wfFolder.getWfFolderParentType());
            wfFolderModel.setWfFolderDetail(wfFolder.getWfFolderDetail());
            wfFolderModel.setWfContentType(wfContentTypeService.tranformToModel(wfFolder.getWfContentType()));
            wfFolderModel.setWfContentType2(wfContentType2Service.tranformToModel(wfFolder.getWfContentType2()));
            wfFolderModel.setWfFolderAutorun(wfFolder.getWfFolderAutorun());
            wfFolderModel.setWfFolderBookNoType(wfFolder.getWfFolderBookNoType());
            wfFolderModel.setWfFolderPreBookNo(wfFolder.getWfFolderPreBookNo());
            wfFolderModel.setWfFolderPreContentNo(wfFolder.getWfFolderPreContentNo());
            wfFolderModel.setWfFolderOwnerId(wfFolder.getWfFolderOwnerId());
            wfFolderModel.setWfFolderLinkFolderId(wfFolder.getWfFolderLinkFolderId());
            wfFolderModel.setWfFolderLinkId(wfFolder.getWfFolderLinkId());
            wfFolderModel.setWfFolderByBudgetYear(wfFolder.getWfFolderByBudgetYear());
            wfFolderModel.setWfFolderTypeYearExpire(wfFolder.getWfFolderTypeYearExpire());
            wfFolderModel.setWfFolderNumYearExpire(wfFolder.getWfFolderNumYearExpire());
            wfFolderModel.setWfFolderExpireDate(Common.localDateTimeToString(wfFolder.getWfFolderExpireDate()));
            wfFolderModel.setConvertId(wfFolder.getConvertId());
            wfFolderModel.setWfFolderOwnerName(userProfileService.getById(wfFolder.getWfFolderOwnerId()).getUserProfileFullName());

            boolean[] searchField = new boolean[14];
            if (wfFolder.getSearchField() != null && wfFolder.getSearchField() != "") {
                String[] tmp = wfFolder.getSearchField().split("");
                int tmpSize = tmp.length;
                for (int i = 0; i < tmpSize; i++) {
                    searchField[i] = ("1".equals(tmp[i]));
                }
                int addedFilterCount = 14 - tmpSize;
                for (int i = 0; i < addedFilterCount; i++) {
                    searchField[tmpSize + i] = true;
                }
            } else {
                for (int i = 0; i < 14; i++) {
                    searchField[i] = true;
                }
            }
            wfFolderModel.setSearchField(searchField);
        }
        return wfFolderModel;
    }

    public void delete(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder must not be null");
        WfFolderDaoImpl.delete(wfFolder);
    }

    public List<WfFolder> listByParentId(int parentId) {
        checkNotNull(parentId, "parentId must not be null");
        return WfFolderDaoImpl.listByParentId(parentId);
    }

    public WfFolderContentAuthModel tranformToModel2(SubmoduleUserAuth subModuleUserAuth) {
        WfFolderContentAuthModel authModel = new WfFolderContentAuthModel();
        Integer id = subModuleUserAuth.getId();
        if (id != null) {
            authModel.setId(id);
        } else {
            authModel.setId(0);
        }
        authModel.setName(subModuleUserAuth.getSubmoduleAuth().getAuth().getAuthName());
        authModel.setSubModuleAuthId(subModuleUserAuth.getSubmoduleAuth().getId());
        if ("1".equals(subModuleUserAuth.getAuthority())) {
            authModel.setAuth(true);
        } else {
            authModel.setAuth(false);
        }

        return authModel;
    }

    public WfFolderModel_groupWfFolderAndShortcut tranformToModelGroupWfFolderAndShortcut(WfFolder wfFolder, int userID) {
        WfContentTypeService wfContentTypeService = new WfContentTypeService();
        WfContentType2Service wfContentType2Service = new WfContentType2Service();
        WfFolderService wfFolderService = new WfFolderService();
        UserProfileService userProfileService = new UserProfileService();
        String hasShortcut = "";

        WfFolderModel_groupWfFolderAndShortcut listWfFolderModel_groupWfFolderAndShortcut = new WfFolderModel_groupWfFolderAndShortcut();
        if (wfFolder != null) {
            //listWfFolderModel_groupWfFolderAndShortcut.setWfFolder(wfFolderService.tranformToModel(wfFolder));
            listWfFolderModel_groupWfFolderAndShortcut.setId(wfFolder.getId());
            listWfFolderModel_groupWfFolderAndShortcut.setCreatedBy(wfFolder.getCreatedBy());
            listWfFolderModel_groupWfFolderAndShortcut.setCreatedDate(Common.localDateTimeToString(wfFolder.getCreatedDate()));
            listWfFolderModel_groupWfFolderAndShortcut.setOrderNo((float) wfFolder.getOrderNo());
            listWfFolderModel_groupWfFolderAndShortcut.setRemovedBy(wfFolder.getRemovedBy());
            listWfFolderModel_groupWfFolderAndShortcut.setRemovedDate(Common.localDateTimeToString(wfFolder.getRemovedDate()));
            listWfFolderModel_groupWfFolderAndShortcut.setUpdatedBy(wfFolder.getUpdatedBy());
            listWfFolderModel_groupWfFolderAndShortcut.setUpdatedDate(Common.localDateTimeToString(wfFolder.getUpdatedDate()));
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderName(wfFolder.getWfFolderName());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderType(wfFolder.getWfFolderType());
            listWfFolderModel_groupWfFolderAndShortcut.setParentId(wfFolder.getParentId());
            listWfFolderModel_groupWfFolderAndShortcut.setParentKey(wfFolder.getParentKey());
            listWfFolderModel_groupWfFolderAndShortcut.setNodeLevel(wfFolder.getNodeLevel());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderParentType(wfFolder.getWfFolderParentType());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderParentName(wfFolder.getWfFolderParentName());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderDetail(wfFolder.getWfFolderDetail());
            listWfFolderModel_groupWfFolderAndShortcut.setWfContentType(wfContentTypeService.tranformToModel(wfFolder.getWfContentType()));
            listWfFolderModel_groupWfFolderAndShortcut.setWfContentType2(wfContentType2Service.tranformToModel(wfFolder.getWfContentType2()));
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderAutorun(wfFolder.getWfFolderAutorun());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderBookNoType(wfFolder.getWfFolderBookNoType());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderPreBookNo(wfFolder.getWfFolderPreBookNo());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderPreContentNo(wfFolder.getWfFolderPreContentNo());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderOwnerId(wfFolder.getWfFolderOwnerId());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderLinkFolderId(wfFolder.getWfFolderLinkFolderId());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderLinkId(wfFolder.getWfFolderLinkId());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderByBudgetYear(wfFolder.getWfFolderByBudgetYear());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderTypeYearExpire(wfFolder.getWfFolderTypeYearExpire());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderNumYearExpire(wfFolder.getWfFolderNumYearExpire());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderExpireDate(Common.localDateTimeToString(wfFolder.getWfFolderExpireDate()));
            listWfFolderModel_groupWfFolderAndShortcut.setConvertId(wfFolder.getConvertId());
            listWfFolderModel_groupWfFolderAndShortcut.setWfFolderOwnerName("");
//            String ownerName = userProfileService.getByUserId(wfFolder.getWfFolderOwnerId()).getUserProfileFullName();
//            if (ownerName != null) {
//                listWfFolderModel_groupWfFolderAndShortcut.setWfFolderOwnerName(ownerName);
//            } else {
//                listWfFolderModel_groupWfFolderAndShortcut.setWfFolderOwnerName("");
//            }
//            WfFolder shortcutWfFolder = wfFolderService.shortcutByUserProfileId(userID, wfFolder.getId());
//            if (shortcutWfFolder != null) {
//                hasShortcut = "true";
//            } else {
//                hasShortcut = "false";
//            }
            listWfFolderModel_groupWfFolderAndShortcut.setHasShortcut(hasShortcut);
        }

        return listWfFolderModel_groupWfFolderAndShortcut;
    }

    public WfFolderModel tranformToModel(WfFolder wfFolder, WfFolder wfFolderMain) {
        WfContentTypeService wfContentTypeService = new WfContentTypeService();
        WfContentType2Service wfContentType2Service = new WfContentType2Service();
        UserProfileService userProfileService = new UserProfileService();
        WfFolderModel wfFolderModel = new WfFolderModel();
        if (wfFolder != null) {
            wfFolderModel.setId(wfFolder.getId());
            wfFolderModel.setCreatedBy(wfFolder.getCreatedBy());
            wfFolderModel.setCreatedDate(Common.localDateTimeToString(wfFolder.getCreatedDate()));
            wfFolderModel.setOrderNo((float) wfFolder.getOrderNo());
            wfFolderModel.setRemovedBy(wfFolder.getRemovedBy());
            wfFolderModel.setRemovedDate(Common.localDateTimeToString(wfFolder.getRemovedDate()));
            wfFolderModel.setUpdatedBy(wfFolder.getUpdatedBy());
            wfFolderModel.setUpdatedDate(Common.localDateTimeToString(wfFolder.getUpdatedDate()));
            wfFolderModel.setWfFolderName(wfFolderMain.getWfFolderName());
            wfFolderModel.setWfFolderType(wfFolder.getWfFolderType());
            wfFolderModel.setParentId(wfFolder.getParentId());
            wfFolderModel.setParentKey(wfFolder.getParentKey());
            wfFolderModel.setNodeLevel(wfFolder.getNodeLevel());
            wfFolderModel.setWfFolderParentType(wfFolderMain.getWfFolderParentType());
            wfFolderModel.setWfFolderParentName(wfFolderMain.getWfFolderParentName());
            wfFolderModel.setWfFolderDetail(wfFolderMain.getWfFolderDetail());
            wfFolderModel.setWfContentType(wfContentTypeService.tranformToModel(wfFolderMain.getWfContentType()));
            wfFolderModel.setWfContentType2(wfContentType2Service.tranformToModel(wfFolderMain.getWfContentType2()));
            wfFolderModel.setWfFolderAutorun(wfFolderMain.getWfFolderAutorun());
            wfFolderModel.setWfFolderBookNoType(wfFolderMain.getWfFolderBookNoType());
            wfFolderModel.setWfFolderPreBookNo(wfFolderMain.getWfFolderPreBookNo());
            wfFolderModel.setWfFolderPreContentNo(wfFolderMain.getWfFolderPreContentNo());
            wfFolderModel.setWfFolderOwnerId(wfFolderMain.getWfFolderOwnerId());
            wfFolderModel.setWfFolderLinkFolderId(wfFolder.getWfFolderLinkFolderId());
            wfFolderModel.setWfFolderLinkId(wfFolderMain.getWfFolderLinkId());
            wfFolderModel.setWfFolderByBudgetYear(wfFolderMain.getWfFolderByBudgetYear());
            wfFolderModel.setWfFolderTypeYearExpire(wfFolderMain.getWfFolderTypeYearExpire());
            wfFolderModel.setWfFolderNumYearExpire(wfFolderMain.getWfFolderNumYearExpire());
            wfFolderModel.setWfFolderExpireDate(Common.localDateTimeToString(wfFolderMain.getWfFolderExpireDate()));
            wfFolderModel.setConvertId(wfFolderMain.getConvertId());
            wfFolderModel.setWfFolderOwnerName(userProfileService.getById(wfFolderMain.getWfFolderOwnerId()).getUserProfileFullName());
        }
        return wfFolderModel;
    }

    public List<WfFolder> listShortcutByUserProfileId(int userProfileId) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        return WfFolderDaoImpl.listShortcutByUserProfileId(userProfileId);
    }

    public List<WfFolder> listByContentTypeId(String folderType, int contentTypeId, int contentType2Id) {
        checkNotNull(folderType, "folderType must not be null");
        checkNotNull(contentTypeId, "contentTypeId must not be null");
        checkNotNull(contentType2Id, "contentType2Id must not be null");
        return WfFolderDaoImpl.listByContentTypeId(folderType, contentTypeId, contentType2Id);
    }

    @Override
    public String generateParentKey(WfFolder wfFolder) {
        String result = "";
        try {
            result = TreeUtil.generateParentKey(wfFolder);
        } catch (Exception e) {
            LOG.info("Exception : " + e);
        }

        return result;
    }

    @Override
    public WfFolder getParent(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder entity must not be null");
        checkNotNull(wfFolder.getParentId(), "wfFolder parent id must not be null");
        return WfFolderDaoImpl.findParent(wfFolder.getParentId());
    }

    @Override
    public List<WfFolder> listChild(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder entity must not be null");
        checkNotNull(wfFolder.getId(), "wfFolder id must not be null");
        return WfFolderDaoImpl.findChild(wfFolder.getId());
    }

    @Override
    public List<WfFolder> listFromParentKey(WfFolder wfFolder) {
        checkNotNull(wfFolder, "wfFolder entity must not be null");
        checkNotNull(wfFolder.getParentId(), "wfFolder parent id must not be null");
        ArrayList<WfFolder> result = new ArrayList();
        try {
            List parentList = TreeUtil.getListFromParentKey(wfFolder);
            for (Object tmp : parentList) {
                result.add((WfFolder) tmp);
            }
        } catch (Exception e) {
            LOG.info("Exception : " + e);
        }

        return result;
    }

    public String checkLengthData(String tempStr, int lengthStr) {
        String result = null;

        if (tempStr.length() > lengthStr) {
            result = tempStr.substring(0, lengthStr);
        } else {
            result = tempStr;
        }

        return result;
    }

    public WfFolderModel checkLengthField(WfFolderModel wfFolderPostModel) {
        WfFolderService wfFolderService = new WfFolderService();
        wfFolderPostModel.setWfFolderType(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderType(), 10));
        //==================================================================================================================
        if (wfFolderPostModel.getWfFolderPreContentNo() != null) {
            wfFolderPostModel.setWfFolderPreContentNo(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderPreContentNo(), 50));
        }
        if (wfFolderPostModel.getWfFolderPreBookNo() != null) {
            wfFolderPostModel.setWfFolderPreBookNo(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderPreBookNo(), 1000));
        }
        if (wfFolderPostModel.getWfFolderTypeYearExpire() != null) {
            wfFolderPostModel.setWfFolderTypeYearExpire(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderTypeYearExpire(), 50));
        }
        //==================================================================================================================
        if (wfFolderPostModel.getWfFolderName() != null) {
            wfFolderPostModel.setWfFolderName(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderName(), 255));
        }
        if (wfFolderPostModel.getWfFolderParentName() != null) {
            wfFolderPostModel.setWfFolderParentName(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderParentName(), 255));
        }
        //==================================================================================================================
        if (wfFolderPostModel.getWfFolderDetail() != null) {
            wfFolderPostModel.setWfFolderDetail(wfFolderService.checkLengthData(wfFolderPostModel.getWfFolderDetail(), 1000));
        }
        return wfFolderPostModel;
    }

    public WfFolder saveLogForCreate(WfFolder wfFolder, String clientIp) {
        //For Create Log when create WfFolder
        String logDescription = this.generateLogForCreateEntity(wfFolder);
        LogData logData = new LogData();
        logData.setCreatedBy(wfFolder.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfFolder.getClass().getName());
        logData.setLinkId(wfFolder.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_WF);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return wfFolder;
    }

    public WfFolder saveLogForUpdate(WfFolder wfFolderOld, WfFolder wfFolderNew, String clientIp) {
        //For Create Log when update WfFolder
        String logDescription = this.generateLogForUpdateEntity(wfFolderOld, wfFolderNew);
        LogData logData = new LogData();
        logData.setCreatedBy(wfFolderNew.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfFolderNew.getClass().getName());
        logData.setLinkId(wfFolderNew.getId());
        logData.setModuleName(LogData.MODULE_WF);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return wfFolderNew;
    }

    public WfFolder saveLogForRemove(WfFolder wfFolder, String clientIp) {
        //For Create Log when remove WfFolder
        String logDescription = this.generateLogForRemoveEntity(wfFolder);
        LogData logData = new LogData();
        logData.setCreatedBy(wfFolder.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfFolder.getClass().getName());
        logData.setLinkId(wfFolder.getId());
        logData.setModuleName(LogData.MODULE_WF);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return wfFolder;
    }

    private String generateLogForCreateEntity(WfFolder wfFolder) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("สร้างแฟ้มทะเบียน : ");
        descriptionLog.append(Common.noNull(wfFolder.getWfFolderName(), ""));
        descriptionLog.append(" ประเภททะเบียน : ");
        descriptionLog.append(Common.noNull(wfFolder.getWfContentType().getContentTypeName(), ""));
        descriptionLog.append(Common.noNull(wfFolder.getWfContentType2().getContentType2Name(), ""));
        descriptionLog.append(" รายละเอียด : ");
        descriptionLog.append(Common.noNull(wfFolder.getWfFolderDetail(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForUpdateEntity(WfFolder wfFolderOld, WfFolder wfFolderNew) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("แก้ไขแฟ้มทะเบียน: ");

        if (!wfFolderOld.getWfFolderName().equals(wfFolderNew.getWfFolderName())) {
//            descriptionLog.append(" ชื่อแฟ้มเอกสาร : ");
            descriptionLog.append(" ชื่อแฟ้มทะเบียน : ");//oat-edit
            descriptionLog.append(Common.noNull(wfFolderOld.getWfFolderName(), "(ไม่มีข้อมูล)"));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfFolderNew.getWfFolderName(), "(ไม่มีข้อมูล)"));
        }

        if (!wfFolderOld.getWfFolderDetail().equals(wfFolderNew.getWfFolderDetail())) {
            descriptionLog.append(" รายละเอียด : ");
            descriptionLog.append(Common.noNull(wfFolderOld.getWfFolderDetail(), "(ไม่มีข้อมูล)"));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfFolderNew.getWfFolderDetail(), "(ไม่มีข้อมูล)"));
        }

        String tmp1 = Common.noNull(wfFolderOld.getWfFolderTypeYearExpire(), "(ไม่มีข้อมูล)");
        String tmp2 = Common.noNull(wfFolderNew.getWfFolderTypeYearExpire(), "(ไม่มีข้อมูล)");
        if ((wfFolderOld.getWfFolderNumYearExpire() != wfFolderNew.getWfFolderNumYearExpire())
                || !tmp1.equals(tmp2)) {
            descriptionLog.append(" กำหนดวันหมดอายุ : ");
            if (wfFolderOld.getWfFolderNumYearExpire() != 0) {
                descriptionLog.append(wfFolderOld.getWfFolderNumYearExpire());
                descriptionLog.append(" ");
            }
            descriptionLog.append(tmp1);
            descriptionLog.append("เป็น");
            if (wfFolderNew.getWfFolderNumYearExpire() != 0) {
                descriptionLog.append(wfFolderNew.getWfFolderNumYearExpire());
                descriptionLog.append(" ");
            }
            descriptionLog.append(tmp2);
        }

        if (wfFolderOld.getWfContentType().getId() != wfFolderNew.getWfContentType().getId()) {
            descriptionLog.append(" ประเภททะเบียน : ");
            descriptionLog.append(Common.noNull(wfFolderOld.getWfContentType().getContentTypeName(), ""));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfFolderNew.getWfContentType().getContentTypeName(), ""));
        }

        if (wfFolderOld.getWfContentType2().getId() != wfFolderNew.getWfContentType2().getId()) {
            descriptionLog.append(" ประเภททะเบียนย่อย : ");
            descriptionLog.append(Common.noNull(wfFolderOld.getWfContentType2().getContentType2Name(), ""));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfFolderNew.getWfContentType2().getContentType2Name(), ""));
        }

        String wfFolderPreContentNoOld = Common.noNull(wfFolderOld.getWfFolderPreContentNo(), "(ไม่มีข้อมูล)");
        String wfFolderPreContentNoNew = Common.noNull(wfFolderNew.getWfFolderPreContentNo(), "(ไม่มีข้อมูล)");
        if (!wfFolderPreContentNoOld.equals(wfFolderPreContentNoNew)) {
            descriptionLog.append(" เลขทะเบียนขึ้นต้นด้วย : ");
            descriptionLog.append(wfFolderPreContentNoOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfFolderPreContentNoNew);
        }

        if (wfFolderOld.getWfFolderByBudgetYear() != wfFolderNew.getWfFolderByBudgetYear()) {
            descriptionLog.append(" รูปแบบปีของเลขทะเบียน : ");
            if (wfFolderOld.getWfFolderByBudgetYear() == 0) {
                descriptionLog.append("เลขทะเบียนไม่นับตามปีงบประมาณ");
                descriptionLog.append("เป็น");
                descriptionLog.append("เลขทะเบียนนับตามปีงบประมาณ");
            } else {
                descriptionLog.append("เลขทะเบียนนับตามปีงบประมาณ");
                descriptionLog.append("เป็น");
                descriptionLog.append("เลขทะเบียนไม่นับตามปีงบประมาณ");
            }
        }

        if (wfFolderOld.getWfFolderAutorun() != wfFolderNew.getWfFolderAutorun()) {
            String bookNoTypeOld = "";
            if (wfFolderOld.getWfFolderBookNoType() == 1) {
                bookNoTypeOld = "ตัวอักษร/ตัวเลข";
            } else if (wfFolderOld.getWfFolderBookNoType() == 2) {
                bookNoTypeOld = "ตัวอักษร/ตัวเลข/ปี";
            }

            String bookNoTypeNew = "";
            if (wfFolderNew.getWfFolderBookNoType() == 1) {
                bookNoTypeNew = "ตัวอักษร/ตัวเลข";
            } else if (wfFolderNew.getWfFolderBookNoType() == 2) {
                bookNoTypeNew = "ตัวอักษร/ตัวเลข/ปี";
            }
            descriptionLog.append(" ออกเลขที่หนังสืออัตโนมัติ : ");
            if (wfFolderOld.getWfFolderAutorun() == 0) {
                descriptionLog.append("ไม่ออกเลขที่หนังสืออัตโนมัติ");
                descriptionLog.append("เป็น");
                descriptionLog.append("ออกเลขที่หนังสืออัตโนมัติ ");
                descriptionLog.append(bookNoTypeNew);
            } else {
                descriptionLog.append("ออกเลขที่หนังสืออัตโนมัติ ");
                descriptionLog.append(bookNoTypeOld);
                descriptionLog.append("เป็น");
                descriptionLog.append("ไม่ออกเลขที่หนังสืออัตโนมัติ");
            }
        } else if (wfFolderOld.getWfFolderAutorun() == 1 && wfFolderNew.getWfFolderAutorun() == 1
                && (wfFolderOld.getWfFolderBookNoType() != wfFolderNew.getWfFolderBookNoType())) {
            String bookNoTypeOld = "";
            if (wfFolderOld.getWfFolderBookNoType() == 1) {
                bookNoTypeOld = "ตัวอักษร/ตัวเลข";
            } else if (wfFolderOld.getWfFolderBookNoType() == 2) {
                bookNoTypeOld = "ตัวอักษร/ตัวเลข/ปี";
            }

            String bookNoTypeNew = "";
            if (wfFolderNew.getWfFolderBookNoType() == 1) {
                bookNoTypeNew = "ตัวอักษร/ตัวเลข";
            } else if (wfFolderNew.getWfFolderBookNoType() == 2) {
                bookNoTypeNew = "ตัวอักษร/ตัวเลข/ปี";
            }

            descriptionLog.append(" ออกเลขที่หนังสืออัตโนมัติ : ");
            descriptionLog.append(bookNoTypeOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(bookNoTypeNew);
        }

        String wfFolderPreBookNoOld = Common.noNull(wfFolderOld.getWfFolderPreBookNo(), "(ไม่มีข้อมูล)");
        String wfFolderPreBookNoNew = Common.noNull(wfFolderNew.getWfFolderPreBookNo(), "(ไม่มีข้อมูล)");
        if (!wfFolderPreBookNoOld.equals(wfFolderPreBookNoNew)) {
            descriptionLog.append(" เลขที่หนังสือขึ้นต้นด้วย : ");
            descriptionLog.append(wfFolderPreBookNoOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfFolderPreBookNoNew);
        }
        return descriptionLog.toString();
    }

    private String generateLogForRemoveEntity(WfFolder wfFolder) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ลบแฟ้มทะเบียน : ");
        descriptionLog.append(Common.noNull(wfFolder.getWfFolderName(), ""));
//        descriptionLog.append(" ประเภททะเบียน : ");
//        descriptionLog.append(Common.noNull(wfFolder.getContentType().getContentTypeName(), ""));
//        descriptionLog.append(Common.noNull(wfFolder.getContentType2().getContentType2Name(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForOpenEntity(WfFolder wfFolder, String type) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("เปิดใช้แฟ้มทะเบียน [");
        descriptionLog.append(type);
        descriptionLog.append("] : ");
        descriptionLog.append(Common.noNull(wfFolder.getWfFolderName(), ""));
//        descriptionLog.append(" ประเภททะเบียน : ");
//        descriptionLog.append(Common.noNull(wfFolder.getContentType().getContentTypeName(), ""));
//        descriptionLog.append(Common.noNull(wfFolder.getContentType2().getContentType2Name(), ""));

        return descriptionLog.toString();
    }

    //oat-add
    public WfFolderModel tranformToModel(WfFolder wfFolder, List<SubmoduleUserAuth> listSubModuleUserAuth) {
        WfContentTypeService wfContentTypeService = new WfContentTypeService();
        WfContentType2Service wfContentType2Service = new WfContentType2Service();
        UserProfileService userProfileService = new UserProfileService();
        WfFolderModel wfFolderModel = null;
        if (wfFolder != null) {
            wfFolderModel = new WfFolderModel();
            wfFolderModel.setId(wfFolder.getId());
            wfFolderModel.setCreatedBy(wfFolder.getCreatedBy());
            wfFolderModel.setCreatedDate(Common.localDateTimeToString(wfFolder.getCreatedDate()));
            wfFolderModel.setOrderNo((float) wfFolder.getOrderNo());
            wfFolderModel.setRemovedBy(wfFolder.getRemovedBy());
            wfFolderModel.setRemovedDate(Common.localDateTimeToString(wfFolder.getRemovedDate()));
            wfFolderModel.setUpdatedBy(wfFolder.getUpdatedBy());
            wfFolderModel.setUpdatedDate(Common.localDateTimeToString(wfFolder.getUpdatedDate()));
            wfFolderModel.setWfFolderName(wfFolder.getWfFolderName());
            wfFolderModel.setWfFolderType(wfFolder.getWfFolderType());
            wfFolderModel.setParentId(wfFolder.getParentId());
            wfFolderModel.setParentKey(wfFolder.getParentKey());
            wfFolderModel.setNodeLevel(wfFolder.getNodeLevel());
            wfFolderModel.setWfFolderParentName(wfFolder.getWfFolderParentName());
            wfFolderModel.setWfFolderParentType(wfFolder.getWfFolderParentType());
            wfFolderModel.setWfFolderDetail(wfFolder.getWfFolderDetail());
            wfFolderModel.setWfContentType(wfContentTypeService.tranformToModel(wfFolder.getWfContentType()));
            wfFolderModel.setWfContentType2(wfContentType2Service.tranformToModel(wfFolder.getWfContentType2()));
            wfFolderModel.setWfFolderAutorun(wfFolder.getWfFolderAutorun());
            wfFolderModel.setWfFolderBookNoType(wfFolder.getWfFolderBookNoType());
            wfFolderModel.setWfFolderPreBookNo(wfFolder.getWfFolderPreBookNo());
            wfFolderModel.setWfFolderPreContentNo(wfFolder.getWfFolderPreContentNo());
            wfFolderModel.setWfFolderOwnerId(wfFolder.getWfFolderOwnerId());
            wfFolderModel.setWfFolderLinkFolderId(wfFolder.getWfFolderLinkFolderId());
            wfFolderModel.setWfFolderLinkId(wfFolder.getWfFolderLinkId());
            wfFolderModel.setWfFolderByBudgetYear(wfFolder.getWfFolderByBudgetYear());
            wfFolderModel.setWfFolderTypeYearExpire(wfFolder.getWfFolderTypeYearExpire());
            wfFolderModel.setWfFolderNumYearExpire(wfFolder.getWfFolderNumYearExpire());
            wfFolderModel.setWfFolderExpireDate(Common.localDateTimeToString(wfFolder.getWfFolderExpireDate()));
            wfFolderModel.setConvertId(wfFolder.getConvertId());
            wfFolderModel.setWfFolderOwnerName(userProfileService.getById(wfFolder.getWfFolderOwnerId()).getUserProfileFullName());

            int numAuth = listSubModuleUserAuth.size();
            boolean[] auth = new boolean[numAuth];
            for (int i = 0; i < numAuth; i++) {
                auth[i] = ("1".equals(listSubModuleUserAuth.get(i).getAuthority()));
            }
            wfFolderModel.setAuth(auth);
        }
        return wfFolderModel;
    }

    public List<WfFolder> listByStructureId(int structureId) {
        checkNotNull(structureId, "structureId must not be null");
        return WfFolderDaoImpl.listByStructureId(structureId);
    }

    public List<WfFolder> listByParentId(int offset, int limit, int parentId) {
        checkNotNull(parentId, "parentId must not be null");
        return WfFolderDaoImpl.listByParentId(offset, limit, parentId);
    }

    public int countlistByParentId(int parentId) {
        checkNotNull(parentId, "parentId must not be null");
        return WfFolderDaoImpl.countlistByParentId(parentId);
    }
}
