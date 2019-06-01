package com.px.share.service; 

import java.util.List;


/**
 *
 * @author Peach
 * @param <T>
 * @param <K>
 */
public interface GenericTreeService<T,K> extends GenericService<T,K> {
    String generateParentKey(T t);
    T getParent(T t);
    List<T> listChild(T t);
    List<T> listFromParentKey(T t);
}
