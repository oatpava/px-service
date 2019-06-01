package com.px.authority.daoimpl; 

import com.px.authority.dao.SubmoduleAuthTemplateValDao;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleAuthTemplateVal;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SubmoduleAuthTemplateValDaoImpl extends GenericDaoImpl<SubmoduleAuthTemplateVal, Integer> implements SubmoduleAuthTemplateValDao{

    public SubmoduleAuthTemplateValDaoImpl() {
        super(SubmoduleAuthTemplateVal.class);
    }

    @Override
    public List<SubmoduleAuthTemplateVal> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplateVal.class);
        criteria.add(conjunction);        
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<SubmoduleAuthTemplateVal> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplateVal.class);
        criteria.add(conjunction);   
        criteria = Common.createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public List<SubmoduleAuthTemplateVal> listBySubmoduleAuthTemplate(SubmoduleAuthTemplate submoduleAuthTemplate,String sort, String dir) {
        List<SubmoduleAuthTemplateVal> result = new ArrayList();
        if(submoduleAuthTemplate!=null){
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.eq("submoduleAuthTemplate", submoduleAuthTemplate));
            DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplateVal.class);
            criteria.add(conjunction);   
            criteria = Common.createOrder(criteria,sort,dir);
            result = this.listByCriteria(criteria);
        }
        
        return result;
    }
    
    @Override
    public Integer countAll() {
        return this.listAll("","").size();
    }
    
    @Override
    public SubmoduleAuthTemplateVal getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(SubmoduleAuthTemplateVal.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
