package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Ad;
import com.px.admin.model.AdModel;
import com.px.admin.service.AdService;
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
@Api(value = "Ad จัดการ Ad")
@Path("v1/ads")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class AdResource {
    private static final Logger LOG = Logger.getLogger(AdResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Ad", 
        notes = "สร้างข้อมูล Ad",
        response = AdModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Ad created successfully.", response = AdModel.class ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        AdModel adModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(adModel.getVersion())
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
            Ad ad = new Ad();
            ad.setAdBase(adModel.getBase());
            ad.setAdHost(adModel.getHost());
            ad.setAdName(adModel.getName());
            ad.setAdPassword(adModel.getPass());
            ad.setAdPort(adModel.getPort());
            ad.setAdUser(adModel.getUser());
            ad.setAdPrefix(adModel.getPrefix());
            ad.setAdSuffix(adModel.getSuffix());
            ad.setAdType(adModel.getType());
            ad.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            ad.setAdAttribute(adModel.getAttribute());
            AdService adService = new AdService();
            ad = adService.create(ad);
            responseData.put("data", adService.tranformToModel(ad));
            responseData.put("message", "Ad created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
            
            //LogData For create ad
            adService.saveLogForCreate(ad, httpHeaders.getHeaderString("clientIp"));
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get Ad by id", 
        notes = "ขอข้อมูล Ad ด้วย รหัส Ad", 
        response = AdModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Ad by id success.", response = AdModel.class ),
        @ApiResponse( code = 404, message = "Ad by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัส Ad", required = true) 
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
        responseData.put("message", "Ad by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AdService adService = new AdService();
            Ad ad = adService.getById(id);
            if(ad!=null){
                status = Response.Status.OK;
                responseData.put("data", adService.tranformToModel(ad));
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
        value = "Method for update Ad.", 
        notes = "แก้ไขข้อมูล Ad", 
        response = AdModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Ad updeted by id success.", response = AdModel.class )
        ,@ApiResponse( code = 404, message = "Ad by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัส Ad", required = true) 
        @PathParam("id") int id, 
        AdModel adModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(adModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Ad by id not found in the database.");
        responseData.put("errorMessage", "");
        HashMap oldAd = new HashMap();
        try{
            AdService adService = new AdService();
            Ad ad = adService.getById(id);
            if(ad!=null){  
                if(!ad.getAdBase().equalsIgnoreCase(adModel.getBase())){
                    ad.setAdBase(adModel.getBase());
                    oldAd.put("Base",adModel.getBase());
                }
                if(!ad.getAdHost().equalsIgnoreCase(adModel.getHost())){
                    ad.setAdHost(adModel.getHost());
                    oldAd.put("Host",adModel.getHost());
                }
                if(!ad.getAdName().equalsIgnoreCase(adModel.getName())){
                    ad.setAdName(adModel.getName());
                    oldAd.put("Name",adModel.getName());
                }
                if(!ad.getAdPassword().equalsIgnoreCase(adModel.getPass())){
                    ad.setAdPassword(adModel.getPass());
                    oldAd.put("Pass",adModel.getPass());
                }
                if(!ad.getAdPort().equalsIgnoreCase(adModel.getPort())){
                    ad.setAdPort(adModel.getPort());
                    oldAd.put("Port",adModel.getPort());
                }
                if(!ad.getAdUser().equalsIgnoreCase(adModel.getUser())){
                    ad.setAdUser(adModel.getUser());
                    oldAd.put("User",adModel.getUser());
                }
                if(!ad.getAdPrefix().equalsIgnoreCase(adModel.getPrefix())){
                    ad.setAdPrefix(adModel.getPrefix());
                    oldAd.put("Prefix",adModel.getPrefix());
                }
                if(!ad.getAdSuffix().equalsIgnoreCase(adModel.getSuffix())){
                    ad.setAdSuffix(adModel.getSuffix());
                    oldAd.put("Suffix",adModel.getSuffix());
                }
                if(!ad.getAdType().equalsIgnoreCase(adModel.getType())){
                    ad.setAdType(adModel.getType());
                    oldAd.put("Type",adModel.getType());
                }
                if(!ad.getAdAttribute().equalsIgnoreCase(adModel.getAttribute())){
                    ad.setAdAttribute(adModel.getAttribute());
                    oldAd.put("Attribute",adModel.getAttribute());
                }
                ad.setUpdatedBy(adModel.getUserId());
                ad = adService.update(ad);
                status = Response.Status.OK;
                responseData.put("data", adService.tranformToModel(ad));
                responseData.put("message", "");
                
                //LogData For update Ad
                adService.saveLogForUpdate(oldAd, ad, httpHeaders.getHeaderString("clientIp"));
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
        value = "Method for delete Ad by id.", 
        notes = "ลบข้อมูล Ad", 
        response = AdModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Ad deleted by id success.", response = AdModel.class )
        ,@ApiResponse( code = 404, message = "Ad by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัส Ad", required = true) 
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
        responseData.put("message", "Ad by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AdService adService = new AdService();
            Ad ad = adService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(ad!=null){
                status = Response.Status.OK;
                responseData.put("data", adService.tranformToModel(ad));
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
        value = "Method for list Ad.", 
        notes = "รายการ Ad", 
        responseContainer = "List",
        response = AdModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Ad list success.", response = AdModel.class ),
        @ApiResponse( code = 404, message = "Ad list not found in the database." ),
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
        responseData.put("message", "Ad list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            AdService adService = new AdService();
            List<Ad> listAd = adService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listAd.isEmpty()){
                ArrayList<AdModel> listAdModel = new ArrayList<>();
                for (Ad ad : listAd) {
                    listAdModel.add(adService.tranformToModel(ad));
                }
                listAdModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listAdModel);
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
