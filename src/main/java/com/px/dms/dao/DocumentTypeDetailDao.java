/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.dao;

import com.px.dms.entity.DocumentTypeDetail;
import com.px.share.dao.GenericDao;
import java.util.List;

/**
 *
 * @author TOP
 */
public interface DocumentTypeDetailDao extends GenericDao<DocumentTypeDetail, Integer>{
    DocumentTypeDetail findByDocumentTypeDetailId(int documentTypeDetailId);
    List<DocumentTypeDetail> findListByDocumentTypeId(int documentTypeId);
    boolean createListDocumentTypeDetail(List<DocumentTypeDetail> listDocumentTypeDetail,int userId);
//    boolean deleteByDocumentTypeId(int documentTypeId);
}
