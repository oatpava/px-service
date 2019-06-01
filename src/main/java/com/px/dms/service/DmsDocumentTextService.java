///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.service;
//
//import static com.google.common.base.Preconditions.checkNotNull;
//import com.px.dms.daoimpl.DmsDocumentTextDaoImpl;
//import com.px.dms.entity.DmsDocumentText;
//import com.px.dms.model.DmsDocumentTextModel;
//import com.px.share.service.GenericService;
//import java.util.List;
//import java.time.LocalDateTime;
//import javax.ws.rs.core.MultivaluedMap;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author TOP
// */
//public class DmsDocumentTextService implements GenericService<DmsDocumentText, DmsDocumentTextModel> {
//
//    private static final Logger log = Logger.getLogger(DmsDocumentTextService.class.getName());
//    private final DmsDocumentTextDaoImpl dmsDocumentTextDaoImpl;
//
//    public DmsDocumentTextService() {
//        this.dmsDocumentTextDaoImpl = new DmsDocumentTextDaoImpl();
//    }
//
//    @Override
//    public DmsDocumentText create(DmsDocumentText t) {
//        checkNotNull(t, "DmsDocumentText entity must not be null");
//        checkNotNull(t.getDmsDocumentFileId(), "DmsDocumentFileId must not be null");
//        checkNotNull(t.getDmsDocumentId(), "DmsDocumentId must not be null");
//        checkNotNull(t.getCreatedBy(), "create by must not be null");
//        return dmsDocumentTextDaoImpl.create(t);
//    }
//
//    @Override
//    public DmsDocumentText getById(int id) {
//        return dmsDocumentTextDaoImpl.getById(id);
//    }
//
//    @Override
//    public DmsDocumentText update(DmsDocumentText t) {
//        checkNotNull(t, "DmsDocumentText entity must not be null");
//        checkNotNull(t.getId(), "DmsDocumentText id must not be null");
//        
//
//        checkNotNull(t.getUpdatedBy(), "update by must not be null");
//        t.setUpdatedDate(LocalDateTime.now());
//        return dmsDocumentTextDaoImpl.update(t);
//    }
//
//    @Override
//    public DmsDocumentText remove(int id, int userId) {
//        checkNotNull(id, "DmsDocumentText id must not be null");
//        DmsDocumentText dmsDocumentText = getById(id);
//        checkNotNull(dmsDocumentText, "dmsDocumentText entity not found in database.");
//        dmsDocumentText.setRemovedBy(userId);
//        dmsDocumentText.setRemovedDate(LocalDateTime.now());
//        return dmsDocumentTextDaoImpl.update(dmsDocumentText);
//    }
//
//    @Override
//    public List<DmsDocumentText> list(int limit, int offset, String sort, String dir) {
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit must not be null");
//        checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return dmsDocumentTextDaoImpl.list(offset,limit,sort, dir);
//    }
//
//    @Override
//    public List<DmsDocumentText> listAll(String sort, String dir) {
//         checkNotNull(sort, "sort must not be null");
//        checkNotNull(dir, "dir must not be null");
//        return dmsDocumentTextDaoImpl.listAll(sort, dir);
//    }
//
//    @Override
//    public int countAll() {
//        return dmsDocumentTextDaoImpl.countAll();
//    }
//
//    @Override
//    public List<DmsDocumentText> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int countSearch(MultivaluedMap<String, String> queryParams) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public DmsDocumentTextModel tranformToModel(DmsDocumentText t) {
//        DmsDocumentTextModel dmsDocumentTextModel = null;
//        if (t != null) {
//            dmsDocumentTextModel = new DmsDocumentTextModel();
//            dmsDocumentTextModel.setDocumentTextData(t.getDmsDocumentTextData());
//            dmsDocumentTextModel.setDmsDocumentFileId(t.getDmsDocumentFileId());
//            dmsDocumentTextModel.setDmsDocumentId(t.getDmsDocumentId());
//            dmsDocumentTextModel.setDocumentTextData(t.getDmsDocumentTextData());
//        }
//        return dmsDocumentTextModel;
//    }
//
//    public List<DmsDocumentText> findListDocumentText(int documentId, int documentFileId) {
//        checkNotNull(documentId, "documentId id must not be null");
//        checkNotNull(documentFileId, "documentFileId id must not be null");
//        return dmsDocumentTextDaoImpl.findListDocumentText(documentId, documentFileId);
//    }
//    
//    @Override
//    public DmsDocumentText getByIdNotRemoved(int id) {
//        checkNotNull(id, "DmsDocumentText id entity must not be null");
//        return dmsDocumentTextDaoImpl.getByIdNotRemoved(id);
//    }
//    
//     public List<DmsDocumentText> searchDocument(String searchText) {
//     checkNotNull(searchText, "searchText  must not be null");
//      return dmsDocumentTextDaoImpl.searchDocument(searchText);
//     }
//     
//     public List<DmsDocumentText> searchDocumentFullText(String searchText) {
//     checkNotNull(searchText, "searchText  must not be null");
//      return dmsDocumentTextDaoImpl.searchDocumentFullText(searchText);
//     }
//}
