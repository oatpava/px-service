package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericTreeDaoImpl;
import com.px.wf.entity.ThegifDepartment;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.px.wf.dao.ThegifDepartmentDao;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

/**
 *
 * @author Mali
 */
public class ThegifDepartmentDaoImpl extends GenericTreeDaoImpl<ThegifDepartment, Integer> implements ThegifDepartmentDao {

    private final String fieldSearch;

    public ThegifDepartmentDaoImpl() {
        super(ThegifDepartment.class);
        fieldSearch = "thegifDepartmentName,thegifDepartmentCode";
    }

    @Override
    public ThegifDepartment getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ThegifDepartment> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDepartment.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public ThegifDepartment getByThegifDepartmentCode(String thegifDepartmentCode) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("thegifDepartmentCode", thegifDepartmentCode));
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDepartment.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<ThegifDepartment> searchThegifDepartment(MultivaluedMap<String, String> queryParams, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchThegifDepartment(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDepartment.class);
        criteria.add(conjunction);

        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public int countSearchThegifDepartment(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearchThegifDepartment(queryParams);
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.isNotNull("thegifDepartmentServiceName"));
        conjunction.add(Restrictions.isNotEmpty("thegifDepartmentServiceName"));
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDepartment.class);
        criteria.add(conjunction);

        return this.listByCriteria(criteria).size();
    }

    @Override
    public Conjunction createConjunctionFormSearchThegifDepartment(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.isNotNull("this.thegifDepartmentServiceName"));
        conjunction.add(Restrictions.ne("this.thegifDepartmentServiceName", ""));

        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "thegifDepartmentName":
                                if (valueArray[i] != null && !valueArray[i].equals("")) {
                                    conjunction.add(Restrictions.like("this.thegifDepartmentName", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                            case "thegifDepartmentCode":
                                if (valueArray[i] != null && !valueArray[i].equals("")) {
                                    conjunction.add(Restrictions.like("this.thegifDepartmentCode", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                        }
                    }
                }
            }
        }
        return conjunction;
    }

    @Override
    public List<ThegifDepartment> listByThegifDepartmentName(String thegifDepartmentName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("thegifDepartmentName", thegifDepartmentName, MatchMode.ANYWHERE));
        DetachedCriteria criteria = DetachedCriteria.forClass(ThegifDepartment.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }
}
