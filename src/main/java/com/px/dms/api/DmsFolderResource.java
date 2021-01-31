/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.Submodule;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.SubmoduleService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.Auth;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleAuthTemplate;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.model.AuthEnableDisableIdListModel;
import com.px.authority.model.SubmoduleAuthTemplateModel;
import com.px.authority.model.SubmoduleUserAuthModel;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleAuthTemplateService;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsFolder;
import com.px.dms.entity.DocumentType;
import com.px.dms.entity.DocumentTypeDetail;
import com.px.dms.entity.WfDocumentType;
import com.px.dms.model.DmsDocumentModel;
import com.px.dms.model.DmsFolderModel;

import com.px.dms.model.DmsSearchModel;
import com.px.dms.service.DmsDocumentService;
import com.px.dms.service.DmsFolderService;
import com.px.dms.service.DmsSearchService;
import com.px.dms.service.DocumentTypeDetailService;
import com.px.dms.service.DocumentTypeService;
import com.px.dms.service.WfDocumentTypeService;
import com.px.menu.entity.Menu;
import com.px.menu.entity.MenuType;
import com.px.menu.service.MenuService;
import com.px.menu.service.MenuTypeService;
import com.px.share.entity.BaseTreeEntity;
import com.px.share.entity.Param;
import com.px.share.entity.TempTable;
import com.px.share.model.ListOptionModel;
import com.px.share.model.TempTableModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import com.px.share.service.TempTableService;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author TOP
 */
@Api(value = "Folder ที่เก็บเอกสาร")
@Path("v1/dmsFolder")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DmsFolderResource {

    private static final Logger log = Logger.getLogger(DmsFolderResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create DmsFolder",
            notes = "สร้างที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            @HeaderParam("userID") int userID,
            DmsFolderModel dmsFolderPostModel
    ) {
        log.info("create...");

        Gson gs = new GsonBuilder()
                .setVersion(dmsFolderPostModel.getVersion())
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

            DmsFolder folder = new DmsFolder(userID, dmsFolderPostModel.getDocumentTypeId(), dmsFolderPostModel.getFolderName(), dmsFolderPostModel.getFolderType(), dmsFolderPostModel.getFolderDescription(), dmsFolderPostModel.getFolderParentId(), dmsFolderPostModel.getFolderParentType(), dmsFolderPostModel.getFolderTypeExpire(), dmsFolderPostModel.getFolderTypeExpireNumber(),
                    dmsFolderPostModel.getDmsFolderTypePreExpire(), dmsFolderPostModel.getDmsFolderTypePreExpireNumber(), dmsFolderPostModel.getDmsUserPreExpire(), dmsFolderPostModel.getDmsEmailUserPreExpire(), null);
            folder.setIcon(dmsFolderPostModel.getIcon());

            folder.setIconColor(dmsFolderPostModel.getIconColor());
            folder.setDmsEmailUserPreExpire(dmsFolderPostModel.getDmsEmailUserPreExpire());

            DmsFolder result2 = new DmsFolder();

            DmsFolderService dmsFolderService = new DmsFolderService();
            folder.setParentId(dmsFolderPostModel.getFolderParentId());

            DmsFolder parentFolder = dmsFolderService.getById(dmsFolderPostModel.getFolderParentId());

            int folderNodeLevel = parentFolder.getDmsFolderNodeLevel() + 1;

            folder.setDmsFolderNodeLevel(folderNodeLevel);

            folder.setNodeLevel(folderNodeLevel);
            folder.setCreatedBy(userID);

            folder.setDmsFolderParentKey("฿0฿");
            folder.setParentKey("฿0฿");

            folder = dmsFolderService.create(folder);

            //add log 
            dmsFolderService.saveLogForCreate(folder, httpHeaders.getHeaderString("clientIp"));

            int result = folder.getId();
            String folderParentKey = parentFolder.getDmsFolderParentKey() + result + "฿";
            int folderOrderId = result;
            folder.setDmsFolderOrderId(folderOrderId);
            folder.setDmsFolderParentKey(folderParentKey);
            folder.setParentKey(folderParentKey);

            if (result2 != null) {
                status = Response.Status.OK;
                responseData.put("data", dmsFolderService.tranformToModel2(result2));
                responseData.put("success", true);
                responseData.put("message", "folder created successfully.");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update Folder.",
            notes = "แก้ไขที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder updeted by id success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @HeaderParam("userID") int userID,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("id") int id,
            DmsFolderModel dmsFolderPostModel
    ) {
        log.info("update...");
        log.info("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(dmsFolderPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder folder = dmsFolderService.getById(id);
            if (folder != null) {
                folder.setDmsFolderDescription(dmsFolderPostModel.getFolderDescription());
                folder.setDmsFolderName(dmsFolderPostModel.getFolderName());
                folder.setDmsFolderTypeExpire(dmsFolderPostModel.getFolderTypeExpire());
                folder.setDmsFolderTypeExpireNumber(dmsFolderPostModel.getFolderTypeExpireNumber());
                folder.setDocumentTypeId(dmsFolderPostModel.getDocumentTypeId());
                folder.setDmsUserPreExpire(dmsFolderPostModel.getDmsUserPreExpire());

                folder.setUpdatedBy(userID);
                folder.setUpdatedDate(LocalDateTime.now());
                folder.setDmsEmailUserPreExpire(dmsFolderPostModel.getDmsEmailUserPreExpire());

                String searchId = folder.getDmsSearchId();
                String folderParentKey = folder.getDmsFolderParentKey();

//                if (searchId != null) {
//                    DmsSearchService dmsSearchService = new DmsSearchService();
//                    DmsSearchModel temp = dmsSearchService.changFolderToSearch(folder);
//                    DmsSearchModel result = dmsSearchService.updateDataFolder(searchId, temp, folderParentKey);
//                    folder.setDmsSearchId(result.getDmsSearchId());
//                } else {
//                    DmsSearchService dmsSearchService = new DmsSearchService();
//                    DmsSearchModel temp = dmsSearchService.changFolderToSearch(folder);
//                    DmsSearchModel result = dmsSearchService.addDataFolder(temp, folderParentKey);
//                    folder.setDmsSearchId(result.getDmsSearchId());
//                }
                String fullPath = dmsFolderService.getFullPathName(folderParentKey);
                folder.setFullPathName(fullPath);
                DmsFolder folderNew = dmsFolderService.update(folder);

                //add log
//                System.out.println("log ");
                dmsFolderService.saveLogForUpdate(folder, folderNew, dmsFolderPostModel.getClientIp());
//                System.out.println("log end");
                status = Response.Status.OK;
                responseData.put("data", dmsFolderService.tranformToModel2(folderNew));
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
            value = "Method for get Folder by id",
            notes = "ขอข้อมูลที่เก็บเอกสาร ด้วย เลขที่เก็บเอกสาร ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder by id success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
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
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();

            DmsFolder folder = dmsFolderService.getById(id);

            if (folder != null) {
                status = Response.Status.OK;
                responseData.put("data", dmsFolderService.tranformToModel2(folder));
                responseData.put("message", "Folder by id success.");
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
            value = "Method for delete Folder by id.",
            notes = "ลบข้อมูลที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder deleted by id success."),
        @ApiResponse(code = 404, message = "folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
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
        responseData.put("message", "folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder folder = dmsFolderService.remove(id, userID);
            System.out.println("userID = " + userID);
            System.out.println("folder.getRemovedBy()  = " + folder.getRemovedBy());
            DmsSearchService dmsSearchService = new DmsSearchService();
            DmsSearchModel temp = dmsSearchService.changFolderToSearch(folder);
            temp.setRemovedBy(userID);

            String searchId = folder.getDmsSearchId();
            System.out.println("searchId = " + searchId);
            System.out.println("temp remove 2 = " + temp.getRemovedBy());
            if (searchId != null) {
                DmsSearchModel result = dmsSearchService.updateData(searchId, temp);
            }
            if (folder != null) {

                //add log
                dmsFolderService.saveLogForRemove(folder, httpHeaders.getHeaderString("clientIp"));
                status = Response.Status.OK;
                responseData.put("data", dmsFolderService.tranformToModel(folder));
                responseData.put("message", "folder deleted by id success.");
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
            value = "Method for list folder by folder parenID.",
            notes = "รายการที่เก็บเอกสาร ด้วย folder parenID",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "เลขที่เก็บเอกสาร", required = true)
            @QueryParam("folderId") int folderId,
            @BeanParam ListOptionModel listOptionModel
    ) {
        log.info("list by folder parenID... ");

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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");

        try {
            DmsFolderService dmsFolderService = new DmsFolderService();

            List<DmsFolder> listFolder = dmsFolderService.listFolderByparenID(folderId, listOptionModel.getOffset(), listOptionModel.getLimit());

            if (!listFolder.isEmpty()) {

                List<DmsFolderModel> listFolderModel = new ArrayList<>();
                for (DmsFolder folder : listFolder) {

                    listFolderModel.add(dmsFolderService.tranformToModel(folder));

                }
                status = Response.Status.OK;
                responseData.put("data", listFolderModel);
                responseData.put("message", "folder list success.");
                responseData.put("success", true);
            } else {
                status = Response.Status.OK;
                responseData.put("message", "folder list success.");
                responseData.put("success", true);

            }

        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());

        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list folder by parenKey.",
            notes = "รายการที่เก็บเอกสาร ด้วย parenKey",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listparenKey")
    public Response listparenKey(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderid", value = "folderid", required = true)
            @QueryParam("folderid") int folderid,
            @ApiParam(name = "dmsFolderName", value = "ชือที่เก็บเอกสาร", required = false)
            @QueryParam("dmsFolderName") String dmsFolderName,
            @ApiParam(name = "description", value = "คำอธิบาย", required = false)
            @QueryParam("description") String description
    ) {
        log.info("list by parenID...");
        log.info("folderId = " + folderid);
        log.info("dmsFolderName = " + dmsFolderName);
        log.info("description = " + description);
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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder folder2 = dmsFolderService.getById(folderid);

            List<DmsFolder> listFolder = dmsFolderService.listFolderByparenKey(folder2.getDmsFolderParentKey(), dmsFolderName, description);
            if (!listFolder.isEmpty()) {
                List<DmsFolderModel> listFolderModel = new ArrayList<>();
                for (DmsFolder folder : listFolder) {
                    listFolderModel.add(dmsFolderService.tranformToModel(folder));
                }
                status = Response.Status.OK;
                responseData.put("data", listFolderModel);
                responseData.put("message", "folder list success.");
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
            value = "Method for check  document in folder.",
            notes = "ตรวจสอบเอกสารในที่เก็บเอกสาร ด้วย folder id",
            //        responseContainer = "List",
            response = DmsDocumentModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "check success."),
        @ApiResponse(code = 404, message = "folderid not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}/document")
    public Response checkDocInFolder(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("id") int id
    ) {
        log.info("get...");
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
        responseData.put("message", "check  not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            boolean result = dmsDocumentService.checkDocmentInFolder(id);

            status = Response.Status.OK;
            responseData.put("data", result);
            responseData.put("message", "check  document in folder by folder id success.");

            responseData.put("success", true);
        } catch (Exception ex) {
//            log.error("Exception = "+ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for move Folder.",
            notes = "ย้ายที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder move success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/M/{id}/{id2}")
    public Response move(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสารที่จะเป็นปลายทาง", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "id2", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("id2") int id2
    ) {
        log.info("move...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
//            DmsFolder folder = dmsFolderService.getById(id);//ปลายทาง
            DmsFolder folderIdMoveData = dmsFolderService.getById(id2);
            int oldParentFolderId = folderIdMoveData.getDmsFolderParentId();

//            folderIdMoveData.setUpdatedBy(userID);
//
//            folderIdMoveData.setDmsFolderNodeLevel(folder.getDmsFolderNodeLevel() + 1);
//
//            folderIdMoveData.setDmsFolderParentId(folder.getId());
//
//            folderIdMoveData.setDmsFolderParentKey(folder.getDmsFolderParentKey() + folderIdMoveData.getId() + "฿");
//            folder.setNodeLevel(folder.getDmsFolderNodeLevel() + 1);
//            folder.setParentId(folder.getId());
//            folder.setParentKey(folder.getDmsFolderParentKey() + folderIdMoveData.getId() + "฿");
//
//            boolean result = dmsFolderService.moveFolder(folderIdMoveData);
            /////
//            System.out.println("moove ");
            dmsFolderService.folderMove(id2, id, userID);

            //add log move
            dmsFolderService.saveLogForMove(dmsFolderService.getById(id2), oldParentFolderId, httpHeaders.getHeaderString("clientIp"), Integer.parseInt(httpHeaders.getHeaderString("userID")));

            status = Response.Status.CREATED;
            responseData.put("data", true);
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "Method for copy DmsFolder",
//            notes = "คัดลอกที่เก็บเอกสาร",
//            response = DmsFolderModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "dmsFolder copy successfully.")
//        ,
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Path(value = "/copy/{id}/{id2}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response copy(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร ที่จะคัดลอก", required = true)
//            @PathParam("id") int id,
//            @ApiParam(name = "id2", value = "รหัสที่เก็บเอกสาร ที่จะนำเอกสารที่คัดลอกไปวาง", required = true)
//            @PathParam("id2") int id2
//    ) {
//
//        System.out.println("copy...");
//
//        log.info("id  =" + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
//        responseData.put("data", null);
//        responseData.put("success", false);
//        responseData.put("message", "Internal Server Error!");
//        responseData.put("errorMessage", "");
//        try {
//            DmsFolder folder = new DmsFolder();
//            DmsFolder folderCopyTo = new DmsFolder();
//            DmsFolderService dmsFolderService = new DmsFolderService();
//
//            folderCopyTo = dmsFolderService.getById(id2);
//            folder = dmsFolderService.getById(id);
////            int oldParentId  = folder.getDmsFolderParentId();
//            folder.setDmsFolderParentId(id2);
//            folder.setDmsFolderNodeLevel(folderCopyTo.getDmsFolderNodeLevel() + 1);
//
//            folder.setCreatedBy(userID);
//            folder.setUpdatedBy(userID);
//
//            int result = dmsFolderService.copyFolder(folder);
//
//            String folderParentKey = folderCopyTo.getDmsFolderParentKey() + result + "฿";
//            int folderOrderId = result;
//
//            folder.setDmsFolderOrderId(folderOrderId);
//            folder.setDmsFolderParentKey(folderParentKey);
//
//            folder.setNodeLevel(folderCopyTo.getDmsFolderNodeLevel() + 1);
//            folder.setParentId(id2);
//            folder.setParentKey(folderParentKey);
//
//            folder = dmsFolderService.update(folder);
//
////            dmsFolderService.saveLogForCopy(folder,oldParentId,versionModel.getClientIp());
//            status = Response.Status.CREATED;
//            responseData.put("data", result);
//            responseData.put("success", true);
//            responseData.put("message", "folder copy successfully.");
//        } catch (Exception ex) {
//            log.error("Exception = " + ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "Method for update Folder order.",
            notes = "แก้ไขลำดับที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder order updeted by id success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/ord/{listFolder}")
    public Response updateOrder(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "listFolder", value = "listFolderId folderid,folderid,folderid,...", required = true)
            @PathParam("listFolder") String listFolder
    ) {
        log.info("updateOrder...");
        log.info("listFolder = " + listFolder);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");

        String[] listFolderId = listFolder.split(",");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            List<Integer> intList = new ArrayList<>();
            List<DmsFolder> listDmsFolder = new ArrayList<>();
            for (String listFolderId1 : listFolderId) {
                try {
                    Integer tempInt = Integer.parseInt(listFolderId1);
                    intList.add(tempInt);
                } catch (Exception e) {

                }
            }
            for (Integer intList1 : intList) {
                DmsFolder dmsFolder = dmsFolderService.getById(intList1);
                dmsFolder.setUpdatedBy(userID);
                listDmsFolder.add(dmsFolder);
            }
            status = Response.Status.CREATED;
            boolean result = dmsFolderService.orderFolder(listDmsFolder);
            responseData.put("data", result);
            responseData.put("message", "folder list success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create  ADocument type",
            notes = "สร้างประเภทเอกสาร ของ ADocument",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createADocumentType")
    public Response createADocument(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel
    ) {
        log.info("create ADocumentFolder type...");

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

            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder parentFolder = dmsFolderService.getById(1);
            DmsFolder folder = new DmsFolder();
//            Param result2 = new pa
            DocumentTypeDetailService documentTypeDetailService = new DocumentTypeDetailService();
            ParamService paramService = new ParamService();
            Param param = new Param();

            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
            DocumentType documentType = new DocumentType();
            DocumentTypeService documentTypeService = new DocumentTypeService();

            int docTypeId = documentTypeService.duplicateDoctype("ADocumentType");

            if (aDocumentTypeParam == null) {

                if (docTypeId == 0) {

                    documentType.setDocumentTypeName("ADocumentType");
                    documentType.setDocumentTypeDescription("ประเภทเอกสารสำหรับ ADocument");
                    documentType.setCreatedBy(userID);
                    documentType = documentTypeService.create(documentType);

                    param.setCreatedBy(0);
                    param.setParamName("ADOCUMENTTYPEID");
                    param.setParamValue(Integer.toString(documentType.getId()));
                    paramService.create(param);

                    DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(documentType.getId(), 1, "ชื่อเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", userID);
                    documentTypeDetail = documentTypeDetailService.create(documentTypeDetail);
                    DocumentTypeDetail ocumentTypeDetail2 = new DocumentTypeDetail(documentType.getId(), 21, "ประเภทเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", userID);
                    documentTypeDetail = documentTypeDetailService.create(ocumentTypeDetail2);
                } else {

                    param.setCreatedBy(0);
                    param.setParamName("ADOCUMENTTYPEID");
                    param.setParamValue(Integer.toString(docTypeId));
                    paramService.create(param);

                    DocumentTypeDetail documentTypeDetail = new DocumentTypeDetail(docTypeId, 1, "ชื่อเรื่อง", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", userID);
                    documentTypeDetail = documentTypeDetailService.create(documentTypeDetail);
                    DocumentTypeDetail ocumentTypeDetail2 = new DocumentTypeDetail(docTypeId, 17, "ประเภทเอกสาร", "Y", "Y", "Y", "Y", "Y", "Y", "#000000", "#000000", "Y", "Y", "Y", "Default", 0, 200, "Y", userID);
                    documentTypeDetail = documentTypeDetailService.create(ocumentTypeDetail2);

                }

            }

            if (param != null) {
                status = Response.Status.OK;
                responseData.put("data", paramService.tranformToModel(param));
                responseData.put("success", true);
                responseData.put("message", "folder created successfully.");
            } else {
                responseData.put("data", null);
                status = Response.Status.OK;
                responseData.put("success", true);
            }
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list folder by parenKey.",
            notes = "รายการที่เก็บเอกสาร ด้วย parenKey",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/ConverseDocTypeToFolder")
    public Response ConverseDocTypeToFolder(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel
    ) {
        log.info("ConverseDocTypeToFolder...");

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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            DocumentTypeService documentTypeService = new DocumentTypeService();
            List<DocumentType> listDoccumentType = documentTypeService.listAll("A", "A");

            List<DmsFolder> listFolder = new ArrayList<>();
            List<DmsFolderModel> listFolderModel = new ArrayList<>();
            DmsFolder dmsFolder = new DmsFolder();
            for (DocumentType docType : listDoccumentType) {
                dmsFolder.setId(docType.getId());
                dmsFolder.setDmsFolderType("F");
                dmsFolder.setDmsFolderParentId(1);
                dmsFolder.setDmsFolderParentKey("฿0฿");
                dmsFolder.setDmsFolderNodeLevel(2);
                dmsFolder.setCreatedBy(0);
                dmsFolder.setDmsFolderDescription(docType.getDocumentTypeDescription());
                dmsFolder.setDmsFolderName(docType.getDocumentTypeName());
                dmsFolder.setDocumentTypeId(docType.getId());
                dmsFolder.setIcon("folder");
                dmsFolder.setIconColor("#e6b800");
                listFolderModel.add(dmsFolderService.tranformToModel2(dmsFolder, "N"));

            }

            status = Response.Status.OK;
            responseData.put("data", listFolderModel);
            responseData.put("message", "folder list success.");

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create projectFolder",
            notes = "สร้าง ตู้ จาก customerName ลิ้นชัก จาก projectName ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder copy successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/ADocumentFolder2/{customerName}/{projectName}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createADocumentFolder(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "customerName", value = "ชื่อตู้", required = true)
            @PathParam("customerName") String customerName,
            @ApiParam(name = "projectName", value = "ชื่อลิ้นชัก", required = true)
            @PathParam("projectName") String projectName
    ) {

        System.out.println("createADocumentFolder...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("drawer", null);
        responseData.put("folder", null);
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            ParamService paramService = new ParamService();
//            int cabinetId = Integer.parseInt(paramService.getByParamName("ADOCUMENTFOLDERID").getParamValue());
//            System.out.println("cabinetId = " + cabinetId);
//            cabinet
//            drawer
//            folder

            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder parentCabinet = dmsFolderService.getById(1);
            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());

            DmsFolder cabinet = new DmsFolder();
            DmsFolder cabinet2 = new DmsFolder();//parentDrawer
            DmsFolder drawer = new DmsFolder();
            DmsFolder drawer2 = new DmsFolder();//parentFolder
            DmsFolder folder = new DmsFolder();
            DmsFolder folder2 = new DmsFolder();

            int duplicateCabinet = dmsFolderService.duplicateAdocFolder(1, customerName);

            if (duplicateCabinet == 0) {
                cabinet.setDmsFolderName(customerName);
                cabinet.setIconColor("#f93550");
                cabinet.setIcon("dashboard");
                cabinet.setDmsFolderType("C");
                cabinet.setDmsFolderParentType("C");
                cabinet.setCreatedBy(userID);
                int drawerNodeLevel = parentCabinet.getDmsFolderNodeLevel() + 1;
                cabinet.setDmsFolderNodeLevel(drawerNodeLevel);
                cabinet.setNodeLevel(drawerNodeLevel);
                cabinet.setParentId(parentCabinet.getId());
                cabinet.setDmsFolderParentId(parentCabinet.getId());
                cabinet.setDocumentTypeId(docType);
                cabinet.setDmsFolderParentKey("฿0฿");
                cabinet.setParentKey("฿0฿");

                cabinet = dmsFolderService.create(cabinet);
                /// log
                dmsFolderService.saveLogForCreate(cabinet, httpHeaders.getHeaderString("clientIp"));

                int cabinetId = cabinet.getId();

                String drawerParentKey = parentCabinet.getDmsFolderParentKey() + cabinetId + "฿";
                int drawerOrderId = cabinetId;
                cabinet.setDmsFolderOrderId(drawerOrderId);
                cabinet.setDmsFolderParentKey(drawerParentKey);
                cabinet.setParentKey(drawerParentKey);
                cabinet2 = dmsFolderService.createUpdateFolder(cabinet);

            } else {

                cabinet2 = dmsFolderService.getById(duplicateCabinet);
            }

            ////////////search
            int duplicateDrawer = dmsFolderService.duplicateAdocFolder(cabinet2.getId(), projectName);

            ///////////create drawer
            if (duplicateDrawer == 0) {

                int folderNodeLevel = 0;
                int folderId = 0;
                int folderOrderId = 0;
                String fParentKey = "";

                DmsFolder parentDrawer = dmsFolderService.getById(cabinet2.getId());
                drawer.setDmsFolderName(projectName);
                drawer.setIconColor("#4ee832");
                drawer.setIcon("dns");
                drawer.setDmsFolderType("D");
                drawer.setDmsFolderParentType("S");
                drawer.setCreatedBy(userID);
                int drawerNodeLevel = parentDrawer.getDmsFolderNodeLevel() + 1;
                drawer.setDmsFolderNodeLevel(drawerNodeLevel);
                drawer.setNodeLevel(drawerNodeLevel);
                drawer.setParentId(parentDrawer.getId());
                drawer.setDmsFolderParentId(parentDrawer.getId());
                drawer.setDocumentTypeId(docType);
                drawer.setDmsFolderParentKey("฿0฿");
                drawer.setParentKey("฿0฿");
                drawer = dmsFolderService.create(drawer);

                // log
                dmsFolderService.saveLogForCreate(drawer, httpHeaders.getHeaderString("clientIp"));
                int drawerId = drawer.getId();

                String drawerParentKey = parentDrawer.getDmsFolderParentKey() + drawerId + "฿";
                int drawerOrderId = drawerId;
                drawer.setDmsFolderOrderId(drawerOrderId);
                drawer.setDmsFolderParentKey(drawerParentKey);
                drawer.setParentKey(drawerParentKey);
                drawer2 = dmsFolderService.createUpdateFolder(drawer);

//                DmsFolder folderASale = new DmsFolder();
//                DmsFolder folderAProject = new DmsFolder();
//                DmsFolder folderAService = new DmsFolder();
//                DmsFolder folderAWorkFlow = new DmsFolder();
                // ASale
//                folderASale.setDmsFolderName("A Sale");
//                folderASale.setIconColor("#eff300");
//                folderASale.setIcon("folder");
//                folderASale.setDmsFolderType("F");
//                folderASale.setDmsFolderParentType("D");
//                folderASale.setCreatedBy(userID);
//                folderNodeLevel = drawer2.getDmsFolderNodeLevel() + 1;
//                folderASale.setDmsFolderNodeLevel(folderNodeLevel);
//                folderASale.setNodeLevel(folderNodeLevel);
//                folderASale.setParentId(drawer2.getId());
//                folderASale.setDmsFolderParentId(drawer2.getId());
//                folderASale.setDocumentTypeId(docType);
//                folderASale.setDmsFolderParentKey("฿0฿");
//                folderASale.setParentKey("฿0฿");
//                folderASale = dmsFolderService.create(folderASale);
//                folderId = folderASale.getId();
//
//                fParentKey = drawer2.getDmsFolderParentKey() + folderId + "฿";
//                folderOrderId = folderId;
//                folderASale.setDmsFolderOrderId(folderOrderId);
//                folderASale.setDmsFolderParentKey(fParentKey);
//                folderASale.setParentKey(fParentKey);
//                folder2 = dmsFolderService.createUpdateFolder(folderASale);
//
//                // A project
//                folderAProject.setDmsFolderName("A Project");
//                folderAProject.setIconColor("#eff300");
//                folderAProject.setIcon("folder");
//                folderAProject.setDmsFolderType("F");
//                folderAProject.setDmsFolderParentType("D");
//                folderAProject.setCreatedBy(userID);
//                folderNodeLevel = drawer2.getDmsFolderNodeLevel() + 1;
//                folderAProject.setDmsFolderNodeLevel(folderNodeLevel);
//                folderAProject.setNodeLevel(folderNodeLevel);
//                folderAProject.setParentId(drawer2.getId());
//                folderAProject.setDmsFolderParentId(drawer2.getId());
//                folderAProject.setDocumentTypeId(docType);
//                folderAProject.setDmsFolderParentKey("฿0฿");
//                folderAProject.setParentKey("฿0฿");
//                folderAProject = dmsFolderService.create(folderAProject);
//                folderId = folderAProject.getId();
//
//                fParentKey = drawer2.getDmsFolderParentKey() + folderId + "฿";
//                folderOrderId = folderId;
//                folderAProject.setDmsFolderOrderId(folderOrderId);
//                folderAProject.setDmsFolderParentKey(fParentKey);
//                folderAProject.setParentKey(fParentKey);
//                folder2 = dmsFolderService.createUpdateFolder(folderAProject);
//
//                // A Service
//                folderAService.setDmsFolderName("A Service");
//                folderAService.setIconColor("#eff300");
//                folderAService.setIcon("folder");
//                folderAService.setDmsFolderType("F");
//                folderAService.setDmsFolderParentType("D");
//                folderAService.setCreatedBy(userID);
//                folderNodeLevel = drawer2.getDmsFolderNodeLevel() + 1;
//                folderAService.setDmsFolderNodeLevel(folderNodeLevel);
//                folderAService.setNodeLevel(folderNodeLevel);
//                folderAService.setParentId(drawer2.getId());
//                folderAService.setDmsFolderParentId(drawer2.getId());
//                folderAService.setDocumentTypeId(docType);
//                folderAService.setDmsFolderParentKey("฿0฿");
//                folderAService.setParentKey("฿0฿");
//                folderAService = dmsFolderService.create(folderAService);
//                folderId = folderAService.getId();
//
//                fParentKey = drawer2.getDmsFolderParentKey() + folderId + "฿";
//                folderOrderId = folderId;
//                folderAService.setDmsFolderOrderId(folderOrderId);
//                folderAService.setDmsFolderParentKey(fParentKey);
//                folderAService.setParentKey(fParentKey);
//                folder2 = dmsFolderService.createUpdateFolder(folderAService);
//
//                // A WorkFlow
//                folderAWorkFlow.setDmsFolderName("A WorkFlow");
//                folderAWorkFlow.setIconColor("#eff300");
//                folderAWorkFlow.setIcon("folder");
//                folderAWorkFlow.setDmsFolderType("F");
//                folderAWorkFlow.setDmsFolderParentType("D");
//                folderAWorkFlow.setCreatedBy(userID);
//                folderNodeLevel = drawer2.getDmsFolderNodeLevel() + 1;
//                folderAWorkFlow.setDmsFolderNodeLevel(folderNodeLevel);
//                folderAWorkFlow.setNodeLevel(folderNodeLevel);
//                folderAWorkFlow.setParentId(drawer2.getId());
//                folderAWorkFlow.setDmsFolderParentId(drawer2.getId());
//                folderAWorkFlow.setDocumentTypeId(docType);
//                folderAWorkFlow.setDmsFolderParentKey("฿0฿");
//                folderAWorkFlow.setParentKey("฿0฿");
//                folderAWorkFlow = dmsFolderService.create(folderAWorkFlow);
//                folderId = folderAWorkFlow.getId();
//
//                fParentKey = drawer2.getDmsFolderParentKey() + folderId + "฿";
//                folderOrderId = folderId;
//                folderAWorkFlow.setDmsFolderOrderId(folderOrderId);
//                folderAWorkFlow.setDmsFolderParentKey(fParentKey);
//                folderAWorkFlow.setParentKey(fParentKey);
//                folder2 = dmsFolderService.createUpdateFolder(folderAWorkFlow);
            } else {

                drawer2 = dmsFolderService.getById(duplicateDrawer);
            }

            ///////////create folder
            status = Response.Status.CREATED;
            responseData.put("drawer", dmsFolderService.tranformToModel2(drawer2));
            responseData.put("cabinet", dmsFolderService.tranformToModel2(cabinet2));
            responseData.put("success", true);
            responseData.put("message", "folder create successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get docType form param",
            notes = "ขอข้อมูลที่เก็บเอกสาร ด้วย เลขที่เก็บเอกสาร ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "docType form param success."),
        @ApiResponse(code = 404, message = "docType form param found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "getWfdocType")
    public Response getWfdocType(
            @BeanParam VersionModel versionModel
    ) {
        log.info("docType form param...");

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
        responseData.put("message", "WfdocType not found in the database.");
        responseData.put("errorMessage", "");
        try {
            ParamService paramService = new ParamService();
            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
//            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());
            status = Response.Status.OK;
            responseData.put("data", paramService.tranformToModel(aDocumentTypeParam));
            responseData.put("message", "docType form param success.");

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create projectFolder",
            notes = "สร้าง ตู้ จาก customerName ลิ้นชัก จาก projectName และ ประเภทเอกกสาร ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder copy successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/ADocumentFolder2/{customerName}/{projectName}/{wfType}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createADocumentFolderAndWfType(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "customerName", value = "ชื่อตู้", required = true)
            @PathParam("customerName") String customerName,
            @ApiParam(name = "projectName", value = "ชื่อลิ้นชัก", required = true)
            @PathParam("projectName") String projectName,
            @ApiParam(name = "wfType", value = "ประเภทเอกสารของ WF", required = true)
            @PathParam("wfType") int wfType
    ) {

        System.out.println("createADocumentFolder...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("drawer", null);
        responseData.put("folder", null);
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            ParamService paramService = new ParamService();
//            int cabinetId = Integer.parseInt(paramService.getByParamName("ADOCUMENTFOLDERID").getParamValue());
//            System.out.println("cabinetId = " + cabinetId);
//            cabinet
//            drawer
//            folder

            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder parentCabinet = dmsFolderService.getById(1);
            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());

            DmsFolder cabinet = new DmsFolder();
            DmsFolder cabinet2 = new DmsFolder();//parentDrawer
            DmsFolder drawer = new DmsFolder();
            DmsFolder drawer2 = new DmsFolder();//parentFolder
            DmsFolder folder = new DmsFolder();
            DmsFolder folder2 = new DmsFolder();

            int duplicateCabinet = dmsFolderService.duplicateAdocFolder(1, customerName);

            if (duplicateCabinet == 0) {
                cabinet.setDmsFolderName(customerName);
                cabinet.setIconColor("#f93550");
                cabinet.setIcon("dashboard");
                cabinet.setDmsFolderType("C");
                cabinet.setDmsFolderParentType("C");
                cabinet.setCreatedBy(userID);
                int drawerNodeLevel = parentCabinet.getDmsFolderNodeLevel() + 1;
                cabinet.setDmsFolderNodeLevel(drawerNodeLevel);
                cabinet.setNodeLevel(drawerNodeLevel);
                cabinet.setParentId(parentCabinet.getId());
                cabinet.setDmsFolderParentId(parentCabinet.getId());
                cabinet.setDocumentTypeId(docType);
                cabinet.setDmsFolderParentKey("฿0฿");
                cabinet.setParentKey("฿0฿");

                cabinet = dmsFolderService.create(cabinet);
                //log
                dmsFolderService.saveLogForCreate(cabinet, httpHeaders.getHeaderString("clientIp"));

                int cabinetId = cabinet.getId();

                String drawerParentKey = parentCabinet.getDmsFolderParentKey() + cabinetId + "฿";
                int drawerOrderId = cabinetId;
                cabinet.setDmsFolderOrderId(drawerOrderId);
                cabinet.setDmsFolderParentKey(drawerParentKey);
                cabinet.setParentKey(drawerParentKey);
                cabinet2 = dmsFolderService.createUpdateFolder(cabinet);

            } else {

                cabinet2 = dmsFolderService.getById(duplicateCabinet);
            }

            ////////////search
            int duplicateDrawer = dmsFolderService.duplicateAdocFolder(cabinet2.getId(), projectName);

            ///////////create drawer
            if (duplicateDrawer == 0) {

                int folderNodeLevel = 0;
                int folderId = 0;
                int folderOrderId = 0;
                String fParentKey = "";

                DmsFolder parentDrawer = dmsFolderService.getById(cabinet2.getId());
                drawer.setDmsFolderName(projectName);
                drawer.setIconColor("#4ee832");
                drawer.setIcon("dns");
                drawer.setDmsFolderType("D");
                drawer.setDmsFolderParentType("S");
                drawer.setCreatedBy(userID);
                int drawerNodeLevel = parentDrawer.getDmsFolderNodeLevel() + 1;
                drawer.setDmsFolderNodeLevel(drawerNodeLevel);
                drawer.setNodeLevel(drawerNodeLevel);
                drawer.setParentId(parentDrawer.getId());
                drawer.setDmsFolderParentId(parentDrawer.getId());
                drawer.setDocumentTypeId(docType);
                drawer.setDmsFolderParentKey("฿0฿");
                drawer.setParentKey("฿0฿");
                drawer = dmsFolderService.create(drawer);

                //log
                dmsFolderService.saveLogForCreate(drawer, httpHeaders.getHeaderString("clientIp"));

                int drawerId = drawer.getId();

                String drawerParentKey = parentDrawer.getDmsFolderParentKey() + drawerId + "฿";
                int drawerOrderId = drawerId;
                drawer.setDmsFolderOrderId(drawerOrderId);
                drawer.setDmsFolderParentKey(drawerParentKey);
                drawer.setParentKey(drawerParentKey);
                drawer2 = dmsFolderService.createUpdateFolder(drawer);

            } else {

                drawer2 = dmsFolderService.getById(duplicateDrawer);
            }

            WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
            WfDocumentType documentType = new WfDocumentType();
            documentType = documentTypeService.getById(wfType);
            /// create folder

//            for(int i =0 ;i< documentType.getDocumentTypeCode().length() ;i=i+3){
//                String str1 = documentType.getDocumentTypeCode().substring(i,i+3);
//                System.out.println("str1 = "+str1);             
//            
//            }           
            int tempFolder = drawer2.getId();
            String[] arrB = documentType.getParentKey().split("฿");
            for (int i = 1; i < arrB.length; i++) {
                System.out.println("arrB " + i + " " + arrB[i]);

                documentType = documentTypeService.getById(Integer.parseInt(arrB[i]));
                int duplicateFolder = dmsFolderService.duplicateAdocFolder(tempFolder, documentType.getDocumentTypeName());

                System.out.println("folder Id = " + tempFolder);
                System.out.println("documentType = " + documentType.getDocumentTypeName());
                System.out.println("documentType = " + documentType);
                if (duplicateFolder == 0) {
                    // A WorkFlow
                    DmsFolder folderParent = dmsFolderService.getById(tempFolder);
                    DmsFolder folderAWorkFlow = new DmsFolder();
                    folderAWorkFlow.setDmsFolderName(documentType.getDocumentTypeName());
                    folderAWorkFlow.setWfDocumentTypeCode(documentType.getDocumentTypeCode());
                    folderAWorkFlow.setIconColor("#e6b800");
                    folderAWorkFlow.setIcon("folder");
                    folderAWorkFlow.setDmsFolderType("F");
                    folderAWorkFlow.setDmsFolderParentType("D");
                    folderAWorkFlow.setCreatedBy(userID);
                    int folderNodeLevel = folderParent.getDmsFolderNodeLevel() + 1;
                    folderAWorkFlow.setDmsFolderNodeLevel(folderNodeLevel);
                    folderAWorkFlow.setNodeLevel(folderNodeLevel);
                    folderAWorkFlow.setParentId(folderParent.getId());
                    folderAWorkFlow.setDmsFolderParentId(folderParent.getId());
                    folderAWorkFlow.setDocumentTypeId(docType);
                    folderAWorkFlow.setDmsFolderParentKey("฿0฿");
                    folderAWorkFlow.setParentKey("฿0฿");
                    folderAWorkFlow = dmsFolderService.create(folderAWorkFlow);
                    int folderId = folderAWorkFlow.getId();

                    String fParentKey = folderParent.getDmsFolderParentKey() + folderId + "฿";
                    int folderOrderId = folderId;
                    folderAWorkFlow.setDmsFolderOrderId(folderOrderId);
                    folderAWorkFlow.setDmsFolderParentKey(fParentKey);
                    folderAWorkFlow.setParentKey(fParentKey);
                    folder2 = dmsFolderService.createUpdateFolder(folderAWorkFlow);

                    //log
                    dmsFolderService.saveLogForCreate(folder2, httpHeaders.getHeaderString("clientIp"));
                    tempFolder = folderId;
//                    System.out.println("folder2 1 "+folder2.getId());
                } else {

                    tempFolder = duplicateFolder;
                    folder2 = dmsFolderService.getById(tempFolder);
//                    System.out.println("folder2 2 "+folder2.getId());
                }

            }
            System.out.println(" end createADocumentFolderAndWfType");

            status = Response.Status.CREATED;
            responseData.put("drawer", dmsFolderService.tranformToModel2(drawer2));
            responseData.put("cabinet", dmsFolderService.tranformToModel2(cabinet2));
            responseData.put("folder", dmsFolderService.tranformToModel2(folder2));
            responseData.put("success", true);
            responseData.put("message", "folder create successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create DmsFolder",
            notes = "สร้างที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Path(value = "/create2")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create2(
            @HeaderParam("userID") int userID,
            DmsFolderModel dmsFolderPostModel
    ) {
        log.info("create2...");

        Gson gs = new GsonBuilder()
                .setVersion(dmsFolderPostModel.getVersion())
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
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder result2 = new DmsFolder();
            WfDocumentType wfDocumentType = new WfDocumentType();
//            WfDocumentType documentType = new WfDocumentType();

            SubmoduleAuth submoduleAuth = new SubmoduleAuth();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();

            SubmoduleUserAuth submoduleUserAuth = new SubmoduleUserAuth();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();

            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getById(userID);

//            ParamService paramService = new ParamService();
//            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
//            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());
            WfDocumentTypeService wfDocumentTypeService = new WfDocumentTypeService();

            wfDocumentType = wfDocumentTypeService.getwftypeByname(dmsFolderPostModel.getFolderName());
//            int drawerId = dmsFolderPostModel.getFolderParentId();
            if (wfDocumentType == null) {
//                System.out.println("create folder ปกติ");//create doc ปกติ
                DmsFolder folder = new DmsFolder(userID, dmsFolderPostModel.getDocumentTypeId(), dmsFolderPostModel.getFolderName(), dmsFolderPostModel.getFolderType(), dmsFolderPostModel.getFolderDescription(), dmsFolderPostModel.getFolderParentId(), dmsFolderPostModel.getFolderParentType(), dmsFolderPostModel.getFolderTypeExpire(), dmsFolderPostModel.getFolderTypeExpireNumber(),
                        dmsFolderPostModel.getDmsFolderTypePreExpire(), dmsFolderPostModel.getDmsFolderTypePreExpireNumber(), dmsFolderPostModel.getDmsUserPreExpire(), dmsFolderPostModel.getDmsEmailUserPreExpire(), null);
                folder.setIcon(dmsFolderPostModel.getIcon());

                folder.setIconColor(dmsFolderPostModel.getIconColor());
                folder.setDmsEmailUserPreExpire(dmsFolderPostModel.getDmsEmailUserPreExpire());

//                DmsFolder result2 = new DmsFolder();
//                DmsFolderService dmsFolderService = new DmsFolderService();
                folder.setParentId(dmsFolderPostModel.getFolderParentId());

                DmsFolder parentFolder = dmsFolderService.getById(dmsFolderPostModel.getFolderParentId());

                int folderNodeLevel = parentFolder.getDmsFolderNodeLevel() + 1;

                folder.setDmsFolderNodeLevel(folderNodeLevel);

                folder.setNodeLevel(folderNodeLevel);
                folder.setCreatedBy(userID);

                folder.setDmsFolderParentKey("฿0฿");
                folder.setParentKey("฿0฿");

                folder = dmsFolderService.create(folder);

                int result = folder.getId();
                String folderParentKey = parentFolder.getDmsFolderParentKey() + result + "฿";
                int folderOrderId = result;
                folder.setDmsFolderOrderId(folderOrderId);
                folder.setDmsFolderParentKey(folderParentKey);
                folder.setParentKey(folderParentKey);

                //add seach
//                DmsSearchService dmsSearchService = new DmsSearchService();
//                DmsSearchModel temp = dmsSearchService.changFolderToSearch(folder);
//                DmsSearchModel resultSearch = dmsSearchService.addDataFolder(temp, folderParentKey);
//                folder.setDmsSearchId(resultSearch.getDmsSearchId());
//                String fullPath = dmsFolderService.getFullPathName(folderParentKey);
//                folder.setFullPathName(fullPath);
                result2 = dmsFolderService.createUpdateFolder(folder);
                //end search

                // addlog add
                dmsFolderService.saveLogForCreate(result2, httpHeaders.getHeaderString("clientIp"));

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
                submoduleUserAuth.setAuthority("1");
                submoduleUserAuth.setLinkId(result2.getId());
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuth.setUserProfile(userProfile);
                submoduleUserAuth.setCreatedBy(userID);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("F_CRE");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("F_EDIT");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("F_DEL");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("D_CRE");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("D_EDIT");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("D_DEL");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_CRE");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_EDIT");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_DEL");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_AUTH");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("D_OPEN");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_SEC1");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_SEC2");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_SEC3");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);

                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DF_SEC4");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);
                
                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_COPY");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);
                
                submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_MOVE");
                submoduleUserAuth.setSubmoduleAuth(submoduleAuth);
                submoduleUserAuthService.create(submoduleUserAuth);
            }

            status = Response.Status.OK;
            responseData.put("data", dmsFolderService.tranformToModel2(result2));
            responseData.put("success", true);
            responseData.put("message", "folder created successfully.");

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get Folder by id",
            notes = "ขอข้อมูลที่เก็บเอกสาร และ เอกสาร ด้วย เลขที่เก็บเอกสาร ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder by id success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/folderAndDoc/{id}")
    public Response getFolderAndDocById(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("id") int id,
            @BeanParam ListOptionModel listOptionModel
    ) {
        log.info("folderAndDoc...");
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
        responseData.put("folder", null);
        responseData.put("document", null);
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsDocumentService DmsDocumentService = new DmsDocumentService();

            List<DmsFolder> listFolder = dmsFolderService.listFolderByparenID(id, listOptionModel.getOffset(), listOptionModel.getLimit());
            List<DmsFolderModel> listFolderModel = new ArrayList<>();

            LocalDateTime nowDate = LocalDateTime.now();
            List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();

            List<DmsDocument> listDmsDocument = DmsDocumentService.findListDocumentS(id, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());

            if (!listDmsDocument.isEmpty()) {
//                List<DmsDocumentModel> listDmsDocumentModel = new ArrayList<>();
                for (DmsDocument dmsDocument : listDmsDocument) {
                    String isExp = "N";
                    if (dmsDocument.getDmsDocumentExpireDate() != null) {
                        if (nowDate.isBefore(dmsDocument.getDmsDocumentExpireDate())) {
                            isExp = "N";
                        } else {
                            isExp = "Y";
                        }
                    }

                    listDmsDocumentModel.add(DmsDocumentService.tranformToModel2(dmsDocument, isExp));
                }

            }
            if (!listFolder.isEmpty()) {

                for (DmsFolder folder : listFolder) {

                    listFolderModel.add(dmsFolderService.tranformToModel(folder));

                }
            }

            status = Response.Status.OK;
            responseData.put("folder", listFolderModel);
            responseData.put("document", listDmsDocumentModel);
            responseData.put("message", "Folder by id success.");
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Template of DmsFolder by SubModuleId.",
            notes = "ขอรายการสิทธิ์ระบบงานย่อยของระบบจัดเก็บด้วย รหัสระบบงานย่อย",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Template of DmsFolder list success."),
        @ApiResponse(code = 404, message = "Template of DmsFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listTemplateBySubModuleId/{submoduleCode}")
    public Response listTemplateBySubModule(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "submoduleCode", value = " code ระบบงานย่อย", required = true)
            @PathParam("submoduleCode") String submoduleCode
    ) {
        log.info("listBySubModule...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", " Template SubModuleAuth of DMSFolder by id not found in the database.");
        try {
            SubmoduleAuthTemplateService submoduleAuthTemplateService = new SubmoduleAuthTemplateService();
            SubmoduleService submoduleService = new SubmoduleService();
            Submodule submodule = submoduleService.getBySubmoduleCode(submoduleCode);
            List<SubmoduleAuthTemplate> listSubmoduleAuthTemplate = submoduleAuthTemplateService.getSubmoduleAuthTemplateBySubmodule(submodule, listOptionModel.getSort(), listOptionModel.getDir());

            if (!listSubmoduleAuthTemplate.isEmpty()) {
                List<SubmoduleAuthTemplateModel> listSubmoduleAuthModel = new ArrayList<>();

                for (SubmoduleAuthTemplate submoduleAuthTemplate : listSubmoduleAuthTemplate) {
                    listSubmoduleAuthModel.add(submoduleAuthTemplateService.tranformToModel(submoduleAuthTemplate));
                }
                status = Response.Status.OK;
                responseData.put("data", listSubmoduleAuthModel);
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
            value = "",
            notes = "from tree",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of dmsFolder list success."),
        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of dmsFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listSubmoduleUserAuthOfChildByStructureIdFromTree/{structureId}/{folderId}/{submoduleCode}")
    public Response listSubmoduleUserAuthOfChildByStructureIdFromTree(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "structureId", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "folderId", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("folderId") int folderId,
            @ApiParam(name = "submoduleCode", value = "code ระบบงานย่อย", required = true)
            @PathParam("submoduleCode") String submoduleCode
    ) {
        log.info("listSubmoduleUserAuthOfChildByStructureIdFromTree...");
        log.info("structureId = " + structureId);
        log.info("folderId = " + folderId);
        log.info("submoduleId = " + submoduleCode);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "dmsFolder by id not found in the database.");
        try {

            DmsFolderService dmsFolderService = new DmsFolderService();
            SubmoduleService subModuleService = new SubmoduleService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            StructureService structureService = new StructureService();

            DmsFolder dmsFolder = dmsFolderService.getById(folderId);
            Submodule submodule = subModuleService.getBySubmoduleCode(submoduleCode);
            Structure structure = structureService.getById(structureId);

            List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityOfChildFromTreeByStructure(submodule, structure, dmsFolder);

            if (!listSubModuleUserAuth.isEmpty()) {
                status = Response.Status.OK;
                responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth));
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
            value = " Method for create SubmoduleUserAuth",
            notes = "สร้างข้อมูลสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth created successfully."),
        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of dmsFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createSubmoduleUserAuth/{structureId}/{userProfileId}/{submoduleAuthId}/{linkId}/{authority}")
    public Response createSubmoduleUserAuth(
            @BeanParam VersionModel versionModel,
            @HeaderParam("userID") int userID,
            @ApiParam(name = "structureId", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userProfileId", value = "userProfileId", required = true)
            @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "submoduleAuthId", value = "submoduleAuthId", required = true)
            @PathParam("submoduleAuthId") int submoduleAuthId,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId,
            @ApiParam(name = "authority", value = "authority", required = true)
            @PathParam("authority") String authority
    ) {
        log.info("createSubmoduleUserAuth...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "");
        try {

            SubmoduleUserAuth submoduleUserAuth = new SubmoduleUserAuth();
//            SubmoduleUserAuthModel submoduleUserAuthModel = new SubmoduleUserAuthModel();

            submoduleUserAuth.setCreatedBy(userID);
            submoduleUserAuth.setLinkId(linkId);
            submoduleUserAuth.setAuthority(authority);

            StructureService structureService = new StructureService();
            //submoduleUserAuth.setStructure(structureService.getById(submoduleUserAuthModel.getStructureId()));
            submoduleUserAuth.setStructure(structureService.getByIdNotRemoved(structureId));
            UserProfileService userProfileService = new UserProfileService();
            //submoduleUserAuth.setUserProfile(userProfileService.getById(submoduleUserAuthModel.getUserProfileId()));
            submoduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(userProfileId));
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            submoduleUserAuth.setSubmoduleAuth(submoduleAuthService.getById(submoduleAuthId));

            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            submoduleUserAuth = submoduleUserAuthService.create(submoduleUserAuth);
            responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));

            responseData.put("message", "");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth created successfully."),
        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of dmsFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateSubmoduleUserAuth/{id}/{structureId}/{userProfileId}/{submoduleAuthId}/{linkId}/{authority}")
    public Response updateSubmoduleUserAuth(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสสิทธิการใช้ระบบงานย่อยของแต่ละผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            @HeaderParam("userID") int userID,
            @ApiParam(name = "structureId", value = "รหัสโครงสร้างหน่วยงาน", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userProfileId", value = "userProfileId", required = true)
            @PathParam("userProfileId") int userProfileId,
            @ApiParam(name = "submoduleAuthId", value = "submoduleAuthId", required = true)
            @PathParam("submoduleAuthId") int submoduleAuthId,
            @ApiParam(name = "linkId", value = "linkId", required = true)
            @PathParam("linkId") int linkId,
            @ApiParam(name = "authority", value = "authority", required = true)
            @PathParam("authority") String authority
    ) {
        log.info("createSubmoduleUserAuth...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "");
        try {

//            SubmoduleUserAuthModel submoduleUserAuthModel = new SubmoduleUserAuthModel();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleUserAuth submoduleUserAuth = submoduleUserAuthService.getById(id);
            if (submoduleUserAuth != null) {

                submoduleUserAuth.setCreatedBy(userID);
                submoduleUserAuth.setLinkId(linkId);
                submoduleUserAuth.setAuthority(authority);

                StructureService structureService = new StructureService();
                //submoduleUserAuth.setStructure(structureService.getById(submoduleUserAuthModel.getStructureId()));
                submoduleUserAuth.setStructure(structureService.getByIdNotRemoved(structureId));
                UserProfileService userProfileService = new UserProfileService();
                //submoduleUserAuth.setUserProfile(userProfileService.getById(submoduleUserAuthModel.getUserProfileId()));
                submoduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(userProfileId));
                SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
                submoduleUserAuth.setSubmoduleAuth(submoduleAuthService.getById(submoduleAuthId));

                submoduleUserAuth = submoduleUserAuthService.update(submoduleUserAuth);

                responseData.put("data", submoduleUserAuthService.tranformToModel(submoduleUserAuth));
                responseData.put("message", "");
            }

            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list folder by folder parenID + auth .",
            notes = "รายการที่เก็บเอกสาร ด้วย folder parenID +auth ",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/auth")
    public Response listWithAuth(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "เลขที่เก็บเอกสาร", required = true)
            @QueryParam("folderId") int folderId,
            @BeanParam ListOptionModel listOptionModel
    ) {
        log.info("list by folder parenID + auth ... ");

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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");

        try {

            DmsFolderService dmsFolderService = new DmsFolderService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            UserProfileService userProfileService = new UserProfileService();
            List<DmsFolder> listFolderAll = dmsFolderService.listFolderByparenID(folderId, listOptionModel.getOffset(), listOptionModel.getLimit());
            SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
            UserProfile userProfile = userProfileService.getById(userID);
            String authority = "1";

            List<DmsFolder> listFolder2All = submoduleUserAuthService.getEntityFromTreeByUserProfileAuthority(submoduleAuth, userProfile, authority, listFolderAll);
            if (!listFolder2All.isEmpty()) {

                List<DmsFolderModel> listFolderModel = new ArrayList<>();
                for (DmsFolder folder : listFolder2All) {

                    listFolderModel.add(dmsFolderService.tranformToModel(folder));

                }
                status = Response.Status.OK;
                responseData.put("data", listFolderModel);
                responseData.put("message", "folder list success.");
                responseData.put("success", true);
            } else {
                status = Response.Status.OK;
                responseData.put("message", "folder list success.");
                responseData.put("success", true);

            }

        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());

        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update All Folder Name Same WF DocumentType Code  .",
            notes = "แก้ไขชื่อ ที่เก็บเอกสาร ตาม WF DocumentType Code",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Folder updeted by id success."),
        @ApiResponse(code = 404, message = "Folder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{wfDocTypeCode}/{newName}")
    public Response updateDocName(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "wfDocTypeCode", value = "wfDocTypeCode", required = true)
            @PathParam("wfDocTypeCode") String wfDocTypeCode,
            @ApiParam(name = "newName", value = "newName", required = true)
            @PathParam("newName") String newName
    ) {
        log.info("updateDocName...");

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
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            DmsFolderService dmsFolderService = new DmsFolderService();
            List<DmsFolder> listFolderAll = dmsFolderService.getListFolderByWfDocTypeCode(wfDocTypeCode);
            if (!listFolderAll.isEmpty()) {

                List<DmsFolderModel> listFolderModel = new ArrayList<>();
                for (DmsFolder folder : listFolderAll) {
                    folder.setDmsFolderName(newName);
                    folder = dmsFolderService.update(folder);
                    listFolderModel.add(dmsFolderService.tranformToModel(folder));

                }
                responseData.put("data", listFolderModel);
                responseData.put("message", "folder updeted by id success.");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list SubmoduleUserAuth Of dmsFolder by ListUserID.",
            notes = "ขอรายการสิทธิการใช้ระบบงานย่อยของผู้ใช้งานระบบของ ด้วย รหัสผู้ใช้งานในระบบ",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of dmsFolder list success."),
        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of dmsFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listMenu/{folderId}")
    public Response listSubmoduleUserAuthByListSubmoduleAuth(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "รหัสที่เก็บเอกสาร", required = true)
            @PathParam("folderId") int folderId
    ) {
        log.info("listMenu...");
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
        responseData.put("message", "Folder by id not found in the database.");
        responseData.put("errorMessage", "");
        try {

            DmsDocumentService dmsDocumentService = new DmsDocumentService();
            DmsFolderService dmsFolderService = new DmsFolderService();

            MenuTypeService menuTypeService = new MenuTypeService();
            MenuService menuService = new MenuService();
            MenuType menuType = menuTypeService.getByMenuTypeCode("dms");
//            List<Menu> menu;
            SubmoduleService subModuleService = new SubmoduleService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            UserProfileService userProfileService = new UserProfileService();

            Submodule submodule = subModuleService.getBySubmoduleCode("dms");
            List<UserProfile> listUserProfile = new ArrayList<>();
            UserProfile userProfile = userProfileService.getById(userID);
            listUserProfile.add(userProfile);

            BaseTreeEntity treeEntity = new BaseTreeEntity() {
            };

            DmsFolder dmsFolder = dmsFolderService.getById(folderId);
            treeEntity = dmsFolder;

            List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, listUserProfile, treeEntity);
//            System.out.println("List<SubmoduleUserAuth>= " + listSubModuleUserAuth.size());
            if (!listSubModuleUserAuth.isEmpty()) {
                status = Response.Status.OK;
                List<Menu> menu = menuService.listByMenuTypeSubmoduleUserAuth(menuType, listSubModuleUserAuth, "orderNo", "asc");
//                System.out.println("List<Menu> ="+menu.size());
            boolean authCopy = false;
            boolean authMove = false;
            for (int i=0;i<listSubModuleUserAuth.size();i++) {
                if (listSubModuleUserAuth.get(i).getSubmoduleAuth().getSubmoduleAuthCode().equals("DMS_COPY")) {
                   authCopy = listSubModuleUserAuth.get(i).getAuthority().equals("1");
                } else if (listSubModuleUserAuth.get(i).getSubmoduleAuth().getSubmoduleAuthCode().equals("DMS_MOVE")) {
                    authMove = listSubModuleUserAuth.get(i).getAuthority().equals("1");
                }
            }
                responseData.put("data", menuService.tranformToModelTree(menu, 0));
                responseData.put("authCopy", authCopy);
                responseData.put("authMove", authMove);
                responseData.put("message", "");
            }

            Boolean haveDocInFolder = dmsDocumentService.checkDocmentInFolder(folderId);
            responseData.put("haveDocInFolder", haveDocInFolder);

            responseData.put("success", true);
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for copy DmsFolder",
            notes = "คัดลอกที่เก็บเอกสาร",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder copy successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/copy/{id}/{id2}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response copy(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสที่เก็บเอกสาร ที่จะคัดลอก", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "id2", value = "รหัสที่เก็บเอกสาร ที่จะนำเอกสารที่คัดลอกไปวาง", required = true)
            @PathParam("id2") int id2
    ) {

        System.out.println("copy...");

        log.info("id  =" + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("data", false);
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            DmsFolder folder = new DmsFolder();
            DmsFolder folderCopyTo = new DmsFolder();
            DmsFolderService dmsFolderService = new DmsFolderService();
            folder = dmsFolderService.createFolderCopy(id, id2, userID);

            //add log copy
            DmsFolder folder2 = dmsFolderService.getById(id);

            dmsFolderService.saveLogForCopy(folder2, id2, httpHeaders.getHeaderString("clientIp"), Integer.parseInt(httpHeaders.getHeaderString("userID")));

            status = Response.Status.CREATED;
            responseData.put("data", true);
            responseData.put("success", true);
            responseData.put("message", "folder copy successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create projectFolder",
            notes = "สร้าง ตู้ จาก customerName ลิ้นชัก จาก projectName และ ประเภทเอกกสาร ",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "dmsFolder copy successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/ADocumentFolder3/{customerName}/{projectName}/{wfType}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createADocumentFolderAndWfType3(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "customerName", value = "ชื่อตู้", required = true)
            @PathParam("customerName") String customerName,
            @ApiParam(name = "projectName", value = "ชื่อลิ้นชัก", required = true)
            @PathParam("projectName") String projectName,
            @ApiParam(name = "wfType", value = "ประเภทเอกสารของ WF 1-2-3-4", required = true)
            @PathParam("wfType") String wfType
    ) {

        System.out.println("createADocumentFolder...");

        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("drawer", null);
        responseData.put("folder", null);
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            ParamService paramService = new ParamService();
//            int cabinetId = Integer.parseInt(paramService.getByParamName("ADOCUMENTFOLDERID").getParamValue());
//            System.out.println("cabinetId = " + cabinetId);
//            cabinet
//            drawer
//            folder

            DmsFolderService dmsFolderService = new DmsFolderService();
            DmsFolder parentCabinet = dmsFolderService.getById(1);
            Param aDocumentTypeParam = paramService.getByParamName("ADOCUMENTTYPEID");
            int docType = Integer.parseInt(aDocumentTypeParam.getParamValue());

            DmsFolder cabinet = new DmsFolder();
            DmsFolder cabinet2 = new DmsFolder();//parentDrawer
            DmsFolder drawer = new DmsFolder();
            DmsFolder drawer2 = new DmsFolder();//parentFolder
            DmsFolder folder = new DmsFolder();
            DmsFolder folder2 = new DmsFolder();

            int duplicateCabinet = dmsFolderService.duplicateAdocFolder(1, customerName);
            System.out.println("duplicateCabinet = " + duplicateCabinet);
            if (duplicateCabinet == 0) {
                cabinet.setDmsFolderName(customerName);
                cabinet.setIconColor("#f93550");
                cabinet.setIcon("dashboard");
                cabinet.setDmsFolderType("C");
                cabinet.setDmsFolderParentType("C");
                cabinet.setCreatedBy(userID);
                int drawerNodeLevel = parentCabinet.getDmsFolderNodeLevel() + 1;
                cabinet.setDmsFolderNodeLevel(drawerNodeLevel);
                cabinet.setNodeLevel(drawerNodeLevel);
                cabinet.setParentId(parentCabinet.getId());
                cabinet.setDmsFolderParentId(parentCabinet.getId());
                cabinet.setDocumentTypeId(docType);
                cabinet.setDmsFolderParentKey("฿0฿");
                cabinet.setParentKey("฿0฿");

                cabinet = dmsFolderService.create(cabinet);

                int cabinetId = cabinet.getId();
                System.out.println("cabinetId = " + cabinetId);

                String drawerParentKey = parentCabinet.getDmsFolderParentKey() + cabinetId + "฿";
                int drawerOrderId = cabinetId;
                cabinet.setDmsFolderOrderId(drawerOrderId);
                cabinet.setDmsFolderParentKey(drawerParentKey);
                cabinet.setParentKey(drawerParentKey);
                System.out.println("--------------aaa");
                cabinet2 = dmsFolderService.createUpdateFolder(cabinet);
                System.out.println("--------------0");
                //log
                dmsFolderService.saveLogForCreate(cabinet2, httpHeaders.getHeaderString("clientIp"));

            } else {

                cabinet2 = dmsFolderService.getById(duplicateCabinet);
            }
            System.out.println("--------------1");
            ////////////search
            int duplicateDrawer = dmsFolderService.duplicateAdocFolder(cabinet2.getId(), projectName);
            System.out.println("duplicateDrawer = " + duplicateDrawer);
            ///////////create drawer
            if (duplicateDrawer == 0) {

                int folderNodeLevel = 0;
                int folderId = 0;
                int folderOrderId = 0;
                String fParentKey = "";

                DmsFolder parentDrawer = dmsFolderService.getById(cabinet2.getId());
                drawer.setDmsFolderName(projectName);
                drawer.setIconColor("#4ee832");
                drawer.setIcon("dns");
                drawer.setDmsFolderType("D");
                drawer.setDmsFolderParentType("S");
                drawer.setCreatedBy(userID);
                int drawerNodeLevel = parentDrawer.getDmsFolderNodeLevel() + 1;
                drawer.setDmsFolderNodeLevel(drawerNodeLevel);
                drawer.setNodeLevel(drawerNodeLevel);
                drawer.setParentId(parentDrawer.getId());
                drawer.setDmsFolderParentId(parentDrawer.getId());
                drawer.setDocumentTypeId(docType);
                drawer.setDmsFolderParentKey("฿0฿");
                drawer.setParentKey("฿0฿");
                drawer = dmsFolderService.create(drawer);

                int drawerId = drawer.getId();

                String drawerParentKey = parentDrawer.getDmsFolderParentKey() + drawerId + "฿";
                int drawerOrderId = drawerId;
                drawer.setDmsFolderOrderId(drawerOrderId);
                drawer.setDmsFolderParentKey(drawerParentKey);
                drawer.setParentKey(drawerParentKey);
                drawer2 = dmsFolderService.createUpdateFolder(drawer);

                //log 
                dmsFolderService.saveLogForCreate(drawer2, httpHeaders.getHeaderString("clientIp"));
            } else {

                drawer2 = dmsFolderService.getById(duplicateDrawer);
            }
            String[] wfTypeId = wfType.split("-");
            String returnData = "0";
            for (int j = 0; j < wfTypeId.length; j++) {
                System.out.println("wfTypeId = " + wfTypeId[j]);
                int wfType2 = Integer.parseInt(wfTypeId[j]);
                WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
                WfDocumentType documentType = new WfDocumentType();
                documentType = documentTypeService.getById(wfType2);
                /// create folder

                int tempFolder = drawer2.getId();
                String[] arrB = documentType.getParentKey().split("฿");
                for (int i = 1; i < arrB.length; i++) {
//                    System.out.println("arrB " + i + " " + arrB[i]);

                    documentType = documentTypeService.getById(Integer.parseInt(arrB[i]));
                    int duplicateFolder = dmsFolderService.duplicateAdocFolder(tempFolder, documentType.getDocumentTypeName());

                    System.out.println("folder Id = " + tempFolder);
                    System.out.println("documentType = " + documentType.getDocumentTypeName());
                    System.out.println("documentType = " + documentType);
                    System.out.println("documentType.getIsFolder() === " + documentType.getIsFolder());
                    if (documentType.getIsFolder() > 0) {
                        if (duplicateFolder == 0) {
                            // A WorkFlow
                            DmsFolder folderParent = dmsFolderService.getById(tempFolder);
                            DmsFolder folderAWorkFlow = new DmsFolder();
                            folderAWorkFlow.setDmsFolderName(documentType.getDocumentTypeName());
                            folderAWorkFlow.setWfDocumentTypeCode(documentType.getDocumentTypeCode());
                            folderAWorkFlow.setIconColor("#e6b800");
                            folderAWorkFlow.setIcon("folder");
                            folderAWorkFlow.setDmsFolderType("F");
                            folderAWorkFlow.setDmsFolderParentType("D");
                            folderAWorkFlow.setCreatedBy(userID);
                            int folderNodeLevel = folderParent.getDmsFolderNodeLevel() + 1;
                            folderAWorkFlow.setDmsFolderNodeLevel(folderNodeLevel);
                            folderAWorkFlow.setNodeLevel(folderNodeLevel);
                            folderAWorkFlow.setParentId(folderParent.getId());
                            folderAWorkFlow.setDmsFolderParentId(folderParent.getId());
                            folderAWorkFlow.setDocumentTypeId(docType);
                            folderAWorkFlow.setDmsFolderParentKey("฿0฿");
                            folderAWorkFlow.setParentKey("฿0฿");
                            folderAWorkFlow = dmsFolderService.create(folderAWorkFlow);
                            int folderId = folderAWorkFlow.getId();

                            String fParentKey = folderParent.getDmsFolderParentKey() + folderId + "฿";
                            int folderOrderId = folderId;
                            folderAWorkFlow.setDmsFolderOrderId(folderOrderId);
                            folderAWorkFlow.setDmsFolderParentKey(fParentKey);
                            folderAWorkFlow.setParentKey(fParentKey);
                            folder2 = dmsFolderService.createUpdateFolder(folderAWorkFlow);
                            tempFolder = folderId;

                            dmsFolderService.saveLogForCreate(folder2, httpHeaders.getHeaderString("clientIp"));
                        } else {

                            tempFolder = duplicateFolder;
                            folder2 = dmsFolderService.getById(tempFolder);

                        }
                    }

                }
                if (returnData.equalsIgnoreCase("0")) {
                    returnData = String.valueOf(tempFolder);
                } else {
                    returnData = returnData + "-" + tempFolder;
                }
            }

            status = Response.Status.CREATED;
            responseData.put("data", returnData);
            responseData.put("success", true);
            responseData.put("message", "folder create successfully.");
        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for add report to temp table",
            notes = "Method for add report to temp table",
            response = TempTableModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "TempTable created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/reportType/{jobType}")
    public Response addReptoTemp(
            List<DmsFolderModel> listDmsFolderModel,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        log.debug("create...");
        log.debug("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), httpHeaders.getHeaderString("clientIp"), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
//            if (listTempTable.size() != 0) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            listDmsFolderModel.forEach(model -> {
                TempTable tempTable = new TempTable();

                tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
//                tempTable.setCreatedBy(1);
//                tempTable.setComputerName("127.0.0.1");
                tempTable.setJobType(jobType);
                tempTable.setInt01(model.getId());
                tempTable.setStr01(model.getFolderName());
                tempTable.setStr02(model.getDmsEmailUserPreExpire());
                tempTable.setStr03(model.getFolderDescription());

                if (tempTable != null) {
                    tempTable = tempTableService.create(tempTable);
                    tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setOrderNo(tempTable.getId());
                    tempTable = tempTableService.update(tempTable);
                }
            });

            responseData.put("data", null);
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "TempTable created successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list folder by folder parenID + new auth + lazyload .",
            notes = "รายการที่เก็บเอกสาร ด้วย folder parenID +auth ",
            responseContainer = "List",
            response = DmsFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/authLazy")
    public Response listWithAuthLazyLoad(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "เลขที่เก็บเอกสาร", required = true)
            @QueryParam("folderId") int folderId,
            @ApiParam(name = "offset", value = "offset", required = true)
            @QueryParam("offset") int offset,
            @ApiParam(name = "limit", value = "limit", required = true)
            @QueryParam("limit") int limit
    //            @BeanParam ListOptionModel listOptionModel
    ) {
//        log.info("list by folder parenID + auth ... ");

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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");

        try {

            DmsFolderService dmsFolderService = new DmsFolderService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            UserProfileService userProfileService = new UserProfileService();
            SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
            UserProfile userProfile = userProfileService.getById(userID);
//            System.out.println("submoduleAuth - "+submoduleAuth.getId());
//            System.out.println("userProfile - "+userProfile.getId());

            AuthEnableDisableIdListModel temp = submoduleUserAuthService.getEnableDisableIdListByUserProfile(submoduleAuth, userProfile);

            int countAll = dmsFolderService.countAll(folderId);

            List<DmsFolder> listFolder2All = dmsFolderService.findListByFolderParentIdLazy(folderId, offset, limit, temp);
            if (!listFolder2All.isEmpty()) {

                List<DmsFolderModel> listFolderModel = new ArrayList<>();
                for (DmsFolder folder : listFolder2All) {

                    listFolderModel.add(dmsFolderService.tranformToModel(folder));

                }
                status = Response.Status.OK;
                responseData.put("data", listFolderModel);
                responseData.put("countAll", countAll);
                responseData.put("message", "folder list success.");
                responseData.put("success", true);
            } else {
                status = Response.Status.OK;
                responseData.put("message", "folder list success.");
                responseData.put("success", true);

            }

        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());

        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "folder list success."),
        @ApiResponse(code = 404, message = "folder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/countAll")
    public Response countAll(
            @HeaderParam("userID") int userID,
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderId", value = "เลขที่เก็บเอกสาร", required = true)
            @QueryParam("folderId") int folderId
    //            @BeanParam ListOptionModel listOptionModel
    ) {
//        log.info("list by folder parenID + auth ... ");

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
        responseData.put("message", "folder list not found in the database.");
        responseData.put("errorMessage", "");

        try {

            DmsFolderService dmsFolderService = new DmsFolderService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            UserProfileService userProfileService = new UserProfileService();
            SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
            UserProfile userProfile = userProfileService.getById(userID);
//            System.out.println("submoduleAuth - "+submoduleAuth.getId());
//            System.out.println("userProfile - "+userProfile.getId());

            AuthEnableDisableIdListModel temp = submoduleUserAuthService.getEnableDisableIdListByUserProfile(submoduleAuth, userProfile);

            int countAll= dmsFolderService.countAll(folderId, 0, 10000, temp);

            status = Response.Status.OK;
            responseData.put("data", countAll);
            responseData.put("message", "folder list success.");
            responseData.put("success", true);

        } catch (Exception ex) {
            log.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());

        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
