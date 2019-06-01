/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.mwp.daoimpl;

import com.px.mwp.dao.PrivateGroupUserDao;
import com.px.mwp.entity.PrivateGroupUser;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Oat
 */
public class PrivateGroupUserDaoImpl extends GenericDaoImpl<PrivateGroupUser, Integer> implements PrivateGroupUserDao {

    public PrivateGroupUserDaoImpl() {
        super(PrivateGroupUser.class);
    }

    @Override
    public PrivateGroupUser getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(PrivateGroupUser.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);    }

    @Override
    public List<PrivateGroupUser> listByPrivateGroupId(int privateGroupId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("privateGroupId", privateGroupId));
        DetachedCriteria criteria = DetachedCriteria.forClass(PrivateGroupUser.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);    }

}
