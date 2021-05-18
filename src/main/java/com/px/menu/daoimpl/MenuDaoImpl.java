package com.px.menu.daoimpl;

import com.px.menu.dao.MenuDao;
import com.px.menu.entity.Menu;
import com.px.menu.entity.MenuType;
import com.px.share.daoimpl.GenericTreeDaoImpl;
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
public class MenuDaoImpl extends GenericTreeDaoImpl<Menu, Integer> implements MenuDao {

    public MenuDaoImpl() {
        super(Menu.class);
    }

    @Override
    public List<Menu> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Menu> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<Menu> listByMenuType(MenuType menuType, String sort, String dir) {
        List<Menu> result = new ArrayList();
        if (menuType != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("menuType", menuType));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<Menu> listByMenuType(List<MenuType> menuType, String sort, String dir) {
        List<Menu> result = new ArrayList();
        if (menuType != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("menuType", menuType));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public Menu getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public Menu getByMenuCode(String menuCode) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("menuCode", menuCode));
        DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
