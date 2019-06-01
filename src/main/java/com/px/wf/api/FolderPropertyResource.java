package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.share.model.VersionModel;
import com.px.wf.entity.FolderProperty;
import com.px.wf.model.FolderPropertyModel;
import com.px.wf.service.FolderPropertyService;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author Mali
 */
@Api(value = "FolderProperty คุณสมบัติของทะเบียน")
@Path("v1/folderPropertys")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FolderPropertyResource {

    private static final Logger log = Logger.getLogger(FolderPropertyResource.class.getName());
    
    @ApiOperation(
            value = "สร้างข้อมูลชื่อฟิลด์ของคุณสมบัติของทะเบียน",
            notes = "สร้างข้อมูลชื่อฟิลด์ของคุณสมบัติของทะเบียน",
            response = FolderPropertyModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "FolderProperty created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            FolderPropertyModel folderPropertyPostModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(folderPropertyPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();        
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            FolderPropertyService folderPropertyService = new FolderPropertyService();
            FolderProperty folderProperty = new FolderProperty();
            //get Data from FolderPropertyPostModel
            //userID = 1;

            folderProperty.setCreatedBy(folderPropertyPostModel.getUserId());
            folderProperty.setFolderPropertyFolderId(folderPropertyPostModel.getFolderPropertyFolderId());
            folderProperty.setFolderPropertyFieldId(folderPropertyPostModel.getFolderPropertyFieldId());
            folderProperty.setFolderPropertyType(folderPropertyPostModel.getFolderPropertyType());
            folderProperty.setFolderPropertyFieldName(folderPropertyPostModel.getFolderPropertyFieldName());
            folderProperty.setFolderPropertyFieldDescription(folderPropertyPostModel.getFolderPropertyFieldDescription());
            folderProperty.setFolderPropertyFieldType(folderPropertyPostModel.getFolderPropertyFieldType());
            folderProperty.setFolderPropertyFieldLength(folderPropertyPostModel.getFolderPropertyFieldLength());
            folderProperty.setFolderPropertyPList(folderPropertyPostModel.getFolderPropertyPList());
            folderProperty.setFolderPropertyPListWidth(folderPropertyPostModel.getFolderPropertyPListWidth());
            folderProperty.setFolderPropertyPSearch(folderPropertyPostModel.getFolderPropertyPSearch());
            folderProperty.setFolderPropertyPView(folderPropertyPostModel.getFolderPropertyPView());
            folderProperty.setFolderPropertyPLookupId(folderPropertyPostModel.getFolderPropertyPLookupId());

            if (folderProperty != null) {
                folderProperty = folderPropertyService.create(folderProperty);
                
                folderProperty.setOrderNo(folderProperty.getId());
//                folderPropertyService.update(folderProperty);
            }

            status = Response.Status.CREATED;
            responseData.put("data", folderPropertyService.tranformToModel(folderProperty));
            responseData.put("success", true);
            responseData.put("message", "FolderProperty created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "ขอข้อมูลคุณสมบัติของทะเบียน ด้วย รหัสคุณสมบัติของทะเบียน",
            notes = "ขอข้อมูลคุณสมบัติของทะเบียน ด้วย รหัสคุณสมบัติของทะเบียน",
            response = FolderPropertyModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FolderProperty by id success."),
        @ApiResponse(code = 404, message = "FolderProperty by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสคุณสมบัติของทะเบียน", required = true)
            @PathParam("id") int id
    ) {
        log.info("getById...");
        log.info("id = " + id);
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
        responseData.put("message", "FolderProperty by id not found in the database.");
        try {
            FolderPropertyService folderPropertyService = new FolderPropertyService();
            FolderProperty folderProperty = folderPropertyService.getById(id);
            if (folderProperty != null) {
                status = Response.Status.OK;
                responseData.put("data", folderPropertyService.tranformToModel(folderProperty));
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "แก้ไขข้อมูลคุณสมบัติของทะเบียน",
            notes = "แก้ไขข้อมูลคุณสมบัติของทะเบียน",
            response = FolderPropertyModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FolderProperty updeted by id success."),
        @ApiResponse(code = 404, message = "FolderProperty by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสสารบรรณ", required = true)
            @PathParam("id") int id,
            FolderPropertyModel folderPropertyPostModel
    ) {
        log.info("update...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(folderPropertyPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "FolderProperty by id not found in the database.");
        try {
            FolderPropertyService folderPropertyService = new FolderPropertyService();
            FolderProperty folderProperty = folderPropertyService.getById(id);
            if (folderProperty != null) {

                folderProperty.setCreatedBy(folderPropertyPostModel.getUserId());
                folderProperty.setFolderPropertyFolderId(folderPropertyPostModel.getFolderPropertyFolderId());
                folderProperty.setFolderPropertyFieldId(folderPropertyPostModel.getFolderPropertyFieldId());
                folderProperty.setFolderPropertyType(folderPropertyPostModel.getFolderPropertyType());
                folderProperty.setFolderPropertyFieldName(folderPropertyPostModel.getFolderPropertyFieldName());
                folderProperty.setFolderPropertyFieldDescription(folderPropertyPostModel.getFolderPropertyFieldDescription());
                folderProperty.setFolderPropertyFieldType(folderPropertyPostModel.getFolderPropertyFieldType());
                folderProperty.setFolderPropertyFieldLength(folderPropertyPostModel.getFolderPropertyFieldLength());
                folderProperty.setFolderPropertyPList(folderPropertyPostModel.getFolderPropertyPList());
                folderProperty.setFolderPropertyPListWidth(folderPropertyPostModel.getFolderPropertyPListWidth());
                folderProperty.setFolderPropertyPSearch(folderPropertyPostModel.getFolderPropertyPSearch());
                folderProperty.setFolderPropertyPView(folderPropertyPostModel.getFolderPropertyPView());
                folderProperty.setFolderPropertyPLookupId(folderPropertyPostModel.getFolderPropertyPLookupId());
                
                folderProperty.setUpdatedBy(folderPropertyPostModel.getUserId());
                folderProperty = folderPropertyService.update(folderProperty);
                status = Response.Status.OK;
                responseData.put("data", folderPropertyService.tranformToModel(folderProperty));
                responseData.put("message", "folder updeted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "รายการค้นหาหรือแสดงสารบรรณ",
            notes = "รายการค้นหาหรือแสดงสารบรรณ",
            responseContainer = "List",
            response = FolderPropertyModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "FolderProperty list success."),
        @ApiResponse(code = 404, message = "FolderProperty list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listByType(           
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "type", value = "ประเภทการเรียก All = 0,List = 1,Search = 2", required = true)
            @QueryParam("type") int type,
            @ApiParam(name = "folderId", value = "รหัสทะเบียน", required = true)
            @QueryParam("folderId") int folderId,
            @ApiParam(name = "folderPropertyType", value = "ประเภท เช่น T=สารบรรณ, Z= ถังขยะ", required = true)
            @QueryParam("folderPropertyType") String folderPropertyType
    ) {
        log.info("listByType...");
        log.info("type = " + type);
        log.info("folderId = " + folderId);
        log.info("folderPropertyType = " + folderPropertyType);
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
        responseData.put("message", "FolderProperty by id not found in the database.");
        try {
            FolderPropertyService folderPropertyService = new FolderPropertyService();
            if(folderPropertyService.checkFolderIdNoData(folderId) == true){
                folderId = 0;
            }

            List<FolderProperty> listFolderProperty = folderPropertyService.listByType(type,folderId,folderPropertyType);
            if (!listFolderProperty.isEmpty()) {
                ArrayList<FolderPropertyModel> listFolderPropertyModel = new ArrayList<>();
                for (FolderProperty folderProperty : listFolderProperty) {
                    listFolderPropertyModel.add(folderPropertyService.tranformToModel(folderProperty));
                }
                listFolderPropertyModel.trimToSize();
                status = Response.Status.OK;
                responseData.put("data", listFolderPropertyModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
