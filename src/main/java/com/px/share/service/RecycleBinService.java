package com.px.share.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.daoimpl.RecycleBinDaoImpl;
import com.px.share.entity.LogData;
import com.px.admin.service.ModuleService;
import com.px.admin.service.UserProfileService;
import com.px.share.entity.RecycleBin;
import com.px.share.model.RecycleBinModel;
import com.px.share.model.RecycleBinSearchModel;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class RecycleBinService implements GenericService<RecycleBin, RecycleBinModel> {

    private static final Logger LOG = Logger.getLogger(RecycleBinService.class.getName());
    private final RecycleBinDaoImpl recycleBinDaoImpl;

    public RecycleBinService() {
        this.recycleBinDaoImpl = new RecycleBinDaoImpl();
    }

    @Override
    public RecycleBin create(RecycleBin recycleBin) {
        checkNotNull(recycleBin, "recycleBin entity must not be null");
        checkNotNull(recycleBin.getCreatedBy(), "create by must not be null");
        recycleBin = recycleBinDaoImpl.create(recycleBin);
        return recycleBin;
    }

    @Override
    public RecycleBin getById(int id) {
        checkNotNull(id, "recycleBin id entity must not be null");
        return recycleBinDaoImpl.getById(id);
    }

    public List<RecycleBin> listByUserId(int offset, int limit, String sort, String dir, int userProfileId) {
        return recycleBinDaoImpl.listByUserId(offset, limit, sort, dir, userProfileId);
    }

    @Override
    public RecycleBin update(RecycleBin recycleBin) {
        checkNotNull(recycleBin, "recycleBin entity must not be null");
        checkNotNull(recycleBin.getUpdatedBy(), "update by must not be null");
        recycleBin.setUpdatedDate(LocalDateTime.now());
        return recycleBinDaoImpl.update(recycleBin);
    }

    @Override
    public RecycleBin remove(int id, int userId) {
        checkNotNull(id, "recycleBin id must not be null");
        RecycleBin recycleBin = getById(id);
        checkNotNull(recycleBin, "recycleBin entity not found in database.");
        recycleBin.setRemovedBy(userId);
        recycleBin.setRemovedDate(LocalDateTime.now());
        return recycleBinDaoImpl.update(recycleBin);
    }

    @Override
    public List<RecycleBin> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RecycleBin> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecycleBinModel tranformToModel(RecycleBin recycleBin) {
        RecycleBinModel recycleBinModel = null;
        UserProfileService userProfileService = new UserProfileService();
        ModuleService moduleService = new ModuleService();

        String moduleName = "";
        String moduleIcon = "";
        int userCreated = 1;
        if (recycleBin != null) {
//            moduleName = moduleService.getByModuleNameEng(recycleBin.getModuleName()).getModuleName();
//            moduleIcon = moduleService.getByModuleNameEng(recycleBin.getModuleName()).getModuleIcon();
            moduleName = moduleService.getByModuleCode(recycleBin.getModuleName()).getModuleName();
            moduleIcon = moduleService.getByModuleCode(recycleBin.getModuleName()).getModuleIcon();
            recycleBinModel = new RecycleBinModel();
            recycleBinModel.setId(recycleBin.getId());
            recycleBinModel.setCreatedDate(Common.localDateTimeToString2(recycleBin.getCreatedDate()));////oat-edit
            recycleBinModel.setDescription(recycleBin.getDescription());
            recycleBinModel.setIpAddress(recycleBin.getIpAddress());
            recycleBinModel.setModuleName(moduleName);
            if (moduleName.equalsIgnoreCase(LogData.MODULE_MWP)) {
                if (recycleBin.getDescription().contains("ข้อมูลเข้า")) {
                    moduleIcon = "icon_inbox_o.png";
                } else if (recycleBin.getDescription().contains("ข้อมูลออก")) {
                    moduleIcon = "icon_outbox_o.png";
                } else if (recycleBin.getDescription().contains("แฟ้มส่วนตัว")) {
                    moduleIcon = "icon_folder_o.png";
                }
            }
            recycleBinModel.setModuleIcon(moduleIcon);
            if (userCreated <= 0) {
                userCreated = 1;
            }
            recycleBinModel.setUserProfileName(userProfileService.getByIdNotRemoved(userCreated).getUserProfileFullName());
        }
        return recycleBinModel;
    }

    @Override
    public int countAll() {
        return recycleBinDaoImpl.countAll();
    }

    @Override
    public List<RecycleBin> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return recycleBinDaoImpl.search(queryParams, offset, limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        return recycleBinDaoImpl.countSearch(queryParams);
    }

    public List<RecycleBin> searchByUserProfileId(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir, Integer userProfileId) {
        return recycleBinDaoImpl.searchByUserProfileId(queryParams, offset, limit, sort, dir, userProfileId);
    }

    public int countSearchByUserProfileId(MultivaluedMap<String, String> queryParams, Integer userProfileId) {
        return recycleBinDaoImpl.countSearchByUserProfileId(queryParams, userProfileId);
    }

    @Override
    public RecycleBin getByIdNotRemoved(int id) {
        checkNotNull(id, "RecycleBin id entity must not be null");
        return recycleBinDaoImpl.getByIdNotRemoved(id);
    }

    public RecycleBin restoreEntity(RecycleBin recycleBin) {
        String entityName = recycleBin.getEntityName();
        int entityId = recycleBin.getLinkId();
        recycleBinDaoImpl.restoreEntity(entityName, entityId);
        return recycleBin;
    }

    public RecycleBin saveLogForRestoreEntity(RecycleBin recycleBin, String clientIp) {
        //For Create Log when restore Entity
        String logDescription = this.generateLogForRestoreEntity(recycleBin);
        LogData logData = new LogData();
        logData.setCreatedBy(recycleBin.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(recycleBin.getClass().getName());
        logData.setLinkId(recycleBin.getLinkId());
        logData.setModuleName(recycleBin.getModuleName());
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.restore(logData);
        return recycleBin;
    }

    private String generateLogForRestoreEntity(RecycleBin recycleBin) {
        StringBuilder mettingRoomLog = new StringBuilder();
        mettingRoomLog.append("กู้คืนรายการที่ : ");
        mettingRoomLog.append(recycleBin.getId());
        mettingRoomLog.append(" รายละเอียด : ");
        mettingRoomLog.append(Common.noNull(recycleBin.getDescription(), ""));
        return mettingRoomLog.toString();
    }

    public List<RecycleBin> searchByModel(RecycleBinSearchModel recycleBinSearchModel, String sort, String dir) {
        checkNotNull(recycleBinSearchModel, "recycleBinSearchModel entity must not be null");
        return recycleBinDaoImpl.searchByModel(recycleBinSearchModel, sort, dir);
    }

    public Integer countListByUserId(int userProfileId) {
        checkNotNull(userProfileId, "userProfileId must not be null");
        return recycleBinDaoImpl.countListByUserId(userProfileId);
    }
}
