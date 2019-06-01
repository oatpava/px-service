package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Lookup;
import com.px.admin.model.LookupModel;
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
@Api(value = "Lookup ข้อมูลตัวเลือก")
@Path("v1/lookups")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class LookupResource {
    private static final Logger LOG = Logger.getLogger(LookupResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "สร้างข้อมูลตัวเลือก", 
        notes = "",
        response = LookupModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Lookup created successfully.", response = LookupModel.class ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        LookupModel lookupModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(lookupModel.getVersion())
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
            Lookup lookup = new Lookup(lookupModel.getName(),Integer.parseInt(httpHeaders.getHeaderString("userID")));
            LookupService lookupService = new LookupService();
            lookup = lookupService.create(lookup);
            responseData.put("data", lookupService.tranformToModel(lookup));
            responseData.put("message", "Lookup created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get Lookup by id", 
        notes = "ขอข้อมูลตัวเลือก ด้วย รหัสตัวเลือก", 
        response = LookupModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Lookup by id success." ),
        @ApiResponse( code = 404, message = "Lookup by id not found in the database." ),
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
        responseData.put("message", "Lookup by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupService lookupService = new LookupService();
            Lookup lookup = lookupService.getById(id);
            if(lookup!=null){
                status = Response.Status.OK;
                responseData.put("data", lookupService.tranformToModel(lookup));
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
        value = "Method for update Lookup.", 
        notes = "แก้ไขข้อมูลตัวเลือก", 
        response = LookupModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Lookup updeted by id success." )
        ,@ApiResponse( code = 404, message = "Lookup by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสตัวเลือก", required = true) 
        @PathParam("id") int id, 
        LookupModel lookupModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(lookupModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Lookup by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupService lookupService = new LookupService();
            Lookup lookup = lookupService.getById(id);
            if(lookup!=null){
                lookup.setLookupName(lookupModel.getName());
                lookup.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                lookup = lookupService.update(lookup);
                status = Response.Status.OK;
                responseData.put("data", lookupService.tranformToModel(lookup));
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
        value = "Method for delete Lookup by id.", 
        notes = "ลบข้อมูลตัวเลือก", 
        response = LookupModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Lookup deleted by id success." )
        ,@ApiResponse( code = 404, message = "Lookup by id not found in the database." )
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
        responseData.put("message", "Lookup by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupService lookupService = new LookupService();
            Lookup lookup = lookupService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(lookup!=null){
                status = Response.Status.OK;
                responseData.put("data", lookupService.tranformToModel(lookup));
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
        value = "Method for list Lookup.", 
        notes = "รายการตัวเลือก", 
        responseContainer = "List",
        response = LookupModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Lookup list success." ),
        @ApiResponse( code = 404, message = "Lookup list not found in the database." ),
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
        responseData.put("message", "Lookup list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            LookupService lookupService = new LookupService();
            List<Lookup> listLookup = lookupService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listLookup.isEmpty()){
                ArrayList<LookupModel> listLookupModel = new ArrayList<>();
                for (Lookup lookup : listLookup) {
                    listLookupModel.add(lookupService.tranformToModel(lookup));
                }
                listLookupModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listLookupModel);
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
