package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.PositionType;
import com.px.admin.model.PositionTypeModel;
import com.px.admin.service.PositionTypeService;
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
@Api(value = "PositionType ประเภทตำแหน่งราชการ")
@Path("v1/positionTypes/types")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class PositionTypeResource {
    private static final Logger LOG = Logger.getLogger(PositionTypeResource.class.getName());
    
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
        value = "Method for create PositionType", 
        notes = "สร้างข้อมูลประเภทตำแหน่งราชการ",
        response = PositionTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 201, message = "PositionType created successfully." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response create(
        PositionTypeModel positionTypeModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(positionTypeModel.getVersion())
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
            PositionType positionType = new PositionType();
            positionType.setCreatedBy(Integer.BYTES);
            positionType.setPositionTypeName(positionTypeModel.getName());
            positionType.setPositionTypeAbbr(positionTypeModel.getAbbr());
            PositionTypeService positionTypeService = new PositionTypeService();
            positionType = positionTypeService.create(positionType);
            responseData.put("data", positionTypeService.tranformToModel(positionType));
            responseData.put("message", "PositionType created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        }catch(Exception ex){
            LOG.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get PositionType by id", 
        notes = "ขอข้อมูลประเภทตำแหน่งราชการ ด้วย รหัสประเภทตำแหน่งราชการ", 
        response = PositionTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "PositionType by id success." ),
        @ApiResponse( code = 404, message = "PositionType by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทตำแหน่งราชการ", required = true) 
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
        responseData.put("message", "PositionType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionTypeService positionTypeService = new PositionTypeService();
            PositionType positionType = positionTypeService.getById(id);
            if(positionType!=null){
                status = Response.Status.OK;
                responseData.put("data", positionTypeService.tranformToModel(positionType));
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
        value = "Method for update PositionType.", 
        notes = "แก้ไขข้อมูลประเภทตำแหน่งราชการ", 
        response = PositionTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "PositionType updeted by id success." )
        ,@ApiResponse( code = 404, message = "PositionType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
        @ApiParam(name = "id", value = "รหัสประเภทตำแหน่งราชการ", required = true) 
        @PathParam("id") int id, 
        PositionTypeModel positionTypeModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(positionTypeModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "PositionType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionTypeService positionTypeService = new PositionTypeService();
            PositionType positionType = positionTypeService.getById(id);
            if(positionType!=null){
                positionType.setPositionTypeAbbr(positionTypeModel.getAbbr());
                positionType.setPositionTypeName(positionTypeModel.getName());
                positionType.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                positionType = positionTypeService.update(positionType);
                status = Response.Status.OK;
                responseData.put("data", positionTypeService.tranformToModel(positionType));
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
        value = "Method for delete PositionType by id.", 
        notes = "ลบข้อมูลประเภทตำแหน่งราชการ", 
        response = PositionTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "PositionType deleted by id success." )
        ,@ApiResponse( code = 404, message = "PositionType by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.TEXT_PLAIN })
    @Path(value = "/{id}")
    public Response remove(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "รหัสประเภทตำแหน่งราชการ", required = true) 
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
        responseData.put("message", "PositionType by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionTypeService positionTypeService = new PositionTypeService();
            PositionType positionType = positionTypeService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if(positionType!=null){
                status = Response.Status.OK;
                responseData.put("data", positionTypeService.tranformToModel(positionType));
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
        value = "Method for list PositionType.", 
        notes = "รายการประเภทตำแหน่งราชการ", 
        responseContainer = "List",
        response = PositionTypeModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "PositionType list success." ),
        @ApiResponse( code = 404, message = "PositionType list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON })
//    @Consumes({ MediaType.TEXT_PLAIN })
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
        responseData.put("message", "PositionType list not found in the database.");
        responseData.put("errorMessage", "");
        try{
            PositionTypeService positionTypeService = new PositionTypeService();
            List<PositionType> listPositionType = positionTypeService.listAll(listOptionModel.getSort(), listOptionModel.getDir());
            if(!listPositionType.isEmpty()){
                ArrayList<PositionTypeModel> listPositionTypeModel = new ArrayList<>();
                for (PositionType positionType : listPositionType) {
                    listPositionTypeModel.add(positionTypeService.tranformToModel(positionType));
                }
                listPositionTypeModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listPositionTypeModel);
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
