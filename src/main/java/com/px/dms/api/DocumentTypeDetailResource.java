/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.px.admin.entity.LookupDetail;
import com.px.admin.model.LookupDetailModel;
import com.px.admin.service.LookupDetailService;
import com.px.admin.service.LookupService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsField;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.dms.model.DocumentTypeDetailModel;

import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsFieldService;
import com.px.dms.service.DocumentTypeDetailService;
import com.px.share.model.VersionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author TOP
 */
@Api(value = "DocumentTypeDetail รายละเอียดประเภทเอกสาร")
@Path("v1/documentTypeDetails")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DocumentTypeDetailResource {

    private static final Logger log = Logger.getLogger(DocumentTypeDetailResource.class.getName());

    @ApiOperation(
            value = "Method for create DocumentTypeDetail",
            notes = "สร้างละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
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
            DocumentTypeDetailModel documentTypeDetailPostModel
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
//            userID =1;

            DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(documentTypeDetailPostModel.getDocumentTypeId(), documentTypeDetailPostModel.getDmsFieldId(), documentTypeDetailPostModel.getDocumentTypeDetailName(), documentTypeDetailPostModel.getDocumentTypeDetailView(), documentTypeDetailPostModel.getDocumentTypeDetailSearch(), documentTypeDetailPostModel.getDocumentTypeDetailEdit(), documentTypeDetailPostModel.getDocumentTypeDetailUnique(), documentTypeDetailPostModel.getDocumentTypeDetailRequire(), documentTypeDetailPostModel.getDocumentTypeDetailShowComma(), documentTypeDetailPostModel.getDocumentTypeDetailColumnColor(), documentTypeDetailPostModel.getDocumentTypeDetailFontColor(), documentTypeDetailPostModel.getDocumentTypeDetailFontItalic(), documentTypeDetailPostModel.getDocumentTypeDetailFontBold(), documentTypeDetailPostModel.getDocumentTypeDetailFontUnderline(), documentTypeDetailPostModel.getDocumentTypeDetailDefault(), documentTypeDetailPostModel.getDocumentTypeDetailLookup(), documentTypeDetailPostModel.getDocumentTypeDetailColumnWidth(), documentTypeDetailPostModel.getDocumentTypeDetailAlignment(), userID);

            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            documentTypeDetail = documentTypeDetailService.create(documentTypeDetail);

            status = Response.Status.CREATED;
            responseData.put("data", documentTypeDetailService.tranformToModel(documentTypeDetail));
            responseData.put("success", true);
            responseData.put("message", "DocumentTypeDetail created successfully.");
        } catch (Exception ex) {
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update DocumentTypeDetail.",
            notes = "แก้ไขข้อมูลละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail updeted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสรายละเอียดเอกสาร", required = true)
            @PathParam("id") int id,
            DocumentTypeDetailModel documentTypeDetailPostModel
    ) {
        log.info("update...");
        log.info("id === " + id);
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
        responseData.put("message", "DocumentTypeDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            DocumentTypeDetail documentTypeDetail = documentTypeDetailService.getById(id);
            if (documentTypeDetail != null) {
                documentTypeDetail.setDocumentTypeId(documentTypeDetailPostModel.getDocumentTypeId());
                documentTypeDetail.setDmsFieldId(documentTypeDetailPostModel.getDmsFieldId());
                documentTypeDetail.setDocumentTypeDetailName(documentTypeDetailPostModel.getDocumentTypeDetailName());
                documentTypeDetail.setDocumentTypeDetailView(documentTypeDetailPostModel.getDocumentTypeDetailView());
                documentTypeDetail.setDocumentTypeDetailSearch(documentTypeDetailPostModel.getDocumentTypeDetailSearch());
                documentTypeDetail.setDocumentTypeDetailEdit(documentTypeDetailPostModel.getDocumentTypeDetailEdit());
                documentTypeDetail.setDocumentTypeDetailUnique(documentTypeDetailPostModel.getDocumentTypeDetailUnique());
                documentTypeDetail.setDocumentTypeDetailRequire(documentTypeDetailPostModel.getDocumentTypeDetailRequire());
                documentTypeDetail.setDocumentTypeDetailShowComma(documentTypeDetailPostModel.getDocumentTypeDetailShowComma());
                documentTypeDetail.setDocumentTypeDetailColumnColor(documentTypeDetailPostModel.getDocumentTypeDetailColumnColor());
                documentTypeDetail.setDocumentTypeDetailFontColor(documentTypeDetailPostModel.getDocumentTypeDetailFontColor());
                documentTypeDetail.setDocumentTypeDetailFontItalic(documentTypeDetailPostModel.getDocumentTypeDetailFontItalic());
                documentTypeDetail.setDocumentTypeDetailFontBold(documentTypeDetailPostModel.getDocumentTypeDetailFontBold());
                documentTypeDetail.setDocumentTypeDetailFontUnderline(documentTypeDetailPostModel.getDocumentTypeDetailFontUnderline());
                documentTypeDetail.setDocumentTypeDetailDefault(documentTypeDetailPostModel.getDocumentTypeDetailDefault());
                documentTypeDetail.setDocumentTypeDetailLookup(documentTypeDetailPostModel.getDocumentTypeDetailLookup());
                documentTypeDetail.setDocumentTypeDetailColumnWidth(documentTypeDetailPostModel.getDocumentTypeDetailColumnWidth());
                documentTypeDetail.setDocumentTypeDetailAlignment(documentTypeDetailPostModel.getDocumentTypeDetailAlignment());

                documentTypeDetail.setUpdatedBy(userID);
                documentTypeDetail.setUpdatedDate(LocalDateTime.now());
                documentTypeDetail = documentTypeDetailService.update(documentTypeDetail);
                status = Response.Status.OK;
                responseData.put("data", documentTypeDetailService.tranformToModel(documentTypeDetail));
                responseData.put("message", "DocumentTypeDetail updeted by id success.");
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
            value = "Method for delete DocumentTypeDetail by id.",
            notes = "ลบข้อมูลละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสรายละเอียดเอกสาร", required = true)
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
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "DocumentTypeDetail by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            DocumentTypeDetail documentTypeDetail = documentTypeDetailService.remove(id, userID);
            if (documentTypeDetail != null) {
                status = Response.Status.OK;
                responseData.put("data", documentTypeDetailService.tranformToModel(documentTypeDetail));
                responseData.put("message", "DocumentTypeDetail deleted by id success.");
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
            value = "Method for list DocumentTypeDetail by id documentTypeId.",
            notes = "รายการละเอียดประเภทเอกสาร",
            responseContainer = "List",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/documentType/{id}")
    public Response list(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        log.info("list...");
//        log.info("documentTypeId = " + documentTypeId);
//        log.info("limit = "+limit);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        List<DocumentTypeDetailModel> listDocumentTypeDetailModel = new ArrayList<>();
        responseData.put("success", false);
        responseData.put("data", listDocumentTypeDetailModel);
        responseData.put("message", "documentTypeId list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            List<DocumentTypeDetail> listdocumentTypeDetail = documentTypeDetailService.findListByDocumentTypeId(id);
            if (!listdocumentTypeDetail.isEmpty()) {

                
                for (DocumentTypeDetail documentTypeDetail : listdocumentTypeDetail) {
                    listDocumentTypeDetailModel.add(documentTypeDetailService.tranformToModel(documentTypeDetail));
                }
                status = Response.Status.OK;
                responseData.put("data", listDocumentTypeDetailModel);
                responseData.put("message", "DocumentTypeDetail list success.");
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
            value = "Method for get DocumentTypeDetail by id .",
            notes = "รายการละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/DocumentTypeDetail/{documentTypeDetailId}")
    public Response listbyDocumentTypeDetailId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "documentTypeDetailId", value = "รหัสรายละเอียดเอกสาร", required = true)
            @PathParam("documentTypeDetailId") int documentTypeDetailId
    ) {
        log.info("list...");
        log.info("documentTypeId = " + documentTypeDetailId);
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
        responseData.put("message", "documentTypeId list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            DocumentTypeDetail documentTypeDetail = documentTypeDetailService.getById(documentTypeDetailId);

            status = Response.Status.OK;
            responseData.put("data", documentTypeDetailService.tranformToModel(documentTypeDetail));
            responseData.put("message", "DocumentTypeDetail list success.");

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for delete list DocumentTypeDetail by DocumentType id.",
            notes = "ลบข้อมูลละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail deleted by id success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/DocumentType/{id}")
    public Response removeByDocumentTypeId(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสประเภทเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        log.info("removeByDocumentTypeId...");
        log.info("id = " + id);
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
        responseData.put("message", "DocumentType by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            boolean documentTypeDetail = documentTypeDetailService.deleteByDocumentTypeId(id, userID);

            status = Response.Status.OK;
            responseData.put("data", documentTypeDetail);
            responseData.put("message", "list DocumentTypeDetail deleted  DocumentType by id success.");

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list DocumentType Detail map by id documentTypeId.",
            notes = "รายการละเอียดประเภทเอกสาร",
            responseContainer = "List",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/map/{documentTypeId}")
    public Response listMap(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "documentTypeId", value = "รหัสประเภทเอกสาร", required = true)
            @PathParam("documentTypeId") int documentTypeId
    ) {
        log.info("list...");
        log.info("documentTypeId = " + documentTypeId);
//        log.info("limit = "+limit);
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
        responseData.put("message", "documentTypeId list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            List<DocumentTypeDetail> listdocumentTypeDetail = documentTypeDetailService.findListByDocumentTypeId(documentTypeId);
            DmsField dmsField;
            DmsFieldService dmsFieldService = new DmsFieldService();
            LookupService lookupService = new LookupService();
            LookupDetailService lookupDetailService = new LookupDetailService();
//            LookupDetail lookupDetail = new LookupDetail();

            HashMap tempHash;
            List resultList = new ArrayList();
            for (DocumentTypeDetail documentTypeDetail1 : listdocumentTypeDetail) {

                int lookupId = documentTypeDetail1.getDocumentTypeDetailLookup();
                String lookupName ="";
                 
                List<LookupDetail> listLookupDetail = null;
                  ArrayList<LookupDetailModel> listLookupDetailModel = new ArrayList<>();
                if (lookupId > 0) {
                    lookupName = lookupService.getById(lookupId).getLookupName();
                    listLookupDetail = lookupDetailService.listAllByLookupId(lookupId, "createdDate", "asc");
                    for (LookupDetail lookupDetail : listLookupDetail) {
                    listLookupDetailModel.add(lookupDetailService.tranformToModel(lookupDetail));
                }
                    listLookupDetailModel.trimToSize();
                }

               

                dmsField = dmsFieldService.getById(documentTypeDetail1.getDmsFieldId());
                tempHash = new HashMap();
                tempHash.put("id", documentTypeDetail1.getId());
                tempHash.put("dmsFieldMap", dmsField.getDmsFieldMap());
                tempHash.put("dmsFieldType", dmsField.getDmsFieldType());
                tempHash.put("dmsFieldName", dmsField.getDmsFieldName());
                tempHash.put("documentTypeDetailName", documentTypeDetail1.getDocumentTypeDetailName());
                tempHash.put("documentTypeDetailLookup", documentTypeDetail1.getDocumentTypeDetailLookup());
                tempHash.put("lookupName", lookupName);
                tempHash.put("lookupDetail", listLookupDetailModel);
                resultList.add(tempHash);
            }
            status = Response.Status.OK;
            responseData.put("data", resultList);
            responseData.put("message", "DocumentTypeDetail list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create list DocumentTypeDetail",
            notes = "สร้างรายการละเอียดประเภทเอกสาร",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "list DocumentType created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/list")
    public Response createList(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "documentTypeId", value = "documentTypeId", required = true)
            @QueryParam("documentTypeId") int documentTypeId,
            @ApiParam(name = "listDmsFieldId", value = "listDmsFieldId", required = true)
            @QueryParam("listDmsFieldId") String listDmsFieldId,
            @ApiParam(name = "listDocumentTypeDetailName", value = "listDocumentTypeDetailName", required = true)
            @QueryParam("listDocumentTypeDetailName") String listDocumentTypeDetailName,
            @ApiParam(name = "listDocumentTypeDetailView", value = "listDocumentTypeDetailView", required = true)
            @QueryParam("listDocumentTypeDetailView") String listDocumentTypeDetailView,
            @ApiParam(name = "listDocumentTypeDetailSearch", value = "listDocumentTypeDetailSearch", required = true)
            @QueryParam("listDocumentTypeDetailSearch") String listDocumentTypeDetailSearch,
            @ApiParam(name = "listDocumentTypeDetailEdit", value = "listDocumentTypeDetailEdit", required = true)
            @QueryParam("listDocumentTypeDetailEdit") String listDocumentTypeDetailEdit,
            @ApiParam(name = "listDocumentTypeDetailUnique", value = "listDocumentTypeDetailUnique", required = true)
            @QueryParam("listDocumentTypeDetailUnique") String listDocumentTypeDetailUnique,
            @ApiParam(name = "listDocumentTypeDetailRequire", value = "listDocumentTypeDetailRequire", required = true)
            @QueryParam("listDocumentTypeDetailRequire") String listDocumentTypeDetailRequire,
            @ApiParam(name = "listDocumentTypeDetailShowComma", value = "listDocumentTypeDetailShowComma", required = true)
            @QueryParam("listDocumentTypeDetailShowComma") String listDocumentTypeDetailShowComma,
            @ApiParam(name = "listDocumentTypeDetailColumnColor", value = "listDocumentTypeDetailColumnColor", required = true)
            @QueryParam("listDocumentTypeDetailColumnColor") String listDocumentTypeDetailColumnColor,
            @ApiParam(name = "listDocumentTypeDetailFontColor", value = "listDocumentTypeDetailFontColor", required = true)
            @QueryParam("listDocumentTypeDetailFontColor") String listDocumentTypeDetailFontColor,
            @ApiParam(name = "listDocumentTypeDetailFontItalic", value = "listDocumentTypeDetailFontItalic", required = true)
            @QueryParam("listDocumentTypeDetailFontItalic") String listDocumentTypeDetailFontItalic,
            @ApiParam(name = "listDocumentTypeDetailFontBold", value = "listDocumentTypeDetailFontBold", required = true)
            @QueryParam("listDocumentTypeDetailFontBold") String listDocumentTypeDetailFontBold,
            @ApiParam(name = "listDocumentTypeDetailFontUnderline", value = "listDocumentTypeDetailFontUnderline", required = true)
            @QueryParam("listDocumentTypeDetailFontUnderline") String listDocumentTypeDetailFontUnderline,
            @ApiParam(name = "listDocumentTypeDetailDefault", value = "listDocumentTypeDetailDefault", required = true)
            @QueryParam("listDocumentTypeDetailDefault") String listDocumentTypeDetailDefault,
            @ApiParam(name = "listDocumentTypeDetailLookup", value = "listDocumentTypeDetailLookup", required = true)
            @QueryParam("listDocumentTypeDetailLookup") String listDocumentTypeDetailLookup,
            @ApiParam(name = "listDocumentTypeDetailColumnWidth", value = "listDocumentTypeDetailColumnWidth", required = true)
            @QueryParam("listDocumentTypeDetailColumnWidth") String listDocumentTypeDetailColumnWidth,
            @ApiParam(name = "listDocumentTypeDetailAlignment", value = "listDocumentTypeDetailAlignment", required = true)
            @QueryParam("listDocumentTypeDetailAlignment") String listDocumentTypeDetailAlignment
    ) {
        log.info("createList...");
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
            List<DocumentTypeDetail> listDocumentTypeDetail = new ArrayList<>();

            float documentTypeDetailOrder = 0;

            String[] dmsFieldIdArray = listDmsFieldId.split(",");
            String[] documentTypeDetailNameArray = listDocumentTypeDetailName.split(",");
            String[] documentTypeDetailViewArray = listDocumentTypeDetailView.split(",");
            String[] documentTypeDetailSearchArray = listDocumentTypeDetailSearch.split(",");
            String[] documentTypeDetailEditArray = listDocumentTypeDetailEdit.split(",");
            String[] documentTypeDetailUniqueArray = listDocumentTypeDetailUnique.split(",");
            String[] documentTypeDetailRequireArray = listDocumentTypeDetailRequire.split(",");
            String[] documentTypeDetailShowCommaArray = listDocumentTypeDetailShowComma.split(",");
            String[] documentTypeDetailColumnColorArray = listDocumentTypeDetailColumnColor.split(",");
            String[] documentTypeDetailFontColorArray = listDocumentTypeDetailFontColor.split(",");
            String[] documentTypeDetailFontItalicArray = listDocumentTypeDetailFontItalic.split(",");
            String[] documentTypeDetailFontBoldArray = listDocumentTypeDetailFontBold.split(",");
            String[] documentTypeDetailFontUnderlineArray = listDocumentTypeDetailFontUnderline.split(",");
            String[] documentTypeDetailDefaultArray = listDocumentTypeDetailDefault.split(",");
            String[] documentTypeDetailLookupArray = listDocumentTypeDetailLookup.split(",");
            String[] documentTypeDetailColumnWidthArray = listDocumentTypeDetailColumnWidth.split(",");
            String[] documentTypeDetailAlignmentArray = listDocumentTypeDetailAlignment.split(",");

            for (int i = 0; i < dmsFieldIdArray.length; i++) {
                int dmsFieldId = Integer.parseInt(dmsFieldIdArray[i]);
                documentTypeDetailOrder++;
                String documentTypeDetailName = documentTypeDetailNameArray[i];
                String documentTypeDetailView = documentTypeDetailViewArray[i];
                String documentTypeDetailSearch = documentTypeDetailSearchArray[i];
                String documentTypeDetailEdit = documentTypeDetailEditArray[i];
                String documentTypeDetailUnique = documentTypeDetailUniqueArray[i];
                String documentTypeDetailRequire = documentTypeDetailRequireArray[i];
                String documentTypeDetailShowComma = documentTypeDetailShowCommaArray[i];
                String documentTypeDetailColumnColor = documentTypeDetailColumnColorArray[i];
                String documentTypeDetailFontColor = documentTypeDetailFontColorArray[i];
                String documentTypeDetailFontItalic = documentTypeDetailFontItalicArray[i];
                String documentTypeDetailFontBold = documentTypeDetailFontBoldArray[i];
                String documentTypeDetailFontUnderline = documentTypeDetailFontUnderlineArray[i];
                String documentTypeDetailDefault = documentTypeDetailDefaultArray[i];
                int documentTypeDetailLookup = Integer.parseInt(documentTypeDetailLookupArray[i]);
                int documentTypeDetailColumnWidth = Integer.parseInt(documentTypeDetailColumnWidthArray[i]);
                String documentTypeDetailAlignment = documentTypeDetailAlignmentArray[i];

                if (documentTypeDetailName.trim().equals("")) {
                    documentTypeDetailName = "";
                }
                if (documentTypeDetailView.trim().equals("")) {
                    documentTypeDetailView = "N";
                }
                if (documentTypeDetailSearch.trim().equals("")) {
                    documentTypeDetailSearch = "N";
                }
                if (documentTypeDetailEdit.trim().equals("")) {
                    documentTypeDetailEdit = "N";
                }
                if (documentTypeDetailUnique.trim().equals("")) {
                    documentTypeDetailUnique = "N";
                }
                if (documentTypeDetailRequire.trim().equals("")) {
                    documentTypeDetailRequire = "N";
                }
                if (documentTypeDetailShowComma.trim().equals("")) {
                    documentTypeDetailShowComma = "N";
                }
                if (documentTypeDetailColumnColor.trim().equals("")) {
                    documentTypeDetailColumnColor = "";
                }
                if (documentTypeDetailFontColor.trim().equals("")) {
                    documentTypeDetailFontColor = "";
                }
                if (documentTypeDetailFontItalic.trim().equals("")) {
                    documentTypeDetailFontItalic = "N";
                }
                if (documentTypeDetailFontBold.trim().equals("")) {
                    documentTypeDetailFontBold = "N";
                }
                if (documentTypeDetailFontUnderline.trim().equals("")) {
                    documentTypeDetailFontUnderline = "N";
                }
                if (documentTypeDetailDefault.trim().equals("")) {
                    documentTypeDetailDefault = "";
                }
                if (documentTypeDetailAlignment.trim().equals("")) {
                    documentTypeDetailAlignment = "0";
                }

                DocumentTypeDetail tempDocumentTypeDetail = new DocumentTypeDetail();

                tempDocumentTypeDetail.setDocumentTypeId(documentTypeId);
                tempDocumentTypeDetail.setDmsFieldId(dmsFieldId);
                tempDocumentTypeDetail.setOrderNo(documentTypeDetailOrder);
                tempDocumentTypeDetail.setDocumentTypeDetailName(documentTypeDetailName);
                tempDocumentTypeDetail.setDocumentTypeDetailView(documentTypeDetailView);
                tempDocumentTypeDetail.setDocumentTypeDetailSearch(documentTypeDetailSearch);
                tempDocumentTypeDetail.setDocumentTypeDetailEdit(documentTypeDetailEdit);
                tempDocumentTypeDetail.setDocumentTypeDetailUnique(documentTypeDetailUnique);
                tempDocumentTypeDetail.setDocumentTypeDetailRequire(documentTypeDetailRequire);
                tempDocumentTypeDetail.setDocumentTypeDetailShowComma(documentTypeDetailShowComma);
                tempDocumentTypeDetail.setDocumentTypeDetailColumnColor(documentTypeDetailColumnColor);
                tempDocumentTypeDetail.setDocumentTypeDetailFontColor(documentTypeDetailFontColor);
                tempDocumentTypeDetail.setDocumentTypeDetailFontItalic(documentTypeDetailFontItalic);
                tempDocumentTypeDetail.setDocumentTypeDetailFontBold(documentTypeDetailFontBold);
                tempDocumentTypeDetail.setDocumentTypeDetailFontUnderline(documentTypeDetailFontUnderline);
                tempDocumentTypeDetail.setDocumentTypeDetailDefault(documentTypeDetailDefault);
                tempDocumentTypeDetail.setDocumentTypeDetailLookup(documentTypeDetailLookup);
                tempDocumentTypeDetail.setDocumentTypeDetailColumnWidth(documentTypeDetailColumnWidth);
                tempDocumentTypeDetail.setDocumentTypeDetailAlignment(documentTypeDetailAlignment);

                listDocumentTypeDetail.add(tempDocumentTypeDetail);
            }
//            DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(documentTypeDetailPostModel.getDocumentTypeId(), documentTypeDetailPostModel.getDmsFieldId(), documentTypeDetailPostModel.getDocumentTypeDetailName(), documentTypeDetailPostModel.getDocumentTypeDetailView(), documentTypeDetailPostModel.getDocumentTypeDetailSearch(), documentTypeDetailPostModel.getDocumentTypeDetailEdit(), documentTypeDetailPostModel.getDocumentTypeDetailUnique(), documentTypeDetailPostModel.getDocumentTypeDetailRequire(), documentTypeDetailPostModel.getDocumentTypeDetailShowComma(), documentTypeDetailPostModel.getDocumentTypeDetailColumnColor(), documentTypeDetailPostModel.getDocumentTypeDetailFontColor(), documentTypeDetailPostModel.getDocumentTypeDetailFontItalic(), documentTypeDetailPostModel.getDocumentTypeDetailFontBold(), documentTypeDetailPostModel.getDocumentTypeDetailFontUnderline(), documentTypeDetailPostModel.getDocumentTypeDetailDefault(), documentTypeDetailPostModel.getDocumentTypeDetailLookup(), documentTypeDetailPostModel.getDocumentTypeDetailColumnWidth(), documentTypeDetailPostModel.getDocumentTypeDetailAlignment(), documentTypeDetailPostModel.getUserId());
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            boolean result = documentTypeDetailService.createListDocumentTypeDetail(listDocumentTypeDetail, userID);
//'''''''''''''''''''''''''''''''
            status = Response.Status.CREATED;
            responseData.put("data", result);
            responseData.put("success", true);
            responseData.put("message", "list DocumentTypeDetail created successfully.");
        } catch (Exception ex) {
//             log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list DocumentType Detail + data by documentId,documentTypeId.",
            notes = "รายการละเอียดประเภทเอกสารและข้อมูล",
            responseContainer = "List",
            response = DocumentTypeDetailModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "DocumentTypeDetail list success.")
        ,
        @ApiResponse(code = 404, message = "DocumentTypeDetail list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/DocumentTypeDetailData")
    public Response listDocumentTypeDetailAndData(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "documentId", value = "รหัสเอกสาร", required = true)
            @QueryParam("documentId") int documentId,
            @ApiParam(name = "documentTypeId", value = "รหัสประเภทเอกสาร", required = true)
            @QueryParam("documentTypeId") int documentTypeId
    ) {
        log.info("findListDocumentTypeDetailAndDataByDocumentTypeId...");
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
        responseData.put("message", "documentTypeId list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsDocument result = dmsDocumentService.getById(documentId);
            DocumentTypeDetailService DocumentTypeDetailService = new DocumentTypeDetailService();
            List<DocumentTypeDetail> documentTypeDetail = DocumentTypeDetailService.findListByDocumentTypeId(documentTypeId);
            ArrayList<DocumentTypeDetailModel> listDocumentTypeDetail = new ArrayList<>();

            for (DocumentTypeDetail documentType : documentTypeDetail) {
                listDocumentTypeDetail.add(DocumentTypeDetailService.tranformToModel(documentType));
            }

            DmsFieldService fielddmsService = new DmsFieldService();
            ArrayList hasArrayList = new ArrayList();

            JsonArray arrayJsonDocumentTypeDetail = (JsonArray) new JsonParser().parse(gs.toJson(listDocumentTypeDetail));
            JsonObject dataDoc = (JsonObject) new JsonParser().parse(gs.toJson(dmsDocumentService.tranformToModel(result)));

            for (int i = 0; i < documentTypeDetail.size(); i++) {
                DmsField fieldsDms = fielddmsService.getById(documentTypeDetail.get(i).getDmsFieldId());//data document
                String fieldMap = fieldsDms.getDmsFieldMap();
                String dataDocument = "";
                if (dataDoc.has(fieldMap)) {
                    dataDocument = dataDoc.get(fieldMap).getAsString();
                } else {
                    dataDocument = "";
                }

                String dmsFieldType = fieldsDms.getDmsFieldType();

                JsonObject objectJson = (JsonObject) arrayJsonDocumentTypeDetail.get(i);
                JsonElement DocumentTypeDetailName = objectJson.get("documentTypeDetailName");
                JsonElement DocumentTypeDetailView = objectJson.get("documentTypeDetailView");
                JsonElement DocumentTypeDetailSearch = objectJson.get("documentTypeDetailSearch");
                JsonElement DocumentTypeDetailEdit = objectJson.get("documentTypeDetailEdit");
                JsonElement DocumentTypeDetailUnique = objectJson.get("documentTypeDetailUnique");
                JsonElement DocumentTypeDetailRequire = objectJson.get("documentTypeDetailRequire");
                JsonElement DocumentTypeDetailShowComma = objectJson.get("documentTypeDetailShowComma");
                JsonElement DocumentTypeDetailColumnColor = objectJson.get("documentTypeDetailColumnColor");
                JsonElement DocumentTypeDetailFontColor = objectJson.get("documentTypeDetailFontColor");
                JsonElement DocumentTypeDetailFontItalic = objectJson.get("documentTypeDetailFontItalic");
                JsonElement DocumentTypeDetailFontUnderline = objectJson.get("documentTypeDetailFontUnderline");
                JsonElement DocumentTypeDetailFontBold = objectJson.get("documentTypeDetailFontBold");
                JsonElement DocumentTypeDetailDefault = objectJson.get("documentTypeDetailDefault");
                JsonElement DocumentTypeDetailLookup = objectJson.get("documentTypeDetailLookup");
                JsonElement DocumentTypeDetailColumnWidth = objectJson.get("documentTypeDetailColumnWidth");
                JsonElement DocumentTypeDetailAlignment = objectJson.get("documentTypeDetailAlignment");
                JsonElement OrderNo = objectJson.get("orderNo");
                JsonElement DocumentTypeDetailId = objectJson.get("id");
                HashMap hashJson2 = new HashMap();
                hashJson2.put("field", fieldMap);

                hashJson2.put("data", dataDocument);

                hashJson2.put("fieldtype", dmsFieldType);

                hashJson2.put("name", DocumentTypeDetailName);
                hashJson2.put("view", DocumentTypeDetailView);
                hashJson2.put("search", DocumentTypeDetailSearch);
                hashJson2.put("edit", DocumentTypeDetailEdit);
                hashJson2.put("unique", DocumentTypeDetailUnique);
                hashJson2.put("require", DocumentTypeDetailRequire);
                hashJson2.put("comma", DocumentTypeDetailShowComma);
                hashJson2.put("columncolor", DocumentTypeDetailColumnColor);
                hashJson2.put("fontcolor", DocumentTypeDetailFontColor);
                hashJson2.put("fontltalic", DocumentTypeDetailFontItalic);
                hashJson2.put("underline", DocumentTypeDetailFontUnderline);
                hashJson2.put("fontbold", DocumentTypeDetailFontBold);
                hashJson2.put("default", DocumentTypeDetailDefault);
                hashJson2.put("lookup", DocumentTypeDetailLookup);
                hashJson2.put("columnwidth", DocumentTypeDetailColumnWidth);
                hashJson2.put("alignment", DocumentTypeDetailAlignment);
                hashJson2.put("orderno", OrderNo);
                hashJson2.put("id", DocumentTypeDetailId);
                hasArrayList.add(hashJson2);

            }

            status = Response.Status.OK;
            responseData.put("data", hasArrayList);
            responseData.put("message", "DocumentTypeDetail list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
