/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.dms.dao.DmsMenuDao;
import com.px.dms.entity.DmsMenu;
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
public class DmsMenuDaoImpl extends GenericDaoImpl<DmsMenu, Integer> implements DmsMenuDao {

    public DmsMenuDaoImpl() {
        super(DmsMenu.class);
    }

    @Override
    public List<DmsMenu> findListMenu() {
         Conjunction conjunction  = Restrictions.conjunction();   
         conjunction.add(Restrictions.eq("removedBy", 0));
        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsMenu.class);
        criteria.add(conjunction);
        
        //Query result
//        DmsMenuService dmsMenuHelper = new DmsMenuService();
        return this.listByCriteria(criteria);
    }
    
     public Integer countAll() {    
        return this.listAll("","").size();
    }

   
    public List<DmsMenu> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsMenu.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        
        return this.listByCriteria(criteria,offset,limit);
    }

    
    public List<DmsMenu> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsMenu.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        
        return this.listByCriteria(criteria);
    }
    
    @Override
    public DmsMenu getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsMenu.class);
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
