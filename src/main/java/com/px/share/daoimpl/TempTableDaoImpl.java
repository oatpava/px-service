package com.px.share.daoimpl;

import com.px.share.dao.TempTableDao;
import com.px.share.entity.TempTable;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Mali
 */
public class TempTableDaoImpl extends GenericDaoImpl<TempTable, Integer> implements TempTableDao {

    public TempTableDaoImpl() {
        super(TempTable.class);
    }

    @Override
    public TempTable getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(TempTable.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    @Override
    public List<TempTable> listByComputerNameAndJobType(int userID, String computerName, String jobType, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("createdBy", userID));
        //conjunction.add(Restrictions.eq("computerName", computerName));
        conjunction.add(Restrictions.eq("jobType", jobType));
        DetachedCriteria criteria = DetachedCriteria.forClass(TempTable.class);
        criteria.add(conjunction);
        //criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }
    
    public List<TempTable> listByJobType(int userID, String jobType, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("createdBy", userID));
        conjunction.add(Restrictions.eq("jobType", jobType));
        DetachedCriteria criteria = DetachedCriteria.forClass(TempTable.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
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

}
