///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.api;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.px.admin.entity.UserProfile;
//import com.px.admin.service.UserProfileService;
//import com.px.admin.service.UserService;
//import com.px.dms.entity.DmsDocument;
//import com.px.dms.entity.DmsDocumentText;
//import com.px.dms.entity.DmsField;
//import com.px.dms.entity.DocumentTypeDetail;
//import com.px.dms.model.get.DmsDocumentTextModel;
//import com.px.dms.model.post.DmsDocumentTextPostModel;
//import com.px.dms.service.DmsDocumentService;
//import com.px.dms.service.DmsDocumentTextService;
//import com.px.dms.service.DmsFieldService;
//import com.px.dms.service.DocumentTypeDetailService;
//import com.px.service.documentfile.DocumentFileService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import javax.ws.rs.BeanParam;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.HeaderParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import org.apache.log4j.Logger;
//
///**
// *
// * @author TOP
// */
//@Api(value = "DmsDocumentText ข้อความเอกสาร")
//@Path("v1/DmsDocumentTexts")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//public class DmsDocumentTextResource {
//
//    private static final Logger log = Logger.getLogger(DmsDocumentTextResource.class.getName());
//
//    @ApiOperation(
//            value = "Method for create DmsDocumentText",
//            notes = "สร้างข้อมูลข้อความเอกสาร",
//            response = DmsDocumentTextModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "DmsDocumentText created successfully."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
//    public Response create(
//            @BeanParam DmsDocumentTextPostModel dmsDocumentTextPostModel
//    ) {
//        log.info("create...");
//        Gson gs = new GsonBuilder()
//                .setVersion(dmsDocumentTextPostModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("success", false);
//        responseData.put("message", "Internal Server Error!");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            DocumentFileService documentFileService = new DocumentFileService();
//
//            DmsDocumentText dmsDocumentText = new DmsDocumentText(dmsDocumentService.getById(dmsDocumentTextPostModel.getDocumentId()), documentFileService.getById(dmsDocumentTextPostModel.getDocumentFileId()), dmsDocumentTextPostModel.getDocumentTextData(), dmsDocumentTextPostModel.getUserId());
//            DmsDocumentTextService dmsDocumentTextService = new DmsDocumentTextService();
////            dmsDocumentText = dmsDocumentTextService.create(dmsDocumentText);
//
//            String documentTextData = dmsDocumentTextPostModel.getDocumentTextData();
//            if (documentTextData.length() > 3999) {
//                String tamp1 = documentTextData;
//                String tamp2;
//                int leng = documentTextData.length();
//                for (int i = 0; i < leng; i = i + 3999) {
//                    tamp2 = tamp1.substring(0, 3999);
//                    dmsDocumentText.setDmsDocumentTextData(tamp2);
//                    dmsDocumentText = dmsDocumentTextService.create(dmsDocumentText);
//                }
//                tamp1 = tamp1.substring(4000);
//
//            } else {
//
//                dmsDocumentText.setDmsDocumentTextData(documentTextData);
//                dmsDocumentText = dmsDocumentTextService.create(dmsDocumentText);
//            }
//
//            responseData.put("data", dmsDocumentTextService.tranformToModel(dmsDocumentText));
//
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "Report created successfully.");
//        } catch (Exception ex) {
////            log.error("Exception = "+ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//  
//    @ApiOperation(
//            value = "Method for update DmsDocumentText.",
//            notes = "แก้ไขข้อมูลข้อความเอกสาร",
//            response = DmsDocumentTextModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "Report updeted by id success."),
//        @ApiResponse(code = 404, message = "Report by id not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @PUT
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
//    @Path(value = "/{id}")
//    public Response update(
//            @ApiParam(name = "id", value = "รหัสรายงาน", required = true)
//            @PathParam("id") int id,
//            @BeanParam DmsDocumentTextPostModel dmsDocumentTextPostModel
//    ) {
//        log.info("update...");
//        log.info("id = " + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(dmsDocumentTextPostModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();     
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "DmsDocumentText by id not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocumentTextService dmsDocumentTextService = new DmsDocumentTextService();
//            DmsDocumentText dmsDocumentText = dmsDocumentTextService.getById(id);
//
//            DmsDocument dmsDocument = dmsDocumentText.getDmsDocumentId();
//            DocumentFile documentFile = dmsDocumentText.getDmsDocumentFileId();
//
//            dmsDocumentText.setDmsDocumentFileId(documentFile);
//            dmsDocumentText.setDmsDocumentId(dmsDocument);
//
//            String documentTextData = dmsDocumentTextPostModel.getDocumentTextData();
//            if (documentTextData.length() > 3999) {
//                String tamp1 = documentTextData;
//                String tamp2;
//                int leng = documentTextData.length();
//                for (int i = 0; i < leng; i = i + 3999) {
//                    tamp2 = tamp1.substring(0, 3999);
//                    dmsDocumentText.setDmsDocumentTextData(tamp2);
//                    dmsDocumentText = dmsDocumentTextService.update(dmsDocumentText);
//                    tamp1 = tamp1.substring(4000);
//                }
//
//            } else {
//
//                dmsDocumentText.setDmsDocumentTextData(documentTextData);
//                dmsDocumentText = dmsDocumentTextService.update(dmsDocumentText);
//            }
//
//            responseData.put("data", dmsDocumentTextService.tranformToModel(dmsDocumentText));
//
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            log.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for delete DmsDocumentText by id.",
//            notes = "ลบข้อมูลข้อความเอกสาร",
//            response = DmsDocumentTextModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "Report deleted by id success."),
//        @ApiResponse(code = 404, message = "Report by id not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @DELETE
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/{id}")
//    public Response remove(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "id", value = "รหัสรายงาน", required = true)
//            @PathParam("id") int id
//    ) {
//        log.info("remove...");
//        log.info("id = " + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "DmsDocumentText by id not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocumentTextService dmsDocumentTextService = new DmsDocumentTextService();
//            DmsDocumentText dmsDocumentText = dmsDocumentTextService.remove(id, userID);
//            if (dmsDocumentText != null) {
//                status = Response.Status.OK;
//                responseData.put("data", dmsDocumentTextService.tranformToModel(dmsDocumentText));
//                responseData.put("message", "Report deleted by id success.");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            log.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list DmsDocumentText.",
//            notes = "รายการข้อความเอกสาร",
//            responseContainer = "List",
//            response = DmsDocumentTextModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "Report list success."),
//        @ApiResponse(code = 404, message = "Report list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    public Response list(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "documentId", value = "เลขที่เอกสาร", required = true)
//            @QueryParam("documentId") int documentId,
//            @ApiParam(name = "documentFileId", value = "เลขที่ฟิวเอกสาร", required = true)
//            @QueryParam("documentFileId") int documentFileId
//    ) {
//        log.info("list...");
//        log.info("documentId = " + documentId);
//        log.info("documentFileId = " + documentFileId);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "Report list not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsDocumentTextService dmsDocumentTextService = new DmsDocumentTextService();
//            List<DmsDocumentText> listDmsDocumentText = dmsDocumentTextService.findListDocumentText(documentId, documentFileId);
//
//            if (!listDmsDocumentText.isEmpty()) {
//                List<DmsDocumentTextModel> listDmsDocumentTextModel = new ArrayList<>();
//                for (DmsDocumentText dmsDocumentText : listDmsDocumentText) {
//                    listDmsDocumentTextModel.add(dmsDocumentTextService.tranformToModel(dmsDocumentText));
//                }
//                status = Response.Status.OK;
//                responseData.put("data", listDmsDocumentTextModel);
//                responseData.put("message", "Report list success.");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            log.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for Search list DmsDocumentText .",
//            notes = "ค้นหา",
//            responseContainer = "List",
//            response = DmsDocumentTextModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "Search list success."),
//        @ApiResponse(code = 404, message = "Search list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/search")
//    public Response Searchlist(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "searchText", value = "searchText", required = true)
//            @QueryParam("searchText") String searchText
//    ) {
//        log.info("list...");
//        log.info("searchText = " + searchText);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "Search list not found in the database.");
//        responseData.put("errorMessage", "");
//
//        try {
//            DmsDocumentTextService dmsDocumentTextService = new DmsDocumentTextService();
//            List<DmsDocumentText> listDmsDocumentText = dmsDocumentTextService.searchDocument(searchText);
//            UserProfileService userProfileService = new UserProfileService();
//            DocumentTypeDetailService DocumentTypeDetailService = new DocumentTypeDetailService();
//            UserService userService = new UserService();
//            ArrayList hasArrayList = new ArrayList();
//            
//            if (!listDmsDocumentText.isEmpty()) {
//
//
//                int typeFolder = 1;
//              
//                List<DocumentTypeDetail> documentTypeDetail = DocumentTypeDetailService.findListByDocumentTypeId(typeFolder);
//             
//
//                DmsFieldService fielddmsService = new DmsFieldService();
//
//                for (int i = 0; i < listDmsDocumentText.size(); i++) {
//                
//                    ArrayList hasArrayList3 = new ArrayList();
//                    JsonObject dataDoc = (JsonObject) new JsonParser().parse(gs.toJson(listDmsDocumentText.get(i).getDmsDocumentId()));
//                    HashMap hashJson = new HashMap();
//                    hashJson.put("docId", listDmsDocumentText.get(i).getDmsDocumentId().getId());
//                    for (DocumentTypeDetail documentTypeDetail1 : documentTypeDetail) {
//                        
//                        DmsField fieldsDms = fielddmsService.getById(documentTypeDetail1.getDmsFieldId()); //data document
//                        String fieldMap = fieldsDms.getDmsFieldMap();
//                        String documentFieldType = fieldsDms.getDmsFieldType();
//                        String dataDocument = "";
//                        if (dataDoc.has(fieldMap)) {
//                            dataDocument = dataDoc.get(fieldMap).getAsString();
//                        } else {
//                            dataDocument = "";
//                        }
//
//                        HashMap hashJson2 = new HashMap();
//
//                        hashJson2.put("docId", listDmsDocumentText.get(i).getDmsDocumentId().getId());
//                        hashJson2.put("documentTypeDetailid", documentTypeDetail1.getId());
//                        hashJson2.put("field", fieldMap);
//                        hashJson2.put("documentTypeDetail", documentTypeDetail1.getDocumentTypeDetailName());
//                        if ("INTEGER".equals(documentFieldType) && dataDocument != null) {
//
//                            int userid = Integer.parseInt(dataDocument);
//
//
//                            UserProfile userData = new UserProfile();
//                            String userName = "";
//                            if (userid != 0) {
//                                userData = userProfileService.getByUserId(userid);
//                                userName = userData.getUserProfileFullName();
//                            }
//                            hashJson2.put("data", userName);
//                        } else {
//                            hashJson2.put("data", dataDocument);
//                        }
//                        hashJson2.put("documentFieldType", documentFieldType);
//                        hashJson2.put("columnColor", documentTypeDetail1.getDocumentTypeDetailColumnColor());
//                        hashJson2.put("columnWidth", documentTypeDetail1.getDocumentTypeDetailColumnWidth());
//                        hashJson2.put("alignment", documentTypeDetail1.getDocumentTypeDetailAlignment());
//                        hashJson2.put("default", documentTypeDetail1.getDocumentTypeDetailDefault());
//                        hashJson2.put("edit", documentTypeDetail1.getDocumentTypeDetailEdit());
//                        hashJson2.put("fontBold", documentTypeDetail1.getDocumentTypeDetailFontBold());
//                        hashJson2.put("fontColor", documentTypeDetail1.getDocumentTypeDetailFontColor());
//                        hashJson2.put("fontItalic", documentTypeDetail1.getDocumentTypeDetailFontItalic());
//                        hashJson2.put("fontUnderline", documentTypeDetail1.getDocumentTypeDetailFontUnderline());
//                        hashJson2.put("lookup", documentTypeDetail1.getDocumentTypeDetailLookup());
//                        hashJson2.put("require", documentTypeDetail1.getDocumentTypeDetailRequire());
//                        hashJson2.put("search", documentTypeDetail1.getDocumentTypeDetailSearch());
//                        hashJson2.put("showComma", documentTypeDetail1.getDocumentTypeDetailShowComma());
//                        hashJson2.put("Unique", documentTypeDetail1.getDocumentTypeDetailUnique());
//                        hashJson2.put("View", documentTypeDetail1.getDocumentTypeDetailView());
//                        hasArrayList3.add(hashJson2);
//                    }
//                    hashJson.put("td", hasArrayList3);
//                    hasArrayList.add(hashJson);
//                    status = Response.Status.OK;
//                    responseData.put("data", hasArrayList);
//                    responseData.put("message", "Search list success.");
//                }
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//}
