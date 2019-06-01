package com.px.share.daoimpl;

import com.google.common.primitives.Ints;
import com.px.share.dao.GenericDao;
import com.px.share.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;

/**
 * Provide generic common implementation of GenericDao interface persistence methods.
 * Extend this abstract class to implement DAO for your specific needs.
 * 
 * @author OPAS
 * @param <T>
 * @param <PK>
 *  
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    private static final Logger LOG = Logger.getLogger(GenericDaoImpl.class.getName());
    
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }        
        
    private Session getCurrentSession(){
        return HibernateUtil.getSESSION_FACTORY().getCurrentSession();
    }
    
    /**
     *
     * @param entity
     * @return
     */
    @Override
    public T create(T entity) {          
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }            
            getCurrentSession().save(entity);
            transaction.commit();
            
        }catch(HibernateException e) {  
            entity = null;
            if (transaction!=null) {
                transaction.rollback();
            }
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return entity;
    }
        
    /**
     *
     * @param entityId
     * @return
     */
    @Override
    public T getById(PK entityId) {
        T entity = null;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            entity = (T) getCurrentSession().get(entityClass, entityId);
            getCurrentSession().flush();
            transaction.commit();       
        }catch (ObjectNotFoundException e) {
            LOG.error("ObjectNotFoundException = "+e);
            entity = null;
        }finally{
            transaction = null;
        }
        return entity;
    }
        
    /**
     *
     * @param criteria
     * @return
     */
    @Override
    public T getOneByCriteria(DetachedCriteria criteria) {
        T entity = null;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession());
            entity = (T) cr.uniqueResult();
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return entity;
    }

    /**
     *
     * @param entity
     * @return
     */
    @Override
    public T update(T entity) {
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            getCurrentSession().merge(entity);
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            entity = null;
            if (transaction!=null) {
                transaction.rollback();
            }
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return entity;
    }

    /**
     *
     * @param entity
     */
    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            getCurrentSession().delete( entity );
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction!=null) {
                transaction.rollback();
            }
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
    }
    
    /**
     *
     * @param criteria
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listByCriteria(DetachedCriteria criteria) {
        List<T> listEntity = null;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession());
            listEntity = cr.list();
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction!=null) {
                transaction.rollback();
            }
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return listEntity;
    }

    /**
     *
     * @param criteria
     * @param offset
     * @param limit
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listByCriteria(DetachedCriteria criteria, int offset, int limit) {
        List<T> listEntity = null;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession());
            cr.setFirstResult(offset);
            cr.setMaxResults(limit);
            listEntity = cr.list();
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return listEntity;
    }

    /**
     *
     * @param criteria
     * @return
     */
    @Override
    public Integer countAll(DetachedCriteria criteria) {
        long count = 0;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession());            
            count = ((Number)cr.setProjection(Projections.rowCount()).uniqueResult()).longValue();
            transaction.commit();
        }catch (HibernateException e) {
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return Ints.checkedCast(count);
    }
    
    public Integer sum(DetachedCriteria criteria){
        long sum = 0;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession());            
            sum = ((Number)cr.uniqueResult()).longValue();
            transaction.commit();
        }catch (HibernateException e) {
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return Ints.checkedCast(sum);
    }

    protected List<HashMap> getColumnName(T entity) throws Exception {
        List<HashMap> list = new ArrayList<HashMap>();
        try {
            AbstractEntityPersister abstractEntity = ((AbstractEntityPersister) HibernateUtil.getSESSION_FACTORY().getClassMetadata(entity.getClass()));
            String[] properties = abstractEntity.getPropertyNames();
            Type[] types = abstractEntity.getPropertyTypes();
            String[] test = abstractEntity.getIdentifierColumnNames();
            String column_name = "";
            String type_name = "";
            int column_length = 0;
            for (int nameIndex = 0; nameIndex != properties.length; nameIndex++) {
                String[] columns = abstractEntity.getPropertyColumnNames(nameIndex);
                column_name = columns[0];
                type_name = types[nameIndex].getName();
                HashMap dataHash = new HashMap();
                try {
                    // ชื่อเดียวกับ Data Base
//                    LOG.info("Name  : " + abstractEntity.getMappedClass().getDeclaredField(properties[nameIndex]).getAnnotation(Column.class).name());  
                    // ดึงค่า length จาก entity มา 
//                    LOG.info("Size  : " + abstractEntity.getMappedClass().getDeclaredField(properties[nameIndex]).getAnnotation(Column.class).length()); 
                    if ((type_name).equalsIgnoreCase("String")) {
                        type_name = "text";
                    } else if ((type_name).equalsIgnoreCase("integer")) {
                        type_name = "int";
                    } else if ((type_name).equalsIgnoreCase("timestamp")) {
                        type_name = "datetime";
                    } else if ((type_name).equalsIgnoreCase("float") ||  (type_name).equalsIgnoreCase("double") ) {
                        type_name = "decimal";
                    } 
                    try {
                        column_length = abstractEntity.getMappedClass().getDeclaredField(properties[nameIndex]).getAnnotation(Column.class).length();
                    } catch (NoSuchFieldException | SecurityException e) {
                        column_length = 0;
                        LOG.error("Exception get length model null : " + e);
                    }
                    dataHash.put("properties", properties[nameIndex]);
                    dataHash.put("column", column_name);
                    dataHash.put("type", type_name);
                    dataHash.put("length", String.valueOf(column_length));
                    list.add(dataHash);

                } catch (SecurityException e) {
                    LOG.error("Error put data to map : " + e);
                }

            }
        } catch (HibernateException e) {
            LOG.error("HibernateException getColumnName = " + e);
        } finally {
            
        }
        return list;
    }
    
    public Integer max(DetachedCriteria criteria,String fieldName){
        Integer max = 0;
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Criteria cr = criteria.getExecutableCriteria(getCurrentSession()); 
            max = (Integer)(cr.setProjection(Projections.max(fieldName))).uniqueResult();
            transaction.commit();
        }catch (HibernateException e) {
            LOG.error("HibernateException = "+e);
            throw new NullPointerException("max null value");
        }finally{
            if (transaction!=null) {
                transaction.rollback();
            }
        }
        return Ints.checkedCast(max);
    }
    
    /**
     *
     * @param entityName
     * @param id
     * @return
     */
    public int restoreEntity(String entityName,int id) {
        Transaction transaction = null;
        try {
            if (getCurrentSession().getTransaction() != null && getCurrentSession().getTransaction().isActive()) {
                transaction = getCurrentSession().getTransaction();
            } else {
                transaction = getCurrentSession().beginTransaction();
            }
            Query query = getCurrentSession().createQuery("update "+entityName+" set removedBy = 0 where id = :id");
            query.setParameter("id", id);
            int result = query.executeUpdate();
            getCurrentSession().flush();
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction!=null) {
                transaction.rollback();
            }
            LOG.error("HibernateException = "+e);
        }finally{
            transaction = null;
        }
        return id;
    }
    
   
}
