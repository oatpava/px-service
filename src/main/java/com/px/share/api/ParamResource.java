package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.entity.Param;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ParamModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
 * @author Pritsana
 */
@Api(value = "Param การตั้งค่าระบบ")
@Path("v1/params")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class ParamResource {
    private static final Logger LOG = Logger.getLogger(ParamResource.class.getName());
    
    @Context private HttpServletResponse response;
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Param", 
        notes = "สร้างข้อมูลตั้งค่าระบบ",
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Param created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        ParamModel paramModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(paramModel.getVersion())
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
            Param param = new Param();
//            param.setCreatedBy(paramModel.getUserId());
            param.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            param.setParamName(paramModel.getParamName());
            param.setParamValue(paramModel.getParamValue());
            
            ParamService paramService = new ParamService();
            param = paramService.create(param);
            responseData.put("data", paramService.tranformToModel(param));
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
        value = "Method for get Param by id", 
        notes = "ขอข้อมูลตั้งค่าระบบ ด้วย รหัสการตั้งค่า", 
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Param by id success." ),
        @ApiResponse( code = 404, message = "Param by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสการตั้งค่า", required = true) 
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
        responseData.put("message", "Param by id not found in the database.");
        try{
            ParamService paramService = new ParamService();
            Param param = paramService.getByIdNotRemoved(id);
            if(param!=null){
                status = Response.Status.OK;
                responseData.put("data", paramService.tranformToModel(param));
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
        value = "Method for update Param.", 
        notes = "แก้ไขข้อมูลตั้งค่า", 
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Param updeted by id success." )
        ,@ApiResponse( code = 404, message = "Param by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสตั้งค่า", required = true) 
        @PathParam("id") int id, 
        ParamModel paramModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(paramModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Param by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ParamService paramService = new ParamService();
            Param param = paramService.getById(id);
            if(param!=null){
//                param.setUpdatedBy(paramModel.getUserId());
                param.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                param.setParamName(paramModel.getParamName());
                param.setParamValue(paramModel.getParamValue());
                
                param = paramService.update(param);
                status = Response.Status.OK;
                responseData.put("data", paramService.tranformToModel(param));
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
        value = "Method for delete Param by id.", 
        notes = "ลบข้อมูลตั้งค่า", 
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Param deleted by id success." )
        ,@ApiResponse( code = 404, message = "Param by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสตั้งค่า", required = true) 
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
        responseData.put("message", "Param by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ParamService paramService = new ParamService();
            Param param = paramService.remove(id, versionModel.getUserId());
            if(param!=null){
                status = Response.Status.OK;
                responseData.put("data", paramService.tranformToModel(param));
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
        value = "Method for list Param.", 
        notes = "รายการตั้งค่า", 
        responseContainer = "List",
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Param list success." ),
        @ApiResponse( code = 404, message = "Param list not found in the database." ),
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
        responseData.put("data", new ArrayList<>());
        responseData.put("message", "Param list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            ParamService paramService = new ParamService();
            List<Param> listParam = paramService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listParam.isEmpty()){
                ArrayList<ParamModel> listParamModel = new ArrayList<>();
                for (Param param : listParam) {
                    listParamModel.add(paramService.tranformToModel(param));
                }
                listParamModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listParamModel);
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
        value = "Method for get Param by ParamName", 
        notes = "ขอข้อมูลตั้งค่าระบบ ด้วย ชื่อการตั้งค่า", 
        response = ParamModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Param by id success." ),
        @ApiResponse( code = 404, message = "Param by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/name/{paramName}")
    public Response getByParamName(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "paramName", value = "ชื่อการตั้งค่า", required = true) 
        @PathParam("paramName") String paramName
    ) {
        LOG.debug("getByParamName...");
        LOG.debug("paramName = "+paramName);
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
        responseData.put("message", "Param by paramName not found in the database.");
        try{
            ParamService paramService = new ParamService();
            Param param = paramService.getByParamName(paramName);
            if(param!=null){
                status = Response.Status.OK;
                responseData.put("data", paramService.tranformToModel(param));
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
