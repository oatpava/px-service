package com.px.wf.service;

import com.px.admin.entity.Organize;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.OrganizeService;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserService;
import com.px.dms.daoimpl.DmsDocumentDaoImpl;
import com.px.dms.entity.DmsDocument;
import com.px.dms.service.DmsDocumentService;
import com.px.mwp.daoimpl.WorkflowDaoImpl;
import com.px.mwp.entity.Workflow;
import com.px.mwp.service.WorkflowService;
import com.px.share.daoimpl.FileAttachDaoImpl;
import com.px.share.entity.FileAttach;
import com.px.share.service.FileAttachService;
import com.px.share.service.ParamService;
import com.px.share.util.Common;
import com.px.wf.daoimpl.WfContentDaoImpl;
import com.px.wf.entity.WfContent;
import com.px.wf.entity.WfFolder;
import com.px.wf.model.ImportFileAttachModel;
import com.px.wf.model.ImportWfContentModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
public class ImportService {

    private static final Logger LOG = Logger.getLogger(ImportService.class.getName());
    private UserProfile userProfile;
    private Structure structure;
    private WfFolder wfFolder;
    private DmsDocument dmsDocument;
    private WfContent wfContent;
    private FileAttach fileAttach;
    private File file;
    private Workflow workflow;

    public ImportService() {
    }

    public String checkData(ImportWfContentModel importWfContentModel) {
        String errorMessage = isValid(importWfContentModel.getBookDate(), true, false);
        if (errorMessage != null) {
            return "bookDate " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getSpeedLevel(), true, 1, 4);
        if (errorMessage != null) {
            return "speedLevel " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getSecretLevel(), true, 1, 4);
        if (errorMessage != null) {
            return "secretLevel " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getFrom(), true, 4000);
        if (errorMessage != null) {
            return "from " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getTo(), true, 4000);
        if (errorMessage != null) {
            return "to " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getThru(), false, 4000);
        if (errorMessage != null) {
            return "thru " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getTitle(), true, 4000);
        if (errorMessage != null) {
            return "title " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getReferTo(), false, 4000);
        if (errorMessage != null) {
            return "referTo " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getAttachWith(), false, 4000);
        if (errorMessage != null) {
            return "attachWith " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getProcedure(), false, 4000);
        if (errorMessage != null) {
            return "procedure " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getRemark(), false, 4000);
        if (errorMessage != null) {
            return "remark " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getHardCopyReceiveDateTime(), false, true);
        if (errorMessage != null) {
            return "hardCopyReceiveDate " + errorMessage;
        }

        errorMessage = isValid(importWfContentModel.getPostNo(), false, 255);
        if (errorMessage != null) {
            return "postNo " + errorMessage;
        }

        if (importWfContentModel.getFileAttach() != null) {
            errorMessage = isValid(importWfContentModel.getFileAttach().getName(), true, 255);
            if (errorMessage != null) {
                return "fileAttach.name " + errorMessage;
            }

            errorMessage = isValid(importWfContentModel.getFileAttach().getSecretLevel(), true, 1, 4);
            if (errorMessage != null) {
                return "fileAttach.secretLevel " + errorMessage;
            }

            errorMessage = isValid(importWfContentModel.getFileAttach().getFileBase64());
            if (errorMessage != null) {
                return "fileAttach.fileBase64 " + errorMessage;
            }
        }

        return null;
    }

    private boolean isNull(String input) {
        return input == null || input.length() == 0;
    }

    private boolean isNull(Integer input) {
        return input == null;
    }

    private boolean isNull(String[] input) {
        return input == null || input.length == 0;
    }

    private String isValid(String input, boolean required, boolean isDateTime) {
        if (!isNull(input)) {
            final String[] tmp = input.split(" ");
            final boolean isDateValidFormat = tmp[0].matches("(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(25[0-9]{2})");

            if (isDateTime) {
                if (tmp.length != 2 || !isDateValidFormat) {
                    return "รูปแบบไม่ถูกต้อง (dd/MM/YYYY HH:mm)";
                }

                final boolean isTimeValidFormat = tmp[1].matches("([0-1][0-9]|2[0-3]):[0-5][0-9]");
                if (!isTimeValidFormat) {
                    return "รูปแบบไม่ถูกต้อง (dd/MM/YYYY HH:mm)";
                }
            } else {
                if (tmp.length != 1 || !isDateValidFormat) {
                    return "รูปแบบไม่ถูกต้อง (dd/MM/YYYY)";
                }
            }

            final int year = Integer.parseInt(tmp[0].split("/")[2]);
            final int currentYear = Year.now().getValue() + 543;
            if (year < 2500 || year > currentYear) {
                return "ปีไม่ถูกต้อง (พ.ศ.)";
            }

            final int month = Integer.parseInt(tmp[0].split("/")[1]);
            if (month < 1 || month > 12) {
                return "เดือนไม่ถูกต้อง";
            }

            final int day = Integer.parseInt(tmp[0].split("/")[0]);
            final int daysInMonth = YearMonth.of(year - 543, month).lengthOfMonth();
            if (day < 1 || day > daysInMonth) {
                return "วันที่ไม่ถูกต้อง";
            }

            return null;
        } else {
            return required ? "ห้ามเป็นค่าว่าง" : null;
        }
    }

    private String isValid(Integer input, boolean required, Integer min, Integer max) {
        if (!isNull(input)) {
            return input < min || input > max ? "ค่าไม่ถูกต้อง (" + min + " - " + max + ")" : null;
        } else {
            return required ? "ห้ามเป็นค่าว่าง" : null;
        }
    }

    private String isValid(String input, boolean required, int max) {
        if (!isNull(input)) {
            return input.length() > max ? "เกินขนาดที่กำหนด (" + max + ")" : null;
        } else {
            return required ? "ห้ามเป็นค่าว่าง" : null;
        }
    }

    private String isValid(String[] input, boolean required, int max) {
        if (!isNull(input)) {
            int length = 0;
            for (String i : input) {
                length += i.length();
            }
            return length > max ? "เกินขนาดที่กำหนด (" + max + ")" : null;
        } else {
            return required ? "ห้ามเป็นค่าว่าง" : null;
        }
    }

    private String isValid(String input) {
        if (isNull(input)) {
            return "ห้ามเป็นค่าว่าง";
        }

        final boolean isValidFormat = input.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        if (!isValidFormat) {
            return "รูปแบบไม่ถูกต้อง (Base64)";
        }

        if (input.length() > 6) {
            final String tmp = input.substring(0, 6);
            if (!tmp.equals("JVBERi")) {
                return "ไฟลล์นามสกุลไม่ถูกต้อง (PDF)";
            }
        } else {
            return "ไฟลล์นามสกุลไม่ถูกต้อง (PDF)";
        }

        //*check file size
        return null;
    }

    public String checkAuthentication(String username, String password) {
        final boolean result = new UserService().authenticationUser(username, password);
        return !result ? "การยืนยันตัวตนไม่สำเร็จ" : null;
    }

    public String checkUserProfile(String username) {
        userProfile = new UserProfileService().getByUsername(username);
        structure = userProfile.getStructure();
        return userProfile == null ? "ไม่พบข้อมูลผู้ใช้งาน (username: " + username + ")" : null;
    }

    public String checkStructure(int structureId) {
        structure = new StructureService().getByIdNotRemoved(structureId);
        return structure == null ? "ไม่พบข้อมูลหน่วยงาน (structureId: " + structureId + ")" : null;
    }

    public String checkWfFolder() {
        List<WfFolder> listWfFolder = new WfFolderService().listByContentTypeIdAndStructureId(1, 3, structure.getId());
        if (listWfFolder.isEmpty()) {
            return "ไม่พบข้อมูลแฟ้มทะเบียนรับหนังสือภายนอก (หน่วยงาน: " + structure.getStructureName() + ")";
        } else {
            wfFolder = listWfFolder.get(0);
            return null;
        }
    }

    public String createDmsDocument() {
        DmsDocumentService dmsDocumentService = new DmsDocumentService();
        try {
            dmsDocument = new DmsDocument();
            dmsDocument.setCreatedBy(userProfile.getId());
            dmsDocument.setDocumentTypeId(4);
            dmsDocument = dmsDocumentService.create(dmsDocument);
            dmsDocument.setRemovedBy(-1);
            dmsDocument = dmsDocumentService.update(dmsDocument);
            return null;
        } catch (Exception ex) {
            LOG.error("/imports createDmsDocument(): " + ex.getMessage());
            return "ไม่สามารถบันทึกรายการหนังสือ (createDmsDocument())";
        }
    }

    public String createWfContent(ImportWfContentModel importWfContentModel) {
        ParamService paramservice = new ParamService();
        final int contentNoFormat;
        try {
            contentNoFormat = Integer.parseInt(paramservice.getByParamName("CONTENTFORMAT").getParamValue());
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getByParamName(\"CONTENTFORMAT\"): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().contentNoFormat)";
        }

        final int bookNoFormat;
        try {
            bookNoFormat = Integer.parseInt(paramservice.getByParamName("BOOKNOFORMAT").getParamValue());
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getByParamName(\"BOOKNOFORMAT\"): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().bookNoFormat)";
        }

        WfContentService wfContentService = new WfContentService();
        final int conentYear = Year.now().getValue() + 543;
        final String contentPre = wfFolder.getWfFolderPreContentNo() == null ? "" : wfFolder.getWfFolderPreContentNo();
        final int conentNumber;
        try {
            conentNumber = wfContentService.getMaxContentNo(contentPre, wfFolder.getId(), conentYear);
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getMaxContentNo(" + contentPre + ", " + wfFolder.getId() + ", " + conentYear + "): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().conentNumber)";
        }
        final String contentNo = wfContentService.convertContentNo(conentYear, conentNumber, 0, contentPre, contentNoFormat);

        final int bookYear = conentYear;
        final String bookPre = wfFolder.getWfFolderPreBookNo() == null ? "" : wfFolder.getWfFolderPreBookNo().split(", ")[0];
        final int bookNumber = conentNumber;
        String bookNo = wfContentService.convertContentNo(bookYear, bookNumber, 0, bookPre, bookNoFormat);
        if (wfFolder.getWfFolderBookNoType() == 1) {
            bookNo = bookNo.substring(0, bookNo.length() - 5);//without "/year"
        }

        final HashMap hashTo = prepareTo(importWfContentModel.getTo());
        if (hashTo == null) {
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().hashTo)";
        }

        final HashMap hashThru = prepareTo(importWfContentModel.getThru());
        if (hashThru == null) {
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().hashThru)";
        }

        try {
            wfContent = new WfContent();
            wfContent.setCreatedBy(userProfile.getId());
            wfContent.setWfContentBookDate(Common.dateThaiToLocalDateTime(importWfContentModel.getBookDate()));
            wfContent.setWfContentContentDate(LocalDateTime.now());
            wfContent.setWfContentExpireDate(null);
            wfContent.setInboxId(0);
            wfContent.setWfDocumentId(-1);
            wfContent.setWfContentFolderId(wfFolder.getId());
            wfContent.setWorkflowId(-1);
            wfContent.setWfContentContentPre(contentPre);
            wfContent.setWfContentYear(conentYear);
            wfContent.setWfContentContentNumber(conentNumber);
            wfContent.setWfContentContentPoint(0);
            wfContent.setWfContentContentTime(Common.localDateTimeToString(wfContent.getWfContentContentDate()).substring(11));
            wfContent.setWfContentContentNo(contentNo);
            wfContent.setWfContentBookYear(bookYear);
            wfContent.setWfContentBookPre(bookPre);
            wfContent.setWfContentBookNo(bookNo);
            wfContent.setWfContentBookNumber(bookNumber);
            wfContent.setWfContentBookPoint(0);
            wfContent.setWfContentFrom(importWfContentModel.getFrom());
            wfContent.setWfContentTo(hashTo.get("listName").toString());
            wfContent.setWfContentTitle(importWfContentModel.getTitle());
            wfContent.setWfContentSpeed(importWfContentModel.getSpeedLevel());
            wfContent.setWfContentSecret(importWfContentModel.getSecretLevel());
            wfContent.setWfContentDescription(importWfContentModel.getRemark());
            wfContent.setWfContentReference(importWfContentModel.getReferTo());
            wfContent.setWfContentAttachment(importWfContentModel.getAttachWith());
            wfContent.setWfContentOwnername(userProfile.getUserProfileFullName());
            wfContent.setWfContentStr02(hashTo.get("listId").toString());
            wfContent.setWfContentStr03(importWfContentModel.getPostNo());
            wfContent.setWfContentStr04(hashThru.get("listId").toString());
            wfContent.setWfContentText01(importWfContentModel.getProcedure());
            wfContent.setWfContentText03(hashThru.get("listName").toString());
            wfContent.setWfContentInt01(0);//1 = head conent
            wfContent.setWfContentInt02(0);//id of registed WfContent
            wfContent.setWfContentInt03(0);//1 = sent
            wfContent.setWfContentDate01(Common.dateThaiToLocalDateTime(importWfContentModel.getHardCopyReceiveDateTime()));
            wfContent.setFullText("");
            wfContent = wfContentService.create(wfContent);
            return null;
        } catch (Exception ex) {
            LOG.error("/imports createWfContent(): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent())";
        }
    }

    private HashMap prepareTo(String[] tos) {
        HashMap result = new HashMap();

        String listName = tos[0];
        Integer userType = getUserType(tos[0]);
        if (userType == null) {
            return null;
        }
        String listId = userType.toString();

        for (int i = 1; i < tos.length; i++) {
            listName += ", " + tos[i];
            userType = getUserType(tos[0]);
            if (userType == null) {
                return null;
            }
            listId += userType.toString();
        }

        result.put("listName", listName);
        result.put("listId", listId);

        return result;
    }

    private Integer getUserType(String name) {
        try {
            List<Structure> listStructure = new StructureService().listByName(name);
            if (!listStructure.isEmpty()) {
                return 1;
            }

            List<UserProfile> listUserProfile = new UserProfileService().listByName(name);
            if (!listUserProfile.isEmpty()) {
                return 0;
            }

            List<Organize> listOrganize = new OrganizeService().listByName(name);
            if (!listOrganize.isEmpty()) {
                return 3;
            }

            return 2;
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().prepareTo().getUserType(" + name + "): " + ex.getMessage());
            return null;
        }
    }

    public String createWorkflow() {
        WorkflowService workflowService = new WorkflowService();
        final String positionName = userProfile.getPosition() != null ? userProfile.getPosition().getPositionName() : null;
        final String str02 = wfContent.getWfContentDate01() != null ? Common.localDateTimeToString(wfContent.getWfContentDate01()).substring(0, 16) : null;

        try {
            workflow = new Workflow();
            workflow.setCreatedBy(userProfile.getId());
            workflow.setLinkId(dmsDocument.getId());
            workflow.setLinkId2(wfContent.getId());
            workflow.setWorkflowActionId(userProfile.getId());
            workflow.setWorkflowActionName(userProfile.getUserProfileFullName());
            workflow.setWorkflowActionPosition(positionName);
            workflow.setWorkflowActionType("R");
            workflow.setWorkflowActionDate(LocalDateTime.now());
            workflow.setWorkflowNote(wfFolder.getWfFolderName());
            workflow.setWorkflowTitle(wfContent.getWfContentTitle());
            workflow.setWorkflowStr02(str02);
            workflow.setWorkflowStr03(wfContent.getWfContentContentNo());
            workflow.setWorkflowStr04(wfContent.getWfContentBookNo());
            workflow.setWorkflowDate01(wfContent.getWfContentBookDate());
            workflow.setWorkflowDate02(LocalDateTime.now());
            workflow = workflowService.create(workflow);
            workflow.setOrderNo(workflow.getId());
            workflow = workflowService.update(workflow);
            return null;
        } catch (Exception ex) {
            LOG.error("/imports createWorkflow(): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createWorkflow())";
        }
    }

    public String updateWfContent() {
        WfContentService wfContentService = new WfContentService();
        try {
            wfContent.setOrderNo(wfContent.getId());
            wfContent.setWfDocumentId(dmsDocument.getId());
            wfContent.setWorkflowId(workflow.getId());
            wfContent.setFullText(wfContentService.genFullText(dmsDocument.getId()));
            wfContent = wfContentService.update(wfContent);
            return null;
        } catch (Exception ex) {
            LOG.error("/imports updateWfContent(): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (updateWfContent())";
        }
    }

    public String createFileAttach(ImportFileAttachModel importFileAttachModel) {
        FileAttachService fileAttachService = new FileAttachService();
        try {
            fileAttach = new FileAttach();
            fileAttach.setCreatedBy(userProfile.getId());
            fileAttach.setFileAttachName(importFileAttachModel + ".pdf");
            fileAttach.setFileAttachType(".PDF");
            fileAttach.setLinkType("dms");
            fileAttach.setLinkId(dmsDocument.getId());
            fileAttach.setReferenceId(0);
            fileAttach.setSecrets(importFileAttachModel.getSecretLevel());
            fileAttach = fileAttachService.create(fileAttach);

            return null;
        } catch (Exception ex) {
            LOG.error("/imports createFileAttach(): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (createFileAttach())";
        }
    }

    public String saveFileBase64(ImportFileAttachModel importFileAttachModel) {
        FileAttachService fileAttachService = new FileAttachService();

        final String pathDocumentTemp;
        try {
            pathDocumentTemp = new ParamService().getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().getByParamName(\"PATH_DOCUMENT_TEMP\"): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().pathDocumentTemp)";
        }

        final String filePathTmp = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
        File fileTmp = new File(filePathTmp);
        if (fileTmp.getParentFile() != null) {
            fileTmp.getParentFile().mkdirs();
        }

        final byte[] dataByteArray;
        try {
            dataByteArray = Base64.decodeBase64(importFileAttachModel.getFileBase64());
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().dataByteArray: " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64()dataByteArray)";
        }

        try (OutputStream outpuStream = new FileOutputStream(new File(filePathTmp))) {
            outpuStream.write(dataByteArray);
            outpuStream.flush();
            outpuStream.close();
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().outpuStream: " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().outpuStream)";
        }

        try {
            final String filePath = fileAttachService.moveToRealPath(fileAttach.getId());
            file = new File(filePath);
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().filePath: " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().filePath)";
        }

        return null;
    }

    public String updateFileAttach() {
        FileAttachService fileAttachService = new FileAttachService();
        try {
            fileAttach.setOrderNo(wfContent.getId());
            fileAttach.setFileAttachSize(file.length());
            fileAttach = fileAttachService.update(fileAttach);
            return null;
        } catch (Exception ex) {
            LOG.error("/imports updateFileAttach(): " + ex.getMessage());
            return "บันทึกรายการหนังสือไม่สำเร็จ (updateFileAttach())";
        }
    }

    public void deleteEntities() {
        if (dmsDocument.getId() != null) {
            try {
                new DmsDocumentDaoImpl().delete(dmsDocument);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().dmsDocument.id: " + dmsDocument.getId() + ": " + ex.getMessage());
            }
        }

        if (wfContent.getId() != null) {
            try {
                new WfContentDaoImpl().delete(wfContent);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().wfContent.id: " + wfContent.getId() + ": " + ex.getMessage());
            }
        }

        if (fileAttach.getId() != null) {
            try {
                new FileAttachDaoImpl().delete(fileAttach);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().fileAttach.id: " + fileAttach.getId() + ": " + ex.getMessage());
            }
        }

        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().file.path: " + file.getPath() + ": " + ex.getMessage());
            }
        }

        if (workflow.getId() != null) {
            try {
                new WorkflowDaoImpl().delete(workflow);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().workflow.id: " + workflow.getId() + ": " + ex.getMessage());
            }

        }

    }

    public HashMap getData() {
        final String contentDate = Common.localDateTimeToString(wfContent.getWfContentContentDate());

        HashMap data = new HashMap();
        data.put("contentNo", wfContent.getWfContentContentNo());
        data.put("contentDate", contentDate.split(" ")[0]);
        data.put("contentTime", contentDate.split(" ")[1]);
        data.put("bookNo", wfContent.getWfContentBookNo());
        data.put("bookDate", Common.localDateTimeToString4(wfContent.getWfContentBookDate()));
        return data;
    }

}
