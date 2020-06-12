package com.px.admin.service;

import com.px.share.service.LogDataService;
import com.px.share.service.ParamService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserDaoImpl;
import com.px.admin.entity.Ad;
import com.px.share.entity.LogData;
import com.px.share.entity.Param;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.model.UserModel;
import com.px.share.service.GenericService;
import com.px.share.util.BCrypt;
import com.px.share.util.Common;
import com.px.share.util.PxInit;
import java.io.UnsupportedEncodingException;
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
////        UserProfile userProfile = new UserProfileService().getDeByUserId(user.getId());//oat-edit
//        UserProfile userProfile = new UserProfileService().getDefaultProfile(user.getId());
//        if (userProfile.getUserProfileType().getId() != 4) {
//            user.setUserPasswordExpireDate(this.getExpirePasswordDate(user.getId()));
//        } else {
//            user.setUserPasswordExpireDate(this.getExpirePassDateSomeType(user.getId()));
//        }
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

    public boolean authenticationUser(String userName, String userPassword) {
        checkNotNull(userName, "userName must not be null");
        checkNotNull(userPassword, "userPassword must not be null");
        User userLogin = null;
        boolean result = false;
        ParamService paramService = new ParamService();
        Param param = paramService.getByParamName("USE_AD");
        if (userName.equalsIgnoreCase("ADMIN")) {
            param = null;
        }
        if (param != null) {
            if (param.getParamValue().equalsIgnoreCase("Y")) {
                Ad ad = new Ad();
                result = authenticationUserByAd(ad, userName, userPassword);
            } else {
                userLogin = userDaoImpl.getUserByUserName(userName);
                checkNotNull(userLogin, "userName login not found in database");
                result = userDaoImpl.checkLogin(userLogin.getUserName(), userPassword, userLogin.getUserPassword());
            }
        } else {
            userLogin = userDaoImpl.getUserByUserName(userName);
            checkNotNull(userLogin, "userName login not found in database");
            result = userDaoImpl.checkLogin(userLogin.getUserName(), userPassword, userLogin.getUserPassword());
        }
//        result = userDaoImpl.checkLogin(userLogin.getUserName(),userPassword,userLogin.getUserPassword());
        return result;
    }

    public User getUserByUserName(String userName) {
        checkNotNull(userName, "userName must not be null");
        return userDaoImpl.getUserByUserName(userName);
    }

    private boolean authenticationUserByAd(Ad ad, String userName, String userPassword) {
        boolean result = false;
        LOG.debug(ad.getAdHost());
        LOG.debug(ad.getAdPort());
        AdService adService = new AdService();
        String userNameForCheckAd = "uid=" + userName + ",jvd=dpim.go.th,dc=dpim,dc=go,dc=th";
        result = adService.simpleAuthenticationUser("ldap.dpim.go.th", "389", userNameForCheckAd, userPassword);
        return result;
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

    public User saveLogForLogin(User user, String clientIp) {
        String logDescription = this.generateLogForLogin(user);
        LogData logData = new LogData();
        logData.setCreatedBy(user.getId());
        logData.setDescription(logDescription);
        logData.setEntityName(user.getClass().getName());
        logData.setLinkId(user.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.login(logData);
        return user;
    }

    public User saveLogForLogout(User user, String clientIp) {
        String logDescription = this.generateLogForLogout(user);
        LogData logData = new LogData();
        logData.setCreatedBy(user.getId());
        logData.setDescription(logDescription);
        logData.setEntityName(user.getClass().getName());
        logData.setLinkId(user.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.logout(logData);
        return user;
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

    public boolean checkUserExpireDate(int id) {
        boolean result = false;
        if (id != 0) {
            User user = new UserService().getById(id);
            if (user.getUserExpireDate() != null && LocalDateTime.now().isAfter(user.getUserExpireDate())) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkUserPasswordExpireDate(int id) {
        boolean result = false;
        if (id != 0) {
            User user = new UserService().getById(id);
            if (user.getUserPasswordExpireDate() != null && LocalDateTime.now().isAfter(user.getUserPasswordExpireDate())) {
                result = true;
            }
        }
        return result;
    }

    public LocalDateTime getExpirePasswordDate(int id) {
        User user = this.getById(id);
        LocalDateTime expireDate;
        String passExpireStr = new ParamService().getByParamName("PASSEXPIRATION").getParamValue();
        long passExpire = Long.parseLong(passExpireStr);
//        expireDate = user.getCreatedDate().plusDays(passExpire);
        expireDate = LocalDateTime.now().plusDays(passExpire);
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
