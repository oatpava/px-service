///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.px.dms.api;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.px.admin.entity.Submodule;
//import com.px.admin.entity.SubmoduleUserAuth;
//import com.px.admin.entity.UserProfile;
//import com.px.admin.service.UserProfileService;
//import com.px.core.entity.menu.MenuType;
//import com.px.dms.entity.DmsFolder;
//import com.px.dms.entity.DmsMenu;
//import com.px.dms.model.get.DmsMenuModel;
//import com.px.dms.model.post.DmsMenuPostModel;
//import com.px.dms.service.DmsDocumentService;
//import com.px.dms.service.DmsFolderService;
//import com.px.dms.service.DmsMenuService;
//import com.px.service.authority.SubmoduleService;
//import com.px.service.authority.SubmoduleUserAuthService;
//import com.px.service.menu.MenuService;
//import com.px.service.menu.MenuTypeService;
//import com.px.share.entity.BaseTreeEntity;
//import com.px.share.model.VersionModel;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import java.awt.Menu;
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
//import javax.ws.rs.core.Response.Status;
//import org.apache.log4j.Logger;
//import java.time.LocalDateTime;
//
///**
// *
// * @author TOP
// */
//@Api(value = "DmsMenu เมนูของระบบจัดเก็บ")
//@Path("v1/DmsMenu")
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//public class DmsMenuResource {
//
//    private static final Logger LOG = Logger.getLogger(DmsMenuResource.class.getName());
//
//    @ApiOperation(
//            value = "Method for create DmsMenu",
//            notes = "สร้างข้อมูลเมนูของระบบจักเก็บ",
//            response = DmsMenuModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "DmsMenu created successfully."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
//    public Response create(
//            @BeanParam DmsMenuPostModel dmsMenuPostModel
//    ) {
//        LOG.info("create...");
//        Gson gs = new GsonBuilder()
//                .setVersion(dmsMenuPostModel.getVersion())
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
//
//            DmsMenu dmsMenu = new DmsMenu(dmsMenuPostModel.getDmsMenuName(), dmsMenuPostModel.getDmsMenuAuthorityDetail(), dmsMenuPostModel.getDmsMenuAuthoritySystem(), dmsMenuPostModel.getDmsMenuType(), dmsMenuPostModel.getDmsMenuAuthorityOrderType(), dmsMenuPostModel.getUserId());
//            DmsMenuService dmsMenuService = new DmsMenuService();
//            dmsMenu = dmsMenuService.create(dmsMenu);
//
//            responseData.put("data", dmsMenuService.tranformToModel(dmsMenu));
//
//            status = Response.Status.CREATED;
//            responseData.put("success", true);
//            responseData.put("message", "DmsMenu created successfully.");
//        } catch (Exception ex) {
////            LOG.error("Exception = "+ex.getMessage());
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for update DmsMenu.",
//            notes = "แก้ไขข้อมูลเมนูของระบบจักเก็บ",
//            response = DmsMenuModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "DmsMenu updeted by id success."),
//        @ApiResponse(code = 404, message = "DmsMenu by id not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @PUT
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
//    @Path(value = "/{id}")
//    public Response update(
//            @ApiParam(name = "id", value = "รหัสเมนูของระบบจักเก็บ", required = true)
//            @PathParam("id") int id,
//            @BeanParam DmsMenuPostModel dmsMenuPostModel
//    ) {
//        LOG.info("update...");
//        LOG.info("id = " + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(dmsMenuPostModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "DmsMenu by id not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsMenuService dmsMenuService = new DmsMenuService();
//            DmsMenu dmsMenu = dmsMenuService.getById(id);
//           
//            if (dmsMenu != null) {
//                dmsMenu.setDmsMenuName(dmsMenuPostModel.getDmsMenuName());
//                dmsMenu.setDmsMenuAuthorityDetail(dmsMenuPostModel.getDmsMenuAuthorityDetail());
//                dmsMenu.setDmsMenuType(dmsMenuPostModel.getDmsMenuType());
//                dmsMenu.setDmsMenuAuthoritySystem(dmsMenuPostModel.getDmsMenuAuthoritySystem());
//                dmsMenu.setDmsMenuAuthorityOrderType(dmsMenuPostModel.getDmsMenuAuthorityOrderType());
//
//                dmsMenu.setUpdatedBy(dmsMenuPostModel.getUserId());
//                dmsMenu.setUpdatedDate(LocalDateTime.now());
//                dmsMenu = dmsMenuService.update(dmsMenu);
//                status = Response.Status.OK;
//                responseData.put("data", dmsMenuService.tranformToModel(dmsMenu));
//                responseData.put("message", "DmsMenu updeted by id success.");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            LOG.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for delete DmsMenu by id.",
//            notes = "ลบข้อมูลเมนูของระบบจักเก็บ",
//            response = DmsMenuModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "DmsMenu deleted by id success."),
//        @ApiResponse(code = 404, message = "DmsMenu by id not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @DELETE
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/{id}")
//    public Response remove(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "id", value = "รหัสเมนูของระบบจักเก็บ", required = true)
//            @PathParam("id") int id
//    ) {
//        LOG.info("remove...");
//        LOG.info("id = " + id);
//        Gson gs = new GsonBuilder()
//                .setVersion(dmsMenuPostModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "DmsMenu by id not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsMenuService dmsMenuService = new DmsMenuService();
//            DmsMenu dmsMenu = dmsMenuService.remove(id, userID);
//            if (dmsMenu != null) {
//                status = Response.Status.OK;
//                responseData.put("data", dmsMenuService.tranformToModel(dmsMenu));
//                responseData.put("message", "DmsMenu deleted by id success.");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            LOG.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list DmsMenu.",
//            notes = "รายการรายงาน",
//            responseContainer = "List",
//            response = DmsMenuModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "DmsMenu list success."),
//        @ApiResponse(code = 404, message = "DmsMenu list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    public Response list(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel
//    )
//    {
//        LOG.info("list...");
////        LOG.info("offset = "+offset);
////        LOG.info("limit = "+limit);
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
//        responseData.put("message", "DmsMenu list not found in the database.");
//        responseData.put("errorMessage", "");
//        try {
//            DmsMenuService dmsMenuService = new DmsMenuService();
//            List<DmsMenu> listDmsMenu = dmsMenuService.listAll();
//            if (!listDmsMenu.isEmpty()) {
//
//                List<DmsMenuModel> listDmsMenuModel = new ArrayList<>();
//                for (DmsMenu dmsMenu : listDmsMenu) {
//                    listDmsMenuModel.add(dmsMenuService.tranformToModel(dmsMenu));
//                }
//                status = Response.Status.OK;
//                responseData.put("data", listDmsMenuModel);
//                responseData.put("message", "DmsMenu list success.");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
////            LOG.error("Exception = "+ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list SubmoduleUserAuth Of dmsFolder by ListUserID.",
//            notes = "ขอรายการสิทธิการใช้ระบบงานย่อยของผู้ใช้งานระบบของ ด้วย รหัสผู้ใช้งานในระบบ",
//            responseContainer = "List",
//            response = DmsMenuModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of dmsFolder list success."),
//        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of dmsFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listSubmoduleUserAuthByListSubmoduleAuth")
//    public Response listSubmoduleUserAuthByListSubmoduleAuth(
//            @HeaderParam("userID") int userID,
//            @BeanParam VersionModel versionModel,
//            @ApiParam(name = "folderId", value = "รหัสที่เก็บเอกสาร", required = true)
//            @QueryParam("folderId") int folderId,
//            @ApiParam(name = "submoduleCode", value = "submoduleCode", required = true)
//            @QueryParam("submoduleCode") String submoduleCode,
//            @ApiParam(name = "listUserID", value = "รหัสผู้ใช้งาน (เช่น 1 หรือ 1,2,...) ", required = false)
//            @QueryParam("listUserID") String listUserId,
//            @ApiParam(name = "listSubmoduleAuthId", value = "รหัสสิทธิ์ที่ต้องการแสดง (เช่น 1 หรือ 1,2,...) ", required = false)
//            @QueryParam("listSubmoduleAuthId") String listSubmoduleAuthId
//    ) {
//        LOG.info("listSubmoduleUserAuthByListUserID...");
//        LOG.info("userID = " + userID);
//        LOG.info("folderId = " + folderId);
//        LOG.info("submoduleId = " + submoduleCode);
//        LOG.info("listUserID = " + listUserId);
//        LOG.info("listSubmoduleAuthId = " + listSubmoduleAuthId);
//        Gson gs = new GsonBuilder()
//                .setVersion(versionModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "dmsFolder by id not found in the database.");
//        try {
//
//            if (listUserId == null) {
////                userID = 1;
//                //ffff
//                listUserId = String.valueOf(userID);
//            }
//            DmsDocumentService dmsDocumentService = new DmsDocumentService();
//            DmsFolderService dmsFolderService = new DmsFolderService();
//            MenuTypeService menuTypeService = new MenuTypeService();
//            MenuService menuService = new MenuService();
//            MenuType menuType = menuTypeService.getById(4);
////            List<Menu> menu;
//            SubmoduleService subModuleService = new SubmoduleService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            Submodule submodule = subModuleService.getBySubmoduleCode(submoduleCode);
//            String[] tmpListUserID = listUserId.split(",");
//            List<UserProfile> listUserProfile = new ArrayList<UserProfile>();
//            for (int i = 0; i < tmpListUserID.length; i++) {
//                UserProfile userProfile = userProfileService.getById(Integer.parseInt(tmpListUserID[i]));
//                listUserProfile.add(userProfile);
//            }
//            BaseTreeEntity treeEntity = new BaseTreeEntity() {};
//            
//            if (submoduleCode.equalsIgnoreCase("dms")) {
//                DmsFolder dmsFolder = dmsFolderService.getById(folderId);
//                treeEntity = dmsFolder;
//            }
//
//            List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, listUserProfile, treeEntity);
//            List<SubmoduleUserAuth> listSubModuleUserAuth2 = new ArrayList<SubmoduleUserAuth>();
//
//            if (listSubmoduleAuthId != null) {
//               
//                listSubmoduleAuthId = "," + listSubmoduleAuthId + ",";
//                for (SubmoduleUserAuth submoduleUserAuth : listSubModuleUserAuth) {
//                    String tmpSubmoduleAuthId = "," + submoduleUserAuth.getSubmoduleAuth().getId() + ",";
//                    if (listSubmoduleAuthId.indexOf(tmpSubmoduleAuthId) > -1) {
//                        listSubModuleUserAuth2.add(submoduleUserAuth);
//                    }
//                }
//            }
//            
//            if (!listSubModuleUserAuth.isEmpty()) {
//                status = Response.Status.OK;
//                if (listSubmoduleAuthId == null) {
//                    List<Menu> menu = menuService.listByMenuTypeSubmoduleUserAuth(menuType, listSubModuleUserAuth, "orderNo", "asc");
//                   
//                    responseData.put("data", menuService.tranformToModelTree(menu, 0));
////                    responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth));
//                } else {
//                    List<Menu> menu = menuService.listByMenuTypeSubmoduleUserAuth(menuType, listSubModuleUserAuth2, "orderNo", "asc");
//            
//                    responseData.put("data", menuService.tranformToModelTree(menu, 0));
//                }
//                responseData.put("message", "");
//            }
//
//            Boolean haveDocInFolder = dmsDocumentService.checkDocmentInFolder(folderId);
//            responseData.put("haveDocInFolder", haveDocInFolder);
//
//
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//}
