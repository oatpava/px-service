package com.px.share.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.entity.Month;
import com.px.share.model.ListOptionModel;
import com.px.share.model.MonthModel;
import com.px.share.model.VersionModel;
import com.px.share.service.MonthService;
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
 * @author Peach
 */
@Api(value = "Month เดือน")
@Path("v1/month")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class MonthResource {
    private static final Logger LOG = Logger.getLogger(MonthResource.class.getName());
    
    @Context private HttpServletResponse response;
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Month", 
        notes = "สร้างข้อมูลเดือน",
        response = MonthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Month created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        MonthModel monthModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(monthModel.getVersion())
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
            Month month = new Month();
            month.setCreatedBy(monthModel.getUserId());
            month.setOrderFiscal(monthModel.getOrderFiscal());
            month.setThaiName(monthModel.getThaiName());
            month.setThaiNameAbbr(monthModel.getThaiNameAbbr());
            month.setEngName(monthModel.getEngName());
            month.setEngNameAbbr(monthModel.getEngNameAbbr());
            
            MonthService monthService = new MonthService();
            month = monthService.create(month);
            responseData.put("data", monthService.tranformToModel(month));
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
        value = "Method for get Month by id", 
        notes = "ขอข้อมูลเดือน ด้วย รหัสเดือน", 
        response = MonthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Month by id success." ),
        @ApiResponse( code = 404, message = "Month by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสเดือน", required = true) 
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
        responseData.put("message", "Month by id not found in the database.");
        try{
            MonthService monthService = new MonthService();
            Month month = monthService.getByIdNotRemoved(id);
            if(month!=null){
                status = Response.Status.OK;
                responseData.put("data", monthService.tranformToModel(month));
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
        value = "Method for update Month.", 
        notes = "แก้ไขข้อมูลเดือน", 
        response = MonthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Month updeted by id success." )
        ,@ApiResponse( code = 404, message = "Month by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสเดือน", required = true) 
        @PathParam("id") int id, 
        MonthModel monthPostModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(monthPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Month by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MonthService monthService = new MonthService();
            Month month = monthService.getById(id);
            if(month!=null){
                month.setUpdatedBy(monthPostModel.getUserId());
                month.setOrderFiscal(monthPostModel.getOrderFiscal());
                month.setThaiName(monthPostModel.getThaiName());
                month.setThaiNameAbbr(monthPostModel.getThaiNameAbbr());
                month.setEngName(monthPostModel.getEngName());
                month.setEngNameAbbr(monthPostModel.getEngNameAbbr());
                
                month = monthService.update(month);
                status = Response.Status.OK;
                responseData.put("data", monthService.tranformToModel(month));
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
        value = "Method for delete Month by id.", 
        notes = "ลบข้อมูลเดือน", 
        response = MonthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Month deleted by id success." )
        ,@ApiResponse( code = 404, message = "Month by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสเดือน", required = true) 
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
        responseData.put("message", "Month by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MonthService monthService = new MonthService();
            Month month = monthService.remove(id, versionModel.getUserId());
            if(month!=null){
                status = Response.Status.OK;
                responseData.put("data", monthService.tranformToModel(month));
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
        value = "Method for list Month.", 
        notes = "รายการเดือน", 
        responseContainer = "List",
        response = MonthModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Month list success." ),
        @ApiResponse( code = 404, message = "Month list not found in the database." ),
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
        responseData.put("message", "Month list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            MonthService monthService = new MonthService();
            List<Month> listMonth = monthService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listMonth.isEmpty()){
                ArrayList<MonthModel> listMonthModel = new ArrayList<>();
                for (Month month : listMonth) {
                    listMonthModel.add(monthService.tranformToModel(month));
                }
                listMonthModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listMonthModel);
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
