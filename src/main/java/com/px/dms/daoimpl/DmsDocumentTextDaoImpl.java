///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.daoimpl;
//
//import com.px.dms.dao.DmsDocumentTextDao;
//import com.px.dms.entity.DmsDocumentText;
//import com.px.share.daoimpl.GenericDaoImpl;
//import java.util.List;
//import org.apache.log4j.Logger;
//import org.hibernate.criterion.Conjunction;
//import org.hibernate.criterion.DetachedCriteria;
//import org.hibernate.criterion.MatchMode;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//
///**
// *
// * @author TOP
// */
//public class DmsDocumentTextDaoImpl extends GenericDaoImpl<DmsDocumentText, Integer> implements DmsDocumentTextDao {
//
//    private static final Logger LOG = Logger.getLogger(DmsDocumentTextDaoImpl.class.getName());
//
//    public DmsDocumentTextDaoImpl() {
//        super(DmsDocumentText.class);
//    }
//
//    @Override
//    public List<DmsDocumentText> findListDocumentText(int documentId, int documentFileId) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("dmsDocumentFileId", documentFileId));
//        conjunction.add(Restrictions.eq("dmsDocumentId", documentId));
//        //create Query
//        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//        criteria.add(conjunction);
//        //query
////        DmsDocumentTextService dmsDocumentTextHelper = new DmsDocumentTextService();
////        dmsDocumentTextHelper.setClass(DmsDocumentText.class);
////        List<DmsDocumentText> dmsDocumentText = dmsDocumentTextHelper.findByCriteria(criteria);
//
//        return this.listByCriteria(criteria);
//    }
//
//    public Integer countAll() {
//        return this.listAll("", "").size();
//    }
//
//    public List<DmsDocumentText> list(int offset, int limit, String sort, String dir) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("removedBy", 0));
//
//        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//        criteria.add(conjunction);
//        criteria = createOrder(criteria, sort, dir);
//
//        return this.listByCriteria(criteria, offset, limit);
//    }
//
//    public List<DmsDocumentText> listAll(String sort, String dir) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("removedBy", 0));
//
//        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//        criteria.add(conjunction);
//        criteria = createOrder(criteria, sort, dir);
//
//        return this.listByCriteria(criteria);
//    }
//
//    @Override
//    public DmsDocumentText getByIdNotRemoved(Integer id) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("id", id));
//        conjunction.add(Restrictions.eq("removedBy", 0));
//        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//        criteria.add(conjunction);
//        return this.getOneByCriteria(criteria);
//    }
//
//    public List<DmsDocumentText> searchDocument(String searchText) {
//        try {
//            Conjunction conjunction = Restrictions.conjunction();
//            conjunction.add(Restrictions.like("this.dmsDocumentTextData", searchText, MatchMode.ANYWHERE));
//            conjunction.add(Restrictions.eq("removedBy", 0));
//
//            DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//            criteria.add(conjunction);
//
////           List<DmsDocumentText> listDoc =  listByCriteria(criteria);
////           
////            for (DmsDocumentText listDoc1 : listDoc) {
////                
////            }
//            List<DmsDocumentText> data = listByCriteria(criteria);
//
//            return data;
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            return null;
//        }
//
//    }
//
//    public List<DmsDocumentText> searchDocumentFullText(String searchText) {
//        List<DmsDocumentText> listDoc = searchDocument(searchText);
//
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("removedBy", 0));
//        for (DmsDocumentText listDoc1 : listDoc) {
//            conjunction.add(Restrictions.eq("id", listDoc1.getDmsDocumentId()));
//        };
//        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocumentText.class);
//        criteria.add(conjunction);
//        return this.listByCriteria(criteria);
//    }
//
//    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
//        if (!sort.isEmpty()) {
//            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
//                switch (sort) {
//                    case "createdDate":
//                        criteria.addOrder(Order.asc("this.createdDate"));
//                        break;
//                    case "orderNo":
//                        criteria.addOrder(Order.asc("this.orderNo"));
//                        break;
//                }
//            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
//                switch (sort) {
//                    case "createdDate":
//                        criteria.addOrder(Order.desc("this.createdDate"));
//                        break;
//                    case "orderNo":
//                        criteria.addOrder(Order.desc("this.orderNo"));
//                        break;
//                }
//            }
//        } else {
//            criteria.addOrder(Order.desc("this.createdDate"));
//        }
//        return criteria;
//    }
//
//}
