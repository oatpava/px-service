package com.px.admin.daoimpl; 

import com.px.admin.dao.SubmoduleDao;
import com.px.admin.entity.Submodule;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SubmoduleDaoImpl extends GenericDaoImpl<Submodule, Integer> implements SubmoduleDao{

    public SubmoduleDaoImpl() {
        super(Submodule.class);
    }

    @Override
    public List<Submodule> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Submodule.class);
        criteria.add(conjunction);        
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<Submodule> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Submodule.class);
        criteria.add(conjunction);   
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public Submodule getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Submodule.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    @Override
    public Submodule getBySubmoduleCode(String submoduleCode) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("submoduleCode", submoduleCode));
        DetachedCriteria criteria = DetachedCriteria.forClass(Submodule.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
