
package com.px.mwp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.mwp.entity.InOutField;
import com.px.mwp.model.InOutFieldModel;
import com.px.mwp.service.InOutFieldService;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "InOutField ชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก")
@Path("v1/inOutFields")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InoutFieldResource {
    private static final Logger LOG = Logger.getLogger(InoutFieldResource.class.getName());
        
    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "สร้างข้อมูลชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            notes = "สร้างข้อมูลชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            response = InOutFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "InOutField created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            InOutFieldModel inOutFieldPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(inOutFieldPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            InOutField inOutField = new InOutField();
            //get Data from InOutFieldPostModel

            inOutField.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            inOutField.setInoutFieldName(inOutFieldPostModel.getInoutFieldName());
            inOutField.setInoutFieldDescription(inOutFieldPostModel.getInoutFieldDescription());
            inOutField.setInoutFieldType(inOutFieldPostModel.getInoutFieldType());
            inOutField.setInoutFieldLength(inOutFieldPostModel.getInoutFieldLength());

            InOutFieldService inOutFieldService = new InOutFieldService();
            if (inOutField != null) {    
                inOutField = inOutFieldService.create(inOutField);
                
                inOutField.setOrderNo(inOutField.getId());
                inOutField.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                inOutField = inOutFieldService.update(inOutField);
            }
            
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("data", inOutFieldService.tranformToModel(inOutField));
            responseData.put("message", "WfField created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "ขอข้อมูลชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก ด้วย รหัสชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            notes = "ขอข้อมูลชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก ด้วย รหัสชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            response = InOutFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutField by id success."),
        @ApiResponse(code = 404, message = "InOutField by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getById...");
        LOG.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "InOutField by id not found in the database.");
        try {
            InOutFieldService inOutFieldService = new InOutFieldService();
            InOutField inOutField = inOutFieldService.getById(id);
            if (inOutField != null) {
                status = Response.Status.OK;
                responseData.put("data", inOutFieldService.tranformToModel(inOutField));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "รายการทั้งหมดประเภทของชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            notes = "รายการทั้งหมดประเภทของชื่อฟิลด์ของกล่องข้อมูลเข้า/กล่องข้อมูลออก",
            responseContainer = "List",
            response = InOutFieldModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "InOutField list success."),
        @ApiResponse(code = 404, message = "InOutField list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listAll(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.info("listAll...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "InOutField by id not found in the database.");
        try {
            InOutFieldService inOutFieldService = new InOutFieldService();
            List<InOutField> listInOutField = inOutFieldService.listAll(sort, dir);
            if (!listInOutField.isEmpty()) {
                List<InOutFieldModel> listInOutFieldModel = new ArrayList<>();
                for (InOutField inOutField : listInOutField) {
                    listInOutFieldModel.add(inOutFieldService.tranformToModel(inOutField));
                }
                status = Response.Status.OK;
                responseData.put("data", listInOutFieldModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
