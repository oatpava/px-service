package com.px.admin.daoimpl;

import com.px.admin.dao.AlertDao;
import com.px.admin.entity.Alert;
import com.px.share.daoimpl.GenericDaoImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Oat
 */
public class AlertDaoImpl extends GenericDaoImpl<Alert, Integer> implements AlertDao {

    public AlertDaoImpl() {
        super(Alert.class);
    }

    @Override
    public Alert getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Alert.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<Alert> list(Boolean active, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        if (active != null) {
            conjunction.add(Restrictions.eq("active", active ? "Y" : "N"));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Alert.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Alert> listAll(Boolean active, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        if (active != null) {
            conjunction.add(Restrictions.eq("active", active ? "Y" : "N"));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Alert.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<Alert> listAllCurrent(List<Integer> listExcludeId) {
        final LocalDateTime now = LocalDateTime.now();

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.le("startDate", now));
        conjunction.add(Restrictions.ge("endDate", now));
        conjunction.add(Restrictions.eq("active", "Y"));
        if (listExcludeId != null && !listExcludeId.isEmpty()) {
            conjunction.add(Restrictions.not(Restrictions.in("id", listExcludeId)));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Alert.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, "startDate", "asc");
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll(Boolean active) {
        Conjunction conjunction = Restrictions.conjunction();
        if (active != null) {
            conjunction.add(Restrictions.eq("active", active ? "Y" : "N"));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Alert.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (sort != null && !sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                    case "startDate":
                        criteria.addOrder(Order.asc("this.startDate"));
                        break;
                    case "endDate":
                        criteria.addOrder(Order.asc("this.endDate"));
                        break;
                    case "id":
                        criteria.addOrder(Order.asc("this.id"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "startDate":
                        criteria.addOrder(Order.desc("this.startDate"));
                        break;
                    case "endDate":
                        criteria.addOrder(Order.desc("this.endDate"));
                        break;
                    case "id":
                        criteria.addOrder(Order.desc("this.id"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.asc("this.startDate"));
        }
        return criteria;
    }

}
