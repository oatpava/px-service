package com.px.share.util;

import com.px.admin.service.AdminModuleEntityService;
import com.px.dms.service.DmsModuleEntityService;
import com.px.wf.service.WfModuleEntityService;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Logger;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import com.px.mwp.service.MwpModuleEntityService;
import java.util.ArrayList;

/**
 *
 * @author OPAS
 */
@WebListener
public class PxInit implements ServletContextListener {

    private static final long serialVersionUID = 2719075102602466086L;
    private static final Logger LOG = Logger.getLogger(PxInit.class.getName());
    private ServletContext sc;
    private final String adminUserName;
    private final String adminPassword;
    private final String adminFullName;
    private final String structureName;

    private final String appName;
    public static String Version = "8.0";
    public static String ProjectName = "Praxticol";
    public static final String ISSUER = "https://praxis.co.th/";
//    public static final String KEY = "42444678902180239428359892197310423042347238957274623846238423423423546";
    public static final String KEY = "42444678902180239428358646519815165132078951561321646432131696998878616";
    public static final String HEADER = "ATENCODE01";
    public static final String DEFAULT_PASSWORD = "12345678";
    public static String PathDocument = "/dbPraxticol/Data/Document/";
    public static String PathDocumentTemp = "/dbPraxticol/Data/Document/Temp/";
    public static String PathWatermark = "/dbPraxticol/Data/Document/Watermark/";
    public static String PathMasterFile = "masterFile/";
    public static String MasterFileSplitBy = "\\|";
    public static String PathLog = "logs\\";
    public static String LogFileName = ".log";
    public static String Separator = "฿";
    public static String Language = "T";
    public static String LookupOrderbyName = "";
    public static String FolderOrderbyName = "";

    public static HashMap HashAuthentication = new HashMap();
    public static List ListAd = new ArrayList();
    public static Metadata METADATA = null;

//    private static Scheduler scheduler;
    public static String getVersion() {
        return "1.0";
    }

    public PxInit() {
        this.appName = "pxservice";
        this.adminUserName = "admin";
        this.adminPassword = "1234";
        this.adminFullName = "ผู้ดูแลระบบ";
        this.structureName = "โครงสร้างหน่วยงาน";
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info(appName + " Service Start... ");
//        Locale.setDefault(new Locale("en", "EN"));
//        LOG.info("Default Locale en_EN");
        String modules = "admin,wf,mwp,dms,";

        long t1 = -System.currentTimeMillis();
        sc = sce.getServletContext();

        try {
//            PathDocument = Common.noNull(sc.getInitParameter("PathDocument"), "D:\\dbPraxticol\\Data\\Document\\");
//            PathDocumentTemp = Common.noNull(sc.getInitParameter("PathDocumentTemp"), "D:\\dbPraxticol\\Data\\Document\\Temp\\");
            //create default data
            createPathFile();

            //Add Entity
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            MetadataSources metadataSource;
            metadataSource = new MetadataSources(standardRegistry);
           
            if (modules.contains("admin")) {
                AdminModuleEntityService adminModuleEntityService = new AdminModuleEntityService();
                metadataSource = adminModuleEntityService.listCreateEntity(metadataSource);
            }
             
            if(modules.contains("dms")){
                DmsModuleEntityService dmsModuleEntityService = new DmsModuleEntityService();
                metadataSource = dmsModuleEntityService.listCreateEntity(metadataSource);                
            }

            if (modules.contains("wf")) {
                WfModuleEntityService wfModuleEntityService = new WfModuleEntityService();
                metadataSource = wfModuleEntityService.listCreateEntity(metadataSource);
            }
            
            if (modules.contains("mwp")) {
                MwpModuleEntityService mwpModuleEntityService = new MwpModuleEntityService();
                metadataSource = mwpModuleEntityService.listCreateEntity(metadataSource);
            }

            METADATA = metadataSource.getMetadataBuilder().applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE).build();

            //Create default Data
            if (modules.contains("admin")) {
                AdminModuleEntityService adminModuleEntityService = new AdminModuleEntityService();
                adminModuleEntityService.createDefaultData(structureName, adminUserName, adminPassword, adminFullName);
            }
            
            if(modules.contains("dms")){
                DmsModuleEntityService dmsModuleEntityService = new DmsModuleEntityService();
                dmsModuleEntityService.createDefaultData();
            }

            //Create default Data
            if (modules.contains("wf")) {
                WfModuleEntityService wfModuleEntityService = new WfModuleEntityService();
                wfModuleEntityService.createDefaultData();
            }

            if (modules.contains("mwp")) {
                MwpModuleEntityService mwpModuleEntityService = new MwpModuleEntityService();
                mwpModuleEntityService.createDefaultData();
            }
        } catch (Exception e) {
            LOG.error("Exception = " + e);
        } finally {
            LOG.info(appName + " Version " + Version + " Initializing Usage Time " + (t1 + System.currentTimeMillis()) + " milliseconds. ***");
            LOG.info(appName + " Version " + Version + " RUNNING.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info(appName + " Version " + Version + " Service Stop.....");
        HibernateUtil.getSESSION_FACTORY().close();
    }

    private void createPathFile() {
        File f = new File(PathDocument);
        if (f.exists()) {
            LOG.info("PathDocument .... OK.");
        } else {
            f.mkdirs();
            LOG.info("PathDocument Not Found!!! .... Auto Create PathDocument =  " + f.exists());
        }

        f = new File(PathDocumentTemp);
        if (f.exists()) {
            LOG.info("PathDocumentTemp .... OK.");
        } else {
            f.mkdirs();
            LOG.info("PathDocumentTemp Not Found!!! .... Auto Create PathDocumentTemp =  " + f.exists());
        }
        
        f = new File(PathWatermark);
        if(f.exists()){
            LOG.info("PathWatermark .... OK.");                                
        }else{
            f.mkdirs();
            LOG.info("PathWatermark Not Found!!! .... Auto Create PathWatermark =  "+f.exists());
        }
    }
}
