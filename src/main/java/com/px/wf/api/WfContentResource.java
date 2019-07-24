package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Organize;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.model.StructureModel;
import com.px.admin.service.OrganizeService;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.service.DmsDocumentService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.wf.entity.WfContent;
import com.px.wf.entity.WfFolder;
import com.px.wf.entity.WfReserveContentNo;
import com.px.mwp.entity.Workflow;
import com.px.wf.model.WfContentModel;
import com.px.wf.model.WfContentModel_groupContentNoAndBookNo;
import com.px.wf.service.WfContentService;
import com.px.wf.service.WfFolderService;
import com.px.wf.service.WfReserveContentNoService;
import com.px.mwp.service.WorkflowService;
import com.px.share.entity.Param;
import com.px.share.entity.TempTable;
import com.px.share.service.FileAttachService;
import com.px.share.service.ParamService;
import com.px.share.service.TempTableService;
import com.px.share.util.Common;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import com.px.wf.model.EmailModel;
import com.px.wf.model.WfContentModel_groupWfContentAndWorkflowfinish;
import com.px.wf.model.WfContentSearchModel;
import com.px.wf.model.WfLastNumberModel;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Mali
 */
@Api(value = "WfContent หนังสือ")
@Path("v1/wfContents")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfContentResource {

    @Context
    HttpHeaders httpHeaders;

    private static final Logger LOG = Logger.getLogger(WfContentResource.class.getName());

    @ApiOperation(
            value = "สร้างข้อมูลหนังสือ",
            notes = "สร้างข้อมูลหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfContent created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfContentModel wfContentPostModel,
            @ApiParam(name = "preBookNoIndex", value = "preBookNoIndex", required = true)
            @QueryParam("preBookNoIndex") int preBookNoIndex,
            @ApiParam(name = "sharedFolderId", value = "sharedFolderId", required = true)
            @QueryParam("sharedFolderId") int sharedFolderId
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfContentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {

            WfContent wfContent = new WfContent();
            //get Data from WfContentPostModel
            UserProfile userProfile = new UserProfile();
            //get Data from UserProfile
            WfFolder wfFolder = new WfFolder();
            //get Data from WfFolder

            //check length Data
            WfContentService wfContentService = new WfContentService();
            wfContentPostModel = wfContentService.checkLengthField(wfContentPostModel);

            String wfContentNo = wfContentPostModel.getWfContentContentNo();
            int wfContentNumber = wfContentPostModel.getWfContentContentNumber();
            String wfBookNo = wfContentPostModel.getWfContentBookNo();
            int wfBookNumber = 0;
            if (wfContentPostModel.getWfContentBookNumber() != null) {
                wfBookNumber = wfContentPostModel.getWfContentBookNumber();
            }
            int chkRepeatContentNoMsg = 0;
            int chkRepeatBookNoMsg = 0;

            //userID = 1;
            wfContent.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContentPostModel.getWfContentBookDate() != null) {
                wfContent.setWfContentBookDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentBookDate()));
            } else {
                wfContent.setWfContentBookDate(null);
            }
            wfContent.setWfContentContentDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentContentDate()));
            if (wfContentPostModel.getWfContentExpireDate() != null) {
                wfContent.setWfContentExpireDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentExpireDate()));
            } else {
                wfContent.setWfContentExpireDate(null);
            }

            if (wfContent != null) {
                //==============userProfile ======================
                UserProfileService userProfileService = new UserProfileService();
                userProfile = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                WfContent wfContentCreate = wfContentService.create(wfContent);

                //==============wffolder=============================
                WfFolderService wfFolderService = new WfFolderService();
                wfFolder = wfFolderService.getByIdNotRemoved(wfContentPostModel.getWfContentFolderId());
                int wfFolderBookNoType = wfFolder.getWfFolderBookNoType();
                int wfFolderAutorun = wfFolder.getWfFolderAutorun();

                //split ContentNo
                HashMap<String, String> hashMapContentNo1 = new HashMap<String, String>();
                hashMapContentNo1 = wfContentService.splitDataContentNo(wfContentNo, wfFolder);
                wfContentNo = hashMapContentNo1.get("wfContentContentNo");
                wfContentNumber = Integer.parseInt(hashMapContentNo1.get("wfContentNumber"));
                int wfContentPoint = Integer.parseInt(hashMapContentNo1.get("wfContentPoint"));
                String wfContentPre = hashMapContentNo1.get("wfContentPre");
                int wfContentYear = Integer.parseInt(hashMapContentNo1.get("wfContentYear"));

                //spilt bookNo
                HashMap<String, String> hashMapBookNo1 = new HashMap<String, String>();
                hashMapBookNo1 = wfContentService.splitDataBookNo(wfBookNo, wfFolder, preBookNoIndex);
                wfBookNo = hashMapBookNo1.get("wfContentBookNo");
                wfBookNumber = Integer.parseInt(hashMapBookNo1.get("wfBookNumber"));
                int wfBookPoint = Integer.parseInt(hashMapBookNo1.get("wfBookPoint"));
                String wfBookPre = hashMapBookNo1.get("wfBookPre");
                int wfBookYear = Integer.parseInt(hashMapBookNo1.get("wfContentbookYear"));

                //check repeat contentNo
                HashMap<String, String> hashMapContentNo = wfContentService.checkMaxContentNo(wfContentNo, wfContentPostModel.getWfContentFolderId(), wfContentPre, wfContentYear, wfContentPoint, Integer.parseInt(httpHeaders.getHeaderString("userID")));

                chkRepeatContentNoMsg = Integer.parseInt(hashMapContentNo.get("chkRepeatContentNoMsg"));
                wfContentNo = hashMapContentNo.get("wfContentNo");
                if (!hashMapContentNo.get("wfContentNumber").equals("0")) {
                    wfContentNumber = Integer.parseInt(hashMapContentNo.get("wfContentNumber"));
                    wfContentPoint = Integer.parseInt(hashMapContentNo.get("wfContentPoint"));
                    wfContentYear = Integer.parseInt(hashMapContentNo.get("wfContentYear"));
                }

                //check repeat bookNo
                if (sharedFolderId == 0) {
                    if (wfBookNumber > 0) {
                        HashMap<String, String> hashMapBookNo = wfContentService.checkMaxBookNo(wfFolderAutorun, wfBookNo, wfContentPostModel.getWfContentFolderId(), wfBookPre, wfBookYear, wfBookPoint, wfFolderBookNoType, wfContentNumber);

                        chkRepeatBookNoMsg = Integer.parseInt(hashMapBookNo.get("chkRepeatBookNoMsg"));
                        wfBookNo = hashMapBookNo.get("wfContentBookNo");
                        if (!hashMapBookNo.get("wfBookNumber").equals("0")) {
                            wfBookNumber = Integer.parseInt(hashMapBookNo.get("wfBookNumber"));
                        }
                    }
                } else {
                    HashMap<String, String> hashMapBookNo = wfContentService.checkMaxBookNoSharedFolder(wfFolderAutorun, sharedFolderId, wfBookPre, wfBookYear, wfBookPoint, wfFolderBookNoType);
                    wfBookNo = hashMapBookNo.get("wfContentBookNo");
                    wfBookNumber = Integer.parseInt(hashMapBookNo.get("wfBookNumber"));
                }

                //=======create WFContent ======================
                wfContentCreate.setOrderNo(wfContentCreate.getId());
                wfContentCreate.setInboxId(wfContentPostModel.getInboxId());
                wfContentCreate.setWfDocumentId(wfContentPostModel.getWfDocumentId());
                wfContentCreate.setWfContentFolderId(wfContentPostModel.getWfContentFolderId());
                wfContentCreate.setWorkflowId(wfContentPostModel.getWorkflowId());
                wfContentCreate.setWfContentContentPre(wfContentPre);
                wfContentCreate.setWfContentYear(wfContentYear);
                wfContentCreate.setWfContentContentNumber(wfContentNumber);
                wfContentCreate.setWfContentContentPoint(wfContentPoint);
                wfContentCreate.setWfContentContentTime(wfContentPostModel.getWfContentContentTime());
                wfContentCreate.setWfContentContentNo(wfContentNo);
                wfContentCreate.setWfContentBookYear(wfBookYear);
                wfContentCreate.setWfContentBookPre(wfBookPre);
                wfContentCreate.setWfContentBookNo(wfBookNo);
                wfContentCreate.setWfContentBookNumber(wfBookNumber);
                wfContentCreate.setWfContentBookPoint(wfBookPoint);
                wfContentCreate.setWfContentFrom(wfContentPostModel.getWfContentFrom());
                wfContentCreate.setWfContentTo(wfContentPostModel.getWfContentTo());
                wfContentCreate.setWfContentTitle(wfContentPostModel.getWfContentTitle());
                wfContentCreate.setWfContentSpeed(wfContentPostModel.getWfContentSpeed());
                wfContentCreate.setWfContentSecret(wfContentPostModel.getWfContentSecret());
                wfContentCreate.setWfContentDescription(wfContentPostModel.getWfContentDescription());
                wfContentCreate.setWfContentReference(wfContentPostModel.getWfContentReference());
                wfContentCreate.setWfContentAttachment(wfContentPostModel.getWfContentAttachment());
                wfContentCreate.setWfContentOwnername(userProfile.getUserProfileFullName());
                wfContentCreate.setWfContentStr01(wfContentPostModel.getWfContentStr01());
                wfContentCreate.setWfContentStr02(wfContentPostModel.getWfContentStr02());//พ.ศ. 
                wfContentCreate.setWfContentStr03(wfContentPostModel.getWfContentStr03());//ลงนาม   
                wfContentCreate.setWfContentStr04(wfContentPostModel.getWfContentStr04());//ตำแหน่ง
                wfContentCreate.setWfContentStr05(wfContentPostModel.getWfContentStr05());//หน่วยงานที่ออก 
                wfContentCreate.setWfContentStr06(wfContentPostModel.getWfContentStr06());
                wfContentCreate.setWfContentStr07(wfContentPostModel.getWfContentStr07());
                wfContentCreate.setWfContentStr08(wfContentPostModel.getWfContentStr08());
                wfContentCreate.setWfContentStr09(wfContentPostModel.getWfContentStr09());
                wfContentCreate.setWfContentStr10(wfContentPostModel.getWfContentStr10());
                wfContentCreate.setWfContentText01(wfContentPostModel.getWfContentText01());//ชื่อคำสั่ง/ประกาศ/อื่นๆ
                wfContentCreate.setWfContentText02(wfContentPostModel.getWfContentText02());//หมายเหตุ
                wfContentCreate.setWfContentText03(wfContentPostModel.getWfContentText03());
                wfContentCreate.setWfContentText04(wfContentPostModel.getWfContentText04());
                wfContentCreate.setWfContentText05(wfContentPostModel.getWfContentText05());
                wfContentCreate.setWfContentText06(wfContentPostModel.getWfContentText06());
                wfContentCreate.setWfContentText07(wfContentPostModel.getWfContentText07());
                wfContentCreate.setWfContentText08(wfContentPostModel.getWfContentText08());
                wfContentCreate.setWfContentText09(wfContentPostModel.getWfContentText09());
                wfContentCreate.setWfContentText10(wfContentPostModel.getWfContentText10());
                wfContentCreate.setWfContentInt01(wfContentPostModel.getWfContentInt01());//ประเภท 
                wfContentCreate.setWfContentInt02(wfContentPostModel.getWfContentInt02());
                wfContentCreate.setWfContentInt03(wfContentPostModel.getWfContentInt03());
                wfContentCreate.setWfContentInt04(wfContentPostModel.getWfContentInt04());
                wfContentCreate.setWfContentInt05(wfContentPostModel.getWfContentInt05());
                wfContentCreate.setWfContentInt06(wfContentPostModel.getWfContentInt06());
                wfContentCreate.setWfContentInt07(wfContentPostModel.getWfContentInt07());
                wfContentCreate.setWfContentInt08(wfContentPostModel.getWfContentInt08());
                wfContentCreate.setWfContentInt09(wfContentPostModel.getWfContentInt09());
                wfContentCreate.setWfContentInt10(wfContentPostModel.getWfContentInt10());
                wfContentCreate.setWfContentDate01(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate01()));
                wfContentCreate.setWfContentDate02(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate02()));
                wfContentCreate.setWfContentDate03(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate03()));
                wfContentCreate.setWfContentDate04(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate04()));
                wfContentCreate.setWfContentDate05(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate05()));
                wfContentCreate.setWfContentDate06(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate06()));
                wfContentCreate.setWfContentDate07(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate07()));
                wfContentCreate.setWfContentDate08(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate08()));
                wfContentCreate.setWfContentDate09(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate09()));
                wfContentCreate.setWfContentDate10(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate10()));
                wfContentCreate.setConvertId(0);
                wfContentCreate.setfOrgId(0);
                wfContentCreate.setfTransMainId(0);
                wfContentCreate.setfType(0);

                wfContentCreate = wfContentService.update(wfContentCreate);
                responseData.put("data", wfContentService.tranformToModel(wfContentCreate));

                //LogData For Create
                wfContentService.saveLogForCreate(wfContentCreate, httpHeaders.getHeaderString("clientIp"));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            if (chkRepeatContentNoMsg == 0 && chkRepeatBookNoMsg == 0) {
                responseData.put("message", "WfContent successfully.");
            } else if (chkRepeatContentNoMsg == 1 && chkRepeatBookNoMsg == 0) {
                responseData.put("message", "WfContent successfully. This ContentNo is used. New ContentNo = " + wfContentNo);
            } else if (chkRepeatContentNoMsg == 0 && chkRepeatBookNoMsg == 1) {
                responseData.put("message", "WfContent successfully. This BookNo is used. New BookNo = " + wfBookNo);
            } else if (chkRepeatContentNoMsg == 1 && chkRepeatBookNoMsg == 1) {
                responseData.put("message", "WfContent successfully. ContentNo and BookNo are used. New ContentNo = " + wfContentNo + " and New BookNo = " + wfBookNo);
            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลหนังสือ ด้วย รหัสหนังสือ",
            notes = "ขอข้อมูลหนังสือ ด้วย รหัสหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent by id success.")
        ,
        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getById...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(id);
            if (wfContent != null) {
                status = Response.Status.OK;
                responseData.put("data", wfContentService.tranformToModelGroupWfContentAndWorkflowfinish(wfContent, true, true));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "แก้ไขข้อมูลหนังสือ",
            notes = "แก้ไขข้อมูลหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            WfContentModel wfContentPostModel,
            @ApiParam(name = "preBookNoIndex", value = "preBookNoIndex", required = true)
            @QueryParam("preBookNoIndex") int preBookNoIndex
    ) {
        LOG.info("update...");
        LOG.info("id = " + wfContentPostModel.getId());
        LOG.info("bookNo = " + wfContentPostModel.getWfContentBookNo());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(wfContentPostModel.getId());
            WfContent wfContentOld = wfContentService.getById(wfContentPostModel.getId());
            //check length Data
            //oat-edit//wfContentPostModel = wfContentService.checkLengthField(wfContentPostModel);
            String wfBookNo = wfContentPostModel.getWfContentBookNo();

            int wfBookNumber = 0;
            //==============wffolder=============================
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.getById(wfContentPostModel.getWfContentFolderId());
//            int wfFolderBookNoType = wfFolder.getWfFolderBookNoType();
//            int wfFolderAutorun = wfFolder.getWfFolderAutorun();
            int chkRepeatBookNo = 0;

//            //check repeat bookNo
//            if (wfFolderAutorun == 1) {
//                WfContent bookNoInDb = wfContentService.getById(wfContentPostModel.getId());
//                String strBookNoInDb = bookNoInDb.getWfContentBookNo();
//                if (bookNoInDb != null && strBookNoInDb.equals(wfContentPostModel.getWfContentBookNo())) {
//                    chkRepeatBookNo = 0;
//                } else {
//                    WfContent bookNoInDb2 = wfContentService.getByBookNo(wfContentPostModel.getWfContentBookNo(), wfContentPostModel.getWfContentFolderId());
//                    if (bookNoInDb2 == null) {
//                        chkRepeatBookNo = 0;
//                    } else {
//                        chkRepeatBookNo = 1;
//                    }
//                }
//            }oat-edit
            if (wfContent != null && chkRepeatBookNo == 0) {
                //spilt bookNo
                //[GHB]
                HashMap<String, String> hashMapBookNo1 = new HashMap<String, String>();
                hashMapBookNo1 = wfContentService.splitDataBookNo(wfBookNo, wfFolder, preBookNoIndex);
                //wfBookNo = hashMapBookNo1.get("wfContentBookNo");
                wfBookNumber = Integer.parseInt(hashMapBookNo1.get("wfBookNumber"));
                int wfBookPoint = Integer.parseInt(hashMapBookNo1.get("wfBookPoint"));
                //String wfBookPre = hashMapBookNo1.get("wfBookPre");
                String wfBookPre = wfContentPostModel.getWfContentBookPre();
                int wfBookYear = Integer.parseInt(hashMapBookNo1.get("wfContentbookYear"));

                wfContent.setWfContentBookPre(wfBookPre);
                wfContent.setWfContentBookYear(wfBookYear);
                wfContent.setWfContentBookNo(wfBookNo);
                wfContent.setWfContentBookNumber(wfBookNumber);
                wfContent.setWfContentBookPoint(wfBookPoint);
                if (wfContentPostModel.getWfContentBookDate() != null) {
                    wfContent.setWfContentBookDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentBookDate()));
                } else {
                    wfContent.setWfContentBookDate(null);
                }
                wfContent.setWfContentFrom(wfContentPostModel.getWfContentFrom());
                wfContent.setWfContentTo(wfContentPostModel.getWfContentTo());
                wfContent.setWfContentTitle(wfContentPostModel.getWfContentTitle());
                wfContent.setWfContentSpeed(wfContentPostModel.getWfContentSpeed());
                wfContent.setWfContentSecret(wfContentPostModel.getWfContentSecret());
                wfContent.setWfContentDescription(wfContentPostModel.getWfContentDescription());
                wfContent.setWfContentReference(wfContentPostModel.getWfContentReference());
                wfContent.setWfContentAttachment(wfContentPostModel.getWfContentAttachment());
                wfContent.setWfContentExpireDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentExpireDate()));
                wfContent.setWfContentOwnername(wfContentPostModel.getWfContentOwnername());
                wfContent.setWfDocumentId(wfContentPostModel.getWfDocumentId());
                wfContent.setWorkflowId(wfContentPostModel.getWorkflowId());
                wfContent.setWfContentStr01(wfContentPostModel.getWfContentStr01());
                wfContent.setWfContentStr02(wfContentPostModel.getWfContentStr02());
                wfContent.setWfContentStr03(wfContentPostModel.getWfContentStr03());
                wfContent.setWfContentStr04(wfContentPostModel.getWfContentStr04());
                wfContent.setWfContentStr05(wfContentPostModel.getWfContentStr05());
                wfContent.setWfContentStr06(wfContentPostModel.getWfContentStr06());
                wfContent.setWfContentStr07(wfContentPostModel.getWfContentStr07());
                wfContent.setWfContentStr08(wfContentPostModel.getWfContentStr08());
                wfContent.setWfContentStr09(wfContentPostModel.getWfContentStr09());
                wfContent.setWfContentStr10(wfContentPostModel.getWfContentStr10());
                wfContent.setWfContentText01(wfContentPostModel.getWfContentText01());
                wfContent.setWfContentText02(wfContentPostModel.getWfContentText02());
                wfContent.setWfContentText03(wfContentPostModel.getWfContentText03());
                wfContent.setWfContentText04(wfContentPostModel.getWfContentText04());
                wfContent.setWfContentText05(wfContentPostModel.getWfContentText05());
                wfContent.setWfContentText06(wfContentPostModel.getWfContentText06());
                wfContent.setWfContentText07(wfContentPostModel.getWfContentText07());
                wfContent.setWfContentText08(wfContentPostModel.getWfContentText08());
                wfContent.setWfContentText09(wfContentPostModel.getWfContentText09());
                wfContent.setWfContentText10(wfContentPostModel.getWfContentText10());
                wfContent.setWfContentInt01(wfContentPostModel.getWfContentInt01());
                wfContent.setWfContentInt02(wfContentPostModel.getWfContentInt02());
                wfContent.setWfContentInt03(wfContentPostModel.getWfContentInt03());
                wfContent.setWfContentInt04(wfContentPostModel.getWfContentInt04());
                wfContent.setWfContentInt05(wfContentPostModel.getWfContentInt05());
                wfContent.setWfContentInt06(wfContentPostModel.getWfContentInt06());
                wfContent.setWfContentInt07(wfContentPostModel.getWfContentInt07());
                wfContent.setWfContentInt08(wfContentPostModel.getWfContentInt08());
                wfContent.setWfContentInt09(wfContentPostModel.getWfContentInt09());
                wfContent.setWfContentInt10(wfContentPostModel.getWfContentInt10());
                wfContent.setWfContentDate01(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate01()));
                wfContent.setWfContentDate02(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate02()));
                wfContent.setWfContentDate03(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate03()));
                wfContent.setWfContentDate04(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate04()));
                wfContent.setWfContentDate05(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate05()));
                wfContent.setWfContentDate06(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate06()));
                wfContent.setWfContentDate07(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate07()));
                wfContent.setWfContentDate08(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate08()));
                wfContent.setWfContentDate09(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate09()));
                wfContent.setWfContentDate10(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate10()));
                wfContent.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                //wfContentService.updateWorkflow(wfContentOld, wfContent, Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContent = wfContentService.update(wfContent);

                //oat-add GHB
                if (wfContentOld.getWfContentDate01() == null && wfContentPostModel.getWfContentDate01() != null) {
                    WorkflowService workflowService = new WorkflowService();
                    Workflow workflow = workflowService.getById(wfContentOld.getWorkflowId());
                    if (workflow != null) {
                        workflow.setWorkflowStr02(wfContentPostModel.getWfContentDate01().substring(0, 16));
                        workflow.setWorkflowStr04(wfContentPostModel.getWfContentBookNo());
                        workflowService.update(workflow);
                    }
                }

                status = Response.Status.OK;
                responseData.put("data", wfContentService.tranformToModel(wfContent));
                responseData.put("message", "");
                responseData.put("success", true);

                //LogData For Update
                wfContentService.saveLogForUpdate(wfContentOld, wfContent, wfContentPostModel.getWfContentBookDate(), httpHeaders.getHeaderString("clientIp"));
            } else if (wfContent != null && chkRepeatBookNo == 1) {
                status = Response.Status.OK;
                responseData.put("data", "");
                responseData.put("message", "This BookNo is used. Please check BookNo again.");
                responseData.put("success", true);
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "ลบข้อมูลหนังสือ",
//            notes = "ลบข้อมูลหนังสือ",
//            response = WfContentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "WfContent deleted by id success.")
//        ,
//        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @DELETE
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/{id}")
//    public Response remove(
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
//            @PathParam("id") int id
//    ) {
//        LOG.info("remove...");
//        LOG.info("id = " + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfContent by id not found in the database.");
//        try {
//            WfContentService wfContentService = new WfContentService();
//            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
//            ParamService paramService = new ParamService();
//            WfReserveContentNo reserveContentNo = new WfReserveContentNo();
//
//            String deleteToReserve = paramService.getByParamName("DELETETORESERVE").getParamValue();
//
//            Calendar calendar = Calendar.getInstance();
//            int yearToday = calendar.get(calendar.YEAR) + 543;
//
//            WfContent wfContent = wfContentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
//            int maxContentNoLast = wfContentService.getMaxContentNo(wfContent.getWfContentContentPre(), wfContent.getWfContentFolderId(), wfContent.getWfContentContentYear());
//            //get max reserveContentNumber
//            int reserveContentNumber = reserveContentNoService.getMaxContentNumber(wfContent.getWfContentFolderId(), wfContent.getWfContentContentYear());
//
//            if (maxContentNoLast < reserveContentNumber) {
//                maxContentNoLast = reserveContentNumber;
//            }
//
//            if (wfContent != null) {
//                if ((maxContentNoLast > wfContent.getWfContentContentNumber() && yearToday == wfContent.getWfContentContentYear())
//                        || (yearToday > wfContent.getWfContentContentYear())) {
//                    if (deleteToReserve.equals("Y")) {
//                        reserveContentNo.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                        reserveContentNo.setReserveContentNoFolderId(wfContent.getWfContentFolderId());
//                        reserveContentNo.setReserveContentNoContentNo(wfContent.getWfContentContentNo());
//                        reserveContentNo.setReserveContentNoContentDate(LocalDateTime.now());
//                        reserveContentNo.setReserveContentNoContentTime(String.valueOf(new Formatter().format("%tR", calendar)));
//                        reserveContentNo.setReserveContentNoUserId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                        reserveContentNo.setReserveContentNoContentYear(wfContent.getWfContentContentYear());
//                        reserveContentNo.setReserveContentNoContentNumber(wfContent.getWfContentContentNumber());
//                        reserveContentNo = reserveContentNoService.create(reserveContentNo);
//                    }
//                }
//                status = Response.Status.OK;
//                responseData.put("data", wfContentService.tranformToModel(wfContent));
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//
//            //LogData For Remove
//            wfContentService.saveLogForRemove(wfContent, httpHeaders.getHeaderString("clientIp"));
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    //oat-edit cause remove can restore not delete then should not make reserve
    @ApiOperation(
            value = "ลบข้อมูลหนังสือ",
            notes = "ลบข้อมูลหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContent != null) {
                status = Response.Status.OK;
                responseData.put("data", wfContentService.tranformToModel(wfContent));
                responseData.put("message", "");
            }
            responseData.put("success", true);
            //LogData For Remove
            wfContentService.saveLogForRemove(wfContent, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลเลขทะเบียนล่าสุด",
            notes = "ขอข้อมูลเลขทะเบียนล่าสุด",
            response = WfContentModel_groupContentNoAndBookNo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "max ContentNo of WfContent by id success.")
        ,
        @ApiResponse(code = 404, message = "max ContentNo of WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/maxContentNo/{folderId}")
    public Response getMaxContentNo(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId
    ) {
        LOG.info("getMaxContentNo...");
        LOG.info("folderId = " + folderId);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "max ContentNo of WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            HashMap<String, String> hashMapContent = wfContentService.maxContentNo(folderId);

            int yearToday = Integer.parseInt(hashMapContent.get("yearToday"));
            int wfContentContentNumber = Integer.parseInt(hashMapContent.get("wfContentContentNumber"));
            int wfContentContentPoint = Integer.parseInt(hashMapContent.get("wfContentContentPoint"));
            String wfFolderPreContentNo = hashMapContent.get("wfFolderPreContentNo");
            int contentFormat = Integer.parseInt(hashMapContent.get("contentFormat"));

            String tmpContentNo = wfContentService.convertContentNo(yearToday, wfContentContentNumber, wfContentContentPoint, wfFolderPreContentNo, contentFormat);

            WfContentModel_groupContentNoAndBookNo wfContentModel_groupContentNoAndBookNo = wfContentService.tranformToModelGroupContentNoAndBookNo(tmpContentNo, wfContentContentNumber, wfContentContentPoint, yearToday, wfFolderPreContentNo);

            status = Response.Status.OK;
            responseData.put("data", wfContentModel_groupContentNoAndBookNo);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลเลขที่หนังสือล่าสุด",
            notes = "ขอข้อมูลเลขที่หนังสือล่าสุด",
            response = WfContentModel_groupContentNoAndBookNo.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "max BookNo of WfContent by id success.")
        ,
        @ApiResponse(code = 404, message = "max BookNo of WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/maxBookNo/{folderId}")
    public Response getMaxBookNo(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId
    ) {
        LOG.info("getMaxBookNo...");
        LOG.info("folderId = " + folderId);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "max BookNo of WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            HashMap<String, String> hashMapBook = wfContentService.maxBookNo(folderId);

            int yearToday = Integer.parseInt(hashMapBook.get("yearToday"));
            int wfContentBookNumber = Integer.parseInt(hashMapBook.get("wfContentBookNumber"));
            int wfContentBookPoint = Integer.parseInt(hashMapBook.get("wfContentBookPoint"));
            String wfFolderPreBookNo = hashMapBook.get("wfFolderPreBookNo");
            int bookNoFormat = Integer.parseInt(hashMapBook.get("bookNoFormat"));
            int wfFolderBookNoType = Integer.parseInt(hashMapBook.get("wfFolderBookNoType"));
            int wfFolderAutorun = Integer.parseInt(hashMapBook.get("wfFolderAutorun"));

            String tmpBookNo = wfContentService.convertBookNo(yearToday, wfContentBookNumber, wfContentBookPoint, wfFolderPreBookNo, bookNoFormat, wfFolderBookNoType, wfFolderAutorun);

            WfContentModel_groupContentNoAndBookNo wfContentModel_groupContentNoAndBookNo = wfContentService.tranformToModelGroupContentNoAndBookNo(tmpBookNo, wfContentBookNumber, wfContentBookPoint, yearToday, wfFolderPreBookNo);

            if (wfContentModel_groupContentNoAndBookNo != null) {
                status = Response.Status.OK;
                responseData.put("data", wfContentModel_groupContentNoAndBookNo);
                responseData.put("message", "");
            }

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for cancel WfContent finish in Workflow",
            notes = "ยกเลิกสถานะเรื่องเสร็จของหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cancel WfContent finish in Workflow success.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/cancelFinish/{contentId}")
    public Response cancelFinish(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "contentId", value = "รหัสหนังสือ", required = true)
            @PathParam("contentId") int contentId
    ) {
        LOG.info("cancelFinish...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(contentId);
            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.getByLinkIdAndLinkId2(wfContent.getWfDocumentId(), wfContent.getId(), "F");
            workflowService.remove(workflow.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

            int removedBy = -1;
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument document = dmsDocumentService.getById(wfContent.getWfDocumentId());
            if (document != null) {
                removedBy = document.getRemovedBy();
            }
            status = Response.Status.OK;
            responseData.put("data", removedBy);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for cancel WfContent cancelContent in Workflow",
            notes = "แก้ไขสถานะยกเลิกหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cancel WfContent finish in Workflow success.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/unCancelContent/{contentId}")
    public Response unCancelContent(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "contentId", value = "รหัสหนังสือ", required = true)
            @PathParam("contentId") int contentId
    ) {
        LOG.info("uncancelContent...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(contentId);

            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.getByLinkIdAndLinkId2(wfContent.getWfDocumentId(), contentId, "C");
            workflowService.remove(workflow.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
            status = Response.Status.OK;
            responseData.put("data", "Cancel WfContent cancelContent success.");
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลหนังสือออกจากฐานข้อมูล",
            notes = "ลบข้อมูลหนังสือออกจากฐานข้อมูล",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent delete by id success.")
        ,
        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/delete/{id}")
    public Response _delete(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("delete...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(id);
            WfReserveContentNoService reserveContentNoService = new WfReserveContentNoService();
            ParamService paramService = new ParamService();
            WfReserveContentNo reserveContentNo = new WfReserveContentNo();

            String deleteToReserve = paramService.getByParamName("DELETETORESERVE").getParamValue();

            Calendar calendar = Calendar.getInstance();
            int yearToday = calendar.get(Calendar.YEAR) + 543;

            int maxContentNoLast = wfContentService.getMaxContentNo(wfContent.getWfContentContentPre(), wfContent.getWfContentFolderId(), wfContent.getWfContentContentYear());
            //get max reserveContentNumber
            int reserveContentNumber = reserveContentNoService.getMaxContentNumber(wfContent.getWfContentFolderId(), wfContent.getWfContentContentYear());

            if (maxContentNoLast < reserveContentNumber) {
                maxContentNoLast = reserveContentNumber;
            }
            maxContentNoLast -= 1;

            if (wfContent != null) {
                wfContentService.delete(wfContent);
                if ((maxContentNoLast > wfContent.getWfContentContentNumber() && yearToday == wfContent.getWfContentContentYear())
                        || (yearToday > wfContent.getWfContentContentYear())) {
                    if (deleteToReserve.equals("Y")) {
                        reserveContentNo.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        reserveContentNo.setReserveContentNoFolderId(wfContent.getWfContentFolderId());
                        reserveContentNo.setReserveContentNoContentNo(wfContent.getWfContentContentNo());
                        reserveContentNo.setReserveContentNoContentDate(LocalDateTime.now());
                        reserveContentNo.setReserveContentNoContentTime(String.valueOf(new Formatter().format("%tR", calendar)));
                        reserveContentNo.setReserveContentNoUserId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        reserveContentNo.setReserveContentNoContentYear(wfContent.getWfContentContentYear());
                        reserveContentNo.setReserveContentNoContentNumber(wfContent.getWfContentContentNumber());
                        reserveContentNo = reserveContentNoService.create(reserveContentNo);
                    }
                }
                status = Response.Status.OK;
                responseData.put("data", "Delete success.");
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลเลขทะเบียนล่าสุด",
            notes = "ขอข้อมูลเลขทะเบียนล่าสุด",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "max ContentNo of WfContent by id success.")
        ,
        @ApiResponse(code = 404, message = "max ContentNo of WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/maxContentPoint")
    public Response getMaxContentPoint(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @QueryParam("folderId") int folderId,
            @ApiParam(name = "contentNumber", value = "แทรกเลข", required = true)
            @QueryParam("contentNumber") int contentNumber
    ) {
        LOG.info("maxContentPoint...");
        LOG.info("folderId = " + folderId);
        LOG.info("contentNumber = " + contentNumber);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "max ContentNo and ContentPoint of WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();

            //check Max ContentNumber
            HashMap<String, String> hashMapContent = wfContentService.maxContentNo(folderId);
            int wfContentContentNumber = Integer.parseInt(hashMapContent.get("wfContentContentNumber")) - 1;
            int yearToday = Integer.parseInt(hashMapContent.get("yearToday"));
            String wfFolderPreContentNo = hashMapContent.get("wfFolderPreContentNo");
            int contentFormat = Integer.parseInt(hashMapContent.get("contentFormat"));

            if (contentNumber >= wfContentContentNumber) {
                responseData.put("data", "");
                responseData.put("message", "ไม่สามารถแทรกเลขได้ เนื่องจากเลขทะเบียนนี้เป็นเลขล่าสุด หรือยังไม่ได้ถูกใช้งาน");
            } else {
                //get Max Point
                int maxContentPoint = wfContentService.getMaxContentPoint(folderId, yearToday, contentNumber);

                if (maxContentPoint > 0) {
                    String newContentNo = wfContentService.convertContentNo(yearToday, contentNumber, maxContentPoint, wfFolderPreContentNo, contentFormat);
                    WfContentModel_groupContentNoAndBookNo wfContentModel_groupContentNoAndBookNo = wfContentService.tranformToModelGroupContentNoAndBookNo(newContentNo, contentNumber, maxContentPoint, yearToday, wfFolderPreContentNo);

                    responseData.put("data", wfContentModel_groupContentNoAndBookNo);
                    responseData.put("message", "เลขแทรกที่ทำการสร้าง คือ " + newContentNo);
                } else {
                    responseData.put("data", "");
                    responseData.put("message", "ไม่สามารถแทรกเลขได้ เนื่องจากเลขทะเบียนนี้ถูกลบไปแล้ว หรือยังไม่ได้ถูกใช้งาน");
                }
            }
            status = Response.Status.OK;

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list WfContent.",
            notes = "รายการสารบรรณ",
            responseContainer = "List",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent list success.")
        ,
        @ApiResponse(code = 404, message = "WfContent list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByFolderId/{folderId}/{year}")
    public Response listByFolderId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "folderId", value = "รหัสทะเบียนสารบรรณ", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "year", value = "ปี", required = true)
            @PathParam("year") int year
    ) {
        LOG.info("listByFolderId...");
        LOG.info("offset = " + listOptionModel.getOffset());
        LOG.info("limit = " + listOptionModel.getLimit());
        LOG.info("sort = " + listOptionModel.getSort());
        LOG.info("dir = " + listOptionModel.getDir());
        LOG.info("folderId = " + folderId);
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            List<WfContent> listWfContent = wfContentService.listByFolderId(listOptionModel.getOffset(), listOptionModel.getLimit(), folderId, year);
            List<WfContentModel> listWfContentModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);
            if (!listWfContent.isEmpty()) {
                int count = listWfContent.size() + listOptionModel.getOffset();
                int countAll = wfContentService.countAllListByFolderId(folderId, year);
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                    if (next >= countAll) {
                        next = 0;
                    }
                }
                listReturnModel = new ListReturnModel(countAll, count, next);
                boolean getNumFileAttach = (listWfContent.get(0).getWorkflowId() == 0);//circularNotice workflowd == 0
                boolean getStatus = !getNumFileAttach;
                //circularNotice(true,false) or content(false,true)
                for (WfContent wfContent : listWfContent) {
                    listWfContentModel.add(wfContentService.tranformToModelGroupWfContentAndWorkflowfinish(wfContent, getNumFileAttach, getStatus));
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listWfContentModel);
            responseData.put("listReturn", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for search content",
            notes = "Method for search content",
            responseContainer = "List",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "content list success.")
        ,
        @ApiResponse(code = 404, message = "content list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response search(
            @BeanParam ListOptionModel listOptionModel,
            WfContentSearchModel contentSearchModel
    ) {
        LOG.info("searchContent... " + contentSearchModel.getWfContentFolderId());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfContentService contentService = new WfContentService();
            List<WfContent> listWfContent = new ArrayList<>();
            ArrayList<WfContentModel> listWfContentModel = new ArrayList<>();

            listWfContent = contentService.searchByModel(contentSearchModel.getWfContentFolderId(), contentSearchModel, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWfContent.isEmpty()) {
                for (WfContent content : listWfContent) {
                    WfContentModel_groupWfContentAndWorkflowfinish tmp = contentService.tranformToModelGroupWfContentAndWorkflowfinish(content, false, true);
                    if (contentSearchModel.getStatus() > 0) {
                        if (tmp.getStatus() == contentSearchModel.getStatus()) {
                            listWfContentModel.add(tmp);
                        }
                    } else {
                        listWfContentModel.add(tmp);
                    }
                }
                listWfContentModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listWfContentModel);
            responseData.put("message", "inbox list success.");
            responseData.put("success", true);

        } catch (Exception ex) {
            ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "อัพเดทข้อมูลเมื่อสร้างหนังสือ (เพิ่ม WF id)",
            notes = "อัพเดทข้อมูลเมื่อสร้างหนังสือ (เพิ่ม WF id)",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Path(value = "/update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateCreate(
            WfContentModel wfContentPostModel
    ) {
        LOG.info("updateCreate...");
        Gson gs = new GsonBuilder()
                .setVersion(wfContentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfContent by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(wfContentPostModel.getId());
            if (wfContent != null) {
                wfContent.setWorkflowId(wfContentPostModel.getWorkflowId());
                wfContent = wfContentService.update(wfContent);
                status = Response.Status.OK;
                responseData.put("data", wfContentService.tranformToModel(wfContent));
                responseData.put("message", "");
                responseData.put("success", true);
            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    //oat-add
    @ApiOperation(
            value = "send email dmsDocument.",
            notes = "ส่งอีเมล",
            response = EmailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Send email success.")
        ,
        @ApiResponse(code = 404, message = "Error!.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/email")
    public Response sendEmail(
            EmailModel emailModel
    ) {
        LOG.info("sendEmail...");
        Gson gs = new GsonBuilder()
                .setVersion(emailModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Error!");
        responseData.put("errorMessage", "");
        try {
            ArrayList<String> listFileAttachPath = new ArrayList<>();
            if ((emailModel.getFileattachsId() != null) && (!emailModel.getFileattachsId().equals(""))) {
                String[] fileAttachIds = emailModel.getFileattachsId().split(",");
                FileAttachService fileAttachService = new FileAttachService();
                for (String fileAttachId : fileAttachIds) {
                    listFileAttachPath.add(fileAttachService.moveToTempPath(Integer.parseInt(fileAttachId)));
                }
            }
            boolean result = Common.sendEmail(emailModel.getSubject(), emailModel.getTo(), emailModel.getCc(),
                    emailModel.getBcc(), emailModel.getBody(), listFileAttachPath, "html", false);
            if (result) {
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "send email success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat
    @ApiOperation(
            value = "ขอข้อมูลเลขล่าสุด",
            notes = "ขอข้อมูลเลขล่าสุด",
            response = WfLastNumberModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "max ContentNo of WfContent by id success.")
        ,
        @ApiResponse(code = 404, message = "max ContentNo of WfContent by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/last/{folderId}")
    public Response getLastNumber(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("folderId") int folderId
    ) {
        LOG.info("getLastNumber...");
        LOG.info("folderId = " + folderId);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "max ContentNo of WfContent by id not found in the database.");
        try {
            ParamService paramservice = new ParamService();
            WfContentService contentService = new WfContentService();
            WfFolderService folderService = new WfFolderService();
            WfFolder folder = folderService.getById(folderId);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) + 543;
            if (folder.getWfFolderByBudgetYear() == 1) {
                if ((calendar.get(Calendar.MONTH) + 1) >= 10) {
                    year++;
                }
            }

            int contentNumber = contentService.getMaxContentNo(folder.getWfFolderPreContentNo(), folderId, year);
            int reserveNumber = new WfReserveContentNoService().getMaxContentNumber(folderId, year);
            if (contentNumber < reserveNumber) {
                contentNumber = reserveNumber;
            }
            int bookNumber = (paramservice.getByParamName("BOOKNOEQUALCONNO").getParamValue().equalsIgnoreCase("Y"))
                    ? contentNumber : contentService.getMaxBookNo(folder.getWfFolderPreContentNo(), folderId, year);

            int point = contentService.getMaxContentPoint(folderId, year, contentNumber - 1);

            String zeroDigit;
            String pre;
            String no;

            int contentFormat = (folder.getWfContentType2().getId() == 5)//ทะเบียนคำสั่งเลข3หลัก, ปกติ5หลัก
                    ? Integer.parseInt(paramservice.getByParamName("ORDERFORMAT").getParamValue()) : Integer.parseInt(paramservice.getByParamName("CONTENTFORMAT").getParamValue());
            zeroDigit = "0";
            for (int i = 0; i < contentFormat; i++) {
                zeroDigit += "0";
            }
            pre = folder.getWfFolderPreContentNo();
            if (pre == null) {
                pre = "";
            }
            no = zeroDigit + contentNumber;
            no = no.substring(no.length() - contentFormat);

            String contentNo = pre + no + "/" + Integer.toString(year);//praxis00001/2560 pre+no+/year          

            int bookFormat = Integer.parseInt(paramservice.getByParamName("BOOKNOFORMAT").getParamValue());
            zeroDigit = "0";
            for (int i = 0; i < contentFormat; i++) {
                zeroDigit += "0";
            }
            pre = folder.getWfFolderPreBookNo();
            if (pre == null) {
                pre = "";
            }
            no = zeroDigit + bookNumber;
            String bookNo = "";
            switch (folder.getWfFolderBookNoType()) {
                case 0:
                    break;
                case 1:
                    bookNo = pre + no.substring(no.length() - bookFormat);//praxis00001
                    break;
                case 2:
                    bookNo = pre + no.substring(no.length() - bookFormat) + "/" + Integer.toString(year);//praxis00001/2560
                    break;
            }

            WfLastNumberModel lastNumberModel = new WfLastNumberModel(year, contentNumber, bookNumber, point, contentFormat, bookFormat, contentNo, bookNo);

            status = Response.Status.OK;
            responseData.put("data", lastNumberModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "สร้างข้อมูลหนังสือ",
            notes = "สร้างข้อมูลหนังสือ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfContent created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/create2")
    public Response create2(
            WfContentModel wfContentPostModel
    ) {
        LOG.info("create...");
        LOG.info("wfContentPostModel = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new GsonBuilder()
                .setVersion(wfContentPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {

            WfContent wfContent = new WfContent();
            UserProfile userProfile = new UserProfile();
            WfFolder wfFolder = new WfFolder();

            WfContentService wfContentService = new WfContentService();
            wfContentPostModel = wfContentService.checkLengthField(wfContentPostModel);

            String wfContentNo = wfContentPostModel.getWfContentContentNo();
            int wfContentNumber = wfContentPostModel.getWfContentContentNumber();
            String wfBookNo = wfContentPostModel.getWfContentBookNo();
            int wfBookNumber = 0;
            if (wfContentPostModel.getWfContentBookNumber() != null) {
                wfBookNumber = wfContentPostModel.getWfContentBookNumber();
            }
            int chkRepeatContentNoMsg = 0;
            int chkRepeatBookNoMsg = 0;

            wfContent.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContentPostModel.getWfContentBookDate() != null) {
                wfContent.setWfContentBookDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentBookDate()));
            } else {
                wfContent.setWfContentBookDate(null);
            }
            wfContent.setWfContentContentDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentContentDate()));
            if (wfContentPostModel.getWfContentExpireDate() != null) {
                wfContent.setWfContentExpireDate(dateThaiToLocalDateTime(wfContentPostModel.getWfContentExpireDate()));
            } else {
                wfContent.setWfContentExpireDate(null);
            }

            if (wfContent != null) {
                //==============userProfile ======================
                UserProfileService userProfileService = new UserProfileService();
                userProfile = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));

                WfContent wfContentCreate = wfContentService.create(wfContent);

                //==============wffolder=============================
                WfFolderService wfFolderService = new WfFolderService();
                wfFolder = wfFolderService.getById(wfContentPostModel.getWfContentFolderId());
                int wfFolderBookNoType = wfFolder.getWfFolderBookNoType();
                int wfFolderAutorun = wfFolder.getWfFolderAutorun();

                //=======create WFContent ======================
                wfContentCreate.setOrderNo(wfContentCreate.getId());
                wfContentCreate.setInboxId(wfContentPostModel.getInboxId());
                wfContentCreate.setWfDocumentId(wfContentPostModel.getWfDocumentId());
                wfContentCreate.setWfContentFolderId(wfContentPostModel.getWfContentFolderId());
                wfContentCreate.setWorkflowId(wfContentPostModel.getWorkflowId());
                wfContentCreate.setWfContentContentPre(wfContentPostModel.getWfContentContentPre());////
                wfContentCreate.setWfContentYear(wfContentPostModel.getWfContentContentYear());////
                wfContentCreate.setWfContentContentNumber(wfContentNumber);
                wfContentCreate.setWfContentContentPoint(0);////
                wfContentCreate.setWfContentContentTime(wfContentPostModel.getWfContentContentTime());
                wfContentCreate.setWfContentContentNo(wfContentNo);
                wfContentCreate.setWfContentBookYear(wfContentPostModel.getWfContentBookYear());////
                wfContentCreate.setWfContentBookPre(wfContentPostModel.getWfContentBookPre());////
                wfContentCreate.setWfContentBookNo(wfBookNo);
                wfContentCreate.setWfContentBookNumber(wfBookNumber);
                wfContentCreate.setWfContentBookPoint(0);////
                wfContentCreate.setWfContentFrom(wfContentPostModel.getWfContentFrom());
                wfContentCreate.setWfContentTo(wfContentPostModel.getWfContentTo());
                wfContentCreate.setWfContentTitle(wfContentPostModel.getWfContentTitle());
                wfContentCreate.setWfContentSpeed(wfContentPostModel.getWfContentSpeed());
                wfContentCreate.setWfContentSecret(wfContentPostModel.getWfContentSecret());
                wfContentCreate.setWfContentDescription(wfContentPostModel.getWfContentDescription());
                wfContentCreate.setWfContentReference(wfContentPostModel.getWfContentReference());
                wfContentCreate.setWfContentAttachment(wfContentPostModel.getWfContentAttachment());
                wfContentCreate.setWfContentOwnername(userProfile.getUserProfileFullName());
                wfContentCreate.setWfContentStr01(wfContentPostModel.getWfContentStr01());
                wfContentCreate.setWfContentStr02(wfContentPostModel.getWfContentStr02());//พ.ศ. 
                wfContentCreate.setWfContentStr03(wfContentPostModel.getWfContentStr03());//ลงนาม   
                wfContentCreate.setWfContentStr04(wfContentPostModel.getWfContentStr04());//ตำแหน่ง
                wfContentCreate.setWfContentStr05(wfContentPostModel.getWfContentStr05());//หน่วยงานที่ออก 
                wfContentCreate.setWfContentStr06(wfContentPostModel.getWfContentStr06());
                wfContentCreate.setWfContentStr07(wfContentPostModel.getWfContentStr07());
                wfContentCreate.setWfContentStr08(wfContentPostModel.getWfContentStr08());
                wfContentCreate.setWfContentStr09(wfContentPostModel.getWfContentStr09());
                wfContentCreate.setWfContentStr10(wfContentPostModel.getWfContentStr10());
                wfContentCreate.setWfContentText01(wfContentPostModel.getWfContentText01());//ชื่อคำสั่ง/ประกาศ/อื่นๆ
                wfContentCreate.setWfContentText02(wfContentPostModel.getWfContentText02());//หมายเหตุ
                wfContentCreate.setWfContentText03(wfContentPostModel.getWfContentText03());
                wfContentCreate.setWfContentText04(wfContentPostModel.getWfContentText04());
                wfContentCreate.setWfContentText05(wfContentPostModel.getWfContentText05());
                wfContentCreate.setWfContentText06(wfContentPostModel.getWfContentText06());
                wfContentCreate.setWfContentText07(wfContentPostModel.getWfContentText07());
                wfContentCreate.setWfContentText08(wfContentPostModel.getWfContentText08());
                wfContentCreate.setWfContentText09(wfContentPostModel.getWfContentText09());
                wfContentCreate.setWfContentText10(wfContentPostModel.getWfContentText10());
                wfContentCreate.setWfContentInt01(wfContentPostModel.getWfContentInt01());//ประเภท 
                wfContentCreate.setWfContentInt02(wfContentPostModel.getWfContentInt02());
                wfContentCreate.setWfContentInt03(wfContentPostModel.getWfContentInt03());
                wfContentCreate.setWfContentInt04(wfContentPostModel.getWfContentInt04());
                wfContentCreate.setWfContentInt05(wfContentPostModel.getWfContentInt05());
                wfContentCreate.setWfContentInt06(wfContentPostModel.getWfContentInt06());
                wfContentCreate.setWfContentInt07(wfContentPostModel.getWfContentInt07());
                wfContentCreate.setWfContentInt08(wfContentPostModel.getWfContentInt08());
                wfContentCreate.setWfContentInt09(wfContentPostModel.getWfContentInt09());
                wfContentCreate.setWfContentInt10(wfContentPostModel.getWfContentInt10());
                wfContentCreate.setWfContentDate01(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate01()));
                wfContentCreate.setWfContentDate02(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate02()));
                wfContentCreate.setWfContentDate03(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate03()));
                wfContentCreate.setWfContentDate04(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate04()));
                wfContentCreate.setWfContentDate05(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate05()));
                wfContentCreate.setWfContentDate06(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate06()));
                wfContentCreate.setWfContentDate07(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate07()));
                wfContentCreate.setWfContentDate08(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate08()));
                wfContentCreate.setWfContentDate09(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate09()));
                wfContentCreate.setWfContentDate10(dateThaiToLocalDateTime(wfContentPostModel.getWfContentDate10()));
                wfContentCreate.setConvertId(0);
                wfContentCreate.setfOrgId(0);
                wfContentCreate.setfTransMainId(0);
                wfContentCreate.setfType(0);

                wfContentCreate = wfContentService.update(wfContentCreate);
                responseData.put("data", wfContentService.tranformToModel(wfContentCreate));

                //LogData For Create
                wfContentService.saveLogForCreate(wfContentCreate, httpHeaders.getHeaderString("clientIp"));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            if (chkRepeatContentNoMsg == 0 && chkRepeatBookNoMsg == 0) {
                responseData.put("message", "WfContent successfully.");
            } else if (chkRepeatContentNoMsg == 1 && chkRepeatBookNoMsg == 0) {
                responseData.put("message", "WfContent successfully. This ContentNo is used. New ContentNo = " + wfContentNo);
            } else if (chkRepeatContentNoMsg == 0 && chkRepeatBookNoMsg == 1) {
                responseData.put("message", "WfContent successfully. This BookNo is used. New BookNo = " + wfBookNo);
            } else if (chkRepeatContentNoMsg == 1 && chkRepeatBookNoMsg == 1) {
                responseData.put("message", "WfContent successfully. ContentNo and BookNo are used. New ContentNo = " + wfContentNo + " and New BookNo = " + wfBookNo);
            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check duplicate WfContent title",
            notes = "เช็คชื่อเรื่องหนังสือซ้ำ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "checkTitle success.")
        ,
        @ApiResponse(code = 404, message = "WfContent not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkTitle")
    public Response checkTitle(
            @BeanParam VersionModel versionModel,
            WfContentModel contentModel
    ) {
        LOG.info("checkTitle...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "checkTitle not found in the database.");
        try {
            WfContentService contentService = new WfContentService();
            int tmp = contentService.countContentTitle(contentModel.getWfContentTitle(), contentModel.getWfContentFolderId(), contentModel.getWfContentContentYear());
            status = Response.Status.OK;
            responseData.put("data", tmp > 0);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    //oat
    @ApiOperation(
            value = "get content/book NoPoint",
            notes = "get content/book NoPoint",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "content/book NoPoint by id success.")
        ,
        @ApiResponse(code = 404, message = "content/book NoPoint by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/point/{folderId}/{contentNumber}/{preBookNoIndex}")
    public Response getNOPoint(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "contentNumber", value = "contentNumber", required = true)
            @PathParam("contentNumber") int contentNumber,
            @ApiParam(name = "preBookNoIndex", value = "preBookNoIndex", required = true)
            @PathParam("preBookNoIndex") int preBookNoIndex
    ) {
        LOG.info("getNoPoint...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "content/book NoPoint by id not found in the database.");
        try {
            ParamService paramservice = new ParamService();
            WfContentService contentService = new WfContentService();
            //WfContent content = contentService.getById(contentId);
            WfFolderService folderService = new WfFolderService();
            WfFolder folder = folderService.getById(folderId);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR) + 543;
            if (folder.getWfFolderByBudgetYear() == 1) {
                if ((calendar.get(Calendar.MONTH) + 1) >= 10) {
                    year++;
                }
            }

            //int contentNumber = content.getWfContentContentNumber();
            //int bookNumber = content.getWfContentBookNumber();
            String point = "0" + Integer.toString(contentService.getMaxContentPoint(folderId, year, contentNumber));
            point = "." + point.substring(point.length() - 2);//00001.01

            String zeroDigit;
            String pre;
            String no;

            int contentFormat = (folder.getWfContentType2().getId() == 5)//ทะเบียนคำสั่งเลข3หลัก, ปกติ5หลัก
                    ? Integer.parseInt(paramservice.getByParamName("ORDERFORMAT").getParamValue()) : Integer.parseInt(paramservice.getByParamName("CONTENTFORMAT").getParamValue());
            zeroDigit = "0";
            for (int i = 0; i < contentFormat; i++) {
                zeroDigit += "0";
            }
            pre = folder.getWfFolderPreContentNo();
            if (pre == null) {
                pre = "";
            }
            no = zeroDigit + contentNumber;
            no = no.substring(no.length() - contentFormat);

            String contentNo = pre + no + point + "/" + Integer.toString(year);//praxis00001/2560 pre+no+/year          

            int bookFormat = Integer.parseInt(paramservice.getByParamName("BOOKNOFORMAT").getParamValue());
            zeroDigit = "0";
            for (int i = 0; i < contentFormat; i++) {
                zeroDigit += "0";
            }
            //[GHB]
//            pre = folder.getWfFolderPreBookNo();
//            if (pre == null) {
//                pre = "";
//            } 
            if (folder.getWfFolderPreBookNo() == null) {
                pre = "";
            } else {
                pre = folder.getWfFolderPreBookNo().split(", ")[preBookNoIndex];
            }
//            no = zeroDigit + bookNumber;
            no = zeroDigit + contentNumber;
            String bookNo = "";
            switch (folder.getWfFolderBookNoType()) {
                case 0:
//                    bookNo = no.substring(no.length() - bookFormat) + point;
                    bookNo = "";
                    break;

                case 1:
                    bookNo = pre + no.substring(no.length() - bookFormat) + point;//praxis00001
                    break;

                case 2:
                    bookNo = pre + no.substring(no.length() - bookFormat) + point + "/" + Integer.toString(year);//praxis00001/2560
                    break;

            }
            WfContent contentMain = contentService.getByContentNumber(contentNumber, folderId);
            WfContentModel tmp = new WfContentModel();
            if (contentMain != null) {
                tmp.setWfContentContentNumber(contentNumber);
                tmp.setWfContentContentNo(contentNo);
//            tmp.setWfContentBookNumber(bookNumber);
                tmp.setWfContentBookNumber(contentNumber);
                tmp.setWfContentBookNo(bookNo);

                tmp.setWfContentContentDate(Common.localDateTimeToString4(contentMain.getWfContentContentDate()));
                tmp.setWfContentContentTime(contentMain.getWfContentContentTime());
            }

            status = Response.Status.OK;
            responseData.put("data", tmp);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search report",
            notes = "Method for search report",
            response = WfContent.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "report success.")
        ,
        @ApiResponse(code = 404, message = "reportt not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/report1_2/{jobType}/{folderId}")
    public Response report1_2(
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId,
            WfContentSearchModel contentSearchModel
    ) {
//      log.info("searchInbox...");
        Gson gs = new GsonBuilder()
                //.setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfContentService contentService = new WfContentService();
            List<WfContent> listWfContent = new ArrayList<>();
            ArrayList<WfContentModel> listWfContentModel = new ArrayList<>();

            listWfContent = contentService.searchByModel(folderId, contentSearchModel, "orderNo", "desc");

            if (!listWfContent.isEmpty()) {
                /////
                listWfContent.forEach((content) -> {
                    WfContentModel_groupWfContentAndWorkflowfinish tmp = contentService.tranformToModelGroupWfContentAndWorkflowfinish(content, false, true);
                    if (contentSearchModel.getStatus() > 0) {
                        if (tmp.getStatus() == contentSearchModel.getStatus()) {
                            listWfContentModel.add(tmp);
                        }
                    } else {
                        listWfContentModel.add(tmp);
                    }
                });
                listWfContentModel.trimToSize();
                /////
                String header = "";
                String tableHeader = "";
                if (folderId > 0) {//else report_16
                    WfFolderService folderService = new WfFolderService();
                    WfFolder folder = folderService.getByIdNotRemoved(folderId);

                    header = "รายงานการรับเอกสารภายในองค์กร";
                    tableHeader = "ทะเบียน : " + folder.getWfFolderParentName() + " " + folder.getWfFolderName();

                    if (folder.getWfContentType().getId() == 1) {
                        if (folder.getWfContentType2().getId() == 2) {
                            header = "รายงานการรับเอกสารภายในองค์กร";
                        } else if (folder.getWfContentType2().getId() == 3) {
                            header = "รายงานการรับเอกสารภายนอกองค์กร";
                        } else {
                            header = "รายงานการรับเอกสารภายในองค์กร";
                        }
                    } else if (folder.getWfContentType().getId() == 2) {
                        if (folder.getWfContentType2().getId() == 2) {
                            header = "รายงานการส่งเอกสารภายในองค์กร";
                        } else if (folder.getWfContentType2().getId() == 3) {
                            header = "รายงานการส่งเอกสารภายนอกองค์กร";
                        } else {
                            header = "รายงานการส่งเอกสารภายในองค์กร";
                        }
                    }
                } else {
                    header = "รายงานการค้นหาจากเลขไปรษณีย์ลงทะเบียน";
                    tableHeader = "ทะเบียนส่วนกลาง";
                }

                String dateBegin = "ตั้งแต่วันที่ ";
                String start = contentSearchModel.getWfContentContentStartDate();
                if (start != null && !"".equals(start)) {
                    dateBegin += contentService.getDateRange(start);
                } else {
                    dateBegin += "-";
                }
                String dateEnd = " ถึงวันที่ ";
                String end = contentSearchModel.getWfContentContentEndDate();
                if (end != null && !"".equals(end)) {
                    dateEnd += contentService.getDateRange(end);
                } else {
                    dateEnd += "-";
                }
//                System.out.println(contentService.getDateRange("01/05/2560"));
//                System.out.println("start " + start);
//                System.out.println("end " + end);
//                System.out.println("DATEEEEE b" + dateBegin);
//                System.out.println("DATEEEEE e" + dateEnd);
//                System.out.println("+++++++++++++++++++++++++++++++");

                TempTableService tempTableService = new TempTableService();

                //for (WfContent content : listWfContent) {//cant use foreach cause header, t.header
                for (WfContentModel content : listWfContentModel) {
                    TempTable tempTable = new TempTable();
                    tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                    tempTable.setJobType(jobType);
                    tempTable.setStr01(content.getWfContentContentNo());
                    //tempTable.setStr02(Common.localDateTimeToString(content.getWfContentContentDate()));
                    tempTable.setStr02(content.getWfContentContentDate());
                    tempTable.setStr03(content.getWfContentBookNo());
                    //tempTable.setStr04(Common.localDateTimeToString(content.getWfContentBookDate()));
                    tempTable.setStr04(content.getWfContentBookDate());
                    tempTable.setText01(content.getWfContentFrom());
                    //
                    tempTable.setText02(content.getWfContentTo());
                    tempTable.setText03(content.getWfContentTitle());
                    tempTable.setText04(content.getWfContentDescription());
                    tempTable.setStr05(header);
                    tempTable.setText05(tableHeader);
                    //tempTable.setDate01(content.getCreatedDate());
                    tempTable.setText06(dateBegin + dateEnd);
                    tempTable = tempTableService.create(tempTable);

                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setOrderNo(tempTable.getId());
                    tempTable = tempTableService.update(tempTable);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "report success.");
            responseData.put("success", true);

        } catch (Exception ex) {
            //ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search report",
            notes = "Method for search report",
            response = WfContent.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "report success.")
        ,
        @ApiResponse(code = 404, message = "reportt not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/report101314/{jobType}/{folderId}/{actionType}")
    public Response report101314(
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "actionType", value = "actionType", required = true)
            @PathParam("actionType") String actionType,
            WfContentSearchModel contentSearchModel
    ) {
//      log.info("searchInbox...");
        Gson gs = new GsonBuilder()
                //.setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfContentService contentService = new WfContentService();
            List<WfContent> listWfContent = new ArrayList<>();
            //ArrayList<WfContentModel> listWfContentModel = new ArrayList<>();

            listWfContent = contentService.searchByModel(folderId, contentSearchModel, "orderNo", "desc");

            if (!listWfContent.isEmpty()) {
//                /////
//                listWfContent.forEach((content) -> {
//                    WfContentModel_groupWfContentAndWorkflowfinish tmp = contentService.tranformToModelGroupWfContentAndWorkflowfinish(content, false);
//                    if (contentSearchModel.getStatus() > 0) {
//                        if (tmp.getStatus() == contentSearchModel.getStatus()) {
//                            listWfContentModel.add(tmp);
//                        }
//                    } else {
//                        listWfContentModel.add(tmp);
//                    }
//                });
//                listWfContentModel.trimToSize();
//                /////               

                WfFolderService folderService = new WfFolderService();
                WfFolder folder = folderService.getByIdNotRemoved(folderId);

                int action = 1;
                String action_str = "อยู่ระหว่างดำเนินการ";
                String header = "รายงานสถานะการดำเนินงานหนังสือ-อยู่ระหว่างดำเนินการ";
                String tableHeader = "หน่วยงาน : " + folder.getWfFolderParentName() + " " + folder.getWfFolderName();

                if (actionType.equalsIgnoreCase("F")) {
                    action = 2;
                    action_str = "เรื่องเสร็จ";
                    header = "รายงานสถานะการดำเนินงานหนังสือ-เรื่องเสร็จ";
                } else if (actionType.equalsIgnoreCase("C")) {
                    action = 3;
                    action_str = "ยกเลิกเรื่อง";
                    header = "รายงานสถานะการดำเนินงานหนังสือ-ยกเลิกเรื่อง";
                }

                String dateBegin = "ตั้งแต่วันที่ ";
                String start = contentSearchModel.getWfContentContentStartDate();
                if (start != null && !"".equals(start)) {
                    dateBegin += contentService.getDateRange(start);
                } else {
                    dateBegin += "-";
                }
                String dateEnd = " ถึงวันที่ ";
                String end = contentSearchModel.getWfContentContentEndDate();
                if (end != null && !"".equals(end)) {
                    dateEnd += contentService.getDateRange(end);
                } else {
                    dateEnd += "-";
                }

                TempTableService tempTableService = new TempTableService();

                for (WfContent content : listWfContent) {//cant use foreach cause header, t.header
                    //for (WfContentModel content : listWfContentModel) {
                    int contentStatus = contentService.tranformToModelGroupWfContentAndWorkflowfinish(content, false, true).getStatus();
                    if (contentStatus == action) {
                        TempTable tempTable = new TempTable();
                        tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                        tempTable.setJobType(jobType);
                        tempTable.setStr01(content.getWfContentContentNo());
                        tempTable.setStr03(content.getWfContentBookNo());
                        tempTable.setStr04(Common.localDateTimeToString4(content.getWfContentBookDate()));
                        tempTable.setText01(content.getWfContentFrom());
                        tempTable.setText02(content.getWfContentTo());
                        tempTable.setText03(content.getWfContentTitle());
                        tempTable.setStr02(action_str);
                        tempTable.setStr05(header);
                        tempTable.setText05(tableHeader);
                        //tempTable.setDate01(content.getCreatedDate());
                        tempTable.setText06(dateBegin + dateEnd);

                        tempTable = tempTableService.create(tempTable);

                        tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        tempTable.setOrderNo(tempTable.getId());
                        tempTable = tempTableService.update(tempTable);
                    }

                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "report success.");
            responseData.put("success", true);

        } catch (Exception ex) {
            ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete temptable",
            notes = "Method for delete temptable",
            response = WfContent.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "temptable success.")
        ,
        @ApiResponse(code = 404, message = "temptable not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/deleteByJobType/{jobType}")
    public Response deleteTempTable(
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType
    ) {
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), httpHeaders.getHeaderString("clientIp"), jobType, "orderNo", "asc");
            if (!listTempTable.isEmpty()) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "report success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search report",
            notes = "Method for search report",
            response = WfContent.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "report success.")
        ,
        @ApiResponse(code = 404, message = "reportt not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/report56/{jobType}/{folderId}/{userId}")
    public Response report56(
            @ApiParam(name = "jobType", value = "jobType", required = true)
            @PathParam("jobType") String jobType,
            @ApiParam(name = "folderId", value = "folderId", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @PathParam("userId") int userId,
            WfContentSearchModel contentSearchModel
    ) {
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "inbox list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            WfContentService contentService = new WfContentService();
            WfFolderService folderService = new WfFolderService();

            String name = "";
            if (userId != 0) {
                UserProfile userProfile = new UserProfileService().getById(userId);
                name = userProfile.getTitle().getTitleName() + " " + userProfile.getUserProfileFullName();
            }
            List<WfFolder> listFolder = folderService.listByParentId(folderId);
            //System.out.println("num folder " + listFolder.size());
            String tableHeader = "ทะเบียนส่วนกลาง";
            if (folderId > 0) {
                tableHeader = "ทะเบียน : " + folderService.getById(folderId).getWfFolderName();
            }
            //System.out.println("tableHeader " + tableHeader);
            int count12 = 0;
            int count13 = 0;
            int count22 = 0;
            int count23 = 0;

            int count12_f = 0;
            int count13_f = 0;
            int count22_f = 0;
            int count23_f = 0;

            String dateBegin = "ตั้งแต่วันที่ ";
            String start = contentSearchModel.getWfContentContentStartDate();
            if (start != null && !"".equals(start)) {
                dateBegin += contentService.getDateRange(start);
            } else {
                dateBegin += "-";
            }
            String dateEnd = " ถึงวันที่ ";
            String end = contentSearchModel.getWfContentContentEndDate();
            if (end != null && !"".equals(end)) {
                dateEnd += contentService.getDateRange(end);
            } else {
                dateEnd += "-";
            }
            //System.out.println(dateBegin + dateEnd);

            for (WfFolder folder : listFolder) {
                int id = folder.getId();
                int countFinished = 0;
                int countCanceled = 0;
                int count = 0;
                List<WfContent> listContent = new ArrayList<WfContent>();
                if (userId != 0) {
                    listContent = contentService.listByFolderIdDateRangeUser(id, contentSearchModel.getWfContentContentStartDate(), contentSearchModel.getWfContentContentEndDate(), userId);
                    //System.out.println("report6: " + folder.getWfFolderName() + " ***** num content: " + listContent.size());
                } else {
                    listContent = contentService.listByFolderIdDateRange(id, contentSearchModel.getWfContentContentStartDate(), contentSearchModel.getWfContentContentEndDate());
                    //System.out.println("report5: " + folder.getWfFolderName() + " ***** num content: " + listContent.size());
                }
                if (!listContent.isEmpty()) {
                    for (WfContent content : listContent) {
                        WfContentModel_groupWfContentAndWorkflowfinish contentModel = contentService.tranformToModelGroupWfContentAndWorkflowfinish(content, false, true);
                        if (contentModel.getHasFinish()) {
                            countFinished++;
                        } else if (contentModel.getIsCanceled()) {
                            countCanceled++;
                        } else {
                            count++;
                        }
                    }
                    //System.out.println("count: " + count + " ***** countFinish :" + countFinished);
                    if (folder.getWfContentType().getId() == 1) {
                        if (folder.getWfContentType2().getId() == 2) {
                            count12 = count12 + count;
                            count12_f = count12_f + countFinished;
                        } else if (folder.getWfContentType2().getId() == 3) {
                            count13 = count13 + count;
                            count13_f = count13_f + countFinished;
                        }
                    } else if (folder.getWfContentType().getId() == 2) {
                        if (folder.getWfContentType2().getId() == 2) {
                            count22 = count22 + count;
                            count22_f = count22_f + countFinished;
                        } else if (folder.getWfContentType2().getId() == 3) {
                            count23 = count23 + count;
                            count23_f = count23_f + countFinished;
                        }
                    }
                }
            }
            TempTableService tempTableService = new TempTableService();
            TempTable tempTable = new TempTable();
            tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
            tempTable.setJobType(jobType);
            tempTable.setInt01(count12);
            tempTable.setInt02(count12_f);
            tempTable.setInt03(count13);
            tempTable.setInt04(count13_f);
            tempTable.setInt05(count22);
            tempTable.setInt06(count22_f);
            tempTable.setInt07(count23);
            tempTable.setInt08(count23_f);
            tempTable.setText02(name);
            tempTable.setText05(tableHeader);
            tempTable.setText06(dateBegin + dateEnd);
            tempTable = tempTableService.create(tempTable);

            tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            tempTable.setOrderNo(tempTable.getId());
            tempTableService.update(tempTable);
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "report success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list MyWork.",
            notes = "รายการเอกสารส่วนตัว",
            responseContainer = "List",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "MyWork list success.")
        ,
        @ApiResponse(code = 404, message = "MyWork list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/myWork")
    public Response list_MyWork() {
        LOG.info("listMyWork...");
        Gson gs = new GsonBuilder()
                .setVersion(1)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "MyWork not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();

            List<WfContent> listWfContent = wfContentService.listMyWork(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            List<WfContentModel> listWfContentModel = new ArrayList<>();
            if (!listWfContent.isEmpty()) {
                for (WfContent wfContent : listWfContent) {
                    listWfContentModel.add(wfContentService.tranformToModelGroupWfContentAndWorkflowfinish(wfContent, false, false));//oat-edit
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listWfContentModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create MyWork.",
            notes = "สร้างเอกสารส่วนตัว",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "MyWork created success.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/myWork")
    public Response create_MyWork(
            WfContentModel wfContentModel
    ) {
        LOG.info("createMyWork...");
        LOG.info("title..." + wfContentModel.getWfContentTitle());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentService wfContentService = new WfContentService();
            //wfContentModel = wfContentService.checkLengthField(wfContentModel);

            WfContent wfContent = new WfContent();
            wfContent.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContent.setWfContentOwnername(new UserProfileService().getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUserProfileFullName());

            wfContent.setWfDocumentId(wfContentModel.getWfDocumentId());
            wfContent.setWfContentContentDate(dateThaiToLocalDateTime(wfContentModel.getWfContentContentDate()));
            wfContent.setWfContentTitle(wfContentModel.getWfContentTitle());
            wfContent.setWfContentFolderId(-1);
//            wfContent.setWfContentContentNo("");
//            wfContent.setWfContentContentNumber(0);
//            wfContent.setWfContentContentPre("");
//            wfContent.setWfContentContentTime("00:00");
            wfContent.setWfContentBookDate(dateThaiToLocalDateTime(wfContentModel.getWfContentBookDate()));
            wfContent.setWfContentBookNo("เอกสารส่วนตัว");
//            wfContent.setWfContentBookPre("");
//            wfContent.setWfContentExpireDate(null);
//            wfContent.setWfContentFrom("");
//            wfContent.setWfContentTo("");
//            wfContent.setWfContentTitle("");
            wfContent.setWorkflowId(0);
            wfContent.setWfContentInt01(1);
            wfContent.setWfContentInt02(0);
            wfContent.setWfContentInt03(0);
            wfContent.setWfContentInt04(0);
            wfContent.setWfContentInt05(0);
            wfContent.setWfContentInt06(0);
            wfContent.setWfContentInt07(0);
            wfContent.setWfContentInt08(0);
            wfContent.setWfContentInt09(0);
            wfContent.setWfContentInt10(0);
            Calendar calendar = Calendar.getInstance();
            int yearToday = Calendar.getInstance().get(calendar.YEAR) + 543;
            wfContent.setWfContentContentYear(yearToday);
            wfContent.setWfContentBookYear(yearToday);
            wfContent = wfContentService.create(wfContent);

            wfContent.setOrderNo(wfContent.getId());
            wfContent.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContent = wfContentService.update(wfContent);

            status = Response.Status.CREATED;
            responseData.put("data", wfContentService.tranformToModel(wfContent));
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update MyWork.",
            notes = "แก้ไขเอกสารส่วนตัว",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "MyWork updated success.")
        ,
        @ApiResponse(code = 404, message = "MyWork not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/myWork")
    public Response update_MyWork(
            WfContentModel wfContentModel
    ) {
        LOG.info("updateMyWork...");
        LOG.info("title..." + wfContentModel.getWfContentTitle());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getByIdNotRemoved(wfContentModel.getId());
            if (wfContent != null) {
                wfContent.setWfContentTitle(wfContentModel.getWfContentTitle());
                wfContent.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfContent = wfContentService.update(wfContent);
                status = Response.Status.OK;
                responseData.put("data", wfContentService.tranformToModel(wfContent));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for remove MyWork.",
            notes = "ลบเอกสารส่วนตัว",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "MyWork removeed success.")
        ,
        @ApiResponse(code = 404, message = "MyWork not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/myWork/{id}")
    public Response remove_MyWork(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสารส่วนตัว", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove MyWork...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "MyWork by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfContent != null) {
                status = Response.Status.OK;
                responseData.put("data", true);
                responseData.put("message", "");
            }
            responseData.put("success", true);

            //for recyclebin
            wfContentService.saveLogForRemove_MyWork(wfContent, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create CircularNotice.",
            notes = "สร้างหนังสือเวียน",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "CircularNotice created success.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/circularNotice")
    public Response create_CircularNotice(
            WfContentModel wfContentModel
    ) {
        LOG.info("createCircularNotice...");
        LOG.info("title..." + wfContentModel.getWfContentTitle());
        Gson gs = new GsonBuilder()
                .setVersion(wfContentModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfContentService wfContentService = new WfContentService();
            //wfContentModel = wfContentService.checkLengthField(wfContentModel);
            WfFolder wfFolder = new WfFolderService().getByIdNotRemoved(wfContentModel.getWfContentFolderId());
            String wfContentNo = wfContentModel.getWfContentContentNo();
            int wfContentNumber = wfContentModel.getWfContentContentNumber();
            HashMap<String, String> hashMapContentNo1 = new HashMap<String, String>();
            hashMapContentNo1 = wfContentService.splitDataContentNo(wfContentNo, wfFolder);
            wfContentNo = hashMapContentNo1.get("wfContentContentNo");
            wfContentNumber = Integer.parseInt(hashMapContentNo1.get("wfContentNumber"));
            int wfContentPoint = Integer.parseInt(hashMapContentNo1.get("wfContentPoint"));
            String wfContentPre = hashMapContentNo1.get("wfContentPre");
            int wfContentYear = Integer.parseInt(hashMapContentNo1.get("wfContentYear"));

            HashMap<String, String> hashMapContentNo = wfContentService.checkMaxContentNo(wfContentNo, wfContentModel.getWfContentFolderId(), wfContentPre, wfContentYear, wfContentPoint, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContentNo = hashMapContentNo.get("wfContentNo");
            if (!hashMapContentNo.get("wfContentNumber").equals("0")) {
                wfContentNumber = Integer.parseInt(hashMapContentNo.get("wfContentNumber"));
                wfContentPoint = Integer.parseInt(hashMapContentNo.get("wfContentPoint"));
                wfContentYear = Integer.parseInt(hashMapContentNo.get("wfContentYear"));
            }

            WfContent wfContent = new WfContent();
            wfContent.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContent.setWfContentOwnername(new UserProfileService().getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUserProfileFullName());

            wfContent.setWfDocumentId(wfContentModel.getWfDocumentId());
            wfContent.setWfContentContentDate(dateThaiToLocalDateTime(wfContentModel.getWfContentContentDate()));
            wfContent.setWfContentTitle(wfContentModel.getWfContentTitle());
            wfContent.setWfContentFolderId(wfContentModel.getWfContentFolderId());
            wfContent.setWfContentContentNo(wfContentNo);
            wfContent.setWfContentContentPre(wfContentPre);
            wfContent.setWfContentContentNumber(wfContentNumber);
            wfContent.setWfContentContentPoint(wfContentPoint);
            wfContent.setWfContentYear(wfContentYear);
            wfContent.setWfContentBookYear(0);
//            wfContent.setWfContentContentTime("00:00");
//            wfContent.setWfContentBookDate(dateThaiToLocalDateTime(wfContentModel.getWfContentBookDate()));
            wfContent.setWfContentBookNo("หนังสือเวียน");
//            wfContent.setWfContentBookPre("");
//            wfContent.setWfContentExpireDate(null);
//            wfContent.setWfContentFrom("");
//            wfContent.setWfContentTo("");
//            wfContent.setWfContentTitle("");
            wfContent.setWorkflowId(0);
            wfContent.setWfContentInt01(1);
            wfContent.setWfContentInt02(0);
            wfContent.setWfContentInt03(0);
            wfContent.setWfContentInt04(0);
            wfContent.setWfContentInt05(0);
            wfContent.setWfContentInt06(0);
            wfContent.setWfContentInt07(0);
            wfContent.setWfContentInt08(0);
            wfContent.setWfContentInt09(0);
            wfContent.setWfContentInt10(0);
//            Calendar calendar = Calendar.getInstance();
//            int yearToday = Calendar.getInstance().get(calendar.YEAR) + 543;
//            wfContent.setWfContentContentYear(yearToday);
//            wfContent.setWfContentBookYear(yearToday);
            wfContent = wfContentService.create(wfContent);

            wfContent.setOrderNo(wfContent.getId());
            wfContent.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            wfContent = wfContentService.update(wfContent);

            status = Response.Status.CREATED;
            responseData.put("data", wfContentService.tranformToModel(wfContent));
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "get user/structure by name",
            notes = "get user/structure by name",
            responseContainer = "List",
            response = Structure.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ShopDetail list success.")
        ,
        @ApiResponse(code = 404, message = "ShopDetail list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/prepareShowFromTo/{searchOrder}")
    public Response prepareShowFromTo(
            @ApiParam(name = "name", value = "name", required = true)
            @QueryParam("name") String name,
            @ApiParam(name = "searchOrder", value = "1=search structure 1st, 0=search user 1st", required = true)
            @PathParam("searchOrder") int searchOrder
    ) {
        LOG.info("prepareShowFromTo... ");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "prepareShowFromTo not found in the database.");
        try {
            String replacedName = name.replaceAll("xxxx", " ");
            int userType = 2;//outside
            StructureModel structureModel = null;
            StructureService structureService = new StructureService();

            if (searchOrder == 1) {//search structure 1st

                List<Structure> listStructure = structureService.listByName(replacedName);
                if (!listStructure.isEmpty()) {
                    userType = 1;//structure
                    structureModel = structureService.tranformToModel(listStructure.get(0));
                } else {
                    List<UserProfile> listUserProfile = new UserProfileService().listByName(replacedName);
                    if (!listUserProfile.isEmpty()) {
                        userType = 0;//user
                        structureModel = structureService.tranformToModel(structureService.getByIdNotRemoved(listUserProfile.get(0).getStructure().getId()));
                        structureModel.setId(listUserProfile.get(0).getId());//to use uerProfile.id to find node not structure.id
                    } else {
                        List<Organize> listOrganize = new OrganizeService().listByName(replacedName);
                        if (!listOrganize.isEmpty()) {
                            userType = 3;//external
                        }
                    }
                }

            } else if (searchOrder == 0) {//search user 1st

                List<UserProfile> listUserProfile = new UserProfileService().listByName(replacedName);
                if (!listUserProfile.isEmpty()) {
                    userType = 0;
                    structureModel = structureService.tranformToModel(structureService.getByIdNotRemoved(listUserProfile.get(0).getStructure().getId()));
                    structureModel.setId(listUserProfile.get(0).getId());
                } else {
                    List<Structure> listStructure = structureService.listByName(replacedName);
                    if (!listStructure.isEmpty()) {
                        userType = 1;
                        structureModel = structureService.tranformToModel(listStructure.get(0));
                    } else {
                        List<Organize> listOrganize = new OrganizeService().listByName(replacedName);
                        if (!listOrganize.isEmpty()) {
                            userType = 3;
                        }
                    }
                }

            } else {//search external 1st

                List<Organize> listOrganize = new OrganizeService().listByName(replacedName);
                if (!listOrganize.isEmpty()) {
                    userType = 3;
                } else {
                    List<Structure> listStructure = structureService.listByName(replacedName);
                    if (!listStructure.isEmpty()) {
                        userType = 1;
                        structureModel = structureService.tranformToModel(listStructure.get(0));
                    } else {
                        List<UserProfile> listUserProfile = new UserProfileService().listByName(replacedName);
                        if (!listUserProfile.isEmpty()) {
                            userType = 0;
                            structureModel = structureService.tranformToModel(structureService.getByIdNotRemoved(listUserProfile.get(0).getStructure().getId()));
                            structureModel.setId(listUserProfile.get(0).getId());
                        }
                    }
                }

            }

            status = Response.Status.OK;
            responseData.put("data", structureModel);
            responseData.put("userType", userType);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "get EMAILATTACHSIZE param",
            notes = "get EMAILATTACHSIZE param",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "get param success.")
        ,
        @ApiResponse(code = 404, message = "param not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/emailAttachSize")
    public Response getEmailAttachSize(
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("checkTitle...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "checkTitle not found in the database.");
        try {
            int emailAttachSize = 25;
            Param param = new ParamService().getByParamName("EMAILATTACHSIZE");
            if (param != null) {
                emailAttachSize = Integer.parseInt(param.getParamValue());
            }
            status = Response.Status.OK;
            responseData.put("data", emailAttachSize);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check duplicate WfContent bookNo",
            notes = "เช็คเลขที่หนังสือซ้ำ",
            response = WfContentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "checkBookNo success.")
        ,
        @ApiResponse(code = 404, message = "WfContent not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkBookNo")
    public Response checkBookNo(
            @BeanParam VersionModel versionModel,
            WfContentModel contentModel
    ) {
        LOG.info("checkTitle...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "checkBookNo not found in the database.");
        try {
            WfContentService contentService = new WfContentService();
            int tmp = contentService.countBookNo(contentModel.getWfContentBookNo(), contentModel.getWfContentFolderId(), contentModel.getWfContentContentYear());
            status = Response.Status.OK;
            responseData.put("data", tmp > 0);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
