package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserProfile;
import com.px.share.entity.FileAttach;
import com.px.share.entity.Param;
import com.px.wf.entity.Thegif;
import com.px.wf.entity.ThegifDepartment;
import com.px.wf.entity.WfContent;
import com.px.share.model.ListReturnModel;
import com.px.wf.model.get.ThegifDepartmentModel;
import com.px.wf.model.get.ThegifModel;
import com.px.wf.model.get.ThegifModel_ECMSMimeCode;
import com.px.wf.model.get.ThegifModel_ECMSMinistry;
import com.px.wf.model.get.ThegifModel_ECMSOfficer;
import com.px.wf.model.get.ThegifModel_ECMSOrganization;
import com.px.wf.model.get.ThegifModel_ECMSResult;
import com.px.wf.model.get.ThegifModel_ECMSSecret;
import com.px.wf.model.get.ThegifModel_ECMSSpeed;
import com.px.wf.model.post.ThegifModel_ECMSFORSEND;
import com.px.wf.model.get.ThegifModel_groupShowList;
import com.px.wf.model.get.ThegifModel_groupShowList2;
import com.px.wf.model.post.ThegifPostModel;
import com.px.admin.service.UserProfileService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.service.FileAttachService;
import com.px.share.service.ParamService;
import com.px.wf.ecms.LetterOther;
import com.px.wf.service.ThegifDepartmentService;
import com.px.wf.service.ThegifService;
import com.px.wf.service.WfContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;
import com.px.wf.ecms.MimeCode;
import com.px.wf.ecms.Ministry;
import com.px.wf.ecms.Organization;
import com.px.wf.ecms.Secret;
import com.px.wf.ecms.Speed;
import com.px.wf.ecms.WsecmsService;
import com.px.wf.ecms.WsecmsService_Service;
import com.px.wf.model.post.ThegifStatusSendModel;
import java.time.LocalDateTime;
import javax.ws.rs.core.HttpHeaders;


/**
 *
 * @author 
 */

@Api(value = "Thegif การสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ")
@Path("v1/thegifs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ThegifResource {
    
    @Context
    HttpHeaders httpHeaders;

    private static final Logger log = Logger.getLogger(ThegifResource.class.getName());

    @ApiOperation(
            value = "Method for create Thegif",
            notes = "สร้างข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Thegif created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userProfileId") int userProfileId,
            @BeanParam ThegifPostModel thegifPostModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            Thegif thegif = new Thegif();
            //userProfileId = 1;
            ThegifService thegifService = new ThegifService();
            thegif.setCreatedBy(userProfileId);
            if (thegif != null) {
                thegif.setThegifElementType(thegifPostModel.getThegifElementType());
                thegif.setThegifProcessId(thegifPostModel.getThegifProcessId());
                thegif.setThegifSendDate(thegifPostModel.getThegifSendDate());
                thegif.setThegifBookNo(thegifPostModel.getThegifBookNo());
                thegif.setThegifBookDate(thegifPostModel.getThegifBookDate());
                thegif.setThegifSpeed(thegifPostModel.getThegifSpeed());
                thegif.setThegifSecret(thegifPostModel.getThegifSecret());
                thegif.setThegifSenderDepartmentCode(thegifPostModel.getThegifSenderDepartmentCode());
                thegif.setThegifReceiverDepartmentCode(thegifPostModel.getThegifReceiverDepartmentCode());
                thegif.setThegifAcceptDate(thegifPostModel.getThegifAcceptDate());
                thegif.setThegifAcceptId(thegifPostModel.getThegifAcceptId());
                thegif.setThegifAcceptDepartmentCode(thegifPostModel.getThegifAcceptDepartmentCode());
                thegif.setThegifLetterStatus(thegifPostModel.getThegifLetterStatus());
                thegif.setThegifSubject(thegifPostModel.getThegifSubject());
                thegif.setThegifDescription(thegifPostModel.getThegifDescription());
                thegif.setThegifAttachment(thegifPostModel.getThegifAttachment());
                thegif.setThegifReference(thegifPostModel.getThegifReference());
                thegif.setThegifFilePath(thegifPostModel.getThegifFilePath());
                thegif = thegifService.create(thegif);

                thegif.setUpdatedBy(userProfileId);
                thegif.setOrderNo(thegif.getId());
                thegifService.update(thegif);
                responseData.put("data", thegifService.tranformToModel(thegif));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Thegif created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Thegif by id",
            notes = "ขอข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ ด้วย รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
            @PathParam("id") int id
    ) {
        log.info("getById...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(id);
            System.out.println("thegif="+thegif);
            if (thegif != null) {
                status = Response.Status.OK;
//                responseData.put("data", thegifService.tranformToModelChangeData(thegif));
                responseData.put("data", thegifService.tranformToModel(thegif));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Thegif By ElementType.",
            notes = "ขอรายการการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐโดยประเภทข้อมูล",
            responseContainer = "List",
            response = ThegifModel_groupShowList2.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif list success."),
        @ApiResponse(code = 404, message = "Thegif list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByElementType")
    public Response listByElementType(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "elementType", value = "ประเภท", required = false)
            @DefaultValue("") @QueryParam("elementType") String elementType,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate")
            @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc")
            @QueryParam("dir") String dir
    ) {
        log.info("listByElementType...");
        log.info("elementType = " + elementType);
        log.info("offset = " + offset);
        log.info("limit = " + limit);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();
            ArrayList<ThegifModel_groupShowList2> listThegifModel_groupShowList2 = new ArrayList();
            List<Thegif> listThegif = thegifService.listByElementType(elementType, offset, limit, sort, dir);
            //System.out.println("listThegifSize="+listThegif.size());
            int countAll = thegifService.countAllListByElementType(elementType);
            //System.out.println("countAll="+countAll);
            int count = listThegif.size();
            int next = 0;
            if (count >= limit) {
                next = offset + limit;
            }
            if (next >= countAll) {
                next = 0;
            }
            ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
            //System.out.println("listThegif="+listThegif);
            //System.out.println("listThegifSize="+listThegif.size());
            if (!listThegif.isEmpty()) {
                List<ThegifModel_groupShowList> listThegifModel_groupShowList = new ArrayList();
                ThegifModel_groupShowList2 thegifModel_groupShowList2 = new ThegifModel_groupShowList2();
                for (Thegif thegif : listThegif) {
                    listThegifModel_groupShowList = thegifService.listbyFieldName(thegif, elementType);
                    thegifModel_groupShowList2 = thegifService.tranformToModelGroupShowList2(thegif, listThegifModel_groupShowList);
                    listThegifModel_groupShowList2.add(thegifModel_groupShowList2);
                }
            }
            listThegifModel_groupShowList2.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_groupShowList2);
            responseData.put("list", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete Thegif by id.",
            notes = "ลบข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif deleted by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "id", value = "รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
            @PathParam("id") int id
    ) {
        log.info("remove...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.remove(id, userProfileId);
            if (thegif != null) {
                status = Response.Status.OK;
                responseData.put("data", thegifService.tranformToModel(thegif));
                responseData.put("message", "");
            }
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update Thegif.",
            notes = "แก้ไขข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif updeted by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "id", value = "รหัสการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ", required = true)
            @PathParam("id") int id,
            @BeanParam ThegifPostModel thegifPostModel
    ) {
        log.info("update...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();

            //userProfileId = 1;
            Thegif thegif = thegifService.getById(id);
            if (thegif != null) {
                thegif.setThegifProcessId(thegifPostModel.getThegifProcessId());
                thegif.setThegifSendDate(thegifPostModel.getThegifSendDate());
                thegif.setThegifBookNo(thegifPostModel.getThegifBookNo());
                thegif.setThegifBookDate(thegifPostModel.getThegifBookDate());
                thegif.setThegifSpeed(thegifPostModel.getThegifSpeed());
                thegif.setThegifSecret(thegifPostModel.getThegifSecret());
                thegif.setThegifSenderDepartmentCode(thegifPostModel.getThegifSenderDepartmentCode());
                thegif.setThegifReceiverDepartmentCode(thegifPostModel.getThegifReceiverDepartmentCode());
                thegif.setThegifAcceptDate(thegifPostModel.getThegifAcceptDate());
                thegif.setThegifAcceptId(thegifPostModel.getThegifAcceptId());
                thegif.setThegifAcceptDepartmentCode(thegifPostModel.getThegifAcceptDepartmentCode());
                thegif.setThegifLetterStatus(thegifPostModel.getThegifLetterStatus());
                thegif.setThegifSubject(thegifPostModel.getThegifSubject());
                thegif.setThegifDescription(thegifPostModel.getThegifDescription());
                thegif.setThegifAttachment(thegifPostModel.getThegifAttachment());
                thegif.setThegifReference(thegifPostModel.getThegifReference());
                thegif.setThegifFilePath(thegifPostModel.getThegifFilePath());

                thegif.setUpdatedBy(userProfileId);
                thegif.setUpdatedDate(LocalDateTime.now());
                thegif = thegifService.update(thegif);

                status = Response.Status.OK;
                responseData.put("data", thegifService.tranformToModel(thegif));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Thegif",
            notes = "ค้นหารายการการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            responseContainer = "List",
            response = ThegifModel_groupShowList2.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif list success."),
        @ApiResponse(code = 404, message = "Thegif list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchThegifReceive")
    public Response searchThegifReceive(
            @Context UriInfo uriInfo,
            @ApiParam(name = "thegifSenderDepartmentCode", value = "ชื่อหน่วยงานผู้ส่ง", required = false)
            @DefaultValue("") @QueryParam("thegifSenderDepartmentCode") String thegifSenderDepartmentCode,
            @ApiParam(name = "thegifReceiverDepartmentCode", value = "ชื่อหน่วยงานผู้รับ", required = false)
            @DefaultValue("") @QueryParam("thegifReceiverDepartmentCode") String thegifReceiverDepartmentCode,
            @ApiParam(name = "thegifBookNo", value = "เลขที่หนังสือ", required = false)
            @QueryParam("thegifBookNo") String thegifBookNo,
            @ApiParam(name = "thegifBookDate", value = "ลงวันที่ format วัน/เดือน/พ.ศ", required = false)
            @QueryParam("thegifBookDate") String thegifBookDate,
            @ApiParam(name = "thegifSubject", value = "เรื่อง", required = false)
            @QueryParam("thegifSubject") String thegifSubject,
            @ApiParam(name = "thegifSecret", value = "ชั้นความลับ", required = false)
            @QueryParam("thegifSecret") String thegifSecret,
            @ApiParam(name = "thegifSpeed", value = "ชั้นความเร็ว", required = false)
            @QueryParam("thegifSpeed") String thegifSpeed,
            @ApiParam(name = "thegifSendDate", value = "วันที่ส่ง format วัน/เดือน/พ.ศ", required = false)
            @QueryParam("thegifSendDate") String thegifSendDate,
            @ApiParam(name = "thegifLetterStatus", value = "สถานะจดหมาย", required = false)
            @QueryParam("thegifLetterStatus") String thegifLetterStatus,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        log.info("searchThegif...");
        log.info("thegifSenderDepartmentCode = " + thegifSenderDepartmentCode);
        log.info("thegifReceiverDepartmentCode = " + thegifReceiverDepartmentCode);
        log.info("thegifBookNo = " + thegifBookNo);
        log.info("thegifBookDate = " + thegifBookDate);
        log.info("thegifSubject = " + thegifSubject);
        log.info("thegifSecret = " + thegifSecret);
        log.info("thegifSpeed = " + thegifSpeed);
        log.info("thegifSendDate = " + thegifSendDate);
        log.info("thegifLetterStatus = " + thegifLetterStatus);
        log.info("offset = " + offset);
        log.info("limit = " + limit);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        Gson gs1 = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifService thegifService = new ThegifService();

            String fieldSearch = "thegifSenderDepartmentCode,thegifReceiverDepartmentCode,thegifBookNo,thegifBookDate,thegifSubject,thegifSecret,thegifSpeed,thegifSendDate,thegifLetterStatus";

            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            for (String key : queryParams.keySet()) {
                if (fieldSearch.contains(key)) {
                    for (String value : queryParams.get(key)) {
                        String[] valueArray = value.split(",");
                        int i = 0;
                        for (i = 0; i < valueArray.length; i++) {
                            log.info("key = " + key + " value = " + valueArray[i]);
                        }
                    }
                }
            }
            ArrayList<ThegifModel_groupShowList2> listThegifModel_groupShowList2 = new ArrayList();
            List<Thegif> listThegif = thegifService.searchThegifReceive(queryParams, offset, limit, sort, dir);
            int countAll = thegifService.countSearchThegifReceive(queryParams);
            int count = listThegif.size();
            int next = 0;
            if (count >= limit) {
                next = offset + limit;
            }
            if (next >= countAll) {
                next = 0;
            }
            ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
            if (!listThegif.isEmpty()) {
                List<ThegifModel_groupShowList> listThegifModel_groupShowList = new ArrayList();
                ThegifModel_groupShowList2 thegifModel_groupShowList2 = new ThegifModel_groupShowList2();
                for (Thegif thegif : listThegif) {
                    listThegifModel_groupShowList = thegifService.listbyFieldName(thegif, "CorrespondenceLetter");
                    thegifModel_groupShowList2 = thegifService.tranformToModelGroupShowList2(thegif, listThegifModel_groupShowList);
                    listThegifModel_groupShowList2.add(thegifModel_groupShowList2);
                }
            }

            listThegifModel_groupShowList2.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_groupShowList2);
            responseData.put("list", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);

        } catch (Exception ex) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search Thegif",
            notes = "ค้นหารายการการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐ",
            responseContainer = "List",
            response = ThegifModel_groupShowList2.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif list success."),
        @ApiResponse(code = 404, message = "Thegif list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/searchThegifStatus")
    public Response searchThegifStatus(
            @Context UriInfo uriInfo,
            @ApiParam(name = "thegifElementType", value = "ประเภท", required = false)
            @QueryParam("thegifElementType") String thegifElementType,
            @ApiParam(name = "thegifBookNo", value = "เลขที่หนังสือ", required = false)
            @QueryParam("thegifBookNo") String thegifBookNo,
            @ApiParam(name = "thegifBookDate", value = "ลงวันที่ format วัน/เดือน/พ.ศ", required = false)
            @QueryParam("thegifBookDate") String thegifBookDate,
            @ApiParam(name = "thegifSubject", value = "เรื่อง", required = false)
            @QueryParam("thegifSubject") String thegifSubject,
            @ApiParam(name = "thegifAcceptDate", value = "วันที่ลงรับ", required = false)
            @QueryParam("thegifAcceptDate") String thegifAcceptDate,
            @ApiParam(name = "thegifAcceptId", value = "เลขที่รับ", required = false)
            @QueryParam("thegifAcceptId") String thegifAcceptId,
            @ApiParam(name = "thegifAcceptDepartmentCode", value = "รหัสโครงสร้างผู้ลงรับ", required = false)
            @QueryParam("thegifAcceptDepartmentCode") String thegifAcceptDepartmentCode,
            @ApiParam(name = "thegifLetterStatus", value = "สถานะจดหมาย", required = false)
            @QueryParam("thegifLetterStatus") String thegifLetterStatus,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        log.info("searchThegifStatus...");
        log.info("thegifElementType = " + thegifElementType);
        log.info("thegifBookNo = " + thegifBookNo);
        log.info("thegifBookDate = " + thegifBookDate);
        log.info("thegifSubject = " + thegifSubject);
        log.info("thegifAcceptDate = " + thegifAcceptDate);
        log.info("thegifAcceptId = " + thegifAcceptId);
        log.info("thegifAcceptDepartmentCode = " + thegifAcceptDepartmentCode);
        log.info("thegifLetterStatus = " + thegifLetterStatus);
        log.info("offset = " + offset);
        log.info("limit = " + limit);
        log.info("sort = " + sort);
        log.info("dir = " + dir);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        Gson gs1 = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ThegifService thegifService = new ThegifService();

            String fieldSearch = "thegifElementType,thegifBookNo,thegifBookDate,thegifSubject,thegifAcceptDate,thegifAcceptId,thegifAcceptDepartmentCode,thegifLetterStatus";

            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            for (String key : queryParams.keySet()) {
                if (fieldSearch.contains(key)) {
                    for (String value : queryParams.get(key)) {
                        String[] valueArray = value.split(",");
                        int i = 0;
                        for (i = 0; i < valueArray.length; i++) {
                            log.info("key = " + key + " value = " + valueArray[i]);
                        }
                    }
                }
            }
            ArrayList<ThegifModel_groupShowList2> listThegifModel_groupShowList2 = new ArrayList();
            List<Thegif> listThegif = thegifService.searchThegifStatus(queryParams, offset, limit, sort, dir);
            int countAll = thegifService.countSearchThegifStatus(queryParams);
            int count = listThegif.size();
            int next = 0;
            if (count >= limit) {
                next = offset + limit;
            }
            if (next >= countAll) {
                next = 0;
            }
            ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
            if (!listThegif.isEmpty()) {
                List<ThegifModel_groupShowList> listThegifModel_groupShowList = new ArrayList();
                ThegifModel_groupShowList2 thegifModel_groupShowList2 = new ThegifModel_groupShowList2();
                for (Thegif thegif : listThegif) {
                    listThegifModel_groupShowList = thegifService.listbyFieldName(thegif, "SendLetter");
                    thegifModel_groupShowList2 = thegifService.tranformToModelGroupShowList2(thegif, listThegifModel_groupShowList);
                    listThegifModel_groupShowList2.add(thegifModel_groupShowList2);
                }
            }

            listThegifModel_groupShowList2.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_groupShowList2);
            responseData.put("list", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);

        } catch (Exception ex) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSTimeCheck",
            notes = "ขอข้อมูลการตรวจสอบเวลา",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSTimeCheck success."),
        @ApiResponse(code = 404, message = "ECMSTimeCheck not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSTimeCheck")
    public Response getECMSTimeCheck() {
        log.info("getECMSTimeCheck...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSTimeCheck not found.");
        try {
            System.out.println("----------------------------ECMS----------------------------");
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();
            System.out.println("tmpECMSdepCode="+tmpECMSdepCode);
            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();
           System.out.println("tmpECMSService="+tmpECMSService);
            ThegifService thegifService = new ThegifService();
            System.out.println("----------------------------ResourceECMS----------------------------");
            System.out.println("tmpECMSdepCode="+tmpECMSdepCode);
            System.out.println("tmpECMSService="+tmpECMSService);
            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSTimeCheck(tmpECMSdepCode, tmpECMSService, tmpECMSService);
            System.out.println("listThegifModel_ECMSResult="+listThegifModel_ECMSResult);
            System.out.println("listThegifModel_ECMSResultSize="+listThegifModel_ECMSResult.size());
            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) { 
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSStatus",
            notes = "ขอข้อมูลการสอบถามสถานะส่งหนังสือ",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSStatus success."),
        @ApiResponse(code = 404, message = "ECMSStatus not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSStatus")
    public Response getECMSStatus() {
        log.info("getECMSStatus...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSStatus not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue(); 

            ThegifService thegifService = new ThegifService();
            
            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSStatus(tmpECMSdepCode, tmpECMSService, tmpECMSService);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSSpeed",
            notes = "ขอข้อมูลรหัสชั้นความเร็ว",
            response = ThegifModel_ECMSSpeed.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSSpeed success."),
        @ApiResponse(code = 404, message = "ECMSSpeed not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSSpeed")
    public Response getECMSSpeed() {
        log.info("getECMSSpeed...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "getECMSSpeed not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ThegifService thegifService = new ThegifService();
            List<Speed> listECMSSpeed = thegifService.getECMSSpeed(tmpECMSdepCode, tmpECMSService, tmpECMSService);

            ArrayList<ThegifModel_ECMSSpeed> listECMSSpeedModel = new ArrayList();
            for (Speed speed : listECMSSpeed) {
                listECMSSpeedModel.add(thegifService.tranformToModelECMSSpeed(speed));
            }

            listECMSSpeedModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listECMSSpeedModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSSecret",
            notes = "ขอข้อมูลรหัสชั้นความลับ",
            response = ThegifModel_ECMSSpeed.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSSecret success."),
        @ApiResponse(code = 404, message = "ECMSSecret not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSSecret")
    public Response getECMSSecret() {
        log.info("getECMSSecret...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSSecret not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ThegifService thegifService = new ThegifService();
            List<Secret> listECMSSecret = thegifService.getECMSSecret(tmpECMSdepCode, tmpECMSService, tmpECMSService);

            ArrayList<ThegifModel_ECMSSecret> listECMSSecretModel = new ArrayList();
            for (Secret secret : listECMSSecret) {
                listECMSSecretModel.add(thegifService.tranformToModelECMSSecret(secret));
            }

            listECMSSecretModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listECMSSecretModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSMimeCode",
            notes = "ขอข้อมูลประเภทไฟล์เอกสาร",
            response = ThegifModel_ECMSMimeCode.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSMimeCode success."),
        @ApiResponse(code = 404, message = "ECMSMimeCode not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSMimeCode")
    public Response getECMSMimeCode() {
        log.info("getECMSMimeCode...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSMimeCode not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ThegifService thegifService = new ThegifService();
            List<MimeCode> listECMSMimeCode = thegifService.getECMSMimeCode(tmpECMSdepCode, tmpECMSService, tmpECMSService);

            ArrayList<ThegifModel_ECMSMimeCode> listECMSMimeCodeModel = new ArrayList();
            for (MimeCode mimeCode : listECMSMimeCode) {
                listECMSMimeCodeModel.add(thegifService.tranformToModelECMSMimeCode(mimeCode));
            }

            listECMSMimeCodeModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listECMSMimeCodeModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSMinistry",
            notes = "ขอข้อมูลรหัสกระทรวง",
            response = ThegifModel_ECMSMinistry.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSMinistry success."),
        @ApiResponse(code = 404, message = "ECMSMinistry not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSMinistry")
    public Response getECMSMinistry() {
        log.info("getECMSMinistry...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSMinistry not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();
log.info("tmpECMSdepCode = "+tmpECMSdepCode);
log.info("tmpECMSService = "+tmpECMSService);
            ThegifService thegifService = new ThegifService();
//log.info("tmpECMSdepCode222 = "+tmpECMSdepCode);
//log.info("tmpECMSService222 = "+tmpECMSService);
            List<Ministry> listECMSMinistry = thegifService.getECMSMinistry(tmpECMSdepCode, tmpECMSService, tmpECMSService);
//
            ArrayList<ThegifModel_ECMSMinistry> listECMSMinistryModel = new ArrayList();
            for (Ministry ministry : listECMSMinistry) {
                listECMSMinistryModel.add(thegifService.tranformToModelECMSMinistry(ministry));
            }

//            WsecmsService_Service service = new WsecmsService_Service();
//            WsecmsService port = service.getWsecmsServicePort();
	
//            String fromDepCode = tmpECMSdepCode;
//            String fromWsurl = tmpECMSService;
//            String toWsurl = tmpECMSService;
//            String tagLetter1 = "GetMinistryOrganizationList";
//            String tagLetter2 = "";
//            LetterOther letterother = null;
//            String depname = "";
//            String depcode = "";
//            String result = port.otherLetterService(fromDepCode, fromWsurl, toWsurl, tagLetter1, tagLetter2, letterother);
//            List<Ministry> list_ministry = port.getMinistryService(result);	
//            Ministry ministry = new Ministry();
//            ArrayList<ThegifModel_ECMSMinistry> listECMSMinistryModel = new ArrayList();
//            for(int m=0;m<list_ministry.size();m++){
//		ministry = list_ministry.get(m);
//		depname = ministry.getMinistryTHName();
//		depcode = ministry.getMinistryID();
//		System.out.println("depname = "+depname);
//            }
            listECMSMinistryModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listECMSMinistryModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSOrganization",
            notes = "ขอข้อมูลหน่วยงาน",
            response = ThegifModel_ECMSOrganization.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSOrganization success."),
        @ApiResponse(code = 404, message = "ECMSOrganization not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSOrganization")
    public Response getECMSOrganization() {
        log.info("getECMSOrganization...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSOrganization not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ThegifService thegifService = new ThegifService();
            List<Organization> listECMSOrganization = thegifService.getECMSOrganization(tmpECMSdepCode, tmpECMSService, tmpECMSService);

            ArrayList<ThegifModel_ECMSOrganization> listECMSOrganizationModel = new ArrayList();
            for (Organization organization : listECMSOrganization) {
                listECMSOrganizationModel.add(thegifService.tranformToModelECMSOrganization(organization));
            }

            listECMSOrganizationModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listECMSOrganizationModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check ECMSLetter",
            notes = "รับข้อมูลจาก ecms",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Check ECMSLetter successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkECMSLetter")
    public Response checkECMSLetter(
            @HeaderParam("userProfileId") int userProfileId
    ) {
        log.info("checkECMSLetter...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            Param param3 = paramService.getByParamName("ECMSDELETEREQUEST");
            String tmpECMSDeleteRequest = param3.getParamValue();

            ThegifService thegifService = new ThegifService();

            String result = thegifService.checkECMSLetter(tmpECMSdepCode, tmpECMSService, tmpECMSService, tmpECMSDeleteRequest, userProfileId);
            responseData.put("data", result);

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Check ECMSLetter successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for send ECMSLetter",
            notes = "ส่งข้อมูลเข้า ecms",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Send ECMSLetter success."),
        @ApiResponse(code = 404, message = "ECMSLetter not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/sendECMSLetter/{wfContentId}/{DEPCODE}")
    public Response sendECMSLetter(
            @BeanParam VersionModel versionModel,
            @BeanParam ListOptionModel listOptionModel,            
            @HeaderParam("clientIp") String clientIp,
            @ApiParam(name = "wfContentId", value = "รหัสหนังสือ", required = true)
            @PathParam("wfContentId") int wfContentId,
            @ApiParam(name = "DEPCODE", value = "รหัสหน่วยงาน", required = true)
            @PathParam("DEPCODE") String DEPCODE
    ) {
        log.info("sendECMSLetter...");
        log.info("wfContentId = " + wfContentId);
        log.info("DEPCODE = " + DEPCODE);
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
        responseData.put("message", "ECMSLetter by id not found in the database.");
        try {
            int userProfileId = Integer.parseInt(httpHeaders.getHeaderString("userID"));
            String deptId = DEPCODE;
            ThegifService thegifService = new ThegifService();
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();
            System.out.println("tmpECMSdepCode="+tmpECMSdepCode);
            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(wfContentId);
            System.out.println("wfContent="+wfContent);
            System.out.println("userProfileId=========="+userProfileId);
            //list fileAttach
            FileAttachService fileAttachService = new FileAttachService();
            List<FileAttach> listFileAttach = new ArrayList();
            List<FileAttach> listFileAttach1 = fileAttachService.listAllByLinkTypeLinkId("dms", wfContent.getWfDocumentId(), "createdDate", "asc");
            System.out.println("listFileAttach1.Size="+listFileAttach1.size());
            if (!listFileAttach1.isEmpty()) {
                for (FileAttach fileAttach : listFileAttach1) {
                    listFileAttach.add(fileAttach);
                }
            }

            System.out.println("userProfileId="+userProfileId);
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getById(userProfileId);
//            System.out.println("userProfile="+userProfile);
//            if(userProfile != null) System.out.println("userProfileFullname="+userProfile.getUserProfileFullName());
            //ผู้ส่ง
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifModel_ECMSOfficer thegifModel_ECMSOfficerSender = new ThegifModel_ECMSOfficer();
            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(tmpECMSdepCode);
            System.out.println("thegifDepartment="+thegifDepartment);
            if (thegifDepartment != null) {
            System.out.println("SenderthegifDepartmentName="+thegifDepartment.getThegifDepartmentName());
            System.out.println("SenderthegifDepartmentCode="+thegifDepartment.getThegifDepartmentCode());
                ThegifDepartment thegifDepartmentParent = thegifDepartmentService.getById(thegifDepartment.getParentId());
                
//                thegifModel_ECMSOfficerSender.setName(thegifDepartment.getThegifDepartmentName()); //ของเก่า
//                thegifModel_ECMSOfficerSender.setlName(thegifDepartmentParent.getThegifDepartmentName());
//                thegifModel_ECMSOfficerSender.setTitle(userProfile.getUserProfileFullName());

                thegifModel_ECMSOfficerSender.setName(""); // ชื่อผู้ส่ง userProfile.getUserProfileFirstName() use This
                thegifModel_ECMSOfficerSender.setlName(""); // นามสกุลผู้ส่ง userProfile.getUserProfileLastName() use This
//                thegifModel_ECMSOfficerSender.setName(wfContent.getWfContentFrom()); // ชื่อผู้ส่ง userProfile.getUserProfileFirstName() For test ecms
//                thegifModel_ECMSOfficerSender.setlName(wfContent.getWfContentFrom()); // นามสกุลผู้ส่ง userProfile.getUserProfileLastName() For test ecms               
                thegifModel_ECMSOfficerSender.setTitle(wfContent.getWfContentFrom()); // จาก
                thegifModel_ECMSOfficerSender.setDeptId(tmpECMSdepCode);
                thegifModel_ECMSOfficerSender.setMinistryId(thegifDepartmentParent.getThegifDepartmentCode());
                thegifModel_ECMSOfficerSender.setWSURL(thegifDepartment.getThegifDepartmentServiceName());
            }else{
                thegifModel_ECMSOfficerSender.setName(""); //use this
                thegifModel_ECMSOfficerSender.setlName(""); //use this
//                thegifModel_ECMSOfficerSender.setTitle(userProfile.getUserProfileFullName());
//                thegifModel_ECMSOfficerSender.setName(wfContent.getWfContentFrom()); //for test ecms
//                thegifModel_ECMSOfficerSender.setlName(wfContent.getWfContentFrom()); //for test ecms
                thegifModel_ECMSOfficerSender.setTitle(wfContent.getWfContentFrom());
                thegifModel_ECMSOfficerSender.setDeptId(tmpECMSdepCode);
                thegifModel_ECMSOfficerSender.setMinistryId("");
                thegifModel_ECMSOfficerSender.setWSURL("");
            }

            //ผู้รับ 
            System.out.println("Reciever");
            ThegifDepartment thegifDepartment1 = thegifDepartmentService.getByThegifDepartmentCode(deptId);
            System.out.println("thegifDepartment1="+thegifDepartment1);
            ThegifDepartment thegifDepartmentParent1 = thegifDepartmentService.getById(thegifDepartment1.getParentId());
            System.out.println("thegifDepartmentParent1="+thegifDepartmentParent1);
            ThegifModel_ECMSOfficer thegifModel_ECMSOfficerReceiver = new ThegifModel_ECMSOfficer();
            thegifModel_ECMSOfficerReceiver.setName(""); // thegifDepartment1.getThegifDepartmentName() // use this
            thegifModel_ECMSOfficerReceiver.setlName("");  // thegifDepartmentParent1.getThegifDepartmentName() //use this
//            thegifModel_ECMSOfficerReceiver.setName(wfContent.getWfContentTo()); // thegifDepartment1.getThegifDepartmentName()
//            thegifModel_ECMSOfficerReceiver.setlName(wfContent.getWfContentTo());  // thegifDepartmentParent1.getThegifDepartmentName()            
            thegifModel_ECMSOfficerReceiver.setTitle(wfContent.getWfContentTo()); // เดิมว่าง ใส่เป็นช่อง ถึง หรือเรียน แทน
            thegifModel_ECMSOfficerReceiver.setDeptId(deptId);
            thegifModel_ECMSOfficerReceiver.setMinistryId(thegifDepartmentParent1.getThegifDepartmentCode());
            thegifModel_ECMSOfficerReceiver.setWSURL(thegifDepartment1.getThegifDepartmentServiceName());

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.sendECMSLetter(tmpECMSdepCode, wfContent, listFileAttach, thegifModel_ECMSOfficerSender, thegifModel_ECMSOfficerReceiver,deptId,wfContentId);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get RejectLetterNotifier",
            notes = "ปฏิเสธหนังสือ",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RejectLetterNotifier success."),
        @ApiResponse(code = 404, message = "RejectLetterNotifier not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSRejectLetterNotifier")
    public Response getECMSRejectLetterNotifier(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "idThegif", value = "รหัส thegif", required = true)
            @QueryParam("idThegif") int idThegif
    ) {
        log.info("getECMSRejectLetterNotifier...");
        log.info("idThegif = " + idThegif);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "RejectLetterNotifier not found.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(idThegif);
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegif.getThegifSenderDepartmentCode());

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSRejectLetterNotifier(tmpECMSdepCode, tmpECMSService, thegifDepartment.getThegifDepartmentServiceName(), thegif);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get InvalidLetterNotifier",
            notes = "แจ้งหนังสือผิด/ส่งผิด",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InvalidLetterNotifier success."),
        @ApiResponse(code = 404, message = "InvalidLetterNotifier not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSInvalidLetterNotifier")
    public Response getECMSInvalidLetterNotifier(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "idThegif", value = "รหัส thegif", required = true)
            @QueryParam("idThegif") int idThegif
    ) {
        log.info("getECMSInvalidLetterNotifier...");
        log.info("idThegif = " + idThegif);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "InvalidLetterNotifier not found.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(idThegif);
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegif.getThegifSenderDepartmentCode());

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSInvalidLetterNotifier(tmpECMSdepCode, tmpECMSService, thegifDepartment.getThegifDepartmentServiceName(), thegif);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get InvalidAcceptIDNotifier",
            notes = "แจ้งเลขรับผิด",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InvalidAcceptIDNotifier success."),
        @ApiResponse(code = 404, message = "InvalidAcceptIDNotifier not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSInvalidAcceptIDNotifier")
    public Response getECMSInvalidAcceptIDNotifier(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "idThegif", value = "รหัส thegif", required = true)
            @QueryParam("idThegif") int idThegif
    ) {
        log.info("getECMSInvalidAcceptIDNotifier...");
        log.info("idThegif = " + idThegif);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "InvalidAcceptIDNotifier not found.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(idThegif);
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegif.getThegifAcceptDepartmentCode());

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSInvalidAcceptIDNotifier(tmpECMSdepCode, tmpECMSService, thegifDepartment.getThegifDepartmentServiceName(), thegif);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSDeleteGovernmentDocumentRequest",
            notes = "ขอลบหนังสือภายนอกเพื่อส่งใหม่",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSDeleteGovernmentDocumentRequest success."),
        @ApiResponse(code = 404, message = "ECMSDeleteGovernmentDocumentRequest not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSDeleteGovernmentDocumentRequest")
    public Response getECMSDeleteGovernmentDocumentRequest(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "idThegif", value = "รหัส thegif", required = true)
            @QueryParam("idThegif") int idThegif
    ) {
        log.info("getECMSDeleteGovernmentDocumentRequest...");
        log.info("idThegif = " + idThegif);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSDeleteGovernmentDocumentRequest not found.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(idThegif);

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSDeleteGovernmentDocumentRequest(tmpECMSdepCode, tmpECMSService, tmpECMSService, thegif);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get ECMSDeleteRequest",
            notes = "ลบเรื่องล่าสุด",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSDeleteRequest success."),
        @ApiResponse(code = 404, message = "ECMSDeleteRequest not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})//TEXT_PLAIN
    @Path(value = "/getECMSDeleteRequest")
    public Response getECMSDeleteRequest(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "thegifProcessId", value = "รหัสการดำเนินการ", required = true)
            @QueryParam("thegifProcessId") String thegifProcessId
    ) {
        log.info("getECMSDeleteRequest...");
        log.info("thegifProcessId = " + thegifProcessId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "ECMSDeleteRequest not found.");
        try {
            ThegifService thegifService = new ThegifService();

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSDeleteRequest(tmpECMSdepCode, tmpECMSService, tmpECMSService, thegifProcessId);

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get AcceptLetterNotifier",
            notes = "ส่งเลขรับกลับไป",
            response = ThegifModel_ECMSResult.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "AcceptLetterNotifier success."),
        @ApiResponse(code = 404, message = "AcceptLetterNotifier not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getECMSAcceptLetterNotifier")
    public Response getECMSAcceptLetterNotifier(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "idThegif", value = "รหัส thegif", required = true)
            @QueryParam("idThegif") int idThegif,
            @ApiParam(name = "idWfContent", value = "รหัสหนังสือ", required = true)
            @QueryParam("idWfContent") int idWfContent
    ) {
        log.info("getECMSAcceptLetterNotifier...");
        log.info("idThegif = " + idThegif);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "AcceptLetterNotifier not found.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(idThegif);
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(thegif.getThegifSenderDepartmentCode());

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            WfContentService wfContentService = new WfContentService();
            WfContent wfContent = wfContentService.getById(idWfContent);

            ArrayList<ThegifModel_ECMSResult> listThegifModel_ECMSResult = thegifService.getECMSAcceptLetterNotifier(tmpECMSdepCode, tmpECMSService, thegifDepartment.getThegifDepartmentServiceName(), thegif, wfContent.getWfContentContentNo());

            listThegifModel_ECMSResult.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listThegifModel_ECMSResult);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update LetterStatus.",
            notes = "แก้ไขสถานะจดหมาย",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LetterStatus updeted by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateLettetStatus")
    public Response updateLettetStatus(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "id", value = "รหัส thegif", required = true)
            @FormParam("id") int id,
            @ApiParam(name = "letterStatus", value = "สถานะจดหมาย", required = true)
            @FormParam("letterStatus") String letterStatus
    ) {
        log.info("updateLettetStatus...");
        log.info("id = " + id);
        log.info("letterStatus = " + letterStatus);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();

            Thegif thegif = thegifService.getById(id);
            thegif.setThegifLetterStatus(letterStatus);
            thegif.setUpdatedBy(userProfileId);
            thegif.setUpdatedDate(LocalDateTime.now());
            thegif = thegifService.update(thegif);

            status = Response.Status.OK;
            responseData.put("data", thegifService.tranformToModel(thegif));
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "Method for update LetterStatus For 8.5",
            notes = "แก้ไขสถานะจดหมายสำหรับ8.5",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "LetterStatus updeted by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateLettetStatus85")
    public Response updateLettetStatus85(
            @HeaderParam("userProfileId") int userProfileId,
            ThegifModel_ECMSFORSEND thegif_ecmsforsend,
            @ApiParam(name = "letterStatus", value = "letterStatus", required = false)
            @QueryParam("letterStatus") String letterStatus           
    ) {
        log.info("updateLettetStatus85...");
        log.info("id = " + thegif_ecmsforsend.getThegifId());
        log.info("letterStatus = " + thegif_ecmsforsend.getThegifLetterStatus());
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(thegif_ecmsforsend.getThegifId());
            thegif.setThegifLetterStatus(letterStatus); //thegif_ecmsforsend.getThegifLetterStatus()
            thegif.setUpdatedBy(userProfileId);
            thegif.setUpdatedDate(LocalDateTime.now());
            thegif = thegifService.update(thegif);

            status = Response.Status.OK;
            responseData.put("data", thegifService.tranformToModel(thegif));
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }    

    @ApiOperation(
            value = "Method for create Thegif from WfContent For Send For 8.5",
            notes = "สร้างข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐจากหนังสือสำหรับส่งสำหรับ 8.5",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Thegif from WfContent created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createThegifFromWfContentForSend85")
    public Response createThegifFromWfContentForSend85(
            @HeaderParam("userProfileId") int userProfileId,
            ThegifModel_ECMSFORSEND thegif_ecmsforsend
    ) {
        log.info("createThegifFromWfContentForSend85...");
        log.info("id = " + thegif_ecmsforsend.getWfContentId());
        log.info("filePath = " + thegif_ecmsforsend.getFilePath());
        log.info("thegifDepartmentReceiver = " + thegif_ecmsforsend.getThegifDepartmentReceiver());
        log.info("thegifLetterStatus = " + thegif_ecmsforsend.getThegifLetterStatus()); 
        Gson gs = new GsonBuilder()
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
            WfContent wfContent = wfContentService.getById(thegif_ecmsforsend.getWfContentId());

            Thegif thegif = new Thegif();
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();

            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            //thegifDepartmentReceiver = "00042";
            //userProfileId = 1;
            ThegifService thegifService = new ThegifService();
            thegif.setCreatedBy(userProfileId);
            if (thegif != null) {
                thegif.setThegifElementType("SendLetter");
                thegif.setThegifProcessId(String.valueOf(wfContent.getId()));
                thegif.setThegifSendDate(thegifService.ECMSNowDate());
                thegif.setThegifBookNo(wfContent.getWfContentBookNo());
                thegif.setThegifBookDate(wfContent.getWfContentBookDate().toString());
                thegif.setThegifSpeed("00"+String.valueOf(wfContent.getWfContentSpeed()));
                thegif.setThegifSecret("00"+String.valueOf(wfContent.getWfContentSecret()));
                thegif.setThegifSenderDepartmentCode(tmpECMSdepCode);
                thegif.setThegifReceiverDepartmentCode(thegif_ecmsforsend.getThegifDepartmentReceiver());
                thegif.setThegifAcceptDate(null);
                thegif.setThegifAcceptId(null);
                thegif.setThegifAcceptDepartmentCode(null);
                thegif.setThegifLetterStatus(null);
                thegif.setThegifSubject(wfContent.getWfContentTitle());
                thegif.setThegifDescription(wfContent.getWfContentDescription());
                thegif.setThegifAttachment(wfContent.getWfContentAttachment());
                thegif.setThegifReference(wfContent.getWfContentReference());
                thegif.setThegifFilePath(thegif_ecmsforsend.getFilePath());
                thegif.setThegifLetterStatus(thegif_ecmsforsend.getThegifLetterStatus());
                thegif = thegifService.create(thegif);
                thegif.setUpdatedBy(userProfileId);
                thegif.setOrderNo(thegif.getId());
                thegifService.update(thegif);
                responseData.put("data", thegifService.tranformToModel(thegif));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Thegif from WfContent created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create Thegif from WfContent For Send",
            notes = "สร้างข้อมูลการสนับสนุนการเชื่อมโยงระบบสารบรรณอิเล็กทรอนิกส์ของหน่วยงานภาครัฐจากหนังสือสำหรับส่ง",
            response = ThegifModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Thegif from WfContent created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path(value = "/createThegifFromWfContentForSend2")
    public Response createThegifFromWfContentForSend2(
            @HeaderParam("userProfileId") int userProfileId,
            @ApiParam(name = "id", value = "รหัสหนังสือ", required = true)
            @FormParam("id") int id,
            @ApiParam(name = "filePath", value = "filePath", required = true)
            @FormParam("filePath") String filePath//เอาจาก result 
    ) {
        log.info("createThegifFromWfContentForSend2...");
        log.info("id = " + id);
        log.info("filePath = " + filePath);
        Gson gs = new GsonBuilder()
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
            WfContent wfContent = wfContentService.getById(id);

            Thegif thegif = new Thegif();
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            List<ThegifDepartment> thegifDepartmentSender = thegifDepartmentService.listByThegifDepartmentName(wfContent.getWfContentFrom());
            List<ThegifDepartment> thegifDepartmentReceiver = thegifDepartmentService.listByThegifDepartmentName(wfContent.getWfContentTo());

            //userProfileId = 1;
            ThegifService thegifService = new ThegifService();
            thegif.setCreatedBy(userProfileId);
            if (thegif != null) {
                thegif.setThegifElementType("SendLetter");
                thegif.setThegifProcessId(String.valueOf(wfContent.getId()));
                thegif.setThegifSendDate(thegifService.ECMSNowDate());
                thegif.setThegifBookNo(wfContent.getWfContentBookNo());
                thegif.setThegifBookDate(wfContent.getWfContentBookDate().toString());
                thegif.setThegifSpeed(String.valueOf(wfContent.getWfContentSpeed()));
                thegif.setThegifSecret(String.valueOf(wfContent.getWfContentSecret()));
                thegif.setThegifSenderDepartmentCode(thegifDepartmentSender.get(0).getThegifDepartmentCode());
                thegif.setThegifReceiverDepartmentCode(thegifDepartmentReceiver.get(0).getThegifDepartmentCode());
                thegif.setThegifAcceptDate(null);
                thegif.setThegifAcceptId(null);
                thegif.setThegifAcceptDepartmentCode(null);
                thegif.setThegifLetterStatus(null);
                thegif.setThegifSubject(wfContent.getWfContentTitle());
                thegif.setThegifDescription(wfContent.getWfContentDescription());
                thegif.setThegifAttachment(wfContent.getWfContentAttachment());
                thegif.setThegifReference(wfContent.getWfContentReference());
                thegif.setThegifFilePath(filePath);
                thegif = thegifService.create(thegif);
                thegif.setUpdatedBy(userProfileId);
                thegif.setOrderNo(thegif.getId());
                thegifService.update(thegif);
                responseData.put("data", thegifService.tranformToModel(thegif));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Thegif from WfContent created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "Method for create New ECMSMinistry",
//            notes = "สร้างข้อมูลกระทรวงเพิ่มเติม",
//            response = ThegifDepartmentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "ECMSMinistry created successfully."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/createNewECMSMinistry")
//    public Response createNewECMSMinistry(
//            @HeaderParam("userProfileId") int userProfileId,
//            ThegifDepartmentModel thegifDepartmentPostModel
//    ) {
//        log.info("createNewECMSMinistry...");
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("success", false);
//        try {
//            ThegifService thegifService = new ThegifService();
//            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//            List<ThegifDepartment> listThegifDepartment = new ArrayList();
//            ArrayList<ThegifDepartmentModel> listThegifDepartmentModel = new ArrayList();
//
//            ParamService paramService = new ParamService();
//            Param param1 = paramService.getByParamName("ECMSDEPCODE");
//            String tmpECMSdepCode = param1.getParamValue();
//
//            Param param2 = paramService.getByParamName("ECMSSERVICE");
//            String tmpECMSService = param2.getParamValue();
//
//            listThegifDepartment = thegifService.createNewECMSMinistry(tmpECMSdepCode, tmpECMSService, tmpECMSService, userProfileId);
//            for (ThegifDepartment ThegifDepartment : listThegifDepartment) {
//                listThegifDepartmentModel.add(thegifDepartmentService.tranformToModel(ThegifDepartment));
//            }
//            listThegifDepartmentModel.trimToSize();
//            responseData.put("data", listThegifDepartmentModel);
//
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "Thegif created successfully.");
//        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }

//    @ApiOperation(
//            value = "Method for create New ECMSMinistry",
//            notes = "สร้างข้อมูลกระทรวงเพิ่มเติม",
//            response = ThegifDepartmentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "ECMSMinistry created successfully."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/createNewECMSMinistry")
//    public Response createNewECMSMinistry(
//            @HeaderParam("userProfileId") int userProfileId//,
////            ThegifDepartmentModel thegifDepartmentPostModel
//    ) {
//        log.info("createNewECMSMinistry..."); //New
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("success", false);
//        try {
//            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//            List<ThegifDepartment> listNewMinistry = new ArrayList();
//            ParamService paramService = new ParamService();
//            Param param1 = paramService.getByParamName("ECMSDEPCODE");
//            String tmpECMSdepCode = param1.getParamValue();
//
//            Param param2 = paramService.getByParamName("ECMSSERVICE");
//            String tmpECMSService = param2.getParamValue();
//log.info("tmpECMSdepCode = "+tmpECMSdepCode);
//log.info("tmpECMSService = "+tmpECMSService);
//            ThegifService thegifService = new ThegifService();
//            List<Ministry> listECMSMinistry = thegifService.getECMSMinistry(tmpECMSdepCode, tmpECMSService, tmpECMSService);
//
//            ArrayList<ThegifModel_ECMSMinistry> listECMSMinistryModel = new ArrayList();
//            for (Ministry ministry : listECMSMinistry) {
//    //                listECMSMinistryModel.add(thegifService.tranformToModelECMSMinistry(ministry));
//                ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(ministry.getMinistryID());
//
//                if (thegifDepartment == null) {
//                    System.out.println("thegifDepartmentname="+thegifDepartment.getThegifDepartmentName());
//                    System.out.println("thegifDepartmentcode="+thegifDepartment.getThegifDepartmentCode());
//                    System.out.println("thegifDepartmentservice="+thegifDepartment.getThegifDepartmentServiceName());
//                    ThegifDepartment thegifDepartmentNew = new ThegifDepartment();
//                    thegifDepartmentNew.setCreatedBy(userProfileId);
//                    if (thegifDepartmentNew != null) {
//                        thegifDepartmentNew.setThegifDepartmentName(ministry.getMinistryTHName());
//                        thegifDepartmentNew.setThegifDepartmentCode(ministry.getMinistryID());
//                        thegifDepartmentNew.setThegifDepartmentServiceName(null);
//
//                        ThegifDepartment parentThegifDepartment = new ThegifDepartment();
//                        thegifDepartmentNew.setParentId(1);
//
//                        //nodelevel and parentKey
//                        parentThegifDepartment = thegifDepartmentService.getById(1);
//
//                        thegifDepartmentNew.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
//                        thegifDepartmentNew = thegifDepartmentService.create(thegifDepartmentNew);
//
//                        thegifDepartmentNew.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartmentNew.getId() + "฿");
//                        thegifDepartmentNew.setOrderNo(thegifDepartmentNew.getId());
//                        thegifDepartmentNew.setUpdatedBy(userProfileId);
//                        thegifDepartmentService.update(thegifDepartmentNew);
//                        listNewMinistry.add(thegifDepartmentNew);
//                    }
//                } else if (!thegifDepartment.getThegifDepartmentName().equals(ministry.getMinistryTHName())) {
//                    thegifDepartment.setThegifDepartmentName(ministry.getMinistryTHName());
//
//                    thegifDepartment.setUpdatedBy(userProfileId);
//                    thegifDepartmentService.update(thegifDepartment);
//                    listNewMinistry.add(thegifDepartment);
//                }
//            }
//
//            listECMSMinistryModel.trimToSize();
////            responseData.put("data", listECMSMinistryModel);
////            responseData.put("data", listNewMinistry);
//            responseData.put("data", "Created ECMSMinistry Success.");
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "Thegif created successfully.");
//        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }

    @ApiOperation(
            value = "Method for get ECMSMinistry",
            notes = "สร้างข้อมูลรหัสกระทรวง",
            response = ThegifModel_ECMSMinistry.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "ECMSMinistry success."),
        @ApiResponse(code = 404, message = "ECMSMinistry not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createNewECMSMinistry")
    public Response createNewECMSMinistry() {
        log.info("createNewECMSMinistry...");
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
        responseData.put("message", "ECMSMinistry not found.");
        try {
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();
log.info("tmpECMSdepCode = "+tmpECMSdepCode);
log.info("tmpECMSService = "+tmpECMSService);
            ThegifService thegifService = new ThegifService();
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            List<ThegifDepartment> listNewMinistry = new ArrayList();

            List<Ministry> listECMSMinistry = thegifService.getECMSMinistry(tmpECMSdepCode, tmpECMSService, tmpECMSService);
            System.out.println("listECMSMinistrySize="+listECMSMinistry.size());
            ArrayList<ThegifModel_ECMSMinistry> listECMSMinistryModel = new ArrayList();
            int count = 1; 
            for (Ministry ministry : listECMSMinistry) {
                listECMSMinistryModel.add(thegifService.tranformToModelECMSMinistry(ministry));
                System.out.println("Find ministry.getMinistryID()="+ministry.getMinistryID());
                ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(ministry.getMinistryID());
                System.out.println("thegifDepartment="+thegifDepartment);
                System.out.println("count="+count);
                 if (thegifDepartment == null) {
                     System.out.println("Create ECMSMinistry");
                    ThegifDepartment thegifDepartmentNew = new ThegifDepartment();
                    System.out.println("thegifDepartmentNew="+thegifDepartmentNew);
//                    if (thegifDepartmentNew != null) {
                        
                        thegifDepartmentNew.setThegifDepartmentName(ministry.getMinistryTHName()); //ministry.getMinistryTHName()
                        thegifDepartmentNew.setThegifDepartmentCode(ministry.getMinistryID()); //ministry.getMinistryID()
                        thegifDepartmentNew.setThegifDepartmentServiceName(null);
                        thegifDepartmentNew.setCreatedBy(1);
                        ThegifDepartment parentThegifDepartment = new ThegifDepartment();
                        thegifDepartmentNew.setParentId(1);

                        //nodelevel and parentKey
                        parentThegifDepartment = thegifDepartmentService.getById(1);
                        System.out.println("parentThegifDepartment="+parentThegifDepartment);
                        thegifDepartmentNew.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
                        thegifDepartmentNew = thegifDepartmentService.create(thegifDepartmentNew);

                        thegifDepartmentNew.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartmentNew.getId() + "฿");
                        thegifDepartmentNew.setOrderNo(thegifDepartmentNew.getId());
                        thegifDepartmentNew.setUpdatedBy(1);
                        thegifDepartmentService.update(thegifDepartmentNew);
                        listNewMinistry.add(thegifDepartmentNew);
//                    }
                } else if (!thegifDepartment.getThegifDepartmentName().equals(ministry.getMinistryTHName())) {
                     System.out.println("UpdateECMSMinistry");
                     System.out.println("else if "+thegifDepartment.getThegifDepartmentName()+" "+ministry.getMinistryTHName());
                    thegifDepartment.setThegifDepartmentName(ministry.getMinistryTHName());

                    thegifDepartment.setUpdatedBy(1);
                    thegifDepartmentService.update(thegifDepartment);
                    listNewMinistry.add(thegifDepartment);
                }
                 count++;
            }                
                
                
            

//            listECMSMinistryModel.trimToSize();
            status = Response.Status.OK;
//            responseData.put("data", listECMSMinistryModel);
//            responseData.put("data", listNewMinistrys);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }    
    
//    @ApiOperation(
//            value = "Method for create New ECMSOrganization",
//            notes = "สร้างข้อมูลหน่วยงานเพิ่มเติม",
//            response = ThegifDepartmentModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "ECMSOrganization created successfully."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/createNewECMSOrganization")
//    public Response createNewECMSOrganization(
//            @HeaderParam("userProfileId") int userProfileId
//    ) {
//        log.info("createNewECMSOrganization...");
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("success", false);
//        try {
//            //userProfileId = 1;
//            ThegifService thegifService = new ThegifService();
//            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
//            List<ThegifDepartment> listThegifDepartment = new ArrayList();
//            ArrayList<ThegifDepartmentModel> listThegifDepartmentModel = new ArrayList();
//
//            ParamService paramService = new ParamService();
//            Param param1 = paramService.getByParamName("ECMSDEPCODE");
//            String tmpECMSdepCode = param1.getParamValue();
//
//            Param param2 = paramService.getByParamName("ECMSSERVICE");
//            String tmpECMSService = param2.getParamValue();
//
//            listThegifDepartment = thegifService.createNewECMSOrganization(tmpECMSdepCode, tmpECMSService, tmpECMSService, userProfileId);
//            for (ThegifDepartment ThegifDepartment : listThegifDepartment) {
//                listThegifDepartmentModel.add(thegifDepartmentService.tranformToModel(ThegifDepartment));
//            }
//            listThegifDepartmentModel.trimToSize();
//            responseData.put("data", listThegifDepartmentModel);
//
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "Thegif created successfully.");
//        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }

    @ApiOperation(
            value = "Method for create New ECMSOrganization",
            notes = "สร้างข้อมูลหน่วยงานเพิ่มเติม",
            response = ThegifDepartmentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "ECMSOrganization created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createNewECMSOrganization")
    public Response createNewECMSOrganization(
            @HeaderParam("userProfileId") int userProfileId
    ) {
        System.out.println("createNewECMSOrganization...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            ThegifDepartmentService thegifDepartmentService = new ThegifDepartmentService();
            List<ThegifDepartment> listNewOrg = new ArrayList();
            ParamService paramService = new ParamService();
            Param param1 = paramService.getByParamName("ECMSDEPCODE");
            String tmpECMSdepCode = param1.getParamValue();

            Param param2 = paramService.getByParamName("ECMSSERVICE");
            String tmpECMSService = param2.getParamValue();

            ThegifService thegifService = new ThegifService();
            List<Organization> listECMSOrganization = thegifService.getECMSOrganization(tmpECMSdepCode, tmpECMSService, tmpECMSService);
            System.out.println("listECMSOrganizationSize="+listECMSOrganization.size());
            ArrayList<ThegifModel_ECMSOrganization> listECMSOrganizationModel = new ArrayList();
            for (Organization organization : listECMSOrganization) {
    //                listECMSOrganizationModel.add(thegifService.tranformToModelECMSOrganization(organization));
                ThegifDepartment thegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(organization.getOrganizationID());
                System.out.println("thegifDepartment="+thegifDepartment);
                if (thegifDepartment == null) {

                    ThegifDepartment thegifDepartmentNew = new ThegifDepartment();
                    thegifDepartmentNew.setCreatedBy(userProfileId);
                    if (thegifDepartmentNew != null) {
                        thegifDepartmentNew.setThegifDepartmentName(organization.getOrganizationTHName());
                        thegifDepartmentNew.setThegifDepartmentCode(organization.getOrganizationID());
                        thegifDepartmentNew.setThegifDepartmentServiceName(organization.getECMSURL());

                        ThegifDepartment parentThegifDepartment = new ThegifDepartment();

                        String orgCode = organization.getOrganizationID().substring(0, 2);
                        parentThegifDepartment = thegifDepartmentService.getByThegifDepartmentCode(orgCode);

                        thegifDepartmentNew.setNodeLevel(parentThegifDepartment.getNodeLevel() + 1);
                        thegifDepartmentNew.setParentId(parentThegifDepartment.getId());
                        thegifDepartmentNew = thegifDepartmentService.create(thegifDepartmentNew);

                        thegifDepartmentNew.setParentKey(parentThegifDepartment.getParentKey() + thegifDepartmentNew.getId() + "฿");
                        thegifDepartmentNew.setOrderNo(thegifDepartmentNew.getId());
                        thegifDepartmentNew.setUpdatedBy(userProfileId);
                        thegifDepartmentService.update(thegifDepartmentNew);
                        listNewOrg.add(thegifDepartmentNew);
                    }
                } else if (!thegifDepartment.getThegifDepartmentName().equals(organization.getOrganizationTHName())) {
                    thegifDepartment.setThegifDepartmentName(organization.getOrganizationTHName());

                    thegifDepartment.setUpdatedBy(userProfileId);
                    thegifDepartmentService.update(thegifDepartment);
                    listNewOrg.add(thegifDepartment);
                }

            }

            listECMSOrganizationModel.trimToSize();
//            status = Response.Status.OK;
//            responseData.put("data", listECMSOrganizationModel);
            responseData.put("data", "");
            responseData.put("message", "Created ECMSOrganization Success.");
            responseData.put("success", true);
            
            
//            listThegifDepartmentModel.trimToSize();
//            responseData.put("data", listThegifDepartmentModel);

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Thegif created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }    
    
    @ApiOperation(
            value = "Method for change Date Reference in Thegif",
            notes = "แปลงวันที่ในอ้างอิงถึงของ ecms ก่อนลงรับหนังสือ"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "change Date Reference in Thegif success."),
        @ApiResponse(code = 404, message = "change Date Reference in Thegif not found."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/changeDateReferenceThegif")
    public Response changeDateReferenceThegif(
            @ApiParam(name = "reference", value = "อ้างอิง", required = true)
            @QueryParam("reference") String reference
    ) {
        log.info("changeDateReferenceThegif...");
        log.info("reference = " + reference);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "change Date Reference in Thegif not found.");
        try {
            ThegifService thegifService = new ThegifService();
            String result = thegifService.changeDateInReferenceThegif(reference);

            status = Response.Status.OK;
            responseData.put("data", result);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "Method for get Thegif by BookNo",
            notes = "รายการสถานะการส่งหนังสือรายเรื่อง",
            response = ThegifStatusSendModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Thegif by id success."),
        @ApiResponse(code = 404, message = "Thegif by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listStatusSend/{id}")
    public Response listStatusSend(
            @ApiParam(name = "id", value = "รหัส THeGifId", required = true)
            @PathParam("id") int id
    ) {
        log.info("listStatusSend...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Thegif by id not found in the database.");
        try {
            ThegifService thegifService = new ThegifService();
            Thegif thegif = thegifService.getById(id);

            List<ThegifStatusSendModel> listThegifStatusSendModel = new ArrayList<>();
            if (thegif != null) {
                List<Thegif> listthegif = thegifService.listByBookNo(thegif.getThegifBookNo(), "SendLetter", "",0, 1000, "thegifReceiverDepartmentCode", "asc"); //bookno,elementype,depcode
                if (!listthegif.isEmpty()) {
                    for(Thegif tmpthegif : listthegif){
                        ThegifStatusSendModel tmp = thegifService.tranformToThegifStatusSendModel(tmpthegif);
                        listThegifStatusSendModel.add(tmp);
                    }
                }
                
                status = Response.Status.OK;
                responseData.put("data", listThegifStatusSendModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }        
}
