package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.mwp.model.StructureFolderModel;
import com.px.mwp.entity.StructureFolder;
import com.px.mwp.service.StructureFolderService;
import com.px.share.model.VersionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
@Api(value = "StructureFolder แฟ้มของโครงสร้างระบบ")
@Path("v1/structureFolders")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StructureFolderResource {

    private static final Logger LOG = Logger.getLogger(StructureFolderResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลแฟ้มของโครงสร้างระบบ",
            notes = "สร้างข้อมูลแฟ้มของโครงสร้างระบบ",
            response = StructureFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "StructureFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            StructureFolderModel structureFolderModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(structureFolderModel.getVersion())
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
            StructureFolderService structureFolderService = new StructureFolderService();

            StructureFolder structureFolder = new StructureFolder();
            structureFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            structureFolder.setStructureFolderDetail(structureFolderModel.getStructureFolderDetail());
            structureFolder.setStructureFolderLinkId(structureFolderModel.getStructureFolderLinkId());
            structureFolder.setStructureFolderName(structureFolderModel.getStructureFolderName());
            structureFolder.setStructureFolderType(structureFolderModel.getStructureFolderType());
            structureFolder.setStructureId(structureFolderModel.getStructureId());

            if (structureFolder != null) {
                structureFolder = structureFolderService.create(structureFolder);

                structureFolder.setOrderNo(structureFolder.getId());
                structureFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                structureFolder = structureFolderService.update(structureFolder);

                responseData.put("data", structureFolderService.tranformToModel(structureFolder));
                responseData.put("message", "UserProfileFolder created successfully.");
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
            value = "แก้ไขข้อมูลแฟ้มของโครงสร้างระบบ",
            notes = "แก้ไขข้อมูลแฟ้มของโครงสร้างระบบ",
            response = StructureFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder updeted by id success."),
        @ApiResponse(code = 404, message = "UserProfileFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            StructureFolderModel structureFolderModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + structureFolderModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(structureFolderModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            StructureFolderService structureFolderService = new StructureFolderService();
            StructureFolder structureFolder = structureFolderService.getById(structureFolderModel.getId());

            if (structureFolder != null) {
                structureFolder.setStructureFolderDetail(structureFolderModel.getStructureFolderDetail());
                structureFolder.setStructureFolderLinkId(structureFolderModel.getStructureFolderLinkId());
                structureFolder.setStructureFolderName(structureFolderModel.getStructureFolderName());
                structureFolder.setStructureFolderType(structureFolderModel.getStructureFolderType());
                structureFolder.setStructureId(structureFolderModel.getStructureId());

                structureFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                structureFolder = structureFolderService.update(structureFolder);

                status = Response.Status.OK;
                responseData.put("data", structureFolderService.tranformToModel(structureFolder));
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
            value = "ลบข้อมูลแฟ้มของโครงสร้างระบบ",
            notes = "ลบข้อมูลแฟ้มของโครงสร้างระบบ",
            response = StructureFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "StructureFolder removed by id success."),
        @ApiResponse(code = 404, message = "StructureFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มของผู้ใช้งานระบบ", required = true)
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
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            StructureFolderService structureFolderService = new StructureFolderService();
            StructureFolder structureFolder = structureFolderService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (structureFolder != null) {
                status = Response.Status.OK;
                responseData.put("data", structureFolderService.tranformToModel(structureFolder));
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
            value = "รายการแฟ้มของโครงสร้างระบบโดยรหัสโครงสร้างระบบ",
            notes = "รายการแฟ้มของโครงสร้างระบบโดยรหัสโครงสร้างระบบ",
            responseContainer = "List",
            response = StructureFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "StructureFolder list success."),
        @ApiResponse(code = 404, message = "StructureFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/list/{structureId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByStructureId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "structureId", value = "รายการรหัสโครงสร้างระบบ", required = true)
            @PathParam("structureId") int structureId
    ) {
        LOG.info("listByStructureId...");
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
        responseData.put("data", null);
        responseData.put("message", "StructureFolder by id not found in the database.");
        try {
            StructureFolderService structureFolderService = new StructureFolderService();
            List<StructureFolder> listStructureFolder = structureFolderService.listByStructureId(structureId);
            if (!listStructureFolder.isEmpty()) {
                ArrayList<StructureFolderModel> listStructureFolderModel = new ArrayList<>();
                for (StructureFolder structureFolder : listStructureFolder) {
                    listStructureFolderModel.add(structureFolderService.tranformToModel(structureFolder));
                }
                listStructureFolderModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listStructureFolderModel);
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
            value = "รายการแฟ้มของโครงสร้างระบบโดยรหัสโครงสร้างระบบและประเภท",
            notes = "รายการแฟ้มของโครงสร้างระบบโดยรหัสโครงสร้างระบบและประเภท",
            responseContainer = "List",
            response = StructureFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "StructureFolder list success."),
        @ApiResponse(code = 404, message = "StructureFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/structure/{structureId}/{type}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByStructureIdAndType(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "structureId", value = "รายการรหัสโครงสร้างระบบ", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "type", value = "ประเภท", required = true)
            @PathParam("type") String type
    ) {
        LOG.info("listByStructureId...");
        LOG.info("structureId = " + structureId);
        LOG.info("type = " + type);
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
        responseData.put("data", null);
        responseData.put("message", "StructureFolder by id not found in the database.");
        try {
            StructureFolderService structureFolderService = new StructureFolderService();
            List<StructureFolder> listStructureFolder = structureFolderService.listByStructureId(structureId, type);
            if (!listStructureFolder.isEmpty()) {
                ArrayList<StructureFolderModel> listStructureFolderModel = new ArrayList<>();
                for (StructureFolder structureFolder : listStructureFolder) {
                    listStructureFolderModel.add(structureFolderService.tranformToModel(structureFolder));
                }
                listStructureFolderModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listStructureFolderModel);
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
