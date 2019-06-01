package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.VersionModel;
import com.px.mwp.entity.WorkflowType;
import com.px.mwp.model.WorkflowModel;
import com.px.mwp.model.WorkflowTypeModel;
import com.px.mwp.service.WorkflowTypeService;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "WorkflowType ประเภทของการดำเนินการ")
@Path("v1/workflowTypes")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WorkflowTypeResource {
    private static final Logger LOG = Logger.getLogger(WorkflowTypeResource.class.getName());

    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "สร้างข้อมูลประเภทของการดำเนินการ",
            notes = "สร้างข้อมูลประเภทของการดำเนินการ",
            response = WorkflowTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WorkflowType created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WorkflowTypeModel workflowTypePostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(workflowTypePostModel.getVersion())
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
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();
            WorkflowType workflowType = new WorkflowType();
            workflowType.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            workflowType.setWorkflowTypeTitle(workflowTypePostModel.getWorkflowTypeTitle());
            workflowType.setWorkflowTypeAction(workflowTypePostModel.getWorkflowTypeAction());
            workflowType.setWorkflowTypeActionType(workflowTypePostModel.getWorkflowTypeActionType());

            if (workflowType != null) {
                workflowType = workflowTypeService.create(workflowType);

                workflowType.setOrderNo(workflowType.getId());
                workflowType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                workflowType = workflowTypeService.update(workflowType);

                responseData.put("data", workflowTypeService.tranformToModel(workflowType));
                responseData.put("message", "Workflow created successfully.");
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
            value = "ขอข้อมูลประเภทของการดำเนินการ ด้วย รหัสประเภทของการดำเนินการ",
            notes = "ขอข้อมูลประเภทของการดำเนินการ ด้วย รหัสประเภทของการดำเนินการ",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType by id success."),
        @ApiResponse(code = 404, message = "WorkflowType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทของการดำเนินการ", required = true)
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
        responseData.put("message", "WorkflowType by id not found in the database.");
        try {
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();
            WorkflowType workflowType = workflowTypeService.getById(id);
            if (workflowType != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowTypeService.tranformToModel(workflowType));
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
            value = "แก้ไขข้อมูลประเภทของการดำเนินการ",
            notes = "แก้ไขข้อมูลประเภทของการดำเนินการ",
            response = WorkflowTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType updeted by id success."),
        @ApiResponse(code = 404, message = "WorkflowType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(
            WorkflowTypeModel workflowTypePostModel
    ) {
        LOG.info("update...");
        Gson gs = new GsonBuilder()
                .setVersion(workflowTypePostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WorkflowType by id not found in the database.");
        try {
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();
            WorkflowType workflowType = workflowTypeService.getById(workflowTypePostModel.getId());

            if (workflowType != null) {
                workflowType.setWorkflowTypeTitle(workflowTypePostModel.getWorkflowTypeTitle());
                workflowType.setWorkflowTypeAction(workflowTypePostModel.getWorkflowTypeAction());
                workflowType.setWorkflowTypeActionType(workflowTypePostModel.getWorkflowTypeActionType());
                workflowType = workflowTypeService.update(workflowType);

                workflowType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                workflowType = workflowTypeService.update(workflowType);
                
                status = Response.Status.OK;
                responseData.put("data", workflowTypeService.tranformToModel(workflowType));
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
            value = "ลบข้อมูลประเภทของการดำเนินการ",
            notes = "ลบข้อมูลประเภทของการดำเนินการ",
            response = WorkflowModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType deleted by id success."),
        @ApiResponse(code = 404, message = "WorkflowType by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทของการดำเนินการ", required = true)
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
        responseData.put("message", "WorkflowType by id not found in the database.");
        try {
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();
            WorkflowType workflowType = workflowTypeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (workflowType != null) {
                status = Response.Status.OK;
                responseData.put("data", workflowTypeService.tranformToModel(workflowType));
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
            value = "รายการผังการทำงาน",
            notes = "รายการผังการทำงาน",
            responseContainer = "List",
//            response = WorkflowModel.class
            response = WorkflowTypeModel.class//oat-edit
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType list success."),
        @ApiResponse(code = 404, message = "WorkflowType list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listAll")
    public Response listAll(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.info("listAll...");
        LOG.info("sort = " + listOptionModel.getSort());
        LOG.info("dir = " + listOptionModel.getDir());
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
        responseData.put("message", "WorkflowType by id not found in the database.");
        try {
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();
            List<WorkflowType> listWorkflowType = workflowTypeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWorkflowType.isEmpty()) {
                List<WorkflowTypeModel> listWorkflowTypeModel = new ArrayList<>();
                for (WorkflowType workflowType : listWorkflowType) {
                    listWorkflowTypeModel.add(workflowTypeService.tranformToModel(workflowType));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowTypeModel);
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
            response = WorkflowTypeModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WorkflowType list success."),
        @ApiResponse(code = 404, message = "WorkflowType list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByActionType/{actionType}")
    public Response listByActionType(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "actionType", value = "ประเภทของการใช้งาน", required = true)
            @PathParam("actionType") String actionType
    ) {
        LOG.info("listByActionType...");
        LOG.info("actionType = " + actionType);
        LOG.info("sort = " + listOptionModel.getSort());
        LOG.info("dir = " + listOptionModel.getDir());
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
        responseData.put("message", "WorkflowType by id not found in the database.");
        try {
            WorkflowTypeService workflowTypeService = new WorkflowTypeService();

            List<WorkflowType> listWorkflowType = workflowTypeService.listByActionType(actionType, listOptionModel.getSort(), listOptionModel.getDir());
            if (!listWorkflowType.isEmpty()) {
                List<WorkflowTypeModel> listWorkflowTypeModel = new ArrayList<>();
                for (WorkflowType workflowType : listWorkflowType) {
                    listWorkflowTypeModel.add(workflowTypeService.tranformToModel(workflowType));
                }
                status = Response.Status.OK;
                responseData.put("data", listWorkflowTypeModel);
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
