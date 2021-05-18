/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.daoimpl;

import com.px.documentfile.dao.DocumentFileDao;
import com.px.documentfile.entity.DocumentFile;
import com.px.share.daoimpl.GenericDaoImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author TOP
 */
public class DocumentFileDaoImpl extends GenericDaoImpl<DocumentFile, Integer> implements DocumentFileDao {

    public DocumentFileDaoImpl() {
        super(DocumentFile.class);
    }

    @Override
    public int createDocumentFile(DocumentFile docFile) {

        docFile.setCreatedBy(docFile.getCreatedBy());
        docFile.setUpdatedBy(docFile.getCreatedBy());
        docFile.setUpdatedDate(LocalDateTime.now());
        int docFileId = this.create(docFile).getId();

        DocumentFile docData = getDocumentFileByDocumentFileId(docFileId);
        docData.setUpdatedDate(LocalDateTime.now());
        if (docFile.getGroupFileId() == 0) {
            docData.setGroupFileId(docFileId);
        } else {
            docData.setGroupFileId(docFile.getGroupFileId());
        }
        this.update(docData);

        return docFileId;
    }

    @Override
    public boolean updateDocumentFile(DocumentFile docFile) {
        DocumentFile documentFileDbNew = findDocumentFile(docFile.getModuleId(), docFile.getLinkId(), docFile.getLinkId2());
        DocumentFile documentFileDbOld = findDocumentFile(docFile.getModuleId(), docFile.getLinkId(), docFile.getLinkId2());

        documentFileDbOld.setUpdatedBy(docFile.getUpdatedBy());
        documentFileDbOld.setCreatedBy(docFile.getUpdatedBy());
        documentFileDbOld.setUpdatedDate(LocalDateTime.now());
        documentFileDbNew.setLinkType(docFile.getLinkType());
        documentFileDbNew.setFileOrder(docFile.getFileOrder());
        documentFileDbNew.setDocumentFileName(docFile.getDocumentFileName());
        documentFileDbNew.setDocumentFileType(docFile.getDocumentFileType());
        documentFileDbNew.setCaption(docFile.getCaption());
        documentFileDbNew.setOwnerName(docFile.getOwnerName());
        documentFileDbNew.setIsText(docFile.getIsText());
        documentFileDbNew.setImageCount(docFile.getImageCount());
        documentFileDbNew.setGroupFileId(docFile.getGroupFileId());
        documentFileDbNew.setDocumentFileSec(docFile.getDocumentFileSec());
        documentFileDbNew.setFileSize(docFile.getFileSize());
        documentFileDbNew.setMd5(docFile.getMd5());
        int newDocFileId = createDocumentFile(documentFileDbNew);

        changeRefId(docFile.getModuleId(), docFile.getLinkId(), docFile.getLinkId2(), newDocFileId, docFile.getUpdatedBy());
        return true;
    }

    @Override
    public DocumentFile findDocumentFile(int moduleId, int linkId, int linkId2) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId2", linkId2));
        conjunction.add(Restrictions.eq("moduleId", moduleId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);

        return this.getOneByCriteria(criteria);
    }

    @Override
    public boolean removeDocumentFile(DocumentFile documentFile) {
        List<DocumentFile> result = ListDocumentFile(documentFile.getModuleId(), documentFile.getLinkId(), documentFile.getLinkId2(), "id", 0);
        int size = result.size();
        for (int i = 0; i < size; i++) {
            //set
            DocumentFile documentFileDb = findDocumentFile(documentFile.getModuleId(), documentFile.getLinkId(), documentFile.getLinkId2());
            documentFileDb.setRemovedBy(documentFile.getRemovedBy());
            documentFileDb.setRemovedDate(LocalDateTime.now());

            this.update(documentFileDb);
        }
        return true;
    }

    @Override
    public List<DocumentFile> ListDocumentFile(int moduleId, int linkId, int linkId2, String order, int sort) {
        //sort = 0 : น้อยไปมาก asc
        // sort = 1 : มาก ไปน้อย des
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId2", linkId2));
        conjunction.add(Restrictions.eq("moduleId", moduleId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query        
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        if (sort == 0) {
            criteria.add(conjunction).addOrder(Order.asc(order));
        }
        if (sort == 1) {
            criteria.add(conjunction).addOrder(Order.desc(order));
        }
        return this.listByCriteria(criteria);

    }

    @Override
    public boolean changeRefId(int moduleId, int linkId, int linkId2, int refId, int ModifiedBy) {
        List<DocumentFile> result = ListDocumentFile(moduleId, linkId, linkId2, "id", 0);
        int size = result.size();
        for (int i = 0; i < size - 1; i++) {
            DocumentFile documentFileDbOld = result.get(i);

            documentFileDbOld.setReferenceId(refId);
            documentFileDbOld.setUpdatedBy(ModifiedBy);
            documentFileDbOld.setUpdatedDate(LocalDateTime.now());

            this.update(documentFileDbOld);

        }
        DocumentFile documentFileDbOld = result.get(size - 1);
        documentFileDbOld.setReferenceId(0);
        this.update(documentFileDbOld);

        return true;
    }

    @Override
    public boolean removeDocumentFileById(DocumentFile documentFile) {
        DocumentFile documentFileDb = findDocumentFileByDocumentId(documentFile.getId());
        documentFileDb.setRemovedBy(documentFile.getRemovedBy());
        documentFileDb.setRemovedDate(LocalDateTime.now());
        this.update(documentFileDb);

        List<DocumentFile> result = findListDocumentFileByRefId(documentFile.getId());
        int size = result.size();
        int i;
        for (i = 0; i < size; i++) {

            DocumentFile documentFileDb2 = result.get(i);
            documentFileDb2.setRemovedBy(documentFile.getRemovedBy());
            documentFileDb2.setRemovedDate(LocalDateTime.now());
            this.update(documentFileDb2);

        }
        return true;
    }

    @Override
    public int updateDocumentFileById(DocumentFile documentFile) {
        DocumentFile documentFileDbNew = findDocumentFileByDocumentId(documentFile.getId());
        DocumentFile documentFileDbOld = findDocumentFileByDocumentId(documentFile.getId());
        documentFileDbOld.setUpdatedBy(documentFile.getUpdatedBy());
        documentFileDbOld.setCreatedBy(documentFile.getUpdatedBy());
        documentFileDbOld.setUpdatedDate(LocalDateTime.now());
        documentFile.setModuleId(documentFileDbOld.getModuleId());
        documentFile.setLinkId(documentFileDbOld.getLinkId());
        documentFile.setLinkId2(documentFileDbOld.getLinkId2());
        documentFile.setGroupFileId(documentFileDbOld.getGroupFileId());
        documentFile.setCreatedBy(documentFileDbOld.getCreatedBy());
        documentFile.setUpdatedDate(LocalDateTime.now());
        documentFile.setUpdatedBy(documentFileDbNew.getUpdatedBy());
        int newDocFileId = createDocumentFile(documentFile);

        changeReferenceId(newDocFileId, documentFileDbOld);

        return newDocFileId;
    }

    @Override
    public DocumentFile findDocumentFileByDocumentId(int documentFileID) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", documentFileID));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<DocumentFile> findListDocumentFileByRefId(int referenceId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("referenceId", referenceId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public boolean changeReferenceId(int documentFileIdNew, DocumentFile documentFileOld) {
        DocumentFile documentFileDbOld = documentFileOld;
        documentFileDbOld.setUpdatedBy(documentFileOld.getUpdatedBy());
        documentFileDbOld.setUpdatedDate(LocalDateTime.now());
        documentFileDbOld.setReferenceId(documentFileIdNew);

        this.update(documentFileDbOld);

        List<DocumentFile> result = findListDocumentFileByRefId(documentFileOld.getId());
        int size = result.size();
        int i;
        int order = 100;
        for (i = 0; i < size; i++) {

            DocumentFile documentFileDb2 = result.get(i);
            documentFileDb2.setUpdatedBy(documentFileOld.getUpdatedBy());
            documentFileDb2.setReferenceId(documentFileIdNew);
            documentFileDb2.setFileOrder(order - i);
            this.update(documentFileDb2);

        }

        return true;
    }

    @Override
    public boolean updateDocumentFileName(DocumentFile documentFile) {
        DocumentFile documentFileDb = findDocumentFileByDocumentId(documentFile.getId());
        documentFileDb.setUpdatedBy(documentFile.getUpdatedBy());
        documentFileDb.setUpdatedDate(LocalDateTime.now());
        documentFileDb.setDocumentFileName(documentFile.getDocumentFileName());

        this.update(documentFileDb);

        return true;
    }

    @Override
    public DocumentFile getDocumentFileDetailByDocumentId(int documentFileFile) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", documentFileFile));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public boolean updateDocumentFileSec(DocumentFile documentFile) {
        DocumentFile documentFileDb = findDocumentFileByDocumentId(documentFile.getId());
        documentFileDb.setUpdatedBy(documentFile.getUpdatedBy());
        documentFileDb.setUpdatedDate(LocalDateTime.now());
        documentFileDb.setDocumentFileSec(documentFile.getDocumentFileSec());
        this.update(documentFileDb);

        return true;
    }

    @Override
    public DocumentFile getDocumentFileByDocumentFileId(int docFileId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", docFileId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);

        return this.getOneByCriteria(criteria);
    }

    public boolean checkDocumentFileinDocument(int documentid) {

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", documentid));
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);
        DocumentFile dmsDocumentFile = this.getOneByCriteria(criteria);
        int getdocid = 0;
        if (dmsDocumentFile != null) {
            getdocid = dmsDocumentFile.getId();
        }
        return getdocid > 0;
    }

    @Override
    public DocumentFile getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<DocumentFile> ListDocumentFileWithSec(int moduleId, int linkId, int linkId2, String order, int sort, String sec) {
        //sort = 0 : น้อยไปมาก asc
        // sort = 1 : มาก ไปน้อย des

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId2", linkId2));
        conjunction.add(Restrictions.eq("moduleId", moduleId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        String[] arrB = sec.split(",");

        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        if (sort == 0) {
            criteria.add(conjunction).addOrder(Order.asc(order));
        }
        if (sort == 1) {
            criteria.add(conjunction).addOrder(Order.desc(order));
        }
        return this.listByCriteria(criteria);
    }

    public List<DocumentFile> listMotherDocFile(int moduleId, int linkId, int linkId2, String order, int sort) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("linkId", linkId));
        conjunction.add(Restrictions.eq("linkId2", linkId2));
        conjunction.add(Restrictions.eq("referenceId", 0));
        conjunction.add(Restrictions.eq("moduleId", moduleId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        order = "fileOrder";

        //create Query        
        DetachedCriteria criteria = DetachedCriteria.forClass(DocumentFile.class);
        if (sort == 0) {
            criteria.add(conjunction).addOrder(Order.asc(order));
        }
        if (sort == 1) {
            criteria.add(conjunction).addOrder(Order.desc(order));
        }
        return this.listByCriteria(criteria);

    }
}
