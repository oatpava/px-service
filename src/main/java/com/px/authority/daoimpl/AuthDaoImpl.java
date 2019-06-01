package com.px.authority.daoimpl; 

import com.px.authority.dao.AuthDao;
import com.px.authority.entity.Auth;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class AuthDaoImpl extends GenericDaoImpl<Auth, Integer> implements AuthDao{

    public AuthDaoImpl() {
        super(Auth.class);
    }

    @Override
    public List<Auth> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Auth.class);
        criteria.add(conjunction);        
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<Auth> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Auth.class);
        criteria.add(conjunction);   
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public Auth getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Auth.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
