package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Title;
import com.px.admin.model.TitleModel;
import com.px.admin.service.TitleService;
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
@Api(value = "Title คำนำหน้า")
@Path("v1/titles")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class TitleResource {
    private static final Logger LOG = Logger.getLogger(TitleResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "สร้างข้อมูลคำนำหน้า",
        notes = "สร้างข้อมูลคำนำหน้า",
        response = TitleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Title created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        TitleModel titleModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(titleModel.getVersion())
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
            Title title = new Title();
            title.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            title.setTitleName(titleModel.getName());
            title.setTitleNameEng(titleModel.getNameEng());
            TitleService titleService = new TitleService();
            title = titleService.create(title);
            responseData.put("data", titleService.tranformToModel(title));
            responseData.put("message", "Title created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "ขอข้อมูลคำนำหน้า ด้วย รหัสคำนำหน้า", 
        notes = "ขอข้อมูลคำนำหน้า ด้วย รหัสคำนำหน้า", 
        response = TitleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Title by id success." ),
        @ApiResponse( code = 404, message = "Title by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสคำนำหน้า", required = true) 
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
        responseData.put("message", "Title by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            TitleService titleService = new TitleService();
            Title title = titleService.getByIdNotRemoved(id);
            if(title!=null){
                status = Response.Status.OK;
                responseData.put("data", titleService.tranformToModel(title));
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
        value = "แก้ไขข้อมูลคำนำหน้า", 
        notes = "แก้ไขข้อมูลคำนำหน้า", 
        response = TitleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Title updeted by id success." )
        ,@ApiResponse( code = 404, message = "Title by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสคำนำหน้า", required = true) 
        @PathParam("id") int id, 
        TitleModel titleModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(titleModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Title by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            TitleService titleService = new TitleService();
            Title title = titleService.getByIdNotRemoved(id);
            if(title!=null){
                title.setTitleName(titleModel.getName());
                title.setTitleNameEng(titleModel.getNameEng());
                title.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                title = titleService.update(title);
                status = Response.Status.OK;
                responseData.put("data", titleService.tranformToModel(title));
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
        value = "ลบข้อมูลคำนำหน้าด้วยรหัสคำนำหน้า", 
        notes = "ลบข้อมูลคำนำหน้า", 
        response = TitleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Title deleted by id success." )
        ,@ApiResponse( code = 404, message = "Title by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสคำนำหน้า", required = true) 
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
        responseData.put("message", "Title by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            TitleService titleService = new TitleService();
            Title title = titleService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(title!=null){
                status = Response.Status.OK;
                responseData.put("data", titleService.tranformToModel(title));
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
        code = 200,
        value = "รายการคำนำหน้า", 
        notes = "รายการคำนำหน้า", 
        responseContainer = "List",
        response = TitleModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Title list success.", response = TitleModel.class, responseContainer = "List" ),
        @ApiResponse( code = 404, message = "Title list not found in the database." ),
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
        LOG.debug("sort = "+listOptionModel.getSort());
        LOG.debug("dir = "+listOptionModel.getDir());
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
        responseData.put("message", "Title list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            TitleService titleService = new TitleService();
            List<Title> listTitle = titleService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listTitle.isEmpty()){
                ArrayList<TitleModel> listTitleModel = new ArrayList<>();
                for (Title title : listTitle) {
                    listTitleModel.add(titleService.tranformToModel(title));
                }
                listTitleModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listTitleModel);
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
