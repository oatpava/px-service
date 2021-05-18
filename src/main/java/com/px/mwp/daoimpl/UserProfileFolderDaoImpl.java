package com.px.mwp.daoimpl;

import com.px.mwp.dao.UserProfileFolderDao;
import com.px.mwp.entity.UserProfileFolder;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class UserProfileFolderDaoImpl extends GenericDaoImpl<UserProfileFolder, Integer> implements UserProfileFolderDao {

    public UserProfileFolderDaoImpl() {
        super(UserProfileFolder.class);
    }

    public UserProfileFolder getByUserProfileId(int userProfileId, String type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileId", userProfileId));
        conjunction.add(Restrictions.eq("userProfileFolderType", type));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfileFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<UserProfileFolder> listByUserProfileId(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileId", userProfileId));
        //conjunction.add(Restrictions.eq("usp.id", userProfileId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfileFolder.class);
        //criteria.createCriteria("userProfile", "usp", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, "userProfileFolderType", "asc");
        return this.listByCriteria(criteria);
    }

    @Override
    public List<UserProfileFolder> listByUserProfileId(int userProfileId, String type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileId", userProfileId));
        //conjunction.add(Restrictions.eq("usp.id", userProfileId));
        conjunction.add(Restrictions.eq("userProfileFolderType", type));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfileFolder.class);
        //criteria.createCriteria("userProfile", "usp", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public UserProfileFolder getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfileFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.asc("this.orderNo"));
                        break;
                    case "userProfileFolderType":
                        criteria.addOrder(Order.asc("this.userProfileFolderType"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.desc("this.orderNo"));
                        break;
                    case "userProfileFolderType":
                        criteria.addOrder(Order.desc("this.userProfileFolderType"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

}
