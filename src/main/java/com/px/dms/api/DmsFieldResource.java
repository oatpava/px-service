/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.DmsField;
import com.px.dms.model.DmsFieldModel;

import com.px.dms.service.DmsFieldService;
import com.px.share.model.VersionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
@Api(value = "DmsField ฟิว")
@Path("v1/dmsFields")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DmsFieldResource {

    private static final Logger log = Logger.getLogger(DmsFieldResource.class.getName());

    @ApiOperation(
            value = "Method for create DmsField",
            notes = "สร้างข้อมูล DmsField",
            response = DmsFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "DmsField created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
             @HeaderParam("userID") int userID,
             DmsFieldModel dmsFieldPostModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(dmsFieldPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsField DmsField = new DmsField(dmsFieldPostModel.getFieldName(), dmsFieldPostModel.getFieldType(), dmsFieldPostModel.getFieldMap(), userID);

            DmsFieldService dmsFieldService = new DmsFieldService();
            DmsField = dmsFieldService.create(DmsField);
            if (DmsField != null) {
                responseData.put("data", dmsFieldService.tranformToModel(DmsField));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "DmsField created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for update DmsField.", 
        notes = "แก้ไขข้อมูล DmsField", 
        response = DmsFieldModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "DmsField updeted by id success." )
        ,@ApiResponse( code = 404, message = "DmsField by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @PUT
    @Consumes({MediaType.APPLICATION_JSON })
    @Path(value = "/{id}")
    public Response update(
             @HeaderParam("userID") int userID,
        @ApiParam(name = "id", value = "DmsFieldID", required = true) 
        @PathParam("id") int id, 
          DmsFieldModel dmsFieldPostModel
    ) {
        log.info("update...");
        log.info("id = "+id);
        Gson gs = new GsonBuilder()
                .setVersion(dmsFieldPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();           
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "DmsField by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            DmsFieldService dmsFieldService = new DmsFieldService();
            DmsField dmsField  = dmsFieldService.getById(id);
            
         
            if(dmsField!=null){
                dmsField.setDmsFieldName(dmsFieldPostModel.getFieldName());
                dmsField.setDmsFieldType(dmsFieldPostModel.getFieldType());
                dmsField.setDmsFieldMap(dmsFieldPostModel.getFieldMap());
                dmsField.setUpdatedBy(userID);
                dmsField.setUpdatedDate(LocalDateTime.now());
                dmsField = dmsFieldService.update(dmsField);
                status = Response.Status.OK;
                responseData.put("data", dmsFieldService.tranformToModel(dmsField));
                responseData.put("message", "DmsField updeted by id success.");
            }
            responseData.put("success", true);
        }catch(Exception ex){
            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
        value = "Method for get DmsField by id", 
        notes = "ขอข้อมูลรายงาน ด้วย DmsField id", 
        response = DmsFieldModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "DmsField by id success." ),
        @ApiResponse( code = 404, message = "DmsField by id not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "DmsField id", required = true) 
        @PathParam("id") int id
    ) {
        log.info("getById...");
        log.info("id = "+id);
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
        responseData.put("data", null);
        responseData.put("message", "DmsField by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            
             DmsFieldService dmsFieldService = new DmsFieldService();
            DmsField dmsField  = dmsFieldService.getById(id);
            if(dmsField!=null){
                status = Response.Status.OK;
                responseData.put("data", dmsFieldService.tranformToModel(dmsField));
                responseData.put("message", "DmsField by id success.");
            }
            responseData.put("success", true);
        }catch(Exception ex){
            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
     @ApiOperation(
        value = "Method for delete DmsField by id.", 
        notes = "ลบข้อมูล DmsField", 
        response = DmsFieldModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "DmsField deleted by id success." )
        ,@ApiResponse( code = 404, message = "DmsField by id not found in the database." )
        ,@ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
        @HeaderParam("userID") int userID,
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "id", value = "DmsField id", required = true) 
        @PathParam("id") int id
    ) {
        log.info("remove...");
        log.info("id = "+id);
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
        responseData.put("data", null);
        responseData.put("message", "DmsField by id not found in the database.");
        responseData.put("errorMessage", "");
        try{
            DmsFieldService dmsFieldService = new DmsFieldService();
           
            DmsField dmsField = dmsFieldService.remove(id, userID);
            if(dmsField!=null){
                status = Response.Status.OK;
                responseData.put("data", dmsFieldService.tranformToModel(dmsField));
                responseData.put("message", "DmsField deleted by id success.");
            }
            responseData.put("success", true);
        }catch(Exception ex){
            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
     @ApiOperation(
        value = "Method for list Field.", 
        notes = "รายการฟิว", 
        responseContainer = "List",
        response = DmsFieldModel.class
    )
    @ApiResponses( {
        @ApiResponse( code = 200, message = "Field list success." ),
        @ApiResponse( code = 404, message = "Field list not found in the database." ),
        @ApiResponse( code = 500, message = "Internal Server Error!" )
    } )
    @GET
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response list(
        @HeaderParam("userID") int userID,
        @BeanParam VersionModel versionModel,
        @ApiParam(name = "offset", value = "ตำแหน่งเริ่มต้น", required = true) 
        @DefaultValue("0") @QueryParam("offset") int offset,
        @ApiParam(name = "limit", value = "จำนวนข้อมูลที่ต้องการ", required = true) 
        @DefaultValue("20") @QueryParam("limit") int limit,
        @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false) 
        @DefaultValue("orderNo") @QueryParam("sort") String sort,
        @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false) 
        @DefaultValue("asc") @QueryParam("dir") String dir
    ) {
        log.info("list...");
        log.info("offset = "+offset);
        log.info("limit = "+limit);
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
        responseData.put("data", null);
        responseData.put("message", "Field list not found in the database.");
        responseData.put("errorMessage", "");
        try{
             DmsFieldService dmsFieldService = new DmsFieldService();
             List<DmsField> listField = dmsFieldService.listAll(sort, dir);
            if(!listField.isEmpty()){
                ArrayList<DmsFieldModel> listFieldModel = new ArrayList<>();
                for (DmsField field : listField) {
                    listFieldModel.add(dmsFieldService.tranformToModel(field));
                }
                status = Response.Status.OK;
                responseData.put("data", listFieldModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        }catch(Exception ex){
            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
