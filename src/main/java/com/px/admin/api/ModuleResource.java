package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Module;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.model.ModuleModel;
import com.px.admin.service.ModuleService;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.authority.service.ModuleUserAuthService;
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
@Api(value = "Module module ระบบ")
@Path("v1/authority/modules")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class ModuleResource {
    private static final Logger LOG = Logger.getLogger(ModuleResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Module", 
        notes = "สร้างข้อมูลประเภทผู้ใช้งานระบบ",
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Module created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        ModuleModel moduleModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(moduleModel.getVersion())
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
            Module module = new Module();
            module.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            module.setModuleCode(moduleModel.getModuleCode());
            module.setModuleConfigInAdmin(moduleModel.getModuleConfigInAdmin());
            module.setModuleIcon(moduleModel.getModuleIcon());
            module.setModuleName(moduleModel.getModuleName());
            module.setModuleNameEng(moduleModel.getModuleNameEng());
            
            ModuleService moduleService = new ModuleService();
            module = moduleService.create(module);
            responseData.put("data", moduleService.tranformToModel(module));
            LOG.info("modudle Service : "+ gs.toJson(moduleService.tranformToModel(module)));
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
        value = "Method for get Module by id", 
        notes = "ขอข้อมูลประเภทผู้ใช้งานระบบ ด้วย รหัสประเภทผู้ใช้งานระบบ", 
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module by id success." ),
        @ApiResponse( code = 404, message = "Module by id not found in the database." ),
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
        LOG.info("getById...");
        LOG.info("id = "+id);
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
        responseData.put("message", "Module by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleService moduleService = new ModuleService();
            Module module = moduleService.getById(id);
            if(module!=null){
                responseData.put("data", moduleService.tranformToModel(module));
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
    
    @ApiOperation(
        value = "Method for update Module.", 
        notes = "แก้ไขข้อมูลประเภทผู้ใช้งานระบบ", 
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module updeted by id success." )
        ,@ApiResponse( code = 404, message = "Module by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสประเภทผู้ใช้งานระบบ", required = true) 
        @PathParam("id") int id, 
        ModuleModel moduleModel
    ) {
        LOG.info("update...");
        LOG.info("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(moduleModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Module by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleService moduleService = new ModuleService();
            Module module = moduleService.getById(id);
            if(module!=null){
                module.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                module.setModuleCode(moduleModel.getModuleCode());
                module.setModuleName(moduleModel.getModuleName());
                
                module = moduleService.update(module);
                responseData.put("data", moduleService.tranformToModel(module));
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

    @ApiOperation(
        value = "Method for delete Module by id.", 
        notes = "ลบข้อมูลประเภทผู้ใช้งานระบบ", 
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module deleted by id success." )
        ,@ApiResponse( code = 404, message = "Module by id not found in the database." )
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
        LOG.info("remove...");
        LOG.info("id = "+id);
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
        responseData.put("message", "Module by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleService moduleService = new ModuleService();
            Module module = moduleService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(module!=null){
                responseData.put("data", moduleService.tranformToModel(module));
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

    @ApiOperation(
        value = "Method for list Module.", 
        notes = "รายการประเภทผู้ใช้งานระบบ", 
        responseContainer = "List",
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module list success." ),
        @ApiResponse( code = 404, message = "Module list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response list(
        @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.info("list...");
        LOG.info("offset = "+listOptionModel.getOffset());
        LOG.info("limit = "+listOptionModel.getLimit());
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
        responseData.put("message", "Module list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleService moduleService = new ModuleService();
            List<Module> listModule = moduleService.listByUserType(Integer.parseInt(listOptionModel.getUserType()),listOptionModel.getSort(), listOptionModel.getDir());
            if(!listModule.isEmpty()){
                ArrayList<ModuleModel> listModuleModel = new ArrayList<>();
                for (Module module : listModule) {
                    listModuleModel.add(moduleService.tranformToModel(module));
                }
                listModuleModel.trimToSize();
                responseData.put("data", listModuleModel);
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
    
    @ApiOperation(
        value = "Method for list Module by structure authority.", 
        notes = "รายการระบบงานของแต่ละผู้ใช้งานระบบ ด้วย สิทธิของโครงสร้าง",
        responseContainer = "List",
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module list by structure authority success." ),
        @ApiResponse( code = 404, message = "Module list by structure authority not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/structure/{structureidList}/authority/{authority}")
    public Response listByStructureIdAuthority(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "structureidList", value = "รายการรหัสโครงสร้าง", required = true) 
        @PathParam("structureidList") String structureidList,
        @ApiParam(name = "authority", value = "ค่าของสิทธิ", required = true) 
        @PathParam("authority") String authority
    ) {
        LOG.info("listByStructureIdAuthority...");
        LOG.info("structureidList = "+structureidList);
        LOG.info("authority = "+authority);
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
        responseData.put("message", "List module by structure id list and authority not found in the database.");
        try{
            StructureService structureService = new StructureService();
            ArrayList<Structure> structureList = new ArrayList();
            for (String tmp: structureidList.split(",")){
                if(!tmp.isEmpty()){
                    Structure structure = structureService.getById(Integer.parseInt(tmp));
                    if(structure!=null){structureList.add(structure);}
                }
            }
            
            ModuleService moduleService = new ModuleService();
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            List<Module> listModule = moduleUserAuthService.getModuleByStructureAuthority(structureList,authority);

            if(!listModule.isEmpty()){
                ArrayList<ModuleModel> listModuleModel = new ArrayList<>();
                for (Module module : listModule) {
                    listModuleModel.add(moduleService.tranformToModel(module));
                }
                listModuleModel.trimToSize();
                responseData.put("data", listModuleModel);
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
    
    @ApiOperation(
        value = "Method for list Module by userProfile authority.", 
        notes = "รายการระบบงานของแต่ละผู้ใช้งานระบบ ด้วย สิทธิของประวัติผู้ใช้งานระบบ",
        responseContainer = "List",
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module list by userProfile authority success." ),
        @ApiResponse( code = 404, message = "Module list by userProfile authority not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/userProfile/{userProfileidList}/authority/{authority}")
    public Response listByUserProfileIdAuthority(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "userProfileidList", value = "รายการรหัสประวัติผู้ใช้งานระบบ", required = true) 
        @PathParam("userProfileidList") String userProfileidList,
        @ApiParam(name = "authority", value = "ค่าของสิทธิ", required = true) 
        @PathParam("authority") String authority
    ) {
        LOG.info("listByUserProfileIdAuthority...");
        LOG.info("userProfileidList = "+userProfileidList);
        LOG.info("authority = "+authority);
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
        responseData.put("message", "List module by userProfile id list and authority not found in the database.");
        try{
            UserProfileService userProfileService = new UserProfileService();
            ArrayList<UserProfile> userProfileList = new ArrayList();
            for (String tmp: userProfileidList.split(",")){
                if(!tmp.isEmpty()){
                    UserProfile userProfile = userProfileService.getById(Integer.parseInt(tmp));
                    if(userProfile!=null){userProfileList.add(userProfile);}
                }
            }
            
            ModuleService moduleService = new ModuleService();
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            List<Module> listModule = moduleUserAuthService.getModuleByUserProfileAuthority(userProfileList,authority);

            if(!listModule.isEmpty()){
                ArrayList<ModuleModel> listModuleModel = new ArrayList<>();
                for (Module module : listModule) {
                    listModuleModel.add(moduleService.tranformToModel(module));
                }
                listModuleModel.trimToSize();
                responseData.put("data", listModuleModel);
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
    
    @ApiOperation(
        value = "Method for list Module For Config.", 
        notes = "รายการ Module ส่วน Admin", 
        responseContainer = "List",
        response = ModuleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Module list For Config success." ),
        @ApiResponse( code = 404, message = "Module list For Config not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/configs")
    public Response listForConfig(
        @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.info("list...");
        LOG.info("offset = "+listOptionModel.getOffset());
        LOG.info("limit = "+listOptionModel.getLimit());
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
        responseData.put("message", "Module list For Config not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleService moduleService = new ModuleService();
            List<Module> listModule = moduleService.listAllForConfig(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listModule.isEmpty()){
                ArrayList<ModuleModel> listModuleModel = new ArrayList<>();
                for (Module module : listModule) {
                    listModuleModel.add(moduleService.tranformToModel(module));
                }
                listModuleModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listModuleModel);
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
