package com.px.share.daoimpl;

import com.px.share.dao.RecycleBinDao;
import com.px.share.entity.RecycleBin;
import com.px.share.model.RecycleBinSearchModel;
import com.px.share.service.ParamService;
import com.px.share.util.AdvanceSearch;
import com.px.share.util.Common;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
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
public class RecycleBinDaoImpl extends GenericDaoImpl<RecycleBin, Integer> implements RecycleBinDao {

    private final String fieldSearch;

    public RecycleBinDaoImpl() {
        super(RecycleBin.class);
        fieldSearch = ",createdBy,createdDateBegin,createdDateEnd,type,moduleName,description,subModule,";
    }

    @Override
    public List<RecycleBin> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<RecycleBin> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public RecycleBin getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<RecycleBin> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams, 0);
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public Integer countSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams, 0);
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<RecycleBin> searchByUserProfileId(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir, Integer userProfileId) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams, userProfileId);
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<RecycleBin> listByUserId(int offset, int limit, String sort, String dir, int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("createdBy", userProfileId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public Integer countSearchByUserProfileId(MultivaluedMap<String, String> queryParams, Integer userProfileId) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams, userProfileId);
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams, Integer userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        if (userProfileId > 0) {
            conjunction.add(Restrictions.eq("createdBy", userProfileId));
        }
        Calendar cal = Calendar.getInstance();
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains("," + key + ",")) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "createdBy":
                                conjunction.add(Restrictions.eq("this.createdBy", valueArray[i]));
                                break;
                            case "createdDateBegin":
                                conjunction.add(Restrictions.ge("this.createdDate", Common.dateThaiToEng(valueArray[i])));
                                break;
                            case "createdDateEnd":
                                cal.setTime(Common.dateThaiToEng((String) valueArray[i]));
                                cal.add(Calendar.DATE, 1);
                                conjunction.add(Restrictions.le("this.createdDate", cal.getTime()));
                                break;
                            case "type":
                                conjunction.add(Restrictions.eq("this.type", Integer.parseInt(valueArray[i])));
                                break;
                            case "moduleName":
                                if (!valueArray[i].equalsIgnoreCase("")) {
                                    conjunction.add(Restrictions.eq("this.moduleName", valueArray[i]));
                                }
                                break;
                            case "description":
                                conjunction.add(Restrictions.like("this.description", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "subModule":
                                if (!valueArray[i].equalsIgnoreCase("")) {
                                    conjunction.add(Restrictions.like("this.description", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                            default:
                                conjunction.add(Restrictions.ge("this.createdDate", Common.dateThaiToEng(valueArray[i])));
                                cal.setTime(Common.dateThaiToEng((String) valueArray[i]));
                                cal.add(Calendar.DATE, 1);
                                conjunction.add(Restrictions.le("this.createdDate", cal.getTime()));
                        }
                    }
                }
            }
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
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

    public List<RecycleBin> searchByModel(RecycleBinSearchModel recycleBinSearchModel, String sort, String dir) {
        try {
            Conjunction conjunction = createConjunction(recycleBinSearchModel);
            DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
            criteria.add(conjunction);
            criteria = createOrder(criteria, sort, dir);
            return this.listByCriteria(criteria);
        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
            return null;
        }
    }

    private Conjunction createConjunction(RecycleBinSearchModel recycleBinSearchModel) {
        ParamService paramService = new ParamService();
        String symbolAnd = paramService.getByParamName("ANDTXT").getParamValue();
        String symbolOr = paramService.getByParamName("ORTXT").getParamValue();
        String symbolNot = paramService.getByParamName("NOTTXT").getParamValue();
        
        Conjunction conjunction = Restrictions.conjunction();
        String moduleName = recycleBinSearchModel.getModuleName();
        String description = recycleBinSearchModel.getDescription();
        String startDate = recycleBinSearchModel.getStartDate();
        String endDate = recycleBinSearchModel.getEndDate();

        if (moduleName != null && moduleName != "") {
            conjunction.add(Restrictions.eq("this.moduleName", moduleName));
        }
        if (description != null && description != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.description", description, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (startDate != null && startDate != "") {
            conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(recycleBinSearchModel.getStartDate())));
        }
        if (endDate != null && endDate != "") {
            conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(recycleBinSearchModel.getEndDate()).plusHours(23).plusMinutes(59)));
        }
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        return conjunction;
    }

    public Integer countListByUserId(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("createdBy", userProfileId));
         conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(RecycleBin.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

}
