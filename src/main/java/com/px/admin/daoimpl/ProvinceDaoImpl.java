package com.px.admin.daoimpl;

import com.px.admin.dao.ProvinceDao;
import com.px.admin.entity.Province;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Oat
 */
public class ProvinceDaoImpl extends GenericDaoImpl<Province, Integer> implements ProvinceDao {

    public ProvinceDaoImpl() {
        super(Province.class);
    }

    @Override
    public List<Province> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Province> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Province getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public Province getByName(String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("name", name));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public Province getByCode(String code) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("code", code));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Province.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public static DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(sort));
            }
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(sort));
            }
        } else {
            criteria.addOrder(Order.desc("createdDate"));
        }
        return criteria;
    }
}
