package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.entity.FileAttachApprove;
import com.px.share.model.FileAttachApproveModel;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.service.FileAttachApproveService;
import com.px.share.service.FileAttachService;
import com.px.share.util.Common;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author Oat
 */
@Api(value = "FileAttachApprove การรับรองไฟล์เอกสารแนบ")
@Path("v1/fileAttachApproves")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FileAttachApproveResource {

    private static final Logger LOG = Logger.getLogger(FileAttachApproveResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลการรับรองไฟล์เอกสารแนบ",
            notes = "สร้างข้อมูลการรับรองไฟล์เอกสารแนบ",
            response = FileAttachApproveModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FileAttachApprove created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @ApiParam(name = "caPassword", value = "รหัสผ่าน CA", required = true)
            @QueryParam("caPassword") String caPassword,
            FileAttachApproveModel fileAttachApproveModel
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachApproveModel.getVersion())
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
        FileAttachApproveService fileAttachApproveService = new FileAttachApproveService();
        FileAttachApprove fileAttachApprove = new FileAttachApprove();
        try {
            FileAttachService fielAttachService = new FileAttachService();
            UserProfile userProfile = new UserProfileService().getById(fileAttachApproveModel.getUserProfileId());

            fileAttachApprove.setCreatedBy(Integer.valueOf(httpHeaders.getHeaderString("userID")));
            fileAttachApprove.setFileAttach(fielAttachService.getById(fileAttachApproveModel.getFileAttachId()));
            fileAttachApprove.setUserProfile(userProfile);
            fileAttachApprove = fileAttachApproveService.create(fileAttachApprove);

            final String issuedBy = "Issued by: DPIM Certification Authority";
            final String signedBy = "Digitally Signed by: "
                    + (userProfile.getUserProfileFullNameEng() != null ? userProfile.getUserProfileFullNameEng() : "")
                    + " "
                    + (Common.localDateTimeToString(fileAttachApprove.getCreatedDate()));

            final String errorMsg = fielAttachService.cert(fileAttachApprove, caPassword, issuedBy, signedBy);
            if (errorMsg == null) {
                responseData.put("data", fileAttachApproveService.tranformToModel(fileAttachApprove));
                responseData.put("message", "FileAttachApprove created successfully.");
                status = Response.Status.CREATED;
                responseData.put("success", true);
            } else {
                responseData.put("message", errorMsg);
                status = Response.Status.NOT_FOUND;
                throw new Exception(errorMsg);
            }
        } catch (Exception ex) {
            if (fileAttachApprove.getId() != null) {
                fileAttachApproveService.delete(fileAttachApprove);
            }
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลการรับรองไฟล์เอกสารแนบ ด้วย รหัสการรับรองไฟล์เอกสารแนบ",
            notes = "ขอข้อมูลการรับรองไฟล์เอกสารแนบ ด้วย รหัสการรับรองไฟล์เอกสารแนบ",
            response = FileAttachApproveModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttachApprove by id success."),
        @ApiResponse(code = 404, message = "FileAttachApprove by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสการรับรองไฟล์เอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttachApprove by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachApproveService fileAttachApproveService = new FileAttachApproveService();
            FileAttachApprove fileAttachApprove = fileAttachApproveService.getByIdNotRemoved(id);
            if (fileAttachApprove != null) {
                status = Response.Status.OK;
                responseData.put("data", fileAttachApproveService.tranformToModel(fileAttachApprove));
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
            value = "แก้ไขข้อมูลการรับรองไฟล์เอกสารแนบ",
            notes = "แก้ไขข้อมูลการรับรองไฟล์เอกสารแนบ",
            response = FileAttachApproveModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttachApprove updeted by id success."),
        @ApiResponse(code = 404, message = "FileAttachApprove by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสการรับรองไฟล์เอกสารแนบ", required = true)
            @PathParam("id") int id,
            FileAttachApproveModel fileAttachApproveModel
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(fileAttachApproveModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttachApprove by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachApproveService fileAttachApproveService = new FileAttachApproveService();
            FileAttachApprove fileAttachApprove = fileAttachApproveService.getByIdNotRemoved(id);
            if (fileAttachApprove != null) {
                fileAttachApprove.setFileAttach(new FileAttachService().getById(fileAttachApproveModel.getFileAttachId()));
                fileAttachApprove.setUserProfile(new UserProfileService().getById(fileAttachApproveModel.getUserProfileId()));
                fileAttachApprove.setUpdatedBy(Integer.valueOf(httpHeaders.getHeaderString("userID")));
                fileAttachApprove = fileAttachApproveService.update(fileAttachApprove);
                status = Response.Status.OK;
                responseData.put("data", fileAttachApproveService.tranformToModel(fileAttachApprove));
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
            value = "ลบข้อมูลการรับรองไฟล์เอกสารแนบด้วยรหัสการรับรองไฟล์เอกสารแนบ",
            notes = "ลบข้อมูลการรับรองไฟล์เอกสารแนบ",
            response = FileAttachApproveModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttachApprove deleted by id success."),
        @ApiResponse(code = 404, message = "FileAttachApprove by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสการรับรองไฟล์เอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttachApprove by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachApproveService fileAttachApproveService = new FileAttachApproveService();
            FileAttachApprove fileAttachApprove = fileAttachApproveService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (fileAttachApprove != null) {
                status = Response.Status.OK;
                responseData.put("data", fileAttachApproveService.tranformToModel(fileAttachApprove));
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
            code = 200,
            value = "รายการการรับรองไฟล์เอกสารแนบทั้งหมด ด้วยรหัสไฟล์เอกสารแนบ",
            notes = "รายการการรับรองไฟล์เอกสารแนบทั้งหมด ด้วยรหัสไฟล์เอกสารแนบ",
            responseContainer = "List",
            response = FileAttachApproveModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FileAttachApprove list all by FileAttach id success.", response = FileAttachApproveModel.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "FileAttachApprove list all by FileAttach id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/all/{fileAttachId}")
    public Response listAllByFielAttachId(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "fileAttachId", value = "รหัสไฟล์เอกสารแนบ", required = true)
            @PathParam("fileAttachId") int fileAttachId
    ) {
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FileAttachApprove list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            FileAttachApproveService fileAttachApproveService = new FileAttachApproveService();
            List<FileAttachApprove> listFileAttachApprove = fileAttachApproveService.listAll(listOptionModel.getSort(), listOptionModel.getDir(), fileAttachId);
            ArrayList<FileAttachApproveModel> listFileAttachApproveModel = new ArrayList<>();
            if (!listFileAttachApprove.isEmpty()) {
                for (FileAttachApprove fileAttachApprove : listFileAttachApprove) {
                    listFileAttachApproveModel.add(fileAttachApproveService.tranformToModel(fileAttachApprove));
                }
                listFileAttachApproveModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listFileAttachApproveModel);
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
