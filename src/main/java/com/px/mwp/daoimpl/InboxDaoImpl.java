package com.px.mwp.daoimpl;

import com.px.mwp.dao.InboxDao;
import com.px.mwp.entity.Inbox;
import com.px.mwp.model.InboxSearchModel;
import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.AdvanceSearch;
import static com.px.share.util.Common.dateThaiToLocalDateTime;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

/**
 *
 * @author Oat
 */
public class InboxDaoImpl extends GenericDaoImpl<Inbox, Integer> implements InboxDao {

    private static final Logger LOG = Logger.getLogger(InboxDaoImpl.class.getName());

    public InboxDaoImpl() {
        super(Inbox.class);
    }

    @Override
    public List<Inbox> listByStructureFolderId(int structureFolderId, int offset, int limit, String sort, String dir, int status) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureFolderId", structureFolderId));
        switch (status) {
            case 1:
                conjunction.add(Restrictions.eq("inboxReceiveFlag", 1));
                break;
            case 2:
                conjunction.add(Restrictions.eq("inboxOpenFlag", 1));
                break;
            case 3:
                conjunction.add(Restrictions.eq("inboxActionFlag", 1));
                break;
            case 4:
                conjunction.add(Restrictions.eq("inboxFinishFlag", 1));
                break;
            default:
                break;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Inbox> listByUserProfileFolderId(int userProfileFolderId, int offset, int limit, String sort, String dir, int status) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("userProfileFolderId", userProfileFolderId));
        switch (status) {
            case 1:
                conjunction.add(Restrictions.eq("inboxReceiveFlag", 1));
                break;
            case 2:
                conjunction.add(Restrictions.eq("inboxOpenFlag", 1));
                break;
            case 3:
                conjunction.add(Restrictions.eq("inboxActionFlag", 1));
                break;
            case 4:
                conjunction.add(Restrictions.eq("inboxFinishFlag", 1));
                break;
            default:
                break;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public int countListByStructureFolderId(int structureFolderId, int status) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("structureFolderId", structureFolderId));
        switch (status) {
            case 1:
                conjunction.add(Restrictions.eq("inboxReceiveFlag", 1));
                break;
            case 2:
                conjunction.add(Restrictions.eq("inboxOpenFlag", 1));
                break;
            case 3:
                conjunction.add(Restrictions.eq("inboxActionFlag", 1));
                break;
            case 4:
                conjunction.add(Restrictions.eq("inboxFinishFlag", 1));
                break;
            default:
                break;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public int countListByUserProfileFolderId(int userProfileFolderId, int status) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.in("userProfileFolderId", userProfileFolderId));
        switch (status) {
            case 1:
                conjunction.add(Restrictions.eq("inboxReceiveFlag", 1));
                break;
            case 2:
                conjunction.add(Restrictions.eq("inboxOpenFlag", 1));
                break;
            case 3:
                conjunction.add(Restrictions.eq("inboxActionFlag", 1));
                break;
            case 4:
                conjunction.add(Restrictions.eq("inboxFinishFlag", 1));
                break;
            default:
                break;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public List<Inbox> listByWorkflowId(int workflowId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("workflowId", workflowId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }

    @Override
    public Inbox getByIdNotRemoved(Integer id
    ) {
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

    public List<Inbox> searchInboxByModel(String search, int folderId, InboxSearchModel inboxsearchModel, String sort, String dir) {
        try {
            Conjunction conjunction = createConjunction(search, folderId, inboxsearchModel);
            DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
            criteria.add(conjunction);
            criteria = createOrder(criteria, sort, dir);
            return this.listByCriteria(criteria);
        } catch (Exception ex) {
            LOG.error("Exception = " + ex.getMessage());
            return null;
        }
    }

    private Conjunction createConjunction(String search, int folderId, InboxSearchModel inboxsearchModel) {
        Conjunction conjunction = Restrictions.conjunction();
        String inboxFrom = inboxsearchModel.getInboxFrom();
        String inboxTitle = inboxsearchModel.getInboxTitle();
        String startDate = inboxsearchModel.getInboxStartDate();
        String endDate = inboxsearchModel.getInboxEndDate();
        String inboxNote = inboxsearchModel.getInboxNote();
        String inboxDescription = inboxsearchModel.getInboxDescription();
        String inboxStr04 = inboxsearchModel.getInboxStr04();
        String inboxStr03 = inboxsearchModel.getInboxStr03();

        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(Restrictions.in(search, folderId));

        if (inboxFrom != null && inboxFrom != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxFrom", inboxFrom, null, "&", "|", "!", "^", null));
        }
        if (inboxTitle != null && inboxTitle != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxTitle", inboxTitle, null, "&", "|", "!", "^", null));
        }
        if (startDate != null && startDate != "") {
            conjunction.add(Restrictions.ge("this.inboxSendDate", dateThaiToLocalDateTime(inboxsearchModel.getInboxStartDate())));
        }

        if (endDate != null && endDate != "") {
            conjunction.add(Restrictions.le("this.inboxSendDate", dateThaiToLocalDateTime(inboxsearchModel.getInboxEndDate()).plusHours(23).plusMinutes(59)));
        }
        if (inboxNote != null && inboxNote != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxNote", inboxNote, null, "&", "|", "!", "^", null));

        }
        if (inboxDescription != null && inboxDescription != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxDescription", inboxDescription, null, "&", "|", "!", "^", null));

        }
        if (inboxStr04 != null && inboxStr04 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxStr04", inboxStr04, null, "&", "|", "!", "^", null));
        }
        if (inboxStr03 != null && inboxStr03 != "") {
            conjunction.add(new AdvanceSearch().advanceSearchTextQuery("this.inboxStr03", inboxStr03, null, "&", "|", "!", "^", null));
        }
        return conjunction;
    }

    public List<Inbox> listAllByWorkflowId(int workflowId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("workflowId", workflowId));
        DetachedCriteria criteria = DetachedCriteria.forClass(Inbox.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

}
