package com.px.share.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.share.daoimpl.MonthDaoImpl;
import com.px.share.entity.Month;
import com.px.share.model.MonthModel;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Peach
 */
public class MonthService implements GenericService<Month, MonthModel>{
    private static final Logger LOG = Logger.getLogger(MonthService.class.getName());
    private final MonthDaoImpl monthDaoImpl;
    
    public MonthService() {
        this.monthDaoImpl = new MonthDaoImpl();
    }

    @Override
    public Month create(Month month) {
        checkNotNull(month, "month entity must not be null");
        checkNotNull(month.getOrderFiscal(), "month order fiscal must not be null");
        checkNotNull(month.getThaiName(), "month thai name must not be null");
        checkNotNull(month.getThaiNameAbbr(), "month thai name abbr must not be null");
        checkNotNull(month.getEngName(), "month eng name must not be null");
        checkNotNull(month.getEngNameAbbr(), "month eng abbr name must not be null");
        checkNotNull(month.getCreatedBy(),"create by must not be null");
        month = monthDaoImpl.create(month);
        if(month.getOrderNo()==0){
            month.setOrderNo(month.getId());
            month = monthDaoImpl.update(month);
        }
        
        return month;
    }

    @Override
    public Month getById(int id) {
        checkNotNull(id, "month id entity must not be null");
        return monthDaoImpl.getById(id);
    }

    @Override
    public Month getByIdNotRemoved(int id) {
        checkNotNull(id, "month id entity must not be null");
        return monthDaoImpl.getByIdNotRemoved(id);
    }

    @Override
    public Month update(Month month) {
        checkNotNull(month, "month entity must not be null");
        checkNotNull(month.getOrderFiscal(), "month order fiscal must not be null");
        checkNotNull(month.getThaiName(), "month thai name must not be null");
        checkNotNull(month.getThaiNameAbbr(), "month thai name abbr must not be null");
        checkNotNull(month.getEngName(), "month eng name must not be null");
        checkNotNull(month.getEngNameAbbr(), "month eng abbr name must not be null");
        checkNotNull(month.getUpdatedBy(),"update by must not be null");
        month.setUpdatedDate(LocalDateTime.now());
        return monthDaoImpl.update(month);
    }

    @Override
    public Month remove(int id, int userId) {
        checkNotNull(id, "month id must not be null");
        Month month = getById(id);
        checkNotNull(month, "month entity not found in database.");
        month.setRemovedBy(userId);
        month.setRemovedDate(LocalDateTime.now());
        return monthDaoImpl.update(month);
    }

    @Override
    public List<Month> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return monthDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public List<Month> listAll(String sort, String dir) {
        return monthDaoImpl.listAll(sort, dir);
    }

    @Override
    public int countAll() {
        return monthDaoImpl.countAll();
    }

    @Override
    public List<Month> search(MultivaluedMap<String, String> queryParams, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryParams) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MonthModel tranformToModel(Month month) {
        MonthModel monthModel = null;
        if(month!=null){
            monthModel = new MonthModel();
            monthModel.setId(month.getId());
            monthModel.setOrderFiscal(month.getOrderFiscal());
            monthModel.setThaiName(month.getThaiName());
            monthModel.setThaiNameAbbr(month.getThaiNameAbbr());
            monthModel.setEngName(month.getEngName());
            monthModel.setEngNameAbbr(month.getEngNameAbbr());
        }
        return monthModel;
    }

}
