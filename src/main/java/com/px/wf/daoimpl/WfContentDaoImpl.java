package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.service.ParamService;
import com.px.share.util.AdvanceSearch;
import com.px.wf.dao.WfContentDao;
import com.px.wf.entity.WfContent;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import com.px.wf.model.WfContentESModel;
import com.px.wf.model.WfContentSearchModel;
import com.px.wf.service.WfSearchService;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Mali
 */
public class WfContentDaoImpl extends GenericDaoImpl<WfContent, Integer> implements WfContentDao {

    private static final Logger LOG = Logger.getLogger(WfContentDaoImpl.class.getName());

    public WfContentDaoImpl() {
        super(WfContent.class);
    }

    @Override
    public List<WfContent> listByFolderId(int offset, int limit, String sort, String dir, int folderid, int wfContentYear) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<WfContent> listByFolderId(int offset, int limit, int folderid, int wfContentYear) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria.addOrder(Order.desc("wfContentContentYear"));
        criteria.addOrder(Order.desc("wfContentContentNumber"));
        criteria.addOrder(Order.desc("wfContentContentPoint"));
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WfContent> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countAllListByFolderId(int folderId, int wfContentYear) {
        return this.countListByFolderId(folderId, wfContentYear);
    }

    @Override
    public int getMaxContentNo(String wfFolderPreContentNo, int folderId, int wfFolderContentYear) {
        int maxContentNo = 1;
        Conjunction conjunction = Restrictions.conjunction();
        if (wfFolderPreContentNo != null) {
            conjunction.add(Restrictions.eq("wfContentContentPre", wfFolderPreContentNo));
        }
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfFolderContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        maxContentNo = this.max(criteria, "wfContentContentNumber");

        maxContentNo = maxContentNo + 1;
        return maxContentNo;
    }

    @Override
    public int getMaxBookNo(String wfFolderPreBookNo, int folderId, int wfFolderContentYear) {
        int maxBookNo = 1;
        Conjunction conjunction = Restrictions.conjunction();
        if (wfFolderPreBookNo != null) {
            conjunction.add(Restrictions.eq("wfContentBookPre", wfFolderPreBookNo));
        }
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfFolderContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        maxBookNo = this.max(criteria, "wfContentBookNumber");
        if (maxBookNo >= 0) {
            maxBookNo = maxBookNo + 1;
        }

        return maxBookNo;
    }

    @Override
    public WfContent getByContentNo(String wfContentContentNo, int folderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wfContentContentNo", wfContentContentNo));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public WfContent getByBookNo(String wfContentBookNo, int folderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wfContentBookNo", wfContentBookNo));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public WfContent getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<WfContent> listByFolderId(int folderid, int wfContentYear) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    public Integer countListByFolderId(int folderid, int wfContentYear) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfContentYear));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    //oat
    public List<WfContent> listByFolderIdDateRange(int folderid, String start, String end, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        //conjunction.add(Restrictions.eq("wfContentContentYear", wfContentYear));
        conjunction.add(Restrictions.ge("createdDate", dateThaiToLocalDateTime(start)));
        conjunction.add(Restrictions.le("createdDate", dateThaiToLocalDateTime(end).plusHours(23).plusMinutes(59)));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
            criteria.addOrder(Order.asc("wfContentContentYear"));
            criteria.addOrder(Order.asc("wfContentContentNumber"));
            criteria.addOrder(Order.asc("wfContentContentPoint"));
        } else {
            criteria.addOrder(Order.desc("wfContentContentYear"));
            criteria.addOrder(Order.desc("wfContentContentNumber"));
            criteria.addOrder(Order.desc("wfContentContentPoint"));
        }
        return this.listByCriteria(criteria);
    }

    //oat
    public List<WfContent> listByFolderIdDateRangeUser(int folderid, String start, String end, int userId, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderid));
        conjunction.add(Restrictions.eq("createdBy", userId));
        conjunction.add(Restrictions.ge("createdDate", dateThaiToLocalDateTime(start)));
        conjunction.add(Restrictions.le("createdDate", dateThaiToLocalDateTime(end).plusHours(23).plusMinutes(59)));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
            criteria.addOrder(Order.asc("wfContentContentYear"));
            criteria.addOrder(Order.asc("wfContentContentNumber"));
            criteria.addOrder(Order.asc("wfContentContentPoint"));
        } else {
            criteria.addOrder(Order.desc("wfContentContentYear"));
            criteria.addOrder(Order.desc("wfContentContentNumber"));
            criteria.addOrder(Order.desc("wfContentContentPoint"));
        }
        return this.listByCriteria(criteria);
    }

    public boolean checkWfContentInWfFolder(int folderId) {
        //create AND 
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));

        //create Query
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);

        List<WfContent> wfContentlist = this.listByCriteria(criteria);
        int getContentId = 0;
        if (wfContentlist.size() != 0) {
            WfContent wfContent = wfContentlist.get(0);
            if (wfContent != null) {
                getContentId = wfContent.getId();
            }
        }
        //LOG.info("getContentId==" + getContentId);
        return getContentId > 0;
    }

    @Override
    public int getMaxContentPoint(int folderId, int wfFolderContentYear, int contentNumber) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentContentYear", wfFolderContentYear));
        conjunction.add(Restrictions.eq("wfContentContentNumber", contentNumber));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, "wfContentContentPoint", "desc");
        List<WfContent> wfContent = this.listByCriteria(criteria, 0, 1);
        int maxContentPoint = 1;
        if (wfContent.size() > 0) {
            maxContentPoint = wfContent.get(0).getWfContentContentPoint() + 1;
        } else {
            maxContentPoint = -1;
        }
        return maxContentPoint;
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
                    case "wfContentContentYear":
                        criteria.addOrder(Order.asc("this.wfContentContentYear"));
                        break;
                    case "wfContentContentNo":
                        criteria.addOrder(Order.asc("this.wfContentContentNo"));
                        break;
                    case "wfContentBookNo":
                        criteria.addOrder(Order.asc("this.wfContentBookNo"));
                        break;
                    case "wfContentBookDate":
                        criteria.addOrder(Order.asc("this.wfContentBookDate"));
                        break;
                    case "wfContentFrom":
                        criteria.addOrder(Order.asc("this.wfContentFrom"));
                        break;
                    case "wfContentTo":
                        criteria.addOrder(Order.asc("this.wfContentTo"));
                        break;
                    case "wfContentTitle":
                        criteria.addOrder(Order.asc("this.wfContentTitle"));
                        break;
                    case "wfContentReference":
                        criteria.addOrder(Order.asc("this.wfContentReference"));
                        break;
                    case "wfContentOwnername":
                        criteria.addOrder(Order.asc("this.wfContentOwnername"));
                        break;
                    case "wfContentContentDate":
                        criteria.addOrder(Order.asc("this.wfContentContentDate"));
                        break;
                    case "wfContentDescription":
                        criteria.addOrder(Order.asc("this.wfContentDescription"));
                        break;
                    case "wfContentContentPoint":
                        criteria.addOrder(Order.asc("this.wfContentContentPoint"));
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
                    case "wfContentContentYear":
                        criteria.addOrder(Order.desc("this.wfContentContentYear"));
                        break;
                    case "wfContentContentNo":
                        criteria.addOrder(Order.desc("this.wfContentContentNo"));
                        break;
                    case "wfContentBookNo":
                        criteria.addOrder(Order.desc("this.wfContentBookNo"));
                        break;
                    case "wfContentBookDate":
                        criteria.addOrder(Order.desc("this.wfContentBookDate"));
                        break;
                    case "wfContentFrom":
                        criteria.addOrder(Order.desc("this.wfContentFrom"));
                        break;
                    case "wfContentTo":
                        criteria.addOrder(Order.desc("this.wfContentTo"));
                        break;
                    case "wfContentTitle":
                        criteria.addOrder(Order.desc("this.wfContentTitle"));
                        break;
                    case "wfContentReference":
                        criteria.addOrder(Order.desc("this.wfContentReference"));
                        break;
                    case "wfContentOwnername":
                        criteria.addOrder(Order.desc("this.wfContentOwnername"));
                        break;
                    case "wfContentContentDate":
                        criteria.addOrder(Order.desc("this.wfContentContentDate"));
                        break;
                    case "wfContentDescription":
                        criteria.addOrder(Order.desc("this.wfContentDescription"));
                        break;
                    case "wfContentContentPoint":
                        criteria.addOrder(Order.desc("this.wfContentContentPoint"));
                        break;
                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    //oat-add
    public List<WfContent> searchByModel(int folderId, WfContentSearchModel wfContentSearchModel, String sort, String dir) {
        try {
            String contentNo = wfContentSearchModel.getWfContentContentNo();
            List<Integer> listContentNumber = new ArrayList();
//            if (contentNo.contains(",")) {
//                wfContentSearchModel.setWfContentContentNo("");
//                //wfContentSearchModel.setWfContentStartContentNo(0);
//                //wfContentSearchModel.setWfContentEndContentNo(0);
//                String[] listContentNo = contentNo.split(",");
//                for (int i = 0; i < listContentNo.length; i++) {
//                    listContentNumber.add(Integer.parseInt(listContentNo[i].replaceAll("[^0-9]", "")));
//                }
//            }
            Conjunction conjunction = createConjunction(folderId, wfContentSearchModel, listContentNumber);
            DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
            criteria.add(conjunction);
            if ((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc("wfContentContentYear"));
                criteria.addOrder(Order.asc("wfContentContentNumber"));
                criteria.addOrder(Order.asc("wfContentContentPoint"));
            } else {
                criteria.addOrder(Order.desc("wfContentContentYear"));
                criteria.addOrder(Order.desc("wfContentContentNumber"));
                criteria.addOrder(Order.desc("wfContentContentPoint"));
            }
            //criteria = createOrder(criteria, sort, dir);
            List<WfContent> listResult = this.listByCriteria(criteria);
//GHB no ES
//            if (!listResult.isEmpty()) {
//                if (wfContentSearchModel.getFileAttachText() != null && wfContentSearchModel.getFileAttachText() != "") {
//                    List<WfContent> listAfterES = new ArrayList<>();
//                    List<WfContentESModel> listESModel = new WfSearchService().search(wfContentSearchModel.getWfContentFolderId(), wfContentSearchModel.getFileAttachText());
//                    if (!listESModel.isEmpty()) {
//                        for (WfContent content : listResult) {
//                            for (WfContentESModel ESModel : listESModel) {
//                                if (content.getWfDocumentId() == ESModel.getLinkId()) {
//                                    listAfterES.add(content);
//                                    break;
//                                }
//                            }
//                        }
//                        //listAfterES.trimToSize();
//                        listResult = listAfterES;
//                    }
//                }
//            }
            return listResult;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("Exception = " + ex.getMessage());
            return null;
        }
    }

    private Conjunction createConjunction(int folderId, WfContentSearchModel wfContentSearchModel, List contentNumber) {
        ParamService paramService = new ParamService();
        String symbolAnd = paramService.getByParamName("ANDTXT").getParamValue();
        String symbolOr = paramService.getByParamName("ORTXT").getParamValue();
        String symbolNot = paramService.getByParamName("NOTTXT").getParamValue();

        Conjunction conjunction = Restrictions.conjunction();
        int wfContentContentYear = wfContentSearchModel.getWfContentContentYear();
        String wfContentContentNo = wfContentSearchModel.getWfContentContentNo();
        int startContentNo = wfContentSearchModel.getWfContentStartContentNo();
        int endContentNo = wfContentSearchModel.getWfContentEndContentNo();
        String contentStartdate = wfContentSearchModel.getWfContentContentStartDate();
        String contentEndDate = wfContentSearchModel.getWfContentContentEndDate();
        String wfContentBookNo = wfContentSearchModel.getWfContentBookNo();
        String bookStartDate = wfContentSearchModel.getWfContentBookStartDate();
        String bookEndDate = wfContentSearchModel.getWfContentBookEndDate();
        String wfContentFrom = wfContentSearchModel.getWfContentFrom();
        String wfContentTo = wfContentSearchModel.getWfContentTo();
        String wfContentTitle = wfContentSearchModel.getWfContentTitle();
        String userName = wfContentSearchModel.getUserName();
        String wfContentDescription = wfContentSearchModel.getWfContentDescription();
        String wfContentStr03 = wfContentSearchModel.getWfContentStr03();
        //int contentTimeRange = wfContentSearchModel.getContentTimeRange();
        String fullText = wfContentSearchModel.getFileAttachText();
        int sendingStatus = wfContentSearchModel.getSendingStatus();
        String wfContentText01 = wfContentSearchModel.getWfContentText01();

        conjunction.add(Restrictions.eq("this.removedBy", 0));
        if (folderId > 0) {
            conjunction.add(Restrictions.eq("this.wfContentFolderId", folderId));
        }

        if (wfContentContentYear != 0) {
            conjunction.add(Restrictions.eq("this.wfContentContentYear", wfContentContentYear));
        }
//        if (wfContentContentNo != null && wfContentContentNo != "") {
//            conjunction.add(Restrictions.like("this.wfContentContentNo", wfContentContentNo, MatchMode.ANYWHERE));
//        } else if (contentNumber.size() > 0) {
//            conjunction.add(Restrictions.in("this.wfContentContentNumber", contentNumber));
//        }
        if (wfContentContentNo != null && wfContentContentNo != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentContentNo", wfContentContentNo, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (startContentNo > 0) {
            conjunction.add(Restrictions.ge("this.wfContentContentNumber", startContentNo));
        }
        if (endContentNo > 0) {
            conjunction.add(Restrictions.le("this.wfContentContentNumber", endContentNo));
        }
        if (contentStartdate != null && contentStartdate != "") {
            conjunction.add(Restrictions.ge("this.wfContentContentDate", dateThaiToLocalDateTime(wfContentSearchModel.getWfContentContentStartDate())));
        }
        if (contentEndDate != null && contentEndDate != "") {
            conjunction.add(Restrictions.le("this.wfContentContentDate", dateThaiToLocalDateTime(wfContentSearchModel.getWfContentContentEndDate()).plusHours(23).plusMinutes(59)));
        }
        if (wfContentBookNo != null && wfContentBookNo != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentBookNo", wfContentBookNo, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (bookStartDate != null && bookStartDate != "") {
            conjunction.add(Restrictions.ge("this.wfContentBookDate", dateThaiToLocalDateTime(wfContentSearchModel.getWfContentBookStartDate())));
        }
        if (bookEndDate != null && bookEndDate != "") {
            conjunction.add(Restrictions.le("this.wfContentBookDate", dateThaiToLocalDateTime(wfContentSearchModel.getWfContentBookEndDate()).plusHours(23).plusMinutes(59)));
        }
        if (wfContentFrom != null && wfContentFrom != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentFrom", wfContentFrom, null, symbolAnd, symbolOr, symbolNot, "^", null));

        }
        if (wfContentTo != null && wfContentTo != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentTo", wfContentTo, null, symbolAnd, symbolOr, symbolNot, "^", null));

        }
        if (wfContentTitle != null && wfContentTitle != "") {
//            conjunction.add(Restrictions.like("this.wfContentTitle", wfContentTitle, MatchMode.ANYWHERE));           
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentTitle", wfContentTitle, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (userName != null && userName != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentOwnername", userName, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (wfContentDescription != null && wfContentDescription != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentDescription", wfContentDescription, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (wfContentStr03 != null && wfContentStr03 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentStr03", wfContentStr03, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
//        if (contentTimeRange > 0) {
//            switch (contentTimeRange) {
//                case 1: 
//            }
//        }
        if (fullText != null && fullText != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.fullText", fullText, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        if (sendingStatus > 0) {
            conjunction.add(Restrictions.eq("this.wfContentInt03", sendingStatus - 1));//0=unSend, 1=sent
        }
        if (wfContentText01 != null && wfContentText01 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.wfContentText01", wfContentText01, null, symbolAnd, symbolOr, symbolNot, "^", null));
        }
        return conjunction;
    }

    public int countContentTitle(String contentTitle, int folderId, int year) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("wfContentContentYear", year));
        conjunction.add(Restrictions.eq("wfContentTitle", contentTitle));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
//        return this.getOneByCriteria(criteria);
//        return this.listByCriteria(criteria, 0, 1);
        return this.countAll(criteria);
    }

    public WfContent getByContentNumber(int contentNumber, int folderId, int year) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("wfContentContentNumber", contentNumber));
        conjunction.add(Restrictions.eq("wfContentContentYear", year));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        WfContent tmp = new WfContent();
        List<WfContent> listTmp = this.listByCriteria(criteria, 0, 1);
        if (!listTmp.isEmpty()) {
            tmp = listTmp.get(0);
        }
        return tmp;
    }

    public List<WfContent> listMyWork(int userId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("createdBy", userId));
        conjunction.add(Restrictions.eq("wfContentFolderId", -1));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria.addOrder(Order.desc("orderNo"));
        return this.listByCriteria(criteria);
    }

    public int countBookNo(String bookNo, int folderId, int year) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("wfContentContentYear", year));
        conjunction.add(Restrictions.eq("wfContentBookNo", bookNo));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    public List<WfContent> listByBookNo(String bookNo, int folderId, int year) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfContentFolderId", folderId));
        conjunction.add(Restrictions.eq("wfContentContentYear", year));
        conjunction.add(Restrictions.eq("wfContentBookNo", bookNo));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        criteria.addOrder(Order.desc("orderNo"));
        return this.listByCriteria(criteria, 0, 1);
    }

    public List<WfContent> listByDocumentId(int documentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("wfDocumentId", documentId));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfContent.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }
}
