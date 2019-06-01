/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.share.entity.TempTable;
import com.px.admin.entity.UserProfile;
import com.px.share.service.TempTableService;
import com.px.admin.service.UserProfileService;
import com.px.authority.entity.SubmoduleAuth;
import com.px.authority.service.SubmoduleAuthService;
import com.px.authority.service.SubmoduleUserAuthService;
import com.px.dms.dao.DmsDocumentDao;
import com.px.dms.entity.DmsDocument;
import com.px.dms.entity.DmsFolder;
import com.px.dms.entity.WfDocumentType;
import com.px.dms.model.FieldSearchModel;
import com.px.dms.service.DmsFolderService;
import com.px.dms.service.WfDocumentTypeService;
import com.px.share.daoimpl.GenericDaoImpl;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author TOP
 */
public class DmsDocumentDaoImpl extends GenericDaoImpl<DmsDocument, Integer> implements DmsDocumentDao {

    private static final Logger LOG = Logger.getLogger(DmsDocumentDaoImpl.class.getName());
    private final String fieldSearch;

    public DmsDocumentDaoImpl() {
        super(DmsDocument.class);
        fieldSearch = "documentName,createdDateForm,createdDateTo,createdBy,updatedDateForm,updatedDateTo,"
                + "updatedBy,documentExpireDateForm,documentExpireDateTo,documentDate01Form,documentDate01To,documentDate02Form,documentDate02To,documentDate03Form,documentDate03To,documentDate04Form,documentDate04To,documentFloat01,documentFloat02,documentInt01,documentInt02,documentInt03,documentInt04,documentText01,documentText02,documentText03,documentText04,documentText05,documentVarchar01,documentVarchar02,documentVarchar03,documentVarchar04,documentVarchar05,documentVarchar06,documentVarchar07,documentVarchar08,documentVarchar09,documentVarchar10";
    }

    @Override
    public boolean checkDocmentInFolder(int folderId) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);

        List<DmsDocument> dmsDocumentlist = this.listByCriteria(criteria);
        int getdocid = 0;
        if (dmsDocumentlist.size() != 0) {
            DmsDocument dmsDocument = dmsDocumentlist.get(0);
            if (dmsDocument != null) {
                getdocid = dmsDocument.getId();
            }
        }
        return getdocid > 0;
    }

    @Override
    public List<DmsDocument> findListDocumentExpire(int folderId) {
//        Calendar cal = Calendar.getInstance();
        DmsFolderService dmsFolderService = new DmsFolderService();
        String[] valueFolderParent = dmsFolderService.getListChildFolder(folderId);
        LocalDateTime nowDate = LocalDateTime.now();
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();
        conjunction.add(Restrictions.le("dmsDocumentExpireDate", nowDate));
        conjunction.add(Restrictions.eq("removedBy", 0));
        if (folderId > 0) {
//            conjunction.add(Restrictions.eq("dmsFolderId", folderId));
            for (String valueFolderParent1 : valueFolderParent) {
                disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
            }

        }

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        conjunction.add(disjunction);
        
        criteria.add(conjunction);
        criteria.addOrder(Order.desc("this.createdDate"));
//                .addOrder(Order.asc("dmsFolderId"));

        return this.listByCriteria(criteria);

    }

    public List<DmsDocument> findListDocumentExpire2() {
//        Calendar cal = Calendar.getInstance();

        LocalDateTime nowDate = LocalDateTime.now();
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.le("dmsDocumentExpireDate", nowDate));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);

        criteria.add(conjunction)
                .addOrder(Order.asc("dmsFolderId"));

        return this.listByCriteria(criteria);

    }
    
    

    @Override
    public List<DmsDocument> findListDocument(int folderId) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);

        return this.listByCriteria(criteria);
    }

    public List<DmsDocument> findListDocumentS(int folderId, int offset, int limit, String sort, String dir) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
//        conjunction.add(Restrictions.ne("dmsDocumentName",""));
        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria = createOrder(criteria, sort, dir);
        criteria.add(conjunction);
//        criteria.add(conjunction);

        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<HashMap> listColumnName() throws Exception {
        DmsDocument entity = new DmsDocument();
        return this.getColumnName(entity);

    }

    @Override
    public boolean updateNameDocumentByDocumentId(DmsDocument document) {
        DmsDocument dmsDocumentDb = getById(document.getId());
        dmsDocumentDb.setUpdatedBy(document.getUpdatedBy());
        dmsDocumentDb.setUpdatedDate(LocalDateTime.now());

        dmsDocumentDb.setDmsDocumentName(document.getDmsDocumentName());

        //query
        this.update(dmsDocumentDb);
        return true;
    }

    @Override
    public boolean updateDocumentTypeByDocumentId(DmsDocument document) {
        DmsDocument dmsDocumentDb = getById(document.getId());
        dmsDocumentDb.setUpdatedBy(document.getUpdatedBy());
        dmsDocumentDb.setUpdatedDate(LocalDateTime.now());

        dmsDocumentDb.setDocumentTypeId(document.getDocumentTypeId());

        //query
        this.update(dmsDocumentDb);
        return true;
    }

    public List<DmsDocument> searchDocumentWithOutAuth(FieldSearchModel fieldSearchData, int folderid, int offset, int limit, int userId) {
        try {
            Conjunction conjunction = createConjunctionFormSearchField(fieldSearchData, folderid, userId);
            DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
            criteria.add(conjunction);
            return this.listByCriteria(criteria, offset, limit);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            return null;
        }

    }

    public List<DmsDocument> searchDocumentWithDocType(FieldSearchModel fieldSearchData, int docTypeId, String isWfType, int offset, int limit, int userId) {
        try {

            Conjunction conjunction = createConjunctionFormSearchField2(fieldSearchData, docTypeId, isWfType);
            DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);

            criteria.add(conjunction);
            return this.listByCriteria(criteria, offset, limit);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            return null;
        }

    }

    @Override
    public DmsDocument getById(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
//        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);

        return this.getOneByCriteria(criteria);
    }

    public DmsDocument getByIdRemovedByNotzero(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
//        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);

        return this.getOneByCriteria(criteria);
    }

    @Override
    public Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams, int folderID) {
        DmsFolderService dmsFolderService = new DmsFolderService();
        String[] valueFolderParent = dmsFolderService.getListChildFolder(folderID);
        UserProfileService userProfileService = new UserProfileService();
        Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();

        for (String valueFolderParent1 : valueFolderParent) {
            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
        }

        conjunction.add(Restrictions.eq("this.removedBy", 0));
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains(key)) {

                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {

                            case "documentName":
                                conjunction.add(Restrictions.like("this.dmsDocumentName", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "createdDateForm":
                                conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "createdDateTo":
                                conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "createdBy":
                                List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(valueArray[i]);
                                for (int z = 0; z < listUserProfile.size(); z++) {

                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
                                }

//                                
                                break;
                            case "updatedDateForm":
                                conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "updatedDateTo":
                                conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "updatedBy":
                                List<UserProfile> listUserProfile2 = userProfileService.getUserIdFromName(valueArray[i]);
                                for (int z = 0; z < listUserProfile2.size(); z++) {

                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile2.get(z).getUser().getId()));
                                }
                                break;
                            case "documentExpireDateForm":
                                conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "documentExpireDateTo":
                                conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;

                            case "documentDate01Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "documentDate01To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "documentDate02Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "documentDate02To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "documentDate03Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "documentDate03To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "documentDate04Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "documentDate04To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "documentFloat01":
                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(valueArray[i])));
                                break;
                            case "documentFloat02":
                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(valueArray[i])));
                                break;
                            case "documentInt01":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt01", Integer.parseInt(valueArray[i])));
                                break;
                            case "documentInt02":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt02", Integer.parseInt(valueArray[i])));
                                break;
                            case "documentInt03":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt03", Integer.parseInt(valueArray[i])));
                                break;
                            case "documentInt04":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt04", Integer.parseInt(valueArray[i])));
                                break;
                            case "documentText01":
                                conjunction.add(Restrictions.like("this.dmsDocumentText01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentText02":
                                conjunction.add(Restrictions.like("this.dmsDocumentText02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentText03":
                                conjunction.add(Restrictions.like("this.dmsDocumentText03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentText04":
                                conjunction.add(Restrictions.like("this.dmsDocumentText04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentText05":
                                conjunction.add(Restrictions.like("this.dmsDocumentText05", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar01":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar02":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar03":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar04":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar05":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "documentVarchar06":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "documentVarchar07":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "documentVarchar08":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "documentVarchar09":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "documentVarchar10":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", valueArray[i], MatchMode.ANYWHERE));
                                break;

                        }
                    }
                }
            }
        }
        conjunction.add(disjunction);
        return conjunction;
    }

    public Integer countAll() {
        return this.listAll("", "").size();
    }

    public List<DmsDocument> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DmsDocument> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);

        return this.listByCriteria(criteria);
    }

    @Override
    public DmsDocument getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
//        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public DmsDocument updateDocumentByDocumentId(DmsDocument document) {
        DmsDocument dmsDocumentDb = getById(document.getId());
        dmsDocumentDb.setUpdatedBy(document.getUpdatedBy());
        dmsDocumentDb.setUpdatedDate(LocalDateTime.now());
        return this.update(dmsDocumentDb);
    }

    public Conjunction createConjunctionFormSearchDocumentExp(MultivaluedMap<String, String> queryParams, int folderID) {
        DmsFolderService dmsFolderService = new DmsFolderService();
        String[] valueFolderParent = dmsFolderService.getListChildFolder(folderID);
        UserProfileService userProfileService = new UserProfileService();

        Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();

        for (String valueFolderParent1 : valueFolderParent) {
            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
        }
        Date nowDate = Calendar.getInstance().getTime();
        nowDate.setDate(nowDate.getDate() - 1);

        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", nowDate));

        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {

                            case "dmsDocumentName":
                                conjunction.add(Restrictions.like("this.dmsDocumentName", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "createdDateForm":
                                conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "createdDateTo":
                                conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "createdBy":
                                List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(valueArray[i]);
                                for (int z = 0; z < listUserProfile.size(); z++) {
                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
                                }
                                break;
                            case "updatedDateForm":
                                conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "updatedDateTo":
                                conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "updatedBy":
                                List<UserProfile> listUserProfile2 = userProfileService.getUserIdFromName(valueArray[i]);
                                for (int z = 0; z < listUserProfile2.size(); z++) {
                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile2.get(z).getUser().getId()));
                                }
                                break;
                            case "dmsDocumentExpireDateForm":
                                conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            case "dmsDocumentExpireDateTo":
                                conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
                                break;

                            case "dmsDocumentDatetime01Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "dmsDocumentDatetime01To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "dmsDocumentDatetime02Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "dmsDocumentDatetime02To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "dmsDocumentDatetime03Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "dmsDocumentDatetime03To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "dmsDocumentDatetime04Form": {
                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }
                            case "dmsDocumentDatetime04To": {
                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
                                break;
                            }

                            case "dmsDocumentFloat01":
                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(valueArray[i])));
                                break;
                            case "dmsDocumentFloat02":
                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(valueArray[i])));
                                break;
                            case "dmsDocumentInt01":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt01", Integer.parseInt(valueArray[i])));
                                break;
                            case "dmsDocumentInt02":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt02", Integer.parseInt(valueArray[i])));
                                break;
                            case "dmsDocumentInt03":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt03", Integer.parseInt(valueArray[i])));
                                break;
                            case "dmsDocumentInt04":
                                conjunction.add(Restrictions.eq("this.dmsDocumentInt04", Integer.parseInt(valueArray[i])));
                                break;
                            case "dmsDocumentText01":
                                conjunction.add(Restrictions.like("this.dmsDocumentText01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentText02":
                                conjunction.add(Restrictions.like("this.dmsDocumentText02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentText03":
                                conjunction.add(Restrictions.like("this.dmsDocumentText03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentText04":
                                conjunction.add(Restrictions.like("this.dmsDocumentText04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentText05":
                                conjunction.add(Restrictions.like("this.dmsDocumentText05", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar01":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar02":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar03":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar04":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar05":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "dmsDocumentVarchar06":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "dmsDocumentVarchar07":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "dmsDocumentVarchar08":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "dmsDocumentVarchar09":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", valueArray[i], MatchMode.ANYWHERE));
                                break;

                            case "dmsDocumentVarchar10":
                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", valueArray[i], MatchMode.ANYWHERE));
                                break;

                        }
                    }
                }
            }
        }
        conjunction.add(disjunction);

        return conjunction;
    }

//    public List<DmsDocument> searchDocumentExt(MultivaluedMap<String, String> queryParams, int folderid, int offset, int limit, int userId) {
//        try {
//            Conjunction conjunction = createConjunctionFormSearchDocumentExpAndAuth(queryParams, folderid, userId);
//            DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
//            criteria.add(conjunction);
//
//            return this.listByCriteria(criteria, offset, limit);
//        } catch (Exception ex) {
//            LOG.error("Exception = " + ex.getMessage());
//            return null;
//        }
//
//    }
//    public Conjunction createConjunctionFormSearchAndAuth(MultivaluedMap<String, String> queryParams, int folderID, int userId) {
//        DmsFolderService dmsFolderService = new DmsFolderService();
//        UserProfileService userProfileService = new UserProfileService();
//        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//        List<DmsFolder> listFolder = dmsFolderService.getListChildFolderList(folderID);
//        SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("open");
//        UserProfile userProfile = userProfileService.getById(userId);
//        String authority = "1";
//
//        List<DmsFolder> listFolder2 = submoduleUserAuthService.getEntityFromTreeByUserProfileAuthority(submoduleAuth, userProfile, authority, listFolder);
//        Conjunction conjunction = Restrictions.conjunction();
//        Disjunction disjunction = Restrictions.disjunction();
//        String listId = "";
//        for (DmsFolder lsitFolder1 : listFolder2) {
//            int Id = lsitFolder1.getId();
//            listId = listId + Id + ",";
//        }
//        String[] valueFolderParent = listId.split(",");
//
//        for (String valueFolderParent1 : valueFolderParent) {
//            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
//        }
//        conjunction.add(Restrictions.eq("this.removedBy", 0));
//        for (String key : queryParams.keySet()) {
//            if (fieldSearch.contains(key)) {
//                for (String value : queryParams.get(key)) {
//                    String[] valueArray = value.split(",");
//                    int i = 0;
//                    for (i = 0; i < valueArray.length; i++) {
//                        switch (key) {
//
//                            case "dmsDocumentName":
//                                conjunction.add(Restrictions.like("this.dmsDocumentName", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "createdDateForm":
//                                conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdDateTo":
//                                conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdBy":
//                                List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile.size(); z++) {
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
//                                }
//                                break;
//                            case "updatedDateForm":
//                                conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedDateTo":
//                                conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedBy":
//                                List<UserProfile> listUserProfile2 = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile2.size(); z++) {
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile2.get(z).getUser().getId()));
//                                }
//                                break;
//                            case "dmsDocumentExpireDateForm":
//                                conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "dmsDocumentExpireDateTo":
//                                conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//
//                            case "dmsDocumentDatetime01Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime01To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime02Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime02To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime03Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime03To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime04Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime04To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentFloat01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentFloat02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt01", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt02", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt03":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt03", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt04":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt04", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentText01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar05":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar06":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar07":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar08":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar09":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar10":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                        }
//                    }
//                }
//            }
//        }
//        conjunction.add(disjunction);
//        return conjunction;
//    }
//
//    public Conjunction createConjunctionFormSearchDocumentExpAndAuth(MultivaluedMap<String, String> queryParams, int folderID, int userId) {
//        DmsFolderService dmsFolderService = new DmsFolderService();
//
//        UserProfileService userProfileService = new UserProfileService();
//        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//        List<DmsFolder> listFolder = dmsFolderService.getListChildFolderList(folderID);
//        SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("open");
//        UserProfile userProfile = userProfileService.getById(userId);
//        String authority = "1";
//
//        List<DmsFolder> listFolder2 = submoduleUserAuthService.getEntityFromTreeByUserProfileAuthority(submoduleAuth, userProfile, authority, listFolder);
//        Conjunction conjunction = Restrictions.conjunction();
//        Disjunction disjunction = Restrictions.disjunction();
//        String listId = "";
//        for (DmsFolder lsitFolder1 : listFolder2) {
//            int Id = lsitFolder1.getId();
//            listId = listId + Id + ",";
//        }
//
//        String[] valueFolderParent = listId.split(",");
//
//        for (String valueFolderParent1 : valueFolderParent) {
//            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
//        }
//        Date nowDate = Calendar.getInstance().getTime();
//        nowDate.setDate(nowDate.getDate() - 1);
//        conjunction.add(Restrictions.eq("this.removedBy", 0));
//        conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", nowDate));
//        for (String key : queryParams.keySet()) {
//            if (fieldSearch.contains(key)) {
//                for (String value : queryParams.get(key)) {
//                    String[] valueArray = value.split(",");
//                    int i = 0;
//                    for (i = 0; i < valueArray.length; i++) {
//                        switch (key) {
//
//                            case "dmsDocumentName":
//                                conjunction.add(Restrictions.like("this.dmsDocumentName", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "createdDateForm":
//                                conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdDateTo":
//                                conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdBy":
//                                List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile.size(); z++) {
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
//                                }
//                                break;
//                            case "updatedDateForm":
//                                conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedDateTo":
//                                conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedBy":
//                                List<UserProfile> listUserProfile2 = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile2.size(); z++) {
//
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile2.get(z).getUser().getId()));
//                                }
//                                break;
//                            case "dmsDocumentExpireDateForm":
//                                conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "dmsDocumentExpireDateTo":
//                                conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//
//                            case "dmsDocumentDatetime01Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime01To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime02Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime02To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime03Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime03To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime04Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime04To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentFloat01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentFloat02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt01", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt02", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt03":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt03", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt04":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt04", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentText01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar05":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar06":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar07":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar08":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar09":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar10":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                        }
//                    }
//                }
//            }
//        }
//        conjunction.add(disjunction);
//        return conjunction;
//    }
//
//    public Conjunction createConjunctionFormSearchDocumentExpAndAuthAndOpertor(MultivaluedMap<String, String> queryParams, int folderID, int userId) {
//        DmsFolderService dmsFolderService = new DmsFolderService();
//
//        UserProfileService userProfileService = new UserProfileService();
//        ParamService paramService = new ParamService();
//
//        String andSymbol = paramService.getByParamName("AND_SYMBOL").getParamValue();
//        String orSymbol = paramService.getByParamName("OR_SYMBOL").getParamValue();
//        String notSymbol = paramService.getByParamName("NOT_SYMBOL").getParamValue();
//        String spaceSymbol = paramService.getByParamName("SPACE_SYMBOL").getParamValue();
//
//        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
//        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
//        List<DmsFolder> listFolder = dmsFolderService.getListChildFolderList(folderID);
//        SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("open");
//        UserProfile userProfile = userProfileService.getById(userId);
//        String authority = "1";
//
//        List<DmsFolder> listFolder2 = submoduleUserAuthService.getEntityFromTreeByUserProfileAuthority(submoduleAuth, userProfile, authority, listFolder);
//        Conjunction conjunction = Restrictions.conjunction();
//        Disjunction disjunction = Restrictions.disjunction();
//        String listId = "";
//        for (DmsFolder lsitFolder1 : listFolder2) {
//            int Id = lsitFolder1.getId();
//            listId = listId + Id + ",";
//        }
//
//        String[] valueFolderParent = listId.split(",");
//
//        for (String valueFolderParent1 : valueFolderParent) {
//            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
//        }
//        Date nowDate = Calendar.getInstance().getTime();
//        nowDate.setDate(nowDate.getDate() - 1);
//        conjunction.add(Restrictions.eq("this.removedBy", 0));
//        conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", nowDate));
//        for (String key : queryParams.keySet()) {
//            if (fieldSearch.contains(key)) {
//                for (String value : queryParams.get(key)) {
//                    String[] valueArray = value.split(",");
//                    int i = 0;
//                    for (i = 0; i < valueArray.length; i++) {
//                        switch (key) {
//
//                            case "dmsDocumentName":
//                                int isAndInt = valueArray[i].indexOf(andSymbol);
//                                int isOrInt = valueArray[i].indexOf(orSymbol);
//                                int isNoIint = valueArray[i].indexOf(notSymbol);
//
//                                boolean isAnd = false;
//                                boolean isOr = false;
//                                boolean isNot = false;
//
//                                if (isAndInt != -1) {
//                                    isAnd = true;
//                                }
//                                if (isOrInt != -1) {
//                                    isOr = true;
//                                }
//                                if (isNoIint != -1) {
//                                    isNot = true;
//                                }
//
//                                conjunction.add(Restrictions.like("this.dmsDocumentName", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "createdDateForm":
//                                conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdDateTo":
//                                conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "createdBy":
//                                List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile.size(); z++) {
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
//                                }
//
//                                break;
//                            case "updatedDateForm":
//                                conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedDateTo":
//                                conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "updatedBy":
//                                List<UserProfile> listUserProfile2 = userProfileService.getUserIdFromName(valueArray[i]);
//                                for (int z = 0; z < listUserProfile2.size(); z++) {
//                                    disjunction.add(Restrictions.eq("this.createdBy", listUserProfile2.get(z).getUser().getId()));
//                                }
//                                break;
//                            case "dmsDocumentExpireDateForm":
//                                conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            case "dmsDocumentExpireDateTo":
//                                conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//
//                            case "dmsDocumentDatetime01Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime01To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime01", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime02Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime02To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime02", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime03Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime03To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime03", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentDatetime04Form": {
//                                conjunction.add(Restrictions.ge("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//                            case "dmsDocumentDatetime04To": {
//                                conjunction.add(Restrictions.le("this.dmsDocumentDatetime04", dateThaiToLocalDateTime(valueArray[i])));
//                                break;
//                            }
//
//                            case "dmsDocumentFloat01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentFloat02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt01":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt01", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt02":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt02", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt03":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt03", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentInt04":
//                                conjunction.add(Restrictions.eq("this.dmsDocumentInt04", Integer.parseInt(valueArray[i])));
//                                break;
//                            case "dmsDocumentText01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentText04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentText04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar01":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar02":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar03":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar04":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar05":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//                            case "dmsDocumentVarchar06":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar07":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar08":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar09":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                            case "dmsDocumentVarchar10":
//                                conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", valueArray[i], MatchMode.ANYWHERE));
//                                break;
//
//                        }
//                    }
//                }
//            }
//        }
//        conjunction.add(disjunction);
//        return conjunction;
//    }
    public static Conjunction createConjunction(Conjunction conjunction, String stringValue, String isAnd, String isOr, String isNot, String spaceValue) {
        boolean isAndBoo = false;
        boolean isOrBoo = false;
        boolean isNotBoo = false;
        int isAndInt = stringValue.indexOf(isAnd);
        int isOrInt = stringValue.indexOf(isOr);
        int isNoIint = stringValue.indexOf(isNot);

        if (isAndInt != -1) {
            isAndBoo = true;
        }
        if (isOrInt != -1) {
            isOrBoo = true;
        }
        if (isNoIint != -1) {
            isNotBoo = true;
        }

        if (!isAndBoo | isOrBoo | isNotBoo) {
            return conjunction;
        }

        return conjunction;
    }

    public String Teststring(String stringValue, String isAnd, String isOr, String isNot, String spaceValue) {

        boolean isAndBoo = false;
        boolean isOrBoo = false;
        boolean isNotBoo = false;
        int isAndInt = stringValue.indexOf(isAnd);
        int isOrInt = stringValue.indexOf(isOr);
        int isNoIint = stringValue.indexOf(isNot);

        if (isAndInt != -1) {
            isAndBoo = true;
        }
        if (isOrInt != -1) {
            isOrBoo = true;
        }
        if (isNoIint != -1) {
            isNotBoo = true;
        }

        if ((isAndBoo) && (!isOrBoo) && (!isNotBoo)) {
            LOG.debug("1");
        }

        return stringValue;
    }

    public TempTable temptableForreport(List<DmsDocument> document, String jobType, String clientIp, int userID) {
        TempTable tempTable = new TempTable();
        for (int i = 0; i < document.size(); i++) {
            String computerName = clientIp;
            String jobtype = jobType;

            UserProfileService userProfile = new UserProfileService();
            //create to tempTable
            tempTable.setCreatedBy(userID);
            tempTable.setComputerName(computerName);
            tempTable.setJobType(jobtype);
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            String createDate = "";
            String updateDate = "";
            if (document.get(i).getCreatedDate() != null) {
                createDate = format1.format(document.get(i).getCreatedDate());
            }
            if (document.get(i).getUpdatedDate() != null) {
                updateDate = format1.format(document.get(i).getUpdatedDate());
            }
            String createName = "";
            String updateName = "";

            if (document.get(i).getCreatedBy() > 0 && document.get(i).getCreatedBy() != null) {
                createName = userProfile.getById(document.get(i).getCreatedBy()).getUserProfileFullName();
            }
            if (document.get(i).getUpdatedBy() > 0 && document.get(i).getCreatedBy() != null) {
                updateName = userProfile.getById(document.get(i).getUpdatedBy()).getUserProfileFullName();
            }
            tempTable.setStr01(document.get(i).getDmsDocumentName());
            tempTable.setStr02(createName);
            tempTable.setStr03(createDate);
            tempTable.setText01(updateName);
            tempTable.setText02(updateDate);

            if (tempTable != null) {
                TempTableService tempTableService = new TempTableService();
                tempTable = tempTableService.create(tempTable);
            }
        }
        return tempTable;
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria, String sort, String dir) {
        if (!sort.isEmpty()) {
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.asc("this.orderNo"));
                        break;
                }
            } else if ((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")) {
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                    case "orderNo":
                        criteria.addOrder(Order.desc("this.orderNo"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    @Override
    public int copyDocument(DmsDocument documentCopy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean moveDocument(DmsDocument documentMove) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DmsDocument> searchDocument(MultivaluedMap<String, String> queryParams, int folderid, int offset, int limit, int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Conjunction createConjunctionFormSearchField(FieldSearchModel fieldSearchData, int folderID, int userId) {
        DmsFolderService dmsFolderService = new DmsFolderService();
        String[] valueFolderParent = dmsFolderService.getListChildFolder(folderID);
        UserProfileService userProfileService = new UserProfileService();
        Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();

        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();
        SubmoduleAuthService submoduleAuthService = new SubmoduleAuthService();
        List<DmsFolder> listFolderAll = dmsFolderService.getListChildFolderObj(folderID);
        SubmoduleAuth submoduleAuth = submoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
        UserProfile userProfile = userProfileService.getById(userId);
        String authority = "1";

        List<DmsFolder> listFolder2All = submoduleUserAuthService.getEntityFromTreeByUserProfileAuthority(submoduleAuth, userProfile, authority, listFolderAll);

        for (DmsFolder valueFolderParent1 : listFolder2All) {
            disjunction.add(Restrictions.eq("this.dmsFolderId", valueFolderParent1.getId()));
        }

        conjunction.add(Restrictions.eq("this.removedBy", 0));

        if (fieldSearchData.getDocumentName() != null && fieldSearchData.getDocumentName() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentName", fieldSearchData.getDocumentName(), MatchMode.ANYWHERE));
        }

        if (fieldSearchData.getCreatedDateForm() != null && fieldSearchData.getCreatedDateForm() != "") {
            conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(fieldSearchData.getCreatedDateForm())));
        }

        if (fieldSearchData.getCreatedDateTo() != null && fieldSearchData.getCreatedDateTo() != "") {
            conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(fieldSearchData.getCreatedDateTo()).plusHours(23).plusMinutes(59)));
        }
        if (fieldSearchData.getCreatedBy() != null && fieldSearchData.getCreatedBy() != "") {
            List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(fieldSearchData.getCreatedBy());
            for (int z = 0; z < listUserProfile.size(); z++) {

                disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
            }
//            conjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(0).getUser().getId()));

        }

        if (fieldSearchData.getUpdatedDateForm() != null && fieldSearchData.getUpdatedDateForm() != "") {
            conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(fieldSearchData.getUpdatedDateForm())));
        }

        if (fieldSearchData.getUpdatedDateTo() != null && fieldSearchData.getUpdatedDateTo() != "") {
            conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(fieldSearchData.getUpdatedDateTo()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getUpdatedBy() != null && fieldSearchData.getUpdatedBy() != "") {
            List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(fieldSearchData.getUpdatedBy());
            for (int z = 0; z < listUserProfile.size(); z++) {

                disjunction.add(Restrictions.eq("this.updatedBy", listUserProfile.get(z).getUser().getId()));
            }

        }
        if (fieldSearchData.getDocumentExpireDateForm() != null && fieldSearchData.getDocumentExpireDateForm() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(fieldSearchData.getDocumentExpireDateForm())));
        }

        if (fieldSearchData.getDocumentExpireDateTo() != null && fieldSearchData.getDocumentExpireDateTo() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(fieldSearchData.getDocumentExpireDateTo()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate01Form() != null && fieldSearchData.getDocumentDate01Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate01", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate01Form())));
        }

        if (fieldSearchData.getDocumentDate01To() != null && fieldSearchData.getDocumentDate01To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate01", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate01To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate02Form() != null && fieldSearchData.getDocumentDate02Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate02", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate02Form())));
        }

        if (fieldSearchData.getDocumentDate02To() != null && fieldSearchData.getDocumentDate02To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate02", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate02To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate03Form() != null && fieldSearchData.getDocumentDate03Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate03", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate03Form())));
        }

        if (fieldSearchData.getDocumentDate03To() != null && fieldSearchData.getDocumentDate03To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate03", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate03To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate04Form() != null && fieldSearchData.getDocumentDate04Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate04", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate04Form())));
        }

        if (fieldSearchData.getDocumentDate04To() != null && fieldSearchData.getDocumentDate04To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate04", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate04To())));
        }

        if (fieldSearchData.getDocumentFloat01() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(fieldSearchData.getDocumentFloat01())));
        }

        if (fieldSearchData.getDocumentFloat02() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(fieldSearchData.getDocumentFloat02())));
        }

        if (fieldSearchData.getDocumentInt01() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt01", fieldSearchData.getDocumentInt01()));
        }

        if (fieldSearchData.getDocumentInt02() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt02", fieldSearchData.getDocumentInt02()));
        }

        if (fieldSearchData.getDocumentInt03() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt03", fieldSearchData.getDocumentInt03()));
        }

        if (fieldSearchData.getDocumentInt04() != -1) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt04", fieldSearchData.getDocumentInt04()));
        }

        if (fieldSearchData.getDocumentText01() != null && fieldSearchData.getDocumentText01() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText01", fieldSearchData.getDocumentText01(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText02() != null && fieldSearchData.getDocumentText02() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText02", fieldSearchData.getDocumentText02(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText03() != null && fieldSearchData.getDocumentText03() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText03", fieldSearchData.getDocumentText03(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText04() != null && fieldSearchData.getDocumentText04() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText04", fieldSearchData.getDocumentText04(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText05() != null && fieldSearchData.getDocumentText05() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText05", fieldSearchData.getDocumentText05(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentVarchar01() != null && fieldSearchData.getDocumentVarchar01() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", fieldSearchData.getDocumentVarchar01(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar02() != null && fieldSearchData.getDocumentVarchar02() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", fieldSearchData.getDocumentVarchar02(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar03() != null && fieldSearchData.getDocumentVarchar03() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", fieldSearchData.getDocumentVarchar03(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar04() != null && fieldSearchData.getDocumentVarchar04() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", fieldSearchData.getDocumentVarchar04(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar05() != null && fieldSearchData.getDocumentVarchar05() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", fieldSearchData.getDocumentVarchar05(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar06() != null && fieldSearchData.getDocumentVarchar06() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", fieldSearchData.getDocumentVarchar06(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar07() != null && fieldSearchData.getDocumentVarchar07() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", fieldSearchData.getDocumentVarchar07(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar08() != null && fieldSearchData.getDocumentVarchar08() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", fieldSearchData.getDocumentVarchar08(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar09() != null && fieldSearchData.getDocumentVarchar09() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", fieldSearchData.getDocumentVarchar09(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar10() != null && fieldSearchData.getDocumentVarchar10() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", fieldSearchData.getDocumentVarchar10(), MatchMode.ANYWHERE));

        }
        conjunction.add(disjunction);
        return conjunction;
    }

    public List<DmsDocument> listDocByDocType(int docType) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();

        conjunction.add(Restrictions.eq("removedBy", 0));

        conjunction.add(Restrictions.eq("documentTypeId", docType));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);

        criteria.add(conjunction);
//                .addOrder(Order.asc("dmsFolderId"));

        return this.listByCriteria(criteria);

    }

    public List<DmsDocument> listDocByDocType2(int docType, int folderId, int userID) {
        Conjunction conjunction = Restrictions.conjunction();
        DmsFolderService dmsFolderService = new DmsFolderService();

        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("documentTypeId", docType));
        List<DmsFolder> listFolder = dmsFolderService.getListChildFolderObj(folderId);
        SubmoduleAuthService SubmoduleAuthService = new SubmoduleAuthService();
        SubmoduleAuth submoduleAuth = SubmoduleAuthService.getBySubmoduleAuthCode("DMS_OF");
        UserProfileService UserProfileService = new UserProfileService();
        UserProfile userProfile = UserProfileService.getById(userID);
        SubmoduleUserAuthService submoduleUserAuthService = new SubmoduleUserAuthService();

        List<DmsFolder> listFolderAuth = submoduleUserAuthService.getEntityByUserProfileAuthority(submoduleAuth, userProfile, "1", listFolder);
        Disjunction disjunction = Restrictions.disjunction();
        for (DmsFolder valueFolderParent1 : listFolderAuth) {
            disjunction.add(Restrictions.eq("this.dmsFolderId", valueFolderParent1.getId()));
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        conjunction.add(disjunction);
        criteria.add(conjunction);

        return this.listByCriteria(criteria);

    }

    public List<DmsDocument> listDocByDocType3(int wfTypeId, int folderId) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();

        conjunction.add(Restrictions.eq("removedBy", 0));

//        conjunction.add(Restrictions.eq("documentTypeId", docType));
        conjunction.add(Restrictions.eq("wfTypeId", wfTypeId));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);

        criteria.add(conjunction);
//                .addOrder(Order.asc("dmsFolderId"));

        return this.listByCriteria(criteria);

    }

    public List<DmsDocument> listByWfId(int flowId) {
        Conjunction conjunction = Restrictions.conjunction();

        conjunction.add(Restrictions.eq("flowId", flowId));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);

    }

    public List<DmsDocument> listByFolderIdAndWfType(int folderId, int wfTypeId) {
        try {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("removedBy", 0));

            conjunction.add(Restrictions.eq("wfTypeId", wfTypeId));
            conjunction.add(Restrictions.eq("dmsFolderId", folderId));

            DetachedCriteria criteria = DetachedCriteria.forClass(DmsDocument.class);
            criteria.add(conjunction);
            return this.listByCriteria(criteria);
        } catch (Exception ex) {

//            LOG.error("Exception = " + ex.getMessage())
            return null;
        }
    }

    public Conjunction createConjunctionFormSearchField2(FieldSearchModel fieldSearchData, int docTypeId, String isWfType) {
        DmsFolderService dmsFolderService = new DmsFolderService();

        WfDocumentTypeService documentTypeService = new WfDocumentTypeService();
        WfDocumentType documentType = new WfDocumentType();

        UserProfileService userProfileService = new UserProfileService();
        Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();

//        for (String valueFolderParent1 : valueFolderParent) {
//            disjunction.add(Restrictions.eq("this.dmsFolderId", Integer.parseInt(valueFolderParent1)));
//        }
        conjunction.add(Restrictions.eq("this.removedBy", 0));

        if (fieldSearchData.getDocumentName() != null && fieldSearchData.getDocumentName() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentName", fieldSearchData.getDocumentName(), MatchMode.ANYWHERE));
        }

        if (fieldSearchData.getCreatedDateForm() != null && fieldSearchData.getCreatedDateForm() != "") {
            conjunction.add(Restrictions.ge("this.createdDate", dateThaiToLocalDateTime(fieldSearchData.getCreatedDateForm())));
        }

        if (fieldSearchData.getCreatedDateTo() != null && fieldSearchData.getCreatedDateTo() != "") {
            conjunction.add(Restrictions.le("this.createdDate", dateThaiToLocalDateTime(fieldSearchData.getCreatedDateTo()).plusHours(23).plusMinutes(59)));
        }
        if (fieldSearchData.getCreatedBy() != null && fieldSearchData.getCreatedBy() != "") {
            List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(fieldSearchData.getCreatedBy());
            for (int z = 0; z < listUserProfile.size(); z++) {

                disjunction.add(Restrictions.eq("this.createdBy", listUserProfile.get(z).getUser().getId()));
            }

        }

        if (fieldSearchData.getUpdatedDateForm() != null && fieldSearchData.getUpdatedDateForm() != "") {
            conjunction.add(Restrictions.ge("this.updatedDate", dateThaiToLocalDateTime(fieldSearchData.getUpdatedDateForm())));
        }

        if (fieldSearchData.getUpdatedDateTo() != null && fieldSearchData.getUpdatedDateTo() != "") {
            conjunction.add(Restrictions.le("this.updatedDate", dateThaiToLocalDateTime(fieldSearchData.getUpdatedDateTo()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getUpdatedBy() != null && fieldSearchData.getUpdatedBy() != "") {
            List<UserProfile> listUserProfile = userProfileService.getUserIdFromName(fieldSearchData.getUpdatedBy());
            for (int z = 0; z < listUserProfile.size(); z++) {

                disjunction.add(Restrictions.eq("this.updatedBy", listUserProfile.get(z).getUser().getId()));
            }

        }
        if (fieldSearchData.getDocumentExpireDateForm() != null && fieldSearchData.getDocumentExpireDateForm() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(fieldSearchData.getDocumentExpireDateForm())));
        }

        if (fieldSearchData.getDocumentExpireDateTo() != null && fieldSearchData.getDocumentExpireDateTo() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentExpireDate", dateThaiToLocalDateTime(fieldSearchData.getDocumentExpireDateTo()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate01Form() != null && fieldSearchData.getDocumentDate01Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate01", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate01Form())));
        }

        if (fieldSearchData.getDocumentDate01To() != null && fieldSearchData.getDocumentDate01To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate01", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate01To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate02Form() != null && fieldSearchData.getDocumentDate02Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate02", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate02Form())));
        }

        if (fieldSearchData.getDocumentDate02To() != null && fieldSearchData.getDocumentDate02To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate02", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate02To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate03Form() != null && fieldSearchData.getDocumentDate03Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate03", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate03Form())));
        }

        if (fieldSearchData.getDocumentDate03To() != null && fieldSearchData.getDocumentDate03To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate03", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate03To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentDate04Form() != null && fieldSearchData.getDocumentDate04Form() != "") {
            conjunction.add(Restrictions.ge("this.dmsDocumentDate04", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate04Form())));
        }

        if (fieldSearchData.getDocumentDate04To() != null && fieldSearchData.getDocumentDate04To() != "") {
            conjunction.add(Restrictions.le("this.dmsDocumentDate04", dateThaiToLocalDateTime(fieldSearchData.getDocumentDate04To()).plusHours(23).plusMinutes(59)));
        }

        if (fieldSearchData.getDocumentFloat01() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentFloat01", Float.valueOf(fieldSearchData.getDocumentFloat01())));
        }

        if (fieldSearchData.getDocumentFloat02() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentFloat02", Float.valueOf(fieldSearchData.getDocumentFloat02())));
        }

        if (fieldSearchData.getDocumentInt01() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt01", fieldSearchData.getDocumentInt01()));
        }

        if (fieldSearchData.getDocumentInt02() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt02", fieldSearchData.getDocumentInt02()));
        }

        if (fieldSearchData.getDocumentInt03() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt03", fieldSearchData.getDocumentInt03()));
        }

        if (fieldSearchData.getDocumentInt04() != 0) {
            conjunction.add(Restrictions.eq("this.dmsDocumentInt04", fieldSearchData.getDocumentInt04()));
        }

        if (fieldSearchData.getDocumentText01() != null && fieldSearchData.getDocumentText01() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText01", fieldSearchData.getDocumentText01(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText02() != null && fieldSearchData.getDocumentText02() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText02", fieldSearchData.getDocumentText02(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText03() != null && fieldSearchData.getDocumentText03() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText03", fieldSearchData.getDocumentText03(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText04() != null && fieldSearchData.getDocumentText04() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText04", fieldSearchData.getDocumentText04(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentText05() != null && fieldSearchData.getDocumentText05() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentText05", fieldSearchData.getDocumentText05(), MatchMode.ANYWHERE));

        }

        if (fieldSearchData.getDocumentVarchar01() != null && fieldSearchData.getDocumentVarchar01() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar01", fieldSearchData.getDocumentVarchar01(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar02() != null && fieldSearchData.getDocumentVarchar02() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar02", fieldSearchData.getDocumentVarchar02(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar03() != null && fieldSearchData.getDocumentVarchar03() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar03", fieldSearchData.getDocumentVarchar03(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar04() != null && fieldSearchData.getDocumentVarchar04() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar04", fieldSearchData.getDocumentVarchar04(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar05() != null && fieldSearchData.getDocumentVarchar05() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar05", fieldSearchData.getDocumentVarchar05(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar06() != null && fieldSearchData.getDocumentVarchar06() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar06", fieldSearchData.getDocumentVarchar06(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar07() != null && fieldSearchData.getDocumentVarchar07() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar07", fieldSearchData.getDocumentVarchar07(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar08() != null && fieldSearchData.getDocumentVarchar08() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar08", fieldSearchData.getDocumentVarchar08(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar09() != null && fieldSearchData.getDocumentVarchar09() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar09", fieldSearchData.getDocumentVarchar09(), MatchMode.ANYWHERE));

        }
        if (fieldSearchData.getDocumentVarchar10() != null && fieldSearchData.getDocumentVarchar10() != "") {
            conjunction.add(Restrictions.like("this.dmsDocumentVarchar10", fieldSearchData.getDocumentVarchar10(), MatchMode.ANYWHERE));

        }
        conjunction.add(disjunction);
        return conjunction;
    }

}
