package com.px.admin.daoimpl;

import com.px.admin.dao.UserDao;
import com.px.admin.entity.User;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.BCrypt;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author OPAS
 */
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<User> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<User> listByUserStatusId(int userStatusId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("us.id", userStatusId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.createCriteria("userStatus", "us", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<User> listAllByUserStatusId(int userStatusId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("us.id", userStatusId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.createCriteria("userStatus", "us", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Integer countAllByUserStatusId(int userStatusId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("us.id", userStatusId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.createCriteria("userStatus", "us", JoinType.INNER_JOIN);
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

    public User getUserByUserName(String userName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userName", userName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public boolean checkLogin(String userName, String userPassword, String password) {
        return BCrypt.checkpw(userName.toUpperCase() + userPassword, password);
    }

    @Override
    public User getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
