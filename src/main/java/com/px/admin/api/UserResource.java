package com.px.admin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.UserStatus;
import com.px.admin.model.UserModel;
import com.px.admin.model.UserProfileModel;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserService;
import com.px.admin.service.UserStatusService;
import com.px.share.entity.Param;
import com.px.share.filter.HTTPHeaderNames;
import com.px.share.model.AuthenticationModel;
import com.px.share.model.ListOptionModel;
import com.px.share.model.ListReturnModel;
import com.px.share.model.VersionModel;
import com.px.share.service.ParamService;
import com.px.share.util.Common;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "User ผู้ใช้งานระบบ")
@Path("v1/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for create Users",
            notes = "สร้างผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Users created successfully."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(
            UserModel userModel
    ) {
        LOG.debug("create...");
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("userType = " + httpHeaders.getHeaderString("userType"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
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
            User user = new User();
            user.setCreatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            user.setUserActiveDate(dateThaiToLocalDateTime(userModel.getActiveDate()));
            user.setUserExpireDate(dateThaiToLocalDateTime(userModel.getExpireDate()));
            user.setUserName(userModel.getName());
            user.setUserPassword(userModel.getPasswords());
            user.setUserPasswordExpireDate(dateThaiToLocalDateTime(userModel.getPasswordExpireDate()));
            user = userService.create(user);
            if (user.getOrderNo() == 0) {
                user.setOrderNo(user.getId());
                user = userService.update(user);
            }
            //LogData For create User
            userService.saveLogForCreate(user, httpHeaders.getHeaderString("clientIp"));
            responseData.put("data", userService.tranformToModel(user));
            responseData.put("message", "UserProfile created successfully.");
            status = Response.Status.CREATED;
            responseData.put("success", true);
        } catch (NumberFormatException ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "ขอข้อมูลผู้ใช้งานระบบ ด้วย รหัสผู้ใช้งานระบบ",
            notes = "ขอข้อมูลผู้ใช้งานระบบ ด้วย รหัสผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response getById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสคำนำหน้า", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("getById...");
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
        responseData.put("message", "Title by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            User user = userService.getByIdNotRemoved(id);
            if (user != null) {
                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
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
            value = "Method for delete User by id.",
            notes = "ลบผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User deleted by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{userId}")
    public Response remove(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userId", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("userId") int userId
    ) {
        LOG.debug("remove...");
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
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            User user = userService.remove(userId, Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (user != null) {
                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
                responseData.put("message", "");

                //LogData For remove userProfile
                userService.saveLogForRemove(user, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for list User.",
            notes = "รายการผู้ใช้งานระบบ",
            responseContainer = "List",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User list success."),
        @ApiResponse(code = 404, message = "User list not found in the database."),
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
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "User list not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            List<User> listUser = userService.list(listOptionModel.getOffset(), listOptionModel.getLimit(), listOptionModel.getSort(), listOptionModel.getDir());
            if (!listUser.isEmpty()) {
                int countAll = userService.countAll();
                int count = listUser.size();
                int next = 0;
                if (count >= listOptionModel.getLimit()) {
                    next = listOptionModel.getOffset() + listOptionModel.getLimit();
                }
                if (next >= countAll) {
                    next = 0;
                }
                ListReturnModel listReturnModel = new ListReturnModel(countAll, count, next);
                ArrayList<UserModel> listUserModel = new ArrayList<>();
                for (User user : listUser) {
                    listUserModel.add(userService.tranformToModel(user));
                }
                listUserModel.trimToSize();
                responseData.put("data", listUserModel);
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
            value = "Method for authentication user by user name and password",
            notes = "ยืนยันการเข้าใช้งานระบบด้วยชื่อผู้ใช้งานและรหัสผ่าน",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/login")
    public Response checkAuthentication(
            UserModel userModel
    ) {
        LOG.debug("checkAuthentication...");
        LOG.debug("name = " + userModel.getName());
        LOG.debug("passwords = " + userModel.getPasswords());
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("userType = " + httpHeaders.getHeaderString("userType"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Authentication fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserService userService = new UserService();
            boolean result = userService.authenticationUser(userModel.getName(), userModel.getPasswords());
            if (result) {
                User user = userService.getUserByUserName(userModel.getName());
                if (user != null) {
                    UserProfileService userProfileService = new UserProfileService();
                    List<UserProfile> listUserProfile = userProfileService.listByUserId(user.getId(), null, null);
                    UserProfile userProfile = listUserProfile.get(0);
                    if (userProfile.getUserProfileStatus() != null && userProfile.getUserProfileStatus().getId() != 1) {
                        if (userProfile.getUserProfileStatus().getId() == 3) {
                            result = false;
                            responseData.put("message", "ผู้ใช้นี้ถูกระงับการใช้งาน กรุณาติดต่อแอดมินเพื่อขอใช้งาน.");
                        } else if (userProfile.getUserProfileStatus().getId() == 2) {
                            responseData.put("message", "");
                        }
                    } else {
                        final String token = userService.genToken(user, userProfile);
                        response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
                        List<UserProfileModel> listUserProfileModel = new ArrayList<>();
                        listUserProfile.forEach(u -> {
                            listUserProfileModel.add(userProfileService.tranformToModel(u));
                        });
                        responseData.put("message", listUserProfileModel);
                    }

//                    UserProfileService userProfileService = new UserProfileService();
//                    UserProfile userProfile = userProfileService.getByUserId(user.getId());
//                    final String token = userService.genToken(user, userProfile);
//                    response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
//                    responseData.put("message", "");
                    //LogData For Login
                    userService.saveLogForLogin(userProfile, httpHeaders.getHeaderString("clientIp"));
                }
                authenticationModel = new AuthenticationModel(result);
                responseData.put("data", authenticationModel);
            } else {
                responseData.put("message", "คุณกรอก Username และ Password ไม่ถูกต้อง.");
                authenticationModel = new AuthenticationModel(result);
                responseData.put("data", authenticationModel);
            }
            status = Response.Status.OK;
            responseData.put("success", true);
            responseData.put("clientIp", httpHeaders.getHeaderString("clientIp"));
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update User Password.",
            notes = "แก้ไขรหัสผ่านผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User Password updeted by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/pw")
    public Response updatePassword(
            UserModel userModel
    ) {
        LOG.debug("updatePassword...");
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Authentication fail.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            //User user = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUser();
            User user = userProfile.getUser();

            UserService userService = new UserService();
//            boolean result = userService.authenticationUser(user.getUserName(), user.getUserPassword());
//            if (result) {
            user.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            user.setUserPassword(userModel.getPasswords());
//            boolean result2 = userService.checkUserPasswordExpireDate(user.getId());
//            if (result2) {
//                user.setUserPasswordExpireDate(null);
//            }
            user = userService.updatePassword(user);

            String newPassword = userModel.getPasswords();
            String recentPassword = userProfile.getRecentPassword();
            if (recentPassword != null && recentPassword != "") {
                String[] listTmp = recentPassword.split("XX-XX");
                switch (listTmp.length) {
                    case 1:
                        userProfile.setRecentPassword(listTmp[0] + "XX-XX" + newPassword);
                        break;
                    case 2:
                        userProfile.setRecentPassword(listTmp[0] + "XX-XX" + listTmp[1] + "XX-XX" + newPassword);
                        break;
                    case 3:
                        userProfile.setRecentPassword(listTmp[1] + "XX-XX" + listTmp[2] + "XX-XX" + newPassword);
                        break;
                }
            } else {
                userProfile.setRecentPassword(newPassword);
            }
            userProfileService.update(userProfile);

            responseData.put("data", userService.tranformToModel(user));
            status = Response.Status.OK;
            responseData.put("message", "");
            //LogData For update password
            userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
//            }
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for Reset Password",
            notes = "ล้างค่ารหัสผ่านเป็นค่า default",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Reset Password id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/reset/{id}")
    public Response resetPasswordById(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id
    ) {
        LOG.debug("resetPasswordById...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            if (userProfile != null && userProfile.getUserProfileType().getId() == 1) {
                userProfile = userProfileService.getByIdNotRemoved(id);
                User user = userProfile.getUser();
                if (user != null) {
                    UserService userService = new UserService();
//                    user.setUserPassword(PxInit.DEFAULT_PASSWORD);
                    Param param = new ParamService().getByParamName("DEFAULT_PASSWORD");
                    user.setUserPassword(param.getParamValue());
                    user = userService.updatePassword(user);
                    status = Response.Status.OK;
                    responseData.put("data", userService.tranformToModel(user));
                    responseData.put("message", "");

                    //LogData For update password
                    userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
                }
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
            value = "ขอข้อมูลผู้ใช้งานระบบ ด้วย รหัสผู้ใช้งานระบบ",
            notes = "ขอข้อมูลผู้ใช้งานระบบ ด้วย รหัสผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/userName")
    public Response getByUserName(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userName", value = "ชื่อเข้าใช้ระบบ", required = true)
            @QueryParam("userName") String userName
    ) {
        LOG.debug("getByuserName...");
        LOG.debug("userName = " + userName);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "User by user name not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
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
//    @ApiOperation(
//            value = "Method for get tokenby user name",
//            notes = "ยืนยันการเข้าใช้งานระบบด้วยชื่อผู้ใช้งาน",
//            response = AuthenticationModel.class
//    )
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
//        @ApiResponse(code = 404, message = "Authentication fail."),
//        @ApiResponse(code = 500, message = "Internal Server Error!")
//    })
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/token")
//    public Response checkAuthenticationByUserName(
//            UserModel userModel
//    ) {
//        LOG.debug("checkAuthenticationByUserName...");
//        LOG.debug("name = " + userModel.getName());
//        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
//        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
//        Gson gs = new GsonBuilder()
//                .setVersion(userModel.getVersion())
//                .excludeFieldsWithoutExposeAnnotation()
//                .disableHtmlEscaping()
//                .setPrettyPrinting()
//                .serializeNulls()
//                .create();
//        HashMap responseData = new HashMap();
//        Status status = Response.Status.NOT_FOUND;
//        responseData.put("success", false);
//        responseData.put("message", "Authentication fail.");
//        responseData.put("errorMessage", "");
//        AuthenticationModel authenticationModel = new AuthenticationModel(false);
//        responseData.put("data", authenticationModel);
//        try {
//            UserService userService = new UserService();
//            User user = userService.getUserByUserName(userModel.getName());
//            if (user != null) {
//                UserProfileService userProfileService = new UserProfileService();
//                UserProfile userProfile = userProfileService.getByUserId(user.getId());
//
//                final String token = userService.genToken(user, userProfile);
////                    LOG.debug("token = "+token);
//                response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
//                responseData.put("message", "");
//                authenticationModel = new AuthenticationModel(true);
//                responseData.put("data", authenticationModel);
//
//                //LogData For Login
//                userService.saveLogForLogin(user, httpHeaders.getHeaderString("clientIp"));
//            }
//            status = Response.Status.OK;
//            responseData.put("success", true);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            status = Response.Status.OK;
//            responseData.put("errorMessage", ex.getMessage());
//        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
//    }
//
//    ;

    @ApiOperation(
            value = "ขอวันที่หมดอายุ ด้วย วันที่เริ่มใช้งาน",
            notes = "ขอวันที่หมดอายุ ด้วย วันที่เริ่มใช้งาน",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Expire by ActiveDate success."),
        @ApiResponse(code = 404, message = "Expire by ActiveDate not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/activeDate/")
    public Response getExpireByActiveDate(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userActiveDate", value = "วันที่เริ่มใช้งาน", required = true)
            @QueryParam("userActiveDate") String activeDate
    ) {
        LOG.debug("getExpireByActiveDate...");
        LOG.debug("userActiveDate = " + activeDate);
        Gson gs = new GsonBuilder()
                .setVersion(versionModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.OK;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "Expire by ActiveDate not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            String expireDate = userService.getExpireByActiveDate(activeDate);
//            LOG.info("expireDate : "+expireDate);
            responseData.put("data", expireDate);
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
            value = "Method for check user can login by status id",
            notes = "ยืนยันการเข้าใช้งานระบบจากสถานะผู้ใช้งาน และช่วงวันที่เปิดใช้งาน",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/login/checkLock/")
    public Response checkUserStatusId(
            UserModel userModel
    ) {
        LOG.debug("checkUserStatusId...");
        LOG.debug("name = " + userModel.getName());
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Authentication fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserProfileService userProfileService = new UserProfileService();
            User user = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUser();
            UserService userService = new UserService();
            boolean result = userService.checkUserLock(user.getId());
            if (result) {
                authenticationModel = new AuthenticationModel(result);
                responseData.put("data", authenticationModel);
                responseData.put("message", "");
            }
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check change password",
            notes = "ตรวจสอบการแจ้งเปลียนพาสเวิร์ด",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkChangePassword/")
    public Response checkChangePassword(
            UserModel userModel
    ) {
        LOG.debug("checkFirstLogin...");
        LOG.debug("name = " + userModel.getName());
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "check change password fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
//            UserProfileService userProfileService = new UserProfileService();
//            User user = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID"))).getUser();
//            User user = userProfileService.getById(3).getUser();
            UserService userService = new UserService();
            User user = userService.getUserByUserName(userModel.getName());
            boolean result = false;
            result = userService.checkUserExpireDate(user.getId());
            if (result) {
                responseData.put("message", "ชื่อผู้ใช้หมดอายุการใช้งาน โปรดติดต่อผู้ดูแลระบบ");
            } else {
                Param param = new ParamService().getByParamName("USE_AD");
                if (!param.getParamValue().equalsIgnoreCase("Y")) {
                    if (user.getUpdatedBy() == 0) {
                        result = true;
                        responseData.put("message", "กรุณาเปลี่ยนรหัสผ่านในครั้งแรกที่เข้าระบบ");
                    } else {
                        result = userService.checkUserPasswordExpireDate(user.getId());
                        if (result) {
                            responseData.put("message", "รหัสผ่านหมดอายุ กรุณาเปลี่ยนรหัสผ่าน");
                        }
                    }
                }
            }
            LOG.debug("result: " + result);
            authenticationModel = new AuthenticationModel(result);
            responseData.put("data", authenticationModel);

            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check change password expire date",
            notes = "ตรวจสอบวันที่หมดอายุของรหัสผ่าน",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/checkUserPasswordExpireDate/")
    public Response checkUserPasswordExpireDate(
            UserModel userModel
    ) {
        LOG.debug("checkUserPasswordExpireDate...");
        LOG.debug("name = " + userModel.getName());
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "check change password expiredate fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserService userService = new UserService();
            User user = userService.getUserByUserName(userModel.getName());
            boolean result = false;
            if (user.getUpdatedBy() != 0) {
                result = userService.checkUserPasswordExpireDate(user.getId());
                responseData.put("message", "Your password is Expired, Please Change Password");
            }
            authenticationModel = new AuthenticationModel(result);
            responseData.put("data", authenticationModel);

            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update user.",
            notes = "แก้ไขผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User updeted by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/{id}")
    public Response update(
            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            @PathParam("id") int id,
            UserModel userModel
    ) {
        LOG.debug("update...");
        LOG.debug("id = " + id);
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            User user = userService.getByIdNotRemoved(id);
            if (user != null) {
                user.setUpdatedBy(Integer.parseInt(httpHeaders.getHeaderString("userID")));
//                user.setUserPassword(userModel.getPasswords());
                user.setUserActiveDate(dateThaiToLocalDateTime(userModel.getActiveDate()));
                user.setUserExpireDate(dateThaiToLocalDateTime(userModel.getExpireDate()));
                user.setUserPasswordExpireDate(dateThaiToLocalDateTime(userModel.getPasswordExpireDate()));
                if (userModel.getStatus() != null) {
                    user.setUserStatus(new UserStatusService().getByIdNotRemoved(userModel.getStatus().getId()));
                }
                user = userService.update(user);

                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
                responseData.put("message", "");
                //LogData For update password
                userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for update user.",
            notes = "แก้ไขผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User updeted by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/pass/{userName}")
    public Response updatePassByUserId(
            @ApiParam(name = "userName", value = "ชื่อผู้ใช้งาน", required = true)
            @PathParam("userName") String userName,
            UserModel userModel
    ) {
        LOG.debug("updatePassByUserId...");
        LOG.debug("userName = " + userName);
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            User user = userService.getUserByUserName(userName);
            if (user != null) {
                user.setUpdatedBy(user.getId());
                user.setUserPassword(userModel.getPasswords());
//                user.setUserPasswordExpireDate(dateThaiToLocalDateTime(userModel.getPasswordExpireDate()));
                if (userModel.getStatus() != null) {
                    user.setUserStatus(new UserStatusService().getByIdNotRemoved(userModel.getStatus().getId()));
                }
                user = userService.update(user);

                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
                responseData.put("message", "");
                //LogData For update password
                userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
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
            value = "Method for update password.",
            notes = "แก้ไขผู้ใช้รหัสผ่าน",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User updeted by id success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/changePass/{userNameUrl}")
    public Response updatePassByUsername(
            @ApiParam(name = "userNameUrl", value = "ชื่อผู้ใช้งาน", required = true)
            @PathParam("userNameUrl") String userNameUrl,
            UserModel userModel
    ) {
        LOG.debug("updatePassByUserId...");
        LOG.debug("userName = " + userModel.getName());
        LOG.debug("userNameUrl = " + userNameUrl);
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
//        Response.Status status = Response.Status.NOT_FOUND;
        Response.Status status = Response.Status.OK;
//        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserService userService = new UserService();
            boolean result = userService.checkUserNameForgotPassword(userModel.getName(), userNameUrl);
            if (result) {
                User user = userService.getUserByUserName(userModel.getName());
                if (user != null) {
                    user.setUpdatedBy(user.getId());
                    user.setUserPassword(userModel.getPasswords());
//                user.setUserPasswordExpireDate(dateThaiToLocalDateTime(userModel.getPasswordExpireDate()));
//                    if (userModel.getStatus() != null) {
//                        user.setUserStatus(new UserStatusService().getByIdNotRemoved(userModel.getStatus().getId()));
//                    }
                    user = userService.updatePassword(user);

                    status = Response.Status.OK;
                    authenticationModel = new AuthenticationModel(result);
                    responseData.put("data", authenticationModel);
//                    responseData.put("data", userService.tranformToModel(user));
                    responseData.put("message", "");
                    //LogData For update password
                    userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
                    responseData.put("success", true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for check email",
            notes = "ตรวจสอบชื่อผู้ใช้และอีเมล์ ไม่ถูกต้อง",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/checkEmail/{userName}/email/{email}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response checkEmail(
            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userName", value = "user login", required = false)
            @PathParam("userName") String userName,
            @ApiParam(name = "email", value = "email", required = false)
            @PathParam("email") String email
    ) {
        LOG.debug("checkEmail...");
//        LOG.debug("name = " + userModel.getName());
        LOG.debug("userName = " + userName);
        LOG.debug("email = " + email);
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
        responseData.put("message", "email is fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserProfileService userProfileService = new UserProfileService();
//            UserProfile userProfile = userProfileService.getByUserId(userId);
//            UserProfile userProfile = userProfileService.checkEmail(userId,email);
//            User user = userService.getUserByUserName(userId);
            boolean result = false;
            result = userProfileService.checkEmail(userName, email);
            if (!result) {
                responseData.put("message", "email is fail.");
            }
            responseData.put("message", "");
            authenticationModel = new AuthenticationModel(result);
            responseData.put("data", authenticationModel);

            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for send email exp doc.",
            notes = "ใช้ส่งเมล",
            response = AuthenticationModel.class
    //            responseContainer = "List",
    //                response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "send email user success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "user's email not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Path(value = "/sendEmail/{userName}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response sendEmail(
            UserModel userModel,
            //            @BeanParam VersionModel versionModel,
            @ApiParam(name = "userName", value = "user login", required = false)
            @PathParam("userName") String userName
    //            @ApiParam(name = "email", value = "email", required = false)
    //            @QueryParam("email") String email
    ) {
        LOG.info("emailDocExp...");
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "user's email not found in the database.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserService userService = new UserService();
            UserProfileService userProfileService = new UserProfileService();
            ArrayList<String> fileAttachPath = new ArrayList<>();
//            String fullPathUrl = " ท่านสามารถเข้าสู่ระบบได้ตามลิงค์นี้ <br>";
//            String detailEmail = "<br>";
            String fullPathUrl = " ท่านสามารถเข้าสู่ระบบเพื่อเปลี่ยนรหัสผ่านได้ตามลิงค์นี้ <br>";
            String url = userService.generateLinkForgotPassword(userName);
            String detailEmail = "<br>";
            //send mail 
            detailEmail = detailEmail + "<br>" + fullPathUrl + "<br> <a href=\"" + url + "\">" + url + "</a>";
//            detailEmail = "ตรวจพบเอกสารหมดอายุจำนวน " + countDocAll + " เอกสาร <br>" + detailEmail;

            String emailUserProfileId = userProfileService.getByUsername(userName).getUserProfileEmail();
            if (emailUserProfileId != "" && emailUserProfileId != null) {
                boolean debug = false;
                String mailSubject = "[เปลี่ยนรหัสผ่าน e-Document] แจ้งเตือนเพื่อขอเปลี่ยนรหัสผ่านเข้าสู่ระบบใหม่";
                String mailTo = emailUserProfileId;
                String mailToCC = "";
                String mailToBCC = "";
                String mailBody = detailEmail;
                String mailType = "html";

                boolean result = Common.sendEmail(mailSubject, mailTo, mailToCC, mailToBCC, mailBody, fileAttachPath, mailType, debug);
                if (result) {
                    authenticationModel = new AuthenticationModel(result);
                    responseData.put("data", authenticationModel);
//                    responseData.put("data", authenticationModel);
                    status = Response.Status.OK;
                    responseData.put("success", true);
                }
            }

        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
            responseData.put("errorMessage", ex.getMessage());
        }

        return Response.status(status)
                .entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "check recentPassword",
            notes = "check recentPassword",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "recentPassword success."),
        @ApiResponse(code = 404, message = "recentPasswordnot found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
//    @Path(value = "/checkRecentPassword/{newPassword}")
    @Path(value = "/checkRecentPassword")
    public Response checkRecentPassword(
            UserModel userModel
    //            @BeanParam VersionModel versionModel,
    //            @ApiParam(name = "newPassword", value = "newPassword", required = true)
    //            @PathParam("newPassword") String newPassword
    ) {
        LOG.debug("checkRecentPassword...");
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "UserProfile by id not found in the database.");
        responseData.put("errorMessage", "");

        boolean result = false;
        int id = Integer.parseInt(httpHeaders.getHeaderString("userID"));

        try {
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getByIdNotRemoved(id);
            if (userProfile != null) {
                String recentPassword = userProfile.getRecentPassword();
                if (recentPassword != null && recentPassword != "") {
                    String[] listTmp = recentPassword.split("XX-XX");
                    for (String tmp : listTmp) {
                        if (userModel.getPasswords().equalsIgnoreCase(tmp)) {
                            result = true;
                            break;
                        }
                    }
                }
                final String token = new UserService().genToken(userProfile.getUser(), userProfile);
                response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
                status = Response.Status.OK;
                responseData.put("data", result);
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
            value = "Method for logout",
            notes = "ยืนยันการออกจากระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success."),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/logout")
    public Response checkLogout(
            @BeanParam VersionModel versionModel
    ) {
        LOG.debug("checkLogout...");
        LOG.debug("userId = " + httpHeaders.getHeaderString("userID"));
        LOG.debug("userType = " + httpHeaders.getHeaderString("userType"));
        LOG.debug("clientIp = " + httpHeaders.getHeaderString("clientIp"));
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
        responseData.put("message", "Authentication fail.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            UserProfileService userProfileService = new UserProfileService();
            UserProfile userProfile = userProfileService.getById(Integer.parseInt(httpHeaders.getHeaderString("userID")));
            //LogData For Logout
            userService.saveLogForLogout(userProfile, httpHeaders.getHeaderString("clientIp"));
            responseData.put("data", userService.tranformToModel(userProfile.getUser()));
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    //oat-add
    @ApiOperation(
            value = "mock Method for authentication user by user name and password",
            notes = "mock ยืนยันการเข้าใช้งานระบบด้วยชื่อผู้ใช้งานและรหัสผ่าน",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/getMocktoken")
    public Response getMocktoken() {
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "Authentication fail.");
        responseData.put("errorMessage", "");
        AuthenticationModel authenticationModel = new AuthenticationModel(false);
        responseData.put("data", authenticationModel);
        try {
            UserService userService = new UserService();
            User user = userService.getUserByUserName("admin");
            if (user != null) {
                UserProfileService userProfileService = new UserProfileService();
                UserProfile userProfile = userProfileService.getDefaultProfile(user.getId());
                final String token = userService.genToken(user, userProfile);
                response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
                responseData.put("result", true);
            }
            responseData.put("data", "");
            status = Response.Status.OK;
            responseData.put("success", true);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            status = Response.Status.OK;
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }

    @ApiOperation(
            value = "Method for update user.",
            notes = "แก้ไขสถานะผู้ใช้งานระบบ",
            response = UserModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "User updeted by id success."),
        @ApiResponse(code = 404, message = "User by id not found in the database."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/userName")
    public Response updateStatusByUsername(
            //            @ApiParam(name = "id", value = "รหัสผู้ใช้งานระบบ", required = true)
            //            @PathParam("id") int id,
            //            @ApiParam(name = "userName", value = "ชื่อผู้ใช้งานระบบ", required = true)
            //            @PathParam("userName") String userName,
            //            @ApiParam(name = "status", value = "สถานะผู้ใช้งาน", required = true)
            //            @PathParam("status") int stat,
            UserModel userModel
    ) {
        LOG.debug("updateStatusByUsername...");
//        LOG.debug("userName = " + userName);
//        LOG.debug("status = " + stat);
        Gson gs = new GsonBuilder()
                .setVersion(userModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("success", false);
        responseData.put("message", "User by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            UserService userService = new UserService();
            UserProfileService userProfileService = new UserProfileService();
//            User user = userService.getByIdNotRemoved(id);
            User user = userService.getUserByUserName(userModel.getName());
            if (user != null) {
//                UserProfile userProfile = new UserProfileService().getByUserId(user.getId());//oat-edit
                UserProfile userProfile = new UserProfileService().getDefaultProfile(user.getId());
                user.setUpdatedBy(user.getId());
                userProfile.setUpdatedBy(userProfile.getId());
//                user.setUserPassword(userModel.getPasswords());
//                user.setUserActiveDate(dateThaiToLocalDateTime(userModel.getActiveDate()));
//                user.setUserExpireDate(dateThaiToLocalDateTime(userModel.getExpireDate()));
//                user.setUserPasswordExpireDate(dateThaiToLocalDateTime(userModel.getPasswordExpireDate()));
                if (userModel.getStatus() != null) {
                    UserStatus userStatus = new UserStatusService().getByIdNotRemoved(userModel.getStatus().getId());
                    user.setUserStatus(userStatus);
                    userProfile.setUserProfileStatus(userStatus);

                }
                user = userService.update(user);
                userProfile = userProfileService.update(userProfile);

                status = Response.Status.OK;
                responseData.put("data", userService.tranformToModel(user));
                responseData.put("message", "");
                //LogData For update password
                userService.saveLogForUpdatePassword(user, httpHeaders.getHeaderString("clientIp"));
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
            value = "swap userProfile",
            notes = "swap userProfile",
            response = AuthenticationModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Authentication success.", response = AuthenticationModel.class),
        @ApiResponse(code = 404, message = "Authentication fail."),
        @ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(value = "/swapUserProfile")
    public Response swapUserProfile(
           UserProfileModel userProfileModel
    ) {
        LOG.debug("swapUserProfile...");
        Gson gs = new GsonBuilder()
                .setVersion(userProfileModel.getVersion())
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Status status = Response.Status.INTERNAL_SERVER_ERROR;
        responseData.put("success", false);
        responseData.put("data", "");
        String token = "";
        try {
            UserProfile userProfile = new UserProfileService().getByIdNotRemoved(userProfileModel.getId());
            if (userProfile != null) {
                UserService userService = new UserService();
                User user = userService.getByIdNotRemoved(userProfile.getUser().getId());
                //userProfile.setUserProfileDefaultSelect(0);
                token = userService.genToken(user, userProfile);
//                response.setHeader(HTTPHeaderNames.AUTH_TOKEN, token);
                responseData.put("data", token);
            }
            status = Response.Status.OK;
            responseData.put("success", true);
            responseData.put("message", "");
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
//        return Response.status(status).entity(gs.toJson(responseData)).build();
        return Response.status(status).header(HTTPHeaderNames.AUTH_TOKEN, token).entity(gs.toJson(responseData)).build();
    }

}
