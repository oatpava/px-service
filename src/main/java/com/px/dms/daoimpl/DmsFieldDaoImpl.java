/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.dms.dao.DmsFieldDao;
import com.px.dms.entity.DmsField;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author TOP
 */
public class DmsFieldDaoImpl extends GenericDaoImpl<DmsField, Integer> implements DmsFieldDao {

    public DmsFieldDaoImpl() {
        super(DmsField.class);
    }

    @Override
    public List<DmsField> listDmsField() {
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsField.class);
        criteria.addOrder(Order.asc("orderNo"));

        return this.listByCriteria(criteria);
    }

    public Integer countAll() {
        return this.listAll("", "").size();
    }

    public List<DmsField> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsField.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DmsField> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsField.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria);
    }

    @Override
    public DmsField getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsField.class);
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
    
    public DmsField getByFieldMap(String map) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsFieldMap", map));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsField.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
}
