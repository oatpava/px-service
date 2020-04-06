package com.px.admin.daoimpl;

import com.px.admin.dao.OrganizeDao;
import com.px.admin.entity.Organize;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author PRAXiS
 */
public class OrganizeDaoImpl extends GenericTreeDaoImpl<Organize, Integer> implements OrganizeDao {

    public OrganizeDaoImpl() {
        super(Organize.class);
    }

    @Override
    public List<Organize> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Organize> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Integer countDup(String code, String name) {
        Conjunction conjunction = createConjunction(code, name);
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    private Conjunction createConjunction(String code, String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        if (code != null && code != "") {
            conjunction.add(Restrictions.eq("organizeCode", code));
        }
        if (name != null && name != "") {
            conjunction.add(Restrictions.eq("organizeName", name));
        }
        return conjunction;
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Organize getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    //oat-add
    public List<Organize> listByName(String organizeName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("organizeName", organizeName));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction).addOrder(Order.desc("this.createdDate"));
        return this.listByCriteria(criteria);
    }

}
