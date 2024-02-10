package com.px.wf.service;

import com.px.admin.entity.Organize;
import com.px.admin.entity.Province;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.OrganizeService;
import com.px.admin.service.ProvinceService;
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
import com.px.wf.model.ImportStatusModel;
import com.px.wf.model.ImportWfContentModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
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
    private WfFolder wfFolder;
    private DmsDocument dmsDocument;
    private WfContent wfContent;
    private FileAttach fileAttach;
    private File file;
    private Workflow workflow;
    private List<Structure> listStructure;
    private Province province;

    public ImportService() {
    }

    public ImportStatusModel checkData(ImportWfContentModel importWfContentModel) {
        String errorMessage = isValid(importWfContentModel.getBookDate(), true, false);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "bookDate " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getSpeedLevel(), true, 1, 4);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "speedLevel " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getSecretLevel(), true, 1, 4);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "secretLevel " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getFrom(), true, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "from " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getTo(), true, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "to " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getThru(), false, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "thru " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getTitle(), true, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "title " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getReferTo(), false, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "referTo " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getAttachWith(), false, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "attachWith " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getProcedure(), false, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "procedure " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getRemark(), false, 4000);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "remark " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getHardCopyReceiveDateTime(), false, true);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "hardCopyReceiveDate " + errorMessage);
        }

        errorMessage = isValid(importWfContentModel.getPostNo(), false, 255);
        if (errorMessage != null) {
            return new ImportStatusModel(400, "postNo " + errorMessage);
        }

        if (importWfContentModel.getFileAttach() != null) {
            errorMessage = isValid(importWfContentModel.getFileAttach().getName(), true, 255);
            if (errorMessage != null) {
                return new ImportStatusModel(400, "fileAttach.name " + errorMessage);
            }

            errorMessage = isValid(importWfContentModel.getFileAttach().getSecretLevel(), true, 1, 4);
            if (errorMessage != null) {
                return new ImportStatusModel(400, "fileAttach.secretLevel " + errorMessage);
            }

            errorMessage = isValid(importWfContentModel.getFileAttach().getFileBase64());
            if (errorMessage != null) {
                return new ImportStatusModel(400, "fileAttach.fileBase64 " + errorMessage);
            }
        }

        return new ImportStatusModel();
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

    public ImportStatusModel checkAuthentication(String username, String password) {
        try {
            final boolean result = new UserService().authenticationUser(username, password);
            if (!result) {
                return new ImportStatusModel(401, "การยืนยันตัวตนไม่สำเร็จ");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports checkAuthentication()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkAuthentication())");
        }
    }

    public ImportStatusModel checkUserProfile(String username) {
        try {
            userProfile = new UserProfileService().getByUsername(username);
            if (userProfile == null) {
                return new ImportStatusModel(404, "ไม่พบข้อมูลผู้ใช้งาน (username: " + username + ")");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports checkAuthentication()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkUserProfile())");
        }
    }

    public ImportStatusModel checkStructure(int structureId) {
        try {
            Structure structure = new StructureService().getByIdNotRemoved(structureId);
            if (structure == null) {
                return new ImportStatusModel(404, "ไม่พบข้อมูลหน่วยงาน (structureId: " + structureId + ")");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports checkStructure()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkStructure())");
        }
    }

    public ImportStatusModel checkWfFolder(Integer structureId) {
        if (isNull(structureId) || structureId == 0) {
            structureId = userProfile.getStructure().getId();
        }

        WfFolderService wfFolderService = new WfFolderService();
        try {
            List<WfFolder> listWfFolder = wfFolderService.listShortcutByUserProfileId(userProfile.getId(), 5, 2, structureId);
            if (listWfFolder.isEmpty()) {
                return new ImportStatusModel(404, "ไม่พบข้อมูลแฟ้มทะเบียนรับคำขออิเล็กทรอนิกส์ (" + userProfile.getUserProfileFullName() + ")");
            } else {
                final int folderId = listWfFolder.get(0).getWfFolderLinkFolderId();
                try {
                    wfFolder = wfFolderService.getByIdNotRemoved(folderId);
                    if (wfFolder == null) {
                        return new ImportStatusModel(404, "ไม่พบข้อมูลแฟ้มทะเบียนรับคำขออิเล็กทรอนิกส์ (" + userProfile.getUserProfileFullName() + ")");
                    } else {
                        return new ImportStatusModel();
                    }
                } catch (Exception ex) {
                    LOG.error("/imports checkWfFolder().getByIdNotRemoved(" + folderId + ")", ex);
                    return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkWfFolder())");
                }
            }
        } catch (Exception ex) {
            LOG.error("/imports checkWfFolder().listShortcutByUserProfileId(" + userProfile.getId() + ", 5, 2, " + structureId + ")", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkWfFolder())");
        }
    }

    public ImportStatusModel createDmsDocument() {
        DmsDocumentService dmsDocumentService = new DmsDocumentService();
        try {
            dmsDocument = new DmsDocument();
            dmsDocument.setCreatedBy(userProfile.getId());
            dmsDocument.setDocumentTypeId(4);
            dmsDocument.setDmsDocumentName(null);
            dmsDocument.setDmsDocumentPublicStatus(null);
            dmsDocument = dmsDocumentService.create(dmsDocument);
            dmsDocument.setRemovedBy(-1);
            dmsDocument = dmsDocumentService.update(dmsDocument);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports createDmsDocument()", ex);
            return new ImportStatusModel(500, "ไม่สามารถบันทึกรายการหนังสือ (createDmsDocument())");
        }
    }

    public ImportStatusModel createWfContent(ImportWfContentModel importWfContentModel) {
        ParamService paramservice = new ParamService();
        final int contentNoFormat;
        try {
            contentNoFormat = Integer.parseInt(paramservice.getByParamName("CONTENTFORMAT").getParamValue());
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getByParamName(\"CONTENTFORMAT\")", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().contentNoFormat)");
        }

        final int bookNoFormat;
        try {
            bookNoFormat = Integer.parseInt(paramservice.getByParamName("BOOKNOFORMAT").getParamValue());
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getByParamName(\"BOOKNOFORMAT\")", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().bookNoFormat)");

        }

        WfContentService wfContentService = new WfContentService();
        final int conentYear = Year.now().getValue() + 543;
        final String contentPre = wfFolder.getWfFolderPreContentNo() == null ? "" : wfFolder.getWfFolderPreContentNo();
        final int conentNumber;
        try {
            conentNumber = wfContentService.getMaxContentNo(contentPre, wfFolder.getId(), conentYear);
        } catch (Exception ex) {
            LOG.error("/imports createWfContent().getMaxContentNo(" + contentPre + ", " + wfFolder.getId() + ", " + conentYear + ")", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().conentNumber)");
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
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().hashTo)");
        }

        final HashMap hashThru = prepareTo(importWfContentModel.getThru());
        if (hashThru == null) {
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent().hashThru)");
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
            wfContent.setWfContentStr01(null);
            wfContent.setWfContentStr02(hashTo.get("listId").toString());
            wfContent.setWfContentStr03(importWfContentModel.getPostNo());
            if (hashThru.get("listId") != null) {
                wfContent.setWfContentStr04(hashThru.get("listId").toString());
            }
            wfContent.setWfContentStr05(null);
            wfContent.setWfContentStr06(null);
            wfContent.setWfContentStr07(null);
            wfContent.setWfContentStr08(null);
            wfContent.setWfContentStr09(null);
            wfContent.setWfContentStr10(null);
            wfContent.setWfContentText01(importWfContentModel.getProcedure());
            wfContent.setWfContentText02(null);
            if (hashThru.get("listName") != null) {
                wfContent.setWfContentText03(hashThru.get("listName").toString());
            }
            wfContent.setWfContentText04(null);
            wfContent.setWfContentText05(null);
            wfContent.setWfContentText06(null);
            wfContent.setWfContentText07(null);
            wfContent.setWfContentText08(null);
            wfContent.setWfContentText09(null);
            wfContent.setWfContentText10(null);
            wfContent.setWfContentInt01(0);//1 = head conent
            wfContent.setWfContentInt02(0);//id of registed WfContent
            wfContent.setWfContentInt03(0);//1 = sent
            wfContent.setWfContentInt04(0);
            wfContent.setWfContentInt05(0);
            wfContent.setWfContentInt06(0);
            wfContent.setWfContentInt07(0);
            wfContent.setWfContentInt08(0);
            wfContent.setWfContentInt09(0);
            wfContent.setWfContentInt10(0);
            wfContent.setWfContentDate01(Common.dateThaiToLocalDateTime(importWfContentModel.getHardCopyReceiveDateTime()));
            wfContent.setWfContentDate02(null);
            wfContent.setWfContentDate03(null);
            wfContent.setWfContentDate04(null);
            wfContent.setWfContentDate05(null);
            wfContent.setWfContentDate06(null);
            wfContent.setWfContentDate07(null);
            wfContent.setWfContentDate08(null);
            wfContent.setWfContentDate09(null);
            wfContent.setWfContentDate10(null);
            wfContent.setConvertId(0);
            wfContent.setfOrgId(0);
            wfContent.setfTransMainId(0);
            wfContent.setfType(0);
            wfContent.setFullText("");
            wfContent = wfContentService.create(wfContent);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports createWfContent(): ", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWfContent())");
        }
    }

    private HashMap prepareTo(String[] tos) {
        if (tos == null || tos.length == 0) {//thru is null able
            HashMap tmp = new HashMap();
            tmp.put("listName", null);
            tmp.put("listId", null);
            return tmp;
        }

        String listName = tos[0];
        Integer userType = getUserType(tos[0]);
        if (userType == null) {
            return null;
        }
        String listId = userType.toString();

        for (int i = 1; i < tos.length; i++) {
            final String name = tos[i].replace(", ", " และ ");
            listName += ", " + name;
            userType = getUserType(tos[0]);
            if (userType == null) {
                return null;
            }
            listId += userType.toString();
        }

        HashMap result = new HashMap();
        result.put("listName", listName);
        result.put("listId", listId);
        return result;
    }

    private Integer getUserType(String name) {
        try {
            List<Structure> listStructureLocal = new StructureService().listByName(name);
            if (!listStructureLocal.isEmpty()) {
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
            LOG.error("/imports createWfContent().prepareTo().getUserType(" + name + ")", ex);
            return null;
        }
    }

    public ImportStatusModel createWorkflow() {
        WorkflowService workflowService = new WorkflowService();
        final String positionName = userProfile.getPosition() != null ? userProfile.getPosition().getPositionName() : null;
        final String str02 = wfContent.getWfContentDate01() != null ? Common.localDateTimeToString(wfContent.getWfContentDate01()).substring(0, 16) : null;

        try {
            workflow = new Workflow();
            workflow.setCreatedBy(userProfile.getId());
            workflow.setLinkId(dmsDocument.getId());
            workflow.setLinkId2(wfContent.getId());
            workflow.setLinkId3(0);
            workflow.setWorkflowActionId(userProfile.getId());
            workflow.setWorkflowActionIdType(0);
            workflow.setWorkflowActionName(userProfile.getUserProfileFullName());
            workflow.setWorkflowActionPosition(positionName);
            workflow.setWorkflowActionType("R");
            workflow.setWorkflowActionDate(LocalDateTime.now());
            workflow.setWorkflowNote(wfFolder.getWfFolderName());
            workflow.setWorkflowTitle(wfContent.getWfContentTitle());
            workflow.setWorkflowStr01(null);
            workflow.setWorkflowStr02(str02);
            workflow.setWorkflowStr03(wfContent.getWfContentContentNo());
            workflow.setWorkflowStr04(wfContent.getWfContentBookNo());
            workflow.setWorkflowDate01(wfContent.getWfContentBookDate());
            workflow.setWorkflowDate02(LocalDateTime.now());
            workflow.setConvertId(0);
            workflow = workflowService.create(workflow);
            workflow.setOrderNo(workflow.getId());
            workflow = workflowService.update(workflow);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports createWorkflow()", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createWorkflow())");
        }
    }

    public ImportStatusModel updateWfContent() {
        WfContentService wfContentService = new WfContentService();
        try {
            wfContent.setOrderNo(wfContent.getId());
            wfContent.setWfDocumentId(dmsDocument.getId());
            wfContent.setWorkflowId(workflow.getId());
            wfContent.setFullText(wfContentService.genFullText(dmsDocument.getId()));
            wfContent = wfContentService.update(wfContent);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports updateWfContent()", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (updateWfContent())");
        }
    }

    public ImportStatusModel createFileAttach(ImportFileAttachModel importFileAttachModel) {
        FileAttachService fileAttachService = new FileAttachService();
        try {
            fileAttach = new FileAttach();
            fileAttach.setCreatedBy(userProfile.getId());
            fileAttach.setFileAttachName(this.noSpecialChar(importFileAttachModel.getName()) + ".pdf");
            fileAttach.setFileAttachType(".PDF");
            fileAttach.setLinkType("dms");
            fileAttach.setLinkId(dmsDocument.getId());
            fileAttach.setReferenceId(0);
            fileAttach.setSecrets(importFileAttachModel.getSecretLevel());
            fileAttach = fileAttachService.create(fileAttach);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports createFileAttach()", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createFileAttach())");
        }
    }

    private String noSpecialChar(String input) {
        final String result = input.replaceAll("[^ก-๚a-zA-Z0-9_-]", "");
        return result.length() == 0 ? "untitled" : result;
    }

    public ImportStatusModel saveFileBase64(ImportFileAttachModel importFileAttachModel) {
        FileAttachService fileAttachService = new FileAttachService();

        final String pathDocumentTemp;
        try {
            pathDocumentTemp = new ParamService().getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().getByParamName(\"PATH_DOCUMENT_TEMP\")", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().pathDocumentTemp)");
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
            LOG.error("/imports saveFileBase64().dataByteArray", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64()dataByteArray)");
        }

        try (OutputStream outpuStream = new FileOutputStream(new File(filePathTmp))) {
            outpuStream.write(dataByteArray);
            outpuStream.flush();
            outpuStream.close();
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().outpuStream", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().outpuStream)");
        }

        try {
            final String filePath = fileAttachService.moveToRealPath(fileAttach.getId());
            file = new File(filePath);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports saveFileBase64().filePath", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (saveFileBase64().filePath)");
        }
    }

    public ImportStatusModel updateFileAttach() {
        FileAttachService fileAttachService = new FileAttachService();
        try {
            fileAttach.setOrderNo(wfContent.getId());
            fileAttach.setFileAttachSize(file.length());
            fileAttach = fileAttachService.update(fileAttach);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports updateFileAttach()", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (updateFileAttach())");
        }
    }

    public void deleteEntities() {
        if (dmsDocument != null && dmsDocument.getId() != null) {
            try {
                new DmsDocumentDaoImpl().delete(dmsDocument);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().dmsDocument.id: " + dmsDocument.getId() + "", ex);
            }

        }

        if (wfContent != null && wfContent.getId() != null) {
            try {
                new WfContentDaoImpl().delete(wfContent);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().wfContent.id: " + wfContent.getId() + "", ex);
            }

        }

        if (fileAttach != null && fileAttach.getId() != null) {
            try {
                new FileAttachDaoImpl().delete(fileAttach);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().fileAttach.id: " + fileAttach.getId() + "", ex);
            }
        }

        if (file != null && file.exists()) {
            try {
                file.delete();
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().file.path: " + file.getPath() + "", ex);
            }
        }

        if (workflow != null && workflow.getId() != null) {
            try {
                new WorkflowDaoImpl().delete(workflow);
            } catch (Exception ex) {
                LOG.error("/imports deleteEntities().workflow.id: " + workflow.getId() + "", ex);
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

    public ImportStatusModel listWfFolder() {
        List<WfFolder> listWfFolder = new WfFolderService().listShortcutByUserProfileId(userProfile.getId(), 5, 2, null);
        if (listWfFolder.isEmpty()) {
            return new ImportStatusModel(404, "ไม่พบข้อมูลแฟ้มทะเบียนรับคำขออิเล็กทรอนิกส์ (" + userProfile.getUserProfileFullName() + ")");
        } else {
            StructureService structureService = new StructureService();
            listStructure = new ArrayList<>();
            for (WfFolder wfFolderLocal : listWfFolder) {
                Structure structure = new Structure();
                try {
                    structure = structureService.getById(wfFolderLocal.getWfFolderLinkId());
                } catch (Exception ex) {
                    structure.setId(wfFolderLocal.getWfFolderLinkId());
                    structure.setStructureName("");
                    LOG.error("/imports/structure listWfFloder().structure.id: " + structure.getId() + "", ex);
                }
                listStructure.add(structure);
            }
            return new ImportStatusModel();
        }
    }

    public List<HashMap> getListData() {
        List<HashMap> data = new ArrayList<>();
        for (Structure structure : listStructure) {
            HashMap tmp = new HashMap();
            tmp.put("structureName", structure.getStructureName());
            tmp.put("structureId", structure.getId());

            data.add(tmp);
        }
        return data;
    }

    public ImportStatusModel checkAuthentication(String key) {
        try {
            userProfile = new UserProfileService().getByCode(key);
            if (userProfile == null) {
                return new ImportStatusModel(401, "การยืนยันตัวตนไม่สำเร็จ");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports/external checkAuthentication()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkAuthentication())");
        }
    }

    public ImportStatusModel checkProvince(String provinceCode) {
        try {
            province = new ProvinceService().getByCode(provinceCode);
            if (province == null) {
                return new ImportStatusModel(404, "ไม่พบข้อมูลจังหวัด (provinceCode: " + provinceCode + ")");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports/external checkProvince()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkProvince())");
        }
    }

    public ImportStatusModel checkWfFolder() {
        try {
            wfFolder = new WfFolderService().getExternalByProvinceId(province.getId());
            if (wfFolder == null) {
                return new ImportStatusModel(404, "ไม่พบข้อมูลแฟ้มทะเบียนรับคำขออิเล็กทรอนิกส์ (" + province.getId() + ")");
            } else {
                return new ImportStatusModel();
            }
        } catch (Exception ex) {
            LOG.error("/imports/external checkWfFolder()", ex);
            return new ImportStatusModel(500, "เกิดข้อผิดพลาด (checkWfFolder())");
        }
    }

    public ImportStatusModel createFileAttach() {
        FileAttachService fileAttachService = new FileAttachService();
        try {
            fileAttach = new FileAttach();
            fileAttach.setCreatedBy(userProfile.getId());
            fileAttach.setFileAttachName("เอกสารแนบคำขออิเล็กทรอนิกส์.pdf");
            fileAttach.setFileAttachType(".PDF");
            fileAttach.setLinkType("dms");
            fileAttach.setLinkId(dmsDocument.getId());
            fileAttach.setReferenceId(0);
            fileAttach.setSecrets(1);
            fileAttach = fileAttachService.create(fileAttach);
            return new ImportStatusModel();
        } catch (Exception ex) {
            LOG.error("/imports createFileAttachFromTemplate()", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (createFileAttachFromTemplate())");
        }
    }

    public ImportStatusModel copyFileFromTemplate() {
        FileAttachService fileAttachService = new FileAttachService();

        final String encodeFile;
        final String pathDocument;
        try {
            ParamService paramService = new ParamService();
            encodeFile = paramService.getByParamName("ENCODE_FILE").getParamValue();
            pathDocument = paramService.getByParamName("PATH_DOCUMENT").getParamValue();
        } catch (Exception ex) {
            LOG.error("/imports createFileFromTemplate().getByParamName(\"PATH_DOCUMENT\", \"ENCODE_FILE\")", ex);
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (copyFileFromTemplate())");
        }

        final String templateFilePath = pathDocument + "wf" + File.separator + "template_import.pdf";
        File templateFile = new File(templateFilePath);
        if (!templateFile.exists()) {
            LOG.error("/imports createFileFromTemplate().templateFile, file not found!!!");
            return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (copyFileFromTemplate())");
        }

        final String filePath = fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
        final String dstFilePath = pathDocument + filePath;
        file = new File(dstFilePath);
        
        if (encodeFile.equalsIgnoreCase("Y")) {
            try {
                Common.encodeFile(templateFilePath, dstFilePath);
            } catch (Exception ex) {
                LOG.error("/imports createFileFromTemplate().encodeFile()", ex);
                return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (copyFileFromTemplate())");
            }
        } else {
            String p = dstFilePath.substring(0, dstFilePath.lastIndexOf(File.separator));
            File f = new File(p);
            if (!f.exists()) {
                f.mkdirs();
            }

            try {
                Files.copy(templateFile.toPath(), file.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                LOG.error("/imports createFileFromTemplate().copy()", ex);
               return new ImportStatusModel(500, "บันทึกรายการหนังสือไม่สำเร็จ (copyFileFromTemplate())");
            }
        }
        return new ImportStatusModel();
    }

}
