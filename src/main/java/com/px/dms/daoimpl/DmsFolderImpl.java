/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.px.dms.daoimpl;

import com.px.authority.model.AuthEnableDisableIdListModel;
import com.px.dms.dao.DmsFolderDao;
import com.px.dms.entity.DmsFolder;
import com.px.dms.service.DmsFolderService;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.collections4.list.AbstractLinkedList;
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
public class DmsFolderImpl extends GenericTreeDaoImpl<DmsFolder, Integer> implements DmsFolderDao {

    public DmsFolderImpl() {
        super(DmsFolder.class);
    }

    public DmsFolder findByFolderId(int folderId) {
        //validate

        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);

        //query
        return this.getOneByCriteria(criteria);
    }

    public List<DmsFolder> findListFolder(int folderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("dmsFolderParentId", folderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction)
                .addOrder(Order.asc("dmsFolderType"))
                .addOrder(Order.asc("dmsFolderOrderId"));

        return this.listByCriteria(criteria);
    }

    public List<DmsFolder> findListByFolderParentId(int folderId, int offset, int limit) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("dmsFolderParentId", folderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction)
                .addOrder(Order.asc("dmsFolderType"))
                .addOrder(Order.asc("dmsFolderOrderId"));
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DmsFolder> findListByParentKey(String dmsFolderParentKey, String dmsFolderName, String dmsFolderDescription) {
        //create AND 

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.ilike("dmsFolderParentKey", dmsFolderParentKey, MatchMode.START));

        if (dmsFolderName != null) {
            conjunction.add(Restrictions.ilike("dmsFolderName", dmsFolderName, MatchMode.ANYWHERE));
        }

        if (dmsFolderName != null) {
            conjunction.add(Restrictions.ilike("dmsFolderDescription", dmsFolderDescription, MatchMode.ANYWHERE));
        }

        //create OR
//        Disjunction disjunction = Restrictions.disjunction();
//        disjunction.add(Restrictions.eq("dmsFolderType", "C"));
//        disjunction.add(Restrictions.eq("dmsFolderType", "D"));
//        disjunction.add(Restrictions.eq("dmsFolderType", "F"));
//
//        conjunction.add(disjunction);
        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction).addOrder(Order.asc("dmsFolderNodeLevel")).addOrder(Order.asc("dmsFolderType"));

        //Query Result
        List<DmsFolder> list = this.listByCriteria(criteria);

        return list;
    }

    public boolean moveFolder(DmsFolder folderMove) {
        //validate

        //set
        DmsFolder dmsFolderDb = findByFolderId(folderMove.getId());
        String tempFolderParentKey = dmsFolderDb.getDmsFolderParentKey();

        List<DmsFolder> listDmsFolder = findListByParentKey(tempFolderParentKey, null, null);

        int dmsFolderNodeLevelOld = dmsFolderDb.getDmsFolderNodeLevel();
        String dmsFolderParentKeyOld = dmsFolderDb.getDmsFolderParentKey();
        int dmsFolderNodeLevelDiff = folderMove.getDmsFolderNodeLevel() - dmsFolderNodeLevelOld;
        String dmsFolderParentKeyNew = folderMove.getDmsFolderParentKey();

        for (int i = 0; i < listDmsFolder.size(); i++) {
            DmsFolder dmsFolderUpdate = listDmsFolder.get(i);
            dmsFolderUpdate.setUpdatedBy(folderMove.getUpdatedBy());
            dmsFolderUpdate.setUpdatedDate(LocalDateTime.now());
            if (i == 0) {
                dmsFolderUpdate.setDmsFolderNodeLevel(folderMove.getDmsFolderNodeLevel());
                dmsFolderUpdate.setDmsFolderParentId(folderMove.getDmsFolderParentId());
                dmsFolderUpdate.setDmsFolderParentKey(folderMove.getDmsFolderParentKey());
            } else {
                int dmsFolderNodeLevelNew = dmsFolderNodeLevelDiff + dmsFolderUpdate.getDmsFolderNodeLevel();
                dmsFolderUpdate.setDmsFolderNodeLevel(dmsFolderNodeLevelNew);
                dmsFolderUpdate.setDmsFolderParentKey(dmsFolderUpdate.getDmsFolderParentKey().replaceAll(dmsFolderParentKeyOld, dmsFolderParentKeyNew));
            }

            //query
            dmsFolderUpdate.setUpdatedBy(folderMove.getUpdatedBy());
            dmsFolderUpdate.setUpdatedDate(LocalDateTime.now());

            this.update(dmsFolderUpdate);
        }
        return true;
    }

    public boolean orderFolder(List<DmsFolder> listFolderOrder) {
        //validate

        //set
        for (int i = 0; i < listFolderOrder.size(); i++) {
            DmsFolder dmsFolderUpdate = listFolderOrder.get(i);
            dmsFolderUpdate.setUpdatedDate(LocalDateTime.now());
            dmsFolderUpdate.setDmsFolderOrderId(i + 1);

            //query
            this.update(dmsFolderUpdate);
        }
        return true;
    }

    public boolean updateOrderFolder(int folderId, float orderId) {

        DmsFolder dmsFolderDb = findByFolderId(folderId);
        dmsFolderDb.setDmsFolderOrderId(orderId);

        //query
        this.update(dmsFolderDb);

        return true;
    }

    public String checkTypeFolder(int folderId) {
        DmsFolder folder = findByFolderId(folderId);
        return folder.getDmsFolderType();
    }

    public DmsFolder createUpdateFolder(DmsFolder folder) {
        //validate

        //set
        DmsFolder dmsFolderDb = findByFolderId(folder.getId());
        dmsFolderDb.setDmsFolderOrderId(folder.getDmsFolderOrderId());
        dmsFolderDb.setDmsFolderParentKey(folder.getDmsFolderParentKey());

        dmsFolderDb.setParentKey(folder.getDmsFolderParentKey());
        dmsFolderDb.setFullPathName(folder.getFullPathName());
        dmsFolderDb.setDmsSearchId(folder.getDmsSearchId());
        //query
        return this.update(dmsFolderDb);
    }

    public int copyFolder(DmsFolder folderCopy) {

        folderCopy.setUpdatedBy(folderCopy.getUpdatedBy());
        folderCopy.setCreatedBy(folderCopy.getCreatedBy());
        folderCopy.setUpdatedDate(LocalDateTime.now());

        return this.create(folderCopy).getId();
    }

    public Integer countAll() {
        return this.listAll("", "").size();
    }

    public Integer countAll(int folderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("dmsFolderParentId", folderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<DmsFolder> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        criteria = createOrder1(criteria, sort, dir);

        return this.listByCriteria(criteria, offset, limit);
    }

    public List<DmsFolder> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        criteria = createOrder1(criteria, sort, dir);

        return this.listByCriteria(criteria);
    }

    public List<DmsFolder> findListParentByFolderId(int folder) {
        DmsFolderService dmsFolderHelper = new DmsFolderService();
        String listParent = dmsFolderHelper.getById(folder).getDmsFolderParentKey();
        String[] arrSplit = listParent.split("฿");

        List<DmsFolder> listDmsFolder = new ArrayList<>();

        for (int i = 1; i < arrSplit.length; i++) {
            DmsFolder a = dmsFolderHelper.getById(Integer.parseInt(arrSplit[i]));
            boolean add = listDmsFolder.add(a);
        }
        return listDmsFolder;
    }

    @Override
    public DmsFolder getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public String[] getListChildFolder(int folderId) {
        String folderIdS = Integer.toString(folderId);
        String folderIdSS = "฿" + folderIdS + "฿";
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("dmsFolderParentKey", folderIdSS, MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        List<DmsFolder> lsitFolder = this.listByCriteria(criteria);
        String listId = "";
        for (DmsFolder lsitFolder1 : lsitFolder) {
            int Id = lsitFolder1.getId();
            listId = listId + Id + ",";
        }
        String[] valueFolderParent = listId.split(",");

        return valueFolderParent;
    }

    public List<DmsFolder> getListChildFolderObj(int folderId) {
        String folderIdS = Integer.toString(folderId);
        String folderIdSS = "฿" + folderIdS + "฿";
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("dmsFolderParentKey", folderIdSS, MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        List<DmsFolder> lsitFolder = this.listByCriteria(criteria);
        return lsitFolder;
    }

    public List<DmsFolder> getListChildFolderList(int folderId) {
        String folderIdS = Integer.toString(folderId);
        String folderIdSS = "฿" + folderIdS + "฿";
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("dmsFolderParentKey", folderIdSS, MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        List<DmsFolder> lsitFolder = this.listByCriteria(criteria);
        return lsitFolder;
    }

    public DmsFolder createFolder(DmsFolder folder) {

        if (folder.getDmsFolderType().equalsIgnoreCase("S")) {
            int idfolder = this.create(folder).getId();
            int folderParentId = idfolder;

            folder.setParentId(folderParentId);
            int folderNodeLevel = 1;

            folder.setDmsFolderNodeLevel(folderNodeLevel);
            folder.setNodeLevel(folderNodeLevel);

            String folderParentKey = "฿" + folderParentId + "฿";
            int folderOrderId = 1;

            folder.setDmsFolderOrderId(folderOrderId);
            folder.setDmsFolderParentKey(folderParentKey);
            folder.setParentKey(folderParentKey);

            folder = this.update(folder);
        } else {
            folder = this.create(folder);

        }

        return folder;
    }

    private DetachedCriteria createOrder1(DetachedCriteria criteria, String sort, String dir) {
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

    public DmsFolder getByname(String folderName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("dmsFolderName", folderName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public int duplicateAdocFolder(int parentId, String nameFolder) {

        try {

            Conjunction conjunction = Restrictions.conjunction();

//            Disjunction disjunction = Restrictions.disjunction();
            conjunction.add(Restrictions.eq("dmsFolderParentId", parentId));
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.eq("dmsFolderName", nameFolder));

//            conjunction.add(disjunction);
            DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
            criteria.add(conjunction);

            DmsFolder folder = this.getOneByCriteria(criteria);

            if (folder.getId() > 0) {

                return folder.getId();
            } else {

                return 0;
            }

        } catch (Exception ex) {

//            LOG.error("Exception = " + ex.getMessage())
            return 0;
        }
    }

    public List<DmsFolder> getListFolderByWfDocTypeCode(String wfDocTypeCode) {

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfDocumentTypeCode", wfDocTypeCode));

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);

        return this.listByCriteria(criteria);
    }

    public List<DmsFolder> findListByFolderParentId2(int folderId, int offset, int limit) {
        try {
            Conjunction conjunction = Restrictions.conjunction();
            conjunction.add(Restrictions.eq("removedBy", 0));
            conjunction.add(Restrictions.eq("dmsFolderParentId", folderId));
            DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
            criteria.add(conjunction)
                    .addOrder(Order.asc("dmsFolderType"))
                    .addOrder(Order.asc("dmsFolderOrderId"));
            return this.listByCriteria(criteria, offset, limit);

        } catch (Exception ex) {
            List<DmsFolder> a = new ArrayList<>();
            return a;
        }
    }

    public List<DmsFolder> findListByFolderParentIdLazyback(int folderId, int offset, int limit, AuthEnableDisableIdListModel temp) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("dmsFolderParentId", folderId));

        Disjunction disjunction = Restrictions.disjunction();

        if (temp.getDisableList().length() > 0) {
            String[] listDms = temp.getDisableList().split(",");
            for (String folder : listDms) {
                //mustNot

                disjunction.add(Restrictions.not(Restrictions.like("dmsFolderParentKey", '฿' + folder + '฿', MatchMode.ANYWHERE)));
            }
        }

        if (temp.getEnableList().length() > 0) {
            String[] listDms = temp.getEnableList().split(",");
            for (String folder : listDms) {
                //must

                disjunction.add(Restrictions.like("dmsFolderParentKey", "฿" + folder + "฿", MatchMode.ANYWHERE));
            }
        }

        conjunction.add(disjunction);
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction)
                .addOrder(Order.asc("dmsFolderType"))
                .addOrder(Order.asc("dmsFolderOrderId"));

        if (temp.getDisableList().length() == 0 && temp.getEnableList().length() == 0) {
            List<DmsFolder> a = new ArrayList<>();
            return a;
        } else {
            return this.listByCriteria(criteria, offset, limit);
        }
    }

    public List<DmsFolder> findListByFolderParentIdLazy(int folderId, int offset, int limit, AuthEnableDisableIdListModel temp) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("parentId", folderId));
//        System.out.println("dmsFolderParentId - "+folderId);
        Disjunction disjunction = Restrictions.disjunction();
        Disjunction disjunctionNot = Restrictions.disjunction();
        boolean haveCondition = false;
        if (temp.getDisableList().length() > 0) {

            String[] listDms = temp.getDisableList().split(",");

            for (String folder : listDms) {
                //mustNot

//                System.out.println("mustNot - "+folder);
//                disjunction.add(Restrictions.not(Restrictions.like("parentKey", '฿' + folder + '฿', MatchMode.ANYWHERE)));
                disjunctionNot.add(Restrictions.like("parentKey", '฿' + folder + '฿', MatchMode.ANYWHERE));
            }
            conjunction.add(Restrictions.not(disjunctionNot));
        }

        if (temp.getEnableList().length() > 0) {
            String[] listDms = temp.getEnableList().split(",");

            for (String folder : listDms) {
                //must
//                System.out.println("must - "+folder);
//                disjunction.add(Restrictions.like("parentKey", "฿" + folder + "฿", MatchMode.ANYWHERE));
                disjunction.add(Restrictions.like("parentKey", "฿" + folder + "฿", MatchMode.ANYWHERE));
            }
            conjunction.add(disjunction);
            haveCondition = true;
        }
        if (!haveCondition) {
            conjunction.add(Restrictions.eq("removedBy", -77));
        }
//        conjunction.add(disjunction);
        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction)
                .addOrder(Order.asc("dmsFolderType"))
                .addOrder(Order.asc("dmsFolderOrderId"));
//        System.out.println("aaaaaaaaaaaaaaaaaaaaa");

//            System.out.println("criteria - "+criteria);
        return this.listByCriteria(criteria, offset, limit);
    }

    public int countAll(int folderId, int offset, int limit, AuthEnableDisableIdListModel temp) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("parentId", folderId));

        Disjunction disjunction = Restrictions.disjunction();
        Disjunction disjunctionNot = Restrictions.disjunction();
        boolean haveCondition = false;
        if (temp.getDisableList().length() > 0) {

            String[] listDms = temp.getDisableList().split(",");

            for (String folder : listDms) {
                //mustNot
                disjunctionNot.add(Restrictions.like("parentKey", '฿' + folder + '฿', MatchMode.ANYWHERE));
            }
            conjunction.add(Restrictions.not(disjunctionNot));
        }

        if (temp.getEnableList().length() > 0) {
            String[] listDms = temp.getEnableList().split(",");

            for (String folder : listDms) {
                //must
                disjunction.add(Restrictions.like("parentKey", "฿" + folder + "฿", MatchMode.ANYWHERE));
            }
            conjunction.add(disjunction);
            haveCondition = true;
        }
        if (!haveCondition) {
            conjunction.add(Restrictions.eq("removedBy", -77));
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(DmsFolder.class);
        criteria.add(conjunction);

        return this.countAll(criteria);
    }
}
