/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.service;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.entity.TempTable;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.dms.daoimpl.DmsDocumentDaoImpl;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DocumentType;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.dms.entity.WfDocumentType;
import com.px.dms.model.DmsDocumentModel;
import com.px.dms.model.FieldSearchModel;
import com.px.share.entity.LogData;
import com.px.share.service.GenericService;
import com.px.share.service.LogDataService;
import com.px.share.service.ParamService;
import com.px.share.util.Common;
import java.awt.Color;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
public class DmsDocumentService implements GenericService<DmsDocument, DmsDocumentModel> {

    private static final Logger LOG = Logger.getLogger(DmsDocumentService.class.getName());
    private final DmsDocumentDaoImpl dmsDocumentDaoImpl;

    public DmsDocumentService() {
        this.dmsDocumentDaoImpl = new DmsDocumentDaoImpl();
    }

    @Override
    public DmsDocument create(DmsDocument t) {

        return dmsDocumentDaoImpl.create(t);
    }

    @Override
    public DmsDocument getById(int id) {
        return dmsDocumentDaoImpl.getById(id);
    }

    public DmsDocument getByIdRemovedByNotzero(int id) {
        return dmsDocumentDaoImpl.getByIdRemovedByNotzero(id);
    }

    @Override
    public DmsDocument update(DmsDocument t) {
        checkNotNull(t, "Document entity must not be null");
        checkNotNull(t.getId(), "Document id must not be null");
        return dmsDocumentDaoImpl.update(t);
    }

    @Override
    public DmsDocument remove(int id, int userId) {
        checkNotNull(id, "documentid must not be null");
        DmsDocument doc = getById(id);
        checkNotNull(doc, "document entity not found in database.");
        doc.setRemovedBy(userId);
        doc.setRemovedDate(LocalDateTime.now());
        return dmsDocumentDaoImpl.update(doc);
    }

    @Override
    public List<DmsDocument> list(int limit, int offset, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsDocumentDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<DmsDocument> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return dmsDocumentDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return dmsDocumentDaoImpl.countAll();
    }
    
     public int countDocInfolder(int folder) {
        return dmsDocumentDaoImpl.countDocInfolder(folder);
    }

    @Override
    public List<DmsDocument> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DmsDocumentModel tranformToModel(DmsDocument t) {
        DmsDocumentModel dmsDocumentModel = new DmsDocumentModel();

        if (t != null) {

//            dmsDocumentModel = new DmsDocumentModel(t.getId(), t.getDocumentTypeId(), t.getDmsDocumentName(), null, t.getDmsDocumentPublicStatus(), t.getDmsFolderId(), null, null, null, null, null, t.getDmsDocumentFloat01(), t.getDmsDocumentFloat02(), t.getDmsDocumentVarchar01(), t.getDmsDocumentVarchar02(), t.getDmsDocumentVarchar03(), t.getDmsDocumentVarchar04(), t.getDmsDocumentVarchar05(), t.getDmsDocumentVarchar06(), t.getDmsDocumentText01(), t.getDmsDocumentText02(), t.getDmsDocumentText03(), t.getDmsDocumentText04(), t.getDmsDocumentInt01(), t.getDmsDocumentInt02(), t.getDmsDocumentInt03(), t.getDmsDocumentInt04());
            dmsDocumentModel.setDocumentDate01(Common.localDateTimeToString(t.getDmsDocumentDatetime01()));

            dmsDocumentModel.setDocumentDate02(Common.localDateTimeToString(t.getDmsDocumentDatetime02()));

            dmsDocumentModel.setDocumentDate03(Common.localDateTimeToString(t.getDmsDocumentDatetime03()));

            dmsDocumentModel.setDocumentDate04(Common.localDateTimeToString(t.getDmsDocumentDatetime04()));

            dmsDocumentModel.setDocumentExpireDate(Common.localDateTimeToString(t.getDmsDocumentExpireDate()));

            dmsDocumentModel.setDocumentPublicDate(Common.localDateTimeToString(t.getDmsDocumentPublicDate()));

            dmsDocumentModel.setId(t.getId());
            dmsDocumentModel.setDocumentTypeId(t.getDocumentTypeId());
            dmsDocumentModel.setDocumentName(t.getDmsDocumentName());
            dmsDocumentModel.setDocumentPublicStatus(t.getDmsDocumentPublicStatus());
            dmsDocumentModel.setDocumentFolderId(t.getDmsFolderId());
            dmsDocumentModel.setDocumentFloat01(t.getDmsDocumentFloat01());
            dmsDocumentModel.setDocumentFloat02(t.getDmsDocumentFloat02());
            dmsDocumentModel.setDocumentVarchar01(t.getDmsDocumentVarchar01());
            dmsDocumentModel.setDocumentVarchar02(t.getDmsDocumentVarchar02());
            dmsDocumentModel.setDocumentVarchar03(t.getDmsDocumentVarchar03());
            dmsDocumentModel.setDocumentVarchar04(t.getDmsDocumentVarchar04());
            dmsDocumentModel.setDocumentVarchar05(t.getDmsDocumentVarchar05());
            dmsDocumentModel.setDocumentVarchar06(t.getDmsDocumentVarchar06());
            dmsDocumentModel.setDocumentVarchar07(t.getDmsDocumentVarchar07());
            dmsDocumentModel.setDocumentVarchar08(t.getDmsDocumentVarchar08());
            dmsDocumentModel.setDocumentVarchar09(t.getDmsDocumentVarchar09());
            dmsDocumentModel.setDocumentVarchar10(t.getDmsDocumentVarchar10());
            dmsDocumentModel.setDocumentText01(t.getDmsDocumentText01());
            dmsDocumentModel.setDocumentText02(t.getDmsDocumentText02());
            dmsDocumentModel.setDocumentText03(t.getDmsDocumentText03());
            dmsDocumentModel.setDocumentText04(t.getDmsDocumentText04());
            dmsDocumentModel.setDocumentText05(t.getDmsDocumentText05());
            dmsDocumentModel.setDocumentInt01(t.getDmsDocumentInt01());
            dmsDocumentModel.setDocumentInt02(t.getDmsDocumentInt02());
            dmsDocumentModel.setDocumentInt03(t.getDmsDocumentInt03());
            dmsDocumentModel.setDocumentInt04(t.getDmsDocumentInt04());
            dmsDocumentModel.setDmsDocumentSec(t.getDmsDocumentSec());
            dmsDocumentModel.setCreatedDate(Common.localDateTimeToString(t.getCreatedDate()));
            dmsDocumentModel.setCreatedBy(t.getCreatedBy());
            dmsDocumentModel.setUpdatedDate(Common.localDateTimeToString(t.getUpdatedDate()));
            dmsDocumentModel.setUpdatedBy(t.getUpdatedBy());
            dmsDocumentModel.setRemovedDate(Common.localDateTimeToString(t.getRemovedDate()));
            dmsDocumentModel.setRemovedBy(t.getRemovedBy());
            dmsDocumentModel.setWfTypeId(t.getWfTypeId());
            dmsDocumentModel.setFlowId(t.getFlowId());
            dmsDocumentModel.setDmsSearchId(t.getDmsSearchId());
            dmsDocumentModel.setFullPathName(t.getFullPathName());
            dmsDocumentModel.setBorrowStatus(t.getBorrowStatus());
            dmsDocumentModel.setCheckInout(t.getCheckInout());
        }
//        System.out.println("tranformToModel dmsDocumentModel = "+dmsDocumentModel.getDocumentName());
        return dmsDocumentModel;
    }

    public DmsDocumentModel tranformToModel2(DmsDocument t, String isExp) {
        DmsDocumentModel dmsDocumentModel = new DmsDocumentModel();

        if (t != null) {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = new UserProfile();

//            dmsDocumentModel = new DmsDocumentModel(t.getId(), t.getDocumentTypeId(), t.getDmsDocumentName(), null, t.getDmsDocumentPublicStatus(), t.getDmsFolderId(), null, null, null, null, null, t.getDmsDocumentFloat01(), t.getDmsDocumentFloat02(), t.getDmsDocumentVarchar01(), t.getDmsDocumentVarchar02(), t.getDmsDocumentVarchar03(), t.getDmsDocumentVarchar04(), t.getDmsDocumentVarchar05(), t.getDmsDocumentVarchar06(), t.getDmsDocumentText01(), t.getDmsDocumentText02(), t.getDmsDocumentText03(), t.getDmsDocumentText04(), t.getDmsDocumentInt01(), t.getDmsDocumentInt02(), t.getDmsDocumentInt03(), t.getDmsDocumentInt04());
            dmsDocumentModel.setDocumentDate01(Common.localDateTimeToString(t.getDmsDocumentDatetime01()));

            dmsDocumentModel.setDocumentDate02(Common.localDateTimeToString(t.getDmsDocumentDatetime02()));

            dmsDocumentModel.setDocumentDate03(Common.localDateTimeToString(t.getDmsDocumentDatetime03()));

            dmsDocumentModel.setDocumentDate04(Common.localDateTimeToString(t.getDmsDocumentDatetime04()));

            dmsDocumentModel.setDocumentExpireDate(Common.localDateTimeToString(t.getDmsDocumentExpireDate()));

            dmsDocumentModel.setDocumentPublicDate(Common.localDateTimeToString(t.getDmsDocumentPublicDate()));

            dmsDocumentModel.setId(t.getId());
            dmsDocumentModel.setDocumentTypeId(t.getDocumentTypeId());
            dmsDocumentModel.setDocumentName(t.getDmsDocumentName());
            dmsDocumentModel.setDocumentPublicStatus(t.getDmsDocumentPublicStatus());
            dmsDocumentModel.setDocumentFolderId(t.getDmsFolderId());
            dmsDocumentModel.setDocumentFloat01(t.getDmsDocumentFloat01());
            dmsDocumentModel.setDocumentFloat02(t.getDmsDocumentFloat02());
            dmsDocumentModel.setDocumentVarchar01(t.getDmsDocumentVarchar01());
            dmsDocumentModel.setDocumentVarchar02(t.getDmsDocumentVarchar02());
            dmsDocumentModel.setDocumentVarchar03(t.getDmsDocumentVarchar03());
            dmsDocumentModel.setDocumentVarchar04(t.getDmsDocumentVarchar04());
            dmsDocumentModel.setDocumentVarchar05(t.getDmsDocumentVarchar05());
            dmsDocumentModel.setDocumentVarchar06(t.getDmsDocumentVarchar06());
            dmsDocumentModel.setDocumentVarchar07(t.getDmsDocumentVarchar07());
            dmsDocumentModel.setDocumentVarchar08(t.getDmsDocumentVarchar08());
            dmsDocumentModel.setDocumentVarchar09(t.getDmsDocumentVarchar09());
            dmsDocumentModel.setDocumentVarchar10(t.getDmsDocumentVarchar10());
            dmsDocumentModel.setDocumentText01(t.getDmsDocumentText01());
            dmsDocumentModel.setDocumentText02(t.getDmsDocumentText02());
            dmsDocumentModel.setDocumentText03(t.getDmsDocumentText03());
            dmsDocumentModel.setDocumentText04(t.getDmsDocumentText04());
            dmsDocumentModel.setDocumentText05(t.getDmsDocumentText05());
            dmsDocumentModel.setDocumentInt01(t.getDmsDocumentInt01());
            dmsDocumentModel.setDocumentInt02(t.getDmsDocumentInt02());
            dmsDocumentModel.setDocumentInt03(t.getDmsDocumentInt03());
            dmsDocumentModel.setDocumentInt04(t.getDmsDocumentInt04());
            dmsDocumentModel.setDmsDocumentSec(t.getDmsDocumentSec());
            dmsDocumentModel.setCreatedDate(Common.localDateTimeToString(t.getCreatedDate()));
            dmsDocumentModel.setCreatedBy(t.getCreatedBy());
            dmsDocumentModel.setUpdatedDate(Common.localDateTimeToString(t.getUpdatedDate()));
            dmsDocumentModel.setUpdatedBy(t.getUpdatedBy());
            dmsDocumentModel.setRemovedDate(Common.localDateTimeToString(t.getRemovedDate()));
            dmsDocumentModel.setRemovedBy(t.getRemovedBy());
            dmsDocumentModel.setIsExp(isExp);
            dmsDocumentModel.setFlowId(t.getFlowId());
            dmsDocumentModel.setDmsSearchId(t.getDmsSearchId());
            dmsDocumentModel.setFullPathName(t.getFullPathName());
            dmsDocumentModel.setBorrowStatus(t.getBorrowStatus());
            dmsDocumentModel.setCheckInout(t.getCheckInout());

            if (t.getCreatedBy() != 0 && t.getCreatedBy() != -1) {
                userProfile = userProfileService.getById(t.getCreatedBy());
                dmsDocumentModel.setUserProfileCreate(userProfileService.tranformToModel(userProfile));
            }
            if (t.getUpdatedBy() != 0 && t.getCreatedBy() != -1) {
                userProfile = userProfileService.getById(t.getUpdatedBy());
                dmsDocumentModel.setUserProfileUpdate(userProfileService.tranformToModel(userProfile));

            }
            if (t.getUpdatedBy() == 0 && t.getCreatedBy() != -1) {
                userProfile.setUserProfileFullName("");
                dmsDocumentModel.setUserProfileUpdate(userProfileService.tranformToModel(userProfile));

            }

            if (t.getRemovedBy() != 0 && t.getCreatedBy() != -1) {
                userProfile = userProfileService.getById(t.getRemovedBy());
                dmsDocumentModel.setUserProfileDel(userProfileService.tranformToModel(userProfile));

            }

        }
        return dmsDocumentModel;
    }

    public boolean checkDocmentInFolder(int folderId) {
        checkNotNull(folderId, "folder Id must not be null");
        return dmsDocumentDaoImpl.checkDocmentInFolder(folderId);
    }

    public int copyDocument(DmsDocument documentCopy) {
        checkNotNull(documentCopy, "documentCopy entity must not be null");
        checkNotNull(documentCopy.getDmsFolderId(), "folder Id must not be null");
//     checkNotNull(documentCopy, "documentCopy Id must not be null");
        return dmsDocumentDaoImpl.copyDocument(documentCopy);
    }

    public boolean moveDocument(DmsDocument documentMove) {
        checkNotNull(documentMove, "documentMove entity must not be null");
        checkNotNull(documentMove.getDmsFolderId(), "folder Id must not be null");
        return dmsDocumentDaoImpl.moveDocument(documentMove);
    }

    public List< DmsDocument> findListDocumentExpire(int folderId) {
        checkNotNull(folderId, "folder Id must not be null");
        return dmsDocumentDaoImpl.findListDocumentExpire(folderId);
    }

    public List< DmsDocument> findListDocumentExpire2() {
//        checkNotNull(folderId, "folder Id must not be null");
        return dmsDocumentDaoImpl.findListDocumentExpire2();
    }

    public List< DmsDocument> findListDocument(int folderId) {
        checkNotNull(folderId, "folder Id must not be null");
        return dmsDocumentDaoImpl.findListDocument(folderId);
    }

//    public List< DmsDocument> findListDocumentWithSec(int folderId, int id) {
//        checkNotNull(folderId, "folder Id must not be null");
//        return dmsDocumentDaoImpl.findListDocumentWithSec(folderId, id);
//    }
//    public List< DmsDocument> findListDocumentWithSecAndLimit(int folderId, int id, int offset, int limit, String sort, String dir) {
//        checkNotNull(folderId, "folder Id must not be null");
//        checkNotNull(id, "user Id must not be null");
//        checkNotNull(offset, "offset must not be null");
//        checkNotNull(limit, "limit  must not be null");
//        return dmsDocumentDaoImpl.findListDocumentWithSecAndLimit(folderId, id, offset, limit, sort, dir);
//    }
    public List< DmsDocument> findListDocumentS(int folderId, int offset, int limit, String sort, String dir) {
        checkNotNull(folderId, "folder Id must not be null");
//        checkNotNull(id, "user Id must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit  must not be null");
        return dmsDocumentDaoImpl.findListDocumentS(folderId, offset, limit, sort, dir);
    }

    public List<HashMap> listColumnName() throws Exception {
        return dmsDocumentDaoImpl.listColumnName();
    }

    public boolean updateNameDocumentByDocumentId(DmsDocument document) {
        checkNotNull(document, "document entity must not be null");
        checkNotNull(document.getDmsDocumentName(), "document name must not be null");
        return dmsDocumentDaoImpl.updateNameDocumentByDocumentId(document);
    }

    public boolean updateDocumentTypeByDocumentId(DmsDocument document) {
        checkNotNull(document, "document entity must not be null");
        checkNotNull(document.getDocumentTypeId(), "document type must not be null");
        return dmsDocumentDaoImpl.updateDocumentTypeByDocumentId(document);
    }

    public List<DmsDocument> searchDocument(MultivaluedMap<String, String> queryParams, int folderId, int offset, int limit, int userId) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(folderId, "folderId entity must not be null");
        return dmsDocumentDaoImpl.searchDocument(queryParams, folderId, offset, limit, userId);
    }

    public List<DmsDocument> searchDocumentWithOutAuth(FieldSearchModel fieldSearchData, int folderId, int offset, int limit, int userId) {
        checkNotNull(fieldSearchData, "fieldSearchData entity must not be null");
        checkNotNull(folderId, "folderId entity must not be null");
        return dmsDocumentDaoImpl.searchDocumentWithOutAuth(fieldSearchData, folderId, offset, limit, userId);
    }

    public List<DmsDocument> searchDocumentWithDocType(FieldSearchModel fieldSearchData, int docTypeId, String isWfType, int offset, int limit) {
        checkNotNull(fieldSearchData, "fieldSearchData entity must not be null");
        checkNotNull(docTypeId, "docTypeId not be null");
        checkNotNull(isWfType, "isWfType not be null");
        return dmsDocumentDaoImpl.searchDocumentWithDocType(fieldSearchData, docTypeId, isWfType, offset, limit, 0);
    }

    @Override
    public DmsDocument getByIdNotRemoved(int id) {
        checkNotNull(id, "DmsDocument id entity must not be null");
        return dmsDocumentDaoImpl.getByIdNotRemoved(id);
    }

    public DmsDocument updateDocumentByDocumentId(DmsDocument document) {

        checkNotNull(document.getId(), "DmsDocument id entity must not be null");
        return dmsDocumentDaoImpl.updateDocumentByDocumentId(document);
    }

    public DmsDocument createForWf(int userID, String documentName) {
        DmsDocument document = new DmsDocument();
        document.setCreatedBy(userID);
        document.setUpdatedDate(LocalDateTime.now());
        document.setUpdatedBy(userID);
        document.setDocumentTypeId(1);
        document.setDmsDocumentName(documentName);
        document.setDmsFolderId(-1);
        document.setDmsDocumentSec(0);
        return dmsDocumentDaoImpl.create(document);
    }

    public TempTable temptableForreport(List<DmsDocument> document, String jobType, String clientIp, int userID) {
        checkNotNull(document, "DmsDocument id entity must not be null");
        checkNotNull(jobType, "jobType must not be null");
        checkNotNull(clientIp, "clientIp must not be null");
        checkNotNull(userID, "userID must not be null");
        return dmsDocumentDaoImpl.temptableForreport(document, jobType, clientIp, userID);
    }

    public List<DmsDocument> listDocByDocType(int docType) {
        checkNotNull(docType, "docType  must not be null");
        return dmsDocumentDaoImpl.listDocByDocType(docType);
    }

    public List<DmsDocument> listDocByDocType2(int docType, int folderId, int userId) {
        checkNotNull(docType, "docTypeId  must not be null");
        checkNotNull(folderId, "folderId  must not be null");
        checkNotNull(userId, "userId  must not be null");
        return dmsDocumentDaoImpl.listDocByDocType2(docType, folderId, userId);
    }

    public List<DmsDocument> listDocByDocType3(int wfTypeId, int folderId) {
        checkNotNull(wfTypeId, "docTypeId  must not be null");
        checkNotNull(folderId, "folderId  must not be null");
        return dmsDocumentDaoImpl.listDocByDocType3(wfTypeId, folderId);
    }

    public DmsDocument saveLogForCreate(DmsDocument dmsDocument, String clientIp) {
        //For Create Log when create MeetingRoom
//        System.out.println("saveLogForCreate --- 0");
        String logDescription = this.generateLogForCreateEntity(dmsDocument);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_DMS);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
//        System.out.println("saveLogForCreate --- 1");
        return dmsDocument;
    }

    public DmsDocument saveLogForUpdate(DmsDocument dmsDocumentOld, DmsDocument dmsDocumentNew, String clientIp) {

        String logDescription = this.generateLogForUpdateEntity(dmsDocumentOld, dmsDocumentNew);

        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocumentNew.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocumentNew.getClass().getName());
        logData.setLinkId(dmsDocumentNew.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsDocumentNew;
    }

    public DmsDocument saveLogForRemove(DmsDocument dmsDocument, String clientIp) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForRemoveEntity(dmsDocument);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return dmsDocument;
    }

    public DmsDocument saveLogForCopy(DmsDocument dmsDocument, int oldFolderId, String clientIp) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForCopyEntity(dmsDocument, oldFolderId);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return dmsDocument;
    }

    public DmsDocument saveLogForMove(DmsDocument dmsDocument, int oldFolderId, String clientIp) {
        //For Create Log when remove MeetingRoom
        String logDescription = this.generateLogForMoveEntity(dmsDocument, oldFolderId);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsDocument;
    }

    public DmsDocument saveLogForEditName(DmsDocument dmsDocument, String oldName, String clientIp) {

        String logDescription = this.generateLogForEditNameEntity(dmsDocument, oldName);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsDocument;
    }

    public DmsDocument saveLogForEditType(DmsDocument dmsDocument, int oldType, String clientIp) {

        String logDescription = this.generateLogForEditTypeEntity(dmsDocument, oldType);
        LogData logData = new LogData();
        logData.setCreatedBy(dmsDocument.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(dmsDocument.getClass().getName());
        logData.setLinkId(dmsDocument.getId());
        logData.setModuleName(LogData.MODULE_DMS);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return dmsDocument;
    }

    private String generateLogForCreateEntity(DmsDocument dmsDocument) {
//        System.out.println("generateLogForCreateEntity ----- 0");
        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append(" ชื่อประเภทเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(documentTypeService.getById(dmsDocument.getDocumentTypeId()).getDocumentTypeName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่สร้าง : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getCreatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่สร้าง : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getCreatedDate()));
        dmsDocumentLog.append(" ชื่อที่เก็บเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsDocument.getDmsFolderId()).getDmsFolderName(), ""));
//        System.out.println("generateLogForCreateEntity ----- 1");
        return dmsDocumentLog.toString();
    }

    private String generateLogForRemoveEntity(DmsDocument dmsDocument) {
//         DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่ลบ : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getRemovedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่ลบ : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getRemovedDate()));
        dmsDocumentLog.append(" ชื่อที่เก็บเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsDocument.getDmsFolderId()).getDmsFolderName(), ""));
        return dmsDocumentLog.toString();
    }

    private String generateLogForUpdateEntity(DmsDocument dmsDocumentOld, DmsDocument dmsDocumentNew) {

        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder dmsDocumentLog = new StringBuilder();

        if (dmsDocumentNew.getDmsDocumentName() != null) {

            if (dmsDocumentOld.getDmsDocumentName() == null) {
                dmsDocumentOld.setDmsDocumentName(" ");
            }
            if (!dmsDocumentNew.getDmsDocumentName().equalsIgnoreCase(dmsDocumentOld.getDmsDocumentName())) {
                dmsDocumentLog.append("ชื่อเอกสาร : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentName());
                dmsDocumentLog.append("เป็น : ");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentName());
            } else {
                dmsDocumentLog.append("ชื่อเอกสาร : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentName());
            }

        }

        dmsDocumentLog.append(" ชื่อคนแก้ไข : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocumentNew.getUpdatedBy()).getUserProfileFullName(), ""));

        dmsDocumentLog.append(" เวลาที่แก้ไข : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocumentNew.getUpdatedDate()));

        if (dmsDocumentNew.getDmsDocumentDatetime01() != null) {
            if (dmsDocumentOld.getDmsDocumentDatetime01() == null) {
                dmsDocumentLog.append("เอกสารหมดอายุ : ");
                dmsDocumentLog.append(" ");
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentExpireDate());
            } else {
                dmsDocumentLog.append("เอกสารหมดอายุ : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentExpireDate());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentExpireDate());

            }
        }

        if (dmsDocumentNew.getDmsDocumentDatetime01() != null) {

            if (dmsDocumentOld.getDmsDocumentDatetime01() == null) {

                dmsDocumentLog.append("DATETIME01 : ");
                dmsDocumentLog.append(" ");
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime01());
            } else {

                dmsDocumentLog.append("DATETIME01 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentDatetime01());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime01());
            }
        }

        if (dmsDocumentNew.getDmsDocumentDatetime02() != null) {
            if (dmsDocumentOld.getDmsDocumentDatetime02() == null) {
                dmsDocumentLog.append("DATETIME02 : ");
                dmsDocumentLog.append(" ");
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime02());
            } else {
                dmsDocumentLog.append("DATETIME02 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentDatetime02());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime02());
            }

        }

        if (dmsDocumentNew.getDmsDocumentDatetime03() != null) {
            if (dmsDocumentOld.getDmsDocumentDatetime03() == null) {
                dmsDocumentLog.append("DATETIME03 : ");
                dmsDocumentLog.append(" ");
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime03());
            } else {
                dmsDocumentLog.append("DATETIME03 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentDatetime03());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime03());
            }

        }

        if (dmsDocumentNew.getDmsDocumentDatetime04() != null) {
            if (dmsDocumentOld.getDmsDocumentDatetime03() == null) {
                dmsDocumentLog.append("DATETIME04 : ");
                dmsDocumentLog.append(" ");
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime04());

            } else {
                dmsDocumentLog.append("DATETIME04 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentDatetime04());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentDatetime04());
            }

        }

        if (dmsDocumentOld.getDmsDocumentIntComma() != dmsDocumentNew.getDmsDocumentIntComma()) {

            dmsDocumentLog.append("dmsDocumentIntComma : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentIntComma());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentIntComma());

        }

        if (dmsDocumentOld.getDmsDocumentFloat01() != dmsDocumentNew.getDmsDocumentFloat01()) {

            dmsDocumentLog.append("FLOAT01 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentFloat01());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentFloat01());

        }

        if (dmsDocumentOld.getDmsDocumentFloat02() != dmsDocumentNew.getDmsDocumentFloat02()) {

            dmsDocumentLog.append("FLOAT02 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentFloat02());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentFloat02());

        }

        if (dmsDocumentOld.getDmsDocumentText01() != null && dmsDocumentNew.getDmsDocumentText01() != null) {
            if (dmsDocumentOld.getDmsDocumentText01().equals(dmsDocumentNew.getDmsDocumentText01())) {

                dmsDocumentLog.append("TEXT01 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentText01());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentText01());

            }
        }

        if (dmsDocumentOld.getDmsDocumentText02() != null && dmsDocumentNew.getDmsDocumentText02() != null) {
            if (!dmsDocumentOld.getDmsDocumentText02().equals(dmsDocumentNew.getDmsDocumentText02())) {

                dmsDocumentLog.append("TEXT02 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentText02());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentText02());

            }
        }

        if (dmsDocumentOld.getDmsDocumentText03() != null && dmsDocumentNew.getDmsDocumentText03() != null) {
            if (!dmsDocumentOld.getDmsDocumentText03().equals(dmsDocumentNew.getDmsDocumentText03())) {

                dmsDocumentLog.append("TEXT03 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentText03());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentText03());

            }
        }

        if (dmsDocumentOld.getDmsDocumentText04() != null && dmsDocumentNew.getDmsDocumentText04() != null) {
            if (!dmsDocumentOld.getDmsDocumentText04().equals(dmsDocumentNew.getDmsDocumentText04())) {

                dmsDocumentLog.append("TEXT04 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentText04());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentText04());

            }
        }

        if (dmsDocumentOld.getDmsDocumentInt01() != dmsDocumentNew.getDmsDocumentInt01()) {

            dmsDocumentLog.append("INT01 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentInt01());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentInt01());

        }

        if (dmsDocumentOld.getDmsDocumentInt02() != dmsDocumentNew.getDmsDocumentInt02()) {

            dmsDocumentLog.append("INT02 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentInt02());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentInt02());

        }

        if (dmsDocumentOld.getDmsDocumentInt03() != dmsDocumentNew.getDmsDocumentInt03()) {

            dmsDocumentLog.append("INT03 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentInt03());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentInt03());

        }

        if (dmsDocumentOld.getDmsDocumentInt04() != dmsDocumentNew.getDmsDocumentInt04()) {

            dmsDocumentLog.append("INT04 : ");
            dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentInt04());
            dmsDocumentLog.append("เป็น :");
            dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentInt04());

        }

        if (dmsDocumentNew.getDmsDocumentVarchar01() != null) {

            if (dmsDocumentOld.getDmsDocumentVarchar01() == null) {
                dmsDocumentOld.setDmsDocumentVarchar01("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar01().equals(dmsDocumentNew.getDmsDocumentVarchar01())) {

                dmsDocumentLog.append("VARCHAR01 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar01());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar01());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar02() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar02() == null) {
                dmsDocumentOld.setDmsDocumentVarchar02("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar02().equals(dmsDocumentNew.getDmsDocumentVarchar02())) {

                dmsDocumentLog.append("VARCHAR02 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar02());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar02());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar03() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar03() == null) {
                dmsDocumentOld.setDmsDocumentVarchar03("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar03().equals(dmsDocumentNew.getDmsDocumentVarchar03())) {

                dmsDocumentLog.append("VARCHAR03 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar03());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar03());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar04() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar04() == null) {
                dmsDocumentOld.setDmsDocumentVarchar04("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar04().equals(dmsDocumentNew.getDmsDocumentVarchar04())) {

                dmsDocumentLog.append("VARCHAR04 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar04());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar04());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar05() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar05() == null) {
                dmsDocumentOld.setDmsDocumentVarchar05("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar05().equals(dmsDocumentNew.getDmsDocumentVarchar05())) {

                dmsDocumentLog.append("VARCHAR05 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar05());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar05());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar06() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar06() == null) {
                dmsDocumentOld.setDmsDocumentVarchar06("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar06().equals(dmsDocumentNew.getDmsDocumentVarchar06())) {

                dmsDocumentLog.append("VARCHAR06 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar06());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar06());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar07() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar07() == null) {
                dmsDocumentOld.setDmsDocumentVarchar07("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar07().equals(dmsDocumentNew.getDmsDocumentVarchar07())) {

                dmsDocumentLog.append("VARCHAR07 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar07());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar07());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar08() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar08() == null) {
                dmsDocumentOld.setDmsDocumentVarchar08("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar08().equals(dmsDocumentNew.getDmsDocumentVarchar08())) {

                dmsDocumentLog.append("VARCHAR08 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar08());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar08());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar09() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar09() == null) {
                dmsDocumentOld.setDmsDocumentVarchar09("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar09().equals(dmsDocumentNew.getDmsDocumentVarchar09())) {

                dmsDocumentLog.append("VARCHAR09 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar09());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar09());

            }
        }

        if (dmsDocumentNew.getDmsDocumentVarchar10() != null) {
            if (dmsDocumentOld.getDmsDocumentVarchar10() == null) {
                dmsDocumentOld.setDmsDocumentVarchar10("");
            }
            if (!dmsDocumentOld.getDmsDocumentVarchar10().equals(dmsDocumentNew.getDmsDocumentVarchar10())) {

                dmsDocumentLog.append("VARCHAR10 : ");
                dmsDocumentLog.append(dmsDocumentOld.getDmsDocumentVarchar10());
                dmsDocumentLog.append("เป็น :");
                dmsDocumentLog.append(dmsDocumentNew.getDmsDocumentVarchar10());

            }
        }

        return dmsDocumentLog.toString();

    }

    private String generateLogForCopyEntity(DmsDocument dmsDocument, int oldFolderId) {
//         DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่คัดลอก : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getCreatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่คัดลอก : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getCreatedDate()));
        dmsDocumentLog.append(" คัดลอกจาก : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(oldFolderId).getDmsFolderName(), ""));

        dmsDocumentLog.append(" ไป : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsDocument.getDmsFolderId()).getDmsFolderName(), ""));
        return dmsDocumentLog.toString();
    }

    private String generateLogForMoveEntity(DmsDocument dmsDocument, int oldFolderId) {
//         DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append(" ชื่อคนที่ย้าย : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getUpdatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่ย้าย : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getUpdatedDate()));
        dmsDocumentLog.append(" ย้ายจาก : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(oldFolderId).getDmsFolderName(), ""));
        dmsDocumentLog.append(" ไป : ");
        dmsDocumentLog.append(Common.noNull(dmsFolderService.getById(dmsDocument.getDmsFolderId()).getDmsFolderName(), ""));
        return dmsDocumentLog.toString();
    }

    private String generateLogForEditNameEntity(DmsDocument dmsDocument, String nameOld) {
//         DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//    DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(nameOld, ""));
        dmsDocumentLog.append("เป็น : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append(" ชื่อคนแก้ไข : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getUpdatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่แก้ไข : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getUpdatedDate()));

        return dmsDocumentLog.toString();
    }

    private String generateLogForEditTypeEntity(DmsDocument dmsDocument, int typeOld) {
        DocumentTypeService documentTypeService = new DocumentTypeService();
        UserProfileService userProfileService = new UserProfileService();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//    DmsFolderService dmsFolderService = new DmsFolderService();
        StringBuilder dmsDocumentLog = new StringBuilder();
        dmsDocumentLog.append("ชื่อเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(dmsDocument.getDmsDocumentName(), ""));
        dmsDocumentLog.append("ประเภทเอกสาร : ");
        dmsDocumentLog.append(Common.noNull(documentTypeService.getById(typeOld).getDocumentTypeName(), ""));
        dmsDocumentLog.append("เป็น : ");
        dmsDocumentLog.append(Common.noNull(documentTypeService.getById(dmsDocument.getDocumentTypeId()).getDocumentTypeName(), ""));
        dmsDocumentLog.append(" ชื่อคนแก้ไข : ");
        dmsDocumentLog.append(Common.noNull(userProfileService.getById(dmsDocument.getUpdatedBy()).getUserProfileFullName(), ""));
        dmsDocumentLog.append(" เวลาที่แก้ไข : ");
        dmsDocumentLog.append(Common.localDateTimeToString(dmsDocument.getUpdatedDate()));

        return dmsDocumentLog.toString();
    }

    public List<DmsDocument> listByWfId(int flowId) {
        return dmsDocumentDaoImpl.listByWfId(flowId);
    }

    public List<DmsDocument> listByFolderIdAndWfType(int folderId, int wfTypeId) {
        return dmsDocumentDaoImpl.listByFolderIdAndWfType(folderId, wfTypeId);
    }

    public String createDynamicReportDataFromSearch(String reportOutput, int folderId, int mode, String docTypeId) {//mode  1ปกติ 2หมดอายุ 3ค้นหา
        String result = "";
        try {
            ParamService paramService = new ParamService();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();
            UserProfileService userProfileService = new UserProfileService();
            String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
            String pathDocumentHttp = paramService.getByParamName("PATH_DOCUMENT_HTTP").getParamValue();

            int tempId = (int) (LocalDateTime.now().toLocalTime().toNanoOfDay() / 1000000);
            Random rand = new Random();
            int value = rand.nextInt(1000000);

            String reportPath = pathDocumentTemp + "searchReport" + tempId + value;
            pathDocumentHttp = pathDocumentHttp + "/Temp/" + "searchReport" + tempId + value;
            //--------Create dynamic report Data------------
            List reportData = new ArrayList();

            String preHighlightTag = "";
            String postHighlightTag = "";
//            SearchCCSearchResultModel searchResult = search(searchFormList,order,fetchSource,from,size,defaultSymbolForSpace,symbolAnd,symbolOr,symbolNot,symbolWith,preHighlightTag,postHighlightTag);
//            List<SearchCCModel> resultList = searchResult.getSearchResult();
            List<DmsDocument> listDmsDocument = new ArrayList<>();

            if (mode == 1) {
                listDmsDocument = DmsDocumentService.findListDocumentS(folderId, 0, 1000, "createdDate", "asc");
            }
            if (mode == 2) {
                listDmsDocument = DmsDocumentService.findListDocumentExpire(folderId);
            }
            if (mode == 3) {
                String[] docId = reportOutput.split("-");
                reportOutput = "XLS";
                for (String id : docId) {
                    DmsDocument temp = DmsDocumentService.getById(Integer.parseInt(id));

                    listDmsDocument.add(temp);
                }

//                listDmsDocument = DmsDocumentService.findListDocumentS(folderId, 0, 1000, "createdDate", "asc");
            }

//            String contentNo;
            //double orderNo = 0;
//            for(SearchCCModel seachCCModel:resultList){
//                contentNo = "";
//                for(NoModel noModel:seachCCModel.getContentNo()){
//                    if(contentNo.isEmpty()){
//                        contentNo = noModel.getDisplayNo();
//                    } else {
//                        contentNo+=","+noModel.getDisplayNo();
//                    }
//                }
//
//                Map<String, Object> map = new HashMap<>();
//                map.put("contentNo", seachCCModel.getContentType()+"ที่ "+contentNo);
//                map.put("title", seachCCModel.getTitle());
//
//                reportData.add(map);
//            }
            for (DmsDocument document : listDmsDocument) {

                Map<String, Object> map = new HashMap<>();
//                map.put("contentNo", "");
//                map.put("title", "");
                map.put("documentName", document.getDmsDocumentName());
                map.put("createdDate", Common.localDateTimeToString(document.getCreatedDate()));

                if (document.getCreatedBy() != 0 && document.getCreatedBy() != -1) {
                    map.put("createdBy", " ");
                    String userProfile = userProfileService.getById(document.getCreatedBy()).getUserProfileFullName();
                    map.put("createdBy", userProfile);
                } else {
                    map.put("createdBy", " ");
                }

                map.put("updatedDate", Common.localDateTimeToString(document.getUpdatedDate()));

                if (document.getUpdatedBy() != 0 && document.getUpdatedBy() != -1) {
                    String userProfile = userProfileService.getById(document.getUpdatedBy()).getUserProfileFullName();
                    map.put("updatedBy", userProfile);
                } else {
                    map.put("updatedBy", " ");
                }

                map.put("documentExpireDate", Common.localDateTimeToString(document.getDmsDocumentExpireDate()));

                map.put("documentDate01", Common.localDateTimeToString(document.getDmsDocumentDatetime01()));
                map.put("documentDate02", Common.localDateTimeToString(document.getDmsDocumentDatetime02()));
                map.put("documentDate03", Common.localDateTimeToString(document.getDmsDocumentDatetime03()));
                map.put("documentDate04", Common.localDateTimeToString(document.getDmsDocumentDatetime04()));

                map.put("documentFloat01", document.getDmsDocumentFloat01());
                map.put("documentFloat02", document.getDmsDocumentFloat02());

                map.put("documentInt01", document.getDmsDocumentInt01());
                map.put("documentInt02", document.getDmsDocumentInt02());
                map.put("documentInt03", document.getDmsDocumentInt03());
                map.put("documentInt04", document.getDmsDocumentInt04());
                map.put("dmsDocumentIntComma", document.getDmsDocumentIntComma());

                map.put("documentText01", document.getDmsDocumentText01());
                map.put("documentText02", document.getDmsDocumentText02());
                map.put("documentText03", document.getDmsDocumentText03());
                map.put("documentText04", document.getDmsDocumentText04());
                map.put("documentText05", document.getDmsDocumentText05());

                map.put("documentVarchar01", document.getDmsDocumentVarchar01());
                map.put("documentVarchar02", document.getDmsDocumentVarchar02());
                map.put("documentVarchar03", document.getDmsDocumentVarchar03());
                map.put("documentVarchar04", document.getDmsDocumentVarchar04());
                map.put("documentVarchar05", document.getDmsDocumentVarchar05());
                map.put("documentVarchar06", document.getDmsDocumentVarchar06());
                map.put("documentVarchar07", document.getDmsDocumentVarchar07());
                map.put("documentVarchar08", document.getDmsDocumentVarchar08());
                map.put("documentVarchar09", document.getDmsDocumentVarchar09());
                map.put("documentVarchar10", document.getDmsDocumentVarchar10());

                reportData.add(map);
            }

            //------------- Setup Report -----------------
            String reportName = "รายงานเอกสาร";
            if (mode == 2) {
                reportName = "รายงานเอกสารหมดอายุ";
            }
            if (mode == 3) {
                reportName = "รายงานเอกสารผลการค้นหา";
            }

            String fontName = "THSarabun";
            String pdfFontName = "THSarabun.ttf";
            int detailSize = 12;
            int margin = 20;

            Page p = Page.Page_A4_Landscape();
            p.setOrientationPortrait(false);

            Font titleFont = new Font();
            titleFont.setFontName(fontName);
            titleFont.setFontSize(18);
            titleFont.setBold(true);
            titleFont.setItalic(false);
            titleFont.setUnderline(true);
            titleFont.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
            titleFont.setPdfFontEmbedded(true);
            titleFont.setPdfFontName(pdfFontName);
            Style titleStyle = new Style("titleStyle");
            titleStyle.setFont(titleFont);
            titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            titleStyle.setPaddingBottom(5);

            Font subtitleFont = new Font();
            subtitleFont.setFontName(fontName);
            subtitleFont.setFontSize(16);
            subtitleFont.setBold(true);
            subtitleFont.setItalic(false);
            subtitleFont.setUnderline(true);
            subtitleFont.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
            subtitleFont.setPdfFontEmbedded(true);
            subtitleFont.setPdfFontName(pdfFontName);
            Style subtitleStyle = new Style("subtitleStyle");
            subtitleStyle.setFont(subtitleFont);
            subtitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            subtitleStyle.setPaddingBottom(5);
            subtitleStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            subtitleStyle.setTransparency(Transparency.OPAQUE);

            Font headerFont = new Font();
            headerFont.setFontName(fontName);
            headerFont.setFontSize(detailSize);
            headerFont.setBold(false);
            headerFont.setItalic(false);
            headerFont.setUnderline(false);
            headerFont.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
            headerFont.setPdfFontEmbedded(true);
            headerFont.setPdfFontName(pdfFontName);
            Style headerStyle = new Style();
            headerStyle.setFont(headerFont);
            headerStyle.setBorder(Border.THIN());
            headerStyle.setBackgroundColor(Color.lightGray);
            //headerStyle.setTextColor(Color.white);
            headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            headerStyle.setTransparency(Transparency.OPAQUE);

            Font groupFont = new Font();
            groupFont.setFontName(fontName);
            groupFont.setFontSize(detailSize);
            groupFont.setBold(true);
            groupFont.setItalic(false);
            groupFont.setUnderline(false);
            groupFont.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
            groupFont.setPdfFontEmbedded(true);
            groupFont.setPdfFontName(pdfFontName);
            Style groupStyle = new Style();
            groupStyle.setFont(groupFont);
            groupStyle.setBorder(Border.THIN());
            groupStyle.setBackgroundColor(Color.gray);
            groupStyle.setTextColor(Color.white);
            groupStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            groupStyle.setTransparency(Transparency.OPAQUE);

            Font detailFont = new Font();
            detailFont.setFontName(fontName);
            detailFont.setFontSize(detailSize);
            detailFont.setBold(false);
            detailFont.setItalic(false);
            detailFont.setUnderline(false);
            detailFont.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
            detailFont.setPdfFontEmbedded(true);
            detailFont.setPdfFontName(pdfFontName);
            Style detailStyle = new Style();
            detailStyle.setFont(detailFont);
            detailStyle.setBorder(Border.THIN());
            detailStyle.setVerticalAlign(VerticalAlign.TOP);

            Style detailCenterStyle = new Style();
            detailCenterStyle.setFont(detailFont);
            detailCenterStyle.setBorder(Border.THIN());
            detailCenterStyle.setVerticalAlign(VerticalAlign.TOP);
            detailCenterStyle.setHorizontalAlign(HorizontalAlign.CENTER);

            Style detailRightStyle = new Style();
            detailRightStyle.setFont(detailFont);
            detailRightStyle.setBorder(Border.THIN());
            detailRightStyle.setVerticalAlign(VerticalAlign.TOP);
            detailRightStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

            Style oddRowStyle = new Style();
            oddRowStyle.setBorder(Border.NO_BORDER());
            oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
            oddRowStyle.setTransparency(Transparency.OPAQUE);

            FastReportBuilder drb = new FastReportBuilder();

            drb.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                    .setTitle(reportName)
                    .setTitleStyle(titleStyle)
                    .setDetailHeight(15)
                    .setLeftMargin(margin)
                    .setRightMargin(margin)
                    .setTopMargin(margin)
                    .setBottomMargin(margin)
                    .setPageSizeAndOrientation(p)
                    .setPrintBackgroundOnOddRows(false)
                    .setOddRowBackgroundStyle(oddRowStyle)
                    .setAllowDetailSplit(false)
                    .setPrintColumnNames(true)
                    .setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");

            //-----------------Manage Value---------------- เลื่อก field
//            drb.addColumn("เลขที่", "contentNo", String.class.getName(), 7, detailStyle, headerStyle);
//            drb.addColumn("ชื่อเรื่อง", "title", String.class.getName(), 43, detailStyle, headerStyle);
            String[] docDetailId = docTypeId.split("-");
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            DocumentTypeDetail tempDocumentTypeDetail = new DocumentTypeDetail();

            DmsFieldService dmsFieldService = new DmsFieldService();

            for (String id : docDetailId) {
                tempDocumentTypeDetail = documentTypeDetailService.getById(Integer.parseInt(id));
                String field = tempDocumentTypeDetail.getDocumentTypeDetailName();
                String map = dmsFieldService.getById(tempDocumentTypeDetail.getDmsFieldId()).getDmsFieldMap();
                drb.addColumn(field, map, String.class.getName(), 7, detailStyle, headerStyle);

            }

            drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_RIGHT);
            drb.setUseFullPageWidth(true); //make colums to fill the page width  

            DynamicReport dr = drb.build();
            JRDataSource dataSource = new JRBeanCollectionDataSource(reportData);

            JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), dataSource);

            if (reportOutput.equalsIgnoreCase("XLS")) {
                //---------------------------- Excel --------------------------------------------------------
                JRXlsExporter exporter = new JRXlsExporter();

                exporter.setExporterInput(new SimpleExporterInput(jp));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportPath + ".xls"));

                SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
                xlsReportConfiguration.setOnePagePerSheet(false);
                xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
                //xlsReportConfiguration.setDetectCellType(true);
                xlsReportConfiguration.setWhitePageBackground(false);
                exporter.setConfiguration(xlsReportConfiguration);

                exporter.exportReport();

//                result = reportPath + ".xls";
                result = pathDocumentHttp + ".xls";
            } else {
                //---------------------------- PDF --------------------------------------------------------
                byte[] jasperByte = JasperExportManager.exportReportToPdf(jp);
                FileUtils.writeByteArrayToFile(new File(reportPath + ".pdf"), jasperByte);
//                result = reportPath + ".pdf";
                 result = pathDocumentHttp + ".pdf";
            }

        } catch (Exception e) {
            System.out.println("Exception:DynamicReportDataFromSearch = " + e);
        }

        return result;
    }
    
    
    public String changDocumntToSearchField(DmsDocument document) {
        String returnData = "";
        returnData = returnData + document.getDmsDocumentName()+" ";

        returnData = returnData + document.getDmsDocumentFloat01()+" ";
        returnData = returnData + document.getDmsDocumentFloat02()+" ";

        returnData = returnData + document.getDmsDocumentVarchar01()+" ";
        returnData = returnData + document.getDmsDocumentVarchar02()+" ";
        returnData = returnData + document.getDmsDocumentVarchar03()+" ";
        returnData = returnData + document.getDmsDocumentVarchar04()+" ";
        returnData = returnData + document.getDmsDocumentVarchar05()+" ";
        returnData = returnData + document.getDmsDocumentVarchar06()+" ";
        returnData = returnData + document.getDmsDocumentVarchar07()+" ";
        returnData = returnData + document.getDmsDocumentVarchar08()+" ";
        returnData = returnData + document.getDmsDocumentVarchar09()+" ";
        returnData = returnData + document.getDmsDocumentVarchar10()+" ";


        returnData = returnData + document.getDmsDocumentText01()+" ";
        returnData = returnData + document.getDmsDocumentText02()+" ";
        returnData = returnData + document.getDmsDocumentText03()+" ";
        returnData = returnData + document.getDmsDocumentText04()+" ";
        returnData = returnData + document.getDmsDocumentText05()+" ";


        returnData = returnData + document.getDmsDocumentInt01()+" ";
        returnData = returnData + document.getDmsDocumentInt02()+" ";
        returnData = returnData + document.getDmsDocumentInt03()+" ";
        returnData = returnData + document.getDmsDocumentInt04()+" ";


        UserProfileService userProfileService = new UserProfileService();
        UserProfile userProfile = new UserProfile();

        if (document.getCreatedBy() > 0) {
            userProfile = userProfileService.getById(document.getCreatedBy());
             returnData = returnData + userProfile.getUserProfileFullName();
        }
        if (document.getUpdatedBy() > 0) {
            userProfile = userProfileService.getById(document.getUpdatedBy());
            returnData = returnData + userProfile.getUserProfileFullName();
        }

        return returnData;

    }

}
