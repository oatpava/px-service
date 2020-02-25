
package com.px.wf.dao;

import com.px.share.dao.GenericTreeDao;
import com.px.wf.entity.ThegifDepartment;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;

/**
 *
 * @author TUMKUNG
 */
public interface ThegifDepartmentDao extends GenericTreeDao<ThegifDepartment, Integer>{
    List<ThegifDepartment> listAll(String sort,String dir);
    ThegifDepartment getByThegifDepartmentCode(String thegifDepartmentCode);
    List<ThegifDepartment> listByThegifDepartmentName(String thegifDepartmentName);
    List<ThegifDepartment> searchThegifDepartment(MultivaluedMap<String, String> queryParams,String sort,String dir);
    int countSearchThegifDepartment(MultivaluedMap<String, String> queryParams);
    Conjunction createConjunctionFormSearchThegifDepartment(MultivaluedMap<String, String> queryParams);
}
