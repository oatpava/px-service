package com.px.share.daoimpl; 

import com.px.share.dao.GenericTreeDao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
/**
 * Provide generic common implementation of GenericDao interface persistence methods.
 * Extend this abstract class to implement DAO for your specific needs.
 * 
 * @author Peach
 * @param <T>
 * @param <PK>
 *  
 */
@SuppressWarnings("unchecked")
public class GenericTreeDaoImpl<T, PK extends Serializable> extends GenericDaoImpl<T, PK> implements GenericTreeDao<T, PK>{
    
    private final Class<T> treeEntityClass;

    @SuppressWarnings("unchecked")
    public GenericTreeDaoImpl(Class<T> treeEntityClass) {
        super(treeEntityClass);
        this.treeEntityClass = treeEntityClass;
    }
    
    /**
     *
     * @param parentId
     * @return
     */
    @Override
    public T findParent(PK parentId) {
        return this.getById(parentId);
    }
    
    /**
     *
     * @param entityId
     * @return
     */
    @Override
    public List<T> findChild(PK entityId) {          
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("parentId", entityId));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(treeEntityClass);
        criteria.add(conjunction);
        
        return this.listByCriteria(criteria);
    }
    
    /**
     *
     * @param entityId
     * @param sort
     * @param dir
     * @return
     */
    @Override
    public List<T> findChild(PK entityId,String sort, String dir) {          
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("parentId", entityId));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(treeEntityClass);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        
        return this.listByCriteria(criteria);
    }

    @Override
    public T getByIdNotRemoved(PK id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(treeEntityClass);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
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
