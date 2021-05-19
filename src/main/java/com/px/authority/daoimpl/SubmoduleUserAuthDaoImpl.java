package com.px.authority.daoimpl;

import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.authority.dao.SubmoduleUserAuthDao;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

public class SubmoduleUserAuthDaoImpl extends GenericDaoImpl<SubmoduleUserAuth, Integer> implements SubmoduleUserAuthDao {

    public SubmoduleUserAuthDaoImpl() {
        super(SubmoduleUserAuth.class);
    }

    @Override
    public List<SubmoduleUserAuth> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<SubmoduleUserAuth> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthStructure(SubmoduleAuth submoduleAuth, Structure structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthStructure(SubmoduleAuth submoduleAuth, List<Structure> structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthStructure(List<SubmoduleAuth> submoduleAuth, Structure structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthStructure(List<SubmoduleAuth> submoduleAuth, List<Structure> structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(SubmoduleAuth submoduleAuth, UserProfile userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(SubmoduleAuth submoduleAuth, List<UserProfile> userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(List<SubmoduleAuth> submoduleAuth, UserProfile userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthUserProfile(List<SubmoduleAuth> submoduleAuth, List<UserProfile> userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(SubmoduleAuth submoduleAuth, Integer linkId, Structure structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(SubmoduleAuth submoduleAuth, Integer linkId, List<Structure> structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth, Integer linkId, Structure structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.eq("structure", structure));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth, Integer linkId, List<Structure> structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdStructure(List<SubmoduleAuth> submoduleAuth, List<Integer> linkId, List<Structure> structure, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (structure != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("linkId", linkId));
            conjunction.add(Restrictions.in("structure", structure));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(SubmoduleAuth submoduleAuth, Integer linkId, UserProfile userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(SubmoduleAuth submoduleAuth, Integer linkId, List<UserProfile> userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.eq("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth, Integer linkId, UserProfile userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth, List<Integer> linkId, UserProfile userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("linkId", linkId));
            conjunction.add(Restrictions.eq("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth, Integer linkId, List<UserProfile> userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("linkId", linkId));
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public List<SubmoduleUserAuth> listBySubmoduleAuthLinkIdUserProfile(List<SubmoduleAuth> submoduleAuth, List<Integer> linkId, List<UserProfile> userProfile, String sort, String dir) {
        List<SubmoduleUserAuth> result = new ArrayList();
        if (userProfile != null && submoduleAuth != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.in("linkId", linkId));
            conjunction.add(Restrictions.in("userProfile", userProfile));
            conjunction.add(Restrictions.in("submoduleAuth", submoduleAuth));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    public List<SubmoduleUserAuth> listByDmsFolder(Integer dmsFolderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", dmsFolderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.createCriteria("submoduleAuth", "sa", JoinType.INNER_JOIN);
        criteria.createCriteria("submoduleAuth.auth", "au", JoinType.INNER_JOIN);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("au.id"));
//        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    public List<SubmoduleUserAuth> listByDmsFolders(Integer dmsFolderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", dmsFolderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
//        conjunction.add(Restrictions.eq("submoduleAuth.auth.id", authId));        
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.createCriteria("submoduleAuth", "sa", JoinType.INNER_JOIN);
        criteria.createCriteria("submoduleAuth.auth", "au", JoinType.INNER_JOIN);
        criteria.createCriteria("structure", "st", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("userProfile", "us", JoinType.LEFT_OUTER_JOIN);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("au.id"));
//        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    public List<SubmoduleUserAuth> listUserByDmsFoldersAuth(Integer dmsFolderId, int auth) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", dmsFolderId));
        conjunction.add(Restrictions.eq("sa.auth.id", auth));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.createCriteria("submoduleAuth", "sa", JoinType.INNER_JOIN);
//        criteria.createCriteria("submoduleAuth.auth", "au", JoinType.INNER_JOIN);
//        criteria.createCriteria("structure", "st", JoinType.LEFT_OUTER_JOIN);
//        criteria.createCriteria("userProfile", "us", JoinType.LEFT_OUTER_JOIN);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("linkId"));
//        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public SubmoduleUserAuth getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<SubmoduleUserAuth> listTemplateValueByLinkId(int linkId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.isNull("structure"));
        conjunction.add(Restrictions.isNull("userProfile"));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleUserAuth.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

}
