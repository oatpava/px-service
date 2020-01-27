/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericTreeDaoImpl;
import com.px.wf.dao.WfRecordDao;
import com.px.wf.entity.WfRecord;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author oat
 */
public class WfRecordDaoImpl extends GenericTreeDaoImpl<WfRecord, Integer> implements WfRecordDao {


    public WfRecordDaoImpl() {
        super(WfRecord.class);
    }

    @Override
    public List<WfRecord> listByContentId(int contentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("contentId", contentId));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfRecord.class);
        criteria.add(conjunction).addOrder(Order.desc("orderNo"));
        return this.listByCriteria(criteria, 0, 50);
    }
    
    @Override
    public List<WfRecord> listByDocumentId(int documentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("documentId", documentId));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfRecord.class);
        criteria.add(conjunction).addOrder(Order.desc("orderNo"));
        return this.listByCriteria(criteria, 0, 50);
    }
}
