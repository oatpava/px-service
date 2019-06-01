/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.dms.dao.DocumentTypeDao;
import com.px.dms.entity.DocumentType;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author TOP
 */
public class DocumentTypeDaoImpl extends GenericDaoImpl<DocumentType, Integer> implements DocumentTypeDao {

    public DocumentTypeDaoImpl() {
        super(DocumentType.class);
    }

    @Override
    public List<DocumentType> listAllDocumentType() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
        criteria.add(conjunction);

        //query
//        DocumentTypeService documentTypeHelper = new DocumentTypeService();
        return this.listByCriteria(criteria);
    }

    @Override
    public boolean updateNameDocumentType(DocumentType documentType) {

        DocumentType documentTypeDb = findByDocumentTypeId(documentType.getId());

        documentTypeDb.setUpdatedBy(documentType.getUpdatedBy());
        documentTypeDb.setUpdatedDate(LocalDateTime.now());
        documentTypeDb.setDocumentTypeName(documentType.getDocumentTypeName());

        this.update(documentTypeDb);
        return true;
    }

    @Override
    public DocumentType findByDocumentTypeId(int documentTypeId) {

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", documentTypeId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public Integer countAll() {
        return this.listAll("", "").size();
    }

    public List<DocumentType> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DocumentType> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria);
    }

    @Override
    public DocumentType getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
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
    
    
     public int duplicateDoctype( String nameDocType) {

        try {

            Conjunction conjunction = Restrictions.conjunction();

//            Disjunction disjunction = Restrictions.disjunction();

            
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.eq("documentTypeName", nameDocType));

//            conjunction.add(disjunction);
            DetachedCriteria criteria = DetachedCriteria.forClass(DocumentType.class);
            criteria.add(conjunction);

            DocumentType documentType = this.getOneByCriteria(criteria);

            if (documentType.getId() > 0) {

                return documentType.getId();
            } else {

                return 0;
            }

        } catch (Exception ex) {
            return 0;
        }
    }
}
