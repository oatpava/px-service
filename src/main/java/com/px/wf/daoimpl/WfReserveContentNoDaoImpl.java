package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.wf.entity.WfReserveContentNo;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import com.px.wf.dao.WfReserveContentNoDao;
import org.hibernate.criterion.Order;
import java.time.LocalDateTime;
import static com.px.share.util.Common.dateThaiToLocalDateTime;

/**
 *
 * @author Mali
 */
public class WfReserveContentNoDaoImpl extends GenericDaoImpl<WfReserveContentNo, Integer> implements WfReserveContentNoDao {

    public WfReserveContentNoDaoImpl() {
        super(WfReserveContentNo.class);
    }

    @Override
    public WfReserveContentNo getByIdNotRemoved(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMaxContentNumber(int folderId, int contentYear) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("reserveContentNoContentYear", contentYear));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, "reserveContentNoContentNumber", "desc");
        List<WfReserveContentNo> reserveContentNo = this.listByCriteria(criteria, 0, 1);
        int maxContentNo = 0;
        if (reserveContentNo.size() > 0) {
            maxContentNo = reserveContentNo.get(0).getReserveContentNoContentNumber() + 1;
        }
        return maxContentNo;
    }

    @Override
    public WfReserveContentNo getContentNoByFolderId(int folderId, String contentNo) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoContentNo", contentNo));
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<WfReserveContentNo> listWfReserveContentNoByContentDate(int folderId, String reserveContentNoContentDateBegin, String reserveContentNoContentDateEnd, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        LocalDateTime dateReserveContentNoContentDateBegin = dateThaiToLocalDateTime(reserveContentNoContentDateBegin);
        dateReserveContentNoContentDateBegin.withHour(0);
        dateReserveContentNoContentDateBegin.withMinute(0);
        dateReserveContentNoContentDateBegin.withSecond(0);
        dateReserveContentNoContentDateBegin.withNano(0);
        conjunction.add(Restrictions.ge("reserveContentNoContentDate", dateReserveContentNoContentDateBegin));

        LocalDateTime dateReserveContentNoContentDateEnd = dateThaiToLocalDateTime(reserveContentNoContentDateEnd);
        dateReserveContentNoContentDateEnd.withHour(23);
        dateReserveContentNoContentDateEnd.withMinute(59);
        dateReserveContentNoContentDateEnd.withSecond(59);
        dateReserveContentNoContentDateEnd.withNano(99);
        conjunction.add(Restrictions.le("reserveContentNoContentDate", dateReserveContentNoContentDateEnd));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    //oat-edit
    public List<WfReserveContentNo> listByDate(int folderId, String dateBegin, String dateEnd, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        if (dateBegin != null && dateBegin != "") {
            dateBegin = dateBegin.replace('x', '/');
            conjunction.add(Restrictions.ge("reserveContentNoContentDate", dateThaiToLocalDateTime(dateBegin)));
        }

        if (dateEnd != null && dateEnd != "") {
            dateEnd = dateEnd.replace('x', '/');
            conjunction.add(Restrictions.le("reserveContentNoContentDate", dateThaiToLocalDateTime(dateEnd).plusHours(23).plusMinutes(59)));
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    //oat-add
    public List<WfReserveContentNo> list(int folderId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    //oat-add
    public List<WfReserveContentNo> listByUser(int folderId, int userId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("createdBy", userId));
        conjunction.add(Restrictions.eq("reserveContentNoUserId", userId));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    //oat-add
    public List<WfReserveContentNo> listCanceled(int folderId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 2));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
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
                    case "reserveContentNoContentNumber":
                        criteria.addOrder(Order.asc("this.reserveContentNoContentNumber"));
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
                    case "reserveContentNoContentNumber":
                        criteria.addOrder(Order.desc("this.reserveContentNoContentNumber"));
                        break;

                }
            }
        } else {
            criteria.addOrder(Order.desc("this.createdDate"));
        }
        return criteria;
    }

    public List<WfReserveContentNo> listByFolderId(int folderId, int year, String contentStartdate, String contentEndDate) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("reserveContentNoContentYear", year));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        if (contentStartdate != null && contentStartdate != "") {
            conjunction.add(Restrictions.ge("this.reserveContentNoContentDate", dateThaiToLocalDateTime(contentStartdate)));
        }
        if (contentEndDate != null && contentEndDate != "") {
            conjunction.add(Restrictions.le("this.reserveContentNoContentDate", dateThaiToLocalDateTime(contentEndDate).plusHours(23).plusMinutes(59)));
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }
    
    public List<WfReserveContentNo> listByStructure(int folderId, int structureId, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("reserveContentNoFolderId", folderId));
        conjunction.add(Restrictions.eq("reserveContentNoStructureId", structureId));
        conjunction.add(Restrictions.eq("reserveContentNoStatus", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfReserveContentNo.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }
}
