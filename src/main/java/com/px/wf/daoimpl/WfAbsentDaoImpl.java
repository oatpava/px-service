package com.px.wf.daoimpl;

import com.px.share.daoimpl.GenericDaoImpl;
import com.px.share.util.Common;
import com.px.wf.dao.WfAbsentDao;
import com.px.wf.entity.WfAbsent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author CHALERMPOL
 */
public class WfAbsentDaoImpl extends GenericDaoImpl<WfAbsent, Integer> implements WfAbsentDao{

    public WfAbsentDaoImpl() {
        super(WfAbsent.class);
    }

    @Override
    public List<WfAbsent> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<WfAbsent> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }
    
    public List<WfAbsent> listByCurrentYear(int offset, int limit, String sort, String dir) {
        Calendar calendarStart = Calendar.getInstance(TimeZone.getDefault());
        int year = calendarStart.get(Calendar.YEAR);
        calendarStart.set(Calendar.YEAR,year);
        calendarStart.set(Calendar.MONTH,Calendar.JANUARY);
        calendarStart.set(Calendar.DAY_OF_MONTH,1);
        
        Calendar calendarEnd = Calendar.getInstance(TimeZone.getDefault());
        calendarEnd.set(Calendar.YEAR,year);
        calendarEnd.set(Calendar.MONTH,Calendar.DECEMBER);
        calendarEnd.set(Calendar.DAY_OF_MONTH,31);
        
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.between("absentDate", LocalDateTime.ofInstant(calendarStart.toInstant(),ZoneId.systemDefault()),LocalDateTime.ofInstant(calendarEnd.toInstant(),ZoneId.systemDefault())));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }
    
    public List<WfAbsent> listByDateRange(LocalDateTime startDate,LocalDateTime endDate,int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.between("absentDate", startDate,endDate));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }
    
    @Override
    public WfAbsent getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public List<WfAbsent> checkDateInWfAbsent(Date dateIn){
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.between("absentDate", dateIn.getTime(),dateIn.getTime()));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }
    
    public List<WfAbsent> checkDateInWfAbsent(Date dateBegin,Date dateEnd){
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.between("absentDate", dateBegin.getTime(),dateEnd.getTime()));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        return this.listByCriteria(criteria);
    }
    
    public int countWorkday(String beginDate,String endDate){
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        try{
            dateBegin = Common.dateThaiToEng(beginDate);
            dateEnd = Common.dateThaiToEng(endDate);
        }catch(Exception ee){}
        conjunction.add(Restrictions.between("absentDate", dateBegin,dateEnd));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        int numholiday = this.listByCriteria(criteria).size();
        Date tomorrow = dateBegin;
        int numLeave = 0;
        Calendar c = Calendar.getInstance();
        while(tomorrow.compareTo(dateEnd)<=0){
            c.setTime(tomorrow);
            if((c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) numLeave++;
            c.add(Calendar.DATE, 1);
            tomorrow = c.getTime();
        }        
        numLeave-=numholiday;
        if(numLeave<0) numLeave=0;
        return numLeave;
    }    
    
    public static DetachedCriteria createOrder(DetachedCriteria criteria,String sort,String dir){
        if(!sort.isEmpty()){
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")){
                criteria.addOrder(Order.asc(sort));
            }
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")){
                criteria.addOrder(Order.desc(sort));
            }            
        }else{
            criteria.addOrder(Order.desc("createdDate")); 
        }
        return criteria;
    }    
            
    public WfAbsent checkIfExist(int userId, LocalDateTime absentDate) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("absentDate", absentDate));
        conjunction.add(Restrictions.eq("userId", userId));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfAbsent.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
}
