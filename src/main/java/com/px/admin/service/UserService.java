package com.px.admin.service;

import com.px.share.service.LogDataService;
import com.px.share.service.ParamService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserDaoImpl;
import com.px.share.entity.LogData;
import com.px.share.entity.Param;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.model.UserModel;
import com.px.share.service.GenericService;
import com.px.share.util.BCrypt;
import com.px.share.util.Common;
import com.px.share.util.PxInit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;

/**
 *
 * @author OPAS
 */
public class UserService implements GenericService<User, UserModel> {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    private final UserDaoImpl userDaoImpl;

    public UserService() {
        this.userDaoImpl = new UserDaoImpl();
    }

    @Override
    public User create(User user) {
        checkNotNull(user, "user entity must not be null");
        checkNotNull(user.getUserName(), "user name must not be null");
        checkNotNull(user.getUserPassword(), "user password must not be null");
        checkNotNull(user.getCreatedBy(), "create by must not be null");
        checkArgument(user.getUserName().length() > 0, "user name must not be empty");
        checkArgument(user.getUserPassword().length() > 0, "user password must not be empty");
        user.setUserPassword(encyptPassword(user.getUserName().toUpperCase(), user.getUserPassword()));
        user = userDaoImpl.create(user);
        if (user.getOrderNo() == 0) {
            user.setOrderNo(user.getId());
            user = update(user);
        }
        return user;
    }

    @Override
    public User getById(int id) {
        checkNotNull(id, "user id must not be null");
        return userDaoImpl.getById(id);
    }

    @Override
    public User update(User user) {
        checkNotNull(user, "user entity must not be null");
        checkNotNull(user.getUserName(), "user name must not be null");
//        checkNotNull(user.getUserPassword(),"user password must not be null");
        checkNotNull(user.getCreatedBy(), "create by must not be null");
        checkArgument(user.getUserName().length() > 0, "user name must not be empty");
//        checkArgument(user.getUserPassword().length() > 0,"user password must not be empty");
        checkNotNull(user.getUpdatedBy(), "update by must not be null");
        user.setUpdatedDate(LocalDateTime.now());
        return userDaoImpl.update(user);
    }

    public User updatePassword(User user) {
        checkNotNull(user, "user entity must not be null");
        checkNotNull(user.getUserName(), "user name must not be null");
        checkNotNull(user.getCreatedBy(), "create by must not be null");
        checkArgument(user.getUserName().length() > 0, "user name must not be empty");
        checkNotNull(user.getUpdatedBy(), "update by must not be null");
        user.setUpdatedDate(LocalDateTime.now());
//////        UserProfile userProfile = new UserProfileService().getDeByUserId(user.getId());//oat-edit
////        UserProfile userProfile = new UserProfileService().getDefaultProfile(user.getId());
//        if (userProfile.getUserProfileType().getId() != 4) {
//            user.setUserPasswordExpireDate(this.getExpirePasswordDate(user.getId()));
//        } else {//ผู้ใช้งานระดับสูง
//            user.setUserPasswordExpireDate(this.getExpirePassDateSomeType(user.getId()));
//        }
        LocalDateTime expirePasswordDate = this.getExpirePasswordDate();
        if (expirePasswordDate != null) {
            user.setUserPasswordExpireDate(expirePasswordDate);
        }
        user.setUserPassword(encyptPassword(user.getUserName().toUpperCase(), user.getUserPassword()));
        return userDaoImpl.update(user);
    }

    @Override
    public User remove(int id, int userId) {
        checkNotNull(id, "user id must not be null");
        User user = getById(id);

        checkNotNull(user, "user entity not found in database.");
        user.setRemovedBy(userId);
        user.setRemovedDate(LocalDateTime.now());
        return userDaoImpl.update(user);
    }

    @Override
    public List<User> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<User> listAll(String sort, String dir) {
        return userDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return userDaoImpl.countAll();
    }

    @Override
    public List<User> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public UserModel tranformToModel(User user) {
        UserModel userModel = null;
        if (user != null) {
            userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setActiveDate(Common.localDateTimeToString(user.getUserActiveDate()));
            userModel.setExpireDate(Common.localDateTimeToString(user.getUserExpireDate()));
            userModel.setPasswordExpireDate(Common.localDateTimeToString(user.getUserPasswordExpireDate()));
            userModel.setName(user.getUserName());
            userModel.setStatus(new UserStatusService().tranformToModel(user.getUserStatus()));
        }
        return userModel;
    }

    public List<User> listByUserStatusId(int userStatusId, int offset, int limit, String sort, String dir) {
        checkNotNull(userStatusId, "userStatusId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userDaoImpl.listByUserStatusId(userStatusId, offset, limit, sort, dir);
    }

    public List<User> listAllByUserStatusId(int userStatusId, String sort, String dir) {
        checkNotNull(userStatusId, "userStatusId must not be null");
        return userDaoImpl.listAllByUserStatusId(userStatusId, sort, dir);
    }

    public Integer countAllByUserStatusId(int userStatusId) {
        checkNotNull(userStatusId, "userStatusId must not be null");
        return userDaoImpl.countAllByUserStatusId(userStatusId);
    }

    private String encyptPassword(String userName, String userPassword) {
        return BCrypt.hashpw(userName.toUpperCase() + userPassword, BCrypt.gensalt(4));
    }

    public HashMap authentication(String userName, String password) {
        if (userName.equalsIgnoreCase("ADMIN")) {
            return authenticationByAdmin(userName, password);
        }

        ParamService paramService = new ParamService();
        Param param = paramService.getByParamName("USE_AD");
        final String useAd = param == null ? "N" : param.getParamValue();
        if (!useAd.equalsIgnoreCase("Y")) {
            return authenticationByLocal(userName, password);
        } else {
            param = paramService.getByParamName("DCEN_API_URI");
            final String dcenApiUri = param == null ? "" : param.getParamValue();
            if (dcenApiUri.length() == 0) {
                HashMap resultData = new HashMap();
                resultData.put("status", -1);
                resultData.put("message", "ไม่พบข้อมูล Param 'DCEN_API_URI' กรุณาติดต่อผู้ดูแลระบบ");
                return resultData;
            }

            param = paramService.getByParamName("DCEN_API_SECRET_KEY");
            final String dcenSecretKey = param == null ? "" : param.getParamValue();
            if (dcenSecretKey.length() == 0) {
                HashMap resultData = new HashMap();
                resultData.put("status", -1);
                resultData.put("message", "ไม่พบข้อมูล Param 'DCEN_API_SECRET_KEY' กรุณาติดต่อผู้ดูแลระบบ");
                return resultData;
            }

            return authenticationByDcen(dcenApiUri, dcenSecretKey, userName, password);
        }
    }

    private HashMap authenticationByAdmin(String userName, String password) {
        HashMap resultData = new HashMap();
        
        User user = getUserByUserName(userName);
        final boolean result = userDaoImpl.checkLogin(userName, password, user.getUserPassword());
        if (!result) {
            resultData.put("status", -3);
            resultData.put("message", "รหัสผ่านไม่ถูกต้อง");
            return resultData;
        }

        resultData.put("data", user);
        return resultData;
    }

    private HashMap authenticationByLocal(String userName, String password) {
        HashMap resultData = new HashMap();

        User user = getUserByUserName(userName);
        if (user == null) {
            resultData.put("status", -2);
            resultData.put("message", "ชื่อผู้ใช้ไม่ถูกต้อง");
            return resultData;
        }

        final boolean result = userDaoImpl.checkLogin(userName, password, user.getUserPassword());
        if (!result) {
            resultData.put("status", -3);
            resultData.put("message", "รหัสผ่านไม่ถูกต้อง");
            return resultData;
        }

        if (user.getUserExpireDate() != null && LocalDateTime.now().isAfter(user.getUserExpireDate())) {
            resultData.put("status", -4);
            resultData.put("message", "ผู้ใช้นี้หมดอายุการใช้งาน โปรดติดต่อผู้ดูแลระบบ");
            return resultData;
        }

        if (user.getUserPasswordExpireDate() != null && LocalDateTime.now().isAfter(user.getUserPasswordExpireDate())) {
            resultData.put("status", -5);
            resultData.put("message", "รหัสผ่านหมดอายุการใช้งาน โปรดติดต่อผู้ดูแลระบบ");
            return resultData;
        }

        if (user.getUserStatus().getId() == 3) {
            resultData.put("status", -6);
            resultData.put("message", "ผู้ใช้นี้ถูกระงับการใช้งาน โปรดติดต่อผู้ดูแลระบบ");
            return resultData;
        }

        if (user.getUpdatedBy() == 0) {
            resultData.put("status", 2);
            resultData.put("message", "กรุณาเปลี่ยนรหัสผ่านในครั้งแรกที่เข้าระบบ");
            return resultData;
        }

        resultData.put("data", user);
        return resultData;
    }

    private HashMap authenticationByDcen(String dcenApiUri, String dcenSecretKey, String userName, String password) {
        HashMap resultData = new HashMap();

        final HashMap responseData = loginDcen(dcenApiUri, dcenSecretKey, userName, password);
//        System.out.println("responseData: " + responseData);
//        System.out.println("");
        if (responseData == null) {
            resultData.put("status", -1);
            resultData.put("message", "เกิดข้อผิดพลาดในการเชื่อมต่อกับระบบ DCEN");
            return resultData;
        }

        final HashMap dcenUserData = (HashMap) responseData.get("data");
        if (dcenUserData == null) {
            resultData.put("status", -1);
            resultData.put("message", Common.getString(responseData, "message"));
            return resultData;
        }

        final String email = Common.getString(dcenUserData, "email");
        if (email == null) {
            resultData.put("status", -1);
            resultData.put("message", "ไม่มีข้อมูล email จากระบบ DCEN");
            return resultData;
        }

        User user = getUserByUserName(userName);
        if (user == null) {
            user = createUserDcen(dcenUserData, userName, password);
            if (user == null) {
                resultData.put("status", -1);
                resultData.put("message", "เกิดข้อผิดพลาดในการสร้างข้อมูลผู้ใช้จากระบบ DCEN");
                return resultData;
            }
        } else {
            user = updateUserDcen(user, dcenUserData);
            if (user == null) {//if no match email, create profile
                resultData.put("status", -1);
                resultData.put("message", "เกิดข้อผิดพลาดในการสร้างข้อมูลผู้ใช้จากระบบ DCEN");
                return resultData;
            }
        }

        resultData.put("data", user);
        return resultData;
    }

    private HashMap loginDcen(String dcenApiUri, String dcenSecretKey, String userName, String password) {
        final String apiPath = dcenApiUri + "api/auth/logon";

        try {
            URL url = new URL(apiPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("secret-key", dcenSecretKey);

            String body = "username=" + URLEncoder.encode(userName, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
            }

            int status = conn.getResponseCode();

            InputStream is = (status == 200)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            HashMap resultData = new HashMap();
            String msgPrefix = "เข้าสู่ระบบ DCEN ไม่สำเร็จ";
            switch (status) {
                case 200:
                    ObjectMapper mapper = new ObjectMapper();
                    HashMap<String, Object> responseData = mapper.readValue(
                            response.toString(),
                            new TypeReference<HashMap<String, Object>>() {
                    }
                    );
//                    System.out.println("Parsed JSON: " + responseData);
//                    System.out.println("");

                    if (responseData.containsKey("user")) {
                        Object userObj = responseData.get("user");
                        if (userObj != null) {
                            resultData.put("data", userObj);
                        } else {
                            resultData.put("message", "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
                        }
                    } else {
                        String msg = Common.getString(responseData, "status");
                        if (msg != null) {
                            resultData.put("message", msgPrefix + ": " + msg);
                        } else {
                            resultData.put("message", msgPrefix);
                        }
                    }
                    break;
                case 404:
                    resultData.put("message", msgPrefix + ": ไม่พบ API '" + apiPath + "'");
                    break;
                default:
                    resultData.put("message", msgPrefix + ": เกิดข้อผิดพลาด status '" + status + "'");
                    break;
            }

            return resultData;
        } catch (IOException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            LOG.error("authenticationByDcen.loginDcen().Exception = " + ex.getMessage());
            LOG.error("authenticationByDcen.loginDcen().ST = " + sw.toString());
            return null;
        }
    }

    private User createUserDcen(HashMap dcenUserData, String userName, String password) {
        try {
            User user = new User();
            user.setCreatedBy(1);
            user.setUserActiveDate(LocalDateTime.now());
            user.setUserExpireDate(null);
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setUserPasswordExpireDate(null);
            user = create(user);

            UserProfile userProfile = new UserProfileService().createDcenUserProfile(user, dcenUserData);
            if (userProfile == null) {
                userDaoImpl.delete(user);
                return null;
            }

            return user;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            LOG.error("createUserDcen().Exception = " + ex.getMessage());
            LOG.error("createUserDcen().ST = " + sw.toString());
            return null;
        }
    }

    private User updateUserDcen(User user, HashMap dcenUserData) {
        try {
            UserProfile userProfile = new UserProfileService().updateDcenUserProfile(user, dcenUserData);
            if (userProfile == null) {//error create()
                return null;
            } else if (userProfile.getId() == null) {//un change
                return user;
            }

            user.setUpdatedBy(1);
            return update(user);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            LOG.error("updateUserDcen().Exception = " + ex.getMessage());
            LOG.error("updateUserDcen().ST = " + sw.toString());
            return user;
        }
    }

    public User getUserByUserName(String userName) {
        checkNotNull(userName, "userName must not be null");
        return userDaoImpl.getUserByUserName(userName);
    }

    @Override
    public User getByIdNotRemoved(int id) {
        checkNotNull(id, "User id entity must not be null");
        return userDaoImpl.getByIdNotRemoved(id);
    }

    public String genToken(User user, UserProfile userProfile) {
        String result = "";
        final long iat = System.currentTimeMillis() / 1000l; // issued at claim 
        final long exp = iat + (1800L); // expires claim. In this case the token expires in 60 seconds
        final HashMap<String, Object> headerClaims = new HashMap<>();
//        headerClaims.put("exp", exp);
//        headerClaims.put("iat", iat);
        headerClaims.put("name", user.getUserName());
        headerClaims.put("pfid", userProfile.getId());
        headerClaims.put("pftyp", userProfile.getUserProfileType().getId());
        try {
            Algorithm algorithm = Algorithm.HMAC256(PxInit.KEY);
            result = JWT.create()
                    .withIssuer(PxInit.ISSUER)
                    //                    .withClaim("name", user.getUserName())
                    .withClaim("pfid", Common.encryptString(Integer.toString(userProfile.getId())))
                    .withClaim("pftyp", Common.encryptString(Integer.toString(userProfile.getUserProfileType().getId())))
                    .withHeader(headerClaims)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException | JWTCreationException ex) {
            //UTF-8 encoding not supported
            LOG.debug(ex);
        }
        //Invalid Signing configuration / Couldn't convert Claims.
        return result;
    }

    public User saveLogForCreate(User user, String clientIp) {
        String logDescription = this.generateLogForCreateEntity(user);
        LogData logData = new LogData();
        logData.setCreatedBy(user.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(user.getClass().getName());
        logData.setLinkId(user.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return user;
    }

    public User saveLogForUpdate(User userOld, User userNew, String clientIp) {
        String logDescription = this.generateLogForUpdateEntity(userOld, userNew);
        LogData logData = new LogData();
        logData.setCreatedBy(userNew.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(userNew.getClass().getName());
        logData.setLinkId(userNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return userNew;
    }

    public User saveLogForRemove(User user, String clientIp) {
        String logDescription = this.generateLogForRemoveEntity(user);
        LogData logData = new LogData();
        logData.setCreatedBy(user.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(user.getClass().getName());
        logData.setLinkId(user.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return user;
    }

    public UserProfile saveLogForLogin(UserProfile userProfile, String clientIp) {
        String logDescription = this.generateLogForLogin(userProfile.getUser());
        LogData logData = new LogData();
        logData.setCreatedBy(userProfile.getId());
        logData.setDescription(logDescription);
        logData.setEntityName(userProfile.getClass().getName());
        logData.setLinkId(userProfile.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.login(logData);
        return userProfile;
    }

    public UserProfile saveLogForLogout(UserProfile userProfile, String clientIp) {
        String logDescription = this.generateLogForLogout(userProfile.getUser());
        LogData logData = new LogData();
        logData.setCreatedBy(userProfile.getId());
        logData.setDescription(logDescription);
        logData.setEntityName(userProfile.getClass().getName());
        logData.setLinkId(userProfile.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.logout(logData);
        return userProfile;
    }

    public User saveLogForUpdatePassword(User user, String clientIp) {
        String logDescription = this.generateLogForUpdatePassword(user);
        LogData logData = new LogData();
        logData.setCreatedBy(user.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(user.getClass().getName());
        logData.setLinkId(user.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return user;
    }

    private String generateLogForCreateEntity(User user) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อเข้าใช้งานระบบ : ");
        userLog.append(Common.noNull(user.getUserName(), ""));
        return userLog.toString();
    }

    private String generateLogForUpdateEntity(User userOld, User userNew) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อเข้าใช้งานระบบ : ");
        userLog.append(userOld.getUserName());
        userLog.append("เป็น");
        userLog.append(Common.noNull(userNew.getUserName(), ""));
        return userLog.toString();
    }

    private String generateLogForRemoveEntity(User user) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อเข้าใช้งานระบบ : ");
        userLog.append(user.getUserName());
        return userLog.toString();
    }

    private String generateLogForLogin(User user) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("เข้าใช้งานระบบ : ");
        userLog.append(user.getUserName());
        return userLog.toString();
    }

    private String generateLogForLogout(User user) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ออกจากระบบ : ");
        userLog.append(user.getUserName());
        return userLog.toString();
    }

    private String generateLogForUpdatePassword(User user) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("เข้าใช้งานระบบ : ");
        userLog.append(user.getUserName());
        userLog.append("ทำการเปลี่ยนรหัสผ่าน : ");
        return userLog.toString();
    }

    public String getExpireByActiveDate(String activeDateStr) {
        String expireDateStr;
        activeDateStr = activeDateStr + " 00:00:00";
        LocalDateTime expireDate, activeDate;
        String numActive = new ParamService().getByParamName("NUMBER_OF_ACTIVE").getParamValue();
        long numAct = Long.parseLong(numActive);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        activeDate = LocalDateTime.parse(activeDateStr, formatter);
        expireDate = activeDate.plusDays(numAct);
        expireDateStr = expireDate.format(formatter);
        return expireDateStr;
    }

    public boolean checkUserLock(int id) {
        boolean result = false;
        if (id != 0) {
            User user = new UserService().getById(id);
            if (user.getUserStatus() == null) {
                user.setUserStatus(new UserStatusService().getById(2));
                userDaoImpl.update(user);
            }
            if (user.getUserStatus().getId() != 3) {
                result = true;
                if (user.getUserStatus().getId() == 2) {
                    user.setUserStatus(new UserStatusService().getById(1));
                    userDaoImpl.update(user);
                }
                if (user.getUserActiveDate() != null && LocalDateTime.now().isAfter(user.getUserActiveDate())) {
                    result = true;
                    if (user.getUserExpireDate() != null) {
                        if (LocalDateTime.now().isBefore(user.getUserExpireDate())) {
                            result = true;
                        } else {
                            result = false;
                        }
                    }
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public LocalDateTime getExpirePasswordDate() {
        LocalDateTime expireDate;
        String passExpireStr = new ParamService().getByParamName("PASSEXPIRATION").getParamValue();
        long passExpire = Long.parseLong(passExpireStr);
//        expireDate = user.getCreatedDate().plusDays(passExpire);
        if (passExpire == 0) {
            expireDate = null;
        } else {
            expireDate = LocalDateTime.now().plusDays(passExpire);
        }
        return expireDate;
    }

    public LocalDateTime getExpirePassDateSomeType(int id) {
        User user = this.getById(id);
        LocalDateTime expireDate;
        String passExpireStr = new ParamService().getByParamName("PASSEXPIRATIONTYPE").getParamValue();
        long passExpire = Long.parseLong(passExpireStr);
//        expireDate = user.getCreatedDate().plusDays(passExpire);
        expireDate = LocalDateTime.now().plusDays(passExpire);
        return expireDate;
    }

    public String generateLinkForgotPassword(String userName) {
        String returnUrl = "";
        ParamService paramService = new ParamService();
        Param param = paramService.getByParamName("FORGOT_PASS_PATH");
        String encode = Base64.getEncoder().encodeToString(userName.toUpperCase().getBytes());
        String encode2 = Base64.getEncoder().encodeToString(encode.toUpperCase().getBytes());
        String encode3 = Base64.getEncoder().encodeToString(encode2.toUpperCase().getBytes());
        returnUrl = param.getParamValue() + "?f=" + encode3;
        return returnUrl;
    }

    public boolean checkUserNameForgotPassword(String userName, String userNameFromUrl) {
        String encode = Base64.getEncoder().encodeToString(userName.toUpperCase().getBytes());
        String encode2 = Base64.getEncoder().encodeToString(encode.toUpperCase().getBytes());
        String encode3 = Base64.getEncoder().encodeToString(encode2.toUpperCase().getBytes());
        return encode3.equals(userNameFromUrl);
    }

}
