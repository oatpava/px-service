package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Position;
import com.px.admin.model.PositionModel;
import com.px.admin.service.PositionService;
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
@Api(value = "Position ตำแหน่งราชการ")
@Path("v1/positions")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class PositionResource {
    private static final Logger LOG = Logger.getLogger(PositionResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create Position", 
        notes = "สร้างข้อมูลตำแหน่งราชการ",
        response = PositionModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "Position created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        PositionModel positionPostModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(positionPostModel.getVersion())
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
            Position position = new Position();
            position.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            position.setPositionName(positionPostModel.getName());
            position.setPositionNameEng(positionPostModel.getNameEng());
            position.setPositionNameExtra(positionPostModel.getNameExtra());
            PositionService positionService = new PositionService();
            position = positionService.create(position);
            responseData.put("data", positionService.tranformToModel(position));
            responseData.put("message", "Position created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get Position by id", 
        notes = "ขอข้อมูลตำแหน่งราชการ ด้วย รหัสตำแหน่งราชการ", 
        response = PositionModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Position by id success." ),
        @ApiResponse( code = 404, message = "Position by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสตำแหน่งราชการ", required = true) 
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
        responseData.put("message", "Position by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionService positionService = new PositionService();
            Position position = positionService.getById(id);
            if(position!=null){
                status = Response.Status.OK;
                responseData.put("data", positionService.tranformToModel(position));
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
        value = "Method for update Position.", 
        notes = "แก้ไขข้อมูลตำแหน่งราชการ", 
        response = PositionModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Position updeted by id success." )
        ,@ApiResponse( code = 404, message = "Position by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสตำแหน่งราชการ", required = true) 
        @PathParam("id") int id, 
        PositionModel positionPostModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(positionPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Position by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionService positionService = new PositionService();
            Position position = positionService.getById(id);
            if(position!=null){
                position.setPositionName(positionPostModel.getName());
                position.setPositionNameEng(positionPostModel.getNameEng());
                position.setPositionNameExtra(positionPostModel.getNameExtra());
                position.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                position = positionService.update(position);
                status = Response.Status.OK;
                responseData.put("data", positionService.tranformToModel(position));
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
        value = "Method for delete Position by id.", 
        notes = "ลบข้อมูลตำแหน่งราชการ", 
        response = PositionModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Position deleted by id success." )
        ,@ApiResponse( code = 404, message = "Position by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสตำแหน่งราชการ", required = true) 
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
        responseData.put("message", "Position by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionService positionService = new PositionService();
            Position position = positionService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(position!=null){
                status = Response.Status.OK;
                responseData.put("data", positionService.tranformToModel(position));
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
        value = "Method for list Position.", 
        notes = "รายการตำแหน่งราชการ", 
        responseContainer = "List",
        response = PositionModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Position list success.", response = PositionModel.class, responseContainer = "List" ),
        @ApiResponse( code = 404, message = "Position list not found in the database." ),
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
        responseData.put("message", "Position list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionService positionService = new PositionService();
            List<Position> listPosition = positionService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listPosition.isEmpty()){
                ArrayList<PositionModel> listPositionModel = new ArrayList<>();
                for (Position position : listPosition) {
                    listPositionModel.add(positionService.tranformToModel(position));
                }
                listPositionModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listPositionModel);
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
