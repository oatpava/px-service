package com.px.wf.daoimpl;

import com.px.wf.dao.FolderPropertyDao;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.wf.entity.FolderProperty;
import com.px.share.util.Common;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class FolderPropertyDaoImpl extends GenericDaoImpl<FolderProperty, Integer> implements FolderPropertyDao {

    public FolderPropertyDaoImpl() {
        super(FolderProperty.class);
    }

    @Override
    public List<FolderProperty> listByType(int type,int folderId,String folderPropertyType) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("folderPropertyFolderId", folderId));
        conjunction.add(Restrictions.eq("folderPropertyType", folderPropertyType));
        if (type == 1) {
            conjunction.add(Restrictions.eq("folderPropertyPList", "Y"));
        } else if(type == 2){
            conjunction.add(Restrictions.eq("folderPropertyPSearch", "Y"));
        }

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(FolderProperty.class);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("orderNo"));

        //query
        return this.listByCriteria(criteria);
    }
    
    @Override
    public List<FolderProperty> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("folderPropertyFolderId", 0));
        //conjunction.add(Restrictions.eq("folderPropertyType", "T"));
        DetachedCriteria criteria = DetachedCriteria.forClass(FolderProperty.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }
    
    @Override
    public FolderProperty getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(FolderProperty.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public boolean checkFolderIdNoData(int folderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("folderPropertyFolderId", folderId));
        conjunction.add(Restrictions.eq("folderPropertyType", "T"));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(FolderProperty.class);
        criteria.add(conjunction);

        //query
        return this.listByCriteria(criteria).size() <= 0;
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
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.desc("this.orderNo"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

}
