package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.wf.model.ImportStatusModel;
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
        @ApiResponse(code = 201, message = "Create Success."),
        @ApiResponse(code = 400, message = "Bad Request!."),
        @ApiResponse(code = 401, message = "Unauthorized!."),
        @ApiResponse(code = 404, message = "Not Found!."),
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
        Status responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        HashMap error = new HashMap();
        ImportService importService = new ImportService();
        try {
            ImportStatusModel status = importService.checkData(importWfContentModel);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkData");

            status = importService.checkAuthentication(username, password);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkAuthentication");

            status = importService.checkUserProfile(username);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkUserProfile");

            if (structureId != null) {
                status = importService.checkStructure(structureId);
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("checkStructure");
            }

            status = importService.checkWfFolder(structureId);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkWfFolder");

            status = importService.createDmsDocument();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createDmsDocument");

            status = importService.createWfContent(importWfContentModel);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createWfContent");

            status = importService.createWorkflow();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createWorkflow");

            status = importService.updateWfContent();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("updateWfContent");

            if (importWfContentModel.getFileAttach() != null) {
                status = importService.createFileAttach(importWfContentModel.getFileAttach());
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("createFileAttach");

                status = importService.saveFileBase64(importWfContentModel.getFileAttach());
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("saveFileBase64");

                status = importService.updateFileAttach();
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("updateFileAttach");

            }

            responseData.put("success", true);
            responseData.put("data", importService.getData());
            responseStatus = Response.Status.CREATED;
            System.out.println("success");
        } catch (Exception ex) {
            if (error.get("code") == null) {
                error.put("code", 500);
                error.put("message", ex.getMessage());
                System.out.println("err: " + ex.getMessage());
            }
            responseData.put("success", false);
            responseData.put("error", error);
            importService.deleteEntities();
        }
        return Response.status(responseStatus).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอรหัสหน่วยงาน",
            notes = "ขอรหัสหน่วยงาน"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success."),
        @ApiResponse(code = 401, message = "Unauthorized!."),
        @ApiResponse(code = 404, message = "Not Found!."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    }
    )
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
        Status responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        HashMap error = new HashMap();
        ImportService importService = new ImportService();
        try {
            ImportStatusModel status = importService.checkAuthentication(username, password);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }

            status = importService.checkUserProfile(username);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }

            status = importService.listWfFolder();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }

            responseData.put("success", true);
            responseData.put("data", importService.getListData());
            responseStatus = Response.Status.OK;
        } catch (Exception ex) {
            if (error.get("code") == null) {
                error.put("code", 500);
                error.put("message", ex.getMessage());
            }
            responseData.put("success", false);
            responseData.put("error", error);
        }
        return Response.status(responseStatus).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "เชื่อมโยงข้อมูลหนังสือ (ภายนอก)",
            notes = "เชื่อมโยงข้อมูลหนังสือ (ภายนอก)"
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Create Success."),
        @ApiResponse(code = 400, message = "Bad Request!."),
        @ApiResponse(code = 401, message = "Unauthorized!."),
        @ApiResponse(code = 404, message = "Not Found!."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/external")
    public Response importWfContentExternal(
            @ApiParam(name = "key", value = "key", required = true)
            @QueryParam("key") String key,
            @ApiParam(name = "provinceCode", value = "provinceCode", required = true)
            @QueryParam("provinceCode") String provinceCode,
            ImportWfContentModel importWfContentModel
    ) {
        LOG.info("importWfContentExternal...");
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status responseStatus = Response.Status.INTERNAL_SERVER_ERROR;
        HashMap error = new HashMap();
        ImportService importService = new ImportService();
        try {
            ImportStatusModel status = importService.checkData(importWfContentModel);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkData");

            status = importService.checkAuthentication(key);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkAuthentication");

            status = importService.checkProvince(provinceCode);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkUserProfile");

            status = importService.checkWfFolder();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("checkWfFolder");

            status = importService.createDmsDocument();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createDmsDocument");

            status = importService.createWfContent(importWfContentModel);
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createWfContent");

            status = importService.createWorkflow();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("createWorkflow");

            status = importService.updateWfContent();
            if (!status.getIsSuccess()) {
                error.put("code", status.getStatusCode());
                error.put("message", status.getErrorMessage());
                responseStatus = status.getResponseStatus();
                throw new Exception(status.getErrorMessage());
            }
            System.out.println("updateWfContent");

            if (importWfContentModel.getFileAttach() != null) {
                status = importService.createFileAttach(importWfContentModel.getFileAttach());
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("createFileAttach");

                status = importService.saveFileBase64(importWfContentModel.getFileAttach());
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("saveFileBase64");

                status = importService.updateFileAttach();
                if (!status.getIsSuccess()) {
                    error.put("code", status.getStatusCode());
                    error.put("message", status.getErrorMessage());
                    responseStatus = status.getResponseStatus();
                    throw new Exception(status.getErrorMessage());
                }
                System.out.println("updateFileAttach");

            }

            responseData.put("success", true);
            responseData.put("data", importService.getData());
            responseStatus = Response.Status.CREATED;
            System.out.println("success");
        } catch (Exception ex) {
            if (error.get("code") == null) {
                error.put("code", 500);
                error.put("message", ex.getMessage());
                System.out.println("err: " + ex.getMessage());
            }
            responseData.put("success", false);
            responseData.put("error", error);
            importService.deleteEntities();
        }
        return Response.status(responseStatus).entity(gs.toJson(responseData)).build();
    }

}
