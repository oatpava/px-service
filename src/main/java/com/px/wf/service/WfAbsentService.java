package com.px.wf.service;

import com.px.share.service.LogDataService;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.entity.LogData;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import com.px.wf.daoimpl.WfAbsentDaoImpl;
import com.px.wf.entity.WfAbsent;
import com.px.wf.model.WfAbsentModel;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author CHALERMPOL
 */
public class WfAbsentService implements GenericService<WfAbsent, WfAbsentModel> {

    private static final Logger LOG = Logger.getLogger(WfAbsentService.class.getName());
    private final WfAbsentDaoImpl wfAbsentDaoImpl;

    public WfAbsentService() {
        this.wfAbsentDaoImpl = new WfAbsentDaoImpl();
    }

    @Override
    public WfAbsent create(WfAbsent wfAbsent) {
        checkNotNull(wfAbsent, "wfAbsent entity must not be null");
        checkNotNull(wfAbsent.getAbsentDate(), "wfAbsent date must not be null");
        checkNotNull(wfAbsent.getUserId(), "wfAbsent userId must not be null");
        checkNotNull(wfAbsent.getCreatedBy(), "create by must not be null");
        wfAbsent = wfAbsentDaoImpl.create(wfAbsent);
        if (wfAbsent.getOrderNo() == 0) {
            wfAbsent.setOrderNo(wfAbsent.getId());
            wfAbsent = update(wfAbsent);
        }
        return wfAbsent;
    }

    @Override
    public WfAbsent getById(int id) {
        checkNotNull(id, "wfAbsent id entity must not be null");
        return wfAbsentDaoImpl.getById(id);
    }

    @Override
    public WfAbsent update(WfAbsent wfAbsent) {
        checkNotNull(wfAbsent, "wfAbsent entity must not be null");
        checkNotNull(wfAbsent.getAbsentDate(), "wfAbsent name must not be null");
        checkNotNull(wfAbsent.getUserId(), "wfAbsent userId must not be null");
        checkNotNull(wfAbsent.getUpdatedBy(), "update by must not be null");
        wfAbsent.setUpdatedDate(LocalDateTime.now());
        return wfAbsentDaoImpl.update(wfAbsent);
    }

    @Override
    public WfAbsent remove(int id, int userId) {
        checkNotNull(id, "wfAbsent id must not be null");
        WfAbsent wfAbsent = getById(id);
        checkNotNull(wfAbsent, "wfAbsent entity not found in database.");
        wfAbsent.setRemovedBy(userId);
        wfAbsent.setRemovedDate(LocalDateTime.now());
        return wfAbsentDaoImpl.update(wfAbsent);
    }

    @Override
    public List<WfAbsent> listAll(String sort, String dir) {
        return wfAbsentDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<WfAbsent> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return wfAbsentDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public WfAbsentModel tranformToModel(WfAbsent wfAbsent) {
        WfAbsentModel wfAbsentModel = null;
        if (wfAbsent != null) {
            wfAbsentModel = new WfAbsentModel();
            wfAbsentModel.setId(wfAbsent.getId());
            wfAbsentModel.setAbsentDate(Common.localDateTimeToString(wfAbsent.getAbsentDate()));
            wfAbsentModel.setUserId(wfAbsent.getUserId());
        }
        return wfAbsentModel;
    }

    @Override
    public int countAll() {
        return wfAbsentDaoImpl.countAll();
    }

    @Override
    public List<WfAbsent> search(MultivaluedMap<String, String> queryWfAbsents, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryWfAbsents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<WfAbsent> listByCurrentYear(int offset, int limit, String sort, String dir) {
        return wfAbsentDaoImpl.listByCurrentYear(offset, limit, sort, dir);
    }

    @Override
    public WfAbsent getByIdNotRemoved(int id) {
        checkNotNull(id, "wfAbsent id entity must not be null");
        return wfAbsentDaoImpl.getByIdNotRemoved(id);
    }

    public boolean checkDateInWfAbsent(Date dateIn) {
        boolean result = true;
        if (wfAbsentDaoImpl.checkDateInWfAbsent(dateIn).size() > 0) {
            result = false;
        }
        return result;
    }

    public int countWorkday(String beginDate, String endDate) {
        checkNotNull(beginDate, "beginDate must not be null");
        checkNotNull(endDate, "endDate must not be null");
        return wfAbsentDaoImpl.countWorkday(beginDate, endDate);
    }

    public List<WfAbsent> listByDateRange(LocalDateTime startDate, LocalDateTime endDate, int offset, int limit, String sort, String dir) {
        return wfAbsentDaoImpl.listByDateRange(startDate, endDate, offset, limit, sort, dir);
    }
//    public boolean checkDateInWfAbsent(Date dateBegin,Date dateEnd){
//        boolean result = true;
//        if(wfAbsentDaoImpl.checkDateInWfAbsent(dateIn).size()>0){
//            result = false;
//        }
//        return result;
//    }

    public WfAbsent saveLogForCreate(WfAbsent wfAbsent, String clientIp) {
        //For Create Log when create WfAbsent
        String logDescription = this.generateLogForCreateEntity(wfAbsent);
        LogData logData = new LogData();
        logData.setCreatedBy(wfAbsent.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfAbsent.getClass().getName());
        logData.setLinkId(wfAbsent.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return wfAbsent;
    }

    public WfAbsent saveLogForUpdate(WfAbsent wfAbsentOld, WfAbsent wfAbsentNew, String clientIp) {
        //For Create Log when update WfAbsent
        String logDescription = this.generateLogForUpdateEntity(wfAbsentOld, wfAbsentNew);
        LogData logData = new LogData();
        logData.setCreatedBy(wfAbsentNew.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfAbsentNew.getClass().getName());
        logData.setLinkId(wfAbsentNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return wfAbsentNew;
    }

    public WfAbsent saveLogForRemove(WfAbsent wfAbsent, String clientIp) {
        //For Create Log when remove WfAbsent
        String logDescription = this.generateLogForRemoveEntity(wfAbsent);
        LogData logData = new LogData();
        logData.setCreatedBy(wfAbsent.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfAbsent.getClass().getName());
        logData.setLinkId(wfAbsent.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return wfAbsent;
    }

    private String generateLogForCreateEntity(WfAbsent wfAbsent) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("วันหยุดพนักงาน : ");
        descriptionLog.append(wfAbsent.getUserId());
        descriptionLog.append("วันหยุด : ");
        descriptionLog.append(Common.noNull(Common.localDateTimeToString(wfAbsent.getAbsentDate()), ""));
        return descriptionLog.toString();
    }

    private String generateLogForUpdateEntity(WfAbsent wfAbsentOld, WfAbsent wfAbsentNew) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("วันหยุดพนักงาน : ");
        descriptionLog.append(wfAbsentOld.getUserId());
        descriptionLog.append("วันหยุด : ");
        descriptionLog.append(Common.noNull(Common.localDateTimeToString(wfAbsentOld.getAbsentDate()), ""));
        descriptionLog.append("เป็น");
        descriptionLog.append(Common.noNull(Common.localDateTimeToString(wfAbsentNew.getAbsentDate()), ""));
        return descriptionLog.toString();
    }

    private String generateLogForRemoveEntity(WfAbsent wfAbsent) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("วันหยุดพนักงาน : ");
        descriptionLog.append(wfAbsent.getUserId());
        descriptionLog.append("วันหยุด : ");
        descriptionLog.append(Common.noNull(Common.localDateTimeToString(wfAbsent.getAbsentDate()), ""));
        return descriptionLog.toString();
    }   
        
    public WfAbsent checkIfExist(int userId, LocalDateTime absentDate) {
        return wfAbsentDaoImpl.checkIfExist(userId, absentDate);
    }
}
