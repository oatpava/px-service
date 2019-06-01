package com.px.mwp.daoimpl;

import com.px.mwp.dao.InOutAssignDao;
import com.px.mwp.entity.InOutAssign;
import com.px.share.daoimpl.GenericDaoImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class InOutAssignDaoImpl extends GenericDaoImpl<InOutAssign, Integer> implements InOutAssignDao {

    public InOutAssignDaoImpl() {
        super(InOutAssign.class);
    }

    @Override
    public InOutAssign getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InOutAssign> listByAssignId(int assignId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("inOutAssignAssignId", assignId));
        DetachedCriteria criteria = DetachedCriteria.forClass(InOutAssign.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<InOutAssign> listByOwnerId(int ownerId, int ownerType, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("inOutAssignOwnerId", ownerId));
        conjunction.add(Restrictions.eq("inOutAssignOwnerType", ownerType));
        DetachedCriteria criteria = DetachedCriteria.forClass(InOutAssign.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public List<InOutAssign> listByOwnerIdAndAssignId(int ownerId, int ownerType,int assignId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("inOutAssignOwnerId", ownerId));
        conjunction.add(Restrictions.eq("inOutAssignOwnerType", ownerType));
        conjunction.add(Restrictions.eq("inOutAssignAssignId", assignId));
        DetachedCriteria criteria = DetachedCriteria.forClass(InOutAssign.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<InOutAssign> listAfterEndDate(LocalDateTime nowDate) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("inOutAssignIsperiod", 1));
        conjunction.add(Restrictions.lt("inOutAssignEndDate", nowDate));
        DetachedCriteria criteria = DetachedCriteria.forClass(InOutAssign.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
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
                    case "inOutAssignAssignId":
                        criteria.addOrder(Order.asc("this.inOutAssignAssignId"));
                        break;
                    case "inOutAssignStartDate":
                        criteria.addOrder(Order.asc("this.inOutAssignStartDate"));
                        break;
                    case "inOutAssignEndDate":
                        criteria.addOrder(Order.asc("this.inOutAssignEndDate"));
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
                    case "inOutAssignAssignId":
                        criteria.addOrder(Order.desc("this.inOutAssignAssignId"));
                        break;
                    case "inOutAssignStartDate":
                        criteria.addOrder(Order.desc("this.inOutAssignStartDate"));
                        break;
                    case "inOutAssignEndDate":
                        criteria.addOrder(Order.desc("this.inOutAssignEndDate"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

}
