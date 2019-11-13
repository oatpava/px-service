package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.Position;
import com.px.admin.entity.Structure;
import com.px.admin.entity.Title;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.VUserProfile;
import com.px.admin.model.UserProfileConvertModel;
import com.px.admin.model.UserProfileModel;
import com.px.admin.model.VUserProfileModel;
import com.px.admin.service.PositionService;
import com.px.admin.service.PositionTypeService;
import com.px.admin.service.StructureService;
import com.px.admin.service.TitleService;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserProfileTypeService;
import com.px.admin.service.UserService;
import com.px.admin.service.UserStatusService;
import com.px.admin.service.UserTypeOrderService;
import com.px.admin.service.VUserProfileService;
import com.px.mwp.entity.UserProfileFolder;
import com.px.mwp.service.UserProfileFolderService;
import com.px.share.entity.Param;
import com.px.share.entity.TempTable;
import com.px.share.filter.HTTPHeaderNames;
import com.px.share.model.AuthenticationModel;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import com.px.share.service.TempTableService;
import com.px.share.util.PxInit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "UserProfile ประวัติผู้ใช้งานระบบ")
@Path("v1/userProfiles")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserProfileResource {

    private static final Logger LOG = Logger.getLogger(UserProfileResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create UserProfile",
            notes = "สร้างผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "UserProfile created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            UserProfileModel userProfileModel
    ) {
        LOG.debug("create...");
        Gson gs = new GsonBuilder()
                .setVersion(userProfileModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            StructureService structureService = new StructureService();
            TitleService titleService = new TitleService();
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            PositionService positionService = new PositionService();
            PositionTypeService positionTypeService = new PositionTypeService();

            UserStatusService userProfileStatusService = new UserStatusService();
            UserProfile userProfile = new UserProfile();
            userProfile.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            userProfile.setStructure(structureService.getByIdNotRemoved(userProfileModel.getStructure().getId()));
            userProfile.setTitle(titleService.getByIdNotRemoved(userProfileModel.getTitle().getId()));
            userProfile.setUser(userService.getByIdNotRemoved(userProfileModel.getUser().getId()));
            userProfile.setUserProfileDefaultSelect(userProfileModel.getDefaultSelect());
            userProfile.setUserProfileEmail(userProfileModel.getEmail());
            userProfile.setUserProfileFirstName(userProfileModel.getFirstName());
            userProfile.setUserProfileFirstNameEng(userProfileModel.getFirstNameEng());
            userProfile.setUserProfileFullName(userProfileModel.getFirstName() + " " + userProfileModel.getLastName());
            userProfile.setUserProfileFullNameEng(userProfileModel.getFirstNameEng() + " " + userProfileModel.getLastNameEng());
            userProfile.setUserProfileLastName(userProfileModel.getLastName());
            userProfile.setUserProfileLastNameEng(userProfileModel.getLastNameEng());
            userProfile.setUserProfileTel(userProfileModel.getTel());
            userProfile.setUserProfileVersion(1);
            userProfile.setUserProfileStatus(userProfileStatusService.getById(1));
            userProfile.setUserProfileType(userProfileTypeService.getByIdNotRemoved(userProfileModel.getUserProfileType().getId()));
//            userProfile.setUserProfileTypeOrder(userTypeOrderService.getByIdNotRemoved(userProfileModel.getUserTypeOrder().getId()));
            userProfile.setUserProfileCardId(userProfileModel.getIdCard());
            userProfile.setUserProfileCode(userProfileModel.getCode());
            userProfile.setUserProfileAddress(userProfileModel.getAddress());
            userProfile.setPosition(positionService.getByIdNotRemoved(userProfileModel.getPosition().getId()));
            userProfile.setPositionLevel(userProfileModel.getPositionLevel());
//            userProfile.setPositionType(positionTypeService.getByIdNotRemoved(userProfileModel.getPositionType().getId()));
            userProfile.setDigitalKey(userProfileModel.getDigitalKey());
            UserProfileService userProfileService = new UserProfileService();
            userProfile = userProfileService.create(userProfile);

            responseData.put("data", userProfileService.tranformToModel(userProfile));
            responseData.put("message", "UserProfile created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);

            //LogData For create userProfile
            userProfileService.saveLogForCreate(userProfile, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for create UserProfile",
            notes = "สร้างผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "UserProfile created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{userId}/userProfile")
    public Response createUserProfile(
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId,
            UserProfileModel userProfileModel
    ) {
        LOG.debug("createUserProfile...");
        LOG.debug("userId = " + userId);
        Gson gs = new GsonBuilder()
                .setVersion(userProfileModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            StructureService structureService = new StructureService();
            TitleService titleService = new TitleService();
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            PositionService positionService = new PositionService();
            PositionTypeService positionTypeService = new PositionTypeService();
            UserProfileService userProfileService = new UserProfileService();
            UserStatusService userProfileStatusService = new UserStatusService();

            UserService userService = new UserService();
            User user = userService.getByIdNotRemoved(userId);
            UserProfile oldUserProfile = userProfileService.getByUserId(userId);
//            oldUserProfile.setUserProfileDefaultSelect(1);
            oldUserProfile = userProfileService.update(oldUserProfile);

            UserProfile userProfile = new UserProfile();
            userProfile.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            userProfile.setStructure(structureService.getByIdNotRemoved(userProfileModel.getStructure().getId()));
            userProfile.setTitle(titleService.getById(userProfileModel.getTitle().getId()));
            userProfile.setUser(user);
            userProfile.setUserProfileDefaultSelect(1);
            userProfile.setUserProfileEmail(userProfileModel.getEmail());
            userProfile.setUserProfileFirstName(userProfileModel.getFirstName());
            userProfile.setUserProfileFirstNameEng(userProfileModel.getFirstNameEng());
            userProfile.setUserProfileFullName(userProfileModel.getFirstName() + " " + userProfileModel.getLastName());
            userProfile.setUserProfileFullNameEng(userProfileModel.getFirstNameEng() + " " + userProfileModel.getLastNameEng());
            userProfile.setUserProfileLastName(userProfileModel.getLastName());
            userProfile.setUserProfileLastNameEng(userProfileModel.getLastNameEng());
            userProfile.setUserProfileTel(userProfileModel.getTel());
            userProfile.setUserProfileVersion(1);
            userProfile.setUserProfileStatus(userProfileStatusService.getByIdNotRemoved(1));
            userProfile.setUserProfileType(userProfileTypeService.getByIdNotRemoved(userProfileModel.getUserProfileType().getId()));
//            userProfile.setUserProfileTypeOrder(userTypeOrderService.getByIdNotRemoved(userProfileModel.getUserTypeOrder().getId()));
            userProfile.setUserProfileCardId(userProfileModel.getIdCard());
            userProfile.setUserProfileCode(userProfileModel.getCode());
            userProfile.setUserProfileAddress(userProfileModel.getAddress());
            userProfile.setPosition(positionService.getByIdNotRemoved(userProfileModel.getPosition().getId()));
//            userProfile.setPositionType(positionTypeService.getByIdNotRemoved(userProfileModel.getPositionType().getId()));
            userProfile.setDigitalKey(userProfileModel.getDigitalKey());

            userProfile = userProfileService.create(userProfile);

            responseData.put("data", userProfileService.tranformToModel(userProfile));
            responseData.put("message", "UserProfile created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);

            //LogData For create userProfile
            userProfileService.saveLogForCreate(userProfile, httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get UserProfile by id",
            notes = "ขอข้อมูลผู้ใช้งานระบบ ด้วย รหัสผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile by id success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("getById...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                //                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        if (id == -1) {
            id = Integer.parseInt(httpHeaders.getHeaderString("userID"));
        }
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(id);
            if (userProfile != null) {
                status = Response.Status.OK;
                responseData.put("data", userProfileService.tranformToModel(userProfile));
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
            value = "Method for update UserProfile.",
            notes = "แก้ไขผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile updeted by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            UserProfileModel userProfileModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(userProfileModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            UserProfileService userProfileService = new UserProfileService();
            PositionService positionService = new PositionService();
            PositionTypeService positionTypeService = new PositionTypeService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(id);
            LOG.debug(userProfile);
            LOG.debug("title = " + userProfileModel.getTitle().getId());
            LOG.debug("profileType = " + userProfileModel.getUserProfileType().getId());
            LOG.debug("position = " + userProfileModel.getPosition().getId());
            if (userProfile != null) {
                String fullName = userProfile.getUserProfileFullName();

                TitleService titleService = new TitleService();
                UserProfile newUserProfile = new UserProfile();
                userProfile.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                if (userProfileModel.getStructure().getId() != userProfile.getStructure().getId()) {
                    userProfile.setStructure(new StructureService().getByIdNotRemoved(userProfileModel.getStructure().getId()));
                    newUserProfile = userProfileService.update(userProfile);
                } else {
                    userProfile.setTitle(titleService.getByIdNotRemoved(userProfileModel.getTitle().getId()));
                    userProfile.setUserProfileDefaultSelect(userProfileModel.getDefaultSelect());
                    userProfile.setUserProfileEmail(userProfileModel.getEmail());
                    userProfile.setUserProfileFirstName(userProfileModel.getFirstName());
                    userProfile.setUserProfileFirstNameEng(userProfileModel.getFirstNameEng());
                    userProfile.setUserProfileLastName(userProfileModel.getLastName());
                    userProfile.setUserProfileLastNameEng(userProfileModel.getLastNameEng());
                    userProfile.setUserProfileFullName(userProfileModel.getFirstName() + " " + userProfileModel.getLastName());
                    userProfile.setUserProfileFullNameEng(userProfileModel.getFirstNameEng() + " " + userProfileModel.getLastNameEng());
                    userProfile.setUserProfileTel(userProfileModel.getTel());
                    userProfile.setUserProfileType(userProfileTypeService.getByIdNotRemoved(userProfileModel.getUserProfileType().getId()));
                    if (userProfileModel.getUserTypeOrder() != null) {
                        userProfile.setUserProfileTypeOrder(userTypeOrderService.getByIdNotRemoved(userProfileModel.getUserTypeOrder().getId()));
                    }
                    userProfile.setUserProfileCardId(userProfileModel.getIdCard());
                    userProfile.setUserProfileCode(userProfileModel.getCode());
                    userProfile.setUserProfileAddress(userProfileModel.getAddress());
                    userProfile.setUserProfileStatus(new UserStatusService().getByIdNotRemoved(userProfileModel.getUserStatus().getId()));
                    userProfile.setPosition(positionService.getByIdNotRemoved(userProfileModel.getPosition().getId()));
                    userProfile.setPositionLevel(userProfileModel.getPositionLevel());
                    if (userProfileModel.getPositionType() != null) {
                        userProfile.setPositionType(positionTypeService.getByIdNotRemoved(userProfileModel.getPositionType().getId()));
                    }
//                if(!userProfileModel.getDigitalKey().equals("")){
//                    DigitalKey digitalKey = new DigitalKey(userProfileModel.getDigitalKey()+userProfile.getId());
//                    userProfile.setDigitalKey(digitalKey.getEncryptDigitalKey());
//                }                
                }
                newUserProfile = userProfileService.update(userProfile);
                LOG.debug(newUserProfile);
                status = Response.Status.OK;
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");

                //LogData For update userProfile
                userProfileService.saveLogForUpdate(userProfile, newUserProfile, httpHeaders.getHeaderString("clientIp"));

                //oat-add
                if (!fullName.equals(newUserProfile.getUserProfileFullName())) {
                    UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                    UserProfileFolder inbox = userProfileFolderService.getByUserProfileId(id, "I");
                    if (inbox != null) {
                        inbox.setUserProfileFolderDetail("หนังสือเข้าของ " + newUserProfile.getUserProfileFullName());
                        userProfileFolderService.update(inbox);
                    }
                    UserProfileFolder outbox = userProfileFolderService.getByUserProfileId(id, "O");
                    if (outbox != null) {
                        outbox.setUserProfileFolderDetail("หนังสือออกของ " + newUserProfile.getUserProfileFullName());
                        userProfileFolderService.update(outbox);
                    }
                    UserProfileFolder recycleBin = userProfileFolderService.getByUserProfileId(id, "Z");
                    if (recycleBin != null) {
                        recycleBin.setUserProfileFolderDetail("ถังขยะของ " + newUserProfile.getUserProfileFullName());
                        userProfileFolderService.update(recycleBin);
                    }
                    UserProfileFolder myWork = userProfileFolderService.getByUserProfileId(id, "W");
                    if (myWork != null) {
                        myWork.setUserProfileFolderDetail("แฟ้มส่วนตัวของ " + newUserProfile.getUserProfileFullName());
                        userProfileFolderService.update(myWork);
                    }
                }
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
            value = "Method for delete UserProfile by id.",
            notes = "ลบผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile deleted by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("remove...");
        LOG.debug("id = " + id);
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
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.remove(id, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (userProfile != null) {
                userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                status = Response.Status.OK;
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");

                //LogData For remove userProfile
                userProfileService.saveLogForRemove(userProfile, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for delete UserProfile by id.",
            notes = "ลบผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile deleted by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/users/{userId}")
    public Response removeByUserId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("removeByUserId...");
        LOG.debug("userId = " + userId);
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
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            boolean result = userProfileService.removeByUserId(userId, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            status = Response.Status.OK;
            responseData.put("data", result);
            responseData.put("message", "");
            //LogData For remove userProfile
//                userProfileService.saveLogForRemove(userProfile,httpHeaders.getHeaderString("clientIp"));
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list UserProfile.",
            notes = "รายการผู้ใช้งานระบบ",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response list(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("list...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            List<UserProfile> listUserProfile = userProfileService.list(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUserProfile.isEmpty()) {
                int countAll = userProfileService.countAll();
                int count = listUserProfile.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
                for (UserProfile userProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(userProfile));
                }
                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("list", listReturnModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for search UserProfile By firstName.",
            notes = "ค้นหาผู้ใช้งานระบบ",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile search success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile search not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/search")
    public Response searchByFields(
            @Context UriInfo uriInfo,
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("searchByFields...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile search not found in the database.");
        responseData.put("errorMessage", "");
        try {
            String filedSearch = "userProfileFirstName";
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
//            for (String key : queryParams.keySet()) {
//                if(filedSearch.contains(","+key+",")){
//                    for (String value : queryParams.get(key)) {
//                        String[] valueArray = value.split(",");
//                        int i = 0;
//                        for(i = 0;i<valueArray.length;i++){
//                            LOG.debug("key = " + key + " value = " + valueArray[i]);
//                        }
//                    }
//                }                
//            }

            UserProfileService userProfileService = new UserProfileService();
            List<UserProfile> listUserProfile = userProfileService.search(queryParams, listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUserProfile.isEmpty()) {
                int countAll = userProfileService.countAll();
                int count = listUserProfile.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
                for (UserProfile userProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(userProfile));
                }
                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("list", listReturnModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list UserProfile for Change.",
            notes = "รายการผู้ใช้งานระบบ ของผู้ที่กำลังใช้งานระบบอยู่",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/profiles")
    public Response listProfiles(
            @BeanParam ListOptionModel listOptionModel
    ) {
        LOG.debug("profiles...");
        LOG.debug("offset = " + listOptionModel.getOffset());
        LOG.debug("limit = " + listOptionModel.getLimit());
        Gson gs = new GsonBuilder()
                .setVersion(listOptionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", listUserProfileModel);
        responseData.put("message", "UserProfile list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            List<UserProfile> listUserProfile = userProfileService.listByUserId(userProfile.getUser().getId(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUserProfile.isEmpty()) {
                int countAll = userProfileService.countAllByUserId(userProfile.getUser().getId());
                int count = listUserProfile.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                listUserProfileModel = new ArrayList<>();
                for (UserProfile tempUserProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(tempUserProfile));
                }
                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("list", listReturnModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for change UserProfile.",
            notes = "เปลี่ยนผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile changed by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/change/{id}")
    public Response changeProfile(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("change...");
        LOG.debug("id = " + id);
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
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getById(id);
            UserService userService = new UserService();
            if (userProfile != null) {
                User user = userProfile.getUser();

                List<UserProfile> listUserProfiles = userProfileService.listByUserId(user.getId(), "", "");
                for (UserProfile userP : listUserProfiles) {
                    userP.setUserProfileDefaultSelect(1);
                    userProfileService.update(userP);
                }

                userProfile.setUserProfileDefaultSelect(0);
                userProfile = userProfileService.update(userProfile);
                final String token = userService.genToken(user, userProfile);
                response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
                authenticationModel = new AuthenticationModel(true);

                status = Response.Status.OK;
                responseData.put("data", authenticationModel);
                responseData.put("message", "");

                //LogData For update userProfile
//                userProfileService.saveLogForUpdate(userProfile,newUserProfile,httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for get UserProfile by userId.",
            notes = "ข้อมูลผู้ใช้งานระบบ ด้วยรหัสเข้าใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/user/{userId}")
    public Response getProfileByUserId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("getProfilesByUserId...");
        LOG.debug("userId = " + userId);
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
        responseData.put("message", "UserProfile not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByUserId(userId);
            if (userProfile != null) {
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for list UserProfile for Change.",
            notes = "รายการผู้ใช้งานระบบ ด้วยรหัสเข้าใช้งานระบบ",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/users/{userId}")
    public Response listProfilesByUserId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("listProfilesByUserId...");
        LOG.debug("userId = " + userId);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                //                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("data", listUserProfileModel);
        responseData.put("data", null);
        responseData.put("message", "UserProfile not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            List<UserProfile> listUserProfile = userProfileService.listByUserId(userId, "createdDate", "desc");
            if (!listUserProfile.isEmpty()) {
                listUserProfileModel = new ArrayList<>();
                for (UserProfile tempUserProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(tempUserProfile));
                }
                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "Method for get default UserProfile.",
            notes = "ขอข้อมูลค่า default ผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile default success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile default not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/default")
    public Response getDefaultProfile(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("getDefaultProfiles...");
        LOG.debug("userProfileId = " + Integer.parseInt(httpHeaders.getHeaderString("userID")));
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
        responseData.put("message", "UserProfile not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getDefaultProfile(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (userProfile != null) {
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for get default UserProfile.",
            notes = "ขอข้อมูลค่า default ผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile default success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile default not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/users/{userId}/default")
    public Response getDefaultProfileByUserId(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("getDefaultProfiles...");
        LOG.debug("userId = " + userId);
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
        responseData.put("message", "UserProfile not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getDefaultProfile(userId);
            if (userProfile != null) {
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update UserProfile.",
            notes = "จัดเรียงผู้ใช้งานด้วย id",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile updeted by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateOrderNo/{id}/from/{curId}")
    public Response updateOrderNoById(
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            @ApiParam(name = "curId", value = "รหัสผู้ใช้ที่นำ Id ใหม่ไปแทรก", required = true)
            @PathParam("curId") int curId,
            UserProfileModel userProfileModel
    ) {
        LOG.debug("updateOrderNoById...");
        LOG.debug("id = " + id);
        LOG.debug("curId = " + curId);
        Gson gs = new GsonBuilder()
                .setVersion(userProfileModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(id);
            LOG.debug(userProfile);
            if (userProfile != null) {
                UserProfile newUserProfile = new UserProfile();
                double orderNoNew = userProfileService.findOrderNo(curId);
                LOG.debug("orderNoNew : " + orderNoNew);
                userProfile.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                userProfile.setOrderNo(orderNoNew);
                newUserProfile = userProfileService.update(userProfile);
                LOG.debug(newUserProfile);
                status = Response.Status.OK;
                responseData.put("data", userProfileService.tranformToModel(userProfile));
                responseData.put("message", "");

                //LogData For update userProfile
                userProfileService.saveLogForUpdate(userProfile, newUserProfile, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for check Duplicate UserProfile by code.",
            notes = "เช็คค่าซ้ำ",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile check duplicate by code success.", response = AuthenticationModel.class)
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkDup/{code}/id/{id}")
    public Response checkDup(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "code", value = "รหัสอ้างอิงผู้ใช้", required = true)
            @PathParam("code") String code,
            @ApiParam(name = "id", value = "รหัสโครงสร้าง", required = true)
            @PathParam("id") int id
    ) {
        LOG.info("checkDup...");
        LOG.info("code = " + code);
        LOG.info("id = " + id);
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
        responseData.put("message", "Structure by id not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserProfileService userProfileService = new UserProfileService();
            if (id != 0) {
                UserProfile userProfile = userProfileService.getById(id);
                if (!code.equals(userProfile.getUserProfileCode())) {
                    boolean result = userProfileService.checkDup(code);
                    if (result) {
                        authenticationModel = new AuthenticationModel(result);
                        responseData.put("message", "");
                    }
                } else {
                    authenticationModel = new AuthenticationModel(false);
                }
                responseData.put("data", authenticationModel);
            } else {
                boolean result = userProfileService.checkDup(code);
                if (result) {
                    authenticationModel = new AuthenticationModel(result);
                    responseData.put("data", authenticationModel);
                    responseData.put("message", "");
                }
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for import file Excel UserProfile",
            notes = "นำเข้าข้อมูลข้อมูลโครงสร้าง",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "UserProfile Excel created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/importFileExcel")
    public Response importFileExcel(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fileId", value = "รหัสไฟล์", required = true)
            @DefaultValue("0") @QueryParam("fileId") int fileId
    ) {
        LOG.info("importFile...");
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            int countRecord = userProfileService.readFileUserProExcel(fileId, versionModel.getClientIp());

            status = Response.Status.CREATED;
            responseData.put("data", countRecord);
            responseData.put("success", true);
            responseData.put("countRecord", countRecord);
//        LOG.info("countRecord = "+countRecord);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for import file atternal",
            notes = "นำเข้าข้อมูลผู้ใช้",
            response = VUserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "VUserProfile Excel created successfully.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/importFile")
    public Response importFile(
            List<VUserProfileModel> listVUserProfileModel
    //            @BeanParam VersionModel versionModel,
    //            ArrayList<Object> userList
    //            ArrayList<String> userList
    //            @ApiParam(name = "fileId", value = "รหัสไฟล์", required = true)
    //            @DefaultValue("0") @QueryParam("fileId") int fileId
    ) {
        LOG.info("importFile...");
        LOG.info("userList..." + listVUserProfileModel.size());
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("message", "Internal Server Error!");
        responseData.put("errorMessage", "");
        try {
            VUserProfileService vUserProfileService = new VUserProfileService();
            VUserProfile vUserProfile = new VUserProfile();

            List<VUserProfile> listVUserProfile = vUserProfileService.listAllNotRemove("id", "asc");
            if (!listVUserProfile.isEmpty()) {
                for (VUserProfile vUserProfile1 : listVUserProfile) {
                    vUserProfileService.delete(vUserProfile1);
                }
            }
            for (VUserProfileModel vUserProfileModel : listVUserProfileModel) {
                StructureService structureService = new StructureService();
                LOG.info("vUserProfileModel : " + vUserProfileModel.getCode() + " - " + vUserProfileModel.getFirstNameEng());
                Structure struc = structureService.getByCode(vUserProfileModel.getFirstNameEng());
//                structureCode = String.valueOf((int) cell.getNumericCellValue());
//                                    StructureService structureService = new StructureService();
////                                    VStructureService structureService = new VStructureService();
//                                    Structure struc = structureService.getByCode(structureCode);
//                                    if(struc != null){
//                                        stat = 1;
//                                        idStructure = struc.getId();
//                                    }
                if (struc != null) {
                    LOG.info("UserProfileCode : " + vUserProfileModel.getCode() + " Title : " + vUserProfileModel.getTitle().getName());
                    vUserProfile.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));//test
//                    vUserProfile.setCreatedBy(1);//test
                    vUserProfile.setUserProfileCode(vUserProfileModel.getCode());
                    vUserProfile.setStructure(new StructureService().getByIdNotRemoved(struc.getId()));
                    if (vUserProfileModel.getTitle() != null && !"".equalsIgnoreCase(vUserProfileModel.getTitle().getName())) {
                        TitleService titleService = new TitleService();
                        Title title = titleService.getByTitleName(vUserProfileModel.getTitle().getName());
                        if (title != null) {
                            vUserProfile.setTitle(titleService.getByIdNotRemoved(vUserProfileModel.getTitle().getId()));
                        }
                    }
//                    if (vUserProfileModel.getTitle() != null) {
//                        vUserProfile.setTitle(new TitleService().getByIdNotRemoved(vUserProfileModel.getTitle().getId()));
//                    }
                    vUserProfile.setUserProfileFirstName(vUserProfileModel.getFirstName());
                    vUserProfile.setUserProfileLastName(vUserProfileModel.getLastName());
                    vUserProfile.setUserProfileFullName(vUserProfileModel.getFullName());
                    vUserProfile.setUserProfileEmail(vUserProfileModel.getEmail());
                    vUserProfile.setUserProfileTel(vUserProfileModel.getTel());
//                vUserProfile.setUserProfileType(new UserProfileTypeService().getById(vUserProfileModel));
//                vUserProfile.setUserProfileFirstNameEng(fristNameEng);
//                vUserProfile.setUserProfileLastNameEng(lastNameEng);
//                vUserProfile.setUserProfileFullNameEng(fullNameEng);
//                vUserProfile.setUserProfileCardId(cardId);
//                vUserProfile.setUserProfileAddress(address);
//                vUserProfile.setPosition(new PositionService().getById(position));
                    PositionService positionService = new PositionService();
                    if (vUserProfileModel.getLastNameEng() != null && !"".equalsIgnoreCase(vUserProfileModel.getLastNameEng())) {
                        Position position = positionService.getByPositionName(vUserProfileModel.getLastNameEng());
                        if (position != null) {
//                            LOG.info("aaaaaaaaaa...");
                            vUserProfile.setPosition(positionService.getByIdNotRemoved(position.getId()));
                        } else {
//                            LOG.info("bbbbbbbbbb...");
                            position = new Position();
                            position.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                            position.setCreatedBy(1);
                            position.setPositionName(vUserProfileModel.getLastNameEng());
                            position = positionService.create(position);
                        }
                    }
                    vUserProfile.setPositionLevel(vUserProfileModel.getPositionLevel());
                    if (vUserProfileModel.getUserStatus() != null) {
                        vUserProfile.setUserProfileStatus(new UserStatusService().getByIdNotRemoved(vUserProfileModel.getUserStatus().getId()));
                    }
//                VUserProfileService vUserProfileService = new VUserProfileService();
                    vUserProfile = vUserProfileService.create(vUserProfile);
                }
            }
            responseData.put("data", vUserProfileService.tranformToModel(vUserProfile));
            status = Response.Status.CREATED;
            responseData.put("success", true);
//          responseData.put("countRecord", countRecord);
//        LOG.info("countRecord = "+countRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();

    }

    @ApiOperation(
            value = "Method for get VUserProfile by id",
            notes = "ขอข้อมูลโครงสร้างหน่วยงาน ด้วย รหัสอ้างอิงโครงสร้าง",
            response = VUserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile by id success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "userProfileCode/{code}")
    public Response getByCode(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "code", value = "รหัสอ้างอิงผู้ใช้ระบบ", required = true)
            @PathParam("code") String code
    ) {
        LOG.debug("getByCode...");
        LOG.debug("code = " + code);
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
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            VUserProfileService vUserProfileService = new VUserProfileService();
            VUserProfile vUserProfile = vUserProfileService.getByCode(code);
            if (vUserProfile != null) {
                status = Response.Status.OK;
                responseData.put("data", vUserProfileService.tranformToModel(vUserProfile));
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
            value = "Method for list All Convert UserProfile.",
            notes = "รายการนำเข้าผู้ใช้งานระบบทั้งหมด",
            responseContainer = "List",
            response = UserProfileConvertModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile Convert list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile Convert list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/convert")
    public Response listConvert(
            @BeanParam VersionModel versionModel,
            //            @BeanParam ListOptionModel listOptionModel,
            @ApiParam(name = "vstatus", value = "สถานะ", required = false)
            @DefaultValue("0") @QueryParam("vstatus") int vstatus,
            @ApiParam(name = "userCode", value = "รหัสพนักงาน", required = false)
            @DefaultValue("") @QueryParam("userCode") String userCode,
            @ApiParam(name = "fullName", value = "ชื่อ-นามสกุล", required = false)
            @DefaultValue("") @QueryParam("fullName") String fullName,
            @ApiParam(name = "positionName", value = "ชื่อตำแหน่งตามสายงาน", required = false)
            @DefaultValue("") @QueryParam("positionName") String positionName,
            @ApiParam(name = "structureCode", value = "รหัสหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("structureCode") String structureCode,
            @ApiParam(name = "structureName", value = "ชื่อหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("structureName") String structureName,
            @ApiParam(name = "strucShortName", value = "ชื่อย่อหน่วยงาน", required = false)
            @DefaultValue("") @QueryParam("strucShortName") String strucShortName,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.debug("list convert...");
        LOG.debug("vstatus = " + vstatus);
        LOG.debug("userCode = " + userCode);
        LOG.debug("fullName = " + fullName);
        LOG.debug("positionName = " + positionName);
        LOG.debug("structureCode = " + structureCode);
        LOG.debug("structureName = " + structureName);
        LOG.debug("strucShortName = " + strucShortName);
        LOG.debug("sort = " + sort);
        LOG.debug("dir = " + dir);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        UserProfileModel userProfileModel = new UserProfileModel();
        VUserProfileModel vUserProfileModel = new VUserProfileModel();
        UserProfileConvertModel userProfileConvertModel = new UserProfileConvertModel();
        ArrayList<UserProfileConvertModel> listUserProfileConvertModel = new ArrayList<>();
        ArrayList<UserProfileConvertModel> listUserProfileConvertModel2 = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", listUserProfileConvertModel);
        responseData.put("success", false);
        responseData.put("message", "Structure Convert list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            VUserProfileService vUserProfileService = new VUserProfileService();
            int stat = 0;
            int count = 0;
            int chkSearch = 0;
            List<UserProfile> listUserProfile = userProfileService.listAll(sort, dir);
            List<VUserProfile> listVUserProfile = vUserProfileService.listAll(sort, dir);
            // add
            if (!listVUserProfile.isEmpty()) {
                for (VUserProfile vUserProfile : listVUserProfile) {
                    vUserProfileModel = new VUserProfileModel();
                    if (!listUserProfile.isEmpty()) {
                        for (UserProfile userProfile : listUserProfile) {
                            if (userProfile.getUserProfileCode() != null && vUserProfile.getUserProfileCode() != null) {
                                // edit
                                if (userProfile.getUserProfileCode().equalsIgnoreCase(vUserProfile.getUserProfileCode())) {
                                    if (!userProfile.getStructure().getId().equals(vUserProfile.getStructure().getId())) {
                                        stat = 4;
                                        if (userProfile.getTitle() != null && vUserProfile.getTitle() != null && !userProfile.getTitle().getId().equals(vUserProfile.getTitle().getId())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileFirstName() != null && vUserProfile.getUserProfileFirstName() != null && !userProfile.getUserProfileFirstName().equalsIgnoreCase(vUserProfile.getUserProfileFirstName())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileLastName() != null && vUserProfile.getUserProfileLastName() != null && !userProfile.getUserProfileLastName().equalsIgnoreCase(vUserProfile.getUserProfileLastName())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileFullName() != null && vUserProfile.getUserProfileFullName() != null && !userProfile.getUserProfileFullName().equalsIgnoreCase(vUserProfile.getUserProfileFullName())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileEmail() != null && vUserProfile.getUserProfileEmail() != null && !userProfile.getUserProfileEmail().equalsIgnoreCase(vUserProfile.getUserProfileEmail())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileType() != null && vUserProfile.getUserProfileType() != null && !userProfile.getUserProfileType().getId().equals(vUserProfile.getUserProfileType().getId())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileTel() != null && vUserProfile.getUserProfileTel() != null && !userProfile.getUserProfileTel().equalsIgnoreCase(vUserProfile.getUserProfileTel())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileFirstNameEng() != null && vUserProfile.getUserProfileFirstNameEng() != null && !userProfile.getUserProfileFirstNameEng().equalsIgnoreCase(vUserProfile.getUserProfileFirstNameEng())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileLastNameEng() != null && vUserProfile.getUserProfileLastNameEng() != null && !userProfile.getUserProfileLastNameEng().equalsIgnoreCase(vUserProfile.getUserProfileLastNameEng())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileFullNameEng() != null && vUserProfile.getUserProfileFullNameEng() != null && !userProfile.getUserProfileFullNameEng().equalsIgnoreCase(vUserProfile.getUserProfileFullNameEng())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileCardId() != null && vUserProfile.getUserProfileCardId() != null && !userProfile.getUserProfileCardId().equalsIgnoreCase(vUserProfile.getUserProfileCardId())) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileAddress() != null && vUserProfile.getUserProfileAddress() != null && !userProfile.getUserProfileAddress().equalsIgnoreCase(vUserProfile.getUserProfileAddress())) {
                                            stat = 5;
                                        } else if (userProfile.getPosition().getId() != vUserProfile.getPosition().getId()) {
                                            stat = 5;
                                        } else if (userProfile.getPositionLevel() != vUserProfile.getPositionLevel()) {
                                            stat = 5;
                                        } else if (userProfile.getUserProfileStatus() != null && vUserProfile.getUserProfileStatus() != null && !userProfile.getUserProfileStatus().getId().equals(vUserProfile.getUserProfileStatus().getId())) {
                                            stat = 5;
                                        }
                                    } else {
                                        if (userProfile.getTitle() != null && vUserProfile.getTitle() != null && !userProfile.getTitle().getId().equals(vUserProfile.getTitle().getId())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileFirstName() != null && vUserProfile.getUserProfileFirstName() != null && !userProfile.getUserProfileFirstName().equalsIgnoreCase(vUserProfile.getUserProfileFirstName())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileLastName() != null && vUserProfile.getUserProfileLastName() != null && !userProfile.getUserProfileLastName().equalsIgnoreCase(vUserProfile.getUserProfileLastName())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileFullName() != null && vUserProfile.getUserProfileFullName() != null && !userProfile.getUserProfileFullName().equalsIgnoreCase(vUserProfile.getUserProfileFullName())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileEmail() != null && vUserProfile.getUserProfileEmail() != null && !userProfile.getUserProfileEmail().equalsIgnoreCase(vUserProfile.getUserProfileEmail())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileType() != null && vUserProfile.getUserProfileType() != null && !userProfile.getUserProfileType().getId().equals(vUserProfile.getUserProfileType().getId())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileTel() != null && vUserProfile.getUserProfileTel() != null && !userProfile.getUserProfileTel().equalsIgnoreCase(vUserProfile.getUserProfileTel())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileFirstNameEng() != null && vUserProfile.getUserProfileFirstNameEng() != null && !userProfile.getUserProfileFirstNameEng().equalsIgnoreCase(vUserProfile.getUserProfileFirstNameEng())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileLastNameEng() != null && vUserProfile.getUserProfileLastNameEng() != null && !userProfile.getUserProfileLastNameEng().equalsIgnoreCase(vUserProfile.getUserProfileLastNameEng())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileFullNameEng() != null && vUserProfile.getUserProfileFullNameEng() != null && !userProfile.getUserProfileFullNameEng().equalsIgnoreCase(vUserProfile.getUserProfileFullNameEng())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileCardId() != null && vUserProfile.getUserProfileCardId() != null && !userProfile.getUserProfileCardId().equalsIgnoreCase(vUserProfile.getUserProfileCardId())) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileAddress() != null && vUserProfile.getUserProfileAddress() != null && !userProfile.getUserProfileAddress().equalsIgnoreCase(vUserProfile.getUserProfileAddress())) {
                                            stat = 3;
                                        } else if (userProfile.getPosition().getId() != vUserProfile.getPosition().getId()) {
                                            stat = 3;
                                        } else if (userProfile.getPositionLevel() != vUserProfile.getPositionLevel()) {
                                            stat = 3;
                                        } else if (userProfile.getUserProfileStatus() != null && vUserProfile.getUserProfileStatus() != null && !userProfile.getUserProfileStatus().getId().equals(vUserProfile.getUserProfileStatus().getId())) {
                                            stat = 3;
                                        } else {
                                            stat = 0;
                                        }
                                    }
                                    userProfileModel = userProfileService.tranformToModel(userProfile);
                                    vUserProfileModel = vUserProfileService.tranformToModel(vUserProfile);
                                    break;
                                }

                            } else {
                                stat = 0;
                                if (userProfile.getUserProfileCode() == null && vUserProfile.getUserProfileCode() != null) {
                                    stat = 1;
//                                    userProfileModel = null;
                                }
                            }
                        }

                        if (stat != 0) {
                            if (stat == 1) {
                                userProfileModel = null;
                                vUserProfileModel = vUserProfileService.tranformToModel(vUserProfile);
                            }
                            userProfileConvertModel = userProfileService.tranformToConvertModel(stat, userProfileModel, vUserProfileModel);
                        }
                    }
                    if (stat != 0) {
//                        count++;
//                        chkSearch = 0;
                        listUserProfileConvertModel.add(userProfileConvertModel);
                        if (vstatus != 0) {
                            if (vstatus == stat) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(userCode)) {
                            if (((userProfileConvertModel.getVUserProfile() != null) && userProfileConvertModel.getVUserProfile().getCode().equals(userCode)) || ((userProfileConvertModel.getUserProfile() != null) && userProfileConvertModel.getUserProfile().getCode().equals(userCode))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(fullName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getFullName() != null && userProfileConvertModel.getVUserProfile().getFullName().matches("(.*)" + fullName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getFullName() != null && userProfileConvertModel.getUserProfile().getFullName().matches("(.*)" + fullName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(positionName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getPosition() != null && userProfileConvertModel.getVUserProfile().getPosition().getName() != null && userProfileConvertModel.getVUserProfile().getPosition().getName().matches("(.*)" + positionName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getPosition() != null && userProfileConvertModel.getUserProfile().getPosition().getName() != null && userProfileConvertModel.getUserProfile().getPosition().getName().matches("(.*)" + positionName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureCode)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure().getCode() != null && userProfileConvertModel.getVUserProfile().getStructure().getCode().matches(structureCode + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure().getCode() != null && userProfileConvertModel.getUserProfile().getStructure().getCode().matches(structureCode + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure() != null && userProfileConvertModel.getVUserProfile().getStructure().getName() != null && userProfileConvertModel.getVUserProfile().getStructure().getName().matches("(.*)" + structureName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure() != null && userProfileConvertModel.getUserProfile().getStructure().getName() != null && userProfileConvertModel.getUserProfile().getStructure().getName().matches("(.*)" + structureName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucShortName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure() != null && userProfileConvertModel.getVUserProfile().getStructure().getShortName() != null && userProfileConvertModel.getVUserProfile().getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure() != null && userProfileConvertModel.getUserProfile().getStructure().getShortName() != null && userProfileConvertModel.getUserProfile().getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        }
                    }
                }
            }
            // remove
            if (!listUserProfile.isEmpty()) {
                String code = "";
                for (UserProfile userProfile : listUserProfile) {
//                    structureModel = new StructureModel();
                    if (!listVUserProfile.isEmpty()) {
                        for (VUserProfile vUserProfile : listVUserProfile) {
                            if (vUserProfile.getUserProfileCode() != null) {
                                if (userProfile.getUserProfileCode() != null) {
                                    if (userProfile.getUserProfileCode().equalsIgnoreCase(vUserProfile.getUserProfileCode())) {
                                        code = userProfile.getUserProfileCode();
                                        stat = 0;
                                        break;
                                    } else {
                                        if (!code.equalsIgnoreCase("") && code.equalsIgnoreCase(vUserProfile.getUserProfileCode())) {
                                            stat = 0;
                                            continue;
                                        } else {
                                            stat = 2;
//                                            vUserProfileModel = null;
                                        }
                                    }
                                } else {
                                    stat = 2;
//                                    vUserProfileModel = null;
                                }
                            }
                        }
                        if (stat != 0) {
                            if (stat == 2) {
                                vUserProfileModel = null;
                                userProfileModel = userProfileService.tranformToModel(userProfile);
                            }
                            userProfileConvertModel = userProfileService.tranformToConvertModel(stat, userProfileModel, vUserProfileModel);
                        }
                    }
                    if (stat != 0) {
//                        count++;
                        listUserProfileConvertModel.add(userProfileConvertModel);
                        if (vstatus != 0) {
                            if (vstatus == stat) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(userCode)) {
                            if (((userProfileConvertModel.getVUserProfile() != null) && userProfileConvertModel.getVUserProfile().getCode().equals(userCode)) || ((userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getCode() != null) && userProfileConvertModel.getUserProfile().getCode().equals(userCode))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(fullName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getFullName() != null && userProfileConvertModel.getVUserProfile().getFullName().matches("(.*)" + fullName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getFullName() != null && userProfileConvertModel.getUserProfile().getFullName().matches("(.*)" + fullName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(positionName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getPosition() != null && userProfileConvertModel.getVUserProfile().getPosition().getName() != null && userProfileConvertModel.getVUserProfile().getPosition().getName().matches("(.*)" + positionName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getPosition() != null && userProfileConvertModel.getUserProfile().getPosition().getName() != null && userProfileConvertModel.getUserProfile().getPosition().getName().matches("(.*)" + positionName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureCode)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure().getCode() != null && userProfileConvertModel.getVUserProfile().getStructure().getCode().matches(structureCode + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure().getCode() != null && userProfileConvertModel.getUserProfile().getStructure().getCode().matches(structureCode + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(structureName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure() != null && userProfileConvertModel.getVUserProfile().getStructure().getName() != null && userProfileConvertModel.getVUserProfile().getStructure().getName().matches("(.*)" + structureName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure() != null && userProfileConvertModel.getUserProfile().getStructure().getName() != null && userProfileConvertModel.getUserProfile().getStructure().getName().matches("(.*)" + structureName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        } else if (!"".equalsIgnoreCase(strucShortName)) {
                            if ((userProfileConvertModel.getVUserProfile() != null && userProfileConvertModel.getVUserProfile().getStructure() != null && userProfileConvertModel.getVUserProfile().getStructure().getShortName() != null && userProfileConvertModel.getVUserProfile().getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)")) || (userProfileConvertModel.getUserProfile() != null && userProfileConvertModel.getUserProfile().getStructure() != null && userProfileConvertModel.getUserProfile().getStructure().getShortName() != null && userProfileConvertModel.getUserProfile().getStructure().getShortName().matches("(.*)" + strucShortName + "(.*)"))) {
                                chkSearch += 1;
                                listUserProfileConvertModel2.add(userProfileConvertModel);
                                count++;
                            } else {
                                chkSearch += 1;
                            }
                        }
                    }
                }
            }
            listUserProfileConvertModel.trimToSize();
            responseData.put("data", chkSearch == 0 ? listUserProfileConvertModel : listUserProfileConvertModel2);
//            responseData.put("data", listUserProfileConvertModel);
            responseData.put("message", count + "");

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
            value = "Method for update UserProfile.",
            notes = "ปรับโครงสร้างจากข้อมูลนำเข้า hr",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile updeted by id success.")
        ,@ApiResponse(code = 404, message = "UserProfile by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/updateConvert")
    public Response updateConvert(
            List<UserProfileConvertModel> userProfileConvertModelList
    ) {
        LOG.debug("updateConvert...");
//        LOG.debug("id = " + id);
//        LOG.debug("curId = " + curId);
        Gson gs = new GsonBuilder()
                .setVersion(1)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        UserProfileConvertModel userProfileConvertModel = new UserProfileConvertModel();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            VUserProfileService vUserProfileService = new VUserProfileService();
            UserService userService = new UserService();
            List<UserProfileModel> listUserProfileModel = new ArrayList<>();
//            VStructureService vStructureService = new VStructureService();
//            VStructureModel vStructureModel = new VStructureModel();
            UserProfile userProfile = new UserProfile();
            User user = new User();
            for (UserProfileConvertModel userProfileConvert : userProfileConvertModelList) {
                int stat = userProfileConvert.getStatus();
                if (stat == 1) {
                    userProfile = new UserProfile();
//                    VUserProfile vUserProfile = new VUserProfile();

                    String passExpireStr = new ParamService().getByParamName("PASSEXPIRATION").getParamValue();
                    long passExpire = Long.parseLong(passExpireStr);

                    user = new User();
                    user.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    user.setUserActiveDate(LocalDateTime.now());
                    user.setUserName(userProfileConvert.getVUserProfile().getCode());
                    user.setUserPassword(PxInit.DEFAULT_PASSWORD);
                    user.setUserPasswordExpireDate(LocalDateTime.now().plusDays(passExpire));

                    user = userService.create(user);

                    userProfile.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    if (userProfileConvert.getVUserProfile().getStructure() != null) {
                        userProfile.setStructure(new StructureService().getByCode(userProfileConvert.getVUserProfile().getStructure().getCode()));
                    }
                    if (userProfileConvert.getVUserProfile().getTitle() != null) {
                        userProfile.setTitle(new TitleService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getTitle().getId()));
                    }
                    userProfile.setUser(new UserService().getByIdNotRemoved(user.getId()));
                    userProfile.setUserProfileEmail(userProfileConvert.getVUserProfile().getEmail());
                    userProfile.setUserProfileFirstName(userProfileConvert.getVUserProfile().getFirstName());
                    userProfile.setUserProfileFirstNameEng(userProfileConvert.getVUserProfile().getFirstNameEng());
                    userProfile.setUserProfileFullName(userProfileConvert.getVUserProfile().getFullName());
                    userProfile.setUserProfileFullNameEng(userProfileConvert.getVUserProfile().getFullNameEng());
                    userProfile.setUserProfileLastName(userProfileConvert.getVUserProfile().getLastName());
                    userProfile.setUserProfileLastNameEng(userProfileConvert.getVUserProfile().getLastNameEng());
                    userProfile.setUserProfileTel(userProfileConvert.getVUserProfile().getTel());
                    userProfile.setUserProfileVersion(1);

                    userProfile.setUserProfileStatus(new UserStatusService().getByIdNotRemoved(1));
                    if (userProfileConvert.getVUserProfile().getUserProfileType() != null) {
                        userProfile.setUserProfileType(new UserProfileTypeService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getUserProfileType().getId()));
                    } else {
                        userProfile.setUserProfileType(new UserProfileTypeService().getByIdNotRemoved(2));
                    }
                    //            userProfile.setUserProfileTypeOrder(userTypeOrderService.getByIdNotRemoved(userProfileModel.getUserTypeOrder().getId()));
                    userProfile.setUserProfileCardId(userProfileConvert.getVUserProfile().getIdCard());
                    userProfile.setUserProfileCode(userProfileConvert.getVUserProfile().getCode());
                    userProfile.setUserProfileAddress(userProfileConvert.getVUserProfile().getAddress());
                    if (userProfileConvert.getVUserProfile().getPosition() != null) {
                        userProfile.setPosition(new PositionService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getPosition().getId()));
                    }
                    userProfile.setPositionLevel(userProfileConvert.getVUserProfile().getPositionLevel());
//                    userProfile.setDigitalKey(userProfileConvert.getVUserProfile().getDigitalKey());

                    userProfile = userProfileService.create(userProfile);
                    // ----- create userProfileFolder -----
                    UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                    //genInbox
                    UserProfileFolder userProfileFolder = new UserProfileFolder();
                    userProfileFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder.setUserProfileFolderDetail("หนังสือเข้าของ " + userProfile.getUserProfileFullName());
                    userProfileFolder.setUserProfileFolderLinkId(0);
                    userProfileFolder.setUserProfileFolderName("กล่องหนังสือเข้า");
                    userProfileFolder.setUserProfileFolderType("I");
                    userProfileFolder.setUserProfileId(userProfile.getId());
                    if (userProfileFolder != null) {
                        userProfileFolder = userProfileFolderService.create(userProfileFolder);
                    }
                    //genOutbox
                    userProfileFolder = new UserProfileFolder();
                    userProfileFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder.setUserProfileFolderDetail("หนังสือออกของ " + userProfile.getUserProfileFullName());
                    userProfileFolder.setUserProfileFolderLinkId(0);
                    userProfileFolder.setUserProfileFolderName("กล่องหนังสือออก");
                    userProfileFolder.setUserProfileFolderType("O");
                    userProfileFolder.setUserProfileId(userProfile.getId());
                    if (userProfileFolder != null) {
                        userProfileFolder = userProfileFolderService.create(userProfileFolder);
                    }
                    //genRecyclebin
                    userProfileFolder = new UserProfileFolder();
                    userProfileFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder.setUserProfileFolderDetail("ถังขยะของ " + userProfile.getUserProfileFullName());
                    userProfileFolder.setUserProfileFolderLinkId(0);
                    userProfileFolder.setUserProfileFolderName("ถังขยะ");
                    userProfileFolder.setUserProfileFolderType("Z");
                    userProfileFolder.setUserProfileId(userProfile.getId());
                    if (userProfileFolder != null) {
                        userProfileFolder = userProfileFolderService.create(userProfileFolder);
                    }
                    //genMyWork
                    userProfileFolder = new UserProfileFolder();
                    userProfileFolder.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder.setUserProfileFolderDetail("แฟ้มส่วนตัวของ " + userProfile.getUserProfileFullName());
                    userProfileFolder.setUserProfileFolderLinkId(0);
                    userProfileFolder.setUserProfileFolderName("แฟ้มส่วนตัว");
                    userProfileFolder.setUserProfileFolderType("W");
                    userProfileFolder.setUserProfileId(userProfile.getId());
                    if (userProfileFolder != null) {
                        userProfileFolder = userProfileFolderService.create(userProfileFolder);
                    }

//                    System.out.println("vUserProfile code : " + userProfileConvert.getVUserProfile().getCode());
//                    System.out.println("vUserProfile : " + vUserProfileService.getByCode(userProfileConvert.getVUserProfile().getCode()).getId());
//                    vUserProfileService.remove(vUserProfileService.getByCode(userProfileConvert.getVUserProfile().getCode()).getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                }
                if (stat == 2) {
                    userProfile = new UserProfile();
                    userProfile = userProfileService.getById(userProfileConvert.getUserProfile().getId());
                    userProfile = userProfileService.remove(userProfile.getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    if (userProfile != null) {
                        userService.remove(userProfile.getUser().getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    }

                    // ----- remove userProfileFolder -----
                    UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                    UserProfileFolder userProfileFolder = new UserProfileFolder();
                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "I").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "O").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "Z").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    userProfileFolder = userProfileFolderService.remove(userProfileFolderService.getByUserProfileId(userProfile.getId(), "W").getId(), Integer.parseInt(httpHeaders.getHeaderString("userID")));

                }
                if (stat == 3 || stat == 4 || stat == 5) {
                    userProfile = new UserProfile();
                    userProfile = userProfileService.getById(userProfileConvert.getUserProfile().getId());
                    if (userProfile != null) {
                        userProfile.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userProfile.setStructure(new StructureService().getByCode(userProfileConvert.getVUserProfile().getStructure().getCode()));
//                        userProfile.setStructure(new StructureService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getStructure().getId()));
                        userProfile.setTitle(new TitleService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getTitle().getId()));
                        //                    userProfile.setUserProfileDefaultSelect(userProfileModel.getDefaultSelect());
                        userProfile.setUserProfileEmail(userProfileConvert.getVUserProfile().getEmail());
                        userProfile.setUserProfileFirstName(userProfileConvert.getVUserProfile().getFirstName());
                        userProfile.setUserProfileFirstNameEng(userProfileConvert.getVUserProfile().getFirstNameEng());
                        userProfile.setUserProfileLastName(userProfileConvert.getVUserProfile().getLastName());
                        userProfile.setUserProfileLastNameEng(userProfileConvert.getVUserProfile().getLastNameEng());
                        userProfile.setUserProfileFullName(userProfileConvert.getVUserProfile().getFullName());
                        userProfile.setUserProfileFullNameEng(userProfileConvert.getVUserProfile().getFullNameEng());
                        userProfile.setUserProfileTel(userProfileConvert.getVUserProfile().getTel());
                        if (userProfileConvert.getVUserProfile().getUserProfileType() != null) {
                            userProfile.setUserProfileType(new UserProfileTypeService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getUserProfileType().getId()));
                        } else {
                            userProfile.setUserProfileType(new UserProfileTypeService().getByIdNotRemoved(2));
                        }
                        //                    if (userProfileModel.getUserTypeOrder() != null) {
                        //                        userProfile.setUserProfileTypeOrder(userTypeOrderService.getByIdNotRemoved(userProfileConvert.getVUserProfile().getUserTypeOrder().getId()));
                        //                    }
                        userProfile.setUserProfileCardId(userProfileConvert.getVUserProfile().getIdCard());
                        userProfile.setUserProfileCode(userProfileConvert.getVUserProfile().getCode());
                        userProfile.setUserProfileAddress(userProfileConvert.getVUserProfile().getAddress());
                        if (userProfileConvert.getVUserProfile().getPosition() != null) {
                            userProfile.setPosition(new PositionService().getByIdNotRemoved(userProfileConvert.getVUserProfile().getPosition().getId()));
                        }
                        userProfile.setPositionLevel(userProfileConvert.getVUserProfile().getPositionLevel());

                        userProfile = userProfileService.update(userProfile);
                    }

                    UserProfileFolderService userProfileFolderService = new UserProfileFolderService();
                    //updateInbox
                    UserProfileFolder userProfileFolder = userProfileFolderService.getByUserProfileId(userProfile.getId(), "I");
                    if (userProfileFolder != null) {
                        userProfileFolder.setUserProfileFolderDetail("หนังสือเข้าของ " + userProfile.getUserProfileFullName());
                        userProfileFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userProfileFolder = userProfileFolderService.update(userProfileFolder);
                    }
                    //updateOutbox
                    userProfileFolder = userProfileFolderService.getByUserProfileId(userProfile.getId(), "O");
                    if (userProfileFolder != null) {
                        userProfileFolder.setUserProfileFolderDetail("หนังสือออกของ " + userProfile.getUserProfileFullName());
                        userProfileFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userProfileFolder = userProfileFolderService.update(userProfileFolder);
                    }
                    //updateRecyclebin
                    userProfileFolder = userProfileFolderService.getByUserProfileId(userProfile.getId(), "Z");
                    if (userProfileFolder != null) {
                        userProfileFolder.setUserProfileFolderDetail("ถังขยะของ " + userProfile.getUserProfileFullName());
                        userProfileFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userProfileFolder = userProfileFolderService.update(userProfileFolder);
                    }
                    //updateMyWork
                    userProfileFolder = userProfileFolderService.getByUserProfileId(userProfile.getId(), "W");
                    if (userProfileFolder != null) {
                        userProfileFolder.setUserProfileFolderDetail("แฟ้มส่วนตัวของ " + userProfile.getUserProfileFullName());
                        userProfileFolder.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        userProfileFolder = userProfileFolderService.update(userProfileFolder);
                    }
                }
                listUserProfileModel.add(userProfileService.tranformToModel(userProfile));
                status = Response.Status.OK;
            }
            status = Response.Status.OK;
            responseData.put("data", listUserProfileModel);
            responseData.put("message", "");
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
            value = "Method for list User.",
            notes = "รายการผู้ใช้งานระบบ",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Name hr list success.", response = AuthenticationModel.class)
        ,@ApiResponse(code = 404, message = "Name hr list not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/vuserProfiles")
    public Response listVProfiles(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("list...");
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
        responseData.put("message", "User list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            Client client = ClientBuilder.newClient();
            ParamService paramService = new ParamService();
            Param param = paramService.getByParamName("PATHHRIS");
//            WebTarget base = client.target("http://192.168.142.149/pxservice-hris/api/");
            WebTarget base = client.target(param.getParamValue() + "pxservice-hris/api/");
            WebTarget version = base.path("v1");
            WebTarget title = version.path("/vwNameHrs/employee").queryParam("version", "1").queryParam("api_key", "praXis");
            // Create uriBuilder from path
            Invocation.Builder builder = title.request(MediaType.APPLICATION_JSON);
            Response response = builder.get();
            String responseStr = response.readEntity(String.class);
            responseData.put("data", responseStr);
            responseData.put("message", "");
            status = Response.Status.OK;
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
            value = "Method for list All From hr.",
            notes = "รายการชื่อผู้ใช้ที่นำเข้าจากระบบ hr",
            responseContainer = "List",
            response = VUserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Structure Convert list success.")
        ,
        @ApiResponse(code = 404, message = "Structure Convert list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/hris/name")
    public Response listHrisStructureByName(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "fullName", value = "ชื่อผู้ใช้", required = false)
            @DefaultValue("") @QueryParam("fullName") String fullName,
            @ApiParam(name = "sort", value = "ฟิลด์ที่ต้องการเรียงลำดับ", required = false)
            @DefaultValue("createdDate") @QueryParam("sort") String sort,
            @ApiParam(name = "dir", value = "เรียงลำดับจาก", required = false)
            @DefaultValue("desc") @QueryParam("dir") String dir
    ) {
        LOG.debug("listHrisStructureByName...");
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        ArrayList<VUserProfileModel> listVUserProfileModel = new ArrayList<>();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", listVUserProfileModel);
        responseData.put("success", false);
        responseData.put("message", "VUserProfile list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            VUserProfileService vUserProfileService = new VUserProfileService();
            List<VUserProfile> listVUserProfile = vUserProfileService.listByName(fullName, sort, dir);
            if (!listVUserProfile.isEmpty()) {
                for (VUserProfile vUserProfile : listVUserProfile) {
                    listVUserProfileModel.add(vUserProfileService.tranformToModel(vUserProfile));
                }
                listVUserProfileModel.trimToSize();
                responseData.put("data", listVUserProfileModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
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
            value = "Method for list UserProfile.",
            notes = "รายการสถานะผู้เข้าใช้ระบบ",
            responseContainer = "List",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "UserProfile list success.")
        ,
        @ApiResponse(code = 404, message = "UserProfile list not found in the database.")
        ,
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/userStatus/{parentKey}/report/{jobType}")
    public Response listByStucture(
            @ApiParam(name = "parentKey", value = "รหัสผู้ใช้", required = true)
            @PathParam("parentKey") String parentKey,
            @ApiParam(name = "jobType", value = "รหัสประเภทงาน", required = true)
            @PathParam("jobType") String jobType
    ) {
        LOG.info("list...");
        LOG.info("parentKey..." + parentKey);
        LOG.info("jobType..." + jobType);
        Gson gs = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", new ArrayList<>());
        responseData.put("success", false);
        responseData.put("message", "UserStatus list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            TempTableService tempTableService = new TempTableService();
            List<TempTable> listTempTable = tempTableService.listByJobType(Integer.parseInt(httpHeaders.getHeaderString("userID")), jobType, "", "");
//            List<TempTable> listTempTable = tempTableService.listByComputerNameAndJobType(1, "127.0.0.1", jobType, "", "");

            if (!listTempTable.isEmpty()) {
                for (TempTable tempTable : listTempTable) {
                    tempTableService.delete(tempTable);
                }
            }

            UserProfileService userProfileService = new UserProfileService();
            UserStatusService userStatusService = new UserStatusService();
            List<UserProfile> listUserProfile = userProfileService.listByStructureParentKey(parentKey, "structure", "asc");
//            List<DmsFolder> listDmsFolder = dmsFolderService.listFolderByparenID(id, 0, 500);
            if (!listUserProfile.isEmpty()) {
                ArrayList<UserProfileModel> listUserProfileModel = new ArrayList<>();
                for (UserProfile userProfile : listUserProfile) {
                    listUserProfileModel.add(userProfileService.tranformToModel(userProfile));
                }

                listUserProfileModel.forEach(model -> {
                    TempTable tempTable = new TempTable();
                    String subName = "";

                    tempTable.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                    tempTable.setComputerName(httpHeaders.getHeaderString("clientIp"));
                    tempTable.setJobType(jobType);
                    tempTable.setInt01(model.getId());
//                        tempTable.setInt01(model.getSubmoduleAuth().getAuth().getId());
                    tempTable.setStr01(model.getStructure().getName());
                    tempTable.setStr02(model.getFullName());
                    tempTable.setStr03(model.getUser().getName());
                    tempTable.setStr04(model.getUserStatus().getName());

                    if (tempTable != null) {
                        tempTable = tempTableService.create(tempTable);
                        tempTable.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
                        tempTable.setOrderNo(tempTable.getId());
                        tempTable = tempTableService.update(tempTable);
                    }
                });

                listUserProfileModel.trimToSize();
                responseData.put("data", listUserProfileModel);
                responseData.put("message", "");

            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
