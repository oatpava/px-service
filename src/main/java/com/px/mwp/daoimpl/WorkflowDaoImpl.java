package com.px.mwp.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.mwp.dao.WorkflowDao;
import com.px.mwp.entity.Workflow;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Oat
 */
public class WorkflowDaoImpl extends GenericDaoImpl<Workflow, Integer> implements WorkflowDao {

    public WorkflowDaoImpl() {
        super(Workflow.class);
    }

    @Override
    public Workflow getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Workflow.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<Workflow> listByLinkIdGetRemoved(int linkId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Workflow.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Workflow getByLinkIdAndLinkId2(int linkId, int linkId2, String type) {//for "F" and "C" only
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId2", linkId2));
        conjunction.add(Restrictions.eq("workflowActionType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(Workflow.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public Workflow getByLinkIdAndLinkId3(int linkId, int linkId3, String type) {//for "F" and "C" only
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId3", linkId3));
        conjunction.add(Restrictions.eq("workflowActionType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(Workflow.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public List<Workflow> listByLinkIdAndLinkId3(int linkId, int linkId3, String type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId3", linkId3));
        conjunction.add(Restrictions.eq("workflowActionType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(Workflow.class);
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
