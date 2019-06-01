/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.dao;

import com.px.dms.entity.DmsDocument;
import com.px.share.dao.GenericDao;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;

/**
 *
 * @author TOP
 */
public interface DmsDocumentDao extends GenericDao<DmsDocument, Integer>{
    boolean checkDocmentInFolder(int folderId);
     int copyDocument(DmsDocument documentCopy);
     boolean moveDocument(DmsDocument documentMove);
     List< DmsDocument> findListDocumentExpire(int folderId);
     List< DmsDocument> findListDocument(int folderId) ;
     List<HashMap> listColumnName() throws Exception;
     boolean updateNameDocumentByDocumentId (DmsDocument document);
     boolean updateDocumentTypeByDocumentId (DmsDocument document);
      Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams, int folderid);
      List<DmsDocument> searchDocument(MultivaluedMap<String, String> queryParams , int folderid ,int offset,int limit,int userId) ;
     public DmsDocument updateDocumentByDocumentId(DmsDocument document) ;
}
