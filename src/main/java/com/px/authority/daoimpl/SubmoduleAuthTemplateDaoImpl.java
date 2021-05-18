package com.px.authority.daoimpl;

import com.px.admin.entity.Submodule;
import com.px.authority.dao.SubmoduleAuthTemplateDao;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SubmoduleAuthTemplateDaoImpl extends GenericDaoImpl<SubmoduleAuthTemplate, Integer> implements SubmoduleAuthTemplateDao {

    public SubmoduleAuthTemplateDaoImpl() {
        super(SubmoduleAuthTemplate.class);
    }

    @Override
    public List<SubmoduleAuthTemplate> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplate.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<SubmoduleAuthTemplate> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplate.class);
        criteria.add(conjunction);
        criteria = Common.createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<SubmoduleAuthTemplate> listBySubmodule(Submodule submodule, String sort, String dir) {
        List<SubmoduleAuthTemplate> result = new ArrayList();
        if (submodule != null) {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("submodule", submodule));
            conjunction.add(Restrictions.eq("removedBy", 0));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplate.class);
            criteria.add(conjunction);
            criteria = Common.createOrder(criteria, sort, dir);
            result = this.listByCriteria(criteria);
        }

        return result;
    }

    @Override
    public Integer countAll() {
        return this.listAll("", "").size();
    }

    @Override
    public SubmoduleAuthTemplate getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplate.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
}
