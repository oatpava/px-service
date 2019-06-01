package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.model.UserProfileFolderModel;
import com.px.mwp.service.UserProfileFolderService;
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
@Api(value = "UserProfileFolder แฟ้มของผู้ใช้งานระบบ")
@Path("v1/userProfileFolders")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserProfileFolderResource {

    private static final Logger LOG = Logger.getLogger(UserProfileFolderResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลแฟ้มของผู้ใช้งานระบบ",
            notes = "สร้างข้อมูลแฟ้มของผู้ใช้งานระบบ",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "UserProfileFolder created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            UserProfileFolderModel userProfileFolderModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userProfileFolderModel.getVersion())
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
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();

            UserProfileFolder userProfileFolder = new UserProfileFolder();
            userProfileFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            userProfileFolder.setUserProfileFolderDetail(userProfileFolderModel.getUserProfileFolderDetail());
            userProfileFolder.setUserProfileFolderLinkId(userProfileFolderModel.getUserProfileFolderLinkId());
            userProfileFolder.setUserProfileFolderName(userProfileFolderModel.getUserProfileFolderName());
            userProfileFolder.setUserProfileFolderType(userProfileFolderModel.getUserProfileFolderType());
            userProfileFolder.setUserProfileId(userProfileFolderModel.getUserProfileId());

            if (userProfileFolder != null) {
                userProfileFolder = userProfileFolderService.create(userProfileFolder);

                userProfileFolder.setOrderNo(userProfileFolder.getId());
                userProfileFolder = userProfileFolderService.update(userProfileFolder);

                responseData.put("data", userProfileFolderService.tranformToModel(userProfileFolder));
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
            value = "แก้ไขข้อมูลแฟ้มของผู้ใช้งานระบบ",
            notes = "แก้ไขข้อมูลแฟ้มของผู้ใช้งานระบบ",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            UserProfileFolderModel userProfileFolderModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + userProfileFolderModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(userProfileFolderModel.getVersion())
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
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            UserProfileFolder userProfileFolder = userProfileFolderService.getById(userProfileFolderModel.getId());

            if (userProfileFolder != null) {
                userProfileFolder.setUserProfileFolderDetail(userProfileFolderModel.getUserProfileFolderDetail());
                userProfileFolder.setUserProfileFolderLinkId(userProfileFolderModel.getUserProfileFolderLinkId());
                userProfileFolder.setUserProfileFolderName(userProfileFolderModel.getUserProfileFolderName());
                userProfileFolder.setUserProfileFolderType(userProfileFolderModel.getUserProfileFolderType());
                userProfileFolder.setUserProfileId(userProfileFolderModel.getUserProfileId());

                userProfileFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userProfileFolder = userProfileFolderService.update(userProfileFolder);

                status = Response.Status.OK;
                responseData.put("data", userProfileFolderService.tranformToModel(userProfileFolder));
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
            value = "ลบข้อมูลแฟ้มของผู้ใช้งานระบบ",
            notes = "ลบข้อมูลแฟ้มของผู้ใช้งานระบบ",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder removed by id success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder by id not found in the database.")
        ,
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
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            UserProfileFolder userProfileFolder = userProfileFolderService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (userProfileFolder != null) {
                status = Response.Status.OK;
                responseData.put("data", userProfileFolderService.tranformToModel(userProfileFolder));
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
            value = "รายการแฟ้มของผู้ใช้งานระบบโดยรหัสผู้ใช้งาน",
            notes = "รายการแฟ้มของผู้ใช้งานระบบโดยรหัสผู้ใช้งาน",
            responseContainer = "List",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/userProfileId/{userProfileId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByUserProfileId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งาน", required = true)
            @PathParam("userProfileId") int userProfileId
    ) {
        LOG.info("listByUserProfileId...");
        LOG.info("userProfileId = " + userProfileId);
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
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(userProfileId);
            if (!listUserProfileFolder.isEmpty()) {
                ArrayList<UserProfileFolderModel> listUserProfileFolderModel = new ArrayList<>();
                for (UserProfileFolder userProfileFolder : listUserProfileFolder) {
                    listUserProfileFolderModel.add(userProfileFolderService.tranformToModel(userProfileFolder));
                }
                listUserProfileFolderModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listUserProfileFolderModel);
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
            value = "รายการแฟ้มของผู้ใช้งานระบบโดยรหัสผู้ใช้งานและประเภท",
            notes = "รายการแฟ้มของผู้ใช้งานระบบโดยรหัสผู้ใช้งานและประเภท",
            responseContainer = "List",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/userProfileId/{userProfileId}/{type}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByUserProfileIdAndType(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งาน", required = true)
            @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "type", value = "ประเภท", required = true)
            @PathParam("type") String type
    ) {
        LOG.info("listByUserProfileId...");
        LOG.info("userProfileId = " + userProfileId);
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
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(userProfileId, type);
            if (!listUserProfileFolder.isEmpty()) {
                ArrayList<UserProfileFolderModel> listUserProfileFolderModel = new ArrayList<>();
                for (UserProfileFolder userProfileFolder : listUserProfileFolder) {
                    listUserProfileFolderModel.add(userProfileFolderService.tranformToModel(userProfileFolder));
                }
                listUserProfileFolderModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listUserProfileFolderModel);
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
            value = "รายการแฟ้มของผู้ใช้งานระบบโดยประเภทของรายการ",
            notes = "รายการแฟ้มของผู้ใช้งานระบบโดยประเภทของรายการ",
            responseContainer = "List",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/userProfileIdHeader/{type}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByType(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "type", value = "ประเภท", required = true)
            @PathParam("type") String type
    ) {
        LOG.info("userProfileIdHeader...");
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
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(Integer.parseInt(httpHeaders.getHeaderString("userID")), type);
            if (!listUserProfileFolder.isEmpty()) {
                ArrayList<UserProfileFolderModel> listUserProfileFolderModel = new ArrayList<>();
                for (UserProfileFolder userProfileFolder : listUserProfileFolder) {
                    listUserProfileFolderModel.add(userProfileFolderService.tranformToModel(userProfileFolder));
                }
                listUserProfileFolderModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listUserProfileFolderModel);
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
            value = "รายการแฟ้มของผู้ใช้งานระบบในหน้าจอส่วนตัว",
            notes = "รายการแฟ้มของผู้ใช้งานระบบในหน้าจอส่วนตัว",
            responseContainer = "List",
            response = UserProfileFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfileFolder list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfileFolder list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/folderInMWP")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response folderInMWP(
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("folderInMWP...");
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
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
        responseData.put("message", "UserProfileFolder by id not found in the database.");
        try {
            UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
            List<UserProfileFolder> listUserProfileFolder = userProfileFolderService.listByUserProfileId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            ArrayList<UserProfileFolderModel> listUserProfileFolderModel = new ArrayList<>();
            if (!listUserProfileFolder.isEmpty()) {
                for (UserProfileFolder userProfileFolder : listUserProfileFolder) {
                    listUserProfileFolderModel.add(userProfileFolderService.tranformToModel2(userProfileFolder));
                }
                listUserProfileFolderModel.trimToSize();
            }
            status = Response.Status.OK;
            responseData.put("data", listUserProfileFolderModel);
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
