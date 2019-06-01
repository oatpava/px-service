package com.px.admin.daoimpl;

import com.px.admin.dao.StructureDao;
import com.px.admin.entity.Structure;
import com.px.admin.entity.UserProfile;
import com.px.admin.service.UserProfileService;
import com.px.share.daoimpl.GenericTreeDaoImpl;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OPAS
 */
public class StructureDaoImpl extends GenericTreeDaoImpl<Structure, Integer> implements StructureDao {

    private final String fieldSearch;

    public StructureDaoImpl() {
        super(Structure.class);
        fieldSearch = ",structureName,structureCode,";
    }

    @Override
    public List<Structure> list(int offset, int limit, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    @Override
    public List<Structure> listAll(String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Integer countDup(String code, String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        if (!"".equals(code) && !"".equals(name)) {
            conjunction.add(Restrictions.disjunction()
                    .add(Restrictions.eq("structureCode", code))
                    .add(Restrictions.eq("structureName", name))
            );
        } else if ("".equals(code)) {
            conjunction.add(Restrictions.eq("structureName", name));
        } else if ("".equals(name)) {
            conjunction.add(Restrictions.eq("structureCode", code));
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Integer countAll() {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    @Override
    public Structure getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public List<Structure> leaveReportStatStructure(int userId, String sort, String dir) {
        String tmpStructureKey = "฿1฿5฿";
        UserProfileService userProfileService = new UserProfileService();
        UserProfile userProfile = new UserProfile();
        userProfile = userProfileService.getById(userId);
        if (userProfile != null) {
            String tmpParentKey = userProfile.getStructure().getParentKey();
            if (tmpParentKey.length() > 5) {
                tmpParentKey = tmpParentKey.substring(5, tmpParentKey.length());
                tmpParentKey = tmpParentKey.substring(0, tmpParentKey.indexOf("฿"));
                tmpStructureKey += tmpParentKey + "฿";
            }
        }

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("nodeLevel", 2));
        conjunction.add(Restrictions.like("parentKey", tmpStructureKey, MatchMode.START));

        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public List<Structure> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    public List<Structure> searchByNameOrCode(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        Disjunction disjunction = createDisjunctionFormSearch(queryParams);
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        conjunction.add(disjunction);
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria, offset, limit);
    }

    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        for (String key : queryParams.keySet()) {
            if (fieldSearch.contains("," + key + ",")) {
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for (i = 0; i < valueArray.length; i++) {
                        switch (key) {
                            case "structureName":
                                conjunction.add(Restrictions.like("this.structureName", valueArray[i], MatchMode.START));
                                break;
                        }
                    }
                }
            }
        }
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
                            case "structureName":
                                disjunction.add(Restrictions.like("this.structureName", valueArray[i], MatchMode.ANYWHERE));
                                disjunction.add(Restrictions.like("this.structureCode", valueArray[i], MatchMode.ANYWHERE));
                                break;
                        }
                    }
                }
            }
        }
        return disjunction;
    }

    public List<Structure> listStructureByType(int type, String sort, String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureType", type));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria, sort, dir);
        return this.listByCriteria(criteria);
    }

    public Structure getPrevOrderBy(int id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("id", id - 1));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    public Structure getByCode(String code) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("structureCode", code));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }

    //oat-add
    public List<Structure> listByName(String structureName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("structureName", structureName));
        DetachedCriteria criteria = DetachedCriteria.forClass(Structure.class);
        criteria.add(conjunction).addOrder(Order.desc("this.createdDate"));
        return this.listByCriteria(criteria);
    }

}
