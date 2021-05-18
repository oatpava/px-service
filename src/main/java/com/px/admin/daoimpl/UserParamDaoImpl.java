package com.px.admin.daoimpl;

import com.px.admin.dao.UserParamDao;
import com.px.admin.entity.UserParam;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Pritsana
 */
public class UserParamDaoImpl extends GenericDaoImpl<UserParam, Integer> implements UserParamDao {

    public UserParamDaoImpl() {
        super(UserParam.class);
    }

    @Override
    public UserParam getByParamName(String paramName, int id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("user.id", id));
        conjunction.add(Restrictions.eq("paramName", paramName));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.add(conjunction);

        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<UserParam> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<UserParam> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<UserParam> listByUserId(int userId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("u.id", userId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    @Override
    public UserParam getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserParam.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
