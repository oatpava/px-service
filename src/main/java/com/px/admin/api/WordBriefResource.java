package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.WordBrief;
import com.px.admin.model.WordBriefModel;
import com.px.admin.service.WordBriefService;
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
@Api(value = "WordBrief แฟ้มคำเหมือน")
@Path("v1/wordBriefs")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class WordBriefResource {
    private static final Logger LOG = Logger.getLogger(WordBriefResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create WordBrief", 
        notes = "สร้างแฟ้มคำเหมือน",
        response = WordBriefModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "WordBrief created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        WordBriefModel wordBriefModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wordBriefModel.getVersion())
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
            WordBrief wordBrief = new WordBrief(wordBriefModel.getName(),Integer.parseInt(httpHeaders.getHeaderString("userID")));
            WordBriefService wordBriefService = new WordBriefService();
            wordBrief = wordBriefService.create(wordBrief);
            responseData.put("message", "WordBrief created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get WordBrief by id", 
        notes = "ขอแฟ้มคำเหมือน ด้วย รหัสคำเหมือน", 
        response = WordBriefModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBrief by id success." ),
        @ApiResponse( code = 404, message = "WordBrief by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสคำเหมือน", required = true) 
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
        responseData.put("message", "WordBrief by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefService wordBriefService = new WordBriefService();
            WordBrief wordBrief = wordBriefService.getById(id);
            if(wordBrief!=null){
                status = Response.Status.OK;
                responseData.put("data", wordBriefService.tranformToModel(wordBrief));
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
        value = "Method for update WordBrief.", 
        notes = "แก้ไขแฟ้มคำเหมือน", 
        response = WordBriefModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBrief updeted by id success." )
        ,@ApiResponse( code = 404, message = "WordBrief by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสคำเหมือน", required = true) 
        @PathParam("id") int id, 
        WordBriefModel wordBriefModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(wordBriefModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WordBrief by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefService wordBriefService = new WordBriefService();
            WordBrief wordBrief = wordBriefService.getById(id);
            if(wordBrief!=null){
                wordBrief.setWordBriefName(wordBriefModel.getName());
                wordBrief.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wordBrief = wordBriefService.update(wordBrief);
                status = Response.Status.OK;
                responseData.put("data", wordBriefService.tranformToModel(wordBrief));
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
        value = "Method for delete WordBrief by id.", 
        notes = "ลบแฟ้มคำเหมือน", 
        response = WordBriefModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBrief deleted by id success." )
        ,@ApiResponse( code = 404, message = "WordBrief by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสคำเหมือน", required = true) 
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
        responseData.put("message", "WordBrief by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefService wordBriefService = new WordBriefService();
            WordBrief wordBrief = wordBriefService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(wordBrief!=null){
                status = Response.Status.OK;
                responseData.put("data", wordBriefService.tranformToModel(wordBrief));
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
        value = "Method for list WordBrief.", 
        notes = "รายการคำเหมือน", 
        responseContainer = "List",
        response = WordBriefModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBrief list success." ),
        @ApiResponse( code = 404, message = "WordBrief list not found in the database." ),
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
        responseData.put("message", "WordBrief list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefService wordBriefService = new WordBriefService();
            List<WordBrief> listWordBrief = wordBriefService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listWordBrief.isEmpty()){
                ArrayList<WordBriefModel> listWordBriefModel = new ArrayList<>();
                for (WordBrief wordBrief : listWordBrief) {
                    listWordBriefModel.add(wordBriefService.tranformToModel(wordBrief));
                }
                listWordBriefModel.trimToSize();
                responseData.put("data", listWordBriefModel);
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
}
