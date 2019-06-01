package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserProfileType;
import com.px.admin.model.UserProfileTypeModel;
import com.px.admin.service.UserProfileTypeService;
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
@Api(value = "UserProfileType ประเภทผู้ใช้งานระบบ")
@Path("v1/userProfileTypes")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class UserProfileTypeResource {
    private static final Logger LOG = Logger.getLogger(UserProfileTypeResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create UserProfileType", 
        notes = "สร้างข้อมูลประเภทผู้ใช้งานระบบ",
        response = UserProfileTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "UserProfileType created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        UserProfileTypeModel userProfileTypeModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userProfileTypeModel.getVersion())
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
            UserProfileType userProfileType = new UserProfileType(userProfileTypeModel.getName(),Integer.parseInt(httpHeaders.getHeaderString("userID")));
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            userProfileType = userProfileTypeService.create(userProfileType);
            responseData.put("message", "UserProfileType created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get UserProfileType by id", 
        notes = "ขอข้อมูลประเภทผู้ใช้งานระบบ ด้วย รหัสประเภทผู้ใช้งานระบบ", 
        response = UserProfileTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserProfileType by id success." ),
        @ApiResponse( code = 404, message = "UserProfileType by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบ", required = true) 
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
        responseData.put("message", "UserProfileType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserProfileType userProfileType = userProfileTypeService.getById(id);
            if(userProfileType!=null){
                status = Response.Status.OK;
                responseData.put("data", userProfileTypeService.tranformToModel(userProfileType));
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
        value = "Method for update UserProfileType.", 
        notes = "แก้ไขข้อมูลประเภทผู้ใช้งานระบบ", 
        response = UserProfileTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserProfileType updeted by id success." )
        ,@ApiResponse( code = 404, message = "UserProfileType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบ", required = true) 
        @PathParam("id") int id, 
        UserProfileTypeModel userProfileTypeModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(userProfileTypeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfileType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserProfileType userProfileType = userProfileTypeService.getById(id);
            if(userProfileType!=null){
                userProfileType.setUserProfileTypeName(userProfileTypeModel.getName());
                userProfileType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userProfileType = userProfileTypeService.update(userProfileType);
                status = Response.Status.OK;
                responseData.put("data", userProfileTypeService.tranformToModel(userProfileType));
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
        value = "Method for delete UserProfileType by id.", 
        notes = "ลบข้อมูลประเภทผู้ใช้งานระบบ", 
        response = UserProfileTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserProfileType deleted by id success." )
        ,@ApiResponse( code = 404, message = "UserProfileType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบ", required = true) 
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
        responseData.put("message", "UserProfileType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserProfileType userProfileType = userProfileTypeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(userProfileType!=null){
                status = Response.Status.OK;
                responseData.put("data", userProfileTypeService.tranformToModel(userProfileType));
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
        value = "Method for list UserProfileType.", 
        notes = "รายการประเภทผู้ใช้งานระบบ", 
        responseContainer = "List",
        response = UserProfileTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserProfileType list success." ),
        @ApiResponse( code = 404, message = "UserProfileType list not found in the database." ),
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
        responseData.put("message", "UserProfileType list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            List<UserProfileType> listUserProfileType = userProfileTypeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listUserProfileType.isEmpty()){
                ArrayList<UserProfileTypeModel> listUserProfileTypeModel = new ArrayList<>();
                for (UserProfileType userProfileType : listUserProfileType) {
                    listUserProfileTypeModel.add(userProfileTypeService.tranformToModel(userProfileType));
                }
                listUserProfileTypeModel.trimToSize();
                responseData.put("data", listUserProfileTypeModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
