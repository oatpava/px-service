package com.px.menu.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.menu.entity.Menu;
import com.px.menu.model.MenuModel;
import com.px.menu.service.MenuService;
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
@Api(value = "Menu เมนู")
@Path("v1/menu/menu")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class MenuResource {
    private static final Logger LOG = Logger.getLogger(MenuResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Menu", 
        notes = "สร้างข้อมูลเมนู",
        response = MenuModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Menu created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        MenuModel menuModel
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
            Menu menu = new Menu();
            menu.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            menu.setMenuCode(menuModel.getMenuCode());
            menu.setMenuName(menuModel.getMenuName());
            menu.setMenuEngName(menuModel.getMenuEngName());
            menu.setMenuPicture(menuModel.getMenuPicture());
            menu.setMenuDetail(menuModel.getMenuDetail());
            menu.setMenuFunction(menuModel.getMenuFunction());
            menu.setMenuDisableShow(menuModel.getMenuDisableShow());
            menu.setMenuDisableCSS(menuModel.getMenuDisableCSS());
            menu.setParentId(menuModel.getParentId());
            menu.setParentKey(menuModel.getParentKey());
            menu.setNodeLevel(menuModel.getNodeLevel());
            
            MenuTypeService menuTypeService = new MenuTypeService();
            menu.setMenuType(menuTypeService.getById(menuModel.getMenuType().getId()));
            
            MenuService menuService = new MenuService();
            menu = menuService.create(menu);
            responseData.put("data", menuService.tranformToModel(menu));
            LOG.info("menu Service : "+ gs.toJson(menuService.tranformToModel(menu)));
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
        value = "Method for get Menu by id", 
        notes = "ขอข้อมูลเมนู ด้วย รหัสเมนู", 
        response = MenuModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Menu by id success." ),
        @ApiResponse( code = 404, message = "Menu by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสเมนู", required = true) 
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
        responseData.put("message", "Menu by id not found in the database.");
        try{
            MenuService menuService = new MenuService();
            Menu menu = menuService.getById(id);
            if(menu!=null){
                status = Response.Status.OK;
                responseData.put("data", menuService.tranformToModel(menu));
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
        value = "Method for update Menu.", 
        notes = "แก้ไขข้อมูลเมนู", 
        response =MenuModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Menu updeted by id success." )
        ,@ApiResponse( code = 404, message = "Menu by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสเมนู", required = true) 
        @PathParam("id") int id, 
        MenuModel menuModel
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
        responseData.put("message", "Menu by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuService menuService = new MenuService();
            Menu menu = menuService.getById(id);
            if(menu!=null){
                menu.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                menu.setMenuCode(menuModel.getMenuCode());
                menu.setMenuName(menuModel.getMenuName());
                menu.setMenuEngName(menuModel.getMenuEngName());
                menu.setMenuPicture(menuModel.getMenuPicture());
                menu.setMenuDetail(menuModel.getMenuDetail());
                menu.setMenuFunction(menuModel.getMenuFunction());
                menu.setMenuDisableShow(menuModel.getMenuDisableShow());
                menu.setMenuDisableCSS(menuModel.getMenuDisableCSS());
                menu.setParentId(menuModel.getParentId());
                menu.setParentKey(menuModel.getParentKey());
                menu.setNodeLevel(menuModel.getNodeLevel());
                
                MenuTypeService menuTypeService = new MenuTypeService();
                menu.setMenuType(menuTypeService.getById(menuModel.getMenuType().getId()));
                
                menu = menuService.update(menu);
                status = Response.Status.OK;
                responseData.put("data", menuService.tranformToModel(menu));
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
        value = "Method for delete Menu by id.", 
        notes = "ลบข้อมูลเมนู", 
        response = MenuModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Menu deleted by id success." )
        ,@ApiResponse( code = 404, message = "Menu by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสเมนู", required = true) 
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
        responseData.put("message", "Menu by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuService menuService = new MenuService();
            Menu menu = menuService.remove(id, userID);
            if(menu!=null){
                status = Response.Status.OK;
                responseData.put("data", menuService.tranformToModel(menu));
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
        value = "Method for list Menu.", 
        notes = "รายการเมนู", 
        responseContainer = "List",
        response = MenuModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Menu list success." ),
        @ApiResponse( code = 404, message = "Menu list not found in the database." ),
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
        responseData.put("message", "Menu list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MenuService menuService = new MenuService();
            List<Menu> listMenu = menuService.listAll(sort, dir);
            if(!listMenu.isEmpty()){
                ArrayList<MenuModel> listMenuModel = new ArrayList<>();
                for (Menu menu : listMenu) {
                    listMenuModel.add(menuService.tranformToModel(menu));
                }
                listMenuModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listMenuModel);
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
