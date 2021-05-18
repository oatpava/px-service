/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.dms.dao.borrowDao;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.borrow;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author top
 */
public class borrowDaoImpl extends GenericDaoImpl<borrow, Integer> implements borrowDao {

    public borrowDaoImpl() {
        super(borrow.class);
    }

    @Override
    public borrow getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public borrow lend(DmsDocument document, int numDate, int lendID, int handlerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public borrow Returned(DmsDocument document, String returnDate, int returnedID, int handlerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<borrow> history(DmsDocument document) {
        Conjunction conjunction = Restrictions.conjunction();

        conjunction.add(Restrictions.eq("dmsDocument", document));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(borrow.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<borrow> getView(DmsDocument document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkStatusDocBorrow(DmsDocument document) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsDocument", document));
//        conjunction.add(Restrictions.or(Restrictions.eq("returnName", ""), Restrictions.isNull("returnName"));
        conjunction.add(Restrictions.or(Restrictions.eq("returnName", ""), Restrictions.isNull("returnName")));
        conjunction.add(Restrictions.eq("removedBy", 0));
        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(borrow.class);
        criteria.add(conjunction);

        List<borrow> dmsDocumentlist = this.listByCriteria(criteria);
        int borrowId = 0;
        if (dmsDocumentlist.size() != 0) {
            borrow borrow = dmsDocumentlist.get(0);
            if (borrow != null) {
                borrowId = borrow.getId();
            }
        }
        return borrowId > 0;
    }

    public List<borrow> getByIdNotRemoved(DmsDocument document) {
        Conjunction conjunction = Restrictions.conjunction();

        conjunction.add(Restrictions.eq("dmsDocument", document));
//        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(borrow.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    public List<borrow> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(borrow.class);
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
