package com.px.share.daoimpl;

import com.px.share.dao.FileAttachDao;
import com.px.share.entity.FileAttach;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OPAS
 */
public class FileAttachDaoImpl extends GenericDaoImpl<FileAttach, Integer> implements FileAttachDao {

    public FileAttachDaoImpl() {
        super(FileAttach.class);
    }

    @Override
    public List<FileAttach> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<FileAttach> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<FileAttach> listAllByLinkTypeLinkId(String linkType, int linkId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkType", linkType));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    //oat-add
    public List<FileAttach> listAllAfterAdd(String linkType, int linkId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("secrets", 0));
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkType", linkType));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<FileAttach> listByLinkTypeLinkId(String linkType, int linkId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkType", linkType));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public FileAttach getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public static DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(sort));
            }
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(sort));
            }
        } else {
            criteria.addOrder(Order.desc("createdDate"));
        }
        return criteria;
    }

    public FileAttach getByReferenceId(int referenceId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("referenceId", referenceId));
        //conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    //oat-add
    public int countAllByLinkTypeLinkId(String linkType, int linkId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkType", linkType));
        conjunction.add(Restrictions.ne("fileAttachType", ".TTT"));
        conjunction.add(Restrictions.eq("referenceId", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FileAttach.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
}
