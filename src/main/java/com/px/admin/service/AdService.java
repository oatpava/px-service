package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.AdDaoImpl;
import com.px.admin.entity.Ad;
import com.px.admin.model.AdModel;
import com.px.share.entity.LogData;
import com.px.share.service.GenericService;
import com.px.share.service.LogDataService;
import com.px.share.util.Common;
import com.px.share.util.PxInit;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class AdService implements GenericService<Ad, AdModel>{
    private static final Logger LOG = Logger.getLogger(AdService.class.getName());
    private static final String CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private final AdDaoImpl adDaoImpl;
    
    public AdService() {
        this.adDaoImpl = new AdDaoImpl();
    }

    @Override
    public Ad create(Ad ad) {
        checkNotNull(ad, "ad entity must not be null");
        checkNotNull(ad.getAdName(), "ad name must not be null");
        checkNotNull(ad.getCreatedBy(),"create by must not be null");
        ad = adDaoImpl.create(ad);
        if(ad.getOrderNo()==0){
            ad.setOrderNo(ad.getId());
            ad = update(ad);
            PxInit.ListAd = listAll("id", "asc");
        }
        return ad;
    }

    @Override
    public Ad getById(int id) {
        checkNotNull(id, "ad id entity must not be null");
        return adDaoImpl.getById(id);
    }

    @Override
    public Ad update(Ad ad) {
        checkNotNull(ad, "ad entity must not be null");
        checkNotNull(ad.getAdName(), "ad name must not be null");
        checkNotNull(ad.getUpdatedBy(),"update by must not be null");
        ad.setUpdatedDate(LocalDateTime.now());
        ad = adDaoImpl.update(ad);
        PxInit.ListAd = listAll("id", "asc");
        return ad;
    }

    @Override
    public Ad remove(int id, int userId) {
        checkNotNull(id, "ad id must not be null");
        Ad ad = getById(id);
        checkNotNull(ad, "ad entity not found in database.");
        ad.setRemovedBy(userId);
        ad.setRemovedDate(LocalDateTime.now());
        ad = adDaoImpl.update(ad);
        PxInit.ListAd = listAll("id", "asc");
        return ad;
    }

    @Override
    public List<Ad> listAll(String sort, String dir) {
        return adDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Ad> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return adDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public AdModel tranformToModel(Ad ad) {
        AdModel adModel = null;
        if(ad!=null){
            adModel = new AdModel();
            adModel.setId(ad.getId());
            adModel.setBase(ad.getAdBase());
            adModel.setHost(ad.getAdHost());
            adModel.setName(ad.getAdName());
            adModel.setPass(ad.getAdPassword());
            adModel.setPort(ad.getAdPort());
            adModel.setUser(ad.getAdUser());
            adModel.setPrefix(ad.getAdPrefix());
            adModel.setSuffix(ad.getAdSuffix());
            adModel.setType(ad.getAdType());
            adModel.setAttribute(ad.getAdAttribute());
        }
        return adModel;
    }

    @Override
    public int countAll() {
        return adDaoImpl.countAll();
    }

    @Override
    public List<Ad> search(MultivaluedMap<String, String> queryAds, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryAds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Ad getByIdNotRemoved(int id) {
        checkNotNull(id, "Ad id entity must not be null");
        return adDaoImpl.getByIdNotRemoved(id);
    }
    
    public boolean simpleAuthenticationUser(String adHost,String adPort,String adUser,String adPassword){
        boolean result = false;
        String adUrl = "ldap://"+adHost+":"+adPort+"/";
        Hashtable env = new Hashtable();
        InitialLdapContext context;
        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, adUrl);
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, adUser);
            env.put(Context.SECURITY_CREDENTIALS, adPassword);
            context = new InitialLdapContext(env,null);
            LOG.info("VerifyUser Success!");
            result = true;
//            distinguishedName: OU=EDOC,OU=NHA Users,DC=nha,DC=co,DC=th
//distinguishedName: CN=เกณิกา จันทร์แก้ว,OU=NHA Users,DC=nha,DC=co,DC=th
        }catch (NamingException ex) {
            LOG.error("Something went wrong!");
            LOG.error("Exception = "+ex);
        }
        return result;
    }
    
    public boolean simpleAuthenticationUserWithAttribute(String adHost,String adPort,String adUser,String adPassword,String adBase, String adAttribute, String userName, String userPassword){
        boolean result = false;
        String adUrl = "ldap://"+adHost+":"+adPort+"/";
        Properties serviceEnv = new Properties();
        DirContext serviceCtx;
        try {
            serviceEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
            serviceEnv.put(Context.PROVIDER_URL, adUrl);
            serviceEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
            serviceEnv.put(Context.SECURITY_PRINCIPAL, adUser);
            serviceEnv.put(Context.SECURITY_CREDENTIALS, adPassword);
            serviceCtx = new InitialDirContext(serviceEnv);
            
            String[] attributeFilter = { adAttribute };
            SearchControls sc = new SearchControls();
            sc.setReturningAttributes(attributeFilter);
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            
            String searchFilter = "(" + adAttribute + "=" + userName + ")";
            NamingEnumeration<SearchResult> results = serviceCtx.search(adBase, searchFilter, sc);
            if (results.hasMore()) {
                SearchResult searchResult = results.next();
                String distinguishedName = searchResult.getNameInNamespace();
                Properties authEnv = new Properties();
                authEnv.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_FACTORY);
                authEnv.put(Context.PROVIDER_URL, adUrl);
                authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
                authEnv.put(Context.SECURITY_CREDENTIALS, userPassword);
                new InitialDirContext(authEnv);
                LOG.debug("VerifyUser Success!");
                result = true;
            }
//            LOG.info("VerifyUser Success!");
//            result = true;
        }catch (NamingException ex) {
            LOG.error("Something went wrong!");
            LOG.error("Exception = "+ex);
        }        
        return result;
    }
    
    boolean sslAuthenticationUser(String adHost,String adPort,String adUser,String adPassword){
        boolean result = false;
        String adUrl = "ldaps://"+adHost+":"+adPort+"/";//"ldaps://192.168.2.11:636/";
        return result;
    }
    
    public Ad saveLogForCreate(Ad ad,String clientIp){
        String logDescription = this.generateLogForCreateEntity(ad);
        LogData logData = new LogData();
        logData.setCreatedBy(ad.getCreatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(ad.getClass().getName());
        logData.setLinkId(ad.getId());
        logData.setIpAddress(clientIp);
        logData.setModuleName(LogData.MODULE_ADMIN);
        LogDataService logDataService = new LogDataService();
        logDataService.createEntity(logData);
        return ad;
    }
    
    public Ad saveLogForUpdate(HashMap adOld, Ad adNew, String clientIp){
        String logDescription = this.generateLogForUpdateEntity(adOld,adNew);
        LogData logData = new LogData();
        logData.setCreatedBy(adNew.getUpdatedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(adNew.getClass().getName());
        logData.setLinkId(adNew.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.updateEntity(logData);
        return adNew;
    }
    
    public Ad saveLogForRemove(Ad ad,String clientIp){
        String logDescription = this.generateLogForRemoveEntity(ad);
        LogData logData = new LogData();
        logData.setCreatedBy(ad.getRemovedBy());
        logData.setDescription(logDescription);
        logData.setEntityName(ad.getClass().getName());
        logData.setLinkId(ad.getId());
        logData.setModuleName(LogData.MODULE_ADMIN);
        logData.setIpAddress(clientIp);
        LogDataService logDataService = new LogDataService();
        logDataService.removeEntity(logData);
        return ad;
    }
    
    private String generateLogForCreateEntity(Ad ad){
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อ : ");
        userLog.append(Common.noNull(ad.getAdName(), ""));
        return userLog.toString();
    }
    
    private String generateLogForUpdateEntity(HashMap adOld,Ad adNew){
        StringBuilder userLog = new StringBuilder();
        if(!adOld.get("Base").equals("")){
            userLog.append("ชื่อ : ");
            userLog.append(adOld.get("Base"));
            userLog.append("เป็น");
            userLog.append(Common.noNull(adNew.getAdBase(),""));
        }
        if(!adOld.get("Host").equals("")){
            userLog.append("ชื่อ : ");
            userLog.append(adOld.get("Base"));
            userLog.append("เป็น");
            userLog.append(Common.noNull(adNew.getAdHost(),""));
        }
        return userLog.toString();
    }
    
    private String generateLogForRemoveEntity(Ad ad){
        StringBuilder userLog = new StringBuilder();
        userLog.append("ชื่อ : ");
        userLog.append(ad.getAdName());        
        return userLog.toString();
    }
}
