package com.px.admin.daoimpl;

import com.px.admin.dao.VStructureDao;
import com.px.admin.entity.VStructure;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author PRAXiS
 */
public class VStructureDaoImpl extends GenericTreeDaoImpl<VStructure, Integer> implements VStructureDao{

    public VStructureDaoImpl() {
        super(VStructure.class);
    }
    
    @Override
    public List<VStructure> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<VStructure> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }
    
    public List<VStructure> listAllNotRemove(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    @Override
    public VStructure getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public VStructure getByCode(String code) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("structureCode", code));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(VStructure.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
}
