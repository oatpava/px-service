package com.px.mwp.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.entity.DigitalKey;
import com.px.share.entity.LogData;
import com.px.admin.entity.Module;
import com.px.share.service.LogDataService;
import com.px.admin.service.ModuleService;
import com.px.mwp.daoimpl.InboxDaoImpl;
import com.px.mwp.entity.Inbox;
import com.px.mwp.entity.Workflow;
import com.px.mwp.model.InboxModel;
import com.px.mwp.model.InboxSearchModel;
import com.px.share.service.GenericService;
import com.px.share.util.Common;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author Oat
 */
public class InboxService implements GenericService<Inbox, InboxModel> {

    private static final Logger LOG = Logger.getLogger(InboxService.class.getName());
    private final InboxDaoImpl InboxDaoImpl;

    public InboxService() {
        this.InboxDaoImpl = new InboxDaoImpl();
    }

    @Override
    public Inbox create(Inbox inbox) {
        checkNotNull(inbox, "inbox entity must not be null");
        checkNotNull(inbox.getCreatedBy(), "create by must not be null");
        checkNotNull(inbox.getUserProfileFolderId(), "UserProfileFolderId by must not be null");
        checkNotNull(inbox.getStructureFolderId(), "StructureFolderId by must not be null");
        checkNotNull(inbox.getInboxReceiveFlag(), "InboxReceiveFlag by must not be null");
        checkNotNull(inbox.getInboxOpenFlag(), "InboxOpenFlag by must not be null");
        checkNotNull(inbox.getInboxActionFlag(), "InboxActionFlag by must not be null");
        checkNotNull(inbox.getInboxFinishFlag(), "InboxFinishFlag by must not be null");
        checkNotNull(inbox.getLetterStatus1(), "LetterStatus1 by must not be null");
        checkNotNull(inbox.getLetterStatus2(), "LetterStatus2 by must not be null");
        checkNotNull(inbox.getLetterStatus3(), "LetterStatus3 by must not be null");
        checkNotNull(inbox.getModuleId(), "ModuleId by must not be null");
        checkNotNull(inbox.getLinkId(), "LinkId by must not be null");
        checkNotNull(inbox.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(inbox.getWorkflowId(), "WorkflowId by must not be null");
        checkNotNull(inbox.getInboxSendDate(), "InboxSendDate by must not be null");
        checkNotNull(inbox.getInboxApprove(), "InboxApprove by must not be null");
        checkNotNull(inbox.getInboxApproveStatus(), "InboxApproveStatus by must not be null");
        if ((inbox.getInboxKey() != null) && (!inbox.getInboxKey().equalsIgnoreCase("0"))) {
            DigitalKey digitalKey = new DigitalKey(inbox.getInboxKey() + inbox.getCreatedBy().toString());
            inbox.setInboxKey(digitalKey.encrypt());
        }
        return InboxDaoImpl.create(inbox);
    }

    @Override
    public Inbox getById(int id) {
        return InboxDaoImpl.getById(id);
    }

    @Override
    public Inbox getByIdNotRemoved(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Inbox update(Inbox inbox) {
        checkNotNull(inbox, "inbox entity must not be null");
        checkNotNull(inbox.getUpdatedBy(), "update by must not be null");
        checkNotNull(inbox.getUserProfileFolderId(), "UserProfileFolderId by must not be null");
        checkNotNull(inbox.getStructureFolderId(), "StructureFolderId by must not be null");
        checkNotNull(inbox.getInboxReceiveFlag(), "InboxReceiveFlag by must not be null");
        checkNotNull(inbox.getInboxOpenFlag(), "InboxOpenFlag by must not be null");
        checkNotNull(inbox.getInboxActionFlag(), "InboxActionFlag by must not be null");
        checkNotNull(inbox.getInboxFinishFlag(), "InboxFinishFlag by must not be null");
        checkNotNull(inbox.getLetterStatus1(), "LetterStatus1 by must not be null");
        checkNotNull(inbox.getLetterStatus2(), "LetterStatus2 by must not be null");
        checkNotNull(inbox.getLetterStatus3(), "LetterStatus3 by must not be null");
        checkNotNull(inbox.getModuleId(), "ModuleId by must not be null");
        checkNotNull(inbox.getLinkId(), "LinkId by must not be null");
        checkNotNull(inbox.getLinkId2(), "LinkId2 by must not be null");
        checkNotNull(inbox.getWorkflowId(), "WorkflowId by must not be null");
        checkNotNull(inbox.getInboxSendDate(), "InboxSendDate by must not be null");
        inbox.setUpdatedDate(LocalDateTime.now());
        return InboxDaoImpl.update(inbox);
    }

    @Override
    public Inbox remove(int id, int userId) {
        checkNotNull(id, "inbox id must not be null");
        Inbox inbox = getById(id);
        checkNotNull(inbox, "inbox entity not found in database.");
        inbox.setRemovedBy(userId);
        inbox.setRemovedDate(LocalDateTime.now());
        return InboxDaoImpl.update(inbox);
    }

    @Override
    public List<Inbox> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inbox> listAll(String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inbox> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InboxModel tranformToModel(Inbox inbox) {
        InboxModel inboxModel = new InboxModel();
        inboxModel.setId(inbox.getId());
        inboxModel.setCreatedBy(inbox.getCreatedBy());
        inboxModel.setCreatedDate(Common.localDateTimeToString(inbox.getCreatedDate()));
        inboxModel.setOrderNo((float) inbox.getOrderNo());
        inboxModel.setUpdatedBy(inbox.getUpdatedBy());
        inboxModel.setUpdatedDate(Common.localDateTimeToString(inbox.getUpdatedDate()));
        inboxModel.setRemovedBy(inbox.getRemovedBy());
        inboxModel.setRemovedDate(Common.localDateTimeToString(inbox.getRemovedDate()));
        inboxModel.setUserProfileFolderId(inbox.getUserProfileFolderId());
        inboxModel.setStructureFolderId(inbox.getStructureFolderId());
        inboxModel.setInboxFrom(inbox.getInboxFrom());
        inboxModel.setInboxTo(inbox.getInboxTo());
        inboxModel.setInboxTitle(inbox.getInboxTitle());
        inboxModel.setInboxSendDate(Common.localDateTimeToString2(inbox.getInboxSendDate()));////
        inboxModel.setInboxReceiveDate(Common.localDateTimeToString(inbox.getInboxReceiveDate()));
        inboxModel.setInboxReceiveDateDefine(Common.localDateTimeToString(inbox.getInboxReceiveDateDefine()));
        inboxModel.setInboxReceiveFlag(inbox.getInboxReceiveFlag());
        inboxModel.setInboxOpenDate(Common.localDateTimeToString2(inbox.getInboxOpenDate()));////
        inboxModel.setInboxOpenDateDefine(Common.localDateTimeToString(inbox.getInboxOpenDateDefine()));
        inboxModel.setInboxOpenFlag(inbox.getInboxOpenFlag());
        inboxModel.setInboxActionDate(Common.localDateTimeToString(inbox.getInboxActionDate()));
        inboxModel.setInboxActionDateDefine(Common.localDateTimeToString(inbox.getInboxActionDateDefine()));
        inboxModel.setInboxActionFlag(inbox.getInboxActionFlag());
        inboxModel.setInboxFinishDate(Common.localDateTimeToString(inbox.getInboxFinishDate()));
        inboxModel.setInboxFinishDateDefine(Common.localDateTimeToString(inbox.getInboxFinishDateDefine()));
        inboxModel.setInboxFinishFlag(inbox.getInboxFinishFlag());
        inboxModel.setLetterStatus1(inbox.getLetterStatus1());
        inboxModel.setLetterStatus2(inbox.getLetterStatus2());
        inboxModel.setLetterStatus3(inbox.getLetterStatus3());
        inboxModel.setModuleId(inbox.getModuleId());
        inboxModel.setLinkId(inbox.getLinkId());
        inboxModel.setLinkId2(inbox.getLinkId2());
        inboxModel.setLinkType(inbox.getLinkType());
        inboxModel.setInboxStr01(inbox.getInboxStr01());
        inboxModel.setInboxStr02(inbox.getInboxStr02());
        inboxModel.setInboxStr03(inbox.getInboxStr03());
        inboxModel.setInboxStr04(inbox.getInboxStr04());
        inboxModel.setInboxDate01(Common.localDateTimeToString(inbox.getInboxDate01()));
        inboxModel.setInboxDate02(Common.localDateTimeToString(inbox.getInboxDate02()));
        inboxModel.setInboxDescription(inbox.getInboxDescription());
        inboxModel.setInboxNote(inbox.getInboxNote());
        inboxModel.setWorkflowId(inbox.getWorkflowId());
        inboxModel.setInboxKey(inbox.getInboxKey());
        inboxModel.setInboxApprove(inbox.getInboxApprove());
        inboxModel.setInboxApproveStatus(inbox.getInboxApproveStatus());
        if (inbox.getInboxKey() == null) {
            inboxModel.setInboxKey("0");
        }

        inboxModel.setIsFinished(false);
        inboxModel.setIsCanceled(false);
        WorkflowService workflowservice = new WorkflowService();

        Workflow workflowFinishAll = workflowservice.getByLinkIdAndLinkId3(inbox.getLinkId(), 1, "F");//ghb check finish all
        if (workflowFinishAll != null) {
            inboxModel.setIsFinished(true);
        } else {
            Workflow workflowFinish = workflowservice.getByLinkIdAndLinkId2(inbox.getLinkId(), inbox.getLinkId2(), "F");
            if (workflowFinish != null) {
                inboxModel.setIsFinished(true);
            } else {
                Workflow workflowCancelAll = workflowservice.getByLinkIdAndLinkId3(inbox.getLinkId(), 1, "C");
                if (workflowCancelAll != null) {
                    inboxModel.setIsCanceled(true);
                } else {
                    Workflow workflowCancel = workflowservice.getByLinkIdAndLinkId2(inbox.getLinkId(), inbox.getLinkId2(), "C");
                    if (workflowCancel != null) {
                        inboxModel.setIsCanceled(true);
                    }
                }
            }
        }

        inboxModel.setInboxSpeed(inbox.getInboxSpeed());
        return inboxModel;
    }

//    public void delete(Inbox inbox) {
//        checkNotNull(inbox, "inbox must not be null");
//        InboxDaoImpl.delete(inbox);
//    }
    public List<Inbox> listByStructureFolderId(int structureFolderId, int limit, int offset, String sort, String dir, int status) {
        checkNotNull(structureFolderId, "structureFolderId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InboxDaoImpl.listByStructureFolderId(structureFolderId, offset, limit, sort, dir, status);
    }

    public List<Inbox> listByUserProfileFolderId(int userProfileFolderId, int limit, int offset, String sort, String dir, int status) {
        checkNotNull(userProfileFolderId, "userProfileFolderId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InboxDaoImpl.listByUserProfileFolderId(userProfileFolderId, offset, limit, sort, dir, status);
    }

    public List<Inbox> listByWorkflowId(int workflowId) {
        checkNotNull(workflowId, "workflowId entity must not be null");
        return InboxDaoImpl.listByWorkflowId(workflowId);
    }

    public boolean checkUserNoRepeat(String[] InboxUserMain, String inboxUserRepeat, String[] inboxUserTypeMain, String InboxUserTypeRepeat) {
        boolean checkUserNoRepeat = true;
        for (int i = 0; i < InboxUserMain.length; i++) {
            if (InboxUserMain[i].equals(inboxUserRepeat)) {
                if (inboxUserTypeMain[i].equals(InboxUserTypeRepeat)) {
                    checkUserNoRepeat = false;
                }
            }
        }
        return checkUserNoRepeat;
    }

    public String checkLengthData(String tempStr, int lengthStr) {
        String result = null;

        if (tempStr.length() > lengthStr) {
            result = tempStr.substring(0, lengthStr);
        } else {
            result = tempStr;
        }
        return result;
    }

    public Inbox saveLogForCreate(Inbox inbox, String clientIp) {
        //For Create Log when create Inbox
        String logDescription = this.generateLogForCreateEntity(inbox);
        LogData logData = new LogData();
        logData.setCreatedBy(inbox.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(inbox.getClass().getName());
        logData.setLinkId(inbox.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_MWP);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return inbox;
    }

    public Inbox saveLogForRemove(Inbox inbox, String clientIp) {
        //For Create Log when remove Inbox
        String logDescription = this.generateLogForRemoveEntity(inbox);
        LogData logData = new LogData();
        logData.setCreatedBy(inbox.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(inbox.getClass().getName());
        logData.setLinkId(inbox.getId());
        logData.setModuleName(LogData.MODULE_MWP);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return inbox;
    }

    private String generateLogForCreateEntity(Inbox inbox) {
        ModuleService moduleService = new ModuleService();
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ข้อมูลเข้า : ");

        descriptionLog.append("ส่งเอกสาร[");
        Module module = moduleService.getById(inbox.getModuleId());
        descriptionLog.append(Common.noNull(module.getModuleName(), ""));
        descriptionLog.append("] ");

        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(inbox.getInboxTitle(), ""));

        descriptionLog.append(" จาก : ");
        descriptionLog.append(Common.noNull(inbox.getInboxFrom(), ""));

        descriptionLog.append(" ถึง : ");
        descriptionLog.append(Common.noNull(inbox.getInboxTo(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForRemoveEntity(Inbox inbox) {
        ModuleService moduleService = new ModuleService();
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ข้อมูลเข้า : ");
        descriptionLog.append("ลบรายการ[");
        Module module = moduleService.getById(inbox.getModuleId());
        descriptionLog.append(Common.noNull(module.getModuleName(), ""));
        descriptionLog.append("] ");

        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(inbox.getInboxTitle(), ""));

        return descriptionLog.toString();
    }

    //oat-edit
    public Integer checkLetterStatus(Inbox inbox, int type) {
        int letterStatus;
        LocalDateTime dateDefined = null;
        int inboxFlag = 0;
        switch (type) {
            case 1:
                dateDefined = inbox.getInboxOpenDateDefine();
                inboxFlag = inbox.getInboxOpenFlag();
                break;
            case 2:
                dateDefined = inbox.getInboxActionDateDefine();
                inboxFlag = inbox.getInboxActionFlag();
                break;
            case 3:
                dateDefined = inbox.getInboxFinishDateDefine();
                inboxFlag = inbox.getInboxFinishFlag();
                break;
            default:
                letterStatus = 0;
                break;
        }
        if (dateDefined != null && checkExpire(dateDefined)) {
            if (inboxFlag == 1) {
                letterStatus = 4;
            } else {
                letterStatus = 2;
            }
        } else {
            if (inboxFlag == 1) {
                letterStatus = 3;
            } else {
                letterStatus = 1;
            }
        }
        return letterStatus;
    }

    private boolean checkExpire(LocalDateTime dateDefined) {
        dateDefined.withHour(0);
        dateDefined.withMinute(0);
        dateDefined.withSecond(0);
        dateDefined.withNano(0);
        LocalDateTime dateNow = LocalDateTime.now();
        if (dateNow.isAfter(dateDefined)) {
            return true;
        } else {
            return false;
        }
    }

    public Integer countListByUserProfileFolderId(int userProfileFolderId, int status) {
        checkNotNull(userProfileFolderId, "userProfileFolderId must not be null");
        checkNotNull(status, "status must not be null");
        return InboxDaoImpl.countListByUserProfileFolderId(userProfileFolderId, status);
    }

    public Integer countListByStructureFolderId(int structureFolderId, int status) {
        checkNotNull(structureFolderId, "structureFolderId must not be null");
        checkNotNull(status, "status must not be null");
        return InboxDaoImpl.countListByStructureFolderId(structureFolderId, status);
    }

    public List<Inbox> searchInboxByModel(String search, int folderId, InboxSearchModel inboxsearchModel, String sort, String dir) {
        checkNotNull(inboxsearchModel, "inboxsearchModel entity must not be null");
        return InboxDaoImpl.searchInboxByModel(search, folderId, inboxsearchModel, sort, dir);
    }

    public List<Inbox> listAllByWorkflowId(int workflowId, String sort, String dir) {
        checkNotNull(workflowId, "workflowId entity must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return InboxDaoImpl.listAllByWorkflowId(workflowId, sort, dir);
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
