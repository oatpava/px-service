package com.px.admin.service;

import com.px.share.service.LogDataService;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.HolidayDaoImpl;
import com.px.admin.entity.Holiday;
import com.px.share.entity.LogData;
import com.px.admin.model.HolidayModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class HolidayService implements GenericService<Holiday, HolidayModel>{
    private static final Logger LOG = Logger.getLogger(HolidayService.class.getName());
    private final HolidayDaoImpl holidayDaoImpl;
    
    public HolidayService() {
        this.holidayDaoImpl = new HolidayDaoImpl();
    }

    @Override
    public Holiday create(Holiday holiday) {
        checkNotNull(holiday, "holiday entity must not be null");
        checkNotNull(holiday.getHolidayName(), "holiday name must not be null");
        checkNotNull(holiday.getCreatedBy(),"create by must not be null");
        holiday = holidayDaoImpl.create(holiday);
        if(holiday.getOrderNo()==0){
            holiday.setOrderNo(holiday.getId());
            holiday = update(holiday);
        }
        return holiday;
    }

    @Override
    public Holiday getById(int id) {
        checkNotNull(id, "holiday id entity must not be null");
        return holidayDaoImpl.getById(id);
    }

    @Override
    public Holiday update(Holiday holiday) {
        checkNotNull(holiday, "holiday entity must not be null");
        checkNotNull(holiday.getHolidayName(), "holiday name must not be null");
        checkNotNull(holiday.getUpdatedBy(),"update by must not be null");
        holiday.setUpdatedDate(LocalDateTime.now());
        return holidayDaoImpl.update(holiday);
    }

    @Override
    public Holiday remove(int id, int userId) {
        checkNotNull(id, "holiday id must not be null");
        Holiday holiday = getById(id);
        checkNotNull(holiday, "holiday entity not found in database.");
        holiday.setRemovedBy(userId);
        holiday.setRemovedDate(LocalDateTime.now());
        return holidayDaoImpl.update(holiday);
    }

    @Override
    public List<Holiday> listAll(String sort, String dir) {
        return holidayDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Holiday> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return holidayDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public HolidayModel tranformToModel(Holiday holiday) {
        HolidayModel holidayModel = null;
        if(holiday!=null){
            holidayModel = new HolidayModel();
            holidayModel.setId(holiday.getId());
            holidayModel.setHolidayDate(Common.localDateTimeToString(holiday.getHolidayDate()));
            holidayModel.setHolidayName(holiday.getHolidayName());
        }
        return holidayModel;
    }

    @Override
    public int countAll() {
        return holidayDaoImpl.countAll();
    }

    @Override
    public List<Holiday> search(MultivaluedMap<String, String> queryHolidays, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryHolidays) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Holiday> listByCurrentYear(int offset, int limit, String sort, String dir) {
        return holidayDaoImpl.listByCurrentYear(offset, limit, sort, dir);
    }

    @Override
    public Holiday getByIdNotRemoved(int id) {
        checkNotNull(id, "holiday id entity must not be null");
        return holidayDaoImpl.getByIdNotRemoved(id);
    }
    
    public boolean checkDateInHoliday(Date dateIn){
        boolean result = true;
        if(holidayDaoImpl.checkDateInHoliday(dateIn).size()>0){
            result = false;
        }
        return result;
    }
    
    public int countWorkday(String beginDate,String endDate){
        checkNotNull(beginDate, "beginDate must not be null");
        checkNotNull(endDate, "endDate must not be null");
        return holidayDaoImpl.countWorkday(beginDate,endDate);
    }    
    
    public List<Holiday> listByDateRange(LocalDateTime startDate,LocalDateTime endDate,int offset, int limit, String sort, String dir) {
        return holidayDaoImpl.listByDateRange(startDate,endDate,offset, limit, sort, dir);
    }
//    public boolean checkDateInHoliday(Date dateBegin,Date dateEnd){
//        boolean result = true;
//        if(holidayDaoImpl.checkDateInHoliday(dateIn).size()>0){
//            result = false;
//        }
//        return result;
//    }
    
    public Holiday saveLogForCreate(Holiday holiday, String clientIp){
        //For Create Log when create Holiday
        String logDescription = this.generateLogForCreateEntity(holiday);
        LogData logData = new LogData();
        logData.setCreatedBy(holiday.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(holiday.getClass().getName());
        logData.setLinkId(holiday.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return holiday;
    }
    
    public Holiday saveLogForUpdate(Holiday holidayOld, Holiday holidayNew, String clientIp){
        //For Create Log when update Holiday
        String logDescription = this.generateLogForUpdateEntity(holidayOld,holidayNew);
        LogData logData = new LogData();
        logData.setCreatedBy(holidayNew.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(holidayNew.getClass().getName());
        logData.setLinkId(holidayNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return holidayNew;
    }
    
    public Holiday saveLogForRemove(Holiday holiday, String clientIp){
        //For Create Log when remove Holiday
        String logDescription = this.generateLogForRemoveEntity(holiday);
        LogData logData = new LogData();
        logData.setCreatedBy(holiday.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(holiday.getClass().getName());
        logData.setLinkId(holiday.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return holiday;
    }
    
    private String generateLogForCreateEntity(Holiday holiday){
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อวันหยุด : ");
        descriptionLog.append(Common.noNull(holiday.getHolidayName(), ""));
        return descriptionLog.toString();
    }
    
    private String generateLogForUpdateEntity(Holiday holidayOld,Holiday holidayNew){
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อวันหยุด : ");
        descriptionLog.append(holidayOld.getHolidayName());
        descriptionLog.append("เป็น");
        descriptionLog.append(Common.noNull(holidayNew.getHolidayName(),""));        
        return descriptionLog.toString();
    }
    
    private String generateLogForRemoveEntity(Holiday holiday){
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ชื่อวันหยุด : ");
        descriptionLog.append(holiday.getHolidayName());        
        return descriptionLog.toString();
    }
}
