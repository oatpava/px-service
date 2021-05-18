package com.px.share.daoimpl;

import com.px.share.dao.LogDataDao;
import com.px.share.entity.LogData;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OPAS
 */
public class LogDataDaoImpl extends GenericDaoImpl<LogData, Integer> implements LogDataDao {

    private final String fieldSearch;

    public LogDataDaoImpl() {
        super(LogData.class);
        fieldSearch = ",createdBy,createdDateBegin,createdDateEnd,type,moduleName,description,";
    }

    @Override
    public List<LogData> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<LogData> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public LogData getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<LogData> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        List<LogData> result = (limit == 0) ? this.listByCriteria(criteria) : this.listByCriteria(criteria, offset, limit);
        return result;
    }

    public Integer countSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = Restrictions.conjunction();
        Calendar cal = Calendar.getInstance();
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains("," + key + ",")) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "createdBy":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.eq("this.createdBy", Integer.parseInt(valueArray[i])));
                                }
                                break;
                            case "createdDateBegin":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.ge("this.createdDate", Common.dateThaiToLocalDateTime(valueArray[i])));
                                }
                                break;
                            case "createdDateEnd":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.le("this.createdDate", Common.dateThaiToLocalDateTime((String) valueArray[i]).plusDays(1)));
                                }
                                break;
                            case "type":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    String[] typeArr = valueArray[i].split("฿");
                                    List<Integer> typeInt = new ArrayList<Integer>();
                                    for (i = 0; i < typeArr.length; i++) {
                                        typeInt.add(Integer.parseInt(typeArr[i]));
                                    }
                                    conjunction.add(Restrictions.in("this.type", typeInt));//Integer.parseInt(valueArray[i])
                                }
                                break;
                            case "moduleName":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.eq("this.moduleName", valueArray[i]));
                                }
                                break;
                            case "description":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.like("this.description", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                            case "subModule":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.like("this.description", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                            default:
                                conjunction.add(Restrictions.ge("this.createdDate", Common.dateThaiToLocalDateTime(valueArray[i])));
                                conjunction.add(Restrictions.le("this.createdDate", Common.dateThaiToLocalDateTime((String) valueArray[i]).plusDays(1)));
                        }
                    }
                }
            }
        }
        return conjunction;
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    public Integer countLogByModuleNameNoDate(String moduleName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("moduleName", moduleName));
        conjunction.add(Restrictions.eq("removedBy", 0));
//        conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(Common.dateThaiToEng(endDate));
//        cal.add(Calendar.DATE, 1);
//        conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
//        conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countLogByModuleName(String moduleName, String startDate, String endDate) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(Common.dateThaiToEng(endDate));
//        cal.add(Calendar.DATE, 1);
//        conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
        conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        conjunction.add(Restrictions.eq("moduleName", moduleName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countLogByModuleName(String moduleName, List<Integer> listUserProfileId, String startDate, String endDate) {
        Conjunction conjunction = Restrictions.conjunction();
        if (!listUserProfileId.isEmpty()) {
            conjunction.add(Restrictions.in("createdBy", listUserProfileId));
        }
//        conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToEng(startDate)));
        conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(Common.dateThaiToEng(endDate));
//        cal.add(Calendar.DATE, 1);
//        conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
        conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        conjunction.add(Restrictions.eq("moduleName", moduleName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countLogByModuleName(String moduleName, List<Integer> listUserProfileId, int type, String startDate, String endDate) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//        Calendar cal = Calendar.getInstance();
//        cal.setTime(Common.dateThaiToEng(endDate));
//        cal.add(Calendar.DATE, 1);
//        conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
        conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        conjunction.add(Restrictions.in("createdBy", listUserProfileId));
        conjunction.add(Restrictions.eq("type", type));
        conjunction.add(Restrictions.eq("moduleName", moduleName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public List<LogData> listLogByModuleName(String moduleName, List<Integer> listUserProfileId, List<Integer> type, String startDate, String endDate, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        if (!startDate.equals("")) {
            conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//            Calendar cal = Calendar.getInstance();
//            cal.setTime(Common.dateThaiToEng(endDate));
//            cal.add(Calendar.DATE, 1);
//            conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
            conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        }
        if (!listUserProfileId.isEmpty()) {
            conjunction.add(Restrictions.in("createdBy", listUserProfileId));
        }
        if (!type.isEmpty()) {
            conjunction.add(Restrictions.in("type", type));
        }
        if (!moduleName.equals("")) {
            conjunction.add(Restrictions.eq("moduleName", moduleName));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria = Common.createOrder(criteria, sort, dir);
        criteria.add(conjunction);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public Integer countListLogByModuleName(String moduleName, List<Integer> listUserProfileId, List<Integer> type, String startDate, String endDate) {
        Conjunction conjunction = Restrictions.conjunction();
        if (!startDate.equals("")) {
            conjunction.add(Restrictions.ge("createdDate", Common.dateThaiToLocalDateTime(startDate)));

//            Calendar cal = Calendar.getInstance();
//            cal.setTime(Common.dateThaiToEng(endDate));
//            cal.add(Calendar.DATE, 1);
//            conjunction.add(Restrictions.lt("createdDate", cal.getTime()));
            conjunction.add(Restrictions.lt("createdDate", Common.dateThaiToLocalDateTime(endDate).plusDays(1)));
        }
        if (!listUserProfileId.isEmpty()) {
            conjunction.add(Restrictions.in("createdBy", listUserProfileId));
        }
        if (!type.isEmpty()) {
            conjunction.add(Restrictions.in("type", type));
        }
        if (!moduleName.equals("")) {
            conjunction.add(Restrictions.eq("moduleName", moduleName));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countLogByUserId(int userId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", userId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(LogData.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
}
