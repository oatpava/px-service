package com.px.admin.service;

import com.px.share.service.ParamService;
import com.px.admin.entity.Ad;
import com.px.admin.entity.Module;
import com.px.admin.entity.ModuleConfig;
import com.px.share.entity.Param;
import com.px.admin.entity.Position;
import com.px.admin.entity.PositionType;
import com.px.admin.entity.Structure;
import com.px.admin.entity.Title;
import com.px.admin.entity.User;
import com.px.admin.entity.UserProfile;
import com.px.admin.entity.UserProfileType;
import com.px.admin.entity.UserStatus;
import com.px.admin.entity.UserTypeOrder;
import com.px.share.entity.Month;
import com.px.share.service.MonthService;
import com.px.share.util.Common;
import com.px.share.util.PxInit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.boot.MetadataSources;

/**
 * For create Admin Module Entity.
 * <br>
 * author OPAS
 */
public class AdminModuleEntityService {

    private static final Logger LOG = Logger.getLogger(AdminModuleEntityService.class.getName());
    private final int createdBy;

    public AdminModuleEntityService() {
        this.createdBy = 0;
    }

    public MetadataSources listCreateEntity(MetadataSources metadataSource) {
        //Add Entity 
        metadataSource.addAnnotatedClass(com.px.admin.entity.Ad.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.Auth.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.FileAttach.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Holiday.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.LogData.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Lookup.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.LookupDetail.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Module.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.ModuleConfig.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.ModuleUserAuth.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.Param.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Position.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.PositionType.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.RecycleBin.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.Month.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Organize.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Structure.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Submodule.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.SubmoduleAuth.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.SubmoduleAuthTemplate.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.SubmoduleAuthTemplateVal.class);
        metadataSource.addAnnotatedClass(com.px.authority.entity.SubmoduleUserAuth.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.TempTable.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.Title.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.User.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.UserParam.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.UserProfile.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.UserProfileType.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.UserStatus.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.UserTypeOrder.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.WordBrief.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.WordBriefDetail.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.VStructure.class);
        metadataSource.addAnnotatedClass(com.px.admin.entity.VUserProfile.class);
        metadataSource.addAnnotatedClass(com.px.menu.entity.Menu.class);
        metadataSource.addAnnotatedClass(com.px.menu.entity.MenuSubmoduleAuth.class);
        metadataSource.addAnnotatedClass(com.px.menu.entity.MenuType.class);
        metadataSource.addAnnotatedClass(com.px.share.entity.Month.class);

        return metadataSource;
    }

    public void createDefaultData(String structureName, String adminUserName, String adminPassword, String adminFullName) {
        createDataParam();
        createDataPosition("pc_position.csv");
        createDataPositionType("pc_position_type.csv");
        createDataTitle("pc_title.csv");
        createDataUserProfileType("pc_user_profile_type.csv");
        createDataUserStatus("pc_user_status.csv");
        createDataUserTypeOrder("pc_user_type_order.csv");
        createDataModule();
        createDataModuleConfig();
        createDataStructure(structureName);
        createDataUser(adminUserName, adminPassword, adminFullName);
        createMonth();
        createListAd();
    }

    private void createListAd() {
        AdService adService = new AdService();
        List<Ad> listAd = adService.listAll("", "");
        LOG.info("create List Ad. ");
        LOG.info(listAd);
        if (!listAd.isEmpty()) {
            for (Ad ad : listAd) {
                LOG.info(ad);
                PxInit.ListAd.add(ad);
            }
        }
    }

    private void createMonth() {
        MonthService monthService = new MonthService();
        Month month = monthService.getById(1);
        Month result = null;
        if (month == null) {
            LOG.info("Month not found!! Auto create Month. ");
            long t1 = -System.currentTimeMillis();
            String monthName = "มกราคม,กุมภาพันธ์,มีนาคม,เมษายน,พฤษภาคม,มิถุนายน,กรกฎาคม,สิงหาคม,กันยายน,ตุลาคม,พฤศจิกายน,ธันวาคม";
            String monthNameAbbr = "ม.ค.,ก.พ.,มี.ค.,เม.ย.,พ.ค.,มิ.ย.,ก.ค.,ส.ค.,ก.ย.,ต.ค.,พ.ย.,ธ.ค.";
            String monthNameEng = "January,February,March,April,May,June,July,August,September,October,November,December";
            String monthNameEngAbbr = "JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC";
            String orderFiscal = "4,5,6,7,8,9,10,11,12,1,2,3";
            String[] monthNameArray = monthName.split(",");
            String[] monthNameAbbrArray = monthNameAbbr.split(",");
            String[] monthNameEngArray = monthNameEng.split(",");
            String[] monthNameEngAbbrArray = monthNameEngAbbr.split(",");
            String[] orderFiscalArray = orderFiscal.split(",");
            int i = 0;
            for (i = 0; i < monthNameArray.length; i++) {
                month = new Month();
                month.setCreatedBy(this.createdBy);
                month.setEngName(monthNameEngArray[i]);
                month.setEngNameAbbr(monthNameEngAbbrArray[i]);
                month.setOrderFiscal(Integer.parseInt(orderFiscalArray[i]));
                month.setThaiName(monthNameArray[i]);
                month.setThaiNameAbbr(monthNameAbbrArray[i]);
                result = monthService.create(month);
                result.setOrderNo(result.getId());
                result = monthService.update(result);
            }
            LOG.info("Month created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataUserTypeOrder(String masterFileName) {
        UserTypeOrderService userTypeOrderService = new UserTypeOrderService();
        UserTypeOrder userTypeOrder = userTypeOrderService.getById(1);
        UserTypeOrder result = null;
        if (userTypeOrder == null) {
            LOG.info("UserTypeOrder not found!! Auto create UserTypeOrder. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    userTypeOrder = new UserTypeOrder();
                    userTypeOrder.setCreatedBy(this.createdBy);
                    userTypeOrder.setUserTypeOrderName(masterDataArray[0]);
                    result = userTypeOrderService.create(userTypeOrder);
                    result.setOrderNo(result.getId());
                    result = userTypeOrderService.update(result);
                }
            }
            LOG.info("UserTypeOrder created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataUserStatus(String masterFileName) {
        UserStatusService userStatusService = new UserStatusService();
        UserStatus userStatus = userStatusService.getById(1);
        UserStatus result = null;
        if (userStatus == null) {
            LOG.info("UserStatus not found!! Auto create UserStatus. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    userStatus = new UserStatus();
                    userStatus.setCreatedBy(this.createdBy);
                    userStatus.setUserStatusName(masterDataArray[0]);
                    result = userStatusService.create(userStatus);
                    result.setOrderNo(result.getId());
                    result = userStatusService.update(result);
                }
            }
            LOG.info("UserStatus created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataUserProfileType(String masterFileName) {
        UserProfileTypeService userProfileTypeService = new UserProfileTypeService();
        UserProfileType userProfileType = userProfileTypeService.getById(1);
        UserProfileType result = null;
        if (userProfileType == null) {
            LOG.info("UserProfileType not found!! Auto create UserProfileType. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    userProfileType = new UserProfileType();
                    userProfileType.setCreatedBy(this.createdBy);
                    userProfileType.setUserProfileTypeName(masterDataArray[0]);
                    result = userProfileTypeService.create(userProfileType);
                    result.setOrderNo(result.getId());
                    result = userProfileTypeService.update(result);
                }
            }
            LOG.info("UserProfileType created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataUser(String userName, String password, String fullName) {
        UserService userService = new UserService();
        User user = userService.getById(1);
        UserProfile result = null;
        if (user == null) {
            LOG.info("User admin not found!! Auto create admin. ");
            long t1 = -System.currentTimeMillis();
            Structure structure = new Structure();
            structure.setId(1);

            Title title = new Title();
            title.setId(1);

            UserProfileType userProfileType = new UserProfileType();
            userProfileType.setId(1);

            UserStatus userStatus = new UserStatus();
            userStatus.setId(1);

            user = new User();
            user.setCreatedBy(this.createdBy);
            user.setUserName(userName);
            user.setUserPassword(password);
            user = userService.create(user);

            UserProfile userProfile = new UserProfile();
            userProfile.setCreatedBy(this.createdBy);
            userProfile.setStructure(structure);
            userProfile.setUserProfileFullName(fullName);
            userProfile.setTitle(title);
            userProfile.setUserProfileStatus(userStatus);
            userProfile.setUserProfileType(userProfileType);
            userProfile.setUser(user);

            UserProfileService userProfileService = new UserProfileService();
            result = userProfileService.create(userProfile);

            user = userService.getById(result.getUser().getId());
            user.setOrderNo(user.getId());
            result.setOrderNo(result.getId());
            result.setUser(user);
            result = userProfileService.update(result);
            LOG.info("User admin created successfully." + (t1 + System.currentTimeMillis()));
            LOG.info("Default Admin user name = " + userName + " , password = " + password);
        }
    }

    private void createDataTitle(String masterFileName) {
        TitleService titleService = new TitleService();
        Title title = titleService.getById(1);
        Title result = null;
        if (title == null) {
            LOG.info("Title not found!! Auto create Title. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    title = new Title();
                    title.setCreatedBy(this.createdBy);
                    title.setTitleName(masterDataArray[0]);
                    title.setTitleNameEng(masterDataArray[1]);
                    result = titleService.create(title);
                    result.setOrderNo(result.getId());
                    result = titleService.update(result);
                }
            }
            LOG.info("Title created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataStructure(String structureName) {
        StructureService structureService = new StructureService();
        Structure structure = structureService.getById(1);
        Structure result = null;
        if (structure == null) {
            LOG.info("Structure not found!! Auto create Structure. ");
            long t1 = -System.currentTimeMillis();
            structure = new Structure();
            structure.setCreatedBy(this.createdBy);
            structure.setStructureName(structureName);
//            structure.setStructureShortName(" ");
            structure.setParentId(0);
            structure.setNodeLevel(0);
            structure.setParentKey(PxInit.Separator + "1" + PxInit.Separator);
            result = structureService.create(structure);
            result.setOrderNo(result.getId());
            result = structureService.update(result);
            LOG.info("Structure created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataPosition(String masterFileName) {
        PositionService positionService = new PositionService();
        Position position = positionService.getById(1);
        Position result = null;
        if (position == null) {
            LOG.info("Position not found!! Auto create Position. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    position = new Position();
                    position.setCreatedBy(this.createdBy);
                    position.setPositionName(masterDataArray[0]);
                    if (!masterDataArray[1].equalsIgnoreCase("NULLNULLNULL")) {
                        position.setPositionNameEng(masterDataArray[1]);
                    }
                    if (!masterDataArray[2].equalsIgnoreCase("NULLNULLNULL")) {
                        position.setPositionNameExtra(masterDataArray[2]);
                    }
                    result = positionService.create(position);
                    result.setOrderNo(result.getId());
                    result = positionService.update(result);
                }
                LOG.info("Position created successfully." + (t1 + System.currentTimeMillis()));
            }
            LOG.info("Position created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataPositionType(String masterFileName) {
        PositionTypeService positionTypeService = new PositionTypeService();
        PositionType positionType = positionTypeService.getById(1);
        PositionType result = null;
        if (positionType == null) {
            LOG.info("PositionType not found!! Auto create PositionType. ");
            long t1 = -System.currentTimeMillis();
            Common common = new Common();
            List<String> listMasterData = (ArrayList<String>) common.readMasterFileToList(masterFileName);
            if (listMasterData.size() > 1) {
                for (String masterData : listMasterData) {
                    String[] masterDataArray = masterData.split(PxInit.MasterFileSplitBy);
                    positionType = new PositionType();
                    positionType.setCreatedBy(this.createdBy);
                    positionType.setPositionTypeAbbr(masterDataArray[0]);
                    positionType.setPositionTypeName(masterDataArray[1]);
                    result = positionTypeService.create(positionType);
                    result.setOrderNo(result.getId());
                    result = positionTypeService.update(result);
                }
                LOG.info("PositionType created successfully." + (t1 + System.currentTimeMillis()));
            }
        }
    }

    private void createDataParam() {
        Map<String, String> listParam = new HashMap();
        listParam.put("PATH_DOCUMENT", "D:\\dbPraxticol\\Data\\Document\\");
        listParam.put("PATH_DOCUMENT_TEMP", "D:\\dbPraxticol\\Data\\Document\\Temp\\");
        listParam.put("PATH_DOCUMENT_HTTP", "http://192.168.142.149:80/document/");
        listParam.put("REPORT_USERNAME", "jasperadmin");
        listParam.put("REPORT_PASSWORD", "jasperadmin");
        listParam.put("REPORT_URI", "http://192.168.142.149:85/jasperserver/rest_v2/reports/reports");
        listParam.put("USE_AD", "N");
        listParam.put("ENCODE_FILE", "Y");
        listParam.put("EMAIL_SERVER", "mail.praxis.co.th");
        listParam.put("EMAIL_PORT", "25");
        listParam.put("EMAIL_SYSTEM_FROM", "admin@praxis.co.th");
        listParam.put("EMAIL_USER_NAME", "opas@praxis.co.th");
        listParam.put("EMAIL_USER_PASS", " ");
        listParam.put("ELASTICSEARCH_SERVER_NAME", "localhost");
        listParam.put("ELASTICSEARCH_SERVER_PORT", "9300");
        listParam.put("ELASTICSEARCH_CLUSTER_NAME", "elasticsearch");
        listParam.put("WATERMARK", "N");
        listParam.put("PATH_FILE_WATERMARK", "C:\\dbPraxticol\\Data\\Document\\Watermark\\watermark.png");
        listParam.put("USE_FTS", "N");
        listParam.put("FORGOT_PASS_PATH", "http://192.168.142.149:80/forgot");
        listParam.put("TIMEOUT", "1800");
        listParam.put("PASSEXPIRATION", "90");
        listParam.put("ANDTXT", "&");
        listParam.put("ORTXT", ",");
        listParam.put("NOTTXT", "!");
        listParam.put("NULLTXT", "NULL");
        listParam.put("PATHHRIS", "http://192.168.142.148:8080/");
        listParam.put("DEFAULT_PASSWORD", "1234");
        listParam.put("TIMEOUT", "30000");
        listParam.put("PASSEXPIRATION", "90");
        listParam.put("PASSEXPIRATIONTYPE", "60");

        ParamService paramService = new ParamService();
        Param param = null;
        for (String key : listParam.keySet()) {
            param = paramService.getByParamName(key);
            if (param == null) {
                LOG.info("Param " + key + " not found!! Auto create " + key + ". ");
                param = new Param();
                param.setCreatedBy(this.createdBy);
                param.setParamName(key);
                param.setParamValue(listParam.get(key));
                param = paramService.create(param);
                param.setOrderNo(param.getId());
                param = paramService.update(param);
            }
        }
    }

    private void createDataModuleConfig() {
        ModuleService moduleService = new ModuleService();
        Module module = moduleService.getByModuleNameEng("admin");
        ModuleConfigService moduleConfigService = new ModuleConfigService();
        ModuleConfig moduleConfig = moduleConfigService.getById(1);
        ModuleConfig result = null;
        if (moduleConfig == null) {
            LOG.info("ModuleConfig not found!! Auto create ModuleConfig. ");
            long t1 = -System.currentTimeMillis();
            String moduleConfigName = "รายชื่อบุคลากร,ประวัติการใช้งาน,สถานะผู้ใช้งานระบบ ณ เวลาปัจจุบัน,แฟ้มคำเหมือน,ข้อมูลตัวเลือก,ถังขยะ,จัดกลุ่มรายชื่อบุคลากร,กำหนดวันหยุดประจำปี";
            String moduleConfigNameEng = "usersprofile,syslog,userlogon,wordbrief,lookup,recyclebin,usergroup,holiday";
            String moduleConfigIcon = "admin1_01.png,admin1_02.png,admin1_03.png,admin1_04.png,admin1_05.png,admin1_06.png,admin1_09.png,admin1_12.png";

            String[] moduleConfigNameArray = moduleConfigName.split(",");
            String[] moduleConfigEngArray = moduleConfigNameEng.split(",");
            String[] moduleConfigIconArray = moduleConfigIcon.split(",");
            int i = 0;
            for (i = 0; i < moduleConfigNameArray.length; i++) {
                moduleConfig = new ModuleConfig();
                moduleConfig.setCreatedBy(this.createdBy);
                moduleConfig.setModule(module);
                moduleConfig.setModuleConfigFunction(null);
                moduleConfig.setModuleConfigIcon(moduleConfigIconArray[i]);
                moduleConfig.setModuleConfigName(moduleConfigNameArray[i]);
                moduleConfig.setModuleConfigNameEng(moduleConfigEngArray[i]);
                moduleConfig.setModuleConfigShow(0);
                result = moduleConfigService.create(moduleConfig);
                result.setOrderNo(result.getId());
                result = moduleConfigService.update(result);
            }
            LOG.info("ModuleConfig created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    private void createDataModule() {
        ModuleService moduleService = new ModuleService();
        Module module = moduleService.getById(1);
        Module result = null;
        if (module == null) {
            LOG.info("Module not found!! Auto create Module. ");
            long t1 = -System.currentTimeMillis();
            String moduleCode = "mwp,wf,dms,admin,";
            String moduleIcon = "folder,class,dashboard,settings,";
//            String moduleName = "หน้าจอส่วนตัว,ระบบสารบรรณฯ,ระบบจัดเก็บเอกสารฯ,ระบบตารางงาน,ระบบลาราชการ,ระบบจองห้องประชุม,ระบบจองรถ,ระบบทะเบียนรายชื่อ,ระบบทะเบียนบัตรจอดรถ,ระบบประชุม,ระบบบุคคลากร,ระบบห้องสมุด,ผู้ดูแลระบบ,ระบบลงเวลาปฏิบัติราชการ,ระบบพัสดุ";
//            String moduleNameEng = "My Workplace,wf,dms,calendar,leave,meeting,vehicle,ochart,carcard,conference,hr,vl,admin,timestamp,supply";
            String moduleName = "หน้าจอส่วนตัว,ระบบสารบรรณฯ,ระบบจัดเก็บเอกสารฯ,ผู้ดูแลระบบ,";
            String moduleNameEng = "mwp,wf,dms,admin,";

            String[] moduleCodeArray = moduleCode.split(",");
            String[] moduleIconArray = moduleIcon.split(",");
            String[] moduleNameArray = moduleName.split(",");
            String[] moduleNameEngArray = moduleNameEng.split(",");
            int i = 0;
            for (i = 0; i < moduleCodeArray.length; i++) {
                module = new Module();
                module.setCreatedBy(this.createdBy);
                module.setModuleCode(moduleCodeArray[i]);
                module.setModuleConfigInAdmin(0);
                module.setModuleIcon(moduleIconArray[i]);
                module.setModuleName(moduleNameArray[i]);
                module.setModuleNameEng(moduleNameEngArray[i]);
                result = moduleService.create(module);
                result.setOrderNo(result.getId());
                result = moduleService.update(result);
            }
            LOG.info("Module created successfully." + (t1 + System.currentTimeMillis()));
        }
    }

    /**
     * For create default data when start service.
     *
     * author OPAS
     */
    private void createDataAd() {
        AdService adService = new AdService();
        Ad ad = adService.getById(1);
        if (ad == null) {

        }
    }
}
