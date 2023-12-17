package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.model.VersionModel;
import com.px.mwp.entity.Workflow;
import com.px.mwp.model.WorkflowModel;
import com.px.mwp.model.WorkflowModel_groupFlow;
import com.px.mwp.service.WorkflowService;
import com.px.share.model.ListOptionModel;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Mali
 */
@Api(value = "Workflow ผังการไหล")
@Path("v1/workflows")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WorkflowResource {

    private static final Logger LOG = Logger.getLogger(WorkflowResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลผังการไหล",
            notes = "สร้างข้อมูลผังการไหล",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Workflow created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WorkflowModel workflowPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(workflowPostModel.getVersion())
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
            WorkflowService workflowService = new WorkflowService();

            final int userProfileId = (workflowPostModel.getWorkflowActionId() > 0) ? workflowPostModel.getWorkflowActionId() : Integer.parseInt(httpHeaders.getHeaderString("userID"));
            UserProfile userProfile = new UserProfileService().getById(userProfileId);
            
            String actionName;
            String actionPosition;
            if (workflowPostModel.getWorkflowActionName() == null) {
                actionName = userProfile.getUserProfileFullName();
            } else {
                actionName = workflowPostModel.getWorkflowActionName();
            }

            if (workflowPostModel.getWorkflowActionPosition() == null) {
                if (userProfile.getPosition() != null) {
                    actionPosition = userProfile.getPosition().getPositionName();
                } else {
                    actionPosition = null;
                }
            } else {
                actionPosition = workflowPostModel.getWorkflowActionPosition();
            }

            Workflow workflow = new Workflow();
            workflow.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            workflow.setLinkId(workflowPostModel.getLinkId());
            workflow.setLinkId2(workflowPostModel.getLinkId2());
            workflow.setLinkId3(workflowPostModel.getLinkId3());
            workflow.setWorkflowActionId(userProfileId);
            workflow.setWorkflowActionIdType(0);
            workflow.setWorkflowActionName(actionName);
            workflow.setWorkflowActionPosition(actionPosition);
            workflow.setWorkflowActionType(workflowPostModel.getWorkflowActionType());
            workflow.setWorkflowActionDate(LocalDateTime.now());
            workflow.setWorkflowDescription(workflowPostModel.getWorkflowDescription());
            workflow.setWorkflowNote(workflowPostModel.getWorkflowNote());
            workflow.setWorkflowTitle(workflowPostModel.getWorkflowTitle());
            workflow.setWorkflowStr01(workflowPostModel.getWorkflowStr01());
            workflow.setWorkflowStr02(workflowPostModel.getWorkflowStr02());
            workflow.setWorkflowStr03(workflowPostModel.getWorkflowStr03());
            workflow.setWorkflowStr04(workflowPostModel.getWorkflowStr04());
            workflow.setWorkflowDate01(dateThaiToLocalDateTime(workflowPostModel.getWorkflowDate01()));
            workflow.setWorkflowDate02(dateThaiToLocalDateTime(workflowPostModel.getWorkflowDate02()));
            workflow.setConvertId(0);

            workflow = workflowService.create(workflow);

            workflow.setOrderNo(workflow.getId());
            workflow = workflowService.update(workflow);

            responseData.put("data", workflowService.tranformToModel(workflow));
            responseData.put("message", "Workflow created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลผังการทำงาน ด้วย รหัสผังการทำงาน",
            notes = "ขอข้อมูลผังการทำงาน ด้วย รหัสผังการทำงาน",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow by id success."),
        @ApiResponse(code = 404, message = "Workflow by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response _getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผังการทำงาน", required = true)
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
        responseData.put("message", "Workflow by id not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.getById(id);
            if (workflow != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowService.tranformToModel(workflow));
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
            value = "แก้ไขข้อมูลผังการทำงาน",
            notes = "แก้ไขข้อมูลผังการทำงาน",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow updeted by id success."),
        @ApiResponse(code = 404, message = "Workflow by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response _update(
            WorkflowModel workflowPostModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + workflowPostModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(workflowPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Workflow by id not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.getById(workflowPostModel.getId());

            //check length Data
            workflowPostModel = workflowService.checkLengthField(workflowPostModel);

            if (workflow != null) {

                workflow.setLinkId(workflowPostModel.getLinkId());
                workflow.setLinkId2(workflowPostModel.getLinkId2());
                workflow.setLinkId3(workflowPostModel.getLinkId3());
                workflow.setWorkflowActionId(workflowPostModel.getWorkflowActionId());
                workflow.setWorkflowActionIdType(workflowPostModel.getWorkflowActionIdType());
                workflow.setWorkflowActionName(workflowPostModel.getWorkflowActionName());
                workflow.setWorkflowActionPosition(workflowPostModel.getWorkflowActionPosition());
                workflow.setWorkflowActionType(workflowPostModel.getWorkflowActionType());
                workflow.setWorkflowActionDate(dateThaiToLocalDateTime(workflowPostModel.getWorkflowActionDate()));
                workflow.setWorkflowDescription(workflowPostModel.getWorkflowDescription());
                workflow.setWorkflowNote(workflowPostModel.getWorkflowNote());
                workflow.setWorkflowTitle(workflowPostModel.getWorkflowTitle());
                workflow.setWorkflowStr01(workflowPostModel.getWorkflowStr01());
                workflow.setWorkflowStr02(workflowPostModel.getWorkflowStr02());
                workflow.setWorkflowStr03(workflowPostModel.getWorkflowStr03());
                workflow.setWorkflowStr04(workflowPostModel.getWorkflowStr04());
                workflow.setWorkflowDate01(dateThaiToLocalDateTime(workflowPostModel.getWorkflowDate01()));
                workflow.setWorkflowDate02(dateThaiToLocalDateTime(workflowPostModel.getWorkflowDate02()));

                workflow.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                workflow = workflowService.update(workflow);

                status = Response.Status.OK;
                responseData.put("data", workflowService.tranformToModel(workflow));
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
            value = "ลบข้อมูลผังการทำงาน",
            notes = "ลบข้อมูลผังการทำงาน",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow deleted by id success."),
        @ApiResponse(code = 404, message = "Workflow by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response _remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผังการทำงาน", required = true)
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
        responseData.put("message", "Workflow by id not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            Workflow workflow = workflowService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (workflow != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowService.tranformToModel(workflow));
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
            value = "Method for get workflow list by documentId",
            notes = "Method for get workflow list by documentId",
            responseContainer = "List",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow list success."),
        @ApiResponse(code = 404, message = "Workflow list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listWithDetail/{linkId}")
    public Response listWithDetail(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId
    ) {
        LOG.info("listByLinkId...");
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
        responseData.put("message", "Workflow search not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            //MUST GET removed by, cause show both F and E
            List<Workflow> listWorkflow = workflowService.listByLinkIdGetRemoved(linkId, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWorkflow.isEmpty()) {
                List<WorkflowModel> listWorkflowModel = new ArrayList<>();
                for (Workflow workflow : listWorkflow) {
                    listWorkflowModel.add(workflowService.tranformToModelWithDetail(workflow));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowModel);
            }
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
            value = "Method for list workFlow for Image.",
            notes = "รายการข้อมูลผังการไหลแบบรูปภาพ",
            responseContainer = "List",
            response = WorkflowModel_groupFlow.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow list success."),
        @ApiResponse(code = 404, message = "Workflow list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listForImage/{id}")
    public Response listForImage(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "document id", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("listForImage...");
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
        responseData.put("message", "Workflow by id not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            //MUST GET removed by, cause show both F and E
            List<Workflow> listWorkflow = workflowService.listByLinkIdGetRemoved(id, "orderNo", "asc");
            WorkflowModel_groupFlow workflowModel_groupFlow = workflowService.getWorkflowImage(listWorkflow);
            status = Response.Status.OK;
            responseData.put("data", workflowModel_groupFlow);
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
            value = "Method for get workflow list by documentId",
            notes = "Method for get workflow list by documentId",
            responseContainer = "List",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow list success."),
        @ApiResponse(code = 404, message = "Workflow list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list/{linkId}")
    public Response listByLinkId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId
    ) {
        LOG.info("listByLinkId...");
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
        responseData.put("message", "Workflow search not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            //MUST GET removed by, cause show both F and E
            List<Workflow> listWorkflow = workflowService.listByLinkIdGetRemoved(linkId, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWorkflow.isEmpty()) {
                List<WorkflowModel> listWorkflowModel = new ArrayList<>();
                for (Workflow workflow : listWorkflow) {
                    listWorkflowModel.add(workflowService.tranformToModel(workflow));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowModel);
            }
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
            value = "Method for get last workflow type A by created by",
            notes = "Method for get last workflow type A by created by",
            responseContainer = "List",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Workflow list success."),
        @ApiResponse(code = 404, message = "Workflow list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getLastReply")
    public Response getLastReply(
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("getLastReply...");
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
        responseData.put("message", "Workflow by id not found in the database.");
        try {
            WorkflowService workflowService = new WorkflowService();
            WorkflowModel workflowModel = null;
            List<Workflow> listWorkflow = workflowService.listLastReply(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (!listWorkflow.isEmpty()) {
                workflowModel = workflowService.tranformToModel(listWorkflow.get(0));
            }
            status = Response.Status.OK;
            responseData.put("data", workflowModel);
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
