package com.px.share.daoimpl;

import com.px.share.dao.FileAttachApproveDao;
import com.px.share.entity.FileAttachApprove;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Oat
 */
public class FileAttachApproveDaoImpl extends GenericDaoImpl<FileAttachApprove, Integer> implements FileAttachApproveDao {

    public FileAttachApproveDaoImpl() {
        super(FileAttachApprove.class);
    }

    @Override
    public FileAttachApprove getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttachApprove.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<FileAttachApprove> listAll(String sort, String dir, int fileAttachId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("fileAttach.id", fileAttachId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttachApprove.class);
        criteria.createCriteria("fileAttach", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
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

}
