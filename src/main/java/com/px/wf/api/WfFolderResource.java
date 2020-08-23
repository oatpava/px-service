package com.px.wf.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Structure;
import com.px.admin.entity.Submodule;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.StructureService;
import com.px.admin.service.SubmoduleService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.entity.SubmoduleUserAuth;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.share.entity.BaseTreeEntity;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import com.px.wf.entity.WfContentType;
import com.px.wf.entity.WfFolder;
import com.px.wf.model.WfContentTypeModel;
import com.px.wf.model.WfFolderContentAuthModel;
import com.px.wf.model.WfFolderModel;
import com.px.wf.model.WfFolderModel_groupWfFolderAndShortcut;
import com.px.wf.service.WfContentService;
import com.px.wf.service.WfContentType2Service;
import com.px.wf.service.WfContentTypeService;
import com.px.wf.service.WfFolderService;
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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author Mali
 */
@Api(value = "WfFolder แฟ้มทะเบียน")
@Path("v1/wfFolders")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WfFolderResource {

    private static final Logger LOG = Logger.getLogger(WfFolderResource.class.getName());

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "สร้างข้อมูลแฟ้มทะเบียน",
            notes = "สร้างข้อมูลแฟ้มทะเบียน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "WfFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            WfFolderModel wfFolderPostModel
    ) {
        LOG.info("create...");
        Gson gs = new GsonBuilder()
                .setVersion(wfFolderPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfFolder wfFolder = new WfFolder();
            //get Data from WfFolderPostModel

            //check length Data
            WfFolderService wfFolderService = new WfFolderService();
            wfFolderPostModel = wfFolderService.checkLengthField(wfFolderPostModel);
            wfFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfFolder != null) {
                WfContentTypeService contentTypeService = new WfContentTypeService();
                WfContentType2Service contentType2Service = new WfContentType2Service();
                UserProfileService userProfileService = new UserProfileService();
                wfFolder.setWfContentType(contentTypeService.getById(wfFolderPostModel.getWfContentType().getId()));
                wfFolder.setWfContentType2(contentType2Service.getById(wfFolderPostModel.getWfContentType2().getId()));
                wfFolder.setConvertId(0);
                wfFolder.setParentId(wfFolderPostModel.getParentId());
                wfFolder.setNodeLevel(wfFolderPostModel.getNodeLevel());
                WfFolder wfFolderNew = wfFolderService.create(wfFolder);
                WfFolder wfFolderParent = new WfFolder();

                if (wfFolderPostModel.getParentId() > 0) {
                    wfFolderParent = wfFolderService.getById(wfFolderPostModel.getParentId());
                    String parentKey = wfFolderParent.getParentKey() + wfFolderNew.getId() + "฿";
                    int nodeLevel = wfFolderParent.getNodeLevel() + 1;
                    int parentType = wfFolderParent.getWfFolderParentType();
                    String parentName = wfFolderParent.getWfFolderName();
                    wfFolder.setParentKey(parentKey);
                    wfFolder.setWfFolderParentName(parentName);
                    wfFolder.setNodeLevel(nodeLevel);
                    wfFolder.setWfFolderParentType(parentType);
                } else {
                    String parentKey = "฿" + wfFolderNew.getId() + "฿";
                    int nodeLevel = 1;
                    String parentName = "";
                    wfFolder.setParentKey(parentKey);
                    wfFolder.setWfFolderParentName(parentName);
                    wfFolder.setNodeLevel(nodeLevel);
                    wfFolder.setWfFolderParentType(0);
                }

                wfFolder.setOrderNo(wfFolderNew.getId());
                wfFolder.setWfFolderName(wfFolderPostModel.getWfFolderName());
                wfFolder.setWfFolderType(wfFolderPostModel.getWfFolderType());
                wfFolder.setWfFolderDetail(wfFolderPostModel.getWfFolderDetail());
                wfFolder.setWfFolderAutorun(wfFolderPostModel.getWfFolderAutorun());
                wfFolder.setWfFolderBookNoType(wfFolderPostModel.getWfFolderBookNoType());
                wfFolder.setWfFolderPreBookNo(wfFolderPostModel.getWfFolderPreBookNo());
                wfFolder.setWfFolderPreContentNo(wfFolderPostModel.getWfFolderPreContentNo());
                wfFolder.setWfFolderLinkFolderId(wfFolderPostModel.getWfFolderLinkFolderId());
                wfFolder.setWfFolderLinkId(wfFolderPostModel.getWfFolderLinkId());
                wfFolder.setWfFolderByBudgetYear(wfFolderPostModel.getWfFolderByBudgetYear());
                wfFolder.setWfFolderTypeYearExpire(wfFolderPostModel.getWfFolderTypeYearExpire());
                wfFolder.setWfFolderNumYearExpire(wfFolderPostModel.getWfFolderNumYearExpire());
                wfFolder.setWfFolderOwnerId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                if (wfFolderPostModel.getWfFolderNumYearExpire() != 0) {
                    LocalDateTime calendar = LocalDateTime.now();
                    if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("Y")) {
                        calendar.plusYears(wfFolderPostModel.getWfFolderNumYearExpire());
                    } else if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("M")) {
                        calendar.plusMonths(wfFolderPostModel.getWfFolderNumYearExpire());
                    } else if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("D")) {
                        calendar.plusDays(wfFolderPostModel.getWfFolderNumYearExpire());
                    }
                    calendar.withSecond(0);
                    calendar.withMinute(0);
                    calendar.withHour(0);
                    wfFolder.setWfFolderExpireDate(calendar);
                }

                wfFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfFolder = wfFolderService.update(wfFolder);
                responseData.put("data", wfFolderService.tranformToModel(wfFolder));

                //LogData For Create
                //wfFolderService.saveLogForCreate(wfFolder, wfFolderPostModel.getClientIp());
                wfFolderService.saveLogForCreate(wfFolder, httpHeaders.getHeaderString("clientIp"));//oat-edit
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "WfFolder created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลแฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            notes = "ขอข้อมูลแฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
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
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.getById(id);
            if (wfFolder != null) {
                status = Response.Status.OK;
                responseData.put("data", wfFolderService.tranformToModel(wfFolder));
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
            value = "แก้ไขข้อมูลแฟ้มทะเบียน",
            notes = "แก้ไขข้อมูลแฟ้มทะเบียน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder updeted by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            WfFolderModel wfFolderPostModel
    ) {
        LOG.info("update...");
        LOG.info("id = " + wfFolderPostModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(wfFolderPostModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfContentTypeService contentTypeService = new WfContentTypeService();
            WfContentType2Service contentType2Service = new WfContentType2Service();
            UserProfileService userProfileService = new UserProfileService();

            //check length Data
            wfFolderPostModel = wfFolderService.checkLengthField(wfFolderPostModel);

            WfFolder wfFolder = wfFolderService.getById(wfFolderPostModel.getId());
            WfFolder wfFolderOld = wfFolderService.getById(wfFolderPostModel.getId());
            if (wfFolder != null) {
                wfFolder.setWfFolderName(wfFolderPostModel.getWfFolderName());
                wfFolder.setWfFolderType(wfFolderPostModel.getWfFolderType());
                wfFolder.setWfFolderDetail(wfFolderPostModel.getWfFolderDetail());
                wfFolder.setWfContentType(contentTypeService.getById(wfFolderPostModel.getWfContentType().getId()));
                wfFolder.setWfContentType2(contentType2Service.getById(wfFolderPostModel.getWfContentType2().getId()));
                wfFolder.setWfFolderAutorun(wfFolderPostModel.getWfFolderAutorun());
                wfFolder.setWfFolderBookNoType(wfFolderPostModel.getWfFolderBookNoType());
                wfFolder.setWfFolderPreBookNo(wfFolderPostModel.getWfFolderPreBookNo());
                wfFolder.setWfFolderPreContentNo(wfFolderPostModel.getWfFolderPreContentNo());
                wfFolder.setWfFolderOwnerId(wfFolderPostModel.getWfFolderOwnerId());
                wfFolder.setWfFolderLinkFolderId(wfFolderPostModel.getWfFolderLinkFolderId());
                wfFolder.setWfFolderLinkId(wfFolderPostModel.getWfFolderLinkId());
                wfFolder.setWfFolderByBudgetYear(wfFolderPostModel.getWfFolderByBudgetYear());
                wfFolder.setWfFolderTypeYearExpire(wfFolderPostModel.getWfFolderTypeYearExpire());
                wfFolder.setWfFolderNumYearExpire(wfFolderPostModel.getWfFolderNumYearExpire());
                if (wfFolderPostModel.getWfFolderNumYearExpire() != 0) {
                    LocalDateTime calendar = LocalDateTime.now();
                    if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("Y")) {
                        calendar.plusYears(wfFolderPostModel.getWfFolderNumYearExpire());
                    } else if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("M")) {
                        calendar.plusMonths(wfFolderPostModel.getWfFolderNumYearExpire());
                    } else if (wfFolderPostModel.getWfFolderTypeYearExpire().equals("D")) {
                        calendar.plusDays(wfFolderPostModel.getWfFolderNumYearExpire());
                    }
                    calendar.withSecond(0);
                    calendar.withMinute(0);
                    calendar.withHour(0);
                    wfFolder.setWfFolderExpireDate(calendar);
                }
                //oat-add
                wfFolder.setParentId(wfFolderPostModel.getParentId());

                //oat-add
                String tmp = "";
                if (wfFolderPostModel.getSearchField() != null) {
                    for (boolean searchFields : wfFolderPostModel.getSearchField()) {
                        if (searchFields) {
                            tmp += 1;
                        } else {
                            tmp += 0;
                        }
                    }
                }
                wfFolder.setSearchField(tmp);

                wfFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                wfFolder = wfFolderService.update(wfFolder);

                //change wffolderparentName of child
                List<WfFolder> listWfFolderChild = wfFolderService.listByParentId(wfFolderPostModel.getId());
                if (!listWfFolderChild.isEmpty()) {
                    for (WfFolder wfFolder1 : listWfFolderChild) {
                        wfFolder1.setWfFolderParentName(wfFolderPostModel.getWfFolderName());
                        wfFolder1 = wfFolderService.update(wfFolder1);
                    }
                }

                status = Response.Status.OK;
                responseData.put("data", wfFolderService.tranformToModel(wfFolder));
                responseData.put("message", "");

                //LogData For Update
                //wfFolderService.saveLogForUpdate(wfFolderOld, wfFolder, wfFolderPostModel.getClientIp());
                wfFolderService.saveLogForUpdate(wfFolderOld, wfFolder, httpHeaders.getHeaderString("clientIp"));//oat-edit
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลแฟ้มทะเบียน",
            notes = "ลบข้อมูลแฟ้มทะเบียน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder deleted by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("remove...");
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
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfFolder != null) {
                status = Response.Status.OK;
                responseData.put("data", wfFolderService.tranformToModel(wfFolder));
                responseData.put("message", "");
            }
            responseData.put("success", true);

            //LogData For Remove
            //wfFolderService.saveLogForRemove(wfFolder, versionModel.getClientIp());
            wfFolderService.saveLogForRemove(wfFolder, httpHeaders.getHeaderString("clientIp"));//oat-edit
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list WfFolder by folderParentId.",
            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสแม่ของทะเบียน",
            responseContainer = "List",
            response = WfFolderModel_groupWfFolderAndShortcut.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder list success."),
        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByParentId/{parentId}")
    public Response listByFolderParentId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "parentId", value = "รหัสแม่ของทะเบียน", required = true)
            @PathParam("parentId") int parentId
    ) {
        LOG.info("listByFolderParentId...");
        LOG.info("parentId = " + parentId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder by parentId not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listByParentId(parentId);
            List<WfFolderModel> listWfFolderModel = new ArrayList<>();
            List<SubmoduleUserAuth> listSubModuleUserAuth = new ArrayList<>();
            SubmoduleUserAuth tmp = new SubmoduleUserAuth();
            tmp.setAuthority("1");
            listSubModuleUserAuth.add(tmp);
            tmp.setAuthority("0");
            for (int i = 1; i < 5; i++) { //still not get count SubmoduleUserAuth
                listSubModuleUserAuth.add(tmp);
            }
            for (WfFolder wfFolder : listWfFolder) {
                listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder, listSubModuleUserAuth));
            }
            status = Response.Status.OK;
            responseData.put("data", listWfFolderModel);
            responseData.put("message", "");

            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

//    @ApiOperation(
//            value = "Method for list WfFolder by folderParentId.",
//            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสแม่ของทะเบียน",
//            responseContainer = "List",
//            response = WfFolderModel_groupWfFolderAndShortcut.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "WfFolder list success."),
//        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listByFolderParentId")
//    public Response listByFolderParentId(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "folderParentid", value = "รหัสแม่ของทะเบียน", required = true)
//            @QueryParam("folderParentid") int folderParentid,
//            @ApiParam(name = "submoduleAuthCode", value = "รหัสสิทธิการใช้งานระบบงานย่อย", required = true)
//            @QueryParam("submoduleAuthCode") String submoduleAuthCode
//    ) {
//        LOG.info("listByFolderParentId...");
//        LOG.info("folderParentid = " + folderParentid);
//        LOG.info("submoduleAuthCode = " + submoduleAuthCode);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//            //userID = 1;
//            long t1 = -System.currentTimeMillis();
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth.add(submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode));
//
//            UserProfile userProfile = userProfileService.getById(userID);
//            if (folderParentid > 0) {
//                List<WfFolder> listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//                List<WfFolderModel_groupWfFolderAndShortcut> listWfFolderModel_groupWfFolderAndShortcut = new ArrayList<>();
//                for (WfFolder wfFolder : listWfFolder) {
//                    List<SubmoduleUserAuth> resultCheckAuthUser = wfFolderService.checkAuthUser(wfFolder, submoduleAuth, userProfile);
//                    if (!resultCheckAuthUser.isEmpty() && resultCheckAuthUser.get(0).getAuthority().equals("1")) {
//                        listWfFolderModel_groupWfFolderAndShortcut.add(wfFolderService.tranformToModelGroupWfFolderAndShortcut(wfFolder, userID));
//                    }
//                }
//                status = Response.Status.OK;
//                responseData.put("data", listWfFolderModel_groupWfFolderAndShortcut);
//                responseData.put("message", "");
//            } else {
//                List<WfFolder> listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//                if (!listWfFolder.isEmpty()) {
//                    List<WfFolderModel_groupWfFolderAndShortcut> listWfFolderModel_groupWfFolderAndShortcut = new ArrayList<>();
//                    for (WfFolder wfFolder : listWfFolder) {
//                        listWfFolderModel_groupWfFolderAndShortcut.add(wfFolderService.tranformToModelGroupWfFolderAndShortcut(wfFolder, userID));
//                    }
//                    status = Response.Status.OK;
//                    responseData.put("data", listWfFolderModel_groupWfFolderAndShortcut);
//                    responseData.put("message", "");
//                }
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//    @ApiOperation(
//            value = "Method for list WfFolder by folderParentId and UserProfileId.",
//            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสแม่ของทะเบียนและรหัสผู้ใช้งานในระบบ",
//            responseContainer = "List",
//            response = WfFolderModel_groupWfFolderAndShortcut.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "WfFolder list success."),
//        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listByFolderParentIdForShortcut")
//    public Response listByFolderParentIdForShortcut(
//            @ApiParam(name = "folderParentid", value = "รหัสแม่ของทะเบียน", required = true)
//            @QueryParam("folderParentid") int folderParentid,
//            @ApiParam(name = "submoduleAuthCode", value = "รหัสสิทธิการใช้งานระบบงานย่อย", required = true)
//            @QueryParam("submoduleAuthCode") String submoduleAuthCode,
//            @ApiParam(name = "userID", value = "รหัสผู้ใช้งานในระบบ", required = true)
//            @QueryParam("userID") int userID,
//            @ApiParam(name = "userType", value = "ประเภทผู้ใช้งานในระบบ 0=user,1=Structure", required = true)
//            @QueryParam("userType") int userType
//    ) {
//        LOG.info("listByFolderParentIdForShortcut...");
//        LOG.info("folderParentid = " + folderParentid);
//        LOG.info("submoduleAuthCode = " + submoduleAuthCode);
//        LOG.info("userID = " + userID);
//        LOG.info("userType = " + userType);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//            //String authority = "1";
//            //userID = 1; 
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//            StructureService structureService = new StructureService();
//
//            //SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode);
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth.add(submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode));
//            List<WfFolder> listWfFolder = new ArrayList();
//            List<WfFolder> listWfFolder2 = new ArrayList();
//            List<WfFolderModel_groupWfFolderAndShortcut> listWfFolderModel_groupWfFolderAndShortcut = new ArrayList<>();
//
//            if (folderParentid > 0) {
//                if (userType == 0) {
//                    UserProfile userProfile = userProfileService.getById(userID);
//
//                    listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//                    //listWfFolder2 = submoduleUserAuthService.getEntityByUserProfileAuthority(submoduleAuth, userProfile, authority, listWfFolder);
//                    for (WfFolder wfFolder : listWfFolder) {
//                        List<SubmoduleUserAuth> resultCheckAuthUser = wfFolderService.checkAuthUser(wfFolder, submoduleAuth, userProfile);
//                        if (!resultCheckAuthUser.isEmpty() && resultCheckAuthUser.get(0).getAuthority().equals("1")) {
//                            listWfFolderModel_groupWfFolderAndShortcut.add(wfFolderService.tranformToModelGroupWfFolderAndShortcut(wfFolder, userID));
//                        }
//                    }
//                } else {
//                    Structure structure = structureService.getById(userID);
//
//                    listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//                    //listWfFolder2 = submoduleUserAuthService.getEntityByStructureAuthority(submoduleAuth, structure, authority, listWfFolder);
//                    for (WfFolder wfFolder : listWfFolder) {
//                        List<SubmoduleUserAuth> resultCheckAuthStructure = wfFolderService.checkAuthStructure(wfFolder, submoduleAuth, structure);
//                        if (!resultCheckAuthStructure.isEmpty() && resultCheckAuthStructure.get(0).getAuthority().equals("1")) {
//                            listWfFolderModel_groupWfFolderAndShortcut.add(wfFolderService.tranformToModelGroupWfFolderAndShortcut(wfFolder, userID));
//                        }
//                    }
//                }
//
//                status = Response.Status.OK;
//                responseData.put("data", listWfFolderModel_groupWfFolderAndShortcut);
//                responseData.put("message", "");
//
//            } else {
//                listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//
//                if (!listWfFolder.isEmpty()) {
//                    for (WfFolder wfFolder : listWfFolder) {
//                        listWfFolderModel_groupWfFolderAndShortcut.add(wfFolderService.tranformToModelGroupWfFolderAndShortcut(wfFolder, userID));
//                    }
//                    status = Response.Status.OK;
//                    responseData.put("data", listWfFolderModel_groupWfFolderAndShortcut);
//                    responseData.put("message", "");
//                }
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//    @ApiOperation(
//            value = "Method for list WfFolder by folderParentId.",
//            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสแม่ของทะเบียน",
//            responseContainer = "List",
//            response = WfFolderModel_groupWfFolderAndAuth.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "WfFolder list success."),
//        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listByFolderParentIdAuth")
//    public Response listByFolderParentIdAuth(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "folderParentid", value = "รหัสแม่ของทะเบียน", required = true)
//            @QueryParam("folderParentid") int folderParentid,
//            @ApiParam(name = "submoduleAuthCode", value = "รหัสสิทธิการใช้งานระบบงานย่อย", required = true)
//            @QueryParam("submoduleAuthCode") String submoduleAuthCode
//    ) {
//        LOG.info("listByFolderParentIdAuth...");
//        LOG.info("folderParentid = " + folderParentid);
//        LOG.info("submoduleAuthCode = " + submoduleAuthCode);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//            //String authority = "1";
//            //userID = 1; 
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            //SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode);
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth.add(submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode));
//            UserProfile userProfile = userProfileService.getById(userID);
//
//            if (folderParentid > 0) {
//                List<WfFolder> listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//                ArrayList<WfFolderModel_groupWfFolderAndAuth> listWfFolderModel_groupWfFolderAndAuth = new ArrayList<>();
//
//                for (WfFolder wfFolder : listWfFolder) {
//                    List<SubmoduleUserAuth> resultCheckAuthUser = wfFolderService.checkAuthUser(wfFolder, submoduleAuth, userProfile);
//                    if (!resultCheckAuthUser.isEmpty() && resultCheckAuthUser.get(0).getAuthority().equals("1")) {
//                        listWfFolderModel_groupWfFolderAndAuth.add(wfFolderService.tranformToModelGroupSubModuleUserAuthOfWfFolder(wfFolder, userID));
//                    }
//                }
//                status = Response.Status.OK;
//                responseData.put("data", listWfFolderModel_groupWfFolderAndAuth);
//                responseData.put("message", "");
//            } else {
//                List<WfFolder> listWfFolder = wfFolderService.listByFolderParentId(folderParentid);
//
//                if (!listWfFolder.isEmpty()) {
//                    ArrayList<WfFolderModel_groupWfFolderAndAuth> listWfFolderModel_groupWfFolderAndAuth = new ArrayList<>();
//                    for (WfFolder wfFolder : listWfFolder) {
//                        listWfFolderModel_groupWfFolderAndAuth.add(wfFolderService.tranformToModelGroupSubModuleUserAuthOfWfFolder(wfFolder, userID));
//                    }
//                    status = Response.Status.OK;
//                    responseData.put("data", listWfFolderModel_groupWfFolderAndAuth);
//                    responseData.put("message", "");
//                }
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list SubmoduleUserAuth Of WfFolder by ListUserID.",
//            notes = "ขอรายการสิทธิการใช้ระบบงานย่อยของผู้ใช้งานระบบของแต่ละทะเบียน ด้วย รหัสผู้ใช้งานในระบบ",
//            responseContainer = "List",
//            response = SubmoduleUserAuthModel_groupStructureUser.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of WfFolder list success."),
//        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listSubmoduleUserAuthByListUserID")
//    public Response listSubmoduleUserAuthByListUserID(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
//            @QueryParam("folderId") int folderId,
//            @ApiParam(name = "submoduleCode", value = "รหัสระบบงานย่อย", required = true)
//            @QueryParam("submoduleCode") String submoduleCode,
//            @ApiParam(name = "listUserID", value = "รหัสผู้ใช้งาน (เช่น 1 หรือ 1,2,...) ", required = false)
//            @QueryParam("listUserID") String listUserID
//    ) {
//        LOG.info("listSubmoduleUserAuthByListUserID...");
//        LOG.info("userID = " + userID);
//        LOG.info("folderId = " + folderId);
//        LOG.info("submoduleCode = " + submoduleCode);
//        LOG.info("listUserID = " + listUserID);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//
//            if (listUserID == null) {
//                //userID = 1;
//                listUserID = String.valueOf(userID);
//            }
//
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleService subModuleService = new SubmoduleService();
//            SubmoduleAuthService subModuleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            Submodule submodule = subModuleService.getBySubmoduleCode(submoduleCode);
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth = subModuleAuthService.listBySubmodule(submodule, "orderNo", "asc");
//            String[] tmpListUserID = listUserID.split(",");
//            List<UserProfile> listUserProfile = new ArrayList<UserProfile>();
//            for (int i = 0; i < tmpListUserID.length; i++) {
//                UserProfile userProfile = userProfileService.getById(Integer.parseInt(tmpListUserID[i]));
//                listUserProfile.add(userProfile);
//            }
//
//            WfFolder wfFolder = wfFolderService.getById(folderId);
//            List<SubmoduleUserAuth> listSubModuleUserAuth = wfFolderService.getAuthorityByUserProfileOfWfFolder(submoduleAuth, listUserProfile, wfFolder);
//            if (!listSubModuleUserAuth.isEmpty()) {
//                status = Response.Status.OK;
//                responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth));
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list SubmoduleUserAuth Of WfFolder by ListUserID and ListSubmoduleAuth.",
//            notes = "ขอรายการสิทธิการใช้ระบบงานย่อยของผู้ใช้งานระบบของแต่ละทะเบียนสารบรรณ ด้วย รหัสผู้ใช้งานในระบบ และ รหัสสิทธิ์",
//            responseContainer = "List",
//            response = SubmoduleUserAuthModel_groupStructureUser.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of WfFolder list success."),
//        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listSubmoduleUserAuthByListSubmoduleAuth")
//    public Response listSubmoduleUserAuthByListSubmoduleAuth(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
//            @QueryParam("folderId") int folderId,
//            @ApiParam(name = "submoduleCode", value = "รหัสระบบงานย่อย", required = true)
//            @QueryParam("submoduleCode") String submoduleCode,
//            @ApiParam(name = "listUserId", value = "รหัสผู้ใช้งาน (เช่น 1 หรือ 1,2,...) ", required = false)
//            @QueryParam("listUserId") String listUserId,
//            @ApiParam(name = "listSubmoduleAuthCode", value = "รหัสสิทธิ์ที่ต้องการแสดง (เช่น WF_AUTH1 หรือ WF_AUTH1,WF_AUTH2,...) ", required = false)
//            @QueryParam("listSubmoduleAuthCode") String listSubmoduleAuthCode
//    ) {
//        LOG.info("listSubmoduleUserAuthByListSubmoduleAuth...");
//        LOG.info("userID = " + userID);
//        LOG.info("folderId = " + folderId);
//        LOG.info("submoduleCode = " + submoduleCode);
//        LOG.info("listUserId = " + listUserId);
//        LOG.info("listSubmoduleAuthCode = " + listSubmoduleAuthCode);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//
//            if (listUserId == null) {
//                listUserId = String.valueOf(userID);
//            }
//
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleService subModuleService = new SubmoduleService();
//            SubmoduleAuthService subModuleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            Submodule submodule = subModuleService.getBySubmoduleCode(submoduleCode);
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth = subModuleAuthService.listBySubmodule(submodule, "orderNo", "asc");
//            String[] tmpListUserID = listUserId.split(",");
//            List<UserProfile> listUserProfile = new ArrayList<UserProfile>();
//            for (int i = 0; i < tmpListUserID.length; i++) {
//                UserProfile userProfile = userProfileService.getById(Integer.parseInt(tmpListUserID[i]));
//                listUserProfile.add(userProfile);
//            }
//
//            WfFolder wfFolder = wfFolderService.getById(folderId);
//            //List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityByUserProfile(submodule, listUserProfile, wfFolder);
//            List<SubmoduleUserAuth> listSubModuleUserAuth = wfFolderService.getAuthorityByUserProfileOfWfFolder(submoduleAuth, listUserProfile, wfFolder);
//            List<SubmoduleUserAuth> listSubModuleUserAuth2 = new ArrayList<SubmoduleUserAuth>();
//            if (listSubmoduleAuthCode != null) {
//                listSubmoduleAuthCode = "," + listSubmoduleAuthCode + ",";
//                for (SubmoduleUserAuth submoduleUserAuth : listSubModuleUserAuth) {
//                    String tmpSubmoduleAuthCode = "," + submoduleUserAuth.getSubmoduleAuth().getSubmoduleAuthCode() + ",";
//                    if (listSubmoduleAuthCode.indexOf(tmpSubmoduleAuthCode) > -1) {
//                        listSubModuleUserAuth2.add(submoduleUserAuth);
//                    }
//                }
//            }
//
//            if (!listSubModuleUserAuth.isEmpty()) {
//                status = Response.Status.OK;
//                if (listSubmoduleAuthCode == null) {
//                    responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth));
//                } else {
//                    responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth2));
//                }
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list All SubmoduleUserAuth Of WfFolder by StructureId.",
//            notes = "ขอรายการสิทธิการใช้ระบบงานย่อยของผู้ใช้งานระบบทั้งหมดของแต่ละทะเบียน ด้วย รหัสโครงสร้าง",
//            responseContainer = "List",
//            response = SubmoduleUserAuthModel_groupStructureUser.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "SubmoduleUserAuth Of WfFolder list success."),
//        @ApiResponse(code = 404, message = "SubmoduleUserAuth Of WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listSubmoduleUserAuthOfChildByStructureId")
//    public Response listSubmoduleUserAuthOfChildByStructureId(
//            @ApiParam(name = "structureId", value = "รหัสโครงสร้างหน่วยงาน", required = true)
//            @QueryParam("structureId") int structureId,
//            @ApiParam(name = "folderId", value = "รหัสแฟ้มทะเบียน", required = true)
//            @QueryParam("folderId") int folderId,
//            @ApiParam(name = "submoduleCode", value = "รหัสระบบงานย่อย", required = true)
//            @QueryParam("submoduleCode") String submoduleCode
//    ) {
//        LOG.info("listSubmoduleUserAuthOfChildByStructureId...");
//        LOG.info("structureId = " + structureId);
//        LOG.info("folderId = " + folderId);
//        LOG.info("submoduleCode = " + submoduleCode);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleService subModuleService = new SubmoduleService();
//            SubmoduleAuthService subModuleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            StructureService structureService = new StructureService();
//
//            WfFolder wfFolder = wfFolderService.getById(folderId);
//            Submodule submodule = subModuleService.getBySubmoduleCode(submoduleCode);
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth = subModuleAuthService.listBySubmodule(submodule, "orderNo", "asc");
//            Structure structure = structureService.getById(structureId);
//
//            List<SubmoduleUserAuth> listSubModuleUserAuth = wfFolderService.getAuthorityOfChildByStructureOfWfFolder(submoduleAuth, structure, wfFolder);
//            if (!listSubModuleUserAuth.isEmpty()) {
//                status = Response.Status.OK;
//                responseData.put("data", submoduleUserAuthService.tranformToModelGroupStructureUser(listSubModuleUserAuth));
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//    @ApiOperation(
//            value = "Method for list WfFolder by folderParentKey.",
//            notes = "ขอรายการทะเบียนด้วย folderParentKey",
//            responseContainer = "List",
//            response = WfFolderModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "WfFolder list success."),
//        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listByFolderParentKey")
//    public Response listByFolderParentKey(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "folderid", value = "รหัสแฟ้มทะเบียน", required = true)
//            @QueryParam("folderid") int folderid,
//            @ApiParam(name = "wfFolderName", value = "ชื่อแฟ้มทะเบียน", required = false)
//            @QueryParam("wfFolderName") String wfFolderName,
//            @ApiParam(name = "wfFolderDetail", value = "รายละเอียด", required = false)
//            @QueryParam("wfFolderDetail") String wfFolderDetail,
//            @ApiParam(name = "submoduleAuthCode", value = "รหัสสิทธิการใช้งานระบบงานย่อย", required = true)
//            @QueryParam("submoduleAuthCode") String submoduleAuthCode
//    ) {
//        LOG.info("listByFolderParentKey...");
//        LOG.info("folderid = " + folderid);
//        LOG.info("wfFolderName = " + wfFolderName);
//        LOG.info("wfFolderDetail = " + wfFolderDetail);
//        LOG.info("submoduleAuthCode = " + submoduleAuthCode);
////        log.info("authority = " + authority);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "WfFolder by id not found in the database.");
//        try {
//            String authority = "1";
//            //userID = 1;
//            WfFolderService wfFolderService = new WfFolderService();
//            WfFolder WfFolder2 = wfFolderService.getById(folderid);
//
//            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//            UserProfileService userProfileService = new UserProfileService();
//
//            List<SubmoduleAuth> submoduleAuth = new ArrayList();
//            submoduleAuth.add(submoduleAuthService.getBySubmoduleAuthCode(submoduleAuthCode));
//            UserProfile userProfile = userProfileService.getById(userID);
//            List<WfFolder> listWfFolder = wfFolderService.listByFolderParentKey(WfFolder2.getWfFolderParentKey(), wfFolderName, wfFolderDetail);
//            //List<WfFolder> listWfFolder2 = submoduleUserAuthService.getEntityByUserProfileAuthority(submoduleAuth, userProfile, authority, listWfFolder);
//
//            List<WfFolderModel> listWfFolderModel = new ArrayList<>();
//            for (WfFolder wfFolder : listWfFolder) {
//                List<SubmoduleUserAuth> resultCheckAuthUser = wfFolderService.checkAuthUser(wfFolder, submoduleAuth, userProfile);
//                if (!resultCheckAuthUser.isEmpty() && resultCheckAuthUser.get(0).getAuthority().equals("1")) {
//                    listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder));
//                }
//            }
//            status = Response.Status.OK;
//            responseData.put("data", listWfFolderModel);
//            responseData.put("message", "");
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//
//        return Response.status(status)
//                .entity(gs.toJson(responseData)).build();
//    }
//
//    @ApiOperation(
//            value = "Method for list SubModuleAuth of WfFolder by SubModuleId.",
//            notes = "ขอรายการสิทธิ์ระบบงานย่อยของทะเบียน ด้วย รหัสระบบงานย่อย",
//            responseContainer = "List",
//            response = SubmoduleAuthModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "SubModuleAuth of WfFolder list success."),
//        @ApiResponse(code = 404, message = "SubModuleAuth of WfFolder list not found in the database."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @GET
//    @Consumes({MediaType.TEXT_PLAIN})
//    @Path(value = "/listBySubModule")
//    public Response listBySubModule(
//            @HeaderParam("userID") int userID,
//            @ApiParam(name = "submoduleCode", value = "รหัสระบบงานย่อย", required = true)
//            @QueryParam("submoduleCode") String submoduleCode,
//            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
//            @DefaultValue("createdDate")
//            @QueryParam("sort") String sort,
//            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
//            @DefaultValue("desc")
//            @QueryParam("dir") String dir
//    ) {
//        LOG.info("listBySubModule...");
//        LOG.info("submoduleCode = " + submoduleCode);
//        Gson gs = new GsonBuilder()
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "SubModuleAuth of WfFolder by id not found in the database.");
//        try {
//            WfFolderService wfFolderService = new WfFolderService();
//            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//            SubmoduleService submoduleService = new SubmoduleService();
//
//            Submodule submodule = submoduleService.getBySubmoduleCode(submoduleCode);
//            List<SubmoduleAuth> listSubmoduleAuth = submoduleAuthService.listBySubmodule(submodule, sort, dir);
//
//            if (!listSubmoduleAuth.isEmpty()) {
//                List<SubmoduleAuthModel> listSubmoduleAuthModel = new ArrayList<>();
//                for (SubmoduleAuth submoduleAuth : listSubmoduleAuth) {
//                    listSubmoduleAuthModel.add(submoduleAuthService.tranformToModel(submoduleAuth));
//                }
//                status = Response.Status.OK;
//                responseData.put("data", listSubmoduleAuthModel);
//                responseData.put("message", "");
//            }
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.INTERNAL_SERVER_ERROR;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
    @ApiOperation(
            value = "Method for list Shortcut WfFolder by UserProfileId.",
            notes = "รายการสำเนาทะเบียนตามผู้ใช้งานในระบบ",
            responseContainer = "List",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder list Shortcut success."),
        @ApiResponse(code = 404, message = "WfFolder list Shortcut not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listShortcutByUserProfileIdHeader")
    public Response listShortcutByUserProfileIdHeader(
            @BeanParam VersionModel versionModel
    ) {
        LOG.info("listShortcutByUserProfileIdHeader...");
        LOG.info("userID = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
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
        responseData.put("message", "WfFolder list Shortcut not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listShortcutByUserProfileId(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            List<WfFolderModel> listWffolderModel = new ArrayList<>();

            if (!listWfFolder.isEmpty()) {
                //List<WfFolderModel> listWffolderModel = new ArrayList<>();
                for (WfFolder wfFolder : listWfFolder) {
//                    WfFolder mainWfFolder = wfFolderService.getById(wfFolder.getWfFolderLinkFolderId());               
//                    listWffolderModel.add(wfFolderService.tranformToModel(wfFolder, mainWfFolder));
                    WfFolder mainWfFolder = wfFolderService.getByIdNotRemoved(wfFolder.getWfFolderLinkFolderId());
                    if (mainWfFolder != null) {
                        listWffolderModel.add(wfFolderService.tranformToModel(wfFolder, mainWfFolder));
                    }
                }
//                status = Response.Status.OK;
//                responseData.put("data", listWffolderModel);
//                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("data", listWffolderModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list Shortcut WfFolder by UserProfileId.",
            notes = "รายการสำเนาทะเบียนตามผู้ใช้งานในระบบ",
            responseContainer = "List",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder list Shortcut success."),
        @ApiResponse(code = 404, message = "WfFolder list Shortcut not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listShortcutByUserProfileId/{userProfileId}")
    public Response listShortcutByUserProfileId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userProfileId", value = "รหัสผู้ใช้งานในระบบ", required = true)
            @PathParam("userProfileId") int userProfileId
    ) {
        LOG.info("listShortcutByUserProfileId...");
        LOG.info("userProfileId = " + userProfileId);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder list Shortcut not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listShortcutByUserProfileId(userProfileId);
            List<WfFolderModel> listWffolderModel = new ArrayList<>();
            if (!listWfFolder.isEmpty()) {
                for (WfFolder wfFolder : listWfFolder) {
                    WfFolder mainWfFolder = wfFolderService.getByIdNotRemoved(wfFolder.getWfFolderLinkFolderId());
                    if (mainWfFolder != null) {
                        listWffolderModel.add(wfFolderService.tranformToModel(wfFolder, mainWfFolder));
                    }
                }
            }
            status = Response.Status.OK;
            responseData.put("data", listWffolderModel);
            responseData.put("message", "");
            responseData.put("success", true);
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ลบข้อมูลทะเบียนออกจากฐานข้อมูล",
            notes = "ลบข้อมูลทะเบียนออกจากฐานข้อมูล"
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder delete by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/delete/{id}")
    public Response _delete(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("delete...");
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
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.getById(id);
            if (wfFolder != null) {
                wfFolderService.delete(wfFolder);

                status = Response.Status.OK;
                responseData.put("data", "Delete success.");
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

    //oat-edit
    @ApiOperation(
            value = "check shared folder.",
            notes = "ดึงทะเบียนส่วนกลาง",
            responseContainer = "List",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "List WfFolder by ContentTypeId success."),
        @ApiResponse(code = 404, message = "List WfFolder by ContentTypeId not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByContentTypeId/{folderType}/{typeId}/{type2Id}/{withAuth}")
    public Response listByContentTypeId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "folderType", value = "folderType", required = true)
            @PathParam("folderType") String folderType,
            @ApiParam(name = "typeId", value = "typeId", required = true)
            @PathParam("typeId") int typeId,
            @ApiParam(name = "type2Id", value = "type2Id", required = true)
            @PathParam("type2Id") int type2Id,
            @ApiParam(name = "withAuth", value = "withAuth", required = true)
            @PathParam("withAuth") int withAuth
    ) {
        LOG.info("listByContentTypeId...");
        LOG.info("FolderType = " + folderType);
        LOG.info("ContentTypeId = " + typeId);
        LOG.info("ContentType2Id = " + type2Id);
        LOG.info("withAuth = " + withAuth);
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
        responseData.put("message", "List WfFolder by ContentTypeId not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listByContentTypeId(folderType, typeId, type2Id);
            ArrayList<WfFolderModel> listWfFolderModel = new ArrayList<>();
            if (!listWfFolder.isEmpty()) {
                if (withAuth == 1) {
                    int userId = Integer.parseInt(httpHeaders.getHeaderString("userID"));
                    if (userId == 1) {
                        List<SubmoduleUserAuth> listSubModuleUserAuth = new ArrayList<>();
                        SubmoduleUserAuth tmp = new SubmoduleUserAuth();
                        tmp.setAuthority("1");
                        for (int i = 0; i < 5; i++) { //still not get count SubmoduleUserAuth
                            listSubModuleUserAuth.add(tmp);
                        }
                        for (WfFolder wfFolder : listWfFolder) {
                            listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder, listSubModuleUserAuth));
                        }
                    } else {
                        for (WfFolder wfFolder : listWfFolder) {
                            BaseTreeEntity treeEntity = wfFolderService.getById(wfFolder.getId());
                            SubmoduleService subModuleService = new SubmoduleService();
                            Submodule submodule = subModuleService.getBySubmoduleCode("wf_f");
                            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
                            UserProfileService userProfileService = new UserProfileService();
                            List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfileService.getById(userId), treeEntity);
                            listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder, listSubModuleUserAuth));
                        }
                    }
                } else {
                    for (WfFolder wfFolder : listWfFolder) {
                        listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder));
                    }
                }

            }
            listWfFolderModel.trimToSize();
            status = Response.Status.OK;
            responseData.put("data", listWfFolderModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "สร้างข้อมูลแฟ้มทะเบียนลัดของผู้ใช้งาน",
            notes = "สร้างข้อมูลแฟ้มทะเบียนลัดของผู้ใช้งาน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Shortcut WfFolder created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createShortcut/{ownerID}")
    public Response createShortcut(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "ownerID", value = "เจ้าของ Shortcut", required = true)
            @PathParam("ownerID") int ownerID,
            WfFolderModel wfFolderModel
    ) {
        LOG.info("createShortcutUserProfile...");
        LOG.info("ownerId = " + ownerID);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.getById(wfFolderModel.getId());
            WfFolder shortcutFolder = new WfFolder();

            shortcutFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (wfFolder != null) {
                shortcutFolder.setWfContentType(wfFolder.getWfContentType());
                shortcutFolder.setWfContentType2(wfFolder.getWfContentType2());
                shortcutFolder.setWfFolderOwnerId(ownerID);
                shortcutFolder = wfFolderService.create(shortcutFolder);

                shortcutFolder.setWfFolderName(wfFolder.getWfFolderName());
                shortcutFolder.setWfFolderType("SC");
                shortcutFolder.setWfFolderLinkFolderId(wfFolder.getId());
                shortcutFolder.setWfFolderLinkId(wfFolder.getWfFolderLinkId());
                shortcutFolder.setConvertId(0);
                shortcutFolder.setParentId(-1);
                shortcutFolder.setNodeLevel(-1);
                shortcutFolder.setOrderNo(wfFolderModel.getOrderNo());
                shortcutFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                shortcutFolder = wfFolderService.update(shortcutFolder);

                responseData.put("data", wfFolderService.tranformToModel(shortcutFolder));
            }
            status = Response.Status.CREATED;
            responseData.put("success", true);
            responseData.put("message", "Shortcut WfFolder created successfully.");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลสิทธิ์หนังสือ ด้วย รหัสแฟ้มทะเบียน",
            notes = "ขอข้อมูลสิทธิ์หนังสือ ด้วย รหัสแฟ้มทะเบียน",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent Auth by id success."),
        @ApiResponse(code = 404, message = "WfContent Auth by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/contentAuth/{id}/{structureId}/{userId}")
    public Response listContentAuth(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.info("getContentAuth...");
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
        responseData.put("message", "WfContent Auth by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            //WfFolder wfFolder = wfFolderService.getById(id);
            BaseTreeEntity treeEntity;
            treeEntity = wfFolderService.getById(id);
            //treeEntity = wfFolder;
            SubmoduleService subModuleService = new SubmoduleService();
            Submodule submodule = subModuleService.getBySubmoduleCode("wf_c");
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            List<SubmoduleUserAuth> listSubModuleUserAuth;
            if (structureId == 0) {
                UserProfileService userProfileService = new UserProfileService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfileService.getById(userId), treeEntity);
            } else {
                StructureService structureService = new StructureService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByStructure(submodule, structureService.getById(structureId), treeEntity);
            }
            //UserProfileService userProfileService = new UserProfileService();
            //UserProfile userProfile = userProfileService.getById(userId);
            //List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfile, treeEntity);

            List<WfFolderContentAuthModel> ListContentAuthModel = new ArrayList<>();
            if (!listSubModuleUserAuth.isEmpty()) {
                for (SubmoduleUserAuth x : listSubModuleUserAuth) {
                    //System.out.println(x.getSubmoduleAuth().getSubmoduleAuthCode() + " : " + x.getAuthority());
                    ListContentAuthModel.add(wfFolderService.tranformToModel2(x));

                }
                //System.out.println("_______________________");
                status = Response.Status.OK;
                responseData.put("data", ListContentAuthModel);
                responseData.put("message", "");
                responseData.put("submodule", subModuleService.tranformToModel(submodule));
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลสิทธิ์แฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            notes = "ขอข้อมูลสิทธิ์แฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent Auth by id success."),
        @ApiResponse(code = 404, message = "WfContent Auth by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/folderAuth/{id}/{structureId}/{userId}")
    public Response listFolderAuth(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.info("getContentAuth...");
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
        responseData.put("message", "WfContent Auth by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            BaseTreeEntity treeEntity = wfFolderService.getById(id);
            SubmoduleService subModuleService = new SubmoduleService();
            Submodule submodule = subModuleService.getBySubmoduleCode("wf_f");
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            List<SubmoduleUserAuth> listSubModuleUserAuth;

            if (structureId == 0) {
                UserProfileService userProfileService = new UserProfileService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfileService.getById(userId), treeEntity);
            } else {
                StructureService structureService = new StructureService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByStructure(submodule, structureService.getById(structureId), treeEntity);
            }

            List<WfFolderContentAuthModel> ListContentAuthModel = new ArrayList<>();
            if (!listSubModuleUserAuth.isEmpty()) {
                for (SubmoduleUserAuth x : listSubModuleUserAuth) {
                    ListContentAuthModel.add(wfFolderService.tranformToModel2(x));
                }
                status = Response.Status.OK;
                responseData.put("data", ListContentAuthModel);
                responseData.put("message", "");
                responseData.put("submodule", subModuleService.tranformToModel(submodule));
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
            value = "create auth",
            notes = "create auth",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent Auth by id success."),
        @ApiResponse(code = 404, message = "WfContent Auth by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/createContentAuth/{id}/{structureId}/{userId}")
    public Response createContentAuth(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @PathParam("userId") int userId,
            List<WfFolderContentAuthModel> listContentAuthModel
    ) {
        LOG.info("getContentAuth...");
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
        responseData.put("message", "WfContent Auth by id not found in the database.");
        try {
            SubmoduleUserAuth submoduleUserAuth = new SubmoduleUserAuth();
            submoduleUserAuth.setLinkId(id);

            if (id > 0) {
                if (structureId == 0) {
                    UserProfileService userProfileService = new UserProfileService();
                    submoduleUserAuth.setUserProfile(userProfileService.getByIdNotRemoved(userId));

                } else if (userId == 0) {
                    StructureService structureService = new StructureService();
                    submoduleUserAuth.setStructure(structureService.getByIdNotRemoved(structureId));
                }
            } else {//for auth template
                submoduleUserAuth.setUserProfile(null);
                submoduleUserAuth.setStructure(null);
            }

            SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();

            for (WfFolderContentAuthModel authModel : listContentAuthModel) {
                submoduleUserAuth.setSubmoduleAuth(submoduleAuthService.getById(authModel.getSubModuleAuthId()));
                submoduleUserAuth.setAuthority(authModel.getAuth() ? "1" : "2");
                if (authModel.getId() == 0) {
                    //create
                    submoduleUserAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    submoduleUserAuth = submoduleUserAuthService.create(submoduleUserAuth);
                    submoduleUserAuth.setOrderNo(submoduleUserAuth.getId());
                    submoduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    submoduleUserAuth = submoduleUserAuthService.update(submoduleUserAuth);
                } else {
                    //update
                    submoduleUserAuth.setId(authModel.getId());
                    submoduleUserAuth.setOrderNo(authModel.getId());
                    submoduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    submoduleUserAuth = submoduleUserAuthService.update(submoduleUserAuth);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลสิทธิ์หนังสือ ด้วย รหัสหนังสือ",
            notes = "ขอข้อมูลสิทธิ์หนังสือ ด้วย รหัสหนังสือ",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfContent Auth by id success."),
        @ApiResponse(code = 404, message = "WfContent Auth by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/contentAuthMWP/{contentId}/{structureId}/{userId}")
    public Response listContentAuthMWP(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "contentId", value = "รหัสหนังสือ", required = true)
            @PathParam("contentId") int contentId,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId,
            @ApiParam(name = "userId", value = "userId", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.info("getContentAuth...");
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
        responseData.put("message", "WfContent Auth by id not found in the database.");
        try {
            WfContentService wfContentService = new WfContentService();
            WfFolderService wfFolderService = new WfFolderService();

            BaseTreeEntity treeEntity;
            treeEntity = wfFolderService.getById(wfContentService.getById(contentId).getWfContentFolderId());

            SubmoduleService subModuleService = new SubmoduleService();
            Submodule submodule = subModuleService.getBySubmoduleCode("wf_c");
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            List<SubmoduleUserAuth> listSubModuleUserAuth;
            if (structureId == 0) {
                UserProfileService userProfileService = new UserProfileService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfileService.getById(userId), treeEntity);
            } else {
                StructureService structureService = new StructureService();
                listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByStructure(submodule, structureService.getById(structureId), treeEntity);
            }

            List<WfFolderContentAuthModel> ListContentAuthModel = new ArrayList<>();
            if (!listSubModuleUserAuth.isEmpty()) {
                for (SubmoduleUserAuth x : listSubModuleUserAuth) {
                    ListContentAuthModel.add(wfFolderService.tranformToModel2(x));
                }
                status = Response.Status.OK;
                responseData.put("data", ListContentAuthModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "Method for list WfFolder by folderParentId.",
            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสแม่ของทะเบียน",
            responseContainer = "List",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder list success."),
        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByParentIdAuth/{parentId}")
    public Response listByParentIdAuth(
            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "parentId", value = "รหัสแม่ของทะเบียน", required = true)
            @PathParam("parentId") int parentId
    ) {
        LOG.info("listByFolderParentId...");
        LOG.info("parentId = " + parentId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder by parentId not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listByParentId(listOptionModel.getOffset(), listOptionModel.getLimit(), parentId);
            List<WfFolderModel> listWfFolderModel = new ArrayList<>();
            ListReturnModel listReturnModel = new ListReturnModel(0, 0, 0);

            int userId = Integer.parseInt(httpHeaders.getHeaderString("userID"));
            if (userId == 1) {
                List<SubmoduleUserAuth> listSubModuleUserAuth = new ArrayList<>();
                SubmoduleUserAuth tmp = new SubmoduleUserAuth();
                tmp.setAuthority("1");
                for (int i = 0; i < 5; i++) { //still not get count SubmoduleUserAuth
                    listSubModuleUserAuth.add(tmp);
                }
                for (WfFolder wfFolder : listWfFolder) {
                    listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder, listSubModuleUserAuth));
                }
                if (!listWfFolder.isEmpty()) {
                    int count = listWfFolder.size() + listOptionModel.getOffset();
                    int countAll = wfFolderService.countlistByParentId(parentId);
                    int next = 0;
                    if (count >= listOptionModel.getLimit()) {
                        next = listOptionModel.getOffset() + listOptionModel.getLimit();
                        if (next >= countAll) {
                            next = 0;
                        }
                    }
                    listReturnModel = new ListReturnModel(countAll, count, next);
                }
            } else {

                for (WfFolder wfFolder : listWfFolder) {
                    BaseTreeEntity treeEntity = wfFolderService.getById(wfFolder.getId());
                    SubmoduleService subModuleService = new SubmoduleService();
                    Submodule submodule = subModuleService.getBySubmoduleCode("wf_f");
                    SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
                    UserProfileService userProfileService = new UserProfileService();
                    List<SubmoduleUserAuth> listSubModuleUserAuth = submoduleUserAuthService.getAuthorityFromTreeByUserProfile(submodule, userProfileService.getById(userId), treeEntity);
                    if ("1".equals(listSubModuleUserAuth.get(0).getAuthority())) {//still not implement litReturn (not admin, always limit@200)
                        listWfFolderModel.add(wfFolderService.tranformToModel(wfFolder, listSubModuleUserAuth));
                    }
                }
                listReturnModel = new ListReturnModel(listWfFolderModel.size(), listWfFolderModel.size(), 0);
            }

            status = Response.Status.OK;
            responseData.put("data", listWfFolderModel);
            responseData.put("listReturn", listReturnModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลแฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            notes = "ขอข้อมูลแฟ้มทะเบียน ด้วย รหัสแฟ้มทะเบียน",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/folderMenuType/{id}")
    public Response getFolderMenuTypeByParam(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสแฟ้มทะเบียน", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("getfolderMenuType...");
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
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder wfFolder = wfFolderService.getById(id);
            if (wfFolder != null) {
                ParamService paramService = new ParamService();
                String[] params = paramService.getByParamName("RESERVEFOLDERS").getParamValue().split(",");
                String menuType = "list-saraban";
                int folderId = wfFolder.getId();

                for (String param : params) {
                    if (folderId == Integer.parseInt(param)) {
                        menuType = "list-saraban-reserve";
                    }
                }

                status = Response.Status.OK;
                responseData.put("data", menuType);
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
            value = "แก้ไขข้อมูลลำดับแฟ้มทะเบียนลัด",
            notes = "แก้ไขข้อมูลลำดับแฟ้มทะเบียนลัด",
            response = WfFolderModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder updeted by id success."),
        @ApiResponse(code = 404, message = "WfFolder by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateOrder")
    public Response updateOrder(
            WfFolderModel wfFolderModel
    ) {
        LOG.info("updateOrder...");
        LOG.info("id = " + wfFolderModel.getId());
        Gson gs = new GsonBuilder()
                .setVersion(wfFolderModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder by id not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            WfFolder shortcutFolder = wfFolderService.getById(wfFolderModel.getId());
            if (shortcutFolder != null) {
                shortcutFolder.setOrderNo(wfFolderModel.getOrderNo());
                shortcutFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                shortcutFolder = wfFolderService.update(shortcutFolder);
                status = Response.Status.OK;
                responseData.put("data", wfFolderService.tranformToModel(shortcutFolder));
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
            value = "Method for list WfFolder by wfFolderLinkId.",
            notes = "ขอรายการแฟ้มทะเบียนด้วยรหัสเชื่อมต่อหน่วยงาน",
            responseContainer = "List",
            response = WfFolderModel_groupWfFolderAndShortcut.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "WfFolder list success."),
        @ApiResponse(code = 404, message = "WfFolder list not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listByLinkId/{structureId}")
    public Response listByLinkId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "structureId", value = "structureId", required = true)
            @PathParam("structureId") int structureId
    ) {
        LOG.info("listByLinkId...");
        LOG.info("structureId = " + structureId);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "WfFolder by listByLinkId not found in the database.");
        try {
            WfFolderService wfFolderService = new WfFolderService();
            List<WfFolder> listWfFolder = wfFolderService.listByStructureId(structureId);
            if (!listWfFolder.isEmpty()) {
                List<WfFolderModel> listWffolderModel = new ArrayList<>();
                for (WfFolder wfFolder : listWfFolder) {
                    listWffolderModel.add(wfFolderService.tranformToModel(wfFolder));
                }
                status = Response.Status.OK;
                responseData.put("data", listWffolderModel);
                responseData.put("message", "");
            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "createAuthTemplateValue",
            notes = "createAuthTemplateValue",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "createAuthTemplateValue success."),
        @ApiResponse(code = 404, message = "createAuthTemplateValue not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/authTemplateValue/{templateId}")
    public Response createAuthTemplateValue(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "templateId", value = "รหัสรูปแบบสิทธิ์", required = true)
            @PathParam("templateId") int templateId,
            List<WfFolderContentAuthModel> listContentAuthModel
    ) {
        LOG.info("createAuthTemplateValue...");
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
        responseData.put("message", "createAuthTemplateValue not found in the database.");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            SubmoduleUserAuth submoduleUserAuth = new SubmoduleUserAuth();
            submoduleUserAuth.setLinkId(templateId);
            submoduleUserAuth.setUserProfile(null);
            submoduleUserAuth.setStructure(null);
            for (WfFolderContentAuthModel authModel : listContentAuthModel) {
                submoduleUserAuth.setSubmoduleAuth(new SubmoduleAuthService().getById(authModel.getSubModuleAuthId()));
                submoduleUserAuth.setAuthority(authModel.getAuth() ? "1" : "2");
                submoduleUserAuth.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleUserAuth = submoduleUserAuthService.create(submoduleUserAuth);
                submoduleUserAuth.setOrderNo(submoduleUserAuth.getId());
                submoduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                submoduleUserAuthService.update(submoduleUserAuth);
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "createAuthTemplateValue",
            notes = "createAuthTemplateValue",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "createAuthTemplateValue success."),
        @ApiResponse(code = 404, message = "createAuthTemplateValue not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/authTemplateValue/{templateId}")
    public Response updateAuthTemplateValue(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "templateId", value = "รหัสรูปแบบสิทธิ์", required = true)
            @PathParam("templateId") int templateId,
            List<WfFolderContentAuthModel> listContentAuthModel
    ) {
        LOG.info("createAuthTemplateValue...");
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
        responseData.put("message", "createAuthTemplateValue not found in the database.");
        try {
            SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
            for (WfFolderContentAuthModel authModel : listContentAuthModel) {
                SubmoduleUserAuth submoduleUserAuth = submoduleUserAuthService.getById(authModel.getId());
                if (submoduleUserAuth != null) {
                    submoduleUserAuth.setAuthority(authModel.getAuth() ? "1" : "2");
                    submoduleUserAuth.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    submoduleUserAuthService.update(submoduleUserAuth);
                }
            }
            status = Response.Status.OK;
            responseData.put("data", null);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "listAuthTemplateValue",
            notes = "listAuthTemplateValue",
            response = WfFolderContentAuthModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "listAuthTemplateValue success."),
        @ApiResponse(code = 404, message = "listAuthTemplateValue not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/listAuthTemplateValue/{linkId}")
    public Response listAuthTemplateValue(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "linkId", value = "รหัสรูปแบบสิทธิ์", required = true)
            @PathParam("linkId") int linkId
    ) {
        LOG.info("listAuthTemplateValue...");
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
        responseData.put("message", "listAuthTemplateValue not found in the database.");
        try {
            List<WfFolderContentAuthModel> ListContentAuthModel = new ArrayList<>();
            List<SubmoduleUserAuth> listSubModuleUserAuth = new SubmoduleUserAuthService().listTemplateValueByLinkId(linkId);
            if (!listSubModuleUserAuth.isEmpty()) {
                for (SubmoduleUserAuth x : listSubModuleUserAuth) {
                    ListContentAuthModel.add(new WfFolderService().tranformToModel2(x));
                }
            }
            status = Response.Status.OK;
            responseData.put("data", ListContentAuthModel);
            responseData.put("message", "");
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

}
