/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.dms.daoimpl.DocumentTypeDetailDaoImpl;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.dms.model.DocumentTypeDetailModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DocumentTypeDetailService implements GenericService<DocumentTypeDetail, DocumentTypeDetailModel> {

    private static final Logger LOG = Logger.getLogger(DocumentTypeDetailService.class.getName());

    private final DocumentTypeDetailDaoImpl documentTypeDetailDaoImpl;

    public DocumentTypeDetailService() {
        this.documentTypeDetailDaoImpl = new DocumentTypeDetailDaoImpl();
    }

    @Override
    public DocumentTypeDetail create(DocumentTypeDetail t) {
        checkNotNull(t, "DocumentTypeDetail entity must not be null");
        checkNotNull(t.getDocumentTypeId(), "DocumentTypeDetail id must not be null");
        checkNotNull(t.getDmsFieldId(), "DocumentTypeDetail Field Id must not be null");
        return documentTypeDetailDaoImpl.create(t);
    }

    @Override
    public DocumentTypeDetail getById(int id) {
        return documentTypeDetailDaoImpl.getById(id);
    }

    @Override
    public DocumentTypeDetail update(DocumentTypeDetail t) {
        checkNotNull(t, "DocumentTypeDetail entity must not be null");
        checkNotNull(t.getId(), "DocumentTypeDetail id must not be null");
        checkNotNull(t.getDocumentTypeId(), "DocumentType id must not be null");
        checkNotNull(t.getDmsFieldId(), "DocumentTypeDetail Field Id must not be null");
        return documentTypeDetailDaoImpl.update(t);

    }

    @Override
    public DocumentTypeDetail remove(int id, int userId) {
        checkNotNull(id, "DocumentTypeDetail id must not be null");
        DocumentTypeDetail documentTypeDetail = getById(id);
        checkNotNull(documentTypeDetail, "documentTypeDetail entity not found in database.");
        documentTypeDetail.setRemovedBy(userId);
        documentTypeDetail.setRemovedDate(LocalDateTime.now());
        return documentTypeDetailDaoImpl.update(documentTypeDetail);
    }

    @Override
    public List<DocumentTypeDetail> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return documentTypeDetailDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<DocumentTypeDetail> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return documentTypeDetailDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return documentTypeDetailDaoImpl.countAll();
    }

    @Override
    public List<DocumentTypeDetail> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DocumentTypeDetailModel tranformToModel(DocumentTypeDetail t) {
        DocumentTypeDetailModel documentTypeDetailModel = null;
        if (t != null) {
            LOG.debug("tranformToModel == 0");
             documentTypeDetailModel = new DocumentTypeDetailModel();
            documentTypeDetailModel.setId(t.getId());
            documentTypeDetailModel.setDocumentTypeId(t.getDocumentTypeId());
            documentTypeDetailModel.setDmsFieldId(t.getDmsFieldId());
            documentTypeDetailModel.setOrder(t.getOrderNo());
            documentTypeDetailModel.setDocumentTypeDetailName(t.getDocumentTypeDetailName());
            documentTypeDetailModel.setDocumentTypeDetailView(t.getDocumentTypeDetailView());
            documentTypeDetailModel.setDocumentTypeDetailSearch(t.getDocumentTypeDetailSearch());
            documentTypeDetailModel.setDocumentTypeDetailEdit(t.getDocumentTypeDetailEdit());
            documentTypeDetailModel.setDocumentTypeDetailUnique(t.getDocumentTypeDetailUnique());
            documentTypeDetailModel.setDocumentTypeDetailRequire(t.getDocumentTypeDetailRequire());
            documentTypeDetailModel.setDocumentTypeDetailShowComma(t.getDocumentTypeDetailShowComma());
            documentTypeDetailModel.setDocumentTypeDetailColumnColor(t.getDocumentTypeDetailColumnColor());
            documentTypeDetailModel.setDocumentTypeDetailFontColor(t.getDocumentTypeDetailFontColor());
            documentTypeDetailModel.setDocumentTypeDetailFontItalic(t.getDocumentTypeDetailFontItalic());
            documentTypeDetailModel.setDocumentTypeDetailFontBold(t.getDocumentTypeDetailFontBold());
            documentTypeDetailModel.setDocumentTypeDetailFontUnderline(t.getDocumentTypeDetailFontUnderline());
            documentTypeDetailModel.setDocumentTypeDetailDefault(t.getDocumentTypeDetailDefault());
            documentTypeDetailModel.setDocumentTypeDetailLookup(t.getDocumentTypeDetailLookup());
            documentTypeDetailModel.setDocumentTypeDetailColumnWidth(t.getDocumentTypeDetailColumnWidth());
            documentTypeDetailModel.setDocumentTypeDetailAlignment(t.getDocumentTypeDetailAlignment());

        } else {
            LOG.debug("tranformToModel == 1");
        }
        return documentTypeDetailModel;
    }

    public List<DocumentTypeDetail> findListByDocumentTypeId(int documentTypeId) {
        checkNotNull(documentTypeId, "documentTypeId must not be null");
        return documentTypeDetailDaoImpl.findListByDocumentTypeId(documentTypeId);
    }

    public boolean createListDocumentTypeDetail(List<DocumentTypeDetail> listDocumentTypeDetail, int userId) {
        checkNotNull(listDocumentTypeDetail, "listDocumentTypeDetail must not be null");
        checkNotNull(userId, "userId not must not be null.");
        return documentTypeDetailDaoImpl.createListDocumentTypeDetail(listDocumentTypeDetail, userId);
    }

    public boolean deleteByDocumentTypeId(int id, int userId) {
        checkNotNull(id, "DocumentType id must not be null");
        checkNotNull(userId, "userId not must not be null.");
        return documentTypeDetailDaoImpl.deleteByDocumentTypeId(id, userId);

    }

    @Override
    public DocumentTypeDetail getByIdNotRemoved(int id) {
        checkNotNull(id, "DocumentTypeDetail id entity must not be null");
        return documentTypeDetailDaoImpl.getByIdNotRemoved(id);
    }
}
