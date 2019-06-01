package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.ModuleService;
import com.px.admin.service.StructureService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.ModuleUserAuth;
import com.px.authority.model.ModuleUserAuthModel;
import com.px.authority.service.ModuleUserAuthService;
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
@Api(value = "ModuleUserAuth สิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ")
@Path("v1/authority/moduleUserAuths")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class ModuleUserAuthResource {
    private static final Logger LOG = Logger.getLogger(ModuleUserAuthResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create ModuleUserAuth", 
        notes = "สร้างข้อมูลสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ",
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "ModuleUserAuth created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        ModuleUserAuthModel moduleUserAuthModel
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
            ModuleUserAuth moduleUserAuth = new ModuleUserAuth();
            moduleUserAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            moduleUserAuth.setAuthority(moduleUserAuthModel.getAuthority());
            
            StructureService structureService = new StructureService();
            //moduleUserAuth.setStructure(structureService.getById(moduleUserAuthModel.getStructureId()));
            moduleUserAuth.setStructure(structureService.getByIdNotRemoved(moduleUserAuthModel.getStructure().getId()));
            UserProfileService userProfileService = new UserProfileService();
            //moduleUserAuth.setUserProfile(userProfileService.getById(moduleUserAuthModel.getUserProfileId()));
            moduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(moduleUserAuthModel.getUserProfile().getId()));
            ModuleService moduleService = new ModuleService();
            moduleUserAuth.setModule(moduleService.getById(moduleUserAuthModel.getModule().getId()));
            
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            moduleUserAuth = moduleUserAuthService.create(moduleUserAuth);
            responseData.put("data", moduleUserAuthService.tranformToModel(moduleUserAuth));
            LOG.info("moduleUserAuth Service : "+ gs.toJson(moduleUserAuthService.tranformToModel(moduleUserAuth)));
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
        value = "Method for get ModuleUserAuth by id", 
        notes = "ขอข้อมูลสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ ด้วย รหัสสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", 
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth by id success." ),
        @ApiResponse( code = 404, message = "ModuleUserAuth by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", required = true) 
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
        responseData.put("message", "ModuleUserAuth by id not found in the database.");
        try{
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            ModuleUserAuth moduleUserAuth = moduleUserAuthService.getById(id);
            if(moduleUserAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", moduleUserAuthService.tranformToModel(moduleUserAuth));
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
        value = "Method for update ModuleUserAuth.", 
        notes = "แก้ไขข้อมูลสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", 
        response =ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth updeted by id success." )
        ,@ApiResponse( code = 404, message = "ModuleUserAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", required = true) 
        @PathParam("id") int id, 
        ModuleUserAuthModel moduleUserAuthModel
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
        responseData.put("message", "ModuleUserAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            ModuleUserAuth moduleUserAuth = moduleUserAuthService.getById(id);
            if(moduleUserAuth!=null){
                moduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                moduleUserAuth.setAuthority(moduleUserAuthModel.getAuthority());
                
                StructureService structureService = new StructureService();
                //moduleUserAuth.setStructure(structureService.getById(moduleUserAuthModel.getStructureId()));
                moduleUserAuth.setStructure(structureService.getByIdNotRemoved(moduleUserAuthModel.getStructure().getId()));
                UserProfileService userProfileService = new UserProfileService();
                //moduleUserAuth.setUserProfile(userProfileService.getById(moduleUserAuthModel.getUserProfileId()));
                moduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(moduleUserAuthModel.getUserProfile().getId()));
                ModuleService moduleService = new ModuleService();
                moduleUserAuth.setModule(moduleService.getById(moduleUserAuthModel.getModule().getId()));
                
                moduleUserAuth = moduleUserAuthService.update(moduleUserAuth);
                status = Response.Status.OK;
                responseData.put("data", moduleUserAuthService.tranformToModel(moduleUserAuth));
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
        value = "Method for delete ModuleUserAuth by id.", 
        notes = "ลบข้อมูลสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", 
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth deleted by id success." )
        ,@ApiResponse( code = 404, message = "ModuleUserAuth by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", required = true) 
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
        responseData.put("message", "ModuleUserAuth by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            ModuleUserAuth moduleUserAuth = moduleUserAuthService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(moduleUserAuth!=null){
                status = Response.Status.OK;
                responseData.put("data", moduleUserAuthService.tranformToModel(moduleUserAuth));
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
        value = "Method for list ModuleUserAuth.", 
        notes = "รายการสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ", 
        responseContainer = "List",
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth list success." ),
        @ApiResponse( code = 404, message = "ModuleUserAuth list not found in the database." ),
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
        responseData.put("message", "ModuleUserAuth list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            List<ModuleUserAuth> listModuleUserAuth = moduleUserAuthService.listAll(sort, dir);
            if(!listModuleUserAuth.isEmpty()){
                ArrayList<ModuleUserAuthModel> listModuleUserAuthModel = new ArrayList<>();
                for (ModuleUserAuth moduleUserAuth : listModuleUserAuth) {
                    listModuleUserAuthModel.add(moduleUserAuthService.tranformToModel(moduleUserAuth));
                }
                listModuleUserAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listModuleUserAuthModel);
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
        value = "Method for list ModuleUserAuth by structure.", 
        notes = "รายการสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ ด้วย โครงสร้าง",
        responseContainer = "List",
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth list by structure id success." ),
        @ApiResponse( code = 404, message = "ModuleUserAuth list by structure id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/structure/{structureidList}")
    public Response listByStructureId(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "structureidList", value = "รายการรหัสโครงสร้าง", required = true) 
        @PathParam("structureidList") String structureidList
    ) {
        LOG.info("listByStructureId...");
        LOG.info("structureidList = "+structureidList);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "List moduleUserAuth by structure id list not found in the database.");
        try{
            StructureService structureService = new StructureService();
            ArrayList<Structure> structureList = new ArrayList();
            for (String tmp: structureidList.split(",")){
                if(!tmp.isEmpty()){
                    Structure structure = structureService.getById(Integer.parseInt(tmp));
                    if(structure!=null){structureList.add(structure);}
                }
            }

            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            List<ModuleUserAuth> listModuleUserAuth = moduleUserAuthService.getAuthorityByStructure(structureList);

            if(!listModuleUserAuth.isEmpty()){
                ArrayList<ModuleUserAuthModel> listModuleUserAuthModel = new ArrayList<>();
                for (ModuleUserAuth moduleUserAuth : listModuleUserAuth) {
                    listModuleUserAuthModel.add(moduleUserAuthService.tranformToModel(moduleUserAuth));
                }
                listModuleUserAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listModuleUserAuthModel);
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
        value = "Method for list ModuleUserAuth by userProfile.", 
        notes = "รายการสิทธิการใช้ระบบงานของแต่ละผู้ใช้งานระบบ ด้วย ประวัติผู้ใช้งานระบบ",
        responseContainer = "List",
        response = ModuleUserAuthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "ModuleUserAuth list by userProfile id success." ),
        @ApiResponse( code = 404, message = "ModuleUserAuth list by userProfile id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/userProfile/{userProfileidList}")
    public Response listByUserProfileId(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "userProfileidList", value = "รายการรหัสประวัติผู้ใช้งานระบบ", required = true) 
        @PathParam("userProfileidList") String userProfileidList
    ) {
        LOG.info("listByUserProfileId...");
        LOG.info("userProfileidList = "+userProfileidList);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "List moduleUserAuth by userProfile id list not found in the database.");
        try{
            UserProfileService userProfileService = new UserProfileService();
            ArrayList<UserProfile> userProfileList = new ArrayList();
            for (String tmp: userProfileidList.split(",")){
                if(!tmp.isEmpty()){
                    UserProfile userProfile = userProfileService.getById(Integer.parseInt(tmp));
                    if(userProfile!=null){userProfileList.add(userProfile);}
                }
            }

            ModuleUserAuthService moduleUserAuthService = new ModuleUserAuthService();
            List<ModuleUserAuth> listModuleUserAuth = moduleUserAuthService.getAuthorityByUserProfile(userProfileList);

            if(!listModuleUserAuth.isEmpty()){
                ArrayList<ModuleUserAuthModel> listModuleUserAuthModel = new ArrayList<>();
                for (ModuleUserAuth moduleUserAuth : listModuleUserAuth) {
                    listModuleUserAuthModel.add(moduleUserAuthService.tranformToModel(moduleUserAuth));
                }
                listModuleUserAuthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listModuleUserAuthModel);
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
