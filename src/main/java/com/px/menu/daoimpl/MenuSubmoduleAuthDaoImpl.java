package com.px.menu.daoimpl;

import com.px.authority.entity.SubmoduleAuth;
import com.px.menu.dao.MenuSubmoduleAuthDao;
import com.px.menu.entity.MenuSubmoduleAuth;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Peach
 */
public class MenuSubmoduleAuthDaoImpl extends GenericDaoImpl<MenuSubmoduleAuth, Integer> implements MenuSubmoduleAuthDao{

    public MenuSubmoduleAuthDaoImpl() {
        super(MenuSubmoduleAuth.class);
    }
    
    @Override
    public List<MenuSubmoduleAuth> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuSubmoduleAuth.class);
        criteria.add(conjunction);        
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<MenuSubmoduleAuth> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuSubmoduleAuth.class);
        criteria.add(conjunction);   
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public List<MenuSubmoduleAuth> listBySubmoduleAuth(SubmoduleAuth submoduleAuth,String sort, String dir){
        List<MenuSubmoduleAuth> result = new ArrayList();
        if(submoduleAuth!=null){
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            DetachedCriteria criteria = DetachedCriteria.forClass(MenuSubmoduleAuth.class);
            criteria.add(conjunction);   
            criteria = Common.createOrder(criteria,sort,dir);
            result = this.listByCriteria(criteria);
        }
        
        return result;
    }
    
    @Override
    public List<MenuSubmoduleAuth> listBySubmoduleAuth(List<SubmoduleAuth> submoduleAuth,String sort, String dir){
        List<MenuSubmoduleAuth> result = new ArrayList();
        if(submoduleAuth!=null){
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            DetachedCriteria criteria = DetachedCriteria.forClass(MenuSubmoduleAuth.class);
            criteria.add(conjunction);   
            criteria = Common.createOrder(criteria,sort,dir);
            result = this.listByCriteria(criteria);
        }
        
        return result;
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public MenuSubmoduleAuth getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuSubmoduleAuth.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
}
