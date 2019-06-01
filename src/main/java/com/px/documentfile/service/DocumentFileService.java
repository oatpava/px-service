/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.documentfile.daoimpl.DocumentFileDaoImpl;
import com.px.documentfile.entity.DocumentFile;
import com.px.documentfile.model.DocumentFileModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DocumentFileService implements GenericService<DocumentFile, DocumentFileModel> {
    
    private static final Logger log = Logger.getLogger(DocumentFileService.class.getName());
    private final DocumentFileDaoImpl documentFileDaoImpl;
    
    public DocumentFileService() {
        this.documentFileDaoImpl = new DocumentFileDaoImpl();
    }
    
    @Override
    public DocumentFile create(DocumentFile t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public DocumentFile getById(int id) {
        return documentFileDaoImpl.getById(id);
    }
    
    @Override
    public DocumentFile update(DocumentFile t) {
        checkNotNull(t, "Document entity must not be null");
        checkNotNull(t.getId(), "Document id must not be null");
        return documentFileDaoImpl.update(t);
    }
    
    @Override
    public DocumentFile remove(int id, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<DocumentFile> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<DocumentFile> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<DocumentFile> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public DocumentFileModel tranformToModel(DocumentFile t) {
        DocumentFileModel documentFileModel = null;
        if (t != null) {
//            documentFileModel = new DocumentFileModel(t.getId(), t.getLinkType(), t.getFileOrder(), t.getDocumentFileName(), t.getDocumentFileType(), t.getCaption(), t.getOwnerName(), t.getIsText(), t.getImageCount(), t.getDocumentFileSec(), t.getFileSize(), t.getMd5(), t.getLinkId());
            documentFileModel.setId(t.getId());
            documentFileModel.setLinkType(t.getLinkType());
            documentFileModel.setFileOrder(t.getFileOrder());
            documentFileModel.setDocumentFileName(t.getDocumentFileName());
            documentFileModel.setDocumentFileType(t.getDocumentFileType());
            documentFileModel.setCaption(t.getCaption());
            documentFileModel.setOwnerName(t.getOwnerName());
            documentFileModel.setIsText(t.getIsText());
            documentFileModel.setImageCount(t.getImageCount());
            documentFileModel.setDocumentFileSec( t.getDocumentFileSec());
            documentFileModel.setFileSize(t.getFileSize());
            documentFileModel.setMd5(t.getMd5());
            documentFileModel.setLinkId(t.getLinkId());
        }
        return documentFileModel;
    }
    
    public int createDocumentFile(DocumentFile docFile) {
        checkNotNull(docFile, "DocumentFile entity must not be null");
        checkNotNull(docFile.getCreatedBy(), "create by must not be null");
        checkNotNull(docFile.getModuleId(), "ModuleId by must not be null");
        checkNotNull(docFile.getLinkId(), "LinkId by must not be null");
        checkNotNull(docFile.getLinkId2(), "LinkId2 by must not be null");
        
        return documentFileDaoImpl.createDocumentFile(docFile);
    }
    
    public boolean updateDocumentFile(DocumentFile docFile) {
        checkNotNull(docFile, "DocumentFile entity must not be null");
        checkNotNull(docFile.getUpdatedBy(), "update  must not be null");
        checkNotNull(docFile.getModuleId(), "ModuleId  must not be null");
        checkNotNull(docFile.getLinkId(), "LinkId  must not be null");
        checkNotNull(docFile.getLinkId2(), "LinkId2  must not be null");
        docFile.setUpdatedDate(LocalDateTime.now());
        return documentFileDaoImpl.updateDocumentFile(docFile);
    }
    
    public boolean removeDocumentFile(DocumentFile documentFile) {
        checkNotNull(documentFile.getModuleId(), "ModuleId  must not be null");
        checkNotNull(documentFile.getLinkId(), "LinkId  must not be null");
        checkNotNull(documentFile.getLinkId2(), "LinkId2  must not be null");
        checkNotNull(documentFile.getRemovedBy(), "remove  must not be null");
        checkNotNull(documentFile, "DocumentFile entity must not be null");
        return documentFileDaoImpl.removeDocumentFile(documentFile);
    }
    
    public List<DocumentFile> ListDocumentFile(int moduleId, int linkId, int linkId2, String order, int sort) {
        checkNotNull(moduleId, "moduleId  must not be null");
        checkNotNull(linkId, "linkId  must not be null");
        checkNotNull(linkId2, "linkId2  must not be null");
        checkNotNull(order, "order  must not be null");
        checkNotNull(sort, "sort  must not be null");
        return documentFileDaoImpl.ListDocumentFile(moduleId, linkId, linkId2, order, sort);
    }
    
    public List<DocumentFile> ListDocumentFileWithSec(int moduleId, int linkId, int linkId2, String order, int sort, String sec) {
        checkNotNull(moduleId, "moduleId  must not be null");
        checkNotNull(linkId, "linkId  must not be null");
        checkNotNull(linkId2, "linkId2  must not be null");
        checkNotNull(order, "order  must not be null");
        checkNotNull(sort, "sort  must not be null");
        checkNotNull(sec, "sort  must not be null");
        return documentFileDaoImpl.ListDocumentFileWithSec(moduleId, linkId, linkId2, order, sort, sec);
    }
    
    public boolean removeDocumentFileById(DocumentFile documentFile) {
        checkNotNull(documentFile, "documentFile entity  must not be null");
        checkNotNull(documentFile.getId(), "documentFile id  must not be null");
        return documentFileDaoImpl.removeDocumentFileById(documentFile);
    }
    
    public int updateDocumentFileById(DocumentFile documentFile) {
        checkNotNull(documentFile, "documentFile entity  must not be null");
        checkNotNull(documentFile.getId(), "documentFile id  must not be null");
        return documentFileDaoImpl.updateDocumentFileById(documentFile);
    }
    
    public DocumentFile findDocumentFileByDocumentFileId(int documentFileID) {
        checkNotNull(documentFileID, "documentFile id  must not be null");
        return documentFileDaoImpl.findDocumentFileByDocumentId(documentFileID);
    }
    
    public boolean updateDocumentFileName(DocumentFile documentFile) {
        checkNotNull(documentFile, "documentFile entity  must not be null");
        checkNotNull(documentFile.getUpdatedBy(), "UpdatedBy   must not be null");
        return documentFileDaoImpl.updateDocumentFileName(documentFile);
    }
    
    public boolean updateDocumentFileSec(DocumentFile documentFile) {
        checkNotNull(documentFile, "documentFile entity  must not be null");
        checkNotNull(documentFile.getUpdatedBy(), "UpdatedBy   must not be null");
        
        return documentFileDaoImpl.updateDocumentFileSec(documentFile);
    }
    
    public boolean checkDocumentFileinDocument(int documentid) {
        checkNotNull(documentid, "document id   must not be null");
        return documentFileDaoImpl.checkDocumentFileinDocument(documentid);
    }

    @Override
    public DocumentFile getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
