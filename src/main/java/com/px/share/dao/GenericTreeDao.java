package com.px.share.dao; 

import java.io.Serializable; 
import java.util.List;

/**
 *
 * @author Peach
 * @param <T>
 * @param <PK>
 */
public interface GenericTreeDao<T, PK extends Serializable> extends GenericDao<T, PK>{
    T findParent(PK id);
    List<T>  findChild(PK id);
    List<T>  findChild(PK id,String sort, String dir);
}
