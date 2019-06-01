package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Submodule;
import com.px.admin.model.SubmoduleModel;
import com.px.admin.service.ModuleService;
import com.px.admin.service.SubmoduleService;
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
@Api(value = "Submodule ระบบงานย่อย")
@Path("v1/authority/submodules")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SubmoduleResource {
    private static final Logger LOG = Logger.getLogger(SubmoduleResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Submodule", 
        notes = "สร้างข้อมูลระบบงานย่อย",
        response = SubmoduleResource.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Submodule created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        SubmoduleModel submoduleModel
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
            Submodule submodule = new Submodule();
            submodule.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            submodule.setSubmoduleCode(submoduleModel.getSubmoduleCode());
            submodule.setSubmoduleName(submoduleModel.getSubmoduleName());
            
            ModuleService moduleService = new ModuleService();
            submodule.setModule(moduleService.getById(submoduleModel.getModule().getId()));
            
            SubmoduleService submoduleService = new SubmoduleService();
            submodule = submoduleService.create(submodule);
            responseData.put("data", submoduleService.tranformToModel(submodule));
            LOG.info("submodule Service : "+ gs.toJson(submoduleService.tranformToModel(submodule)));
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
        value = "Method for get Submodule by id", 
        notes = "ขอข้อมูลระบบงานย่อย ด้วย รหัสระบบงานย่อย", 
        response = SubmoduleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Submodule by id success." ),
        @ApiResponse( code = 404, message = "Submodule by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสระบบงานย่อย", required = true) 
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
        responseData.put("message", "Submodule by id not found in the database.");
        try{
            SubmoduleService submoduleService = new SubmoduleService();
            Submodule submodule = submoduleService.getById(id);
            if(submodule!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleService.tranformToModel(submodule));
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
        value = "Method for update Submodule.", 
        notes = "แก้ไขข้อมูลระบบงานย่อย", 
        response =SubmoduleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Submodule updeted by id success." )
        ,@ApiResponse( code = 404, message = "Submodule by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสระบบงานย่อย", required = true) 
        @PathParam("id") int id, 
        SubmoduleModel submoduleModel
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
        responseData.put("message", "Submodule by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleService submoduleService = new SubmoduleService();
            Submodule submodule = submoduleService.getById(id);
            if(submodule!=null){
                submodule.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submodule.setSubmoduleCode(submoduleModel.getSubmoduleCode());
                submodule.setSubmoduleName(submoduleModel.getSubmoduleName());
                
                ModuleService moduleService = new ModuleService();
                submodule.setModule(moduleService.getById(submoduleModel.getModule().getId()));
                
                submodule = submoduleService.update(submodule);
                status = Response.Status.OK;
                responseData.put("data", submoduleService.tranformToModel(submodule));
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
        value = "Method for delete Submodule by id.", 
        notes = "ลบข้อมูลระบบงานย่อย", 
        response = SubmoduleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Submodule deleted by id success." )
        ,@ApiResponse( code = 404, message = "Submodule by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสระบบงานย่อย", required = true) 
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
        responseData.put("message", "Submodule by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleService submoduleService = new SubmoduleService();
            Submodule submodule = submoduleService.remove(id, userID);
            if(submodule!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleService.tranformToModel(submodule));
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
        value = "Method for list Submodule.", 
        notes = "รายการระบบงานย่อย", 
        responseContainer = "List",
        response = SubmoduleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Submodule list success." ),
        @ApiResponse( code = 404, message = "Submodule list not found in the database." ),
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
        responseData.put("message", "Submodule list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleService submoduleService = new SubmoduleService();
            List<Submodule> listSubmodule = submoduleService.listAll(sort, dir);
            if(!listSubmodule.isEmpty()){
                ArrayList<SubmoduleModel> listSubmoduleModel = new ArrayList<>();
                for (Submodule submodule : listSubmodule) {
                    listSubmoduleModel.add(submoduleService.tranformToModel(submodule));
                }
                listSubmoduleModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listSubmoduleModel);
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
