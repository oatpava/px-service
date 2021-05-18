package com.px.dms.daoimpl;

import com.px.dms.dao.WfDocumentTypeDao;
import com.px.dms.entity.WfDocumentType;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author CHALERMPOL
 */
public class WfDocumentTypeDaoImpl extends GenericTreeDaoImpl<WfDocumentType, Integer> implements WfDocumentTypeDao {

    private final String fieldSearch;

    public WfDocumentTypeDaoImpl() {
        super(WfDocumentType.class);
        fieldSearch = ",documentTypeName,documentTypeCode,";
    }

    @Override
    public List<WfDocumentType> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<WfDocumentType> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public WfDocumentType getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<WfDocumentType> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<WfDocumentType> searchByNameOrCode(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Disjunction disjunction = createDisjunctionFormSearch(queryParams);
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(disjunction);
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = Restrictions.conjunction();
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains("," + key + ",")) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "documentTypeName":
                                conjunction.add(Restrictions.like("this.documentTypeName", valueArray[i], MatchMode.START));
                                break;
                        }
                    }
                }
            }
        }
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        return conjunction;
    }

    private Disjunction createDisjunctionFormSearch(MultivaluedMap<String, String> queryParams) {
        Disjunction disjunction = Restrictions.disjunction();
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains("," + key + ",")) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "documentTypeName":
                                disjunction.add(Restrictions.like("this.documentTypeName", valueArray[i], MatchMode.ANYWHERE));
                                disjunction.add(Restrictions.like("this.documentTypeCode", valueArray[i], MatchMode.ANYWHERE));
                                break;
                        }
                    }
                }
            }
        }
        return disjunction;
    }

    @Override
    public List<WfDocumentType> listAllParent(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("parentId", 0));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public List<WfDocumentType> listChildByParentId(String sort, String dir, int parentId) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("parentId", parentId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    @Override
    public Integer getMaxCode(int parentId) {
        Integer maxTypeId;
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("parentId", parentId));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        maxTypeId = this.max(criteria, "id");
        return maxTypeId;
    }

    public WfDocumentType getwftypeByname(String wfTypeName) {

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("documentTypeName", wfTypeName));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
        WfDocumentType wfDocumentType = new WfDocumentType();
        try {

            wfDocumentType = this.getOneByCriteria(criteria);
            return wfDocumentType;
        } catch (Exception e) {
            return wfDocumentType;
        }

//        return wfDocumentType;
    }

    public List<WfDocumentType> listWfTypeByTypeCode(String wfTypeCode) {

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("documentTypeCode", wfTypeCode, MatchMode.ANYWHERE));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(WfDocumentType.class);
        criteria.add(conjunction);
//        WfDocumentType wfDocumentType = new WfDocumentType();
        try {

//            wfDocumentType = this.getOneByCriteria(criteria);
            return this.listByCriteria(criteria, 0, 1000);
        } catch (Exception e) {

            return null;
        }

//        return wfDocumentType;
    }

}
