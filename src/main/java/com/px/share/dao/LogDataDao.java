package com.px.share.dao;

import com.px.share.entity.LogData;
import java.util.List;

/**
 *
 * @author OPAS
 */
public interface LogDataDao extends GenericDao<LogData, Integer>{
    List<LogData> list(int offset,int limit,String sort,String dir);
    List<LogData> listAll(String sort,String dir);    
    Integer countAll();
    Integer countLogByModuleName(String moduleName,String startDate,String endDate);
    Integer countLogByModuleName(String moduleName,List<Integer> listUserProfileId,String startDate,String endDate);
    Integer countLogByModuleName(String moduleName,List<Integer> listUserProfileId,int type,String startDate,String endDate);
    List<LogData> listLogByModuleName(String moduleName,List<Integer> listUserProfileId,List<Integer> type,String startDate,String endDate, int offset, int limit, String sort, String dir);
    Integer countListLogByModuleName(String moduleName,List<Integer> listUserProfileId,List<Integer> type,String startDate,String endDate);
    Integer countLogByUserId(int userId);
}
