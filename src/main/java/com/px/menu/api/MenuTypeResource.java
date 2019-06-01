package com.px.menu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.service.ModuleService;
import com.px.menu.entity.MenuType;
import com.px.menu.model.MenuTypeModel;
import com.px.menu.service.MenuTypeService;
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
@Api(value = "MenuType ประเภทเมนู")
@Path("v1/menu/menuType")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class MenuTypeResource {
    private static final Logger LOG = Logger.getLogger(MenuTypeResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create MenuType", 
        notes = "สร้างข้อมูลประเภทเมนู",
        response = MenuTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "MenuType created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        MenuTypeModel menuTypeModel
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
            MenuType menuType = new MenuType();
            menuType.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            menuType.setMenuTypeCode(menuTypeModel.getMenuTypeCode());
            menuType.setMenuTypeName(menuTypeModel.getMenuTypeName());
            
            ModuleService moduleService = new ModuleService();
            menuType.setModule(moduleService.getById(menuTypeModel.getModule().getId()));
            
            MenuTypeService menuTypeService = new MenuTypeService();
            menuType = menuTypeService.create(menuType);
            responseData.put("data", menuTypeService.tranformToModel(menuType));
            LOG.info("menuType Service : "+ gs.toJson(menuTypeService.tranformToModel(menuType)));
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
        value = "Method for get MenuType by id", 
        notes = "ขอข้อมูลประเภทเมนู ด้วย รหัสประเภทเมนู", 
        response = MenuTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuType by id success." ),
        @ApiResponse( code = 404, message = "MenuType by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสประเภทเมนู", required = true) 
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
        responseData.put("message", "MenuType by id not found in the database.");
        try{
            MenuTypeService menuTypeService = new MenuTypeService();
            MenuType menuType = menuTypeService.getById(id);
            if(menuType!=null){
                status = Response.Status.OK;
                responseData.put("data", menuTypeService.tranformToModel(menuType));
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
        value = "Method for update MenuType.", 
        notes = "แก้ไขข้อมูลประเภทเมนู", 
        response =MenuTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuType updeted by id success." )
        ,@ApiResponse( code = 404, message = "MenuType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสประเภทเมนู", required = true) 
        @PathParam("id") int id, 
        MenuTypeModel menuTypeModel
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
        responseData.put("message", "MenuType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuTypeService menuTypeService = new MenuTypeService();
            MenuType menuType = menuTypeService.getById(id);
            if(menuType!=null){
                menuType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                menuType.setMenuTypeCode(menuTypeModel.getMenuTypeCode());
                menuType.setMenuTypeName(menuTypeModel.getMenuTypeName());
                
                ModuleService moduleService = new ModuleService();
                menuType.setModule(moduleService.getById(menuTypeModel.getModule().getId()));
                
                menuType = menuTypeService.update(menuType);
                status = Response.Status.OK;
                responseData.put("data", menuTypeService.tranformToModel(menuType));
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
        value = "Method for delete MenuType by id.", 
        notes = "ลบข้อมูลประเภทเมนู", 
        response = MenuTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuType deleted by id success." )
        ,@ApiResponse( code = 404, message = "MenuType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสประเภทเมนู", required = true) 
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
        responseData.put("message", "MenuType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuTypeService menuTypeService = new MenuTypeService();
            MenuType menuType = menuTypeService.remove(id, userID);
            if(menuType!=null){
                status = Response.Status.OK;
                responseData.put("data", menuTypeService.tranformToModel(menuType));
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
        value = "Method for list MenuType.", 
        notes = "รายการประเภทเมนู", 
        responseContainer = "List",
        response = MenuTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "MenuType list success." ),
        @ApiResponse( code = 404, message = "MenuType list not found in the database." ),
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
        responseData.put("message", "MenuType list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuTypeService menuTypeService = new MenuTypeService();
            List<MenuType> listMenuType = menuTypeService.listAll(sort, dir);
            if(!listMenuType.isEmpty()){
                ArrayList<MenuTypeModel> listMenuTypeModel = new ArrayList<>();
                for (MenuType menuType : listMenuType) {
                    listMenuTypeModel.add(menuTypeService.tranformToModel(menuType));
                }
                listMenuTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listMenuTypeModel);
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
