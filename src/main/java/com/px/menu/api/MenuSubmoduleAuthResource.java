package com.px.menu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.authority.service.SubmoduleAuthService;
import com.px.menu.entity.MenuSubmoduleAuth;
import com.px.menu.model.MenuSubmoduleAuthModel;
import com.px.menu.service.MenuService;
import com.px.menu.service.MenuSubmoduleAuthService;
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
@Api(value = "MenuSubmoduleAuth การควบคุมเมนูโดยสิทธิการใช้งาน")
@Path("v1/menu/menuSubmoduleAuth")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class MenuSubmoduleAuthResource {
    private static final Logger LOG = Logger.getLogger(MenuSubmoduleAuthResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create MenuSubmoduleAuth", 
        notes = "สร้างข้อมูลควบคุมเมนูโดยสิทธิการใช้งาน",
        response = MenuSubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "MenuSubmoduleAuth created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        MenuSubmoduleAuthModel menuSubmoduleAuthModel
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
            MenuSubmoduleAuth menuSubmoduleAuth = new MenuSubmoduleAuth();
            menuSubmoduleAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            
            MenuService menuService = new MenuService();
            menuSubmoduleAuth.setMenu(menuService.getById(menuSubmoduleAuthModel.getMenu().getId()));
            
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            menuSubmoduleAuth.setSubmoduleAuth(submoduleAuthService.getById(menuSubmoduleAuthModel.getSubmoduleAuth().getId()));
            
            MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
            menuSubmoduleAuth = menuSubmoduleAuthService.create(menuSubmoduleAuth);
            responseData.put("data", menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth));
            LOG.info("menuSubmoduleAuth Service : "+ gs.toJson(menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth)));
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
        value = "Method for get MenuSubmoduleAuth by id", 
        notes = "ขอข้อมูลควบคุมเมนูโดยสิทธิการใช้งาน ด้วย รหัสควบคุมเมนูโดยสิทธิการใช้งาน", 
        response = MenuSubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuSubmoduleAuth by id success." ),
        @ApiResponse( code = 404, message = "MenuSubmoduleAuth by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสควบคุมเมนูโดยสิทธิการใช้งาน", required = true) 
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
        responseData.put("message", "MenuSubmoduleAuth by id not found in the database.");
        try{
            MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
            MenuSubmoduleAuth menuSubmoduleAuth = menuSubmoduleAuthService.getById(id);
            if(menuSubmoduleAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth));
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
        value = "Method for update MenuSubmoduleAuth.", 
        notes = "แก้ไขข้อมูลควบคุมเมนูโดยสิทธิการใช้งาน", 
        response =MenuSubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuSubmoduleAuth updeted by id success." )
        ,@ApiResponse( code = 404, message = "MenuSubmoduleAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสควบคุมเมนูโดยสิทธิการใช้งาน", required = true) 
        @PathParam("id") int id, 
        MenuSubmoduleAuthModel menuSubmoduleAuthModel
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
        responseData.put("message", "MenuSubmoduleAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
            MenuSubmoduleAuth menuSubmoduleAuth = menuSubmoduleAuthService.getById(id);
            if(menuSubmoduleAuth!=null){
                menuSubmoduleAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                
                MenuService menuService = new MenuService();
                menuSubmoduleAuth.setMenu(menuService.getById(menuSubmoduleAuthModel.getMenu().getId()));
                
                SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
                menuSubmoduleAuth.setSubmoduleAuth(submoduleAuthService.getById(menuSubmoduleAuthModel.getSubmoduleAuth().getId()));
                
                menuSubmoduleAuth = menuSubmoduleAuthService.update(menuSubmoduleAuth);
                status = Response.Status.OK;
                responseData.put("data", menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth));
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
        value = "Method for delete MenuSubmoduleAuth by id.", 
        notes = "ลบข้อมูลควบคุมเมนูโดยสิทธิการใช้งาน", 
        response = MenuSubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuSubmoduleAuth deleted by id success." )
        ,@ApiResponse( code = 404, message = "MenuSubmoduleAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสควบคุมเมนูโดยสิทธิการใช้งาน", required = true) 
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
        responseData.put("message", "MenuSubmoduleAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
            MenuSubmoduleAuth menuSubmoduleAuth = menuSubmoduleAuthService.remove(id, userID);
            if(menuSubmoduleAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth));
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
        value = "Method for list MenuSubmoduleAuth.", 
        notes = "รายการควบคุมเมนูโดยสิทธิการใช้งาน", 
        responseContainer = "List",
        response = MenuSubmoduleAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuSubmoduleAuth list success." ),
        @ApiResponse( code = 404, message = "MenuSubmoduleAuth list not found in the database." ),
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
        responseData.put("message", "MenuSubmoduleAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuSubmoduleAuthService menuSubmoduleAuthService = new MenuSubmoduleAuthService();
            List<MenuSubmoduleAuth> listMenuSubmoduleAuth = menuSubmoduleAuthService.listAll(sort, dir);
            if(!listMenuSubmoduleAuth.isEmpty()){
                ArrayList<MenuSubmoduleAuthModel> listMenuSubmoduleAuthModel = new ArrayList<>();
                for (MenuSubmoduleAuth menuSubmoduleAuth : listMenuSubmoduleAuth) {
                    listMenuSubmoduleAuthModel.add(menuSubmoduleAuthService.tranformToModel(menuSubmoduleAuth));
                }
                listMenuSubmoduleAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listMenuSubmoduleAuthModel);
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
