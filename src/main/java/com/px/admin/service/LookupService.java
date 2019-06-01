package com.px.admin.service;

import static com.google.common.base.Preconditions.checkNotNull;
import com.px.admin.daoimpl.LookupDaoImpl;
import com.px.admin.entity.Lookup;
import com.px.admin.model.LookupModel;
import com.px.share.service.GenericService;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author OPAS
 */
public class LookupService implements GenericService<Lookup, LookupModel>{
    private static final Logger LOG = Logger.getLogger(LookupService.class.getName());
    private final LookupDaoImpl lookupDaoImpl;
    
    public LookupService() {
        this.lookupDaoImpl = new LookupDaoImpl();
    }

    @Override
    public Lookup create(Lookup lookup) {
        checkNotNull(lookup, "lookup entity must not be null");
        checkNotNull(lookup.getLookupName(), "lookup name must not be null");
        checkNotNull(lookup.getCreatedBy(),"create by must not be null");
        lookup = lookupDaoImpl.create(lookup);
        if(lookup.getOrderNo()==0){
            lookup.setOrderNo(lookup.getId());
            lookup = update(lookup);
        }
        return lookup;
    }

    @Override
    public Lookup getById(int id) {
        checkNotNull(id, "lookup id entity must not be null");
        return lookupDaoImpl.getById(id);
    }

    @Override
    public Lookup update(Lookup lookup) {
        checkNotNull(lookup, "lookup entity must not be null");
        checkNotNull(lookup.getLookupName(), "lookup name must not be null");
        checkNotNull(lookup.getUpdatedBy(),"update by must not be null");
        lookup.setUpdatedDate(LocalDateTime.now());
        return lookupDaoImpl.update(lookup);
    }

    @Override
    public Lookup remove(int id, int userId) {
        checkNotNull(id, "lookup id must not be null");
        Lookup lookup = getById(id);
        checkNotNull(lookup, "lookup entity not found in database.");
        lookup.setRemovedBy(userId);
        lookup.setRemovedDate(LocalDateTime.now());
        return lookupDaoImpl.update(lookup);
    }

    @Override
    public List<Lookup> listAll(String sort, String dir) {
        return lookupDaoImpl.listAll(sort, dir);
    }

    @Override
    public List<Lookup> list(int offset, int limit, String sort, String dir) {
        checkNotNull(offset, "offset must not be null");
        checkNotNull(limit, "limit must not be null");
        return lookupDaoImpl.list(offset, limit, sort, dir);
    }

    @Override
    public LookupModel tranformToModel(Lookup lookup) {
        LookupModel lookupModel = null;
        if(lookup!=null){
            lookupModel = new LookupModel();
            lookupModel.setId(lookup.getId());
            lookupModel.setName(lookup.getLookupName());
        }
        return lookupModel;
    }

    @Override
    public int countAll() {
        return lookupDaoImpl.countAll();
    }

    @Override
    public List<Lookup> search(MultivaluedMap<String, String> queryLookups, int offset, int limit, String sort, String dir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countSearch(MultivaluedMap<String, String> queryLookups) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Lookup getByIdNotRemoved(int id) {
        checkNotNull(id, "Lookup id entity must not be null");
        return lookupDaoImpl.getByIdNotRemoved(id);
    }
    
}
