package com.px.admin.daoimpl;

import com.px.admin.dao.LookupDetailDao;
import com.px.admin.entity.LookupDetail;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author OPAS
 */
public class LookupDetailDaoImpl extends GenericDaoImpl<LookupDetail, Integer> implements LookupDetailDao{

    public LookupDetailDaoImpl() {
        super(LookupDetail.class);
    }

    @Override
    public List<LookupDetail> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LookupDetail.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<LookupDetail> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LookupDetail.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LookupDetail.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    public List<LookupDetail> listAllByLookupId(int lookupId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.eq("lu.id", lookupId));
        DetachedCriteria criteria = DetachedCriteria.forClass(LookupDetail.class);
        criteria.createCriteria("lookup","lu",JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public LookupDetail getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(LookupDetail.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public static DetachedCriteria createOrder(DetachedCriteria criteria,String sort,String dir){
        if(!sort.isEmpty()){
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")){
                criteria.addOrder(Order.asc(sort));
            }
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")){
                criteria.addOrder(Order.desc(sort));
            }            
        }else{
            criteria.addOrder(Order.desc("createdDate")); 
        }
        return criteria;
    }
    
}
