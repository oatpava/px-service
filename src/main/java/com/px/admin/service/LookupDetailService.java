package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.LookupDetailDaoImpl;
import com.px.admin.entity.LookupDetail;
import com.px.admin.model.LookupDetailModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class LookupDetailService implements GenericService<LookupDetail, LookupDetailModel>{
    private static final Logger LOG = Logger.getLogger(LookupDetailService.class.getName());
    private final LookupDetailDaoImpl lookupDetailDaoImpl;
    
    public LookupDetailService() {
        this.lookupDetailDaoImpl = new LookupDetailDaoImpl();
    }

    @Override
    public LookupDetail create(LookupDetail lookupDetail) {
        checkNotNull(lookupDetail, "lookupDetail entity must not be null");
        checkNotNull(lookupDetail.getLookupDetailName(), "lookupDetail name must not be null");
        checkNotNull(lookupDetail.getCreatedBy(),"create by must not be null");
        lookupDetail = lookupDetailDaoImpl.create(lookupDetail);
        if(lookupDetail.getOrderNo()==0){
            lookupDetail.setOrderNo(lookupDetail.getId());
            lookupDetail = update(lookupDetail);
        }
        return lookupDetail;
    }

    @Override
    public LookupDetail getById(int id) {
        checkNotNull(id, "lookupDetail id entity must not be null");
        return lookupDetailDaoImpl.getById(id);
    }

    @Override
    public LookupDetail update(LookupDetail lookupDetail) {
        checkNotNull(lookupDetail, "lookupDetail entity must not be null");
        checkNotNull(lookupDetail.getLookupDetailName(), "lookupDetail name must not be null");
        checkNotNull(lookupDetail.getUpdatedBy(),"update by must not be null");
        lookupDetail.setUpdatedDate(LocalDateTime.now());
        return lookupDetailDaoImpl.update(lookupDetail);
    }

    @Override
    public LookupDetail remove(int id, int userId) {
        checkNotNull(id, "lookupDetail id must not be null");
        LookupDetail lookupDetail = getById(id);
        checkNotNull(lookupDetail, "lookupDetail entity not found in database.");
        lookupDetail.setRemovedBy(userId);
        lookupDetail.setRemovedDate(LocalDateTime.now());
        return lookupDetailDaoImpl.update(lookupDetail);
    }

    @Override
    public List<LookupDetail> listAll(String sort, String dir) {
        return lookupDetailDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<LookupDetail> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return lookupDetailDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public LookupDetailModel tranformToModel(LookupDetail lookupDetail) {
        LookupDetailModel lookupDetailModel = null;
        if(lookupDetail!=null){
            lookupDetailModel = new LookupDetailModel();
            lookupDetailModel.setId(lookupDetail.getId());
            lookupDetailModel.setName(lookupDetail.getLookupDetailName());
        }
        return lookupDetailModel;
    }

    @Override
    public int countAll() {
        return lookupDetailDaoImpl.countAll();
    }

    @Override
    public List<LookupDetail> search(MultivaluedMap<String, String> queryLookupDetails, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryLookupDetails) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<LookupDetail> listAllByLookupId(int lookupId, String sort, String dir) {
        return lookupDetailDaoImpl.listAllByLookupId(lookupId,sort, dir);
    }
    
    @Override
    public LookupDetail getByIdNotRemoved(int id) {
        checkNotNull(id, "LookupDetail id entity must not be null");
        return lookupDetailDaoImpl.getByIdNotRemoved(id);
    }
}