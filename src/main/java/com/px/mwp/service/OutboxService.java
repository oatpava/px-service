package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.entity.LogData;
import com.px.admin.entity.Module;
import com.px.share.service.LogDataService;
import com.px.admin.service.ModuleService;
import com.px.mwp.daoimpl.OutboxDaoImpl;
import com.px.mwp.entity.Outbox;
import com.px.mwp.entity.Workflow;
import com.px.mwp.entity.WorkflowTo;
import com.px.mwp.model.OutboxModel;
import com.px.mwp.model.OutboxSearchModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Mali
 */
public class OutboxService implements GenericService<Outbox, OutboxModel> {

    private static final Logger LOG = Logger.getLogger(OutboxService.class.getName());
    private final OutboxDaoImpl outboxDaoImpl;

    @Context
    HttpHeaders httpHeaders;

    public OutboxService() {
        this.outboxDaoImpl = new OutboxDaoImpl();
    }

    @Override
    public Outbox create(Outbox outbox) {
        checkNotNull(outbox, "outbox entity must not be null");
        checkNotNull(outbox.getCreatedBy(), "create by must not be null");
        checkNotNull(outbox.getUserProfileFolderId(), "UserProfileFolderId by must not be null");
        checkNotNull(outbox.getStructureFolderId(), "StructureFolderId by must not be null");
        checkNotNull(outbox.getOutboxReceiveFlag(), "OutboxReceiveFlag by must not be null");
        checkNotNull(outbox.getOutboxOpenFlag(), "OutboxOpenFlag by must not be null");
        checkNotNull(outbox.getOutboxActionFlag(), "OutnboxActionFlag by must not be null");
        checkNotNull(outbox.getOutboxFinishFlag(), "OutboxFinishFlag by must not be null");
        checkNotNull(outbox.getLetterStatus1(), "LetterStatus1 by must not be null");
        checkNotNull(outbox.getLetterStatus2(), "LetterStatus2 by must not be null");
        checkNotNull(outbox.getLetterStatus3(), "LetterStatus3 by must not be null");
        checkNotNull(outbox.getModuleId(), "ModuleId by must not be null");
        checkNotNull(outbox.getLinkId(), "LinkId by must not be null");
        checkNotNull(outbox.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(outbox.getWorkflowId(), "WorkflowId by must not be null");
        checkNotNull(outbox.getOutboxSendDate(), "OutboxSendDate by must not be null");
        checkNotNull(outbox.getOutboxApprove(), "OutboxApprove by must not be null");
        return outboxDaoImpl.create(outbox);
    }

    @Override
    public Outbox getById(int id) {
        return outboxDaoImpl.getById(id);
    }

    @Override
    public Outbox update(Outbox outbox) {
        checkNotNull(outbox, "outbox entity must not be null");
        checkNotNull(outbox.getUpdatedBy(), "update by must not be null");
        checkNotNull(outbox.getUserProfileFolderId(), "UserProfileFolderId by must not be null");
        checkNotNull(outbox.getStructureFolderId(), "StructureFolderId by must not be null");
        checkNotNull(outbox.getOutboxReceiveFlag(), "OutboxReceiveFlag by must not be null");
        checkNotNull(outbox.getOutboxOpenFlag(), "OutboxOpenFlag by must not be null");
        checkNotNull(outbox.getOutboxActionFlag(), "OutnboxActionFlag by must not be null");
        checkNotNull(outbox.getOutboxFinishFlag(), "OutboxFinishFlag by must not be null");
        checkNotNull(outbox.getLetterStatus1(), "LetterStatus1 by must not be null");
        checkNotNull(outbox.getLetterStatus2(), "LetterStatus2 by must not be null");
        checkNotNull(outbox.getLetterStatus3(), "LetterStatus3 by must not be null");
        checkNotNull(outbox.getModuleId(), "ModuleId by must not be null");
        checkNotNull(outbox.getLinkId(), "LinkId by must not be null");
        checkNotNull(outbox.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(outbox.getWorkflowId(), "WorkflowId by must not be null");
        checkNotNull(outbox.getOutboxSendDate(), "OutboxSendDate by must not be null");
        checkNotNull(outbox.getOutboxApprove(), "OutboxApprove by must not be null");
        outbox.setUpdatedDate(LocalDateTime.now());
        return outboxDaoImpl.update(outbox);
    }

    @Override
    public Outbox remove(int id, int userId) {
        checkNotNull(id, "outbox id must not be null");
        Outbox outbox = getById(id);
        checkNotNull(outbox, "outbox entity not found in database.");
        outbox.setRemovedBy(userId);
        outbox.setRemovedDate(LocalDateTime.now());
        return outboxDaoImpl.update(outbox);
    }

    public void delete(Outbox outbox) {
        checkNotNull(outbox, "outbox must not be null");
        outboxDaoImpl.delete(outbox);
    }

    @Override
    public List<Outbox> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Outbox> listByUserProfileFolderId(int userProfileFolderId, int limit, int offset, String sort, String dir) {
        checkNotNull(userProfileFolderId, "userProfileFolderId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listByUserProfileFolderId(userProfileFolderId, offset, limit, sort, dir);
    }

    public Integer countListByUserProfileFolderId(int userProfileFolderId) {
        checkNotNull(userProfileFolderId, "userProfileFolderId must not be null");
        return outboxDaoImpl.countListByUserProfileFolderId(userProfileFolderId);
    }

    public List<Outbox> listByStructureFolderId(List<Integer> listStructureFolderId, int limit, int offset, String sort, String dir) {
        checkNotNull(listStructureFolderId, "listStructureFolderId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listByStructureFolderId(listStructureFolderId, offset, limit, sort, dir);
    }

    public List<Outbox> listByUserProfileFolderIdAll(List<Integer> listUserProfileFolderId, String sort, String dir) {
        checkNotNull(listUserProfileFolderId, "listUserProfileFolderId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listByUserProfileFolderIdAll(listUserProfileFolderId, sort, dir);
    }

    public List<Outbox> listByStructureFolderIdAll(List<Integer> listStructureFolderId, String sort, String dir) {
        checkNotNull(listStructureFolderId, "listStructureFolderId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listByStructureFolderIdAll(listStructureFolderId, sort, dir);
    }

    @Override
    public List<Outbox> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return outboxDaoImpl.countAll();
    }

    @Override
    public OutboxModel tranformToModel(Outbox outbox) {
        OutboxModel outboxModel = null;
        if (outbox != null) {
            outboxModel = new OutboxModel();
            outboxModel.setId(outbox.getId());
            outboxModel.setCreatedBy(outbox.getCreatedBy());
            outboxModel.setCreatedDate(Common.localDateTimeToString(outbox.getCreatedDate()));
            outboxModel.setOrderNo((float) outbox.getOrderNo());
            outboxModel.setUpdatedBy(outbox.getUpdatedBy());
            outboxModel.setUpdatedDate(Common.localDateTimeToString(outbox.getUpdatedDate()));
            outboxModel.setRemovedBy(outbox.getRemovedBy());
            outboxModel.setRemovedDate(Common.localDateTimeToString(outbox.getRemovedDate()));
            outboxModel.setUserProfileFolderId(outbox.getUserProfileFolderId());
            outboxModel.setStructureFolderId(outbox.getStructureFolderId());
            outboxModel.setOutboxFrom(outbox.getOutboxFrom());
            outboxModel.setOutboxTo(outbox.getOutboxTo());
            outboxModel.setOutboxTitle(outbox.getOutboxTitle());
            outboxModel.setOutboxSendDate(Common.localDateTimeToString2(outbox.getOutboxSendDate()));////
            outboxModel.setOutboxOpenDate(Common.localDateTimeToString(outbox.getOutboxOpenDate()));
            outboxModel.setOutboxOpenDateDefine(Common.localDateTimeToString(outbox.getOutboxOpenDateDefine()));
            outboxModel.setOutboxOpenFlag(outbox.getOutboxOpenFlag());
            outboxModel.setOutboxActionDate(Common.localDateTimeToString(outbox.getOutboxActionDate()));
            outboxModel.setOutboxActionDateDefine(Common.localDateTimeToString(outbox.getOutboxActionDateDefine()));
            outboxModel.setOutboxActionFlag(outbox.getOutboxActionFlag());
            outboxModel.setOutboxReceiveDate(Common.localDateTimeToString(outbox.getOutboxReceiveDate()));
            outboxModel.setOutboxReceiveDateDefine(Common.localDateTimeToString(outbox.getOutboxReceiveDateDefine()));
            outboxModel.setOutboxReceiveFlag(outbox.getOutboxReceiveFlag());
            outboxModel.setOutboxFinishDate(Common.localDateTimeToString(outbox.getOutboxFinishDate()));
            outboxModel.setOutboxFinishDateDefine(Common.localDateTimeToString(outbox.getOutboxFinishDateDefine()));
            outboxModel.setOutboxFinishFlag(outbox.getOutboxFinishFlag());
            outboxModel.setLetterStatus1(outbox.getLetterStatus1());
            outboxModel.setLetterStatus2(outbox.getLetterStatus2());
            outboxModel.setLetterStatus3(outbox.getLetterStatus3());
            outboxModel.setModuleId(outbox.getModuleId());
            outboxModel.setLinkId(outbox.getLinkId());
            outboxModel.setLinkId2(outbox.getLinkId2());
            outboxModel.setLinkType(outbox.getLinkType());
            outboxModel.setOutboxStr01(outbox.getOutboxStr01());
            outboxModel.setOutboxStr02(outbox.getOutboxStr02());
            outboxModel.setOutboxStr03(outbox.getOutboxStr03());
            outboxModel.setOutboxStr04(outbox.getOutboxStr04());
            outboxModel.setOutboxDate01(Common.localDateTimeToString(outbox.getOutboxDate01()));
            outboxModel.setOutboxDate02(Common.localDateTimeToString(outbox.getOutboxDate02()));
            outboxModel.setOutboxDescription(outbox.getOutboxDescription());
            outboxModel.setOutboxNote(outbox.getOutboxNote());
            outboxModel.setWorkflowId(outbox.getWorkflowId());
            outboxModel.setOutboxApprove(outbox.getOutboxApprove());
        }
        return outboxModel;
    }

    public OutboxModel tranformToModel_cc(Outbox outbox) {
        OutboxModel outboxModel = null;
        if (outbox != null) {
            outboxModel = new OutboxModel();
            outboxModel.setId(outbox.getId());
            outboxModel.setCreatedBy(outbox.getCreatedBy());
            outboxModel.setCreatedDate(Common.localDateTimeToString(outbox.getCreatedDate()));
            outboxModel.setOrderNo((float) outbox.getOrderNo());
            outboxModel.setUpdatedBy(outbox.getUpdatedBy());
            outboxModel.setUpdatedDate(Common.localDateTimeToString(outbox.getUpdatedDate()));
            outboxModel.setRemovedBy(outbox.getRemovedBy());
            outboxModel.setRemovedDate(Common.localDateTimeToString(outbox.getRemovedDate()));
            outboxModel.setUserProfileFolderId(outbox.getUserProfileFolderId());
            outboxModel.setStructureFolderId(outbox.getStructureFolderId());
            outboxModel.setOutboxFrom(outbox.getOutboxFrom());
            outboxModel.setOutboxTo(outbox.getOutboxTo());
            outboxModel.setOutboxTitle(outbox.getOutboxTitle());
            outboxModel.setOutboxSendDate(Common.localDateTimeToString2(outbox.getOutboxSendDate()));////
            outboxModel.setOutboxOpenDate(Common.localDateTimeToString(outbox.getOutboxOpenDate()));
            outboxModel.setOutboxOpenDateDefine(Common.localDateTimeToString(outbox.getOutboxOpenDateDefine()));
            outboxModel.setOutboxOpenFlag(outbox.getOutboxOpenFlag());
            outboxModel.setOutboxActionDate(Common.localDateTimeToString(outbox.getOutboxActionDate()));
            outboxModel.setOutboxActionDateDefine(Common.localDateTimeToString(outbox.getOutboxActionDateDefine()));
            outboxModel.setOutboxActionFlag(outbox.getOutboxActionFlag());
            outboxModel.setOutboxReceiveDate(Common.localDateTimeToString(outbox.getOutboxReceiveDate()));
            outboxModel.setOutboxReceiveDateDefine(Common.localDateTimeToString(outbox.getOutboxReceiveDateDefine()));
            outboxModel.setOutboxReceiveFlag(outbox.getOutboxReceiveFlag());
            outboxModel.setOutboxFinishDate(Common.localDateTimeToString(outbox.getOutboxFinishDate()));
            outboxModel.setOutboxFinishDateDefine(Common.localDateTimeToString(outbox.getOutboxFinishDateDefine()));
            outboxModel.setOutboxFinishFlag(outbox.getOutboxFinishFlag());
            outboxModel.setLetterStatus1(outbox.getLetterStatus1());
            outboxModel.setLetterStatus2(outbox.getLetterStatus2());
            outboxModel.setLetterStatus3(outbox.getLetterStatus3());
            outboxModel.setModuleId(outbox.getModuleId());
            outboxModel.setLinkId(outbox.getLinkId());
            outboxModel.setLinkId2(outbox.getLinkId2());
            outboxModel.setLinkType(outbox.getLinkType());
            outboxModel.setOutboxStr01(outbox.getOutboxStr01());
            outboxModel.setOutboxStr02(outbox.getOutboxStr02());
            outboxModel.setOutboxStr03(outbox.getOutboxStr03());
            outboxModel.setOutboxStr04(outbox.getOutboxStr04());
            outboxModel.setOutboxDate01(Common.localDateTimeToString(outbox.getOutboxDate01()));
            outboxModel.setOutboxDate02(Common.localDateTimeToString(outbox.getOutboxDate02()));
            outboxModel.setOutboxDescription(outbox.getOutboxDescription());
            outboxModel.setOutboxNote(outbox.getOutboxNote());
            outboxModel.setWorkflowId(outbox.getWorkflowId());
            outboxModel.setOutboxApprove(outbox.getOutboxApprove());

            outboxModel.setIsCanceled(false);
            WorkflowService workflowservice = new WorkflowService();
            Workflow workflowCancelAll = workflowservice.getByLinkIdAndLinkId3(outbox.getLinkId(), 1, "C");
            if (workflowCancelAll != null) {
                outboxModel.setIsCanceled(true);
            } else {
                Workflow workflowCancel = workflowservice.getByLinkIdAndLinkId2(outbox.getLinkId(), outbox.getLinkId2(), "C");
                if (workflowCancel != null) {
                    outboxModel.setIsCanceled(true);
                }
            }

            outboxModel.setOutboxSpeed(outbox.getOutboxSpeed());
        }
        return outboxModel;
    }

    @Override
    public List<Outbox> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Outbox> searchOutboxUser(MultivaluedMap<String, String> queryParams, List<Integer> listUserProfileFolderId, int offset, int limit, String sort, String dir) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(listUserProfileFolderId, "listUserProfileFolderId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.searchOutboxUser(queryParams, listUserProfileFolderId, offset, limit, sort, dir);
    }

    public List<Outbox> searchOutboxStructure(MultivaluedMap<String, String> queryParams, List<Integer> listStructureId, int offset, int limit, String sort, String dir) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(listStructureId, "listStructureId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.searchOutboxStructure(queryParams, listStructureId, offset, limit, sort, dir);
    }

    public List<Outbox> searchOutboxBin(MultivaluedMap<String, String> queryParams, int userID, int offset, int limit, String sort, String dir) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(userID, "userID must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.searchOutboxBin(queryParams, userID, offset, limit, sort, dir);
    }

    public int countSearchOutboxUser(MultivaluedMap<String, String> queryParams, List<Integer> listUserProfileFolderId) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(listUserProfileFolderId, "listUserProfileFolderId must not be null");
        return outboxDaoImpl.countSearchOutboxUser(queryParams, listUserProfileFolderId);
    }

    public int countSearchOutboxStructure(MultivaluedMap<String, String> queryParams, List<Integer> listStructureId) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(listStructureId, "listStructureId must not be null");
        return outboxDaoImpl.countSearchOutboxStructure(queryParams, listStructureId);
    }

    public int countSearchOutboxBin(MultivaluedMap<String, String> queryParams, int userID) {
        checkNotNull(queryParams, "queryParams entity must not be null");
        checkNotNull(userID, "userID must not be null");
        return outboxDaoImpl.countSearchOutboxBin(queryParams, userID);
    }

    public Outbox getByWorkflowId(int workflowId) {
        checkNotNull(workflowId, "workflowId entity must not be null");
        return outboxDaoImpl.getByWorkflowId(workflowId);
    }

    @Override
    public Outbox getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Outbox saveLogForRemove(Outbox outbox, String clientIp) {
        //For Create Log when remove Outbox
        String logDescription = this.generateLogForRemoveEntity(outbox);
        LogData logData = new LogData();
        logData.setCreatedBy(outbox.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(outbox.getClass().getName());
        logData.setLinkId(outbox.getId());
        logData.setModuleName(LogData.MODULE_MWP);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return outbox;
    }

    private String generateLogForRemoveEntity(Outbox outbox) {
        ModuleService moduleService = new ModuleService();
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("หนังสือออก : ");
        descriptionLog.append("ลบรายการ[");
        Module module = moduleService.getById(outbox.getModuleId());
        descriptionLog.append(Common.noNull(module.getModuleName(), ""));
        descriptionLog.append("] ");

        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(outbox.getOutboxTitle(), ""));

        return descriptionLog.toString();
    }

//    public Integer checkLetterStatus(Outbox outbox, int type) {
//        int letterStatus = 0;
//
//        if (type == 1) {
//            if (outbox.getOutboxOpenFlag() == 1) {
//                if (outbox.getOutboxOpenDateDefine() != null) {
//
//                    LocalDateTime outboxOpenDate = outbox.getOutboxOpenDate();
//                    outboxOpenDate.withHour(0);
//                    outboxOpenDate.withMinute(0);
//                    outboxOpenDate.withSecond(0);
//                    outboxOpenDate.withNano(0);
//
//                    LocalDateTime outboxOpenDateDefine = outbox.getOutboxOpenDateDefine();
//
//                    if (outboxOpenDate.isAfter(outboxOpenDateDefine)) {
//                        letterStatus = 4;
//                    } else {
//                        letterStatus = 3;
//                    }
//                } else {
//                    letterStatus = 3;
//                }
//            } else if (outbox.getOutboxOpenFlag() == 0) {
//                if (outbox.getOutboxOpenDateDefine() != null) {
//
//                    LocalDateTime currentDateTime = LocalDateTime.now();
//                    currentDateTime.withHour(0);
//                    currentDateTime.withMinute(0);
//                    currentDateTime.withSecond(0);
//                    currentDateTime.withNano(0);
//
//                    LocalDateTime outboxOpenDateDefine = outbox.getOutboxOpenDateDefine();
//
//                    LocalDateTime tmpOutboxOpenDate = outbox.getOutboxOpenDateDefine();
//                    tmpOutboxOpenDate.minusDays(1);
//
//                    if (currentDateTime.equals(tmpOutboxOpenDate) || currentDateTime.equals(outboxOpenDateDefine)) {
//                        letterStatus = 1;
//                    }
//                    if (currentDateTime.isAfter(outboxOpenDateDefine)) {
//                        letterStatus = 2;
//                    }
//                } else {
//                    letterStatus = 0;
//                }
//            }
//        } else if (type == 2) {
//            if (outbox.getOutboxActionFlag() == 1) {
//                if (outbox.getOutboxActionDateDefine() != null) {
//
//                    LocalDateTime outboxActionDate = outbox.getOutboxActionDate();
//                    outboxActionDate.withHour(0);
//                    outboxActionDate.withMinute(0);
//                    outboxActionDate.withSecond(0);
//                    outboxActionDate.withNano(0);
//
//                    LocalDateTime outboxActionDateDefine = outbox.getOutboxActionDateDefine();
//
//                    if (outboxActionDate.isAfter(outboxActionDateDefine)) {
//                        letterStatus = 4;
//                    } else {
//                        letterStatus = 3;
//                    }
//                } else {
//                    letterStatus = 3;
//                }
//            } else if (outbox.getOutboxActionFlag() == 0) {
//                if (outbox.getOutboxActionDateDefine() != null) {
//
//                    LocalDateTime currentDateTime = LocalDateTime.now();
//                    currentDateTime.withHour(0);
//                    currentDateTime.withMinute(0);
//                    currentDateTime.withSecond(0);
//                    currentDateTime.withNano(0);
//
//                    LocalDateTime outboxActionDateDefine = outbox.getOutboxActionDateDefine();
//
//                    LocalDateTime tmpOutboxActionDate = outbox.getOutboxActionDateDefine();
//                    tmpOutboxActionDate.minusDays(1);
//
//                    if (currentDateTime.equals(tmpOutboxActionDate) || currentDateTime.equals(outboxActionDateDefine)) {
//                        letterStatus = 1;
//                    }
//                    if (currentDateTime.isAfter(outboxActionDateDefine)) {
//                        letterStatus = 2;
//                    }
//                } else {
//                    letterStatus = 0;
//                }
//            }
//        } else if (type == 3) {
//            if (outbox.getOutboxFinishFlag() == 1) {
//                if (outbox.getOutboxFinishDateDefine() != null) {
//
//                    LocalDateTime outboxFinishDate = outbox.getOutboxFinishDate();
//                    outboxFinishDate.withHour(0);
//                    outboxFinishDate.withMinute(0);
//                    outboxFinishDate.withSecond(0);
//                    outboxFinishDate.withNano(0);
//
//                    LocalDateTime outboxFinishDateDefine = outbox.getOutboxFinishDateDefine();
//
//                    if (outboxFinishDate.isAfter(outboxFinishDateDefine)) {
//                        letterStatus = 4;
//                    } else {
//                        letterStatus = 3;
//                    }
//                } else {
//                    letterStatus = 3;
//                }
//            } else if (outbox.getOutboxFinishFlag() == 0) {
//                if (outbox.getOutboxFinishDateDefine() != null) {
//
//                    LocalDateTime currentDateTime = LocalDateTime.now();
//                    currentDateTime.withHour(0);
//                    currentDateTime.withMinute(0);
//                    currentDateTime.withSecond(0);
//                    currentDateTime.withNano(0);
//
//                    LocalDateTime outboxFinishDateDefine = outbox.getOutboxFinishDateDefine();
//
//                    LocalDateTime tmpOutboxFinishDate = outbox.getOutboxFinishDateDefine();
//                    tmpOutboxFinishDate.minusDays(1);
//
//                    if (currentDateTime.equals(tmpOutboxFinishDate) || currentDateTime.equals(outboxFinishDateDefine)) {
//                        letterStatus = 1;
//                    }
//                    if (currentDateTime.isAfter(outboxFinishDateDefine)) {
//                        letterStatus = 2;
//                    }
//                } else {
//                    letterStatus = 0;
//                }
//            }
//        }
//
//        return letterStatus;
//    }
    //oat-add
    public List<Outbox> listByLinkId(int linkId, String sort, String dir) {
        checkNotNull(linkId, "linkId must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return outboxDaoImpl.listByLinkId(linkId, sort, dir);
    }

    public List<Outbox> searchUserOutboxByModel(int userFolderId, OutboxSearchModel outboxsearchModel, String sort, String dir) {
        checkNotNull(userFolderId, "userFolderId must not be null");
        checkNotNull(outboxsearchModel, "outboxsearchModel entity must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
//        List<Outbox> listOutbox = new ArrayList<>();
//        List<Outbox> listSearchResult = outboxDaoImpl.searchUserOutboxByModel(userFolderId, outboxsearchModel, sort, dir);
//        String outboxTo = outboxsearchModel.getOutboxTo();
//        if (outboxTo != null && outboxTo != "") {
//            List<WorkflowTo> listWorkflowTo = new WorkflowToService().listByName(Integer.parseInt(httpHeaders.getHeaderString("userID")), outboxTo);
//            if (!listWorkflowTo.isEmpty()) {
//                for(Outbox outbox : listSearchResult) {
//                    for(WorkflowTo workflowTo: listWorkflowTo) {
//                        if (outbox.getWorkflowId() == workflowTo.getWorkflow().getId()) {
//                            listOutbox.add(outbox);
//                            break;
//                        }
//                    }
//                }
//            }
//        } else {
//            listOutbox = listSearchResult;
//        }
//        return listOutbox;
        return outboxDaoImpl.searchUserOutboxByModel(userFolderId, outboxsearchModel, sort, dir);
    }

    public String getDateRange(String tmp) {
        String day = tmp.substring(0, 2);
        String month = tmp.substring(3, 5);
        String year = tmp.substring(6);

        switch (month) {
            case "01":
                month = "มกราคม";
                break;
            case "02":
                month = "กุมภาพันธ์";
                break;
            case "03":
                month = "มีนาคม";
                break;
            case "04":
                month = "เมษายน";
                break;
            case "05":
                month = "พฤษภาคม";
                break;
            case "06":
                month = "มิถุนายน";
                break;
            case "07":
                month = "กรกฎาคม";
                break;
            case "08":
                month = "สิงหาคม";
                break;
            case "09":
                month = "กันยายน";
                break;
            case "10":
                month = "ตุลาคม";
                break;
            case "11":
                month = "พฤศจิกายน";
                break;
            case "12":
                month = "ธันวาคม";
                break;
        }
        return day + " " + month + " " + year;
    }

}
