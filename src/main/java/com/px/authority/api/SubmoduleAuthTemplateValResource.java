package com.px.authority.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleAuthTemplateVal;
import com.px.authority.model.SubmoduleAuthTemplateValModel;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleAuthTemplateService;
import com.px.authority.service.SubmoduleAuthTemplateValService;
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
@Api(value = "SubmoduleAuthTemplateVal สิทธิของรูปแบบการใช้งานระบบงานย่อย")
@Path("v1/authority/submoduleAuthTemplateVals")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class SubmoduleAuthTemplateValResource {
    private static final Logger LOG = Logger.getLogger(SubmoduleAuthTemplateValResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create SubmoduleAuthTemplateVal", 
        notes = "สร้างข้อมูลสิทธิของรูปแบบการใช้งานระบบงานย่อย",
        response = SubmoduleAuthTemplateValResource.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "SubmoduleAuthTemplateVal created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        SubmoduleAuthTemplateValModel submoduleAuthTemplateValModel
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
            SubmoduleAuthTemplateVal submoduleAuthTemplateVal = new SubmoduleAuthTemplateVal();
            submoduleAuthTemplateVal.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            submoduleAuthTemplateVal.setLinkId(submoduleAuthTemplateValModel.getLinkId());
            submoduleAuthTemplateVal.setAuthority(submoduleAuthTemplateValModel.getAuthority());
            
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            submoduleAuthTemplateVal.setSubmoduleAuthTemplate(submoduleAuthTemplateService.getById(submoduleAuthTemplateValModel.getSubmoduleAuthTemplate().getId()));
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            submoduleAuthTemplateVal.setSubmoduleAuth(submoduleAuthService.getById(submoduleAuthTemplateValModel.getSubmoduleAuth().getId()));
            
            SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
            submoduleAuthTemplateVal = submoduleAuthTemplateValService.create(submoduleAuthTemplateVal);
            responseData.put("data", submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
            LOG.info("submoduleAuthTemplateVal Service : "+ gs.toJson(submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal)));
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
        value = "Method for get SubmoduleAuthTemplateVal by id", 
        notes = "ขอข้อมูลสิทธิของรูปแบบการใช้งานระบบงานย่อย ด้วย รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", 
        response = SubmoduleAuthTemplateValModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuthTemplateVal by id success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuthTemplateVal by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", required = true) 
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
        responseData.put("message", "SubmoduleAuthTemplateVal by id not found in the database.");
        try{
            SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
            SubmoduleAuthTemplateVal submoduleAuthTemplateVal = submoduleAuthTemplateValService.getById(id);
            if(submoduleAuthTemplateVal!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
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
        value = "Method for update SubmoduleAuthTemplateVal.", 
        notes = "แก้ไขข้อมูลสิทธิของรูปแบบการใช้งานระบบงานย่อย", 
        response =SubmoduleAuthTemplateValModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuthTemplateVal updeted by id success." )
        ,@ApiResponse( code = 404, message = "SubmoduleAuthTemplateVal by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", required = true) 
        @PathParam("id") int id, 
        SubmoduleAuthTemplateValModel submoduleAuthTemplateValModel
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
        responseData.put("message", "SubmoduleAuthTemplateVal by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
            SubmoduleAuthTemplateVal submoduleAuthTemplateVal = submoduleAuthTemplateValService.getById(id);
            if(submoduleAuthTemplateVal!=null){
                submoduleAuthTemplateVal.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleAuthTemplateVal.setLinkId(submoduleAuthTemplateValModel.getLinkId());
                submoduleAuthTemplateVal.setAuthority(submoduleAuthTemplateValModel.getAuthority());

                SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
                submoduleAuthTemplateVal.setSubmoduleAuthTemplate(submoduleAuthTemplateService.getById(submoduleAuthTemplateValModel.getSubmoduleAuthTemplate().getId()));
                SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
                submoduleAuthTemplateVal.setSubmoduleAuth(submoduleAuthService.getById(submoduleAuthTemplateValModel.getSubmoduleAuth().getId()));
                
                submoduleAuthTemplateVal = submoduleAuthTemplateValService.update(submoduleAuthTemplateVal);
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
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
        value = "Method for delete SubmoduleAuthTemplateVal by id.", 
        notes = "ลบข้อมูลสิทธิของรูปแบบการใช้งานระบบงานย่อย", 
        response = SubmoduleAuthTemplateValModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuthTemplateVal deleted by id success." )
        ,@ApiResponse( code = 404, message = "SubmoduleAuthTemplateVal by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @ApiParam(name = "id", value = "รหัสสิทธิของรูปแบบการใช้งานระบบงานย่อย", required = true) 
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
        responseData.put("message", "SubmoduleAuthTemplateVal by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
            SubmoduleAuthTemplateVal submoduleAuthTemplateVal = submoduleAuthTemplateValService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(submoduleAuthTemplateVal!=null){
                status = Response.Status.OK;
                responseData.put("data", submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
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
        value = "Method for list SubmoduleAuthTemplateVal.", 
        notes = "รายการสิทธิของรูปแบบการใช้งานระบบงานย่อย", 
        responseContainer = "List",
        response = SubmoduleAuthTemplateValModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuthTemplateVal list success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuthTemplateVal list not found in the database." ),
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
        responseData.put("message", "SubmoduleAuthTemplateVal list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
            List<SubmoduleAuthTemplateVal> listSubmoduleAuthTemplateVal = submoduleAuthTemplateValService.listAll(sort, dir);
            if(!listSubmoduleAuthTemplateVal.isEmpty()){
                ArrayList<SubmoduleAuthTemplateValModel> listSubmoduleAuthTemplateValModel = new ArrayList<>();
                for (SubmoduleAuthTemplateVal submoduleAuthTemplateVal : listSubmoduleAuthTemplateVal) {
                    listSubmoduleAuthTemplateValModel.add(submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
                }
                listSubmoduleAuthTemplateValModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listSubmoduleAuthTemplateValModel);
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
        value = "Method for get list SubmoduleAuthTemplateVal by submoduleAuthTemplate id", 
        notes = "ขอข้อมูลรายการสิทธิของรูปแบบการใช้งานระบบงานย่อย ด้วย รหัสรูปแบบการใช้งานระบบงานย่อย", 
        responseContainer = "List",
        response = SubmoduleAuthTemplateValModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "SubmoduleAuthTemplateVal list by submoduleAuthTemplate id success." ),
        @ApiResponse( code = 404, message = "SubmoduleAuthTemplateVal list by submoduleAuthTemplate id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/submoduleAuthTemplate/{submoduleAuthTemplateid}")
    public Response listBySubmoduleAuthTemplateId(
        @HeaderParam("userID") int userID,
        @ApiParam(name = "submoduleAuthTemplateid", value = "รหัสรูปแบบการใช้งานระบบงานย่อย", required = true) 
        @PathParam("submoduleAuthTemplateid") int submoduleAuthTemplateid,
        @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false) 
        @DefaultValue("orderNo") @QueryParam("sort") String sort,
        @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false) 
        @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        LOG.info("listBySubmoduleAuthTemplateId...");
        LOG.info("submoduleAuthTemplateid = "+submoduleAuthTemplateid);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "List submoduleAuthTemplateVal by submoduleAuthTemplate id not found in the database.");
        try{SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleAuthTemplate submoduleAuthTemplate = submoduleAuthTemplateService.getById(submoduleAuthTemplateid);
            if(submoduleAuthTemplate!=null){
                SubmoduleAuthTemplateValService submoduleAuthTemplateValService = new SubmoduleAuthTemplateValService();
                List<SubmoduleAuthTemplateVal> listSubmoduleAuthTemplateVal = submoduleAuthTemplateValService.listBySubmoduleAuthTemplate(submoduleAuthTemplate, sort, dir);
                if(!listSubmoduleAuthTemplateVal.isEmpty()){
                    ArrayList<SubmoduleAuthTemplateValModel> listSubmoduleAuthTemplateValModel = new ArrayList<>();
                    for (SubmoduleAuthTemplateVal submoduleAuthTemplateVal : listSubmoduleAuthTemplateVal) {
                        listSubmoduleAuthTemplateValModel.add(submoduleAuthTemplateValService.tranformToModel(submoduleAuthTemplateVal));
                    }
                    listSubmoduleAuthTemplateValModel.trimToSize();
                    status = Response.Status.OK;
                    responseData.put("data", listSubmoduleAuthTemplateValModel);
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
