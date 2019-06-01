package com.px.share.daoimpl;

import com.px.share.dao.MonthDao;
import com.px.share.entity.Month;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Opas
 */
public class MonthDaoImpl extends GenericDaoImpl<Month, Integer> implements MonthDao{

    public MonthDaoImpl() {
        super(Month.class);
    }
    
    @Override
    public List<Month> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Month.class);
        criteria.add(conjunction);        
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<Month> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Month.class);
        criteria.add(conjunction);   
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public Month getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Month.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    private DetachedCriteria createOrder(DetachedCriteria criteria,String sort,String dir){
        if(!sort.isEmpty()){
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")){
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                }
            }else if((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")){
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                }
            }            
        }else{
            criteria.addOrder(Order.desc("this.createdDate")); 
        }
        return criteria;
    }
}
