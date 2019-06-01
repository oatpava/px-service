package com.px.share.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.model.UserProfileModel;
import com.px.admin.service.PositionService;
import com.px.admin.service.PositionTypeService;
import com.px.admin.service.StructureService;
import com.px.admin.service.TitleService;
import com.px.admin.service.UserProfileService;
import com.px.admin.service.UserProfileTypeService;
import com.px.admin.service.UserService;
import com.px.admin.service.UserStatusService;
import com.px.admin.service.UserTypeOrderService;
import com.px.share.service.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
@Api(value = "Notification รับแจ้งเตือนการแก้ไขข้อมูลต่างๆ")
@Path("v1/notifications")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class NotificationsResource {

    private static final Logger LOG = Logger.getLogger(NotificationsResource.class.getName());

    @Context
    private HttpServletResponse response;

    @Context
    HttpHeaders httpHeaders;

    @ApiOperation(
            value = "Method for notify UserProfiles status updated by id",
            notes = "รับแจ้งการแก้ไขข้อมูลผู้ใช้งานระบบ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Notify UserProfiles status updated by id success.")
        ,@ApiResponse(code = 404, message = "UserProfiles by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/employees/{empId}")
    public Response notifyUpdateUserProfile(
            @ApiParam(name = "empId", value = "รหัส record id ของ employee", required = true)
            @PathParam("empId") int empId,
            @ApiParam(name = "token", value = "token ที่ใช้สำหรับส่งไปเรียกดูข้อมุล ของ employee", required = true)
            @QueryParam("token") String token
    ) {
        LOG.debug("notifyUpdateUserProfile...");
        LOG.debug("empId = " + empId);
        LOG.debug("token = " + token);
        Gson gs = new GsonBuilder()
                .setVersion(1.1)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", new UserProfileModel());
        responseData.put("success", false);
        responseData.put("message", "UserProfiles by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            String host = "http://app.ar.co.th";

            String employeeUri = host + "/arraAPI/api/ArraApi/masterApi";
            WebTarget webTarget = ClientBuilder.newClient().target(employeeUri);
            String postBodyEmployee = "json={\"module\":\"EMPLOYEEID\",\"target\":\"GETEMPLOYEEID\",\"token\":\"" + token + "\",\"entries\":{\"EmpCode\": \"doraemon001\"},\"environment\":\"DEV\",\"system\":\"AWORKFLOW\",\"action\":\"SELECT\",\"limit\":null,\"offset\":null,}";
            Response response = webTarget
                    .request(MediaType.APPLICATION_JSON)
                    .buildPost(Entity.entity(postBodyEmployee, MediaType.APPLICATION_FORM_URLENCODED)).invoke();
            LOG.debug("response.getStatus = " + response.getStatus());
            if (response.getStatus() == 200) {
                String responseStr = response.readEntity(String.class);
                LOG.debug("responseStr = " + responseStr);
                ObjectMapper om = new ObjectMapper();
                HashMap responseDataHashMap = (HashMap) om.readValue(responseStr, HashMap.class);
                String responseStatus = (String) responseDataHashMap.get("status");
                LOG.debug("responseStatus = " + responseStatus);
                String responseMessage = (String) responseDataHashMap.get("message");
                LOG.debug("responseMessage = " + responseMessage);
                HashMap entries = (HashMap) responseDataHashMap.get("entries");
                int entriesEmpId = (Integer) entries.get("EmpId");
                LOG.debug("entriesEmpId = " + entriesEmpId);
                String entriesEmpCode = (String) entries.get("EmpCode");
                LOG.debug("entriesEmpCode = " + entriesEmpCode);
                String entriesEmpPrefixCode = (String) entries.get("EmpPrefixCode");
                LOG.debug("entriesEmpPrefixCode = " + entriesEmpPrefixCode);
                String entriesEmpFirstnameTh = (String) entries.get("EmpFirstnameTh");
                LOG.debug("entriesEmpFirstnameTh = " + entriesEmpFirstnameTh);
                String entriesEmpSurnameTh = (String) entries.get("EmpSurnameTh");
                LOG.debug("entriesEmpSurnameTh = " + entriesEmpSurnameTh);
                String entriesFullNameTh = (String) entries.get("FullNameTh");
                LOG.debug("entriesFullNameTh = " + entriesFullNameTh);
                String entriesEmpFirstnameEng = (String) entries.get("EmpFirstnameEng");
                LOG.debug("entriesEmpFirstnameEng = " + entriesEmpFirstnameEng);
                String entriesEmpSurnameEng = (String) entries.get("EmpSurnameEng");
                LOG.debug("entriesEmpSurnameEng = " + entriesEmpSurnameEng);
                String entriesEmpPhone = (String) entries.get("EmpPhone");
                LOG.debug("entriesEmpPhone = " + entriesEmpPhone);
                String entriesEmpMobile = (String) entries.get("EmpMobile");
                LOG.debug("entriesEmpMobile = " + entriesEmpMobile);
                String entriesEmpEmail = (String) entries.get("EmpEmail");
                LOG.debug("entriesEmpEmail = " + entriesEmpEmail);
                String entriesEmpPositionCode = (String) entries.get("EmpPositionCode");
                LOG.debug("entriesEmpPositionCode = " + entriesEmpPositionCode);
                String entriesEmpPositionName = (String) entries.get("EmpPositionName");
                LOG.debug("entriesEmpPositionName = " + entriesEmpPositionName);
                String entriesEmpBusinessTypeCode = (String) entries.get("EmpBusinessTypeCode");
                LOG.debug("entriesEmpBusinessTypeCode = " + entriesEmpBusinessTypeCode);
                String entriesEmpBusinessTypeName = (String) entries.get("EmpBusinessTypeName");
                LOG.debug("entriesEmpBusinessTypeName = " + entriesEmpBusinessTypeName);
                String entriesEmpDepartmentCode = (String) entries.get("EmpDepartmentCode");
                LOG.debug("entriesEmpDepartmentCode = " + entriesEmpDepartmentCode);
                String entriesEmpDepartmentName = (String) entries.get("EmpDepartmentName");
                LOG.debug("entriesEmpDepartmentName = " + entriesEmpDepartmentName);
                String entriesEmpTeamCode = (String) entries.get("EmpTeamCode");
                LOG.debug("entriesEmpTeamCode = " + entriesEmpTeamCode);
                String entriesEmpTeamName = (String) entries.get("EmpTeamName");
                LOG.debug("entriesEmpTeamName = " + entriesEmpTeamName);
                String entriesEmpRoleCode = (String) entries.get("EmpRoleCode");
                LOG.debug("entriesEmpRoleCode = " + entriesEmpRoleCode);
                String entriesEmpRoleName = (String) entries.get("EmpRoleName");
                LOG.debug("entriesEmpRoleName = " + entriesEmpRoleName);
                String entriesEmpSupervisorCode = (String) entries.get("EmpSupervisorCode");
                LOG.debug("entriesEmpSupervisorCode = " + entriesEmpSupervisorCode);
                String entriesEmpSupervisorName = (String) entries.get("EmpSupervisorName");
                LOG.debug("entriesEmpSupervisorName = " + entriesEmpSupervisorName);
                String entriesEmpStatusCode = (String) entries.get("EmpStatusCode");
                LOG.debug("entriesEmpStatusCode = " + entriesEmpStatusCode);
                String entriesEmpStatusName = (String) entries.get("EmpStatusName");
                LOG.debug("entriesEmpStatusName = " + entriesEmpStatusName);
                String entriesEmpZoneCode = (String) entries.get("EmpZoneCode");
                LOG.debug("entriesEmpZoneCode = " + entriesEmpZoneCode);
                String entriesEmpZoneName = (String) entries.get("EmpZoneName");
                LOG.debug("entriesEmpZoneName = " + entriesEmpZoneName);
                String entriesEmpUsername = (String) entries.get("EmpUsername");
                LOG.debug("entriesEmpUsername = " + entriesEmpUsername);
                String entriesEmpPassword = (String) entries.get("EmpPassword");
                LOG.debug("entriesEmpPassword = " + entriesEmpPassword);

//                UserProfileService userProfileService = new UserProfileService();
//                UserProfile userProfile = new UserProfile();
//                
//                responseData.put("data", userProfileService.tranformToModel(userProfile));
//                responseData.put("message", "UserProfile created successfully.");
//                status = Response.Status.CREATED;
//                responseData.put("success", true);
//                UserService userService = new UserService();                    
//                User user = new User();
//                user.setCreatedBy(-9);
//                user.setUserName(entriesEmpUsername);
//                user.setUserPassword("");
//                user = userService.create(user);
//                if(user.getOrderNo()==0){
//                    user.setOrderNo(user.getId());
//                    user = userService.update(user);
//                }
//                //LogData For create User
//                userService.saveLogForCreate(user, httpHeaders.getHeaderString("clientIp"));
//                if(user!=null) {
//                    UserProfileService userProfileService = new UserProfileService();
//                    StructureService structureService = new StructureService();
//                    TitleService titleService = new TitleService();
//                    UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
//                    UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
//                    PositionService positionService = new PositionService();
//                    PositionTypeService positionTypeService = new PositionTypeService();
//                    UserStatusService userProfileStatusService = new UserStatusService();
//                    
//                    UserProfile userProfile = new UserProfile();
//                    userProfile.setCreatedBy(-9);
//                    
//                }
            }

//            String verifyTokenUri = host+"/arraAPI/api/ArraApi/tokenApi";
//            WebTarget webTarget = ClientBuilder.newClient().target(verifyTokenUri);
//            String postBodyVerifyToken = "json={\"module\":\"TOKEN\",\"target\":\"GETTOKEN\",\"token\":\""+token+"\",\"entries\":null,\"environment\":\"DEV\",\"system\":\"AWORKFLOW\",\"action\":\"SELECT\",\"limit\":null,\"offset\":null,}";
//            Response response = webTarget
//                                .request(MediaType.APPLICATION_JSON)
//                                .buildPost(Entity.entity(postBodyVerifyToken, MediaType.APPLICATION_FORM_URLENCODED)).invoke();
//            LOG.debug("response.getStatus = "+response.getStatus());
//            if (response.getStatus() == 200) {
//                String responseStr = response.readEntity(String.class);
//                LOG.debug("responseStr = "+responseStr);
//                ObjectMapper om = new ObjectMapper();
//                HashMap responseDataHashMap = (HashMap) om.readValue(responseStr, HashMap.class);
//                String responseStatus = (String)responseDataHashMap.get("status");
//                LOG.debug("responseStatus = "+responseStatus);
//                String responseMessage = (String)responseDataHashMap.get("message");
//                LOG.debug("responseMessage = "+responseMessage);
//                HashMap entries = (HashMap) responseDataHashMap.get("entries");
//                int entriesUserId = (Integer)entries.get("userid");
//                LOG.debug("entriesUserId = "+entriesUserId);
//                String entriesUserName = (String)entries.get("username");
//                LOG.debug("entriesUserName = "+entriesUserName);
//                String entriesToken = (String)entries.get("token");
//                LOG.debug("entriesToken = "+entriesToken);
//                String entriesAction = (String)entries.get("action");
//                LOG.debug("entriesAction = "+entriesAction);
//            }
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
    
    @ApiOperation(
            value = "Method for notify by SMS",
            notes = "รับแจ้งเตือนด้วย sms ",
            response = UserProfileModel.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "Notify UserProfiles status updated by id success.")
        ,@ApiResponse(code = 404, message = "UserProfiles by id not found in the database.")
        ,@ApiResponse(code = 500, message = "Internal Server Error!")
    })
    @GET
    @Path(value = "/sms/{phoneNumber}")
    public Response notifyBySMS(
            @ApiParam(name = "phoneNumber", value = "หมายเลขโทรศัพท์", required = true)
            @PathParam("phoneNumber") String phoneNumber
    ) {
        LOG.debug("notifyBySMS...");
        LOG.debug("phoneNumber = " + phoneNumber);
        Gson gs = new GsonBuilder()
                .setVersion(1.0)
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        HashMap responseData = new HashMap();
        Response.Status status = Response.Status.NOT_FOUND;
        responseData.put("data", null);
        responseData.put("success", false);
        responseData.put("message", "notifyBySMS by id not found in the database.");
        responseData.put("errorMessage", "");
        try {
            SMSService smsService = new SMSService();
            String result = smsService.sendSMS("", phoneNumber);
            responseData.put("data", result);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            responseData.put("errorMessage", ex.getMessage());
        }
        return Response.status(status).entity(gs.toJson(responseData)).build();
    }
}
