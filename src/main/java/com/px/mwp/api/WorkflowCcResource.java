package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.share.model.VersionModel;
import com.px.mwp.entity.WorkflowCc;
import com.px.mwp.model.WorkflowCcModel;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.mwp.service.WorkflowCcService;
import com.px.mwp.service.WorkflowService;
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
@Api(value = "WorkflowCc สำเนาการส่งข้อมูล")
@Path("v1/workflowCcs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WorkflowCcResource {
    private static final Logger LOG = Logger.getLogger(WorkflowCcResource.class.getName());

    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "สร้างข้อมูลสำเนาการส่งข้อมูล",
            notes = "สร้างข้อมูลสำเนาการส่งข้อมูล",
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Workflow created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WorkflowCcModel workflowCcPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(workflowCcPostModel.getVersion())
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
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowService workflowService = new WorkflowService();
            UserProfileService userProfileService = new UserProfileService();
            StructureService structureService = new StructureService();

            WorkflowCc workflowCc = new WorkflowCc();
            workflowCc.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            workflowCc.setWorkflow(workflowService.getById(workflowCcPostModel.getWorkflowId()));
            workflowCc.setUserProfileId(workflowCcPostModel.getUserProfileId());
            workflowCc.setStructureId(workflowCcPostModel.getStructureId());

            if (workflowCcPostModel.getUserProfileId() > 0) {
                UserProfile userProfile = userProfileService.getById(workflowCcPostModel.getUserProfileId());
                workflowCc.setUserProfileFullName(userProfile.getUserProfileFullName());
                workflowCc.setUserProfilePosition(userProfile.getPosition().getPositionName());
            } else {
                Structure structure = structureService.getById(workflowCcPostModel.getStructureId());
                workflowCc.setUserProfileFullName(structure.getStructureName());
                workflowCc.setUserProfilePosition(null);
            }

            if (workflowCc != null) {
                workflowCc = workflowCcService.create(workflowCc);

                workflowCc.setOrderNo(workflowCc.getId());
                workflowCc = workflowCcService.update(workflowCc);

                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
                responseData.put("message", "WorkflowCc created successfully.");
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
            value = "ขอข้อมูลสำเนาการส่งข้อมูล ด้วย รหัสสำเนาการส่งข้อมูล",
            notes = "ขอข้อมูลสำเนาการส่งข้อมูล ด้วย รหัสสำเนาการส่งข้อมูล",
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc by id success."),
        @ApiResponse(code = 404, message = "WorkflowCc by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสสำเนาการส่งข้อมูล", required = true)
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowCc workflowCc = workflowCcService.getById(id);
            if (workflowCc != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
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
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc list success."),
        @ApiResponse(code = 404, message = "WorkflowCc list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Path(value = "/updateWorkflowCcReceive/user/{userProfileId}/struc/{structureId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateInboxReceiveAll(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @DefaultValue("0") @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "structureId", value = "รหัสหน่วยงาน", required = true)
            @DefaultValue("0") @PathParam("structureId") int structureId
    ) {
        LOG.info("updateWorkflowCcReceive...");
        LOG.info("userProfileId = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
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
            WorkflowCcService workflowCcService = new WorkflowCcService();
            List<WorkflowCc> listWorkflowCc = new ArrayList<>();
            if (userProfileId > 0) {
                listWorkflowCc = workflowCcService.listByUserProfileId(userProfileId);
            } else {
                listWorkflowCc = workflowCcService.listByStructureId(structureId);
            }

            if (!listWorkflowCc.isEmpty()) {
                List<WorkflowCcModel> listWorkflowCcModel = new ArrayList<>();
                for (WorkflowCc workflowCc : listWorkflowCc) {
                    if (workflowCc.getWorkflowCcReceiveFlag() == 0) {
                        workflowCc.setWorkflowCcReceiveDate(LocalDateTime.now());
                        workflowCc.setWorkflowCcReceiveFlag(1);
                        workflowCcService.update(workflowCc);
                    }
                    listWorkflowCcModel.add(workflowCcService.tranformToModel(workflowCc));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowCcModel);
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
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc by id success."),
        @ApiResponse(code = 404, message = "WorkflowCc by id not found in the database."),
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowCc workflowCc = new WorkflowCc();
            if (userProfileId > 0) {
                workflowCc = workflowCcService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowCc = workflowCcService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowCc != null) {
                if (workflowCc.getWorkflowCcOpenFlag() == 0) {
                    workflowCc.setWorkflowCcOpenDate(LocalDateTime.now());
                    workflowCc.setWorkflowCcOpenFlag(1);
                    workflowCc = workflowCcService.update(workflowCc);
                }
                status = Response.Status.OK;
                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
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
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc by id success."),
        @ApiResponse(code = 404, message = "WorkflowCc by id not found in the database."),
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowCc workflowCc = new WorkflowCc();
            if (userProfileId > 0) {
                workflowCc = workflowCcService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowCc = workflowCcService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowCc != null) {
                if (workflowCc.getWorkflowCcActionFlag() == 0) {
                    workflowCc.setWorkflowCcActionDate(LocalDateTime.now());
                    workflowCc.setWorkflowCcActionFlag(1);
                    workflowCc = workflowCcService.update(workflowCc);
                }

                status = Response.Status.OK;
                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
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
            value = "แก้ไขวันที่เสร็จ",
            notes = "แก้ไขวันที่เสร็จ",
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc by id success."),
        @ApiResponse(code = 404, message = "WorkflowCc by id not found in the database."),
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowCc workflowCc = new WorkflowCc();
            if (userProfileId > 0) {
                workflowCc = workflowCcService.getByWorkflowIdAndUserProfileId(workflowId, userProfileId);
            } else {
                workflowCc = workflowCcService.getByWorkflowIdAndStructureId(workflowId, structureId);
            }

            if (workflowCc != null) {
                if (workflowCc.getWorkflowCcFinishFlag() == 0) {
                    workflowCc.setWorkflowCcFinishDate(LocalDateTime.now());
                    workflowCc.setWorkflowCcFinishFlag(1);
                    workflowCc = workflowCcService.update(workflowCc);
                }

                status = Response.Status.OK;
                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
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
            value = "ลบข้อมูลสำเนาการส่งข้อมูล",
            notes = "ลบข้อมูลสำเนาการส่งข้อมูล",
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc deleted by id success."),
        @ApiResponse(code = 404, message = "WorkflowCc by id not found in the database."),
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();
            WorkflowCc workflowCc = workflowCcService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (workflowCc != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowCcService.tranformToModel(workflowCc));
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
            value = "รายการสำเนาการส่งข้อมูล",
            notes = "รายการสำเนาการส่งข้อมูล",
            responseContainer = "List",
            response = WorkflowCcModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowCc list success."),
        @ApiResponse(code = 404, message = "WorkflowCc list not found in the database."),
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
        LOG.info("listByWorkflowId...");
        LOG.info("workflowId = " + workflowId);
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
        responseData.put("message", "WorkflowCc by id not found in the database.");
        try {
            WorkflowCcService workflowCcService = new WorkflowCcService();

            List<WorkflowCc> listWorkflowCc = workflowCcService.listByWorkflowId(workflowId, limit, offset, sort, dir);
            if (!listWorkflowCc.isEmpty()) {
                List<WorkflowCcModel> listWorkflowCcModel = new ArrayList<>();
                for (WorkflowCc workflowCc : listWorkflowCc) {
                    listWorkflowCcModel.add(workflowCcService.tranformToModel(workflowCc));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowCcModel);
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
