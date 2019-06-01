package com.px.share.service;

import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author OPAS
 * @param <T>
 * @param <K>
 */
public interface GenericService<T,K> {
    T create(T t);
    T getById(int id);
    T getByIdNotRemoved(int id);
    T update(T t);
    T remove(int id,int userId);
    List<T> list(int offset,int limit,String sort,String dir);
    List<T> listAll(String sort,String dir);
    int countAll();
    List<T> search(MultivaluedMap<String, String> queryParams,int offset,int limit,String sort,String dir);
    int countSearch(MultivaluedMap<String, String> queryParams);
    K tranformToModel(T t);
    
}
