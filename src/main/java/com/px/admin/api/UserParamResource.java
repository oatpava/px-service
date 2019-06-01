package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.User;
import com.px.admin.entity.UserParam;
import com.px.admin.model.UserParamModel;
import com.px.admin.service.UserParamService;
import com.px.admin.service.UserService;
import com.px.share.entity.Param;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Pritsana
 */
@Api(value = "UserParam การตั้งค่าระบบผู้ใช้")
@Path("v1/userParams")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserParamResource {

    private static final Logger LOG = Logger.getLogger(UserParamResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create UserParam",
            notes = "สร้างข้อมูลตั้งค่าระบบ",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "UserParam created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @BeanParam UserParamModel userParamModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userParamModel.getVersion())
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
            UserParam userParam = new UserParam();
//            userParam.setCreatedBy(userParamModel.getUserId());
            userParam.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            userParam.setUser(new UserService().getByIdNotRemoved(userParamModel.getUser().getId()));
            userParam.setParamName(userParamModel.getParamName());
            userParam.setParamValue(userParamModel.getParamValue());

            UserParamService userParamService = new UserParamService();
            userParam = userParamService.create(userParam);
            responseData.put("data", userParamService.tranformToModel(userParam));
            responseData.put("message", "");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get UserParam by id",
            notes = "ขอข้อมูลตั้งค่าระบบ ด้วย รหัสการตั้งค่า",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserParam by id success.")
        ,
        @ApiResponse(code = 404, message = "UserParam by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสการตั้งค่า", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("getById...");
        LOG.debug("id = " + id);
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
        responseData.put("message", "UserParam by id not found in the database.");
        try {
            UserParamService userParamService = new UserParamService();
            UserParam userParam = userParamService.getByIdNotRemoved(id);
            if (userParam != null) {
                status = Response.Status.OK;
                responseData.put("data", userParamService.tranformToModel(userParam));
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
            value = "Method for update UserParam.",
            notes = "แก้ไขข้อมูลตั้งค่า",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserParam updeted by id success.")
        ,@ApiResponse(code = 404, message = "UserParam by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสตั้งค่า", required = true)
            @PathParam("id") int id,
            UserParamModel userParamModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(userParamModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserParam by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserParamService userParamService = new UserParamService();
            UserParam userParam = userParamService.getById(id);
            if (userParam != null) {
                userParam.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userParam.setUser(new UserService().getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID"))));
//                userParam.setUser(new UserService().getByIdNotRemoved(userParamModel.getUser().getId()));
                userParam.setParamName(userParamModel.getParamName());
                userParam.setParamValue(userParamModel.getParamValue());

                userParam = userParamService.update(userParam);
                status = Response.Status.OK;
                responseData.put("data", userParamService.tranformToModel(userParam));
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
            value = "Method for delete UserParam by id.",
            notes = "ลบข้อมูลตั้งค่า",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserParam deleted by id success.")
        ,@ApiResponse(code = 404, message = "UserParam by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสตั้งค่า", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("remove...");
        LOG.debug("id = " + id);
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
        responseData.put("message", "UserParam by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserParamService userParamService = new UserParamService();
            UserParam userParam = userParamService.remove(id, versionModel.getUserId());
            if (userParam != null) {
                status = Response.Status.OK;
                responseData.put("data", userParamService.tranformToModel(userParam));
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
            value = "Method for list UserParam.",
            notes = "รายการตั้งค่า",
            responseContainer = "List",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserParam list success.")
        ,
        @ApiResponse(code = 404, message = "UserParam list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("list...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
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
        responseData.put("data", new ArrayList<>());
        responseData.put("message", "UserParam list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserParamService userParamService = new UserParamService();
            List<UserParam> listUserParam = userParamService.list(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUserParam.isEmpty()) {
                ArrayList<UserParamModel> listUserParamModel = new ArrayList<>();
                for (UserParam userParam : listUserParam) {
                    listUserParamModel.add(userParamService.tranformToModel(userParam));
                }
                listUserParamModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listUserParamModel);
                responseData.put("message", "");
            }
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
            value = "Method for get userParam by ParamName",
            notes = "ขอข้อมูลตั้งค่าระบบ ด้วย ชื่อการตั้งค่า",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Param by paramName success.")
        ,
        @ApiResponse(code = 404, message = "Param by paramName not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/name/{paramName}")
    public Response getByParamName(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "paramName", value = "ชื่อการตั้งค่า", required = true)
            @PathParam("paramName") String paramName
    ) {
        LOG.debug("getByParamName...");
        LOG.debug("paramName = " + paramName);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Param by paramName not found in the database.");
        try {
            UserParamService userParamService = new UserParamService();
            UserParam userParam = userParamService.getByParamName(paramName,Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(userParam!=null){
                            status = Response.Status.OK;
                            responseData.put("data", userParamService.tranformToModel(userParam));
                            responseData.put("message", "");
                        }
                responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get UserParam by userId.",
            notes = "ข้อมูลผู้ใช้งานระบบ ด้วยรหัสเข้าใช้งานระบบ",
            response = UserParamModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserParam success.")
        ,
        @ApiResponse(code = 404, message = "UserParam not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/user/{userId}")
    public Response getParamByUserId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("getParamByUserId...");
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        ArrayList<UserParamModel> listUserParamModel = new ArrayList<>();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", listUserParamModel);
        responseData.put("message", "UserParam not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserParamService userParamService = new UserParamService();
            List<UserParam> listUserParam = userParamService.listByUserId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            listUserParamModel = new ArrayList<>();
            if (!listUserParam.isEmpty()) {
                for (UserParam userParam : listUserParam) {
                    listUserParamModel.add(userParamService.tranformToModel(userParam));
                }
                listUserParamModel.trimToSize();
                responseData.put("data", listUserParamModel);
                responseData.put("message", "");
            } else {
                ParamService paramService = new ParamService();
                List<Param> listParam = paramService.getByName("TXT");
                if (!listParam.isEmpty()) {
                    for (Param param : listParam) {
                        UserParam userParam = new UserParam();
                        userParam.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userParam.setUser(new UserService().getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID"))));
                        userParam.setParamName(param.getParamName());
                        userParam.setParamValue(param.getParamValue());
                        LOG.debug(userParam.getUser());
                        userParam = userParamService.create(userParam);
                        responseData.put("data", userParamService.tranformToModel(userParam));
                        responseData.put("message", "");
                    }
                }
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
