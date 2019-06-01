/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.documentfile.api;

import com.google.gson.Gson;
import com.px.admin.service.UserService;
import com.px.documentfile.entity.DocumentFile;
import com.px.documentfile.model.DocumentFileModel;

import com.px.documentfile.service.DocumentFileService;
import com.px.share.model.VersionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
@Api(value = "DocumentFile เอกสารแนบ")
@Path("v1/documentFiles")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DocumentFileResource {
    private static final Logger LOG = Logger.getLogger(DocumentFileResource.class.getName());

    @Context 
    HttpHeaders httpHeaders;
    
    @ApiOperation(
            value = "Method for create DocumentFiles",
            notes = "สร้างข้อมูลเอกสารแนบ",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "DocumentFiles created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            DocumentFileModel documentFilePostModel
    ) {
        LOG.info("create...");
        Gson gs = new Gson();
//                .setVersion(documentFilePostModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DocumentFile DocumentFilePostModel = new DocumentFile();

            DocumentFilePostModel.setModuleId(documentFilePostModel.getModuleId());
            DocumentFilePostModel.setLinkId(documentFilePostModel.getLinkId());
            DocumentFilePostModel.setLinkId2(documentFilePostModel.getLinkId2());
            DocumentFilePostModel.setLinkType(documentFilePostModel.getLinkType());
            DocumentFilePostModel.setFileOrder(documentFilePostModel.getFileOrder());
            DocumentFilePostModel.setDocumentFileName(documentFilePostModel.getDocumentFileName());
            DocumentFilePostModel.setDocumentFileType(documentFilePostModel.getDocumentFileType());
            DocumentFilePostModel.setCaption(documentFilePostModel.getCaption());
            DocumentFilePostModel.setOwnerName(documentFilePostModel.getOwnerName());
            DocumentFilePostModel.setIsText(documentFilePostModel.getIsText());
            DocumentFilePostModel.setImageCount(documentFilePostModel.getImageCount());
            DocumentFilePostModel.setReferenceId(0);
            DocumentFilePostModel.setDocumentFileSec(documentFilePostModel.getDocumentFileSec());
            DocumentFilePostModel.setFileSize(documentFilePostModel.getFileSize());
            DocumentFilePostModel.setMd5(documentFilePostModel.getMd5());
            DocumentFilePostModel.setGroupFileId(0);
            DocumentFilePostModel.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));

            DocumentFileService DocumentFileService = new DocumentFileService();
            int a = DocumentFileService.createDocumentFile(DocumentFilePostModel);

//            DocumentFileService.saveLogForCreate(DocumentFileService.getById(a), documentFilePostModel.getClientIp());
            DocumentFile documentFile = DocumentFileService.getById(a);
            documentFile.setRemovedBy(-1);
            DocumentFileService.update(documentFile);

            responseData.put("data", a);

            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "DocumentFiles created successfully.");
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update DocumentFiles.",
            notes = "แก้ไขข้อมูลเอกสารแนบ",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFiles updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFiles by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/{id}")
    public Response update(
            DocumentFileModel documentFilePostModel
    ) {
        LOG.info("update 00...");

        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Report by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentFile DocumentFilePostModel = new DocumentFile();
            DocumentFilePostModel.setModuleId(documentFilePostModel.getModuleId());
            DocumentFilePostModel.setLinkId(documentFilePostModel.getLinkId());
            DocumentFilePostModel.setLinkId2(documentFilePostModel.getLinkId2());
            DocumentFilePostModel.setLinkType(documentFilePostModel.getLinkType());
            DocumentFilePostModel.setFileOrder(documentFilePostModel.getFileOrder());
            DocumentFilePostModel.setDocumentFileName(documentFilePostModel.getDocumentFileName());
            DocumentFilePostModel.setDocumentFileType(documentFilePostModel.getDocumentFileType());
            DocumentFilePostModel.setCaption(documentFilePostModel.getCaption());
            DocumentFilePostModel.setOwnerName(documentFilePostModel.getOwnerName());
            DocumentFilePostModel.setIsText(documentFilePostModel.getIsText());
            DocumentFilePostModel.setImageCount(documentFilePostModel.getImageCount());
            DocumentFilePostModel.setReferenceId(0);
            DocumentFilePostModel.setDocumentFileSec(documentFilePostModel.getDocumentFileSec());
            DocumentFilePostModel.setFileSize(documentFilePostModel.getFileSize());
            DocumentFilePostModel.setMd5(documentFilePostModel.getMd5());
            DocumentFilePostModel.setGroupFileId(0);
            DocumentFilePostModel.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            DocumentFilePostModel.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
           
            DocumentFileService DocumentFileService = new DocumentFileService();
            boolean a = DocumentFileService.updateDocumentFile(DocumentFilePostModel);

            responseData.put("data", a);
            status = Response.Status.OK;

            responseData.put("message", "DocumentFiles updeted by id success.");

            responseData.put("success", true);
        } catch (NumberFormatException ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete DocumentFiles.",
            notes = "ลบข้อมูลข้อมูลเอกสารแนบ",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFiles deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFiles by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/{id}")
    public Response remove(
            @ApiParam(name = "mouduleId", value = "mouduleId", required = true)
            @QueryParam("mouduleId") int mouduleId,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @QueryParam("linkId") int linkId,
            @ApiParam(name = "linkId2", value = "linkId2", required = true)
            @QueryParam("linkId2") int linkId2
    ) {
        LOG.info("remove...");
        LOG.info("mouduleId = " + mouduleId);
        LOG.info("linkId = " + linkId);
        LOG.info("linkId2 = " + linkId2);
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Report by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentFile documentFile = new DocumentFile();
            documentFile.setRemovedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            DocumentFileService DocumentFileService = new DocumentFileService();
            boolean a = DocumentFileService.removeDocumentFile(documentFile);
            if (a) {
                status = Response.Status.OK;
                responseData.put("data", a);
                responseData.put("message", "DocumentFiles deleted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete DocumentFiles by DocumentFilesid.",
            notes = "ลบข้อมูลข้อมูลเอกสารแนบ ด้วย id",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFiles deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFiles by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response removeByID(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("removeByID...");
        LOG.info("mouduleId = " + id);
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "DocumentFiles by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentFile documentFile = new DocumentFile();
            DocumentFileService DocumentFileService = new DocumentFileService();
//            DocumentFile del = DocumentFileService.getById(id);
            documentFile.setRemovedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            documentFile.setId(id);

            boolean a = DocumentFileService.removeDocumentFileById(documentFile);
            DocumentFile del = DocumentFileService.getById(id);
            if (a) {
                status = Response.Status.OK;
                responseData.put("data", a);
                responseData.put("message", "Report deleted by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update DocumentFiles by id.",
            notes = "แก้ไขข้อมูลเอกสารแนบ ด้วย id",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFiles updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFiles by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("id") int id,
            //            @ApiParam(name = "fullText", value = "fullText", required = false)
            //            @FormParam("fullText") String fullText,
            //            @ApiParam(name = "documentId", value = "documentId", required = false)
            //            @FormParam("documentId") int documentId,
            DocumentFileModel documentFilePostModel
    ) {
        LOG.info("update  by id....");
        LOG.info("id = " + id);

        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
//        responseData.put("message", "Report by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
//            String filetype = documentFilePostModel.getDocumentFileType();
            DocumentFileService DocumentFileService = new DocumentFileService();
            DocumentFile DocumentFilePostModel = new DocumentFile();
            DocumentFile dmsDocumentFileOld = DocumentFileService.getById(id);
            DocumentFilePostModel.setId(id);
            DocumentFilePostModel.setLinkType(documentFilePostModel.getLinkType());
            DocumentFilePostModel.setFileOrder(documentFilePostModel.getFileOrder());
            DocumentFilePostModel.setDocumentFileName(documentFilePostModel.getDocumentFileName());
            DocumentFilePostModel.setDocumentFileType(documentFilePostModel.getDocumentFileType());
            DocumentFilePostModel.setCaption(documentFilePostModel.getCaption());
            DocumentFilePostModel.setOwnerName(documentFilePostModel.getOwnerName());
            DocumentFilePostModel.setIsText(documentFilePostModel.getIsText());
            DocumentFilePostModel.setImageCount(documentFilePostModel.getImageCount());
//            DocumentFilePostModel.setGroupFileId(documentFilePostModel.getGroupFileId);
            DocumentFilePostModel.setDocumentFileSec(documentFilePostModel.getDocumentFileSec());
            DocumentFilePostModel.setFileSize(documentFilePostModel.getFileSize());
            DocumentFilePostModel.setMd5(documentFilePostModel.getMd5());

            DocumentFilePostModel.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));

            int a = DocumentFileService.updateDocumentFileById(DocumentFilePostModel);

            responseData.put("data", a);
            status = Response.Status.OK;

            responseData.put("message", "Report updeted by id success.");

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get DocumentFiles by id",
            notes = "ขอข้อมูลเอกสารแนบ ด้วย รหัสเอกสารแนบ",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFiles by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFiles by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @ApiParam(name = "id", value = "รหัสเอกสารแนบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getById...");
        LOG.info("id = " + id);
        Gson gs = new Gson();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "DocumentFiles by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentFileService DocumentFileService = new DocumentFileService();
            DocumentFile documentFile = new DocumentFile();
            documentFile = DocumentFileService.findDocumentFileByDocumentFileId(id);

            if (documentFile != null) {
                status = Response.Status.OK;
                responseData.put("data", documentFile);
                responseData.put("message", "DocumentFiles by id success.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list DocumentFile.",
            notes = "รายการเอกสารแนบ",
            responseContainer = "List",
            response = DocumentFileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentFile list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentFile list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response listDocumentFile(
            @ApiParam(name = "documentFileName", value = "ชื่อเอกสารแนบ", required = true)
            @QueryParam("documentFileName") String documentFileName,
            @ApiParam(name = "moduleId", value = "subModuleId", required = true)
            @QueryParam("moduleId") int moduleId,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @QueryParam("linkId") int linkId,
            @ApiParam(name = "linkId2", value = "linkId2", required = true)
            @QueryParam("linkId2") int linkId2,
            @ApiParam(name = "order", value = "order ชื่อตาม model", required = true)
            @QueryParam("order") String order,
            @ApiParam(name = "sort", value = "sort sort = 0 : น้อยไปมาก asc\n"
                    + " sort = 1 : มาก ไปน้อย des", required = true)
            @QueryParam("sort") int sort
    ) {
        LOG.info("listDocumentFile...");
        LOG.info("documentFileName = " + documentFileName);
        LOG.info("moduleId = " + moduleId);
        LOG.info("linkId = " + linkId);
        LOG.info("linkId2 = " + linkId2);
        LOG.info("order = " + order);
        LOG.info("sort = " + sort);
        Gson gs = new Gson();

        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Report by id not found in the database.");
        try {
            DocumentFileService documentFile = new DocumentFileService();
            UserService UserService = new UserService();
            List<DocumentFile> result = documentFile.ListDocumentFile(moduleId, linkId, linkId2, order, sort);

//            for (int i = 0; i < result.size(); i++) {
//                DocumentFile data = result.get(i);
//
//                int id = data.getCreatedBy();
//                User nameCreated = UserService.getById(id);
//                data.setNameCreated(nameCreated.getUserName());
//                result.set(i, data);
//
//            }
            if (result.size() > 0) {

                status = Response.Status.OK;
                responseData.put("data", result);
                responseData.put("message", "");
            }

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
