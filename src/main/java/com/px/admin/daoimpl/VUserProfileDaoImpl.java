package com.px.admin.daoimpl;

import com.px.admin.dao.VUserProfileDao;
import com.px.admin.entity.VUserProfile;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author PRAXiS
 */
public class VUserProfileDaoImpl extends GenericTreeDaoImpl<VUserProfile, Integer> implements VUserProfileDao{

    public VUserProfileDaoImpl() {
        super(VUserProfile.class);
    }
    
    @Override
    public List<VUserProfile> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<VUserProfile> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    public List<VUserProfile> listAllNotRemove(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    public List<VUserProfile> listByName(String name, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("userProfileFullName", name, MatchMode.ANYWHERE));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    @Override
    public VUserProfile getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public VUserProfile getByCode(String code) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("userProfileCode", code));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VUserProfile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
}
