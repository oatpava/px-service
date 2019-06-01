package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.UserTypeOrder;
import com.px.admin.model.UserTypeOrderModel;
import com.px.admin.service.UserTypeOrderService;
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
@Api(value = "UserTypeOrder ประเภทผู้ใช้งานระบบสำหรับจัดลำดับ")
@Path("v1/userTypeOrders")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class UserTypeOrderResource {
    private static final Logger LOG = Logger.getLogger(UserTypeOrderResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create UserTypeOrder", 
        notes = "สร้างข้อมูลประเภทผู้ใช้งานระบบสำหรับจัดลำดับ",
        response = UserTypeOrderModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "UserTypeOrder created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        UserTypeOrderModel userTypeOrderModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userTypeOrderModel.getVersion())
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
            UserTypeOrder userTypeOrder = new UserTypeOrder(userTypeOrderModel.getName(),Integer.parseInt(httpHeaders.getHeaderString("userID")));
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            userTypeOrder = userTypeOrderService.create(userTypeOrder);
            responseData.put("message", "UserTypeOrder created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get UserTypeOrder by id", 
        notes = "ขอข้อมูลประเภทผู้ใช้งานระบบสำหรับจัดลำดับ ด้วย รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", 
        response = UserTypeOrderModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserTypeOrder by id success." ),
        @ApiResponse( code = 404, message = "UserTypeOrder by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", required = true) 
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
        responseData.put("message", "UserTypeOrder by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            UserTypeOrder userTypeOrder = userTypeOrderService.getById(id);
            if(userTypeOrder!=null){
                status = Response.Status.OK;
                responseData.put("data", userTypeOrderService.tranformToModel(userTypeOrder));
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
        value = "Method for update UserTypeOrder.", 
        notes = "แก้ไขข้อมูลประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", 
        response = UserTypeOrderModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserTypeOrder updeted by id success." )
        ,@ApiResponse( code = 404, message = "UserTypeOrder by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", required = true) 
        @PathParam("id") int id, 
        UserTypeOrderModel userTypeOrderModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(userTypeOrderModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserTypeOrder by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            UserTypeOrder userTypeOrder = userTypeOrderService.getById(id);
            if(userTypeOrder!=null){
                userTypeOrder.setUserTypeOrderName(userTypeOrderModel.getName());
                userTypeOrder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userTypeOrder = userTypeOrderService.update(userTypeOrder);
                status = Response.Status.OK;
                responseData.put("data", userTypeOrderService.tranformToModel(userTypeOrder));
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
        value = "Method for delete UserTypeOrder by id.", 
        notes = "ลบข้อมูลประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", 
        response = UserTypeOrderModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserTypeOrder deleted by id success." )
        ,@ApiResponse( code = 404, message = "UserTypeOrder by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", required = true) 
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
        responseData.put("message", "UserTypeOrder by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            UserTypeOrder userTypeOrder = userTypeOrderService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(userTypeOrder!=null){
                status = Response.Status.OK;
                responseData.put("data", userTypeOrderService.tranformToModel(userTypeOrder));
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
        value = "Method for list UserTypeOrder.", 
        notes = "รายการประเภทผู้ใช้งานระบบสำหรับจัดลำดับ", 
        responseContainer = "List",
        response = UserTypeOrderModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "UserTypeOrder list success." ),
        @ApiResponse( code = 404, message = "UserTypeOrder list not found in the database." ),
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
        responseData.put("message", "UserTypeOrder list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            List<UserTypeOrder> listUserTypeOrder = userTypeOrderService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listUserTypeOrder.isEmpty()){
                ArrayList<UserTypeOrderModel> listUserTypeOrderModel = new ArrayList<>();
                for (UserTypeOrder userTypeOrder : listUserTypeOrder) {
                    listUserTypeOrderModel.add(userTypeOrderService.tranformToModel(userTypeOrder));
                }
                listUserTypeOrderModel.trimToSize();
                responseData.put("data", listUserTypeOrderModel);
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
