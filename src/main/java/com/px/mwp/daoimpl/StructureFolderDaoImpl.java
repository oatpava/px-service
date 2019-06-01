package com.px.mwp.daoimpl;

import com.px.mwp.dao.StructureFolderDao;
import com.px.mwp.entity.StructureFolder;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Mali
 */
public class StructureFolderDaoImpl extends GenericDaoImpl<StructureFolder, Integer> implements StructureFolderDao {

    public StructureFolderDaoImpl() {
        super(StructureFolder.class);
    }

    @Override
    public List<StructureFolder> listByStructureId(int structureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureId", structureId));
        DetachedCriteria criteria = DetachedCriteria.forClass(StructureFolder.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, "structureFolderType", "asc");
        return this.listByCriteria(criteria);
    }

    @Override
    public List<StructureFolder> listByStructureId(int structureId, String type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureId", structureId));
        conjunction.add(Restrictions.eq("structureFolderType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(StructureFolder.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public StructureFolder getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(StructureFolder.class);
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
                    case "structureFolderType":
                        criteria.addOrder(Order.asc("this.structureFolderType"));
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
                    case "structureFolderType":
                        criteria.addOrder(Order.desc("this.structureFolderType"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    public StructureFolder getByStructureId(int structureId, String type) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureId", structureId));
        conjunction.add(Restrictions.eq("structureFolderType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(StructureFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

}
