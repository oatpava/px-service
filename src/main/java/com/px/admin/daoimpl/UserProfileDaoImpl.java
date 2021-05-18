package com.px.admin.daoimpl;

import com.px.admin.dao.UserProfileDao;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author OPAS
 */
public class UserProfileDaoImpl extends GenericDaoImpl<UserProfile, Integer> implements UserProfileDao {

    private final String fieldSearch;

    public UserProfileDaoImpl() {
        super(UserProfile.class);
        fieldSearch = ",userProfileFirstName,";
    }

    @Override
    public List<UserProfile> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileDefaultSelect", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<UserProfile> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Integer countDup(String code) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileCode", code));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<UserProfile> listByUserProfileStatusId(int userStatusId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("us.id", userStatusId));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("userStatus", "us", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<UserProfile> listByStructure(Structure structure, String sort, String dir) {
        List<UserProfile> result = new ArrayList();
        if (structure != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("s.id", structure.getId()));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
            criteria.createCriteria("structure", "s", JoinType.INNER_JOIN);
            criteria.add(conjunction);
            criteria = createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    public List<UserProfile> listAllByUserProfileStatusId(int userStatusId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("us.id", userStatusId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("userStatus", "us", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<UserProfile> listByStructureParentKey(String parentKey, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("struc.parentKey", parentKey, MatchMode.START));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("structure", "struc", JoinType.INNER_JOIN);
//        criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Integer countAllByUserProfileStatusId(int userStatusId) {
        return this.listAllByUserProfileStatusId(userStatusId, "", "").size();
    }

    public List<UserProfile> listByUserId(int userId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("u.id", userId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    public Integer countAllByUserId(int userId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("u.id", userId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

//    public UserProfile getByUserId(int userId) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("this.removedBy", 0));
////        conjunction.add(Restrictions.eq("this.userProfileDefaultSelect", 0));
//        conjunction.add(Restrictions.eq("u.id", userId));
//        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
//        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
//        criteria.add(conjunction);
//        return this.getOneByCriteria(criteria);
//    }
    public UserProfile getByUsername(String username) {
        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("this.userProfileDefaultSelect", 0));
        conjunction.add(Restrictions.eq("u.userName", username));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public UserProfile checkEmail(String username, String email) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.userProfileEmail", email));
        conjunction.add(Restrictions.eq("u.userName", username));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public UserProfile getDefaultProfile(int userProfileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.id", userProfileId));
        conjunction.add(Restrictions.eq("this.userProfileDefaultSelect", 0));
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
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
                    case "structure":
                        criteria.addOrder(Order.asc("this.structure.id"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.asc("this.orderNo"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "structure":
                        criteria.addOrder(Order.asc("this.structure.id"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.asc("this.orderNo"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    @Override
    public UserProfile getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<UserProfile> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public Integer countSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria).size();
    }

    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = Restrictions.conjunction();
        for (String key : queryParams.keySet()) {
//            if(fieldSearch.contains(","+key+",")){
            if (fieldSearch.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "userProfileFirstName":
                                conjunction.add(Restrictions.like("this.userProfileFullName", valueArray[i], MatchMode.ANYWHERE));
                                break;
//                            case "userProfileFullName":
//                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
//                                    conjunction.add(Restrictions.like("this.userProfileFullName", valueArray[i], MatchMode.ANYWHERE));
//                                }
//                                break;
                            case "userProfile":
                                if (valueArray[i] != null && !"".equalsIgnoreCase(valueArray[i])) {
                                    conjunction.add(Restrictions.like("u.userName", valueArray[i], MatchMode.ANYWHERE));
                                }
                                break;
                        }
                    }
                }
            }
        }
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        return conjunction;
    }

    public List<UserProfile> getUserIdFromName(String userProfileFullName) {
//        UserProfileService userProfileService = new UserProfileService();
//        UserProfile userProfile = null;
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("userProfileFullName", userProfileFullName, MatchMode.ANYWHERE));
//        conjunction.add(Restrictions.eq("userProfileFullName", userProfileFullName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    public UserProfile getPrevOrderBy(int id) {
        Conjunction conjunction = Restrictions.conjunction();
//        ProjectionList projection = Projections.projectionList();
        conjunction.add(Restrictions.eq("id", id - 1));
//        conjunction.add(Restrictions.eq("orderNO", orderNO - 1));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
//        projection.add(Projections.property("id").as("id"));
//        projection.add(Projections.sqlProjection("lag(order_no) over (order by user_profile_id) as prev_word", new String[]{"orderNo"}, new Type[]{StandardBasicTypes.FLOAT}));
//        criteria.setProjection(projection).setResultTransformer(Transformers.aliasToBean(UserProfile.class));
        criteria.add(conjunction);
//        criteria.setProjection(projection);
        return this.getOneByCriteria(criteria);
    }

    //oat-add
    public List<UserProfile> listByName(String userProfileFullName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileFullName", userProfileFullName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
        criteria.add(conjunction).addOrder(Order.desc("this.createdDate"));
        return this.listByCriteria(criteria);
    }

}
