package com.px.share.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Generic interface for Data Access Objects. To be extended or implanted.
 * Contains common persistence methods.
 * 
 * @author OPAS
 * @param <T>
 * @param <PK>
 * 
 *  
 */
public interface GenericDao<T, PK extends Serializable> {
    T create(T t);
    T getById(PK id);
    T getByIdNotRemoved(PK id);
    T getOneByCriteria(DetachedCriteria criteria);
    T update( T entity );
    void delete( T entity );
    List<T> listByCriteria(DetachedCriteria criteria);
    List<T> listByCriteria(DetachedCriteria criteria,int firstResult,int maxResult);
    Integer countAll(DetachedCriteria criteria);
}
