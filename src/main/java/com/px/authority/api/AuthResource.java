package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.authority.entity.Auth;
import com.px.authority.model.AuthModel;
import com.px.authority.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
 * @author Peach
 */
@Api(value = "Auth สิทธิ")
@Path("v1/authority/auths")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class AuthResource {
    private static final Logger LOG = Logger.getLogger(AuthResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Auth", 
        notes = "สร้างข้อมูลสิทธิ",
        response = AuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Auth created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        AuthModel authModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
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
            Auth auth = new Auth();
            auth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            auth.setAuthName(authModel.getAuthName());
            
            AuthService authService = new AuthService();
            auth = authService.create(auth);
            responseData.put("data", authService.tranformToModel(auth));
            LOG.info("auth Service : "+ gs.toJson(authService.tranformToModel(auth)));
            responseData.put("message", "");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get Auth by id", 
        notes = "ขอข้อมูลสิทธิ ด้วย รหัสสิทธิ", 
        response = AuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Auth by id success." ),
        @ApiResponse( code = 404, message = "Auth by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสสิทธิ", required = true) 
        @PathParam("id") int id
    ) {
        LOG.info("getById...");
        LOG.info("id = "+id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Auth by id not found in the database.");
        try{
            AuthService authService = new AuthService();
            Auth auth = authService.getById(id);
            if(auth!=null){
                status = Response.Status.OK;
                responseData.put("data", authService.tranformToModel(auth));
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
        value = "Method for update Auth.", 
        notes = "แก้ไขข้อมูลสิทธิ", 
        response = AuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Auth updeted by id success." )
        ,@ApiResponse( code = 404, message = "Auth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสสิทธิ", required = true) 
        @PathParam("id") int id, 
        AuthModel authModel
    ) {
        LOG.info("update...");
        LOG.info("id = "+id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Auth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AuthService authService = new AuthService();
            Auth auth = authService.getById(id);
            if(auth!=null){
                auth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                auth.setAuthName(authModel.getAuthName());
                
                auth = authService.update(auth);
                status = Response.Status.OK;
                responseData.put("data", authService.tranformToModel(auth));
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
        value = "Method for delete Auth by id.", 
        notes = "ลบข้อมูลสิทธิ", 
        response = AuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Auth deleted by id success." )
        ,@ApiResponse( code = 404, message = "Auth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสสิทธิ", required = true) 
        @PathParam("id") int id
    ) {
        LOG.info("remove...");
        LOG.info("id = "+id);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Auth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AuthService authService = new AuthService();
            Auth auth = authService.remove(id, userID);
            if(auth!=null){
                status = Response.Status.OK;
                responseData.put("data", authService.tranformToModel(auth));
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
        value = "Method for list Auth.", 
        notes = "รายการสิทธิ", 
        responseContainer = "List",
        response = AuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Auth list success." ),
        @ApiResponse( code = 404, message = "Auth list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response list(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true) 
        @DefaultValue("0") @QueryParam("offset") int offset,
        @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true) 
        @DefaultValue("20") @QueryParam("limit") int limit,
        @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false) 
        @DefaultValue("orderNo") @QueryParam("sort") String sort,
        @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false) 
        @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        LOG.info("list...");
        LOG.info("offset = "+offset);
        LOG.info("limit = "+limit);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Auth list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AuthService authService = new AuthService();
            List<Auth> listAuth = authService.listAll(sort, dir);
            if(!listAuth.isEmpty()){
                ArrayList<AuthModel> listAuthModel = new ArrayList<>();
                for (Auth auth : listAuth) {
                    listAuthModel.add(authService.tranformToModel(auth));
                }
                listAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listAuthModel);
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
