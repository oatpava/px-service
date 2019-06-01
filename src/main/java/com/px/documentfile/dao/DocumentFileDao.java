/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.dao;


import com.px.documentfile.entity.DocumentFile;
import com.px.share.dao.GenericDao;
import java.util.List;
/**
 *
 * @author TOP
 */
public interface DocumentFileDao extends GenericDao<DocumentFile, Integer>{
    
    int createDocumentFile(DocumentFile docFile);

    boolean updateDocumentFile(DocumentFile docFile);

    DocumentFile findDocumentFile(int moduleId, int linkId, int linkId2);

    boolean removeDocumentFile(DocumentFile documentFile);

    List< DocumentFile> ListDocumentFile(int moduleId, int linkId, int linkId2, String order, int sort);

    boolean changeRefId(int moduleId, int linkId, int linkId2, int refId, int ModifiedBy);

    boolean removeDocumentFileById(DocumentFile documentFile);

    int updateDocumentFileById(DocumentFile documentFile);

    DocumentFile findDocumentFileByDocumentId(int documentFileID);

    List< DocumentFile> findListDocumentFileByRefId(int referenceId);

    boolean changeReferenceId(int documentFileIdNew, DocumentFile documentFileOld);

    boolean updateDocumentFileName(DocumentFile documentFile);

    DocumentFile getDocumentFileDetailByDocumentId(int documentFileFile);

    boolean updateDocumentFileSec(DocumentFile documentFile);

    DocumentFile getDocumentFileByDocumentFileId(int docFileId);
    
    List<DocumentFile> ListDocumentFileWithSec(int moduleId, int linkId, int linkId2, String order, int sort, String sec);
    
    
    
}
