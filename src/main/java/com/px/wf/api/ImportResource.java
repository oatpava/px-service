package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import com.px.wf.model.ImportWfContentModel;
import com.px.wf.service.ImportService;
import javax.ws.rs.GET;

/**
 *
 * @author Oat
 */
@Api(value = "Import เชื่อมโยงข้อมูล")
@Path("v1/imports")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ImportResource {

    private static final Logger LOG = Logger.getLogger(ImportResource.class.getName());

    @ApiOperation(
            value = "เชื่อมโยงข้อมูลหนังสือ (LDAP)",
            notes = "เชื่อมโยงข้อมูลหนังสือ (LDAP)"
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfContent created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response importWfContent(
            @ApiParam(name = "username", value = "username", required = true)
            @QueryParam("username") String username,
            @ApiParam(name = "password", value = "password", required = true)
            @QueryParam("password") String password,
            @ApiParam(name = "structureId", value = "structureId", required = false)
            @QueryParam("structureId") Integer structureId,
            ImportWfContentModel importWfContentModel
    ) {
        LOG.info("importWfContent...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        HashMap error = new HashMap();
        ImportService importService = new ImportService();
        try {
            String errorMessage = importService.checkData(importWfContentModel);
            if (errorMessage != null) {
                error.put("code", 400);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.checkAuthentication(username, password);
            if (errorMessage != null) {
                error.put("code", 401);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.checkUserProfile(username);
            if (errorMessage != null) {
                error.put("code", 404);
                error.put("message", errorMessage);
                throw new Exception();
            }

            if (structureId != null) {
                errorMessage = importService.checkStructure(structureId);
                if (errorMessage != null) {
                    error.put("code", 404);
                    error.put("message", errorMessage);
                    throw new Exception();
                }
            }

            errorMessage = importService.checkWfFolder(structureId);
            if (errorMessage != null) {
                error.put("code", 404);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.createDmsDocument();
            if (errorMessage != null) {
                error.put("code", 500);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.createWfContent(importWfContentModel);
            if (errorMessage != null) {
                error.put("code", 500);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.createWorkflow();
            if (errorMessage != null) {
                error.put("code", 500);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.updateWfContent();
            if (errorMessage != null) {
                error.put("code", 500);
                error.put("message", errorMessage);
                throw new Exception();
            }

            if (importWfContentModel.getFileAttach() != null) {
                errorMessage = importService.createFileAttach(importWfContentModel.getFileAttach());
                if (errorMessage != null) {
                    error.put("code", 500);
                    error.put("message", errorMessage);
                    throw new Exception();
                }

                errorMessage = importService.saveFileBase64(importWfContentModel.getFileAttach());
                if (errorMessage != null) {
                    error.put("code", 500);
                    error.put("message", errorMessage);
                    throw new Exception();
                }

                errorMessage = importService.updateFileAttach();
                if (errorMessage != null) {
                    error.put("code", 500);
                    error.put("message", errorMessage);
                    throw new Exception();
                }
            }

            responseData.put("success", true);
            responseData.put("data", importService.getData());
        } catch (Exception ex) {
            importService.deleteEntities();
            responseData.put("success", false);
            responseData.put("error", error);
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "ขอรหัสหน่วยงาน",
            notes = "ขอรหัสหน่วยงาน"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure list success."),
        @ApiResponse(code = 404, message = "Structure list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/structure")
    public Response listStructure(
            @ApiParam(name = "username", value = "username", required = true)
            @QueryParam("username") String username,
            @ApiParam(name = "password", value = "password", required = true)
            @QueryParam("password") String password
    ) {
        LOG.info("listStructure...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        HashMap error = new HashMap();
        ImportService importService = new ImportService();
        try {
            String errorMessage = importService.checkAuthentication(username, password);
            if (errorMessage != null) {
                error.put("code", 401);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.checkUserProfile(username);
            if (errorMessage != null) {
                error.put("code", 404);
                error.put("message", errorMessage);
                throw new Exception();
            }

            errorMessage = importService.listWfFolder();
            if (errorMessage != null) {
                error.put("code", 404);
                error.put("message", errorMessage);
                throw new Exception();
            }

            responseData.put("success", true);
            responseData.put("data", importService.getListData());
        } catch (Exception ex) {
            responseData.put("success", false);
            responseData.put("error", error);
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
}