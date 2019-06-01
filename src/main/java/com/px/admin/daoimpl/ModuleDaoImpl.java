package com.px.admin.daoimpl; 

import com.px.admin.dao.ModuleDao;
import com.px.admin.entity.Module;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ModuleDaoImpl extends GenericDaoImpl<Module, Integer> implements ModuleDao{

    public ModuleDaoImpl() {
        super(Module.class);
    }

    @Override
    public List<Module> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);        
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<Module> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);   
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    public List<Module> listAllForConfig(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("moduleConfigInAdmin", 1));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);   
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Module getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    @Override
    public Module getByModuleCode(String moduleCode) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("moduleCode", moduleCode));
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
        
    public Module getByModuleNameEng(String moduleNameEng) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("moduleNameEng", moduleNameEng));
        DetachedCriteria criteria = DetachedCriteria.forClass(Module.class);
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
