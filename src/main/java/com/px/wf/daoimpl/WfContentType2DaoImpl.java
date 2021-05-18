package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.wf.entity.WfContentType2;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.px.wf.dao.WfContentType2Dao;
import org.hibernate.criterion.Order;

/**
 *
 * @author Mali
 */
public class WfContentType2DaoImpl extends GenericDaoImpl<WfContentType2, Integer> implements WfContentType2Dao {

    public WfContentType2DaoImpl() {
        super(WfContentType2.class);
    }

    public List<WfContentType2> listByWfContentType(List<Integer> listContentType, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.in("id", listContentType));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContentType2.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        //query
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WfContentType2> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContentType2.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public WfContentType2 getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContentType2.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.asc("this.orderNo"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.desc("this.orderNo"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

}
