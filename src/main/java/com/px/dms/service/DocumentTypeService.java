/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.dms.daoimpl.DocumentTypeDaoImpl;
import com.px.dms.entity.DocumentType;
import com.px.dms.model.DocumentTypeModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DocumentTypeService implements GenericService<DocumentType, DocumentTypeModel> {

    private static final Logger log = Logger.getLogger(DocumentTypeService.class.getName());

    private final DocumentTypeDaoImpl documentTypeDaoImpl;

    public DocumentTypeService() {
        this.documentTypeDaoImpl = new DocumentTypeDaoImpl();
    }

    @Override
    public DocumentType create(DocumentType t) {
        return documentTypeDaoImpl.create(t);

    }

    @Override
    public DocumentType getById(int id) {
        return documentTypeDaoImpl.getById(id);
    }

    @Override
    public DocumentType update(DocumentType t) {
        checkNotNull(t, "DocumentType entity must not be null");
        checkNotNull(t.getId(), "DocumentType id must not be null");
        return documentTypeDaoImpl.update(t);
    }

    @Override
    public DocumentType remove(int id, int userId) {
        checkNotNull(id, "DocumentType id must not be null");
        DocumentType documentType = getById(id);
        checkNotNull(documentType, "report entity not found in database.");
        documentType.setRemovedBy(userId);
        documentType.setRemovedDate(LocalDateTime.now());
        return documentTypeDaoImpl.update(documentType);
    }

    @Override
    public List<DocumentType> list(int limit, int offset, String sort, String dir) {
         checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return documentTypeDaoImpl.list(offset,limit,sort, dir);
    }

    @Override
    public List<DocumentType> listAll(String sort, String dir) {
         checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return documentTypeDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
         return documentTypeDaoImpl.countAll();

    }

    @Override
    public List<DocumentType> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DocumentTypeModel tranformToModel(DocumentType t) {
        DocumentTypeModel documentTypeModel = null;

        if (t != null) {
           

            documentTypeModel = new DocumentTypeModel();
           
           documentTypeModel.setId(t.getId());
          
           documentTypeModel.setDocumentTypeName(t.getDocumentTypeName());

           documentTypeModel.setDocumentTypeDescription(t.getDocumentTypeDescription());
        }
        return documentTypeModel;
    }

    public boolean updateNameDocumentType(DocumentType t) {
        checkNotNull(t, "DocumentType entity must not be null");
        checkNotNull(t.getId(), "DocumentType id must not be null");
        checkNotNull(t.getDocumentTypeName(), "DocumentTypeNAme must not be null");
        return documentTypeDaoImpl.updateNameDocumentType(t);
    }
    
    @Override
    public DocumentType getByIdNotRemoved(int id) {
        checkNotNull(id, "DocumentType id entity must not be null");
        return documentTypeDaoImpl.getByIdNotRemoved(id);
    }
    
     public int duplicateDoctype(String typeName) {
        checkNotNull(typeName, "DocumentType typeName entity must not be null");
        return documentTypeDaoImpl.duplicateDoctype(typeName);
    }
    
    
}
