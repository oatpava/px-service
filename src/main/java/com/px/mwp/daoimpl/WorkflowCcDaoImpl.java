package com.px.mwp.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.mwp.dao.WorkflowCcDao;
import com.px.mwp.entity.WorkflowCc;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Mali
 */
public class WorkflowCcDaoImpl extends GenericDaoImpl<WorkflowCc, Integer> implements WorkflowCcDao {

    public WorkflowCcDaoImpl() {
        super(WorkflowCc.class);
    }

    @Override
    public List<WorkflowCc> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WorkflowCc> listByWorkflowId(int workflowId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WorkflowCc> listByWorkflowId(int workflowId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WorkflowCc> listByUserProfileId(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.userProfileId", userProfileId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WorkflowCc> listByStructureId(int structureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.structureId", structureId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public WorkflowCc getByWorkflowIdAndUserProfileId(int workflowId, int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.userProfileId", userProfileId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public WorkflowCc getByWorkflowIdAndStructureId(int workflowId, int structureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.structureId", structureId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<WorkflowCc> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public WorkflowCc getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowCc.class);
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
