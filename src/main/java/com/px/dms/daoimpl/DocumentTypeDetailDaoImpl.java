/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.dms.dao.DocumentTypeDetailDao;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import java.time.LocalDateTime;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author TOP
 */
public class DocumentTypeDetailDaoImpl extends GenericDaoImpl<DocumentTypeDetail, Integer> implements DocumentTypeDetailDao {

    public DocumentTypeDetailDaoImpl() {
        super(DocumentTypeDetail.class);
    }

    @Override
    public DocumentTypeDetail findByDocumentTypeDetailId(int documentTypeDetailId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", documentTypeDetailId));
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentTypeDetail.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<DocumentTypeDetail> findListByDocumentTypeId(int documentTypeId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("documentTypeId", documentTypeId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentTypeDetail.class);
        criteria.add(conjunction);
        criteria.addOrder(Order.asc("this.orderNo"));
        return this.listByCriteria(criteria);
    }

    @Override
    public boolean createListDocumentTypeDetail(List<DocumentTypeDetail> listDocumentTypeDetail, int userId) {
        for (DocumentTypeDetail documentTypeDetailCreate : listDocumentTypeDetail) {
            //query
            documentTypeDetailCreate.setCreatedBy(userId);

            this.create(documentTypeDetailCreate);
        }
        return true;
    }

    public boolean deleteByDocumentTypeId(int documentTypeId, int userIdDel) {
        //validate

        //set
        List<DocumentTypeDetail> listDocumentTypeDetail = findListByDocumentTypeId(documentTypeId);
        for (DocumentTypeDetail documentTypeDetailDelete : listDocumentTypeDetail) {
            //query
            documentTypeDetailDelete.setRemovedBy(userIdDel);
            documentTypeDetailDelete.setRemovedDate(LocalDateTime.now());

            this.update(documentTypeDetailDelete);
        }
        return true;
    }

    public Integer countAll() {
        return this.listAll("", "").size();
    }

    public List<DocumentTypeDetail> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentTypeDetail.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DocumentTypeDetail> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentTypeDetail.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria);
    }

    @Override
    public DocumentTypeDetail getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentTypeDetail.class);
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
