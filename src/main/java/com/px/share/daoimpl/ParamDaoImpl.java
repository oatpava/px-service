package com.px.share.daoimpl;

import com.px.share.dao.ParamDao;
import com.px.share.entity.Param;
import com.px.share.daoimpl.GenericDaoImpl;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ParamDaoImpl extends GenericDaoImpl<Param, Integer> implements ParamDao{
    private final String fieldSearch;
    public ParamDaoImpl() {
        super(Param.class);
        fieldSearch = ",paramName,";
    }

    @Override
    public Param getByParamName(String paramName) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.eq("paramName", paramName));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        
        return this.getOneByCriteria(criteria);
    }
    
    public List<Param> getByName(String name) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.ilike("paramName", name, MatchMode.END));
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        
        return this.listByCriteria(criteria);
    }
    
    @Override
    public List<Param> list(int offset,int limit,String sort,String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }

    @Override
    public List<Param> listAll(String sort,String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);        
        return this.listByCriteria(criteria);        
    }    

    @Override
    public Integer countAll() {    
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));        
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        return this.countAll(criteria);
    }

    private DetachedCriteria createOrder(DetachedCriteria criteria,String sort,String dir){
        if(!sort.isEmpty()){
            if((!dir.isEmpty()) && dir.equalsIgnoreCase("asc")){
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.asc("this.createdDate"));
                        break;
                }
            }else if((!dir.isEmpty()) && dir.equalsIgnoreCase("desc")){
                switch (sort) {
                    case "createdDate":
                        criteria.addOrder(Order.desc("this.createdDate"));
                        break;
                }
            }            
        }else{
            criteria.addOrder(Order.desc("this.createdDate")); 
        }
        return criteria;
    }
    
    @Override
    public Param getByIdNotRemoved(Integer id) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("id", id));
        conjunction.add(Restrictions.eq("removedBy", 0));
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        return this.getOneByCriteria(criteria);
    }
    
    public List<Param> search(MultivaluedMap<String, String> queryParams, int offset, int limit,String sort,String dir){
        Conjunction conjunction = createConjunctionFormSearch(queryParams);
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria, offset, limit);
    }
    
    public List<Param> listByParamName(String paramName,int offset,int limit,String sort,String dir) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("removedBy", 0));
        conjunction.add(Restrictions.like("paramName", paramName, MatchMode.START));
        DetachedCriteria criteria = DetachedCriteria.forClass(Param.class);
        criteria.add(conjunction);
        criteria = createOrder(criteria,sort,dir);
        return this.listByCriteria(criteria,offset,limit);
    }
    
    private Conjunction createConjunctionFormSearch(MultivaluedMap<String, String> queryParams){
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("this.removedBy", 0));
        for (String key : queryParams.keySet()) {
            if(fieldSearch.contains(","+key+",")){
                for (String value : queryParams.get(key)) {
                    String[] valueArray = value.split(",");
                    int i = 0;
                    for(i = 0;i<valueArray.length;i++){
                        switch (key) {
                            case "paramName":
                                conjunction.add(Restrictions.like("this.paramName", valueArray[i],MatchMode.START));
                                break;
                        }
                    }
                }
            }
        }
        return conjunction;
    }
}
