package com.px.mwp.daoimpl;

import com.px.mwp.dao.OutboxDao;
import com.px.mwp.entity.Outbox;
import com.px.mwp.entity.Workflow;
import com.px.mwp.model.OutboxSearchModel;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.AdvanceSearch;
import com.px.share.util.Common;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

/**
 *
 * @author Mali
 */
public class OutboxDaoImpl extends GenericDaoImpl<Outbox, Integer> implements OutboxDao {

    private static final Logger LOG = Logger.getLogger(OutboxDaoImpl.class.getName());
    private final String fieldSearch;
    private final String fieldSearchBin;

    public OutboxDaoImpl() {
        super(Outbox.class);
        fieldSearch = "boxFrom,boxTo,boxTitle,boxSendDateBegin,boxSendDateEnd,boxStr01,boxStr02,boxStr03,boxStr04,boxDate01Begin,boxDate01End";
        fieldSearchBin = "boxTitle,removedDateBegin,removedDateEnd";
    }

    public List<Outbox> listByUserProfileFolderId(int userProfileFolderId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
//        conjunction.add(Restrictions.in("userProfileFolderId", ListUserProfileFolderId));
        conjunction.add(Restrictions.eq("userProfileFolderId", userProfileFolderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public Integer countListByUserProfileFolderId(int userProfileFolderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("userProfileFolderId", userProfileFolderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    //@Override
    public List<Outbox> listByStructureFolderId(List<Integer> listStructureFolderId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("structureFolderId", listStructureFolderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<Outbox> listByUserProfileFolderIdAll(List<Integer> listUserProfileFolderId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("userProfileFolderId", listUserProfileFolderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<Outbox> listByStructureFolderIdAll(List<Integer> listStructureFolderId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("structureFolderId", listStructureFolderId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }
//    @Override
//    public List<Outbox> list(int offset, int limit, String sort, String dir) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.eq("removedBy", 0));
//        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
//        criteria.add(conjunction);
//        criteria = Common.createOrder(criteria, sort, dir);
//        return this.listByCriteria(criteria, offset, limit);
//    }

    @Override
    public List<Outbox> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
//        return this.listAll("", "").size();
    }

    @Override
    public List<Outbox> searchOutboxUser(MultivaluedMap<String, String> queryParams, List<Integer> listUserProfileFolderId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchUser(queryParams, listUserProfileFolderId);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Outbox> searchOutboxStructure(MultivaluedMap<String, String> queryParams, List<Integer> listStructureId, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchStructure(queryParams, listStructureId);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Outbox> searchOutboxBin(MultivaluedMap<String, String> queryParams, int userID, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearchBin(queryParams, userID);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public int countSearchOutboxUser(MultivaluedMap<String, String> queryParams, List<Integer> listUserProfileFolderId) {
        Conjunction conjunction = createConjunctionFormSearchUser(queryParams, listUserProfileFolderId);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        return this.countAll(criteria);
        //return this.listByCriteria(criteria).size();
    }

    @Override
    public int countSearchOutboxStructure(MultivaluedMap<String, String> queryParams, List<Integer> listStructureId) {
        Conjunction conjunction = createConjunctionFormSearchStructure(queryParams, listStructureId);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        return this.countAll(criteria);
        //return this.listByCriteria(criteria).size();
    }

    @Override
    public int countSearchOutboxBin(MultivaluedMap<String, String> queryParams, int userID) {
        Conjunction conjunction = createConjunctionFormSearchBin(queryParams, userID);
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);

        criteria.add(conjunction);
        LOG.info("conjunction ===" + conjunction);
        LOG.info("criteria ===" + criteria);

        return this.countAll(criteria);
        //return this.listByCriteria(criteria).size();
    }

    @Override
    public Conjunction createConjunctionFormSearchUser(MultivaluedMap<String, String> queryParams, List<Integer> listUserProfileFolderId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.in("this.userProfileFolderId", listUserProfileFolderId));

        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "boxFrom":
                                conjunction.add(Restrictions.like("this.outboxFrom", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxTo":
                                conjunction.add(Restrictions.like("this.outboxTo", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxTitle":
                                conjunction.add(Restrictions.like("this.outboxTitle", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxSendDateBegin":
                                Date outboxSendDateBegin = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(outboxSendDateBegin);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                outboxSendDateBegin = calendar.getTime();
                                conjunction.add(Restrictions.ge("this.outboxSendDate", outboxSendDateBegin));
                                break;
                            case "boxSendDateEnd":
                                Date outboxSendDateEnd = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(outboxSendDateEnd);
                                calendar1.set(Calendar.HOUR_OF_DAY, 23);
                                calendar1.set(Calendar.MINUTE, 59);
                                calendar1.set(Calendar.SECOND, 59);
                                calendar1.set(Calendar.MILLISECOND, 99);
                                outboxSendDateEnd = calendar1.getTime();
                                conjunction.add(Restrictions.le("this.outboxSendDate", outboxSendDateEnd));
                                break;
                            case "boxStr01":
                                conjunction.add(Restrictions.like("this.outboxStr01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr02":
                                conjunction.add(Restrictions.like("this.outboxStr02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr03":
                                conjunction.add(Restrictions.like("this.outboxStr03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr04":
                                conjunction.add(Restrictions.like("this.outboxStr04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxDate01Begin":
                                Date contentBookDateBegin = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(contentBookDateBegin);
                                calendar2.set(Calendar.HOUR_OF_DAY, 0);
                                calendar2.set(Calendar.MINUTE, 0);
                                calendar2.set(Calendar.SECOND, 0);
                                calendar2.set(Calendar.MILLISECOND, 0);
                                contentBookDateBegin = calendar2.getTime();
                                conjunction.add(Restrictions.ge("this.outboxDate01", contentBookDateBegin));
                                break;
                            case "boxDate01End":
                                Date contentBookDateEnd = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(contentBookDateEnd);
                                calendar3.set(Calendar.HOUR_OF_DAY, 23);
                                calendar3.set(Calendar.MINUTE, 59);
                                calendar3.set(Calendar.SECOND, 59);
                                calendar3.set(Calendar.MILLISECOND, 99);
                                contentBookDateEnd = calendar3.getTime();
                                conjunction.add(Restrictions.le("this.outboxDate01", contentBookDateEnd));
                                break;
                        }
                    }
                }
            }
        }
        return conjunction;
    }

    @Override
    public Conjunction createConjunctionFormSearchStructure(MultivaluedMap<String, String> queryParams, List<Integer> listStructureId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.in("this.structureFolderId", listStructureId));

        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "boxFrom":
                                conjunction.add(Restrictions.like("this.outboxFrom", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxTo":
                                conjunction.add(Restrictions.like("this.outboxTo", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxTitle":
                                conjunction.add(Restrictions.like("this.outboxTitle", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxSendDateBegin":
                                Date outboxSendDateBegin = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(outboxSendDateBegin);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                outboxSendDateBegin = calendar.getTime();
                                conjunction.add(Restrictions.ge("this.outboxSendDate", outboxSendDateBegin));
                                break;
                            case "boxSendDateEnd":
                                Date outboxSendDateEnd = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(outboxSendDateEnd);
                                calendar1.set(Calendar.HOUR_OF_DAY, 23);
                                calendar1.set(Calendar.MINUTE, 59);
                                calendar1.set(Calendar.SECOND, 59);
                                calendar1.set(Calendar.MILLISECOND, 99);
                                outboxSendDateEnd = calendar1.getTime();
                                conjunction.add(Restrictions.le("this.outboxSendDate", outboxSendDateEnd));
                                break;
                            case "boxStr01":
                                conjunction.add(Restrictions.like("this.outboxStr01", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr02":
                                conjunction.add(Restrictions.like("this.outboxStr02", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr03":
                                conjunction.add(Restrictions.like("this.outboxStr03", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxStr04":
                                conjunction.add(Restrictions.like("this.outboxStr04", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "boxDate01Begin":
                                Date contentBookDateBegin = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(contentBookDateBegin);
                                calendar2.set(Calendar.HOUR_OF_DAY, 0);
                                calendar2.set(Calendar.MINUTE, 0);
                                calendar2.set(Calendar.SECOND, 0);
                                calendar2.set(Calendar.MILLISECOND, 0);
                                contentBookDateBegin = calendar2.getTime();
                                conjunction.add(Restrictions.ge("this.outboxDate01", contentBookDateBegin));
                                break;
                            case "boxDate01End":
                                Date contentBookDateEnd = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(contentBookDateEnd);
                                calendar3.set(Calendar.HOUR_OF_DAY, 23);
                                calendar3.set(Calendar.MINUTE, 59);
                                calendar3.set(Calendar.SECOND, 59);
                                calendar3.set(Calendar.MILLISECOND, 99);
                                contentBookDateEnd = calendar3.getTime();
                                conjunction.add(Restrictions.le("this.outboxDate01", contentBookDateEnd));
                                break;
                        }
                    }
                }
            }
        }
        return conjunction;
    }

    @Override
    public Conjunction createConjunctionFormSearchBin(MultivaluedMap<String, String> queryParams, int userID) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", userID));

        for (String key : queryParams.keySet()) {
            if (fieldSearchBin.contains(key)) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "boxTitle":
                                conjunction.add(Restrictions.like("this.outboxTitle", valueArray[i], MatchMode.ANYWHERE));
                                break;
                            case "removedDateBegin":
                                Date removedDate = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(removedDate);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                removedDate = calendar.getTime();
                                conjunction.add(Restrictions.ge("this.removedDate", removedDate));
                                break;
                            case "removedDateEnd":
                                Date removedDate1 = Common.dateThaiToEng(valueArray[i]);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(removedDate1);
                                calendar2.set(Calendar.HOUR_OF_DAY, 23);
                                calendar2.set(Calendar.MINUTE, 59);
                                calendar2.set(Calendar.SECOND, 59);
                                calendar2.set(Calendar.MILLISECOND, 99);
                                removedDate1 = calendar2.getTime();
                                conjunction.add(Restrictions.le("this.removedDate", removedDate1));
                                break;
                        }
                    }
                }
            }
        }
        return conjunction;
    }

    @Override
    public Outbox getByWorkflowId(int workflowId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("workflowId", workflowId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public Outbox getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    //oat-add
    public List<Outbox> listByLinkId(int linkId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("linkId", linkId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<Outbox> searchUserOutboxByModel(int usereFolderId, OutboxSearchModel outboxsearchModel, String sort, String dir) {
        try {
            Conjunction conjunction = createConjunction(usereFolderId, outboxsearchModel);
            DetachedCriteria criteria = DetachedCriteria.forClass(Outbox.class);
            criteria.add(conjunction);
            criteria = createOrder(criteria, sort, dir);
            return this.listByCriteria(criteria);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            return null;
        }
    }

    private Conjunction createConjunction(int UserProfileFolderId, OutboxSearchModel outboxsearchModel) {
        Conjunction conjunction = Restrictions.conjunction();
        String outboxTo = outboxsearchModel.getOutboxTo();
        String outboxFrom = outboxsearchModel.getOutboxFrom();
        String outboxTitle = outboxsearchModel.getOutboxTitle();
        String startDate = outboxsearchModel.getOutboxStartDate();
        String endDate = outboxsearchModel.getOutboxEndDate();
        String outboxNote = outboxsearchModel.getOutboxNote();
        String outboxDescription = outboxsearchModel.getOutboxDescription();
        String outboxStr04 = outboxsearchModel.getOutboxStr04();
        String outboxStr03 = outboxsearchModel.getOutboxStr03();

        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.in("this.userProfileFolderId", UserProfileFolderId));

        if (outboxTo != null && outboxTo != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxTo", outboxTo, null, "&", "|", "!", "^", null));
        }
        if (outboxFrom != null && outboxFrom != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxFrom", outboxFrom, null, "&", "|", "!", "^", null));
        }
        if (outboxTitle != null && outboxTitle != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxTitle", outboxTitle, null, "&", "|", "!", "^", null));
        }
        if (startDate != null && startDate != "") {
            conjunction.add(Restrictions.ge("this.outboxSendDate", dateThaiToLocalDateTime(outboxsearchModel.getOutboxStartDate())));
        }

        if (endDate != null && endDate != "") {
            conjunction.add(Restrictions.le("this.outboxSendDate", dateThaiToLocalDateTime(outboxsearchModel.getOutboxEndDate()).plusHours(23).plusMinutes(59)));
        }
        if (outboxNote != null && outboxNote != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxNote", outboxNote, null, "&", "|", "!", "^", null));
        }
        if (outboxDescription != null && outboxDescription != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxDescription", outboxDescription, null, "&", "|", "!", "^", null));
        }
        if (outboxStr04 != null && outboxStr04 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxStr04", outboxStr04, null, "&", "|", "!", "^", null));
        }
        if (outboxStr03 != null && outboxStr03 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.outboxStr03", outboxStr03, null, "&", "|", "!", "^", null));
        }
        return conjunction;
    }

}
