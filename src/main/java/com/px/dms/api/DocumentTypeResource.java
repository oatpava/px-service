/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.dms.entity.DocumentType;
import com.px.dms.model.DocumentTypeModel;

import com.px.dms.service.DocumentTypeService;
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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;

/**
 *
 * @author TOP
 */
@Api(value = "DocumentType ประเภทเอกสาร")
@Path("v1/documentTypes")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DocumentTypeResource {
    
    private static final Logger log = Logger.getLogger(DocumentTypeResource.class.getName());
    
    @ApiOperation(
            value = "Method for create DocumentType",
            notes = "สร้างประเภทเอกสาร",
            response = DocumentTypeResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "DocumentType created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            DocumentTypeModel documentTypePostModel
    ) {
        log.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
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
//            System.out.println("0000000000");
            DocumentType documentType = new DocumentType();
//            documentTypePostModel.getDocumentTypeName(), documentTypePostModel.getDocumentTypeDescription(), userID
            documentType.setDocumentTypeName(documentTypePostModel.getDocumentTypeName());
            documentType.setDocumentTypeDescription(documentTypePostModel.getDocumentTypeDescription());
            documentType.setCreatedBy(userID);
            
            DocumentTypeService documentTypeService = new DocumentTypeService();
            documentType = documentTypeService.create(documentType);
            
            status = Response.Status.CREATED;
            responseData.put("data", documentTypeService.tranformToModel(documentType));
            responseData.put("success", true);
            responseData.put("message", "DocumentType created successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "Method for update DocumentType.",
            notes = "แก้ไขประเภทเอกสาร",
            response = DocumentTypeResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentType updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentType by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @HeaderParam("userID") int userID,
              @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเอกสาร", required = true)
            @PathParam("id") int id,
            DocumentTypeModel documentTypePostModel
    ) {
        log.info("update...");
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
        responseData.put("data", null);
        responseData.put("message", "DocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeService documentTypeService = new DocumentTypeService();
            DocumentType documentType = documentTypeService.getById(id);
            if (documentType != null) {
                documentType.setUpdatedBy(userID);
                documentType.setUpdatedDate(LocalDateTime.now());
                documentType.setDocumentTypeDescription(documentTypePostModel.getDocumentTypeDescription());
                documentType.setDocumentTypeName(documentTypePostModel.getDocumentTypeName());
                documentType = documentTypeService.update(documentType);
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
                responseData.put("message", "DocumentType updeted by id success.");
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
            value = "Method for delete DocumentType by id.",
            notes = "ลบประเภทเอกสาร",
            response = DocumentTypeResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "RepoDocumentTypert deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentType by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        log.info("remove...");
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
        responseData.put("data", null);
        responseData.put("message", "DocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeService documentTypeService = new DocumentTypeService();
            DocumentType documentType = documentTypeService.remove(id, userID);
            if (documentType != null) {
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
                responseData.put("message", "DocumentType deleted by id success.");
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
            value = "Method for list DocumentType.",
            notes = "รายการประเภทเอกสาร",
            responseContainer = "List",
            response = DocumentTypeResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentType list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentType list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listAll(
            @BeanParam VersionModel versionModel
    ) {
        log.info("list...");
        
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
        responseData.put("message", "DocumentType list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeService documentTypeService = new DocumentTypeService();
            List<DocumentType> listDoccumentType = documentTypeService.listAll("A", "A");
            if (!listDoccumentType.isEmpty()) {
                List<DocumentTypeModel> listDocumentTypeModel = new ArrayList<>();
                for (DocumentType documentType : listDoccumentType) {
                    listDocumentTypeModel.add(documentTypeService.tranformToModel(documentType));
                }
                status = Response.Status.OK;
                responseData.put("data", listDocumentTypeModel);
                responseData.put("message", "DocumentType list success.");
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
            value = "Method for get DocumentType.",
            notes = "รายการประเภทเอกสาร",
            responseContainer = "List",
            response = DocumentTypeResource.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentType get success.")
        ,
        @ApiResponse(code = 404, message = "DocumentType get not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
     @Path(value = "/{id}")
    public Response getById(
            
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
            @PathParam("id") int id
            
    ) {
//        log.info("list...");
            
        
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
        responseData.put("message", "DocumentType  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeService documentTypeService = new DocumentTypeService();
//            List<DocumentType> listDoccumentType = documentTypeService.listAll("A", "A");
            DocumentType documentType = documentTypeService.getById(id);
            if (documentType != null) {
                
                status = Response.Status.OK;
                responseData.put("data", documentTypeService.tranformToModel(documentType));
                responseData.put("message", "DocumentType get success.");
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
