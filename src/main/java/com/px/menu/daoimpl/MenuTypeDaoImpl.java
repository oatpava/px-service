package com.px.menu.daoimpl;

import com.px.menu.dao.MenuTypeDao;
import com.px.menu.entity.MenuType;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Peach
 */
public class MenuTypeDaoImpl extends GenericDaoImpl<MenuType, Integer> implements MenuTypeDao{

    public MenuTypeDaoImpl() {
        super(MenuType.class);
    }
    
    @Override
    public List<MenuType> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuType.class);
        criteria.add(conjunction);        
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<MenuType> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuType.class);
        criteria.add(conjunction);   
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public MenuType getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuType.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    @Override
    public MenuType getByMenuTypeCode(String menuTypeCode) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("menuTypeCode", menuTypeCode));
        DetachedCriteria criteria = DetachedCriteria.forClass(MenuType.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
}
