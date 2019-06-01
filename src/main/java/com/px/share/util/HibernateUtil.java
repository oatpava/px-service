package com.px.share.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * 
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    
    static {        
        try {
            SESSION_FACTORY = PxInit.METADATA.buildSessionFactory();
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSESSION_FACTORY() {
        return SESSION_FACTORY;
    }
    
    public static void shutdown() {
        // Close caches and connection pools
        getSESSION_FACTORY().close();
    }

}
