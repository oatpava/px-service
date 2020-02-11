package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.share.model.VersionModel;
import com.px.mwp.entity.WorkflowTo;
import com.px.mwp.model.WorkflowToModel;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.service.WorkflowService;
import com.px.mwp.service.WorkflowToService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "WorkflowTo ผู้รับข้อมูลการส่ง")
@Path("v1/workflowTos")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WorkflowToResource {
    private static final Logger LOG = Logger.getLogger(WorkflowToResource.class.getName());

    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "สร้างข้อมูลผู้รับข้อมูลการส่ง",
            notes = "สร้างข้อมูลผู้รับข้อมูลการส่ง",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Workflow created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WorkflowToModel workflowToPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(workflowToPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            //check length Data
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowService workflowService = new WorkflowService();
            UserProfileService userProfileService = new UserProfileService();
            StructureService structureService = new StructureService();

            WorkflowTo workflowTo = new WorkflowTo();
            workflowTo.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            workflowTo.setWorkflow(workflowService.getById(workflowToPostModel.getWorkflowId()));
            workflowTo.setUserProfileId(workflowToPostModel.getUserProfileId());
            workflowTo.setStructureId(workflowToPostModel.getStructureId());

            if (workflowToPostModel.getUserProfileId() > 0) {
                UserProfile userProfile = userProfileService.getById(workflowToPostModel.getUserProfileId());
                workflowTo.setUserProfileFullName(userProfile.getUserProfileFullName());
                workflowTo.setUserProfilePosition(userProfile.getPosition().getPositionName());
            } else {
                Structure structure = structureService.getById(workflowToPostModel.getStructureId());
                workflowTo.setUserProfileFullName(structure.getStructureName());
                workflowTo.setUserProfilePosition(null);
            }

            if (workflowTo != null) {
                workflowTo = workflowToService.create(workflowTo);

                workflowTo.setOrderNo(workflowTo.getId());
                workflowTo = workflowToService.update(workflowTo);

                responseData.put("data", workflowToService.tranformToModel(workflowTo));
                responseData.put("message", "WorkflowTo created successfully.");
                status = Response.Status.CREATED;
                responseData.put("success", true);
            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลผู้รับข้อมูลการส่ง ด้วย รหัสผู้รับข้อมูลการส่ง",
            notes = "ขอข้อมูลผู้รับข้อมูลการส่ง ด้วย รหัสผู้รับข้อมูลการส่ง",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo by id success."),
        @ApiResponse(code = 404, message = "WorkflowTo by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผู้รับข้อมูลการส่ง", required = true)
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowTo workflowTo = workflowToService.getById(id);
            if (workflowTo != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowToService.tranformToModel(workflowTo));
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
            value = "ลบข้อมูลผู้รับข้อมูลการส่ง",
            notes = "ลบข้อมูลผู้รับข้อมูลการส่ง",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo deleted by id success."),
        @ApiResponse(code = 404, message = "WorkflowTo by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสสำเนาการส่งข้อมูล", required = true)
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowTo workflowTo = workflowToService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (workflowTo != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowToService.tranformToModel(workflowTo));
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
            value = "รายการผู้รับข้อมูลการส่ง",
            notes = "รายการผู้รับข้อมูลการส่ง",
            responseContainer = "List",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo list success."),
        @ApiResponse(code = 404, message = "WorkflowTo list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByWorkflowId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "workflowId", value = "รายการรหัสผังการทำงาน", required = true)
            @QueryParam("workflowId") int workflowId,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.info("list...");
        LOG.info("offset = " + offset);
        LOG.info("limit = " + limit);
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();

            List<WorkflowTo> listWorkflowTo = workflowToService.listByWorkflowId(workflowId, limit, offset, sort, dir);
            if (!listWorkflowTo.isEmpty()) {
                List<WorkflowToModel> listWorkflowToModel = new ArrayList<>();
                for (WorkflowTo workflowTo : listWorkflowTo) {
                    listWorkflowToModel.add(workflowToService.tranformToModel(workflowTo));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowToModel);
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
            value = "แก้ไขวันที่ได้รับ เมื่อทำการเปิดกล่องหนังสือเข้า",
            notes = "แก้ไขวันที่ได้รับ เมื่อทำการเปิดกล่องหนังสือเข้า",
            responseContainer = "List",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo list success."),
        @ApiResponse(code = 404, message = "WorkflowTo list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Path(value = "/updateWorkflowToReceive/user/{userProfileId}/struc/{structureId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateInboxReceiveAll(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @DefaultValue("0") @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @PathParam("structureId") int structureId
    ) {
        LOG.info("updateWorkflowToReceive...");
        LOG.info("userProfileId = " + userProfileId);
        LOG.info("structureId = " + structureId);
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
        responseData.put("message", "Inbox by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            List<WorkflowTo> listWorkflowTo = new ArrayList<>();
            if (userProfileId > 0) {
                listWorkflowTo = workflowToService.listByUserProfileId(userProfileId);
            } else {
                listWorkflowTo = workflowToService.listByStructureId(structureId);
            }

            if (!listWorkflowTo.isEmpty()) {
                List<WorkflowToModel> listWorkflowToModel = new ArrayList<>();
                for (WorkflowTo workflowTo : listWorkflowTo) {
                    if (workflowTo.getWorkflowToReceiveFlag() == 0) {
                        workflowTo.setWorkflowToReceiveDate(LocalDateTime.now());
                        workflowTo.setWorkflowToReceiveFlag(1);
                        workflowToService.update(workflowTo);
                    }
                    listWorkflowToModel.add(workflowToService.tranformToModel(workflowTo));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowToModel);
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
            value = "แก้ไขวันที่เปิด",
            notes = "แก้ไขวันที่เปิด",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo by id success."),
        @ApiResponse(code = 404, message = "WorkflowTo by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateOpenDate/{workflowId}/user/{userProfileId}/struc/{structureId}")
    public Response updateOpenDate(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "workflowId", value = "รหัสผังการไหล", required = true)
            @PathParam("workflowId") int workflowId,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @DefaultValue("0") @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @PathParam("structureId") int structureId
    ) {
        LOG.info("updateOpenDate...");
        LOG.info("workflowId = " + workflowId);
        LOG.info("userProfileId = " + userProfileId);
        LOG.info("structureId = " + structureId);
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowTo workflowTo = new WorkflowTo();
            if (userProfileId > 0) {
                workflowTo = workflowToService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowTo = workflowToService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowTo != null) {
                if (workflowTo.getWorkflowToOpenFlag() == 0) {
                    workflowTo.setWorkflowToOpenDate(LocalDateTime.now());
                    workflowTo.setWorkflowToOpenFlag(1);
                    workflowTo = workflowToService.update(workflowTo);
                }
                status = Response.Status.OK;
                responseData.put("data", workflowToService.tranformToModel(workflowTo));
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
            value = "แก้ไขวันที่กระทำ",
            notes = "แก้ไขวันที่กระทำ",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo by id success."),
        @ApiResponse(code = 404, message = "WorkflowTo by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateActionDate/{workflowId}/user/{userProfileId}/struc/{structureId}")
    public Response updateActionDate(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "workflowId", value = "รหัสผังการไหล", required = true)
            @PathParam("workflowId") int workflowId,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @DefaultValue("0") @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @PathParam("structureId") int structureId
    ) {
        LOG.info("updateActionDate...");
        LOG.info("workflowId = " + workflowId);
        LOG.info("userProfileId = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        LOG.info("structureId = " + structureId);
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowTo workflowTo = new WorkflowTo();
             if (userProfileId > 0) {
                workflowTo = workflowToService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowTo = workflowToService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowTo != null) {
                if (workflowTo.getWorkflowToActionFlag() == 0) {
                    workflowTo.setWorkflowToActionDate(LocalDateTime.now());
                    workflowTo.setWorkflowToActionFlag(1);
                    workflowTo = workflowToService.update(workflowTo);
                }
                status = Response.Status.OK;
                responseData.put("data", workflowToService.tranformToModel(workflowTo));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "แก้ไขวันที่เสร็จ",
            notes = "แก้ไขวันที่เสร็จ",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo by id success."),
        @ApiResponse(code = 404, message = "WorkflowTo by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateFinishDate/{workflowId}/user/{userProfileId}/struc/{structureId}")
    public Response updateFinishDate(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "workflowId", value = "รหัสผังการไหล", required = true)
            @PathParam("workflowId") int workflowId,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @DefaultValue("0") @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @PathParam("structureId") int structureId
    ) {
        LOG.info("updateFinishDate...");
        LOG.info("workflowId = " + workflowId);
        LOG.info("userProfileId = " + userProfileId);
        LOG.info("structureId = " + structureId);
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            WorkflowTo workflowTo = new WorkflowTo();
             if (userProfileId > 0) {
                workflowTo = workflowToService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowTo = workflowToService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowTo != null) {
                if (workflowTo.getWorkflowToFinishFlag() == 0) {
                    workflowTo.setWorkflowToFinishDate(LocalDateTime.now());
                    workflowTo.setWorkflowToFinishFlag(1);
                    workflowTo = workflowToService.update(workflowTo);
                }
                status = Response.Status.OK;
                responseData.put("data", workflowToService.tranformToModel(workflowTo));
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
    
    //oat-add
    @ApiOperation(
            value = "get WFtos by contentId",
            notes = "get WFtos by contentId",
            responseContainer = "List",
            response = WorkflowToModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowTo list success."),
        @ApiResponse(code = 404, message = "WorkflowTo list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/content")
    public Response listByContentId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "contentId", value = "contentId", required = true)
            @QueryParam("contentId") int contentId,
            @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true)
            @DefaultValue("0") @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true)
            @DefaultValue("50") @QueryParam("limit") int limit,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.info("list...");
        LOG.info("offset = " + offset);
        LOG.info("limit = " + limit);
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
        responseData.put("message", "WorkflowTo by id not found in the database.");
        try {
            WorkflowToService workflowToService = new WorkflowToService();
            List<WorkflowTo> listWorkflowTo = workflowToService.listByContentId(contentId, limit, offset, sort, dir);
            if (!listWorkflowTo.isEmpty()) {
                List<WorkflowToModel> listWorkflowToModel = new ArrayList<>();
                for (WorkflowTo workflowTo : listWorkflowTo) {
                    listWorkflowToModel.add(workflowToService.tranformToModel(workflowTo));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowToModel);
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
}
