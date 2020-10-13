package com.px.share.service;

import com.px.admin.service.ModuleService;
import com.px.admin.service.UserProfileService;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.UserProfile;
import com.px.share.daoimpl.LogDataDaoImpl;
import com.px.share.entity.LogData;
import com.px.share.entity.RecycleBin;
import com.px.share.model.LogDataModel;
import com.px.share.model.LogDataModel_report;
import com.px.share.util.Common;
import java.time.LocalDateTime;

/**
 *
 * @author OPAS
 */
public class LogDataService implements GenericService<LogData, LogDataModel> {

    private static final Logger LOG = Logger.getLogger(LogDataService.class.getName());
    private final LogDataDaoImpl logDataDaoImpl;

    public LogDataService() {
        this.logDataDaoImpl = new LogDataDaoImpl();
    }

    @Override
    public LogData create(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        logData = logDataDaoImpl.create(logData);
        return logData;
    }

    @Override
    public LogData getById(int id) {
        checkNotNull(id, "logData id entity must not be null");
        return logDataDaoImpl.getById(id);
    }

    @Override
    public LogData update(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getUpdatedBy(), "update by must not be null");
        logData.setUpdatedDate(LocalDateTime.now());
        return logDataDaoImpl.update(logData);
    }

    @Override
    public LogData remove(int id, int userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LogData> listAll(String sort, String dir) {
        return logDataDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<LogData> list(int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogDataModel tranformToModel(LogData logData) {
        LogDataModel logDataModel = null;
        UserProfileService userProfileService = new UserProfileService();
        ModuleService moduleService = new ModuleService();
        String logType = "";
        int userCreated = 1;
        if (logData != null) {
            logDataModel = new LogDataModel();
            logDataModel.setId(logData.getId());
            if (logData.getType() == 1) {
                logType = logData.getDescription();
            } else if (logData.getType() == 2) {
                logType = logData.getDescription();
            } else if (logData.getType() == 3) {
                logType = logData.getDescription();
            } else {
                logType = logData.getDescription();
            }
            logDataModel.setCreatedDate(Common.localDateTimeToString2(logData.getCreatedDate()));
            logDataModel.setDescription(logType);
            logDataModel.setIpAddress(logData.getIpAddress());
            userCreated = logData.getCreatedBy();
            if (userCreated <= 0) {
                userCreated = 1;
            }
            if (logData.getType() == 4 || logData.getType() == 10) {//4=login, 10=logout //userCreated = userId
                List<UserProfile> listUserProfile = userProfileService.listByUserId(userCreated, "createdDate", "asc");
                String name = (listUserProfile.size() > 0) ? listUserProfile.get(0).getUserProfileFullName() : "-";
                logDataModel.setUserProfileName(name);
                logDataModel.setModuleName(moduleService.getByModuleCode("wf").getModuleName());
                logDataModel.setModuleIcon(moduleService.getByModuleCode("wf").getModuleIcon());
            } else {//userCreated = userProfileId
                logDataModel.setUserProfileName(userProfileService.getById(userCreated).getUserProfileFullName());
                logDataModel.setModuleName(moduleService.getByModuleCode(logData.getModuleName()).getModuleName());
                logDataModel.setModuleIcon(moduleService.getByModuleCode(logData.getModuleName()).getModuleIcon());
            }
            logDataModel.setModuleName(moduleService.getByModuleCode(logData.getModuleName()).getModuleName());
            logDataModel.setModuleIcon(moduleService.getByModuleCode(logData.getModuleName()).getModuleIcon());
        }
        return logDataModel;
    }

    @Override
    public int countAll() {
        return logDataDaoImpl.countAll();
    }

    @Override
    public List<LogData> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return logDataDaoImpl.search(queryParams, offset, limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        return logDataDaoImpl.countSearch(queryParams);
    }

    @Override
    public LogData getByIdNotRemoved(int id) {
        checkNotNull(id, "LogData id entity must not be null");
        return logDataDaoImpl.getByIdNotRemoved(id);
    }

    public LogData createEntity(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 1 for create
        logData.setType(1);
        return this.create(logData);
    }

    public LogData updateEntity(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 2 for update
        logData.setType(2);
        return this.create(logData);
    }

    /*
    * For remove set type 3 
     */
    public LogData removeEntity(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        logData.setType(3);
        LogData result = this.create(logData);
        checkNotNull(logData, "result logData entity for remove must not be null");

        RecycleBin recycleBin = new RecycleBin();
        recycleBin.setCreatedBy(logData.getCreatedBy());
        recycleBin.setDescription(logData.getDescription());
        recycleBin.setEntityName(logData.getEntityName());
        recycleBin.setIpAddress(logData.getIpAddress());
        recycleBin.setLinkId(logData.getLinkId());
        recycleBin.setModuleName(logData.getModuleName());
        RecycleBinService recycleBinService = new RecycleBinService();
        recycleBin = recycleBinService.create(recycleBin);
        return result;
    }

    public LogData login(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 4 for login
        logData.setType(4);
        return this.create(logData);
    }

    public LogData restore(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 5 for restore
        logData.setType(5);
        return this.create(logData);
    }

    public LogData print(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 6 for print
        logData.setType(6);
        return this.create(logData);
    }

    public LogData copy(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 7 for copy
        logData.setType(7);
        return this.create(logData);
    }

    public LogData export(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 8 for export
        logData.setType(8);
        return this.create(logData);
    }

    public LogData open(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 9 for open
        logData.setType(9);
        return this.create(logData);
    }

    public LogData logout(LogData logData) {
        checkNotNull(logData, "logData entity must not be null");
        checkNotNull(logData.getCreatedBy(), "create by must not be null");
        //Type 10 for logout
        logData.setType(10);
        return this.create(logData);
    }

    public Integer countLogByModuleNameNoDate(String moduleName) {
        checkNotNull(moduleName, "moduleName must not be null");
        return logDataDaoImpl.countLogByModuleNameNoDate(moduleName);
    }

    public Integer countLogByModuleName(String moduleName, String startDate, String endDate) {
        checkNotNull(moduleName, "moduleName must not be null");
        checkNotNull(startDate, "startDate must not be null");
        checkNotNull(endDate, "endDate must not be null");
        return logDataDaoImpl.countLogByModuleName(moduleName, startDate, endDate);
    }

    public Integer countLogByModuleName(String moduleName, List<Integer> listUserProfileId, String startDate, String endDate) {
        checkNotNull(moduleName, "moduleName must not be null");
        checkNotNull(listUserProfileId, "listUserProfileId must not be null");
        checkNotNull(startDate, "startDate must not be null");
        checkNotNull(endDate, "endDate must not be null");
        return logDataDaoImpl.countLogByModuleName(moduleName, listUserProfileId, startDate, endDate);
    }

    public Integer countLogByModuleName(String moduleName, List<Integer> listUserProfileId, int type, String startDate, String endDate) {
        checkNotNull(moduleName, "moduleName must not be null");
        checkNotNull(listUserProfileId, "listUserProfileId must not be null");
        checkNotNull(type, "type must not be null");
        checkNotNull(startDate, "startDate must not be null");
        checkNotNull(endDate, "endDate must not be null");
        return logDataDaoImpl.countLogByModuleName(moduleName, listUserProfileId, type, startDate, endDate);
    }

    public List<LogData> listLogByModuleName(String moduleName, List<Integer> listUserProfileId, List<Integer> type, String startDate, String endDate, int offset, int limit, String sort, String dir) {
        return logDataDaoImpl.listLogByModuleName(moduleName, listUserProfileId, type, startDate, endDate, offset, limit, sort, dir);
    }

    public Integer countListLogByModuleName(String moduleName, List<Integer> listUserProfileId, List<Integer> type, String startDate, String endDate) {
        return logDataDaoImpl.countListLogByModuleName(moduleName, listUserProfileId, type, startDate, endDate);
    }

    public Integer countLogByUserId(int userId) {
        checkNotNull(userId, "userId must not be null");
        return logDataDaoImpl.countLogByUserId(userId);
    }

    public LogDataModel_report tranformToModelReport(String moduleName, int countLog) {
        LogDataModel_report logDataModel_report = new LogDataModel_report();
        logDataModel_report.setModuleName(moduleName);
        logDataModel_report.setCountLog(countLog);

        return logDataModel_report;
    }

}
