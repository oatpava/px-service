package com.px.mwp.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.mwp.dao.WorkflowToDao;
import com.px.mwp.entity.WorkflowTo;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Mali
 */
public class WorkflowToDaoImpl extends GenericDaoImpl<WorkflowTo, Integer> implements WorkflowToDao {

    public WorkflowToDaoImpl() {
        super(WorkflowTo.class);
    }

    @Override
    public List<WorkflowTo> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WorkflowTo> listByWorkflowId(int workflowId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    //oat-add
    public List<WorkflowTo> listByContentId(int contentId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.linkId2", contentId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WorkflowTo> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public WorkflowTo getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<WorkflowTo> listByWorkflowId(int workflowId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WorkflowTo> listByUserProfileId(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.userProfileId", userProfileId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WorkflowTo> listByStructureId(int structureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.structureId", structureId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public WorkflowTo getByWorkflowIdAndUserProfileId(int workflowId, int userprofileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.userProfileId", userprofileId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public WorkflowTo getByWorkflowIdAndStructureId(int workflowId, int structureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wf.id", workflowId));
        conjunction.add(Restrictions.eq("this.structureId", structureId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.createCriteria("workflow", "wf", JoinType.INNER_JOIN);
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

    public List<WorkflowTo> listByName(int userId, String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.createdBy", userId));
        conjunction.add(Restrictions.like("this.userProfileFullName", name, MatchMode.ANYWHERE));
        DetachedCriteria criteria = DetachedCriteria.forClass(WorkflowTo.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

}
