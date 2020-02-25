
package com.px.wf.dao;

import com.px.share.dao.GenericDao;
import com.px.wf.entity.Thegif;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
/**
 *
 * @author Mali
 */
public interface ThegifDao extends GenericDao<Thegif, Integer>{
    List<Thegif> listByElementType(String elementType,int offset,int limit,String sort,String dir);
    List<Thegif> listByElementType(String elementType);
    List<Thegif> listAll(String sort,String dir);
    Integer countAllListByElementType(String elementType); 
    List<Thegif> searchThegifReceive(MultivaluedMap<String, String> queryParams,int offset,int limit,String sort,String dir);
    int countSearchThegifReceive(MultivaluedMap<String, String> queryParams);
    Conjunction createConjunctionFormSearchThegifReceive(MultivaluedMap<String, String> queryParams);
    List<Thegif> searchThegifStatus(MultivaluedMap<String, String> queryParams,int offset,int limit,String sort,String dir);
    int countSearchThegifStatus(MultivaluedMap<String, String> queryParams);
    Conjunction createConjunctionFormSearchThegifStatus(MultivaluedMap<String, String> queryParams);
}
