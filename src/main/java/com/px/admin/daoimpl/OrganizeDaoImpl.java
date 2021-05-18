package com.px.admin.daoimpl;

import com.px.admin.dao.OrganizeDao;
import com.px.admin.entity.Organize;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
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
        if (code != null && code != "") {
            conjunction.add(Restrictions.eq("organizeCode", code));
        }
        if (name != null && name != "") {
            conjunction.add(Restrictions.eq("organizeName", name));
        }
        conjunction.add(Restrictions.eq("removedBy", 0));
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
        conjunction.add(Restrictions.eq("organizeName", organizeName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction).addOrder(Order.desc("this.createdDate"));
        return this.listByCriteria(criteria);
    }

    //oat-add
    public Organize getLatest(int parentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("parentId", parentId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction).addOrder(Order.desc("this.id"));
        List<Organize> tmp = this.listByCriteria(criteria, 0, 1);
        return tmp.get(0);
    }

    public Organize getPrevOrderBy(int id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id - 1));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<Organize> listAllByName(String sort, String dir, String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("organizeName", name, MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Organize.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

}
