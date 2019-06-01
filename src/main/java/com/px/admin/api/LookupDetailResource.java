package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.LookupDetail;
import com.px.admin.model.LookupDetailModel;
import com.px.admin.service.LookupDetailService;
import com.px.admin.service.LookupService;
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
@Api(value = "LookupDetail รายละเอียดข้อมูลตัวเลือก")
@Path("v1/lookupDetails")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class LookupDetailResource {
    private static final Logger LOG = Logger.getLogger(LookupDetailResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create LookupDetail", 
        notes = "สร้างรายละเอียดข้อมูลตัวเลือก",
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "LookupDetail created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        LookupDetailModel lookupDetailModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(lookupDetailModel.getVersion())
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
            LookupService lookupService = new LookupService();
            LookupDetail lookupDetail = new LookupDetail(lookupDetailModel.getName(), lookupService.getById(lookupDetailModel.getLookupId()), Integer.parseInt(httpHeaders.getHeaderString("userID")));
            LookupDetailService lookupDetailService = new LookupDetailService();
            lookupDetail = lookupDetailService.create(lookupDetail);
            status = Response.Status.CREATED;
            responseData.put("data", lookupDetailService.tranformToModel(lookupDetail));
            responseData.put("message", "LookupDetail created successfully.");
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get LookupDetail by id", 
        notes = "ขอรายละเอียดข้อมูลตัวเลือก ด้วย รหัสตัวเลือก", 
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail by id success." ),
        @ApiResponse( code = 404, message = "LookupDetail by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสตัวเลือก", required = true) 
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
        responseData.put("message", "LookupDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            LookupDetail lookupDetail = lookupDetailService.getById(id);
            if(lookupDetail!=null){
                status = Response.Status.OK;
                responseData.put("data", lookupDetailService.tranformToModel(lookupDetail));
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
        value = "Method for update LookupDetail.", 
        notes = "แก้ไขรายละเอียดข้อมูลตัวเลือก", 
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail updeted by id success." )
        ,@ApiResponse( code = 404, message = "LookupDetail by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสรายละเอียดตัวเลือก", required = true) 
        @PathParam("id") int id, 
        LookupDetailModel lookupDetailModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(lookupDetailModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "LookupDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            LookupDetail lookupDetail = lookupDetailService.getById(id);
            if(lookupDetail!=null){
                lookupDetail.setLookupDetailName(lookupDetailModel.getName());
                lookupDetail.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                lookupDetail = lookupDetailService.update(lookupDetail);
                status = Response.Status.OK;
                responseData.put("data", lookupDetailService.tranformToModel(lookupDetail));
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
        value = "Method for delete LookupDetail by id.", 
        notes = "ลบรายละเอียดข้อมูลตัวเลือก", 
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail deleted by id success." )
        ,@ApiResponse( code = 404, message = "LookupDetail by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสตัวเลือก", required = true) 
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
        responseData.put("message", "LookupDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            LookupDetail lookupDetail = lookupDetailService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(lookupDetail!=null){
                status = Response.Status.OK;
                responseData.put("data", lookupDetailService.tranformToModel(lookupDetail));
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
        value = "Method for delete LookupDetail by id.", 
        notes = "ลบรายละเอียดข้อมูลตัวเลือก", 
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail deleted by id success." )
        ,@ApiResponse( code = 404, message = "LookupDetail by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/lookup/{lookupId}")
    public Response removeByLookupId(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "lookupId", value = "รหัสตัวเลือก", required = true) 
        @PathParam("lookupId") int lookupId
    ) {
        LOG.debug("removeByLookupId...");
        LOG.debug("lookupId = "+lookupId);
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
        responseData.put("message", "LookupDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            List<LookupDetail> listLookupDetail = lookupDetailService.listAllByLookupId(lookupId, "createdDate","desc");
            if(!listLookupDetail.isEmpty()){
                ArrayList<LookupDetailModel> listLookupDetailModel = new ArrayList<>();
                for (LookupDetail lookupDetail : listLookupDetail) {
                    lookupDetailService.remove(lookupDetail.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                }
                status = Response.Status.OK;
                responseData.put("data", listLookupDetailModel);
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
        value = "Method for list LookupDetail.", 
        notes = "รายการตัวเลือก", 
        responseContainer = "List",
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail list success." ),
        @ApiResponse( code = 404, message = "LookupDetail list not found in the database." ),
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
        responseData.put("message", "LookupDetail list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            List<LookupDetail> listLookupDetail = lookupDetailService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listLookupDetail.isEmpty()){
                ArrayList<LookupDetailModel> listLookupDetailModel = new ArrayList<>();
                for (LookupDetail lookupDetail : listLookupDetail) {
                    listLookupDetailModel.add(lookupDetailService.tranformToModel(lookupDetail));
                }
                listLookupDetailModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listLookupDetailModel);
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
        value = "Method for list LookupDetail By Lookup id.", 
        notes = "รายการตัวเลือกด้วยรหัสตัวเลือก", 
        responseContainer = "List",
        response = LookupDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "LookupDetail list By Lookup id success." ),
        @ApiResponse( code = 404, message = "LookupDetail list By Lookup id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/lookup/{lookupId}")
    public Response listByLookupId(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "lookupId", value = "รหัสตัวเลือก", required = true) 
        @PathParam("lookupId") int lookupId
    ) {
        LOG.debug("listByLookupId...");
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
        responseData.put("message", "LookupDetail list By Lookup id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupDetailService lookupDetailService = new LookupDetailService();
            List<LookupDetail> listLookupDetail = lookupDetailService.listAllByLookupId(lookupId, listOptionModel.getSort(), listOptionModel.getDir());
            if(!listLookupDetail.isEmpty()){
                ArrayList<LookupDetailModel> listLookupDetailModel = new ArrayList<>();
                for (LookupDetail lookupDetail : listLookupDetail) {
                    listLookupDetailModel.add(lookupDetailService.tranformToModel(lookupDetail));
                }
                listLookupDetailModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listLookupDetailModel);
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
