package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserStatus;
import com.px.admin.model.UserStatusModel;
import com.px.admin.service.UserStatusService;
import com.px.share.model.ListOptionModel;
import com.px.share.model.VersionModel;
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
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "UserStatus สถานะผู้ใช้งานระบบ")
@Path("v1/userStatuss")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class UserStatusResource {
    private static final Logger LOG = Logger.getLogger(UserStatusResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create UserStatus", 
        notes = "สร้างข้อมูล UserStatus",
        response = UserStatusModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "UserStatus created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        UserStatusModel userStatusModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userStatusModel.getVersion())
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
        try{
            UserStatus userStatus = new UserStatus();
            userStatus.setUserStatusName(userStatusModel.getName());
            userStatus.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            UserStatusService userStatusService = new UserStatusService();
            userStatus = userStatusService.create(userStatus);
            responseData.put("message", "UserStatus created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get UserStatus by id", 
        notes = "ขอข้อมูล UserStatus ด้วย รหัส UserStatus", 
        response = UserStatusModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserStatus by id success." ),
        @ApiResponse( code = 404, message = "UserStatus by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัส UserStatus", required = true) 
        @PathParam("id") int id
    ) {
        LOG.debug("getById...");
        LOG.debug("id = "+id);
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
        responseData.put("message", "UserStatus by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserStatusService userStatusService = new UserStatusService();
            UserStatus userStatus = userStatusService.getById(id);
            if(userStatus!=null){
                status = Response.Status.OK;
                responseData.put("data", userStatusService.tranformToModel(userStatus));
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
        value = "Method for update UserStatus.", 
        notes = "แก้ไขข้อมูล UserStatus", 
        response = UserStatusModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserStatus updeted by id success." )
        ,@ApiResponse( code = 404, message = "UserStatus by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัส UserStatus", required = true) 
        @PathParam("id") int id, 
        UserStatusModel userStatusModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(userStatusModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserStatus by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserStatusService userStatusService = new UserStatusService();
            UserStatus userStatus = userStatusService.getById(id);
            if(userStatus!=null){                
                userStatus.setUserStatusName(userStatusModel.getName());
                userStatus.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userStatus = userStatusService.update(userStatus);
                status = Response.Status.OK;
                responseData.put("data", userStatusService.tranformToModel(userStatus));
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
        value = "Method for delete UserStatus by id.", 
        notes = "ลบข้อมูล UserStatus", 
        response = UserStatusModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserStatus deleted by id success." )
        ,@ApiResponse( code = 404, message = "UserStatus by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัส UserStatus", required = true) 
        @PathParam("id") int id
    ) {
        LOG.debug("remove...");
        LOG.debug("id = "+id);
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
        responseData.put("message", "UserStatus by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserStatusService userStatusService = new UserStatusService();
            UserStatus userStatus = userStatusService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(userStatus!=null){
                status = Response.Status.OK;
                responseData.put("data", userStatusService.tranformToModel(userStatus));
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
        value = "Method for list UserStatus.", 
        notes = "รายการ UserStatus", 
        responseContainer = "List",
        response = UserStatusModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserStatus list success." ),
        @ApiResponse( code = 404, message = "UserStatus list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response list(
        @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("list...");
        LOG.debug("offset = "+listOptionModel.getOffset());
        LOG.debug("limit = "+listOptionModel.getLimit());
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
        responseData.put("message", "UserStatus list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserStatusService userStatusService = new UserStatusService();
            List<UserStatus> listUserStatus = userStatusService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listUserStatus.isEmpty()){
                ArrayList<UserStatusModel> listUserStatusModel = new ArrayList<>();
                for (UserStatus userStatus : listUserStatus) {
                    listUserStatusModel.add(userStatusService.tranformToModel(userStatus));
                }
                listUserStatusModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listUserStatusModel);
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
}
