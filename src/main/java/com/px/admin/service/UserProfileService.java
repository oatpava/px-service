package com.px.admin.service;

import com.px.share.service.LogDataService;
import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.UserProfileDaoImpl;
import com.px.admin.daoimpl.VUserProfileDaoImpl;
import com.px.admin.entity.DigitalKey;
import com.px.share.entity.LogData;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.VUserProfile;
import com.px.admin.model.UserProfileConvertModel;
import com.px.admin.model.UserProfileModel;
import com.px.admin.model.VUserProfileModel;
import com.px.share.service.FileAttachService;
import com.px.share.service.GenericService;
import com.px.share.util.BCrypt;
import com.px.share.util.Common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author OPAS
 */
public class UserProfileService implements GenericService<UserProfile, UserProfileModel> {

    private static final Logger LOG = Logger.getLogger(UserProfileService.class.getName());
    private final UserProfileDaoImpl userProfileDaoImpl;

    public UserProfileService() {
        this.userProfileDaoImpl = new UserProfileDaoImpl();
    }

    @Override
    public UserProfile create(UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        String inputDigitalKey = userProfile.getDigitalKey();
        userProfile = userProfileDaoImpl.create(userProfile);
        if (userProfile.getOrderNo() == 0) {
            userProfile.setOrderNo(userProfile.getId());
            DigitalKey digitalKey = new DigitalKey(inputDigitalKey + userProfile.getId());
            userProfile.setDigitalKey(digitalKey.getEncryptDigitalKey());
            userProfile = update(userProfile);
        }
        return userProfile;
    }

    @Override
    public UserProfile getById(int id) {
        checkNotNull(id, "userProfile id must not be null");
        return userProfileDaoImpl.getById(id);
    }

    public boolean checkEmail(String username, String email) {
        checkNotNull(username, "username must not be null");
        checkNotNull(email, "userProfile email must not be null");
        UserProfile userProfile = userProfileDaoImpl.checkEmail(username, email);
        boolean result = false;
        if (userProfile != null) {
            result = true;
        }
        return result;
    }

    @Override
    public UserProfile update(UserProfile userProfile) {
        checkNotNull(userProfile, "userProfile entity must not be null");
        checkNotNull(userProfile.getUpdatedBy(), "update by must not be null");
        userProfile.setUpdatedDate(LocalDateTime.now());
        return userProfileDaoImpl.update(userProfile);
    }

    @Override
    public UserProfile remove(int id, int userProfileId) {
        checkNotNull(id, "userProfile id must not be null");
        UserProfile userProfile = getById(id);
        checkNotNull(userProfile, "userProfile entity not found in database.");
        userProfile.setRemovedBy(userProfileId);
        userProfile.setRemovedDate(LocalDateTime.now());
        return userProfileDaoImpl.update(userProfile);
    }

    @Override
    public List<UserProfile> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userProfileDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<UserProfile> listAll(String sort, String dir) {
        return userProfileDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return userProfileDaoImpl.countAll();
    }

    @Override
    public List<UserProfile> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        return userProfileDaoImpl.search(queryParams, offset, limit, sort, dir);
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        return userProfileDaoImpl.countSearch(queryParams);
    }

    @Override
    public UserProfileModel tranformToModel(UserProfile userProfile) {
        UserProfileModel userProfileModel = null;
        if (userProfile != null) {
            StructureService structureService = new StructureService();
            UserService userService = new UserService();
            TitleService titleService = new TitleService();
            UserStatusService userProfileStatusService = new UserStatusService();
            UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
            UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
            PositionService positionService = new PositionService();
            PositionTypeService positionTypeService = new PositionTypeService();
            userProfileModel = new UserProfileModel();
            userProfileModel.setId(userProfile.getId());
            userProfileModel.setStructure(structureService.tranformToModel(userProfile.getStructure()));
            userProfileModel.setTitle(titleService.tranformToModel(userProfile.getTitle()));
            userProfileModel.setDefaultSelect(userProfile.getUserProfileDefaultSelect());
            userProfileModel.setEmail(userProfile.getUserProfileEmail());
            userProfileModel.setFirstName(userProfile.getUserProfileFirstName());
            userProfileModel.setFirstNameEng(userProfile.getUserProfileFirstNameEng());
            userProfileModel.setFullName(userProfile.getUserProfileFullName());
            userProfileModel.setFullNameEng(userProfile.getUserProfileFullNameEng());
            userProfileModel.setLastName(userProfile.getUserProfileLastName());
            userProfileModel.setLastNameEng(userProfile.getUserProfileLastNameEng());
            userProfileModel.setUserStatus(userProfileStatusService.tranformToModel(userProfile.getUserProfileStatus()));
            userProfileModel.setTel(userProfile.getUserProfileTel());
            userProfileModel.setUserProfileType(userProfileTypeService.tranformToModel(userProfile.getUserProfileType()));
//            userProfileModel.setUserTypeOrder(userTypeOrderService.tranformToModel(userProfile.getUserProfileTypeOrder()));
            userProfileModel.setIdCard(userProfile.getUserProfileCardId());
            userProfileModel.setCode(userProfile.getUserProfileCode());
            userProfileModel.setAddress(userProfile.getUserProfileAddress());
            userProfileModel.setPosition(positionService.tranformToModel(userProfile.getPosition()));
            userProfileModel.setPositionLevel(userProfile.getPositionLevel());
            userProfileModel.setPositionType(positionTypeService.tranformToModel(userProfile.getPositionType()));
//            userProfileModel.setDigitalKey("******");
            userProfileModel.setUser(userService.tranformToModel(userProfile.getUser()));
        }
        return userProfileModel;
    }

    public UserProfileConvertModel tranformToConvertModel(int stat, UserProfileModel userProfile, VUserProfileModel vUserProfile) {
        UserProfileConvertModel userProfileConvertModel = null;

        if (stat != 0) {
            userProfileConvertModel = new UserProfileConvertModel();
            userProfileConvertModel.setStatus(stat);
            userProfileConvertModel.setUserProfile(userProfile);
            userProfileConvertModel.setVUserProfile(vUserProfile);
        }
        return userProfileConvertModel;
    }

    public List<UserProfile> listByStructure(Structure structure, String sort, String dir) {
        checkNotNull(structure, "structure entity must not be null");
        return userProfileDaoImpl.listByStructure(structure, sort, dir);
    }

    public List<UserProfile> listByUserProfileStatusId(int userProfileStatusId, int offset, int limit, String sort, String dir) {
        checkNotNull(userProfileStatusId, "userProfileStatusId must not be null");
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return userProfileDaoImpl.listByUserProfileStatusId(userProfileStatusId, offset, limit, sort, dir);
    }

    public List<UserProfile> listAllByUserProfileStatusId(int userProfileStatusId, String sort, String dir) {
        checkNotNull(userProfileStatusId, "userProfileStatusId must not be null");
        return userProfileDaoImpl.listAllByUserProfileStatusId(userProfileStatusId, sort, dir);
    }

    public List<UserProfile> listByStructureParentKey(String parentKey, String sort, String dir) {
        checkNotNull(parentKey, "userProfileStatusId must not be null");
        return userProfileDaoImpl.listByStructureParentKey(parentKey, sort, dir);
    }

    public Integer countAllByUserProfileStatusId(int userProfileStatusId) {
        checkNotNull(userProfileStatusId, "userProfileStatusId must not be null");
        return userProfileDaoImpl.countAllByUserProfileStatusId(userProfileStatusId);
    }

    public UserProfile getByUserId(int userId) {
        checkNotNull(userId, "userId must not be null");
        return userProfileDaoImpl.getByUserId(userId);
    }

    public UserProfile getByUsername(String username) {
        checkNotNull(username, "username must not be null");
        return userProfileDaoImpl.getByUsername(username);
    }

    public List<UserProfile> listByUserId(int userId, String sort, String dir) {
        checkNotNull(userId, "userId must not be null");
        return userProfileDaoImpl.listByUserId(userId, sort, dir);
    }

    public Integer countAllByUserId(int userId) {
        checkNotNull(userId, "userId must not be null");
        return userProfileDaoImpl.countAllByUserId(userId);
    }

    private String encyptPassword(String userName, String userPassword) {
        return BCrypt.hashpw(userName.toUpperCase() + userPassword, BCrypt.gensalt(4));
    }

    @Override
    public UserProfile getByIdNotRemoved(int id) {
        checkNotNull(id, "UserProfile id entity must not be null");
        return userProfileDaoImpl.getByIdNotRemoved(id);
    }

    public List<UserProfile> getUserIdFromName(String userProfileFullName) {
        checkNotNull(userProfileFullName, "userProfileFullName  must not be null");
        return userProfileDaoImpl.getUserIdFromName(userProfileFullName);
    }

    public UserProfile getDefaultProfile(int userProfilesId) {
        checkNotNull(userProfilesId, "userProfilesId must not be null");
        return userProfileDaoImpl.getDefaultProfile(userProfilesId);
    }

    public boolean removeByUserId(int userId, int userProfileId) {
        checkNotNull(userId, "userId must not be null");
        List<UserProfile> listUserProfile = listByUserId(userId, "createdDate", "desc");
        if (!listUserProfile.isEmpty()) {
            for (UserProfile userProfile : listUserProfile) {
                checkNotNull(userProfile, "userProfile entity not found in database.");
                userProfile.setRemovedBy(userProfileId);
                userProfile.setRemovedDate(LocalDateTime.now());
                userProfileDaoImpl.update(userProfile);
            }
        }
        return true;
    }

    public double findOrderNo(int id) {
        checkNotNull(id, "userProfile id must not be null");
        UserProfile userProfile2 = this.getByIdNotRemoved(id);
        UserProfile userProfile = userProfileDaoImpl.getPrevOrderBy(id);
        double orderNoNum = 0;
        if (userProfile != null && userProfile.getOrderNo() == (userProfile2.getOrderNo() - 1)) {
            orderNoNum = (userProfile.getOrderNo() + userProfile2.getOrderNo()) / 2;
        } else {
            orderNoNum = userProfile2.getOrderNo() - 1;
        }
        return orderNoNum;
    }

    public boolean checkDup(String code) {
        boolean result = false;
        int countOfStucture = userProfileDaoImpl.countDup(code);
        if (countOfStucture != 0) {
            result = true;
        }
        return result;
    }

    public UserProfile saveLogForCreate(UserProfile userProfile, String clientIp) {
        String logDescription = this.generateLogForCreateEntity(userProfile);
        LogData logData = new LogData();
        logData.setCreatedBy(userProfile.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(userProfile.getClass().getName());
        logData.setLinkId(userProfile.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return userProfile;
    }

    public UserProfile saveLogForUpdate(UserProfile userProfileOld, UserProfile userProfileNew, String clientIp) {
        String logDescription = this.generateLogForUpdateEntity(userProfileOld, userProfileNew);
        LogData logData = new LogData();
        logData.setCreatedBy(userProfileNew.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(userProfileNew.getClass().getName());
        logData.setLinkId(userProfileNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return userProfileNew;
    }

    public UserProfile saveLogForRemove(UserProfile userProfile, String clientIp) {
        String logDescription = this.generateLogForRemoveEntity(userProfile);
        LogData logData = new LogData();
        logData.setCreatedBy(userProfile.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(userProfile.getClass().getName());
        logData.setLinkId(userProfile.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return userProfile;
    }

    private String generateLogForCreateEntity(UserProfile userProfile) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อ : ");
        userLog.append(Common.noNull(userProfile.getUserProfileFullName(), ""));
        return userLog.toString();
    }

    private String generateLogForUpdateEntity(UserProfile userProfileOld, UserProfile userProfileNew) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อ : ");
        userLog.append(userProfileOld.getUserProfileFirstName());
        userLog.append("เป็น");
        userLog.append(Common.noNull(userProfileNew.getUserProfileFirstName(), ""));
        return userLog.toString();
    }

    private String generateLogForRemoveEntity(UserProfile userProfile) {
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อ : ");
        userLog.append(userProfile.getUserProfileFullName());
        return userLog.toString();
    }

    public int readFileUserProExcel(int fileId, String clientIp) {
        int result = 0;
        BufferedReader br = null;
        try {
            FileAttachService fileAttachService = new FileAttachService();
            String dataPathFile = fileAttachService.moveToTempPath(fileId);
            FileInputStream targetFile = new FileInputStream(new File(dataPathFile));
            LOG.info("dataPathFile = " + dataPathFile);
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(targetFile);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int countRow = 0;

            VUserProfileDaoImpl vUserProfileDaoImpl = new VUserProfileDaoImpl();
            VUserProfileService vUserProfileService = new VUserProfileService();
            List<VUserProfile> listVUserProfile = vUserProfileService.listAllNotRemove("id", "asc");
            if (!listVUserProfile.isEmpty()) {
                for (VUserProfile vUserProfile1 : listVUserProfile) {
                    vUserProfileService.delete(vUserProfile1);
                }
            }

            while (rowIterator.hasNext()) {
                countRow++;
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                LOG.info("----------------------------------------------");
                int countCell = 0;
                int stat = 0;
//                String codeChk = codeChk;//รับจาก interface
                String code = "";
                String structureCode = "";
                Integer idStructure = null;
                Integer idTitle = null;
                String fristName = "";
                String lastName = "";
                String fullName = "";
                String email = "";
                Integer idProfileType = null;
                String tel = "";
                String fristNameEng = "";
                String lastNameEng = "";
                String fullNameEng = "";
                String cardId = "";
                String address = "";
                Integer position = null;
                String positionStr = "";
                int level = 0;
                Integer status = null;

                if (countRow > 1) {
                    for (int i = 0; i < 17; i++) {        //51 = last cell
                        countCell++;
                        Cell cell = row.getCell(countCell - 1, Row.CREATE_NULL_AS_BLANK);
                        //Check the cell type and format accordingly
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC: // numeric value in Excel  
                                LOG.info(countRow + " " + countCell + "N)" + cell.getNumericCellValue());
                                if (countCell == 1) {
                                    code = String.valueOf((int) cell.getNumericCellValue());
                                    LOG.info(code);
                                }
                                if (countCell == 2) {
                                    structureCode = String.valueOf((int) cell.getNumericCellValue());
                                    StructureService structureService = new StructureService();
//                                    VStructureService structureService = new VStructureService();
                                    Structure struc = structureService.getByCode(structureCode);
                                    if (struc != null) {
                                        stat = 1;
                                        idStructure = struc.getId();
                                    }
                                    LOG.info(idStructure);
                                }
                                if (countCell == 3) {
                                    idTitle = (int) cell.getNumericCellValue();
                                    LOG.info(idTitle);
                                }
                                if (countCell == 8) {
                                    idProfileType = (int) cell.getNumericCellValue();
                                    LOG.info(idProfileType);
                                }
                                if (countCell == 9) {
                                    tel = String.valueOf((int) cell.getNumericCellValue());
                                    LOG.info(tel);
                                }
                                if (countCell == 13) {
                                    cardId = String.valueOf((int) cell.getNumericCellValue());
                                    LOG.info(cardId);
                                }
//                                if (countCell == 15) {
//                                    position = (int) cell.getNumericCellValue();
//                                    LOG.info(position);
//                                }
                                if (countCell == 16) {
                                    level = (int) cell.getNumericCellValue();
                                    LOG.info(level);
                                }
                                if (countCell == 17) {
                                    status = (int) cell.getNumericCellValue();
                                    LOG.info(status);
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA: // precomputed value based on formula
                                LOG.info(countRow + " " + countCell + "F)" + cell.getCellFormula());
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                LOG.info(countRow + " " + countCell + "S)" + "");
                            case Cell.CELL_TYPE_BOOLEAN: //boolean value 
                                LOG.info(countRow + " " + countCell + "B)" + cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_STRING: // String Value in Excel 
                                LOG.info(countRow + " " + countCell + "T)" + cell.getStringCellValue());
                                if (countCell == 4) {
                                    fristName = cell.getStringCellValue().trim();
                                }
                                if (countCell == 5) {
                                    lastName = cell.getStringCellValue().trim();
                                }
                                if (countCell == 6) {
                                    fullName = cell.getStringCellValue().trim();
                                }
                                if (countCell == 7) {
                                    email = cell.getStringCellValue().trim();
                                }
                                if (countCell == 10) {
                                    fristNameEng = cell.getStringCellValue().trim();
                                }
                                if (countCell == 11) {
                                    lastNameEng = cell.getStringCellValue().trim();
                                }
                                if (countCell == 12) {
                                    fullNameEng = cell.getStringCellValue().trim();
                                }
                                if (countCell == 14) {
                                    address = cell.getStringCellValue().trim();
                                }
//                                if (countCell == 15) {
//                                    positionStr = cell.getStringCellValue();
//                                    LOG.info(positionStr);
//                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                            default:
                                throw new RuntimeException("There is no support for this type of cell");
//                        break;
                        }
                    }//while (cellIterator.hasNext()) {  //// OR ///// for(int i=0; i<20; i++){  
                    VUserProfile vUserProfile = new VUserProfile();
                    if (stat == 1) {
                        vUserProfile.setCreatedBy(2);//test
                        vUserProfile.setUserProfileCode(code);
                        vUserProfile.setStructure(new StructureService().getById(idStructure));
                        vUserProfile.setTitle(new TitleService().getById(idTitle));
                        vUserProfile.setUserProfileFirstName(fristName);
                        vUserProfile.setUserProfileLastName(lastName);
                        vUserProfile.setUserProfileFullName(fullName);
                        vUserProfile.setUserProfileEmail(email);
                        vUserProfile.setUserProfileType(new UserProfileTypeService().getById(idProfileType));
                        vUserProfile.setUserProfileTel(tel);
                        vUserProfile.setUserProfileFirstNameEng(fristNameEng);
                        vUserProfile.setUserProfileLastNameEng(lastNameEng);
                        vUserProfile.setUserProfileFullNameEng(fullNameEng);
                        vUserProfile.setUserProfileCardId(cardId);
                        vUserProfile.setUserProfileAddress(address);
//                        vUserProfile.setPosition(new PositionService().getById(position));
                        vUserProfile.setPosition(null);
                        vUserProfile.setPositionLevel(level);
                        vUserProfile.setUserProfileStatus(new UserStatusService().getById(status));
                        try {
                            vUserProfile = vUserProfileService.create(vUserProfile);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LOG.error("Exception create = " + e.getMessage());
                        }

                        if (vUserProfile != null) {
                            vUserProfile.setOrderNo(vUserProfile.getId());
                            vUserProfile = vUserProfileService.update(vUserProfile);
                        }
//                    vStructureService.saveLogForCreate(insureTax, clientIp);
                        result++;
                    }
                }
            }
            targetFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception = " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                LOG.error("Exception Close BufferedReader = " + ex.getMessage());
            }
        }
        return result;
    }

    public int readFileUser(ArrayList<String> userObj) {
        int result = 0;
        try {
            int countRow = 0;

            VUserProfileDaoImpl vUserProfileDaoImpl = new VUserProfileDaoImpl();
            VUserProfileService vUserProfileService = new VUserProfileService();
            List<VUserProfile> listVUserProfile = vUserProfileService.listAllNotRemove("id", "asc");
            if (!listVUserProfile.isEmpty()) {
                for (VUserProfile vUserProfile1 : listVUserProfile) {
                    vUserProfileService.delete(vUserProfile1);
                }
            }
            Iterator<String> rowIterator = userObj.iterator();
            while (rowIterator.hasNext()) {
                countRow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception = " + e.getMessage());
        }
        return result;
    }

    public List<UserProfile> listByName(String userProfileFullName) {
        return userProfileDaoImpl.listByName(userProfileFullName);
    }
}
