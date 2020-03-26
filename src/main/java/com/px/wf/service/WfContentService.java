package com.px.wf.service;

import com.px.wf.daoimpl.WfContentDaoImpl;
import com.px.wf.entity.WfReserveContentNo;
import com.px.wf.entity.WfContent;
import com.px.wf.entity.WfFolder;
import com.px.share.service.GenericService;
import com.px.wf.model.WfContentModel;
import com.px.wf.model.WfContentModel_groupWfContentAndWorkflowfinish;
import com.px.admin.service.UserProfileService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import com.px.share.util.Common;
import com.px.wf.model.WfContentModel_groupContentNoAndBookNo;
import java.util.Date;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.mwp.service.WorkflowService;
import com.px.share.entity.LogData;
import com.px.share.entity.Param;
import com.px.share.service.LogDataService;
import com.px.share.service.ParamService;
import com.px.mwp.entity.Workflow;
import com.px.share.entity.FileAttach;
import com.px.share.service.FileAttachService;
import com.px.wf.model.WfContentSearchModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author Mali
 */
public class WfContentService implements GenericService<WfContent, WfContentModel> {

    private static final Logger log = Logger.getLogger(WfContentService.class.getName());
    private final WfContentDaoImpl WfContentDaoImpl;

    public WfContentService() {
        this.WfContentDaoImpl = new WfContentDaoImpl();
    }

    @Override
    public WfContent create(WfContent wfContent) {
        checkNotNull(wfContent, "wfContent entity must not be null");
        checkNotNull(wfContent.getCreatedBy(), "create by must not be null");
        return WfContentDaoImpl.create(wfContent);
    }

    @Override
    public WfContent getById(int id) {
        return WfContentDaoImpl.getById(id);
    }

    @Override
    public WfContent update(WfContent wfContent) {
        checkNotNull(wfContent, "wfContent entity must not be null");
        checkNotNull(wfContent.getUpdatedBy(), "update by must not be null");
        checkNotNull(wfContent.getWfDocumentId(), "WfDocumentId by must not be null");
        checkNotNull(wfContent.getWorkflowId(), "WorkflowId by must not be null");
        checkNotNull(wfContent.getWfContentFolderId(), "WfContentFolderId by must not be null");
        checkNotNull(wfContent.getWfContentContentYear(), "WfContentContentYear by must not be null");
        checkNotNull(wfContent.getWfContentBookYear(), "WfContentBookYear by must not be null");
        checkNotNull(wfContent.getWfContentInt01(), "WfContentInt01 by must not be null");
        checkNotNull(wfContent.getWfContentInt02(), "WfContentInt02 by must not be null");
        checkNotNull(wfContent.getWfContentInt03(), "WfContentInt03 by must not be null");
        checkNotNull(wfContent.getWfContentInt04(), "WfContentInt04 by must not be null");
        checkNotNull(wfContent.getWfContentInt05(), "WfContentInt05 by must not be null");
        checkNotNull(wfContent.getWfContentInt06(), "WfContentInt06 by must not be null");
        checkNotNull(wfContent.getWfContentInt07(), "WfContentInt07 by must not be null");
        checkNotNull(wfContent.getWfContentInt08(), "WfContentInt08 by must not be null");
        checkNotNull(wfContent.getWfContentInt09(), "WfContentInt09 by must not be null");
        checkNotNull(wfContent.getWfContentInt10(), "WfContentInt10 by must not be null");
        checkNotNull(wfContent.getWfContentContentDate(), "WfContentContentDate by must not be null");
        wfContent.setUpdatedDate(LocalDateTime.now());
        return WfContentDaoImpl.update(wfContent);
    }

    @Override
    public WfContent remove(int id, int userId) {
        checkNotNull(id, "wfContent id must not be null");
        WfContent wfContent = getById(id);
        checkNotNull(wfContent, "wfContent entity not found in database.");
        wfContent.setRemovedBy(userId);
        wfContent.setRemovedDate(LocalDateTime.now());
        return WfContentDaoImpl.update(wfContent);
    }

    public void delete(WfContent wfContent) {
        checkNotNull(wfContent, "wfContent must not be null");
        WfContentDaoImpl.delete(wfContent);
    }

    public List<WfContent> listByFolderId(int offset, int limit, String sort, String dir, int folderid, int wfContentYear) {
        checkNotNull(folderid, "folderid must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        checkNotNull(wfContentYear, "wfContentYear must not be null");
        return WfContentDaoImpl.listByFolderId(offset, limit, sort, dir, folderid, wfContentYear);
    }

    public List<WfContent> listByFolderId(int offset, int limit, int folderid, int wfContentYear) {
        checkNotNull(folderid, "folderid must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        checkNotNull(wfContentYear, "wfContentYear must not be null");
        return WfContentDaoImpl.listByFolderId(offset, limit, folderid, wfContentYear);
    }

    @Override
    public List<WfContent> listAll(String sort, String dir) {
        checkNotNull(sort, "sort must not be null");
        checkNotNull(dir, "dir must not be null");
        return WfContentDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return WfContentDaoImpl.countAll();
    }

    @Override
    public WfContentModel tranformToModel(WfContent wfContent) {//oat-edit
        WfContentModel wfContentModel = new WfContentModel();
        if (wfContent != null) {
            wfContentModel.setId(wfContent.getId());
            wfContentModel.setCreatedBy(wfContent.getCreatedBy());
            wfContentModel.setCreatedDate(Common.localDateTimeToString(wfContent.getCreatedDate()));
            wfContentModel.setOrderNo((float) wfContent.getOrderNo());
            wfContentModel.setRemovedBy(wfContent.getRemovedBy());
            wfContentModel.setRemovedDate(Common.localDateTimeToString(wfContent.getRemovedDate()));
            wfContentModel.setUpdatedBy(wfContent.getUpdatedBy());
            wfContentModel.setUpdatedDate(Common.localDateTimeToString(wfContent.getUpdatedDate()));
            wfContentModel.setWfDocumentId(wfContent.getWfDocumentId());
            wfContentModel.setWfContentFolderId(wfContent.getWfContentFolderId());
            wfContentModel.setWorkflowId(wfContent.getWorkflowId());
            wfContentModel.setWfContentContentPre(wfContent.getWfContentContentPre());
            wfContentModel.setWfContentContentYear(wfContent.getWfContentContentYear());
            wfContentModel.setWfContentContentNo(wfContent.getWfContentContentNo());
            wfContentModel.setWfContentContentNumber(wfContent.getWfContentContentNumber());
            wfContentModel.setWfContentContentPoint(wfContent.getWfContentContentPoint());
            //wfContentModel.setWfContentContentDate(Common.localDateTimeToString(wfContent.getWfContentContentDate()));
            wfContentModel.setWfContentContentDate(Common.localDateTimeToString4(wfContent.getWfContentContentDate()));
            wfContentModel.setWfContentBookPre(wfContent.getWfContentBookPre());
            wfContentModel.setWfContentBookYear(wfContent.getWfContentBookYear());
            wfContentModel.setWfContentBookNo(wfContent.getWfContentBookNo());
            wfContentModel.setWfContentBookNumber(wfContent.getWfContentBookNumber());
            wfContentModel.setWfContentBookPoint(wfContent.getWfContentBookPoint());
            wfContentModel.setWfContentBookDate(Common.localDateTimeToString(wfContent.getWfContentBookDate()));
            wfContentModel.setWfContentFrom(wfContent.getWfContentFrom());
            wfContentModel.setWfContentTo(wfContent.getWfContentTo());
            wfContentModel.setWfContentTitle(wfContent.getWfContentTitle());
            wfContentModel.setWfContentSpeed(wfContent.getWfContentSpeed());
//            String wfContentSpeedStr = "";
//            if (wfContent.getWfContentSpeed() == 1) {
//                wfContentSpeedStr = "ปกติ";
//            } else if (wfContent.getWfContentSpeed() == 2) {
//                wfContentSpeedStr = "ด่วน";
//            } else if (wfContent.getWfContentSpeed() == 3) {
//                wfContentSpeedStr = "ด่วนมาก";
//            } else if (wfContent.getWfContentSpeed() == 4) {
//                wfContentSpeedStr = "ด่วนที่สุด";
//            }
//            wfContentModel.setWfContentSpeedStr(wfContentSpeedStr);
            wfContentModel.setWfContentSecret(wfContent.getWfContentSecret());
//            String wfContetnSecretStr = "";
//            if (wfContent.getWfContentSecret() == 1) {
//                wfContetnSecretStr = "ปกติ";
//            } else if (wfContent.getWfContentSecret() == 2) {
//                wfContetnSecretStr = "ลับ";
//            } else if (wfContent.getWfContentSecret() == 3) {
//                wfContetnSecretStr = "ลับมาก";
//            } else if (wfContent.getWfContentSecret() == 4) {
//                wfContetnSecretStr = "ลับที่สุด";
//            }
//            wfContentModel.setWfContentSecretStr(wfContetnSecretStr);
            wfContentModel.setWfContentDescription(wfContent.getWfContentDescription());
            wfContentModel.setWfContentReference(wfContent.getWfContentReference());
            wfContentModel.setWfContentAttachment(wfContent.getWfContentAttachment());
            wfContentModel.setWfContentExpireDate(Common.localDateTimeToString(wfContent.getWfContentExpireDate()));
            wfContentModel.setWfContentOwnername(wfContent.getWfContentOwnername());
            wfContentModel.setWfContentContentTime(wfContent.getWfContentContentTime());
            wfContentModel.setInboxId(wfContent.getInboxId());
            wfContentModel.setWfContentStr01(wfContent.getWfContentStr01());
            wfContentModel.setWfContentStr02(wfContent.getWfContentStr02());
            wfContentModel.setWfContentStr03(wfContent.getWfContentStr03());
            wfContentModel.setWfContentStr04(wfContent.getWfContentStr04());
            wfContentModel.setWfContentStr05(wfContent.getWfContentStr05());
            wfContentModel.setWfContentStr06(wfContent.getWfContentStr06());
            wfContentModel.setWfContentStr07(wfContent.getWfContentStr07());
            wfContentModel.setWfContentStr08(wfContent.getWfContentStr08());
            wfContentModel.setWfContentStr09(wfContent.getWfContentStr09());
            wfContentModel.setWfContentStr10(wfContent.getWfContentStr10());
            wfContentModel.setWfContentText01(wfContent.getWfContentText01());
            wfContentModel.setWfContentText02(wfContent.getWfContentText02());
            wfContentModel.setWfContentText03(wfContent.getWfContentText03());
            wfContentModel.setWfContentText04(wfContent.getWfContentText04());
            wfContentModel.setWfContentText05(wfContent.getWfContentText05());
            wfContentModel.setWfContentText06(wfContent.getWfContentText06());
            wfContentModel.setWfContentText07(wfContent.getWfContentText07());
            wfContentModel.setWfContentText08(wfContent.getWfContentText08());
            wfContentModel.setWfContentText09(wfContent.getWfContentText09());
            wfContentModel.setWfContentText10(wfContent.getWfContentText10());
            wfContentModel.setWfContentInt01(wfContent.getWfContentInt01());
            wfContentModel.setWfContentInt02(wfContent.getWfContentInt02());
            wfContentModel.setWfContentInt03(wfContent.getWfContentInt03());
            wfContentModel.setWfContentInt04(wfContent.getWfContentInt04());
            wfContentModel.setWfContentInt05(wfContent.getWfContentInt05());
            wfContentModel.setWfContentInt06(wfContent.getWfContentInt06());
            wfContentModel.setWfContentInt07(wfContent.getWfContentInt07());
            wfContentModel.setWfContentInt08(wfContent.getWfContentInt08());
            wfContentModel.setWfContentInt09(wfContent.getWfContentInt09());
            wfContentModel.setWfContentInt10(wfContent.getWfContentInt10());
            wfContentModel.setWfContentDate01(Common.localDateTimeToString(wfContent.getWfContentDate01()));
            wfContentModel.setWfContentDate02(Common.localDateTimeToString(wfContent.getWfContentDate02()));
            wfContentModel.setWfContentDate03(Common.localDateTimeToString(wfContent.getWfContentDate03()));
            wfContentModel.setWfContentDate04(Common.localDateTimeToString(wfContent.getWfContentDate04()));
            wfContentModel.setWfContentDate05(Common.localDateTimeToString(wfContent.getWfContentDate05()));
            wfContentModel.setWfContentDate06(Common.localDateTimeToString(wfContent.getWfContentDate06()));
            wfContentModel.setWfContentDate07(Common.localDateTimeToString(wfContent.getWfContentDate07()));
            wfContentModel.setWfContentDate08(Common.localDateTimeToString(wfContent.getWfContentDate08()));
            wfContentModel.setWfContentDate09(Common.localDateTimeToString(wfContent.getWfContentDate09()));
            wfContentModel.setWfContentDate10(Common.localDateTimeToString(wfContent.getWfContentDate10()));

        }

        return wfContentModel;
    }

    public WfContentModel_groupWfContentAndWorkflowfinish tranformToModelGroupWfContentAndWorkflowfinish(WfContent wfContent, boolean getNumFileattach, boolean getStatus) {
        WorkflowService workflowservice = new WorkflowService();
        //WfCommandTypeService commandTypeService = new WfCommandTypeService();
        String commandTypeName1 = "";
//        if (wfContent.getWfContentInt01() > 0) {
//            commandTypeName1 = commandTypeService.getById(wfContent.getWfContentInt01()).getCommandTypeName();
//        } else {
//            commandTypeName1 = "";
//        }
        commandTypeName1 = "";

        WfContentModel_groupWfContentAndWorkflowfinish WfContentModel_groupWfContentAndWorkflowfinish = new WfContentModel_groupWfContentAndWorkflowfinish();

        WfContentModel_groupWfContentAndWorkflowfinish.setId(wfContent.getId());
        WfContentModel_groupWfContentAndWorkflowfinish.setCreatedBy(wfContent.getCreatedBy());
        WfContentModel_groupWfContentAndWorkflowfinish.setCreatedDate(Common.localDateTimeToString(wfContent.getCreatedDate()));
        WfContentModel_groupWfContentAndWorkflowfinish.setOrderNo((float) wfContent.getOrderNo());
        WfContentModel_groupWfContentAndWorkflowfinish.setRemovedBy(wfContent.getRemovedBy());
        WfContentModel_groupWfContentAndWorkflowfinish.setRemovedDate(Common.localDateTimeToString(wfContent.getRemovedDate()));
        WfContentModel_groupWfContentAndWorkflowfinish.setUpdatedBy(wfContent.getUpdatedBy());
        WfContentModel_groupWfContentAndWorkflowfinish.setUpdatedDate(Common.localDateTimeToString(wfContent.getUpdatedDate()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfDocumentId(wfContent.getWfDocumentId());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentFolderId(wfContent.getWfContentFolderId());
        WfContentModel_groupWfContentAndWorkflowfinish.setWorkflowId(wfContent.getWorkflowId());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentPre(wfContent.getWfContentContentPre());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentYear(wfContent.getWfContentContentYear());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentNo(wfContent.getWfContentContentNo());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentNumber(wfContent.getWfContentContentNumber());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentPoint(wfContent.getWfContentContentPoint());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentDate(Common.localDateTimeToString4(wfContent.getWfContentContentDate()));//
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookPre(wfContent.getWfContentBookPre());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookYear(wfContent.getWfContentBookYear());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookNo(wfContent.getWfContentBookNo());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookNumber(wfContent.getWfContentBookNumber());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookPoint(wfContent.getWfContentBookPoint());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentBookDate(Common.localDateTimeToString4(wfContent.getWfContentBookDate()));//
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentFrom(wfContent.getWfContentFrom());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentTo(wfContent.getWfContentTo());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentTitle(wfContent.getWfContentTitle());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentSpeed(wfContent.getWfContentSpeed());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentSecret(wfContent.getWfContentSecret());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDescription(wfContent.getWfContentDescription());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentReference(wfContent.getWfContentReference());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentAttachment(wfContent.getWfContentAttachment());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentExpireDate(Common.localDateTimeToString(wfContent.getWfContentExpireDate()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentOwnername(wfContent.getWfContentOwnername());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentContentTime(wfContent.getWfContentContentTime());
        WfContentModel_groupWfContentAndWorkflowfinish.setInboxId(wfContent.getInboxId());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr01(wfContent.getWfContentStr01());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr02(wfContent.getWfContentStr02());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr03(wfContent.getWfContentStr03());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr04(wfContent.getWfContentStr04());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr05(wfContent.getWfContentStr05());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr06(wfContent.getWfContentStr06());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr07(wfContent.getWfContentStr07());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr08(wfContent.getWfContentStr08());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr09(wfContent.getWfContentStr09());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentStr10(wfContent.getWfContentStr10());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText01(wfContent.getWfContentText01());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText02(wfContent.getWfContentText02());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText03(wfContent.getWfContentText03());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText04(wfContent.getWfContentText04());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText05(wfContent.getWfContentText05());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText06(wfContent.getWfContentText06());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText07(wfContent.getWfContentText07());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText08(wfContent.getWfContentText08());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText09(wfContent.getWfContentText09());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentText10(wfContent.getWfContentText10());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt01(wfContent.getWfContentInt01());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt02(wfContent.getWfContentInt02());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt03(wfContent.getWfContentInt03());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt04(wfContent.getWfContentInt04());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt05(wfContent.getWfContentInt05());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt06(wfContent.getWfContentInt06());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt07(wfContent.getWfContentInt07());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt08(wfContent.getWfContentInt08());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt09(wfContent.getWfContentInt09());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentInt10(wfContent.getWfContentInt10());
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate01(Common.localDateTimeToString2(wfContent.getWfContentDate01()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate02(Common.localDateTimeToString(wfContent.getWfContentDate02()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate03(Common.localDateTimeToString(wfContent.getWfContentDate03()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate04(Common.localDateTimeToString(wfContent.getWfContentDate04()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate05(Common.localDateTimeToString(wfContent.getWfContentDate05()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate06(Common.localDateTimeToString(wfContent.getWfContentDate06()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate07(Common.localDateTimeToString(wfContent.getWfContentDate07()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate08(Common.localDateTimeToString(wfContent.getWfContentDate08()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate09(Common.localDateTimeToString(wfContent.getWfContentDate09()));
        WfContentModel_groupWfContentAndWorkflowfinish.setWfContentDate10(Common.localDateTimeToString(wfContent.getWfContentDate10()));
        WfContentModel_groupWfContentAndWorkflowfinish.setCommandTypeName(commandTypeName1);
        //oat-add
        WfContentModel_groupWfContentAndWorkflowfinish.setNumFileAttach(0);
        WfContentModel_groupWfContentAndWorkflowfinish.setStatus(1);
        WfContentModel_groupWfContentAndWorkflowfinish.setHasFinish(false);
        WfContentModel_groupWfContentAndWorkflowfinish.setIsCanceled(false);
        WfContentModel_groupWfContentAndWorkflowfinish.setFinishByS(0);
        WfContentModel_groupWfContentAndWorkflowfinish.setCancelBy(0);
        WfContentModel_groupWfContentAndWorkflowfinish.setIsKeeped(false);
        if (getStatus) {
            Workflow workflowFinishAll = workflowservice.getByLinkIdAndLinkId3(wfContent.getWfDocumentId(), 1, "F");//ghb check finish all
            if (workflowFinishAll != null) {
                WfContentModel_groupWfContentAndWorkflowfinish.setHasFinish(true);
                WfContentModel_groupWfContentAndWorkflowfinish.setStatus(2);
                WfContentModel_groupWfContentAndWorkflowfinish.setFinishByS(new UserProfileService().getById(workflowFinishAll.getWorkflowActionId()).getStructure().getId());
                WfContentModel_groupWfContentAndWorkflowfinish.setIsKeeped(false);//ghb no dms->no keep
            } else {
                //        Workflow workflow = workflowservice.getFinishByLinkId(wfContent.getWfDocumentId());//oat-edit use onlylinkid
                Workflow workflowFinish = workflowservice.getByLinkIdAndLinkId2(wfContent.getWfDocumentId(), wfContent.getId(), "F");//not all flow
                if (workflowFinish != null) {//finish
                    WfContentModel_groupWfContentAndWorkflowfinish.setHasFinish(true);
                    WfContentModel_groupWfContentAndWorkflowfinish.setStatus(2);
                    //WfContentModel_groupWfContentAndWorkflowfinish.setFinishByS(workflowFinish.getLinkId3());//structureId
                    WfContentModel_groupWfContentAndWorkflowfinish.setFinishByS(new UserProfileService().getById(workflowFinish.getWorkflowActionId()).getStructure().getId());
                    WfContentModel_groupWfContentAndWorkflowfinish.setIsKeeped(false);//ghb no dms->no keep
                } else {
                    Workflow workflowCancelAll = workflowservice.getByLinkIdAndLinkId3(wfContent.getWfDocumentId(), 1, "C");//check cc all
                    if (workflowCancelAll != null) {
                        //Workflow wf_b = workflowservice.getUnCancelByLinkIdAndLinkId2(wfContent.getWfDocumentId(), wfContent.getId());//oat-add
                        //if (wf_b == null) {//cancelAll
                        WfContentModel_groupWfContentAndWorkflowfinish.setIsCanceled(true);
                        WfContentModel_groupWfContentAndWorkflowfinish.setStatus(3);
                        WfContentModel_groupWfContentAndWorkflowfinish.setCancelBy(workflowCancelAll.getWorkflowActionId());
                        //}
                    } else {
                        Workflow workflowCancel = workflowservice.getByLinkIdAndLinkId2(wfContent.getWfDocumentId(), wfContent.getId(), "C");//not all flow
                        if (workflowCancel != null) {//cancel
                            WfContentModel_groupWfContentAndWorkflowfinish.setIsCanceled(true);
                            WfContentModel_groupWfContentAndWorkflowfinish.setStatus(3);
                            WfContentModel_groupWfContentAndWorkflowfinish.setCancelBy(workflowCancel.getWorkflowActionId());
                        }
                    }
                }
            }
        }

        if (getNumFileattach) {
            WfContentModel_groupWfContentAndWorkflowfinish.setNumFileAttach(new FileAttachService().countAllByLinkTypeLinkId("dms", wfContent.getWfDocumentId()));
        }

        return WfContentModel_groupWfContentAndWorkflowfinish;
    }

    public WfContentModel_groupContentNoAndBookNo tranformToModelGroupContentNoAndBookNo(String wfContentNo, int wfContentNumber, int wfContentPoint, int wfContentYear, String wfContentPre) {
        WfContentModel_groupContentNoAndBookNo wfContentModel_groupContentNoAndBookNo = new WfContentModel_groupContentNoAndBookNo();

        wfContentModel_groupContentNoAndBookNo.setWfContentNo(wfContentNo);
        wfContentModel_groupContentNoAndBookNo.setWfContentYear(wfContentYear);
        wfContentModel_groupContentNoAndBookNo.setWfContentPoint(wfContentPoint);
        wfContentModel_groupContentNoAndBookNo.setWfContentNumber(wfContentNumber);
        wfContentModel_groupContentNoAndBookNo.setWfFolderPre(wfContentPre);

        return wfContentModel_groupContentNoAndBookNo;
    }

    @Override
    public List<WfContent> list(int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WfContent> search(MultivaluedMap<String, String> queryParams, int limit, int offset, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getMaxContentNo(String wfFolderPreContentNo, int folderId, int wfFolderContentYear) {
        return WfContentDaoImpl.getMaxContentNo(wfFolderPreContentNo, folderId, wfFolderContentYear);
    }

    public int getMaxBookNo(String wfFolderPreBookNo, int folderId, int wfFolderContentYear) {
        return WfContentDaoImpl.getMaxBookNo(wfFolderPreBookNo, folderId, wfFolderContentYear);
    }

    public WfContent getByContentNo(String wfContentContentNo, int folderId) {
        return WfContentDaoImpl.getByContentNo(wfContentContentNo, folderId);
    }

    public WfContent getByBookNo(String wfContentBookNo, int folderId) {
        return WfContentDaoImpl.getByBookNo(wfContentBookNo, folderId);
    }

    @Override
    public WfContent getByIdNotRemoved(int id) {
        checkNotNull(id, "WfContent id entity must not be null");
        return WfContentDaoImpl.getByIdNotRemoved(id);
    }

    public List<WfContent> listByFolderId(int folderid, int wfContentYear) {
        checkNotNull(folderid, "folderid must not be null");
        checkNotNull(wfContentYear, "wfContentYear must not be null");
        return WfContentDaoImpl.listByFolderId(folderid, wfContentYear);
    }

    public boolean checkWfContentInWfFolder(int folderId) {
        checkNotNull(folderId, "folder Id must not be null");
        return WfContentDaoImpl.checkWfContentInWfFolder(folderId);
    }

    public int countAllListByFolderId(int folderid, int wfContentYear) {
        checkNotNull(folderid, "folderid must not be null");
        checkNotNull(wfContentYear, "wfContentYear must not be null");
        return WfContentDaoImpl.countAllListByFolderId(folderid, wfContentYear);
    }

    public List<WfContent> listByFolderIdDateRange(int folderid, String start, String end, String dir) {
        checkNotNull(folderid, "folderid must not be null");
        return WfContentDaoImpl.listByFolderIdDateRange(folderid, start, end, dir);
    }

    public List<WfContent> listByFolderIdDateRangeUser(int folderid, String start, String end, int userId, String dir) {
        checkNotNull(folderid, "folderid must not be null");
        return WfContentDaoImpl.listByFolderIdDateRangeUser(folderid, start, end, userId, dir);
    }

    public HashMap<String, String> maxContentNo(int folderId) {
        HashMap<String, String> listMaxContentNo = new HashMap<String, String>();
        WfContentService wfContentService = new WfContentService();
        WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();

        //get current year
        Calendar calendar = Calendar.getInstance();
        int monthToday = calendar.get(Calendar.MONTH) + 1;
        int yearToday = calendar.get(Calendar.YEAR) + 543;

        //get contentFormat from Param
        Param param = new Param();
        ParamService paramservice = new ParamService();
        param = paramservice.getByParamName("CONTENTFORMAT");
        int contentFormat = Integer.parseInt(param.getParamValue());

        //point
        int wfContentContentPoint = 0;

        //get preContentNo
        WfFolderService wfFolderService = new WfFolderService();
        WfFolder wfFolder = wfFolderService.getById(folderId);
        String wfFolderPreContentNo = wfFolder.getWfFolderPreContentNo();
        int wfFolderByBudgetYear = wfFolder.getWfFolderByBudgetYear();

        if (wfFolderByBudgetYear == 1) {
            if (monthToday == 10 || monthToday == 11 || monthToday == 12) {
                yearToday += 1;
            }
        }

        //get max contentNumber
        int wfContentContentNumber = wfContentService.getMaxContentNo(wfFolderPreContentNo, folderId, yearToday);
        //get max reserveContentNumber
        int reserveContentNumber = reserveContentNoService.getMaxContentNumber(folderId, yearToday);

        if (wfContentContentNumber < reserveContentNumber) {
            wfContentContentNumber = reserveContentNumber;
        }

        listMaxContentNo.put("yearToday", String.valueOf(yearToday));
        listMaxContentNo.put("wfContentContentNumber", String.valueOf(wfContentContentNumber));
        listMaxContentNo.put("wfContentContentPoint", String.valueOf(wfContentContentPoint));
        listMaxContentNo.put("wfFolderPreContentNo", wfFolderPreContentNo);
        listMaxContentNo.put("contentFormat", String.valueOf(contentFormat));
        return listMaxContentNo;
    }

    public HashMap<String, String> maxBookNo(int folderId) {

        HashMap<String, String> listMaxBookNo = new HashMap<String, String>();
        Param param1 = new Param();
        ParamService paramservice = new ParamService();
        param1 = paramservice.getByParamName("BOOKNOEQUALCONNO");
        String bookNoEqualConNo = param1.getParamValue();

        WfContentService wfContentService = new WfContentService();

        //get current year
        Calendar calendar = Calendar.getInstance();
        int monthToday = calendar.get(Calendar.MONTH) + 1;
        int yearToday = calendar.get(Calendar.YEAR) + 543;

        //get bookNoFormat from Param
        Param param = new Param();
        param = paramservice.getByParamName("BOOKNOFORMAT");
        int bookNoFormat = Integer.parseInt(param.getParamValue());

        //point
        int wfContentBookPoint = 0;

        //get preBookNo
        WfFolderService wfFolderService = new WfFolderService();
        WfFolder wfFolder = wfFolderService.getById(folderId);
        String wfFolderPreBookNo = wfFolder.getWfFolderPreBookNo();
        int wfFolderBookNoType = wfFolder.getWfFolderBookNoType();
        int wfFolderAutorun = wfFolder.getWfFolderAutorun();
        int wfFolderByBudgetYear = wfFolder.getWfFolderByBudgetYear();

        if (wfFolderByBudgetYear == 1) {
            if (monthToday == 10 || monthToday == 11 || monthToday == 12) {
                yearToday += 1;
            }
        }

        int wfContentBookNumber = 0;
        if (bookNoEqualConNo.equals("Y")) {
            HashMap<String, String> hashMapContent = wfContentService.maxContentNo(folderId);
            wfContentBookNumber = Integer.parseInt(hashMapContent.get("wfContentContentNumber"));
        } else {
            //get max bookNumber
            wfContentBookNumber = wfContentService.getMaxBookNo(wfFolderPreBookNo, folderId, yearToday);
        }

        listMaxBookNo.put("yearToday", String.valueOf(yearToday));
        listMaxBookNo.put("wfContentBookNumber", String.valueOf(wfContentBookNumber));
        listMaxBookNo.put("wfContentBookPoint", String.valueOf(wfContentBookPoint));
        listMaxBookNo.put("wfFolderPreBookNo", wfFolderPreBookNo);
        listMaxBookNo.put("bookNoFormat", String.valueOf(bookNoFormat));
        listMaxBookNo.put("wfFolderBookNoType", String.valueOf(wfFolderBookNoType));
        listMaxBookNo.put("wfFolderAutorun", String.valueOf(wfFolderAutorun));

        return listMaxBookNo;
    }

    public HashMap<String, String> checkMaxContentNo(String wfContentNo, int folderId, String wfContentContentPre, int year, int wfContentContentPoint, int userID) {
        WfContentService wfContentService = new WfContentService();
        int chkRepeatContentNoMsg = 0;
        int wfContentNumber = 0;
        HashMap<String, String> checkMaxContentNo = new HashMap<String, String>();

        //check repeat contentNo
        WfContent chkRepeatContentNo = wfContentService.getByContentNo(wfContentNo, folderId);
        WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
        WfReserveContentNo chkContentNoINReserve = reserveContentNoService.getContentNoByFolderId(folderId, wfContentNo);
        if (chkContentNoINReserve == null) {
            if (chkRepeatContentNo != null) {
                List<String> checkRepeatContentNo = wfContentService.checkRepeatContentNo(folderId, wfContentContentPre, year, wfContentContentPoint);
                chkRepeatContentNoMsg = Integer.parseInt(checkRepeatContentNo.get(0));
                wfContentNo = checkRepeatContentNo.get(1);
                wfContentNumber = Integer.parseInt(checkRepeatContentNo.get(2));
            }
        } else {
            chkContentNoINReserve.setUpdatedBy(userID);
            chkContentNoINReserve.setUpdatedDate(LocalDateTime.now());
            chkContentNoINReserve.setReserveContentNoStatus(1);//0 เลขจอง 1 เลขใช้แล้ว 2 เลขยกเลิกการจอง
            chkContentNoINReserve = reserveContentNoService.update(chkContentNoINReserve);
        }

        checkMaxContentNo.put("chkRepeatContentNoMsg", String.valueOf(chkRepeatContentNoMsg));
        checkMaxContentNo.put("wfContentNo", wfContentNo);
        checkMaxContentNo.put("wfContentNumber", String.valueOf(wfContentNumber));
        checkMaxContentNo.put("wfContentPoint", "0");
        checkMaxContentNo.put("wfContentYear", String.valueOf(year));

        return checkMaxContentNo;
    }

    public HashMap<String, String> checkMaxBookNo(int wfFolderAutorun, String wfContentBookNo, int folderId, String wfContentBookPre, int year, int wfContentBookPoint, int wfFolderBookNoType, int wfContentNumber) {
        Param param1 = new Param();
        ParamService paramservice = new ParamService();
        param1 = paramservice.getByParamName("BOOKNOEQUALCONNO");
        String bookNoEqualConNo = param1.getParamValue();

        //get bookNoFormat from Param
        Param param = new Param();
        param = paramservice.getByParamName("BOOKNOFORMAT");
        int bookNoFormat = Integer.parseInt(param.getParamValue());

        WfContentService wfContentService = new WfContentService();
        int chkRepeatBookNoMsg = 0;
        int wfBookNumber = 0;
        HashMap<String, String> checkMaxBookNo = new HashMap<String, String>();

        boolean chkBookNoEqualConNo = false;

        //check repeat bookNo
        if (wfFolderAutorun == 1) {
            if (bookNoEqualConNo.equals("Y")) {
                chkBookNoEqualConNo = true;
            } else {
                WfContent chkRepeatBookNo = wfContentService.getByBookNo(wfContentBookNo, folderId);
                if (chkRepeatBookNo != null) {
                    List<String> checkRepeatBookNo = wfContentService.checkRepeatBookNo(folderId, wfContentBookPre, year, wfContentBookPoint, wfFolderBookNoType, wfFolderAutorun);
                    chkRepeatBookNoMsg = Integer.parseInt(checkRepeatBookNo.get(0));
                    wfContentBookNo = checkRepeatBookNo.get(1);
                    wfBookNumber = Integer.parseInt(checkRepeatBookNo.get(2));
                }
            }
        } else {
            chkBookNoEqualConNo = true;
        }

        if (chkBookNoEqualConNo) {
            WfContent chkRepeatBookNo = wfContentService.getByBookNo(wfContentBookNo, folderId);
            if (chkRepeatBookNo != null) {
                chkRepeatBookNoMsg = 1;
                wfContentBookNo = wfContentService.convertBookNo(year, wfContentNumber, wfContentBookPoint, wfContentBookPre, bookNoFormat, wfFolderBookNoType, wfFolderAutorun);
                wfBookNumber = wfContentNumber;
            }
        }

        checkMaxBookNo.put("chkRepeatBookNoMsg", String.valueOf(chkRepeatBookNoMsg));
        checkMaxBookNo.put("wfContentBookNo", wfContentBookNo);
        checkMaxBookNo.put("wfBookNumber", String.valueOf(wfBookNumber));

        return checkMaxBookNo;
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

    public WfContentModel checkLengthField(WfContentModel wfContentPostModel) {
        WfContentService wfContentService = new WfContentService();
        wfContentPostModel.setWfContentContentTime(wfContentService.checkLengthData(wfContentPostModel.getWfContentContentTime(), 10));
        //==================================================================================================================
        if (wfContentPostModel.getWfContentContentPre() != null) {
            wfContentPostModel.setWfContentContentPre(wfContentService.checkLengthData(wfContentPostModel.getWfContentContentPre(), 50));
        }
        wfContentPostModel.setWfContentContentNo(wfContentService.checkLengthData(wfContentPostModel.getWfContentContentNo(), 50));
        if (wfContentPostModel.getWfContentBookPre() != null) {
            wfContentPostModel.setWfContentBookPre(wfContentService.checkLengthData(wfContentPostModel.getWfContentBookPre(), 50));
        }
        if (wfContentPostModel.getWfContentBookNo() != null) {
            wfContentPostModel.setWfContentBookNo(wfContentService.checkLengthData(wfContentPostModel.getWfContentBookNo(), 50));
        }
        //==================================================================================================================
        if (wfContentPostModel.getWfContentOwnername() != null) {
            wfContentPostModel.setWfContentOwnername(wfContentService.checkLengthData(wfContentPostModel.getWfContentOwnername(), 255));
        }
        if (wfContentPostModel.getWfContentStr01() != null) {
            wfContentPostModel.setWfContentStr01(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr01(), 255));
        }
        if (wfContentPostModel.getWfContentStr02() != null) {
            wfContentPostModel.setWfContentStr02(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr02(), 255));
        }
        if (wfContentPostModel.getWfContentStr03() != null) {
            wfContentPostModel.setWfContentStr03(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr03(), 255));
        }
        if (wfContentPostModel.getWfContentStr04() != null) {
            wfContentPostModel.setWfContentStr04(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr04(), 255));
        }
        if (wfContentPostModel.getWfContentStr05() != null) {
            wfContentPostModel.setWfContentStr05(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr05(), 255));
        }
        if (wfContentPostModel.getWfContentStr06() != null) {
            wfContentPostModel.setWfContentStr06(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr06(), 255));
        }
        if (wfContentPostModel.getWfContentStr07() != null) {
            wfContentPostModel.setWfContentStr07(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr07(), 255));
        }
        if (wfContentPostModel.getWfContentStr08() != null) {
            wfContentPostModel.setWfContentStr08(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr08(), 255));
        }
        if (wfContentPostModel.getWfContentStr09() != null) {
            wfContentPostModel.setWfContentStr09(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr09(), 255));
        }
        if (wfContentPostModel.getWfContentStr10() != null) {
            wfContentPostModel.setWfContentStr10(wfContentService.checkLengthData(wfContentPostModel.getWfContentStr10(), 255));
        }
        //==================================================================================================================
        if (wfContentPostModel.getWfContentFrom() != null) {
            wfContentPostModel.setWfContentFrom(wfContentService.checkLengthData(wfContentPostModel.getWfContentFrom(), 1000));
        }

        if (wfContentPostModel.getWfContentTo() != null) {
            wfContentPostModel.setWfContentTo(wfContentService.checkLengthData(wfContentPostModel.getWfContentTo(), 4000));
        }
        if (wfContentPostModel.getWfContentTitle() != null) {
            wfContentPostModel.setWfContentTitle(wfContentService.checkLengthData(wfContentPostModel.getWfContentTitle(), 4000));
        }
        if (wfContentPostModel.getWfContentDescription() != null) {
            wfContentPostModel.setWfContentDescription(wfContentService.checkLengthData(wfContentPostModel.getWfContentDescription(), 4000));
        }
        if (wfContentPostModel.getWfContentReference() != null) {
            wfContentPostModel.setWfContentReference(wfContentService.checkLengthData(wfContentPostModel.getWfContentReference(), 4000));
        }
        if (wfContentPostModel.getWfContentAttachment() != null) {
            wfContentPostModel.setWfContentAttachment(wfContentService.checkLengthData(wfContentPostModel.getWfContentAttachment(), 4000));
        }
        if (wfContentPostModel.getWfContentText01() != null) {
            wfContentPostModel.setWfContentText01(wfContentService.checkLengthData(wfContentPostModel.getWfContentText01(), 4000));
        }
        if (wfContentPostModel.getWfContentText02() != null) {
            wfContentPostModel.setWfContentText02(wfContentService.checkLengthData(wfContentPostModel.getWfContentText02(), 4000));
        }
        if (wfContentPostModel.getWfContentText03() != null) {
            wfContentPostModel.setWfContentText03(wfContentService.checkLengthData(wfContentPostModel.getWfContentText03(), 4000));
        }
        if (wfContentPostModel.getWfContentText04() != null) {
            wfContentPostModel.setWfContentText04(wfContentService.checkLengthData(wfContentPostModel.getWfContentText04(), 4000));
        }
        if (wfContentPostModel.getWfContentText05() != null) {
            wfContentPostModel.setWfContentText05(wfContentService.checkLengthData(wfContentPostModel.getWfContentText05(), 4000));
        }
        if (wfContentPostModel.getWfContentText06() != null) {
            wfContentPostModel.setWfContentText06(wfContentService.checkLengthData(wfContentPostModel.getWfContentText06(), 4000));
        }
        if (wfContentPostModel.getWfContentText07() != null) {
            wfContentPostModel.setWfContentText07(wfContentService.checkLengthData(wfContentPostModel.getWfContentText07(), 4000));
        }
        if (wfContentPostModel.getWfContentText08() != null) {
            wfContentPostModel.setWfContentText08(wfContentService.checkLengthData(wfContentPostModel.getWfContentText08(), 4000));
        }
        if (wfContentPostModel.getWfContentText09() != null) {
            wfContentPostModel.setWfContentText09(wfContentService.checkLengthData(wfContentPostModel.getWfContentText09(), 4000));
        }
        if (wfContentPostModel.getWfContentText10() != null) {
            wfContentPostModel.setWfContentText10(wfContentService.checkLengthData(wfContentPostModel.getWfContentText10(), 4000));
        }
        //==================================================================================================================
        return wfContentPostModel;
    }

    public int getMaxContentPoint(int folderId, int wfFolderContentYear, int contentNumber) {
        checkNotNull(folderId, "folderId must not be null");
        checkNotNull(wfFolderContentYear, "wfFolderContentYear must not be null");
        checkNotNull(contentNumber, "contentNumber must not be null");
        return WfContentDaoImpl.getMaxContentPoint(folderId, wfFolderContentYear, contentNumber);
    }

    public WfContent saveLogForCreate(WfContent wfContent, String clientIp) {
        //For Create Log when create WfContent
        String logDescription = this.generateLogForCreateEntity(wfContent);
        LogData logData = new LogData();
        logData.setCreatedBy(wfContent.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfContent.getClass().getName());
        logData.setLinkId(wfContent.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_WF);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return wfContent;
    }

    public WfContent saveLogForUpdate(WfContent wfContentOld, WfContent wfContentNew, String bookDateNew, String clientIp) {
        //For Create Log when update WfContent
        String logDescription = this.generateLogForUpdateEntity(wfContentOld, wfContentNew, bookDateNew);
        LogData logData = new LogData();
        logData.setCreatedBy(wfContentNew.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfContentNew.getClass().getName());
        logData.setLinkId(wfContentNew.getId());
        logData.setModuleName(LogData.MODULE_WF);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return wfContentNew;
    }

    public WfContent saveLogForRemove(WfContent wfContent, String clientIp) {
        //For Create Log when remove WfContent
        String logDescription = this.generateLogForRemoveEntity(wfContent);
        LogData logData = new LogData();
        logData.setCreatedBy(wfContent.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfContent.getClass().getName());
        logData.setLinkId(wfContent.getId());
        logData.setModuleName(LogData.MODULE_WF);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return wfContent;
    }

    //oat-add
    public WfContent saveLogForRemove_MyWork(WfContent myWork, String clientIp) {
        String logDescription = this.generateLogForRemoveMyWork(myWork);
        LogData logData = new LogData();
        logData.setCreatedBy(myWork.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(myWork.getClass().getName());//*****
        logData.setLinkId(myWork.getId());
        logData.setModuleName(LogData.MODULE_MWP);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return myWork;
    }

    public WfContent saveLogForOpen(WfContent wfContent, String clientIp, String type) {
        //For Create Log when open WfContent
        String logDescription = this.generateLogForOpenEntity(wfContent, type);
        LogData logData = new LogData();
        logData.setCreatedBy(wfContent.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(wfContent.getClass().getName());
        logData.setLinkId(wfContent.getId());
        logData.setModuleName(LogData.MODULE_WF);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.open(logData);
        return wfContent;
    }

    private String generateLogForCreateEntity(WfContent wfContent) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("สร้างหนังสือ:");
        descriptionLog.append(" เลขทะเบียน : ");
        descriptionLog.append(Common.noNull(wfContent.getWfContentContentNo(), ""));
        if (!wfContent.getWfContentBookNo().equals("") && wfContent.getWfContentBookNo() != null) {
            descriptionLog.append(" เลขที่หนังสือ : ");
            descriptionLog.append(Common.noNull(wfContent.getWfContentBookNo(), ""));
        }
        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(wfContent.getWfContentTitle(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForUpdateEntity(WfContent wfContentOld, WfContent wfContentNew, String bookDateNew) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("แก้ไขหนังสือ: ");
        if (wfContentOld.getWfContentBookNo() == null) {
            wfContentOld.setWfContentBookNo("");
        }
        if (wfContentNew.getWfContentBookNo() == null) {
            wfContentNew.setWfContentBookNo("");
        }
        if (!wfContentOld.getWfContentBookNo().equals(wfContentNew.getWfContentBookNo())) {
            descriptionLog.append(" เลขที่หนังสือ : ");
            descriptionLog.append(Common.noNull(wfContentOld.getWfContentBookNo(), "(ไม่มีข้อมูล)"));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfContentNew.getWfContentBookNo(), "(ไม่มีข้อมูล)"));
        }

        if (bookDateNew.contains(" ")) {
            String[] splitBookDate = bookDateNew.split(" ");
            bookDateNew = splitBookDate[0];
        }
        String bookDateOld = changeDateToFormat(wfContentOld.getWfContentBookDate().toString(), 0);
        if (!bookDateOld.equals(bookDateNew)) {
            descriptionLog.append(" ลงวันที่ : ");
            descriptionLog.append(bookDateOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(bookDateNew);
        }

        if (wfContentOld.getWfContentSpeed() != wfContentNew.getWfContentSpeed()) {
            String wfContetnSpeedOld = "";
            if (wfContentOld.getWfContentSpeed() == 1) {
                wfContetnSpeedOld = "ปกติ";
            } else if (wfContentOld.getWfContentSpeed() == 2) {
                wfContetnSpeedOld = "ด่วน";
            } else if (wfContentOld.getWfContentSpeed() == 3) {
                wfContetnSpeedOld = "ด่วนมาก";
            } else if (wfContentOld.getWfContentSpeed() == 4) {
                wfContetnSpeedOld = "ด่วนที่สุด";
            }

            String wfContetnSpeedNew = "";
            if (wfContentNew.getWfContentSpeed() == 1) {
                wfContetnSpeedNew = "ปกติ";
            } else if (wfContentNew.getWfContentSpeed() == 2) {
                wfContetnSpeedNew = "ด่วน";
            } else if (wfContentNew.getWfContentSpeed() == 3) {
                wfContetnSpeedNew = "ด่วนมาก";
            } else if (wfContentNew.getWfContentSpeed() == 4) {
                wfContetnSpeedNew = "ด่วนที่สุด";
            }
            descriptionLog.append(" ชั้นเร่งด่วน : ");
            descriptionLog.append(wfContetnSpeedOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfContetnSpeedNew);
        }

        if (wfContentOld.getWfContentSecret() != wfContentNew.getWfContentSecret()) {
            String wfContetnSecretOld = "";
            if (wfContentOld.getWfContentSecret() == 1) {
                wfContetnSecretOld = "ปกติ";
            } else if (wfContentOld.getWfContentSecret() == 2) {
                wfContetnSecretOld = "ลับ";
            } else if (wfContentOld.getWfContentSecret() == 3) {
                wfContetnSecretOld = "ลับมาก";
            } else if (wfContentOld.getWfContentSecret() == 4) {
                wfContetnSecretOld = "ลับที่สุด";
            }

            String wfContetnSecretNew = "";
            if (wfContentNew.getWfContentSecret() == 1) {
                wfContetnSecretNew = "ปกติ";
            } else if (wfContentNew.getWfContentSecret() == 2) {
                wfContetnSecretNew = "ลับ";
            } else if (wfContentNew.getWfContentSecret() == 3) {
                wfContetnSecretNew = "ลับมาก";
            } else if (wfContentNew.getWfContentSecret() == 4) {
                wfContetnSecretNew = "ลับที่สุด";
            }
            descriptionLog.append(" ชั้นความลับ : ");
            descriptionLog.append(wfContetnSecretOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfContetnSecretNew);
        }

        String wfContentFromOld = Common.noNull(wfContentOld.getWfContentFrom(), "(ไม่มีข้อมูล)");
        String wfContentFromNew = Common.noNull(wfContentNew.getWfContentFrom(), "(ไม่มีข้อมูล)");
        if (!wfContentFromOld.equals(wfContentFromNew)) {
            descriptionLog.append(" จาก : ");
            descriptionLog.append(wfContentFromOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfContentFromNew);
        }

        String wfContentToOld = Common.noNull(wfContentOld.getWfContentTo(), "(ไม่มีข้อมูล)");
        String wfContentToNew = Common.noNull(wfContentNew.getWfContentTo(), "(ไม่มีข้อมูล)");
        if (!wfContentToOld.equals(wfContentToNew)) {
            descriptionLog.append(" เรียน : ");
            descriptionLog.append(wfContentToOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(wfContentToNew);
        }

        if (!wfContentOld.getWfContentTitle().equals(wfContentNew.getWfContentTitle())) {
            descriptionLog.append(" เรื่อง : ");
            descriptionLog.append(Common.noNull(wfContentOld.getWfContentTitle(), "(ไม่มีข้อมูล)"));
            descriptionLog.append("เป็น");
            descriptionLog.append(Common.noNull(wfContentNew.getWfContentTitle(), "(ไม่มีข้อมูล)"));
        }

        String referenceOld = Common.noNull(wfContentOld.getWfContentReference(), "(ไม่มีข้อมูล)");
        String referenceNew = Common.noNull(wfContentNew.getWfContentReference(), "(ไม่มีข้อมูล)");
        if (!referenceOld.equals(referenceNew)) {
            descriptionLog.append(" อ้างถึง : ");
            descriptionLog.append(referenceOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(referenceNew);
        }

        String attachmentOld = Common.noNull(wfContentOld.getWfContentAttachment(), "(ไม่มีข้อมูล)");
        String attachmentNew = Common.noNull(wfContentNew.getWfContentAttachment(), "(ไม่มีข้อมูล)");
        if (!attachmentOld.equals(attachmentNew)) {
            descriptionLog.append(" สิ่งที่ส่งมาด้วย : ");
            descriptionLog.append(attachmentOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(attachmentNew);
        }

        String descriptionOld = Common.noNull(wfContentOld.getWfContentAttachment(), "(ไม่มีข้อมูล)");
        String descriptionNew = Common.noNull(wfContentNew.getWfContentAttachment(), "(ไม่มีข้อมูล)");
        if (!descriptionOld.equals(descriptionNew)) {
            descriptionLog.append(" หมายเหตุ : ");
            descriptionLog.append(descriptionOld);
            descriptionLog.append("เป็น");
            descriptionLog.append(descriptionNew);
        }
//oat-edit
//        String str05Old = Common.noNull(wfContentOld.getWfContentStr05(), "(ไม่มีข้อมูล)");
//        String str05New = Common.noNull(wfContentNew.getWfContentStr05(), "(ไม่มีข้อมูล)");
//        if (!str05Old.equals(str05New)) {
//            descriptionLog.append(" หน่วยงานที่ออก : ");
//            descriptionLog.append(str05Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(str05New);
//        }
//
//        String text01Old = Common.noNull(wfContentOld.getWfContentText01(), "(ไม่มีข้อมูล)");
//        String text01New = Common.noNull(wfContentNew.getWfContentText01(), "(ไม่มีข้อมูล)");
//        if (!text01Old.equals(text01New)) {
//            descriptionLog.append(" ชื่อคำสั่ง/ประกาศ/อื่นๆ : ");
//            descriptionLog.append(text01Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(text01New);
//        }
//
//        String str03Old = Common.noNull(wfContentOld.getWfContentStr03(), "(ไม่มีข้อมูล)");
//        String str03New = Common.noNull(wfContentNew.getWfContentStr03(), "(ไม่มีข้อมูล)");
//        if (!str03Old.equals(str03New)) {
//            descriptionLog.append(" ลงนามโดย : ");
//            descriptionLog.append(str03Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(str03New);
//        }
//
//        String str04Old = Common.noNull(wfContentOld.getWfContentStr04(), "(ไม่มีข้อมูล)");
//        String str04New = Common.noNull(wfContentNew.getWfContentStr04(), "(ไม่มีข้อมูล)");
//        if (!str04Old.equals(str04New)) {
//            descriptionLog.append(" ตำแหน่ง : ");
//            descriptionLog.append(str04Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(str04New);
//        }
//
//        String text02Old = Common.noNull(wfContentOld.getWfContentText02(), "(ไม่มีข้อมูล)");
//        String text02New = Common.noNull(wfContentNew.getWfContentText02(), "(ไม่มีข้อมูล)");
//        if (!text02Old.equals(text02New)) {
//            descriptionLog.append(" หมายเหตุ(ลงนามโดย) : ");
//            descriptionLog.append(text02Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(text02New);
//        }
//
//        if (wfContentOld.getWfContentInt01() != wfContentNew.getWfContentInt01()) {
//            WfCommandTypeService wfCommandTypeService = new WfCommandTypeService();
//            String wfContentInt01Old = wfCommandTypeService.getById(wfContentOld.getWfContentInt01()).getCommandTypeName();
//            String wfContentInt01New = wfCommandTypeService.getById(wfContentNew.getWfContentInt01()).getCommandTypeName();
//            descriptionLog.append(" ประเภทหลัก : ");
//            descriptionLog.append(wfContentInt01Old);
//            descriptionLog.append("เป็น");
//            descriptionLog.append(wfContentInt01New);
//        }
        return descriptionLog.toString();
    }

    private String generateLogForRemoveEntity(WfContent wfContent) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("ลบหนังสือ:");
        descriptionLog.append(" เลขทะเบียน : ");
        descriptionLog.append(Common.noNull(wfContent.getWfContentContentNo(), ""));
        if (!wfContent.getWfContentBookNo().equals("") && wfContent.getWfContentBookNo() != null) {
            descriptionLog.append(" เลขที่หนังสือ : ");
            descriptionLog.append(Common.noNull(wfContent.getWfContentBookNo(), ""));
        }
        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(wfContent.getWfContentTitle(), ""));

        return descriptionLog.toString();
    }

    //oat-add
    private String generateLogForRemoveMyWork(WfContent myWork) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("แฟ้มส่วนตัว : ลบเอกสารส่วนตัว");
        descriptionLog.append(" เรื่อง : ");
        descriptionLog.append(Common.noNull(myWork.getWfContentTitle(), ""));

        return descriptionLog.toString();
    }

    private String generateLogForOpenEntity(WfContent wfContent, String type) {
        StringBuilder descriptionLog = new StringBuilder();
        descriptionLog.append("เปิดหนังสือ [");
        descriptionLog.append(type);
        descriptionLog.append("]: เลขทะเบียน :");
        descriptionLog.append(Common.noNull(wfContent.getWfContentContentNo(), ""));

        return descriptionLog.toString();
    }

    public String changeDateToFormat(String strDate, int type) {
        Date tmpDate = new Date();
        Calendar calendar = Calendar.getInstance();
        if (strDate != null && !strDate.equals("")) {
            if (strDate.length() > 10) {
                String tempData1 = strDate.substring(0, 10);
                String tempData2 = strDate.substring(12);
                String[] tempData3 = tempData2.split("\\.");
                String[] tempData = tempData1.split("-");//yyyy-MM-dd
                String[] tempDataTime = tempData3[0].split(":");//hh:mm:ss

                calendar.set(Integer.parseInt(tempData[0]) + 543, Integer.parseInt(tempData[1]) - 1, Integer.parseInt(tempData[2]), Integer.parseInt(tempDataTime[0]), Integer.parseInt(tempDataTime[1]), 0);
                tmpDate = calendar.getTime();
            } else {//no Time
                String[] tempData = strDate.split("-");//yyyy-MM-dd

                calendar.set(Integer.parseInt(tempData[0]) + 543, Integer.parseInt(tempData[1]) - 1, Integer.parseInt(tempData[2]));
                tmpDate = calendar.getTime();
            }

            if (type == 0) {
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                strDate = format1.format(calendar.getTime());
            } else {
                SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                strDate = format1.format(calendar.getTime());
            }
        } else {
            strDate = "(ไม่มีข้อมูล)";
        }
        return strDate;
    }

//    public boolean createCopyFileAttach(FileAttach fileAttach, int userID, int docId) throws FileNotFoundException, IOException {
//        DmsDocument document = new DmsDocument();
//        SubmoduleService submoduleService = new SubmoduleService();
//        int submoduleId = submoduleService.getBySubmoduleCode("wf").getId();
//        DmsDocumentService dmsDocumentService = new DmsDocumentService();
//        DocumentFile documentFile = new DocumentFile();
//        DocumentFileService documentFileService = new DocumentFileService();
//        ParamService paramService = new ParamService();
//        FileAttach fileAttach1 = new FileAttach();
//        FileAttachService fileAttachService = new FileAttachService();
//
//        documentFile.setDocumentFileName(fileAttach.getFileAttachName());
//        documentFile.setModuleId(submoduleId);
//        documentFile.setLinkId(docId);
//        documentFile.setLinkId2(3);
//        documentFile.setCreatedBy(userID);
//        documentFile.setDocumentFileType(fileAttach.getFileAttachType());
//        int documentFileId = documentFileService.createDocumentFile(documentFile);
//
//        fileAttach1.setCreatedBy(userID);
//        fileAttach1.setFileAttachName(fileAttach.getFileAttachName());
//        fileAttach1.setFileAttachType(fileAttach.getFileAttachType());
//        fileAttach1.setLinkType("wf");
//        fileAttach1.setLinkId(documentFileId);
//        fileAttach1 = fileAttachService.create(fileAttach1);
//
//        if (fileAttach1 != null) {
//            String pathDocumentTemp = paramService.getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
//            String fileSave1 = pathDocumentTemp + fileAttach1.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach1.getId()) + fileAttach1.getFileAttachType();
//
//            //String fileSave = fileAttachService.getRealPathFile(fileAttach.getId());
//            String fileSave = pathDocumentTemp + fileAttach.getLinkType() + File.separator + fileAttachService.buildFilePathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
//            File file = new File(fileSave);
//            InputStream imageInFile = new FileInputStream(file);
//            fileAttachService.saveFile(imageInFile, fileSave1);
//
//            fileAttach1.setFileAttachSize(file.length());
//            fileAttach1 = fileAttachService.update(fileAttach1);
//
//        }
//        return true;
//    }
    public List<String> checkRepeatContentNo(int wfContentFolderId, String wfContentContentPre, int wfContentContentYear, int wfContentContentPoint) {
        List<String> resultRepeatContentNo = new ArrayList<String>();
        //check repeat contentNo
        WfContentService wfContentService = new WfContentService();
        WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
        int newWfContentContentNumber = wfContentService.getMaxContentNo(wfContentContentPre, wfContentFolderId, wfContentContentYear);
        int reserveContentNumber = reserveContentNoService.getMaxContentNumber(wfContentFolderId, wfContentContentYear);

        if (newWfContentContentNumber < reserveContentNumber) {
            newWfContentContentNumber = reserveContentNumber;
        }

        //get contentFormat from Param
        Param param = new Param();
        ParamService paramservice = new ParamService();
        param = paramservice.getByParamName("CONTENTFORMAT");
        int contentFormat = Integer.parseInt(param.getParamValue());

        String wfContentNo = wfContentService.convertContentNo(wfContentContentYear, newWfContentContentNumber, wfContentContentPoint, wfContentContentPre, contentFormat);
        int wfContentNumber = newWfContentContentNumber;

        resultRepeatContentNo.add("1");
        resultRepeatContentNo.add(wfContentNo);
        resultRepeatContentNo.add(String.valueOf(wfContentNumber));

        return resultRepeatContentNo;
    }

    public List<String> checkRepeatBookNo(int wfContentFolderId, String wfContentBookPre, int wfContentBookYear, int wfContentBookPoint, int wfFolderBookNoType, int wfFolderAutorun) {
        Param param1 = new Param();
        ParamService paramservice = new ParamService();
        param1 = paramservice.getByParamName("BOOKNOEQUALCONNO");
        String bookNoEqualConNo = param1.getParamValue();

        List<String> resultRepeatBookNo = new ArrayList<String>();
        WfContentService wfContentService = new WfContentService();
        int newWfContentBookNumber = wfContentService.getMaxBookNo(wfContentBookPre, wfContentFolderId, wfContentBookYear);
        if (bookNoEqualConNo.equals("Y")) {
            HashMap<String, String> hashMapContent = wfContentService.maxContentNo(wfContentFolderId);
            newWfContentBookNumber = Integer.parseInt(hashMapContent.get("wfContentContentNumber"));
        } else {
            //get max bookNumber
            newWfContentBookNumber = wfContentService.getMaxBookNo(wfContentBookPre, wfContentFolderId, wfContentBookYear);
        }

        //get bookNoFormat from Param
        Param param = new Param();
        param = paramservice.getByParamName("BOOKNOFORMAT");
        int bookNoFormat = Integer.parseInt(param.getParamValue());

        String wfBookNo = wfContentService.convertBookNo(wfContentBookYear, newWfContentBookNumber, wfContentBookPoint, wfContentBookPre, bookNoFormat, wfFolderBookNoType, wfFolderAutorun);
        int wfBookNumber = newWfContentBookNumber;

        resultRepeatBookNo.add("1");
        resultRepeatBookNo.add(wfBookNo);
        resultRepeatBookNo.add(String.valueOf(wfBookNumber));

        return resultRepeatBookNo;
    }

    public String convertContentNo(int wfContentContentYear, int wfContentContentNumber, int wfContentContentPoint, String wfFolderPreContentNo, int contentFormat) {
        String contentNo = "";
        String contentNumber = "";
        int countContentNumber = String.valueOf(wfContentContentNumber).length();
        for (int i = countContentNumber; i < contentFormat; i++) {
            contentNumber += "0";
        }
        contentNumber += String.valueOf(wfContentContentNumber);
        if (wfFolderPreContentNo != null) {
            contentNo += wfFolderPreContentNo;
        }
        contentNo += contentNumber;
        if (wfContentContentPoint != 0) {
            contentNo += "." + wfContentContentPoint;
        }
        contentNo += "/" + wfContentContentYear;

        return contentNo;
    }

    public String convertBookNo(int wfContentBookYear, int wfContentBookNumber, int wfContentBookPoint, String wfFolderPreBookNo, int bookNoFormat, int wfFolderBookNoType, int wfFolderAutorun) {
        String bookNo = "";
        String bookNumber = "";

        if (wfFolderAutorun == 1 && (wfFolderBookNoType == 1 || wfFolderBookNoType == 2)) {
            //bookNoType 1 = ตัวอักษร/ตัวเลข
            //bookNoType 2 = ตัวอักษร/ตัวเลข/ปี
            //bookNoType 3 = freeStyle
            int countBookNumber = String.valueOf(wfContentBookNumber).length();
            for (int i = countBookNumber; i < bookNoFormat; i++) {
                bookNumber += "0";
            }
            bookNumber += String.valueOf(wfContentBookNumber);
            if (wfFolderPreBookNo != null) {
                bookNo += wfFolderPreBookNo + "/";
            }
            bookNo += bookNumber;
            if (wfContentBookPoint != 0) {
                bookNo += "." + wfContentBookPoint;
            }
            if (wfFolderBookNoType == 2) {
                bookNo += "/" + wfContentBookYear;
            }
        } else if (wfFolderPreBookNo != null) {
            bookNo += wfFolderPreBookNo;
        }
        return bookNo;
    }

    //oat-add
    public List<WfContent> searchByModel(int folderId, WfContentSearchModel wfContentSearchModel, String sort, String dir) {
        checkNotNull(folderId, "folderId must not be null");
        checkNotNull(wfContentSearchModel, "wfContentSearchModel entity must not be null");
        return WfContentDaoImpl.searchByModel(folderId, wfContentSearchModel, sort, dir);
    }

    public int countContentTitle(String contentTitle, int folderId, int year) {
        return WfContentDaoImpl.countContentTitle(contentTitle, folderId, year);
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

    public WfContent getByContentNumber(int contentNumber, int folderId) {
        return WfContentDaoImpl.getByContentNumber(contentNumber, folderId);
    }

    public List<WfContent> listMyWork(int userId) {
        checkNotNull(userId, "userId must not be null");
        return WfContentDaoImpl.listMyWork(userId);
    }

    public void updateWorkflow(WfContent wfContentOld, WfContent wfContentNew, int userId) {
        boolean updated = false;
        if (!wfContentOld.getWfContentTitle().equals(wfContentNew.getWfContentTitle())) {
            updated = true;
        }
        if (wfContentNew.getWfContentDescription() != null) {
            if (wfContentOld.getWfContentDescription() != null) {
                if (!wfContentOld.getWfContentDescription().equals(wfContentNew.getWfContentDescription())) {
                    updated = true;
                }
            } else {
                updated = true;
            }
        } else {
            if (wfContentOld.getWfContentDescription() != null) {
                updated = true;
            }
        }

        if (updated) {
            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.getByIdNotRemoved(wfContentNew.getWorkflowId());
            workflow.setWorkflowTitle(wfContentNew.getWfContentTitle());
            workflow.setWorkflowStr02(wfContentNew.getWfContentDescription());
            workflow.setUpdatedBy(userId);
            workflowService.update(workflow);
        }
    }

    public int countBookNo(String bookNo, int folderId, int year) {
        return WfContentDaoImpl.countBookNo(bookNo, folderId, year);
    }

    public HashMap<String, String> checkMaxBookNoSharedFolder(int wfFolderAutorun, int folderId, String wfContentBookPre, int year, int wfContentBookPoint, int wfFolderBookNoType) {
        ParamService paramservice = new ParamService();
        //String bookNoEqualConNo = paramservice.getByParamName("BOOKNOEQUALCONNO").getParamValue();
        int bookNoFormat = Integer.parseInt(paramservice.getByParamName("BOOKNOFORMAT").getParamValue());

        //int chkRepeatBookNoMsg = 0;
        int wfBookNumber = getMaxContentNo(null, folderId, year) - 1;//cause created shared
        String wfContentBookNo = convertBookNo(year, wfBookNumber, wfContentBookPoint, wfContentBookPre, bookNoFormat, wfFolderBookNoType, wfFolderAutorun);

        HashMap<String, String> checkMaxBookNo = new HashMap<String, String>();
        //checkMaxBookNo.put("chkRepeatBookNoMsg", String.valueOf(chkRepeatBookNoMsg));
        checkMaxBookNo.put("wfContentBookNo", wfContentBookNo);
        checkMaxBookNo.put("wfBookNumber", String.valueOf(wfBookNumber));

        return checkMaxBookNo;
    }

    public List<WfContent> listByBookNo(String bookNo, int folderId, int year) {
        return WfContentDaoImpl.listByBookNo(bookNo, folderId, year);
    }

    public String genFullText(int documentId) throws IOException {
        String result;
        List<String> listFileAttachName = new ArrayList<String>();
        List<String> fulltext = new ArrayList<String>();
        FileAttachService fileAttachService = new FileAttachService();
        List<FileAttach> listFileAttach = fileAttachService.listAllByLinkTypeLinkId("dms", documentId, "createdDate", "asc");
        if (!listFileAttach.isEmpty()) {
            for (FileAttach fileAttach : listFileAttach) {
                String pathDocumentHttp = new ParamService().getByParamName("PATH_DOCUMENT_TEMP").getParamValue();
                String url = pathDocumentHttp + fileAttach.getLinkType() + "/" + fileAttachService.buildHtmlPathExt(fileAttach.getId()) + fileAttach.getFileAttachType();
                listFileAttachName.add(fileAttach.getFileAttachName());
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".TXT")) {
                    File file = new File(url);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) {
                        fulltext.add(sCurrentLine);
                    }
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOCX")) {
                    InputStream in = new FileInputStream(url);
                    XWPFDocument doc = new XWPFDocument(in);
                    XWPFWordExtractor ex = new XWPFWordExtractor(doc);
                    String text = ex.getText();
                    fulltext.add(text);
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".DOC")) {
                    File file = new File(url);
                    NPOIFSFileSystem fs = new NPOIFSFileSystem(file);
                    WordExtractor extractor = new WordExtractor(fs.getRoot());
                    for (String rawText : extractor.getParagraphText()) {
                        String text = extractor.stripFields(rawText);
                        fulltext.add(text);
                    }
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLS")) {
                    File file = new File(url);
                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                    HSSFSheet sheet = wb.getSheetAt(0);
                    HSSFRow row;
                    HSSFCell cell;
                    int rows; // No of rows
                    rows = sheet.getPhysicalNumberOfRows();
                    int cols = 0; // No of columns
                    int tmp = 0;
                    for (int i = 0; i < 10 || i < rows; i++) {
                        row = sheet.getRow(i);
                        if (row != null) {
                            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                            if (tmp > cols) {
                                cols = tmp;
                            }
                        }
                    }
                    for (int r = 0; r < rows; r++) {
                        row = sheet.getRow(r);
                        if (row != null) {
                            for (int c = 0; c < cols; c++) {
                                cell = row.getCell((short) c);
                                if (cell != null) {
                                    fulltext.add(cell.toString());
                                }
                            }
                        }
                    }
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".XLSX")) {
                    InputStream ExcelFileToRead = new FileInputStream(url);
                    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
                    XSSFSheet sheet = wb.getSheetAt(0);
                    XSSFRow row;
                    XSSFCell cell;
                    Iterator rows = sheet.rowIterator();
                    while (rows.hasNext()) {
                        row = (XSSFRow) rows.next();
                        Iterator cells = row.cellIterator();
                        while (cells.hasNext()) {
                            cell = (XSSFCell) cells.next();
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                fulltext.add(cell.getStringCellValue());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            }
                        }
                    }
                }
                if (fileAttach.getFileAttachType().equalsIgnoreCase(".PDF")) {
                    File myFile = new File(url);
                    try (PDDocument doc = PDDocument.load(myFile)) {
                        PDFTextStripper stripper = new PDFTextStripper();
                        String text = stripper.getText(doc);
                        fulltext.add(text);
                    } catch (Exception ex) {
                        System.out.println("can not read text in pdf file");
                    }
                }
            }
        }
        String tmp = "";
        tmp = tmp + listFileAttachName;
        tmp = tmp + fulltext;
        result = tmp.replaceAll("null", " ");
        return result;
    }

    public List<WfContent> listByDocumentId(int documentId) {
        checkNotNull(documentId, "documentId must not be null");
        return WfContentDaoImpl.listByDocumentId(documentId);
    }

}
