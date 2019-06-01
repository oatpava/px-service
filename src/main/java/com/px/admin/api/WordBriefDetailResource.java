package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.WordBriefDetail;
import com.px.admin.model.WordBriefDetailModel;
import com.px.admin.service.WordBriefDetailService;
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
@Api(value = "WordBriefDetail รายละเอียดข้อมูลคำเหมือน")
@Path("v1/wordBriefDetails")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class WordBriefDetailResource {
    private static final Logger LOG = Logger.getLogger(WordBriefDetailResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create WordBriefDetail", 
        notes = "สร้างรายละเอียดข้อมูลคำเหมือน",
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "WordBriefDetail created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        WordBriefDetailModel wordBriefDetailModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wordBriefDetailModel.getVersion())
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
            WordBriefService wordBriefService = new WordBriefService();
            WordBriefDetail wordBriefDetail = new WordBriefDetail(wordBriefDetailModel.getName(), wordBriefService.getById(wordBriefDetailModel.getWordBrief().getId()), Integer.parseInt(httpHeaders.getHeaderString("userID")));
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            wordBriefDetail = wordBriefDetailService.create(wordBriefDetail);
            responseData.put("message", "WordBriefDetail created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get WordBriefDetail by id", 
        notes = "ขอรายละเอียดข้อมูลคำเหมือน ด้วย รหัสรายละเอียดคำเหมือน", 
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail by id success." ),
        @ApiResponse( code = 404, message = "WordBriefDetail by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสรายละเอียดคำเหมือน", required = true) 
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
        responseData.put("message", "WordBriefDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            WordBriefDetail wordBriefDetail = wordBriefDetailService.getById(id);
            if(wordBriefDetail!=null){
                status = Response.Status.OK;
                responseData.put("data", wordBriefDetailService.tranformToModel(wordBriefDetail));
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
        value = "Method for update WordBriefDetail.", 
        notes = "แก้ไขรายละเอียดข้อมูลคำเหมือน", 
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail updeted by id success." )
        ,@ApiResponse( code = 404, message = "WordBriefDetail by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสรายละเอียดคำเหมือน", required = true) 
        @PathParam("id") int id, 
        WordBriefDetailModel wordBriefDetailModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(wordBriefDetailModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WordBriefDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            WordBriefDetail wordBriefDetail = wordBriefDetailService.getById(id);
            if(wordBriefDetail!=null){
                wordBriefDetail.setWordBriefDetailName(wordBriefDetailModel.getName());
                wordBriefDetail.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wordBriefDetail = wordBriefDetailService.update(wordBriefDetail);
                status = Response.Status.OK;
                responseData.put("data", wordBriefDetailService.tranformToModel(wordBriefDetail));
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
        value = "Method for delete WordBriefDetail by id.", 
        notes = "ลบรายละเอียดข้อมูลคำเหมือน", 
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail deleted by id success." )
        ,@ApiResponse( code = 404, message = "WordBriefDetail by id not found in the database." )
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
        responseData.put("message", "WordBriefDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            WordBriefDetail wordBriefDetail = wordBriefDetailService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(wordBriefDetail!=null){
                status = Response.Status.OK;
                responseData.put("data", wordBriefDetailService.tranformToModel(wordBriefDetail));
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
        value = "Method for list WordBriefDetail.", 
        notes = "รายการคำเหมือน", 
        responseContainer = "List",
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail list success." ),
        @ApiResponse( code = 404, message = "WordBriefDetail list not found in the database." ),
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
        responseData.put("message", "WordBriefDetail list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            List<WordBriefDetail> listWordBriefDetail = wordBriefDetailService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listWordBriefDetail.isEmpty()){
                ArrayList<WordBriefDetailModel> listWordBriefDetailModel = new ArrayList<>();
                for (WordBriefDetail wordBriefDetail : listWordBriefDetail) {
                    listWordBriefDetailModel.add(wordBriefDetailService.tranformToModel(wordBriefDetail));
                }
                listWordBriefDetailModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listWordBriefDetailModel);
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
        value = "Method for get WordBriefDetail by WordBrief id", 
        notes = "ขอรายละเอียดข้อมูลคำเหมือน ด้วย รหัสคำเหมือน", 
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail by WordBrief id success." ),
        @ApiResponse( code = 404, message = "WordBriefDetail by WordBrief id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/wordbrief/{wordBriefId}")
    public Response getByWordBriefId(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "wordBriefId", value = "รหัสคำเหมือน", required = true) 
        @PathParam("wordBriefId") int wordBriefId
    ) {
        LOG.debug("getByWordBriefId...");
        LOG.debug("wordBriefId = "+wordBriefId);
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
        responseData.put("message", "WordBriefDetail by WordBrief id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            List<WordBriefDetail> listWordBriefDetail = wordBriefDetailService.listByWordBriefId(wordBriefId,listOptionModel.getSort(),listOptionModel.getDir());
            if(!listWordBriefDetail.isEmpty()){
                ArrayList<WordBriefDetailModel> listWordBriefDetailModel = new ArrayList<>();
                for (WordBriefDetail wordBriefDetail : listWordBriefDetail) {
                    listWordBriefDetailModel.add(wordBriefDetailService.tranformToModel(wordBriefDetail));
                }
                listWordBriefDetailModel.trimToSize();
                responseData.put("data", listWordBriefDetailModel);
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
        value = "Method for get WordBriefDetail by WordBrief name", 
        notes = "ขอรายละเอียดข้อมูลคำเหมือน ด้วย คำเหมือน", 
        response = WordBriefDetailModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "WordBriefDetail by WordBrief name success." ),
        @ApiResponse( code = 404, message = "WordBriefDetail by WordBrief name not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/wordbrief/name/{wordBriefName}")
    public Response getByWordBriefName(
        @BeanParam ListOptionModel listOptionModel,
        @ApiParam(name = "wordBriefName", value = "คำเหมือน", required = true) 
        @PathParam("wordBriefName") String wordBriefName
    ) {
        LOG.debug("getByWordBriefName...");
        LOG.debug("wordBriefName = "+wordBriefName);
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
        responseData.put("message", "WordBriefDetail by WordBrief id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            WordBriefDetailService wordBriefDetailService = new WordBriefDetailService();
            List<WordBriefDetail> listWordBriefDetail = wordBriefDetailService.listByWordBrief(wordBriefName,listOptionModel.getSort(),listOptionModel.getDir());
            if(!listWordBriefDetail.isEmpty()){
                ArrayList<WordBriefDetailModel> listWordBriefDetailModel = new ArrayList<>();
                for (WordBriefDetail wordBriefDetail : listWordBriefDetail) {
                    listWordBriefDetailModel.add(wordBriefDetailService.tranformToModel(wordBriefDetail));
                }
                listWordBriefDetailModel.trimToSize();
                
                responseData.put("data", listWordBriefDetailModel);
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
