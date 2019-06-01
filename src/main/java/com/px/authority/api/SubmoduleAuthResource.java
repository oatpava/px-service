package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Submodule;
import com.px.admin.service.SubmoduleService;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.model.SubmoduleAuthModel;
import com.px.authority.service.AuthService;
import com.px.authority.service.SubmoduleAuthService;
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
@Api(value = "SubmoduleAuth สิทธิการใช้ระบบงานย่อย")
@Path("v1/authority/submoduleAuths")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SubmoduleAuthResource {
    private static final Logger LOG = Logger.getLogger(SubmoduleAuthResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create SubmoduleAuth", 
        notes = "สร้างข้อมูลสิทธิการใช้ระบบงานย่อย",
        response = SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "SubmoduleAuth created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        SubmoduleAuthModel submoduleAuthModel
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
            SubmoduleAuth submoduleAuth = new SubmoduleAuth();
            submoduleAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            submoduleAuth.setSubmoduleAuthCode(submoduleAuthModel.getSubmoduleAuthCode());
            
            SubmoduleService submoduleService = new SubmoduleService();
            submoduleAuth.setSubmodule(submoduleService.getById(submoduleAuthModel.getSubmodule().getId()));
            AuthService authService = new AuthService();
            submoduleAuth.setAuth(authService.getById(submoduleAuthModel.getAuth().getId()));
            
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            submoduleAuth = submoduleAuthService.create(submoduleAuth);
            responseData.put("data", submoduleAuthService.tranformToModel(submoduleAuth));
            LOG.info("submoduleAuth Service : "+ gs.toJson(submoduleAuthService.tranformToModel(submoduleAuth)));
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
        value = "Method for get SubmoduleAuth by id", 
        notes = "ขอข้อมูลสิทธิการใช้ระบบงานย่อย ด้วย รหัสสิทธิการใช้ระบบงานย่อย", 
        response = SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuth by id success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuth by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อย", required = true) 
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
        responseData.put("message", "SubmoduleAuth by id not found in the database.");
        try{
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            SubmoduleAuth submoduleAuth = submoduleAuthService.getById(id);
            if(submoduleAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthService.tranformToModel(submoduleAuth));
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
        value = "Method for update SubmoduleAuth.", 
        notes = "แก้ไขข้อมูลสิทธิการใช้ระบบงานย่อย", 
        response =SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuth updeted by id success." )
        ,@ApiResponse( code = 404, message = "SubmoduleAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อย", required = true) 
        @PathParam("id") int id, 
        SubmoduleAuthModel submoduleAuthModel
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
        responseData.put("message", "SubmoduleAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            SubmoduleAuth submoduleAuth = submoduleAuthService.getById(id);
            if(submoduleAuth!=null){
                submoduleAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleAuth.setSubmoduleAuthCode(submoduleAuthModel.getSubmoduleAuthCode());
                
                SubmoduleService submoduleService = new SubmoduleService();
                submoduleAuth.setSubmodule(submoduleService.getById(submoduleAuthModel.getSubmodule().getId()));
                AuthService authService = new AuthService();
                submoduleAuth.setAuth(authService.getById(submoduleAuthModel.getAuth().getId()));
                
                submoduleAuth = submoduleAuthService.update(submoduleAuth);
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthService.tranformToModel(submoduleAuth));
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
        value = "Method for delete SubmoduleAuth by id.", 
        notes = "ลบข้อมูลสิทธิการใช้ระบบงานย่อย", 
        response = SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuth deleted by id success." )
        ,@ApiResponse( code = 404, message = "SubmoduleAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อย", required = true) 
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
        responseData.put("message", "SubmoduleAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            SubmoduleAuth submoduleAuth = submoduleAuthService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(submoduleAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthService.tranformToModel(submoduleAuth));
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
        value = "Method for list SubmoduleAuth.", 
        notes = "รายการสิทธิการใช้ระบบงานย่อย", 
        responseContainer = "List",
        response = SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuth list success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuth list not found in the database." ),
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
        responseData.put("message", "SubmoduleAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            List<SubmoduleAuth> listSubmoduleAuth = submoduleAuthService.listAll(sort, dir);
            if(!listSubmoduleAuth.isEmpty()){
                ArrayList<SubmoduleAuthModel> listSubmoduleAuthModel = new ArrayList<>();
                for (SubmoduleAuth submoduleAuth : listSubmoduleAuth) {
                    listSubmoduleAuthModel.add(submoduleAuthService.tranformToModel(submoduleAuth));
                }
                listSubmoduleAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listSubmoduleAuthModel);
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
        value = "Method for get list SubmoduleAuth by submodule id", 
        notes = "ขอข้อมูลรายการสิทธิการใช้ระบบงานย่อย ด้วย รหัสระบบงานย่อย", 
        responseContainer = "List",
        response = SubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuth list by submodule id success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuth list by submodule id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/submodule/{submoduleid}")
    public Response listBySubmoduleId(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "submoduleid", value = "รหัสระบบงานย่อย", required = true) 
        @PathParam("submoduleid") int submoduleid,
        @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false) 
        @DefaultValue("orderNo") @QueryParam("sort") String sort,
        @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false) 
        @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        LOG.info("listBySubmoduleId...");
        LOG.info("submoduleid = "+submoduleid);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "List submoduleAuth by submodule id not found in the database.");
        try{SubmoduleService submoduleService = new SubmoduleService();
            Submodule submodule = submoduleService.getById(submoduleid);
            if(submodule!=null){
                SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
                List<SubmoduleAuth> listSubmoduleAuth = submoduleAuthService.listBySubmodule(submodule, sort, dir);
                if(!listSubmoduleAuth.isEmpty()){
                    ArrayList<SubmoduleAuthModel> listSubmoduleAuthModel = new ArrayList<>();
                    for (SubmoduleAuth submoduleAuth : listSubmoduleAuth) {
                        listSubmoduleAuthModel.add(submoduleAuthService.tranformToModel(submoduleAuth));
                    }
                    listSubmoduleAuthModel.trimToSize();
                    status = Response.Status.OK;
                    responseData.put("data", listSubmoduleAuthModel);
                    responseData.put("message", "");
                }
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
