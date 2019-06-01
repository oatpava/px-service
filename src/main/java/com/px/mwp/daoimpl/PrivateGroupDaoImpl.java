/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.daoimpl;

import com.px.mwp.dao.PrivateGroupDao;
import com.px.mwp.entity.PrivateGroup;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Dell
 */
public class PrivateGroupDaoImpl extends GenericDaoImpl<PrivateGroup, Integer> implements PrivateGroupDao {

    public PrivateGroupDaoImpl() {
        super(PrivateGroup.class);
    }

    @Override
    public PrivateGroup getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(PrivateGroup.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<PrivateGroup> listByOwnerIdAndType(int ownerId, int type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("ownerId", ownerId));
        conjunction.add(Restrictions.eq("type", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(PrivateGroup.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

}
